package com.formssi.mall.scg.filter;

import com.formssi.mall.exception.define.ResultCodeEnum;
import com.formssi.mall.exception.exception.BusinessException;
import com.formssi.mall.jwt.JwtConstant;
import com.formssi.mall.jwt.entity.JwtUser;
import com.formssi.mall.jwt.service.JwtTokenService;
import com.formssi.mall.redis.service.RedisService;
import com.formssi.mall.scg.config.IgnorePathsProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 白名单过滤和token自动刷新
 */
@Slf4j
@Component
public class IgnorePathsFilter implements GlobalFilter, Ordered {


    @Autowired
    private IgnorePathsProperties ignorePathsProperties;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private RedisService redisService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获得请求与响应
        ServerHttpRequest request = exchange.getRequest();
        //获取请求路径
        String uri = request.getURI().getPath();
        //获得所有白名单路径
        List<String> paths = ignorePathsProperties.getPaths();
        PathMatcher pathMatcher = new AntPathMatcher();
        //遍历所有白名单路径是否为可放行路径
        for (String path : paths) {
            log.info("PathMatcher path = {} uri = {}", path, uri);
            if (pathMatcher.match(path, uri)) {
                log.info("白名单路径，放行");
                return chain.filter(exchange);
            }
        }
        //获得请求头携带的token
        String encodeData = request.getHeaders().getFirst(JwtConstant.AUTHORIZATION);
        String[] tokens = jwtTokenService.decodeToken(encodeData);
        JwtUser jwtUser = jwtTokenService.parseToken(tokens[0]);
        //token是否过期
        if (jwtTokenService.validateExpired(jwtUser)) {
            jwtUser = jwtTokenService.parseToken(tokens[1]);
            //refreshToken是否过期
            if (jwtTokenService.validateExpired(jwtUser)) {
                throw new BusinessException(ResultCodeEnum.TOKEN_EXPIRED);
            } else {
                //redis里面的refreshToken是否过期
                Object refreshTokenValue = redisService.get(JwtConstant.REDIS_REFRESH_TOKEN_KEY);
                if (refreshTokenValue != null) {
                    //生成新token
                    String newToken = jwtTokenService.generateToken(jwtUser);
                    jwtUser.setExpired(System.currentTimeMillis() + JwtConstant.REFRESH_TOKEN_EXPIRE_TIME);
                    //生成新refreshToken
                    String newRefreshToken = jwtTokenService.generateToken(jwtUser);
                    //缓存新refreshToken
                    redisService.set(jwtUser.getUserId() + JwtConstant.REDIS_REFRESH_TOKEN_KEY, newRefreshToken, JwtConstant.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
                    String encodeCode = jwtTokenService.encodeToken(newToken, newRefreshToken);
                    return handlerFilter(exchange, chain, newToken, encodeCode);
                } else {
                    throw new BusinessException(ResultCodeEnum.TOKEN_EXPIRED);
                }
            }
        } else {
            return handlerFilter(exchange, chain, tokens[0], null);
        }
    }

    private Mono<Void> handlerFilter(ServerWebExchange exchange, GatewayFilterChain chain, String token, String encodeCode) {
        //替换请求token
        ServerHttpRequest newRequest = exchange.getRequest().mutate()
                .header(JwtConstant.AUTHORIZATION, token)
                .build();
        ServerHttpResponse newResponse = exchange.getResponse();

        //替换响应token
        if (StringUtils.isNotBlank(encodeCode)) {
            newResponse.getHeaders().set(JwtConstant.AUTHORIZATION, encodeCode);
        }
        return chain.filter(exchange.mutate().request(newRequest).response(newResponse).build());
    }

    @Override
    public int getOrder() {
        return -2147483640;
    }
}
