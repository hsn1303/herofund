package com.fpt.edu.herofundbackend.dto.comment;

import lombok.Data;

@Data
public class FilterCommentRequest {
    private String content;
    private String username;
    private Long articleId;
    private String articleTitle;
    private Long accountId;
    private Long id;
    private String startDateCreateAt;
    private String endDateCreateAt;
    private Integer status;
    private int limit = 10;
    private int offset = 1;
}
