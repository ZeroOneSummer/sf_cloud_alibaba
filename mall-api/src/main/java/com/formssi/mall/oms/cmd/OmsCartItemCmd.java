package com.formssi.mall.oms.cmd;


import lombok.*;

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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OmsCartItemCmd {


    private Long id;

    /**
     * 购物车id
     */
    private Long cardId;



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
