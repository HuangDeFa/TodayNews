package com.huangdefa.todaynews.Widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huangdefa.todaynews.Utils.DimensionUtil;

/**
 * Created by ken.huang on 9/21/2017.
 * 下拉刷新Layout,只能有一个子View
 */

public class PullToRefreshLayout extends LinearLayout {

    private static final String LOG_TAG = PullToRefreshLayout.class.getSimpleName();
    private int mTouchSlop;
    private boolean mRefreshing,mLoadingMore;
    private int downY, lastY;
    private int mActivePointerId;
    private static final int INVALID_POINTER = -1;

    private boolean mIsBeingDragged = false;
    private boolean mIsBeingLoaded = false;

    private View mRefreshView;
    private RefreshHeaderCreator mRefreshHeaderCreator;

    public void setRefreshListener(onRefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

    private onRefreshListener mRefreshListener;

    //the target view may be ListView、RecyclerView,GridView
    private View mTarget;

    public PullToRefreshLayout(Context context) {
        this(context, null);
    }

    public PullToRefreshLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }
    }

    public PullToRefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        setRefreshListener(mTestListener);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setOrientation(VERTICAL);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mActivePointerId = -1;
        addRefreshView(new DefaultRefreshHeaderCreator(context));
    }

    public void addRefreshView(RefreshHeaderCreator headerCreator){
        if(mRefreshView!=null){
            throw new IllegalArgumentException("only add refreshHeader once !");
        }
        mRefreshHeaderCreator=headerCreator;
        mRefreshView = mRefreshHeaderCreator.createView();
        addView(mRefreshView);
    }

    @Override
    public void setOrientation(int orientation) {
        if (orientation != VERTICAL) {
            throw new IllegalArgumentException("orientation must be vertical");
        }
        super.setOrientation(orientation);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int action = ev.getActionMasked();
        int pointerIndex;

        if (mRefreshing || canChildScrollUp()) {
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                mIsBeingDragged = false;
                if (pointerIndex < 0) {
                    return false;
                }
                downY = (int) ev.getY(pointerIndex);
                mRefreshing = false;
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
                //mActivePointerId = INVALID_POINTER;
                mIsBeingDragged = false;
                break;
        }

        return mIsBeingDragged || processLoadMoreIntercept(ev);
    }

    private boolean processLoadMoreIntercept(MotionEvent ev) {

        final int action = ev.getActionMasked();
        if (mLoadingMore || canChildScrollDown()) {
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = (int) ev.getY(mActivePointerId);
                mLoadingMore = false;
                mIsBeingLoaded = false;
                break;
            case MotionEvent.ACTION_MOVE:
                final float y = ev.getY(mActivePointerId);
                startDraggingForLoadMore(y);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mActivePointerId = INVALID_POINTER;
                mIsBeingLoaded = false;
                break;
        }
        return mIsBeingLoaded;
    }

    private void startDragging(float y) {
        final float yDiff = y - downY;
        if (yDiff > mTouchSlop && !mIsBeingDragged) {
            downY = downY + mTouchSlop;
            mIsBeingDragged = true;
        }
    }

    private void  startDraggingForLoadMore(float y){
        final float yDiff = y - downY;
        if (yDiff > mTouchSlop && !mIsBeingLoaded) {
            lastY = lastY + mTouchSlop;
            mIsBeingLoaded = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        int pointerIndex;
        if (mRefreshing || canChildScrollUp()) {
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                mIsBeingDragged = false;
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
                    float overScroll = y - downY;
                    if (overScroll > 0) {
                        processDrag(overScroll,true);
                    } else {
                        return false;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                return false;
            case MotionEvent.ACTION_UP:
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    Log.e(LOG_TAG, "Got ACTION_UP event but don't have an active pointer id.");
                    return false;
                }

                if (mIsBeingDragged) {
                    final float upy = ev.getY(pointerIndex);
                    final float overscrollTop = upy - downY;
                    finishDrag(overscrollTop,true);
                    mIsBeingDragged = false;
                }
               // mActivePointerId = -1;
                break;
        }
        boolean handleLoadMore = processLoadMoreTouchEvent(ev);
        return handleLoadMore ;
    }

    private boolean processLoadMoreTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        if(mLoadingMore || canChildScrollDown()){
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mIsBeingLoaded = false;
                break;
            case MotionEvent.ACTION_MOVE:

                final float y = ev.getY(mActivePointerId);
                startDraggingForLoadMore(y);
                if (mIsBeingLoaded) {
                    float overScroll =lastY-y;
                    if (overScroll > 0) {
                        processDrag(overScroll,false);
                    } else {
                        return false;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                return false;
            case MotionEvent.ACTION_UP:
                if (mIsBeingLoaded) {
                    final float upy = ev.getY(mActivePointerId);
                    final float overscrollTop = lastY-upy;
                    finishDrag(overscrollTop,false);
                    mIsBeingLoaded = false;
                }
                mActivePointerId = INVALID_POINTER;
                break;
        }
        return true;
    }

    /**
     * 拖拽下拉
     *
     * @param overScroll
     */
    private void processDrag(float overScroll,boolean isRefresh) {
        if(isRefresh) {
            if (mRefreshHeaderCreator != null) {
                mRefreshHeaderCreator.onDragView(overScroll);
            }
        }else {

        }
    }

    private void finishDrag(float overScroll,boolean isRefresh) {
        if(isRefresh) {
            if (mRefreshHeaderCreator != null) {
                mRefreshing = mRefreshHeaderCreator.resetView(overScroll) > 0;
            }
            if (mRefreshing) {
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
            }
        }else{
            //do nothing for load auto
        }
    }

    public void stopRefresh(){
        if(mRefreshHeaderCreator!=null){
            mRefreshHeaderCreator.stopRefresh();
            mRefreshing = false;
        }
    }

    private boolean canChildScrollUp() {
        return mTarget.canScrollVertically(-1);
    }

    private boolean canChildScrollDown() {
        return mTarget.canScrollVertically(1);
    }

    private void ensureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laid
        // out yet.
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if(mRefreshView!=null) {
                    if (!child.equals(mRefreshView)) {
                        mTarget = child;
                        break;
                    }
                }else {
                    mTarget = child;
                }
            }
        }
    }

    public interface RefreshHeaderCreator {

        View createView();

        void onDragView(float distance);

        /**
         * @param distance
         * @return
         */
        int resetView(float distance);

        void stopRefresh();
    }

    private onRefreshListener mTestListener=new onRefreshListener() {
        @Override
        public void onRefresh() {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopRefresh();
                }
            },2000);
        }
    };

    private static class DefaultRefreshHeaderCreator implements RefreshHeaderCreator {
        private Context mContext;
        private RelativeLayout mContainerLayout;
        private LinearLayout.LayoutParams mLayoutParams;
        private TextView mTextView;
        private int mLimit;

        public DefaultRefreshHeaderCreator(Context context) {
            this.mContext = context;
            mContainerLayout = new RelativeLayout(context);
            mTextView = new TextView(context);
            mLimit = DimensionUtil.dp2px(context,50);
           RelativeLayout.LayoutParams params=new
                  RelativeLayout.LayoutParams(-1,mLimit);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            mTextView.setGravity(Gravity.CENTER);
            mTextView.setTextSize(13);
            mTextView.setTextColor(Color.BLACK);
            mTextView.setLayoutParams(params);
            mContainerLayout.addView(mTextView);
            mTextView.setText("下拉刷新");
            mLayoutParams=new LinearLayout.LayoutParams(-1,0);
            mContainerLayout.setLayoutParams(mLayoutParams);
        }
        @Override
        public View createView() {
            return mContainerLayout;
        }

        @Override
        public void onDragView(float distance) {
         mLayoutParams.height=(int) distance;
         mContainerLayout.setLayoutParams(mLayoutParams);
         if(distance>mLimit){
             mTextView.setText("释放立即刷新");
         }else {
             mTextView.setText("下拉刷新");
         }
        }

        private ValueAnimator mValueAnimator;

        @Override
        public int resetView(float distance) {
            int result = 0;
            int realToMargin=0;
           if(distance>mLimit){
               mTextView.setText("正在刷新...");
               result = 1;
               realToMargin = mLimit;
           }else {
               mTextView.setText("下拉刷新");
               realToMargin = 0;
           }
            mValueAnimator=ValueAnimator.ofInt(mLayoutParams.height,realToMargin);
            mValueAnimator.setDuration(300);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                   mLayoutParams.height= (int) animation.getAnimatedValue();
                  mContainerLayout.setLayoutParams(mLayoutParams);
                }
            });
            mValueAnimator.start();
            return result;
        }

        @Override
        public void stopRefresh() {
            mTextView.setText("刷新完毕！");
            mTextView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLayoutParams.height=0;
                    mContainerLayout.setLayoutParams(mLayoutParams);
                }
            },1500);
        }
    }

    public interface onRefreshListener {
        void onRefresh();
    }
}
