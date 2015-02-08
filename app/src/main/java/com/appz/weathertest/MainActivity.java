package com.appz.weathertest;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appz.weathertest.Data.Cities;
import com.appz.weathertest.Data.City;
import com.appz.weathertest.Data.Country;
import com.appz.weathertest.Data.Forecast;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity {

	
	//Cities citiesCache;
	//Forecast weatherCache;
	private SimpleCursorAdapter adapterCities;
	private MatrixCursor matrixCursor;
	private ListView citiesView;
	private ProgressBar progressBar1;

	
	private static final String file_name_cityes = "cityes";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		AppSettings.init(this);
		
		if(!AppSettings.isNetworkAvailable(this)){
			AppSettings.showDialog(this, "Internet Error", "Data will be get from cashe if possyble", false);
		}
    
		
		progressBar1 = (ProgressBar)findViewById(R.id.progressBar1);
		
		String[] columnNames = {"_id", "country", "city"};
		matrixCursor = new MatrixCursor(columnNames);

		citiesView = (ListView) findViewById(R.id.listView1);

		adapterCities = new SimpleCursorAdapter(this,
				R.layout.list_cities_row, matrixCursor,
				new String[] { "country", "city" }, new int[] { R.id.textView1, R.id.textView2 }, 0);

		citiesView.setAdapter(adapterCities);

		///
		//	Click on city
		//
		citiesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				  String city_id = matrixCursor.getString(matrixCursor.getColumnIndex("_id"));
				  Log.v(">>>", "city_id:" + city_id);
					
				  Forecast weather = (Forecast) AppSettings.readObject(MainActivity.this, city_id);
				  	if(weather == null){
						downloadWeather(Integer.valueOf(city_id));
						progressBar1.setVisibility(View.VISIBLE);
				  	}else{
						if(weather.timestamp + 3600 < (System.currentTimeMillis() / 1000 )){
							AppSettings.resetWeatherCashe();
							downloadWeather(Integer.valueOf(city_id));
							progressBar1.setVisibility(View.VISIBLE);
						}else{
							showWeather(weather);
							progressBar1.setVisibility(View.GONE);
						}
				  	}


			  }
		});

		//
		//	Try cities cache
		//
		Cities cities = (Cities) AppSettings.readObject(this, file_name_cityes);
		if(cities == null){
			downloadCities();
		}else{
			//Log.v(">>>", "Get From Cache " + citiesCache.timestamp + "  " + System.currentTimeMillis() / 1000);
			if(cities.timestamp + 3600 < (System.currentTimeMillis() / 1000 )){
				AppSettings.resetCitiesCashe();
				downloadCities();
			}else{
				showCountries(cities);
				progressBar1.setVisibility(View.GONE);
			}
		}
		
		Picasso.with(this).setIndicatorsEnabled(true);
	
	}

	/*
	 * 
	 * Download weather by citi_id
	 * 
	 */
	private void downloadWeather(int city_id) {
		ApiWeatherService apiWeatherService = getApp().getWeatherService();

		apiWeatherService.getWeather(city_id, new Callback<Forecast> (){

			@Override
			public void failure(RetrofitError errorResponse) {
				Log.v(">>>", "Error: " + errorResponse.toString());
				AppSettings.showDialog(MainActivity.this, "Internet Error", "Error load or empty cache", false);
				progressBar1.setVisibility(View.GONE);
			}

			@Override
			public void success(Forecast forecast, Response arg1) {
				Log.v(">>>", "Success");
				Log.v(">>>", "tempr = "  + forecast.fact.get(0).temperature );
				forecast.timestamp = System.currentTimeMillis() / 1000;
				AppSettings.saveObject(MainActivity.this, forecast.id, forecast);
				showWeather(forecast);
			}
			
		
		});


	}

	/*
	 * 
	 * 
	 * 
	 */
	void showWeather(Forecast forecast){
		
		TextView textWeather = (TextView) findViewById(R.id.textWeather);
		textWeather.setText(forecast.fact.get(0).temperature + "  " + forecast.fact.get(0).weather_type);

		ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
		if(forecast.fact.get(0).temperature < 0){
			Picasso.with(MainActivity.this).load("http://hikingartist.files.wordpress.com/2012/05/1-christmas-tree.jpg").resize(300, 300).into(imageView1);
		}else{
			Picasso.with(MainActivity.this).load("http://www.rewalls.com/images/201201/reWalls.com_59293.jpg").resize(300, 300).into(imageView1);
		}
		progressBar1.setVisibility(View.GONE);

	}
	
	/*
	 * 
	 * Update country ListView
	 * 
	 */
	void showCountries(Cities cityes){
	
		List <Country> countries = cityes.countries;
		for (Country country : countries) {
			// Log.v(">>>", "Name:" + country.name);
			for (City cityAttr : country.cityAttr) {
				// Log.v(">>>", "Region:" + cityAttr.region + "   City:" + cityAttr.city);
				
				matrixCursor.addRow(new String[]{cityAttr.id, cityAttr.country, cityAttr.city});
			}
		}
		adapterCities.swapCursor(matrixCursor);
		adapterCities.notifyDataSetChanged();
		progressBar1.setVisibility(View.GONE);
	}


	/*
	 * 
	 * Download cities list
	 * 
	 */
	private void downloadCities() {
		ApiCitiesService apiService = getApp().getCitiesService();

		apiService.getCities( new Callback<Cities> (){

					@Override
					public void failure(RetrofitError errorResponse) {
						Log.v(">>>", "Error: " + errorResponse.toString());
						AppSettings.showDialog(MainActivity.this, "Internet Error", "Error load or empty cache", false);
						progressBar1.setVisibility(View.GONE);
					}

					@Override
					public void success(Cities cityes, Response arg1) {
						Log.v(">>>", "Success");
						cityes.timestamp = System.currentTimeMillis() / 1000;
						AppSettings.saveObject(MainActivity.this, file_name_cityes, cityes);
						Log.v(">>>", "TimeStamp = " + cityes.timestamp);
						showCountries(cityes);
					}
		});

	}



	/*
	 * 
	 * 
	 * 
	 */
	private WeatherRestApp getApp() {
	    return (WeatherRestApp) getApplication();
	  }

	
}
