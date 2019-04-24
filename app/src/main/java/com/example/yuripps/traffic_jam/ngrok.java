package com.example.yuripps.traffic_jam;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yuripps.traffic_jam.DatabaseHelper;

public class ngrok extends AppCompatActivity {
    private final AppCompatActivity activity = ngrok.this;
    private EditText ngrok;
    //    private InputValidation inputValidation;
    //DatabaseHelper db = new DatabaseHelper(activity);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngrok);

        call_ng();

    }

    DatabaseHelper db = new DatabaseHelper(activity);

    private void call_ng(){
//        textInputEditTextNgrok = (TextInputEditText) findViewById(R.id.textInputEditTextNgrok);
//        String ng = textInputEditTextNgrok.getText().toString().trim();
        Button st = findViewById(R.id.savePath_button);
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ngrok = (EditText) findViewById(R.id.path_editText);
                String ng = ngrok.getText().toString().trim();
                if(ng != null && ng.length() == 24){
                    db.addNg(ng);
                    Intent i = new Intent(ngrok.this,MainActivity.class);
                    startActivity(i);
                    //Toast.makeText(getBaseContext(), db1.getNg(), Toast.LENGTH_LONG).show();
                }
                else if(ng.length() == 0){
                    Toast.makeText(getBaseContext(), "กรุณาใส่ URL", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getBaseContext(), "กรุณาใส่ URL ให้ถูกต้อง", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
