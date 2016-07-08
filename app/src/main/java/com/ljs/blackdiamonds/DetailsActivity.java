package com.ljs.blackdiamonds;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljs.view.MyListView;

/**
 * Created by zhouhe on 2016/7/8.
 */
public class DetailsActivity extends Activity{
    private ImageView titleimageView;
    private ImageView searchimageView;
    private TextView titleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
        setListeners();
    }
    private void initView() {
        titleView = (TextView) findViewById(R.id.title);
        titleimageView = (ImageView) findViewById(R.id.title_image);
        searchimageView = (ImageView) findViewById(R.id.search);

        titleView.setText("中国企业跨境并购新趋势");
        titleimageView.setImageResource(R.drawable.back);
        searchimageView.setVisibility(View.GONE);
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
