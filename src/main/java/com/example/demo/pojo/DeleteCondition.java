package com.example.demo.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

@ApiModel("删除条件")
public class DeleteCondition {
	@ApiModelProperty(value = "多ID用逗号分割", example = "1,2,3")
	private String id;

	public ResponseMessage validate() {
		ResponseMessage rm = ResponseMessage.getInstance();
		if (StringUtils.isEmpty(id)) {
			return rm.addError("请选择要删除数据！");
		}
		return rm;
	}

	@ApiModelProperty(hidden = true)
	public String[] getIds() {
		return StringUtils.split(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
