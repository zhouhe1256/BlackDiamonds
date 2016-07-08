package com.ljs.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by zhouhe on 2016/7/5.
 */
public class ItemViewFactory {
    public static View itemView(Context context,int layoutId){
        View view = LayoutInflater.from(context).inflate(layoutId,null);
        return view;
    }
}
