package com.whut.umrhamster.graduationproject;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
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
import com.whut.umrhamster.graduationproject.model.bean.History;
import com.whut.umrhamster.graduationproject.model.bean.InfoGroupBean;
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.presenter.HistoryPresenter;
import com.whut.umrhamster.graduationproject.presenter.IHistoryPresenter;
import com.whut.umrhamster.graduationproject.presenter.ILivePresenter;
import com.whut.umrhamster.graduationproject.presenter.ITimeKeepPresenter;
import com.whut.umrhamster.graduationproject.presenter.LivePresenterCompl;
import com.whut.umrhamster.graduationproject.presenter.TimeKeepPresenter;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.utils.other.AndroidBug5497Workaround;
import com.whut.umrhamster.graduationproject.utils.other.KeyboardHeightObserver;
import com.whut.umrhamster.graduationproject.utils.other.KeyboardHeightProvider;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.IHistoryView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.ILiveView;
import com.whut.umrhamster.graduationproject.view.ITimeKeepView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class PlayerActivity extends AppCompatActivity implements IInitWidgetView,ITimeKeepView,IHistoryView,ILiveView {
    private final static int TAG = 0;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;
    private PlayerFragmentPagerAdapter adapter;

    //activity上的控件
    //顶部播放控制器上的控件
    private ImageView ivBack;
    private ImageView ivMore;
    private TextView tvTitle;
    //底部播放控制器上的控件
    private ImageView ivPause;
    private ImageView ivFullscreen;
    private TextView tvTime;  //暂不使用，可用于记录直播时长
    //
    private RelativeLayout topController; //顶部控制器
    private RelativeLayout bottomController;//底部控制器

    private TextureView textureView; //播放界面

    private ITimeKeepPresenter timeKeepPresenter;
    private IHistoryPresenter historyPresenter;
    private ILivePresenter livePresenter;

    //播放器
    IjkMediaPlayer mediaPlayer;
    Surface mSurface;

    private Live live;
    Student student;

    MyHandler handler;

//    private KeyboardHeightProvider keyboardHeightProvider;
    private int delay=0;//观看时长记录
    private boolean isPlay = true;  //直播内容进行房间就直接开始播放
    private boolean seeController = false;
    private boolean isFullscreen = false;//是否全屏
    private int timeToInv = 0;

    //记录观看时长
    Timer countTimer;
    TimerTask countTask;

    public void startCountTimer(){
        if (countTimer == null){
            countTimer = new Timer();
        }
        if (countTask == null){
            countTask = new TimerTask() {
                @Override
                public void run() {
                    delay++;
                    handler.sendEmptyMessage(TAG);
                    Log.d("计时测试:","delay "+delay);
                }
            };
        }
        if (countTimer != null && countTask != null){
            countTimer.scheduleAtFixedRate(countTask,delay,1000);
        }

    }

    public void stopCountTimer(){
        if (countTimer != null){
            countTimer.cancel();
            countTimer = null;
        }
        if (countTask != null){
            countTask.cancel();
            countTask = null;
        }

    }

    @Override
    public void onHistorySuccess(List<History> historyList) {

    }

    @Override
    public void onHistoryFail(int code) {

    }

    @Override
    public void existResult(int code) {

    }

    @Override
    public void insertResult(int code) {

    }

    @Override
    public void updateResult(int code) {

    }

    @Override
    public void deleteResult(int code) {

    }

    @Override
    public void onTimeKeepSuccess(List<InfoGroupBean> groupBeanList) {

    }

    @Override
    public void onTimeKeepFail(int code) {

    }

    @Override
    public void onAllLiveSuccess(List<Live> liveList) {

    }

    @Override
    public void onAllLiveFail(int code) {

    }

    @Override
    public void onTypeLiveSuccess(List<Live> liveList) {

    }

    static class MyHandler extends Handler {
        WeakReference<PlayerActivity> weakReference;

        public MyHandler(PlayerActivity playerActivity){
            weakReference = new WeakReference<>(playerActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PlayerActivity activity = weakReference.get();
            if (activity != null){
                switch (msg.what){
                    case TAG:
                        if (activity.isPlay){  //播放状态中，8秒后自动收起播放控制器及状态栏隐藏
                            activity.timeToInv++;
                            if (activity.timeToInv == 8){
                                activity.showController(false);
                                activity.timeToInv = 0;
                            }
                        }else {
                            activity.timeToInv=0;
                        }
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdaptionUtil.setCustomDensity(PlayerActivity.this,getApplication());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //不显示状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_player);
//        AndroidBug5497Workaround.assistActivity(this);
        live = getIntent().getParcelableExtra("live");
        student = SPUtil.loadStudent(PlayerActivity.this);
        initView();
        initEvent();
    }

    private void createPlayer(){
        if (mediaPlayer == null){
            mediaPlayer = new IjkMediaPlayer();
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"start-on-prepared",0);
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT,"analyzeduration",1);
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT,"analyzemaxduration",100L);
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT,"probesize",1024*2);
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT,"flush_packets",1L);
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"packet-buffering",1);
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"framedrop",5);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(live.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer iMediaPlayer) {
                    mediaPlayer.start();
                    startCountTimer();
                }
            });
            handler = new MyHandler(this);
            mediaPlayer.prepareAsync();
