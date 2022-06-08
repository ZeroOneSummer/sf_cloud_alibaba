package com.formssi.mall.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.formssi.mall.order.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author chison
 * @email chison@gmail.com
 * @date 2022-05-16 11:42:00
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
