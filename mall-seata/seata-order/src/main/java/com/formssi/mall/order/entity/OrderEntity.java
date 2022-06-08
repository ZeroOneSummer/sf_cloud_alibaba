package com.formssi.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author chison
 * @email chison@gmail.com
 * @date 2022-05-16 11:42:00
 */
@Data
@TableName("t_order")
public class OrderEntity implements Serializable {
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
	private Integer count;
	/**
	 * 
	 */
	private Integer amount;
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
