package com.bl.bias.objects;

import java.time.LocalDate;

public class RadixxOverlappingFlightOriginDestinationByDate 
{
	private LocalDate dateOfOperationForThisFlightAndOrigindestination;
	private String flightNumber;
	private String origin;
	private String destination;
	private String daysOfOperation;
	
	public RadixxOverlappingFlightOriginDestinationByDate(LocalDate dateOfOperationForThisFlightAndOrigindestination, String flightNumber, String origin, String destination, String daysOfOperation)
	{
		this.dateOfOperationForThisFlightAndOrigindestination = dateOfOperationForThisFlightAndOrigindestination;
		this.flightNumber = flightNumber;
		this.origin = origin;
		this.destination = destination;
		this.daysOfOperation = daysOfOperation;
	}
	
	public LocalDate getDateOfOperationForThisFlightAndOrigindestination() 
	{
		return dateOfOperationForThisFlightAndOrigindestination;
	}

	public String getFlightNumber()
	{
		return flightNumber;
	}
	
	public String getOrigin()
	{
		return origin;
	}
	
	public String getDestination()
	{
		return destination;
	}
	
	public String getDaysOfOperation()
	{
		return daysOfOperation;
	}
}