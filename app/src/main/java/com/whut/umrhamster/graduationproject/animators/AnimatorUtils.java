package com.whut.umrhamster.graduationproject.animators;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*
 *动画工具，控制动画工作方式
 */
public class AnimatorUtils {
    /*
     * 登录界面
     * 属性动画
     * Y轴平移
     * 输入：view-进行动画的控件，height-动画距离，正数向上，负数向下
     * 输出：无
     */
    public static void LoginAnimator(final View view, int height){ //问题：使用ObjectAnimator属性动画，只会改变控件位置，不会改变控件占用布局的位置，导致复数次动画不正常问题。
//        Log.d("AnimatorUtils",""+height);
        Log.d("ddd",""+view.getTop());
        final int top = view.getTop();
        final int bottom = view.getBottom();
        final float viewY = view.getY();
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"translationY",0,height);
//        objectAnimator.setDuration(300);
//        objectAnimator.start();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,height);
        valueAnimator.setDuration(300);  //动画持续时间
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
//                if (top+currentValue < 0){
//                    return;
//                }
//                view.layout(view.getLeft(),top+currentValue,view.getRight(),bottom+currentValue);
                view.setY(viewY+currentValue);
                view.requestLayout();
//                view.invalidate();
            }
        });
        valueAnimator.start();

    }

    public static void AnimatorScale(View view, float from, float to){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view,"scaleX",from,to);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view,"scaleY",from,to);
        animatorSet.setDuration(300);
        animatorSet.play(scaleX).with(scaleY);
        animatorSet.start();
    }
}
