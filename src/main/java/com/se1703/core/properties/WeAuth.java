package com.se1703.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author leekejin
 * @date 2020-9-3 15:10:08
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WeAuth {
    private String appId;

    private String secret;

    private String grantType;

    private String sessionHost;
}
