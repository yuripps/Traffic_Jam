package com.example.yuripps.traffic_jam;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.yuripps.traffic_jam.helper.InputValidation;
import com.example.yuripps.traffic_jam.model.User;
import com.example.yuripps.traffic_jam.utils.PreferenceUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private AppCompatTextView sever;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }
    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

//        sever = (AppCompatTextView) findViewById(R.id.sever);

        PreferenceUtils utils = new PreferenceUtils();

        if (utils.getName(this) != null ){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }else{

        }
    }

    private void initListeners(){
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
//        sever.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
//            case R.id.sever:
//                Intent intentsever = new Intent(getApplicationContext(), ngrok_login.class);
//                startActivity(intentsever);
//                break;

        }
    }

    private void verifyFromSQLite(){
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutName, getString(R.string.error_message_email))) {
            return;
        }
//        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
//            return;
//        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }
        String name = textInputEditTextEmail.getText().toString().trim();
        String password = textInputEditTextPassword.getText().toString().trim();

        if (databaseHelper.checkUser(name, password)) {
            PreferenceUtils.saveName(name, this);
            databaseHelper.add(name);
            PreferenceUtils.savePassword(password, this);
            Intent accountsIntent = new Intent(activity, MainActivity.class);
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            //emptyInputEditText();

            startActivity(accountsIntent);
            finish();



        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

//    private void emptyInputEditText(){
//        textInputEditTextEmail.setText(null);
//        textInputEditTextPassword.setText(null);
//    }

}
