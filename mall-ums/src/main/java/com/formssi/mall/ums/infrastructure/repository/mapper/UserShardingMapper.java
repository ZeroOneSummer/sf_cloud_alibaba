package com.formssi.mall.ums.infrastructure.repository.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.formssi.mall.ums.interfaces.UserSharding;
import org.springframework.stereotype.Repository;

//在对应的Mapper 接口上 基础基本的 BaseMapper<T> T是对应的pojo类
// 测试类
//告诉容器你是持久层的 @Repository是spring提供的注释，能够将该类注册成Bean
@Repository
public interface UserShardingMapper extends BaseMapper<UserSharding> {
    //所有的crud都编写完成了

}

