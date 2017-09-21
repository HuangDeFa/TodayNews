package com.huangdefa.todaynews.Widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * Created by ken.huang on 9/21/2017.
 *  下拉刷新Layout,只能有一个子View
 */

public class PullToRefreshLayout extends LinearLayout {

    private static final String LOG_TAG = PullToRefreshLayout.class.getSimpleName();
    private int mTouchSlop;
    private boolean mRefreshing;
    private int downY,lastY;
    private int  mActivePointerId;

    private boolean mIsBeingDragged=false;

    public PullToRefreshLayout(Context context) {
        this(context,null);
    }

    public PullToRefreshLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PullToRefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setOrientation(VERTICAL);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mActivePointerId = -1;
    }

    @Override
    public void setOrientation(int orientation) {
        if(orientation!=VERTICAL){
            throw new IllegalArgumentException("orientation must be vertical");
        }
        super.setOrientation(orientation);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int action = ev.getActionMasked();
        int pointerIndex = -1;

        if(mRefreshing||canScrollUp()){
            return false;
        }
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                mIsBeingDragged=false;
                if (pointerIndex < 0) {
                    return false;
                }
                downY= (int) ev.getY(pointerIndex);
                lastY = downY;
                mRefreshing =false;
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    Log.e(LOG_TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                final float y = ev.getY(pointerIndex);
                startDragging(y);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mActivePointerId=-1;
                mIsBeingDragged = false;
                break;
        }

        return mIsBeingDragged;
    }

    private void startDragging(float y) {
        final float yDiff = y - downY;
        if (yDiff > mTouchSlop && !mIsBeingDragged) {
            mIsBeingDragged = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        int pointerIndex = -1;
        if(mRefreshing||canScrollUp()){
            return false;
        }

        switch (action){
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                mIsBeingDragged=false;
                if (pointerIndex < 0) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    Log.e(LOG_TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }

                final float y = ev.getY(pointerIndex);
                startDragging(y);

                if (mIsBeingDragged) {

                }
                break;
            case MotionEvent.ACTION_UP:
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    Log.e(LOG_TAG, "Got ACTION_UP event but don't have an active pointer id.");
                    return false;
                }

                if (mIsBeingDragged) {

                }
                mActivePointerId = -1;
                break;
        }

        return true;
    }

    private boolean canScrollUp(){
        return this.canScrollVertically(-1);
    }

    public interface RefreshHeaderCreator{

        View createView();

        void onDragView(int distance);

        int resetView(int distance);
    }

    public interface onRefreshListener{
        void onRefresh();
    }
}
