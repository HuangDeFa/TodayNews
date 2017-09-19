package com.huangdefa.todaynews.Adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangdefa on 20/09/2017.
 * Version 1.0
 */

public abstract class HorizontalScrollBaseAdapter {

    public abstract int getCount();

    public abstract View getView(int position, ViewGroup parent);
}
