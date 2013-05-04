/* 
 * @(#)MediaConfigFactory.java    Created on 2013-5-2
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.lovean.media.helper;

/**
 * 获取特定的MediaConfig
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-5-2 下午8:09:06 $
 */
public abstract class MediaConfigFactory {

    /**
     * 获取默认的MediaConfig配置
     * 
     * @return
     */
    public static MediaConfig getDefaultMediaConfig() {
        return new MediaConfig();
    }

    /**
     * 自定定义一个MediaConfig配置
     * 
     * @param voicePath
     * @param voiceExt
     * @return
     */
    public static MediaConfig getMediaConfig(String voicePath, String voiceExt) {
        MediaConfig mc = new MediaConfig();
        mc.setVoicePath(voicePath);
        mc.setVoiceExt(voiceExt);

        return mc;
    }

}
