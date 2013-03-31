package com.xuan.lovean.utils.textviewhtml;

import java.net.URL;

import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.util.Log;

import com.xuan.lovean.common.Constants;

/**
 * 处理图片时去网络上加载
 * 
 * @author xuan
 */
public class ImgGetter4Url implements ImageGetter {
	@Override
	public Drawable getDrawable(String source) {
		Drawable drawable = null;
		URL url;
		try {
			url = new URL(source);
			drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
		} catch (Exception e) {
			Log.e(Constants.TAG, "", e);
			return null;
		}

		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		return drawable;
	}

}
