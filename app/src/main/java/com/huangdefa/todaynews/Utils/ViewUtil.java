package com.huangdefa.todaynews.Utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by huangdefa on 06/06/2017.
 */

public class ViewUtil {
    public static <T extends View> T findViewById(Activity activity, int id){
     return  (T)activity.findViewById(id);
    }

    public static <T extends View> T findViewById(View view,int id){
       return (T)view.findViewById(id);
    }

    /**
     * Activity 状态栏透明，可以设置fitSystemWindows属性将布局延伸到全屏
     * @param activity 需要全屏多Activity
     */
    public static void transparentStatusBar(Activity activity){
        //5.0+
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            View decorView = activity.getWindow().getDecorView();
           int option=  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){ //4.4-5.0
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            attributes.flags|= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            activity.getWindow().setAttributes(attributes);
        }
    }

    /**
     * 动态全屏
     * 使用动态全屏 最好再Activity onCreate中设置：
     *  WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
     *  WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
     *  防止全屏重新布局
     * @param activity 需要全屏的Activity
     */
    public static void fullScreenActivity(@NonNull Activity activity, boolean fullscreen){
        if(fullscreen){
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            attributes.flags|=WindowManager.LayoutParams.FLAG_FULLSCREEN;
            activity.getWindow().setAttributes(attributes);
        }else {
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            attributes.flags&=~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            activity.getWindow().setAttributes(attributes);
        }
    }

    /**
     * 获取Activity 的状态栏高度
     * @param context 上下文对象
     * @return 状态栏高度Px
     */
    public static int getStatusBarHeight(@NonNull Context context){
        int statusHeight=0;
       int resourceId= context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resourceId>0){
            statusHeight=context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusHeight;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(@NonNull Activity activity, int color){
        activity.getWindow().setStatusBarColor(color);
    }

    public static void fadeInViewAnimation(View view, @Nullable Animator.AnimatorListener listener){
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "Alpha", 0.0f, 1f);
        alpha.setDuration(200);
        alpha.setInterpolator(new AccelerateInterpolator());
        if(null!=listener)
        alpha.addListener(listener);
        alpha.start();

       //ObjectAnimator scale= ObjectAnimator.ofFloat(view,"ScaleY",0,1f);
       // scale.setDuration(200);
       // scale.setInterpolator(new AccelerateInterpolator());
       /*AnimatorSet animatorSet= new  AnimatorSet();
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(200);
        animatorSet.playTogether(alpha,scale);
        animatorSet.start();*/
    }

    public static void fadeOutViewAnimation(View view,@Nullable Animator.AnimatorListener listener){
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "Alpha", 1f, 0f);
        alpha.setDuration(200);
        alpha.setInterpolator(new AccelerateInterpolator());
        if(null!=listener)
        alpha.addListener(listener);
        alpha.start();
        //ObjectAnimator scale= ObjectAnimator.ofFloat(view,"ScaleY",1,0f);
        // scale.setDuration(200);
        // scale.setInterpolator(new AccelerateInterpolator());
       /* AnimatorSet animatorSet= new  AnimatorSet();
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(200);
        animatorSet.playTogether(alpha,scale);
        animatorSet.start();*/
    }

    public static void TranslateViewAnimation(@NonNull View view, boolean in,@Nullable Animation.AnimationListener listener){
        TranslateAnimation animation=new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF,0,
                TranslateAnimation.RELATIVE_TO_SELF,0,TranslateAnimation.RELATIVE_TO_SELF,in?1f:0f,TranslateAnimation.RELATIVE_TO_SELF,in?0f:1f);
        animation.setDuration(300);
        animation.setFillAfter(true);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(listener);
        view.startAnimation(animation);
    }


}
