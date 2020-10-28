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
//    private final ArrayList<String> orderNumber;
//    private final ArrayList<String> status;
    private final ArrayList<Report> reportArrayList;

//    public DeliveryDashboardListAdapter(Context context, ArrayList<String> orderNumber, ArrayList<String> status){
//        this.context = context;
//        this.orderNumber = orderNumber;
//        this.status = status;
//    }

    public DeliveryDashboardListAdapter(Context context, ArrayList<Report> reportArrayList){
        this.context = context;
        this.reportArrayList = reportArrayList;
    }

    @Override
    public int getCount() {
        return reportArrayList.size();
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
            viewHolder.orderDate = (TextView) convertView.findViewById(R.id.single_list_item_order_date);
            viewHolder.deliveryDate = (TextView) convertView.findViewById(R.id.single_list_item_delivery_date);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.orderNo.setText("Order no: " + reportArrayList.get(position).getOrderNumber());
        viewHolder.status.setText("Status: " + reportArrayList.get(position).getStatus());
        viewHolder.orderDate.setText("Order Date: " + reportArrayList.get(position).getOrderDate());
        viewHolder.deliveryDate.setText("Delivery Date: " + reportArrayList.get(position).getDeliveryDate());

        return convertView;
    }

    private static class ViewHolder {
        TextView orderNo;
        TextView status;
        TextView orderDate;
        TextView deliveryDate;
    }
}
