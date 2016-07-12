package com.ljs.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjcathay.android.async.Arguments;
import com.bjcathay.android.async.ICallback;
import com.bjcathay.android.remote.Http;
import com.bjcathay.android.view.ImageViewAdapter;
import com.ljs.blackdiamonds.R;
import com.ljs.constant.ApiUrl;
import com.ljs.model.ProjectModel;
import com.ljs.util.ItemViewFactory;
import com.ljs.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            convertView = ItemViewFactory.itemView(context, R.layout.project_item);
            holder = new ViewHolder();
            holder.tagImageView = (ImageView) convertView.findViewById(R.id.project_item_tag);
            holder.imageView = (ImageView) convertView.findViewById(R.id.project_item_image);
            holder.describeText = (TextView) convertView.findViewById(R.id.project_item_describe);
            holder.numText = (TextView) convertView.findViewById(R.id.project_item_num);
            holder.zanImageView = (ImageView) convertView.findViewById(R.id.zan);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ImageViewAdapter.adapt(holder.imageView,
                projectModels.get(position).getIcon(),
                R.drawable.ic_launcher,true);
        holder.numText.setText(projectModels.get(position).getLikeCount()+"");
        if(projectModels.get(position).isLike()){
            holder.zanImageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher));
        }else{
            holder.zanImageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.dianzan));
        }
        final String uid = projectModels.get(position).getPid();
        holder.zanImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLike("1",uid,holder.zanImageView,holder.numText);
            }
        });

        return convertView;
    }



    class ViewHolder{
        ImageView tagImageView;
        ImageView imageView;
        TextView describeText;
        TextView numText;
        ImageView zanImageView;
    }
    public void isLike(String type, String targetId, final ImageView imageView,final TextView numText){
        Http.instance().get(ApiUrl.LIKE)
                .param("type",type)
                .param("targetId",targetId)
                .run().done(new ICallback() {
            @Override
            public void call(Arguments arguments) {
                String json = arguments.get(0).toString();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int rescode = jsonObject.getInt("rescode");
                    if(rescode==100){
                        imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher));
                        int count = Integer.parseInt(numText.getText().toString().trim());
                        numText.setText((count+1)+"");
                        //ProjectAdapter.this.notifyDataSetChanged();
                    }else{
                        ToastUtil.toast(context,jsonObject.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).fail(new ICallback() {
            @Override
            public void call(Arguments arguments) {
                ToastUtil.toast(context,context.getString(R.string.user_err));
            }
        });
    }

}
