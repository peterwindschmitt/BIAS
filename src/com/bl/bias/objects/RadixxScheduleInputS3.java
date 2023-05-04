package com.bl.bias.objects;

import java.util.ArrayList;

public class RadixxScheduleInputS3 
{
	private RadixxHeaderInputS3 header;
	private RadixxCarrierInputS3 carrier;
	private RadixxTrailerInputS3 trailer;
	
	private ArrayList<RadixxFlightLegInputS3> flightLegs;
	
	public RadixxScheduleInputS3()
	{
		flightLegs = new ArrayList<RadixxFlightLegInputS3>();
	}
	
	public RadixxHeaderInputS3 getHeader() 
	{
		return header;
	}

	public RadixxCarrierInputS3 getCarrier()
	{
		return carrier;
	}
	
	public ArrayList<RadixxFlightLegInputS3> getFlightLegs()
	{
		return flightLegs;
	}
	
	public RadixxTrailerInputS3 getTrailer()
	{
		return trailer;
	}
	
	public void setHeader(RadixxHeaderInputS3 header) 
	{
		this.header = header;
	}
	
	public void setCarrier(RadixxCarrierInputS3 carrier) 
	{
		this.carrier = carrier;
	}
	
	public void setTrainLeg(RadixxFlightLegInputS3 flightLeg) 
	{
		flightLegs.add(flightLeg);
	}
	
	public void setTrailer(RadixxTrailerInputS3 trailer) 
	{
		this.trailer = trailer;
	}
}