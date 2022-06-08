package com.formssi.mall.common.util;

import com.formssi.mall.common.constant.oms.NumberConstants;
import lombok.Getter;

/**
 * 单号生成类型枚举
 *
 * @author ld
 * 注：随机号位于流水号之后,流水号使用redis计数据，每天都是一个新的key,长度不足时则自动补0
 * <p>
 * 生成规则 =固定前缀+当天日期串+流水号(redis自增，不足长度则补0)+随机数
 */
@Getter
public enum NumberTypeEnum {

    /**
     * 商品编号：
     * 固定前缀：SPU
     * 时间格式：yyMMddHH
     * 流水号长度：6(当单日单据较多时可根据业务适当增加流水号长度)
     * 随机数长度：5
     * 总长度：22
     */
    SPU_NUM("SPU", NumberConstants.SERIAL_YYMMDD_PREFIX, 6, 5, 22),

    /**
     * 订单编号：
     * 固定前缀：
     * 时间格式：yyMMddHH
     * 流水号长度：6
     * 随机数长度：6
     * 总长度：20
     */
    ORDER_NUM("", NumberConstants.SERIAL_YYMMDD_PREFIX, 7, 6, 20),

    /**
     * 库存单号：
     * 固定前缀："INV"
     * 时间格式：yyMMddHH
     * 流水号长度：6
     * 随机数长度：5
     * 总长度：22
     */
    INV_NUM("INV", NumberConstants.SERIAL_YYMMDD_PREFIX, 6, 5, 22),
    ;

    /**
     * 单号前缀
     * 为空时填""
     */
    private String prefix;

    /**
     * 时间格式表达式
     * 例如：yyMMddHH
     */
    private String datePattern;

    /**
     * 流水号长度
     */
    private Integer serialLength;
    /**
     * 随机数长度
     */
    private Integer randomLength;

    /**
     * 总长度
     */
    private Integer totalLength;


    NumberTypeEnum(String prefix, String datePattern, Integer serialLength, Integer randomLength, Integer totalLength) {
        this.prefix = prefix;
        this.datePattern = datePattern;
        this.serialLength = serialLength;
        this.randomLength = randomLength;
        this.totalLength = totalLength;
    }
}
