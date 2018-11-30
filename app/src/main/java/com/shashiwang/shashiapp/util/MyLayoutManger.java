package com.shashiwang.shashiapp.util;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class MyLayoutManger extends RecyclerView.LayoutManager {

    SparseArray<Rect> allItems = new SparseArray<>();

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //detach全部子View，并放入缓存
        detachAndScrapAttachedViews(recycler);
        layoutChildren(recycler);
    }

    private void layoutChildren(RecyclerView.Recycler recycler){
        //每个子 view 的 top 位置
        int childTop = 0;
        for(int i = 0; i < getItemCount(); i++){
            //最终调用tryGetViewHolderForPositionByDeadline,要么使用已有的ViewHolder，要么直接创建一个
            View childView = recycler.getViewForPosition(i);
            addView(childView);
            //add后，计算子 view 的宽高
            measureChildWithMargins(childView, 0, 0);

            //宽和高附带了分割线的尺寸，这里没有使用分割线
            int width = getDecoratedMeasuredWidth(childView);
            int height = getDecoratedMeasuredHeight(childView);

            allItems.put(i, new Rect(0, childTop, width, childTop + height));
            //最终调用 view.layout 方法

            layoutDecorated(childView, 0, childTop, width, childTop + height);
            childTop += height;
        }

        detachAndScrapAttachedViews(recycler);
    }
}
