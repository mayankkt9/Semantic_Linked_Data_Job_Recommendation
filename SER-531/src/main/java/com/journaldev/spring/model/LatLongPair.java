package com.journaldev.spring.model;

public class LatLongPair {

	String Latitude;
	String Longitude;

	public LatLongPair() {

	}

	public LatLongPair(String latitude, String longitude) {
		super();
		Latitude = latitude;
		Longitude = longitude;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	@Override
	public String toString() {
		return "LatLongPair [Latitude=" + Latitude + ", Longitude=" + Longitude + "]";
	}

	
}
