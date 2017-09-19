package com.huangdefa.todaynews.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.UiThread;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.huangdefa.todaynews.R;
import com.huangdefa.todaynews.Utils.DimensionUtil;

/**
 * Created by huangdefa on 05/06/2017.
 */

public class TabItem extends View {
    private Paint mNormalPaint;
    private Paint mMessagePaint;
    private Drawable mIcon;
    private Drawable mRefreshIcon;
    private float mIconHeight;
    private float mIconWidth;
    private boolean mState;


    private boolean mTabMessageEnable;
    private String mTabName;
    private int mTabNormalColor;
    private float mTextSize;
    private  final  int MINIHEIGHT= DimensionUtil.dp2px(getContext(),40);

    public Drawable getmIcon() {
        return mIcon;
    }

    public void setmIcon(Drawable mIcon) {
        this.mIcon = mIcon;
        initDrawable();
    }

    public float getmIconHeight() {
        return mIconHeight;
    }

    public void setmIconHeight(float mIconHeight) {
        this.mIconHeight = mIconHeight;
    }

    public float getmIconWidth() {
        return mIconWidth;
    }

    public void setmIconWidth(float mIconWidth) {
        this.mIconWidth = mIconWidth;
    }

    public boolean ismState() {
        return mState;
    }

    public void setmState(boolean mState) {
        this.mState = mState;
        if(!mState){
            if(mStartRefresh){
                stopRefresh();
            }
        }
        invalidate();
    }

    public String getmTabName() {
        return mTabName;
    }

    public void setmTabName(String mTabName) {
        this.mTabName = mTabName;
    }

    public int getmTabNormalColor() {
        return mTabNormalColor;
    }

    public void setmTabNormalColor(int mTabNormalColor) {
        this.mTabNormalColor = mTabNormalColor;
        mNormalPaint.setColor(mTabNormalColor);
    }

    public int getmSelectedColor() {
        return mSelectedColor;
    }

    public void setmSelectedColor(int mSelectedColor) {
        this.mSelectedColor = mSelectedColor;
    }

    private int mSelectedColor;

    public TabItem(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public TabItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public TabItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public TabItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabItem, defStyleAttr, defStyleRes);
        mIcon = typedArray.getDrawable(R.styleable.TabItem_Icon);
        mIconHeight = typedArray.getDimension(R.styleable.TabItem_IconHeight, DimensionUtil.dp2px(context, 25));
        mIconWidth = typedArray.getDimension(R.styleable.TabItem_IconWidth, DimensionUtil.dp2px(context, 25));
        mTabName = typedArray.getString(R.styleable.TabItem_TabName);
        mState = typedArray.getInteger(R.styleable.TabItem_TabState, 0)==1;
        mTabNormalColor = typedArray.getColor(R.styleable.TabItem_TabNormalColor, Color.GRAY);
        mSelectedColor = typedArray.getColor(R.styleable.TabItem_TabSelectedColor, Color.RED);
        mTextSize = typedArray.getDimension(R.styleable.TabItem_TabTextSize, DimensionUtil.sp2px(context, 13f));
        mTabMessageEnable = typedArray.getBoolean(R.styleable.TabItem_TabMessageEnable,false);
        mRefreshIcon = typedArray.getDrawable(R.styleable.TabItem_TabRefreshIcon);
        typedArray.recycle();

        mNormalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNormalPaint.setColor(mTabNormalColor);
        mNormalPaint.setStyle(Paint.Style.STROKE);
        mNormalPaint.setTextSize(mTextSize);

        mMessagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMessagePaint.setColor(mSelectedColor);
        mMessagePaint.setStyle(Paint.Style.FILL);
        mMessagePaint.setTextSize(mTextSize-5);
        initDrawable();

    }

    private void initIconDrawable() {
        final int height = mIcon.getIntrinsicHeight();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            width = (int) (mIconWidth + getPaddingRight() + getPaddingLeft());
            height = (int) (mIconHeight + Math.abs(mNormalPaint.getFontMetrics().ascent - mNormalPaint.getFontMetrics().descent) + getPaddingBottom() + getPaddingTop());
        } else {
            width = widthSize;
            height = heightSize;
        }
        setMeasuredDimension(width, Math.max(height,MINIHEIGHT));
    }

    private void initDrawable(){
        final int h=mIcon.getIntrinsicHeight();
        final int w=mIcon.getIntrinsicWidth();
        if(h>mIconHeight){
            mIcon.setBounds(0,0,w,(int)mIconHeight);
        }
        if(w>mIconWidth){
            mIcon.setBounds(0,0, (int) mIconWidth,h);
        }
        if(mRefreshIcon!=null)
        mRefreshIcon.setBounds(mIcon.getBounds());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       mIcon.setBounds((int)(getPaddingLeft()+(getWidth()-mIconWidth)/2),getPaddingTop()+
               10,(int)(getPaddingLeft()+(getWidth()-mIconWidth)/2+mIconWidth),(int)(getPaddingTop()+10+mIconHeight));
        DrawableCompat.setTint(mIcon,mState?mSelectedColor:mTabNormalColor);
        if(!mStartRefresh) {
            mIcon.draw(canvas);
        }else {
            canvas.save();
            mRefreshIcon.setBounds(mIcon.getBounds());
            canvas.rotate(mRrefreshDegree,mRefreshIcon.getBounds().centerX(),mRefreshIcon.getBounds().centerY());
            mRefreshIcon.draw(canvas);
            canvas.restore();
        }
        Rect textBount=new Rect();
        mNormalPaint.setColor(mState?mSelectedColor:mTabNormalColor);
        mNormalPaint.getTextBounds(mTabName,0,mTabName.length(),textBount);
        canvas.drawText(mTabName,(getWidth()-Math.abs(textBount.left-textBount.right))/2,
                getHeight()-5-getPaddingBottom()-Math.abs(textBount.top-textBount.bottom)/4,mNormalPaint);

        //绘制消息红点
        if(mTabMessageEnable && !mStartRefresh)
        drawMessage(canvas);
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public boolean isTabMessageEnable() {
        return mTabMessageEnable;
    }

    private boolean mStartRefresh;
    private int mRrefreshDegree;
    @UiThread
    public boolean startRefresh(){
        if(!mStartRefresh&&!TextUtils.isEmpty(mMessage) && mTabMessageEnable){
            mStartRefresh=true;
            mRrefreshDegree=0;
            postDelayed(mRefreshRunnable,20);
            return true;
        }
        return false;
    }

    @UiThread
    public void stopRefresh(){
        mStartRefresh=false;
        mMessage=null;
        mRrefreshDegree=0;
        removeCallbacks(mRefreshRunnable);
        invalidate();
    }

    private Runnable mRefreshRunnable=new Runnable() {
        @Override
        public void run() {
            mRrefreshDegree+=6;
            if(mRrefreshDegree>=360){
                mRrefreshDegree=0;
            }
            invalidate();
            postDelayed(mRefreshRunnable,20);
        }
    };

    private String mMessage="99";

    private int mMessageBoundRadius=31;

    private void drawMessage(Canvas canvas) {
        if(!TextUtils.isEmpty(mMessage)){
            Rect bound=new Rect();
            Rect iconBound=mIcon.getBounds();
            mMessagePaint.getTextBounds(mMessage,0,mMessage.length(),bound);
            Paint.FontMetricsInt fontMetricsInt=mMessagePaint.getFontMetricsInt();
            float baseLineY=iconBound.top+mMessageBoundRadius+(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
            mMessagePaint.setColor(mSelectedColor);
            canvas.drawCircle(iconBound.right-mMessageBoundRadius,iconBound.top+mMessageBoundRadius,
                    mMessageBoundRadius,mMessagePaint);
            mMessagePaint.setColor(Color.WHITE);
            canvas.drawText(mMessage,iconBound.right-mMessageBoundRadius-bound.width()*1.0f/2,baseLineY,mMessagePaint);
        }
    }

}
