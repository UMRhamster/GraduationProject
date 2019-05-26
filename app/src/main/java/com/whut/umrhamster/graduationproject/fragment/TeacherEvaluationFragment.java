package com.whut.umrhamster.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.TeacherEvaluationAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Evaluation;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.presenter.EvaluationPresenter;
import com.whut.umrhamster.graduationproject.presenter.IEvaluationPresenter;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.view.IEvaluationView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.List;

public class TeacherEvaluationFragment extends Fragment implements IInitWidgetView,IEvaluationView {
    private RecyclerView recyclerView;
    private TeacherEvaluationAdapter adapter;
    private List<Evaluation> evaluationList;

    private Teacher teacher;
    private IEvaluationPresenter evaluationPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AdaptionUtil.setCustomDensity(getActivity(),getActivity().getApplication());
        View view = inflater.inflate(R.layout.fg_teacher_evaluation,container,false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.fg_teacher_evaluation_rv);
        evaluationList = new ArrayList<>();
        adapter = new TeacherEvaluationAdapter(evaluationList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initData();
    }

    private void initData() {
        teacher = getArguments().getParcelable("teacher");
        evaluationPresenter = new EvaluationPresenter(this);
        evaluationPresenter.doGetEvaluationByTeacherId(teacher.getId());
    }

    @Override
    public void onEvaluationByTeacherId(List<Evaluation> evaluationList) {
        this.evaluationList.addAll(evaluationList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onEvaluationFail(int code) {

    }
}
