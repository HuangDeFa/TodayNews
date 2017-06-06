package com.huangdefa.todaynews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.huangdefa.todaynews.Utils.ViewUtil;
import com.huangdefa.todaynews.Widget.TabBarLayout;

public class MainActivity extends AppCompatActivity {

    private TabBarLayout tabBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabBarLayout= ViewUtil.findViewById(this,R.id.linearLayout);
        tabBarLayout.setTabBarItemListener(new TabBarLayout.TabBarItemListener() {
            @Override
            public void onItemSelected(int index) {

            }
        });
    }
}
