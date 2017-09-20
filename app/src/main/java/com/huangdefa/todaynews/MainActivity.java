package com.huangdefa.todaynews;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.huangdefa.todaynews.Fragment.FragmentManager;
import com.huangdefa.todaynews.Utils.ViewUtil;
import com.huangdefa.todaynews.Widget.TabBarLayout;

public class MainActivity extends AppCompatActivity {

    private TabBarLayout tabBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        changeStatusBarColor(0);
        FragmentManager.getManager().init(getSupportFragmentManager(), R.id.main_content);
        FragmentManager.getManager().showFragment(0);
        tabBarLayout = ViewUtil.findViewById(this, R.id.linearLayout);
        tabBarLayout.setTabBarItemListener(new TabBarLayout.TabBarItemListener() {
            @Override
            public void onItemSelected(int index) {
                changeStatusBarColor(index);
                FragmentManager.getManager().showFragment(index);
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


    private View statusBarView;

    private void changeStatusBarColor(int tabIndex) {

        int color = generateStatusBarColor(tabIndex);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        } else {
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            if (statusBarView == null) {
                statusBarView = new View(this);
                int statusBarHeight = ViewUtil.getStatusBarHeight(this);
                ViewGroup.LayoutParams lp = new FrameLayout.LayoutParams(-1, statusBarHeight);
                decorView.addView(statusBarView, lp);
            }
            statusBarView.setBackgroundColor(color);
        }
    }

    private int generateStatusBarColor(int tabIndex) {
        switch (tabIndex) {
            case 0:
                return Color.rgb(212, 61, 61);
            case 1:
            case 2:
                return Color.rgb(190, 190, 190);
            case 3:
                return Color.rgb(55, 55, 55);
        }
        return 0;
    }
}
