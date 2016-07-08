package com.ljs.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjcathay.android.view.ImageViewAdapter;
import com.ljs.blackdiamonds.R;
import com.ljs.model.ProjectModel;
import com.ljs.util.ItemViewFactory;

import java.util.List;

/**
 * Created by zhouhe on 2016/7/5.
 */
public class ProjectAdapter extends BaseAdapter {
    private Context context;
    private List<ProjectModel> projectModels;
    public ProjectAdapter(Context context,List<ProjectModel> projectModels) {
        this.context = context;
        this.projectModels = projectModels;
    }

    @Override
    public int getCount() {
        return projectModels.size();
    }

    @Override
    public Object getItem(int position) {
        return projectModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = ItemViewFactory.itemView(context, R.layout.project_item);
            holder = new ViewHolder();
            holder.tagImageView = (ImageView) convertView.findViewById(R.id.project_item_tag);
            holder.imageView = (ImageView) convertView.findViewById(R.id.project_item_image);
            holder.describeText = (TextView) convertView.findViewById(R.id.project_item_describe);
            holder.numText = (TextView) convertView.findViewById(R.id.project_item_num);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ImageViewAdapter.adapt(holder.imageView,
                projectModels.get(position).getIcon(),
                R.drawable.ic_launcher,true);
        holder.numText.setText(projectModels.get(position).getLikeCount()+"");
        return convertView;
    }

    class ViewHolder{
        ImageView tagImageView;
        ImageView imageView;
        TextView describeText;
        TextView numText;
    }
}
