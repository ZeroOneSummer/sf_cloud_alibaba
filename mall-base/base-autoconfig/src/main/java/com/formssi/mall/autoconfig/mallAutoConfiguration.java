package com.formssi.mall.autoconfig;

import com.formssi.mall.autoconfig.properties.SmsProperties;
import com.formssi.mall.autoconfig.template.SmsTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author jiangyaoting
 */
@EnableConfigurationProperties({
        SmsProperties.class
})
public class mallAutoConfiguration {

    @Bean
    public SmsTemplate smsTemplate(SmsProperties properties){
        return new SmsTemplate(properties);
    }
}
