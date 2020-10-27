package com.frskynet.as_deliveryreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ErrorActivity extends Activity {
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        errorMessage = (TextView) findViewById(R.id.error_message);
        errorMessage.setText("This is an error message This is an error message This is an error message This is an error message ");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {
            String text =(String) bundle.get("LOGIN_ACCESS_GRANTED");
            errorMessage.setText(text);
        }
    }
}