package com.frskynet.as_deliveryreport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReportImageUpload extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_image_upload);
    }

    public void cameraBtnHandler(View view) {
    }

    public void galleryBtnHandler(View view) {
    }

    public void uploadImageToServer(View view) {
    }

    public void goToSignatureHandler(View view) {
        startActivity(new Intent(this, SignatureUpload.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}