package com.ljs.model;

import com.bjcathay.android.async.IPromise;
import com.bjcathay.android.json.annotation.JSONCollection;
import com.bjcathay.android.remote.Http;
import com.bjcathay.android.remote.IContentDecoder;
import com.ljs.constant.ApiUrl;

import java.util.List;

/**
 * Created by zhouhe on 2016/7/6.
 */
public class CategoriesArrayModel {
    private int rescode;
    private String msg;
    private String token;
    private long sysTime;
    @JSONCollection(type = CategoriesModel.class)
    private List<CategoriesModel> data;

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

    public List<CategoriesModel> getData() {
        return data;
    }

    public void setData(List<CategoriesModel> data) {
        this.data = data;
    }
    private static IContentDecoder<CategoriesArrayModel> decoder = new IContentDecoder.BeanDecoder<CategoriesArrayModel>(
            CategoriesArrayModel.class);

    public static IPromise getCategories(String apiurl){
        return Http.instance().get(apiurl).contentDecoder(decoder).isCache(true).run();
    }
}

