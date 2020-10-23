package com.frskynet.as_deliveryreport;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<Report> reportArrayList;
    private ArrayAdapter<String> arrayAdapter;
    private ToasterMessage toasterMessage;
    private DataLoadFromSheet dataLoadFromSheet;
    private String[] orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_dashboard);

        dbHelper = new DBHelper(this, null, null, 1);
        toasterMessage = new ToasterMessage();

        name =  (TextView) findViewById(R.id.dashboard_user_name);
        email =  (TextView) findViewById(R.id.dashboard_user_email);
        address =  (TextView) findViewById(R.id.dashboard_user_address);
        reportList = findViewById(R.id.dashboard_order_list);
        dataLoadFromSheet = new DataLoadFromSheet();

        deliveryManList = dbHelper.getAllDeliveryMenList();
        DeliveryMan deliveryMan = deliveryManList.get(0);
        name.setText(deliveryMan.getName());
        email.setText(deliveryMan.getEmail());
        address.setText(deliveryMan.getAddress());

        String[] item = {"Items name", "Items name", "Items name", "Items name", "Items name", "Items name", "Items name", "Items name", "Items name"};
        arrayAdapter = new ArrayAdapter<String>(DeliveryDashboard.this, android.R.layout.simple_list_item_1, item);
        reportList.setAdapter(arrayAdapter);

        orderLoad(deliveryMan.getId());
    }

    public void resetBtnClickHandler(View view) {
//        final ProgressDialog loading = ProgressDialog.show(this,"Processing...","Please wait...",false,false);
//        dbHelper.removeAllTable();
//        changeActivity(loading);
        reportArrayList = new ArrayList<>();
        reportArrayList = dbHelper.getAllDeliveryReportList();
        System.out.println(reportArrayList.size());
        orderList = new String[reportArrayList.size()];
        for(int i=0; i< reportArrayList.size(); i++) {
            System.out.println(reportArrayList.get(i).getId());
            orderList[i] = reportArrayList.get(i).getId();
        }
        arrayAdapter = new ArrayAdapter<String>(DeliveryDashboard.this, android.R.layout.simple_list_item_1, orderList);
        reportList.setAdapter(arrayAdapter);
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

    public void orderLoad (String deliveryPartnerId) {
//        Toast.makeText(this, this.getApplicationContext().toString(), Toast.LENGTH_SHORT).show();
        final ProgressDialog loading = ProgressDialog.show(this,"Your order is loading","Please wait until loading process finish...",false,false);
        dataLoadFromSheet.loadDeliveryReportData(this.getApplicationContext(), deliveryPartnerId, loading);
    }

    @Override
    public void onBackPressed() {
        toasterMessage.showInformationToaster(this, DASHBOARD_BACK_BUTTON_PRESS);
    }
}