package com.ljs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljs.blackdiamonds.R;
import com.ljs.util.ItemViewFactory;
import com.ljs.view.MyListView;

/**
 * Created by zhouhe on 2016/7/5.
 */
public class SocialContactFragment extends Fragment{
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = ItemViewFactory.itemView(getActivity(), R.layout.action_fragment);
        return view;
    }


}
