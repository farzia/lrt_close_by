package com.brtc.nearby.listadapter;

import android.database.Cursor;

public class BusStopagesListItem {
	private String marketName;
	private String marketLocation;
	private String closedDay;
	private static Cursor cursor;

	public BusStopagesListItem(String name, String location, String closedDay) {
		super();
		this.marketName = name;
		this.marketLocation = location;
		this.closedDay=closedDay;

	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getMarketLocation() {
		return marketLocation;
	}

	public void setMarketLocation(String marketLocation) {
		this.marketLocation = marketLocation;
	}

	public String getClosedDay() {
		return closedDay;
	}

	public void setClosedDay(String closedDay) {
		this.closedDay = closedDay;
	}

	public static Cursor getCursor() {
		return cursor;
	}

	public static void setCursor(Cursor cursor) {
		BusStopagesListItem.cursor = cursor;
	}
	
	

}