package com.fpt.edu.herofundbackend.dto;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusAndIdsRequest {

    @NotEmpty(message = SystemConstant.Message.IDS_VALIDATION)
    private List<@NotNull Long> ids;

    @Min(value = 0, message = SystemConstant.Message.STATUS_VALIDATION)
    @Max(value = 4, message = SystemConstant.Message.STATUS_VALIDATION)
    private int status;
}
