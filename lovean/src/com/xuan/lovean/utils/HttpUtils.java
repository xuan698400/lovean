/* 
 * @(#)HttpUtils.java    Created on 2011-2-18
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id: HttpUtils.java 33829 2012-12-26 05:28:54Z xuan $
 */
package com.xuan.lovean.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.xuan.lovean.common.Constants;

/**
 * 提供访问HTTP服务的工具类
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-3-25 上午9:24:40 $
 */
public abstract class HttpUtils {

	// 默认连接超时时间: 30s
	private static final int DEFAULT_CONNECTION_TIMEOUT = 1000 * 12;
	// 默认读取超时时间: 30s
	private static final int DEFAULT_READ_TIMEOUT = 1000 * 12;

	private static final String DEFAULT_ENCODE = "utf-8";

	private static final String TAG = "szxy.HttpUtils";

	public static String requestURL(String url, int connectionTimeout,
			int readTimeout) throws IOException {
		return requestURL(url, DEFAULT_ENCODE, connectionTimeout, readTimeout);
	}

	/**
	 * 请求指定地址的服务.,返回网页内容，使用UTF-8编码
	 * 
	 * @param url
	 *            URL地址
	 * @return 请求服务返回的结果
	 */
	public static String requestURL(String url) throws IOException {
		return requestURL(url, DEFAULT_ENCODE);
	}

	public static String requestURL(String url, Map<String, String> params,
			String md5Key) throws IOException {
		String securyKey = SecurityUtils.getHttpParamsSecuryKey(params, md5Key);
		params.put("securyKey", securyKey);
		StringBuilder str = new StringBuilder();
		for (Map.Entry<String, String> e : params.entrySet()) {
			str.append(e.getKey()).append("=").append(e.getValue()).append("&");
		}
		if (!params.isEmpty()) {
			str.deleteCharAt(str.length() - 1);
		}
		return requestURL(url + "?" + str.toString(), DEFAULT_ENCODE);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param paramsMap
	 * @param md5Key
	 * @return
	 */
	public static String requestURLPost(String url,
			Map<String, String> paramsMap) {
		Log.v(TAG, url + paramsMap.toString());

		LinkedList<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();

		for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
			try {
				params.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			} catch (Exception e) {
				Log.e(Constants.TAG, "", e);
			}
		}

		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

			HttpResponse response = client.execute(httpPost); // 执行POST方法

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), "utf-8");
			} else {
				Log.e(Constants.TAG, "请求错误");
				return "";
			}

		} catch (Exception e) {
			Log.e(Constants.TAG, "", e);
		}

		return "";
	}

	/**
	 * 请求指定地址的服务.,返回网页内容
	 * 
	 * @param url
	 * @param encoding
	 *            编码
	 * @return
	 */
	public static String requestURL(String url, String encoding)
			throws IOException {
		return requestURL(url, encoding, DEFAULT_CONNECTION_TIMEOUT,
				DEFAULT_READ_TIMEOUT);
	}

	public static String requestURL(String url, String encoding,
			int connectionTimeout, int readTimeout) throws IOException {
		Log.d(TAG, url);
		String result = null;
		BufferedReader reader = null;
		try {
			URLConnection connection = new URL(url).openConnection();
			connection.setConnectTimeout(connectionTimeout);
			connection.setReadTimeout(readTimeout);

			connection.connect();
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), encoding), 8 * 1024);
			String line = null;
			StringBuilder buffer = new StringBuilder();
			line = reader.readLine();
			if (line != null) {
				buffer.append(line);
				while ((line = reader.readLine()) != null) {
					buffer.append("\n" + line);
				}
			}

			result = buffer.toString();
		} catch (IOException e) {
			Log.e(TAG, "Request url[" + url + "] error");
			throw e;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					// Ignore
				}
			}
		}

		return result;
	}

	/**
	 * 下载文件，输出流到file里
	 * 
	 * @param downloadUrl
	 * @param file
	 * @return
	 */
	public static void downloadURLToFile(String downloadUrl, File file)
			throws Exception {
		InputStream in = null;
		OutputStream out = null;
		try {
			File parentFile = file.getParentFile();
			if (!parentFile.exists() || !parentFile.isDirectory()) {
				parentFile.mkdirs();
			}
			URLConnection connection = new URL(downloadUrl).openConnection();
			connection.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
			connection.setReadTimeout(1000 * 60 * 10);

			connection.connect();
			in = connection.getInputStream();
			out = new BufferedOutputStream(new FileOutputStream(file));
			byte[] bs = new byte[1024];
			int bytesReaded = 0;
			while ((bytesReaded = in.read(bs)) != -1) {
				out.write(bs, 0, bytesReaded);
			}
		} catch (Exception e) {
			Log.e(TAG, "download file from url[" + downloadUrl + "] error", e);
			file.delete();
			throw e;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// Ignore
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// Ignore
				}
			}
		}
	}

}
