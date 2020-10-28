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
import android.widget.Spinner;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
    private ArrayList<String> reportStatusList;
    private ArrayAdapter<String> arrayAdapter;
    private ToasterMessage toasterMessage;
    boolean flag = false;
    private DeliveryDashboardListAdapter deliveryDashboardListAdapter;
    private Spinner spinner;
    private List<String> sortList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_dashboard);

        dbHelper = new DBHelper(this, null, null, 1);
        name =  (TextView) findViewById(R.id.dashboard_user_name);
        email =  (TextView) findViewById(R.id.dashboard_user_email);
        address =  (TextView) findViewById(R.id.dashboard_user_address);
        reportList = findViewById(R.id.dashboard_order_list);
        toasterMessage = new ToasterMessage();
        reportOrderNumbersList = new ArrayList<>();
        reportStatusList = new ArrayList<>();
        reportArrayList = new ArrayList<>();
        spinner = (Spinner) findViewById(R.id.sort_spinner);

        sortSpinnerItems();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                sortHandler(sortList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(DeliveryDashboard.this, "Nothing Select", Toast.LENGTH_LONG).show();
            }

        });

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
        deliveryDashboardListAdapter = new DeliveryDashboardListAdapter(DeliveryDashboard.this, reportArrayList);
        reportList.setAdapter(deliveryDashboardListAdapter);
        orderNumberClickListener(reportList);
    }

    public void reloadOrderListClickHandler(View view) {
        DeliveryMan deliveryMan = dbHelper.getAllDeliveryMenList().get(0);
        orderLoad(deliveryMan.getId());
    }

    public void orderLoad (final String deliveryPartnerId) {
        reportOrderNumbersList.clear();
        reportStatusList.clear();
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
                                    reportStatusList.add(jo.getString(KEY_DELIVERY_REPORT_STATUS));
                                    reportArrayList.add(report);
                                    dbHelper.addDeliveryReport(report);
                                    Toast.makeText(DeliveryDashboard.this,"Your order list....", Toast.LENGTH_LONG).show();
                                } else {
                                    if(i == (jarray.length()-1) && flag == false) {
                                        Toast.makeText(DeliveryDashboard.this,"You have no order.\nCommunicate with Shasroy Manager", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            deliveryDashboardListAdapter = new DeliveryDashboardListAdapter(DeliveryDashboard.this, reportArrayList);
                            reportList.setAdapter(deliveryDashboardListAdapter);
                            orderNumberClickListener(reportList);
                            loading.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DeliveryDashboard.this,"There is no order created.\\nCommunicate with Shasroy Manager", Toast.LENGTH_LONG).show();
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

    public void sortHandler(final String sortItem) {
        Collections.sort(reportArrayList, new Comparator<Report>() {
            @Override
            public int compare(Report o1, Report o2) {
                if(sortItem.equals("Order Number")) {
                    return o1.getOrderNumber().compareTo(o2.getOrderNumber());
                }  else if(sortItem.equals("Order Status")) {
                    return o2.getStatus().compareTo(o1.getStatus());
                } else {
                    return o1.getOrderNumber().compareTo(o2.getOrderNumber());
                }
            }
        });
        deliveryDashboardListAdapter.notifyDataSetChanged();
    }

    private void sortSpinnerItems() {
        sortList = new ArrayList<>();
        sortList.add("Default Sort");
        sortList.add("Order Number");
        sortList.add("Order Status");
        spinnerArrayAdapter(spinner, sortList);
    }

    public void spinnerArrayAdapter(Spinner spinner, List<String> list) {
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
        sortAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(sortAdapter);
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