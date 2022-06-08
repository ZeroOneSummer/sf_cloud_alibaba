package com.formssi.mall.jwt.config;

import com.formssi.mall.jwt.service.JwtTokenService;
import com.formssi.mall.jwt.service.impl.JwtTokenServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtTokenService jwtTokenService() {
        return new JwtTokenServiceImpl();
    }

    @Bean
    public JwtSecretProperties jwtSecretProperties() {
        return new JwtSecretProperties();
    }

}
