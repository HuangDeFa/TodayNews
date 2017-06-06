package com.huangdefa.todaynews.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.huangdefa.todaynews.R;

/**
 * Created by huangdefa on 06/06/2017.
 */

public class ViewUtil {
    public static <T extends View> T findViewById(Activity activity, int id){
     return  (T)activity.findViewById(id);
    }
}
