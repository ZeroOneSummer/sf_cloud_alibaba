package com.formssi.mall.pay.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 支付宝属性
 *
 * @author kk
 * @date 2022/05/10 08:56:39
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AlipayProperties {
    /**
     * 应用程序id
     */
    private String appId;
    /**
     * 应用私钥
     */
    private String applicationAlipayPrivateKey;
    /**
     * 应用公钥
     */
    private String applicationAlipayPublicKey;
    /**
     * 支付宝公钥
     */
    private String alipayPublicKey;
    /**
     * 网关地址
     */
    private String gatewayUrl;
    /**
     * 同步回调
     */
    private String returnUrl;
    /**
     * 异步回调
     */
    private String notifyUrl;
    /**
     * 格式
     */
    private String format;
    /**
     * 字符集
     */
    private String charset;
    /**
     * 签名方式
     */
    private String signType;
    /**
     * 日志路径
     */
    private String logPath;
}