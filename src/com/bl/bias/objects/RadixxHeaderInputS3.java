package com.bl.bias.objects;

public class RadixxHeaderInputS3 
{
	private String titleOfContents;
	private String recordSerialNumber;
	
	public RadixxHeaderInputS3(String titleOfContents, String recordSerialNumber)
	{
		this.titleOfContents = titleOfContents;
		this.recordSerialNumber = recordSerialNumber;
	}
	
	public String getTitleOfContents() 
	{
		return titleOfContents;
	}

	public String getRecordSerialNumber()
	{
		return recordSerialNumber;
	}
	
	public void setTitleOfContents(String titleOfContents) 
	{
		this.titleOfContents = titleOfContents;
	}
		
	public void setRecordSerialNumber(String recordSerialNumber) 
	{
		this.recordSerialNumber = recordSerialNumber;
	}
}