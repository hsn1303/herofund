package com.fpt.edu.herofundbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpt.edu.herofundbackend.constant.SystemConstant;
import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CommonResponse {

    private boolean status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public CommonResponse(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public CommonResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public void setValidateErrorsResponse(Map<String, String> errors){
        this.status = false;
        this.message = SystemConstant.Message.FAIL;
        this.errors = errors;
    }

    public void setFailResponse(String message){
        this.status = false;
        this.message = message;
    }

    public void setSuccessResponse(String message){
        this.status = true;
        this.message = message;
    }

    public void setSuccessWithDataResponse(Object data){
        this.status = true;
        this.message = SystemConstant.Message.SUCCESS;
        this.data = data;
    }

    public void setSuccessWithDataResponse(Object data, String message){
        this.status = true;
        this.message = message;
        this.data = data;
    }
}
