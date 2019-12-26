package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.pojo.PageData;
import com.example.demo.pojo.ResponseMessage;
import com.example.demo.pojo.ResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * @author DELL
 */
public class BaseController
{

	/**
	 * 日志
	 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public void writePage(PageData pageDate)
	{
		ResponseMessage rm = ResponseMessage.getInstance();
		rm.setPageData(pageDate);
		writeJson(rm.toJsonString());
	}

	public void writeText(HttpServletResponse response, String text)
	{
		ResponseWriter.writeText(response, text);
	}

	public void writeJson(String jsonString)
	{
		ResponseWriter.writeJson(jsonString);
	}

	public void writeJson(JSONObject json)
	{
		ResponseWriter.writeJson(json);
	}

	public void writeSuccess(String message)
	{
		writeSuccess(message, null);
	}

	public void writeSuccess(String message, JSONObject json)
	{
		JSONObject rj = ResponseMessage.createSuccessJsonMsg(message);
		if (json != null)
		{
			rj.putAll(json);
		}
		writeJson(rj);
	}

	public void write(ResponseMessage rm)
	{
		writeJson(rm.toJsonString());
	}

	public <T> void writeSuccess(String message, T data)
	{
		writeJson(ResponseMessage.createSuccessJsonMsg(message, data));
	}

	public <T> void writeSuccess(T data)
	{
		writeJson(ResponseMessage.createSuccessJsonMsg(data));
	}

	public void writeError()
	{
		writeJson(ResponseMessage.createAnErrorMsg());
	}

	public void writeError(String errorMsg)
	{
		writeJson(ResponseMessage.createAnErrorMsg(errorMsg));
	}

	public void writeError(String errorMsg, JSONObject json)
	{
		JSONObject rj = ResponseMessage.createAnErrorJsonMsg(errorMsg);
		rj.putAll(json);
		writeJson(rj);
	}
}
