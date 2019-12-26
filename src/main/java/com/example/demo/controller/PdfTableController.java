package com.example.demo.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.vo.PdfTableVO;
import com.example.demo.service.PdfTableService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.controller.BaseController;
import com.example.demo.pojo.DeleteCondition;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * pdf单元格属性 控制类
 * @author yk
 * 
 */ 
@Controller
@RequestMapping("pdfTable")
@Api(tags = { "pdf单元格属性" })
public class PdfTableController extends BaseController {
	
	@Resource
	private PdfTableService pdfTableService;


    @RequestMapping(value = "/getPageData", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取列表")
    public void getListData(@RequestBody PdfTableVO query) {
        try {
            write(pdfTableService.getPageData(query));
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
            write(pdfTableService.getDetail(id));
        } catch (Exception e) {
            logger.error("获取明细出错.", e);
            writeError();
        }
    }

    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存数据")
    public void saveData(@RequestBody PdfTableVO query) {
        try {
            write(pdfTableService.saveData(query));
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
            write(pdfTableService.delData(dc));
        } catch (Exception e) {
            logger.error("删除数据出错.param={}", ToStringBuilder.reflectionToString(dc), e);
            writeError();
        }
    }
}
