package com.wzjwhut.example;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.NoneKeyDatabaseShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.LinkedHashSet;


public class NonkeyDatabaseShardingAlgorithm implements NoneKeyDatabaseShardingAlgorithm<Long> {
    private final static Logger log = LogManager.getLogger(NonkeyDatabaseShardingAlgorithm.class);

    @Override
    public String doSharding(Collection availableTargetNames, ShardingValue shardingValue) {
        log.info("sharding value: {}, availableTargetNames: {}", shardingValue, availableTargetNames);
        return (String) availableTargetNames.iterator().next();
    }
}
