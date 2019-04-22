package com.whut.umrhamster.graduationproject;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;

public class GridDecoration extends RecyclerView.ItemDecoration {
    private Context context;

    public GridDecoration(Context context){
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int space = AdaptionUtil.dp2px(context,12);
        if (parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1){
            outRect.top = space;
        }
        if (parent.getChildAdapterPosition(view)%2 == 0){
            outRect.left = space;
            outRect.right = space/2;
        }else {
            outRect.right = space;
            outRect.left = space/2;
        }
        outRect.bottom = space;
    }
}
