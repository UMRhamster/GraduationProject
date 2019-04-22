package com.whut.umrhamster.graduationproject;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class PositionControl {
    class Position{
        int left;
        int top;
        int right;
        int bottom;
        float y;
        float scaleX;
        float scaleY;
        public Position(int left, int top, int right, int bottom, float y, float scaleX, float scaleY){
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.y = y;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
        }
    }
    private ArrayList<View> views;
    private ArrayList<Position> positions;
    public PositionControl(){
        views = new ArrayList<>();
        positions = new ArrayList<>();
    }

    public void addView(View view){
        views.add(view);
        positions.add(new Position(view.getLeft(),view.getTop(),view.getRight(),view.getBottom(),view.getY(),view.getScaleX(),view.getScaleY()));
        Log.d("ADDposition",""+positions.get(positions.size()-1).left+"   "+positions.get(positions.size()-1).bottom);
    }

    public void viewsPositionInit(){
        int viewNumber = views.size();
        for (int i = 0; i < viewNumber; i++){
            Position position = positions.get(i);
            View view = views.get(i);
//            view.layout(position.left,position.top,position.right,position.bottom);
            view.setY(position.y);
            view.setScaleX(position.scaleX);
            view.setScaleY(position.scaleY);
        }
    }

    public int size(){
        return views.size();
    }

}
