package com.bl.bias.objects;

public class GradeXingRawLink 
{
	private String nodeA;
	private String nodeB;
	private Integer passengerSpeed;
	private Integer throughSpeed;
	private Integer localSpeed;
	
	public GradeXingRawLink(String nodeA, String nodeB, Integer designPassengerSpeed, Integer designThroughSpeed, Integer designLocalSpeed) 
	{
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.passengerSpeed = designPassengerSpeed;
		this.throughSpeed = designThroughSpeed;
		this.localSpeed = designLocalSpeed;
	}

	public String getNodeA()
	{
		return nodeA;
	}

	public String getNodeB() 
	{
		return nodeB;
	}
	
	public Integer getPasssengerSpeed()
	{
		return passengerSpeed;
	}
	
	public Integer getThroughSpeed()
	{
		return throughSpeed;
	}
	
	public Integer getLocalSpeed()
	{
		return localSpeed;
	}
}