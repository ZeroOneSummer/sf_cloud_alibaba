package com.formssi.mall.gms.dto;

import lombok.Data;

import java.util.List;

/**
 * @author:prms
 * @create: 2022-04-19 16:33
 * @version: 1.0
 */
@Data
public class GmsCategoryDTO {


    private Long id;

    private String name;

    private Long parentId;

    private String image;

    private Integer level;

    private Integer priority;

    private Integer optionStatus;

    List<GmsCategoryDTO> zList;

}
