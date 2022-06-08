package com.formssi.mall.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class DynamicRouteConfig implements ApplicationEventPublisherAware {


    @Autowired
    private RouteDefinitionWriter routedefinitionWriter;

    private ApplicationEventPublisher publisher;

    @Value("${spring.cloud.nacos.config.data-id}")
    private String dataId;

    @Value("${spring.cloud.nacos.config.group}")
    private String group;

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;

    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;

    @Value("${spring.cloud.nacos.config.timeout}")
    private long timeout;

    private static final List<String> ROUTE_LIST = new ArrayList<>();

    @PostConstruct
    public void dynamicRouteByNacosListener() {

        try {

            Properties prop = new Properties();

            prop.put("serverAddr", serverAddr);

            prop.put("namespace", namespace);

            ConfigService config = NacosFactory.createConfigService(prop);

            String content = config.getConfig(dataId, group, timeout);

            publisher(content);

            config.addListener(dataId, group, new Listener() {

                @Override

                public void receiveConfigInfo(String config) {

                    publisher(config);

                }

                @Override

                public Executor getExecutor() {

                    return null;

                }

            });

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**

     * 增加路由

     *

     * @param def

     * @return

     */

    public Boolean addRoute(RouteDefinition def) {

        try {

            routedefinitionWriter.save(Mono.just(def)).subscribe();

            ROUTE_LIST.add(def.getId());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return true;

    }

    /**

     * 删除路由

     *

     * @return

     */

    public Boolean clearRoute() {

        for (String id : ROUTE_LIST) {

            routedefinitionWriter.delete(Mono.just(id)).subscribe();

        }

        ROUTE_LIST.clear();

        return Boolean.FALSE;

    }

    /**

     * 发布路由

     */

    private void publisher(String config) {

        clearRoute();

        try {

            log.info("Start updating dynamic routing ....");

            List<RouteDefinition> routeDefinitionList = JSONObject.parseArray(config, RouteDefinition.class);

            for (RouteDefinition route : routeDefinitionList) {

                log.info(route.toString());

                addRoute(route);

            }

            publisher.publishEvent(new RefreshRoutesEvent(this.routedefinitionWriter));

            log.info("update completed ");

        } catch (Exception e) {

            log.error("Failed to update routing information", e);

            e.printStackTrace();

        }

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

        publisher = applicationEventPublisher;

    }

}
