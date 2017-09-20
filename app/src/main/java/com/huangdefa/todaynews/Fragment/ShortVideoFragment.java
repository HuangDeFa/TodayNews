package com.huangdefa.todaynews.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huangdefa.todaynews.R;

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

}
