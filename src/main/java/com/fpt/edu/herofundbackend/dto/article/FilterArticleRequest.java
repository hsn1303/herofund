package com.fpt.edu.herofundbackend.dto.article;

import lombok.Data;

@Data
public class FilterArticleRequest {
    private String keyword;
    private Long campaignId;
    private String campaignTitle;
    private Long id;
    private String startDateCreateAt;
    private String endDateCreateAt;
    private Integer status;
    private int limit = 1;
    private int offset = 10;
}
