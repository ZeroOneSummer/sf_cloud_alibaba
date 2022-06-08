package com.formssi.mall.redis.service;

import com.formssi.mall.redis.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@CacheConfig(cacheNames = "user_cache", cacheManager = "cacheManager")   //统一指定公共的配置项
@Service
public class UserService {

    //key中的: 为目录分割符
    //同步阻塞
    @Cacheable(key = "'userId:' + #id", sync = true)
    public User getUserById(Integer id) {
        log.info("getUserById -> Cache no data, Query DB & Add Cache");
        //返回值进缓存
        return new User(id, "查询不到时，加入的缓存");
    }

    //插入符合条件（1005 < id <= 1010）的，不符合的不插入cache，但会执行方法体
    //已经存在cache则不执行方法体
    @Cacheable(key = "'userId:' + #user.id", condition = "#p0.id > 1005", unless = "#user.id > 1010")
    @Transactional
    public User addUserByCondition(User user) {
        log.info("addUserByCondition -> Insert DB");
        return user;
    }

    //存在cache也会执行方法体，然后更新Cache
    //#result返回对象
    @Transactional
    @CachePut(key = "'userId:' + #result.name")
    public User addUserByPut(User user) {
        log.info("addUserByPut -> Insert DB");
        return user;
    }

    //userCache下全被删除，删除db也执行了
    //无cache，只执行方法体
    @CacheEvict(key = "'userId:' + #user.id", beforeInvocation = true , allEntries = true)
    public void delUserById(User user) {
        log.info("delUserById -> Delete DB");
    }

    //批量执行多个@CacheXXX
    @Caching(
        cacheable = {@Cacheable(key = "'userId:' + #user.id")}
        , put = {@CachePut(key = "'userName:' + #a0.name")}
        , evict = {@CacheEvict(key = "'userId:' + #user.id")}
    )
    public User addUserByCaches(User user) {
        log.info("addUserByCaches -> Insert DB");
        return user;
    }
}
