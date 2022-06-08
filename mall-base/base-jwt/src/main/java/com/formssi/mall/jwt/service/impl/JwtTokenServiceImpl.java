package com.formssi.mall.jwt.service.impl;

import com.formssi.mall.common.util.DesUtils;
import com.formssi.mall.common.util.ExceptionUtils;
import com.formssi.mall.common.util.JsonUtils;
import com.formssi.mall.common.util.RequestUtils;
import com.formssi.mall.jwt.JwtConstant;
import com.formssi.mall.exception.define.ResultCodeEnum;
import com.formssi.mall.exception.exception.BusinessException;
import com.formssi.mall.jwt.config.JwtSecretProperties;
import com.formssi.mall.jwt.entity.JwtUser;
import com.formssi.mall.jwt.service.JwtTokenService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {

    @Autowired
    private JwtSecretProperties jwtSecretProperties;

    @Override
    public String generateToken(JwtUser jwtUser) {
        try {
            //创建JWS头，设置签名算法和类型
            JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).
                    type(JOSEObjectType.JWT)
                    .build();
            //将负载信息封装到Payload对象
            Payload payload = new Payload(JsonUtils.toJSON(jwtUser));
            //创建JWS对象
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);
            //创建HMAC签名器
            JWSSigner jwsSigner = new MACSigner(jwtSecretProperties.getSecret());
            //签名
            jwsObject.sign(jwsSigner);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.warn(ExceptionUtils.getStackTraceAsString(e));
            throw new BusinessException("Token生成异常");
        }
    }

    public JwtUser parseToken(String token) {
        JWSObject jwsObject;
        try {
            //从token中解析JWS对象
            jwsObject = JWSObject.parse(token);
            //创建HMAC验证器
            JWSVerifier jwsVerifier = new MACVerifier(jwtSecretProperties.getSecret());
            if (!jwsObject.verify(jwsVerifier)) {
                throw new BusinessException(ResultCodeEnum.TOKEN_INVALID);
            }
        } catch (ParseException | JOSEException | NullPointerException e) {
            log.warn(ExceptionUtils.getStackTraceAsString(e));
            throw new BusinessException(ResultCodeEnum.TOKEN_INVALID);
        }
        String payload = jwsObject.getPayload().toString();
        return JsonUtils.toObject(payload, JwtUser.class);
    }


    public boolean validateExpired(JwtUser jwtUser) {
        return jwtUser.getExpired() < System.currentTimeMillis();
    }

    @Override
    public String encodeToken(String token, String refreshToken) {
        String format = String.format("%s%s%s", token, JwtConstant.TOKEN_SEPARATOR, refreshToken);
        return DesUtils.encode(format, JwtConstant.DEFAULT_DES_SECRET);
    }

    @Override
    public String[] decodeToken(String encodeData) {
        String format = DesUtils.decode(encodeData, JwtConstant.DEFAULT_DES_SECRET);
        if (format == null) {
            throw new BusinessException(ResultCodeEnum.TOKEN_INVALID);
        }
        String[] tokens = format.split(JwtConstant.TOKEN_SEPARATOR);
        if (tokens.length != 2) {
            throw new BusinessException(ResultCodeEnum.TOKEN_INVALID);
        }
        return tokens;
    }

    @Override
    public Long getUserId() {
        return getJwtUser().getUserId();
    }

    public String getUsername() {
        return getJwtUser().getUsername();
    }

    public JwtUser getJwtUser() {
        String token = RequestUtils.getHeader(JwtConstant.AUTHORIZATION);
        return parseToken(token);
    }

    @Override
    public boolean isLogin() {
        return !ObjectUtils.isEmpty(getJwtUser());
    }

}
