package com.huangdefa.todaynews.Fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huangdefa.todaynews.MyApplication;
import com.huangdefa.todaynews.R;
import com.huangdefa.todaynews.Utils.DimensionUtil;
import com.huangdefa.todaynews.Utils.ViewUtil;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AboutFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Map<String,List<String>> mListMap=new ArrayMap<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initData() {
      mListMap.put("item1",null);
      mListMap.put("item2",new ArrayList<String>(){{add("我的关注");add("消息通知");}});
      mListMap.put("item3",new ArrayList<String>(){{add("头条商城:邀请好友获得200元现金奖励");add("暴走大事件:小孩子不要看暴漫");}});
      mListMap.put("item4",new ArrayList<String>(){{add("我要爆料");add("用户反馈");add("系统设置");}});
    }

    private void initView() {
        mRecyclerView = ViewUtil.findViewById(getView(), R.id.about_page_content);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new SimpleItemDecoration());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(new SimpleAdapter(mListMap));
    }

    private static class SimpleItemDecoration extends RecyclerView.ItemDecoration {
        private Paint mPaint;
        private Rect mBounds;

        SimpleItemDecoration() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setColor(Color.parseColor("#d9d9d9"));
            mBounds=new Rect();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
           c.save();
            final int right=parent.getWidth();
            final int childCount = parent.getChildCount();
            final int leftEdge=DimensionUtil.dp2px(parent.getContext(),10);
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                parent.getDecoratedBoundsWithMargins(child, mBounds);
                final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                int left=(bottom-child.getBottom())>10?0:leftEdge;
                c.drawRect(left,child.getBottom(),right,bottom,mPaint);
            }
            c.restore();
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            SimpleAdapter adapter = (SimpleAdapter) parent.getAdapter();
            if(position!=adapter.getItemCount()-1)
            outRect.set(0, 0, 0, adapter.getItemDecorationHeight(position));
        }
    }

    private static class SimpleAdapter extends RecyclerView.Adapter<SimpleVH>{
        private Map<String,List<String>> mListMap;
        SimpleAdapter(Map<String,List<String>> dataList) {
           this.mListMap=dataList;
        }
        @Override
        public SimpleVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView=null;
            switch (viewType){
                case 0:
                    itemView=LayoutInflater.from(parent.getContext()).
                            inflate(R.layout.about_page_head_item,parent,false);
                    break;
                case 1:
                    itemView=LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.about_page_item,parent,false);
                    break;
            }
            return new SimpleVH(itemView);
        }

        @Override
        public int getItemViewType(int position) {
            if(position==0)
                return 0;
          return 1;
        }

        public int getItemDecorationHeight(int position){
            int height=0;
            int sectionCount=getSectionCount();
            int realPosition=0;
            for(int i=0;i<sectionCount;i++){
                realPosition += getRowInSection(i);
                if(position<realPosition){
                    if(position==(realPosition-1)){
                        height =30;
                    }else {
                        height =2;
                    }
                    break;
                }
            }
            return height;
        }

        public int getSectionCount(){
            return mListMap.size();
        }

        public int getRowInSection(int sectionIndex){
            return ((ArrayMap<String,List<String>>)mListMap).valueAt(sectionIndex)!=null?
                    ((ArrayMap<String,List<String>>)mListMap).valueAt(sectionIndex).size():1;
        }

        private String getDataByPosition(int position){
            int sectionCount=getSectionCount();
            int realPosition=0;
            for(int i=0;i<sectionCount;i++){
                realPosition += getRowInSection(i);
                if(position<realPosition){
                   position=position-(realPosition-getRowInSection(i));
                   return ((ArrayMap<String,List<String>>)mListMap).valueAt(i).get(position);
                }
            }
            return null;
        }

        @Override
        public void onBindViewHolder(SimpleVH holder, int position) {
         if(position>0){
             String data = getDataByPosition(position);
            TextView tv1= holder.getView(R.id.about_page_item_left_text);
            TextView tv2= holder.getView(R.id.about_page_item_right_text);
             String[] split = data.split(":");
             if(split.length==2){
                 tv1.setText(split[0]);
                 tv2.setText(split[1]);
             }else {
                 tv1.setText(split[0]);
                 tv2.setText("");
             }
         }
        }

        @Override
        public int getItemCount() {
            int sectionCount=getSectionCount();
            int count=0;
            for(int i=0;i<sectionCount;i++){
                count+=getRowInSection(i);
            }
            return count;
        }
    }
    private static class SimpleVH extends RecyclerView.ViewHolder{

        private SparseArray<View> mSparseArray;
        public SimpleVH(View itemView) {
            super(itemView);
            mSparseArray=new SparseArray<>();
        }

        public <V extends View> V getView(@IdRes int resId){
            View view=mSparseArray.get(resId);
            if(view==null){
                view=itemView.findViewById(resId);
                mSparseArray.put(resId,view);
            }
            return (V) view;
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
