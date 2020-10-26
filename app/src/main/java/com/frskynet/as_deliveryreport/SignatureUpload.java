package com.frskynet.as_deliveryreport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class SignatureUpload extends Activity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_upload);

        button = (Button) findViewById(R.id.report_sign_upload_usr_sign_submit);
    }

    public void cameraBtnHandlerForSignature(View view) {
    }

    public void uploadSignatureToServer(View view) {
        button.setEnabled(false);
        changeActivity();
    }

    private void changeActivity() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SignatureUpload.this, SubmitReport.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);
    }
}