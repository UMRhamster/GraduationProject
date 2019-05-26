package com.whut.umrhamster.graduationproject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.whut.umrhamster.graduationproject.adapter.VideoPlayerFragmentPagerAdapter;
import com.whut.umrhamster.graduationproject.fragment.VideoPlayerBriefFragment;
import com.whut.umrhamster.graduationproject.fragment.VideoPlayerCommentFragment;
import com.whut.umrhamster.graduationproject.model.bean.History;
import com.whut.umrhamster.graduationproject.model.bean.InfoGroupBean;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.presenter.HistoryPresenter;
import com.whut.umrhamster.graduationproject.presenter.IHistoryPresenter;
import com.whut.umrhamster.graduationproject.presenter.ITimeKeepPresenter;
import com.whut.umrhamster.graduationproject.presenter.IVideoPresenter;
import com.whut.umrhamster.graduationproject.presenter.TimeKeepPresenter;
import com.whut.umrhamster.graduationproject.presenter.VideoPresenter;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.IHistoryView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.ITimeKeepView;
import com.whut.umrhamster.graduationproject.view.IVideoView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

public class VideoPlayerActivity extends AppCompatActivity implements IInitWidgetView,ITimeKeepView,IVideoView,IHistoryView {
    private final static int TAG = 0;

    private RelativeLayout topController;       //顶部播放控制器
    private ConstraintLayout bottomController;  //底部播放控制器
    //textrueview上面控制器上的控件
    private TextView tvTopTitle;
    private ImageView ivBack;
    private ImageView ivSetting;

    private TextureView textureView;
    private ImageView ivPlay;
    private SeekBar seekBar;
    private TextView tvTime;
    private ImageView ivFullscreen;
    View view;

    IjkMediaPlayer ijkMediaPlayer;
    Surface mSurface;
    //tab fg
    private VideoPlayerFragmentPagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;

    //shipin
    Video video;
    private int delay = 0; //观看时间记录

    //状态
    private boolean isPlay = false;
    private boolean isDestroy = false;
    private boolean seeController = true;
    private boolean isFullscreen = false;//是否全屏
    private int timeToInv = 0;
    private Speed speed = Speed.SPEED_1_0; //默认为正常速度1.0f
    //presenter
    private ITimeKeepPresenter timeKeepPresenter;//处理学习时长记录
    private IVideoPresenter videoPresenter;      //处理视频播放量
    private IHistoryPresenter historyPresenter;  //处理历史记录

    enum Speed{ //内部枚举类，用于播放速度判定
        SPEED_0_5,SPEED_0_75,SPEED_1_0,SPEED_1_25,SPEED_1_5,SPEED_2_0
    }

    Student student;

    MyHandler handler;

