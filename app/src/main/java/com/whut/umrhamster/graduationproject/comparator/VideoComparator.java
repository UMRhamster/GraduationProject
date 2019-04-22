package com.whut.umrhamster.graduationproject.comparator;

import com.whut.umrhamster.graduationproject.model.bean.Video;

import java.util.Comparator;

public class VideoComparator implements Comparator<Object> {
    @Override
    public int compare(Object o1, Object o2) {
        return (int) (((Video)o2).getDate().getTimeInMillis()-((Video)o1).getDate().getTimeInMillis());
    }
}
