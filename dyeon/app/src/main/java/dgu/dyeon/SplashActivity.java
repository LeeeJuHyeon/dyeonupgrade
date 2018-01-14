package dgu.dyeon;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by csy on 2017-09-14.
 */

public class SplashActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                finish();
            }
        };

        mHandler.sendEmptyMessageDelayed(0, 3000);
    }
}
