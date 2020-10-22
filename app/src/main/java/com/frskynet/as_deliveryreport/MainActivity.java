package com.frskynet.as_deliveryreport;

import androidx.appcompat.app.AppCompatActivity;

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

        DeliveryMan deliveryMan = new DeliveryMan();
        deliveryMan.setId("2014200000071");
        deliveryMan.setUsername("frsummit");
        deliveryMan.setPassword("developer001");

        DeliveryMan deliveryMan2 = new DeliveryMan();
        deliveryMan2.setId("20160000000059");
        deliveryMan2.setUsername("rimi");
        deliveryMan2.setPassword("developer002");

        dbHelper = new DBHelper(this, null, null, 1);
        //dbHelper.removeAllTable();
//        dbHelper.addLoginDetails(deliveryMan);
//        dbHelper.addLoginDetails(deliveryMan2);

        list = new ArrayList<>();

        changeActivity();
    }

    private void changeActivity() {
        list = dbHelper.getAllLoginDetails();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(list.isEmpty()) {
                    startActivity(new Intent(MainActivity.this, NonDeliveryMan.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    startActivity(new Intent(MainActivity.this, DeliveryReport.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }
}