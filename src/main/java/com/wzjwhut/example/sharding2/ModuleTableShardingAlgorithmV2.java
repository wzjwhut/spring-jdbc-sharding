package com.wzjwhut.example.sharding2;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

/**
 * 分表算法,与分库类似,不同的是:
 * 报警表根据设备id首字母分库, alarms_0~alarms_f, 即(0,16);
 * 0~9,a~f(A~F),分别对应0~16,
 * 其它的字母都对应0 (只有在测试情况下, 才会出现这种情况)
 *
 * 适用于 == 和 in
 *
 *
 * @date: 2019/02/20
 * @author: zhenguo.yao
 */
@Log4j2
public class ModuleTableShardingAlgorithmV2 implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<Long> shardingValue) {
        log.info("ModuleTableShardingAlgorithmV2, dbNames: {}, sharding: {}", tableNames, shardingValue);

        Long shardingVal = shardingValue.getValue();
        return tableNames.iterator().next();
    }
}
