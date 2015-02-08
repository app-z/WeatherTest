package com.appz.weathertest.Data;

import com.appz.weathertest.AppSettings;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;


@Root(name="country")
public class Country implements Serializable{
		private static final long serialVersionUID = AppSettings.getCitiesSerialVersionUID();

	    @Attribute(name = "name")
		public String name;
	    @ElementList(entry = "city", inline = true)
	    public List <City> cityAttr;


	}

