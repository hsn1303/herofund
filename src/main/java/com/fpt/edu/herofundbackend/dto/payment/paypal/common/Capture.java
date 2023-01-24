package com.fpt.edu.herofundbackend.dto.payment.paypal.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Capture {

    private String status;
    private String id;
}
