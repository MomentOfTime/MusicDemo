package com.example.musicdemo.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.musicdemo.R;
import com.example.musicdemo.utils.UserUtils;

import java.util.Timer;
import java.util.TimerTask;

//1、延迟3s 后跳转
public class WelcomeActivity extends BaseActivity {

    Timer mTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }
    private void init(){
        final boolean isLogin = UserUtils.validateUserLogin(this);
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                Log.e("WelcomeActivity","当前线程为："+Thread.currentThread());
//                toMain();
                if (isLogin){
                    toMain();
                }else{
                    toLogin();
                }
//                toLogin();
            }
        },3*1000);
    }

    private void toMain(){
        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void toLogin(){
        Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
