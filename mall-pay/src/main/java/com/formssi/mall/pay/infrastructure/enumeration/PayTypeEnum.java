package com.formssi.mall.pay.infrastructure.enumeration;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 支付类型枚举
 *
 * @author kk
 * @date 2022/05/09 16:08:30
 */
@Getter
public enum PayTypeEnum {
    /**
     * 支付宝
     */
    ALIPAY("alipay", "支付宝支付"),
    /**
     * 微信
     */
    WECHAT("wechat", "微信支付");
    /**
     * 代码
     */
    private String code;

    /**
     * 名字
     */
    private String name;

    /**
     * 支付类型枚举
     *
     * @param code 代码
     * @param name 名字
     */
    PayTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据枚举code查询枚举
     *
     * @param code 代码
     * @return {@link PayTypeEnum}
     */
    public static PayTypeEnum queryPayTypeEnumByCode(String code) {
        for (PayTypeEnum payTypeEnum : values()) {
            if (StringUtils.isNoneBlank(code) && payTypeEnum.code.equals(code)) {
                return payTypeEnum;
            }
        }
        return null;
    }
}
