package com.wzjwhut.example;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.MultipleKeysDatabaseShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.LinkedHashSet;


/**
 * 分库算法
 * 多键分片 {@link com.dangdang.ddframe.rdb.sharding.api.strategy.database.MultipleKeysDatabaseShardingAlgorithm}
 */
public class ModuleDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Long> {
    private final static Logger log = LogManager.getLogger(ModuleDatabaseShardingAlgorithm.class);

    //sql的出现相应字段的等于条件时，执行
    @Override
    public String doEqualSharding(Collection<String> dbNames, ShardingValue<Long> shardingValue) {
        //数据库列表，本例为[database0, database1]
        log.info("[doEqualSharding] availableTargetNames: {}", dbNames);

        //分库字段的值，本例为person的id值
        log.info("[doEqualSharding] shardingValue.getValue(): {}", shardingValue.getValue());

        //分库策略的类型. 本例为SINGLE
        log.info("[doEqualSharding] shardingValue.getType(): {}", shardingValue.getType());

        //分库字段的名称， 本例为id
        log.info("[doEqualSharding] shardingValue.getColumnName(): {}", shardingValue.getColumnName());

        //表的逻辑名称, 本例为person
        log.info("[doEqualSharding] shardingValue.getLogicTableName(): {}", shardingValue.getLogicTableName());

        //根据字段的值，选出一个数据库
        //临时写法
        for (String dbName : dbNames) {
            if (dbName.endsWith(String.valueOf(shardingValue.getValue() % 2 ))) {
                return dbName;
            }
        }
        throw new IllegalArgumentException();
    }

    //sql的出现相应字段的in条件时，执行
    @Override
    public Collection<String> doInSharding(Collection<String> dbNames, ShardingValue<Long> shardingValue) {
        log.info("[doInSharding] values: {}", shardingValue.getValues());
        Collection<String> result = new LinkedHashSet<>(dbNames.size());
        for (Long value : shardingValue.getValues()) {
            for (String dbName : dbNames) {
                if (dbName.endsWith(value % 2 + "")) {
                    result.add(dbName);
                }
            }
        }
        return result;
    }

    //sql的出现相应字段的between条件时，执行
    @Override
    public Collection<String> doBetweenSharding(Collection<String> dbNames,
                                                ShardingValue<Long> shardingValue) {
        log.info("[doBetweenSharding] values: {}", shardingValue.getValueRange());
        Collection<String> result = new LinkedHashSet<>(dbNames.size());
        Range<Long> range = shardingValue.getValueRange();
        for (Long i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String dbName : dbNames) {
                if (dbName.endsWith(i % 2 + "")) {
                    result.add(dbName);
                }
            }
        }
        return result;
    }
}
