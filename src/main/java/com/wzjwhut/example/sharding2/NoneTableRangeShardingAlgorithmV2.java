package com.wzjwhut.example.sharding2;//package com.eques.eqhome.db.sharding;

import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

/**
 * @date: 2019/03/11
 * @author: zhenguo.yao
 */
@Log4j2
public class NoneTableRangeShardingAlgorithmV2 implements RangeShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection tableNames, RangeShardingValue shardingValue) {
        log.info("NoneTableRangeShardingAlgorithmV2, dbNames: {}, sharding: {}", tableNames, shardingValue);
        return tableNames;
    }
}
