package com.formssi.mall.log.framework.interceptor;

import com.formssi.mall.log.constant.Constant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;


/**
 * @author echo
 * @date 2022年04月14日 9:07
 */


@Slf4j
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("进入feign拦截器...THREAD_ID:{}", MDC.get(Constant.TRACE_ID));
        requestTemplate.header(Constant.TRACE_ID, MDC.get(Constant.TRACE_ID));
    }
}
