package com.frskynet.as_deliveryreport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NonDeliveryMan extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_delivery_man);
    }

    public void shasroyLeaderHandler(View view) {
        Uri uri = Uri.parse("https://www.amarshasroy.com/become-a-shasroy-leader");
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
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