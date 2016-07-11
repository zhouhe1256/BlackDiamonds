package com.ljs.blackdiamonds;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bjcathay.android.async.Arguments;
import com.bjcathay.android.async.ICallback;
import com.ljs.model.LoginArrayModel;
import com.ljs.singleton.UserInfo;
import com.ljs.util.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhouhe on 2016/7/8.
 */
public class UpdataUserActivity extends Activity{
    private String type;
    private String content;
    private EditText infoEditText;
    private LoginArrayModel loginArrayModel;
    private ImageView titleimageView;
    private ImageView searchimageView;
    private TextView titleView;
    private TextView nameTextView;
    private EditText iphoneEditext;
    private LinearLayout phoneLinearLayout;
    private Button iphoneButton;
    private TimerTask timerTask;
    private Timer timer;
    private int time = 60;
    private static final int IPHONE_CODE = 0X000;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case IPHONE_CODE:
                    if(time>0){
                        time--;
                        iphoneButton.setText(time+"");
                        iphoneButton.setFocusable(false);
                    }else{
                        iphoneButton.setText("获取验证码");
                        iphoneButton.setFocusable(true);
                    }
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_user);
        initView();
        setListeners();
    }


    private void initView() {
        iphoneEditext = (EditText) findViewById(R.id.phone_code);
        titleView = (TextView) findViewById(R.id.title);
        titleimageView = (ImageView) findViewById(R.id.title_image);
        searchimageView = (ImageView) findViewById(R.id.search);
        nameTextView = (TextView) findViewById(R.id.up_name);
        infoEditText = (EditText) findViewById(R.id.user_info);
        phoneLinearLayout = (LinearLayout) findViewById(R.id.user_phone);
        iphoneButton = (Button) findViewById(R.id.iphone_button);
        timer = new Timer();
        titleimageView.setImageResource(R.drawable.back);

        searchimageView.setVisibility(View.GONE);
        type = getIntent().getStringExtra("type");
        if(type.equals("0")){

        }else if(type.equals("1")){
            titleView.setText("修改昵称");
            nameTextView.setText("请输入新的昵称");
            phoneLinearLayout.setVisibility(View.GONE);
        }else if(type.equals("2")){
            titleView.setText("中国企业跨境并购新趋势");
            nameTextView.setText("请输入公司昵称");
            phoneLinearLayout.setVisibility(View.GONE);
        }else if(type.equals("3")){
            titleView.setText("公司职务");
            nameTextView.setText("请输入公司职务");
            phoneLinearLayout.setVisibility(View.GONE);
        }else {
            titleView.setText("手机号修改");
            nameTextView.setText("请输入新的手机号");
            phoneLinearLayout.setVisibility(View.VISIBLE);
        }


    }

    private void initData() {
        content = infoEditText.getText().toString().trim();
        LoginArrayModel.UpdataUserInfoRequst(type,content).done(new ICallback() {
            @Override
            public void call(Arguments arguments) {
                loginArrayModel = arguments.get(0);
                UserInfo.getInstance().setUserInfo(loginArrayModel);
                if(loginArrayModel.getRescode()==100){
                    ToastUtil.toast(UpdataUserActivity.this,getString(R.string.user_success));
                    finish();
                }else{
                    ToastUtil.toast(UpdataUserActivity.this,loginArrayModel.getMsg());
                }
            }
        }).fail(new ICallback() {
            @Override
            public void call(Arguments arguments) {
                ToastUtil.toast(UpdataUserActivity.this,getString(R.string.user_success));
            }
        });
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.info_affirm:
                if(type.equals("100")){
                    phoneCodeRequest();
                }else{
                    initData();
                }
                break;
            case R.id.iphone_button:
                setphoneCode();
                break;
        }
    }

    public void phoneCodeRequest(){
        String phone = infoEditText.getText().toString().trim();
        String phoneCode = iphoneEditext.getText().toString().trim();
        LoginArrayModel.binPhone(phone,phoneCode).done(new ICallback() {
            @Override
            public void call(Arguments arguments) {
                loginArrayModel = arguments.get(0);
                UserInfo.getInstance().setUserInfo(loginArrayModel);

            }
        }).fail(new ICallback() {
            @Override
            public void call(Arguments arguments) {

            }
        });
    }

    private void setListeners() {
        titleimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setphoneCode() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.what = IPHONE_CODE;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(timerTask,1000,1000);

    }
}
