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
        //ִ������
        new DocumentationExecute(config).execute();
    }

    /**
     * ��ȡ���ݿ�Դ
     */
    private static DataSource getDataSource() {
        System.out.println("����Դ��ȡ���");
        //����Դ
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://10.16.3.85:3306/epmp_br_test");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("epmp");
        //���ÿ��Ի�ȡtables remarks��Ϣ
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        return new HikariDataSource(hikariConfig);
    }

    /**
     * ��ȡ�ļ���������
     */
    private static EngineConfig getEngineConfig() {
        //��������
        System.out.println("�ļ��������ö�ȡ���");
        return EngineConfig.builder()
                //�����ļ�·��
                .fileOutputDir("D:/guide/Documents/����ʾ��/screw-demo/doc")
                //��Ŀ¼
                .openOutputDir(true)
                //�ļ�����
                .fileType(EngineFileType.HTML)
                //����ģ��ʵ��
                .produceType(EngineTemplateType.freemarker)
                //�Զ����ļ�����
                .fileName("���ݿ�ṹ�ĵ�").build();
    }

    /**
     * ��ȡ���ݿ��Ĵ������ã��ɺ���
     */
    private static ProcessConfig getProcessConfig() {
        ArrayList<String> ignorePrefix = new ArrayList<>();
        ignorePrefix.add("tb_cube_");
        System.out.println("�������ö�ȡ���");
        return ProcessConfig.builder()
                // ָ��ֻ���� blog ��
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
//                //���Ա���
//                .ignoreTableName(ignoreTableName)
//                //���Ա�ǰ׺
//                .ignoreTablePrefix(ignorePrefix)
//                //���Ա��׺
//                .ignoreTableSuffix(ignoreSuffix)
//                .build();
    }


    private static Configuration getScrewConfig(DataSource dataSource, EngineConfig engineConfig, ProcessConfig processConfig) {
        System.out.println("���ö�ȡ���");
        return Configuration.builder()
                //�汾
                .version("1.0.0")
                //����
                .description("���ݿ�����ĵ�����")
                //����Դ
                .dataSource(dataSource)
                //��������
                .engineConfig(engineConfig)
                //��������
                .produceConfig(processConfig)
                .build();
    }

}
