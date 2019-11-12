package com.example.musicdemo.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.musicdemo.R;
import com.example.musicdemo.adapters.MusicListAdapter;
import com.example.musicdemo.helps.RealmHelp;
import com.example.musicdemo.models.AlbumModel;

public class AlbumActivity extends BaseActivity {

    public static final String ALBUM_ID= "albumId";

    private RecyclerView mRvList;
    private MusicListAdapter mAdapter;

    private String mAlbumId;
    private RealmHelp mRealmHelper;

    private AlbumModel mAlbumMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        initData();
        initView();
    }

    private void initData () {
        mAlbumId = getIntent().getStringExtra(ALBUM_ID);
        mRealmHelper = new RealmHelp();
        mAlbumMode = mRealmHelper.getAlbum(mAlbumId);
    }

    private void initView(){
        initNavBar(true,"专辑列表",false);

        mRvList = fd(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAdapter = new MusicListAdapter(this,null,mAlbumMode.getList());
        mRvList.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmHelper.close();
    }
}
