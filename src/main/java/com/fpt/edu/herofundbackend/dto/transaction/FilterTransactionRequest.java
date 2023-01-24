package com.fpt.edu.herofundbackend.dto.transaction;

import lombok.Data;

@Data
public class FilterTransactionRequest {

    private String keyword;
    private Long campaignId;
    private Long paymentChannelId;
    private Long startAmount;
    private Long endAmount;
    private String paymentStatus;
    private String startDateSendingTime;
    private String endDateSendingTime;
    private int offset = 1;
    private int limit = 10;

}
