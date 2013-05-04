/* 
 * @(#)MediaConfig.java    Created on 2013-5-2
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.lovean.media.helper;

import android.os.Environment;

/**
 * media操作的参数类
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-5-2 下午8:03:27 $
 */
public class MediaConfig {
    public String voicePath = Environment.getExternalStorageDirectory().getPath() + "/bigapple/voice/";
    public String voiceExt = "svf";// szxy vodio file

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    public String getVoiceExt() {
        return voiceExt;
    }

    public void setVoiceExt(String voiceExt) {
        this.voiceExt = voiceExt;
    }

}
