/* 
 * @(#)UpdateConfigFactory.java    Created on 2013-5-2
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.lovean.utils.update.helper;

/**
 * 产生一些常用的配置信息的工厂类
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-5-2 下午1:12:33 $
 */
public abstract class UpdateConfigFactory {

    /**
     * 获取更新用途的配置
     * 
     * @param apkUrl
     * @return
     */
    public static UpdateConfig getUpdateConfig(String apkUrl, String savePath, String saveFileName) {
        UpdateConfig uc = new UpdateConfig();
        uc.setApkUrl(apkUrl);
        uc.setSavePath(savePath);
        uc.setSaveFileName(saveFileName);
        return uc;
    }

    /**
     * 获取下载用途的配置
     * 
     * @param apkUrl
     * @return
     */
    public static UpdateConfig getDownloadConfig(String apkUrl, String savePath, String saveFileName) {
        UpdateConfig uc = new UpdateConfig();
        uc.setApkUrl(apkUrl);
        uc.setSavePath(savePath);
        uc.setSaveFileName(saveFileName);

        uc.setNegativeBtnText("以后再说");
        uc.setPositiveBtnText("现在安装");
        uc.setProgressText("软件下载安装");
        uc.setUpdateTitle("组件安装");
        uc.setUpdateText("系统还没安装该组件，请安装！");
        return uc;
    }

}
