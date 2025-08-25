package com.bl.bias.objects;

public class GtmAnalysisTraversalToAddToLink 
{
	private String trainSymbol;
	private String trainType;
	private Integer weight;
	private String linkDirection;
				
	public GtmAnalysisTraversalToAddToLink(String trainSymbol, String trainType, Integer weight, String linkDirection)
	{
		this.trainSymbol = trainSymbol;
		this.trainType = trainType;
		this.weight = weight;
		this.linkDirection = linkDirection;
	}

	public String getTrainSymbol() 
	{
		return trainSymbol;
	}

	public String getTrainType() 
	{
		return trainType;
	}
	
	public Integer getWeight() 
	{
		return weight;
	}
	
	public String getLinkDirection() 
	{
		return linkDirection;
	}
}