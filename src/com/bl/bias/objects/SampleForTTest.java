package com.bl.bias.objects;

public class SampleForTTest 
{
	private String line;
	private String category;
	private Double velocity;
	private Double delayMinutesPer100TM;
	private String elapsedRunTimePerTrainAsString;
	private Double elapsedRunTimePerTrainInSeconds;
	private Double elapsedRunTimePerTrainAsSerialTime;
	private String otp;
			
	public SampleForTTest(String line, String category, double velocity, double delayMinutesPer100TM, String elapsedRunTimePerTrainAsString, double elapsedRunTimePerTrainInSeconds, double elapsedRunTimePerTrainAsSerialTime, String otp)
	{
		this.line = line;
		this.category = category;
		this.velocity = velocity;
		this.delayMinutesPer100TM = delayMinutesPer100TM;
		this.elapsedRunTimePerTrainAsString = elapsedRunTimePerTrainAsString;
		this.elapsedRunTimePerTrainInSeconds = elapsedRunTimePerTrainInSeconds;
		this.elapsedRunTimePerTrainAsSerialTime = elapsedRunTimePerTrainAsSerialTime;
		this.otp = otp;
	}
	
	public String getLine() 
	{
		return line;
	}

	public String getCategory() 
	{
		return category;
	}
	
	public double getVelocity()
	{
		return velocity;
	}
	
	public double getDelayMinutesPer100TM()
	{
		return delayMinutesPer100TM;
	}
	
	public String getElapsedRunTimePerTrainAsString()
	{
		return elapsedRunTimePerTrainAsString;
	}
	
	public Double getElapsedRunTimePerTrainInSeconds()
	{
		return elapsedRunTimePerTrainInSeconds;
	}
	
	public Double getElapsedRunTimePerTrainAsSerialTime()
	{
		return elapsedRunTimePerTrainAsSerialTime;
	}
	
	public String getOtp()
	{
		return otp;
	}
}