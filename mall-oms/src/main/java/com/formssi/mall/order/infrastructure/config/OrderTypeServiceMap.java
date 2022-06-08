package com.formssi.mall.order.infrastructure.config;

import com.formssi.mall.order.application.OrderService;
import com.formssi.mall.order.infrastructure.annotation.OmsOrderType;
import com.formssi.mall.order.infrastructure.enumeration.OmsOrderTypeEnum;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class OrderTypeServiceMap implements ApplicationListener<ContextRefreshedEvent> {



    @Autowired
    private Map<String, OrderService> orderServiceMap;

    public static final Map<OmsOrderTypeEnum,OrderService> SERVICE_HAND_MAP = new HashMap<>(2);


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (Map.Entry<String, OrderService> entry:orderServiceMap.entrySet()){
            Class<? extends OrderService> aClass = entry.getValue().getClass();
            if (aClass.isAnnotationPresent(OmsOrderType.class)){
                OmsOrderType annotation = aClass.getAnnotation(OmsOrderType.class);
                SERVICE_HAND_MAP.put(annotation.value(),entry.getValue());
                continue;
            }
            /*
             * 说明：如果service类方法添加事务，那么service会被spring事务代理，这里的aClass会是一个代理类，
             * 所以这里需要做一个兼容，如果是代理类则需要找到目标类获取目标类上的注解
             */
            Class<?> superclass = aClass.getSuperclass();
            if (superclass.isAnnotationPresent(OmsOrderType.class)){
                OmsOrderType annotation = superclass.getAnnotation(OmsOrderType.class);
                SERVICE_HAND_MAP.put(annotation.value(),entry.getValue());
            }

        }
    }

}
