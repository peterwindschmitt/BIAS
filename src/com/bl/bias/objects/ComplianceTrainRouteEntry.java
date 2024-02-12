package com.bl.bias.objects;

public class ComplianceTrainRouteEntry 
{
	private String node;
	private String arrivalTime;
	private String departureTime;
	private String minimumDwellTime;

	public ComplianceTrainRouteEntry(String node, String arrivalTime, String departureTime, String minimumDwellTime) 
	{
		this.node = node;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.minimumDwellTime = minimumDwellTime;
	}

	public String getNode()
	{
		return node;
	}

	public String getArrivalTime() 
	{
		return arrivalTime;
	}

	public String getDepartureTime()
	{
		return departureTime;
	}
	
	public String getMinimumDwellTime()
	{
		return minimumDwellTime;
	}
}