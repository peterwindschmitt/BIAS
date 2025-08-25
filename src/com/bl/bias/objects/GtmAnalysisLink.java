package com.bl.bias.objects;

public class GtmAnalysisLink 
{
	private String originNodeId;
	private String destinationNodeId;
	private Double linkDistance;
	private String linkDirection;
				
	public GtmAnalysisLink(String nodeAId, String nodeBId, Double linkDistance, String linkDirection)
	{
		this.originNodeId = nodeAId;
		this.destinationNodeId = nodeBId;
		this.linkDistance = linkDistance;
		this.linkDirection = linkDirection;
	}
	
	public String getNodeAId() 
	{
		return originNodeId;
	}

	public String getNodeBId() 
	{
		return destinationNodeId;
	}
	
	public Double getLinkDistance()
	{
		return linkDistance;
	}
	
	public String getLinkDirection()
	{
		return linkDirection;
	}
}