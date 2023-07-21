package com.bl.bias.objects;

import java.util.ArrayList;

import com.bl.bias.app.BIASBridgeClosureAnalysisConfigPageController;
import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.tools.DoesEventOccurDuringActiveMarineAccessPeriod;

public class BridgeAnalysisClosure 
{
	// A closure is defined as the time period of bridge occupancy + bridge lowering + bridge raising + signal set-up + unoccupied period if minimum up-time cannot be achieved
	// Values passed in via constructor
	private ArrayList<String> trainSymbolsAndDirectionsInClosure = new ArrayList<String>();

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
	private Integer plannedBridgeDownStartTimeInSeconds;
	private Integer signalSetUpCompleteTimeInSeconds;
	private Integer bridgeUpStartTimeInSeconds;
	private Integer bridgeUpCompleteTimeInSeconds;
	
	public BridgeAnalysisClosure(ArrayList<String> trainSymbolsAndDirectionsInOccupancy, Integer preferredBridgeDownStartTimeInSeconds, Integer latestBridgeDownStartTimeInSeconds, Integer closureEndTimeInSeconds, Integer bridgeMoveDownDurationInSeconds, Integer signalSetUpDurationInSeconds, Integer bridgeMoveUpDurationInSeconds, Integer upTimeBetweenCurrentAndNextClosureInSeconds) 
	{
		this.trainSymbolsAndDirectionsInClosure.addAll(trainSymbolsAndDirectionsInOccupancy);
		this.preferredBridgeDownStartTimeInSeconds = preferredBridgeDownStartTimeInSeconds;
		this.latestBridgeDownStartTimeInSeconds = latestBridgeDownStartTimeInSeconds;
		this.closureEndTimeInSeconds = closureEndTimeInSeconds;
		this.bridgeMoveDownDurationInSeconds = bridgeMoveDownDurationInSeconds;
		this.signalSetUpDurationInSeconds = signalSetUpDurationInSeconds;
		this.bridgeMoveUpDurationInSeconds = bridgeMoveUpDurationInSeconds;
		this.upTimeBetweenCurrentAndNextClosureInSeconds = upTimeBetweenCurrentAndNextClosureInSeconds;
		
		this.plannedBridgeDownStartTimeInSeconds = getPlannedBridgeDownStartTimeInSeconds();
	}

	public ArrayList<String> getTrainSymbolsAndDirectionsInClosure() 
	{
		return trainSymbolsAndDirectionsInClosure;
	}

	public Integer getClosureDurationInSeconds()
	{
		closureDurationInSeconds = closureEndTimeInSeconds - plannedBridgeDownStartTimeInSeconds;
		return closureDurationInSeconds;
	}

	public Integer getPreferredBridgeDownStartTimeInSeconds() 
	{
		return preferredBridgeDownStartTimeInSeconds;
	}

	public Integer getLatestBridgeDownStartTimeInSeconds() 
	{
		latestBridgeDownStartTimeInSeconds = preferredBridgeDownStartTimeInSeconds + signalSetUpDurationInSeconds;
		return latestBridgeDownStartTimeInSeconds;
	}

