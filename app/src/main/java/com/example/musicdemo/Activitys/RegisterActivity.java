package com.example.musicdemo.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.musicdemo.R;
import com.example.musicdemo.utils.UserUtils;
import com.example.musicdemo.views.InputView;

public class RegisterActivity extends BaseActivity {

    private InputView mInputPhone,mInputPassword,mInputPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView(){
        initNavBar(true,"注册",false);
        mInputPhone = fd(R.id.input_phone);
        mInputPassword = fd(R.id.input_password);
        mInputPasswordConfirm = fd(R.id.input_password_confirm);
    }

    /**
     * 注册点击
     * 1、合法验证
     *      1、手机号是否正确
     *      2、是否输入密码 和 确认密码是否一致
     *      3、手机号是否被注册
     * 2、保存用户输入的手机号和密码 (MD5 加密)
     */
    public void onRegisterClick(View v){
        String phone = mInputPhone.getInputStr();
        String password = mInputPassword.getInputStr();
        String passwordConfirm = mInputPasswordConfirm.getInputStr();
        boolean res = UserUtils.registerUser(this,phone,password,passwordConfirm);
        if (res){
            onBackPressed();
        }
        //后退页面
    }
}
