package com.bl.bias.objects;

public class RouteEntry
{
	private Integer rtcIncrement;
	
	private String node;
	private String scheduledDepartureTimeAsString;
	private String scheduledArrivalTimeAsString;
	private String simulatedDepartureTimeAsString;
	private String simulatedArrivalTimeAsString;
	private String cumulativeElapsedTimeAsString;
	private String minimumDwellTimeAsString;
	private String waitOnScheduleAsString;
	
	private Double cumulativeDistance;
				
	public RouteEntry(Integer rtcIncrement, String node, String scheduledDepartureTimeAsString, String scheduledArrivalTimeAsString, String simulatedDepartureTimeAsString, 
			String simulatedArrivalTimeAsString, String cumulativeElapsedTimeAsString, String minimumDwellTimeAsString, String waitOnScheduleAsString, Double cumulativeDistance)
	{
		this.rtcIncrement = rtcIncrement;
		this.node = node;
		this.scheduledDepartureTimeAsString = scheduledDepartureTimeAsString;
		this.scheduledArrivalTimeAsString = scheduledArrivalTimeAsString;
		this.simulatedDepartureTimeAsString = simulatedDepartureTimeAsString;
		this.simulatedArrivalTimeAsString = simulatedArrivalTimeAsString;
		this.cumulativeElapsedTimeAsString = cumulativeElapsedTimeAsString;
		this.minimumDwellTimeAsString = minimumDwellTimeAsString;
		this.waitOnScheduleAsString = waitOnScheduleAsString;
		this.cumulativeDistance = cumulativeDistance;
	}
	
	public Integer getRtcIncrement() 
	{
		return rtcIncrement;
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
	
	public String getCumulativeElapsedTimeAsString() 
	{
		return cumulativeElapsedTimeAsString;
	}
	
	public String getMinimumDwellTimeAsString() 
	{
		return minimumDwellTimeAsString;
	}
	
	public String getWaitOnScheduleAsString() 
	{
		return waitOnScheduleAsString;
	}
	
	public Double getCumulativeDistance() 
	{
		return cumulativeDistance;
	}
}