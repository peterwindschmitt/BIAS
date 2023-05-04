package com.bl.bias.objects;

public class RadixxSegmentDataRecordInputIATA 
{
	private String airlineDesignator;
	private String flightNumber;
	private String itineraryVariationNumber;
	private String legSequenceNumber;
	private String serviceType;
	private String boardPointIndicator;
	private String offPointIndicator;
	private String dataElementIdentifier;
	private String segmentBoardPoint;
	private String segmentOffPoint;
	private String data;
	private String recordSerialNumber;
	
	public RadixxSegmentDataRecordInputIATA(String airlineDesignator, String flightNumber, String itineraryVariationNumber, String legSequenceNumber, String serviceType, String boardPointIndicator, String offPointIndicator, 
			String dataElementIdentifier, String segmentBoardPoint, String segmentOffPoint, String data, String recordSerialNumber) 
	{
		this.airlineDesignator = airlineDesignator;
		this.flightNumber = flightNumber;
		this.itineraryVariationNumber = itineraryVariationNumber;
		this.legSequenceNumber = legSequenceNumber;
		this.serviceType = serviceType;
		this.boardPointIndicator = boardPointIndicator;
		this.offPointIndicator = offPointIndicator;
		this.dataElementIdentifier = dataElementIdentifier;
		this.segmentBoardPoint = segmentBoardPoint;
		this.segmentOffPoint = segmentOffPoint;
		this.data = data;
		this.recordSerialNumber = recordSerialNumber;
	}

	public String getAirlineDesignator()
	{
		return airlineDesignator;
	}

	public String getFlightNumber()
	{
		return flightNumber;
	}

	public String getItineraryVariationNumber()
	{
		return itineraryVariationNumber;
	}

	public String getLegSequenceNumber()
	{
		return legSequenceNumber;
	}

	public String getServiceType()
	{
		return serviceType;
	}

	public String getBoardPointIndicator()
	{
		return boardPointIndicator;
	}

	public String getOffPointIndicator()
	{
		return offPointIndicator;
	}

	public String getDataElementIdentifier()
	{
		return dataElementIdentifier;
	}

	public String getSegmentBoardPoint()
	{
		return segmentBoardPoint;
	}

	public String getSegmentOffPoint()
	{
		return segmentOffPoint;
	}

	public String getData()
	{
		return data;
	}

	public String getRecordSerialNumber()
	{
		return recordSerialNumber;
	}
	
	public void setAirlineDesignator(String airlineDesignator)
	{
		this.airlineDesignator = airlineDesignator;
	}

	public void setFlightNumber(String flightNumber)
	{
		this.flightNumber = flightNumber;
	}

	public void setItineraryVariationNumber(String itineraryVariationNumber)
	{
		this.itineraryVariationNumber = itineraryVariationNumber;
	}

	public void setLegSequenceNumber(String legSequenceNumber)
	{
		this.legSequenceNumber = legSequenceNumber;
	}

	public void setServiceType(String serviceType)
	{
		this.serviceType = serviceType;
	}

	public void setBoardPointIndicator(String boardPointIndicator)
	{
		this.boardPointIndicator = boardPointIndicator;
	}

	public void setOffPointIndicator(String offPointIndicator)
	{
		this.offPointIndicator = offPointIndicator;
	}

	public void setDataElementIdentifier(String dataElementIdentifier)
	{
		this.dataElementIdentifier = dataElementIdentifier;
	}

	public void setSegmentBoardPoint(String segmentBoardPoint)
	{
		this.segmentBoardPoint = segmentBoardPoint;
	}

	public void setSegmentOffPoint(String segmentOffPoint)
	{
		this.segmentOffPoint = segmentOffPoint;
	}

	public void setData(String data)
	{
		this.data = data;
	}
	
	public void setRecordSerialNumber(String recordSerialNumber)
	{
		this.recordSerialNumber = recordSerialNumber;
	}
}