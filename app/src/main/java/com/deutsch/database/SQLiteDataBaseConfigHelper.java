package com.deutsch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.deutsch.model.User;
import com.deutsch.util.Constants;

public class SQLiteDataBaseConfigHelper extends SQLiteOpenHelper {

    public static final String TABLE_USER = "USER";
    public static final String COLUMN_USER_ID = "ID";

    public static final String COLUMN_USER_NAME = "NAME";
    public static final String COLUMN_USER_EMAIL = "EMAIL";
    public static final String COLUMN_USER_TELEPHONE = "TELEPHONE";
    public static final String COLUMN_USER_PASSWORD = "PASSWORD";



    public SQLiteDataBaseConfigHelper(@Nullable Context context) {
        super(context, Constants.DATABASE, null, Constants.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE ");
        query.append(TABLE_USER);
        query.append("(");
        query.append(COLUMN_USER_ID);
        query.append(" INTEGER PRIMARY KEY AUTOINCREMENT ");
        query.append(",");
        query.append(COLUMN_USER_NAME);
        query.append(" TEXT ");
        query.append(",");
        query.append(COLUMN_USER_EMAIL);
        query.append(" TEXT ");
        query.append(",");
        query.append(COLUMN_USER_TELEPHONE);
        query.append(" TEXT ");
        query.append(",");
        query.append(COLUMN_USER_PASSWORD);
        query.append(" TEXT ");
        query.append(");");
       // db.execSQL(query.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addUser(User user){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_NAME,user.getName());
        cv.put(COLUMN_USER_EMAIL,user.getEmail());
        cv.put(COLUMN_USER_TELEPHONE,user.getTelephone());
        cv.put(COLUMN_USER_PASSWORD,user.getPassword());
        long insert = db.insert(TABLE_USER, null, cv);
        db.close();

        return insert != -1;


    }
}
