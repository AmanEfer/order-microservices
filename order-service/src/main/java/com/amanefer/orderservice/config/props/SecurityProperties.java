package com.amanefer.orderservice.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record SecurityProperties(
        Token access,
        Token refresh
) {
    public record Token(
            String secret,
            long expiration
    ) {
    }
}
