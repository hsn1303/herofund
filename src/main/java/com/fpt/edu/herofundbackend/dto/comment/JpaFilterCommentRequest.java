package com.fpt.edu.herofundbackend.dto.comment;

import com.fpt.edu.herofundbackend.dto.article.FilterArticleRequest;
import com.fpt.edu.herofundbackend.util.DateUtils;
import com.fpt.edu.herofundbackend.util.MyStringUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JpaFilterCommentRequest {
    private String content;
    private String username;
    private Long articleId;
    private String articleTitle;
    private Long accountId;
    private Long id;
    private LocalDateTime startDateCreateAt;
    private LocalDateTime endDateCreateAt;
    private Integer status;

    public JpaFilterCommentRequest(FilterCommentRequest request){
        this.content = MyStringUtils.isNullOrEmptyWithTrim(request.getContent()) ? null : request.getContent() ;
        this.articleId = request.getArticleId();
        this.accountId = request.getAccountId();
        this.id = request.getId();
        this.articleTitle = MyStringUtils.isNullOrEmptyWithTrim(request.getArticleTitle()) ? null : request.getArticleTitle() ;
        this.username = MyStringUtils.isNullOrEmptyWithTrim(request.getUsername()) ? null : request.getUsername() ;
        this.status = request.getStatus();
        this.startDateCreateAt = !MyStringUtils.isNullOrEmptyWithTrim(request.getStartDateCreateAt()) ?
                DateUtils.stringToLocalDateTimeStartDate(request.getStartDateCreateAt()) : null;
        this.endDateCreateAt = !MyStringUtils.isNullOrEmptyWithTrim(request.getEndDateCreateAt()) ?
                DateUtils.stringToLocalDateTimeEndDate(request.getEndDateCreateAt()) : null;
    }
}
