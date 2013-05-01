package com.xuan.lovean;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.Menu;

import com.xuan.lovean.asynctask.DemoTask;
import com.xuan.lovean.asynctask.callback.AsyncTaskFailCallback;
import com.xuan.lovean.asynctask.callback.AsyncTaskSuccessCallback;
import com.xuan.lovean.asynctask.helper.Result;
import com.xuan.lovean.content.ContentResolverUtils;
import com.xuan.lovean.content.address.helper.AddressContentObserver;
import com.xuan.lovean.content.address.helper.AddressContentObserver.AddressChangeListener;
import com.xuan.lovean.content.address.helper.AddressEntity;

public class MainLoveAnActivity extends Activity {
	private final Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ContentResolverUtils.registerContentObserver(this, Phone.CONTENT_URI,
				true, new AddressContentObserver(handler, this, "xuan1",
						new AddressChangeListener() {
							@Override
							public void onChange(List<AddressEntity> addAeList,
									List<AddressEntity> delAeList) {
								for (AddressEntity ae : addAeList) {
									Log.d("add", "add------:" + ae.getPhone()
											+ ae.getName());
								}

								for (AddressEntity ae : delAeList) {
									Log.d("del", "del------:" + ae.getPhone()
											+ ae.getName());
								}
							}
						}));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	public void asyncTest() {
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
