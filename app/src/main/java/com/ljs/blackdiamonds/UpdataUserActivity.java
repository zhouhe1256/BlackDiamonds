package com.ljs.blackdiamonds;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bjcathay.android.async.Arguments;
import com.bjcathay.android.async.ICallback;
import com.ljs.model.LoginArrayModel;
import com.ljs.singleton.UserInfo;
import com.ljs.util.ToastUtil;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_user);
        initView();
    }

    private void initView() {
        titleView = (TextView) findViewById(R.id.title);
        titleimageView = (ImageView) findViewById(R.id.title_image);
        searchimageView = (ImageView) findViewById(R.id.search);
        nameTextView = (TextView) findViewById(R.id.up_name);
        infoEditText = (EditText) findViewById(R.id.user_info);
        titleimageView.setImageResource(R.drawable.back);

        searchimageView.setVisibility(View.GONE);
        type = getIntent().getStringExtra("type");
        if(type.equals("0")){

        }else if(type.equals("1")){
            titleView.setText("修改昵称");
            nameTextView.setText("请输入新的昵称");
        }else if(type.equals("2")){
            titleView.setText("中国企业跨境并购新趋势");
            nameTextView.setText("请输入公司昵称");
        }else if(type.equals("3")){
            titleView.setText("公司职务");
            nameTextView.setText("请输入公司职务");
        }
    }

    private void initData() {
        content = infoEditText.getText().toString().trim();
        LoginArrayModel.UpdataUserInfoRequst(type,content).done(new ICallback() {
            @Override
            public void call(Arguments arguments) {
                loginArrayModel = arguments.get(0);
                UserInfo.getInstance().setUserInfo(loginArrayModel);
                Log.i("rescode",loginArrayModel.getRescode()+"");
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
                initData();
                break;
        }
    }
}
