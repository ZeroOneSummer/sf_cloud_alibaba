package com.formssi.mall.order.infrastructure.enumeration;

import lombok.Getter;

@Getter
public enum EsSortEnum {
    ASC("asc"),
    DESC("desc");

    EsSortEnum(String name){
        this.name = name;
    }

    private String name;
}
