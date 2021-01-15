package com.example.apiintroductionapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class UsersDatabase extends RoomDatabase {
    public abstract UserEntityDAO userEntityDAO();
    private static volatile UsersDatabase INSTANCE;
    public static UsersDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UsersDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    UsersDatabase.class, "users_database")
                                    //!!!not to use in production - possible data loss!!!
                                    .fallbackToDestructiveMigration()
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}

