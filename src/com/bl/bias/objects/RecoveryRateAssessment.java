package com.bl.bias.objects;

public class RecoveryRateAssessment 
{
	private String aNode;
	private String bNode;
	
	private Integer differenceInScheduledTimeInSeconds;
	private Integer differenceInSimulatedTimeInSeconds;
	
	private Boolean dwellEventBetweenNodes;

	private Double distanceCovered;
				
	public RecoveryRateAssessment(String aNode, String bNode, Integer differenceInScheduledTimeInSeconds, Integer differenceInSimulatedTimeInSeconds, Boolean dwellEventBetweenNodes, Double distanceCovered)
	{
		this.aNode = aNode;
		this.bNode = bNode;
		this.differenceInScheduledTimeInSeconds = differenceInScheduledTimeInSeconds;
		this.differenceInSimulatedTimeInSeconds = differenceInSimulatedTimeInSeconds;
		this.dwellEventBetweenNodes = dwellEventBetweenNodes;
		this.distanceCovered = distanceCovered;
	}
	
	public String getANode()
	{
		return aNode;
	}
	
	public String getBNode()
	{
		return bNode;
	}
	
	public Integer getDifferenceInScheduledTimeInSeconds()
	{
		return differenceInScheduledTimeInSeconds;
	}
	
	public Integer getDifferenceInSimulatedTimeInSeconds()
	{
		return differenceInSimulatedTimeInSeconds;
	}
	
	public Boolean getDwellEventBetweenNodes()
	{
		return dwellEventBetweenNodes;
	}
	public Double getDistanceCovered()
	{
		return distanceCovered;
	}
}