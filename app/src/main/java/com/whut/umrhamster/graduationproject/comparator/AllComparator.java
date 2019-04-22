package com.whut.umrhamster.graduationproject.comparator;

import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.model.bean.Video;

import java.util.Comparator;

public class AllComparator implements Comparator<Object> {
    @Override
    public int compare(Object o1, Object o2) {
        Live live1 = null,live2 = null;
        Video video1 = null,video2 = null;
        if (o1 instanceof Live){
            live1 = (Live) o1;
        }else {
            video1 = (Video) o1;
        }
        if (o2 instanceof Live){
            live2 = (Live) o2;
        }else {
            video2 = (Video) o2;
        }
        if (live2 != null){
            if (live1 != null){
                return (int) (live2.getCalendar().getTimeInMillis()-live1.getCalendar().getTimeInMillis());
            }else {
                return (int) (live2.getCalendar().getTimeInMillis()-video1.getDate().getTimeInMillis());
            }
        }else {
            if (video1 != null){
                return (int) (video2.getDate().getTimeInMillis()-video1.getDate().getTimeInMillis());
            }else {
                return (int) (video2.getDate().getTimeInMillis()-live1.getCalendar().getTimeInMillis());
            }
        }

    }
}
