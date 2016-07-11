package com.ljs.model;

import com.bjcathay.android.async.IPromise;
import com.bjcathay.android.json.annotation.JSONCollection;
import com.bjcathay.android.remote.Http;
import com.bjcathay.android.remote.IContentDecoder;
import com.ljs.constant.ApiUrl;

import java.util.List;

/**
 * Created by zhouhe on 2016/7/11.
 */
public class AddressArrayModel {
    private int rescode;
    private String msg;
    private String token;
    private long sysTime;
    @JSONCollection(type = AddressModel.class)
    private List<AddressModel> data;

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

    public List<AddressModel> getData() {
        return data;
    }

    public void setData(List<AddressModel> data) {
        this.data = data;
    }

    private static final IContentDecoder<AddressArrayModel> decoder = new IContentDecoder.BeanDecoder<AddressArrayModel>(
            AddressArrayModel.class);

    public static IPromise getAddressRequest(){
        return Http.instance().get(ApiUrl.ADDRESS_ALL).isCache(true).run();
    }
}
