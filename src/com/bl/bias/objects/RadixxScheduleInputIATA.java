package com.bl.bias.objects;

import java.util.ArrayList;

public class RadixxScheduleInputIATA 
{
	private RadixxHeaderInputIATA header;
	private RadixxCarrierInputIATA carrier;
	private RadixxTrailerInputIATA trailer;
	
	private ArrayList<RadixxFlightLegInputIATA> flightLegs;
	
	public RadixxScheduleInputIATA()
	{
		flightLegs = new ArrayList<RadixxFlightLegInputIATA>();
	}
	
	public RadixxHeaderInputIATA getHeader() 
	{
		return header;
	}

	public RadixxCarrierInputIATA getCarrier()
	{
		return carrier;
	}
	
	public ArrayList<RadixxFlightLegInputIATA> getFlightLegs()
	{
		return flightLegs;
	}
	
	public RadixxTrailerInputIATA getTrailer()
	{
		return trailer;
	}
	
	public void setHeader(RadixxHeaderInputIATA header) 
	{
		this.header = header;
	}
	
	public void setCarrier(RadixxCarrierInputIATA carrier) 
	{
		this.carrier = carrier;
	}
	
	public void setFlightLeg(RadixxFlightLegInputIATA flightLeg) 
	{
		flightLegs.add(flightLeg);
	}
	
	public void setTrailer(RadixxTrailerInputIATA trailer) 
	{
		this.trailer = trailer;
	}
}