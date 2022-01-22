package com.deutsch.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.deutsch.R;
import com.deutsch.email.GMailSender;
import com.deutsch.util.Util;

import java.util.Random;


public class PasswordRecoverOption extends AppCompatActivity {

    EditText emailRecover;
    GMailSender sender;
    public static String PIN = "";
    public static String SHARED_PREFS = "sharedPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recover_option);
        emailRecover = (EditText) findViewById(R.id.edit_email_recover);
        sender = new GMailSender();

    }

    class MailAsync extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(PasswordRecoverOption.this);
            pDialog.setMessage("Sending PIN to: " + emailRecover.getText().toString());
            pDialog.show();

        }

        @Override

        protected Void doInBackground(Void... mApi) {
            try {
                String subject = "password PIN recover";
                String body = String.valueOf(generatePIN());
                PIN = body;
                body = "PIN: " + body;
                String recipient = emailRecover.getText().toString();
                saveSharedData();
                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail(subject, body, recipient);

                Log.d("send", "done: " + body);
            } catch (Exception ex) {
                Log.d("exception sending", ex.toString());
            }
            return null;
        }

        public int generatePIN() {
            Random rand1 = new Random();
            int resRandom1 = rand1.nextInt((9999 - 100) + 1) + 10;
            return resRandom1;
        }

        @Override

        protected void onPostExecute(Void result) {

            pDialog.cancel();

            //Toast.makeText(PasswordRecoverOption.this, "mail send to " +emailRecover.getText().toString(), Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);
            Intent i = new Intent(PasswordRecoverOption.this, PasswordVerification.class);
            i.putExtra("PIN", PIN);
            i.putExtra("email", emailRecover.getText().toString());
            startActivity(i);
            finish();

        }

        public void saveSharedData() {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", emailRecover.getText().toString());
            editor.putString("pin", PIN);

        }

    }

    public void recoverSendEventListener(View view) {

        if (Util.isEmailValid(emailRecover.getText().toString())) {

            new MailAsync().execute();

        } else {
            Toast.makeText(PasswordRecoverOption.this, "Invalid email ", Toast.LENGTH_SHORT).show();
        }
    }


    public void backEventListener(View view) {

        Intent i = new Intent(PasswordRecoverOption.this, Login.class);
        startActivity(i);
        finish();
    }


}