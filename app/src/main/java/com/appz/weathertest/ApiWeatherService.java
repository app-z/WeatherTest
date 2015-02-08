package com.appz.weathertest;

import com.appz.weathertest.Data.Forecast;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ApiWeatherService {

	@GET("/weather-ng/forecasts/{city_id}.xml")
	void getWeather(@Path("city_id") int city_id,
			Callback<Forecast> callback);

}
