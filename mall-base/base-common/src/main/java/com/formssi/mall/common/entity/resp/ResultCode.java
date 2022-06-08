package com.formssi.mall.common.entity.resp;

public interface ResultCode {

    String getCode();

    String getMessage();

    default String getDesc() {
        return getMessage();
    }

}

