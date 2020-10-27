package com.frskynet.as_deliveryreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.ArrayList;

import static com.frskynet.as_deliveryreport.Configuration.INTENT_EXTRA_DELIVERY_DASHBOARD_ORDER_NUMBER;
import static com.frskynet.as_deliveryreport.Configuration.DELIVERY_REPORT_TO_IMAGE_UPLOAD;

public class DeliveryReport extends Activity {
    private EditText customerName;
    private EditText orderNumber;
    private EditText orderBy;
    private DatePicker orderDate;
    private DatePicker deliveryDate;
    private EditText deliveredToName;
    private EditText status;
    private EditText comments;
    private DBHelper dbHelper;
    private String intentExtra;
    private Report singleReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_report);

        customerName = (EditText) findViewById(R.id.delivery_report_customer_name);
        orderNumber = (EditText) findViewById(R.id.delivery_report_order_number_input);
        orderBy = (EditText) findViewById(R.id.delivery_report_order_by_input);
        orderDate = (DatePicker) findViewById(R.id.delivery_report_order_date);
        deliveryDate = (DatePicker) findViewById(R.id.delivery_report_delivery_date);
        deliveredToName = (EditText) findViewById(R.id.delivery_report_delivered_to_input);
        status = (EditText) findViewById(R.id.delivery_report_status_input);
        comments = (EditText) findViewById(R.id.delivery_report_comment_input);

        dbHelper = new DBHelper(this, null, null, 1);

        Intent intentText = getIntent();
        Bundle extraText = intentText.getExtras();
        if(extraText != null) {
            intentExtra = (String) extraText.get(INTENT_EXTRA_DELIVERY_DASHBOARD_ORDER_NUMBER);
        }
        loadOrderReportDataFromDB(intentExtra);

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

                customerName.setText(report.getCustomerName());
                orderNumber.setText(report.getOrderNumber());
                orderBy.setText(report.getOrderBy());
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
                deliveredToName.setText(report.getDeliveredToName());
                status.setText(report.getStatus());
                comments.setText(report.getComments());
            }
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
        report.setStatus(status.getText().toString().trim());
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