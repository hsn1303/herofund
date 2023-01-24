package com.fpt.edu.herofundbackend.service;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.channel.PaymentChannelAdminDto;
import com.fpt.edu.herofundbackend.dto.payment.paypal.request.PaypalPaymentRequest;
import com.fpt.edu.herofundbackend.entity.PaymentChannel;
import com.fpt.edu.herofundbackend.enums.BaseStatusEnum;
import com.fpt.edu.herofundbackend.exception.MyNotFoundException;
import com.fpt.edu.herofundbackend.repository.PaymentChannelRepository;
import com.fpt.edu.herofundbackend.util.MyCollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class PaymentChannelService {

    private final PaymentChannelRepository paymentChannelRepository;

    public PaymentChannel save(PaypalPaymentRequest request) {
        try {
            PaymentChannel paymentChannel = new PaymentChannel(request);
            paymentChannel.setStatus(BaseStatusEnum.ENABLE.getKey());
            return paymentChannelRepository.save(paymentChannel);
        } catch (Exception e) {
            return null;
        }
    }

    public PaymentChannel update(PaypalPaymentRequest request) {
        PaymentChannel exist = paymentChannelRepository.findById(request.getId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.PAYMENT_CHANNEL_NOT_FOUND));
        try {
            exist.setName(request.getName());
            exist.setClientId(request.getClientId());
            exist.setSecretId(request.getSecretId());
            exist.setPayerId(request.getPayerId());
            return paymentChannelRepository.save(exist);
        } catch (Exception e) {
            return null;
        }
    }

    public PaymentChannel updateStatusById(long id, int status) {
        PaymentChannel channel = paymentChannelRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.PAYMENT_CHANNEL_NOT_FOUND));
        channel.setStatus(status);
        try {
            return paymentChannelRepository.save(channel);
        } catch (Exception e) {
            return null;
        }
    }

    public List<PaymentChannel> getAll() {
        return paymentChannelRepository.findAll();
    }

    public List<PaymentChannel> updateStatusChannels(List<Long> ids, int status) {
        try {
            List<PaymentChannel> channels = paymentChannelRepository.getChannelByIds(ids);
            if (MyCollectionUtils.listIsNullOrEmpty(channels)) {
                return null;
            }
            channels.forEach(c -> c.setStatus(status));
            return paymentChannelRepository.saveAll(channels);
        } catch (Exception e) {
            return null;
        }

    }

    public PaymentChannelAdminDto getDetail(long id) {
        PaymentChannel channel = paymentChannelRepository.getPaymentChannelByIdAndStatus(id, BaseStatusEnum.ENABLE.getKey())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.PAYMENT_CHANNEL_NOT_FOUND));
        return new PaymentChannelAdminDto(channel);
    }

    public List<PaymentChannel> getPaymentChannelByStatus(int status) {
        return paymentChannelRepository.getAllByStatus(status);
    }
}
