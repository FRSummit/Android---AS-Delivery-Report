package com.frskynet.as_deliveryreport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DeliveryHistory extends Activity {
    private ListView reportList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_history);

        reportList = findViewById(R.id.report_list);
        String[] item = {"Items name", "Items name", "Items name", "Items name", "Items name", "Items name", "Items name", "Items name", "Items name"};

        arrayAdapter = new ArrayAdapter<String>(DeliveryHistory.this, android.R.layout.simple_list_item_1, item);
        reportList.setAdapter(arrayAdapter);
    }
}