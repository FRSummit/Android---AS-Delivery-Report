package com.frskynet.as_deliveryreport;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import static com.frskynet.as_deliveryreport.ErrorMessages.NON_DELIVERY_MAN_BACK_BUTTON_PRESS;

public class NonDeliveryMan extends Activity {
    private ToasterMessage toasterMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_delivery_man);

        toasterMessage = new ToasterMessage();
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

    @Override
    public void onBackPressed() {
        toasterMessage.showInformationToaster(this, NON_DELIVERY_MAN_BACK_BUTTON_PRESS);
    }
}