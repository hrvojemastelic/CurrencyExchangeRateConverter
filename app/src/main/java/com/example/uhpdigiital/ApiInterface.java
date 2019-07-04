package com.example.uhpdigiital;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ApiInterface {

    @GET("api/v1/rates/daily/")
    Call<List<CurrencyModel>> getDailyCurrencyValues();

}
