package com.formssi.mall.pay.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import javax.annotation.Resource;

/**
 * http公用方法
 *
 * @author user
 */
@Slf4j
public class HttpUtils {
    @Resource
    private static CloseableHttpClient wechatPayClient;

    public static String doPost(String body, String url) {
        log.info("请求参数:{}", body);
        String responseStr = "";
        StringEntity entity = new StringEntity(body, "utf-8");
        entity.setContentType("application/json");
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setEntity(entity);
        try (CloseableHttpResponse response = wechatPayClient.execute(httpPost)) {
            //响应码
            int statusCode = response.getStatusLine().getStatusCode();
            //响应体
            responseStr = EntityUtils.toString(response.getEntity());
            if (statusCode == 200) {
                //处理成功
                log.info("成功啦,响应码:{},返回结果:{}", statusCode, responseStr);
            } else if (statusCode == 204) {
                //处理成功，无返回Body
                log.info("成功啦,响应码:{},无响应体", statusCode);
            } else {
                log.info("失败,响应码:{},返回结果:{}", statusCode, responseStr);
                log.info("微信支付错误信息" + responseStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseStr;
    }

    public static String doGet(String url) {
        String responseStr = "";
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        try (CloseableHttpResponse response = wechatPayClient.execute(httpGet)) {
            //响应码
            int statusCode = response.getStatusLine().getStatusCode();
            //响应体
            responseStr = EntityUtils.toString(response.getEntity());
            log.info("查询响应码:{},响应体:{}", statusCode, responseStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseStr;
    }
}
