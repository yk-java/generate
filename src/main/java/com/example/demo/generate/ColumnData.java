package com.example.demo.generate;

/**
 * 获取数据库表中字段信息
 *
 * @author yk
 */
public class ColumnData {

    private String columnName;
    private String dataType;
    private String jdbcType;
    private String columnComment;
    private String columnType;
    private String charMaxLength = "";
    private String nullable;
    private String scale;
    private String precision;
    private String classType = "";

    private String optionType = "";

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getColumnComment() {
        return this.columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getScale() {
        return this.scale;
    }

    public String getPrecision() {
        return this.precision;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getClassType() {
        return this.classType;
    }

    public String getOptionType() {
        return this.optionType;
    }

    public String getCharMaxLength() {
        return this.charMaxLength;
    }

    public String getNullable() {
        return this.nullable;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public void setCharMaxLength(String charMaxLength) {
        this.charMaxLength = charMaxLength;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getColumnType() {
        return this.columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}
