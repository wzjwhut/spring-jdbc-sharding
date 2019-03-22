package com.wzjwhut.example.sharding2;//package com.eques.eqhome.db.sharding;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

/**
 * @date: 2019/03/11
 * @author: zhenguo.yao
 */
@Log4j2
public class NoneTableShardingAlgorithmV2 implements PreciseShardingAlgorithm {

    @Override
    public String doSharding(Collection tableNames, PreciseShardingValue shardingValue) {
        log.info("NoneTableShardingAlgorithmV2, dbNames: {}, sharding: {}", tableNames, shardingValue);
        return (String)tableNames.iterator().next();
    }
}
