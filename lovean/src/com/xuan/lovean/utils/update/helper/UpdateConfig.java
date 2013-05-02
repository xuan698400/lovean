/* 
 * @(#)UpdateConfig.java    Created on 2013-5-2
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.lovean.utils.update.helper;

import android.os.Environment;

/**
 * 下载更新的一些参数
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-5-2 下午12:26:39 $
 */
public class UpdateConfig {

    // apk存放目录和下载地址
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/bigapple/";
    private String saveFileName = savePath + "bigapple-default.apk";
    private String apkUrl;

    // 更新提示语
    private String updateText = "请下载更新最新版本";
    private String updateTitle = "软件版本更新";

    private String positiveBtnText = "现在更新";
    private String negativeBtnText = "以后再说";

    private String progressText = "软件更新";

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getUpdateText() {
        return updateText;
    }

    public void setUpdateText(String updateText) {
        this.updateText = updateText;
    }

    public String getUpdateTitle() {
        return updateTitle;
    }

    public void setUpdateTitle(String updateTitle) {
        this.updateTitle = updateTitle;
    }

    public String getPositiveBtnText() {
        return positiveBtnText;
    }

    public void setPositiveBtnText(String positiveBtnText) {
        this.positiveBtnText = positiveBtnText;
    }

    public String getNegativeBtnText() {
        return negativeBtnText;
    }

    public void setNegativeBtnText(String negativeBtnText) {
        this.negativeBtnText = negativeBtnText;
    }

    public String getProgressText() {
        return progressText;
    }

    public void setProgressText(String progressText) {
        this.progressText = progressText;
    }

}
