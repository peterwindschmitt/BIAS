package com.bl.bias.objects;

import java.util.ArrayList;

public class MaintenanceWindowAnalysisSubLineOccupancy 
{
	// A sub-line occupancy is defined as the time period that one of more trains continuously occupy a segment of track (aka a sub-line)
	private ArrayList<String> trainSymbolsAndDirectionsInOccupancy = new ArrayList<String>();
	
	private Integer occupancyStartTimeInSeconds;
	private Integer occupancyEndTimeInSeconds;
	
	public MaintenanceWindowAnalysisSubLineOccupancy(ArrayList<String> trainSymbolsAndDirectionsInOccupancy, Integer occupancyStartTimeInSeconds, Integer occupancyEndTimeInSeconds ) 
	{
		this.trainSymbolsAndDirectionsInOccupancy.addAll(trainSymbolsAndDirectionsInOccupancy);
		this.occupancyStartTimeInSeconds = occupancyStartTimeInSeconds;
		this.occupancyEndTimeInSeconds = occupancyEndTimeInSeconds;
	}

	public ArrayList<String> getTrainSymbolsAndDirectionsInOccupancy() 
	{
		return trainSymbolsAndDirectionsInOccupancy;
	}

	public Integer getOccupancyStartTimeInSeconds()
	{
		return occupancyStartTimeInSeconds;
	}

	public Integer getOccupancyEndTimeInSeconds() 
	{
		return occupancyEndTimeInSeconds;
	}
}