	public Integer getPlannedBridgeDownStartTimeInSeconds() 
	{
		if (DoesEventOccurDuringActiveMarineAccessPeriod.doesEventOccurDuringActiveMarineAccessPeriod(preferredBridgeDownStartTimeInSeconds)) 
		{
			//System.out.print("Preferred bridge down start request at "+preferredClosureStartTimeInSeconds+" which is "+ConvertDateTime.convertSecondsToDDHHMMSSString(preferredClosureStartTimeInSeconds));
			//System.out.print(" and occcurs during marine access period starting at minute "+Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", ""))+" and ending at minute "+Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", ""))+".");
			//System.out.println(" Latest start down time at "+latestBridgeDownStartTimeInSeconds+" which is "+ConvertDateTime.convertSecondsToDDHHMMSSString(latestBridgeDownStartTimeInSeconds)+".");

			plannedBridgeDownStartTimeInSeconds = preferredBridgeDownStartTimeInSeconds;

			int incrementAmountInSeconds = 300; // 300 seconds reflects 5 min increments from bridge config selection

			// Case 0:  If marine period does NOT contain top of hour AND if latest bridge down start time occurs between start and end of marine period (not inclusive)
			if ((Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", "")) > (Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", "")))
					&& (ConvertDateTime.convertSecondsToMM(latestBridgeDownStartTimeInSeconds) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", "")))
					&& (ConvertDateTime.convertSecondsToMM(latestBridgeDownStartTimeInSeconds) > Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", "")))))
			{
				//System.out.print("C0: ");
				plannedBridgeDownStartTimeInSeconds = latestBridgeDownStartTimeInSeconds;
			}
			// Case 1:  If marine period does contain top of hour AND if latest bridge down start time occurs between start and end of marine period (not inclusive)
			else if ((Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", "")) < (Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", "")))
					&& ((ConvertDateTime.convertSecondsToMM(latestBridgeDownStartTimeInSeconds) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", "")))
					|| (ConvertDateTime.convertSecondsToMM(latestBridgeDownStartTimeInSeconds) > Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", ""))))))
			{
				//System.out.print("C1: ");
				plannedBridgeDownStartTimeInSeconds = latestBridgeDownStartTimeInSeconds;
			}
			// Case 2:  When end of marine period is NOT going to exceed top of the hour AND incrementing to next 5 minutes does NOT exceed latest bridge down start time
			else if (((Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", "")) > (Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", "")))
					&& (plannedBridgeDownStartTimeInSeconds + incrementAmountInSeconds <= latestBridgeDownStartTimeInSeconds))))
			{
				//System.out.print("C2: ");
				do 
				{
					plannedBridgeDownStartTimeInSeconds = (int) ConvertDateTime.ceilingToNearestIncrementOfSeconds(plannedBridgeDownStartTimeInSeconds, incrementAmountInSeconds); 
					//System.out.print("*");
				}
				while
					(ConvertDateTime.convertSecondsToMM(plannedBridgeDownStartTimeInSeconds) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", ""))
							&& (plannedBridgeDownStartTimeInSeconds <= latestBridgeDownStartTimeInSeconds)); 
			}
			// Case 3: When end of marine period is going to exceed top of the hour AND incrementing to next 5 minutes does NOT exceed latest bridge down start time
			else if (((Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", "")) < (Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", "")))
					&& (plannedBridgeDownStartTimeInSeconds + incrementAmountInSeconds <= latestBridgeDownStartTimeInSeconds))))
			{
				//System.out.print("C3: ");
				do 
				{
					plannedBridgeDownStartTimeInSeconds = (int) ConvertDateTime.ceilingToNearestIncrementOfSeconds(plannedBridgeDownStartTimeInSeconds, incrementAmountInSeconds); 
					//System.out.print("*");
				}
				while
					(ConvertDateTime.convertSecondsToMM(plannedBridgeDownStartTimeInSeconds) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", ""))
							&& (plannedBridgeDownStartTimeInSeconds <= latestBridgeDownStartTimeInSeconds)); 
			}
			//else
				//System.out.println("Shit");

			// Output
			// System.out.print("Planned start down time is set to "+plannedBridgeDownStartTimeInSeconds + " which is "+ConvertDateTime.convertSecondsToDDHHMMSSString(plannedBridgeDownStartTimeInSeconds)+".\n\n");
		}
		else
		{
			plannedBridgeDownStartTimeInSeconds = preferredBridgeDownStartTimeInSeconds;
		}

		return plannedBridgeDownStartTimeInSeconds;
	}

	public Integer getBridgeDownCompleteTimeInSeconds() 
	{
		bridgeDownCompleteTimeInSeconds = plannedBridgeDownStartTimeInSeconds + bridgeMoveDownDurationInSeconds;
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
		signalSetUpCompleteTimeInSeconds = preferredBridgeDownStartTimeInSeconds + bridgeMoveDownDurationInSeconds + signalSetUpDurationInSeconds;  // Uses preferred rather than planned to determine when HE arrives on circuit
		return signalSetUpCompleteTimeInSeconds;
	}

	public Integer getClosureStartTimeInSeconds() 
	{
		return plannedBridgeDownStartTimeInSeconds;
	}

	public Integer getClosureEndTimeInSeconds() 
	{
		return closureEndTimeInSeconds;
	}
	
	public Integer getUpTimeBetweenCurrentAndNextClosureInSeconds() 
	{
		return upTimeBetweenCurrentAndNextClosureInSeconds;
	}
	
	public Integer getBridgeMoveDownDurationInSeconds()
	{
		return bridgeMoveDownDurationInSeconds;
	}
	
	public Integer getBridgeMoveUpDurationInSeconds()
	{
		return bridgeMoveUpDurationInSeconds;
	}
}