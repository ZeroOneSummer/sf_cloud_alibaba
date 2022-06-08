package com.formssi.mall.scg.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author jiangyaoting
 * @date 2022/3/28 14:54
 * @description 白名单配置参数
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mall.ignore")
public class IgnorePathsProperties {

    private List<String> paths;

}
