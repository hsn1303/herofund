package com.fpt.edu.herofundbackend.dto.payment.paypal.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Payer {

    public String payment_method;

    public Payer(String payment_method) {
        this.payment_method = payment_method;
    }
}
