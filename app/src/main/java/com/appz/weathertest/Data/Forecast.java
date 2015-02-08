package com.appz.weathertest.Data;

import com.appz.weathertest.AppSettings;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;



@Root(name = "forecast", strict=false)
public class Forecast implements Serializable{

	private static final long serialVersionUID = AppSettings.getWeatherSerialVersionUID();

	/*
	@Attribute(name="country") String country;
	@Attribute(name="country_id") String country_id;
	@Attribute(name="part") String part;
	@Attribute(name="link") String link;
	@Attribute(name="unique_for") String unique_for;
	@Attribute(name="part_id") String part_id;
	@Attribute(name="lat") String lat;
	@Attribute(name="slug") String slug;
	@Attribute(name="city") String city;
	@Attribute(name="region") String region;
	@Attribute(name="lon") String lon;
	@Attribute(name="zoom") String zoom;
	
	@Attribute(name="climate") String climate;
	
	@Attribute(name="id") String id;
	@Attribute(name="source") String source;
	@Attribute(name="exactname") String exactname;
	@Attribute(name="geoid") String geoid;
	 */
	@Attribute(name="id") 
	public String id;
	
	@ElementList(inline = true)
	public
	List<Fact> fact;

	public long timestamp;
}
