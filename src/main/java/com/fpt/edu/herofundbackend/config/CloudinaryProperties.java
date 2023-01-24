package com.fpt.edu.herofundbackend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "cloudinary")
@Getter
@Setter
@Component
public class CloudinaryProperties {

    private String url;
    private String folder;
}
