package com.example.musicdemo.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.musicdemo.R;
import com.example.musicdemo.helps.UserHelper;
import com.example.musicdemo.utils.UserUtils;

public class MeActivity extends BaseActivity {

    private TextView mTvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        initView();
    }

    private void initView(){

        initNavBar(true,"个人中心",false);

        mTvUser = fd(R.id.tv_user);
        mTvUser.setText("用户名：" + UserHelper.getInstance().getPhone());
    }

    /**
     * 修改密码
     */
    public void onChangeClick(View v){
        startActivity(new Intent(this,ChangePasswordActivity.class));
    }

    /**
     * 退出登录
     * @param v
     */

    public void onLogoutClick(View v){
        UserUtils.logout(this);
    }
}
