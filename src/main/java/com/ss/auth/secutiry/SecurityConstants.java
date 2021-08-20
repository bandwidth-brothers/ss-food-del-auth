package com.ss.auth.secutiry;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
@PropertySource("classpath:securityconstants.properties")
public class SecurityConstants {

    private String SECRET;
    private String EXPIRATION_TIME; // 10 days
    private String TOKEN_PREFIX;
    private String HEADER_STRING;

    public Long getExpirationDuration() {
        return Long.valueOf(this.EXPIRATION_TIME.replaceAll("_", ""));
    }

}
