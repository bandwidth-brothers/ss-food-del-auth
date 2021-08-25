package com.ss.scrumptious_auth.secutiry;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Date;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
@PropertySource("classpath:securityconstants.properties")
public class SecurityConstants {

    private String SECRET;
    private String EXPIRATION_DURATION; // 10 days
    private String TOKEN_PREFIX;
    private String HEADER_STRING;
    private String endpoint;
    private String jwtSecret;
    private String jwtHeaderName;
    private String jwtHeaderPrefix;
    private String jwtIssuer;
    private long jwtExpirationDuration;
    private String authorityClaimKey;
    private String userIdClaimKey;

    public Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + Long.valueOf(this.EXPIRATION_DURATION.replaceAll("_", "")));
    }

    public Date getExpiresAt() {
        return new Date(System.currentTimeMillis() + jwtExpirationDuration);
    }

}
