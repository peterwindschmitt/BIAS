package com.bl.bias.objects;

public class RadixxHeaderInputIATA 
{
	private String titleOfContents;
	private String dataSetSerialNumber;
	private String recordSerialNumber;
	
	public RadixxHeaderInputIATA(String titleOfContents, String dataSetSerialNumber, String recordSerialNumber)
	{
		this.titleOfContents = titleOfContents;
		this.dataSetSerialNumber = dataSetSerialNumber;
		this.recordSerialNumber = recordSerialNumber;
	}
	
	public String getTitleOfContents() 
	{
		return titleOfContents;
	}

	public String getDataSetSerialNumber()
	{
		return dataSetSerialNumber;
	}
	
	public String getRecordSerialNumber()
	{
		return recordSerialNumber;
	}
	
	public void setTitleOfContents(String titleOfContents) 
	{
		this.titleOfContents = titleOfContents;
	}
	
	public void setDataSetSerialNumber(String dataSetSerialNumber) 
	{
		this.dataSetSerialNumber = dataSetSerialNumber;
	}
	
	public void setRecordSerialNumber(String recordSerialNumber) 
	{
		this.recordSerialNumber = recordSerialNumber;
	}
}