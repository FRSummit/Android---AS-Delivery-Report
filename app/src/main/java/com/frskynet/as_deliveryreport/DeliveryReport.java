package com.frskynet.as_deliveryreport;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.frskynet.as_deliveryreport.Configuration.ADD_REPORT_URL;
import static com.frskynet.as_deliveryreport.Configuration.INTENT_EXTRA_DELIVERY_DASHBOARD_ORDER_NUMBER;
import static com.frskynet.as_deliveryreport.Configuration.KEY_ACTION_DELIVERY_REPORT_INSERT_INTO_SPREADSHEET;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ID;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_PARTNER_ID;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_CUSTOMER_NAME;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_CUSTOMER_NAME_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_NUMBER;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_NUMBER_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_BY;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_BY_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_DATE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_DATE_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_DELIVERY_DATE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_DELIVERY_DATE_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_DELIVERY_TO_NAME;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_DELIVERY_TO_NAME_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_STATUS;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_COMMENT;

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

                System.out.println(report.getCustomerNameOverride() + "\n" + report.getOrderByOverride() + "\n" + report.getOrderDateOverride() + "\n" + report.getDeliveryDateOverride() + "\n" + report.getDeliveredToNameOverride() + "\n" + report.getStatus());
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
        startActivity(new Intent(this, ReportImageUpload.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void deliveryReportCancelHandler(View view) {
        startActivity(new Intent(DeliveryReport.this, DeliveryDashboard.class));
    }

    public void saveDateToSpreadsheet(final Report report) {
        final ProgressDialog loading = ProgressDialog.show(this,"Your order is loading","Please wait until loading process finish...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_REPORT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(DeliveryReport.this, response, Toast.LENGTH_LONG).show();
                        System.out.println("Response\n" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DeliveryReport.this, error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println("Error\n" + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_ACTION_DELIVERY_REPORT_INSERT_INTO_SPREADSHEET, "INSERT_REPORT");
                params.put(KEY_DELIVERY_REPORT_ID, report.getId());
                params.put(KEY_DELIVERY_REPORT_PARTNER_ID, report.getDeliveryManId());
                params.put(KEY_DELIVERY_REPORT_CUSTOMER_NAME, report.getCustomerName());
                params.put(KEY_DELIVERY_REPORT_CUSTOMER_NAME_OVERRIDE, report.getCustomerNameOverride());
                params.put(KEY_DELIVERY_REPORT_ORDER_NUMBER, report.getOrderNumber());
                params.put(KEY_DELIVERY_REPORT_ORDER_NUMBER_OVERRIDE, report.getOrderNumberOverride());
                params.put(KEY_DELIVERY_REPORT_ORDER_BY, report.getOrderBy());
                params.put(KEY_DELIVERY_REPORT_ORDER_BY_OVERRIDE, report.getOrderByOverride());
                params.put(KEY_DELIVERY_REPORT_ORDER_DATE, report.getOrderDate());
                params.put(KEY_DELIVERY_REPORT_ORDER_DATE_OVERRIDE, report.getOrderDateOverride());
                params.put(KEY_DELIVERY_REPORT_DELIVERY_DATE, report.getDeliveryDate());
                params.put(KEY_DELIVERY_REPORT_DELIVERY_DATE_OVERRIDE, report.getDeliveryDateOverride());
                params.put(KEY_DELIVERY_REPORT_DELIVERY_TO_NAME, report.getDeliveredToName());
                params.put(KEY_DELIVERY_REPORT_DELIVERY_TO_NAME_OVERRIDE, report.getDeliveredToNameOverride());
                params.put(KEY_DELIVERY_REPORT_STATUS, report.getStatus());
                params.put(KEY_DELIVERY_REPORT_COMMENT, report.getComments());

                return params;
            }
        };

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}