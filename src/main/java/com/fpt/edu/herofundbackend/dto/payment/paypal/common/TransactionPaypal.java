package com.fpt.edu.herofundbackend.dto.payment.paypal.common;

import com.fpt.edu.herofundbackend.dto.payment.paypal.common.Amount;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class TransactionPaypal {

    private Amount amount;
    private String description;

    public TransactionPaypal(Amount amount, String description) {
        this.amount = amount;
        this.description = description;
    }
}
