package com.ljs.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhouhe on 2016/7/11.
 */
public class ToastUtil {
    public static void toast( Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT);
    }
}
