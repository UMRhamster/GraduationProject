package com.whut.umrhamster.graduationproject;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whut.umrhamster.graduationproject.adapter.PlayerFragmentPagerAdapter;
import com.whut.umrhamster.graduationproject.fragment.PlayerHostFragment;
import com.whut.umrhamster.graduationproject.fragment.PlayerInteractFragment;
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.utils.other.KeyboardHeightObserver;
import com.whut.umrhamster.graduationproject.utils.other.KeyboardHeightProvider;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class PlayerActivity extends AppCompatActivity implements IInitWidgetView {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;
    private PlayerFragmentPagerAdapter adapter;

    //activity上的控件
    private ImageView ivBack;
    private ImageView ivMore;
    private TextView tvTitle;
    private ImageView ivPause;
    private ImageView ivFullscreen;
    private TextView tvTime;
    private RelativeLayout topController; //顶部控制器
    private RelativeLayout bottomController;//底部控制器

    private TextureView textureView;

    //播放器
    IjkMediaPlayer mediaPlayer;
    Surface mSurface;

    private Live live;

//    private KeyboardHeightProvider keyboardHeightProvider;

    private boolean isPlay;
    private boolean seeController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdaptionUtil.setCustomDensity(PlayerActivity.this,getApplication());
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player);

        initView();
        initEvent();
    }

    private void createPlayer(){
        if (mediaPlayer == null){
            mediaPlayer = new IjkMediaPlayer();
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"start-on-prepared",0);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource("http://192.168.1.106:8089/video_test_1.mp4");
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.prepareAsync();
        }
    }

    private void release() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        IjkMediaPlayer.native_profileEnd();
    }


    @Override
    public void initView() {
        viewPager = findViewById(R.id.player_vp);
        tabLayout = findViewById(R.id.player_tl);
        topController = findViewById(R.id.player_top_controller);
        //activity
        ivBack = findViewById(R.id.ac_live_room_player_back);
        ivMore = findViewById(R.id.ac_live_room_player_more);
        tvTitle = findViewById(R.id.ac_live_room_player_title);
        ivPause = findViewById(R.id.ac_live_room_player_play);
        ivFullscreen = findViewById(R.id.ac_live_room_player_fulls);
        tvTime = findViewById(R.id.ac_live_room_player_time);
        textureView = findViewById(R.id.player_txv);
        topController = findViewById(R.id.player_top_controller);
        bottomController = findViewById(R.id.player_bottom_controller);

        initData();

        fragmentList = new ArrayList<>(2);

        Bundle bundle = new Bundle();
        bundle.putParcelable("teacher",live.getTeacher());
        bundle.putInt("viewers",live.getViewers());
        bundle.putInt("liveId",live.getId());
        PlayerInteractFragment interactFragment = new PlayerInteractFragment();
        interactFragment.setArguments(bundle);
        fragmentList.add(interactFragment);
        PlayerHostFragment hostFragment = new PlayerHostFragment();
        hostFragment.setArguments(bundle);
        fragmentList.add(hostFragment);

        adapter = new PlayerFragmentPagerAdapter(getSupportFragmentManager(),fragmentList,getResources().getStringArray(R.array.player_tab_titles));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        isPlay = true;
        seeController = false;
        //初始控制器为不可见
//        topController.setVisibility(View.INVISIBLE);
//        bottomController.setVisibility(View.INVISIBLE);

//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) topController.getLayoutParams();
//        params.setMargins(0,getStatusBarHeight(),0,0);
//        topController.setLayoutParams(params);
//        topController.setPadding(0,getStatusBarHeight(),0,0);

    }

    private void initData(){
        live = getIntent().getParcelableExtra("live");
        if (live != null){
            tvTitle.setText(live.getTitle());

        }
    }

    @Override
    public void initEvent() {
        //播放/暂停按钮
        ivPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay){
                    ivPause.setImageResource(R.drawable.play);
                    isPlay = false;
                }else {
                    mediaPlayer.start();
                    ivPause.setImageResource(R.drawable.pause);
                    isPlay = true;
                }
                //视频播放/暂停处理
            }
        });
        //点击屏幕控制器显示与否
        textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seeController){
                    topController.setVisibility(View.VISIBLE);
                    bottomController.setVisibility(View.VISIBLE);
                    seeController = false;
                }else {
                    topController.setVisibility(View.INVISIBLE);
                    bottomController.setVisibility(View.INVISIBLE);
                    seeController = true;
                }
            }
        });
        //顶部控制器
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlayerActivity.this,"开发中...",Toast.LENGTH_SHORT).show();
            }
        });
        ivFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlayerActivity.this,"全屏界面暂不显示",Toast.LENGTH_SHORT).show();
            }
        });
        //播放器相关
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                createPlayer();
                mSurface = new Surface(surface);
                mediaPlayer.setSurface(mSurface);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                textureView.setSurfaceTextureListener(null);
                textureView = null;
                mSurface = null;
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
//        keyboardHeightProvider.close();
    }

    @Override
    public void initView(View view) {

    }

//    @Override
//    public void onKeyboardHeightChanged(int height, int orientation, boolean status) {
//        if (status){
//            Toast.makeText(PlayerActivity.this,"open" +height,Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(PlayerActivity.this,"close "+height,Toast.LENGTH_SHORT).show();
//        }
//    }

}
