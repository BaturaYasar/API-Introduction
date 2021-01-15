package com.example.apiintroductionapp.api;

import com.example.apiintroductionapp.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;


public interface ApiService {

    @GET("/users")
    Observable<Response<List<User>>> getUsers();

}

