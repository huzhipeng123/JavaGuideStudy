package com.java.test;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @program: JavaGuide
 * @description:
 * @author: huzhpm
 * @created: 2021/08/10 20:42
 */
public class ScrewTest {

    public static void main(String[] args) {
        Configuration config = getScrewConfig(getDataSource(), getEngineConfig(), getProcessConfig());
        //执行生成
        new DocumentationExecute(config).execute();
    }

    /**
     * 获取数据库源
     */
    private static DataSource getDataSource() {
        System.out.println("数据源读取完成");
        //数据源
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://10.16.3.85:3306/epmp_br_test");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("epmp");
        //设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        return new HikariDataSource(hikariConfig);
    }

    /**
     * 获取文件生成配置
     */
    private static EngineConfig getEngineConfig() {
        //生成配置
        System.out.println("文件生成配置读取完成");
        return EngineConfig.builder()
                //生成文件路径
                .fileOutputDir("D:/guide/Documents/代码示例/screw-demo/doc")
                //打开目录
                .openOutputDir(true)
                //文件类型
                .fileType(EngineFileType.HTML)
                //生成模板实现
                .produceType(EngineTemplateType.freemarker)
                //自定义文件名称
                .fileName("数据库结构文档").build();
    }

    /**
     * 获取数据库表的处理配置，可忽略
     */
    private static ProcessConfig getProcessConfig() {
        ArrayList<String> ignorePrefix = new ArrayList<>();
        ignorePrefix.add("tb_cube_");
        System.out.println("处理配置读取完成");
        return ProcessConfig.builder()
                // 指定只生成 blog 表
//                .designatedTableName(new ArrayList<>(Collections.singletonList("blog")))
                .ignoreTablePrefix(ignorePrefix)
                .build();


//        ArrayList<String> ignoreTableName = new ArrayList<>();
//        ignoreTableName.add("test_user");
//        ignoreTableName.add("test_group");
//        ArrayList<String> ignorePrefix = new ArrayList<>();
//        ignorePrefix.add("test_");
//        ArrayList<String> ignoreSuffix = new ArrayList<>();
//        ignoreSuffix.add("_test");
//        return ProcessConfig.builder()
//                //忽略表名
//                .ignoreTableName(ignoreTableName)
//                //忽略表前缀
//                .ignoreTablePrefix(ignorePrefix)
//                //忽略表后缀
//                .ignoreTableSuffix(ignoreSuffix)
//                .build();
    }


    private static Configuration getScrewConfig(DataSource dataSource, EngineConfig engineConfig, ProcessConfig processConfig) {
        System.out.println("配置读取完成");
        return Configuration.builder()
                //版本
                .version("1.0.0")
                //描述
                .description("数据库设计文档生成")
                //数据源
                .dataSource(dataSource)
                //生成配置
                .engineConfig(engineConfig)
                //生成配置
                .produceConfig(processConfig)
                .build();
    }

}
