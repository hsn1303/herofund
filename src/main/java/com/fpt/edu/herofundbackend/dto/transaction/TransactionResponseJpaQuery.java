package com.fpt.edu.herofundbackend.dto.transaction;

import com.fpt.edu.herofundbackend.util.MyStringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransactionResponseJpaQuery {

    private String senderName;
    private String message;
    private long amount;
    private LocalDateTime sendingTime;
    private String campaign;
    private String paymentChannel;
    private String paymentStatus;
    private String paypalTransactionId;

    public TransactionResponseJpaQuery(
            String senderName, String message, long amount,
            LocalDateTime sendingTime, String campaign,
            String paymentChannel, String paymentStatus, String paypalTransactionId) {
        this.senderName = MyStringUtils.isNullOrEmptyWithTrim(senderName) ? "Anonymous" : senderName;
        this.message = message;
        this.amount = amount;
        this.sendingTime = sendingTime;
        this.campaign = campaign;
        this.paymentChannel = paymentChannel;
        this.paymentStatus = paymentStatus;
        this.paypalTransactionId = paypalTransactionId;
    }
}
