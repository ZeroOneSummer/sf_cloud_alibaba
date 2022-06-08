package com.formssi.mall.pay.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 微信订单工具类
 *
 * @author user
 */
@Slf4j
public class PayUtils {
    public static final String ORDER_PAY_PREFIX = "ray_pay_";
    public static final String ORDER_REFUND_PREFIX = "ray_refund_";
    public static final String FORMAT = "yyyyMMddHHmmssSSS";

    /**
     * 生成商户订单号
     *
     * @return String
     */
    public static String getOutTradeNo() {
        String order = ORDER_PAY_PREFIX + new SimpleDateFormat(FORMAT).format(new Date());
        log.debug("生成商户订单号: [{}]", order);
        return order;
    }

    /**
     * 生成退款订单号
     *
     * @return String
     */
    public static String getRefundOrder() {
        String order = ORDER_REFUND_PREFIX + new SimpleDateFormat(FORMAT).format(new Date());
        log.debug("生成退款订单号: [{}]", order);
        return order;
    }

    /**
     * 将request中的参数转换成Map
     *
     * @param request 请求
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        java.util.Map<String, String> retMap = new HashMap<>(4);
        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;
            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.substring(1));
                retMap.put(name, sb.substring(1));
            } else {
                retMap.put(name, "");
            }
        }
        return retMap;
    }
}


