package com.fpt.edu.herofundbackend.dto.payment;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentOder {

    private long accountId;
    private String senderName;
    private boolean anonymous = false;
    private String message;

    @Min(value = 1, message = SystemConstant.Message.AMOUNT_GREATER_THAN_ZERO)
    private long amount;
    private String sendingTime;
    private long campaignId;
    private long paymentChannel;
}
