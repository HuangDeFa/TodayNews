package com.huangdefa.todaynews.Widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.widget.TextView;

import com.huangdefa.todaynews.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

/**
 * Created by ken.huang on 9/20/2017.
 * 颜色改变的TextView
 */

public class ColorChangeTextView extends TextView {
    private float mTextSize;
    private int mTextChangeColor=Color.RED;
    private int mChangeColorDirection=DIRECTION_LTR;
    private ColorStateList mTextColor;
    private CharSequence mText;
    private Paint mPaint;


    private float mChangeRatios;

    @IntDef({DIRECTION_LTR,DIRECTION_RTL})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DirectionMode{}

    public static final int DIRECTION_RTL=1;
    public static final int DIRECTION_LTR=2;

    public ColorChangeTextView(Context context) {
        this(context,null);
    }

    public ColorChangeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorChangeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mTextSize = getTextSize();
        mTextColor = getTextColors();
        mText = getText();
        if(attrs!=null) {
            TypedArray typedArray = context.getResources().obtainAttributes(attrs, R.styleable.ColorChangeTextView);
            mTextChangeColor = typedArray.getColor(R.styleable.ColorChangeTextView_TextChangeColor, Color.RED);
            mChangeColorDirection = typedArray.getInt(R.styleable.ColorChangeTextView_TextChangDirection, DIRECTION_LTR);
            typedArray.recycle();
        }
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setColor(mTextColor.getDefaultColor());
    }
    @Override
    protected void onDraw(Canvas canvas) {
        mText = getText();
        mTextSize = getTextSize();
        mTextColor = getTextColors();
        mPaint.setTextSize(mTextSize);

        float textWidth = mPaint.measureText(mText, 0, mText.length());
        float drawX = getWidth()/2-textWidth/2;
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float baseLineY =getHeight()/2 +(fontMetrics.bottom-fontMetrics.top)/2 - fontMetrics.bottom;
        mPaint.setColor(mTextColor.getDefaultColor());
        canvas.drawText(mText,0,mText.length(),drawX,baseLineY,mPaint);
        canvas.save();
        float textChangeWidth=textWidth*mChangeRatios;
        if(mChangeColorDirection==DIRECTION_LTR){
            canvas.clipRect(drawX,fontMetrics.top+baseLineY,drawX+
                    textChangeWidth,baseLineY+fontMetrics.bottom);
        }else {
            canvas.clipRect(drawX+textWidth-textChangeWidth,fontMetrics.top+baseLineY,drawX+
                    textWidth,baseLineY+fontMetrics.bottom);
        }
        mPaint.setColor(mTextChangeColor);
        canvas.drawText(mText,0,mText.length(),drawX,baseLineY,mPaint);
        canvas.restore();
    }

    public int getChangeColorDirection() {
        return mChangeColorDirection;
    }

    public void setChangeColorDirection(@DirectionMode int changeColorDirection) {
        mChangeColorDirection = changeColorDirection;
    }

    @UiThread
    public void setChangeRatios(@FloatRange(from = 0,to = 1) float changeRatios) {
        mChangeRatios = changeRatios;
        invalidate();
    }

}
