package com.formssi.mall.sharding.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 分库分表测试表
 */
@Data
@TableName("t_user_t")
public class UserTSharding {

    private Long id;

    private String nickname;

    private String password;

    private Integer sex;

    private Integer age;

    private String birthday;
}
