package com.formssi.mall.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public final class RequestUtils {

    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    public static String getHeader(String key) {
        return getRequest().getHeader(key);
    }

    public static Map<String, String> getHeaders() {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headerNames = getRequest().getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = getRequest().getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    public static String getParameter(String key) {
        return getRequest().getParameter(key);
    }

}

