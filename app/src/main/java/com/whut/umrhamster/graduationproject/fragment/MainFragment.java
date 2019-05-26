package com.whut.umrhamster.graduationproject.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.HistoryActivity;
import com.whut.umrhamster.graduationproject.LoginActivity;
import com.whut.umrhamster.graduationproject.MainActivity;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.SearchActivity;
import com.whut.umrhamster.graduationproject.adapter.MainFragmentPagerAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.biz.IUserBiz;
import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.CircleImageView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements IInitWidgetView {
//    private DrawerLayout drawerLayout;      //菜单栏控制
//    private NavigationView navigationView;  //左边菜单栏
    private NoScrollViewPager viewPager;            //
    private TabLayout tabLayout;            //底部菜单栏
    private List<Fragment> fragmentList;    //页面源
//    private View headerView;                //头部布局
//    private CircleImageView civIcon;        //头部布局中的圆形头像
//    private TextView textViewNickname;
//    private TextView textViewInfo;

    //顶部工具栏控件
    private Toolbar toolbar;
    private ImageView imageViewMenu;
    private CircleImageView civMenu;
    private ImageView ivSearch;

    private int[] icons;                    //图标
    private String[] titles;                //标题

    //数据
    private Student student;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_main,container,false);
        initView(view);
        initEvent();
        return view;
    }

    public View getTabView(int position){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab,null);
        ImageView imageView = view.findViewById(R.id.custom_tab_iv);
        TextView textView = view.findViewById(R.id.custom_tab_tv);
        imageView.setImageResource(icons[position]);
        textView.setText(titles[position]);
        return view;
    }

    private void initData(){
        student = SPUtil.loadStudent(getActivity());
        if (student != null){
            Picasso.with(getActivity()).load(student.getAvatar()).placeholder(R.drawable.default_user_icon).error(R.drawable.default_user_icon).
                    config(Bitmap.Config.RGB_565).into(civMenu);
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

        //顶部工具栏
        imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openDrawer();
//                drawerLayout.openDrawer(Gravity.START);
            }
        });
        civMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openDrawer();
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });

    }

    @Override
    public void initView(View view) {
//        drawerLayout = view.findViewById(R.id.main_dl);
//        navigationView = view.findViewById(R.id.ac_main_nv);
        viewPager = view.findViewById(R.id.main_cl_vp_show);
        tabLayout = view.findViewById(R.id.main_cl_tl_bottom);

//        headerView = navigationView.getHeaderView(0);
//        civIcon = headerView.findViewById(R.id.navi_header_civ_icon);
//        textViewNickname = headerView.findViewById(R.id.navi_header_tv_nickname);
//        textViewInfo = headerView.findViewById(R.id.navi_header_tv_type);
        //顶部工具栏
        toolbar = view.findViewById(R.id.main_cl_tb_top);
        imageViewMenu = view.findViewById(R.id.main_tb_rl_iv_menu);
        civMenu = view.findViewById(R.id.main_tb_rl_civ_icon);
        ivSearch = view.findViewById(R.id.main_tb_rl_iv_search);
        //底部tab数据源
        icons = new int[]{R.drawable.custom_tab_iv_home,R.drawable.custom_tab_iv_classify,R.drawable.custom_tab_iv_video,R.drawable.custom_tab_iv_foreshow};
        titles = getResources().getStringArray(R.array.tab_titles);
        fragmentList = new ArrayList<>(4);
        fragmentList.add(new HomeFragment());
        fragmentList.add(new ClassifyFragment());
        fragmentList.add(new VideoFragment());
        fragmentList.add(new ForeshowFragment());

//        navigationView.getMenu().getItem(0).setChecked(true);
        viewPager.setAdapter(new MainFragmentPagerAdapter(getChildFragmentManager(),fragmentList,titles));
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i<titles.length;i++){
            tabLayout.getTabAt(i).setCustomView(getTabView(i));
        }
        initData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1){
            Log.d("Sa","ds");

        }
    }
}
