package com.xuan.lovean.camera;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.xuan.lovean.common.Constants;

/**
 * 照相显示
 * 
 * @author xuan
 * 
 */
@SuppressLint("NewApi")
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder surfaceHolder;
	private Camera camera;
	private Bitmap bitmap;

	public CameraPreview(Context context) {
		super(context);
		init();
	}

	public CameraPreview(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	// 初始化
	private void init() {
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	// -----------以下3个是SurfaceHolder.Callback的方法--------------------

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();

		try {
			camera.setPreviewDisplay(holder);
		} catch (Exception e) {
			camera.release();
			camera = null;
		}

		// 设置相机参数
		try {
			Camera.Parameters parameters = camera.getParameters();
			if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
				// 这是一个众所周知但未文档化的特性
				parameters.set("orientation", "portrait");

				// 对于Android 2.2及以上版本
				camera.setDisplayOrientation(90);

				// parameters.setRotation(90);
			} else {
				// 这是一个众所周知但未文档化的特性
				parameters.set("orientation", "landscape");

				// 对于Android 2.2及以上版本
				camera.setDisplayOrientation(0);

				// parameters.setRotation(0);
			}

			camera.setParameters(parameters);
			camera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			camera.release();
			camera = null;
			return;
		}

		camera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.stopPreview();
		camera.release();
		camera = null;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
	}

	public void takePicture() {
		if (null != camera) {
			camera.takePicture(null, null, jpegCallback);
		}
	}

	/**
	 * 拍照后输出回调
	 */
	private final PictureCallback jpegCallback = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

			File file = new File(Constants.SDCARD + "/xuan.apk/111.jpg");
			try {
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(file));

				bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
				bos.flush();
				bos.close();

				Canvas canvas = surfaceHolder.lockCanvas();
				canvas.drawBitmap(bitmap, 0, 0, null);
				surfaceHolder.unlockCanvasAndPost(canvas);

			} catch (Exception e) {
			}
		}
	};

}
