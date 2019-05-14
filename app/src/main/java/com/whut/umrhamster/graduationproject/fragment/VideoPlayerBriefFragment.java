package com.whut.umrhamster.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.view.CircleImageView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class VideoPlayerBriefFragment extends Fragment implements IInitWidgetView {
    private CircleImageView civIcon;
    private TextView tvNickname;
    private TextView tvStudents;
    private TextView tvWatch;
    private TextView tvTitle;
    private TextView tvViewers;
    private TextView tvBrief;

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
                Log.d("tes","tttttttttttttttt");
                OkHttpClient client = new OkHttpClient.Builder()
                        .build();
                String url = "ws://192.168.1.233:8080/websocket/aaa|qwer";
                Request request = new Request.Builder().url(url).build();
                WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {
                    @Override
                    public void onOpen(WebSocket webSocket, Response response) {
                        super.onOpen(webSocket, response);
                        Log.d("websocket","open");
                    }

                    @Override
                    public void onMessage(WebSocket webSocket, String text) {
                        super.onMessage(webSocket, text);
                    }

                    @Override
                    public void onClosed(WebSocket webSocket, int code, String reason) {
                        super.onClosed(webSocket, code, reason);
                    }
                });
                webSocket.request();
            }
        });
    }

    @Override
    public void initView(View view) {
        teacher = getArguments().getParcelable("teacher");
        video = getArguments().getParcelable("video");
        civIcon = view.findViewById(R.id.fg_video_player_brief_civ_icon);
        tvNickname = view.findViewById(R.id.fg_video_player_brief_tv_nickname);
        tvStudents = view.findViewById(R.id.fg_video_player_brief_tv_students);
        tvWatch = view.findViewById(R.id.fg_video_player_brief_tv_watch);
        tvTitle = view.findViewById(R.id.fg_video_player_brief_tv_title);
        tvViewers = view.findViewById(R.id.fg_video_player_brief_tv_nop);
        tvBrief = view.findViewById(R.id.fg_video_player_brief_tv_brief);
        initData();

    }

    public void initData(){
        if (teacher != null){
            Picasso.get().load(teacher.getAvatar()).into(civIcon);
            tvNickname.setText(teacher.getNickname());
        }
        if (video != null){
            tvTitle.setText(video.getTitle());
            tvViewers.setText(""+video.getViewers());
            tvBrief.setText(video.getBrief());
        }
    }
}
