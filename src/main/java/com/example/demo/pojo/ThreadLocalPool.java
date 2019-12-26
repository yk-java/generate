package com.example.demo.pojo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThreadLocalPool {
	private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<>();
	private static final ThreadLocal<HttpServletResponse> RESPONSE = new ThreadLocal<>();

	public static HttpServletRequest getHttpServletRequest() {
		return REQUEST.get();
	}

	public static void setHttpServletRequest(HttpServletRequest request) {
		REQUEST.set(request);
	}

	public static HttpServletResponse getHttpServletResponse() {
		return RESPONSE.get();
	}

	public static void setHttpServletResponse(HttpServletResponse response) {
		RESPONSE.set(response);
	}

	public static void cleanUp() {
		REQUEST.remove();
		RESPONSE.remove();
	}
}
