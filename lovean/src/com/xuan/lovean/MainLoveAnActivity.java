package com.xuan.lovean;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.xuan.lovean.asynctask.DemoTask;
import com.xuan.lovean.asynctask.callback.AsyncTaskFailCallback;
import com.xuan.lovean.asynctask.callback.AsyncTaskSuccessCallback;
import com.xuan.lovean.asynctask.helper.Result;

public class MainLoveAnActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
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
