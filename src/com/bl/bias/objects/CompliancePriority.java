package com.bl.bias.objects;

public class CompliancePriority 
{
	private String type;
	private Integer minimumDelayCost;
	private Integer initialDelayCost;
	private Integer maximumDelayCost;
	private Integer conflictRank;

	public CompliancePriority(String type, Integer minimumDelayCost, Integer initialDelayCost, Integer maximumDelayCost, Integer conflictRank) 
	{
		this.type = type.toUpperCase();
		this.minimumDelayCost = minimumDelayCost;
		this.initialDelayCost = initialDelayCost;
		this.maximumDelayCost = maximumDelayCost;
		this.conflictRank = conflictRank;
	}

	public String getType() 
	{
		return type;
	}

	public Integer getMinimumDelayCost()
	{
		return minimumDelayCost;
	}

	public Integer getInitialDelayCost()
	{
		return initialDelayCost;
	}
	
	public Integer getMaximumDelayCost()
	{
		return maximumDelayCost;
	}
	
	public Integer getConflictRank()
	{
		return conflictRank;
	}
}