package com.formssi.mall.exception.define;


import com.formssi.mall.common.entity.resp.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常码定义
 */
@AllArgsConstructor
@Getter
public enum ResultCodeEnum implements ResultCode {

    // 公用基础相关的
    TOKEN_INVALID("1001", "Token invalid", "Token无效，请检查token正确性"),
    TOKEN_EXPIRED("1002", "Token expired", "Token超时，请刷新token"),
    TOKEN_FAILURE("1003", "Token invalid", "Token失效，请重新登录"),
    LOGIN_CHANNEL_ERROR("1004", "Login channel error", "登录渠道错误"),
    USERNAME_PASSWORD_ERROR("1005", "Username or password error", "用户名或密码错误"),
    ACCOUNT_DISABLED("1005", "Account disabled", "账户已被禁用，请联系管理员"),
    ACCOUNT_LOCKED("1007", "Account locked", "账号已被锁定，请联系管理员"),
    PERMISSION_DENIED("1008", "Permission denied", "访问权限不足，请联系管理员"),
    SPU_ERROR("1009", "Permission denied", "访问权限不足，请联系管理员"),
    ;

    private final String code;
    private final String desc;
    private final String message;

}
