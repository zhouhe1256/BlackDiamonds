package com.ljs.blackdiamonds;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bjcathay.android.async.Arguments;
import com.bjcathay.android.async.ICallback;
import com.bjcathay.android.json.JSONUtil;
import com.bjcathay.android.util.Logger;
import com.ljs.application.HApplication;
import com.ljs.constant.ApiUrl;
import com.ljs.model.LoginArrayModel;
import com.ljs.singleton.UserInfo;
import com.ljs.util.PreferencesUtils;

import org.json.JSONObject;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by zhouhe on 2016/7/7.
 */
public class LoginActivity extends Activity implements PlatformActionListener,Handler.Callback {
    private ImageView wxImageView;
    private static final int MSG_AUTH_CANCEL = 2;
    private static final int MSG_AUTH_ERROR = 3;
    private static final int MSG_AUTH_COMPLETE = 4;
    private Handler handler;
    private String type;
    private LoginArrayModel loginArrayModel;
    private HApplication hApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ShareSDK.initSDK(this);
        handler = new Handler(this);
        initView();
    }

    private void initView() {
        hApplication = HApplication.getInstance();
        wxImageView = (ImageView) findViewById(R.id.wx);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.wx:
                type = "1";
                Platform wx = ShareSDK.getPlatform(Wechat.NAME);
                authorize(wx);
                break;
            case R.id.wb:
                type = "3";
                Platform wb = ShareSDK.getPlatform(SinaWeibo.NAME);
                authorize(wb);
                break;
            case R.id.qq:
                type = "2";
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                authorize(qq);
                break;
        }
    }

    private void authorize(Platform plat) {
        plat.setPlatformActionListener(this);
        // 关闭SSO授权
        plat.SSOSetting(true);
        plat.showUser(null);
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        String resjson = JSONUtil.dump(hashMap);
        if (action == Platform.ACTION_USER_INFOR) {
            PlatformDb platDB = platform.getDb();// 获取数平台数据DB
            Message msg = new Message();
            msg.what = MSG_AUTH_COMPLETE;
            msg.obj = new Object[] {
                    platform.getName(), hashMap, platform
            };
            handler.sendMessage(msg);
        }
    }

    @Override
    public void onError(Platform platform, int action, Throwable t) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_ERROR);
        }
        t.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_CANCEL);
        }
    }

    @Override
    public boolean handleMessage(final Message msg) {
        switch (msg.what) {
            case MSG_AUTH_CANCEL: {
                // 取消授权
                Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_ERROR: {
                // 授权失败
                Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_COMPLETE: {
                // 授权成功
                Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT).show();
                Object[] objs = (Object[]) msg.obj;
                Platform platform = (Platform) objs[2];
                PlatformDb platDB = platform.getDb();// 获取数平台数据DB
                final String platName = platform.getName();
                final String platuserid = platDB.getUserId();
                final String platuserName = platDB.getUserName();
                final String platjson = platDB.exportData();
                final String plattoken = platDB.getToken();

                String deviceType = Build.MODEL.split("-")[0] ;
                String deviceId = android.os.Build.SERIAL;
                Log.i("dddadfas",platuserid);
                String operatingSystem = Build.VERSION.RELEASE;
                String osType = "0";
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                String resolution = dm.heightPixels+"*"+dm.widthPixels;
                String openId = platuserid;
                String name = platuserName;
                String icon = platDB.getUserIcon();
                 LoginArrayModel.loginRequst(deviceType,deviceId,operatingSystem,
                         osType,resolution,openId,name,icon,type).done(new ICallback() {
                     @Override
                     public void call(Arguments arguments) {
                         loginArrayModel = arguments.get(0);
                         UserInfo.getInstance().setUserInfo(loginArrayModel);
                         String token = loginArrayModel.getToken();
                         String uid = loginArrayModel.getData().getUid();
                         PreferencesUtils.putString(LoginActivity.this,"token",token);
                         PreferencesUtils.putString(LoginActivity.this,"uid",uid);
                         hApplication.updateApiToken();
                         Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                         startActivity(intent);
                         finish();
                     }
                 }).fail(new ICallback() {
                     @Override
                     public void call(Arguments arguments) {

                     }
                 });


            }
            break;
        }
        return false;
    }
}
