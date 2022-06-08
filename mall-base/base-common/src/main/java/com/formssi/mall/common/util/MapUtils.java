package com.formssi.mall.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Map工具类
 */
public class MapUtils {

	public static String getString(Map<String, Object> map, String key){
		return map.get(key).toString();
	}

	public static Integer getInteger(Map<String, Object> map, String key){
		return Integer.parseInt(getString(map, key));
	}

	public static Long getLong(Map<String, Object> map, String key){
		return Long.parseLong(getString(map, key));
	}
	
	/**
	 * 创建Map并赋值
	 * @param key
	 * @param value
	 * @return
	 */
	public static Map<String, Object> newMapPut(String key, Object value) {
		HashMap<String, Object> newMap = new HashMap<>();
		newMap.put(key, value);
		return newMap;
	}

}
