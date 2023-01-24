package com.fpt.edu.herofundbackend.service;


import com.fpt.edu.herofundbackend.config.PaypalProperties;
import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.payment.PaymentOder;
import com.fpt.edu.herofundbackend.dto.payment.paypal.PaypalPaymentDto;
import com.fpt.edu.herofundbackend.dto.payment.paypal.common.Link;
import com.fpt.edu.herofundbackend.dto.payment.paypal.request.RootPaypalRequest;
import com.fpt.edu.herofundbackend.dto.payment.paypal.response.PaypalExecuteResponse;
import com.fpt.edu.herofundbackend.dto.payment.paypal.response.PaypalTokenResponse;
import com.fpt.edu.herofundbackend.dto.payment.paypal.response.RootPaypalResponse;
import com.fpt.edu.herofundbackend.entity.Campaign;
import com.fpt.edu.herofundbackend.entity.PaymentChannel;
import com.fpt.edu.herofundbackend.entity.Transaction;
import com.fpt.edu.herofundbackend.enums.BaseStatusEnum;
import com.fpt.edu.herofundbackend.enums.CampaignStatusEnum;
import com.fpt.edu.herofundbackend.enums.PaymentStatusEnum;
import com.fpt.edu.herofundbackend.exception.MyNotFoundException;
import com.fpt.edu.herofundbackend.exception.MySystemException;
import com.fpt.edu.herofundbackend.repository.CampaignRepository;
import com.fpt.edu.herofundbackend.repository.PaymentChannelRepository;
import com.fpt.edu.herofundbackend.repository.TransactionRepository;
import com.fpt.edu.herofundbackend.util.MapUtils;
import com.fpt.edu.herofundbackend.util.MyStringUtils;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.fpt.edu.herofundbackend.constant.PaypalConstant.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaypalService {

    private final RestTemplate restTemplate;
    private final PaypalProperties paypalProperties;
    private final TransactionRepository transactionRepository;
    private final CampaignRepository campaignRepository;
    private final PaymentChannelRepository paymentChannelRepository;
    private final Gson gson;

    public PaypalPaymentDto createPayment(PaymentOder request, String returnUrl, String cancelUrl) {
        Campaign campaign = this.checkExistCampaign(request.getCampaignId());
        PaymentChannel paymentChannel = this.checkExistPaymentChannel(request.getPaymentChannel());
        this.handlerTokenIfExpired(paymentChannel);
        String token = paymentChannel.getToken();

        RootPaypalRequest rootPaypalRequest = new RootPaypalRequest(request, returnUrl, cancelUrl, campaign.getTitle());
        HttpEntity<String> requestPaypalEntity =
                new HttpEntity<>(gson.toJson(rootPaypalRequest), createHeadersPaymentPaypal(token));
        String url = paypalProperties.getBaseUrl() + URL_PAYMENT;
        ResponseEntity<RootPaypalResponse> response = restTemplate.postForEntity(url, requestPaypalEntity, RootPaypalResponse.class);
        int code = response.getStatusCodeValue();
        if ((code != 201 && code != 200) || null == response.getBody()) {
            throw new MySystemException(SystemConstant.Message.SYSTEM_ERROR);
        }
        RootPaypalResponse paypalResponse = response.getBody();
        Transaction transaction = MapUtils.fromPaypalOrderToTransaction(request);
        transaction.setPaypalTransactionId(null);
        transaction.setOrderId(paypalResponse.getId());
        transaction.setPaymentStatus(PaymentStatusEnum.UNPAID.name());
        Link link = paypalResponse.getLinks()
                .stream()
                .filter(l -> l.getRel().equalsIgnoreCase(REL_APPROVAL_URL))
                .findFirst()
                .orElse(null);
        if (link == null) {
            throw new MySystemException(SystemConstant.Message.SYSTEM_ERROR);
        }
        try {
            return new PaypalPaymentDto(transactionRepository.save(transaction), link, paypalResponse.getId());
        } catch (Exception e) {
            return null;
        }

    }

    public CommonResponse paymentExecute(String orderId, long paymentChannelId) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setFailResponse(SystemConstant.Message.SYSTEM_ERROR);
        PaymentChannel paymentChannel = paymentChannelRepository.getPaymentChannelByIdAndStatus(paymentChannelId, BaseStatusEnum.ENABLE.getKey())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.PAYMENT_CHANNEL_NOT_FOUND));

        HttpEntity<Object> requestExecute = new HttpEntity<>(createHeadersPaymentPaypalExecute(paymentChannel.getToken()));

        ResponseEntity<PaypalExecuteResponse> response = restTemplate.postForEntity(
                String.format(paypalProperties.getBaseUrl() + URL_PAYMENT_EXECUTE, orderId),
                requestExecute,
                PaypalExecuteResponse.class
        );
        int code = response.getStatusCodeValue();
        if ((code != 201 && code != 200) || null == response.getBody()) {
            throw new MySystemException();
        }
        PaypalExecuteResponse executeResponse = response.getBody();
        String transactionPaypalId = executeResponse.getPurchase_units().get(0).getPayments().getCaptures().get(0).getId();
        commonResponse.setSuccessResponse(SystemConstant.Message.PAYMENT_SUCCESS);
        PaypalPaymentDto transactionDto = new PaypalPaymentDto(updateCampaignAndTransactionAfterDonateSuccess(orderId, transactionPaypalId));
        commonResponse.setSuccessWithDataResponse(transactionDto);
        return commonResponse;
    }

    private Transaction updateCampaignAndTransactionAfterDonateSuccess(String orderId, String transactionPaypalId) {
        Transaction transaction = transactionRepository.getTransactionByPaypalOrderId(orderId)
                .orElseThrow(() -> new MySystemException(SystemConstant.Message.SYSTEM_ERROR));
        transaction.setPaymentStatus(PaymentStatusEnum.DONE.name());
        transaction.setPaypalTransactionId(transactionPaypalId);
        transaction.setSendingTime(LocalDateTime.now());
        transactionRepository.save(transaction);

        Campaign campaign = campaignRepository.findById(transaction.getCampaignId())
                .orElseThrow(() -> new MySystemException(SystemConstant.Message.SYSTEM_ERROR));
        long currentAmount = campaign.getCurrentAmount();
        long countDonor = campaign.getDonations();
        campaign.setCurrentAmount(currentAmount + transaction.getAmount());
        campaign.setDonations(countDonor + 1);
        campaignRepository.save(campaign);
        return transaction;
    }


    private void handlerTokenIfExpired(PaymentChannel paymentChannel) {
        long currentDate = System.currentTimeMillis() / 1000;
        long expired = paymentChannel.getExpired();
        long result = (expired - currentDate) / 60;
        if (!MyStringUtils.isNullOrEmptyWithTrim(paymentChannel.getToken()) &&
                result > 10) {
            return;
        }
        PaypalTokenResponse tokenResponse = getTokenConnectPaypal(paymentChannel.getClientId(), paymentChannel.getSecretId());
        if (tokenResponse == null) {
            throw new MySystemException();
        }
        paymentChannel.setExpired(currentDate + tokenResponse.getExpires_in());
        paymentChannel.setToken(tokenResponse.getAccess_token());
        paymentChannelRepository.save(paymentChannel);
    }

    private PaypalTokenResponse getTokenConnectPaypal(String clientId, String secretId) {
        try {
            HttpHeaders headers = new HttpHeaders() {{
                String auth = clientId + ":" + secretId;
                byte[] encodedAuth = Base64.encodeBase64(
                        auth.getBytes(StandardCharsets.US_ASCII));
                String authHeader = BASIC + new String(encodedAuth);
                set(AUTHORIZATION, authHeader);
                setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            }};

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add(GRANT_TYPE, CLIENT_CREDENTIALS);
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
            ResponseEntity<PaypalTokenResponse> response = restTemplate.exchange(paypalProperties.getBaseUrl() + URL_LOGIN, HttpMethod.POST, entity, PaypalTokenResponse.class);
            if (null == response.getBody()) {
                return null;
            }
            return response.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    private HttpHeaders createHeadersPaymentPaypal(String token) {
        return new HttpHeaders() {{
            setContentType(MediaType.APPLICATION_JSON);
            setBearerAuth(token);
            set("Prefer", "return=minimal");
        }};
    }

    private HttpHeaders createHeadersPaymentPaypalExecute(String token) {
        return new HttpHeaders() {{
            setContentType(MediaType.APPLICATION_JSON);
            setBearerAuth(token);
            set("Prefer", "return=minimal");
            set("PayPal-Request-Id", "A v4 style guid");
        }};
    }

    private Campaign checkExistCampaign(long campaignId) {
        List<Integer> status = Arrays.asList(
                CampaignStatusEnum.DISABLE.getKey(),
                CampaignStatusEnum.WAIT.getKey(),
                CampaignStatusEnum.REJECT.getKey());
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.CAMPAIGN_NOT_FOUND));
        if (status.contains(campaign.getStatus())) {
            throw new MyNotFoundException(SystemConstant.Message.CAMPAIGN_NOT_FOUND);
        }
        return campaign;
    }

    private PaymentChannel checkExistPaymentChannel(long paymentChannelId) {
        return paymentChannelRepository.getPaymentChannelByIdAndStatus(paymentChannelId, BaseStatusEnum.ENABLE.getKey())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.PAYMENT_CHANNEL_NOT_FOUND));
    }

}
