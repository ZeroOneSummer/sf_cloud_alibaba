package com.formssi.mall.log.framework.aspectj;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.formssi.mall.common.util.SpringUtils;
import com.formssi.mall.jwt.entity.JwtUser;
import com.formssi.mall.jwt.service.JwtTokenService;
import com.formssi.mall.log.framework.annotation.Log;
import com.formssi.mall.log.framework.enums.BusinessStatus;
import com.formssi.mall.log.pojo.OperLog;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author echo
 * @date 2022年04月11日 16:20
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    // 配置织入点
    @Pointcut("@annotation(com.formssi.mall.log.framework.annotation.Log)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        //doBeforeLog(joinPoint);
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        //开始时间
        long begin = System.currentTimeMillis();
        //方法环绕proceed结果
        Object obj = point.proceed();
        //结束时间
        long end = System.currentTimeMillis();
        //时间差
        long timeDiff = (end - begin);
        if (timeDiff < 200) {
            log.info("方法性能分析: 执行耗时 {}毫秒，" + "\uD83D\uDE02", timeDiff);
        } else {
            log.warn("方法性能分析: 执行耗时 {}毫秒，" + "\uD83D\uDE31", timeDiff);
        }
        return obj;
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {

        // 获得注解
        Log controllerLog = getAnnotationLog(joinPoint);
        if (controllerLog == null) {
            return;
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //请求得URI
        String requestURI = request.getRequestURI();
        // 请求的地址
        String ip = getClientIp(request);
        // 获取当前的用户
        JwtUser loginUser = getJwtUser();
        // 日志对象//
        OperLog operLog = new OperLog();
        operLog.setOperId(IdUtil.getSnowflakeNextId());
        operLog.setOperTime(LocalDateTime.now());
        operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
        operLog.setOperIp(ip);
        operLog.setOperUrl(requestURI);
        // 设置方法名称
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        operLog.setMethod(className + "." + methodName + "()");
        // 设置请求方式
        operLog.setRequestMethod(request.getMethod());
        // 处理设置注解上的参数
        getControllerMethodDescription(joinPoint, controllerLog, operLog);
        // 返回参数
        operLog.setJsonResult(JSON.toJSONString(jsonResult));
        if (loginUser != null) {
            operLog.setOperName(loginUser.getUsername());
        }
        if (e != null) {
            operLog.setStatus(BusinessStatus.FAIL.ordinal());
            operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
        }
        log.info("operLog={}", operLog);
    }

    /**
     * 获取当前登录用户
     *
     */
    private JwtUser getJwtUser() {
        JwtUser loginUser = null;
        try {
            loginUser = SpringUtils.getBean(JwtTokenService.class).getJwtUser();
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
        }
        return loginUser;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, OperLog operLog) {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     */
    private void setRequestValue(JoinPoint joinPoint, OperLog operLog) {
        Map<String, Object> paramsMap = getRequestParamsByJoinPoint(joinPoint);
        operLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        log.debug("x-forwarded-for = {}", ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            log.debug("Proxy-Client-IP = {}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.debug("WL-Proxy-Client-IP = {}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            log.debug("RemoteAddr-IP = {}", ip);
        }
        if (StringUtils.isNotBlank(ip)) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    private void doBeforeLog(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestURI = request.getRequestURI();
        Map<String, Object> params = new LinkedHashMap<>(10);
        params.put("uri", requestURI); // 获取请求的url
        params.put("method", request.getMethod()); // 获取请求的方式
        params.put("args", getRequestParamsByJoinPoint(joinPoint)); // 请求参数
        params.put("className", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()); // 获取类名和获取类方法
        params.put("ip", getClientIp(request)); // 获取请求的ip地址
        log.info("params:{}", JSONObject.toJSONString(params));
    }

    /**
     * 获取入参
     *
     */
    private Map<String, Object> getRequestParamsByJoinPoint(JoinPoint joinPoint) {
        //参数名
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValues = joinPoint.getArgs();

        return buildRequestParam(paramNames, paramValues);
    }

    private Map<String, Object> buildRequestParam(String[] paramNames, Object[] paramValues) {
        Map<String, Object> requestParams = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            requestParams.put(paramNames[i], paramValues[i]);
        }
        return requestParams;
    }
}
