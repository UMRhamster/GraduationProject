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

import com.whut.umrhamster.graduationproject.GridDecoration;
import com.whut.umrhamster.graduationproject.PlayerActivity;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.ClassifyLiveAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Classify;
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.presenter.ILivePresenter;
import com.whut.umrhamster.graduationproject.presenter.LivePresenterCompl;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.ILiveView;

import java.util.ArrayList;
import java.util.List;

public class ClassifyLiveFragment extends Fragment implements IInitWidgetView,ILiveView {
    private RecyclerView recyclerView;
    private ClassifyLiveAdapter adapter;
    private List<Live> liveList;

    private ILivePresenter livePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_classify_live,container,false);
        initView(view);
        initEvent();
        return view;
    }

    private void initData(){
        Classify classify = getArguments().getParcelable("classify");
        livePresenter = new LivePresenterCompl(this);
        livePresenter.doGetTypeLive(classify.getId());
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        adapter.setOnItemClickListener(new ClassifyLiveAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(),PlayerActivity.class);
                intent.putExtra("live",liveList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.fg_classify_live_rv);
        liveList = new ArrayList<>();
        adapter = new ClassifyLiveAdapter(liveList);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new GridDecoration(getActivity()));
        initData();
    }

    @Override
    public void onAllLiveSuccess(List<Live> liveList) {

    }

    @Override
    public void onAllLiveFail(int code) {
        Toast.makeText(getActivity(),"获取分类直播异常 "+code,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTypeLiveSuccess(List<Live> liveList) {
        this.liveList.clear();
        this.liveList.addAll(liveList);
        adapter.notifyDataSetChanged();
    }
}
