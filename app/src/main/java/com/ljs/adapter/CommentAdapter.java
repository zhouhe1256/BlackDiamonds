package com.ljs.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ljs.blackdiamonds.R;
import com.ljs.util.ItemViewFactory;

/**
 * Created by zhouhe on 2016/7/7.
 */
public class CommentAdapter extends BaseAdapter {
    private Context context;
    public CommentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = ItemViewFactory.itemView(context, R.layout.comment_item);
        }
        return convertView;
    }
}
