package com.bl.bias.objects;

public class RadixxCarrierInputS3 
{
	private String timeMode;
	private String companyCode;
	private String description;
	private String periodOfValidity;
	private String creationDate;
	
	public RadixxCarrierInputS3(String timeMode, String companyCode, String description, String periodOfValidity, String creationDate)
	{
		this.timeMode = timeMode;
		this.companyCode = companyCode;
		this.description = description;
		this.periodOfValidity = periodOfValidity;
		this.creationDate = creationDate;
	}
	
	public String getTimeMode() 
	{
		return timeMode;
	}
	
	public String getCompanyCode() 
	{
		return companyCode;
	}
	
	public String getDescription() 
	{
		return description;
	}
	
	public String getPeriodOfValidity() 
	{
		return periodOfValidity;
	}
	
	public String getCreationDate()
	{
		return creationDate;
	}
	
	public void setTimeMode(String timeMode) 
	{
		this.timeMode = timeMode;
	}
	
	public void setCompanyCode(String companyCode) 
	{
		this.companyCode = companyCode;
	}
	
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	public void setPeriodOfValidity(String periodOfValidity) 
	{
		this.periodOfValidity = periodOfValidity;
	}
	
	public void setCreationDate(String creationDate) 
	{
		this.creationDate = creationDate;
	}
}