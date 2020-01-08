package com.example.demo.generate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 获取生成文件中需要用到的sql语句等
 *
 * @author yk
 */
public class CodeGenerationBuilder extends AbstractCodeGenerateFactory {

    private CodeGenerationBuilder(GeneratorParam generatorParam, GeneratorConfig generatorConfig) {
        super(generatorParam, generatorConfig);
    }

    public static CodeGenerationBuilder getInstance(GeneratorParam generatorParam, GeneratorConfig generatorConfig) {
        return new CodeGenerationBuilder(generatorParam, generatorConfig);
    }

    private static final String NOW = "now()";

    /**
     * 通过数据库表获取字段信息列表
     *
     * @param tableName 表名
     * @return list
     * @throws SQLException sql异常
     */
    private List<ColumnData> getColumnData(String tableName) throws SQLException {
        //mysql
        //String sqlColumns = "select column_name ,data_type,column_comment,0,0,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = ? and table_schema = ?";
        //pgsql
        String sqlColumns = "SELECT col.column_name,col.data_type,des.description AS column_comment,col.numeric_precision,col.numeric_scale," +
                "col.character_maximum_length," +
                "col.is_nullable AS nullable" +
                " FROM " +
                "information_schema.columns col" +
                " LEFT JOIN pg_description des ON col.table_name :: regclass = des.objoid" +
                " AND col.ordinal_position = des.objsubid" +
                " WHERE " +
                "table_name = ?";

        List<ColumnData> columnList = Lists.newArrayList();
        ResultSet rs = null;
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sqlColumns)) {

            ps.setString(1, tableName);
            rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1).toLowerCase();
                String type = rs.getString(2);
                String comment = rs.getString(3);
                String precision = rs.getString(4);
                String scale = rs.getString(5);
                String charMaxLength = (rs.getString(6) == null) ? "" : rs
                        .getString(6);
                String nullable = TableConvert.getNullAble(rs.getString(7));
                String jdbcType = getJdbcType(type);
                type = getJavaType(type, precision, scale);
                ColumnData cd = new ColumnData();
                cd.setColumnName(name);
                cd.setDataType(type);
                cd.setJdbcType(jdbcType);
                cd.setColumnType(rs.getString(2));
                cd.setColumnComment(comment);
                cd.setPrecision(precision);
                cd.setScale(scale);
                cd.setCharMaxLength(charMaxLength);
                cd.setNullable(nullable);
                columnList.add(cd);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return columnList;
    }

    /**
     * 通过数据库表字段生成java实体类
     *
     * @param tableName 表名
     * @return String
     * @throws SQLException sql异常
     */
    @Override
    public String getBeanFields(String tableName, Boolean flag) throws SQLException {
        List<ColumnData> dataList = getColumnData(tableName);

        StringBuilder str = new StringBuilder();
        StringBuilder getSet = new StringBuilder();

        for (ColumnData d : dataList) {
            String name = d.getColumnName();
            //转驼峰命名
            name = getColumnValue(name);

            String type = d.getDataType();
            String comment = d.getColumnComment();

            String maxChar = name.substring(0, 1).toUpperCase();

            if (flag) {
                str.append("@ApiModelProperty(value = \"").append(comment).append("\")\r");
            } else {
                str.append("/** ").append(comment).append("*/\r");
            }
            str.append("@ExcelProperty(\"").append(comment).append("\")");
            String rt = "\r";
            str.append(rt).append("private ").append(type).append(" ").append(name).append(";").append(rt);

            String methodName = maxChar + name.substring(1);
            getSet.append(rt).append("public ").append(type).append(" ").append("get").append(methodName).append("() {\r");
            getSet.append("    return this.").append(name).append(";\r}");
            getSet.append(rt)
                    .append("public void ").append("set").append(methodName).append("(").append(type).append(" ").append(name).append(") {\r");
            getSet.append("    this.").append(name).append("=").append(name)
                    .append(";\r}");
        }
        return str.append(getSet).toString();
    }

    /**
     * 获取插入和修改的SQL
     *
     * @param tableName 表名
     * @return map
     * @throws Exception 异常
     */
    @Override
    public Map<String, Object> getAutoCreateSql(String tableName) throws Exception {
        Map<String, Object> sqlMap = Maps.newHashMap();
        List<ColumnData> columnData = getColumnData(tableName);
        String[] columnList = columnData.stream().map(ColumnData::getColumnName).toArray(String[]::new);

        //用于查询显示字段（数据表字段加上实体类字段）
        String columnFields = columnData.stream()
                .map(ColumnData::getColumnName)
                .collect(Collectors.joining(","));

        StringBuilder resultMap = new StringBuilder();
        String id = "<id";
        String result = "<result";
        String endLabel = "/>";
        columnData.forEach(x -> {
            String columnName = getColumnValue(x.getColumnName());
            if ("id".equals(x.getColumnName())) {
                resultMap.append(id).append(" ").append("column=\"").append(x.getColumnName()).append("\" ")
                        .append("property=\"").append(columnName).append("\" ").append("jdbcType=\"").append(x.getJdbcType())
                        .append("\" ").append(endLabel).append("\r");
            } else {
                resultMap.append(result).append(" ").append("column=\"").append(x.getColumnName()).append("\" ")
                        .append("property=\"").append(columnName).append("\" ").append("jdbcType=\"").append(x.getJdbcType())
                        .append("\" ").append(endLabel).append("\r");
            }
        });
        String insert = getInsertSql(tableName, columnData);
        String batchInsert = getBatchInsertSql(tableName, columnData);
        String insertSelective = getInsertSelectiveSql(tableName, columnData);
        String update = getUpdateSql(tableName, columnData);
        String updateSelective = getUpdateSelectiveSql(tableName, columnData);
        String delete = getDeleteSql(tableName, columnList);
        sqlMap.put("columnList", columnList);
        sqlMap.put("columnFields", columnFields);
        sqlMap.put("resultMap", resultMap);
        sqlMap.put("insert", insert.replace(":createTime", NOW).replace(":updateTime", NOW));
        sqlMap.put("batchInsert", batchInsert.replace(":createTime", NOW).replace(":updateTime", NOW));
        sqlMap.put("insertSelective", insertSelective.replace(":createTime", NOW).replace(":updateTime", NOW));
        sqlMap.put("update", update.replace(":createTime", NOW).replace(":updateTime", NOW));
        sqlMap.put("updateSelective", updateSelective.replace(":createTime", NOW).replace(":updateTime", NOW));
        sqlMap.put("delete", delete);
        sqlMap.put("isPageWhere", "<#if pageSize ? exists && pageSize &gt; 0> LIMIT :pageIndex, :pageSize </#if>");
        return sqlMap;
    }

    /**
     * 生成插入语句
     *
     * @param tableName  表名
     * @param columnData 表数据
     * @return String
     */
    private String getInsertSql(String tableName, List<ColumnData> columnData) {
        StringBuilder sbColumnName = new StringBuilder();
        StringBuilder sbColumnValue = new StringBuilder();

        sbColumnValue.append(":createTime, ");
        sbColumnName.append("create_time, ");
        String columnFields = columnData.stream().filter(f -> !"create_time".equals(f.getColumnName()))
                .map(ColumnData::getColumnName)
                .collect(Collectors.joining(", "));
        sbColumnName.append(columnFields);
        String columnValues = columnData.stream().filter(f -> !"create_time".equals(f.getColumnName()))
                .map(x -> {
                    String columnValue = getColumnValue(x.getColumnName());
                    return "#{" + columnValue + "," + "jdbcType=" + x.getJdbcType() + "}";
                }).collect(Collectors.joining(", "));
        sbColumnValue.append(columnValues);
        return "INSERT INTO " + tableName + "(" + sbColumnName.toString() + ")"
                + " values (" + sbColumnValue.toString() + ")";

    }

    /**
     * 生成插入语句
     *
     * @param tableName  表名
     * @param columnData 表数据
     * @return String
     */
    private String getBatchInsertSql(String tableName, List<ColumnData> columnData) {
        StringBuilder sbColumnName = new StringBuilder();
        StringBuilder sbColumnValue = new StringBuilder();

        sbColumnName.append("create_time, ");
        sbColumnValue.append(":createTime, ");
        String columnFields = columnData.stream().filter(f -> !"create_time".equals(f.getColumnName()))
                .map(ColumnData::getColumnName)
                .collect(Collectors.joining(", "));
        sbColumnName.append(columnFields);
        String columnValues = columnData.stream().filter(f -> !"create_time".equals(f.getColumnName()))
                .map(x -> {
                    String columnValue = getColumnValue(x.getColumnName());
                    return "#{field." + columnValue + "," + "jdbcType=" + x.getJdbcType() + "}";
                }).collect(Collectors.joining(", "));
        sbColumnValue.append(columnValues);
        return "INSERT INTO " + tableName + "(" + sbColumnName.toString() + ")\r"
                + " VALUES\r"
                +"<foreach collection=\"list\" index=\"index\" item=\"field\" separator=\",\">\r" +
                "     (" + sbColumnValue.toString() + ")\r" +
                " </foreach>";

    }

    /**
     * 生成插入语句
     *
     * @param tableName  表名
     * @param columnData 表数据
     * @return String
     */
    private String getInsertSelectiveSql(String tableName, List<ColumnData> columnData) {
        StringBuilder sbColumnName = new StringBuilder();
        StringBuilder sbColumnValue = new StringBuilder();

        sbColumnName.append("\r<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\r");
        sbColumnValue.append("\r<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
        String columnFields = columnData.stream()
                .map(x -> {
                    String columnValue = getColumnValue(x.getColumnName());
                    return "<if test=\"" + columnValue + " != null\">\r" + x.getColumnName() + ",\r" + "</if>";
                })
                .collect(Collectors.joining("\r"));
        sbColumnName.append(columnFields);
        sbColumnName.append("</trim> \r");
        String columnValues = columnData.stream()
                .map(x -> {
                    String columnValue = getColumnValue(x.getColumnName());
                    String value = "#{" + columnValue + "," + "jdbcType=" + x.getJdbcType() + "}";
                    return "<if test=\"" + columnValue + " != null\">\r" + value + ",\r" + "</if>";
                }).collect(Collectors.joining("\r"));
        sbColumnValue.append(columnValues);
        sbColumnValue.append("</trim>");
        return "INSERT INTO " + tableName + sbColumnName.toString() + sbColumnValue.toString();
    }

    /**
     * 生成修改语句
     *
     * @param tableName  表名
     * @param columnData 列名
     * @return String
     */
    private String getUpdateSql(String tableName, List<ColumnData> columnData) {
        StringBuilder sb = new StringBuilder();
        sb.append("modify_time = :updateTime,\r");
        String columnValues = columnData.stream().filter(f -> !"modify_time".equals(f.getColumnName()))
                .map(x -> {
                    String columnValue = getColumnValue(x.getColumnName());
                    return x.getColumnName() + " = #{" + columnValue + "," + "jdbcType=" + x.getJdbcType() + "}";
                }).collect(Collectors.joining(",\r"));
        sb.append(columnValues);

        return "UPDATE " + tableName + "\r SET \r" + sb.toString()
                + "\r where id = #{id,jdbcType=VARCHAR}";
    }

    /**
     * 生成修改语句
     *
     * @param tableName  表名
     * @param columnData 列名
     * @return String
     */
    private String getUpdateSelectiveSql(String tableName, List<ColumnData> columnData) {
        StringBuilder sb = new StringBuilder();
        sb.append("\r<set>\r");
        sb.append("modify_time = :updateTime,\r");
        String columnValues = columnData.stream().filter(f -> !"modify_time".equals(f.getColumnName()))
                .map(x -> {
                    String columnValue = getColumnValue(x.getColumnName());
                    String value = x.getColumnName() + " = #{" + columnValue + "," + "jdbcType=" + x.getJdbcType() + "}";
                    return "<if test=\"" + columnValue + " != null\">\r" + value + ",\r" + "</if>";
                }).collect(Collectors.joining("\r"));
        sb.append(columnValues);
        sb.append("\r</set>\r");

        return "UPDATE " + tableName + sb.toString()
                + " where id = #{id,jdbcType=VARCHAR}";
    }

    /**
     * 获取删除的SQL
     *
     * @param tableName   表名
     * @param columnsList 列名
     * @return String
     */
    private String getDeleteSql(String tableName, String[] columnsList) {
        return "delete from " + tableName + " where " +
                columnsList[0] + " = #{" + columnsList[0] + "}";
    }


    /**
     * 下划线转驼峰
     *
     * @param column 值
     * @return String
     */
    private String getColumnValue(String column) {
        String[] columnNames = column.split("_");
        StringBuilder sb = new StringBuilder();
        if (columnNames.length > 1) {
            int i = 1;
            int temp = 1;
            if (!ConstantUtil.F.equals(columnNames[0])) {
                i = 0;
                temp = 0;
            }
            for (; i < columnNames.length; i++) {
                if (i > temp) {
                    String tempColumnName = columnNames[i].substring(0, 1).toUpperCase()
                            + columnNames[i].substring(1);
                    sb.append(tempColumnName);
                } else {
                    String tempColumnName = columnNames[i].substring(0, 1).toLowerCase()
                            + columnNames[i].substring(1);
                    sb.append(tempColumnName);
                }
            }
        } else {
            sb.append(columnNames[0]);
        }
        return sb.toString();
    }

    /**
     * 数据库表类型转换为java类型
     *
     * @param dataType  数据类型
     * @param precision 精度
     * @param scale     位数
     * @return String
     */
    private String getJavaType(String dataType, String precision, String scale) {
        dataType = dataType.toLowerCase();

        switch (dataType) {
            case "char":
            case "text":
            case "character":
            case "character varying":
            case "varchar":
                dataType = "String";
                break;
            case "int":
            case "integer":
            case "tinyint":
            case "smallint":
                dataType = "Integer";
                break;
            case "bigint":
                dataType = "Long";
                break;
            case "boolean":
                dataType = "Boolean";
                break;
            case "float":
            case "real":
                dataType = "Float";
                break;
            case "double":
                dataType = "Double";
                break;
            case "decimal":
                dataType = "java.math.BigDecimal";
                break;
            case "date":
            case "timestamp without time zone":
            case "datetime":
                dataType = "Date";
                break;
            case "time":
                dataType = "java.sql.Timestamp";
                break;
            case "clob":
                dataType = "java.sql.Clob";
                break;
            case "numeric":
            case "number":
                if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0)) {
                    dataType = "BigDecimal";
                } else if ((StringUtils.isNotBlank(precision)) && (Integer.parseInt(precision) > ConstantUtil.SIX)) {
                    dataType = "Long";
                } else {
                    dataType = "Integer";
                }
                break;

            default:
                dataType = "Object";

        }
        return dataType;
    }

    /**
     * 数据库表类型转换为jdbc类型
     *
     * @param dataType 数据类型
     * @return String
     */
    private String getJdbcType(String dataType) {
        dataType = dataType.toLowerCase();

        switch (dataType) {
            case "character":
                dataType = "CHAR";
                break;
            case "text":
            case "character varying":
                dataType = "VARCHAR";
                break;
            case "integer":
                dataType = "INTEGER";
                break;
            case "smallint":
                dataType = "SMALLINT";
                break;
            case "bigint":
                dataType = "BIGINT";
                break;
            case "boolean":
                dataType = "BIT";
                break;
            case "real":
                dataType = "REAL";
                break;
            case "double precision":
                dataType = "DOUBLE";
                break;
            case "time without time zone":
                dataType = "TIME";
                break;
            case "date":
                dataType = "DATE";
                break;
            case "timestamp without time zone":
                dataType = "TIMESTAMP";
                break;
            case "numeric":
                dataType = "NUMERIC";
                break;
            default:
                dataType = "OBJECT";
        }
        return dataType;
    }
}
