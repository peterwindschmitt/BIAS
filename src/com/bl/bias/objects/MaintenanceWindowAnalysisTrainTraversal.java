
package com.bl.bias.objects;

public class MaintenanceWindowAnalysisTrainTraversal 
{
	// A traversal is defined as one train's occupancy of a subline
	String trainSymbol;
	String trainDirection;
	String entryNode;
	String exitNode;

	Integer entryNodeOSSeconds;
	Integer exitNodeOSSeconds;

	public MaintenanceWindowAnalysisTrainTraversal(String trainSymbol, String trainDirection, String entryNode, Integer entryNodeOSSeconds, 
			String exitNode, Integer exitNodeOSSeconds ) 
	{
		this.trainSymbol = trainSymbol;
		this.trainDirection = trainDirection;
		this.entryNode = entryNode;
		this.exitNode = exitNode;
		this.entryNodeOSSeconds = entryNodeOSSeconds;
		this.exitNodeOSSeconds = exitNodeOSSeconds;
	}

	public String getTrainSymbol() 
	{
		return trainSymbol;
	}

	public String getTrainDirection() 
	{
		return trainDirection;
	}

	public String getEntryNode() 
	{
		return entryNode;
	}

	public String getExitNode() 
	{
		return exitNode;
	}

	public Integer getEntryNodeOSSeconds() 
	{
		return entryNodeOSSeconds;
	}

	public Integer getExitNodeOSSeconds() 
	{
		return exitNodeOSSeconds;
	}
}