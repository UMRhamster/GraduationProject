package com.whut.umrhamster.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.whut.umrhamster.graduationproject.ClassifyActivity;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.ClassifyAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Classify;
import com.whut.umrhamster.graduationproject.presenter.ClassifyPresenter;
import com.whut.umrhamster.graduationproject.presenter.IClassifyPresenter;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.view.IClassifyView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.List;

public class ClassifyFragment extends Fragment implements IInitWidgetView,IClassifyView {
    private RecyclerView recyclerView;

    private List<Classify> classifyList;
    private ClassifyAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    //复用，可以抽出为基类
    private View mView;

    //sj
    private IClassifyPresenter classifyPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AdaptionUtil.setCustomDensity(getActivity(),getActivity().getApplication()); //今日头条适配方案
        if (mView == null){
            mView = inflater.inflate(R.layout.fg_classify,container,false);
            initView(mView);
            initEvent();
        }
//        View view = inflater.inflate(R.layout.fg_classify,container,false);
//        initView(view);
//        initEvent();
        return mView;
    }

    private void initData(){
        classifyPresenter = new ClassifyPresenter(this);
        classifyPresenter.doGetAllClassify();
    }

    @Override
    public void initView() {
    }

    @Override
    public void initEvent() {
        adapter.setOnItemClickListener(new ClassifyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Classify classify = classifyList.get(position);
                Intent intent = new Intent(getActivity(),ClassifyActivity.class);
                intent.putExtra("classify",classify);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                classifyPresenter.doGetAllClassify();
            }
        });
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.classify_rv_show);
        swipeRefreshLayout = view.findViewById(R.id.fg_classify_srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.themeColor);

        classifyList = new ArrayList<>();
        adapter = new ClassifyAdapter(classifyList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        recyclerView.setAdapter(adapter);
        initData();
    }

    @Override
    public void onClassifySuccess(List<Classify> classifyList) {
        swipeRefreshLayout.setRefreshing(false);
        this.classifyList.clear();
        this.classifyList.addAll(classifyList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClassifyFail(int code) {
        Toast.makeText(getActivity(),"获取分类异常 "+code,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mView != null){
            ((ViewGroup)mView.getParent()).removeView(mView);
        }
    }
}
