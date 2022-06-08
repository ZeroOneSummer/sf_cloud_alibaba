package com.formssi.mall.log.framework.interceptor;

import cn.hutool.core.util.IdUtil;
import com.formssi.mall.log.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author echo
 * @date 2022年04月13日 11:24
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader(Constant.TRACE_ID);
        log.info("进入LogInterceptor拦截器...TRACE_ID:{}", traceId);
        //判断MDC中是否有该traceId
        if (StringUtils.isEmpty(traceId)) {
            //如果没有，添加
            MDC.put(Constant.TRACE_ID, IdUtil.simpleUUID().toUpperCase());
        }else{
            //如果上层服务有则直接使用
            MDC.put(Constant.TRACE_ID, traceId);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        MDC.clear();
    }
}
