package com.bl.bias.objects;

import java.util.ArrayList;

public class ModifiedOtpTrainObjectA 
{
	private String symbol;
	
	private ArrayList<RouteEntryForModifiedOtp> routeEntries = new ArrayList<RouteEntryForModifiedOtp>();
	
	public String getSymbol()
	{
		return symbol;
	}
	
	public ArrayList<RouteEntryForModifiedOtp> getRouteEntries()
	{
		return routeEntries;
	}
	
	public void addRouteEntry(String node, String scheduledDeparture, String scheduledArrival, String observedDeparture, String observedArrival)
	{
		RouteEntryForModifiedOtp newEntry = new RouteEntryForModifiedOtp(node, scheduledDeparture, scheduledArrival, observedDeparture, observedArrival);
		routeEntries.add(newEntry);
	}

	public void setSymbol(String trainSymbol) 
	{
		symbol = trainSymbol;
	}
}
