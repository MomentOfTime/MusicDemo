package com.example.musicdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.musicdemo.Activitys.LoginActivity;
import com.example.musicdemo.R;
import com.example.musicdemo.helps.RealmHelp;
import com.example.musicdemo.helps.UserHelper;
import com.example.musicdemo.models.UserModel;

import java.util.List;

import io.realm.Realm;

public class UserUtils {

    /**
     * 验证登陆用户
     */
    public static boolean validateLogin(Context context,String phone,String password){
        //简单
//        RegexUtils.isMobileSimple(phone);
//        //精确
//        RegexUtils.isMobileExact(phone);
        if(!RegexUtils.isMobileExact(phone)){
            Toast.makeText(context,"无效手机号",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(context,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        /**
         * 1、用户当前手机号是否已经被注册
         * 2、输入的手机号与密码是否匹配
         */
        if (!UserUtils.userExistFromPhone(phone)){
            Toast.makeText(context,"该手机号未注册",Toast.LENGTH_SHORT).show();
            return false;
        }

        RealmHelp realmHelp = new RealmHelp();
        boolean result = realmHelp.validateUser(phone,EncryptUtils.encryptMD5ToString(password));
        if (!result){
            Toast.makeText(context,"手机号或者密码不正确",Toast.LENGTH_SHORT).show();
            return false;
        }

        //保存用户登陆记录
        boolean isSave = SPUtils.saveUser(context , phone);
        if (!isSave){
            Toast.makeText(context,"系统错误，自动登录未保存",Toast.LENGTH_SHORT).show();
            return false;
        }
//      保存标记，在全局单例中
        UserHelper.getInstance().setPhone(phone);

        realmHelp.seMusicSource(context);

        realmHelp.close();
        //保存音乐数据源
        return true;
    }
    /**
     * 退出登陆 删除自动登录 ,清除Activity栈
     */
    public static void logout (Context context) {
        //删除SharedPreferences
        boolean isRemove = SPUtils.removeUser(context);
        if (!isRemove){
            Toast.makeText(context,"系统错误，请稍后重试",Toast.LENGTH_SHORT).show();
            return;
        }
        //删除数据源
        RealmHelp realmHelp = new RealmHelp();
        realmHelp.removeMusicSource();;
        realmHelp.close();
        Intent intent = new Intent(context, LoginActivity.class);
        //清理task栈，新生成task栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //定义Activity跳转动画
        ((Activity)context).overridePendingTransition(R.anim.open_enter,R.anim.open_exit);
    }

    /**
     * 注册用户
     * @param context
     * @param phone
     * @param password
     * @param passwordComfirm
     */
    public static boolean registerUser(Context context,String phone,String password,String passwordComfirm){
        if(!RegexUtils.isMobileExact(phone)){
            Toast.makeText(context,"无效手机号",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isEmpty(password) || !password.equals(passwordComfirm)){
            Toast.makeText(context , "请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        //用户输入的手机号是否被注册
        /**
         * 1、通过Realm获取到当前已经注册的所有用户
         * 2、根据输入手机号匹配，如果匹配则已被注册
         */
        if (UserUtils.userExistFromPhone(phone)){
            Toast.makeText(context,"该手机号已存在",Toast.LENGTH_SHORT).show();
            return false;
        }

        UserModel userModel = new UserModel();
        userModel.setPhone(phone);
        userModel.setPassword(EncryptUtils.encryptMD5ToString(password));

        saveUser(userModel);
        Toast.makeText(context,"注册成功！",Toast.LENGTH_SHORT).show();
        return true;
    }

    /**
     * 保存到数据库
     * @param userModel
     */
    public static void saveUser (UserModel userModel) {
        RealmHelp realmHelp = new RealmHelp();
        realmHelp.saveUser(userModel);
        realmHelp.close();
    }
    /**
     * 根据手机号判断用户是否存在
     */
    public static boolean userExistFromPhone(String phone){
        boolean result = false;

        RealmHelp realmHelp = new RealmHelp();
        List<UserModel> allUser = realmHelp.getAllUser();
        System.out.println("验证userExistFromPhone:"+allUser.toArray().toString());
        if (allUser.size() <= 0){

        }
        else {
            for (UserModel userModel : allUser)
            {
                System.out.println(userModel.getPassword()+userModel.getPhone());
                if (userModel.getPhone() != null && userModel.getPhone().equals(phone)){
                    //当前手机号存在
                    result = true;
                    break;
                }
            }
        }
        realmHelp.close();
        return result;
    }

    /**
     * 验证是否存在已登陆
     */
    public static boolean validateUserLogin (Context context){
        return SPUtils.isLoginUser(context);
    }

    /**
     * 修改密码
     * 1、数据验证
     *          1、原密码输入
     *          2、新密码是否与原密码相同
     *          3、原密码输入是否正确
     *          1、获取 当前登录的用户模型
     *          2、根据用户模型匹配密码
     * 2、利用Realm 自动更新 修改密码
     */
    public static boolean changePassword(Context context,String oldPassword , String password ,String passwordConfirm){
        if (TextUtils.isEmpty(oldPassword)){
            Toast.makeText(context,"请输入原密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password) || !password.equals(passwordConfirm)){
            Toast.makeText(context,"请确认密码",Toast.LENGTH_SHORT).show();
            return false;
        }
//验证原密码是否正确
        RealmHelp realmHelp = new RealmHelp();
        UserModel userModel = realmHelp.getUser();
        if (!EncryptUtils.encryptMD5ToString(oldPassword).equals(userModel.getPassword())){
            Toast.makeText(context,"原密码不正确",Toast.LENGTH_SHORT).show();
            return false;
        }
        realmHelp.changePassword(EncryptUtils.encryptMD5ToString(password));
        realmHelp.close();
        return true;
    }
}
