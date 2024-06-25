package com.bl.bias.objects;

public class SchedulePointForTrainObjectB 
{
	private String scheduleNode;
	private Double scheduledArrivalTime;
	private Double scheduledDepartureTime;
	private Double actualArrivalTime;
	private Double actualDepartureTime;
	
	public SchedulePointForTrainObjectB(String scheduleNode, Double scheduledArrivalTime, Double scheduledDepartureTime, Double actualArrivalTime, Double actualDepartureTime) 
    {
        this.scheduleNode = scheduleNode;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.actualArrivalTime = actualArrivalTime;
        this.actualDepartureTime = actualDepartureTime;
    }
		
	public String getScheduledNode()
	{
		return scheduleNode;
	}
	
	public Double getScheduledArrivalTime()
	{
		return scheduledArrivalTime;
	}
	
	public Double getScheduledDepartureTime()
	{
		return scheduledDepartureTime;
	}
	
	public Double getActualArrivalTime()
	{
		return actualArrivalTime;
	}
	
	public Double getActualDepartureTime()
	{
		return actualDepartureTime;
	}
}