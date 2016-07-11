package com.ljs.blackdiamonds;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.bjcathay.android.async.Arguments;
import com.bjcathay.android.async.ICallback;
import com.ljs.model.LoginArrayModel;
import com.ljs.util.ToastUtil;

/**
 * Created by zhouhe on 2016/7/8.
 */
public class UpdataUserActivity extends Activity{
    private String type;
    private String content;
    private EditText infoEditText;
    private LoginArrayModel loginArrayModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_user);
        initView();
        initData();
    }

    private void initView() {
        infoEditText = (EditText) findViewById(R.id.user_info);
        type = getIntent().getStringExtra("type");
    }

    private void initData() {
        content = infoEditText.getText().toString().trim();
        LoginArrayModel.UpdataUserInfoRequst(type,content).done(new ICallback() {
            @Override
            public void call(Arguments arguments) {
                loginArrayModel = arguments.get(0);
                if(loginArrayModel.getRescode()==100){
                    ToastUtil.toast(UpdataUserActivity.this,getString(R.string.user_success));
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
