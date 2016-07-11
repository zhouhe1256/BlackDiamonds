package com.ljs.model;

import com.bjcathay.android.async.IPromise;
import com.bjcathay.android.json.annotation.JSONCollection;
import com.bjcathay.android.remote.Http;
import com.bjcathay.android.remote.IContentDecoder;
import com.ljs.constant.ApiUrl;

import java.util.List;

/**
 * Created by zhouhe on 2016/7/7.
 */
public class LoginArrayModel {
    private int rescode;
    private String msg;
    private String token;
    private long sysTime;
    @JSONCollection(type = LoginModel.class)
    private LoginModel data;

    public int getRescode() {
        return rescode;
    }

    public void setRescode(int rescode) {
        this.rescode = rescode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getSysTime() {
        return sysTime;
    }

    public void setSysTime(long sysTime) {
        this.sysTime = sysTime;
    }

    public LoginModel getData() {
        return data;
    }

    public void setData(LoginModel data) {
        this.data = data;
    }

    public static IContentDecoder<LoginArrayModel> decoder = new IContentDecoder.BeanDecoder<LoginArrayModel>(
            LoginArrayModel.class);

    public static IPromise loginRequst(String deviceType,String deviceId,String operatingSystem,
                                       String osType,String resolution,
                                       String openId,String name,
                                       String icon,String type){

        return Http.instance().get(ApiUrl.LOGIN).param("deviceType",deviceType)
                .param("operatingSystem",operatingSystem)
                .param("deviceId",deviceId)
                .param("osType",osType)
                .param("resolution",resolution)
                .param("openId",openId)
                .param("name",name)
                .param("icon",icon)
                .param("type",type).contentDecoder(decoder).isCache(false).run();
    }

    public static IPromise userInfoRequst(String token,String uid){

        return Http.instance().get(ApiUrl.USERINFO)
                .param("token",token)
                .param("uid",uid).contentDecoder(decoder).isCache(true).run();
    }

    public static IPromise UpdataUserInfoRequst(String type,String content){

        return Http.instance().get(ApiUrl.USERINFO)
                .param("type",type)
                .param("content",content).contentDecoder(decoder).isCache(true).run();
    }
}
