package com.example.demo.vo;import io.swagger.annotations.ApiModel;import io.swagger.annotations.ApiModelProperty;import com.example.demo.pojo.Page;import com.example.demo.pojo.ResponseMessage;import java.util.Date;/** * pdf单元格属性 实体类 * * @author yk */@ApiModel(value = "pdf单元格属性")public class PdfTableVO extends Page {    @ApiModelProperty(value = "主键")    private String id;    @ApiModelProperty(value = "表名")    private String name;    @ApiModelProperty(value = "列数")    private Integer numColumns;    @ApiModelProperty(value = "宽度比例")    private Float widthPercentage;    @ApiModelProperty(value = "表格宽度(每列分别设置格式“1,2,3,4”)")    private String totalWidth;    @ApiModelProperty(value = "设置表格上面空白宽度")    private Float spacingBefore;    @ApiModelProperty(value = "设置表格下面空白宽度")    private Float spacingAfter;    @ApiModelProperty(value = "设置表格边框")    private Integer defaultCellBorder;    @ApiModelProperty(value = "创建时间")    private Date createTime;    @ApiModelProperty(value = "修改时间")    private Date modifyTime;    @ApiModelProperty(value = "印章图片")    private String sealImg;    public String getId() {        return this.id;    }    public void setId(String id) {        this.id = id;    }    public String getName() {        return this.name;    }    public void setName(String name) {        this.name = name;    }    public Integer getNumColumns() {        return this.numColumns;    }    public void setNumColumns(Integer numColumns) {        this.numColumns = numColumns;    }    public Float getWidthPercentage() {        return this.widthPercentage;    }    public void setWidthPercentage(Float widthPercentage) {        this.widthPercentage = widthPercentage;    }    public String getTotalWidth() {        return this.totalWidth;    }    public void setTotalWidth(String totalWidth) {        this.totalWidth = totalWidth;    }    public Float getSpacingBefore() {        return this.spacingBefore;    }    public void setSpacingBefore(Float spacingBefore) {        this.spacingBefore = spacingBefore;    }    public Float getSpacingAfter() {        return this.spacingAfter;    }    public void setSpacingAfter(Float spacingAfter) {        this.spacingAfter = spacingAfter;    }    public Integer getDefaultCellBorder() {        return this.defaultCellBorder;    }    public void setDefaultCellBorder(Integer defaultCellBorder) {        this.defaultCellBorder = defaultCellBorder;    }    public Date getCreateTime() {        return this.createTime;    }    public void setCreateTime(Date createTime) {        this.createTime = createTime;    }    public Date getModifyTime() {        return this.modifyTime;    }    public void setModifyTime(Date modifyTime) {        this.modifyTime = modifyTime;    }    public String getSealImg() {        return this.sealImg;    }    public void setSealImg(String sealImg) {        this.sealImg = sealImg;    }    public ResponseMessage validate() {        ResponseMessage rm = ResponseMessage.getInstance();        if (true) {            return rm.addError("不能为空！");        }        if (true) {            return rm.addError("不能为空！");        }        return rm;    }}