package com.appz.weathertest.Data;

import com.appz.weathertest.AppSettings;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.io.Serializable;

@Root
public class City implements Serializable{
	private static final long serialVersionUID = AppSettings.getCitiesSerialVersionUID();

	@Text
	public
	String city;

	@Attribute(name="id") 
	public String id;
    @Attribute(name="region")
	public String region;
    @Attribute(name="head") String head;
    @Attribute(name="type") String type;
    @Attribute(name="country")
	public String country;
    @Attribute(name="part") String part;
    @Attribute(name="resort") String resort;
    @Attribute(name="climate") String climate;
    		
}
