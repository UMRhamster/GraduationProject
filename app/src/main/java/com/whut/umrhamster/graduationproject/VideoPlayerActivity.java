package com.whut.umrhamster.graduationproject;

import android.content.Context;
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
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.whut.umrhamster.graduationproject.adapter.VideoPlayerFragmentPagerAdapter;
import com.whut.umrhamster.graduationproject.fragment.VideoPlayerBriefFragment;
import com.whut.umrhamster.graduationproject.fragment.VideoPlayerCommentFragment;
import com.whut.umrhamster.graduationproject.model.bean.InfoGroupBean;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.presenter.ITimeKeepPresenter;
import com.whut.umrhamster.graduationproject.presenter.TimeKeepPresenter;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.utils.other.TimeUtil;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.ITimeKeepView;

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

public class VideoPlayerActivity extends AppCompatActivity implements IInitWidgetView,ITimeKeepView {
    private final static int TAG = 0;

    private RelativeLayout topController;       //顶部播放控制器
    private ConstraintLayout bottomController;  //底部播放控制器

    private TextView tvTopTitle;
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
    //presenter
    private ITimeKeepPresenter timeKeepPresenter;

    Student student;

    MyHandler handler;

    //更新视频播放进度
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            handler.sendEmptyMessage(TAG);
//            Log.d("timer",""+ijkMediaPlayer.getCurrentPosition());
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
                        activity.tvTime.setText(TimeUtil.millint2String(activity.ijkMediaPlayer.getCurrentPosition())+"/"+TimeUtil.millint2String(activity.video.getTotaltime()));
//                        removeMessages(TAG);
//                        sendEmptyMessageDelayed(TAG,1000);
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdaptionUtil.setCustomDensity(VideoPlayerActivity.this,getApplication());
        setContentView(R.layout.activity_video_player);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        video = getIntent().getParcelableExtra("video");
        initView();
        initEvent();
//        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        scheduledExecutorService.

//        mWakeLock.acquire(); // in onPause() call
//
//        mWakeLock.release();
    }

    @Override
    public void initView() {
        view = findViewById(R.id.video_player_bg);
        textureView = findViewById(R.id.video_player_txv);
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
    }

    public void initData(){
        student = SPUtil.loadStudent(VideoPlayerActivity.this);
        timeKeepPresenter = new TimeKeepPresenter(this);
        seekBar.setMax(video.getTotaltime());
    }

    @Override
    public void initEvent() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Log.d("test",""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                Log.d("testsss",""+seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("aaa",""+ijkMediaPlayer.getCurrentPosition()+" "+ijkMediaPlayer.getDuration()+" "+seekBar.getProgress());
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
                if (seeController){
                    topController.setVisibility(View.INVISIBLE);
                    bottomController.setVisibility(View.INVISIBLE);
                    seeController = false;
                }else {
                    topController.setVisibility(View.VISIBLE);
                    bottomController.setVisibility(View.VISIBLE);
                    seeController = true;
                }
            }
        });
    }

    public void createPlayer(){
        if (ijkMediaPlayer == null) {
            ijkMediaPlayer = new IjkMediaPlayer();
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);
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
        stopCountTimer();
        if (student != null){
            timeKeepPresenter.doUploadTimeKeep(student.getId(),video.getClassify().getId(),delay);
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (ijkMediaPlayer != null && ijkMediaPlayer.isPlaying()){
            ijkMediaPlayer.pause();
            ivPlay.setImageResource(R.drawable.play);
            isPlay = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        release();
        isDestroy = false;
        timer.cancel();
    }
}
