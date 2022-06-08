package com.formssi.mall.pay.infrastructure.config;

import com.formssi.mall.pay.application.PayService;
import com.formssi.mall.pay.infrastructure.annotation.PayType;
import com.formssi.mall.pay.infrastructure.enumeration.PayTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付服务类型Map
 *
 * @author kk
 * @date 2022/05/09 16:23:39
 */
@Component
public class PayTypeServiceMap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Map<String, PayService> payServiceMap;

    public static final Map<PayTypeEnum, PayService> SERVICE_HAND_MAP = new HashMap<>(2);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (Map.Entry<String, PayService> entry : payServiceMap.entrySet()) {
            Class<? extends PayService> aClass = entry.getValue().getClass();
            if (aClass.isAnnotationPresent(PayType.class)) {
                PayType annotation = aClass.getAnnotation(PayType.class);
                SERVICE_HAND_MAP.put(annotation.value(), entry.getValue());
            }
        }
    }
}
