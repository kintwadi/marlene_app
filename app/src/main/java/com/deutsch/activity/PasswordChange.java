package com.deutsch.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.deutsch.R;
import com.deutsch.service.AppService;

public class PasswordChange extends AppCompatActivity {

    EditText passwordNew;
    EditText passwordNewRepeat;
    private String storedEmail = "";
    AppService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        passwordNew = (EditText)findViewById(R.id.edit_password_new);
        passwordNewRepeat = (EditText)findViewById(R.id.edit_password_new_repeat);
        service = new AppService(getApplicationContext());
        storedEmail = getIntent().getStringExtra("email");
    }

    public void resetPasswordEventListener(View view) {

        if(passwordNew.getText().toString().equals(passwordNewRepeat.getText().toString())){

            service.updatePassword(storedEmail,passwordNew.getText().toString());
            Intent i = new Intent(PasswordChange.this, MainActivity.class);
            startActivity(i);
            finish();
        }else{
            Toast.makeText(this,"password don't match",Toast.LENGTH_SHORT).show();
        }

    }

    public void backEventListener(View view) {

        Intent i = new Intent(PasswordChange.this, PasswordVerification.class);
        startActivity(i);
        finish();
    }
}