package com.bl.bias.objects;

public class MaintenanceWindowAnalysisLink 
{
	private String originNodeId;
	private String destinationNodeId;
	private String linkClass;
	private String linkDirection;
				
	public MaintenanceWindowAnalysisLink(String nodeAId, String nodeBId, String linkClass, String linkDirection)
	{
		this.originNodeId = nodeAId;
		this.destinationNodeId = nodeBId;
		this.linkClass = linkClass;
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
	
	public String getLinkClass()
	{
		return linkClass;
	}
	
	public String getLinkDirection()
	{
		return linkDirection;
	}
}