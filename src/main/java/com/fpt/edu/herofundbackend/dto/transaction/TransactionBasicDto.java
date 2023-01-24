package com.fpt.edu.herofundbackend.dto.transaction;

import com.fpt.edu.herofundbackend.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionBasicDto {

    private String name;
    private long amount;
    private String sendingTime;
    private String message;

    public TransactionBasicDto(Transaction t) {
        this.name = t.getSenderName();
        this.amount = t.getAmount();
        this.sendingTime = t.getSendingTime().toString();
        this.message = t.getMessage();
    }
}
