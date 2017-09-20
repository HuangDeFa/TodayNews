package com.huangdefa.todaynews.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huangdefa.todaynews.R;
import com.huangdefa.todaynews.Utils.ViewUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShortVideoFragment extends Fragment {


    public ShortVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_short_video, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        int statusBarHeight = ViewUtil.getStatusBarHeight(getContext());
        getView().setPadding(0,statusBarHeight,0,0);
    }
}
