package com.formssi.mall.order.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 訂單項
 */
@Data
@TableName("oms_order_item")
public class OmsOrderItemDO implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 订单编号
     */
    @TableField("order_sn")
    private String orderSn;

    /**
     * 商品编号
     */
    @TableField("spu_sn")
    private String spuSn;

    /**
     * 商品名称
     */
    @TableField("spu_name")
    private String spuName;

    /**
     * 商品图片
     */
    @TableField("spu_image")
    private String spuImage;

    /**
     * 产品ID
     */
    @TableField("sku_id")
    private String skuId;

    /**
     * 价格
     */
    @TableField("price")
    private Long price;

    /**
     * 数量
     */
    @TableField("quantity")
    private Long quantity;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;

}