package com.bl.bias.objects;

public class ModifiedOtpTrainObjectB 
{
	private String trainSymbol;
	private String scheduleNode;
	
	private Double scheduledArrivalTime;
	private Double scheduledDepartureTime;
	private Double actualArrivalTime;
	private Double actualDepartureTime;
	
	public ModifiedOtpTrainObjectB(String trainSymbol, String scheduleNode, Double scheduledArrivalTime, Double scheduledDepartureTime, Double actualArrivalTime, Double actualDepartureTime) 
    {
        this.trainSymbol = trainSymbol;
        this.scheduleNode = scheduleNode;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.actualArrivalTime = actualArrivalTime;
        this.actualDepartureTime = actualDepartureTime;
    }
		
	public String getTrainSymbol()
	{
		return trainSymbol;
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