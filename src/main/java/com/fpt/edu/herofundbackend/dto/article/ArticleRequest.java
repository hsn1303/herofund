package com.fpt.edu.herofundbackend.dto.article;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ArticleRequest {

    private long id;

    @NotBlank(message = SystemConstant.Message.TITLE_NOT_EMPTY)
    private String title;

    @NotBlank(message = SystemConstant.Message.DESCRIPTION_NOT_EMPTY)
    private String description;

    @NotBlank(message = SystemConstant.Message.DETAIL_NOT_EMPTY)
    private String detail;

    @NotBlank(message = SystemConstant.Message.IMAGE_NOT_EMPTY)
    private String image;
    private long campaignId;
}
