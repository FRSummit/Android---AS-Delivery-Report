package com.frskynet.as_deliveryreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by F R Summit on 27th October,2020
 * Simplexhub Limited
 * frsummit@simplexhub.com
 */
public class DeliveryDashboardListAdapter extends BaseAdapter {

    Context context;
    private final ArrayList<String> orderNumber;
    private final ArrayList<String> status;

    public DeliveryDashboardListAdapter(Context context, ArrayList<String> orderNumber, ArrayList<String> status){
        this.context = context;
        this.orderNumber = orderNumber;
        this.status = status;
    }

    @Override
    public int getCount() {
        return orderNumber.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.single_list_item, parent, false);
            viewHolder.orderNo = (TextView) convertView.findViewById(R.id.single_list_item_order_number);
            viewHolder.status = (TextView) convertView.findViewById(R.id.single_list_item_status);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.orderNo.setText("Order no: " + orderNumber.get(position));
        viewHolder.status.setText("Status: " + status.get(position));

        return convertView;
    }

    private static class ViewHolder {
        TextView orderNo;
        TextView status;
    }
}
