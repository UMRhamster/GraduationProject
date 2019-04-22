package com.whut.umrhamster.graduationproject;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.whut.umrhamster.graduationproject.model.bean.Foreshow;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;

public class ForeshowDecoration extends RecyclerView.ItemDecoration {
    private Context context;

    public ForeshowDecoration(Context context){
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int space = AdaptionUtil.dp2px(context,8);
        if (parent.getChildAdapterPosition(view) == 0){
            outRect.top = space;
        }
        outRect.bottom = space;
        outRect.left = space;
        outRect.right = space;
    }
}
