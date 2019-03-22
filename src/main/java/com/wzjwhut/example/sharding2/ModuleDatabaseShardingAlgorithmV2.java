package com.wzjwhut.example.sharding2;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

/**
 * factories.length 指的是配置文件中配置的对应表的数据库节点个数
 * int hash  =  Math.abs(deviceId.hashCode())%factories.length;
 * hash为0 的设备报警存在第1个分库, 依次类推.
 *
 * 分库算法
 * @date: 2019/02/20
 * @author: zhenguo.yao
 */
@Log4j2
public class ModuleDatabaseShardingAlgorithmV2 implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> dbNames, PreciseShardingValue<Long> shardingValue) {
        log.info("ModuleDatabaseShardingAlgorithmV2, dbNames: {}, sharding: {}", dbNames, shardingValue);
        return dbNames.iterator().next();
    }
}
