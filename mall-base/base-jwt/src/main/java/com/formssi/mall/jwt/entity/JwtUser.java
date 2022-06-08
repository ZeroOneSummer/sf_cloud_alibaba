package com.formssi.mall.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtUser {

    private Long userId;

    private String username; //用户名称

    private String salt; //盐

    private Long expired; //过期时间

    private List<String> authorities; //权限列表

}
