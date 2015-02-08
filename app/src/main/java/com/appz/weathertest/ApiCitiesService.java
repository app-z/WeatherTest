package com.appz.weathertest;

import com.appz.weathertest.Data.Cities;

import retrofit.Callback;
import retrofit.http.GET;

public interface ApiCitiesService {
    @GET("/static/cities.xml")
    void getCities(Callback<Cities> callback);
}

