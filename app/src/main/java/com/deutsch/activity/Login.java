package com.deutsch.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.deutsch.R;
import com.deutsch.model.User;
import com.deutsch.service.AppService;
import com.deutsch.locale.Country;
import com.deutsch.locale.CountryAdaper;
import com.deutsch.locale.LocaleHelper;
import com.deutsch.util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    EditText loginname;
    EditText password;
    TextView forgotPassword;
    TextView loginMessage;
    AppService service;
    CountryAdaper countryAdaper;
    Country selectedCountry = null;
    Context context;
    Resources resources;
    Button btnLogin;
    List<User> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        setContentView(R.layout.activity_login);
        loginname = (EditText) findViewById(R.id.edit_sign_name);
        password = (EditText) findViewById(R.id.edit_sign_password);
        loginMessage = findViewById(R.id.login_message);
        btnLogin = findViewById(R.id.btn_login);
        forgotPassword = findViewById(R.id.textView_forgot_password);
        forgotPassword.setPaintFlags(forgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        service = new AppService(getApplicationContext());
        Spinner countriesSpinner = findViewById(R.id.spinner_country_flag);
        countryAdaper = new CountryAdaper(getApplicationContext(), Util.getCountryList());
        countriesSpinner.setAdapter(countryAdaper);
        context = LocaleHelper.setLocale(Login.this, "en");
        resources = context.getResources();
        //readContacts(context);

        countriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCountry = (Country) parent.getItemAtPosition(position);
                context = LocaleHelper.setLocale(Login.this, selectedCountry.getLanguage());

                resources = context.getResources();

                String language = resources.getString(R.string.login);
                btnLogin.setText(language);
                loginname.setHint(resources.getString(R.string.user_name));
                password.setHint(resources.getString(R.string.password));
                TextView forgotPassword = findViewById(R.id.textView_forgot_password);
                forgotPassword.setText(resources.getString(R.string.forgot_password));
                loginMessage.setText("");

                Button createAccount = findViewById(R.id.btn_login2);
                createAccount.setText(resources.getString(R.string.create_account));

                Log.i(TAG, "country selected : " + selectedCountry.toString() + " text: " + language);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void loginEventListener(View view) {

        LiveData<User> user = service.login(loginname.getText().toString(), password.getText().toString());

        user.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

                if (user != null) {

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                    finish();
                } else {

                    loginMessage.setText(resources.getString(R.string.invalid_login_data));
                }
            }
        });


    }

    public void forgotPasswordEventListener(View view) {

        Intent i = new Intent(Login.this, PasswordRecoverOption.class);
        startActivity(i);

        finish();

    }

    public void createAccountEventListener(View view) {

        Intent i = new Intent(Login.this, Signup.class);
        startActivity(i);

        finish();
    }


    public void backEventListener(View view) {


    }

    private void getAllUsers() {

        LiveData<List<User>> users = service.getAllUsers();
        users.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.i(TAG, "in Users : " + users);
            }
        });

    }

    public static void readContacts(Context context) {
      List<FavContact> contactList = new ArrayList<>();
        if (context == null)
            return;

        ContentResolver contentResolver = context.getContentResolver();

        if (contentResolver == null)
            return;

        String[] fieldListProjection = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,
                ContactsContract.Contacts.PHOTO_URI
                ,ContactsContract.Contacts.STARRED
        };
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + " ASC";
        Cursor phones = contentResolver
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                        , fieldListProjection, null, null, sort);
        HashSet<String> normalizedNumbersAlreadyFound = new HashSet<>();

        if (phones != null && phones.getCount() > 0) {
            while (phones.moveToNext()) {
                String normalizedNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                if (Integer.parseInt(phones.getString(phones.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    if (normalizedNumbersAlreadyFound.add(normalizedNumber)) {

                        int id = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                        String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        int fav = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
                        boolean isFav;
                        isFav= fav == 1;

                        String uri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                        if(uri!=null){
                            contactList.add(new FavContact(id,isFav,uri,name,phoneNumber));
                        }
                        else{
                            contactList.add(new FavContact(id,isFav,name,phoneNumber));
                        }

                    }
                }
            }
            phones.close();
        }
    }
}

class FavContact{

    private int id;

    private boolean isFavorite;

    private String image;

    private String name;

    private String number;


    public FavContact(int id,boolean isFavorite, String image, String name, String number){
        this.id=id;
        this.isFavorite = isFavorite;
        this.image = image;
        this.name = name;
        this.number = number;
    }

    public FavContact(int id,boolean isFavorite, String name, String number){
        this.id=id;
        this.isFavorite = isFavorite;
        this.name = name;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FavContact{");
        sb.append("id=").append(id);
        sb.append(", isFavorite=").append(isFavorite);
        sb.append(", image='").append(image).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", number='").append(number).append('\'');
        sb.append('}');
        return sb.toString();
    }
}