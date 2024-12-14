package com.davejang.blockchain_ticketing.common.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.groundx.caver_ext_kas.CaverExtKAS;

@Component
@Getter
@Slf4j
public class KaiaConfig {
    @Value("${kaia.access.key}")
    private String accessKey;
    @Value("${kaia.access.secret.key}")
    private String secretKey;

    public CaverExtKAS initKas() {
        CaverExtKAS caver = new CaverExtKAS();
        caver.initKASAPI(1001, accessKey, secretKey);

        return caver;
    }
}
