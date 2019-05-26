package com.whut.umrhamster.graduationproject;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.jaredrummler.materialspinner.MaterialSpinner;
import com.whut.umrhamster.graduationproject.fragment.SearchHistoryFragment;
import com.whut.umrhamster.graduationproject.fragment.SearchResultFragment;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.TextDrawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements IInitWidgetView {
    private static final int HISTORY=1,RESULT=2;
    private EditText etSearch;
    private TextView tvCancel;
    private ImageView ivClear;

    private SearchHistoryFragment searchHistoryFragment;
    private SearchResultFragment searchResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initEvent();
    }

    @Override
    public void initView() {
        etSearch = findViewById(R.id.ac_search_et_search);
        tvCancel = findViewById(R.id.ac_search_tv_cancel);
        ivClear = findViewById(R.id.ac_search_iv_clear);


        setDefaultFragment();
    }

    private void setDefaultFragment(){
        if (searchHistoryFragment == null){
            searchHistoryFragment = new SearchHistoryFragment();
            searchHistoryFragment.setSearchItemClickListener(new SearchHistoryFragment.SearchItemClickListener() {
                @Override
                public void itemClick(String word){
                    etSearch.setText(word);
                    etSearch.setSelection(etSearch.getText().length());
                    saveHistory(word);
                    replaceFragment(RESULT);
                }
            });
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.ac_search_fl,searchHistoryFragment);
        transaction.commit();
    }

    public void replaceFragment(int frame){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (frame == HISTORY){
            if (searchHistoryFragment == null){
                searchHistoryFragment = new SearchHistoryFragment();
                searchHistoryFragment.setSearchItemClickListener(new SearchHistoryFragment.SearchItemClickListener() {
                    @Override
                    public void itemClick(String word){
                        etSearch.setText(word);
                        etSearch.setSelection(etSearch.getText().length());
                        saveHistory(word);
                        replaceFragment(RESULT);
                    }
                });
            }
            transaction.replace(R.id.ac_search_fl,searchHistoryFragment);
        }else if (frame == RESULT){
            if (searchResultFragment == null){
                searchResultFragment = new SearchResultFragment();
            }
            Bundle bundle = new Bundle();
            bundle.putString("keyword",etSearch.getText().toString());
            searchResultFragment.setArguments(bundle);
            transaction.replace(R.id.ac_search_fl,searchResultFragment);
        }
        transaction.commit();
    }

    @Override
    public void initEvent() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    String tmp = etSearch.getText().toString();
                    saveHistory(tmp);
//                    if (searchHistoryFragment != null){
//                        searchHistoryFragment.saveHistory(tmp);
//                    }
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    searchResultFragment = new SearchResultFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("keyword",tmp);
//                    searchResultFragment.setArguments(bundle);
//                    transaction.replace(R.id.ac_search_fl,searchResultFragment);
//                    transaction.commit();
                    replaceFragment(RESULT);
//                    searchResultFragment.doSearch(tmp);
//                    Toast.makeText(SearchActivity.this,tmp,Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()){
                    ivClear.setVisibility(View.INVISIBLE);
                }else {
                    ivClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
            }
        });
    }

    @Override
    public void initView(View view) {

    }

    public void saveHistory(String searchContent){
        if (searchContent.isEmpty()){
            return;
        }
        List<String> histories = new ArrayList<>(10);
        String history = SPUtil.loadHistory(SearchActivity.this);
        if (history == null){
            histories.add(searchContent);
            SPUtil.saveHistory(SearchActivity.this,searchContent);
        }else {
            String[] historyTemp = history.split("≡");
            histories.addAll(Arrays.asList(historyTemp));
            for (int i=0;i<histories.size();i++){
                if (histories.get(i).equals(searchContent)){
                    histories.remove(i);
                    break;
                }
            }
            histories.add(0,searchContent);
            if (histories.size() > 10){
                histories.remove(10);
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0;i<histories.size();i++){
                stringBuilder.append(histories.get(i)).append("≡");
            }
            if (stringBuilder.length()>0){
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
            }
            SPUtil.saveHistory(SearchActivity.this,stringBuilder.toString());
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getFragments().get(0) instanceof SearchResultFragment){
            replaceFragment(HISTORY);
//            Log.d("activity","onbackpress1");
        }else {
            super.onBackPressed();
//            Log.d("activity","onbackpress");
        }
    }
}
