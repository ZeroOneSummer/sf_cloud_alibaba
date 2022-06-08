package com.formssi.mall.mq.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.formssi.mall.mq.pojo.MqLogFail;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 消息发送失败表 Mapper 接口
 * </p>
 *
 * @author kongquan
 * @since 2022-05-06
 */
//@Repository
public interface MqLogFailMapper extends BaseMapper<MqLogFail> {

}
