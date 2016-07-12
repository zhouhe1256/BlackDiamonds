package com.ljs.blackdiamonds;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjcathay.android.async.Arguments;
import com.bjcathay.android.async.ICallback;
import com.bjcathay.android.view.ImageViewAdapter;
import com.ljs.application.HApplication;
import com.ljs.model.LoginArrayModel;
import com.ljs.singleton.UserInfo;
import com.ljs.util.FileUtils;
import com.ljs.util.ImageUtil;
import com.ljs.util.PreferencesUtils;
import com.ljs.util.ToastUtil;
import com.ljs.view.MyListView;
import com.ljs.view.SelectPicPopupWindow;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.utils.UrlSafeBase64;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhouhe on 2016/7/8.
 */
public class UserInfoActivity extends Activity implements SelectPicPopupWindow.SelectResult{
    private CircleImageView headImageView;
    private TextView userName;
    private TextView companyTitle;
    private TextView companyName;
    private TextView phoneNum;
    private ImageView titleimageView;
    private ImageView searchimageView;
    private TextView titleView;
    private LoginArrayModel loginArrayModel;
    private SelectPicPopupWindow selectPicPopupWindow;
    private int selectCode = 1;
    private int requestCropIcon = 2;
    private int resultPictureCode = 3;
    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
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
                phoneNum.setText(loginArrayModel.getData().getPhoneNum());
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
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.back_user:
                PreferencesUtils.clearData(this);
                HApplication.getInstance().updateApiToken();
                finish();
                break;
            case R.id.user_head:
                selectPicPopupWindow = new SelectPicPopupWindow(this,this);
                selectPicPopupWindow.showAtLocation(headImageView,
                        Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
                break;
            case R.id.user_info_name:
                intent.setClass(this,UpdataUserActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
                break;
            case R.id.user_info_gsname:
                intent.setClass(this,UpdataUserActivity.class);
                intent.putExtra("type","2");
                startActivity(intent);
                break;
            case R.id.user_info_pjname:
                intent.setClass(this,UpdataUserActivity.class);
                intent.putExtra("type","3");
                startActivity(intent);
                break;
            case R.id.user_info_phone:
                intent.setClass(this,UpdataUserActivity.class);
                intent.putExtra("type","100");
                startActivity(intent);
                break;
            case R.id.address:
                intent.setClass(this,AddressMangementActivity.class);
                startActivity(intent);
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

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    Uri uri;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (selectCode == requestCode) {
            uri = data.getData();
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 60);
            intent.putExtra("outputY", 60);
            intent.putExtra("scale", true);//黑边
            intent.putExtra("scaleUpIfNeeded", true);//黑边
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            File file=new File(HApplication.getBaseDir()+"/camera.jpg");
            uri= Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra("return-data", true);// 设置为不返回数据
            startActivityForResult(intent, requestCropIcon);
        }else if (requestCropIcon == requestCode) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                if (uri == null) {
                    return;
                }
                Bitmap photo = null;
                try {
                    photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);
                    headImageView.setImageBitmap(photo);
                    LoginArrayModel.setAvatar(FileUtils.Bitmap2Bytes(ImageUtil.comp(photo))).done(new ICallback() {
                        @Override
                        public void call(Arguments arguments) {
                             loginArrayModel = arguments.get(0);
                             UserInfo.getInstance().setUserInfo(loginArrayModel);
                            ToastUtil.toast(UserInfoActivity.this,getString(R.string.user_success));

                        }
                    }).fail(new ICallback() {
                        @Override
                        public void call(Arguments arguments) {
                            ToastUtil.toast(UserInfoActivity.this,getString(R.string.user_err));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if (resultPictureCode == requestCode) {
            Bundle extras = data.getExtras();
            data.getData();

            if (extras != null) {
                final Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);

                LoginArrayModel.setAvatar(FileUtils.Bitmap2Bytes(ImageUtil.comp(photo))).done(new ICallback() {
                    @Override
                    public void call(Arguments arguments) {
                        loginArrayModel = arguments.get(0);
                        UserInfo.getInstance().setUserInfo(loginArrayModel);
                        if(loginArrayModel.getRescode()==100){
                        ToastUtil.toast(UserInfoActivity.this,getString(R.string.user_success));
                        headImageView.setImageBitmap(photo);
                        }else{
                            ToastUtil.toast(UserInfoActivity.this,loginArrayModel.getMsg());
                        }
                    }
                }).fail(new ICallback() {
                    @Override
                    public void call(Arguments arguments) {
                        ToastUtil.toast(UserInfoActivity.this,getString(R.string.user_err));
                    }
                });
            }
        }
    }

    @Override
    public void resultPicture() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, selectCode);
    }

    @Override
    public void resultCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, resultPictureCode);
    }



}
