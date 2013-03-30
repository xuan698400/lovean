package com.xuan.lovean.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 字母索引
 * 
 * @author xuan
 */
public class LetterSearchBar extends View {
	private final String[] strArray = new String[] { "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z", "#" };

	private int width;// 单个字母的宽
	private int height;// 单个字母的高

	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);// 画笔

	private OnLetterChange onLetterChange;// 字母改变事件

	public LetterSearchBar(Context context) {
		super(context);
	}

	public LetterSearchBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LetterSearchBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// 布局的大小改变时，就会调用该方法
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = w;
		height = h / strArray.length;
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			int tempY = (int) ((event.getY() - 0) / height);// 计算字母的位置
			if (tempY < strArray.length && event.getY() > 0) {
				if (null != onLetterChange) {
					onLetterChange.letterChange(strArray[tempY]);
				}
				// 这里设置背景2chatfrom_bg_new_fmessage_pressed.9.png
			}
		} else {
			// 这里设置背景1
			// setBackgroundResource(0);
		}

		return true;// 防止事件往父容器传递
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 设置Paint的属性
		paint.setFakeBoldText(true);
		paint.setColor(Color.GRAY);
		paint.setStyle(Style.FILL);
		paint.setTextSize(height * 0.75f);
		paint.setTextAlign(Paint.Align.CENTER);
		FontMetrics fm = paint.getFontMetrics();
		float x = width / 2;
		float y = height / 2 - (fm.ascent + fm.descent) / 2;

		for (int i = 0; i < strArray.length; i++) {
			canvas.drawText(strArray[i], x, i * height + y + 0, paint);
		}
	}

	/**
	 * 搜索字母改变事件
	 * 
	 * @author xuan
	 * @version $Revision: 1.0 $, $Date: 2012-11-1 下午6:10:00 $
	 */
	public interface OnLetterChange {
		void letterChange(String letter);
	}

	public void setOnLetterChange(OnLetterChange onLetterChange) {
		this.onLetterChange = onLetterChange;
	}

}
