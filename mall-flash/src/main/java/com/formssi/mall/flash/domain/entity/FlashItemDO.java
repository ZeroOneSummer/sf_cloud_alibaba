package com.formssi.mall.flash.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("flash_item")
public class FlashItemDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 秒杀活动ID
     */
    @TableField("act_id")
    private Long actId;

    /**
     * 商品编号
     */
    @TableField("spu_sn")
    private String spuSn;

    /**
     * 商品名称
     */
    @TableField("name")
    private String name;

    /**
     * 秒杀图片
     */
    @TableField("flash_image")
    private String flashImage;

    /**
     * 秒杀商品介绍
     */
    @TableField("flash_info")
    private String flashInfo;

    /**
     * 销售价
     */
    @TableField("price")
    private Double price;

    /**
     * 秒杀价
     */
    @TableField("flash_price")
    private Double flashPrice;

    /**
     * 秒杀总库存
     */
    @TableField("flash_stock")
    private Integer flashStock;

    /**
     * 每人限制购买数量
     */
    @TableField("limit_qty")
    private Integer limitQty;

    /**
     * 产品ID
     */
    @TableField("sku_id")
    private Long skuId;



    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 操作状态(0、1、2)分别代表D、I、U
     */
    @TableField("option_status")
    private Integer optionStatus;








}



