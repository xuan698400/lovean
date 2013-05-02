/* 
 * @(#)SystemBroadcastReceiverConstants.java    Created on 2013-4-25
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.lovean.resource;

import android.content.Intent;

/**
 * 常用系统广播的常量
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-4-25 下午4:01:15 $
 */
public abstract class SystemBroadcastReceiverConstants {

    // 关闭或打开飞行模式时的广播
    public static final String ACTION_AIRPLANE_MODE_CHANGED = Intent.ACTION_AIRPLANE_MODE_CHANGED;

    // 表示电池电量低
    public static final String ACTION_BATTERY_LOW = Intent.ACTION_BATTERY_LOW;

    // 表示电池电量充足，即从电池电量低变化到饱满时会发出广播
    public static final String ACTION_BATTERY_OKAY = Intent.ACTION_BATTERY_OKAY;

    // 在系统启动完成后，这个动作被广播一次（只有一次）。
    public static final String ACTION_BOOT_COMPLETED = Intent.ACTION_BOOT_COMPLETED;

    // 按下照相时的拍照按键(硬件按键)时发出的广播
    public static final String ACTION_CAMERA_BUTTON = Intent.ACTION_CAMERA_BUTTON;

    // 设备日期发生改变时会发出此广播
    public static final String ACTION_DATE_CHANGED = Intent.ACTION_DATE_CHANGED;

    // 设备内存不足时发出的广播,此广播只能由系统使用，其它APP不可用？
    public static final String ACTION_DEVICE_STORAGE_LOW = Intent.ACTION_DEVICE_STORAGE_LOW;

    // 设备内存从不足到充足时发出的广播,此广播只能由系统使用，其它APP不可用？
    public static final String ACTION_DEVICE_STORAGE_OK = Intent.ACTION_DEVICE_STORAGE_OK;

}
