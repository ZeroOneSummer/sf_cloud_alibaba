package com.formssi.mall.common.entity;

import lombok.Data;

/**
 * @author chison
 * @date 2022/5/16 15:07
 * @desc
 */
@Data
public class Order {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String orderNo;
    /**
     *
     */
    private Integer userId;
    /**
     *
     */
    private Integer count;
    /**
     *
     */
    private Integer amount;
    /**
     *
     */
    private String remark;
}
