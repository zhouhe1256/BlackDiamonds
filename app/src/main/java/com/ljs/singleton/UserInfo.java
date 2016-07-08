package com.ljs.singleton;

import com.ljs.model.LoginArrayModel;

/**
 * Created by zhouhe on 2016/7/8.
 */
public class UserInfo {
    private LoginArrayModel loginArrayModel;
    private static UserInfo instance = null;

    public static UserInfo getInstance() {
        if (instance == null) {
               instance = new UserInfo();
            }
            return instance;
          }

    public void setUserInfo(LoginArrayModel loginArrayModel){
        this.loginArrayModel = loginArrayModel;
    }

    public LoginArrayModel getUserInfo(){
        return  loginArrayModel;
    }
}
