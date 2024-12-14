package com.davejang.blockchain_ticketing.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KaiaConfig {

    @Value("${kaia.access.key}")
    private String accessKey;
    @Value("${kaia.access.secret.key}")
    private String secretKey;
}
