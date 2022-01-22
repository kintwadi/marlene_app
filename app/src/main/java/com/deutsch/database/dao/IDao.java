package com.deutsch.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.deutsch.model.User;
import com.deutsch.model.Word;

import java.util.List;

@Dao
public interface IDao {

    @Insert
    Long insertUser(User user);
    @Delete
    void deleteUser(User user);
    @Update
    void updateUser(User user);
    @Query("SELECT * FROM User")
    LiveData<List<User> >getUsers();
    @Query("SELECT * FROM User WHERE email=:email")
    LiveData<User> getUserByEmail(String email);

    @Query("SELECT * FROM User WHERE name=:name and password=:password")
    LiveData<User> getUserByPasswordAndUserName(String name,String password);

    @Query("UPDATE User SET password=:password WHERE email=:email")
    void updatePassword(String email, String password);

  // word query
    @Query("SELECT * FROM Word WHERE word LIKE :search")
    List<Word> findWords(String search);

    @Query("SELECT * FROM Word ")
    List<Word> findAllWords();

    @Insert
    Long insertWord(Word word);
}
