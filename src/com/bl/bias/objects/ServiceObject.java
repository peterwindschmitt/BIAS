package com.bl.bias.objects;

public final class ServiceObject 
{
	private String date;
	private String dayOfWeek;
	private String serviceName;
	private String serviceType;
	private String departureLocation;
	private String departureTimestamp;
	private String arrivalLocation;
	private String arrivalTimestamp;
	
	public ServiceObject(String date, String dayOfWeek, String serviceName, String serviceType, String departureStation, String departureTimestamp, String arrivalStation, String arrivalTimestamp) 
    {
        this.date = date; // either the short date as string or "CORE"
        this.dayOfWeek = dayOfWeek;
		this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.departureLocation = departureStation;
        this.departureTimestamp = departureTimestamp;
        this.arrivalLocation = arrivalStation;
        this.arrivalTimestamp = arrivalTimestamp;
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
	
	public String getDepartureLocation()
	{
		return departureLocation;
	}
	
	public String getDepartureTimestamp()
	{
		return departureTimestamp;
	}
	
	public String getArrivalLocation()
	{
		return arrivalLocation;
	}
	
	public String getArrivalTimestamp()
	{
		return arrivalTimestamp;
	}
}