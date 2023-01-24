package com.fpt.edu.herofundbackend.dto.payment.paypal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpt.edu.herofundbackend.dto.payment.paypal.common.Link;
import com.fpt.edu.herofundbackend.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaypalPaymentDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String senderName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long amount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long campaignId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long paymentChannel;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long accountId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String paymentStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String orderId;  // mã giao dịch của paypal
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Link link;

    public PaypalPaymentDto(Transaction transaction, Link link, String orderId) {
        this.id = transaction.getId();
        this.senderName = transaction.getSenderName();
        this.message = transaction.getMessage();
        this.amount = transaction.getAmount();
        this.campaignId = transaction.getCampaignId();
        this.paymentChannel = transaction.getPaymentChannel();
        this.accountId = transaction.getAccountId();
        this.paymentStatus = transaction.getPaymentStatus();
        this.orderId = orderId;
        this.link = link;
    }

    public PaypalPaymentDto(Transaction transaction) {
        this.id = transaction.getId();
        this.senderName = transaction.getSenderName();
        this.message = transaction.getMessage();
        this.amount = transaction.getAmount();
        this.campaignId = transaction.getCampaignId();
        this.paymentChannel = transaction.getPaymentChannel();
        this.accountId = transaction.getAccountId();
        this.paymentStatus = transaction.getPaymentStatus();
        this.orderId = transaction.getOrderId();
    }
}
