package com.formssi.mall.ums.cmd;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GoodsCategoryCmd {

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
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
