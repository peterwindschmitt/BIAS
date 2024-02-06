package com.bl.bias.objects;

public class CompliancePermit 
{
	String subdivision;
	Double beginMp;
	Double endMp;
	Integer psgSpeed;
	Integer frtSpeed;
	String startTime;
	String endTime;
	String enabled;

	public CompliancePermit(String subdivision, Double beginMp, Double endMp, Integer psgSpeed, Integer frtSpeed, String startTime, String endTime, String enabled) 
	{
		this.subdivision = subdivision;
		this.beginMp = beginMp;
		this.endMp = endMp;
		this.startTime = startTime;
		this.endTime = endTime;
		this.psgSpeed = psgSpeed;
		this.frtSpeed = frtSpeed;
		this.startTime = startTime;
		this.endTime = endTime;
		this.enabled = enabled;
	}

	public String getSubdivision() 
	{
		return subdivision;
	}

	public Double getBeginMp() 
	{
		return beginMp;
	}

	public Double getEndMp() 
	{
		return endMp;
	}

	public String getStartTime() 
	{
		return startTime;
	}

	public String getEndTime() 
	{
		return endTime;
	}

	public Integer getPasSpeed() 
	{
		return psgSpeed;
	}

	public Integer getFrtSpeed()
	{
		return frtSpeed;
	}
	
	public String getEnabled()
	{
		return enabled;
	}
}