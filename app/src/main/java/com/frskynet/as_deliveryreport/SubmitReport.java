package com.frskynet.as_deliveryreport;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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
import static com.frskynet.as_deliveryreport.Configuration.KEY_ACTION_DELIVERY_REPORT_INSERT_INTO_SPREADSHEET;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_COMMENT;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_CUSTOMER_NAME;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_CUSTOMER_NAME_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_DELIVERY_DATE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_DELIVERY_DATE_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_DELIVERY_TO_NAME;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_DELIVERY_TO_NAME_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ID;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_BY;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_BY_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_DATE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_DATE_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_NUMBER;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_NUMBER_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_PARTNER_ID;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_STATUS;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_IMAGE_URL;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_SIGNATURE_URL;
import static com.frskynet.as_deliveryreport.Configuration.SIGNATURE_UPLOAD_TO_REPORT_SUBMIT;

public class SubmitReport extends Activity {
    private String intentExtra;
    private TextView reportId;
    private TextView deliveryPartnerId;
    private TextView cName;
    private TextView cNameOr;
    private TextView orderNo;
    private TextView orderNoOr;
    private TextView OrderBy;
    private TextView OrderByOr;
    private TextView orderDate;
    private TextView orderDateOr;
    private TextView deliveryDate;
    private TextView deliveryDateOr;
    private TextView deliveryToName;
    private TextView deliveryToNameOr;
    private TextView status;
    private TextView comment;
    private TextView imageURL;
    private TextView signURL;
    private DBHelper dbHelper;
    private ArrayList<Report> reportArrayList;
    private Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);

        Intent intentText = getIntent();
        Bundle extraText = intentText.getExtras();
        if(extraText != null) {
            intentExtra = (String) extraText.get(SIGNATURE_UPLOAD_TO_REPORT_SUBMIT);
        }


        reportId = findViewById(R.id.reportID);
        deliveryPartnerId = findViewById(R.id.deliveryPartnerId);
        cName = findViewById(R.id.cName);
        cNameOr = findViewById(R.id.cNameOr);
        orderNo = findViewById(R.id.orderNo);
        orderNoOr = findViewById(R.id.orderNoOr);
        OrderBy = findViewById(R.id.orderBy);
        OrderByOr = findViewById(R.id.orderByOr);
        orderDate = findViewById(R.id.orderDate);
        orderDateOr = findViewById(R.id.orderDateOr);
        deliveryDate = findViewById(R.id.deliveryDate);
        deliveryDateOr = findViewById(R.id.deliveryDateOr);
        deliveryToName = findViewById(R.id.deliveryToName);
        deliveryToNameOr = findViewById(R.id.deliveryToNameOr);
        status = findViewById(R.id.status);
        comment = findViewById(R.id.comment);
//        imageURL = findViewById(R.id.imgURL);
//        signURL = findViewById(R.id.signURL);

        dbHelper = new DBHelper(this, null, null, 1);
        loadReportFromDB();
    }

    public void loadReportFromDB() {
        reportArrayList = new ArrayList<>();
        reportArrayList = dbHelper.getAllDeliveryReportList();
        for (int i=0; i<reportArrayList.size(); i++) {
            if(reportArrayList.get(i).getId().toString().equals(intentExtra)) {
                report = new Report();
                report = reportArrayList.get(i);

                reportId.setText(report.getId());
                deliveryPartnerId.setText(report.getDeliveryManId());
                cName.setText(report.getCustomerName());
                cNameOr.setText(report.getCustomerNameOverride());
                orderNo.setText(report.getOrderNumber());
                orderNoOr.setText(report.getOrderNumberOverride());
                OrderBy.setText(report.getOrderBy());
                OrderByOr.setText(report.getOrderByOverride());
                orderDate.setText(report.getOrderDate());
                orderDateOr.setText(report.getOrderDateOverride());
                deliveryDate.setText(report.getDeliveryDate());
                deliveryDateOr.setText(report.getDeliveryDateOverride());
                deliveryToName.setText(report.getDeliveredToName());
                deliveryToNameOr.setText(report.getDeliveredToNameOverride());
                status.setText(report.getStatus());
                comment.setText(report.getComments());
            }
        }
    }

    public void submitReportHandler(View view) {
        saveDateToSpreadsheet(report);
    }

    public void saveDateToSpreadsheet(final Report report) {
        final ProgressDialog loading = ProgressDialog.show(this,"Submission is loading","Please wait until process finish...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_REPORT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(SubmitReport.this, "Report Submitted successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SubmitReport.this, DeliveryDashboard.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(SubmitReport.this, error.toString(), Toast.LENGTH_LONG).show();
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
                params.put(KEY_DELIVERY_REPORT_IMAGE_URL, report.getImageURL());
                params.put(KEY_DELIVERY_REPORT_SIGNATURE_URL, report.getSignatureURL());

                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}