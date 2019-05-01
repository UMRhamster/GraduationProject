package com.whut.umrhamster.graduationproject;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.adapter.InfoFragmentPagerAdapter;
import com.whut.umrhamster.graduationproject.fragment.InfoCollectFragment;
import com.whut.umrhamster.graduationproject.fragment.InfoMainFragment;
import com.whut.umrhamster.graduationproject.interfaces.AppBarStateChangeListener;
import com.whut.umrhamster.graduationproject.interfaces.InfoGetListener;
import com.whut.umrhamster.graduationproject.model.bean.InfoGroupBean;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.presenter.ITimeKeepPresenter;
import com.whut.umrhamster.graduationproject.presenter.TimeKeepPresenter;
import com.whut.umrhamster.graduationproject.utils.other.IconUtil;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.CircleImageView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.ITimeKeepView;

import java.util.ArrayList;
import java.util.List;

public class StudentInfoActivity extends AppCompatActivity implements IInitWidgetView,InfoGetListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private TextView tvTopNickname;  //toolbar上的昵称
    private TextView tvNickname;     //昵称
    private TextView tvProgress;     //文字时长
    private ProgressBar progressBar; //进度条时长
    private ImageView ivLevel;       //等级
    private TextView tvWatch;        //关注
    private TextView tvData;         //账号资料
    private CircleImageView civIcon; //圆形头像
    private ImageView ivBack;        //返回键
    private AppBarLayout appBarLayout;

    private InfoFragmentPagerAdapter adapter;
    private List<Fragment> fragmentList;

//    private ITimeKeepPresenter timeKeepPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        initView();
        initEvent();
    }


    @Override
    public void initView() {
        viewPager = findViewById(R.id.ac_student_info_vp);
        tabLayout = findViewById(R.id.ac_student_info_tb);
        tvTopNickname = findViewById(R.id.ac_Student_info_tb_tv_nickname);
        tvNickname = findViewById(R.id.ac_student_info_tv_nickname);
        tvProgress = findViewById(R.id.ac_student_info_tv_progress);
        progressBar = findViewById(R.id.ac_student_info_pb_progress);
        ivLevel = findViewById(R.id.ac_student_info_tv_level);
        tvWatch = findViewById(R.id.ac_student_info_tv_watch);
        tvData = findViewById(R.id.ac_student_info_riv_tv_data);
        civIcon = findViewById(R.id.ac_student_info_civ_icon);
        ivBack = findViewById(R.id.ac_student_info_iv_back);
        appBarLayout = findViewById(R.id.app_bar);

        fragmentList = new ArrayList<>();
        InfoMainFragment infoMainFragment = new InfoMainFragment();
        infoMainFragment.setInfoGetListener(this);
        fragmentList.add(infoMainFragment);
        fragmentList.add(new InfoCollectFragment());
        adapter = new InfoFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        initData();
    }

    public void initData(){
        Student student = SPUtil.loadStudent(this);
        if (student != null){
//            加载学习时长数据
//            timeKeepPresenter.doGetTimeKeepById(student.getId());
            //加载用户信息数据
            Picasso.get().load(student.getAvatar()).placeholder(R.drawable.default_user_icon).into(civIcon); //加载头像
            tvNickname.setText(student.getNickname());
            tvTopNickname.setText(student.getNickname());
        }
    }

    @Override
    public void initEvent() {
        //返回键事件
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED){
                    tvTopNickname.setVisibility(View.INVISIBLE);
                }else if (state == State.COLLAPSED){
                    tvTopNickname.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void onTotalTime(int totalTime) {
        int[] level = TimeUtil.time2level(totalTime);
        ivLevel.setImageResource(IconUtil.getLevelResourceByInt(level[0]));
        tvProgress.setText(level[1]+"/999");
        progressBar.setMax(999);
        progressBar.setProgress(level[1]);
    }

    @Override
    public void onWatchNum(int num) {

    }
}
