package com.whut.umrhamster.graduationproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.whut.umrhamster.graduationproject.adapter.MainFragmentPagerAdapter;
import com.whut.umrhamster.graduationproject.fragment.ClassifyFragment;
import com.whut.umrhamster.graduationproject.fragment.ForeshowFragment;
import com.whut.umrhamster.graduationproject.fragment.HomeFragment;
import com.whut.umrhamster.graduationproject.fragment.WatchFragment;
import com.whut.umrhamster.graduationproject.view.CircleImageView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.VideoPlayerIJK;
import com.whut.umrhamster.graduationproject.view.VideoPlayerListener;
import com.whut.umrhamster.graduationproject.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.exo.IjkExoMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class MainActivity extends AppCompatActivity implements IInitWidgetView {
    private DrawerLayout drawerLayout;      //菜单栏控制
    private NavigationView navigationView;  //左边菜单栏
    private NoScrollViewPager viewPager;            //
    private TabLayout tabLayout;            //底部菜单栏
    private List<Fragment> fragmentList;    //页面源
    private View headerView;                //头部布局
    private CircleImageView civIcon;        //头部布局中的圆形头像
    private TextView textViewType;
    private TextView textViewNickname;
    private TextView textViewInfo;

    //顶部工具栏控件
    private Toolbar toolbar;
    private ImageView imageViewMenu;
    private CircleImageView civMenu;

    private int[] icons;                    //图标
    private String[] titles;                //标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    public View getTabView(int position){
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab,null);
        ImageView imageView = view.findViewById(R.id.custom_tab_iv);
        TextView textView = view.findViewById(R.id.custom_tab_tv);
        imageView.setImageResource(icons[position]);
        textView.setText(titles[position]);
//        textView.setTextColor(getResources().getColorStateList(R.color.tab_tv_selector));
        return view;

    }

    @Override
    public void initView() {
        drawerLayout = findViewById(R.id.main_dl);
        navigationView = findViewById(R.id.ac_main_nv);
        viewPager = findViewById(R.id.main_cl_vp_show);
        tabLayout = findViewById(R.id.main_cl_tl_bottom);

        headerView = navigationView.getHeaderView(0);
        civIcon = headerView.findViewById(R.id.navi_header_civ_icon);
        textViewNickname = headerView.findViewById(R.id.navi_header_tv_nickname);
        textViewInfo = headerView.findViewById(R.id.navi_header_tv_info);
        textViewType = headerView.findViewById(R.id.navi_header_tv_type);
        //顶部工具栏
        toolbar = findViewById(R.id.main_cl_tb_top);
        imageViewMenu = findViewById(R.id.main_tb_rl_iv_menu);
        civMenu = findViewById(R.id.main_tb_rl_civ_icon);
        //底部tab数据源
        icons = new int[]{R.drawable.custom_tab_iv_home,R.drawable.custom_tab_iv_classify,R.drawable.custom_tab_iv_video,R.drawable.custom_tab_iv_foreshow};
        titles = getResources().getStringArray(R.array.tab_titles);
        fragmentList = new ArrayList<>(4);
        fragmentList.add(new HomeFragment());
        fragmentList.add(new ClassifyFragment());
        fragmentList.add(new WatchFragment());
        fragmentList.add(new ForeshowFragment());

//        ColorStateList csl  = getResources().getColorStateList(R.color.custom_tab_tv_selector);
//        navigationView.setItemTextColor(csl);
        navigationView.getMenu().getItem(0).setChecked(true);
        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(),fragmentList,titles));
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i<titles.length;i++){
            tabLayout.getTabAt(i).setCustomView(getTabView(i));
        }
    }

    @Override
    public void initEvent() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Toast.makeText(MainActivity.this,"ceshi",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.history:
                        startActivity(new Intent(MainActivity.this,HistoryActivity.class));
                        break;
                }
                return true;
            }
        });
        civIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        textViewNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                Log.d("asdffff","fffff");
//                tab.getCustomView().findViewById(R.id.custom_tab_iv).setSelected(true);
//                tab.getCustomView().findViewById(R.id.custom_tab_tv).setSelected(true);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                tab.getCustomView().findViewById(R.id.custom_tab_iv).setSelected(false);
//                tab.getCustomView().findViewById(R.id.custom_tab_tv).setSelected(false);
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        //顶部工具栏
        imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        civMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

    }

    @Override
    public void initView(View view) {

    }

}
