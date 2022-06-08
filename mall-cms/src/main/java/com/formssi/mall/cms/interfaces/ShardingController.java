package com.formssi.mall.cms.interfaces;

import com.formssi.mall.cms.infrastructure.repository.mapper.UserShardingMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.route.router.masterslave.MasterVisitedManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * c测试读写分离
 */
@Slf4j
@RestController
@RequestMapping("sharding")
public class ShardingController {

    @Autowired
    private UserShardingMapper userShardingMapper;


    @PostMapping("/save")
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
    public List<UserSharding> findUsers() {
        // 强制走主库去查 对实时性要求很高的可以这样设置查读库
        //MasterVisitedManager.setMasterVisited();
        // 读分离
        return userShardingMapper.selectList(null);
    }





}
