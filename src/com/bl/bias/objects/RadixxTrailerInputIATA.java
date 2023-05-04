package com.bl.bias.objects;

public class RadixxTrailerInputIATA 
{
	private String airlineDesignator;
	private String releaseDate;
	private String serialNumberCheckReference;
	private String continuationEndCode;
	private String recordSerialNumber;
		
	public RadixxTrailerInputIATA(String airlineDesignator, String releaseDate, String serialNumberCheckReference, String continuationEndCode, String recordSerialNumber)
	{
		this.airlineDesignator = airlineDesignator;
		this.releaseDate = releaseDate;
		this.serialNumberCheckReference = serialNumberCheckReference;
		this.continuationEndCode = continuationEndCode;
		this.recordSerialNumber = recordSerialNumber;
	}
	
	public String getAirlineDesignator() 
	{
		return airlineDesignator;
	}

	public String getReleaseDate()
	{
		return releaseDate;
	}
	
	public String getSerialNumberCheckReference()
	{
		return serialNumberCheckReference;
	}
	
	public String getContinuationEndCode()
	{
		return continuationEndCode;
	}
	
	public String getRecordSerialNumber()
	{
		return recordSerialNumber;
	}
	
	public void setAirlineDesignator(String airlineDesignator) 
	{
		this.airlineDesignator = airlineDesignator;
	}
	
	public void setReleaseDate(String releaseDate) 
	{
		this.releaseDate = releaseDate;
	}
	
	public void setSerialNumberCheckReference(String serialNumberCheckReference) 
	{
		this.serialNumberCheckReference = serialNumberCheckReference;
	}
	
	public void setContinuationEndCode(String continuationEndCode) 
	{
		this.continuationEndCode = continuationEndCode;
	}
	
	public void setRecordSerialNumber(String recordSerialNumber) 
	{
		this.recordSerialNumber = recordSerialNumber;
	}
}