package com.example.asignmemt_mod403.API;

import com.example.asignmemt_mod403.Model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("list")
    Call<List<Product>> getProduct();

    @POST("add")
    Call<List<Product>> add(@Body Product product);

    @PUT("update/{id}")
    Call<List<Product>> update(@Path("id") String id, @Body Product product);

    @DELETE("delete/{id}")
    Call<List<Product>> delete(@Path("id") String id);
}
