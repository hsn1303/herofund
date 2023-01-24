package com.fpt.edu.herofundbackend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "paypal")
@Getter
@Setter
@Component
public class PaypalProperties {

    private String baseUrl;
    private String clientId;
    private String secretId;
    private String payerId;
}
