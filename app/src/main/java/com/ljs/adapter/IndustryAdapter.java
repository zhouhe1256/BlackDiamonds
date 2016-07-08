package com.ljs.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ljs.blackdiamonds.R;
import com.ljs.util.ItemViewFactory;

import java.util.List;

/**
 * Created by zhouhe on 2016/7/5.
 */
public class IndustryAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    public IndustryAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = ItemViewFactory.itemView(context, R.layout.spinner_item);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position));
        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }
}
