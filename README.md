# MusicDemo

[主界面](https://sm.ms/image/g1pMeq42YZfhxSN)

[音乐播放界面](https://sm.ms/image/a9QRkBvcyIfhKjV)

简单记录路上的bug

1、当音乐进行切换的时候，如果音乐处于播放状态会崩

MediaPlayerHelp.java

```java
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
```

2、如果手机 Realm数据库中存在旧数据，并且登陆时候显示的是旧数据，则需要退出账号再重试。**理应进入的时候，直接更新**。