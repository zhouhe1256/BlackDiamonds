package com.ljs.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bjcathay.android.async.Arguments;
import com.bjcathay.android.async.ICallback;
import com.bjcathay.android.view.ImageViewAdapter;
import com.ljs.application.HApplication;
import com.ljs.blackdiamonds.LoginActivity;
import com.ljs.blackdiamonds.MainActivity;
import com.ljs.blackdiamonds.R;
import com.ljs.blackdiamonds.UserInfoActivity;
import com.ljs.model.LoginArrayModel;
import com.ljs.singleton.UserInfo;
import com.ljs.util.ItemViewFactory;
import com.ljs.util.PreferencesUtils;
import com.ljs.util.ToastUtil;
import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhouhe on 2016/7/5.
 */
public class LeftFragment extends Fragment {
    private View view;
    private TextView userInfoTextView;
    private LoginArrayModel loginArrayModel;
    private TextView userNametTextView;
    private CircleImageView headImageView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = ItemViewFactory.itemView(getActivity(), R.layout.left_fragment);
        initView();
        //initData();
        setListeners();
        return view;
    }

    private void initData() {
        if(UserInfo.getInstance().getUserInfo()!=null){
            loginArrayModel = UserInfo.getInstance().getUserInfo();
                userNametTextView.setText(loginArrayModel.getData().getName());
                ImageViewAdapter.adapt(headImageView,loginArrayModel.getData().getIcon(),R.drawable.head,true);

        }else{
            getUserInfo();
        }
    }

    private void setListeners() {
        userInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(HApplication.isLogin()){
                    intent.setClass(getActivity(),UserInfoActivity.class);
                }else{
                    intent.setClass(getActivity(),LoginActivity.class);
                }
                startActivity(intent);
            }
        });
    }

    private void initView() {
        userInfoTextView = (TextView) view.findViewById(R.id.user_info);
        userNametTextView = (TextView) view.findViewById(R.id.user_name);
        headImageView = (CircleImageView) view.findViewById(R.id.head_image);

    }

    @Override
    public void onStart() {
        super.onStart();
        if(HApplication.isLogin()){
            initData();
        }else{
            userNametTextView.setText("");
        }

    }

    public void getUserInfo(){
        String token = PreferencesUtils.getString(getActivity(), "token");
        String uid = PreferencesUtils.getString(getActivity(), "uid");
        LoginArrayModel.userInfoRequst(token,uid).done(new ICallback() {
            @Override
            public void call(Arguments arguments) {
                loginArrayModel = arguments.get(0);
                if(loginArrayModel.getRescode()==100){
                    UserInfo.getInstance().setUserInfo(loginArrayModel);
                    userNametTextView.setText(loginArrayModel.getData().getName());
                    ImageViewAdapter.adapt(headImageView,loginArrayModel.getData().getIcon(),R.drawable.head,true);
                }else{
                    ToastUtil.toast(getActivity(),loginArrayModel.getMsg());
                    PreferencesUtils.clearData(getActivity());
                    HApplication.getInstance().updateApiToken();
                }

            }
        }).fail(new ICallback() {
            @Override
            public void call(Arguments arguments) {
                ToastUtil.toast(getActivity(),getString(R.string.user_err));
            }
        });
    }
}
