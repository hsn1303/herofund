package com.fpt.edu.herofundbackend.security;

import com.fpt.edu.herofundbackend.service.AuthenticateService;
import com.fpt.edu.herofundbackend.util.MyStringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {


    private final AuthenticateService authenticateService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        boolean check = Arrays.stream(JwtUtils.WHITE_LIST).map(s -> s.substring(0, s.length() - 3)).anyMatch(path::contains);
        if (check) {
            filterChain.doFilter(request, response);
            return;
        }
        final String username;
        final String jwtToken = jwtUtils.getTokenHeader(request);
        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }
        username = jwtUtils.extractUsername(jwtToken);
        if (!MyStringUtils.isNullOrEmptyWithTrim(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = authenticateService.loadUserByUsername(username);
            if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
