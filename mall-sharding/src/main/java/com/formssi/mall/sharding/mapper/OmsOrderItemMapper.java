package com.formssi.mall.sharding.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.formssi.mall.sharding.DTO.OmsOrderItemDetail;
import com.formssi.mall.sharding.pojo.OmsOrderItemDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

//在对应的Mapper 接口上 基础基本的 BaseMapper<T> T是对应的pojo类
@Repository   //告诉容器你是持久层的 @Repository是spring提供的注释，能够将该类注册成Bean
public interface OmsOrderItemMapper extends BaseMapper<OmsOrderItemDO> {
    //所有的crud都编写完成了

    List<OmsOrderItemDetail>  findOrderAndDetailAll();

    List<OmsOrderItemDetail>  findOrderAndDetailByUserId(@Param("userId") long userId);

    List<OmsOrderItemDetail>  findOrderAndDetailByOrderId(@Param("orderId") long orderId);


    List<OmsOrderItemDetail>  findOrderAndDetailByuserIDAndOrderId(@Param("userId") long userId,@Param("orderId") long orderId);

    // 分页查询
    List<OmsOrderItemDetail>  findOrderAndDetailPage(@Param("startSize") int startSize,@Param("endSize") int endSize);
    List<OmsOrderItemDetail>  findOrderAndDetailByUserIdPage(@Param("userId") long userId,@Param("startSize") int startSize,@Param("endSize") int endSize);



}

