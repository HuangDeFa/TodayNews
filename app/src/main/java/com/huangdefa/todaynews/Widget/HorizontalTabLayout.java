package com.huangdefa.todaynews.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.huangdefa.todaynews.Adapter.HorizontalScrollBaseAdapter;
import com.huangdefa.todaynews.R;

/**
 * Created by huangdefa on 19/09/2017.
 * Version 1.0
 * 水平滚动的TabLayout
 */

public class HorizontalTabLayout extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
    private LinearLayout mItemsContainer;
    private HorizontalScrollBaseAdapter mAdapter;
    private int mVisualItemCount;
    private int mItemWidth;
    private ViewPager mViewPager;

    public HorizontalTabLayout(Context context) {
        this(context,null);
    }

    public HorizontalTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mItemsContainer=new LinearLayout(context);
        addView(mItemsContainer);
        TypedArray typedArray = context.getResources().obtainAttributes(attrs, R.styleable.HorizontalTabLayout);
        //设置一屏可见的item个数
        mVisualItemCount = typedArray.getInt(R.styleable.HorizontalTabLayout_VisualItemCount,0);
        typedArray.recycle();
    }

    public void setAdapter(HorizontalScrollBaseAdapter adapter){
        if(adapter==null){
            throw new NullPointerException("adapter can not be null!!");
        }
       this.mAdapter=adapter;
       mItemsContainer.removeAllViews();
        final int count=mAdapter.getCount();
        for(int i=0;i<count;i++){
            mItemsContainer.addView(mAdapter.getView(i,mItemsContainer));
        }
    }

    public void setAdapter(HorizontalScrollBaseAdapter adapter, ViewPager viewPager){
        setAdapter(adapter);
        this.mViewPager=viewPager;
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            mItemWidth=getItemWidth();
            int childCount=mAdapter.getCount();
            for(int i=0;i<childCount;i++){
                mItemsContainer.getChildAt(i).getLayoutParams().width= mItemWidth;
            }
        }
    }

    /**
     *  获取Item的宽度
     *  如果VisualItem的个数不指定则以宽度最大的为准，否则按照个数平分
     * @return
     */
    private int getItemWidth(){
        int itemWidth=0;
        if(mVisualItemCount==0){
            int childCount=mAdapter.getCount();
            for(int i=0;i<childCount;i++){
                int measuredWidth = mItemsContainer.getChildAt(i).getMeasuredWidth();
                itemWidth=Math.max(itemWidth,measuredWidth);
            }
            //算出来的宽度不够一屏，强制一屏
            if(itemWidth*childCount<getWidth()){
                itemWidth=getWidth()/childCount;
            }
        }else {
            itemWidth=getWidth()/mVisualItemCount;
        }
        return itemWidth;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
