package com.bl.bias.objects;

public final class ServiceObject 
{
	private String date;
	private String serviceName;
	private String serviceType;
	private String originLocation;
	private String originDateTime;
	private String destinationLocation;
	private String destinationDateTime;
	
	public ServiceObject(String date, String serviceName, String serviceType, String originLocation, String originDateTime, String destinationLocation, String destinationDateTime) 
    {
        this.date = date; // either the short date as string or "CORE"
		this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.originLocation = originLocation;
        this.originDateTime = originDateTime;
        this.destinationLocation = destinationLocation;
        this.destinationDateTime = destinationDateTime;
    }
		
	public String getDate()
	{
		return date;
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
	
	public String getOriginDateTime()
	{
		return originDateTime;
	}
	
	public String getDestinationLocation()
	{
		return destinationLocation;
	}
	
	public String getDestinationDateTime()
	{
		return destinationDateTime;
	}
}