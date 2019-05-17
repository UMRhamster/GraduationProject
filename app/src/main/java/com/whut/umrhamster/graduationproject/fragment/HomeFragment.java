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
import com.whut.umrhamster.graduationproject.adapter.HomeAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.presenter.ILivePresenter;
import com.whut.umrhamster.graduationproject.presenter.LivePresenterCompl;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.ILiveView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements IInitWidgetView,ILiveView {
    private RecyclerView recyclerView;
    private HomeAdapter adapter;
//    private List<Video> videoList;
    private List<Live> liveList;

    private ILivePresenter livePresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AdaptionUtil.setCustomDensity(getActivity(),getActivity().getApplication());
        View view = inflater.inflate(R.layout.fg_home,container,false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initEvent() {
        adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(),PlayerActivity.class);
                intent.putExtra("live",liveList.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {
                Toast.makeText(getActivity(),"长按item"+position,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.home_rv_show);

        liveList = new ArrayList<>();
        livePresenter = new LivePresenterCompl(this);

        adapter = new HomeAdapter(liveList,getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new GridDecoration(getActivity()));

        livePresenter.getAllLive();
    }

    @Override
    public void onAllLiveSuccess(List<Live> liveList) {
//        Toast.makeText(getActivity(),"成功",Toast.LENGTH_SHORT).show();
        this.liveList.addAll(liveList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAllLiveFail(int code) {
//        Toast.makeText(getActivity(),"失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTypeLiveSuccess(List<Live> liveList) {

    }
}
