/* 
 * @(#)MediaPlayerUtils.java    Created on 2012-12-13
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.lovean.media;

import java.io.File;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

import com.xuan.lovean.media.helper.MediaConfig;

/**
 * 播放器工具类
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2012-12-13 下午12:20:12 $
 */
public class MediaPlayerModel {
    private static final String TAG = "bigapple.MediaPlayerUtils";

    private final MediaConfig mediaConfig;// 参数配置

    // 保持单例
    private MediaPlayer mediaPlayer;

    public MediaPlayerModel(MediaConfig mediaConfig) {
        this.mediaConfig = mediaConfig;
    }

    /**
     * 播放音频
     * 
     * @param id
     *            文件名
     */
    public void playVoice(String id) {
        prepareMediaPlayer();

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        try {
            mediaPlayer.setDataSource(mediaConfig.getVoicePath() + File.separator + id + "."
                    + mediaConfig.getVoiceExt());
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                }
            });
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
    }

    /**
     * 获取MediaPlayer对象
     * 
     * @return
     */
    public MediaPlayer getMediaPlayer() {
        prepareMediaPlayer();

        return mediaPlayer;
    }

    // 懒加载模式，第一次使用时才实例化对象
    private void prepareMediaPlayer() {
        if (null == mediaPlayer) {
            mediaPlayer = new MediaPlayer();
        }
    }

}
