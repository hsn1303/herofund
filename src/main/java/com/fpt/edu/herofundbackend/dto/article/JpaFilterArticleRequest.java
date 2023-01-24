package com.fpt.edu.herofundbackend.dto.article;

import com.fpt.edu.herofundbackend.util.DateUtils;
import com.fpt.edu.herofundbackend.util.MyStringUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JpaFilterArticleRequest {
    private String keyword;
    private Long campaignId;
    private String campaignTitle;
    private Long id;
    private LocalDateTime startDateCreateAt;
    private LocalDateTime endDateCreateAt;
    private Integer status;

    public JpaFilterArticleRequest(FilterArticleRequest request){
        this.keyword = MyStringUtils.isNullOrEmptyWithTrim(request.getKeyword()) ? null : request.getKeyword() ;
        this.campaignId = request.getCampaignId();
        this.id = request.getId();
        this.campaignTitle = MyStringUtils.isNullOrEmptyWithTrim(request.getCampaignTitle()) ? null : request.getCampaignTitle() ;
        this.status = request.getStatus();
        this.startDateCreateAt = !MyStringUtils.isNullOrEmptyWithTrim(request.getStartDateCreateAt()) ?
                DateUtils.stringToLocalDateTimeStartDate(request.getStartDateCreateAt()) : null;
        this.endDateCreateAt = !MyStringUtils.isNullOrEmptyWithTrim(request.getEndDateCreateAt()) ?
                DateUtils.stringToLocalDateTimeEndDate(request.getEndDateCreateAt()) : null;

    }
}
