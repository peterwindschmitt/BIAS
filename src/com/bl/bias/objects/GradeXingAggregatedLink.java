package com.bl.bias.objects;

public class GradeXingAggregatedLink 
{
	private String nodeA;
	private String nodeB;
	
	public GradeXingAggregatedLink(String nodeA, String nodeB) 
	{
		this.nodeA = nodeA;
		this.nodeB = nodeB;
	}

	public String getNodeA()
	{
		return nodeA;
	}

	public String getNodeB() 
	{
		return nodeB;
	}
}