package com.formssi.mall.common.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
    @Bean
    public GmsSpuFeignClientFallBack gmsSpuFeignClientFallBack(){
        return new GmsSpuFeignClientFallBack();
    }
}
