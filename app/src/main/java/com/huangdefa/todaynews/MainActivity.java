package com.huangdefa.todaynews;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.huangdefa.todaynews.Utils.ViewUtil;
import com.huangdefa.todaynews.Widget.TabBarLayout;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private TabBarLayout tabBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.rgb(212,61,61));
        }else {
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View view=new View(this);
            view.setBackgroundColor(Color.rgb(212,61,61));
            int statusBarHeight = ViewUtil.getStatusBarHeight(this);
            ViewGroup.LayoutParams lp=new FrameLayout.LayoutParams(-1,statusBarHeight);
            decorView.addView(view,lp);
        }
        tabBarLayout= ViewUtil.findViewById(this,R.id.linearLayout);
        tabBarLayout.setTabBarItemListener(new TabBarLayout.TabBarItemListener() {
            @Override
            public void onItemSelected(int index) {

            }

            @Override
            public void onTabRefresh(int index) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tabBarLayout.stopTabRefresh();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
