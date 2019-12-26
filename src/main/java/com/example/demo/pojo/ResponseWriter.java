package com.example.demo.pojo;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseWriter {
	/** plain */
	private static final String CONTENT_TYPE_PLAIN = "text/plain;charset=UTF-8";
	/** json */
	private static final String CONTENT_TYPE_JSON = "text/json;charset=UTF-8";

	/**
	 * 输出TEXT格式的文本到页面
	 * 
	 * @param response
	 * @param text
	 */
	public static void writeText(HttpServletResponse response, String text) {
		write(text, CONTENT_TYPE_PLAIN);
	}

	/**
	 * 输出Json格式的文本到页面
	 * 
	 * @param json
	 *            Json文本内容
	 */
	public static void writeJson(String json) {
		write(json, CONTENT_TYPE_JSON);
	}

	public static void writeJson(JSONObject json) {
		writeJson(json.toJSONString());
	}

	private static void write(String text, String contentType) {
		HttpServletResponse response = HttpUtils.getResponse();
		if (response != null) {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType(contentType);
			PrintWriter pWriter = null;
			try {
				pWriter = response.getWriter();
				pWriter.write(text);
				pWriter.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pWriter != null) {
					pWriter.close();
				}
			}
		}
	}
}