    //更新视频播放进度
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            handler.sendEmptyMessage(TAG);
        }
    };
    //纪录观看时长
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
    public void onTimeKeepSuccess(List<InfoGroupBean> groupBeanList) {

    }

    @Override
    public void onTimeKeepFail(int code) {
        Log.d("VideoPlayerAcivity","计时处理异常"+code);
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


    static class MyHandler extends Handler{
        WeakReference<VideoPlayerActivity> weakReference;

        public MyHandler(VideoPlayerActivity videoPlayerActivity){
            weakReference = new WeakReference<>(videoPlayerActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            VideoPlayerActivity activity = weakReference.get();
            if (activity != null){
                switch (msg.what){
                    case TAG:
                        activity.seekBar.setProgress((int) activity.ijkMediaPlayer.getCurrentPosition());
                        activity.seekBar.setSecondaryProgress((int) (activity.ijkMediaPlayer.getVideoCachedDuration()+activity.ijkMediaPlayer.getCurrentPosition()));
                        activity.tvTime.setText(TimeUtil.millint2String(activity.ijkMediaPlayer.getCurrentPosition())+"/"+TimeUtil.millint2String(activity.video.getTotaltime()));
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
        AdaptionUtil.setCustomDensity(VideoPlayerActivity.this,getApplication());
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //不显示状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_video_player);
        video = getIntent().getParcelableExtra("video");
        initView();
        initEvent();
    }

    @Override
    public void initView() {
        view = findViewById(R.id.video_player_bg);
        textureView = findViewById(R.id.video_player_txv);
        ivBack = findViewById(R.id.ac_live_room_player_back);
        ivSetting = findViewById(R.id.ac_live_room_player_more);
        ivPlay = findViewById(R.id.video_player_iv_play);
        seekBar = findViewById(R.id.video_player_seekbar);
        tvTime = findViewById(R.id.video_player_tv_time);
        ivFullscreen = findViewById(R.id.video_player_iv_fullscreen);
        viewPager = findViewById(R.id.video_player_vp);
        tabLayout = findViewById(R.id.video_player_tl);
        tvTopTitle = findViewById(R.id.ac_live_room_player_title);
        tvTopTitle.setVisibility(View.GONE);

        topController = findViewById(R.id.video_player_top_controller);
        bottomController = findViewById(R.id.video_player_cl_bottom);

        fragmentList = new ArrayList<>(2);

        VideoPlayerBriefFragment briefFragment = new VideoPlayerBriefFragment();
        Bundle bundle = new Bundle();
//        bundle.putParcelable("teacher",video.getUploader());
        bundle.putParcelable("video",video);
        briefFragment.setArguments(bundle);
        VideoPlayerCommentFragment commentFragment = new VideoPlayerCommentFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("videoId",video.getId());
        commentFragment.setArguments(bundle1);
        fragmentList.add(briefFragment);
        fragmentList.add(commentFragment);
        adapter = new VideoPlayerFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);;
        tabLayout.setupWithViewPager(viewPager);
        initData();
        getStatusBarHeight();
    }

    public void initData(){
        student = SPUtil.loadStudent(VideoPlayerActivity.this);
        timeKeepPresenter = new TimeKeepPresenter(this);
        seekBar.setMax(video.getTotaltime());
        videoPresenter = new VideoPresenter(this);
        historyPresenter = new HistoryPresenter(this);
    }

    @Override
    public void initEvent() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ijkMediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay){
                    ivPlay.setImageResource(R.drawable.play);
                    ijkMediaPlayer.pause();
                    isPlay = false;
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    stopCountTimer();
                }else {
                    ivPlay.setImageResource(R.drawable.pause);
//                    ijkMediaPlayer.stop();
//                    ijkMediaPlayer.reset();
                    ijkMediaPlayer.start();
                    view.setVisibility(View.GONE);
                    isPlay = true;
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    startCountTimer();
                }
            }
        });
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                createPlayer();
                mSurface = new Surface(surface);
                ijkMediaPlayer.setSurface(mSurface);
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
        //控制上下控制器显示
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

        //点击全屏按钮
        ivFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFullscreen){
                    setFullscreen(true);
                }
            }
        });
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFullscreen){ //全屏状态才可以进行弹窗设置播放速度
                    showEndDialog();
                }
            }
        });
    }

    public void createPlayer(){
        if (ijkMediaPlayer == null) {
            ijkMediaPlayer = new IjkMediaPlayer();
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"max-buffer-size",2*1024*1024);
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "soundtouch", 1);
            ijkMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                ijkMediaPlayer.setDataSource(video.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            ijkMediaPlayer.setOnTimedTextListener(new IMediaPlayer.OnTimedTextListener() {
                @Override
                public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {
                    Log.d("test",ijkTimedText.getText());
                }
            });
            ijkMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                    stopCountTimer();
                    return false;
                }
            });

            ijkMediaPlayer.prepareAsync();
