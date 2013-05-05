package com.xuan.lovean.http.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 请求参数
 * 
 * @author xuan
 */
public class AjaxParams {

	// 参数
	private final HashMap<String, String> paramsMap = new HashMap<String, String>();

	public AjaxParams() {
	}

	public AjaxParams(Map<String, String> source) {
		for (Map.Entry<String, String> entry : source.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	public AjaxParams(String key, String value) {
		put(key, value);
	}

	/**
	 * 可以这样new String[]{'key1','value1','key2','value2'}初始化参数
	 * 
	 * @param keysAndValues
	 */
	public AjaxParams(String... keysAndValues) {
		int len = keysAndValues.length;
		if (len % 2 != 0) {
			throw new IllegalArgumentException(
					"Supplied arguments must be even");
		}

		for (int i = 0; i < len; i += 2) {
			put(keysAndValues[i], keysAndValues[i + 1]);
		}
	}

	/**
	 * 添加参数
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, String value) {
		if (key != null && value != null) {
			paramsMap.put(key, value);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> entry : paramsMap.entrySet()) {
			if (sb.length() > 0) {
				sb.append("&");
			}

			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
		}

		return sb.toString();
	}

	public HashMap<String, String> getParamsMap() {
		return paramsMap;
	}

}
