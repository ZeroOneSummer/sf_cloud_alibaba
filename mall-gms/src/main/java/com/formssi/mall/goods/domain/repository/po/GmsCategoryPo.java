package com.formssi.mall.goods.domain.repository.po;

import lombok.Data;

import java.util.List;

/**
 * @author:prms
 * @create: 2022-04-24 10:35
 * @version: 1.0
 */
@Data
public class GmsCategoryPo {

    private Long id;

    private String name;

    private Long parentId;

    private String image;

    private Integer level;

    private Integer priority;

    private Integer optionStatus;

    List<GmsCategoryPo> zList;
}
