package com.example.apiintroductionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.apiintroductionapp.adapter.UsersAdapter;
import com.example.apiintroductionapp.api.ApiClient;
import com.example.apiintroductionapp.api.ApiService;
import com.example.apiintroductionapp.database.UserEntity;
import com.example.apiintroductionapp.database.UsersDatabase;
import com.example.apiintroductionapp.mapper.UserMapper;
import com.example.apiintroductionapp.model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private UsersDatabase usersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.usersDatabase = UsersDatabase.getDatabase(getApplicationContext());
        getUsersApiCall();
    }



    private void initRecyclerView(List<User> users) {
        RecyclerView recyclerView = findViewById(R.id.rvUsersList);
        if (recyclerView == null) {
            return;
        } //if
        if (users == null) {
            return;
        } //if
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        UsersAdapter usersAdapter = new UsersAdapter(this, users);
        recyclerView.setAdapter(usersAdapter);
    }
    private void getUsersApiCall() {
        ApiClient.getClient().create(ApiService.class).getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Response<List<User>>>() {
                    @Override
                    public void onNext(@NonNull Response<List<User>> response) {
                        if (response.code() == 200) {
                            initRecyclerView(response.body());
                            saveUsersLocally(response.body());
                        } //if
                    }
                    @Override
                    public void onError(@NonNull Throwable ex) {
                        Log.e("API_CALL", ex.getMessage(), ex);
                        retrieveUsersLocally();
                    }
                    @Override
                    public void onComplete() {
                        //nothing here
                    }
                });


}
    private void saveUsersLocally(List<User> users) {
        if (users == null) {
            return;
        } //if
        usersDatabase.getQueryExecutor().execute(() -> {
            final List<UserEntity> userEntities =
                    usersDatabase.userEntityDAO().getAll();
            if (userEntities != null) {
                if (userEntities.size() > 0) {
                    return;
                } //if
            } //if
            for (User user: users) {
                UserEntity userEntity = UserMapper.create(user);
                if (userEntity != null) {
                    usersDatabase.userEntityDAO().insert(userEntity);
                } //if
            } //for
        });
    }
    private void retrieveUsersLocally() {
        usersDatabase.getQueryExecutor().execute(() -> {
            final List<UserEntity> userEntities =
                    usersDatabase.userEntityDAO().getAll();
            runOnUiThread(() -> {
                if (userEntities == null) {
                    return;
                } //if
                List<User> users = new ArrayList<>();
                for (UserEntity userEntity : userEntities) {
                    User user = UserMapper.create(userEntity);
                    if (user != null) {
                        users.add(user);
                    } //if
                } //for
                initRecyclerView(users);
            });
        });
    }

}