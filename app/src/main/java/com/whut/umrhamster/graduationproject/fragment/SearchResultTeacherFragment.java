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
import com.whut.umrhamster.graduationproject.adapter.SearchResultTeacherAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.presenter.ITeacherPresenter;
import com.whut.umrhamster.graduationproject.presenter.TeacherPresenter;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.ITeacherView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultTeacherFragment extends Fragment implements IInitWidgetView,ITeacherView {
    private RecyclerView recyclerView;
    private SearchResultTeacherAdapter adapter;
    private List<Teacher> teacherList;

    private ITeacherPresenter teacherPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AdaptionUtil.setCustomDensity(getActivity(),getActivity().getApplication());
        View view = inflater.inflate(R.layout.fg_search_result_teacher,container,false);
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
        teacherPresenter = new TeacherPresenter(this);
        recyclerView = view.findViewById(R.id.fg_search_result_teacher_rv);
        teacherList = new ArrayList<>();
        adapter = new SearchResultTeacherAdapter(teacherList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        teacherPresenter.doGetTeacherByKeyword(getArguments().getString("keyword"));
    }

    @Override
    public void onGetKeywordTeacherSuccess(List<Teacher> teacherList) {
        this.teacherList.addAll(teacherList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onTeacherFail(int code) {

    }
}
