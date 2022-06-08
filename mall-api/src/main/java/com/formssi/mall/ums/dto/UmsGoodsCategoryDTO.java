package com.formssi.mall.ums.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 作者
 * @since 2022-04-13
 */
@Data

public class UmsGoodsCategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类别id
     */
    private String kid;

    /**
     * 类别名称
     */
    private String kname;

    /**
     * 上级id
     */
    private String pid;

    /**
     * 上级名称
     */
    private String pname;

    /**
     * 图片地址
     */
    private String pic;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 添加时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


    List<UmsGoodsCategoryDTO> sonList;

}
