package com.xuan.lovean.demo.asynctask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.xuan.lovean.R;
import com.xuan.lovean.asynctask.callback.AsyncTaskFailCallback;
import com.xuan.lovean.asynctask.callback.AsyncTaskSuccessCallback;
import com.xuan.lovean.asynctask.helper.Result;

public class DemoAsyncTaskMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DemoTask demoTask = new DemoTask(this);

		demoTask.setAsyncTaskSuccessCallback(new AsyncTaskSuccessCallback<String>() {
			@Override
			public void successCallback(Result<String> result) {
				String value = result.getValue();
				Log.d("1111", value);
			}
		});

		demoTask.setAsyncTaskFailCallback(new AsyncTaskFailCallback<String>() {
			@Override
			public void failCallback(Result<String> result) {
			}
		});

		demoTask.execute(new Object[] { "xuan" });
	}
}
