package com.formssi.mall.jwt.service;

import com.formssi.mall.jwt.entity.JwtUser;

import java.util.Map;

public interface JwtTokenService {

    String generateToken(JwtUser jwtUser);

    JwtUser parseToken(String token);

    boolean validateExpired(JwtUser jwtUser);

    String encodeToken(String token, String refreshToken);

    String[] decodeToken(String encodeData);

    Long getUserId();

    String getUsername();

    JwtUser getJwtUser();

    boolean isLogin();
    
}
