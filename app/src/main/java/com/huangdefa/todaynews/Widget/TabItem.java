package com.huangdefa.todaynews.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.huangdefa.todaynews.R;
import com.huangdefa.todaynews.Utils.DimensionUtil;

/**
 * Created by huangdefa on 05/06/2017.
 */

public class TabItem extends View {
    private Paint mNormalPaint;
    private Paint mSelectedPaint;
    private Drawable mIcon;
    private float mIconHeight;
    private float mIconWidth;
    private boolean mState;
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
        mSelectedPaint.setColor(mSelectedColor);
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
        typedArray.recycle();

        mNormalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNormalPaint.setColor(mTabNormalColor);
        mNormalPaint.setStyle(Paint.Style.STROKE);
        mNormalPaint.setTextSize(mTextSize);

        mSelectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectedPaint.setColor(mSelectedColor);
        mSelectedPaint.setStyle(Paint.Style.STROKE);
        mSelectedPaint.setTextSize(mTextSize);
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       /* canvas.save();
        canvas.translate(getWidth()/2,getHeight()-getPaddingBottom()-10-
                Math.abs(mNormalPaint.getFontMetrics().ascent-mNormalPaint.getFontMetrics().descent)/2);
        mIcon.draw(canvas);
        canvas.restore();
        canvas.save();
        mNormalPaint.setColor(mState?mSelectedColor:mTabNormalColor);
        canvas.translate(getWidth()/2,getHeight()*2/3);
        canvas.drawText(mTabName,0,0,mNormalPaint);
        canvas.restore();
        canvas.drawRect(0,0,getWidth(),getHeight(),mSelectedPaint);*/
       mIcon.setBounds((int)(getPaddingLeft()+(getWidth()-mIconWidth)/2),getPaddingTop()+
               10,(int)(getPaddingLeft()+(getWidth()-mIconWidth)/2+mIconWidth),(int)(getPaddingTop()+10+mIconHeight));
        DrawableCompat.setTint(mIcon,mState?mSelectedColor:mTabNormalColor);
        mIcon.draw(canvas);
        Rect textBount=new Rect();
        mNormalPaint.setColor(mState?mSelectedColor:mTabNormalColor);
        mNormalPaint.getTextBounds(mTabName,0,mTabName.length(),textBount);
        canvas.drawText(mTabName,(getWidth()-Math.abs(textBount.left-textBount.right))/2,getHeight()-5-getPaddingBottom()-Math.abs(textBount.top-textBount.bottom)/4,mNormalPaint);
       // canvas.drawRect(0,0,getWidth(),getHeight(),mSelectedPaint);
        mSelectedPaint.setTextSize(DimensionUtil.sp2px(getContext(),12));
        mNormalPaint.getTextBounds("12",0,"12".length(),textBount);
       /* canvas.drawRect(mIcon.getBounds(),mSelectedPaint);
       float left=mIcon.getBounds().right-Math.abs(mIconWidth-mIcon.getBounds().width())/2-12;
        float top=mIcon.getBounds().top-Math.abs(mIconHeight-mIcon.getBounds().height())/2;
        RectF rect=new RectF(left,top,left+textBount.width(),
                top+textBount.height());
        canvas.drawRoundRect(rect,15,15,mSelectedPaint);
        */
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP){
            mState=!mState;
            invalidate();
        }
        return true;
    }*/
}
