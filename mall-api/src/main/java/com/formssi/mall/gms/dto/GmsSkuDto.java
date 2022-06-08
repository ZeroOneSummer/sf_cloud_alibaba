package com.formssi.mall.gms.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author:prms
 * @create: 2022-04-20 15:16
 * @version: 1.0
 */
@Data
public class GmsSkuDto {

    private Long id;

    private Long spuId;

    private String skuSn;

    private BigDecimal price;

    private BigDecimal marketPrice;

    private Integer stock;

    private Integer lockStock;

    private String specData;

}
