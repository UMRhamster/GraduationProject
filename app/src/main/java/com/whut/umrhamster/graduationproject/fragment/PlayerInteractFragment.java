package com.whut.umrhamster.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.InteractAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Interact;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.utils.other.KeyboardHeightObserver;
import com.whut.umrhamster.graduationproject.utils.other.KeyboardHeightProvider;
import com.whut.umrhamster.graduationproject.view.CircleImageView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.List;

public class PlayerInteractFragment extends Fragment implements IInitWidgetView {
    private TextView tvTeachername;
    private TextView tvViewers;
    private CircleImageView civAvatar;
    private EditText etMessage;


    private RecyclerView recyclerView;
    private InteractAdapter adapter;
    private List<Interact> interactList;

    private KeyboardHeightProvider keyboardHeightProvider;

    private Teacher teacher;
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
//        View decorView = getActivity().getWindow().getDecorView();
//        View contentView = getActivity().findViewById(Window.ID_ANDROID_CONTENT);
//        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView,contentView));

    }

//    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
//        return new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Rect r = new Rect();
//                decorView.getWindowVisibleDisplayFrame(r);
//
//                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
//                int diff = height - r.bottom;
//
//                if (diff > 0) {
//                    if (contentView.getPaddingBottom() != diff) {
//                        contentView.setPadding(0, 0, 0, diff);
//                    }
//                } else {
//                    if (contentView.getPaddingBottom() != 0) {
//                        contentView.setPadding(0, 0, 0, 0);
//                    }
//                }
//            }
//        };
//    }

    private void initData(){
        teacher = getArguments().getParcelable("teacher");
        if (teacher != null){
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
        initData();

        interactList = new ArrayList<>();
        interactList.add(new Interact("测试人员1","测试测试人员1弹幕1"));
        interactList.add(new Interact("测试人员1","测试弹幕测试人员11"));
        interactList.add(new Interact("测试人员测试人员1测试人员1测试人员11","测试测试人员1测试人员1测试人员1弹幕1"));
        interactList.add(new Interact("测试人测试人员1员1","测试弹幕1"));
        interactList.add(new Interact("测测试人员1试人员1","测试弹测试人员1测试人员1幕1"));
        interactList.add(new Interact("测试人123员1","测试弹幕1"));
        interactList.add(new Interact("测试人dsfsdfsd员1","测试弹测试人员1测试人员1幕1"));
        interactList.add(new Interact("测试人员sdfsdf1","测试弹幕1"));
        interactList.add(new Interact("测试人员1","测试弹幕1"));
        interactList.add(new Interact("测试人员fdsfsd1","测试弹测试人员1测试人员1幕1"));
        interactList.add(new Interact("测试人员测试人员11","测试弹幕1"));
        interactList.add(new Interact("测试人员1","测试弹测试人员1测试人员1幕1"));
        interactList.add(new Interact("测试s人员1","测试弹幕1"));
        interactList.add(new Interact("测试测试人员1测试人员1测试人员1人员1","测试弹幕1"));
        interactList.add(new Interact("测试人员1","测试弹幕1"));
        interactList.add(new Interact("测试sddsaas人员1","测试测试人员1测试人员1弹幕1"));
        interactList.add(new Interact("测试人员1","测试弹幕1"));
        interactList.add(new Interact("测试人员sad1","测试弹测试人员1测试人员1幕1"));
        interactList.add(new Interact("测试人员sda1","测试弹幕1"));
        interactList.add(new Interact("测试人ss员1","测试弹测试人员1测试人员1测试人员1幕1"));
        interactList.add(new Interact("测试人ssssss员1","测试弹测试人员1测试人员1测试人员1幕1"));
        interactList.add(new Interact("测试人员1","测试弹幕1"));




        adapter = new InteractAdapter(interactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
