package com.ljs.blackdiamonds;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjcathay.android.view.ImageViewAdapter;
import com.ljs.application.HApplication;
import com.ljs.model.LoginArrayModel;
import com.ljs.singleton.UserInfo;
import com.ljs.util.PreferencesUtils;
import com.ljs.view.MyListView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhouhe on 2016/7/8.
 */
public class UserInfoActivity extends Activity{
    private CircleImageView headImageView;
    private TextView userName;
    private TextView companyTitle;
    private TextView companyName;
    private TextView phoneNum;
    private ImageView titleimageView;
    private ImageView searchimageView;
    private TextView titleView;
    private LoginArrayModel loginArrayModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initData();
        setListeners();
    }


    private void initData() {
        if(UserInfo.getInstance().getUserInfo()!=null){
            loginArrayModel = UserInfo.getInstance().getUserInfo();
            userName.setText(loginArrayModel.getData().getName());
            ImageViewAdapter.adapt(headImageView,loginArrayModel.getData().getIcon(),R.drawable.head,true);
            companyTitle.setText(loginArrayModel.getData().getCompanyTitle());

            companyName.setText(loginArrayModel.getData().getCompanyName());
            if(loginArrayModel.getData().getPhoneNum()!=null||
                    loginArrayModel.getData().getPhoneNum().equals("")){
                phoneNum.setText(loginArrayModel.getData().getCompanyTitle());
            }
        }
    }

    private void initView() {
        titleView = (TextView) findViewById(R.id.title);
        titleimageView = (ImageView) findViewById(R.id.title_image);
        searchimageView = (ImageView) findViewById(R.id.search);
        titleView.setText("路演英雄");
        titleimageView.setImageResource(R.drawable.back);
        searchimageView.setVisibility(View.GONE);

        headImageView = (CircleImageView) findViewById(R.id.user_head_image);
        userName = (TextView) findViewById(R.id.user_name);
        companyTitle = (TextView) findViewById(R.id.user_pj_name);
        companyName = (TextView) findViewById(R.id.user_gs_name);
        phoneNum = (TextView) findViewById(R.id.user_phone);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_user:
                PreferencesUtils.clearData(this);
                finish();
                break;
        }
    }

    private void setListeners() {
        titleimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
