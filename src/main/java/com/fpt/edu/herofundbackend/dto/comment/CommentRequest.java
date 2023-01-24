package com.fpt.edu.herofundbackend.dto.comment;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    private Long id;
    @NotBlank(message = SystemConstant.Message.CONTENT_NOT_EMPTY)
    private String content;
    private long articleId;
}
