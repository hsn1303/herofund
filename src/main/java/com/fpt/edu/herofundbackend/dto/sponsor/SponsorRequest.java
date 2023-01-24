package com.fpt.edu.herofundbackend.dto.sponsor;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SponsorRequest {

    private long id;

    @NotBlank(message = SystemConstant.Message.NAME_NOT_EMPTY)
    private String name;

    @NotBlank(message = SystemConstant.Message.DESCRIPTION_NOT_EMPTY)
    private String description;

    @NotBlank(message = SystemConstant.Message.DETAIL_NOT_EMPTY)
    private String detail;

    @NotBlank(message = SystemConstant.Message.IMAGE_NOT_EMPTY)
    private String image;
}
