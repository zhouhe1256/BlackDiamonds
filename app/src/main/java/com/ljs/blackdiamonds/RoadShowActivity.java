package com.ljs.blackdiamonds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljs.adapter.RoadShowAdapter;
import com.ljs.view.MyListView;

/**
 * Created by zhouhe on 2016/7/7.
 */
public class RoadShowActivity extends Activity {
    private ImageView titleimageView;
    private ImageView searchimageView;
    private TextView titleView;
    private MyListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_show);
        initView();
        initData();
        setListeners();
    }


    private void initView() {
        titleView = (TextView) findViewById(R.id.title);
        titleimageView = (ImageView) findViewById(R.id.title_image);
        searchimageView = (ImageView) findViewById(R.id.search);
        listView = (MyListView) findViewById(R.id.listview);

        titleView.setText("路演英雄");
        titleimageView.setImageResource(R.drawable.back);
        searchimageView.setVisibility(View.GONE);
    }

    private void initData() {
        listView.setAdapter(new RoadShowAdapter(this));
    }

    private void setListeners() {
        titleimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RoadShowActivity.this,CommentActivity.class);
                startActivity(intent);
            }
        });
    }
}
