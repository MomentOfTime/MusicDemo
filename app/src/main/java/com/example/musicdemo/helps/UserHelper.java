package com.example.musicdemo.helps;


/**
 *  1、用户登陆
 *          1、当用户没有登陆过，使用sharedPreferences 保存登陆用户用户标记(电话)
 *          2、利用全局单例类UserHelper保存登陆信息
 *                  1、用户登陆后
 *                  2、打开应用程序、检测sharedPreferences中是否存在标记
 *                  如果存在 则为userhelp赋值，并进入主页
 *  2、用户退出
 *          1、删除sharedPreferences保存的标记,退出登陆页面
 */
public class UserHelper {

    private static UserHelper instance;

    private UserHelper(){}

    public static UserHelper getInstance(){
        if (instance == null){
            synchronized (UserHelper.class){
                if (instance == null){
                    instance = new UserHelper();
                }
            }
        }
        return instance;
    }

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
