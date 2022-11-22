package com.bl.bias.objects;

public class BridgeAnalysisSignal 
{
	private String signalBeginNodeId;
	private String signalType;
	private String signalDirection;
				
	public BridgeAnalysisSignal(String signalBeginNodeId, String signalType, String signalDirection)
	{
		this.signalBeginNodeId = signalBeginNodeId;
		this.signalType = signalType;
		this.signalDirection = signalDirection;
	}
	
	public String getSignalBeginNodeId() 
	{
		return signalBeginNodeId;
	}

	public String getSignalType()
	{
		return signalType;
	}
	
	public String getSignalDirection()
	{
		return signalDirection;
	}
}