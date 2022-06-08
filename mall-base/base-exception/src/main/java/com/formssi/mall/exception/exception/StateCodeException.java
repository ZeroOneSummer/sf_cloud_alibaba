package com.formssi.mall.exception.exception;

import com.formssi.mall.common.entity.resp.RespCode;

/**
 * 自定义运行时 状态码异常 包含一个状态码stateCode 的异常
 */
public class StateCodeException extends RuntimeException {

    private static final RespCode DEFAULT = RespCode.INTERNAL_SERVER_ERROR;

    private final String stateCode;

    public String getStateCode() {
        return stateCode;
    }

    public StateCodeException() {
        this(DEFAULT.getCode(), DEFAULT.getDesc());
    }

    public StateCodeException(String stateCode) {
        this(stateCode, DEFAULT.getDesc());
    }

    public StateCodeException(String stateCode, String message) {
        this(stateCode, message, null);
    }

    public StateCodeException(String stateCode, String message, Throwable cause) {
        super(message, cause);
        this.stateCode = stateCode;
    }

    public StateCodeException(boolean writableStackTrace) {
        this(DEFAULT.getCode(), writableStackTrace);
    }

    public StateCodeException(String stateCode, boolean writableStackTrace) {
        this(stateCode, DEFAULT.getDesc(), null, true, writableStackTrace);
    }

    public StateCodeException(String stateCode, String message, boolean writableStackTrace) {
        this(stateCode, message, null, true, writableStackTrace);
    }

    protected StateCodeException(String stateCode, String message, Throwable cause, boolean writableStackTrace) {
        this(stateCode, message, cause, true, writableStackTrace);
    }

    protected StateCodeException(String stateCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.stateCode = stateCode;
    }

}
