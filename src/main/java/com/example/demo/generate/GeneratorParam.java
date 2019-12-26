package com.example.demo.generate;

/**
 * @author yk
 */
public class GeneratorParam {

    /**
     * 表名
     */
    private String tableName;
    /**
     * 实体类
     */
    private String className;
    /**
     * 实体类的中文说明
     */
    private String codeName;
    /**
     * 开发者
     */
    private String developer;
    /**
     * 代码生成存放的包名
     */
    private String entityPackage;

    /**
     * 生成存放xml位置
     */
    private String mapper;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getMapper() {
        return mapper;
    }

    public void setMapper(String mapper) {
        this.mapper = mapper;
    }
}
