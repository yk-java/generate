package com.example.demo.pojo;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpUtils {

	public static HttpServletRequest getRequest() {
		return ThreadLocalPool.getHttpServletRequest();
	}

	public static HttpServletResponse getResponse() {
		return ThreadLocalPool.getHttpServletResponse();
	}

}
