package com.fpt.edu.herofundbackend.controller;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.payment.PaymentOder;
import com.fpt.edu.herofundbackend.dto.payment.paypal.PaypalPaymentDto;
import com.fpt.edu.herofundbackend.exception.MyBadRequestException;
import com.fpt.edu.herofundbackend.security.JwtUtils;
import com.fpt.edu.herofundbackend.service.AuthenticateService;
import com.fpt.edu.herofundbackend.service.PaypalService;
import com.fpt.edu.herofundbackend.util.MyStringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/payments")
public class PaymentController {

    private final AuthenticateService authenticateService;
    private final JwtUtils jwtUtils;
    private final PaypalService paypalService;

    @RequestMapping(path = "paypal", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> createPayment(@RequestBody @Valid PaymentOder request, HttpServletRequest servlet) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.SYSTEM_ERROR);
        String returnUrl = servlet.getHeader(SystemConstant.FIELD.RETURN_URL);
        String cancelUrl = servlet.getHeader(SystemConstant.FIELD.CANCEL_URL);
        if (MyStringUtils.isNullOrEmptyWithTrim(returnUrl) || MyStringUtils.isNullOrEmptyWithTrim(cancelUrl)) {
            throw new MyBadRequestException(SystemConstant.Message.INVALID_CONFIG);
        }
        if (!request.isAnonymous() && MyStringUtils.isNullOrEmptyWithTrim(request.getSenderName())) {
            throw new MyBadRequestException(SystemConstant.Message.ANONYMOUS_FALSE);
        }
        String token = jwtUtils.getTokenHeader(servlet);
        if (token != null) {
            String username = jwtUtils.extractUsername(token);
           request.setAccountId(authenticateService.getAccountIdByUsername(username));
        }

        PaypalPaymentDto dto = paypalService.createPayment(request, returnUrl, cancelUrl);
        if (dto == null) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(dto, SystemConstant.Message.SAVE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "paypal/execute", method = RequestMethod.POST)
    public CommonResponse executePayment(
            @RequestParam(name = "payId") String payId,
            @RequestParam(name = "paymentChannelId") long paymentChannelId) {
        return paypalService.paymentExecute(payId, paymentChannelId);
    }
}
