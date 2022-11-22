package com.bl.bias.objects;

public class BridgeAnalysisRouteTraversal 
{
	private String trainSymbol;
	private String rtcIncrement;
	private String node;
	private String headEndSpeed;
	private String headEndArrivalTime;
	private String tailEndDepartureTime;
	private String cumulativeElapsedTime;
	private String aspect;
	private String direction;
				
	public BridgeAnalysisRouteTraversal(String trainSymbol, String rtcIncrement, String node, String headEndSpeed, String headEndArrivalTime, String tailEndDepartureTime, String cumulativeElapsedTime, String aspect, String direction)
	{
		this.trainSymbol = trainSymbol;
		this.rtcIncrement = rtcIncrement;
		this.node = node;
		this.headEndSpeed = headEndArrivalTime;
		this.tailEndDepartureTime = tailEndDepartureTime;
		this.cumulativeElapsedTime = cumulativeElapsedTime;
		this.aspect = aspect;
		this.direction = direction;
		this.headEndArrivalTime = headEndArrivalTime;
	}

	public String getTrainSymbol() 
	{
		return trainSymbol;
	}

	public String getRtcIncrement() 
	{
		return rtcIncrement;
	}

	public String getNode() 
	{
		return node;
	}

	public String getHeadEndSpeed() 
	{
		return headEndSpeed;
	}

	public String getHeadEndArrivalTime() 
	{
		return headEndArrivalTime;
	}

	public String getTailEndDepartureTime() 
	{
		return tailEndDepartureTime;
	}

	public String getCumulativeElapsedTime() 
	{
		return cumulativeElapsedTime;
	}

	public String getAspect() 
	{
		return aspect;
	}
	
	public String getDirection() 
	{
		return direction;
	}
}