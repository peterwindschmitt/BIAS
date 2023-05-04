package com.bl.bias.objects;

public class RadixxCarrierInputIATA 
{
	private String timeMode;
	private String airlineDesignator;
	private String creatorReference;
	private String periodOfValidity;
	private String creationDate;
	private String titleOfData;
	private String releaseDate;
	private String recordSerialNumber;
	
	public RadixxCarrierInputIATA(String timeMode, String airlineDesignator, String creatorReference, String periodOfValidity, String creationDate, String titleOfData, String releaseDate, String recordSerialNumber)
	{
		this.timeMode = timeMode;
		this.airlineDesignator = airlineDesignator;
		this.creatorReference = creatorReference;
		this.periodOfValidity = periodOfValidity;
		this.creationDate = creationDate;
		this.titleOfData = titleOfData;
		this.releaseDate = releaseDate;
		this.recordSerialNumber = recordSerialNumber;
	}
	
	public String getTimeMode() 
	{
		return timeMode;
	}
	
	public String getAirlineDesignator() 
	{
		return airlineDesignator;
	}
	
	public String getCreatorReference() 
	{
		return creatorReference;
	}
	
	public String getPeriodOfValidity() 
	{
		return periodOfValidity;
	}
	
	public String getCreationDate()
	{
		return creationDate;
	}
	
	public String getTitleOfData() 
	{
		return titleOfData;
	}
	
	public String getReleaseDate() 
	{
		return releaseDate;
	}
	
	public String getRecordSerialNumber() 
	{
		return recordSerialNumber;
	}
	
	public void setTimeMode(String timeMode) 
	{
		this.timeMode = timeMode;
	}
	
	public void setAirlineDesignator(String airlineDesignator) 
	{
		this.airlineDesignator = airlineDesignator;
	}
	
	public void setCreatorReference(String creatorReference) 
	{
		this.creatorReference = creatorReference;
	}
	
	public void setPeriodOfValidity(String periodOfValidity) 
	{
		this.periodOfValidity = periodOfValidity;
	}
	
	public void setCreationDate(String creationDate) 
	{
		this.creationDate = creationDate;
	}
	
	public void setTitleOfData(String titleOfData) 
	{
		this.titleOfData = titleOfData;
	}
	
	public void setReleaseDate(String releaseDate)
	{
		this.releaseDate = releaseDate;
	}
	
	public void setRecordSerialNumber(String recordSerialNumber) 
	{
		this.recordSerialNumber = recordSerialNumber;
	}
}