package com.fpt.edu.herofundbackend.dto.campaign;

import com.fpt.edu.herofundbackend.util.DateUtils;
import com.fpt.edu.herofundbackend.util.MyStringUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JpaFilterCampaignRequest {

    private String keyword;
    private Long id;
    private Long accountId;
    private Long category;
    private Long targetAmountStart;
    private Long targetAmountEnd;
    private LocalDateTime startDateCreateAt;
    private LocalDateTime endDateCreateAt;
    private Integer status;

    public JpaFilterCampaignRequest(FilterCampaignRequest request) {
        this.keyword = MyStringUtils.isNullOrEmptyWithTrim(request.getKeyword()) ? null : request.getKeyword() ;
        this.category = request.getCategory();
        this.accountId = request.getAccountId();
        this.id = request.getId();
        this.targetAmountEnd = request.getTargetAmountEnd();
        this.targetAmountStart = request.getTargetAmountStart();
        this.status = request.getStatus();
        this.startDateCreateAt = !MyStringUtils.isNullOrEmptyWithTrim(request.getStartDateCreateAt()) ?
                DateUtils.stringToLocalDateTimeStartDate(request.getStartDateCreateAt()) : null;
        this.endDateCreateAt = !MyStringUtils.isNullOrEmptyWithTrim(request.getEndDateCreateAt()) ?
                DateUtils.stringToLocalDateTimeEndDate(request.getEndDateCreateAt()) : null;

    }
}
