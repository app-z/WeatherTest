package com.appz.weathertest.Data;

import com.appz.weathertest.AppSettings;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name="cities")
public class Cities implements Serializable{

	private static final long serialVersionUID = AppSettings.getCitiesSerialVersionUID();

	public long timestamp;
	
	@ElementList(entry = "country", inline = true)
    public List <Country> countries;
	    
	    
	}

