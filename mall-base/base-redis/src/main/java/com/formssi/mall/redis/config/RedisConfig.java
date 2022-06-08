package com.formssi.mall.redis.config;

import com.formssi.mall.redis.constants.RedisConstant;
import com.formssi.mall.redis.service.RedisService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Jackson2JsonRedisSerializer & GenericJackson2JsonRedisSerializer
 * 1.序列化带泛型的数据时，会以map的结构进行存储，反序列化是不能将map解析成对象
 * 2.使用Jackson2JsonRedisSerializer需要指明序列化的类Class，可以使用Obejct.class
 * 3.使用GenericJacksonRedisSerializer比Jackson2JsonRedisSerializer效率低，占用内存高。
 * 4.Jackson2JsonRedisSerializer反序列化带泛型的数组类会报转换异常，解决办法存储以JSON字符串存储。
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableCaching  //开启缓存
public class RedisConfig {

    private final Duration timeToLive = Duration.ofDays(7);
    private final StringRedisSerializer keySerializer = new StringRedisSerializer();
    private final GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();

    @Bean
    public RedisTemplate<String, Object> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * Cache缓存管理器
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        // 设置缓存的默认过期时间，也是使用Duration设置
        config = config
                // 设置默认ttl，不设置默认为-1表示永不过期
                .entryTtl(timeToLive)
                // 设置 key为string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
                // 设置value为json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))
                // 不缓存空值
                .disableCachingNullValues()
                // 覆盖默认的构造key，否则会多出一个冒号 原规则：cacheName::key  现规则：cacheName:key
                .computePrefixWith(name -> name + ":")
                ;

        // 对每个缓存空间(cacheName属性)应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("gms_cache", config.entryTtl(Duration.ofMinutes(60)));
        configMap.put("fms_cache", config.entryTtl(Duration.ofMinutes(30)));

        // 使用自定义的缓存配置初始化一个cacheManager
        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                //默认配置
                .cacheDefaults(config)
                // 特殊配置（一定要先调用该方法设置初始化的缓存名，再初始化相关的配置）
                .initialCacheNames(configMap.keySet())
                .withInitialCacheConfigurations(configMap)
                //开启redis事务(和jdbc事务相关联)
                .transactionAware()
                .build();

        return cacheManager;
    }

    @Bean
    @ConditionalOnProperty(prefix = RedisConstant.CACHE_CONDITION_PREFIX, name = RedisConstant.CACHE_STRATEGY_REDIS, havingValue = RedisConstant.CACHE_CONDITION_YES)
    public RedisService redisService() {
        return new RedisService();
    }
}
