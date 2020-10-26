package com.frskynet.as_deliveryreport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ReportImageUpload extends Activity {
    private ImageView imageView;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap rbitmap;
    private String userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_image_upload);

        imageView = (ImageView) findViewById(R.id.report_image_upload_view_section_display_image);
    }

    public void cameraBtnHandler(View view) {
        askCameraPermissions();
    }

    private void askCameraPermissions() {
    }

    //    Gallery Button Click: Part -1
    public void galleryBtnHandler(View view) {
        showFileChooser();
    }

    //    Gallery Button Click: Part -2 -> Choose file from gallery
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //    Gallery Button Click: Part -3 -> After choosing you will get a action result where we can see the loaded image.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                rbitmap = getResizedBitmap(bitmap, 250);//Setting the Bitmap to ImageView
                userImage = getStringImage(rbitmap);
                imageView.setImageBitmap(rbitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //    Gallery Button Click: Part -4 -> here image will resize by our ratio
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    //    Gallery Button Click: Part -1 -> This will convert the image as byte string
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;
    }

    public void uploadImageToServer(View view) {
    }

    public void goToSignatureHandler(View view) {
        startActivity(new Intent(this, SignatureUpload.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}