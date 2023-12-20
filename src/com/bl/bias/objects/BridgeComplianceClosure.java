package com.bl.bias.objects;

public class BridgeComplianceClosure 
{
	// Values passed in via constructor
	private Integer spreadsheetRowNumber;
	private Integer closureNumber;
	private Integer closureDate;

	private Double closureStartTime;
	private Double closureEndTime;

	private String tender;	
	private String closureStartDay;
	private String closureTrainType;
	private String closureNotes;

	private Boolean modifyDurationOfFirstClosure;
	private Boolean modifyDurationOfLastClosure;

	// Values computed by other classes
	private Integer marineAccessPeriodViolation = 0;
	private Boolean closureInCircuitException = false;
	private Boolean closureDurationViolation = false;
	private Double closureDurationOccuringDuringMarineHighUsagePeriod; 

	public BridgeComplianceClosure(Boolean modifyDurationOfFirstClosure, Boolean modifyDurationOfLastClosure, Integer spreadsheetRowNumber, Integer closureNumber, Integer closureDate, Double closureStartTime, Double closureEndTime, String tender, String closureStartDay, String closureTrainType, String closureNotes) 
	{
		this.modifyDurationOfFirstClosure = modifyDurationOfFirstClosure;
		this.modifyDurationOfLastClosure = modifyDurationOfLastClosure;
		this.spreadsheetRowNumber = spreadsheetRowNumber;
		this.closureNumber = closureNumber;
		this.closureDate = closureDate;
		this.closureStartTime = closureStartTime;
		this.closureEndTime = closureEndTime;
		this.tender = tender;
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

	public Integer getClosureDate()
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

	public String getTender()
	{
		return tender;
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
		Double closureDuration = 0.0;

		if ((!modifyDurationOfFirstClosure) && (!modifyDurationOfLastClosure))
		{
			closureDuration = closureEndTime - closureStartTime;
			if (closureDuration < 0)
				closureDuration += 1;
		}
		else if (modifyDurationOfFirstClosure)
		{
			closureDuration = closureEndTime;
		}
		else if (modifyDurationOfLastClosure)
		{
			closureDuration = 1 - closureStartTime;
		}

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
		else
			endDay = closureStartDay;

		return endDay;
	}

	public Integer getMarineAccessPeriodViolation()
	{
		return marineAccessPeriodViolation;
	}

	public void setMarineAccessPeriodViolation()
	{
		marineAccessPeriodViolation++;
	}

	public Boolean getClosureDurationViolation()
	{
		return closureDurationViolation;
	}

	public void setClosureDurationViolation()
	{
		closureDurationViolation = true;
	}

	public Boolean getInCircuitException()
	{
		return closureInCircuitException;
	}

	public void setInCircuitExcpetion()
	{
		closureInCircuitException = true;
	}

	public Double getClosureDurationOccuringDuringMarineHighUsagePeriod()
	{
		return closureDurationOccuringDuringMarineHighUsagePeriod;
	}

	public void setClosureDurationOccuringDuringMarineHighUsagePeriod(double highUsagePeriodDuration)
	{
		closureDurationOccuringDuringMarineHighUsagePeriod = highUsagePeriodDuration;
	}
}