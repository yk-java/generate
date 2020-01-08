package com.example.demo.generate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * 代码生成主类(生成文件并保存本地)
 *
 * @author 88400786
 */
public abstract class AbstractCodeGenerateFactory {

    private static String path = "";

    private static final Log log = LogFactory.getLog(AbstractCodeGenerateFactory.class);

    protected GeneratorConfig generatorConfig;
    private GeneratorParam generatorParam;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            log.error("数据库驱动加载失败{}", e);
        }
    }

    AbstractCodeGenerateFactory(GeneratorParam generatorParam, GeneratorConfig generatorConfig) {
        this.generatorParam = generatorParam;
        this.generatorConfig = generatorConfig;
        setPath(generatorConfig.getProjectPath());
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(generatorConfig.getUrl(), generatorConfig.getUsername(), generatorConfig.getPassword());
    }

    /**
     * 通过数据库表字段生成java实体类
     *
     * @param tableName 数据库表名
     * @param flag true 自动生成接口文档注释 false自动生成字段描述注释
     * @return String
     * @throws SQLException 异常
     */
    public abstract String getBeanFields(String tableName, Boolean flag) throws SQLException;

    /**
     * 获取插入和修改的SQL
     *
     * @param tableName 表名
     * @return map
     * @throws Exception 异常
     */
    public abstract Map<String, Object> getAutoCreateSql(String tableName) throws Exception;


    private static final String PATH_FLAG = "/";

    /**
     * 模板方法
     */
    public void codeGenerate() {

        String lowerName = generatorParam.getClassName().substring(0, 1).toLowerCase() + generatorParam.getClassName().substring(1);
        String srcPath = generatorParam.getProjectPath() + generatorConfig.getSourcePackage().replace(".", "/") + PATH_FLAG;
        String pckPath = srcPath + generatorConfig.getBusinessPackage().replace(".", "/") + PATH_FLAG;

        String beanPath = "vo/" + generatorParam.getClassName() + "VO.java";
        String pojoPath = "pojo/" + generatorParam.getClassName() + "PO.java";
        String servicePath = "service/" + generatorParam.getClassName() + "Service.java";
        String controllerPath = "controller/" + generatorParam.getClassName() + "Controller.java";
        String mapper = "mapper/" + generatorParam.getClassName() + "Mapper.java";
        String mapperPath = generatorParam.getMapper() + generatorParam.getClassName() + "Mapper.xml";

        VelocityContext context = new VelocityContext();
        context.put("className", generatorParam.getClassName());
        context.put("lowerName", lowerName);
        context.put("codeName", generatorParam.getCodeName());
        context.put("tableName", generatorParam.getTableName());
        context.put("bussPackage", generatorConfig.getBusinessPackage());
        //context.put("entityPackage", generatorParam.getEntityPackage().replace(".", "/"));
        context.put("developer", generatorParam.getDeveloper());
        try {
            String voFields = getBeanFields(generatorParam.getTableName(), true);
            String poFields = getBeanFields(generatorParam.getTableName(), false);
            context.put("voFields", voFields);
            context.put("poFields", poFields);
            Map<String, Object> sqlMap = getAutoCreateSql(generatorParam.getTableName());
            context.put("SQL", sqlMap);
        } catch (Exception e) {
            log.error("获取数据库表字段异常:{}", e);
            return;
        }

        CommonPageParser.writerPage(context, "EntityTemplate.ftl", pckPath, beanPath);
        CommonPageParser.writerPage(context, "PoTemplate.ftl", pckPath, pojoPath);
        CommonPageParser.writerPage(context, "ServiceTemplate.ftl", pckPath, servicePath);
        CommonPageParser.writerPage(context, "ActionTemplate.ftl", pckPath, controllerPath);
        CommonPageParser.writerPage(context, "DaoTemplate.ftl", pckPath, mapper);
        CommonPageParser.writerPage(context, "MapperTemplate.xml",  generatorParam.getProjectPath() + "src/main/resources/", mapperPath);

        log.info("----------------------------代码生成完毕---------------------------");
    }


    public static final char UNDERLINE = '_';

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     *
     * @param param 参数
     * @return String
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串
     *
     * @param param 参数
     * @return String
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    static String getProjectPath() {
        return path;
    }

    public void setPath(String path) {
        AbstractCodeGenerateFactory.path = path;
    }
}


