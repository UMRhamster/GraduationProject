package com.whut.umrhamster.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.TeacherInfoActivity;
import com.whut.umrhamster.graduationproject.adapter.InteractAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Interact;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.model.bean.Watch;
import com.whut.umrhamster.graduationproject.presenter.IWatchPresenter;
import com.whut.umrhamster.graduationproject.presenter.WatchPresenter;
import com.whut.umrhamster.graduationproject.utils.other.KeyboardHeightObserver;
import com.whut.umrhamster.graduationproject.utils.other.KeyboardHeightProvider;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.CircleImageView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.IWatchView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class PlayerInteractFragment extends Fragment implements IInitWidgetView,IWatchView {
    private TextView tvTeachername;
    private TextView tvViewers;
    private CircleImageView civAvatar;
    private EditText etMessage;
    private ImageView ivSend;//消息发送按钮
    private TextView tvWatch;
    private TextView tvStudents;
    //
    private Toolbar toolbar;


    private RecyclerView recyclerView;
    private InteractAdapter adapter;
    private List<Interact> interactList;

    private IWatchPresenter watchPresenter;

    private Teacher teacher;
    private Student student;

    private WebSocket webSocket;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_player_interact,container,false);
//        AdaptionUtil.setCustomDensity(getActivity(),getActivity().getApplication());
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student == null){
                    Toast.makeText(getActivity(),"登录之后可聊天互动",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (webSocket != null){
                    if (!etMessage.getText().toString().isEmpty()){
                        webSocket.send(etMessage.getText().toString());
                        etMessage.setText("");
                    }
                }
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TeacherInfoActivity.class);
                intent.putExtra("teacher",teacher);
                startActivity(intent);
            }
        });
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
        //edittext焦点事件
        etMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (student == null){
                        Toast.makeText(getActivity(),"登录之后可进行聊天互动",Toast.LENGTH_SHORT).show();
                        etMessage.clearFocus();
                    }
                }
            }
        });
    }

    private void initData(){
        teacher = getArguments().getParcelable("teacher");
        student = SPUtil.loadStudent(getActivity());
        if (teacher != null){
            Picasso.with(getActivity()).load(teacher.getAvatar()).into(civAvatar);
            tvTeachername.setText(teacher.getNickname());
            tvViewers.setText("观看人数:"+getArguments().getInt("viewers"));
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
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.player_interact_rv);
        tvTeachername = view.findViewById(R.id.player_interact_tb_tv_nickname);
        tvViewers = view.findViewById(R.id.player_interact_tb_tv_nof);
        civAvatar = view.findViewById(R.id.player_interact_tb_civ_icon);
        etMessage = view.findViewById(R.id.player_interact_et);
        ivSend = view.findViewById(R.id.player_interact_iv_send);
        toolbar = view.findViewById(R.id.player_interact_tb);
        tvWatch = view.findViewById(R.id.player_interact_tv_watch);
        tvStudents = view.findViewById(R.id.player_interact_tb_tv_now);

        interactList = new ArrayList<>();
        adapter = new InteractAdapter(interactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initData();
        initChatRoom();
    }

    public void initChatRoom(){
        int liveId = getArguments().getInt("liveId");
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request;
        if (student != null){
            request = new Request.Builder()
                    .url("ws://192.168.1.233:8080/websocket/"+liveId+"≡"+student.getNickname())
                    .build();
        }else {
            request = new Request.Builder()
                    .url("ws://192.168.1.233:8080/websocket/"+liveId+"≡"+UUID.randomUUID().toString())
                    .build();
        }
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
            }

            @Override
            public void onMessage(WebSocket webSocket, final String text) {
                super.onMessage(webSocket, text);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] datas = text.split("≡");
                        interactList.add(new Interact(datas[0],datas[1]));
                        adapter.notifyItemChanged(interactList.size()-1);
                        recyclerView.scrollToPosition(interactList.size()-1);
                    }
                });
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
            }
        });

        client.dispatcher().executorService().shutdown();
//        webSocket.request();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webSocket.close(1000,null);
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
