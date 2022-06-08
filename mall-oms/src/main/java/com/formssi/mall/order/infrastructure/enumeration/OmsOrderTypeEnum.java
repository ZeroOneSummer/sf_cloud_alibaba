package com.formssi.mall.order.infrastructure.enumeration;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum OmsOrderTypeEnum {

    JD_ORDER("JD","京东订单"),
    WYYX_ORDER("WYYX","网易严选");

    private String code;

    private String name;

    OmsOrderTypeEnum(String code,String name){
        this.code = code;
        this.name = name;
    }

    /**
     * 根据枚举code查询枚举
     * @param code
     * @return
     */
    public static OmsOrderTypeEnum queryOmsOrderTypeEnumByCode(String code){
        for (OmsOrderTypeEnum omsOrderTypeEnum:values()){
            if (StringUtils.isNoneBlank(code) && omsOrderTypeEnum.code.equals(code)){
                return omsOrderTypeEnum;
            }
        }
        return null;
    }
}
