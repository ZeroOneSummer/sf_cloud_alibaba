package com.formssi.mall.flash.cmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashItemCmd {

    private Long id;

    /**
     * 秒杀活动ID
     */
    private Long actId;

    /**
     * 商品编号
     */
    private String spuSn;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 秒杀图片
     */
    private String flashImage;

    /**
     * 秒杀商品介绍
     */
    private String flashInfo;

    /**
     * 销售价
     */
    private Double price;

    /**
     * 秒杀价
     */
    @NotNull(message = "秒杀价不能为空")
    private Double flashPrice;

    /**
     * 秒杀总库存
     */
    @NotNull(message = "秒杀总库存不能为空")
    private Integer flashStock;

    /**
     * 每人限制购买数量
     */
    private Integer limitQty;

    /**
     * 产品ID
     */
    private Long skuId;

    /**
     * 规格名Id和规格值Id组合
     */
    private String specData;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 操作状态(0、1、2)分别代表D、I、U
     */
    private Integer optionStatus;







}
