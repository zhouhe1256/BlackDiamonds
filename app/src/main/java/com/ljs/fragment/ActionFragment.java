package com.ljs.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ljs.adapter.ActionAdapter;
import com.ljs.blackdiamonds.R;
import com.ljs.blackdiamonds.RoadShowActivity;
import com.ljs.util.ItemViewFactory;
import com.ljs.view.MyListView;

/**
 * Created by zhouhe on 2016/7/5.
 */
public class ActionFragment extends Fragment{
    private View view;
    private MyListView listview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = ItemViewFactory.itemView(getActivity(), R.layout.action_fragment);
        initView();
        setListeners();
        return view;
    }

    private void initView() {
        listview = (MyListView) view.findViewById(R.id.list);
        listview.setAdapter(new ActionAdapter(getActivity()));
    }

    private void setListeners() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), RoadShowActivity.class);
                startActivity(intent);
            }
        });
    }
}
