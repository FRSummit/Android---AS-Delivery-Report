package com.frskynet.as_deliveryreport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DeliveryReport extends Activity {

    private EditText onBehalfOf;
    private EditText orderNumber;
    private EditText orderBy;
    private DatePicker orderDate;
    private DatePicker deliveryDate;
    private EditText deliveredToName;
    private EditText comments;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_report);

        onBehalfOf = (EditText) findViewById(R.id.delivery_report_on_behalf_of_input);
        orderNumber = (EditText) findViewById(R.id.delivery_report_order_number_input);
        orderBy = (EditText) findViewById(R.id.delivery_report_order_by_input);
        orderDate = (DatePicker) findViewById(R.id.delivery_report_order_date);
        deliveryDate = (DatePicker) findViewById(R.id.delivery_report_delivery_date);
        deliveredToName = (EditText) findViewById(R.id.delivery_report_delivered_to_input);
        comments = (EditText) findViewById(R.id.delivery_report_comment_input);

        Report report = new Report();
        report.setOnBehalfOf("No one");

        System.out.println(">>>>>>>>>>>>>>>>>>>" + report.getOnBehalfOf());

        dbHelper = new DBHelper(this, null, null, 1);

//        DeliveryMan deliveryMan = new DeliveryMan();
//        ArrayList<DeliveryMan> list = new ArrayList<>();
//        list = dbHelper.getAllLoginDetails();
//        for (int i=0; i<list.size(); i++) {
//            System.out.println(">>>>>>>>>>>>>>>>>>>" + list.get(i));
//            deliveryMan = list.get(i);
//            System.out.println(i + " id: " + deliveryMan.getId());
//            System.out.println(i + "username: " + deliveryMan.getUsername());
//            System.out.println(i + "password: " + deliveryMan.getPassword());
//        }
//
//        Report report2 = new Report();
//        ArrayList<Report> reportList = new ArrayList<>();
//        reportList = dbHelper.getAllDeliveryReportList();
//        for (int i=0; i<reportList.size(); i++) {
//            System.out.println(">>>>>>>>>>>>>>>>>>>" + reportList.get(i));
//            report2 = reportList.get(i);
//            System.out.println(i + " id: " + report2.getId());
//            System.out.println(i + "orderno: " + report2.getOrderNumber());
//            System.out.println(i + "comment: " + report2.getComments());
//        }



    }

    public void deliveryReportSubmitHandler(View view) {
        startActivity(new Intent(this, ReportImageUpload.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

//        String day = "Day = " + orderDate.getDayOfMonth();
//        String month = "Month = " + (orderDate.getMonth() + 1);
//        String year = "Year = " + orderDate.getYear();
//        // display the values by using a toast
//        Toast.makeText(getApplicationContext(), day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();
//
//        comments.setError("Please Enter a username!");



        /*SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        Date date = new Date();
//        System.out.println(formatter.format(date));

        String id = "DELIVERY_REPORT_ID" + formatter.format(date);
        String deliveryManId = "2014200000071";
        String onBehalf = onBehalfOf.getText().toString().trim();
        String orderNo = orderNumber.getText().toString().trim();
        String orderBY = orderBy.getText().toString().trim();
        String orderDt = "" + orderDate.getDayOfMonth() + "-" + orderDate.getMonth() + "-" + orderDate.getYear();
        String deliveryDt = "" + deliveryDate.getDayOfMonth() + "-" + deliveryDate.getMonth() + "-" + deliveryDate.getYear();
        String deliveryTo = deliveredToName.getText().toString().trim();
        String cmnt = comments.getText().toString().trim();

        if(!onBehalf.isEmpty() && !orderNo.isEmpty() && !orderBY.isEmpty() && !orderDt.isEmpty() && !deliveryDt.isEmpty() && !deliveryTo.isEmpty() && !cmnt.isEmpty()) {
            Report report = new Report(id, "2014200000071", onBehalf, orderNo, orderBY, orderDt, deliveryDt, deliveryTo, cmnt);
            dbHelper.addDeliveryReport(report);

            onBehalfOf.setText("");
            orderNumber.setText("");
            orderBy.setText("");
            deliveredToName.setText("");
            comments.setText("");

        } else {
            if(onBehalf.isEmpty()) {
                onBehalfOf.setError("Raise the request on behalf of should not be empty");
            } if(orderNo.isEmpty()) {
                orderNumber.setError("Order number should not be empty");
            } if(orderBY.isEmpty()) {
                orderBy.setError("Order by should not be empty");
            } if(deliveryTo.isEmpty()) {
                deliveredToName.setError("Delivery to name should not be empty");
            } if(cmnt.isEmpty()) {
                comments.setError("Comment should not be empty");
            }
        }*/
    }

    public void commonLogoClickHandler(View view) {
        Toast.makeText(this, "Working", Toast.LENGTH_LONG).show();
    }
}