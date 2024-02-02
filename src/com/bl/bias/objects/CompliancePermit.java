package com.bl.bias.objects;

public class CompliancePermit 
{
	String subdivision;
	Double beginMp;
	Double endMp;
	String startTime;
	String endTime;
	Integer psgSpeed;
	Integer frtSpeed;

	//public CompliancePermit(String subdivision, Double beginMp, Double endMp, String startTime, String endTime, Integer psgSpeed, Integer frtSpeed) 
	public CompliancePermit(String subdivision, Double beginMp, Double endMp, Integer psgSpeed, Integer frtSpeed) 
	{
		this.subdivision = subdivision;
		this.beginMp = beginMp;
		this.endMp = endMp;
		this.startTime = startTime;
		this.endTime = endTime;
		this.psgSpeed = psgSpeed;
		this.frtSpeed = frtSpeed;
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
}