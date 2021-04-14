package com.example.demo.pojo;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

public class ResponseMessage
{

	private static final String STATUS_KEY = "status";
	private static final String MESSAGE_KEY = "message";
	private static final String DATA_KEY = "data";
	private static final String PAGE_DATA_KEY = "page_data";
	public static final String SYSTEM_MSG_CONTENT = "服务器忙，稍候请重试！";

	private ResponseStatus status = ResponseStatus.SUCCESS;
	private String errorMsg;
	private String message;
	/** 响应内容 */
	private Object data;

	private PageData pd;

	public static ResponseMessage getInstance()
	{
		return new ResponseMessage();
	}

	public String toJsonString()
	{
		return toJson().toString();
	}

	public JSONObject toJson()
	{
		JSONObject jo = new JSONObject();
		if (hasError())
		{
			jo.put(STATUS_KEY, status.getValue());
			jo.put(MESSAGE_KEY, errorMsg);
		}
		else
		{
			jo.put(STATUS_KEY, status.getValue());
			jo.put(MESSAGE_KEY, message);
		}
		if (data != null)
		{
			jo.put(DATA_KEY, data);
		}

		if (pd != null)
		{
			jo.put(PAGE_DATA_KEY, pd);
		}
		return jo;
	}

	public static String createAnErrorMsg()
	{
		return createAnErrorMsg(SYSTEM_MSG_CONTENT);
	}

	public static String createAnErrorMsg(String anErrorMsg)
	{
		return createAnErrorJsonMsg(anErrorMsg).toJSONString();
	}

	public static JSONObject createAnErrorJsonMsg(String anErrorMsg)
	{
		JSONObject jo = new JSONObject();
		jo.put(STATUS_KEY, ResponseStatus.ERROR.getValue());
		jo.put(MESSAGE_KEY, anErrorMsg);
		return jo;
	}

	public static String createSuccessMsg()
	{
		return createSuccessMsg("");
	}

	public static String createSuccessMsg(String msg)
	{

		return createSuccessJsonMsg(msg).toJSONString();
	}

	public static JSONObject createSuccessJsonMsg(String msg)
	{
		return createSuccessJsonMsg(msg, null);
	}

	public static <T> JSONObject createSuccessJsonMsg(String msg, T data)
	{
		JSONObject jo = new JSONObject();
		jo.put(STATUS_KEY, ResponseStatus.SUCCESS.getValue());
		jo.put(MESSAGE_KEY, msg);
		if (null != data)
		{
			jo.put(DATA_KEY, data);
		}
		return jo;
	}

	public static <T> JSONObject createSuccessJsonMsg(T data)
	{
		JSONObject jo = new JSONObject();
		jo.put(STATUS_KEY, ResponseStatus.SUCCESS.getValue());
		jo.put(MESSAGE_KEY, "");
		if (null != data)
		{
			jo.put(DATA_KEY, data);
		}
		return jo;
	}

	public ResponseStatus getStatus()
	{
		return status;
	}

	public void setStatus(ResponseStatus status)
	{
		this.status = status;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}

	public ResponseMessage addError(String anError)
	{
		setErrorMsg(anError);
		return this;
	}

	public void setErrorMsg(String errorMsg)
	{
		setErrorMsg(ResponseStatus.ERROR, errorMsg);
	}

	public void setErrorMsg(ResponseStatus status, String errorMsg)
	{
		this.status = status;
		this.errorMsg = errorMsg;
	}

	public boolean hasError()
	{
		return StringUtils.isNotEmpty(errorMsg);
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.status = ResponseStatus.SUCCESS;
		this.message = message;
	}

	public boolean isSuccess()
	{
		return StringUtils.isEmpty(errorMsg);
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T getData()
	{
		return (T) data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	public void setPageData(PageData pd)
	{
		this.pd = pd;
	}

	public PageData getPageData()
	{
		return pd;
	}

}
