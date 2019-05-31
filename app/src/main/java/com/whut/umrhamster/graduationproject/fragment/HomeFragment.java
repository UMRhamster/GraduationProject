package com.whut.umrhamster.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.presenter.ILivePresenter;
import com.whut.umrhamster.graduationproject.presenter.LivePresenterCompl;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.ILiveView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements IInitWidgetView,ILiveView {
    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView recyclerView;
    private HomeAdapter adapter;
//    private List<Video> videoList;
    private List<Live> liveList;

    private ILivePresenter livePresenter;

    private boolean status = false; //true表示刷新获取数据，false表示加载数据

    private int lastItemPosition = 0;
    private boolean isLoading = false;
    private int start = 0;

    private View mView;  //用于复用


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AdaptionUtil.setCustomDensity(getActivity(),getActivity().getApplication());
        if (mView == null){
            mView = inflater.inflate(R.layout.fg_home,container,false);
            initView(mView);
            initEvent();
        }
//        View view = inflater.inflate(R.layout.fg_home,container,false);
//        initView(view);
//        initEvent();
        return mView;
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                start = 0;
                livePresenter.doGetLiveLimit10(start);
                status = true;
                isLoading = false;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastItemPosition = manager.findLastVisibleItemPosition();
                if (newState != RecyclerView.SCROLL_STATE_IDLE && !isLoading && lastItemPosition == liveList.size()-1){
                    isLoading = true;
                    livePresenter.doGetLiveLimit10(start);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void initView(View view) {
        swipeRefreshLayout = view.findViewById(R.id.fg_home_srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.themeColor);
        recyclerView = view.findViewById(R.id.home_rv_show);

        liveList = new ArrayList<>();
        livePresenter = new LivePresenterCompl(this);

        adapter = new HomeAdapter(liveList,getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new GridDecoration(getActivity()));

        //初始化数据
        livePresenter.doGetLiveLimit10(start);  //start=0
    }

    @Override
    public void onAllLiveSuccess(List<Live> liveList) {
        if (status){
            swipeRefreshLayout.setRefreshing(false);
            this.liveList.clear();
            status = false;
            start = 0;
        }
        isLoading = false;
        if (liveList == null || liveList.size() == 0){
            isLoading = true;//无更多数据
            return;
        }
        this.liveList.addAll(liveList);
        start = start+liveList.size();
        adapter.notifyDataSetChanged();
//        if (liveList.size() == 0)
    }

    @Override
    public void onAllLiveFail(int code) {
        swipeRefreshLayout.setRefreshing(false);
        isLoading = false;
        status = false;
//        Toast.makeText(getActivity(),"失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTypeLiveSuccess(List<Live> liveList) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mView != null){
            ((ViewGroup)mView.getParent()).removeView(mView);
        }
    }
}
