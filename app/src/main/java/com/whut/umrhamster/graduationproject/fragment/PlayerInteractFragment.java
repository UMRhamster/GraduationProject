package com.whut.umrhamster.graduationproject.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.whut.umrhamster.graduationproject.adapter.InteractAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Interact;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.utils.other.KeyboardHeightObserver;
import com.whut.umrhamster.graduationproject.utils.other.KeyboardHeightProvider;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.CircleImageView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class PlayerInteractFragment extends Fragment implements IInitWidgetView {
    private TextView tvTeachername;
    private TextView tvViewers;
    private CircleImageView civAvatar;
    private EditText etMessage;
    private ImageView ivSend;//消息发送按钮


    private RecyclerView recyclerView;
    private InteractAdapter adapter;
    private List<Interact> interactList;

    private KeyboardHeightProvider keyboardHeightProvider;

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
                if (webSocket != null){
                    if (!etMessage.getText().toString().isEmpty()){
                        webSocket.send(etMessage.getText().toString());
                        etMessage.setText("");
                    }
                }
            }
        });
    }

    private void initData(){
        teacher = getArguments().getParcelable("teacher");
        if (teacher != null){
            Log.d("test",teacher.getAvatar()+" "+teacher.getNickname());
            Picasso.get().load(teacher.getAvatar()).into(civAvatar);
            tvTeachername.setText(teacher.getNickname());
            tvViewers.setText("观看人数:"+getArguments().getInt("viewers"));
        }else {
            Toast.makeText(getActivity(),"数据传递失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.player_interact_rv);
        tvTeachername = view.findViewById(R.id.player_interact_tb_tv_nickname);
        tvViewers = view.findViewById(R.id.player_interact_tb_tv_nof);
        civAvatar = view.findViewById(R.id.player_interact_tb_civ_icon);
        etMessage = view.findViewById(R.id.player_interact_et);
        ivSend = view.findViewById(R.id.player_interact_iv_send);
        initData();

        interactList = new ArrayList<>();
        adapter = new InteractAdapter(interactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initChatRoom();
    }

    public void initChatRoom(){
        student = SPUtil.loadStudent(getActivity());
        int liveId = getArguments().getInt("liveId");
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request;
        if (student != null){
            request = new Request.Builder()
                    .url("ws://192.168.1.233:8080/websocket/"+liveId+"≡"+student.getNickname())
                    .build();
        }else {
            request = new Request.Builder()
                    .url("ws://192.168.1.233:8080/websocket/"+liveId+"≡guest"+System.currentTimeMillis())
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
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
            }
        });
        webSocket.request();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webSocket.cancel();
    }
}
