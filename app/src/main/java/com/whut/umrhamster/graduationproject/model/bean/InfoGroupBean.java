package com.whut.umrhamster.graduationproject.model.bean;

import com.hgdendi.expandablerecycleradapter.BaseExpandableRecyclerViewAdapter.BaseGroupBean;
import com.whut.umrhamster.graduationproject.model.bean.InfoChildBean;

import java.util.List;

public class InfoGroupBean implements BaseGroupBean<InfoChildBean> {
    private int id;
    private int totalTime;
    private List<InfoChildBean> childList;
    private String name;

    public InfoGroupBean(List<InfoChildBean> childList, String name){
        this.childList = childList;
        this.name = name;
    }
    @Override
    public int getChildCount() {
        return childList.size();
    }

    @Override
    public InfoChildBean getChildAt(int childIndex) {
        return childList.size() <= childIndex ? null : childList.get(childIndex);
    }

    @Override
    public boolean isExpandable() {
        return getChildCount() > 0;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public void setName(String name) {
        this.name = name;
    }
}
