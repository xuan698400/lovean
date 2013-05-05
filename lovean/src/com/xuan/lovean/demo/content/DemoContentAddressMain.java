package com.xuan.lovean.demo.content;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

import com.xuan.lovean.R;
import com.xuan.lovean.content.ContentResolverUtils;
import com.xuan.lovean.content.address.helper.AddressContentObserver;
import com.xuan.lovean.content.address.helper.AddressContentObserver.AddressChangeListener;
import com.xuan.lovean.content.address.helper.AddressEntity;

public class DemoContentAddressMain extends Activity {
	private final Handler handler = new Handler();

	private AddressContentObserver addressContentObserver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 创建一个观察者
		addressContentObserver = new AddressContentObserver(handler, this,
				"xuan1", new AddressChangeListener() {
					@Override
					public void onChange(List<AddressEntity> addAeList,
							List<AddressEntity> delAeList) {
						for (AddressEntity ae : addAeList) {
							Log.d("add",
									"add------:" + ae.getPhone() + ae.getName());
						}

						for (AddressEntity ae : delAeList) {
							Log.d("del",
									"del------:" + ae.getPhone() + ae.getName());
						}
					}
				});

		// 注册，通讯录变化的观察者
		ContentResolverUtils.registerContentObserver(this, Phone.CONTENT_URI,
				true, addressContentObserver);
	}

	@Override
	protected void onDestroy() {

		// 取消
		ContentResolverUtils.unregisterContentObserver(this,
				addressContentObserver);
		super.onDestroy();
	}

}
