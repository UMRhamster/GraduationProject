package com.whut.umrhamster.graduationproject.presenter;

public interface ICollectionPresenter extends BasePresenter{
    void doCollectionById(int studentId);
    void doGetCollectionLimit10(int start,int studentId);
    void doIsCollectionExist(int studentId,int videoId);
    void doInsertCollection(int studentId,int videoId);
    void doDeleteCollectionById(int id);
    void doDeleteCollectionBySaV(int studentId,int videoId);
}
