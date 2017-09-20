package com.huangdefa.todaynews.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
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

public class MainPageFragment extends Fragment {

    private HorizontalTabLayout mHorizontalTabLayout;
    private SuperViewpager mSuperViewpager;

    private List<String> mTabItems = new ArrayList<String>() {
        {
            add("关注");
            add("推荐");
            add("热点");
            add("视频");
            add("广州");
            add("社会");
            add("娱乐");
            add("电影");
            add("经济");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mainpage, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {

        mHorizontalTabLayout = ViewUtil.findViewById(getView(), R.id.main_page_header_tab);
        mSuperViewpager = ViewUtil.findViewById(getView(), R.id.main_page_content_viewpager);

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
        }, mSuperViewpager);

        final List<View> mViews = new ArrayList<>();
        for (int i = 0; i < mTabItems.size(); i++) {
            View view = new View(getActivity());
            if (i % 2 == 0)
                view.setBackgroundColor(Color.YELLOW);
            else
                view.setBackgroundColor(Color.RED);
            mViews.add(view);
        }
        mSuperViewpager.setPageMargin(10);
        mSuperViewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        mSuperViewpager.setCurrentItem(0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
