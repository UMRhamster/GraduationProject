package com.whut.umrhamster.graduationproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.model.bean.Foreshow;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Watch;
import com.whut.umrhamster.graduationproject.presenter.IWatchPresenter;
import com.whut.umrhamster.graduationproject.presenter.WatchPresenter;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.CircleImageView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.IWatchView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ForeshowActivity extends AppCompatActivity implements IInitWidgetView,IWatchView {
    private ImageView ivCover; //预告封面
    private TextView tvTitle;
    private TextView tvBrief;
    private TextView tvStartTime;
    private TextView tvDuration;
    private TextView tvType;
    //top
    private ImageView ivBack;
    private TextView tvTopTitle;
    //bottom
    private CircleImageView civIcon;
    private TextView tvNickname;
    private TextView tvRoom;
    private TextView tvStudents;
    private TextView tvWatch;
    private Toolbar tbBottom;

    Foreshow foreshow;

    //sj
    private Student student;
    private IWatchPresenter watchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdaptionUtil.setCustomDensity(ForeshowActivity.this,getApplication());
        setContentView(R.layout.activity_foreshow);
        initView();
        initEvent();

//        Calendar calendar = Calendar.getInstance();
//        Log.d("tes",""+calendar.get(Calendar.MONTH));
//        Log.d("tes",""+calendar.get(Calendar.DAY_OF_MONTH));

    }
    public void initData(){
        Picasso.with(ForeshowActivity.this).load(foreshow.getLive().getCover()).config(Bitmap.Config.RGB_565).into(ivCover);
        tvTitle.setText(foreshow.getLive().getTitle());
        tvBrief.setText(foreshow.getLive().getBrief());
        tvStartTime.setText(new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(foreshow.getStartTime()));
        tvDuration.setText(TimeUtil.minute2ham(foreshow.getDuration()));
        tvType.setText(foreshow.getLive().getClassify().getName());
        //
        tvTopTitle.setText(foreshow.getLive().getTitle());
        //
        Picasso.with(ForeshowActivity.this).load(foreshow.getLive().getTeacher().getAvatar()).config(Bitmap.Config.RGB_565).into(civIcon);
        tvNickname.setText(foreshow.getLive().getTeacher().getNickname());
        tvRoom.setText("房间号:"+foreshow.getLive().getId());

        student = SPUtil.loadStudent(ForeshowActivity.this);
        watchPresenter = new WatchPresenter(this);
        if (student != null){
            watchPresenter.isWatchExist(student.getId(),foreshow.getLive().getTeacher().getId());
        }
        watchPresenter.doGetNumOfWatch(2,foreshow.getLive().getTeacher().getId());
    }

    @Override
    public void initView() {
        foreshow = getIntent().getParcelableExtra("foreshow");

        ivCover = findViewById(R.id.foreshow_iv_cover);
        tvTitle = findViewById(R.id.foreshow_tv_title_content);
        tvBrief = findViewById(R.id.foreshow_brief_content);
        tvStartTime = findViewById(R.id.foreshow_tv_starttime_content);
        tvDuration = findViewById(R.id.foreshow_tv_duration_content);
        tvType = findViewById(R.id.foreshow_tv_type_content);
        //
        ivBack = findViewById(R.id.ac_foreshow_iv_back);
        tvTopTitle = findViewById(R.id.ac_foreshow_tb_tv_title);
        //
        civIcon = findViewById(R.id.ac_foreshow_host_civ_icon);
        tvNickname = findViewById(R.id.ac_foreshow_host_tv_nickname);
        tvRoom = findViewById(R.id.ac_foreshow_host_tv_liveid);
        tvStudents = findViewById(R.id.ac_foreshow_host_tv_students);
        tvWatch = findViewById(R.id.ac_foreshow_host_tv_watch);
        tbBottom = findViewById(R.id.foreshow_tb_bottom);
//        Log.d("te",foreshow.getStartTime()+"");
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
                        watchPresenter.doAddWatch(student.getId(),foreshow.getLive().getTeacher().getId());
                    }else {
                        Toast.makeText(ForeshowActivity.this,"登录之后可关注教师",Toast.LENGTH_SHORT).show();
                    }
                    //点击进行关注
                }else {
                    if (student != null){
                        watchPresenter.doDeleteWatchBySaT(student.getId(),foreshow.getLive().getTeacher().getId());
                    }
                }
                //////////////////////////////////
            }
        });
        tbBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForeshowActivity.this,TeacherInfoActivity.class);
                intent.putExtra("teacher",foreshow.getLive().getTeacher());
                startActivity(intent);
            }
        });
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void onWatchSuccess(List<Watch> watchList) {
        //useless
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
        Toast.makeText(ForeshowActivity.this,"关注成功",Toast.LENGTH_SHORT).show();
        tvWatch.setText("已关注");
        tvWatch.setBackgroundResource(R.drawable.text_round_bg_color_light_gray);
    }

    @Override
    public void onDeleteWatchSuccess() {
        Toast.makeText(ForeshowActivity.this,"取消关注成功",Toast.LENGTH_SHORT).show();
        tvWatch.setText("+ 关注");
        tvWatch.setBackgroundResource(R.drawable.text_round_bg_color_theme);
    }

    @Override
    public void onGetNumStudents(List<Watch> watchList) {
        tvStudents.setText("学员:"+watchList.size());
    }

    @Override
    public void onGetNumTeachers(List<Watch> watchList) {

    }
}
