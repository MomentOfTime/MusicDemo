package com.example.musicdemo.views;

import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    public GridSpaceItemDecoration(int space,@NonNull RecyclerView parent) {
        mSpace = space;
        getRecyclerViewOffsets(parent);
    }

    /**
     * @param outRect
     * Item的边界  矩形
     * @param view  ItemView
     * @param parent  RecyclerView
     * @param state  RecyclerView的状态
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.left = mSpace;
//        //判断每一行第一个item
//        if (parent.getChildLayoutPosition(view) % 3 == 0){
//            outRect.left = 0;
//        }

    }
    //网格分割线
    private void getRecyclerViewOffsets(@NonNull RecyclerView parent) {
        //margin 正/负

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) parent.getLayoutParams();
        layoutParams.leftMargin = -mSpace;
        parent.setLayoutParams(layoutParams);
    }
}
