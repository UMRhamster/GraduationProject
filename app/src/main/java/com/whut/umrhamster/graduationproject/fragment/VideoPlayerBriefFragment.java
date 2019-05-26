package com.whut.umrhamster.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.ForeshowActivity;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.TeacherInfoActivity;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.model.bean.Watch;
import com.whut.umrhamster.graduationproject.presenter.IWatchPresenter;
import com.whut.umrhamster.graduationproject.presenter.WatchPresenter;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.CircleImageView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.IWatchView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class VideoPlayerBriefFragment extends Fragment implements IInitWidgetView,IWatchView {
    private CircleImageView civIcon;
    private TextView tvNickname;
    private TextView tvStudents;
    private TextView tvWatch;
    private TextView tvTitle;
    private TextView tvViewers;
    private TextView tvBrief;
    private TextView tvDate;
    //
    private ConstraintLayout constraintLayout;

    private IWatchPresenter watchPresenter;
    Student me;
    Teacher teacher;
    Video video;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_video_player_brief,container,false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        tvWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvWatch.getText().toString().equals("+ 关注")){
                    if (me != null){
                        watchPresenter.doAddWatch(me.getId(),teacher.getId());
                    }else {
                        Toast.makeText(getActivity(),"登录之后可关注教师",Toast.LENGTH_SHORT).show();
                    }
                    //点击进行关注
                }else {
                    if (me != null){
                        watchPresenter.doDeleteWatchBySaT(me.getId(),teacher.getId());
                    }
                }
            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TeacherInfoActivity.class);
                intent.putExtra("teacher",teacher);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initView(View view) {
        video = getArguments().getParcelable("video");
        teacher = video.getUploader();
        civIcon = view.findViewById(R.id.fg_video_player_brief_civ_icon);
        tvNickname = view.findViewById(R.id.fg_video_player_brief_tv_nickname);
        tvStudents = view.findViewById(R.id.fg_video_player_brief_tv_students);
        tvWatch = view.findViewById(R.id.fg_video_player_brief_tv_watch);
        tvTitle = view.findViewById(R.id.fg_video_player_brief_tv_title);
        tvViewers = view.findViewById(R.id.fg_video_player_brief_tv_nop);
        tvBrief = view.findViewById(R.id.fg_video_player_brief_tv_brief);
        constraintLayout = view.findViewById(R.id.fg_video_player_cl_info);
        tvDate = view.findViewById(R.id.fg_video_player_brief_tv_date);
        initData();

    }

    public void initData(){
        tvDate.setText(TimeUtil.uptime2string(video.getUploadtime()));
        if (teacher != null){
            Picasso.with(getActivity()).load(teacher.getAvatar()).into(civIcon);
            tvNickname.setText(teacher.getNickname());
        }
        if (video != null){
            tvTitle.setText(video.getTitle());
            tvViewers.setText(""+video.getViewers());
            tvBrief.setText(video.getBrief());
        }
        me = SPUtil.loadStudent(getActivity());

        watchPresenter = new WatchPresenter(this);
        if (me != null){
            watchPresenter.isWatchExist(me.getId(),teacher.getId());
        }
        watchPresenter.doGetNumOfWatch(2,teacher.getId());

    }

    @Override
    public void onWatchSuccess(List<Watch> watchList) {

    }

    @Override
    public void onWatchFail(int code) {

    }

    @Override
    public void onWatchExist(boolean exist) {
        if (exist){
            tvWatch.setText("已关注");
            tvWatch.setBackgroundResource(R.drawable.text_round_bg_color_light_gray);
        }else {
            tvWatch.setText("+ 关注");
            tvWatch.setBackgroundResource(R.drawable.text_round_bg_color_theme);
        }
    }

    @Override
    public void onAddWatchSuccess() {
        Toast.makeText(getActivity(),"关注成功",Toast.LENGTH_SHORT).show();
        tvWatch.setText("已关注");
        tvWatch.setBackgroundResource(R.drawable.text_round_bg_color_light_gray);
    }

    @Override
    public void onDeleteWatchSuccess() {
        Toast.makeText(getActivity(),"取消关注成功",Toast.LENGTH_SHORT).show();
        tvWatch.setText("+ 关注");
        tvWatch.setBackgroundResource(R.drawable.text_round_bg_color_theme);
    }

    @Override
    public void onGetNumStudents(List<Watch> watchList) {
        tvStudents.setText(watchList.size()+"学员");
    }

    @Override
    public void onGetNumTeachers(List<Watch> watchList) {

    }
}
