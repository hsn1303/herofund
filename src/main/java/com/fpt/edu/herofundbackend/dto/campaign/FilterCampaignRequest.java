package com.fpt.edu.herofundbackend.dto.campaign;

import lombok.Data;

@Data
public class FilterCampaignRequest {

    private String keyword;
    private Long category;
    private Long id;
    private Long accountId;
    private Long targetAmountStart;
    private Long targetAmountEnd;
    private String startDateCreateAt;
    private String endDateCreateAt;
    private Integer status;
    private int limit = 1;
    private int offset = 10;
}
