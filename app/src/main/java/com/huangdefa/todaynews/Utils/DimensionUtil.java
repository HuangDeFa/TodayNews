package com.huangdefa.todaynews.Utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by huangdefa on 05/06/2017.
 */

public final class DimensionUtil {
    public static int dp2px(Context context,float dpValue){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
         return (int)(dpValue*displayMetrics.density +0.5f);
    }

    public static int px2dp(Context context,float pxValue){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)(pxValue/displayMetrics.density-0.5f);
    }

    public static int sp2px(Context context,float spValue){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)(spValue*displayMetrics.scaledDensity +0.5f);
    }
    public static int px2sp(Context context,float pxValue){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)(pxValue/displayMetrics.scaledDensity -0.5f);
    }
}
