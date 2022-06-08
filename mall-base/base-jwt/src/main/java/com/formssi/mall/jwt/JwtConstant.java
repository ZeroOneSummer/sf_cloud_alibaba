package com.formssi.mall.jwt;

public class JwtConstant {

    public static final long TOKEN_EXPIRE_TIME = 30 * 60 * 1000;

    public static final long REFRESH_TOKEN_EXPIRE_TIME = 2 * TOKEN_EXPIRE_TIME;

    public static final String AUTHORIZATION = "Authorization";

    public static final String DEFAULT_JWT_SECRET = "jwt_secret___Aa_12345666___~!@#$%^&*()_+";

    public static final String DEFAULT_DES_SECRET = "des_secret_~!@#$%^";

    public static final String TOKEN_SEPARATOR = "_LINE_";

    public static final String REDIS_REFRESH_TOKEN_KEY = "_refreshToken";

}
