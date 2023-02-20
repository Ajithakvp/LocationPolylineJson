package com.example.locationpolylinejson;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GoogleLocationInterface {

    //http://192.168.29.173/beta1/app/gmap.php

    @GET("gmap.php")
    Call<GoogleLocationResponse>CallRepot();
}
