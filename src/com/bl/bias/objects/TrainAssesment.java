package com.bl.bias.objects;

import java.util.ArrayList;

public class TrainAssesment
{
	private String trainSymbol;
	private String trainGroupAbbreviation;
	ArrayList<RouteEntry> routeEntries = new ArrayList<RouteEntry>();
				
	public TrainAssesment(String trainSymbol, String trainGroupAbbreviation, ArrayList<RouteEntry> routeEntries)
	{
		this.trainSymbol = trainSymbol;
		this.trainGroupAbbreviation = trainGroupAbbreviation;
		this.routeEntries = routeEntries;
	}
	
	public String getTrainSymbol() 
	{
		return trainSymbol;
	}

	public String getTrainGroupAbbreviation() 
	{
		return trainGroupAbbreviation;
	}
	
	public ArrayList<RouteEntry> getRouteEntries() 
	{
		return routeEntries;
	}
}