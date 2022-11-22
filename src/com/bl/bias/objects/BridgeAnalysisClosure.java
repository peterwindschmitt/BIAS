package com.bl.bias.objects;

import java.util.ArrayList;

public class BridgeAnalysisClosure 
{
	// A closure is defined as the time period of bridge occupancy + bridge lowering + bridge raising + signal set-up + unoccupied period if minimum up-time cannot be achieved
	// Values passed in via constructor
	private ArrayList<String> trainSymbolsAndDirectionsInClosure = new ArrayList<String>();
	
	private Integer closureStartTimeInSeconds;
	private Integer closureEndTimeInSeconds;
	private Integer upTimeBetweenCurrentAndNextClosureInSeconds;
	
	private Integer bridgeMoveDownDurationInSeconds;
	private Integer signalSetUpDurationInSeconds;	
	private Integer bridgeMoveUpDurationInSeconds;
		
	// Values computed in this class
	private Integer closureDurationInSeconds;
	private Integer preferredBridgeDownStartTimeInSeconds;
	private Integer bridgeDownCompleteTimeInSeconds;
	private Integer latestBridgeDownStartTimeInSeconds;
	private Integer signalSetUpCompleteTimeInSeconds;
	private Integer bridgeUpStartTimeInSeconds;
	private Integer bridgeUpCompleteTimeInSeconds;
		
	public BridgeAnalysisClosure(ArrayList<String> trainSymbolsAndDirectionsInOccupancy, Integer preferredClosureStartTimeInSeconds, Integer latestClosureStartTimeInSeconds,Integer closureEndTimeInSeconds, Integer bridgeMoveDownDurationInSeconds, Integer signalSetUpDurationInSeconds, Integer bridgeMoveUpDurationInSeconds, Integer upTimeBetweenCurrentAndNextClosureInSeconds) 
	{
		this.trainSymbolsAndDirectionsInClosure.addAll(trainSymbolsAndDirectionsInOccupancy);
		this.closureStartTimeInSeconds = preferredClosureStartTimeInSeconds;
		this.latestBridgeDownStartTimeInSeconds = latestClosureStartTimeInSeconds;
		this.closureEndTimeInSeconds = closureEndTimeInSeconds;
		this.bridgeMoveDownDurationInSeconds = bridgeMoveDownDurationInSeconds;
		this.signalSetUpDurationInSeconds = signalSetUpDurationInSeconds;
		this.bridgeMoveUpDurationInSeconds = bridgeMoveUpDurationInSeconds;
		this.upTimeBetweenCurrentAndNextClosureInSeconds = upTimeBetweenCurrentAndNextClosureInSeconds;
	}

	public ArrayList<String> getTrainSymbolsAndDirectionsInClosure() 
	{
		return trainSymbolsAndDirectionsInClosure;
	}

	public Integer getClosureDurationInSeconds()
	{
		closureDurationInSeconds = closureEndTimeInSeconds - closureStartTimeInSeconds;
		return closureDurationInSeconds;
	}

	public Integer getPreferredBridgeDownStartTimeInSeconds() 
	{
		preferredBridgeDownStartTimeInSeconds = closureStartTimeInSeconds;
		return preferredBridgeDownStartTimeInSeconds;
	}
	
	public Integer getLatestBridgeDownStartTimeInSeconds() 
	{
		latestBridgeDownStartTimeInSeconds = preferredBridgeDownStartTimeInSeconds + signalSetUpDurationInSeconds;
		return latestBridgeDownStartTimeInSeconds;
	}

	public Integer getBridgeDownCompleteTimeInSeconds() 
	{
		bridgeDownCompleteTimeInSeconds = preferredBridgeDownStartTimeInSeconds + bridgeMoveDownDurationInSeconds;
		return bridgeDownCompleteTimeInSeconds;
	}

	public Integer getBridgeUpStartTimeInSeconds() 
	{
		bridgeUpStartTimeInSeconds = closureEndTimeInSeconds - bridgeMoveUpDurationInSeconds;
		return bridgeUpStartTimeInSeconds;
	}

	public Integer getBridgeUpCompleteTimeInSeconds() 
	{
		bridgeUpCompleteTimeInSeconds = closureEndTimeInSeconds;
		return bridgeUpCompleteTimeInSeconds;
	}

	public Integer getSignalSetUpCompleteTimeInSeconds() 
	{
		signalSetUpCompleteTimeInSeconds = closureStartTimeInSeconds + bridgeMoveDownDurationInSeconds + signalSetUpDurationInSeconds;
		return signalSetUpCompleteTimeInSeconds;
	}

	public Integer getClosureStartTimeInSeconds() 
	{
		return closureStartTimeInSeconds;
	}

	public Integer getClosureEndTimeInSeconds() 
	{
		return closureEndTimeInSeconds;
	}
	
	public Integer getUpTimeBetweenCurrentAndNextClosureInSeconds() 
	{
		return upTimeBetweenCurrentAndNextClosureInSeconds;
	}
}