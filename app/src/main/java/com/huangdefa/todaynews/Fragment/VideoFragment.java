package com.huangdefa.todaynews.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huangdefa.todaynews.Adapter.HorizontalScrollBaseAdapter;
import com.huangdefa.todaynews.R;
import com.huangdefa.todaynews.Utils.ViewUtil;
import com.huangdefa.todaynews.Widget.ColorChangeTextView;
import com.huangdefa.todaynews.Widget.HorizontalTabLayout;
import com.huangdefa.todaynews.Widget.SuperViewpager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    private HorizontalTabLayout mHorizontalTabLayout;
    private SuperViewpager mSuperViewpager;
    private List<String> mTabItems = new ArrayList<String>() {
        {
            add("推荐");
            add("音乐");
            add("搞笑");
            add("社会");
            add("小品");
            add("生活");
            add("电影");
            add("娱乐");
            add("呆萌");
            add("游戏");
            add("原创");
            add("开眼");
            add("暴走直播");
        }
    };

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        int statusBarHeight = ViewUtil.getStatusBarHeight(getContext());
        getView().setPadding(0,statusBarHeight,0,0);
        mHorizontalTabLayout = ViewUtil.findViewById(getView(), R.id.video_page_header_tab);
        mSuperViewpager = ViewUtil.findViewById(getView(), R.id.video_page_content_viewpager);
        mHorizontalTabLayout.setAdapter(new HorizontalScrollBaseAdapter() {
            @Override
            public int getCount() {
                return mTabItems.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                ColorChangeTextView textView = new ColorChangeTextView(parent.getContext());
                textView.setPadding(30, 20, 30, 20);
                textView.setText(mTabItems.get(position));
                if(position==0){
                    textView.setChangeRatios(1);
                }
                return textView;
            }
        });
    }
}
