package com.deutsch.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deutsch.R;
import com.deutsch.fragment.Glossary;
import com.deutsch.fragment.Main;
import com.deutsch.fragment.Quiz;
import com.deutsch.fragment.Search;
import com.deutsch.fragment.SearchDetails;
import com.deutsch.fragment.Setup;
import com.deutsch.fragment.Study;
import com.deutsch.model.Word;
import com.deutsch.service.AppService;
import com.deutsch.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, Search.OnSearchMessageListener,SearchDetails.OnSearchDetailsMessageListener {
    private static final String TAG = "MainActivity";
   BottomNavigationView bottomNavigationView;
   private View decorView;
    Search search = new Search();
    Study study = new Study();
    Quiz quiz = new Quiz();
    Glossary glossary = new Glossary();
    Setup setup = new Setup();
    SearchDetails searchDetails = new SearchDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        //addMainFragment(savedInstanceState);
        selectDefaultItem();

        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(hideSystemBars());
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        AppService appService = new AppService(getApplicationContext());

    }
    private void selectDefaultItem(){

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navegation_search);
    }

    private void addMainFragment(Bundle savedInstanceState){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(findViewById(R.id.main_container) != null){
            if(savedInstanceState != null){

                return;
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Main mainFragment = new Main();
            fragmentTransaction.add(R.id.main_container,mainFragment,null);
            fragmentTransaction.addToBackStack(null).commit();
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            hideSystemBars();
        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        Log.i(TAG, "seleted item: "+item.getItemId());
        switch (item.getItemId()) {
            case R.id.navegation_search:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.main_container, search).addToBackStack(null).commit();

                return true;

            case R.id.navegatio_study:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.main_container, study).addToBackStack(null).commit();
                return true;

            case R.id.navegation_quiz:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.main_container, quiz).addToBackStack(null).commit();
                return true;

            case R.id.navegation_glossary:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.main_container, glossary).addToBackStack(null).commit();
                return true;

            case R.id.navegation_setup:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.main_container, setup).addToBackStack(null).commit();
                return true;


        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /*
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem item = menu.findItem((R.id.action_search));
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                search.getmAdapter().getFilter().filter(newText);
                return false;
            }
        });

         */
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSearchMessageSend(int position,String message) {

        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.main_container, searchDetails).addToBackStack(null).commit();

        Log.i(TAG, "message : "+position +" - "+message);
    }

    @Override
    public void back() {

        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.main_container, search).addToBackStack(null).commit();

        Log.i(TAG, "back to search fragment");
    }

}