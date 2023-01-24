package com.fpt.edu.herofundbackend.security;

import com.fpt.edu.herofundbackend.constant.SecurityConstant;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

@Component @Log4j2 @RequiredArgsConstructor
public class Http403AccessDenied implements AccessDeniedHandler {

    private final Gson gson;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exc) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(SecurityConstant.APPLICATION_JSON);
            HashMap<String, String> map = new HashMap<>();
            map.put(SecurityConstant.ERROR, HttpStatus.FORBIDDEN.name());
            map.put(SecurityConstant.CODE, String.valueOf(HttpStatus.FORBIDDEN.value()));
            map.put(SecurityConstant.TIME, LocalDateTime.now().toString());
            map.put(SecurityConstant.PATH, request.getRequestURI());
            response.getWriter().write(gson.toJson(map));
        }

    }
}
