package com.xuan.lovean.camera;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.xuan.lovean.R;

public class CameraActivity extends Activity {
	private CameraPreview preview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.camera_activity);

		preview = (CameraPreview) findViewById(R.id.preview);

		// activity横向显示
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		// preview = new Preview(this);
		// setContentView(preview);
	}
}
