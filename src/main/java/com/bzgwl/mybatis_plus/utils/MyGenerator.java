package com.bzgwl.mybatis_plus.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyGenerator {

//    @Value("${spring.datasource.url}")
//    private static String dataSourceUrl;
//
//    @Value("${spring.datasource.username}")
//    private static String dataSourceName;
//
//    @Value("${spring.datasource.password}")
//    private static String dataSourcePassword;
//
//    @Value("${spring.datasource.driverClassName}")
//    private static String driverClassName;

//    @Value("${spring.jpa.database}")
//    private static String  dataBase;


    /**
     * 代码生成器
     * @param moduleName 模块名称
     * @param tableName  需要生成的表名称
     */
    public static void excuting(String moduleName,String tableName ,String dataSourceUrl,String dataSourceName,
    String dataSourcePassword,String driverClassName){

        //指定自定义模板路径, 位置：/resources/templates/entity2.java.ftl(或者是.vm)
        //注意不要带上.ftl(或者是.vm), 会根据使用的模板引擎自动识别
        TemplateConfig templateConfig = new TemplateConfig().setEntity("templates/entity.java");
        AutoGenerator mpg = new AutoGenerator();
        //配置自定义模板
        mpg.setTemplate(templateConfig);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setDateType(DateType.ONLY_DATE);  //设置日期类型， 数据库为datetime 则转换为Date
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        gc.setOutputDir(projectPath + "/src/main/java");  //生成文件放置位置
        gc.setAuthor("Professor_Kong");
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);  //设置数据库类型
        dsc.setUrl(dataSourceUrl);
        // dsc.setSchemaName("public");
        dsc.setDriverName(driverClassName);
        dsc.setUsername(dataSourceName);
        dsc.setPassword(dataSourcePassword);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);  //模块名称
        //src/java下包名
        pc.setParent("com.bzgwl.mybatis_plus");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("email", "logwto@163.com");
                this.setMap(map);
            }
        };
        //自定义mapper配置
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称   + pc.getModuleName()
                return projectPath + "/src/main/resources/"
                        + "mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 自定义 xxx.html 生成
        focList.add(new FileOutConfig("/templates/sys_tmp/user_list.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath+"/src/main/resources/templates/"
                        + LowAndUpConvert.lowFirst(tableInfo.getEntityName())+"/"
                        + LowAndUpConvert.lowFirst(tableInfo.getEntityName()) + "_list.html";
            }
        });

//         自定义 xxx.addOrUpdate.html 生成
        focList.add(new FileOutConfig("/templates/sys_tmp/user_addOrUpdate.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath+"/src/main/resources/templates/"
                        + LowAndUpConvert.lowFirst(tableInfo.getEntityName())+"/"
                        + LowAndUpConvert.lowFirst(tableInfo.getEntityName()) + "_addOrUpdate.html";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);


        // 策略配置
        String pagePrefix = "com.bzgwl";
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass(pagePrefix+".BaseEntity");
        strategy.setEntityLombokModel(true);
//        strategy.setSuperControllerClass(pagePrefix+".BaseController");
        strategy.setInclude(tableName);  //需要生成的表名 ，可以是string集合
//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true); //controller设置 RequestMapping
        strategy.setRestControllerStyle(false); //设置RestController
//        strategy.setTablePrefix(pc.getModuleName() + "_");  //数据库表名前缀
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

        // 打印注入设置【可无】
        System.err.println(mpg.getCfg().getMap().get("email"));

    }
}
