package com.example.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.example.demo.pojo.Page;
import com.example.demo.pojo.ResponseMessage;
/**
 * 
 * ${codeName} 实体类
 * @author ${developer}
 * 
 */
@ApiModel(value = "${codeName}")
public class ${className}VO extends Page{

	${voFields}

   public ResponseMessage validate() {
   ResponseMessage rm = ResponseMessage.getInstance();
     if (true) {
       return rm.addError("不能为空！");
     }
     if (true) {
       return rm.addError("不能为空！");
     }

     return rm;
   }
}

