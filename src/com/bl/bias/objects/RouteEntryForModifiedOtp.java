package com.bl.bias.objects;

public class RouteEntryForModifiedOtp
{
	private String node;
	private String scheduledDepartureTimeAsString;
	private String scheduledArrivalTimeAsString;
	private String simulatedDepartureTimeAsString;
	private String simulatedArrivalTimeAsString;
		
	public RouteEntryForModifiedOtp(String node, String scheduledDepartureTimeAsString, String scheduledArrivalTimeAsString, String simulatedDepartureTimeAsString, 
			String simulatedArrivalTimeAsString)
	{
		this.node = node;
		this.scheduledDepartureTimeAsString = scheduledDepartureTimeAsString;
		this.scheduledArrivalTimeAsString = scheduledArrivalTimeAsString;
		this.simulatedDepartureTimeAsString = simulatedDepartureTimeAsString;
		this.simulatedArrivalTimeAsString = simulatedArrivalTimeAsString;
	}
	
	public String getNode() 
	{
		return node;
	}
	
	public String getScheduledDepartureTimeAsString() 
	{
		return scheduledDepartureTimeAsString;
	}
	
	public String getScheduledArrivalTimeAsString() 
	{
		return scheduledArrivalTimeAsString;
	}
	
	public String getSimulatedDepartureTimeAsString() 
	{
		return simulatedDepartureTimeAsString;
	}
	
	public String getSimulatedArrivalTimeAsString() 
	{
		return simulatedArrivalTimeAsString;
	}
}