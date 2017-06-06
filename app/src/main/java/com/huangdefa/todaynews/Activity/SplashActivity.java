package com.huangdefa.todaynews.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huangdefa.todaynews.MainActivity;
import com.huangdefa.todaynews.R;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {

    class MyHandler extends Handler {
        private WeakReference<SplashActivity> mActivity;

        private MyHandler(SplashActivity Activity) {
            mActivity = new WeakReference<>(Activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what < COUNT_DOWN_SECOND) {
                handler.sendEmptyMessageDelayed(msg.what += 1, 1000);
                mTextView.setText((COUNT_DOWN_SECOND - msg.what) + "s跳过广告");
            } else {
                if (mActivity.get() != null) {
                    mActivity.get().finish();
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
        }
    }

    Handler handler = new MyHandler(this);
    TextView mTextView;
    final int COUNT_DOWN_SECOND = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mTextView = (TextView) findViewById(R.id.tv_countdown);
        mTextView.setText(COUNT_DOWN_SECOND + "s跳过广告");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacksAndMessages(null);
                finish();
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        });
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
