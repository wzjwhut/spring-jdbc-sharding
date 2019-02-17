package com.wzjwhut.example;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.keygen.DefaultKeyGenerator;
import com.dangdang.ddframe.rdb.sharding.keygen.KeyGenerator;
import com.mysql.jdbc.Driver;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class DataSourceConfiguration {
    private final static Logger log = LogManager.getLogger(DataSourceConfiguration.class);

    @Bean
    public DataSource getDataSource() throws SQLException {
        return buildDataSource();
    }

    private DataSource buildDataSource() throws SQLException {
        // 设置分库映射
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        //分库列表
        dataSourceMap.put("database0", createDataSource("database0"));
        dataSourceMap.put("database1", createDataSource("database1"));
        //默认库
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap, "database0");

        TableRule orderTableRule = TableRule.builder("person")
                .actualTables(Arrays.asList("person_0", "person_1"))
                .dataSourceRule(dataSourceRule)
                .build();

        //分库分表策略
        ShardingRule shardingRule = ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(orderTableRule))
                .databaseShardingStrategy(new DatabaseShardingStrategy("id", new ModuleDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("id", new ModuleTableShardingAlgorithm())).build();
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
        return dataSource;
    }

    private static DataSource createDataSource(final String dataSourceName) {
        log.info("create data source: {}", dataSourceName);
        HikariDataSource result = new HikariDataSource();
        result.setJdbcUrl(String.format("jdbc:mysql://localhost:3306/%s", dataSourceName));
        result.setDriverClassName(Driver.class.getName());
        result.setUsername("root");
        result.setPassword("123456");
        return result;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new DefaultKeyGenerator();
    }
}
