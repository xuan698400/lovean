/* 
 * @(#)UpdateManager.java    Created on 2011-12-20
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id: UpdateManager.java 35745 2013-03-12 01:20:28Z xuan $
 */
package com.xuan.lovean.utils.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.xuan.lovean.utils.update.helper.UpdateConfig;

/**
 * 更新应用的工具类
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-3-25 上午9:28:20 $
 */
public class UpdateManager {
    private NotifyCanGotoListener notifyCanGotoListener;

    private static final String TAG = "bigapple.UpdateManager";

    private static final int DOWN_UPDATE = 1;// 正在下载标识
    private static final int DOWN_OVER = 2;// 下载完成标识

    private final Context context;

    // 一些参数
    private final UpdateConfig updateConfig;

    private ProgressDialog updateProgress;// 更新进度条
    private int progress;// 进度值

    private final boolean interceptFlag = false;// 是否取消下载

    private final Handler handler = new Handler() {// 更新下载进度条的handler
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case DOWN_UPDATE:
                updateProgress.setProgress(progress);
                break;
            case DOWN_OVER:
                updateProgress.dismiss();
                installApk();
                break;
            default:
                break;
            }
        };
    };

    public UpdateManager(Context context, UpdateConfig updateConfig) {
        this.context = context;
        this.updateConfig = updateConfig;
    }

    /**
     * 外部接口让主Activity调用-进行更新
     */
    public void doUpdate() {
        showNoticeDialog();
    }

    // 更新前让用户选择是否更新的对话框
    private void showNoticeDialog() {
        AlertDialog.Builder builder = new Builder(context);
        builder.setTitle(updateConfig.getUpdateTitle());
        builder.setMessage(updateConfig.getUpdateText());
        builder.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                if (null != notifyCanGotoListener) {
                    notifyCanGotoListener.notifyCanGoto();
                }
            }
        });
        builder.setPositiveButton(updateConfig.getPositiveBtnText(), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                update();// 操作更新
            }
        }).setNegativeButton(updateConfig.getNegativeBtnText(), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (null != notifyCanGotoListener) {
                    notifyCanGotoListener.notifyCanGoto();
                }
            }
        });

        builder.create().show();
    }

    // 更新操作
    private void update() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // sdcard 不可用
            Toast.makeText(context, "SD卡不可用，无法下载，请安装SD卡后再试。", Toast.LENGTH_SHORT).show();
            if (null != notifyCanGotoListener) {
                notifyCanGotoListener.notifyCanGoto();
            }
            return;
        }

        // 显示更新进度
        updateProgress = new ProgressDialog(context);
        updateProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        updateProgress.setTitle(updateConfig.getProgressText());
        updateProgress.setCancelable(false);
        updateProgress.show();
        downloadApk();
    }

    // 下载apk
    private void downloadApk() {
        new Thread(downLoadApkRunnable).start();
    }

    // 安装apk
    private void installApk() {
        File apkfile = new File(updateConfig.getSaveFileName());
        if (!apkfile.exists()) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    // 下载apk任务
    private final Runnable downLoadApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                HttpGet getMethod = new HttpGet(updateConfig.getApkUrl());
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse response = httpClient.execute(getMethod);
                HttpEntity httpEntity = response.getEntity();
                InputStream is = httpEntity.getContent();
                long length = httpEntity.getContentLength();

                // 创建件文件夹
                File file = new File(updateConfig.getSavePath());
                if (!file.exists()) {
                    boolean success = file.mkdirs();
                    if (!success) {
                        Log.e(TAG, "mkdirs failed");
                    }
                }

                // 创建文件
                String apkFile = updateConfig.getSaveFileName();
                File ApkFile = new File(apkFile);
                if (!ApkFile.exists()) {
                    ApkFile.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(ApkFile);

                // 从输入流中读取字节数据，写到文件中
                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    handler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        // 下载完成通知安装
                        handler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);
                }
                while (!interceptFlag);// 点击取消就停止下载.

                fos.close();
                is.close();
            }
            catch (Exception e) {
                Log.e(TAG, "", e);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "下载包时发生错误。", Toast.LENGTH_SHORT).show();
                        updateProgress.dismiss();
                        if (null != notifyCanGotoListener) {
                            notifyCanGotoListener.notifyCanGoto();
                        }
                    }
                });
            }
        }
    };

    /**
     * 可跳去登录界面的监听
     * 
     * @author xuan
     * @version $Revision: 1.0 $, $Date: 2013-5-2 下午2:22:30 $
     */
    public interface NotifyCanGotoListener {

        /**
         * 可以进行跳转
         */
        public void notifyCanGoto();
    }

    public void setNotifyCanGotoListener(NotifyCanGotoListener notifyCanGotoListener) {
        this.notifyCanGotoListener = notifyCanGotoListener;
    }

}
