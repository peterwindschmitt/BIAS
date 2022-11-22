package com.bl.bias.objects;

public class MaintenanceWindowAnalysisNode
{
	private String nodeId;
	private String nodeName;
	private String trackNumber;
				
	public MaintenanceWindowAnalysisNode(String nodeId, String nodeName, String trackNumber)
	{
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.trackNumber = trackNumber;
	}
	
	public String getNodeId() 
	{
		return nodeId;
	}

	public String getNodeName() 
	{
		return nodeName;
	}
	
	public String getTrackNumber() 
	{
		return trackNumber;
	}
}