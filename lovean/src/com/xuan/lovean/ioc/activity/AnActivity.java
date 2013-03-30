/* 
 * @(#)AnActivity.java    Created on 2012-11-29
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id: AnActivity.java 33154 2012-12-09 08:28:10Z xuan $
 */
package com.xuan.lovean.ioc.activity;

import java.lang.reflect.Field;

import android.app.Activity;
import android.util.Log;

import com.xuan.lovean.ioc.annotation.InjectView;

/**
 * 安卓IOC框架使用继承该类
 * 
 * @author xuan
 * @version $Revision: 33154 $, $Date: 2012-12-09 16:28:10 +0800 (周日, 09 十二月
 *          2012) $
 */
public class AnActivity extends Activity {
	public static final String AN_SUMMER_LOG_TAG = "ansummer框架错误信息";// 日志输出TAG

	@Override
	public void setContentView(int layout) {
		super.setContentView(layout);
		initAn();
	}

	/**
	 * 调用此方法，就可以进IOC注入
	 */
	private void initAn() {
		Class clazz = this.getClass();
		Field[] fileds = clazz.getDeclaredFields();

		for (int i = 0; i < fileds.length; i++) {
			initInjectView(fileds[i]);
		}
	}

	/**
	 * 注释InjectView注入
	 * 
	 * @param field
	 */
	private void initInjectView(Field field) {
		InjectView injectView = field.getAnnotation(InjectView.class);

		if (null != injectView) {
			try {
				field.setAccessible(true);
				field.set(this, this.findViewById(injectView.value()));
			} catch (Exception e) {
				Log.e(AN_SUMMER_LOG_TAG, "注入[" + field.getName() + "]出错:", e);
			}
		}
	}

}
