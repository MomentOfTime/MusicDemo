package com.example.musicdemo.views;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.musicdemo.R;
import com.example.musicdemo.helps.MediaPlayerHelp;

public class PlayMusicView extends FrameLayout {

    private Context mContext;
    private MediaPlayerHelp mMediaPlayerHelp;
    private View mView;
    private ImageView mIvIcon;
    private String mPath;
    private boolean isPlaying;

    private FrameLayout mFlPlayMusic;
    private ImageView mIvNeedle, mIvPlay;

    private Animation mPlayMusicAnim,mPlayNeedleAnim,mStopNeedleAnim;

    public PlayMusicView(Context context) {
        super(context);
        init(context);
    }

    public PlayMusicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayMusicView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        //MediaPlayer
        mContext = context;

        mView = LayoutInflater.from(mContext).inflate(R.layout.play_music , this, false);

        mFlPlayMusic =   mView.findViewById(R.id.fl_play_music);

        mFlPlayMusic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                trugger();
            }
        });

        mIvIcon = mView.findViewById(R.id.iv_icon);

        mIvNeedle = mView.findViewById(R.id.iv_needle);

        mIvPlay = mView.findViewById(R.id.iv_play);

        /**
         *  定义所需要执行的动画
         *  2、光盘转动
         *  3、指针指向光盘
         *  4、指针离开光盘
         *
         *  startAnimation
         */

        mPlayMusicAnim = AnimationUtils.loadAnimation(mContext,R.anim.play_music_anim);

        mPlayNeedleAnim = AnimationUtils.loadAnimation(mContext,R.anim.play_needle_anim);

        mStopNeedleAnim = AnimationUtils.loadAnimation(mContext,R.anim.stop_needle_anim);

        addView(mView);

        mMediaPlayerHelp = MediaPlayerHelp.getInstance(mContext);

    }


    /**
     * 切换播放状态
     */

    private void trugger(){
        if (isPlaying){
            stopMusic();
        }else{
            playMusic(mPath);
        }
    }

    /**
     * 播放音乐
     */

    public void playMusic (String path) {
        mPath = path;
        isPlaying = true;
        mIvPlay.setVisibility(View.GONE);
        mFlPlayMusic.startAnimation(mPlayMusicAnim);
        mIvNeedle.startAnimation(mPlayNeedleAnim);

        /**
         * 1、判断音乐是否是正在播放
         * 2、如果当前正在播放，直接执行start
         * 3、如果当前播放音乐是不需要的，那么调用setpath
         *
         */
        if (mMediaPlayerHelp.getPath() != null
                && mMediaPlayerHelp.getPath().equals(path)){
            mMediaPlayerHelp.start();
        }else{
            mMediaPlayerHelp.setPath(path);
            mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayerHelp.start();
                }
            });
        }
    }

    /**
     * 停止播放
     */

    public void stopMusic () {
        isPlaying = false;
        mIvPlay.setVisibility(View.VISIBLE);
        mFlPlayMusic.clearAnimation();
        mIvNeedle.startAnimation(mStopNeedleAnim);
        mMediaPlayerHelp.pause();
    }

    /**
     *设置光盘中显示的音乐封面图片
     */
    public void setMusicIcon (String icon) {
        Glide.with(mContext)
                .load(icon)
                .into(mIvIcon);
    }

}
