package com.journaldev.spring.model;

public class Location {

	String cityName;
	LatLongPair pair;

	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Location(String cityName, LatLongPair pair) {
		super();
		this.cityName = cityName;
		this.pair = pair;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public LatLongPair getPair() {
		return pair;
	}

	public void setPair(LatLongPair pair) {
		this.pair = pair;
	}

	@Override
	public String toString() {
		return "Location [cityName=" + cityName + ", pair=" + pair + "]";
	}

	
	
}
