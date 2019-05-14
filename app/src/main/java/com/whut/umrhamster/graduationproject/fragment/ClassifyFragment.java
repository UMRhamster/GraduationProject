package com.whut.umrhamster.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.whut.umrhamster.graduationproject.view.IClassifyView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.List;

public class ClassifyFragment extends Fragment implements IInitWidgetView,IClassifyView {
    private RecyclerView recyclerView;

    private List<Classify> classifyList;
    private ClassifyAdapter adapter;

    //sj
    private IClassifyPresenter classifyPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_classify,container,false);
        initView(view);
        initEvent();
        return view;
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
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.classify_rv_show);

        classifyList = new ArrayList<>();
//        classifyList.add(new Classify("应试教育",-1));
//        classifyList.add(new Classify("高等数学",R.drawable.icon_advanced_math));
//        classifyList.add(new Classify("线性代数",R.drawable.icon_xiandai));
//        classifyList.add(new Classify("大学物理",R.drawable.icon_classify_dxwl));
//        classifyList.add(new Classify("数据结构",R.drawable.icon_classify_sjjg));
//        classifyList.add(new Classify("算法导论",R.drawable.icon_classify_sfdl));
//        classifyList.add(new Classify("操作系统",R.drawable.icon_classify_czxt));
//        classifyList.add(new Classify("数值分析",R.drawable.icon_classify_szfx));
//        classifyList.add(new Classify("计算机网络",R.drawable.icon_classify_jsjwl));
//        classifyList.add(new Classify("素质教育",-1));
//        classifyList.add(new Classify("摄影基础",R.drawable.icon_classify_syjc));
//        classifyList.add(new Classify("书法绘画",R.drawable.icon_classify_sfhh));
//        classifyList.add(new Classify("个人理财",R.drawable.icon_classify_grlc));
//        classifyList.add(new Classify("心理学科",R.drawable.icon_classify_xlxk));
//        classifyList.add(new Classify("生活百科",R.drawable.icon_classify_shbk));
//        classifyList.add(new Classify("运动健康",R.drawable.icon_classify_ydjk));
//        classifyList.add(new Classify("职业教育",-1));
//        classifyList.add(new Classify("前端开发",R.drawable.icon_classify_qdkf));
//        classifyList.add(new Classify("移动开发",R.drawable.icon_classify_ydkf));
//        classifyList.add(new Classify("后端开发",R.drawable.icon_classify_hdkf));
//        classifyList.add(new Classify("人工智能",R.drawable.icon_classify_rgzn));
        adapter = new ClassifyAdapter(classifyList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        recyclerView.setAdapter(adapter);
        initData();
    }

    @Override
    public void onClassifySuccess(List<Classify> classifyList) {
        this.classifyList.clear();
        this.classifyList.addAll(classifyList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClassifyFail(int code) {
        Toast.makeText(getActivity(),"获取分类异常 "+code,Toast.LENGTH_SHORT).show();
    }
}
