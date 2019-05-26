package com.whut.umrhamster.graduationproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.adapter.TeacherInfoFragmentPagerAdapter;
import com.whut.umrhamster.graduationproject.adapter.TeacherVideoAdapter;
import com.whut.umrhamster.graduationproject.fragment.TeacherEvaluationFragment;
import com.whut.umrhamster.graduationproject.fragment.TeacherVideoFragment;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.model.bean.Watch;
import com.whut.umrhamster.graduationproject.presenter.IVideoPresenter;
import com.whut.umrhamster.graduationproject.presenter.IWatchPresenter;
import com.whut.umrhamster.graduationproject.presenter.VideoPresenter;
import com.whut.umrhamster.graduationproject.presenter.WatchPresenter;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.CircleImageView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.IVideoView;
import com.whut.umrhamster.graduationproject.view.IWatchView;

import java.util.ArrayList;
import java.util.List;

public class TeacherInfoActivity extends AppCompatActivity implements IInitWidgetView,IVideoView,IWatchView {
    private TextView tvTopNickname;  //toolbar上的昵称
    private TextView tvNickname;     //昵称
    private TextView tvStudents;     //学员
    private TextView tvWatch;         //关注
    private TextView tvBrief;         //简介
    private CircleImageView civIcon; //圆形头像
    private ImageView ivBack;        //返回键

//    private RecyclerView recyclerView;
//    private List<Video> videoList;
//    private TeacherVideoAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TeacherInfoFragmentPagerAdapter adapter;
    private List<Fragment> fragmentList;

    private Teacher teacher;
    Student student;
//    private IVideoPresenter videoPresenter;
    private IWatchPresenter watchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdaptionUtil.setCustomDensity(TeacherInfoActivity.this,getApplication());
        setContentView(R.layout.activity_teacher_info);
        initView();
        initEvent();
    }

    public void initData(){
//        videoPresenter = new VideoPresenter(this);
        watchPresenter = new WatchPresenter(this);

//        videoPresenter.doGetVideoByTeacher(teacher.getId());
        student = SPUtil.loadStudent(TeacherInfoActivity.this);
        if (student != null){
            watchPresenter.isWatchExist(student.getId(),teacher.getId());
        }
        watchPresenter.doGetNumOfWatch(2,teacher.getId());
    }

    @Override
    public void initView() {
        teacher = getIntent().getParcelableExtra("teacher");
        tvTopNickname = findViewById(R.id.ac_teacher_info_tb_tv_nickname);
        tvNickname = findViewById(R.id.ac_teacher_info_tv_nickname);
        tvStudents = findViewById(R.id.ac_teacher_info_tv_watch);
        tvWatch = findViewById(R.id.ac_teacher_info_riv_tv_data);
        tvBrief = findViewById(R.id.ac_teacher_info_tv_brief);
        civIcon = findViewById(R.id.ac_teacher_info_civ_icon);
        ivBack = findViewById(R.id.ac_teacher_info_iv_back);
        //
        viewPager = findViewById(R.id.ac_teacher_info_vp);
        tabLayout = findViewById(R.id.ac_teacher_info_tb);
        fragmentList = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putParcelable("teacher",teacher);
        TeacherVideoFragment teacherVideoFragment = new TeacherVideoFragment();
        teacherVideoFragment.setArguments(bundle);
        TeacherEvaluationFragment evaluationFragment = new TeacherEvaluationFragment();
        evaluationFragment.setArguments(bundle);
        fragmentList.add(teacherVideoFragment);
        fragmentList.add(evaluationFragment);
        adapter = new TeacherInfoFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

//        recyclerView = findViewById(R.id.ac_teacher_info_rv);
//        videoList = new ArrayList<>();
//        adapter = new TeacherVideoAdapter(videoList,TeacherInfoActivity.this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(TeacherInfoActivity.this));
//        recyclerView.setAdapter(adapter);
        //
        Picasso.with(TeacherInfoActivity.this).load(teacher.getAvatar()).config(Bitmap.Config.RGB_565).into(civIcon);
        tvTopNickname.setText(teacher.getNickname());
        tvNickname.setText(teacher.getNickname());
        tvBrief.setText(teacher.getBrief());
        initData();

    }

    @Override
    public void initEvent() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvWatch.getText().toString().equals("+ 关注")){
                    if (student != null){
                        watchPresenter.doAddWatch(student.getId(),teacher.getId());
                    }else {
                        Toast.makeText(TeacherInfoActivity.this,"登录之后可关注教师",Toast.LENGTH_SHORT).show();
                    }
                    //点击进行关注
                }else {
                    if (student != null){
                        watchPresenter.doDeleteWatchBySaT(student.getId(),teacher.getId());
                    }
                }
            }
        });

//        adapter.setOnItemClickListener(new TeacherVideoAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(TeacherInfoActivity.this,VideoPlayerActivity.class);
//                intent.putExtra("video",videoList.get(position));
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void onVideoSuccess(List<Video> videoList) {

    }

    @Override
    public void onVideoFail(int code) {

    }

    @Override
    public void onVideoTypeSuccess(List<Video> videoList) {

    }

    @Override
    public void onVideoTeacherSuccess(List<Video> videoList) {
//        this.videoList.addAll(videoList);
//        adapter.notifyDataSetChanged();

    }

    @Override
    public void onWatchSuccess(List<Watch> watchList) {

    }

    @Override
    public void onWatchFail(int code) {

    }

    @Override
    public void onWatchExist(boolean exist) {
        if (exist){ //已关注
            tvWatch.setText("已关注");
            tvWatch.setBackgroundResource(R.drawable.text_round_bg_color_light_gray);
        }else { //未关注、请求异常
            tvWatch.setText("+ 关注");
            tvWatch.setBackgroundResource(R.drawable.text_round_bg_color_theme);
        }
    }

    @Override
    public void onAddWatchSuccess() {
        Toast.makeText(TeacherInfoActivity.this,"关注成功",Toast.LENGTH_SHORT).show();
        tvWatch.setText("已关注");
        tvWatch.setBackgroundResource(R.drawable.text_round_bg_color_light_gray);
    }

    @Override
    public void onDeleteWatchSuccess() {
        Toast.makeText(TeacherInfoActivity.this,"取消关注成功",Toast.LENGTH_SHORT).show();
        tvWatch.setText("+ 关注");
        tvWatch.setBackgroundResource(R.drawable.text_round_bg_color_theme);
    }

    @Override
    public void onGetNumStudents(List<Watch> watchList) {
        SpannableString spannableString = new SpannableString(watchList.size()+"学员");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")),0,spannableString.length()-2,SpannableString.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.2f),0,spannableString.length()-2,SpannableString.SPAN_EXCLUSIVE_INCLUSIVE);
        tvStudents.setText(spannableString);
    }

    @Override
    public void onGetNumTeachers(List<Watch> watchList) {

    }
}
