package com.example.yuripps.traffic_jam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuripps.traffic_jam.utils.PreferenceUtils;

public class DistcountDetail extends AppCompatActivity {
    private final AppCompatActivity activity = DistcountDetail.this;
    private TextView textViewcount;
    DatabaseHelper db = new DatabaseHelper(activity);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distcount_detail);

        textViewcount = (TextView) findViewById(R.id.textView);
        String count = String.valueOf(db.checkDistcount(db.getName()));


        textViewcount.setText("คุณมีส่วนลดทั้งหมด \n" + count + "\nเที่ยว");


        Button main = findViewById(R.id.button6);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DistcountDetail.this, com.example.yuripps.traffic_jam.MainActivity.class);
                startActivity(i);

            }
        });
    }
}
