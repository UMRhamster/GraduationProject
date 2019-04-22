package com.whut.umrhamster.graduationproject.comparator;

import com.whut.umrhamster.graduationproject.model.bean.Live;

import java.util.Comparator;

public class LiveComparator implements Comparator<Object> {
    @Override
    public int compare(Object o1, Object o2) {
        return (int) (((Live)o2).getCalendar().getTimeInMillis()-((Live)o1).getCalendar().getTimeInMillis());
    }
}
