package com.wzjwhut.example;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.LinkedHashSet;

public final class ModuleTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Long> {
    private final static Logger log = LogManager.getLogger(ModuleTableShardingAlgorithm.class);

    @Override
    public String doEqualSharding(final Collection<String> tableNames, final ShardingValue<Long> shardingValue) {
        log.info("doEqualSharding, tables: {}", tableNames);
        log.info("[doEqualSharding] shardingValue.getValue(): {}", shardingValue.getValue());
        log.info("[doEqualSharding] shardingValue.getType(): {}", shardingValue.getType());
        log.info("[doEqualSharding] shardingValue.getColumnName(): {}", shardingValue.getColumnName());
        log.info("[doEqualSharding] shardingValue.getLogicTableName(): {}", shardingValue.getLogicTableName());

        //临时写法
        for (String tableName : tableNames) {
            log.info("[doEqualSharding] availableTargetNames: {}", tableName);
            if (tableName.endsWith(shardingValue.getValue() % 2 + "")) {
                return tableName;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doInSharding(final Collection<String> tableNames, final ShardingValue<Long> shardingValue) {
        log.info("doInSharding");
        Collection<String> result = new LinkedHashSet<>(tableNames.size());
        for (Long value : shardingValue.getValues()) {
            for (String tableName : tableNames) {
                if (tableName.endsWith(value % 2 + "")) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(final Collection<String> tableNames, final ShardingValue<Long> shardingValue) {
        log.info("doBetweenSharding");
        Collection<String> result = new LinkedHashSet<>(tableNames.size());
        Range<Long> range = shardingValue.getValueRange();
        for (Long i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String tableName : tableNames) {
                if (tableName.endsWith(i % 2 + "")) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }
}