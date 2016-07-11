package com.ljs.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljs.blackdiamonds.R;
import com.ljs.model.AddressModel;
import com.ljs.util.ItemViewFactory;

import java.util.List;

/**
 * Created by zhouhe on 2016/7/7.
 */
public class AddressMangeAdapter extends BaseAdapter {
    private Context context;
    private List<AddressModel> addressModels;
    public AddressMangeAdapter(Context context,List<AddressModel> addressModels) {
        this.context = context;
        this.addressModels = addressModels;
    }

    @Override
    public int getCount() {
        return addressModels.size();
    }

    @Override
    public Object getItem(int position) {
        return addressModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = ItemViewFactory.itemView(context, R.layout.address_item);
            viewHolder = new ViewHolder();
            viewHolder.addressNameView = (TextView) convertView.findViewById(R.id.address_name);
            viewHolder.phoneView = (TextView) convertView.findViewById(R.id.address_phone);
            viewHolder.addressView = (TextView) convertView.findViewById(R.id.address);
            viewHolder.selectAddressView = (ImageView) convertView.findViewById(R.id.select_address);
            viewHolder.addressEtButton = (Button) convertView.findViewById(R.id.address_bj);
            viewHolder.addressDel = (Button) convertView.findViewById(R.id.address_del);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.addressNameView.setText(addressModels.get(position).getName());
        viewHolder.phoneView.setText(addressModels.get(position).getPhoneNum());
        viewHolder.addressView.setText(addressModels.get(position).getAddress());



        return convertView;
    }
    class ViewHolder{
        TextView addressNameView;
        TextView phoneView;
        TextView addressView;
        ImageView selectAddressView;
        Button addressEtButton;
        Button addressDel;
    }
}
