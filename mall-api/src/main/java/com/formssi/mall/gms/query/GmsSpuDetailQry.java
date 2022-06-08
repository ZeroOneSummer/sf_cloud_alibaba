package com.formssi.mall.gms.query;

import com.formssi.mall.common.entity.resp.PageQry;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * 根据商品id和编号查询商品详情
 * @author:prms
 * @create: 2022-04-20 14:59
 * @version: 1.0
 */
@Data
public class GmsSpuDetailQry extends PageQry {

    /**
     * 商品id
     */
    @NotNull(message = "商品序列号不能为空")
    private Long spuId;

    /**
     * 商品编号
     */
    @NotNull(message = "商品编号不能为空")
    private String spuSn;

}
