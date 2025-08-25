package com.bl.bias.objects;

public class GtmAnalysisTraversalFromRouteFile 
{
	private String trainSymbol;
	private String trainType;
	private String node;
	private Integer weight;
	private Integer rtcIncrement;
	private Integer tpcIncrement;
	private String trainDirection;
				
	public GtmAnalysisTraversalFromRouteFile(String trainSymbol, String trainType, String node, Integer weight, Integer rtcIncrement, Integer tpcIncrement, String trainDirection)
	{
		this.trainSymbol = trainSymbol;
		this.trainType = trainType;
		this.node = node;
		this.weight = weight;
		this.rtcIncrement = rtcIncrement;
		this.tpcIncrement = tpcIncrement;
		this.trainDirection = trainDirection;
	}

	public String getTrainSymbol() 
	{
		return trainSymbol;
	}

	public String getTrainType() 
	{
		return trainType;
	}
	
	public String getNode() 
	{
		return node;
	}
	
	public Integer getWeight() 
	{
		return weight;
	}
	
	public Integer getRtcIncrement() 
	{
		return rtcIncrement;
	}
	
	public Integer getTpcIncrement() 
	{
		return tpcIncrement;
	}
	
	public String getTrainDirection() 
	{
		return trainDirection;
	}
}