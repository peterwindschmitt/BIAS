package com.bl.bias.objects;

public class GradeXingTpcEntry 
{
	// Captures rows from the .TPC file which have a route node.  Records route node, field marker, head-end track speed and current speed  
	private String trainSymbol;
	private String tpcNode;
	private Double tpcFieldMarker;
	private Double tpcDesignSpeed;
	private Double tpcCurrentSpeed;
	
	public GradeXingTpcEntry(String trainSymbol, String tpcNode, Double tpcFieldMarker, Double tpcDesignSpeed, Double tpcCurrentSpeed) 
	{
		this.trainSymbol = trainSymbol;
		this.tpcNode = tpcNode;
		this.tpcFieldMarker = tpcFieldMarker;
		this.tpcDesignSpeed = tpcDesignSpeed;
		this.tpcCurrentSpeed = tpcCurrentSpeed;
	}

	public String getTrainSymbol()
	{
		return trainSymbol;
	}
	
	public String getTpcNode()
	{
		return tpcNode;
	}

	public Double getFieldMarker() 
	{
		return tpcFieldMarker;
	}
	
	public Double getDesignSpeed() 
	{
		return tpcDesignSpeed;
	}
	
	public Double getCurrentSpeed() 
	{
		return tpcCurrentSpeed;
	}
}