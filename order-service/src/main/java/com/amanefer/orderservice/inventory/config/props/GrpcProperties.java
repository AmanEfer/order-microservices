package com.amanefer.orderservice.inventory.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "grpc.inventory")
public record GrpcProperties(
        String host,
        Integer port
) {
}
