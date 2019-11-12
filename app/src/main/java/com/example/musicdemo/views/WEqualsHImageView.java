package com.example.musicdemo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WEqualsHImageView extends androidx.appcompat.widget.AppCompatImageView {

    public WEqualsHImageView(Context context) {
        super(context);
    }

    public WEqualsHImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WEqualsHImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        //模式view  例如 match_parent 、 warp_content
//        int mode = MeasureSpec.getMode(widthMeasureSpec);


    }
}
