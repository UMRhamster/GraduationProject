package com.whut.umrhamster.graduationproject.view;

import com.whut.umrhamster.graduationproject.model.bean.Collection;
import com.whut.umrhamster.graduationproject.model.bean.Video;

import java.util.List;

public interface ICollectionView {
    void onCollectionSuccess(List<Collection> videoList);
    void onCollectionFail(int code);
}
