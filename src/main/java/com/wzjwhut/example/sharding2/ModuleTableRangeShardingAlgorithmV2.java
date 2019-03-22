package com.wzjwhut.example.sharding2;

import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

/**
 * 分表算法,与分库类似,不同的是:
 * 报警表根据设备id首字母分库, alarms_0~alarms_f, 即(0,16);
 * 0~9,a~f(A~F),分别对应0~16,
 * 其它的字母都对应0 (只有在测试情况下, 才会出现这种情况)
 * @date: 2019/02/20
 * @author: zhenguo.yao
 */
@Log4j2
public class ModuleTableRangeShardingAlgorithmV2 implements RangeShardingAlgorithm<String> {
    @Override
    public Collection<String> doSharding(Collection<String> tableNames, RangeShardingValue<String> rangeShardingValue) {
        log.info("ModuleTableRangeShardingAlgorithmV2, dbNames: {}, sharding: {}", tableNames, rangeShardingValue);
        //        Collection<String> result = new LinkedHashSet<>(tableNames.size());
//        Range<String> range = rangeShardingValue.getValueRange();
//        String lowStr = range.lowerEndpoint();
//        String upper = range.upperEndpoint();
//
//        char expected_suffix = shardingVal.charAt(0);
//        for(String table : tableNames){
//            char actual_suffix = table.charAt(table.length() -1 );
//            if(expected_suffix == actual_suffix){
//                result.add(table);
//            }
//        }
        //  @TODO 需要比对在这两个device_id 之间有多少id,得到这些id的首字符,判断属于哪个表,但是这样感觉会很慢,一般不会用到,干脆直接抛出异常
        throw new UnsupportedOperationException("未找到合适的table");

    }
}
