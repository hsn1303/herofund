package com.fpt.edu.herofundbackend.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {

    private String senderName;
    private String message;
    private long amount;
    private LocalDateTime sendingTime;
    private String campaign;
    private String paymentChannel;
    private String paymentStatus;
    private String paypalTransactionId;
}
