package com.formssi.mall.gms.query;

import lombok.Data;

/**
 * @author:prms
 * @create: 2022-04-19 16:10
 * @version: 1.0
 */
@Data
public class GmsCategoryPageQry {

    /**
     * 分类id
     */
    private Long id;

    /**
     * 分类父级id
     */
    private Long parentId;

    /**
     * 分类层级
     */
    private Integer level;



}
