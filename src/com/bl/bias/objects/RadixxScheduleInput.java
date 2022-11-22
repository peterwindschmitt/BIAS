package com.bl.bias.objects;

import java.util.ArrayList;

public class RadixxScheduleInput 
{
	private RadixxHeaderInput header;
	private RadixxCarrierInput carrier;
	private RadixxTrailerInput trailer;
	
	private ArrayList<RadixxFlightLegInput> flightLegs;
	
	public RadixxScheduleInput()
	{
		flightLegs = new ArrayList<RadixxFlightLegInput>();
	}
	
	public RadixxHeaderInput getHeader() 
	{
		return header;
	}

	public RadixxCarrierInput getCarrier()
	{
		return carrier;
	}
	
	public ArrayList<RadixxFlightLegInput> getFlightLegs()
	{
		return flightLegs;
	}
	
	public RadixxTrailerInput getTrailer()
	{
		return trailer;
	}
	
	public void setHeader(RadixxHeaderInput header) 
	{
		this.header = header;
	}
	
	public void setCarrier(RadixxCarrierInput carrier) 
	{
		this.carrier = carrier;
	}
	
	public void setFlightLeg(RadixxFlightLegInput flightLeg) 
	{
		flightLegs.add(flightLeg);
	}
	
	public void setTrailer(RadixxTrailerInput trailer) 
	{
		this.trailer = trailer;
	}
}