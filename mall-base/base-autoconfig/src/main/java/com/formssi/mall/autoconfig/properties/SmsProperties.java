package com.formssi.mall.autoconfig.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mall.sms")
public class SmsProperties {
    private String signName;
    private String templateCode;
    private String templateCodeBatch;
    private String accessKey;
    private String secret;
}