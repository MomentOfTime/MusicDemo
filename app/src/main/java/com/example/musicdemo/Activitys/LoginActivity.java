package com.example.musicdemo.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.musicdemo.R;
import com.example.musicdemo.utils.UserUtils;
import com.example.musicdemo.views.InputView;

public class LoginActivity extends BaseActivity {

    private InputView mInputPhone,mInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    /*
    初始化view
     */
    private void initView(){

        initNavBar(false,"登陆",false);

        mInputPhone = fd(R.id.input_phone);
        mInputPassword = fd(R.id.input_password);
    }
    /**
     * 注册点击 跳转
     */
    public void onRegisterClick(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    /*
    登陆
     */
    public void onCommitClick(View v){
        String phone = mInputPhone.getInputStr();
        String password = mInputPassword.getInputStr();
        if (!UserUtils.validateLogin(this,phone,password)){
            return;
        }
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
