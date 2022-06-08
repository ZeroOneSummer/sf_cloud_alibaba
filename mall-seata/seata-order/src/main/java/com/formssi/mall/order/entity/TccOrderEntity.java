package com.formssi.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chison
 * @date 2022/5/24 9:45
 * @description
 */
@TableName("tcc_order")
@Data
public class TccOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
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
    private Integer amount;
    /**
     * 0：中间状态
     * 1：完成状态
     */
    private Integer orderStatus;
    /**
     *
     */
    private String remark;

    /**
     * 出错阶段
     * 0：正常运行
     * 1：模拟订单服务的try阶段出错
     * 2：模拟账户服务的try阶段出错
     * 3：模拟订单服务的commit阶段出错
     * 4：模拟账户服务的commit阶段出错
     * 5：模拟订单服务的cancel阶段出错
     * 6：模拟账户服务的cancel阶段出错
     */
    @TableField(exist = false)
    private Integer errorPhase;
}
