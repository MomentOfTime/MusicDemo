package com.example.musicdemo.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicdemo.R;
import com.example.musicdemo.helps.RealmHelp;
import com.example.musicdemo.models.MusicModel;
import com.example.musicdemo.views.PlayMusicView;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class PlayMusicActivity extends BaseActivity {

    public static final String MUSIC_ID = "music_Id";

    private ImageView mIvBg;

    private TextView mTvName,mTvAuthor;

    private PlayMusicView mPlayMusicView;

    private String mMusicId;
    private RealmHelp mRealmHelper;
    private MusicModel mMusicModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        //隐藏statusBar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initData();
        initView();
    }

    private void initData () {
        mMusicId = getIntent().getStringExtra(MUSIC_ID);
        mRealmHelper = new RealmHelp();
        mMusicModel = mRealmHelper.getMusic(mMusicId);
    }

    private void initView() {
        mIvBg = fd(R.id.iv_bg);

        mTvName = fd(R.id.tv_name);
        mTvAuthor = fd(R.id.tv_author);

        //glide-transformations 虚化处理,网络图片
        Glide.with(this)
                .load(mMusicModel.getPoster())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 10)))
                .into(mIvBg);

        mTvName.setText(mMusicModel.getName());
        mTvAuthor.setText(mMusicModel.getAuthor());

        mPlayMusicView = findViewById(R.id.play_music_view);
        mPlayMusicView.setMusicIcon(mMusicModel.getPoster());
        mPlayMusicView.playMusic(mMusicModel.getPath());
    }

    /**
     * 后退按钮点击
     * @param v
     */
    public void onBackClick(View v){
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmHelper.close();
    }
}
