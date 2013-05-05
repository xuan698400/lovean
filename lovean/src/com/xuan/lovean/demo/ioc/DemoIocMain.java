package com.xuan.lovean.demo.ioc;

import android.os.Bundle;
import android.widget.TextView;

import com.xuan.lovean.R;
import com.xuan.lovean.ioc.AnActivity;
import com.xuan.lovean.ioc.InjectView;

public class DemoIocMain extends AnActivity {

	@InjectView(R.id.textView)
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView.setText("成功获取到TextView对象，并重新设置文本");
	}

}
