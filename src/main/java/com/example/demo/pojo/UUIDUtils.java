package com.example.demo.pojo;

import java.util.UUID;

/**
 * UUID
 * 
 * @author fengj
 *
 */
public final class UUIDUtils {

	/**
	 * 获取UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		return String.valueOf(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
	}

	public static String getRandomUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
