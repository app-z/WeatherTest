package com.appz.weathertest;

import android.app.Application;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit.RestAdapter;
import retrofit.client.UrlConnectionClient;

public class WeatherRestApp extends Application {

	
	private static final String CITIES_SERVER = "https://pogoda.yandex.ru";

	private static final String WEATHER_SERVER = "http://export.yandex.ru";

	  private ApiCitiesService apiCitiesService;
	  private ApiWeatherService apiWeatherService;

	  @Override public void onCreate() {
	    super.onCreate();

	    // Set up Retrofit for cities
	    RestAdapter restCitiesAdapter = new RestAdapter.Builder()
	        .setEndpoint(CITIES_SERVER)
	  //      .setLogLevel(RestAdapter.LogLevel.FULL)
	        .setClient(new MyUrlConnectionClient())
	        .setConverter(new SimpleXMLConverter())
	        .build();

	    apiCitiesService = restCitiesAdapter.create(ApiCitiesService.class);
	  
	  
	    // Set up Retrofit for Weather
	    RestAdapter restWeatherAdapter = new RestAdapter.Builder()
	        .setEndpoint(WEATHER_SERVER)
	 //       .setLogLevel(RestAdapter.LogLevel.FULL)
	        .setClient(new MyUrlConnectionClient())
	        .setConverter(new SimpleXMLConverter())
	        .build();

	    apiWeatherService = restWeatherAdapter.create(ApiWeatherService.class);
	  
	  }

	  public ApiCitiesService getCitiesService() {
	    return apiCitiesService;
	  }
	  
	  public ApiWeatherService getWeatherService() {
		    return apiWeatherService;
		  }
	  
	  public final class MyUrlConnectionClient extends UrlConnectionClient {
		   @Override
		  protected HttpURLConnection openConnection(retrofit.client.Request request) throws IOException {
              HttpURLConnection connection = super.openConnection(request);
              connection.setReadTimeout(AppSettings.READ_INTERNET_TIMEOUT_SEC * 1000);
              connection.setConnectTimeout(AppSettings.CONNECT_INTERNET_TIMEOUT_SEC * 1000);
              return connection;
          }
	}
}


