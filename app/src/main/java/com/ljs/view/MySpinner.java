package com.ljs.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ljs.adapter.IndustryAdapter;
import com.ljs.blackdiamonds.R;

import java.util.List;

/**
 * Created by zhouhe on 2016/7/6.
 */
public class MySpinner extends LinearLayout {
    private ListView listView;
    private TextView textView;
    private List<String> list;

    public interface SpinnerListener{
        void listener(int postion);
    }

    public interface SpinnerOnClickListener{
        void listener(View v);
    }
    private SpinnerListener spinnerListener;
    private SpinnerOnClickListener spinnerOnClickListener;

    public MySpinner(Context context) {
        super(context);
        setOrientation(VERTICAL);
        initView(context);
    }

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        initView(context);
    }

    public MySpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MySpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOrientation(VERTICAL);
        initView(context);
    }

    public void setSpinnerListener(SpinnerListener spinnerListener){
        this.spinnerListener = spinnerListener;
    }

    public void setSpinnerOnClickListener(SpinnerOnClickListener spinnerOnClickListener){
        this.spinnerOnClickListener = spinnerOnClickListener;
    }

    public void initView(Context context){
        final View view = LayoutInflater.from(context).inflate(R.layout.spinner_view,null);
         listView = (ListView) view.findViewById(R.id.spinner_list);
         listView.setVisibility(View.GONE);
         textView = (TextView) view.findViewById(R.id.spinner_text);
         textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerOnClickListener.listener(v);
                if(View.VISIBLE==listView.getVisibility()){
                    listView.setVisibility(View.GONE);
                }else {
                    listView.setVisibility(View.VISIBLE);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setText(list.get(position));
                spinnerListener.listener(position);
                listView.setVisibility(View.GONE);
            }
        });
        this.addView(view);
    }

    public void initData(IndustryAdapter industryAdapter, List<String> list) {
        this.list = list;
        listView.setAdapter(industryAdapter);
        industryAdapter.notifyDataSetChanged();
    }
    public void setText(String name){
        textView.setText(name);
    }

    public void setVisbility(){
        listView.setVisibility(View.GONE);
    }
}
