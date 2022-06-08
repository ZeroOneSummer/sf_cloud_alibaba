package com.formssi.mall.common.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResp<T> {

    private String code;

    private String message;

    private T body;

    public static CommonResp<Void> ok() {
        return ok(null);
    }

    public static <T> CommonResp<T> ok(T body) {
        return build(RespCode.OK, body);
    }

    public static CommonResp<ErrorBody> error(String code, String message, ErrorBody errorBody) {
        return build(code, message, errorBody);
    }

    public static CommonResp<ErrorBody> error(ResultCode resultCode, ErrorBody errorBody) {
        return build(resultCode.getCode(), resultCode.getMessage(), errorBody);
    }

    public static CommonResp<ErrorBody> error(ResultCode resultCode, String message, ErrorBody errorBody) {
        return build(resultCode.getCode(), message, errorBody);
    }

    public static <T> CommonResp<T> fail(String message) {
        return fail(RespCode.FAIL, message, null);
    }

    public static <T> CommonResp<T> fail(ResultCode resultCode) {
        return fail(resultCode, resultCode.getMessage(), null);
    }

    public static <T> CommonResp<T> fail(ResultCode resultCode, String message, T data) {
        return build(resultCode.getCode(), message, data);
    }


    public static <T> CommonResp<T> build(ResultCode resultCode, T body) {
        return build(resultCode.getCode(), resultCode.getMessage(), body);
    }

    public static <T> CommonResp<T> build(String stateCode, String message, T body) {
        return new CommonResp<>(stateCode, message, body);
    }

    public static <T> CommonResp<T> validate(String message) {
        return new CommonResp<T>(RespCode.BAD_REQUEST.getCode(), message, null);
    }


    public static <T> CommonResp<T> unauthorized(T data) {
        return new CommonResp<T>(RespCode.UNAUTHORIZED.getCode(), RespCode.UNAUTHORIZED.getMessage(), data);
    }


    public static <T> CommonResp<T> forbidden(T data) {
        return new CommonResp<T>(RespCode.FORBIDDEN.getCode(), RespCode.FORBIDDEN.getMessage(), data);
    }

    public boolean isOk() {
        return code.equals(RespCode.OK.getCode());
    }

}
