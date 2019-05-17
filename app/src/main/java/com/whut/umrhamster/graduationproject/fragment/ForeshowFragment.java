package com.whut.umrhamster.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.umrhamster.graduationproject.ForeshowActivity;
import com.whut.umrhamster.graduationproject.ForeshowDecoration;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.ForeshowAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Foreshow;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.presenter.ForeshowPresenter;
import com.whut.umrhamster.graduationproject.presenter.IForeshowPresenter;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.IForeshowView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.List;

public class ForeshowFragment extends Fragment implements IInitWidgetView,IForeshowView {
    private RecyclerView recyclerView;
    private List<Foreshow> foreshowList;
    private ForeshowAdapter adapter;


    private IForeshowPresenter foreshowPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_foreshow,container,false);
        initView(view);
        initEvent();
        return view;
    }

    public void initData(){
        foreshowPresenter = new ForeshowPresenter(this);
        foreshowPresenter.doGetForeshowLimit10(0);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        adapter.setOnItemClickListener(new ForeshowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(),ForeshowActivity.class);
                intent.putExtra("foreshow",foreshowList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.foreshow_rv_show);
        foreshowList = new ArrayList<>();

        adapter = new ForeshowAdapter(foreshowList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new ForeshowDecoration(getActivity()));
        recyclerView.setAdapter(adapter);
        initData();
    }

    @Override
    public void onGetForeshowSuccess(List<Foreshow> foreshowList) {
//        Log.d("test",""+foreshowList.size());
        this.foreshowList.addAll(foreshowList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetForeshowFail(int code) {
//        Log.d("tes","shibai"+code);
    }
}
