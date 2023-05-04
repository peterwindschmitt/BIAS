package com.bl.bias.objects;

public class RadixxTrailerInputS3 
{
	private String companyCode;
	private String startDate;
	private String serialNumberCheckReference;
	private String continuationEndCode;
	private String recordSerialNumber;
		
	public RadixxTrailerInputS3(String companyCode, String startDate, String serialNumberCheckReference, String continuationEndCode, String recordSerialNumber)
	{
		this.companyCode = companyCode;
		this.startDate = startDate;
		this.serialNumberCheckReference = serialNumberCheckReference;
		this.continuationEndCode = continuationEndCode;
		this.recordSerialNumber = recordSerialNumber;
	}
	
	public String getCompanyCode() 
	{
		return companyCode;
	}

	public String getStartDate()
	{
		return startDate;
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
	
	public void setCompanyCode(String companyCode) 
	{
		this.companyCode = companyCode;
	}
	
	public void setStartDate(String startDate) 
	{
		this.startDate = startDate;
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