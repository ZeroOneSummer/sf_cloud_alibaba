package com.formssi.mall.oms.dto;

import java.util.Date;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/13 14:43
 */
public class OmsOrderItemDTO {
    /**
     * ID
     */
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 商品编号
     */
    private String spuSn;

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 商品图片
     */
    private String spuImage;

    /**
     * 产品ID
     */
    private String skuId;

    /**
     * 价格
     */
    private Long price;

    /**
     * 数量
     */
    private Long quantity;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
