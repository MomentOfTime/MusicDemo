package com.example.musicdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.musicdemo.constants.SPConstants;
import com.example.musicdemo.helps.UserHelper;

public class SPUtils {
    /**
     *用户登陆时，利用SharedPreferences保存用户标记（电话号码）
     */
    public static boolean saveUser (Context context,String phone){
        SharedPreferences sharedPreferences =
        context.getSharedPreferences(SPConstants.SP_NAME_USER
                ,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SPConstants.SP_KEY_PHONE,phone);
        boolean result = editor.commit();
        return result;
        }

    /**
     * 验证 是否存在已登录
     */

    public static boolean isLoginUser(Context context){
        boolean result = false;

        SharedPreferences sp = context.getSharedPreferences(SPConstants.SP_NAME_USER
                ,Context.MODE_PRIVATE);

        String phone = sp.getString(SPConstants.SP_KEY_PHONE,"");

        if (!TextUtils.isEmpty(phone)){
            result = true;
            UserHelper.getInstance().setPhone(phone);
        }
        return result;
    }

    /**
     * 删除用户标记
     */
    public static boolean removeUser(Context context){
        SharedPreferences sp = context.getSharedPreferences(SPConstants.SP_KEY_PHONE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(SPConstants.SP_KEY_PHONE);
        boolean result = editor.commit();
        return result;
    }
}
