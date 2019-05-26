package com.whut.umrhamster.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.umrhamster.graduationproject.GridDecoration;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.SearchResultLiveAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.presenter.ILivePresenter;
import com.whut.umrhamster.graduationproject.presenter.LivePresenterCompl;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.ILiveView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultLiveFragment extends Fragment implements IInitWidgetView,ILiveView {

    private ILivePresenter livePresenter;
    private List<Live> liveList;
    private SearchResultLiveAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AdaptionUtil.setCustomDensity(getActivity(),getActivity().getApplication());
        View view = inflater.inflate(R.layout.fg_search_result_live,container,false);
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
        recyclerView = view.findViewById(R.id.fg_search_result_live_rv);
        liveList = new ArrayList<>();
        adapter = new SearchResultLiveAdapter(liveList,getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new GridDecoration(getActivity()));
        livePresenter = new LivePresenterCompl(this);
        livePresenter.doGetLiveByKeyword(getArguments().getString("keyword"));
    }

    @Override
    public void onAllLiveSuccess(List<Live> liveList) {
        this.liveList.addAll(liveList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAllLiveFail(int code) {

    }

    @Override
    public void onTypeLiveSuccess(List<Live> liveList) {

    }
}
