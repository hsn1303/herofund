package com.fpt.edu.herofundbackend.dto.auth;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class AuthenticateResponse {

    private String accessToken;
    private String refreshToken;
    private Date iat;
    private Date expired;


}
