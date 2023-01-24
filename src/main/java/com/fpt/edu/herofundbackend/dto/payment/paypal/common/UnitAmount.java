package com.fpt.edu.herofundbackend.dto.payment.paypal.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitAmount {
    private String currency_code;
    private String value;
}
