package com.formssi.mall.order.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 购物车条目
 * </p>
 *
 * @author 王亮
 * @since 2022-04-12 20:01:14
 */
@Getter
@Setter
@TableName("oms_cart_item")
public class OmsCartItemDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 购物车id
     */
    @TableField("card_id")
    private Long cardId;

    /**
     * 商品id
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * 产品id
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 数量
     */
    @TableField("quantity")
    private int quantity;





}
