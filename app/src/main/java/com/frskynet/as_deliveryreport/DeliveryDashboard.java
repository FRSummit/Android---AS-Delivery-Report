package com.frskynet.as_deliveryreport;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.frskynet.as_deliveryreport.Configuration.GET_DELIVERY_REPORT_LIST_URL;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_COMMENT;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_CUSTOMER_NAME;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_CUSTOMER_NAME_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_DELIVERY_DATE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_DELIVERY_DATE_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_DELIVERY_TO_NAME;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_DELIVERY_TO_NAME_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ID;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_IMAGE_URL;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_BY;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_BY_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_DATE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_DATE_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_NUMBER;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_ORDER_NUMBER_OVERRIDE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_PARTNER_ID;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_SIGNATURE_URL;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_REPORT_STATUS;
import static com.frskynet.as_deliveryreport.ErrorMessages.DASHBOARD_BACK_BUTTON_PRESS;
import static com.frskynet.as_deliveryreport.ErrorMessages.NO_ORDER_FOUND_FOR_YOU;
import static com.frskynet.as_deliveryreport.ErrorMessages.NO_ORDER_FOUND_FROM_DB;
import static com.frskynet.as_deliveryreport.Configuration.INTENT_EXTRA_DELIVERY_DASHBOARD_ORDER_NUMBER;

