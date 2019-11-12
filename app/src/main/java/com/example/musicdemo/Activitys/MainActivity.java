package com.example.musicdemo.Activitys;

import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicdemo.R;
import com.example.musicdemo.adapters.MusicGridAdapter;
import com.example.musicdemo.adapters.MusicListAdapter;
import com.example.musicdemo.helps.RealmHelp;
import com.example.musicdemo.models.MusicSourceModel;
import com.example.musicdemo.views.GridSpaceItemDecoration;

public class MainActivity extends BaseActivity {

    private RecyclerView mRvGrid;
    private MusicGridAdapter musicGridAdapter;
    private RecyclerView mRvList;
    private MusicListAdapter musicListAdapter;
    private RealmHelp mRealmHelper;
    private MusicSourceModel mMusicSourceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData () {
        mRealmHelper = new RealmHelp();
        mMusicSourceModel = mRealmHelper.getMusicSource();

    }

    private void initView(){

        initNavBar(false,"网易云音乐",true);

        mRvGrid = fd(R.id.rv_grid);
        mRvGrid.setLayoutManager(new GridLayoutManager(this,3));
        //边界
        mRvGrid.addItemDecoration(new GridSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.albumMarginSize),mRvGrid));

        //禁止单独滑动
        mRvGrid.setNestedScrollingEnabled(false);
        musicGridAdapter = new MusicGridAdapter(this , mMusicSourceModel.getAlbum());
        mRvGrid.setAdapter(musicGridAdapter);

        /**
         * 1、 直接定义Recyclerview
         * 2、 计算定义
         */

        mRvList = fd(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        //禁止单独滑动
        mRvList.setNestedScrollingEnabled(false);
        musicListAdapter = new MusicListAdapter(this,mRvList,mMusicSourceModel.getHot());
        mRvList.setAdapter(musicListAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmHelper.close();
    }
}
