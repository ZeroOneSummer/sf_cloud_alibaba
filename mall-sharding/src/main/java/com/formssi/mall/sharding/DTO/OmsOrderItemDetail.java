package com.formssi.mall.sharding.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class OmsOrderItemDetail {

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

    private Long userId;

    /**
     * 总金额
     */
    private Long totalAmount;

    /**
     * 订单状态，1：待付款，2：待发货，3：待收货，4：已完成
     */
    private Integer status;
}
