package com.example.musicdemo.helps;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class MediaPlayerHelp {

    private static MediaPlayerHelp instance;
    private Context mContext;
    private MediaPlayer mMeidaPlayer;
    private String mPath;
    private OnMediaPlayerHelperListener onMediaPlayerHelperListener;

    public void setOnMediaPlayerHelperListener(OnMediaPlayerHelperListener onMediaPlayerHelperListener) {
        this.onMediaPlayerHelperListener = onMediaPlayerHelperListener;
    }

    public OnMediaPlayerHelperListener getOnMediaPlayerHelperListener() {
        return onMediaPlayerHelperListener;
    }

    public static MediaPlayerHelp getInstance(Context context){

        if (instance == null){
            synchronized (MediaPlayerHelp.class){
                if (instance == null){
                    instance = new MediaPlayerHelp(context);
                }
            }
        }
        return instance;
    }

    private MediaPlayerHelp (Context context) {
        mContext = context;
        mMeidaPlayer = new MediaPlayer();
    }

    /**
     * 1、setPath:当前需要播放的音乐
     * 2、start：播放
     * 3、pause：暂停
     *
     *
     */
    public void setPath(String path){
        /**
         * 1、音乐正常播放,重置播放
         * 2、设置播放音乐路径
         * 3、准备播放
         */

        mPath = path;
        /**
         * (错误逻辑！)当音乐进行切换的时候，如果音乐处于播放状态，
         * 那么我们就去重置音乐的状态，而如果 音乐没有处于播放状态的话(暂停),那么就不去重置播放状态
         *
         */

        if (mMeidaPlayer.isPlaying()){
            mMeidaPlayer.reset();
        }
//        2、设置播放音乐路径
        try {
            mMeidaPlayer.setDataSource(mContext, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        3、准备播放
        mMeidaPlayer.prepareAsync();
        mMeidaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (onMediaPlayerHelperListener != null){
                    onMediaPlayerHelperListener.onPrepared(mediaPlayer);
                }
            }
        });
    }

    public String getPath(){
        return mPath;
    }

    /**
     *
     */
    public void start(){
        if (mMeidaPlayer.isPlaying())return;
        mMeidaPlayer.start();
    }

    /**
     *
     */
    public void pause(){
       mMeidaPlayer.pause();
    }

    public interface OnMediaPlayerHelperListener{
        void onPrepared(MediaPlayer mp);
    }

}

