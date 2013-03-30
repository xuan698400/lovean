/* 
 * @(#)AudioRecordModel.java    Created on 2012-9-24
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id: MediaRecordModel.java 31004 2012-09-28 11:33:03Z huangwq $
 */
package com.xuan.lovean.model;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Handler;
import android.util.Log;

import com.xuan.lovean.common.Constants;
import com.xuan.lovean.utils.FileUtils;
import com.xuan.lovean.utils.uuid.UUIDUtils;

/**
 * 录音器工具类
 * 
 * @author xuan
 * @version $Revision: 31004 $, $Date: 2012-09-28 19:33:03 +0800 (周五, 28 九月
 *          2012) $
 */
public class MediaRecorderModel {

	private MediaRecorder mediaRecorder;// 录音器
	private volatile String id;// 文件名

	private final ExecutorService singleThreadPool;// 单线程的线程池

	private volatile long lastStartTimeMillis;// 记录开始录音时间，跟结束录音做对比看是否超过某个时间
	private boolean isStarted = false;// 标记是否正在录音

	private final Handler handler = new Handler();

	public MediaRecorderModel() {
		singleThreadPool = Executors.newSingleThreadExecutor();
		checkFile();
	}

	public String getVersionId() {
		return mediaRecorder.toString();
	}

	/**
	 * 开始录制
	 * 
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public void startRecord(
			final OnRecordStartedListener onRecordStartedListener) {
		singleThreadPool.submit(new Runnable() {
			@Override
			public void run() {
				if (isStarted) {
					Log.d(Constants.TAG, "startRecord isStarted");
					return;
				}

				try {
					isStarted = true;
					Log.d(Constants.TAG, "startRecord1");
					mediaRecorder = new MediaRecorder();

					mediaRecorder.setOnInfoListener(new OnInfoListener() {
						@Override
						public void onInfo(MediaRecorder mr, int what, int extra) {
							Log.d(Constants.TAG, "info:" + what + "," + extra);
						}
					});

					mediaRecorder.setOnErrorListener(new OnErrorListener() {
						@Override
						public void onError(MediaRecorder mr, int what,
								int extra) {
							Log.d(Constants.TAG, "error:" + what + "," + extra);
						}
					});

					mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mediaRecorder
							.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);

					// Preparing状态
					mediaRecorder
							.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

					id = UUIDUtils.createId();
					mediaRecorder.setOutputFile(Constants.VOICE_PATH
							+ File.separator + id + "." + Constants.VOICE_EXT);

					try {
						mediaRecorder.prepare();
					} catch (Exception e) {
						Log.e(Constants.TAG, "", e);
					}

					// Prepared状态
					mediaRecorder.start(); // 开始录音

					// 通知录音开始
					handler.post(new Runnable() {
						@Override
						public void run() {
							onRecordStartedListener.onRecordStarted();
						}
					});

					lastStartTimeMillis = System.currentTimeMillis();
					Log.d(Constants.TAG, "startRecord2");
				} catch (Exception e) {
					Log.e(Constants.TAG, "", e);
				}
			}
		});
	}

	/**
	 * 结束录制
	 * 
	 * @param onRecordStopedListener
	 */
	public void stopRecord(final OnRecordStopedListener onRecordStopedListener) {
		singleThreadPool.submit(new Runnable() {
			@Override
			public void run() {
				if (!isStarted) {
					Log.d(Constants.TAG, "stopRecord !isStarted");
					return;
				}

				try {
					Log.d(Constants.TAG, "stopRecord1");
					long x = System.currentTimeMillis() - lastStartTimeMillis;
					boolean success = true;

					// 录音时间如果没超过1S进行onTooShort通知
					if (x < 1000) {
						success = false;

						handler.post(new Runnable() {
							@Override
							public void run() {
								onRecordStopedListener.onTooShort();
							}
						});

						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							Log.e(Constants.TAG, "", e);
						}
					}

					mediaRecorder.stop();
					mediaRecorder.reset(); // 重置mediaRecorder对象，使其为空闲状态
					mediaRecorder.release();// 释放mediaRecorder对象

					// 删除录音时间太短的文件
					if (!success) {
						FileUtils.deleteFolderFile(Constants.VOICE_PATH
								+ File.separator + id + "."
								+ Constants.VOICE_EXT, true);
					}

					final boolean temp = success;

					// 通知录音结束
					handler.post(new Runnable() {
						@Override
						public void run() {
							onRecordStopedListener.onRecordStoped(temp, id);
						}
					});

					Log.d(Constants.TAG, "stopRecord2");
				} catch (Exception e) {
					Log.e(Constants.TAG, "", e);
				} finally {
					isStarted = false;
				}
			}
		});
	}

	/**
	 * 释放资源
	 */
	public void destroy() {
		if (mediaRecorder != null) {
			// mediaRecorder.release();
		}
		singleThreadPool.shutdown();
	}

	// 检查录音文件夹是否存在-不存在就新建一个
	private void checkFile() {
		File _file = new File(Constants.VOICE_PATH);
		if (!_file.exists()) {
			_file.mkdirs();
		}
	}

	/**
	 * 结束录音监听器
	 * 
	 * @author xuan
	 * @version $Revision: 1.0 $, $Date: 2012-11-27 下午2:26:10 $
	 */
	public static interface OnRecordStopedListener {

		// 结束
		void onRecordStoped(boolean success, String fileId);

		// 录音时间太短
		void onTooShort();
	}

	/**
	 * 开始录音监听器
	 * 
	 * @author xuan
	 * @version $Revision: 1.0 $, $Date: 2012-11-27 下午2:08:06 $
	 */
	public static interface OnRecordStartedListener {

		// 开始
		void onRecordStarted();
	}

}
