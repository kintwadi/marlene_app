package com.deutsch.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.deutsch.R;
import com.deutsch.database.SQLiteDataBaseConfigHelper;
import com.deutsch.model.User;
import com.deutsch.service.AppService;
import com.deutsch.locale.Country;
import com.deutsch.locale.CountryAdaper;
import com.deutsch.util.Util;
import com.santalu.maskara.widget.MaskEditText;

public class Signup extends AppCompatActivity {


    private static final String TAG = "Signup";
    EditText username;
    EditText email;
    MaskEditText telephone;
    EditText password;
    EditText passwordRepeat;
    TextView signupMessage;
    SQLiteDataBaseConfigHelper SQLiteDataBaseConfigHelper = null;
    AppService service;
    CountryAdaper countryAdaper;
    Country selectedCountry = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = (EditText)findViewById(R.id.edit_signup_name);
        email = findViewById(R.id.edit_signup_email);
        telephone = (MaskEditText)findViewById(R.id.edit_signup_telephone);
        password = (EditText)findViewById(R.id.edit_signup_password);
        passwordRepeat = (EditText)findViewById(R.id.edit_signup_password_repeat);
        signupMessage = findViewById(R.id.text_signup_message);
        SQLiteDataBaseConfigHelper = new SQLiteDataBaseConfigHelper(Signup.this);
        service = new AppService(getApplicationContext());
        Spinner countriesSpinner = findViewById(R.id.spinner_country_flag);
        countryAdaper = new CountryAdaper(getApplicationContext(), Util.getCountryList());
        countriesSpinner.setAdapter(countryAdaper);

        countriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCountry = (Country) parent.getItemAtPosition(position);
                Log.i(TAG, "add user validated : " + selectedCountry.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void backEventListener(View v) {

        Intent i = new Intent(Signup.this, Login.class);
        startActivity(i);
        finish();
    }

    public void signupEventListener(View view) {


        User user = new User();
        user.setName(username.getText().toString().trim());
        user.setEmail(email.getText().toString().trim());
        user.setTelephone(telephone.getUnMasked().trim());
        user.setPassword(password.getText().toString().trim());

        if (Util.validateUser(user, passwordRepeat.getText().toString())) {

            service.addUser(user);

            Toast.makeText(Signup.this, "create user: success ", Toast.LENGTH_SHORT);
            Log.i(TAG, " create user: success  : "+service.getAllUsers());

            Intent i = new Intent(Signup.this, MainActivity.class);
            startActivity(i);
            finish();

        } else {
            signupMessage.setText(Util.errorMessageMap.get("key"));
            Log.i(TAG, "create user: fail : ");
        }

    }
}