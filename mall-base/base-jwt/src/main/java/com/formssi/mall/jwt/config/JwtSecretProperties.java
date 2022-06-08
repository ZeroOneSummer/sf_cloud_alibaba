package com.formssi.mall.jwt.config;

import com.formssi.mall.jwt.JwtConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mall.jwt")
public class JwtSecretProperties {

    private String secret = JwtConstant.DEFAULT_JWT_SECRET;

}