public class DeliveryDashboard extends Activity {
    private DBHelper dbHelper;
    private TextView name;
    private TextView email;
    private TextView address;
    private ListView reportList;
    private ArrayList<DeliveryMan> deliveryManList;
    private ArrayList<Report> reportArrayList;
    private ArrayList<String> reportOrderNumbersList;
    private ArrayAdapter<String> arrayAdapter;
    private ToasterMessage toasterMessage;
    private DataLoadFromSheet dataLoadFromSheet;
    private ArrayList<String> orderList;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_dashboard);

        dbHelper = new DBHelper(this, null, null, 1);
        name =  (TextView) findViewById(R.id.dashboard_user_name);
        email =  (TextView) findViewById(R.id.dashboard_user_email);
        address =  (TextView) findViewById(R.id.dashboard_user_address);
        reportList = findViewById(R.id.dashboard_order_list);
        dataLoadFromSheet = new DataLoadFromSheet();
        toasterMessage = new ToasterMessage();
        reportOrderNumbersList = new ArrayList<>();
        orderList = new ArrayList<>();
        reportArrayList = new ArrayList<>();

        setUserInformation();
        reportArrayList = dbHelper.getAllDeliveryReportList();

        if(reportArrayList.size() == 0) {
            showNoOrderAlert();
        } else {
            loadOrderFromDB();
        }
    }

    public void setUserInformation() {
        deliveryManList = dbHelper.getAllDeliveryMenList();
        DeliveryMan deliveryMan = deliveryManList.get(0);
        name.setText(deliveryMan.getName());
        email.setText(deliveryMan.getEmail());
        address.setText(deliveryMan.getAddress());
    }

    private void showNoOrderAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(NO_ORDER_FOUND_FROM_DB);
        alertDialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void loadOrderFromDB() {
        for(int i=0; i< reportArrayList.size(); i++) {
            orderList.add(reportArrayList.get(i).getOrderNumber());
        }
        arrayAdapter = new ArrayAdapter<String>(DeliveryDashboard.this, android.R.layout.simple_list_item_1, orderList);
        reportList.setAdapter(arrayAdapter);
        orderNumberClickListener(reportList);
    }

    public void reloadOrderListClickHandler(View view) {
        DeliveryMan deliveryMan = dbHelper.getAllDeliveryMenList().get(0);
        orderLoad(deliveryMan.getId());
    }

    public void orderLoad (final String deliveryPartnerId) {
        reportOrderNumbersList.clear();
        dbHelper.removeAllFromDeliveryReport();
        final ProgressDialog loading = ProgressDialog.show(this,"Your order is loading","Please wait until loading process finish...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_DELIVERY_REPORT_LIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jobj = new JSONObject(response);
                            JSONArray jarray = jobj.getJSONArray("records");
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject jo = jarray.getJSONObject(i);
                                if( deliveryPartnerId.equals(jo.getString(KEY_DELIVERY_REPORT_PARTNER_ID)) ) {
                                    flag = true;
                                    Report report = new Report();
                                    report.setId(jo.getString(KEY_DELIVERY_REPORT_ID));
                                    report.setDeliveryManId(jo.getString(KEY_DELIVERY_REPORT_PARTNER_ID));
                                    report.setCustomerName(jo.getString(KEY_DELIVERY_REPORT_CUSTOMER_NAME));
                                    report.setCustomerNameOverride(jo.getString(KEY_DELIVERY_REPORT_CUSTOMER_NAME_OVERRIDE));
                                    report.setOrderNumber(jo.getString(KEY_DELIVERY_REPORT_ORDER_NUMBER));
                                    report.setOrderNumberOverride(jo.getString(KEY_DELIVERY_REPORT_ORDER_NUMBER_OVERRIDE));
                                    report.setOrderBy(jo.getString(KEY_DELIVERY_REPORT_ORDER_BY));
                                    report.setOrderByOverride(jo.getString(KEY_DELIVERY_REPORT_ORDER_BY_OVERRIDE));
                                    report.setOrderDate(jo.getString(KEY_DELIVERY_REPORT_ORDER_DATE));
                                    report.setOrderDateOverride(jo.getString(KEY_DELIVERY_REPORT_ORDER_DATE_OVERRIDE));
                                    report.setDeliveryDate(jo.getString(KEY_DELIVERY_REPORT_DELIVERY_DATE));
                                    report.setDeliveryDateOverride(jo.getString(KEY_DELIVERY_REPORT_DELIVERY_DATE_OVERRIDE));
                                    report.setDeliveredToName(jo.getString(KEY_DELIVERY_REPORT_DELIVERY_TO_NAME));
                                    report.setDeliveredToNameOverride(jo.getString(KEY_DELIVERY_REPORT_DELIVERY_TO_NAME_OVERRIDE));
                                    report.setStatus(jo.getString(KEY_DELIVERY_REPORT_STATUS));
                                    report.setComments(jo.getString(KEY_DELIVERY_REPORT_COMMENT));
                                    report.setImageURL(jo.getString(KEY_DELIVERY_REPORT_IMAGE_URL));
                                    report.setSignatureURL(jo.getString(KEY_DELIVERY_REPORT_SIGNATURE_URL));

                                    reportOrderNumbersList.add(jo.getString(KEY_DELIVERY_REPORT_ORDER_NUMBER));
                                    reportArrayList.add(report);
                                    dbHelper.addDeliveryReport(report);

                                    toasterMessage = new ToasterMessage();
                                    toasterMessage.showInformationToaster(DeliveryDashboard.this, "Your order list....");
                                } else {
                                    if(i == (jarray.length()-1) && flag == false) {
                                        toasterMessage = new ToasterMessage();
                                        toasterMessage.showErrorToaster(DeliveryDashboard.this, NO_ORDER_FOUND_FOR_YOU);
                                    }
                                }
                            }
                            arrayAdapter = new ArrayAdapter<String>(DeliveryDashboard.this, android.R.layout.simple_list_item_1, reportOrderNumbersList);
                            reportList.setAdapter(arrayAdapter);
                            orderNumberClickListener(reportList);
                            loading.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            toasterMessage = new ToasterMessage();
                            toasterMessage.showInformationToaster(DeliveryDashboard.this, "There is no order created.\nCommunicate with Shasroy Manager");
                            loading.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(DeliveryDashboard.this);
        queue.add(stringRequest);
    }

    public void orderNumberClickListener(final ListView orderList) {
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DeliveryDashboard.this, DeliveryReport.class);
                intent.putExtra(INTENT_EXTRA_DELIVERY_DASHBOARD_ORDER_NUMBER, reportArrayList.get(position).getOrderNumber());
                startActivity(intent);
            }
        });
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