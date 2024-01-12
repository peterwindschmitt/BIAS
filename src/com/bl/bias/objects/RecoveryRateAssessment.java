package com.bl.bias.objects;

public class RecoveryRateAssessment 
{
	private String aNode;
	private String bNode;
	
	private Integer differenceInScheduledTimeInSeconds;
	private Integer differenceInSimulatedTimeInSeconds;
	private Integer dwellEventCumulativeTimeInSeconds;
	private Integer dwellEventOffsetCumulativeTimeInSeconds;
	private Integer waitOnScheduleCumulativeTimeInSeconds;
	
	private Boolean dwellEventBetweenNodes;
	private Boolean waitOnScheduleBetweenNodes;

	private Double distanceCovered;
				
	public RecoveryRateAssessment(String aNode, String bNode, Integer differenceInScheduledTimeInSeconds, Integer differenceInSimulatedTimeInSeconds, Boolean dwellEventBetweenNodes, Integer dwellEventCumulativeTimeInSeconds,  Integer dwellEventOffsetCumulativeTimeInSeconds, Boolean waitOnScheduleBetweenNodes, Integer waitOnScheduleCumulativeTimeInSeconds, Double distanceCovered)
	{
		this.aNode = aNode;
		this.bNode = bNode;
		this.differenceInScheduledTimeInSeconds = differenceInScheduledTimeInSeconds;
		this.differenceInSimulatedTimeInSeconds = differenceInSimulatedTimeInSeconds;
		this.dwellEventBetweenNodes = dwellEventBetweenNodes;
		this.dwellEventCumulativeTimeInSeconds = dwellEventCumulativeTimeInSeconds;
		this.dwellEventOffsetCumulativeTimeInSeconds = dwellEventOffsetCumulativeTimeInSeconds;
		this.waitOnScheduleBetweenNodes = waitOnScheduleBetweenNodes;
		this.waitOnScheduleCumulativeTimeInSeconds = waitOnScheduleCumulativeTimeInSeconds;
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
	
	public Integer getDwellEventCumulativeTimeInSeconds()
	{
		return dwellEventCumulativeTimeInSeconds;
	}
	
	public Integer getDwellEventOffsetCumulativeTimeInSeconds()
	{
		return dwellEventOffsetCumulativeTimeInSeconds;
	}
	
	public Boolean getWaitOnScheduleBetweenNodes()
	{
		return waitOnScheduleBetweenNodes;
	}
	
	public Integer getWaitOnScheduleCumulativeTimeInSeconds()
	{
		return waitOnScheduleCumulativeTimeInSeconds;
	}
	
	public Double getDistanceCovered()
	{
		return distanceCovered;
	}
}