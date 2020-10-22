package com.frskynet.as_deliveryreport;

import android.app.Activity;
import android.content.Context;

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

/**
 * Created by F R Summit on 22th October,2020
 * Simplexhub Limited
 * frsummit@simplexhub.com
 */
public class DataLoadFromSheet {
    private ArrayList<DeliveryMan> deliveryMenList;

    public void loadDeliveryPartnerData(Context context, final String username, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_DELIVERY_PARTNER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        System.out.println(response);
                        try {
                            JSONObject jobj = new JSONObject(response);
                            JSONArray jarray = jobj.getJSONArray("records");
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject jo = jarray.getJSONObject(i);
                                if(( username.equals(jo.getString(KEY_DELIVERY_PARTNER_EMAIL)) ||
                                        username.equals("0" + jo.getString(KEY_DELIVERY_PARTNER_PHONE)) ||
                                        username.equals("00880" + jo.getString(KEY_DELIVERY_PARTNER_PHONE)) ||
                                        username.equals("+880" + jo.getString(KEY_DELIVERY_PARTNER_PHONE)) ||
                                        username.equals(jo.getString(KEY_DELIVERY_PARTNER_USERNAME)) ) &&
                                        (password.equals(jo.getString(KEY_DELIVERY_PARTNER_PASSWORD))) ) {
                                    System.out.println("Access Granted");
                                    String approval = jo.getString(KEY_DELIVERY_PARTNER_IS_APPROVED);
                                    if(approval.equals("1")) {
                                        System.out.println("Approved");

                                        DeliveryMan deliveryMan = new DeliveryMan();
                                        deliveryMan.setId(jo.getString(KEY_DELIVERY_PARTNER_ID));
                                        deliveryMan.setName(jo.getString(KEY_DELIVERY_PARTNER_NAME));
                                        deliveryMan.setEmail(jo.getString(KEY_DELIVERY_PARTNER_EMAIL));
                                        deliveryMan.setPhone(jo.getString(KEY_DELIVERY_PARTNER_PHONE));
                                        deliveryMan.setAddress(jo.getString(KEY_DELIVERY_PARTNER_ADDRESS));
                                        deliveryMan.setUsername(jo.getString(KEY_DELIVERY_PARTNER_USERNAME));
                                        deliveryMan.setPassword(jo.getString(KEY_DELIVERY_PARTNER_PASSWORD));
                                        deliveryMan.setIsApproved(jo.getString(KEY_DELIVERY_PARTNER_IS_APPROVED));


                                    } else {
                                        System.out.println("Apply for approval");
                                    }
                                } else {
                                    System.out.println("Access Denied");
                                }
//                                DeliveryMan deliveryMan = new DeliveryMan();
//                                deliveryMan.setId(jo.getString(KEY_DELIVERY_PARTNER_ID));
//                                deliveryMan.setName(jo.getString(KEY_DELIVERY_PARTNER_NAME));
//                                deliveryMan.setEmail(jo.getString(KEY_DELIVERY_PARTNER_EMAIL));
//                                deliveryMan.setPhone(jo.getString(KEY_DELIVERY_PARTNER_PHONE));
//                                deliveryMan.setAddress(jo.getString(KEY_DELIVERY_PARTNER_ADDRESS));
//                                deliveryMan.setUsername(jo.getString(KEY_DELIVERY_PARTNER_USERNAME));
//                                deliveryMan.setPassword(jo.getString(KEY_DELIVERY_PARTNER_PASSWORD));
//                                deliveryMan.setIsApproved(jo.getString(KEY_DELIVERY_PARTNER_IS_APPROVED));

//                                deliveryMenList = new ArrayList<>();
//                                deliveryMenList.add(deliveryMan);
                            }
                            System.out.println("List : " + deliveryMenList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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