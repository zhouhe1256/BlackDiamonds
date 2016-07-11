package com.ljs.blackdiamonds;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bjcathay.android.async.Arguments;
import com.bjcathay.android.async.ICallback;
import com.ljs.adapter.AddressMangeAdapter;
import com.ljs.model.AddressArrayModel;
import com.ljs.model.AddressModel;
import com.ljs.view.MyListView;
import com.ljs.view.ScrollViewWithListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouhe on 2016/7/11.
 */
public class AddressMangementActivity extends Activity{
    private ScrollViewWithListView listView;
    private ImageView titleimageView;
    private ImageView searchimageView;
    private TextView titleView;
    private AddressMangeAdapter addressMgAdapter;
    private AddressArrayModel addressArrayModel;
    private List<AddressModel>  addressModels;
    private final static int ADDRESS = 0x000;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ADDRESS:
                    addressModels.addAll((List<AddressModel>) msg.obj);
                    addressMgAdapter.notifyDataSetChanged();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_mange);
        initView();
        initData();
    }

    private void initData() {
        AddressArrayModel.getAddressRequest().done(new ICallback() {
            @Override
            public void call(Arguments arguments) {
                addressArrayModel = arguments.get(0);
                if(addressArrayModel.getRescode()==100){
                   Message msg = handler.obtainMessage();
                    msg.what = ADDRESS;
                    msg.obj = addressArrayModel.getData();
                    handler.sendMessage(msg);
                }else{

                }
            }
        }).fail(new ICallback() {
            @Override
            public void call(Arguments arguments) {

            }
        });
    }

    private void initView() {
        listView = (ScrollViewWithListView) findViewById(R.id.listview);
        titleView = (TextView) findViewById(R.id.title);
        titleimageView = (ImageView) findViewById(R.id.title_image);
        searchimageView = (ImageView) findViewById(R.id.search);
        listView = (ScrollViewWithListView) findViewById(R.id.listview);

        titleView.setText("收获地址");
        titleimageView.setImageResource(R.drawable.back);
        searchimageView.setVisibility(View.GONE);
        addressModels = new ArrayList<AddressModel>();
        addressMgAdapter = new AddressMangeAdapter(this,addressModels);
        listView.setAdapter(addressMgAdapter);
    }
    
    
}
