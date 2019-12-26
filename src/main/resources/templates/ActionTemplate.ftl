package ${bussPackage}.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.alibaba.fastjson.JSONObject;
import ${bussPackage}.vo.${className}VO;
import ${bussPackage}.service.${className}Service;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import ${bussPackage}.controller.BaseController;
import ${bussPackage}.pojo.DeleteCondition;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * ${codeName} 控制类
 * @author ${developer}
 * 
 */ 
@Controller
@RequestMapping("${lowerName}")
@Api(tags = { "${codeName}" })
public class ${className}Controller extends BaseController {
	
	@Resource
	private ${className}Service ${lowerName}Service;


    @RequestMapping(value = "/getPageData", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取列表")
    public void getListData(@RequestBody ${className}VO query) {
        try {
            write(${lowerName}Service.getPageData(query));
        } catch (Exception e) {
            logger.error("");
            writeError();
        }
    }

    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取明细")
    public void getDetail(String id) {
        try {
            write(${lowerName}Service.getDetail(id));
        } catch (Exception e) {
            logger.error("获取明细出错.", e);
            writeError();
        }
    }

    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存数据")
    public void saveData(@RequestBody ${className}VO query) {
        try {
            write(${lowerName}Service.saveData(query));
        } catch (Exception e) {
            logger.error("保存数据出错.param={}", ToStringBuilder.reflectionToString(query), e);
            writeError();
        }
    }

    @RequestMapping(value = "/delData", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除数据")
    public void delData(@RequestBody DeleteCondition dc) {
        try {
            write(${lowerName}Service.delData(dc));
        } catch (Exception e) {
            logger.error("删除数据出错.param={}", ToStringBuilder.reflectionToString(dc), e);
            writeError();
        }
    }
}
