package com.formssi.mall.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mall.minio")
public class MinioProperties {

    private String console;

    private String endpoint;

    private String bucketName;

    private String accessKey;

    private String secretKey;

}