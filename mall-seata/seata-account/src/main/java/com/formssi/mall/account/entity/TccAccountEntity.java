package com.formssi.mall.account.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chison
 * @date 2022/5/24 9:59
 * @description
 */
@TableName("tcc_account")
@Data
public class TccAccountEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer userId;
    /**
     *
     */
    private Integer balance;
    /**
     * 冻结金额
     */
    private Integer frozen;
    /**
     *
     */
    private String remark;
}
