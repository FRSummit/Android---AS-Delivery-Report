package com.frskynet.as_deliveryreport;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

import static com.frskynet.as_deliveryreport.Configuration.GET_DELIVERY_PARTNER_URL;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_ADDRESS;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_EMAIL;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_ID;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_IS_APPROVED;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_NAME;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_PASSWORD;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_PHONE;
import static com.frskynet.as_deliveryreport.Configuration.KEY_DELIVERY_PARTNER_USERNAME;
import static com.frskynet.as_deliveryreport.ErrorMessages.ACCESS_DENIED;
import static com.frskynet.as_deliveryreport.ErrorMessages.SIGN_IN_APPROVAL_GRANTED;
import static com.frskynet.as_deliveryreport.ErrorMessages.SIGN_IN_NO_APPROVAL;
import static com.frskynet.as_deliveryreport.ErrorMessages.SIGN_IN_USERNAME_ERROR_MESSAGE;
import static com.frskynet.as_deliveryreport.ErrorMessages.SIGN_IN_PASSWORD_ERROR_MESSAGE;

public class Signin extends Activity {

    private EditText username;
    private EditText password;
    private DataLoadFromSheet dataLoadFromSheet;
    private ToasterMessage toasterMessage;
    private DBHelper dbHelper;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        username = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);

        dataLoadFromSheet = new DataLoadFromSheet();
    }

    public void visitWebsiteHandler(View view) {
        Uri uri = Uri.parse("http://amarshasroy.com");
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void signInClickHandler(View view) {
        final String usrName = username.getText().toString().trim();
        final String usrPass = password.getText().toString().trim();

        if(!usrName.isEmpty() && !usrPass.isEmpty()) {
            final ProgressDialog loading = ProgressDialog.show(this,"Checking your submission","Please wait until loading process finish...",false,false);
//            dataLoadFromSheet.loadDeliveryPartnerData(this.getApplicationContext(), usrName, usrPass, loading);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_DELIVERY_PARTNER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jobj = new JSONObject(response);
                                JSONArray jarray = jobj.getJSONArray("records");
                                for (int i = 0; i < jarray.length(); i++) {
                                    JSONObject jo = jarray.getJSONObject(i);
                                    System.out.println(usrName + "    " + usrPass);
                                    if(( usrName.equals(jo.getString(KEY_DELIVERY_PARTNER_EMAIL)) ||
                                            usrName.equals(jo.getString(KEY_DELIVERY_PARTNER_PHONE)) ||
                                            usrName.equals("0" + jo.getString(KEY_DELIVERY_PARTNER_PHONE)) ||
                                            usrName.equals("00880" + jo.getString(KEY_DELIVERY_PARTNER_PHONE)) ||
                                            usrName.equals("+880" + jo.getString(KEY_DELIVERY_PARTNER_PHONE)) ||
                                            usrName.equals(jo.getString(KEY_DELIVERY_PARTNER_USERNAME)) ) &&
                                            (usrPass.equals(jo.getString(KEY_DELIVERY_PARTNER_PASSWORD))) ) {
                                        String approval = jo.getString(KEY_DELIVERY_PARTNER_IS_APPROVED);
                                        if(approval.equals("1")) {
                                            flag = true;
                                            DeliveryMan deliveryMan = new DeliveryMan();
                                            deliveryMan.setId(jo.getString(KEY_DELIVERY_PARTNER_ID));
                                            deliveryMan.setName(jo.getString(KEY_DELIVERY_PARTNER_NAME));
                                            deliveryMan.setEmail(jo.getString(KEY_DELIVERY_PARTNER_EMAIL));
                                            deliveryMan.setPhone(jo.getString(KEY_DELIVERY_PARTNER_PHONE));
                                            deliveryMan.setAddress(jo.getString(KEY_DELIVERY_PARTNER_ADDRESS));
                                            deliveryMan.setUsername(jo.getString(KEY_DELIVERY_PARTNER_USERNAME));
                                            deliveryMan.setPassword(jo.getString(KEY_DELIVERY_PARTNER_PASSWORD));
                                            deliveryMan.setIsApproved(jo.getString(KEY_DELIVERY_PARTNER_IS_APPROVED));

                                            dbHelper = new DBHelper(Signin.this, null, null, 1);
                                            dbHelper.addDeliveryManDetails(deliveryMan);
                                            Intent intent = new Intent(Signin.this, DeliveryDashboard.class);
                                            intent.putExtra("LOGIN_ACCESS_GRANTED", SIGN_IN_APPROVAL_GRANTED);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            loading.dismiss();
                                            break;
                                        } else {
                                            toasterMessage = new ToasterMessage();
                                            toasterMessage.showErrorToaster(Signin.this, SIGN_IN_NO_APPROVAL);
                                            loading.dismiss();
                                            break;
                                        }
                                    } else {
                                        if(i == (jarray.length()-1) && flag == false) {
                                            toasterMessage = new ToasterMessage();
                                            toasterMessage.showErrorToaster(Signin.this, ACCESS_DENIED);
                                            loading.dismiss();
                                            break;
                                        }
                                        loading.dismiss();
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
                            toasterMessage.showErrorToaster(Signin.this, error.toString());
                        }
                    }
            );

            int socketTimeOut = 50000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue queue = Volley.newRequestQueue(Signin.this);
            queue.add(stringRequest);
//            dataLoadFromSheet.loadDeliveryReportData(this.getApplicationContext());
        } else {
            if(usrName.isEmpty()) {
                username.setError(SIGN_IN_USERNAME_ERROR_MESSAGE);
            } if(usrPass.isEmpty()) {
                password.setError(SIGN_IN_PASSWORD_ERROR_MESSAGE);
            }
        }
    }
}