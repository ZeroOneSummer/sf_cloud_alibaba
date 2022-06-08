package com.formssi.mall.gms.query;

import com.formssi.mall.common.entity.resp.PageQry;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 按类别查询商品列表
 * @author:prms
 * @create: 2022-04-20 13:45
 * @version: 1.0
 */
@Data
public class GmsSpuPageByCateQry extends PageQry {

    /**
     * 商品分类 id
     */
    @NotNull(message = "分类id不能为空")
    private Long cateId;

}
