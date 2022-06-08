package com.formssi.mall.sharding.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.sharding.mapper.UserShardingMapper;
import com.formssi.mall.sharding.mapper.UserTShardingMapper;
import com.formssi.mall.sharding.pojo.UserSharding;
import com.formssi.mall.sharding.pojo.UserTSharding;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.route.router.masterslave.MasterVisitedManager;
//import org.apache.shardingsphere.masterslave.route.engine.impl.MasterVisitedManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("sharding")
@Api(tags = "sharding读写分离、分库分表")
public class ShardingController {

    @Autowired
    private UserShardingMapper userShardingMapper;

    @Autowired
    private com.formssi.mall.sharding.mapper.UserTShardingMapper UserTShardingMapper;

    @PostMapping("/save")
    @ApiOperation(value = "读写分离插入")
    public String addUser() {
        // 写分离
        UserSharding user = new UserSharding();
        user.setNickname("zhangsan" + new Random().nextInt());
        user.setPassword("123456");
        user.setSex(1);
        user.setBirthday("1997-12-03");
        userShardingMapper.insert(user);
        return "success";
    }

    @GetMapping("/findUsers")
    @ApiOperation(value = "读写分离查询")
    public List<UserSharding> findUsers() {
        // 强制走主库去查 对实时性要求很高的可以这样设置查读库
        MasterVisitedManager.setMasterVisited();
        // 读分离
        return userShardingMapper.selectList(null);
    }


    /**
     * sex:奇数
     * age:奇数
     * ds1.t_user_t1
     */
    @PostMapping("/test01")
    @ApiOperation(value = "分库分表ds1.t_user_t1")
    public void test01() {
        UserTSharding user = new UserTSharding();
        user.setNickname("zhangsan" + new Random().nextInt());
        user.setPassword("123456");
        user.setAge(17);
        user.setSex(1);
        user.setBirthday("1997-12-03");
        UserTShardingMapper.insert(user);
    }

    /**
     * sex:奇数
     * age:偶数
     * ds1.t_user_t0
     */
    @PostMapping("/test02")
    @ApiOperation(value = "分库分表ds1.t_user_t0")
    public void test02() {
        UserTSharding user = new UserTSharding();
        user.setNickname("zhangsan" + new Random().nextInt());
        user.setPassword("123456");
        user.setAge(18);
        user.setSex(1);
        user.setBirthday("1997-12-03");
        UserTShardingMapper.insert(user);
    }

    /**
     * sex:偶数
     * age:奇数
     * ds0.t_user_t1
     */
    @PostMapping("/test03")
    @ApiOperation(value = "分库分表ds0.t_user_t1")
    public void test03() {
        UserTSharding user = new UserTSharding();
        user.setNickname("zhangsan" + new Random().nextInt());
        user.setPassword("123456");
        user.setAge(17);
        user.setSex(2);
        user.setBirthday("1997-12-03");
        UserTShardingMapper.insert(user);
    }

    /**
     * sex:偶数
     * age:偶数
     * ds0.t_user_t0
     */
    @PostMapping("/test04")
    @ApiOperation(value = "分库分表ds0.t_user_t0")
    public void test04() {
        UserTSharding user = new UserTSharding();
        user.setNickname("zhangsan" + new Random().nextInt());
        user.setPassword("123456");
        user.setAge(18);
        user.setSex(2);
        user.setBirthday("1997-12-03");
        UserTShardingMapper.insert(user);
    }


    /**
     * 测试分库分表的查询

     */
    @PostMapping("/dbtable/test01")
    @ApiOperation(value = "测试分库分表")
    public void dbtableTest01() {
        // 分库、分表的 分片键都有
        QueryWrapper<UserTSharding> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("age",18).eq("sex", 2);
        List<UserTSharding> result1 = UserTShardingMapper.selectList(queryWrapper1);

        // 只有分库的分片键 性别
        QueryWrapper<UserTSharding> queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("sex", 2);
        List<UserTSharding> result2 = UserTShardingMapper.selectList(queryWrapper2);

        // 只有分表的分片键 年龄
        QueryWrapper<UserTSharding> queryWrapper3 = new QueryWrapper();
        queryWrapper3.eq("age", 18);
        List<UserTSharding> result3 = UserTShardingMapper.selectList(queryWrapper3);

        // 分库分表的键都没有  全局路由
        List<UserTSharding> result4 = UserTShardingMapper.selectList(null);


    }


}
