package com.fpt.edu.herofundbackend.security;

import com.fpt.edu.herofundbackend.constant.SecurityConstant;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;


@Component
@Log4j2
@RequiredArgsConstructor
public class Http401Unauthorized implements AuthenticationEntryPoint {

    private final Gson gson;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
            throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(SecurityConstant.APPLICATION_JSON);
        HashMap<String, String> map = new HashMap<>();
        map.put(SecurityConstant.ERROR, HttpStatus.UNAUTHORIZED.name());
        map.put(SecurityConstant.CODE, String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        map.put(SecurityConstant.TIME, LocalDateTime.now().toString());
        map.put(SecurityConstant.PATH, request.getRequestURI());
        response.getWriter().write(gson.toJson(map));

    }
}
