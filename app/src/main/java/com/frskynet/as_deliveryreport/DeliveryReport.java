package com.frskynet.as_deliveryreport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DeliveryReport extends Activity {

    private EditText onBehalfOf;
    private EditText orderNumber;
    private EditText orderBy;
    private EditText orderDate;
    private EditText deliveryDate;
    private EditText deliveredToName;
    private EditText comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_report);

        onBehalfOf = (EditText) findViewById(R.id.delivery_report_on_behalf_of_input);
        orderNumber = (EditText) findViewById(R.id.delivery_report_order_number_input);
        orderBy = (EditText) findViewById(R.id.delivery_report_order_by_input);
        orderDate = (EditText) findViewById(R.id.delivery_report_order_date_input);
        deliveryDate = (EditText) findViewById(R.id.delivery_report_delivery_date_input);
        deliveredToName = (EditText) findViewById(R.id.delivery_report_delivered_to_input);
        comments = (EditText) findViewById(R.id.delivery_report_comment_input);

        Report report = new Report();
        report.setOnBehalfOf("No one");

        System.out.println(report.getOnBehalfOf());

    }

    public void deliveryReportSubmitHandler(View view) {
        startActivity(new Intent(this, ReportImageUpload.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}