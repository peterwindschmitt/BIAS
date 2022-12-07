package com.bl.bias.objects;

public class GradeXingLink 
{
	private String nodeA;
	private String nodeB;
	
	public GradeXingLink(String nodeA, String nodeB) 
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