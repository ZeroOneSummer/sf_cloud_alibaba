package com.formssi.mall.sharding.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 自定义分库规则类
 */
@Slf4j
public class MyDbPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    /**
     * 分片策略
     *
     * @param availableTargetNames 所有的数据源
     * @param shardingValue        SQL执行时传入的分片值
     * @return 返回
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        // 真实节点
        availableTargetNames.forEach(item -> log.info("actual node db:{}", item));

        log.info("logic table name:{},rout column:{}", shardingValue.getLogicTableName(), shardingValue.getColumnName());

        //精确分片
        log.info("column value:{}", shardingValue.getValue());

        for (String each : availableTargetNames) {
            Long value = shardingValue.getValue();
            if (("ds" + value % 2).equals(each)) {
                return each;
            }
        }

        return null;
    }
}
