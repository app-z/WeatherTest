package com.appz.weathertest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AppSettings {

	public static final int READ_INTERNET_TIMEOUT_SEC = 30;
	public static final int CONNECT_INTERNET_TIMEOUT_SEC = 10;

	static Context context;
	static void init(Context context){
		AppSettings.context = context;
	}

	
	/*
	 * 
	 * 	Save data list to file
	 * 
	 */
	public static void saveObject(Context context, String file_name, Object obj) {
	    try {
	        FileOutputStream fileOutputStream = context.openFileOutput(file_name, Context.MODE_PRIVATE);
	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
	        objectOutputStream.writeObject(obj);
	        objectOutputStream.close();
	        fileOutputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	/*
	 * 
	 * 	Read data list from file
	 * 
	 */
	public static Object readObject(Context context, String file_name) {
	    Object createObj = null;
	    try {
	        FileInputStream fileInputStream = context.openFileInput(file_name);
	        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	        createObj = objectInputStream.readObject();
	        objectInputStream.close();
	        fileInputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return createObj;
	}

	
	static final String APP_PREF_NAME = "app_ref";
	static final String CITIES_CACHE = "cities_cache";
	static final String WEATHER_CACHE = "weather_cache";

	/*
	 * 
	 * 	Cities cache UID
	 * 
	 */
	public static void resetCitiesCashe(){
		long cacheCitiesId = getCitiesSerialVersionUID();
		cacheCitiesId++;
		SharedPreferences pref = context.getSharedPreferences(
				APP_PREF_NAME, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putLong(CITIES_CACHE, cacheCitiesId).commit();
	}
	//static long cacheCitiesId = 4L;
	public static long getCitiesSerialVersionUID() {
		SharedPreferences pref = context.getSharedPreferences(
				APP_PREF_NAME, 0);
		return pref.getLong(CITIES_CACHE, 1L);
	}

	/*
	 * 
	 * Weathe cache UID
	 * 
	 */
	public static void resetWeatherCashe(){
		long cacheWeatherId = getWeatherSerialVersionUID();
		cacheWeatherId++;
		SharedPreferences pref = context.getSharedPreferences(
				APP_PREF_NAME, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putLong(WEATHER_CACHE, cacheWeatherId).commit();

	}

	public static long getWeatherSerialVersionUID() {
		SharedPreferences pref = context.getSharedPreferences(
				APP_PREF_NAME, 0);
		return pref.getLong(WEATHER_CACHE, 1L);
	}

	//
	//
	//
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		// if no network is available networkInfo will be null, otherwise check
		// if we are connected
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/*
	 * 
	 * Show modal dialog with resource string
	 * 
	 */
	static void showDialog(Context context, int res_title, int res_message, boolean isFinish){
		showDialog(context, 
				context.getResources().getString(res_title),
				context.getResources().getString(res_message),
				isFinish);
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	static void showDialog(final Context context, String title, String message, final boolean isFinish){

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(title);
		
		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setMessage(message)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
								dialog.dismiss();
								if(isFinish)
									((Activity)context).finish();
							}
						});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		
	}

}
