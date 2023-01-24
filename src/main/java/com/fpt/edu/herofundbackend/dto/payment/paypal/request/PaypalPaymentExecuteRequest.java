package com.fpt.edu.herofundbackend.dto.payment.paypal.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaypalPaymentExecuteRequest {

    private String payId;
    private long paymentChannelId;
}
