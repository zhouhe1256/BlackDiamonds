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
public class ProjectsArrayModel {
    private int rescode;
    private String msg;
    private String token;
    private long sysTime;
    private int page;
    @JSONCollection(type = ProjectModel.class)
    private List<ProjectModel> data;

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

    public List<ProjectModel> getData() {
        return data;
    }

    public void setData(List<ProjectModel> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public static IContentDecoder<ProjectsArrayModel> decoder = new IContentDecoder.BeanDecoder<ProjectsArrayModel>(
            ProjectsArrayModel.class);

    public static IPromise getProjects(String require,String category,int page){
        return Http.instance().get(ApiUrl.PROJECTS).
                param("require",require).
                param("category",category).
                param("page",page).run();
    }
    public static IPromise getProjectsAll(int page){
        return Http.instance().post(ApiUrl.PROJECTS).
                contentDecoder(decoder).
                param("page",page).run();
    }

}
