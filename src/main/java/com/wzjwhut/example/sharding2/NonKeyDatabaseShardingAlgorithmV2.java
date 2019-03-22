package com.wzjwhut.example.sharding2;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

/**
 * 分库算法
 * @date: 2019/02/20
 * @author: zhenguo.yao
 */
@Log4j2
public class NonKeyDatabaseShardingAlgorithmV2 implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> dbNames, PreciseShardingValue<Long> shardingValue) {
        log.info("NonKeyDatabaseShardingAlgorithmV2, dbNames: {}, sharding: {}", dbNames, shardingValue);
        return dbNames.iterator().next();
    }
}
