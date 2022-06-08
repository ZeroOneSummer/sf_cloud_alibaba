package com.formssi.mall.autoconfig.template;

import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.formssi.mall.autoconfig.properties.SmsProperties;

public class SmsTemplate {

    private SmsProperties properties;

    public SmsTemplate(SmsProperties properties) {
        this.properties = properties;
    }

    public void sendSms(String mobile,String code) throws Exception {
            //配置阿里云
            Config config = new Config()
                    // 您的AccessKey ID
                    .setAccessKeyId(properties.getAccessKey())
                    // 您的AccessKey Secret
                    .setAccessKeySecret(properties.getSecret());
            // 访问的域名
            config.endpoint = "dysmsapi.aliyuncs.com";

            com.aliyun.dysmsapi20170525.Client client =  new com.aliyun.dysmsapi20170525.Client(config);

            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(mobile)
                    .setSignName(properties.getSignName())
                    .setTemplateCode(properties.getTemplateCode())
                    .setTemplateParam("{\"code\":\""+code+"\"}");
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse response = client.sendSms(sendSmsRequest);

            SendSmsResponseBody body = response.getBody();

            System.out.println(body.getMessage());

            if (!"OK".equals(body.getCode())) {
                throw new Exception(body.getCode());
            }
    }

    /**
     * 发送批量短信
     * @param mobiles ["15000000000","15000000001"]
     * @param text
     */
    public void sendBatchSms(String mobiles,String text) throws Exception{
            //配置阿里云
            Config config = new Config()
                    // 您的AccessKey ID
                    .setAccessKeyId(properties.getAccessKey())
                    // 您的AccessKey Secret
                    .setAccessKeySecret(properties.getSecret());
            // 访问的域名
            config.endpoint = "dysmsapi.aliyuncs.com";

            com.aliyun.dysmsapi20170525.Client client =  new com.aliyun.dysmsapi20170525.Client(config);

            SendBatchSmsRequest sendBatchSmsRequest = new SendBatchSmsRequest()
                    .setPhoneNumberJson(mobiles)
                    .setSignNameJson(properties.getSignName())
                    .setTemplateCode(properties.getTemplateCodeBatch())
                    .setTemplateParamJson("{\"code\":\""+text+"\"}");
            // 复制代码运行请自行打印 API 的返回值
            SendBatchSmsResponse response = client.sendBatchSms(sendBatchSmsRequest);

            SendBatchSmsResponseBody body = response.getBody();

            System.out.println(body.getMessage());

            if (!"OK".equals(body.getCode())) {
                throw new Exception(body.getCode());
            }
    }

}