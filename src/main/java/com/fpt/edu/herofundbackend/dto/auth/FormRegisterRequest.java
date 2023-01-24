package com.fpt.edu.herofundbackend.dto.auth;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FormRegisterRequest {

    @NotBlank(message = SystemConstant.Message.USERNAME_NOT_EMPTY)
    private String username;

    @NotBlank(message = SystemConstant.Message.PASSWORD_NOT_EMPTY)
    private String password;

    @NotBlank(message = SystemConstant.Message.CONFIRM_PASSWORD_NOT_EMPTY)
    private String confirmPassword;
}
