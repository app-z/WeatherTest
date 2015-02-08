package com.appz.weathertest.Data;

import com.appz.weathertest.AppSettings;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "fact", strict=false)
public class Fact implements Serializable{

	private static final long serialVersionUID = AppSettings.getWeatherSerialVersionUID();

	@Element
	public	Float temperature;
	@Element	
	public String weather_type;
}
