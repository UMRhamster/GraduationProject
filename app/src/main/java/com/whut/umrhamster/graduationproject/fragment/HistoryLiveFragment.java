package com.whut.umrhamster.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.HistoryAdapter;
import com.whut.umrhamster.graduationproject.comparator.HistoryComparator;
import com.whut.umrhamster.graduationproject.interfaces.TextEditListener;
import com.whut.umrhamster.graduationproject.model.bean.History;
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.presenter.HistoryPresenter;
import com.whut.umrhamster.graduationproject.presenter.IHistoryPresenter;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.IHistoryView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class HistoryLiveFragment extends Fragment implements IInitWidgetView,IHistoryView {
    private SwipeRefreshLayout sRefresh;

    private List<History> historyList;
    private TreeSet<Integer> checkSet;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;

    private IHistoryPresenter historyPresenter;
    private int start;
    private int timeDecor;

    private int scrollState = RecyclerView.SCROLL_STATE_IDLE;
    private int lastItemPosition = -1;
    private boolean isLoading = false;

    private TextEditListener textEditListener;

    //复用
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fg_history_live,container,false);
            initView(rootView);
            initEvent();
        }
        return rootView;
    }

    public void setTextEditListener(TextEditListener textEditListener){
        this.textEditListener = textEditListener;
    }

    public void edit(){
        adapter.inEditing();
    }

    public void cancelEdit(){
        adapter.outEditing();
    }

    public void chooseAll(){
        adapter.chooseAll();
    }
    public void cancelAll(){
        adapter.cancelAll();
    }

    public void delete(){
        HashSet<Integer> ids = new HashSet<>();
        for (Integer i : checkSet){
            ids.add(historyList.get(i).getId());
//            Log.d("bbbbbbbbbb",historyList.get(i).getId()+"");
        }
        //4-根据ids删除所有记录，无需studentId
        Student student = SPUtil.loadStudent(getActivity());
        if (student != null){
            historyPresenter.deleteHistory(student.getId(),0,ids);
        }
    }

    public void deleteAll(){
        Student student = SPUtil.loadStudent(getActivity());
        if (student != null){
            historyPresenter.deleteHistory(student.getId(),2,new HashSet<Integer>()); //hashset此处无任何作用
        }
    }

    public boolean isAllSelected(){
        return adapter.isAllSelected();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        adapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(),"dianji  "+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int position) {
                Toast.makeText(getActivity(),"changan  "+position,Toast.LENGTH_SHORT).show();
            }
        });
        //下拉刷新事件
        sRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Student student = SPUtil.loadStudent(getActivity());
                if (student != null){
                    start = 0;timeDecor = 0;
                    adapter.outEditing();
                    historyList.clear();
                    historyPresenter.doHistory(start,student.getId(),2);
                    textEditListener.onTextEdit("编辑");
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollState = newState;
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastItemPosition = manager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (scrollState != RecyclerView.SCROLL_STATE_IDLE && !isLoading && lastItemPosition == historyList.size()-1 && dy > 0){
                    isLoading = true;
                    Student student = SPUtil.loadStudent(getActivity());
                    if (student != null){
                        historyPresenter.doHistory(start,student.getId(),2);
                    }else {
                        isLoading = false;
                    }
                }
            }
        });
    }

    @Override
    public void initView(View view) {
        historyList = new ArrayList<>();
        checkSet = new TreeSet<>();
        recyclerView = view.findViewById(R.id.history_live_rv);
        sRefresh = view.findViewById(R.id.history_live_srl);
        adapter = new HistoryAdapter(historyList,checkSet,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initData();
        adapter.outEditing();
    }

    private void initData(){
        start = 0;timeDecor = 0;
        historyPresenter = new HistoryPresenter(this);
        Student student = SPUtil.loadStudent(getActivity());
        if (student != null){
            historyPresenter.doHistory(start,student.getId(),2);
        }
    }

    @Override
    public void onHistorySuccess(List<History> historyList) {
        start+=historyList.size();
        Collections.sort(historyList,new HistoryComparator());
        Calendar todayC = Calendar.getInstance();
        todayC.set(Calendar.HOUR_OF_DAY,0);
        todayC.set(Calendar.MINUTE,0);
        todayC.set(Calendar.SECOND,0);

        Calendar yesterdayC = Calendar.getInstance();
        yesterdayC.set(Calendar.DAY_OF_MONTH,yesterdayC.get(Calendar.DAY_OF_MONTH)-1);
        yesterdayC.set(Calendar.HOUR_OF_DAY,0);
        yesterdayC.set(Calendar.MINUTE,0);
        yesterdayC.set(Calendar.SECOND,0);
        for (History history : historyList){
            if (history.getLastTime().after(todayC.getTime())){
                if (timeDecor == 0) {
                    this.historyList.add(new History("今天",-1));
                    timeDecor++;
                }
            }else if (history.getLastTime().after(yesterdayC.getTime())){
                if (timeDecor == 0 || timeDecor == 1){
                    this.historyList.add(new History("昨天",-1));
                    timeDecor+=2;
                }
            }else {
                if (timeDecor == 0 || timeDecor == 1 || timeDecor == 2 || timeDecor == 3){
                    this.historyList.add(new History("更早",-1));
                    timeDecor+=4;
                }
            }
            this.historyList.add(history);
        }
        adapter.notifyDataSetChanged();
        sRefresh.setRefreshing(false);
    }

    @Override
    public void onHistoryFail(int code) {

    }

    @Override
    public void existResult(int code) {

    }

    @Override
    public void insertResult(int code) {

    }

    @Override
    public void updateResult(int code) {

    }

    @Override
    public void deleteResult(int code) {
        if (code == 2081){ //2081-删除成功
            //此处带修改
            for (int i=historyList.size()-1;i>=0;i--){
                if (historyList.get(i).getTotalTime() == -1){ //删除时间标志
                    historyList.remove(i);
                    i--;
                }
                if (checkSet.contains(i)){
                    historyList.remove(i);
                    checkSet.remove(i);
                }
            }
            timeDecor = 0;
            checkSet.clear();
            Log.d("size",historyList.size()+"");
            //删除之后 时间标志需要重新显示
            Calendar todayC = Calendar.getInstance();
            todayC.set(Calendar.HOUR_OF_DAY,0);
            todayC.set(Calendar.MINUTE,0);
            todayC.set(Calendar.SECOND,0);
            Calendar yesterdayC = Calendar.getInstance();
            yesterdayC.set(Calendar.DAY_OF_MONTH,yesterdayC.get(Calendar.DAY_OF_MONTH)-1);
            yesterdayC.set(Calendar.HOUR_OF_DAY,0);
            yesterdayC.set(Calendar.MINUTE,0);
            yesterdayC.set(Calendar.SECOND,0);
            for (int i=0;i<historyList.size();i++){
                History history = historyList.get(i);
                if (history.getLastTime().after(todayC.getTime())){
                    if (timeDecor == 0) {
                        this.historyList.add(i,new History("今天",-1));
                        i++;
                        timeDecor++;
                    }
                }else if (history.getLastTime().after(yesterdayC.getTime())){
                    if (timeDecor == 0 || timeDecor == 1){
                        this.historyList.add(i,new History("昨天",-1));
                        i++;
                        timeDecor+=2;
                    }
                }else {
                    if (timeDecor == 0 || timeDecor == 1 || timeDecor == 2 || timeDecor == 3){
                        this.historyList.add(i,new History("更早",-1));
                        i++;
                        timeDecor+=4;
                    }
                }
//            this.historyList.add(history);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rootView != null){
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
    }
}
