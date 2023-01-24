package com.fpt.edu.herofundbackend.dto.campaign;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CampaignRequest {

    private long id;

    @NotBlank(message = SystemConstant.Message.TITLE_NOT_EMPTY)
    private String title;

    @NotBlank(message = SystemConstant.Message.START_DATE_NOT_EMPTY)
    private String startDate;

    @NotBlank(message = SystemConstant.Message.END_DATE_NOT_EMPTY)
    private String endDate;

    @Min(value = SystemConstant.Number.MIN_TARGET_AMOUNT, message = SystemConstant.Message.MIN_TARGET_AMOUNT)
    @Max(value = SystemConstant.Number.MAX_TARGET_AMOUNT, message = SystemConstant.Message.MAX_TARGET_AMOUNT)
    private long targetAmount;

    @NotBlank(message = SystemConstant.Message.DETAIL_NOT_EMPTY)
    private String detail;

    @NotBlank(message = SystemConstant.Message.DESCRIPTION_NOT_EMPTY)
    private String description;

    @NotBlank(message = SystemConstant.Message.IMAGE_NOT_EMPTY)
    private String image;

    private long categoryId;
    private long sponsorId;
}
