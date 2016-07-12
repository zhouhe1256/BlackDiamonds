
package com.ljs.constant;


import com.ljs.application.HApplication;
import com.ljs.blackdiamonds.R;
import com.ljs.util.SystemUtil;


public class ApiUrl {

    public static final String VERSION = SystemUtil.getCurrentVersionName(HApplication
            .getInstance());
    public static final String HOST_URL = HApplication.getInstance().getResources()
            .getString(R.string.host);
    public static final String OS = SystemUtil.getVersion();

    public static final String LOGIN = "/app/user/thirdLogin";//用户第三方登录
    public static final String CATEGORIES = "/app/project/getCategories";//获取项目行业分类
    public static final String REQUIRES = "/app/project/getRequires";//获取项目需求
    public static final String PROJECTS = "/app/project/getProjects";//获取项目列表
    public static final String USERINFO = "/app/user/getUser";//获取用户信息
    public static final String UPDATAUSER = "/app/user/updateUser";//用户信息修改
    public static final String BIND_PHONE = "/app/user/bindPhone";//绑定手机号
    public static final String ADDRESS_ALL = "/app/user/getAddresses";//用户获取所有地址
    public static final String LIKE ="/app/user/like";//用户点赞

}
