package com.formssi.mall.redis.config;

import com.formssi.mall.redis.constants.RedisConstant;
import com.formssi.mall.redis.enums.RedissonEnum;
import com.formssi.mall.redis.service.RedissonLockService;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @Description: Redisson自动化配置
 *
 * @author ld & jp
 * @date 2022/4/12 下午11:55
 */
@Configuration
@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedissonConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonConfig.class);

    @Autowired
    RedisProperties redisProperties;

    @Bean
    @ConditionalOnProperty(prefix = RedisConstant.REDISSON_CONDITION_PREFIX, name = RedisConstant.REDISSON_STRATEGY_SINGLE, havingValue = RedisConstant.REDISSON_CONDITION_YES)
    public Redisson singleRedisson() {
        return initRedisson((config, redisProperties) -> {
            String address = redisProperties.getHost().concat(":").concat(redisProperties.getPort()+"");
            String password = redisProperties.getPassword();
            int database = redisProperties.getDatabase();
            String redisAddr = redisProperties.isSsl() ? RedissonEnum.SSL_URL_PREFIX.getValue() : RedissonEnum.URL_PREFIX.getValue();
            redisAddr += address;
            config.useSingleServer().setAddress(redisAddr);
            config.useSingleServer().setDatabase(database);
            //密码可以为空
            if (StringUtils.isNotBlank(password)) {
                config.useSingleServer().setPassword(password);
            }
            //看门狗默认30s
            config.setLockWatchdogTimeout(60*1000L);
            LOGGER.info("部署策略[单机模式] -> redisAddress: {}", address);
            return  (Redisson) Redisson.create(config);
        });
    }

    @Bean
    @ConditionalOnProperty(prefix = RedisConstant.REDISSON_CONDITION_PREFIX, name = RedisConstant.REDISSON_STRATEGY_CLUSTER, havingValue = RedisConstant.REDISSON_CONDITION_YES)
    public Redisson clusterRedisson() {
        return initRedisson((config, redisProperties) -> {
            List<String> address = redisProperties.getCluster().getNodes();
            String password = redisProperties.getPassword();
            //String[] addrTokens = address.split(",");
            //设置cluster节点的服务IP和端口
            String redisAddr = redisProperties.isSsl() ? RedissonEnum.SSL_URL_PREFIX.getValue() : RedissonEnum.URL_PREFIX.getValue();
            for (String addres : address) {
                config.useClusterServers()
                        .addNodeAddress(redisAddr + addres);
                if (StringUtils.isNotBlank(password)) {
                    config.useClusterServers().setPassword(password);
                }
            }
            config.setLockWatchdogTimeout(60*1000L);
            LOGGER.info("部署策略[集群模式] -> redisAddress: {}", address);
            return  (Redisson) Redisson.create(config);
        });
    }

    @Bean
    @ConditionalOnProperty(prefix = RedisConstant.REDISSON_CONDITION_PREFIX, name = RedisConstant.REDISSON_STRATEGY_SENTINEL, havingValue = RedisConstant.REDISSON_CONDITION_YES)
    public Redisson sentinelRedisson() {
        return initRedisson((config, redisProperties) -> {
            String masterAddress = redisProperties.getSentinel().getMaster();
            String password = redisProperties.getPassword();
            int database = redisProperties.getDatabase();
            List<String> nodeAddress = redisProperties.getSentinel().getNodes();
            String sentinelAliasName = Optional.ofNullable(nodeAddress)
                    .map(u-> nodeAddress.get(0))
                    .orElseThrow(() -> new RuntimeException("redis.sentinel.nodes is null, please set"));
            //设置redis配置文件sentinel.conf配置的sentinel别名
            config.useSentinelServers().setMasterName(sentinelAliasName);
            config.useSentinelServers().setDatabase(database);
            if (StringUtils.isNotBlank(password)) {
                config.useSentinelServers().setPassword(password);
            }
            //设置sentinel节点的服务IP和端口
            String redisAddr = redisProperties.isSsl() ? RedissonEnum.SSL_URL_PREFIX.getValue() : RedissonEnum.URL_PREFIX.getValue();
            for (String nodeAddres : nodeAddress) {
                config.useSentinelServers().addSentinelAddress(redisAddr + nodeAddres);
            }
            config.setLockWatchdogTimeout(60*1000L);
            LOGGER.info("部署策略[哨兵模式] -> masterAddress: {}, nodeAddress: {}", masterAddress, nodeAddress);
            return  (Redisson) Redisson.create(config);
        });
    }

    private Redisson initRedisson(BiFunction<Config, RedisProperties, Redisson> func){
        Redisson redisson = null;
        Config config = new Config();
        try {
            LOGGER.info("redisson init start...");
            redisson = func.apply(config, redisProperties);
            LOGGER.info("redisson init complete.");
        } catch (Exception e) {
            LOGGER.error("redisson init error!", e);
            e.printStackTrace();
        }
        return redisson;
    }

    @Bean
    @ConditionalOnProperty(prefix = RedisConstant.CACHE_CONDITION_PREFIX, name = RedisConstant.CACHE_STRATEGY_REDISSION, havingValue = RedisConstant.CACHE_CONDITION_YES)
    public RedissonLockService redissonLockService() {
        return new RedissonLockService();
    }
}
