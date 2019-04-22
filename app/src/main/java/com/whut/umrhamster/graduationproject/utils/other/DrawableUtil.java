package com.whut.umrhamster.graduationproject.utils.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class DrawableUtil {
    public static Drawable text2Drawable(Context context, String str){
        Bitmap bitmap = Bitmap.createBitmap(500, 250, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setTextSize(65);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.GRAY);
        Paint.FontMetrics fm = paint.getFontMetrics();
        canvas.drawText(str, 0, 145 + fm.top - fm.ascent, paint);
        canvas.save();
        Drawable drawableright = new BitmapDrawable(bitmap);
        drawableright.setBounds(0, 0, drawableright.getMinimumWidth(),
                drawableright.getMinimumHeight());
        return drawableright;
    }
}
