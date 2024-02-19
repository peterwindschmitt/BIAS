package com.bl.bias.objects;

public class ComplianceLink 
{
	private Double linkDistance;
	private Integer passengerSpeed;
	private Integer throughSpeed;
	private Integer localSpeed;

	public ComplianceLink(Double linkDistance, Integer passengerSpeed, Integer throughSpeed, Integer localSpeed) 
	{
		this.linkDistance = linkDistance;
		this.passengerSpeed = passengerSpeed;
		this.throughSpeed = throughSpeed;
		this.localSpeed = localSpeed;
	}

	public Double getLinkDistance()
	{
		return linkDistance;
	}

	public Integer getPassengerSpeed() 
	{
		return passengerSpeed;
	}
	
	public Integer getThroughSpeed() 
	{
		return throughSpeed;
	}
	
	public Integer getLocalSpeed() 
	{
		return localSpeed;
	}
}