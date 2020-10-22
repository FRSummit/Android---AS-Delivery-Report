package com.frskynet.as_deliveryreport;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class NonDeliveryMan extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_delivery_man);
    }

    public void loginBtnHandler(View view) {
        startActivity(new Intent(NonDeliveryMan.this, Signin.class));
    }

    public void deliveryManHandler(View view) {
        Uri uri = Uri.parse("https://amarshasroy.atlassian.net/servicedesk/customer/portals");
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void visitWebsiteHandler(View view) {
        Uri uri = Uri.parse("http://amarshasroy.com");
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}