package com.formssi.mall.sharding.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 读写分离 测试表
 */
@Data
@TableName("t_user")
public class UserSharding {

    @TableId(type = IdType.INPUT, value = "id")
    private Integer id;

    private String nickname;

    private String password;

    private Integer sex;

    private String birthday;
}
