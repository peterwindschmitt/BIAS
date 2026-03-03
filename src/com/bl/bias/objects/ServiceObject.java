package com.bl.bias.objects;

public final class ServiceObject 
{
	private String date;
	private String dayOfWeek;
	private String serviceName;
	private String serviceType;
	private String originLocation;
	private String originTime;
	private String destinationLocation;
	private String destinationTime;
	
	public ServiceObject(String date, String dayOfWeek, String serviceName, String serviceType, String originLocation, String originTime, String destinationLocation, String destinationTime) 
    {
        this.date = date; // either the short date as string or "CORE"
        this.dayOfWeek = dayOfWeek;
		this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.originLocation = originLocation;
        this.originTime = originTime;
        this.destinationLocation = destinationLocation;
        this.destinationTime = destinationTime;
    }
		
	public String getDate()
	{
		return date;
	}
	
	public String getDayOfWeek()
	{
		return dayOfWeek;
	}
	
	public String getServiceName()
	{
		return serviceName;
	}
	
	public String getServiceType()
	{
		return serviceType;
	}
	
	public String getOriginLocation()
	{
		return originLocation;
	}
	
	public String getOriginTime()
	{
		return originTime;
	}
	
	public String getDestinationLocation()
	{
		return destinationLocation;
	}
	
	public String getDestinationTime()
	{
		return destinationTime;
	}
}