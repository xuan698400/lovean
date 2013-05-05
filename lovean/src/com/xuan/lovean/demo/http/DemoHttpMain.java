package com.xuan.lovean.demo.http;

import android.os.Bundle;
import android.util.Log;

import com.xuan.lovean.R;
import com.xuan.lovean.http.AnHttp;
import com.xuan.lovean.http.helper.AjaxCallBack;
import com.xuan.lovean.http.helper.AjaxParams;
import com.xuan.lovean.ioc.AnActivity;

public class DemoHttpMain extends AnActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// GET已步请求
		AnHttp anHttp = new AnHttp();
		anHttp.get("http://www.baidu.com", null, new AjaxCallBack() {
			@Override
			public void callBack(boolean isSuccess, String result, Exception e) {
				if (isSuccess) {
					Log.d("http_success:---------->", result);
				} else {
					Log.e("http_fail:---------->", result);
				}
			}
		}, false);

		// POST同步请求
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("key1", "value1");
		ajaxParams.put("key2", "value2");

		AnHttp anHttp2 = new AnHttp();
		anHttp2.post("http://www.renren.com", ajaxParams, new AjaxCallBack() {
			@Override
			public void callBack(boolean isSuccess, String result, Exception e) {
				if (isSuccess) {
					Log.d("http_success:---------->", result);
				} else {
					Log.e("http_fail:---------->", result);
				}
			}
		}, true);
	}
}
