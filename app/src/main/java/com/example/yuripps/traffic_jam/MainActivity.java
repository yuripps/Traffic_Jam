package com.example.yuripps.traffic_jam;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yuripps.traffic_jam.utils.PreferenceUtils;

public class MainActivity extends AppCompatActivity {

    private TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewName = (TextView) findViewById(R.id.text1);
        Intent intent = getIntent();
        if (intent.hasExtra("EMAIL")){
            String nameFromIntent = getIntent().getStringExtra("EMAIL");
            textViewName.setText("Welcome " + nameFromIntent);
        }else{
            String email = PreferenceUtils.getName(this);
            textViewName.setText("Welcome " + email);

        }

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
                Intent i = new Intent(MainActivity.this, com.example.yuripps.traffic_jam.DistcountDetail.class);
                startActivity(i);

            }
        });

        Button why = findViewById(R.id.button4);
        why.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, com.example.yuripps.traffic_jam.Detail.class);
                startActivity(i);

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.log_out:
                PreferenceUtils.savePassword(null, this);
                PreferenceUtils.saveName(null, this);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
