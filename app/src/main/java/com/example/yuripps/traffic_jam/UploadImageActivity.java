package com.example.yuripps.traffic_jam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class UploadImageActivity extends AppCompatActivity {

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        db = new DatabaseHelper(this);

        showImage();

        Button cancel_Button = findViewById(R.id.cancel_Button);
        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadImageActivity.this, CameraAPIActivity.class);
                startActivity(intent);
            }
        });

        Button accept_Button = findViewById(R.id.accept_Button);
        accept_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resizeImage();
                uploadToHtdoc();
            }
        });

    }

    public void showImage(){
        OutputStream out = null;
        File file = new File(Environment.getExternalStorageDirectory()+"/car"+".jpg");
        ImageView image = (ImageView)findViewById(R.id.picTest);
        Matrix matrix = new Matrix();
        Intent intent = getIntent();
        String ImageFrom = intent.getStringExtra("ImageFrom");
        if(ImageFrom.equals("gallery")){
            matrix.postRotate(360);
        }
        else if(ImageFrom.equals("camera")){
            matrix.postRotate(90);
        }
        Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/car"+".jpg");
        Bitmap rotated = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,true);

        try {
            out = new FileOutputStream(file);
            rotated.compress(Bitmap.CompressFormat.JPEG, 100, out);
            image.setImageBitmap(rotated);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void uploadToHtdoc(){
        String url = db.getNg();
        Toast.makeText(getBaseContext(), "อัปโหลดรูป", Toast.LENGTH_LONG).show();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm", Locale.KOREA);
        Date now = new Date();
        String path = (Environment.getExternalStorageDirectory()+"/"+"car"+formatter.format(now)+".jpg");
        Ion.with(this)
                .load(url+"/car/upload.php")
                .setMultipartFile("upload_file", new File(path))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Intent i = new Intent(UploadImageActivity.this, com.example.yuripps.traffic_jam.ShowResultClassifyActivity.class);
                        startActivity(i);
                    }
                });
    }

    public void resizeImage(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm", Locale.KOREA);
        Date now = new Date();
        String path = (Environment.getExternalStorageDirectory()+"/car"+".jpg");
        String Newpath = (Environment.getExternalStorageDirectory()+"/"+"car"+formatter.format(now)+".jpg");

        Bitmap photo = BitmapFactory.decodeFile(path);
        photo = Bitmap.createScaledBitmap(photo,480,640,false);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 70,bytes);

        File f = new File(Newpath);
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
            File file = new File(path);
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}