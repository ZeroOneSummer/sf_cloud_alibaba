package com.formssi.mall.sharding.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;

@Slf4j
public class MyTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        // 真实节点
        availableTargetNames.forEach(item -> log.info("actual node table:{}", item));

        log.info("logic table name:{},rout column:{}", shardingValue.getLogicTableName(), shardingValue.getColumnName());

        // 精确分片
        log.info("column value:{}", shardingValue.getValue());
        Date date = new Date();
        date.getMonth();
        for (String each : availableTargetNames) {
            Long value = shardingValue.getValue();
            // oms_cart_t0
            if (("oms_cart_t" + value % 4).equals(each)) {
                return each;
            }
        }

        return null;
    }
}
