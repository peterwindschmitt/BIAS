package com.bl.bias.objects;

import java.util.ArrayList;

public class RadixxFlightLegInput 
{
	private String airlineDesignator;
	private String flightNumber;
	private String itineraryVariationIdentifier;
	private String legSequenceNumber;
	private String serviceType;
	private String periodOfOperation;
	private String dayOfOperation;
	private String departureStation;
	private String passengerSTD;
	private String aircraftSTD;
	private String timeVariationDeparture;
	private String departureTerminal;
	private String arrivalStation;
	private String passengerSTA;
	private String aircraftSTA;
	private String timeVariationArrival;
	private String arrivalTerminal;
	private String aircraftType;
	private String onwardAirlineDesignator;
	private String onwardFlightNumber;
	private String onwardFlightTransitLayover;
	private String aircraftConfiguration;
	private String dateVariation;
	private String recordSerialNumber;
	
	private ArrayList<RadixxSegmentDataRecordInput> segmentDataRecords;

	public RadixxFlightLegInput(String airlineDesignator, String flightNumber, String itineraryVariationIdentifier,
			String legSequenceNumber, String serviceType, String periodOfOperation, String dayOfOperation,
			String departureStation, String passengerSTD, String aircraftSTD, String timeVariationDeparture,
			String departureTerminal, String arrivalStation, String passengerSTA, String aircraftSTA,
			String timeVariationArrival, String arrivalTerminal, String aircraftType, String onwardAirlineDesignator,
			String onwardFlightNumber, String onwardFlightTransitLayover, String aircraftConfiguration, String dateVariation, String recordSerialNumber) 
	{
		this.airlineDesignator = airlineDesignator;
		this.flightNumber = flightNumber;
		this.itineraryVariationIdentifier = itineraryVariationIdentifier;
		this.legSequenceNumber = legSequenceNumber;
		this.serviceType = serviceType;
		this.periodOfOperation = periodOfOperation;
		this.dayOfOperation = dayOfOperation;
		this.departureStation = departureStation;
		this.passengerSTD = passengerSTD;
		this.aircraftSTD = aircraftSTD;
		this.timeVariationDeparture = timeVariationDeparture;
		this.departureTerminal = departureTerminal;
		this.arrivalStation = arrivalStation;
		this.passengerSTA = passengerSTA;
		this.aircraftSTA = aircraftSTA;
		this.timeVariationArrival = timeVariationArrival;
		this.arrivalTerminal = arrivalTerminal;
		this.aircraftType = aircraftType;
		this.onwardAirlineDesignator = onwardAirlineDesignator;
		this.onwardFlightNumber = onwardFlightNumber;
		this.onwardFlightTransitLayover = onwardFlightTransitLayover;
		this.aircraftConfiguration = aircraftConfiguration;
		this.dateVariation = dateVariation;
		this.recordSerialNumber = recordSerialNumber;
		
		this.segmentDataRecords = new ArrayList <RadixxSegmentDataRecordInput>();
	}

	public String getAirlineDesignator()
	{
		return airlineDesignator;
	}

	public String getFlightNumber()
	{
		return flightNumber;
	}

	public String getItineraryVariationIdentifier()
	{
		return itineraryVariationIdentifier;
	}

	public String getLegSequenceNumber()
	{
		return legSequenceNumber;
	}

	public String getServiceType()
	{
		return serviceType;
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

	public String getAircraftSTD()
	{
		return aircraftSTD;
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

	public String getAircraftSTA()
	{
		return aircraftSTA;
	}

	public String getTimeVariationArrival()
	{
		return timeVariationArrival;
	}

	public String getArrivalTerminal()
	{
		return arrivalTerminal;
	}

	public String getAircraftType()
	{
		return aircraftType;
	}

	public String getOnwardAirlineDesignator()
	{
		return onwardAirlineDesignator;
	}

	public String getOnwardFlightNumber()
	{
		return onwardFlightNumber;
	}

	public String getOnwardFlightTransitLayover()
	{
		return onwardFlightTransitLayover;
	}

	public String getAircraftConfiguration()
	{
		return aircraftConfiguration;
	}
	
	public String getDateVariation()
	{
		return dateVariation;
	}
	
	public String getRecordSerialNumber()
	{
		return recordSerialNumber;
	}
	
	public ArrayList<RadixxSegmentDataRecordInput> getSegmentDataRecords()
	{
		return segmentDataRecords;
	}

	public void setAirlineDesignator(String airlineDesignator)
	{
		this.airlineDesignator = airlineDesignator;
	}

	public void setFlightNumber(String flightNumber)
	{
		this.flightNumber = flightNumber;
	}

	public void setItineraryVariationIdentifier(String itineraryVariationIdentifier)
	{
		this.itineraryVariationIdentifier = itineraryVariationIdentifier;
	}

	public void setLegSequenceNumber(String legSequenceNumber)
	{
		this.legSequenceNumber = legSequenceNumber;
	}

	public void setServiceType(String serviceType)
	{
		this.serviceType = serviceType;
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

	public void setAircraftSTD(String aircraftSTD)
	{
		this.aircraftSTD = aircraftSTD;
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

	public void setAircraftSTA(String aircraftSTA)
	{
		this.aircraftSTA = aircraftSTA;
	}

	public void setTimeVariationArrival(String timeVariationArrival)
	{
		this.timeVariationArrival = timeVariationArrival;
	}

	public void setArrivalTerminal(String arrivalTerminal)
	{
		this.arrivalTerminal = arrivalTerminal;
	}

	public void setAircraftType(String aircraftType)
	{
		this.aircraftType = aircraftType;
	}

	public void setOnwardAirlineDesignator(String onwardAirlineDesignator)
	{
		this.onwardAirlineDesignator = onwardAirlineDesignator;
	}

	public void setOnwardFlightNumber(String onwardFlightNumber)
	{
		this.onwardFlightNumber = onwardFlightNumber;
	}

	public void setOnwardFlightTransitLayover(String onwardFlightTransitLayover)
	{
		this.onwardFlightTransitLayover = onwardFlightTransitLayover;
	}

	public void setAircraftConfiguration(String aircraftConfiguration)
	{
		this.aircraftConfiguration = aircraftConfiguration;
	}
	
	public void setDateVariation(String dateVariation)
	{
		this.dateVariation = dateVariation;
	}
	
	public void setRecordSerialNumber(String recordSerialNumber)
	{
		this.recordSerialNumber = recordSerialNumber;
	}
	
	public void setSegmentDataRecord(RadixxSegmentDataRecordInput segmentDataRecord)
	{
		segmentDataRecords.add(segmentDataRecord);
	}
}