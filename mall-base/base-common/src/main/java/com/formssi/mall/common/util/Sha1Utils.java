/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.formssi.mall.common.util;


import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * SHA-1不可逆加密工具类
 * @author ThinkGem
 */
public class Sha1Utils {

	private static final String SHA1 = "SHA-1";

	/**
	 * 生成随机盐
	 */
	public static String genSalt() {
		return RandomStringUtils.randomAlphanumeric(20);
	}

	/**
	 * 对输入字符串进行sha1散列.
	 */
	public static byte[] sha1(byte[] input) {
		return DigestUtils.digest(input, SHA1, null, 1);
	}

	/**
	 * 对输入字符串进行sha1散列.
	 */
	public static byte[] sha1(byte[] input, byte[] salt) {
		return DigestUtils.digest(input, SHA1, salt, 1);
	}

	/**
	 * 对输入字符串进行sha1散列.
	 */
	public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
		return DigestUtils.digest(input, SHA1, salt, iterations);
	}

	/**
	 * 对输入字符串进行sha1散列.
	 */
	public static String sha1(String input, String salt) {
		byte[] bytes = Sha1Utils.sha1(input.getBytes(), salt.getBytes());
		return Hex.encodeHexString(bytes);
	}

	/**
	 * 对文件进行sha1散列.
	 */
	public static byte[] sha1(InputStream input) throws IOException {
		return DigestUtils.digest(input, SHA1);
	}

}
