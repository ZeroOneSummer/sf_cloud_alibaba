package com.formssi.mall.goods.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * sku库存表
 * </p>
 *
 * @author hudemin
 * @since 2022-04-15 20:01:14
 */
@Getter
@Setter
@TableName("gms_sku")
public class GmsSkuDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品ID
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * 产品编号
     */
    @TableField("sku_sn")
    private String skuSn;

    /**
     * 销售价
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 市场价
     */
    @TableField("market_price")
    private BigDecimal marketPrice;

    /**
     * 库存
     */
    @TableField("stock")
    private Integer stock;

    /**
     * 锁定库存
     */
    @TableField("lock_stock")
    private Integer lockStock;

    /**
     * 规格名Id和规格值Id组合
     */
    @TableField("spec_data")
    private String specData;

}



