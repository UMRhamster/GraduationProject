package com.whut.umrhamster.graduationproject.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.widget.FrameLayout;

import android.widget.MediaController.MediaPlayerControl;

import java.util.Map;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class IjkVideoView extends FrameLayout implements MediaPlayerControl {

    private String TAG = "IjkVideoView";

    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;

    private String mPath;        //视频地址
    private IjkMediaPlayer mMediaPlayer = null;   //有ijkplayer提供，用于播放视频
    private Surface mSurface;
    private Map<String,String> mHeader;


    private Context mAppContext;
    private TextureView mTextureView;
    private Context context;


    private boolean mEnableMediaCodec = false;



    public IjkVideoView(@NonNull Context context) {
        super(context);
        initVideoView(context);
    }

    public IjkVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initVideoView(context);
    }

    public IjkVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView(context);
    }

    public IjkVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initVideoView(context);
    }

    private void initVideoView(Context context){
        mAppContext = context.getApplicationContext();
        this.context = context;
        setBackgroundColor(Color.BLACK);
        createTextureView();
    }

    private void createTextureView(){
        mTextureView = new TextureView(context);
        mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                mSurface = new Surface(surface);
                mMediaPlayer.setSurface(mSurface);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                mMediaPlayer.setSurface(null);
                mSurface.release();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
    }

//    创建player
    private void createPlayer(){
        if (mMediaPlayer == null){
            mMediaPlayer = new IjkMediaPlayer();
//            ((IjkMediaPlayer) mMediaPlayer).setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 1);
            mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"start-on-prepare",0);
            mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);  //0-画面质量高，解码开销大；48-画面质量差，解码开销小
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            setEnableMediaCodec(mMediaPlayer,mEnableMediaCodec);
        }
    }

    //设置是否开启硬解码
    private void setEnableMediaCodec(IjkMediaPlayer iMediaPlayer, boolean isEnable){
        int value = isEnable ? 1: 0;
        iMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"mediacodec",value);  //开启硬解码
        iMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"mediacodec-auto-rotate",value);
        iMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"mediacodec-handle-resolution-change",value);
    }

    public void setmEnableMediaCodec(boolean isEnable){
        this.mEnableMediaCodec = isEnable;
    }

    public void setPath(String path){
        setPath(path,null);
    }

    public void setPath(String path, Map<String,String> header){
        this.mPath = path;
        this.mHeader = header;
    }
    public void load(){
        if (mMediaPlayer != null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        createPlayer();
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
