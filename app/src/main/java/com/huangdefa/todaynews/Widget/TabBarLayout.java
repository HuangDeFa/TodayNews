package com.huangdefa.todaynews.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by huangdefa on 05/06/2017.
 */

public class TabBarLayout extends LinearLayout {
    private Paint linePaint;
    public interface TabBarItemListener{
       void onItemSelected(int index);
    }

    public void setTabBarItemListener(TabBarItemListener tabBarItemListener) {
        this.tabBarItemListener = tabBarItemListener;
    }

    private TabBarItemListener tabBarItemListener;

    private int mCurrentSelectedIndex;
    public int getmCurrentSelectedIndex() {
        return mCurrentSelectedIndex;
    }

    public void setmCurrentSelectedIndex(int mCurrentSelectedIndex) {
        this.mCurrentSelectedIndex = mCurrentSelectedIndex;
        ((TabItem)getChildAt(mCurrentSelectedIndex)).setmState(true);
        if(tabBarItemListener!=null){
            tabBarItemListener.onItemSelected(mCurrentSelectedIndex);
        }
    }
    public TabBarLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public TabBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public TabBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public TabBarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setOrientation(HORIZONTAL);
        linePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.LTGRAY);
        linePaint.setStyle(Paint.Style.STROKE);
        mCurrentSelectedIndex=0;
    }

    @Override
    public void setOrientation(int orientation) {
        if (orientation == VERTICAL) {
            throw new IllegalArgumentException("Orientation of TabBarLayout must be HORIZONTAL!");
        }
        super.setOrientation(orientation);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        canvas.drawLine(0,0,getWidth(),2,linePaint);
        boolean handle=super.drawChild(canvas, child, drawingTime);
        ((TabItem)getChildAt(mCurrentSelectedIndex)).setmState(true);
        return handle;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP){
            Rect rect=new Rect();
            int count=getChildCount();
            for(int i=0;i<count;i++){
                getChildAt(i).getHitRect(rect);
               if(rect.contains((int)event.getX(),(int)event.getY())){
                   if(mCurrentSelectedIndex==i){
                       break;
                   }else {
                       ((TabItem)getChildAt(mCurrentSelectedIndex)).setmState(false);
                       mCurrentSelectedIndex=i;
                      ((TabItem)getChildAt(mCurrentSelectedIndex)).setmState(true);
                       if(tabBarItemListener!=null){
                           tabBarItemListener.onItemSelected(mCurrentSelectedIndex);
                       }
                       break;
                   }
               }
            }
        }
       return true;
    }
}
