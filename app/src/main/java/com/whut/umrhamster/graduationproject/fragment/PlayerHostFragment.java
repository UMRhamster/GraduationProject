package com.whut.umrhamster.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.model.bean.Watch;
import com.whut.umrhamster.graduationproject.presenter.IWatchPresenter;
import com.whut.umrhamster.graduationproject.presenter.WatchPresenter;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.CircleImageView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.IWatchView;

import java.util.List;

public class PlayerHostFragment extends Fragment implements IInitWidgetView,IWatchView {
    private CircleImageView civIcon;
    private TextView tvTeachername;
    private TextView tvLiveId;
    private TextView tvStudents;
    private TextView tvWatch;


    private Teacher teacher;
    private Student student;
    //
    private IWatchPresenter watchPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_player_host,container,false);
        initView(view);
        initEvent();
        return view;
    }
    private void initData(){
        student = SPUtil.loadStudent(getActivity());
        teacher = getArguments().getParcelable("teacher");
        if (teacher != null){
            Picasso.get().load(teacher.getAvatar()).into(civIcon);
            tvTeachername.setText(teacher.getNickname());
            tvLiveId.setText("房间号:"+getArguments().getInt("liveId"));
            //xue yuan shu
        }else {
            Toast.makeText(getActivity(),"数据传递失败",Toast.LENGTH_SHORT).show();
        }
        watchPresenter = new WatchPresenter(this);
        if (student != null){
            watchPresenter.isWatchExist(student.getId(),teacher.getId());
        }
        watchPresenter.doGetNumOfWatch(2,teacher.getId());
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
                    if (student != null){
                        watchPresenter.doAddWatch(student.getId(),teacher.getId());
                    }else {
                        Toast.makeText(getActivity(),"登录之后可关注教师",Toast.LENGTH_SHORT).show();
                    }
                    //点击进行关注
                }else {
                    if (student != null){
                        watchPresenter.doDeleteWatchBySaT(student.getId(),teacher.getId());
                    }
                }
            }
        });
    }

    @Override
    public void initView(View view) {
        civIcon = view.findViewById(R.id.fg_player_host_civ_icon);
        tvTeachername = view.findViewById(R.id.fg_player_host_tv_nickname);
        tvLiveId = view.findViewById(R.id.fg_player_host_tv_liveid);
        tvStudents = view.findViewById(R.id.fg_player_host_tv_students);
        tvWatch = view.findViewById(R.id.fg_player_host_tv_watch);
        initData();
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
        tvStudents.setText("学员:"+watchList.size());
    }

    @Override
    public void onGetNumTeachers(List<Watch> watchList) {

    }
}
