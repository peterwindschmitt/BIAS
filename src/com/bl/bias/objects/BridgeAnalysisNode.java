package com.bl.bias.objects;

public class BridgeAnalysisNode 
{
	private String nodeId;
	private String nodeName;
	private String isASignal;
				
	public BridgeAnalysisNode(String nodeId, String nodeName, String isASignal)
	{
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.isASignal = isASignal;
	}
	
	public String getNodeId() 
	{
		return nodeId;
	}

	public String getNodeName() 
	{
		return nodeName;
	}
	
	public String getIsASignal()
	{
		return isASignal;
	}
}