package com.example.demo.pojo;

/**
 * 请求响应状态
 * 
 * @author fengj
 *
 */
public enum ResponseStatus {
	/**
	 * 服务器内部异常
	 */
	ERROR(0),

	/**
	 * 正常
	 */
	SUCCESS(1);

	private final int value;

	ResponseStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
