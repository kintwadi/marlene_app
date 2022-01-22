package com.deutsch.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.deutsch.R;
import com.deutsch.fragment.Main;
import com.deutsch.model.Word;
import com.deutsch.service.AppService;
import com.deutsch.util.Util;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
      // getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                AppService appService = new AppService(getApplicationContext());




                /*
                for (Word word : Util.localWordData()) {
                    appService.addWord(word);
                }

                */



               //Intent i = new Intent(SplashScreen.this, Login.class);
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

               finish();
            }
        }, 1000);

    }

}