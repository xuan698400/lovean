/* 
 * @(#)MediaPlayerUtils.java    Created on 2012-12-13
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.lovean.model;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

import com.xuan.lovean.common.Constants;

/**
 * 播放器工具类
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2012-12-13 下午12:20:12 $
 */
public abstract class MediaPlayerModel {

	private static MediaPlayer mediaPlayer;

	// 播放音频
	public static void playVoice(String id) {
		prepareMediaPlayer();

		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			mediaPlayer.reset();
		}

		try {
			mediaPlayer.setDataSource(Constants.VOICE_PATH + id + "."
					+ Constants.VOICE_EXT);
			mediaPlayer.prepare();
			mediaPlayer.start();
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.reset();
				}
			});
		} catch (Exception e) {
			Log.e(Constants.TAG, "", e);
		}
	}

	/**
	 * 获取MediaPlayer对象
	 * 
	 * @return
	 */
	public static MediaPlayer getMediaPlayer() {
		prepareMediaPlayer();

		return mediaPlayer;
	}

	// 懒加载模式，第一次使用时才实例化对象
	private static void prepareMediaPlayer() {
		if (null == mediaPlayer) {
			mediaPlayer = new MediaPlayer();
		}
	}

}
