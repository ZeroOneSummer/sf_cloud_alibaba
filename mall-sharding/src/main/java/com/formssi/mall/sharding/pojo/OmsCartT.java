package com.formssi.mall.sharding.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
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
@TableName("oms_cart_t")
@Builder
public class OmsCartT implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @TableField("user_id")
    private Long userId;

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
    /**
     * 创建年月
     */
    @TableField("create_time")
    private String createTime;





}
