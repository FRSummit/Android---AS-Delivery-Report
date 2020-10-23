package com.frskynet.as_deliveryreport;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.frskynet.as_deliveryreport.ErrorMessages.DASHBOARD_BACK_BUTTON_PRESS;

public class DeliveryDashboard extends Activity {
    private DBHelper dbHelper;
    private TextView name;
    private TextView email;
    private TextView address;
    private ListView reportList;
    private ArrayList<DeliveryMan> deliveryManList;
    private ArrayAdapter<String> arrayAdapter;
    private ToasterMessage toasterMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_dashboard);

        dbHelper = new DBHelper(this, null, null, 1);
        toasterMessage = new ToasterMessage();

        name =  (TextView) findViewById(R.id.dashboard_user_name);
        email =  (TextView) findViewById(R.id.dashboard_user_email);
        address =  (TextView) findViewById(R.id.dashboard_user_address);

        deliveryManList = dbHelper.getAllDeliveryMenList();
        DeliveryMan deliveryMan = deliveryManList.get(0);
        name.setText(deliveryMan.getName());
        email.setText(deliveryMan.getEmail());
        address.setText(deliveryMan.getAddress());


        reportList = findViewById(R.id.dashboard_order_list);

        String[] item = {"Items name", "Items name", "Items name", "Items name", "Items name", "Items name", "Items name", "Items name", "Items name"};

        arrayAdapter = new ArrayAdapter<String>(DeliveryDashboard.this, android.R.layout.simple_list_item_1, item);
        reportList.setAdapter(arrayAdapter);
    }

    public void resetBtnClickHandler(View view) {
        final ProgressDialog loading = ProgressDialog.show(this,"Processing...","Please wait...",false,false);
        dbHelper.removeAllTable();
        changeActivity(loading);
    }

    private void changeActivity(final ProgressDialog loading) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(DeliveryDashboard.this, MainActivity.class));
                loading.dismiss();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1500);
    }

    @Override
    public void onBackPressed() {
        toasterMessage.showInformationToaster(this, DASHBOARD_BACK_BUTTON_PRESS);
    }
}