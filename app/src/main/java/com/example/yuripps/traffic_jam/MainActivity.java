package com.example.yuripps.traffic_jam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cam = findViewById(R.id.button3);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, com.example.yuripps.traffic_jam.CameraAPIActivity.class);
                startActivity(i);

            }
        });

        Button server = findViewById(R.id.button);
        server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, com.example.yuripps.traffic_jam.ngrok.class);
                startActivity(i);

            }
        });

        Button dis = findViewById(R.id.button2);
        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, com.example.yuripps.traffic_jam.ShowResultClassifyActivity.class);
                startActivity(i);

            }
        });
    }
}
