package com.spring.training.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "remote.services")
@Data
public class ClientConfig {
    String url;
}
