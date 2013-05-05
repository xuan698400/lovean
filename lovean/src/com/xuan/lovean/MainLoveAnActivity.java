package com.xuan.lovean;

import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.xuan.lovean.camera.CameraPreview;
import com.xuan.lovean.ioc.AnActivity;

public class MainLoveAnActivity extends AnActivity {

	private CameraPreview preview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// setContentView(R.layout.camera_activity);

		// preview = (CameraPreview) findViewById(R.id.preview);

		TableLayout table = new TableLayout(this);

		int numberOfRow = 3;
		int numberOfColumn = 1;

		int cellDimension = 24;
		int cellPadding = 2;

		for (int row = 0; row < numberOfRow; row++) {
			TableRow tableRow = new TableRow(this);

			switch (row) {
			case 0:
				tableRow.setLayoutParams(new LayoutParams(60,
						LayoutParams.FILL_PARENT));

				TextView textView = new TextView(this);
				textView.setBackgroundResource(R.color.color_black);

				textView.setText("rrrrrrrrrrr");
				tableRow.addView(textView);

				table.addView(tableRow, new LayoutParams(
						(cellDimension + 2 * cellPadding) * numberOfColumn,
						cellDimension + 2 * cellPadding));
				break;
			case 1:
				tableRow.setLayoutParams(new LayoutParams(
						(cellDimension + 2 * cellPadding) * numberOfColumn,
						LayoutParams.FILL_PARENT));

				TextView textView2 = new TextView(this);
				textView2.setText("rrrrrrrrrrr");
				tableRow.addView(textView2);

				table.addView(tableRow, new LayoutParams(
						(cellDimension + 2 * cellPadding) * numberOfColumn,
						cellDimension + 2 * cellPadding));
				break;
			case 2:
				tableRow.setLayoutParams(new LayoutParams(
						(cellDimension + 2 * cellPadding) * numberOfColumn,
						LayoutParams.FILL_PARENT));

				TextView textView3 = new TextView(this);
				textView3.setText("rrrrrrrrrrr");
				tableRow.addView(textView3);

				table.addView(tableRow, new LayoutParams(
						(cellDimension + 2 * cellPadding) * numberOfColumn,
						cellDimension + 2 * cellPadding));
				break;
			}

			tableRow.setLayoutParams(new LayoutParams(
					(cellDimension + 2 * cellPadding) * numberOfColumn,
					LayoutParams.FILL_PARENT));
		}
		// activity横向显示
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		// preview = new Preview(this);
		setContentView(table);
	}
}
