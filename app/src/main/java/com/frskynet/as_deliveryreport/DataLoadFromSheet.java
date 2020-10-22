package com.frskynet.as_deliveryreport;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import static com.frskynet.as_deliveryreport.Configuration.GET_DELIVERY_PARTNER_URL;
import static com.frskynet.as_deliveryreport.Configuration.GET_DELIVERY_REPORT_LIST_URL;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_ID;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_NAME;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_EMAIL;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_PHONE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_ADDRESS;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_USERNAME;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_PASSWORD;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_IS_APPROVED;
import static com.frskynet.as_deliveryreport.ErrorMessages.SIGN_IN_NO_APPROVAL;
import static com.frskynet.as_deliveryreport.ErrorMessages.ACCESS_DENIED;
import static com.frskynet.as_deliveryreport.ErrorMessages.SIGN_IN_APPROVAL_GRANTED;


/**
 * Created by F R Summit on 22th October,2020
 * Simplexhub Limited
 * frsummit@simplexhub.com
 */
public class DataLoadFromSheet {
    private DBHelper dbHelper;
    private ToasterMessage toasterMessage;

    public void loadDeliveryPartnerData(final Context context, final String username, final String password, final ProgressDialog loading) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_DELIVERY_PARTNER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jobj = new JSONObject(response);
                            JSONArray jarray = jobj.getJSONArray("records");
                            System.out.println(jarray.length());
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject jo = jarray.getJSONObject(i);
                                if(( username.equals(jo.getString(KEY_DELIVERY_PARTNER_EMAIL)) ||
                                        username.equals(jo.getString(KEY_DELIVERY_PARTNER_PHONE)) ||
                                        username.equals("0" + jo.getString(KEY_DELIVERY_PARTNER_PHONE)) ||
                                        username.equals("00880" + jo.getString(KEY_DELIVERY_PARTNER_PHONE)) ||
                                        username.equals("+880" + jo.getString(KEY_DELIVERY_PARTNER_PHONE)) ||
                                        username.equals(jo.getString(KEY_DELIVERY_PARTNER_USERNAME)) ) &&
                                        (password.equals(jo.getString(KEY_DELIVERY_PARTNER_PASSWORD))) ) {
                                    String approval = jo.getString(KEY_DELIVERY_PARTNER_IS_APPROVED);
                                    System.out.println("user");
                                    if(approval.equals("1")) {
                                        System.out.println("approved");
                                        DeliveryMan deliveryMan = new DeliveryMan();
                                        deliveryMan.setId(jo.getString(KEY_DELIVERY_PARTNER_ID));
                                        deliveryMan.setName(jo.getString(KEY_DELIVERY_PARTNER_NAME));
                                        deliveryMan.setEmail(jo.getString(KEY_DELIVERY_PARTNER_EMAIL));
                                        deliveryMan.setPhone(jo.getString(KEY_DELIVERY_PARTNER_PHONE));
                                        deliveryMan.setAddress(jo.getString(KEY_DELIVERY_PARTNER_ADDRESS));
                                        deliveryMan.setUsername(jo.getString(KEY_DELIVERY_PARTNER_USERNAME));
                                        deliveryMan.setPassword(jo.getString(KEY_DELIVERY_PARTNER_PASSWORD));
                                        deliveryMan.setIsApproved(jo.getString(KEY_DELIVERY_PARTNER_IS_APPROVED));

                                        dbHelper = new DBHelper(context, null, null, 1);
                                        dbHelper.addDeliveryManDetails(deliveryMan);
                                        Intent intent = new Intent(context, DeliveryDashboard.class);
                                        intent.putExtra("LOGIN_ACCESS_GRANTED", SIGN_IN_APPROVAL_GRANTED);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);
                                        loading.dismiss();
                                        break;
                                    } else {
                                        System.out.println("not approved");
                                        toasterMessage = new ToasterMessage();
                                        toasterMessage.showErrorToaster(context, SIGN_IN_NO_APPROVAL);
                                        loading.dismiss();
                                    }
                                } else {
                                    if(i == jarray.length()-1) {
                                        toasterMessage = new ToasterMessage();
                                        toasterMessage.showErrorToaster(context, ACCESS_DENIED);
                                        loading.dismiss();
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toasterMessage = new ToasterMessage();
                        toasterMessage.showErrorToaster(context, error.toString());
                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public void loadDeliveryReportData(Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_DELIVERY_REPORT_LIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        System.out.println(response);
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }
}
