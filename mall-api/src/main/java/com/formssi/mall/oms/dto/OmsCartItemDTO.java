package com.formssi.mall.oms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 购物车条目
 * </p>
 *
 * @author 王亮
 * @since 2022-04-12 20:01:14
 */
public class OmsCartItemDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 购物车id
     */
    private Long cardId;

    /**
     * 商品id
     */
    @NotNull
    private Long spuId;

    /**
     * 产品id
     */
    @NotNull
    private Long skuId;

    /**
     * 数量
     */
    @NotNull
    private int quantity;





}
