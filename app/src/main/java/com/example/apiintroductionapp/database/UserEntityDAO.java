package com.example.apiintroductionapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;



@Dao
public interface UserEntityDAO {
    @Query("SELECT * FROM user")
    List<UserEntity> getAll();
    @Query("SELECT * FROM user WHERE first_name LIKE :firstName " +
            "AND last_name LIKE :lastName LIMIT 1")
    UserEntity findByName(String firstName, String lastName);
    @Insert
    void insert(UserEntity... users);
    @Update
    public void update(UserEntity... users);
    @Delete
    void delete(UserEntity user);
}
