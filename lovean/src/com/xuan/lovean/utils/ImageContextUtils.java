/* 
 * @(#)ImageContextUtils.java    Created on 2013-3-13
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.lovean.utils;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * 获取系统图片的工具类
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-3-13 上午9:27:14 $
 * 
 *          crop String 发送裁剪信号<br />
 *          aspectX int X方向上的比例<br />
 *          aspectY int Y方向上的比例<br />
 *          outputX int 裁剪区的宽<br />
 *          outputY int 裁剪区的高<br />
 *          scale boolean 是否保留比例<br />
 *          return-data boolean 是否将数据保留在Bitmap中返回<br />
 *          data Parcelable 相应的Bitmap数据<br />
 *          circleCrop String 圆形裁剪区域？<br />
 *          MediaStore.EXTRA_OUTPUT ("output") URI 将URI指向相应的file:///...<br />
 * 
 *          详见：http://my.oschina.net/ryanhoo/blog/86843
 */
public abstract class ImageContextUtils {

    /**
     * 去系统相册获取图片
     * 
     * @param context
     * @param requestCode
     */
    public static void getMediaStoreImage(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 去相机拍照返回图片
     * 
     * @param activity
     * @param requestCode
     */
    public static void getImageFromCamera(Activity activity, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 裁剪图片，有问题，有ＢＵＧ的
     * 
     * @param activity
     * @param requestCode
     * @param data
     *            是图库选取文件传回的参数
     */
    public static void cutImage(Activity activity, int requestCode, Intent data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setData(data.getData());
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 获取系统图片，并裁剪，并返回裁剪后的图片
     * 
     * @param activity
     * @param requestCode
     * @param filePathName
     */
    public static void getAndCutAndSaveImage(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // intent.putExtra("outputX", 900);
        // intent.putExtra("outputY", 900);
        intent.putExtra("noFaceDetection", false);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(Intent.createChooser(intent, "选择图片"), requestCode);
    }

    /**
     * 获取系统图片，并裁剪，并保存裁剪后的图片到指定路径
     * 
     * @param activity
     * @param requestCode
     * @param filePathName
     */
    public static void getAndCutAndSaveImage2Path(Activity activity, int requestCode, String filePathName) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("output", Uri.fromFile(new File(filePathName)));
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);
        activity.startActivityForResult(Intent.createChooser(intent, "选择图片"), requestCode);
    }

}
