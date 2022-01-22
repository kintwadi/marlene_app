package com.deutsch.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.deutsch.database.dao.IDao;
import com.deutsch.model.User;
import com.deutsch.model.Word;
import com.deutsch.util.Constants;

@Database(entities = {User.class, Word.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase dbInstance = null;
    public abstract IDao dao();

    public static AppDatabase getInstance(final Context context){
        if(dbInstance == null){
            synchronized (AppDatabase.class){
                if(dbInstance == null){
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, Constants.DATABASE).allowMainThreadQueries().build();
                }
            }
        }
        return dbInstance;
    }
}
