package com.bl.bias.objects;

public class RadixxFlightLegInputS3 
{
	private String companyCode;
	private String trainNumber;
	private String itineraryVariationIdentifier;
	private String legSequenceNumber;
	private String commercialCategory;
	private String periodOfOperation;
	private String dayOfOperation;
	private String departureStation;
	private String passengerSTD;
	private String trainSTD;
	private String timeVariationDeparture;
	private String departureTerminal;
	private String arrivalStation;
	private String passengerSTA;
	private String trainSTA;
	private String timeVariationArrival;
	private String arrivalTerminal;
	private String serviceType;
	
	public RadixxFlightLegInputS3(String companyCode, String trainNumber, String itineraryVariationIdentifier,
			String legSequenceNumber, String commercialCategory, String periodOfOperation, String dayOfOperation,
			String departureStation, String passengerSTD, String trainSTD, String timeVariationDeparture,
			String departureTerminal, String arrivalStation, String passengerSTA, String trainSTA,
			String timeVariationArrival, String arrivalTerminal, String serviceType) 
	{
		this.companyCode = companyCode;
		this.trainNumber = trainNumber;
		this.itineraryVariationIdentifier = itineraryVariationIdentifier;
		this.legSequenceNumber = legSequenceNumber;
		this.commercialCategory = commercialCategory;
		this.periodOfOperation = periodOfOperation;
		this.dayOfOperation = dayOfOperation;
		this.departureStation = departureStation;
		this.passengerSTD = passengerSTD;
		this.trainSTD = trainSTD;
		this.timeVariationDeparture = timeVariationDeparture;
		this.departureTerminal = departureTerminal;
		this.arrivalStation = arrivalStation;
		this.passengerSTA = passengerSTA;
		this.trainSTA = trainSTA;
		this.timeVariationArrival = timeVariationArrival;
		this.arrivalTerminal = arrivalTerminal;
		this.serviceType = serviceType;
	}

	public String getCompanyCode()
	{
		return companyCode;
	}

	public String getTrainNumber()
	{
		return trainNumber;
	}

	public String getItineraryVariationIdentifier()
	{
		return itineraryVariationIdentifier;
	}

	public String getLegSequenceNumber()
	{
		return legSequenceNumber;
	}

	public String getCommercialCategory()
	{
		return commercialCategory;
	}

	public String getPeriodOfOperation()
	{
		return periodOfOperation;
	}

	public String getDayOfOperation()
	{
		return dayOfOperation;
	}

	public String getDepartureStation()
	{
		return departureStation;
	}

	public String getPassengerSTD()
	{
		return passengerSTD;
	}

	public String getTrainSTD()
	{
		return trainSTD;
	}

	public String getTimeVariationDeparture()
	{
		return timeVariationDeparture;
	}

	public String getDepartureTerminal()
	{
		return departureTerminal;
	}

	public String getArrivalStation()
	{
		return arrivalStation;
	}

	public String getPassengerSTA()
	{
		return passengerSTA;
	}

	public String getTrainSTA()
	{
		return trainSTA;
	}

	public String getTimeVariationArrival()
	{
		return timeVariationArrival;
	}

	public String getArrivalTerminal()
	{
		return arrivalTerminal;
	}
	
	public String getServiceType()
	{
		return serviceType;
	}

	public void setCompanyCode(String companyCode)
	{
		this.companyCode = companyCode;
	}

	public void setTrainNumber(String trainNumber)
	{
		this.trainNumber = trainNumber;
	}

	public void setItineraryVariationIdentifier(String itineraryVariationIdentifier)
	{
		this.itineraryVariationIdentifier = itineraryVariationIdentifier;
	}

	public void setLegSequenceNumber(String legSequenceNumber)
	{
		this.legSequenceNumber = legSequenceNumber;
	}

	public void setCommercialCategory(String commercialCategory)
	{
		this.commercialCategory = commercialCategory;
	}

	public void setPeriodOfOperation(String periodOfOperation)
	{
		this.periodOfOperation = periodOfOperation;
	}

	public void setDayOfOperation(String dayOfOperation)
	{
		this.dayOfOperation = dayOfOperation;
	}

	public void setDepartureStation(String departureStation)
	{
		this.departureStation = departureStation;
	}

	public void setPassengerSTD(String passengerSTD)
	{
		this.passengerSTD = passengerSTD;
	}

	public void setTrainSTD(String trainSTD)
	{
		this.trainSTD = trainSTD;
	}

	public void setTimeVariationDeparture(String timeVariationDeparture)
	{
		this.timeVariationDeparture = timeVariationDeparture;
	}

	public void setDepartureTerminal(String departureTerminal)
	{
		this.departureTerminal = departureTerminal;
	}

	public void setArrivalStation(String arrivalStation)
	{
		this.arrivalStation = arrivalStation;
	}

	public void setPassengerSTA(String passengerSTA)
	{
		this.passengerSTA = passengerSTA;
	}

	public void setTrainSTA(String trainSTA)
	{
		this.trainSTA = trainSTA;
	}

	public void setTimeVariationArrival(String timeVariationArrival)
	{
		this.timeVariationArrival = timeVariationArrival;
	}

	public void setArrivalTerminal(String arrivalTerminal)
	{
		this.arrivalTerminal = arrivalTerminal;
	}	
	
	public void setServiceType(String serviceType)
	{
		this.serviceType = serviceType;
	}
}