package com.wzjwhut.example.sharding2;


import com.mysql.jdbc.Driver;
import com.zaxxer.hikari.HikariDataSource;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.hint.HintShardingAlgorithm;
import io.shardingsphere.api.config.rule.MasterSlaveRuleConfiguration;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.HintShardingStrategyConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingsphere.core.routing.strategy.hint.HintShardingStrategy;
import io.shardingsphere.core.rule.ShardingRule;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import io.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于获取到数据源信息,并不指定分库分表的操作
 *
 * 目前不支持动态的更新cluster.xml
 *
 * @date: 2019/03/07
 * @author: zhenguo.yao
 */
@Configuration
@Log4j2
public class DynamicDataSourceConfigV2 {
    private static final Logger logger = LogManager.getLogger(DynamicDataSourceConfigV2.class);

    @Bean
    public DataSource getDataSource() throws Exception {
        return buildDataSourceV2();
    }



    private DataSource buildDataSourceV2() throws SQLException, NoSuchFieldException, IllegalAccessException {
        log.info("build data source v2");
        Collection<TableRuleConfiguration> tableRuleConfigurations = new LinkedList<>();
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        // 设置默认数据库(未能命中下面定制的规则时会使用该数据源)
        shardingRuleConfig.setDefaultDataSourceName("database0");

        Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();

        //分库列表
        dataSourceMap.put("database0", createDataSource("database0"));
        dataSourceMap.put("database1", createDataSource("database1"));
        dataSourceMap.put("database2", createDataSource("database0"));


        TableRuleConfiguration rule1 = new TableRuleConfiguration();
        // 设置分表的基础表(前缀部分)
        rule1.setLogicTable("person");
        // 数据节点
        rule1.setActualDataNodes("database${0..1}.person_${0..1}");
        // 分表分库的依据字段列名
        rule1.setKeyGeneratorColumnName("id");
        // 分库策略
        rule1.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration(
                "id",
                new ModuleDatabaseShardingAlgorithmV2()));

        // 分表策略
        rule1.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration(
                "id",
                new ModuleTableShardingAlgorithmV2(),new ModuleTableRangeShardingAlgorithmV2()));

        tableRuleConfigurations.add(rule1);


        {
//            TableRuleConfiguration rule2 = new TableRuleConfiguration();
//            // 设置分表的基础表(前缀部分)
//            rule2.setLogicTable("user");
//            // 数据节点
//            rule2.setActualDataNodes("database1.user");
            // 分表分库的依据字段列名
            //rule2.setKeyGeneratorColumnName("id");
            // 分库策略
//            rule2.setDatabaseShardingStrategyConfig(
//                    new StandardShardingStrategyConfiguration("",
//                            new NonKeyDatabaseShardingAlgorithmV2())
//            );

            // 分表策略
//            rule2.setTableShardingStrategyConfig(new HintShardingStrategyConfiguration(new HintShardingAlgorithm(){
//                @Override
//                public Collection<String> doSharding(Collection<String> availableTargetNames, ShardingValue shardingValue) {
//                    log.info("HintShardingAlgorithm {}", availableTargetNames);
//                    return availableTargetNames;
//                }
//            }));
            //tableRuleConfigurations.add(rule2);

        }

//        {
//            MasterSlaveRuleConfiguration rule3 = new MasterSlaveRuleConfiguration();
//            rule3.setMasterDataSourceName("database0");
//            rule3.setSlaveDataSourceNames(Collections.emptyList());
//            shardingRuleConfig.getMasterSlaveRuleConfigs().add(rule3);
//
//        }


        //shardingRuleConfig.getBroadcastTables().add("user");
        shardingRuleConfig.getTableRuleConfigs().addAll(tableRuleConfigurations);
//        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
//                new StandardShardingStrategyConfiguration("",
//                            new NonKeyDatabaseShardingAlgorithmV2()));

        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
                new HintShardingStrategyConfiguration(new HintShardingAlgorithm(){
                    @Override
                    public Collection<String> doSharding(Collection<String> availableTargetNames, ShardingValue shardingValue) {
                        log.info("HintShardingAlgorithm {}", availableTargetNames);
                        return availableTargetNames;
                    }
                }));
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(
                new HintShardingStrategyConfiguration(new HintShardingAlgorithm(){
                    @Override
                    public Collection<String> doSharding(Collection<String> availableTargetNames, ShardingValue shardingValue) {
                        log.info("HintShardingAlgorithm {}", availableTargetNames);
                        return availableTargetNames;
                    }
                }));

        Properties p = new Properties();
        p.setProperty("sql.show",Boolean.TRUE.toString());
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap,
                shardingRuleConfig,
                new ConcurrentHashMap(),p);
        ShardingDataSource sds = (ShardingDataSource)dataSource;
        ShardingRule rule = sds.getShardingContext().getShardingRule();
//        {
//            HintShardingStrategy hint = (HintShardingStrategy) rule.getDefaultDatabaseShardingStrategy();
//            Class cls = HintShardingStrategy.class;
//            Field field = cls.getDeclaredField("shardingColumns");
//            field.setAccessible(true);
//            field.set(hint, new VoidCollection<String>());
//            log.info("HintShardingStrategy size: {}", hint.getShardingColumns().size());
//        }
//        {
//            HintShardingStrategy hint = (HintShardingStrategy) rule.getDefaultTableShardingStrategy();
//            Class cls = HintShardingStrategy.class;
//            Field field = cls.getDeclaredField("shardingColumns");
//            field.setAccessible(true);
//            field.set(hint, new VoidCollection<String>());
//            log.info("HintShardingStrategy size: {}", hint.getShardingColumns().size());
//        }
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
}
