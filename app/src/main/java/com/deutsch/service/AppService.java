package com.deutsch.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import androidx.lifecycle.LiveData;

import com.deutsch.database.AppDatabase;
import com.deutsch.model.User;
import com.deutsch.model.Word;

import java.util.ArrayList;
import java.util.List;

public class AppService {

    private static final String TAG = "AppService";
    private final Context applicationContext;
    public static List<User> users = new ArrayList<User>();

    public AppService(Context applicationContext) {

        this.applicationContext = applicationContext;
    }

    public void addUser(User user) {

        class saveAsync extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                long result = AppDatabase.getInstance(applicationContext).dao().insertUser(user);
                //Log.i(TAG, " in add user: " + result);
               // Log.i(TAG, " in add user: " + user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(applicationContext, "user Saved", Toast.LENGTH_LONG).show();
            }
        }
        saveAsync st = new saveAsync();
        st.execute();
    }

    public LiveData<List<User> > getAllUsers() {

        LiveData<List<User> > users = AppDatabase.getInstance(applicationContext).dao().getUsers();
        return users;
    }

    public LiveData<User>  getUserByEmail(final String email) {
        LiveData<User> user = AppDatabase.getInstance(applicationContext).dao().getUserByEmail(email);


        return user;
    }

    public LiveData<User> login(String name, String password) {

        LiveData<User> user = AppDatabase.getInstance(applicationContext).dao().getUserByPasswordAndUserName(name, password);

        return user;
    }

    public void updatePassword(String email, String password) {

        class ListAllAsync extends AsyncTask<Void, Void, Void> {


            @Override
            protected Void doInBackground(Void... voids) {

                AppDatabase.getInstance(applicationContext).dao().updatePassword(email, password);

                //Log.i(TAG, " update password : " + email);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //Toast.makeText(applicationContext, user.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ListAllAsync st = new ListAllAsync();
        st.execute();
    }


    public void addWord(Word word
    ) {

        class saveAsync extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                long result = AppDatabase.getInstance(applicationContext).dao().insertWord(word);
                //Log.i(TAG, " in add word: " + word  + "query result: "+result);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(applicationContext, "word Saved", Toast.LENGTH_LONG).show();
            }
        }
        saveAsync st = new saveAsync();
        st.execute();
    }
    public List<Word>  findAllWords() {
        List<Word>  words = AppDatabase.getInstance(applicationContext).dao().findAllWords();
        return words;
    }
    public List<Word> findWords(final String pattern) {
        String search = "%"+pattern+"%";
        return AppDatabase.getInstance(applicationContext).dao().findWords(search);
    }
}
