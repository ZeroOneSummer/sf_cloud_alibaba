package com.formssi.mall.goods.infrastructure.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:prms
 * @create: 2022-04-28 16:21
 * @version: 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "es")
@Data
public class EsConfig {

    private String schema;

    private String address;

    private int connectTimeout;

    private int socketTimeout;

    private int connectionRequestTimeout;

    private int maxConnectNum;

    private int maxConnectPerRoute;


    @Bean
    public RestHighLevelClient restHighLevelClient() {
        String[] hostList = address.split(",");
        List<HttpHost> collect = Arrays.stream(hostList).map(item -> {
            String[] split = item.split(":");
            return new HttpHost(split[0], Integer.valueOf(split[1]), schema);
        }).collect(Collectors.toList());
        RestClientBuilder builder = RestClient.builder(collect.toArray(new HttpHost[]{}));

        //异步链接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(connectTimeout);
            requestConfigBuilder.setSocketTimeout(socketTimeout);
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeout);
            return requestConfigBuilder;
        });

        //异步链接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(maxConnectNum);
            httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
            return httpClientBuilder;
        });
        return new RestHighLevelClient(builder);
    }


}
