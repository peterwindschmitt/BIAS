package com.bl.bias.objects;

public class RTCResultsAnalysisGroupDataRow
{
    private String fileName;
    private String lineName;
    private String trainGroup;
    private Integer trainCount;
    private Double avgSpeed;
    private Double trainMiles;
    private String elapsedTimeAsString;
    private String idealRunTime;
    private String otp;

	public RTCResultsAnalysisGroupDataRow(String fileName, String lineName, String trainGroup, Integer trainCount, Double avgSpeed, Double trainMiles, String elapsedTimeAsString, String idealRunTime, String otp) 
    {
    	this.fileName = fileName;
		this.lineName = lineName;
    	this.trainGroup = trainGroup;
    	this.trainCount = trainCount;
    	this.avgSpeed = avgSpeed;
    	this.trainMiles = trainMiles;
    	this.elapsedTimeAsString = elapsedTimeAsString;
    	this.idealRunTime = idealRunTime;
    	this.otp = otp;    	
	}
	
	public String returnFileName()
	{
		return fileName;
	}
	
	public String returnLineName()
	{
		return lineName;
	}
	
	public String returnTrainGroup()
	{
		return trainGroup;
	}
	
	public Integer returnTrainCount()
	{
		return trainCount;
	}
	
	public Double returnAvgSpeed()
	{
		return avgSpeed;
	}
	
	public Double returnTrainMiles()
	{
		return trainMiles;
	}
	
	public String returnElapsedTimeAsString() 
	{
		return elapsedTimeAsString;
	}
	
	public String returnIdealRunTimeAsString()
	{
		return idealRunTime;
	}
	
	public String returnOTP()
	{
		return otp;
	}	
}