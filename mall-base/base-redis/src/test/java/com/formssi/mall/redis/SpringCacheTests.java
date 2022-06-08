package com.formssi.mall.redis;

import com.alibaba.fastjson.JSON;
import com.formssi.mall.redis.base.BaseTest;
import com.formssi.mall.redis.bean.User;
import com.formssi.mall.redis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jp
 * @version 1.0
 * @Description: SpringCache测试类
 * @date 2022/4/25 17:25
 *
 * @CacheConfig:主要用于配置该类中会用到的一些共用的缓存配置。
 * @Cacheable:主要方法返回值加入缓存。同时在查询时，会先从缓存中取，若不存在才再发起对数据库的访问。
 *      value、cacheNames：两个等同的参数（cacheNames为Spring 4新增，作为value的别名），用于指定缓存存储的集合名
 *      key：缓存对象存储在Map集合中的key值，非必需，缺省按照函数的所有参数组合作为key值，若自己配置需使用SpEL表达式，比如：@Cacheable(key = “#p0”)：使用函数第一个参数作为缓存的key值
 *      condition：缓存对象的条件，非必需，也需使用SpEL表达式，只有满足表达式条件的内容才会被缓存，比如：@Cacheable(key = “#p0”, condition = “#p0.length() < 3”)，表示只有当第一个参数的长度小于3的时候才会被缓存
 *      unless：另外一个缓存条件参数，非必需，需使用SpEL表达式。它不同于condition参数的地方在于它的- 判断时机，该条件是在函数被调用之后才做判断的，所以它可以通过对result进行判断。
 *      keyGenerator：用于指定key生成器，非必需。若需要指定一个自定义的key生成器，我们需要去实现org.springframework.cache.interceptor.KeyGenerator接口，并使用该参数来指定。需要注意的是：该参数与key是互斥的
 *      cacheManager：用于指定使用哪个缓存管理器，非必需。只有当有多个时才需要使用
 *      cacheResolver：用于指定使用那个缓存解析器，非必需。需通过org.springframework.cache.interceptor.CacheResolver接口来实现自己的缓存解析器
 * @CachePut:配置于函数上，能够根据参数定义条件进行缓存，与@Cacheable不同的是，每次回真实调用函数，所以主要用于数据新增和修改操作上。
 * @CacheEvict:配置于函数上，通常用在删除方法上，用来从缓存中移除对应数据。
 * @Caching:配置于函数上，组合多个Cache注解使用。
 *
 */
@Slf4j
public class SpringCacheTests extends BaseTest {

    @Autowired
    UserService userService;

    @Test
    public void getUserCache() throws Exception{
        User user1 = userService.getUserById(1001);
        User user2 = userService.getUserById(1001);
        log.info(JSON.toJSONString("User1 Query Cache -> " + user1));
        log.info(JSON.toJSONString("User2 Query Cache -> " + user2));
        User user3 = userService.getUserById(1005);
    }

    @Test
    public void addUserByCondition() throws Exception{
        //condition = "#p0.id > 1005    不符合
        userService.addUserByCondition(new User(1004, "李四"));
        //condition = "#p0.id > 1005   符合
        userService.addUserByCondition(new User(1006, "赵六"));
        //unless = "#user.id > 1010"   不符合
        userService.addUserByCondition(new User(1011, "王十一"));
    }

    @Test
    public void addUserByPut() throws Exception{
        //已经存在的数据
        userService.addUserByPut(new User(1006, "赵六2"));
    }

    @Test
    public void delUserById() throws Exception{
        //beforeInvocation = true 执行方法体之前删除cache, allEntries = true 删除所有cache
        userService.delUserById(new User(1001, null));
    }

    @Test
    public void addUserByCaches() throws Exception{
        //已经存在的数据
        userService.addUserByCaches(new User(1007, "孙八"));
    }

}
