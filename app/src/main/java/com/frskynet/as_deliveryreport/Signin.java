package com.frskynet.as_deliveryreport;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.frskynet.as_deliveryreport.ErrorMessages.SIGN_IN_USERNAME_ERROR_MESSAGE;
import static com.frskynet.as_deliveryreport.ErrorMessages.SIGN_IN_PASSWORD_ERROR_MESSAGE;

public class Signin extends Activity {

    private EditText username;
    private EditText password;
    private DataLoadFromSheet dataLoadFromSheet;

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
        String usrName = username.getText().toString().trim();
        String usrPass = password.getText().toString().trim();

        if(!usrName.isEmpty() && !usrPass.isEmpty()) {
            dataLoadFromSheet.loadDeliveryPartnerData(this.getApplicationContext(), usrName, usrPass);
            dataLoadFromSheet.loadDeliveryReportData(this.getApplicationContext());
        } else {
            if(usrName.isEmpty()) {
                username.setError(SIGN_IN_USERNAME_ERROR_MESSAGE);
            } if(usrPass.isEmpty()) {
                password.setError(SIGN_IN_PASSWORD_ERROR_MESSAGE);
            }
        }
    }
}