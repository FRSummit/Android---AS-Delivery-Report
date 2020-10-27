package com.frskynet.as_deliveryreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    private DBHelper dbHelper;
    private ArrayList<DeliveryMan> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this, null, null, 1);
        list = new ArrayList<>();
        changeActivity();
    }

    private void changeActivity() {
        list = dbHelper.getAllDeliveryMenList();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(list.isEmpty()) {
                    startActivity(new Intent(MainActivity.this, NonDeliveryMan.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    startActivity(new Intent(MainActivity.this, DeliveryDashboard.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }
}