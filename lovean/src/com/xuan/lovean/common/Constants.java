/* 
 * @(#)Constants.java    Created on 2013-3-25
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.lovean.common;

import android.os.Environment;

/**
 * 常量
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-3-25 上午9:20:44 $
 */
public abstract class Constants {

	public static final String TAG = "lovean";

	// 手机SD卡的路径
	public static final String SDCARD = Environment
			.getExternalStorageDirectory().getPath();

	// 更新apk时，下载下来存放的文件夹路径
	public static final String UPDATE_APK_PATH = SDCARD + "/autils/";
	public static final String APK_NAME = "autils-android.apk";// 文件的名字

	// 音频存放
	public static final String VOICE_PATH = SDCARD + "/autils/voice/";
	public static final String VOICE_EXT = "svf";// szxy vodio file

}
