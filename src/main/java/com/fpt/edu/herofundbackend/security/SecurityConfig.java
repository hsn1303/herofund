package com.fpt.edu.herofundbackend.security;

import com.fpt.edu.herofundbackend.enums.RoleEnum;
import com.fpt.edu.herofundbackend.service.AuthenticateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {



    private final Filter jwtAuthFilter;
    private final AuthenticateService authenticateService;
    private final PasswordEncoder passwordEncode;
    private final Http401Unauthorized http401Unauthorized;
    private final Http403AccessDenied http403AccessDenied;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors();
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(JwtUtils.WHITE_LIST)
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(JwtUtils.PATH_ADMIN).hasAnyAuthority(RoleEnum.ROLE_ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().authenticationEntryPoint(http401Unauthorized);
        http.exceptionHandling().accessDeniedHandler(http403AccessDenied);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncode);
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return authenticateService;
    }
}
