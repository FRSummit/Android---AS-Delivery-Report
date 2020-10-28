package com.frskynet.as_deliveryreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import static com.frskynet.as_deliveryreport.Configuration.INTENT_EXTRA_DELIVERY_DASHBOARD_ORDER_NUMBER;
import static com.frskynet.as_deliveryreport.Configuration.DELIVERY_REPORT_TO_IMAGE_UPLOAD;

public class DeliveryReport extends Activity {
    private EditText customerName;
    private EditText customerNameOverride;
    private EditText orderNumber;
    private EditText orderNumberOverride;
    private EditText orderBy;
    private EditText orderByOverride;
    private DatePicker orderDate;
    private DatePicker deliveryDate;
    private EditText deliveredToName;
    private EditText deliveredToNameOverride;
    private EditText status;
    private EditText comments;
    private Button saveBtn;
    private DBHelper dbHelper;
    private String intentExtra;
    private Report singleReport;
    private Spinner spinner;
    private List<String> statusList;
    private ArrayAdapter<String> spinnerAdapter;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_report);

        customerName = (EditText) findViewById(R.id.delivery_report_customer_name);
        customerNameOverride = (EditText) findViewById(R.id.delivery_report_customer_name_override);
        orderNumber = (EditText) findViewById(R.id.delivery_report_order_number_input);
        orderNumberOverride = (EditText) findViewById(R.id.delivery_report_order_number_input_override);
        orderBy = (EditText) findViewById(R.id.delivery_report_order_by_input);
        orderByOverride = (EditText) findViewById(R.id.delivery_report_order_by_input_override);
        orderDate = (DatePicker) findViewById(R.id.delivery_report_order_date);
        deliveryDate = (DatePicker) findViewById(R.id.delivery_report_delivery_date);
        deliveredToName = (EditText) findViewById(R.id.delivery_report_delivered_to_input);
        deliveredToNameOverride = (EditText) findViewById(R.id.delivery_report_delivered_to_input_override);
        status = (EditText) findViewById(R.id.delivery_report_status_input);
        comments = (EditText) findViewById(R.id.delivery_report_comment_input);
        spinner = (Spinner) findViewById(R.id.delivery_report_status_input_spinner);
        saveBtn = (Button) findViewById(R.id.save_report_button);

        dbHelper = new DBHelper(this, null, null, 1);

        Intent intentText = getIntent();
        Bundle extraText = intentText.getExtras();
        if(extraText != null) {
            intentExtra = (String) extraText.get(INTENT_EXTRA_DELIVERY_DASHBOARD_ORDER_NUMBER);
        }
        loadOrderReportDataFromDB(intentExtra);
        statusListCreator();

    }

    private void statusListCreator() {
        statusList = new ArrayList<>();
        if(flag) {
            System.out.println(flag);
            statusList.add("Pending");
            statusList.add("On Processing");
            statusList.add("On The Way");
            statusList.add("Delivered");
            spinnerArrayAdapter(spinner, statusList);
        } else {
            System.out.println(flag);
            statusList.add("Delivered");
            spinnerArrayAdapter(spinner, statusList);
        }
    }

    public void spinnerArrayAdapter(Spinner spinner, List<String> list) {
        spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_status, list);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_status);
        spinner.setAdapter(spinnerAdapter);
    }

    private void loadOrderReportDataFromDB(String orderNo) {
        ArrayList<Report> reportArrayList = new ArrayList<>();
        reportArrayList = dbHelper.getAllDeliveryReportList();
        for (int i=0; i<reportArrayList.size(); i++) {
            if(reportArrayList.get(i).getOrderNumber().toString().equals(orderNo)) {
                Report report = new Report();
                report = reportArrayList.get(i);
                singleReport = new Report();
                singleReport = reportArrayList.get(i);

                customerName.setHint("Customer: " + report.getCustomerName());
                customerNameOverride.setText(report.getCustomerNameOverride());
                orderNumber.setHint("Order no: " + report.getOrderNumber());
                orderNumberOverride.setText(report.getOrderNumberOverride());
                orderBy.setHint("Order by: " + report.getOrderBy());
                orderByOverride.setText(report.getOrderByOverride());
                orderDate.init(
                        Integer.parseInt(report.getOrderDate().split("-")[2].toString()),
                        Integer.parseInt(report.getOrderDate().split("-")[1].toString())-1,
                        Integer.parseInt(report.getOrderDate().split("-")[0].toString()),
                        null
                );
                deliveryDate.init(
                        Integer.parseInt(report.getDeliveryDate().split("-")[2].toString()),
                        Integer.parseInt(report.getDeliveryDate().split("-")[1].toString())-1,
                        Integer.parseInt(report.getDeliveryDate().split("-")[0].toString()),
                        null
                );
                deliveredToName.setHint("Delivered to: " + report.getDeliveredToName());
                deliveredToNameOverride.setText(report.getDeliveredToNameOverride());
                status.setText("Order status: " + report.getStatus());
                comments.setText(report.getComments());
                setSaveBtnDisable(report.getStatus());
            }
        }

    }

    private void setSaveBtnDisable(String status) {
        System.out.println(status);
        if(status.equals("Delivered")) {
            saveBtn.setEnabled(false);
            flag = false;
        }
    }

    public void deliveryReportSubmitHandler(View view) {
        Report report = new Report();
        report.setId(singleReport.getId());
        report.setDeliveryManId(singleReport.getDeliveryManId());
        report.setCustomerName(singleReport.getCustomerName());
        report.setCustomerNameOverride(customerName.getText().toString().trim());
        report.setOrderNumber(singleReport.getOrderNumber());
        report.setOrderNumberOverride(orderNumber.getText().toString().trim());
        report.setOrderBy(singleReport.getOrderBy());
        report.setOrderByOverride(orderBy.getText().toString().trim());

        report.setOrderDate(singleReport.getOrderDate());

        String dayOrderDate = String.valueOf(orderDate.getDayOfMonth());
        String monthOrderDate = String.valueOf((orderDate.getMonth() + 1));
        String yearOrderDate = String.valueOf(orderDate.getYear());
        report.setOrderDateOverride(dayOrderDate + "-" + monthOrderDate + "-" + yearOrderDate);

        report.setDeliveryDate(singleReport.getDeliveryDate());

        String dayDeliveryDate = String.valueOf(deliveryDate.getDayOfMonth());
        String monthDeliveryDate = String.valueOf((deliveryDate.getMonth() + 1));
        String yearDeliveryDate = String.valueOf(deliveryDate.getYear());
        report.setDeliveryDateOverride(dayDeliveryDate + "-" + monthDeliveryDate + "-" + yearDeliveryDate);

        report.setDeliveredToName(singleReport.getDeliveredToName());
        report.setDeliveredToNameOverride(deliveredToName.getText().toString().trim());
//        report.setStatus(status.getText().toString().trim());
        report.setStatus(spinner.getSelectedItem().toString());
        report.setComments(comments.getText().toString().trim());

        dbHelper.updateDeliveryReport(report);
//        saveDateToSpreadsheet(report);
        Intent intent = new Intent(this, ReportImageUpload.class);
        intent.putExtra(DELIVERY_REPORT_TO_IMAGE_UPLOAD, report.getId());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void deliveryReportCancelHandler(View view) {
        startActivity(new Intent(DeliveryReport.this, DeliveryDashboard.class));
    }
}