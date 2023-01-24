package com.fpt.edu.herofundbackend.dto.transaction;

import com.fpt.edu.herofundbackend.util.DateUtils;
import com.fpt.edu.herofundbackend.util.MyStringUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JpaFilterTransactionRequest {

    private String keyword;
    private Long campaignId;
    private Long paymentChannelId;
    private Long accountId;
    private Long startAmount;
    private Long endAmount;
    private String paymentStatus;
    private LocalDateTime startDateSendingTime;
    private LocalDateTime endDateSendingTime;

    public JpaFilterTransactionRequest(FilterTransactionRequest request) {
        this.keyword = MyStringUtils.isNullOrEmptyWithTrim(request.getKeyword()) ? null : request.getKeyword();
        this.campaignId = request.getCampaignId();
        this.paymentChannelId = request.getPaymentChannelId();
        this.startAmount = request.getStartAmount();
        this.endAmount = request.getEndAmount();
        this.paymentStatus = MyStringUtils.isNullOrEmptyWithTrim(request.getPaymentStatus()) ? null : request.getPaymentStatus();
        this.startDateSendingTime = !MyStringUtils.isNullOrEmptyWithTrim(request.getStartDateSendingTime()) ?
                DateUtils.stringToLocalDateTimeStartDate(request.getStartDateSendingTime()) : null;
        this.endDateSendingTime = !MyStringUtils.isNullOrEmptyWithTrim(request.getEndDateSendingTime()) ?
                DateUtils.stringToLocalDateTimeEndDate(request.getEndDateSendingTime()) : null;

    }
}
