package com.bl.bias.objects;

public class BridgeComplianceClosure 
{
	// Values passed in via constructor
	// As of Dec 2023, only closure start day, start time and end time are used
	private Integer spreadsheetRowNumber;
	private Integer closureNumber;
	
	private Double closureDate;
	private Double closureStartTime;
	private Double closureEndTime;
	
	private String dutyBridgeMonitor;	
	private String closureStartDay;
	private String closureTrainType;
	private String closureNotes;

	// Values computed by other classes
	private Boolean closureError = false;
			
	public BridgeComplianceClosure(Integer spreadsheetRowNumber, Integer closureNumber, Double closureDate, Double closureStartTime, Double closureEndTime, String dutyBridgeMonitor, String closureStartDay, String closureTrainType, String closureNotes) 
	{
		this.spreadsheetRowNumber = spreadsheetRowNumber;
		this.closureNumber = closureNumber;
		this.closureDate = closureDate;
		this.closureStartTime = closureStartTime;
		this.closureEndTime = closureEndTime;
		this.dutyBridgeMonitor = dutyBridgeMonitor;
		this.closureStartDay = closureStartDay;
		this.closureTrainType = closureTrainType;
		this.closureNotes = closureNotes;
	}

	public Integer getSpreadsheetRowNumber()
	{
		return spreadsheetRowNumber;
	}
	
	public Integer getClosureNumber()
	{
		return closureNumber;
	}
	
	public Double getClosureDate()
	{
		return closureDate;
	}
	
	public Double getClosureStartTime()
	{
		return closureStartTime;
	}
	
	public Double getClosureEndTime()
	{
		return closureEndTime;
	}
	
	public String getDutyBridgeMonitor()
	{
		return dutyBridgeMonitor;
	}
	
	public String getClosureStartDay()
	{
		return closureStartDay;
	}
	
	public String getClosureTrainType()
	{
		return closureTrainType;
	}
	
	public String getClosureNotes()
	{
		return closureNotes;
	}
	
	public Double getClosureDuration()
	{
		Double closureDuration = closureEndTime - closureStartTime;
		if (closureDuration < 0)
			closureDuration += 1;
		
		return closureDuration;
	}
	
	public String getClosureEndDay()
	{
		String endDay = null;
		if ((closureStartDay.equals("Sunday")) && (closureEndTime <= closureStartTime))
			endDay = "Monday";
		else if ((closureStartDay.equals("Monday")) && (closureEndTime <= closureStartTime))
			endDay = "Tuesday";
		else if ((closureStartDay.equals("Tuesday")) && (closureEndTime <= closureStartTime))
			endDay = "Wednesday";
		else if ((closureStartDay.equals("Wednesday")) && (closureEndTime <= closureStartTime))
			endDay = "Thursday";
		else if ((closureStartDay.equals("Thursday")) && (closureEndTime <= closureStartTime))
			endDay = "Friday";
		else if ((closureStartDay.equals("Friday")) && (closureEndTime <= closureStartTime))
			endDay = "Saturday";
		else if ((closureStartDay.equals("Saturday")) && (closureEndTime <= closureStartTime))
			endDay = "Sunday";
		
		return endDay;
	}
	
	public Boolean getClosureError()
	{
		return closureError;
	}
	
	public void setClosureError(Boolean error)
	{
		closureError = error;
	}
}