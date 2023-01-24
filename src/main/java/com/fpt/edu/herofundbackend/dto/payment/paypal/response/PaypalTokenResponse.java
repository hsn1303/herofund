package com.fpt.edu.herofundbackend.dto.payment.paypal.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaypalTokenResponse {

    private String access_token;
    private long expires_in;
}
