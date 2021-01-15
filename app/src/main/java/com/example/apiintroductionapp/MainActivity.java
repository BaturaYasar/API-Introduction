package com.example.apiintroductionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.apiintroductionapp.adapter.UsersAdapter;
import com.example.apiintroductionapp.api.ApiClient;
import com.example.apiintroductionapp.api.ApiService;
import com.example.apiintroductionapp.model.User;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                        } //if
                    }
                    @Override
                    public void onError(@NonNull Throwable ex) {
                        Log.e("API_CALL", ex.getMessage(), ex);

                    }
                    @Override
                    public void onComplete() {
                        //nothing here
                    }
                });
    }
}