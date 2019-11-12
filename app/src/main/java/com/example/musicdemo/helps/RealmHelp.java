package com.example.musicdemo.helps;

import android.content.Context;

import com.example.musicdemo.migration.Migration;
import com.example.musicdemo.models.AlbumModel;
import com.example.musicdemo.models.MusicModel;
import com.example.musicdemo.models.MusicSourceModel;
import com.example.musicdemo.models.UserModel;
import com.example.musicdemo.utils.DataUtils;

import java.io.FileNotFoundException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelp {

    private Realm mRealm;

    public RealmHelp() {
        mRealm = Realm.getDefaultInstance();

    }

    /**
     * Realm 数据库迁移
     * 因为数据库内发生结构性变化（模型或者模型中的字段发生了新增、修改、删除）
     */

    /**
     * 告诉Realm数据需要迁移，并且为Realm配置
     *
     */

    public static void migration () {
        RealmConfiguration conf = getRealmConf();
        //设置最新配置
        Realm.setDefaultConfiguration(conf);
        //告诉Realm需要迁移
        try {
            Realm.migrateRealm(conf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回RealmConfiguration
     * @return
     */

    private static RealmConfiguration getRealmConf () {
        return new RealmConfiguration.Builder().schemaVersion(1)
                .migration(new Migration())
                .build();
    }

    /**
     * 关闭数据库
     */
    public void close(){
        if (mRealm != null && !mRealm.isClosed()){
            mRealm.close();
        }
    }

    /**
     * 保存用户信息
     */
    public void saveUser (UserModel userModel){
        //开启事务
        mRealm.beginTransaction();
//        mRealm.insert(userModel);
        mRealm.insertOrUpdate(userModel);
        //提交事务
        mRealm.commitTransaction();
    }

    /**
     *返回所有用户
     */
    public List<UserModel> getAllUser(){
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        RealmResults<UserModel> results = query.findAll();
        return results;
    }

    /**
     * 验证用户信息
     */
    public boolean validateUser (String phone,String password){
        boolean result = false;
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        query = query.equalTo("phone",phone)
                .equalTo("password",password);
        UserModel userModel = query.findFirst();
        if (userModel != null){
            result = true;
        }
        return result;
    }

    /**
     * 获取当前用户
     */

    public UserModel getUser(){
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        UserModel userModel = query.equalTo("phone",UserHelper.getInstance().getPhone()).findFirst();
        return userModel;
    }

    /**修改密码
     *
     */
    public void changePassword(String password){
        UserModel userModel = getUser();
        mRealm.beginTransaction();
        userModel.setPassword(password);
        mRealm.commitTransaction();
    }

    /**
     * 1、用户登陆存放数据
     * 2、用户退出，删除数据
     *
     */

    /**
     * 保存音乐源数据
     */
    public void seMusicSource (Context context) {

        String musicSourceJson = DataUtils.getJsonFromAssets(context , "songdata.json");
        mRealm.beginTransaction();
        mRealm.createObjectFromJson(MusicSourceModel.class , musicSourceJson);
        mRealm.commitTransaction();
    }

    /**
     * 删除 音乐源
     * 1、RealmResult delete
     * 2、Realm delete 删除这个模型下所有的数据
     *
     */
    public void removeMusicSource () {
        mRealm.beginTransaction();
        mRealm.delete(MusicSourceModel.class);
        mRealm.delete(MusicModel.class);
        mRealm.delete(AlbumModel.class);
        mRealm.commitTransaction();
    }


    /**
     * 返回音乐数据
     * @return
     */
    public MusicSourceModel getMusicSource () {
        return mRealm.where(MusicSourceModel.class).findFirst();
    }

    /**
     * 返回歌单
     * @param albumId
     * @return
     */
    public AlbumModel getAlbum (String albumId) {
        return mRealm.where(AlbumModel.class).equalTo("albumId",albumId).findFirst();
    }

    /**
     * 返回歌曲
     * @param musicId
     * @return
     */
    public MusicModel getMusic (String musicId) {
        return mRealm.where(MusicModel.class).equalTo("musicId",musicId).findFirst();
    }
}
