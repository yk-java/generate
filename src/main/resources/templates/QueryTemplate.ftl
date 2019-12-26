package ${bussPackage}.model.query.${entityPackage};

import ${bussPackage}.db.common.Page;

/**
 *
 * ${codeName} 查询对象，前台传入的参数
 * @author ${developer}
 *
 */
public class ${className}VO extends Page {

    /**
     * 查询值
     */
    private String queryValue;
    /**
     * 页码
     */
    private Integer start;
    /**
     * 页码大小
     */
    private Integer length;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getQueryValue() {
        return queryValue;
    }

    public void setQueryValue(String queryValue) {
        this.queryValue = queryValue;
    }
}

