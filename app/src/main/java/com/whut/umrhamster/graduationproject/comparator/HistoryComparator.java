package com.whut.umrhamster.graduationproject.comparator;

import com.whut.umrhamster.graduationproject.model.bean.History;

import java.util.Comparator;

public class HistoryComparator implements Comparator<History> {
    @Override
    public int compare(History o1, History o2) {
        return o2.getLastTime().compareTo(o1.getLastTime());
    }
}
