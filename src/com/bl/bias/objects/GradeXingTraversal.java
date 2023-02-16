package com.bl.bias.objects;

public class GradeXingTraversal 
{
	private String highestSpeedTrainSymbol;
	private String lowestSpeedTrainSymbol;
	private String nodeAName;
	private Double nodeAFieldMP;
	private Double nodeBFieldMP;
	private Double highestObservedSpeed = 0.0;
	private Double lowestObservedSpeed = 999.0;
	private Double highestDesignSpeed = 0.0;
	private Double lowestDesignSpeed = 999.0;
	
	public GradeXingTraversal(Double nodeAFieldMP, Double nodeBFieldMP, String nodeAName) 
	{
		this.nodeAName = nodeAName;
		this.nodeAFieldMP = nodeAFieldMP;
		this.nodeBFieldMP = nodeBFieldMP;
	}

	public void setHighestObservedSpeed(String trainSymbol, Double speed)
	{
		if (speed > highestObservedSpeed)
		{
			highestObservedSpeed = speed;
			highestSpeedTrainSymbol = trainSymbol;
		}
	}
	
	public void setLowestObservedSpeed(String trainSymbol, Double speed)
	{
		if (speed < lowestObservedSpeed)
		{
			lowestObservedSpeed = speed;
			lowestSpeedTrainSymbol = trainSymbol;
		}
	}
		
	public void setHighestDesignSpeed(Double speed)
	{
		if (speed > highestDesignSpeed)
		{
			highestDesignSpeed = speed;
		}
	}
	
	public void setLowestDesignSpeed(Double speed)
	{
		if (speed < lowestDesignSpeed)
		{
			lowestDesignSpeed = speed;
		}
	}
	
	public String getNodeAName()
	{
		return nodeAName;
	}

	public Double getNodeAFieldMP()
	{
		return nodeAFieldMP;
	}

	public Double getNodeBFieldMP()
	{
		return nodeBFieldMP;
	}

	public String getHighestSpeedTrainSymbol()
	{
		return highestSpeedTrainSymbol;
	}
	
	public String getLowestSpeedTrainSymbol()
	{
		return lowestSpeedTrainSymbol;
	}
	
		public Double getHighestObservedSpeed()
	{
		return highestObservedSpeed;
	}
	
	public Double getLowestObservedSpeed()
	{
		return lowestObservedSpeed;
	}
	
	public Double getHighestDesignSpeed()
	{
		return highestDesignSpeed;
	}
	
	public Double getLowestDesignSpeed()
	{
		return lowestDesignSpeed;
	}

	public Double getMaxDesignVsMaxObservedSpeedDifference() 
	{
		double maxDesignVsMaxObservedSpeedDifference = highestDesignSpeed - highestObservedSpeed;
		return maxDesignVsMaxObservedSpeedDifference;
	}
	
	public Double getMaxDesignVsMinObservedSpeedDifference() 
	{
		double maxDesignVsMinObservedSpeedDifference = highestDesignSpeed - lowestObservedSpeed;
		return maxDesignVsMinObservedSpeedDifference;
	}
}