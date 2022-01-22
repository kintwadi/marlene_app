package com.deutsch.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.deutsch.R;
import com.santalu.maskara.widget.MaskEditText;

public class PasswordVerification extends AppCompatActivity {
    private static final String TAG = "PasswordVerification";
    MaskEditText verifycode;
    String storedCode = "";
    String storedEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_verification);
        verifycode = (MaskEditText) findViewById(R.id.edit_verify_code);
        Intent intent = getIntent();
        storedCode = intent.getStringExtra("PIN");
        storedEmail = intent.getStringExtra("email");

    }

    public void verifyEventListener(View view) {


        Log.i(TAG, "code : " + verifycode.getMasked());
        Log.i(TAG, "code raw : " + verifycode.getUnMasked());
        Log.i(TAG, "storedCode : " + storedCode);
        Log.i(TAG, "storedEmail : " + storedEmail);
        // Toast.makeText(this,verifycode.getText(),Toast.LENGTH_SHORT).show();

        if (storedCode != null &&
                storedCode.equals(verifycode.getUnMasked())) {

            Intent i = new Intent(PasswordVerification.this, PasswordChange.class);
            i.putExtra("email", storedEmail);
            startActivity(i);

            finish();
        }

    }

    public void backEventListener(View view) {

        Intent i = new Intent(PasswordVerification.this, PasswordRecoverOption.class);
        startActivity(i);

        finish();
    }
}