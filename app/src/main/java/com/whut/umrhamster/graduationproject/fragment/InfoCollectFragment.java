package com.whut.umrhamster.graduationproject.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.VideoPlayerActivity;
import com.whut.umrhamster.graduationproject.adapter.CollectAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Collection;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.presenter.CollectionPresenter;
import com.whut.umrhamster.graduationproject.presenter.ICollectionPresenter;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.ICollectionView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.List;

public class InfoCollectFragment extends Fragment implements IInitWidgetView,ICollectionView {
    private RecyclerView recyclerView;
    private CollectAdapter adapter;
    private List<Collection> videoList;

    //sj
    private ICollectionPresenter collectionPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_info_collect,container,false);
        initView(view);
        initEvent();
        return view;
    }

    public void initData(){
        collectionPresenter = new CollectionPresenter(this);
        Student student = SPUtil.loadStudent(getActivity());
        if (student != null){
            collectionPresenter.doCollectionById(student.getId());
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        adapter.setOnItemClickListener(new CollectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(),VideoPlayerActivity.class);
                intent.putExtra("video",videoList.get(position).getVideo());
                startActivity(intent);
            }

            @Override
            public void onMenuClick(int position) {
//                Toast.makeText(getActivity(),"testmenu",Toast.LENGTH_SHORT).show();
                showBottomDialog(position);
            }
        });
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.fg_info_collection_rv);
        videoList = new ArrayList<>();
        adapter = new CollectAdapter(videoList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initData();
    }

    public void showBottomDialog(final int position){
        Dialog dialog = new Dialog(getActivity(),R.style.CustomDialog);
        View view = View.inflate(getActivity(),R.layout.dialog_collection_remove,null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.dialog_collection_remove_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"dianjiquxiao"+position,Toast.LENGTH_SHORT).show();
            }
        });

        dialog.findViewById(R.id.dialog_collection_tv_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"dianjishanchu"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCollectionSuccess(List<Collection> videoList) {
        Log.d("dsa",""+videoList.size());
        this.videoList.addAll(videoList);
        adapter.notifyDataSetChanged();
//        for (int i=0;i<videoList.size();i++){
//            Video video = videoList.get(i);
//            Log.d("test",""+video.getCover());
//            Log.d("test",""+video.getPath());
//            Log.d("test",""+video.getTitle());
//            Log.d("test",""+video.getNofw());
//            Log.d("test",""+video.getTotalTime());
//        }
    }

    @Override
    public void onCollectionFail(int code) {
        Toast.makeText(getActivity(),""+code,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        collectionPresenter.onDestroy();
        collectionPresenter = null;
    }
}
