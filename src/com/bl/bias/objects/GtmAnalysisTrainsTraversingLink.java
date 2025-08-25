package com.bl.bias.objects;

import java.util.ArrayList;

public class GtmAnalysisTrainsTraversingLink 
{
	private String nodeA;
	private String nodeB;
	private Double distance;
	private String linkDirection;
	
	private ArrayList<GtmAnalysisTraversalToAddToLink > traversals = new ArrayList<GtmAnalysisTraversalToAddToLink >();
				
	public GtmAnalysisTrainsTraversingLink(String nodeA, String nodeB, Double distance, String linkDirection)
	{
		this.nodeB = nodeB;
		this.nodeA = nodeA;
		this.distance = distance;
		this.linkDirection = linkDirection;
	}

	public void addTraversal(String trainSymbol, String trainType, Integer weight, String linkDirection)
	{
		GtmAnalysisTraversalToAddToLink traversal = new GtmAnalysisTraversalToAddToLink(trainSymbol, trainType, weight, linkDirection);
		traversals.add(traversal);
	}
	
	public String getNodeA()
	{
		return nodeA;
	}
	
	public String getNodeB()
	{
		return nodeB;
	}
	
	public Double getDistance()
	{
		return distance;
	}
	
	public String getLinkDirection()
	{
		return linkDirection;
	}
	
	public ArrayList<GtmAnalysisTraversalToAddToLink> getTraversals()
	{
		return traversals;
	}
}