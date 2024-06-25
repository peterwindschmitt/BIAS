package com.bl.bias.objects;

public class ReportingPointForTrainObjectB 
{
	private String originNode;
	private Double scheduleOriginTimeToUse;
	private Double actualOriginTimeToUse;
	private String destinationNode;
	private Double scheduleDestinationTimeToUse;
	private Double actualDestinationTimeToUse;
	private Double scheduleTransitTime;
	private Double actualTransitTime;
	private String make;
	private Integer num;
	private Integer denom;
	
	public ReportingPointForTrainObjectB(String originNode, Double scheduleOriginTimeToUse, Double actualOriginTimeToUse, String destinationNode, Double scheduleDestinationTimeToUse, Double actualDestinationTimeToUse, Double scheduleTransitTime, Double actualTransitTime, String make, Integer num, Integer denom) 
    {
        this.originNode = originNode;
        this.scheduleOriginTimeToUse = scheduleOriginTimeToUse;
        this.actualOriginTimeToUse = actualOriginTimeToUse;
        this.destinationNode = destinationNode;
        this.scheduleDestinationTimeToUse = scheduleDestinationTimeToUse;
        this.actualDestinationTimeToUse = actualDestinationTimeToUse;
        this.scheduleTransitTime = scheduleTransitTime;
        this.actualTransitTime = actualTransitTime;
        this.make = make;
        this.num = num;
        this.denom = denom;
    }
		
	public String getOriginNode()
	{
		return originNode;
	}
	
	public Double getScheduleOriginTimeToUseAsDouble()
	{
		return scheduleOriginTimeToUse;
	}
	
	public Double getActualOriginTimeToUseAsDouble()
	{
		return actualOriginTimeToUse;
	}
	
	public String getDestinationNode()
	{
		return destinationNode;
	}
	
	public Double getScheduleDestinationTimeToUseAsDouble()
	{
		return scheduleDestinationTimeToUse;
	}
	
	public Double getActualDestinationTimeToUseAsDouble()
	{
		return actualDestinationTimeToUse;
	}
	
	public Double getScheduleTransitTimeAsDouble()
	{
		return scheduleTransitTime;
	}
	
	public Double getActualTransitTimeAsDouble()
	{
		return actualTransitTime;
	}
	
	public Integer getNum()
	{
		return num;
	}
	
	public Integer getDenom()
	{
		return denom;
	}
	
	public String getMake()
	{
		return make;
	}
}