//            mediaPlayer.start();
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
        getStatusBarHeight();
        showController(false);
    }

    private void initData(){
        timeKeepPresenter = new TimeKeepPresenter(this);
        historyPresenter = new HistoryPresenter(this);
        livePresenter = new LivePresenterCompl(this);
        if (live != null){
            tvTitle.setText(live.getTitle());
            livePresenter.doUpdateLiveViewers(live.getId(),0);
        }
    }

    @Override
    public void initEvent() {
        //播放/暂停按钮
        ivPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay){
                    mediaPlayer.pause();
                    ivPause.setImageResource(R.drawable.play);
                    isPlay = false;
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    stopCountTimer();
                }else {
                    mediaPlayer.start();
                    ivPause.setImageResource(R.drawable.pause);
                    isPlay = true;
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    startCountTimer();
                }
                //视频播放/暂停处理
            }
        });
        //点击屏幕控制器显示与否
        textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showController(!seeController);  //seeController为点击前的状态
                if (seeController){
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //不显示状态栏
                }else {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //不显示状态栏
                }
            }
        });
        //顶部控制器
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFullscreen){
                    setFullscreen(false);
                }else {
                    onBackPressed();
                }
            }
        });
        ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFullscreen){
                    setFullscreen(true);
                }
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
    protected void onPause() {
        super.onPause();
        stopCountTimer();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (student != null){
            timeKeepPresenter.doUploadTimeKeep(student.getId(),live.getClassify().getId(),delay);
            historyPresenter.insertHistory(student.getId(),0,live.getId(), 0); //type: 1-视频,0-1直播,直播的watchedTime字段不计时
        }
        livePresenter.doUpdateLiveViewers(live.getId(),1);
        release();
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void onBackPressed() {
        if (isFullscreen){
            setFullscreen(false);
        }else {
            super.onBackPressed();
        }
    }

    //控制播放控制器的显示与否
    public void showController(boolean show){
        if (show){
            topController.setVisibility(View.VISIBLE);
            bottomController.setVisibility(View.VISIBLE);
            seeController = true;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        }else {
            topController.setVisibility(View.INVISIBLE);
            bottomController.setVisibility(View.INVISIBLE);
            seeController = false;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        }
    }

    public void setFullscreen(boolean fullscreen){
        if (!fullscreen){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,AdaptionUtil.dp2px(PlayerActivity.this,202));
            textureView.setLayoutParams(lp);
            isFullscreen = false;
            ivFullscreen.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(AdaptionUtil.dp2px(PlayerActivity.this,640),RelativeLayout.LayoutParams.MATCH_PARENT);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            textureView.setLayoutParams(lp);
            isFullscreen = true;
            ivFullscreen.setVisibility(View.INVISIBLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        }
    }

    public void getStatusBarHeight(){
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        ViewGroup rootView = getWindow().getDecorView().findViewById(R.id.player_top_controller);
        rootView.setPadding(0,result,0,0);
    }
}
