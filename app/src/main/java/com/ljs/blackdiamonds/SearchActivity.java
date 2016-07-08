package com.ljs.blackdiamonds;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljs.adapter.SearchAdapter;
import com.ljs.view.MyListView;



/**
 * Created by zhouhe on 2016/7/7.
 */
public class SearchActivity extends Activity {
    private MyListView listView;
    private ImageView titleimageView;
    private ImageView searchimageView;
    private TextView titleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
    }

    private void initView() {
        listView = (MyListView) findViewById(R.id.search_listview);
        titleView = (TextView) findViewById(R.id.title);
        titleimageView = (ImageView) findViewById(R.id.title_image);
        searchimageView = (ImageView) findViewById(R.id.search);
        titleView.setVisibility(View.GONE);
        titleimageView.setVisibility(View.GONE);
        searchimageView.setVisibility(View.GONE);
    }

    private void initData() {
        listView.setAdapter(new SearchAdapter(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
