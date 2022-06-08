package com.formssi.mall.gms.cmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GmsSkuCmd {


    private Long id;
    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private Long spuId;

    /**
     * 产品编号
     */
    @NotNull(message = "产品编号不能为空")
    private String skuSn;

    /**
     * 销售价
     */
    @NotNull(message = "销售价不能为空")
    private Double price;

    /**
     * 市场价
     */
    private Double marketPrice;

    /**
     * 库存
     */
    @NotNull(message = "库存不能为空")
    private Integer stock;

    /**
     * 锁定库存
     */
    @NotNull(message = "锁定库存不能为空")
    private Integer lockStock;

    /**
     * 规格名Id和规格值Id组合
     */
    @NotNull(message = "规格不能为空")
    private String specData;

}
