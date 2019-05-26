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
import com.whut.umrhamster.graduationproject.SearchActivity;
import com.whut.umrhamster.graduationproject.adapter.SearchHistoryAdapter;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchHistoryFragment extends Fragment implements IInitWidgetView {
    private RecyclerView recyclerView;
    private List<String> histories;
    private SearchHistoryAdapter adapter;

    private SearchItemClickListener searchItemClickListener;

    public void setSearchItemClickListener(SearchItemClickListener searchItemClickListener) {
        this.searchItemClickListener = searchItemClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AdaptionUtil.setCustomDensity(getActivity(),getActivity().getApplication());
        View view = inflater.inflate(R.layout.fg_search_history,container,false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        adapter.setOnItemClickListener(new SearchHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //点击直接搜索
                if (position == histories.size()){
                    histories.clear();
                    SPUtil.clearHistory(getActivity());
                    adapter.notifyDataSetChanged();
                }else {
                    searchItemClickListener.itemClick(histories.get(position));
//                    saveHistory(histories.get(position));
                    //activity 处理搜索
                }
            }

            @Override
            public void onItemDeleteClick(int position) {
                //删除所点击的历史记录
                deleteSingleHistory(position);
            }
        });
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.fg_search_history_rv);
        histories = new ArrayList<>();
        adapter = new SearchHistoryAdapter(histories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        initData();

    }

    private void initData() {
        getHistory();
    }

    private void getHistory(){
        String tempHistory = SPUtil.loadHistory(getActivity());
        if (tempHistory != null){
            String[] historyArray = tempHistory.split("≡");
            histories.addAll(Arrays.asList(historyArray));
            adapter.notifyDataSetChanged();
        }
    }

    private void deleteSingleHistory(int position){
        StringBuilder stringBuilder = new StringBuilder();
        histories.remove(position);
        for (int i=0;i<histories.size();i++){
            stringBuilder.append(histories.get(i)).append("≡");
        }
        if (stringBuilder.length() > 0){
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
        SPUtil.saveHistory(getActivity(),stringBuilder.toString());
        adapter.notifyDataSetChanged();
    }

//    public void saveHistory(String searchContent){
//        if (searchContent.isEmpty()){
//            return;
//        }
//        String history = SPUtil.loadHistory(getActivity());
//        if (history == null){
//            histories.add(searchContent);
//            SPUtil.saveHistory(getActivity(),searchContent);
//            adapter.notifyDataSetChanged();
//            return;
//        }else {
//            for (int i=0;i<histories.size();i++){
//                if (histories.get(i).equals(searchContent)){
//                    histories.remove(i);
//                    break;
//                }
//            }
//            histories.add(0,searchContent);
//            if (histories.size() > 10){
//                histories.remove(10);
//            }
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int i=0;i<histories.size();i++){
//                stringBuilder.append(histories.get(i)).append("≡");
//            }
//            if (stringBuilder.length()>0){
//                stringBuilder.deleteCharAt(stringBuilder.length()-1);
//            }
//            SPUtil.saveHistory(getActivity(),stringBuilder.toString());
//            adapter.notifyDataSetChanged();
//        }
//    }

    public interface SearchItemClickListener{
        void itemClick(String word);
    }
}
