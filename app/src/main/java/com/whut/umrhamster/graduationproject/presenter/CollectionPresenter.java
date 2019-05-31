package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.whut.umrhamster.graduationproject.model.bean.Collection;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.model.biz.CollectionBiz;
import com.whut.umrhamster.graduationproject.model.biz.ICollectionBiz;
import com.whut.umrhamster.graduationproject.view.ICollectionView;

import java.util.List;

public class CollectionPresenter implements ICollectionPresenter {
    private ICollectionView collectionView;
    private ICollectionBiz collectionBiz;
    private Handler handler;

    public CollectionPresenter(ICollectionView collectionView){
        this.collectionView = collectionView;
        collectionBiz = new CollectionBiz();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void doCollectionById(int studentId) {
        collectionBiz.getCollectionById(studentId, new ICollectionBiz.OnCollectionListener() {
            @Override
            public void onSuccess(final List<Collection> videoList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        collectionView.onCollectionSuccess(videoList);
                    }
                });
            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        collectionView.onCollectionFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doIsCollectionExist(int studentId, int videoId) {
        collectionBiz.isCollectionExist(studentId, videoId, new ICollectionBiz.OnCollectionListener() {
            @Override
            public void onSuccess(List<Collection> videoList) {

            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        collectionView.onCollectionFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doInsertCollection(int studentId, int videoId) {
        collectionBiz.insertCollection(studentId, videoId, new ICollectionBiz.OnCollectionListener() {
            @Override
            public void onSuccess(List<Collection> videoList) {

            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        collectionView.onCollectionFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doDeleteCollectionById(int id) {
        collectionBiz.deleteCollectionById(id, new ICollectionBiz.OnCollectionListener() {
            @Override
            public void onSuccess(List<Collection> videoList) {

            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        collectionView.onCollectionFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doDeleteCollectionBySaV(int studentId, int videoId) {
        collectionBiz.deleteCollectionBySaV(studentId, videoId, new ICollectionBiz.OnCollectionListener() {
            @Override
            public void onSuccess(List<Collection> videoList) {

            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        collectionView.onCollectionFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        collectionView = null;
        System.gc(); //并不会立即回收
    }
}
