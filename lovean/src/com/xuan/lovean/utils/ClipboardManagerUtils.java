/* 
 * @(#)ClipboardManagerUtils.java    Created on 2013-4-26
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.lovean.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 黏贴板功能：可以支持Text,Url,Intent
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-4-26 上午10:59:58 $
 */
@SuppressLint("NewApi")
public abstract class ClipboardManagerUtils {

    /**
     * 获取黏贴板
     * 
     * @param context
     * @return
     */
    public static ClipboardManager getClipboardManager(Context context) {
        return (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

    }

    /**
     * 判断是否可黏贴
     * 
     * @param context
     * @return
     */
    public static boolean hasPrimaryClip(Context context) {
        ClipboardManager cm = ClipboardManagerUtils.getClipboardManager(context);
        return cm.hasPrimaryClip();
    }

    /**
     * 判断是否可黏贴Text或Url或Intent <br>
     * Text:ClipDescription.MIMETYPE_TEXT_PLAIN <br>
     * Url:ClipDescription.MIMETYPE_TEXT_URILIST <br>
     * Intent:ClipDescription.MIMETYPE_TEXT_INTENT <br>
     * 
     * @param context
     * @return
     */
    public static boolean hasPrimaryClip(Context context, String type) {
        if (!type.equals(ClipDescription.MIMETYPE_TEXT_PLAIN) && !type.equals(ClipDescription.MIMETYPE_TEXT_URILIST)
                && !type.equals(ClipDescription.MIMETYPE_TEXT_INTENT)) {
            return false;
        }

        ClipboardManager cm = ClipboardManagerUtils.getClipboardManager(context);
        return cm.hasPrimaryClip() && cm.getPrimaryClipDescription().hasMimeType(type);
    }

    /**
     * 是否有可黏贴Text
     * 
     * @param context
     * @return
     */
    public static boolean hasPrimaryClip4Text(Context context) {
        return hasPrimaryClip(context, ClipDescription.MIMETYPE_TEXT_PLAIN);
    }

    /**
     * 是否有可黏贴Uri
     * 
     * @param context
     * @return
     */
    public static boolean hasPrimaryClip4Uri(Context context) {
        return hasPrimaryClip(context, ClipDescription.MIMETYPE_TEXT_URILIST);
    }

    /**
     * 是否有可黏贴Intent
     * 
     * @param context
     * @return
     */
    public static boolean hasPrimaryClip4Intent(Context context) {
        return hasPrimaryClip(context, ClipDescription.MIMETYPE_TEXT_INTENT);
    }

    /**
     * 拷贝文本
     * 
     * @param str
     */
    public static void copy4Text(Context context, String desciption, String text) {
        ClipboardManager cm = ClipboardManagerUtils.getClipboardManager(context);
        ClipData clip = ClipData.newPlainText(desciption, text);
        cm.setPrimaryClip(clip);
    }

    /**
     * 黏贴获取
     * 
     * @param context
     * @return
     */
    public static String paste4Text(Context context) {
        ClipboardManager cm = ClipboardManagerUtils.getClipboardManager(context);
        ClipData.Item item = cm.getPrimaryClip().getItemAt(0);
        return item.getText().toString();
    }

    /**
     * 拷贝Uri
     * 
     * @param str
     */
    public static void copy4Uri(Context context, Uri uri) {
        ClipboardManager cm = ClipboardManagerUtils.getClipboardManager(context);
        ClipData clip = ClipData.newUri(context.getContentResolver(), "URI", uri);
        cm.setPrimaryClip(clip);
    }

    /**
     * 黏贴获取Uri
     * 
     * @param context
     * @return
     */
    public static Uri paste4Uri(Context context) {
        ClipboardManager cm = ClipboardManagerUtils.getClipboardManager(context);
        ClipData.Item item = cm.getPrimaryClip().getItemAt(0);
        return item.getUri();
    }

    /**
     * 拷贝Intent
     * 
     * @param str
     */
    public static void copy4Intent(Context context, Intent intent) {
        ClipboardManager cm = ClipboardManagerUtils.getClipboardManager(context);
        ClipData clip = ClipData.newIntent("Intent", intent);
        cm.setPrimaryClip(clip);
    }

    /**
     * 黏贴获取Uri
     * 
     * @param context
     * @return
     */
    public static Intent paste4Intent(Context context) {
        ClipboardManager cm = ClipboardManagerUtils.getClipboardManager(context);
        ClipData.Item item = cm.getPrimaryClip().getItemAt(0);
        return item.getIntent();
    }

    // -----------------------------注意哦，以下方法在 API level 11中被废弃了哦亲，少用-------------------------

    /**
     * 判断是否有黏贴的东西，用hasPrimaryClip()代替
     * 
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static boolean hasText(Context context) {
        ClipboardManager cm = ClipboardManagerUtils.getClipboardManager(context);
        return cm.hasText();
    }

    /**
     * 复制，用getPrimaryClip()代替
     * 
     * @param context
     * @param str
     */
    @SuppressWarnings("deprecation")
    public static void setClipboard(Context context, String text) {
        ClipboardManager cm = ClipboardManagerUtils.getClipboardManager(context);
        cm.setText(text);
    }

    /**
     * 黏贴，用setPrimaryClip(ClipData clip)代替
     * 
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getClipboard(Context context) {
        ClipboardManager cm = ClipboardManagerUtils.getClipboardManager(context);
        return cm.getText().toString();
    }

}