//            handler.sendEmptyMessage(TAG);
            handler = new MyHandler(this);
            timer.schedule(timerTask,0,1000);
            ijkMediaPlayer.setScreenOnWhilePlaying(true);
        }
    }

    @Override
    public void initView(View view) {

    }

    private void release() {
        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.stop();
            ijkMediaPlayer.release();
            ijkMediaPlayer = null;
        }
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (ijkMediaPlayer != null && !ijkMediaPlayer.isPlaying())
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("aaaa","pause");
        stopCountTimer();
        if (student != null){
            timeKeepPresenter.doUploadTimeKeep(student.getId(),video.getClassify().getId(),delay);
            historyPresenter.insertHistory(student.getId(),1,video.getId(), (int) ijkMediaPlayer.getCurrentPosition()); //type: 1-视频
        }
        if (delay > 10){ //观看时长大于10秒，进行视频播放量加一
            videoPresenter.doAddNumOfPlay(video.getId());
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (ijkMediaPlayer != null && ijkMediaPlayer.isPlaying()){
            ijkMediaPlayer.pause();
            ivPlay.setImageResource(R.drawable.play);
            isPlay = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("aaaa","stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        release();
        isDestroy = false;
        timer.cancel();
    }

    @Override
    public void onBackPressed() {
        if (isFullscreen){
            setFullscreen(false);
        }else {
            super.onBackPressed();
        }
    }

    public void showEndDialog(){
        Dialog dialog = new Dialog(VideoPlayerActivity.this,R.style.PlayerDialog);
        View view = View.inflate(VideoPlayerActivity.this,R.layout.dialog_player_setting,null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.END);
        window.setLayout(AdaptionUtil.dp2px(VideoPlayerActivity.this,300),ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();
//        ijkMediaPlayer.getSpeed() ???
        RadioGroup radioGroup = dialog.findViewById(R.id.player_speed_rg);
        switch (speed){
            case SPEED_0_5:
                radioGroup.check(R.id.player_speed_rb_0_5);
                break;
            case SPEED_0_75:
                radioGroup.check(R.id.player_speed_rb_0_75);
                break;
            case SPEED_1_0:
                radioGroup.check(R.id.player_speed_rb_1_0);
                break;
            case SPEED_1_25:
                radioGroup.check(R.id.player_speed_rb_1_25);
                break;
            case SPEED_1_5:
                radioGroup.check(R.id.player_speed_rb_1_5);
                break;
            case SPEED_2_0:
                radioGroup.check(R.id.player_speed_rb_2_0);
                break;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.player_speed_rb_0_5:
                        ijkMediaPlayer.setSpeed(0.5f);
                        speed = Speed.SPEED_0_5;
                        break;
                    case R.id.player_speed_rb_0_75:
                        ijkMediaPlayer.setSpeed(0.75f);
                        speed = Speed.SPEED_0_75;
                        break;
                    case R.id.player_speed_rb_1_0:
                        ijkMediaPlayer.setSpeed(1.0f);
                        speed = Speed.SPEED_1_0;
                        break;
                    case R.id.player_speed_rb_1_25:
                        ijkMediaPlayer.setSpeed(1.25f);
                        speed = Speed.SPEED_1_25;
                        break;
                    case R.id.player_speed_rb_1_5:
                        ijkMediaPlayer.setSpeed(1.5f);
                        speed = Speed.SPEED_1_5;
                        break;
                    case R.id.player_speed_rb_2_0:
                        ijkMediaPlayer.setSpeed(2.0f);
                        speed = Speed.SPEED_2_0;
                        break;
                }
            }
        });
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
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,AdaptionUtil.dp2px(VideoPlayerActivity.this,202));
            textureView.setLayoutParams(lp);
            isFullscreen = false;
            ivFullscreen.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(AdaptionUtil.dp2px(VideoPlayerActivity.this,640),RelativeLayout.LayoutParams.MATCH_PARENT);
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
        ViewGroup rootView = getWindow().getDecorView().findViewById(R.id.video_player_top_controller);
        rootView.setPadding(0,result,0,0);
    }
}
