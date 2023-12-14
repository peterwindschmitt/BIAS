package com.bl.bias.analyze;

import java.util.ArrayList;

import com.bl.bias.objects.BridgeComplianceClosure;
import com.bl.bias.objects.MarineAccessPeriod;
import com.bl.bias.tools.ConvertDateTime;

public class BridgeComplianceAnalysis 
{
	private static String resultsMessage;
	
	private static ArrayList<BridgeComplianceClosure> closures = new ArrayList<BridgeComplianceClosure>();
	private static ArrayList<MarineAccessPeriod> marineAccessPeriods = new ArrayList<MarineAccessPeriod>();
	
	public BridgeComplianceAnalysis() 
	{
		resultsMessage = "\nStarted creating bridge compliance results at "+ConvertDateTime.getTimeStamp()+"\n";
		
		/*
		// Compute the end of the exclusion period (sim start day and time + warm-up period + exclusion period)
		endOfExclusionPeriodInSeconds = ConvertDateTime.convertDaytoSeconds(BIASBridgeClosureAnalysisController.getSimulationBeginDay())
		     + ConvertDateTime.convertDDHHMMSSStringToSeconds(BIASBridgeClosureAnalysisController.getSimulationBeginTime()+":00")
		     + ConvertDateTime.convertDDHHMMSSStringToSeconds(BIASBridgeClosureAnalysisController.getWarmUpDuration()+":00")
		     + ConvertDateTime.convertDDHHMMSSStringToSeconds(BIASBridgeClosureAnalysisConfigPageController.getResultsExclusionPeriodInHours()+":00:00");

		// Compute the end of the statistical period (sim start day and time + duration of simulation - cool-down period)
		endOfAnalysisPeriodInSeconds = ConvertDateTime.convertDaytoSeconds(BIASBridgeClosureAnalysisController.getSimulationBeginDay())
		     + ConvertDateTime.convertDDHHMMSSStringToSeconds(BIASBridgeClosureAnalysisController.getSimulationBeginTime()+":00")
		     + ConvertDateTime.convertDDHHMMSSStringToSeconds(BIASBridgeClosureAnalysisController.getSimulationDuration()+":00")
		     - ConvertDateTime.convertDDHHMMSSStringToSeconds(BIASBridgeClosureAnalysisController.getCoolDownDuration()+":00");
		
		
		// 1.  Create a crossing object for each applicable train in the BridgeAnalysisRouteTraversal objects.  There may be more than one object created for
		// each train if it crosses a bridge more than once (e.g., changes direction enroute).  Store the first head-end on-station time for the line as well 
		// as the last tail-end on-station time.  These events should occur at absolute signal nodes.
		String entryNode = null;
		String exitNode = null;
		Integer entryNodeArrivalTimeInSeconds = null;
		Integer exitNodeDepartureTimeInSeconds = null;

		Integer lowestPreviousJ = 0;
		Integer crossingCount = 0;

		// For each entry in route file
		for (int i = 0; i < occupanciesFromRouteFile.size(); i++)
		{
			String currentTrainSymbol = occupanciesFromRouteFile.get(i).getTrainSymbol();
			String currentTrainDirection = occupanciesFromRouteFile.get(i).getDirection();
			String currentNode = occupanciesFromRouteFile.get(i).getNode();
			String currentNodeArrivalTimeAsString = occupanciesFromRouteFile.get(i).getHeadEndArrivalTime();
			String currentNodeDepartureTimeAsString = occupanciesFromRouteFile.get(i).getTailEndDepartureTime();
			Integer currentNodeArrivalTimeInSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(currentNodeArrivalTimeAsString);
			Integer currentNodeDepartureTimeInSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(currentNodeDepartureTimeAsString);
			Integer currentNodeRtcIncrement = Integer.valueOf(occupanciesFromRouteFile.get(i).getRtcIncrement());

			entryNode = currentNode;
			entryNodeArrivalTimeInSeconds = currentNodeArrivalTimeInSeconds;
			exitNode = currentNode;
			exitNodeDepartureTimeInSeconds = currentNodeDepartureTimeInSeconds;

			for (int j = lowestPreviousJ; j < occupanciesFromRouteFile.size(); j++)
			{
				String potentialTrainSymbol = occupanciesFromRouteFile.get(j).getTrainSymbol();
				String potentialTrainDirection = occupanciesFromRouteFile.get(j).getDirection();
				String potentialNode = occupanciesFromRouteFile.get(j).getNode();
				String potentialNodeDepartureTimeAsString = occupanciesFromRouteFile.get(j).getTailEndDepartureTime();
				Integer potentialNodeDepartureTimeInSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(potentialNodeDepartureTimeAsString);
				Integer potentialNodeRtcIncrement = Integer.valueOf(occupanciesFromRouteFile.get(j).getRtcIncrement());

				if ((currentTrainSymbol.equals(potentialTrainSymbol)) && (currentTrainDirection.equals(potentialTrainDirection)) && (potentialNodeRtcIncrement > currentNodeRtcIncrement))
				{
					//  Update current crossing object
					exitNode = potentialNode;
					exitNodeDepartureTimeInSeconds = potentialNodeDepartureTimeInSeconds;

					lowestPreviousJ = j + 1;
					i = j;
					continue;
				}
				else if ((currentTrainSymbol.equals(potentialTrainSymbol)) && (currentTrainDirection.equals(potentialTrainDirection)) && (potentialNodeRtcIncrement <= currentNodeRtcIncrement))
				{
					// Set lower bound in array for all objects after this point
					lowestPreviousJ = j + 1;
				}
			}

			// Create Crossing object
			if ((exitNodeDepartureTimeInSeconds + (BIASBridgeClosureAnalysisConfigPageController.getRaiseMinutes() * 60) >= endOfExclusionPeriodInSeconds)
				&& ((entryNodeArrivalTimeInSeconds - BIASBridgeClosureAnalysisConfigPageController.getSignalPreferredMinutesInAdvanceOfTrain() - BIASBridgeClosureAnalysisConfigPageController.getLowerMinutes()) < endOfAnalysisPeriodInSeconds))
			{
				BridgeAnalysisCrossing crossing = new BridgeAnalysisCrossing(currentTrainSymbol, currentTrainDirection, entryNode, entryNodeArrivalTimeInSeconds, exitNode, exitNodeDepartureTimeInSeconds);
				crossings.add(crossing);
				crossingCount++;
			}
		}

		// Sort the ArrayList of closures by entry time OS
		sortedCrossings = new ArrayList <BridgeAnalysisCrossing>();
		sortedCrossings.addAll(crossings);
		Collections.sort(sortedCrossings, new TimeSorter());

		resultsMessage += "Found "+crossingCount+" train closures over bridge\n";

		// 2.  Create a BridgeOccupany object that includes the time period that one or more trains continuously occupies the signal block containing the bridge.
		// This BridgeOccupancy does not consider the bridge-down, signal set-up or bridge-up times.  It's meant to capture when multiple trains may traverse
		// the bridge on parallel tracks and more than one train's OS times should be considered.

		String currentCrossingTrainSymbol = null;
		String currentCrossingTrainDirection = null;
		Integer currentCrossingEntryNodeArrivalTimeInSeconds = null;
		Integer currentCrossingExitNodeDepartureTimeInSeconds = null;
		
		Integer nextCrossingEntryNodeArrivalTimeInSeconds = null;
		
		Boolean overlappingCrossing = null;
		
		ArrayList<String> symbolsAndDirectionsInOccupancy = new ArrayList<String>();
		Integer occupancyStartTime = null;
		Integer occupancyEndTime = null;
		
		// For each entry in sortedCrossings	
		for (int i = 0; i < sortedCrossings.size(); i++)
		{
			currentCrossingTrainSymbol = sortedCrossings.get(i).getTrainSymbol();
			currentCrossingTrainDirection = sortedCrossings.get(i).getTrainDirection();
			currentCrossingEntryNodeArrivalTimeInSeconds = sortedCrossings.get(i).getEntryNodeOSSeconds();
			currentCrossingExitNodeDepartureTimeInSeconds = sortedCrossings.get(i).getExitNodeOSSeconds();
			
			if (i < (sortedCrossings.size() - 1))
				nextCrossingEntryNodeArrivalTimeInSeconds = sortedCrossings.get(i+1).getEntryNodeOSSeconds();
			else
				nextCrossingEntryNodeArrivalTimeInSeconds = Integer.MAX_VALUE;
			
			if ((nextCrossingEntryNodeArrivalTimeInSeconds <= currentCrossingExitNodeDepartureTimeInSeconds) && (i < (sortedCrossings.size() - 1)))
			{
				if (overlappingCrossing)
				{
					// Already at least two trains involved in this occupancy
					symbolsAndDirectionsInOccupancy.add(currentCrossingTrainSymbol+"-"+currentCrossingTrainDirection);
					if (occupancyStartTime == null)
						occupancyStartTime = currentCrossingEntryNodeArrivalTimeInSeconds;
					else if (occupancyStartTime > currentCrossingEntryNodeArrivalTimeInSeconds)
						occupancyStartTime = currentCrossingEntryNodeArrivalTimeInSeconds;
					
					if (occupancyEndTime == null)
						occupancyEndTime = currentCrossingExitNodeDepartureTimeInSeconds;
					else if (occupancyEndTime < currentCrossingExitNodeDepartureTimeInSeconds)
						occupancyEndTime = currentCrossingExitNodeDepartureTimeInSeconds;
				}
				else
				{
					// Second train involved in this occupancy
					overlappingCrossing = true;
					symbolsAndDirectionsInOccupancy.add(currentCrossingTrainSymbol+"-"+currentCrossingTrainDirection);
					if (occupancyStartTime == null)
						occupancyStartTime = currentCrossingEntryNodeArrivalTimeInSeconds;
					else if (occupancyStartTime > currentCrossingEntryNodeArrivalTimeInSeconds)
						occupancyStartTime = currentCrossingEntryNodeArrivalTimeInSeconds;
					
					if (occupancyEndTime == null)
						occupancyEndTime = currentCrossingExitNodeDepartureTimeInSeconds;
					else if (occupancyEndTime < currentCrossingExitNodeDepartureTimeInSeconds)
						occupancyEndTime = currentCrossingExitNodeDepartureTimeInSeconds;
				}
			}
			else
			{
				// No more trains in this occupancy
				// Add current train to the Occupancy object
				symbolsAndDirectionsInOccupancy.add(currentCrossingTrainSymbol+"-"+currentCrossingTrainDirection);
				
				if (occupancyStartTime == null)
					occupancyStartTime = currentCrossingEntryNodeArrivalTimeInSeconds;
				else if (occupancyStartTime > currentCrossingEntryNodeArrivalTimeInSeconds)
					occupancyStartTime = currentCrossingEntryNodeArrivalTimeInSeconds;
				
				if (occupancyEndTime == null)
					occupancyEndTime = currentCrossingExitNodeDepartureTimeInSeconds;
				else if (occupancyEndTime < currentCrossingExitNodeDepartureTimeInSeconds)
					occupancyEndTime = currentCrossingExitNodeDepartureTimeInSeconds;
				
				// Create new occupancy object
				BridgeAnalysisOccupancy occupancy = new BridgeAnalysisOccupancy(symbolsAndDirectionsInOccupancy, occupancyStartTime, occupancyEndTime);
				occupancies.add(occupancy);
				
				// Remove all symbols, directions and times for next object
				overlappingCrossing = false;
				symbolsAndDirectionsInOccupancy.clear();
				occupancyStartTime = null;
				occupancyEndTime = null;
			}
		}
		resultsMessage += "Created "+occupancies.size()+" bridge occupancy periods\n";

		//  3.  Create Bridge Closures by adding bridge down time + signal setup time + bridge up time to the Bridge Occupancies.  If there's less time 
		//  between occupancies than the minimum up-time, the bridge must remain down.  Reported values (incorporating ceiling functions) are used only in writing 
		//  the results to a spreadsheet (classes in the WRITE package).  They are not considered in achieving minimum up-time. This is a different handling than in MOW analysis where
		//  a ceiling/floor value must be applied and is used to determine whether a window is valid.
		
		Integer currentOccupancyStartTimeInSeconds = null;
		Integer currentOccupancyFinishTimeInSeconds = null;
		
		Integer nextOccupancyStartTimeInSeconds = null;
		
		Boolean combineWithNextOccupancy = false;
		
		ArrayList<String> symbolsAndDirectionsInClosure = new ArrayList<String>();
		
		Integer bridgeMoveDownDurationInSeconds = BIASBridgeClosureAnalysisConfigPageController.getLowerMinutes() * 60;
		Integer bridgeMoveUpDurationInSeconds = BIASBridgeClosureAnalysisConfigPageController.getRaiseMinutes() * 60;
		Integer signalSetUpDurationInSeconds = BIASBridgeClosureAnalysisConfigPageController.getSignalPreferredMinutesInAdvanceOfTrain() * 60;
		Integer minimumUpTimeInSeconds = BIASBridgeClosureAnalysisConfigPageController.getMinimumUpTimeMinutes() * 60;
		
		Integer tentativePreferredClosureStartTimeInSeconds = null;
		Integer latestClosureStartTimeInSeconds = null;
		Integer tentativeClosureEndTimeInSeconds = null;
		
		Integer finaledPreferredClosureStartTimeInSeconds = null;
		Integer finaledClosureEndTimeInSeconds = null;
		
		Integer nextClosureStartTimeInSeconds = null;
		Integer upTimeBetweenCurrentAndNextClosureInSeconds = null;
						
		for (int i = 0; i < occupancies.size(); i++)
		{
			// From occupancies
			currentOccupancyStartTimeInSeconds = occupancies.get(i).getOccupancyStartTimeInSeconds();
			currentOccupancyFinishTimeInSeconds = occupancies.get(i).getOccupancyEndTimeInSeconds();
			
			if (!combineWithNextOccupancy)
			{
				tentativePreferredClosureStartTimeInSeconds = currentOccupancyStartTimeInSeconds - signalSetUpDurationInSeconds - bridgeMoveDownDurationInSeconds;
				latestClosureStartTimeInSeconds = currentOccupancyStartTimeInSeconds - bridgeMoveDownDurationInSeconds;
			}
			tentativeClosureEndTimeInSeconds = currentOccupancyFinishTimeInSeconds + bridgeMoveUpDurationInSeconds;
			
			if (i < (occupancies.size() - 1))
			{
				// All occupancies except the last one
				nextOccupancyStartTimeInSeconds = occupancies.get(i+1).getOccupancyStartTimeInSeconds();
				nextClosureStartTimeInSeconds = nextOccupancyStartTimeInSeconds - signalSetUpDurationInSeconds - bridgeMoveDownDurationInSeconds;
				upTimeBetweenCurrentAndNextClosureInSeconds = nextClosureStartTimeInSeconds - tentativeClosureEndTimeInSeconds;
				
				if (upTimeBetweenCurrentAndNextClosureInSeconds < minimumUpTimeInSeconds)
				{
					// Continue closure
					finaledPreferredClosureStartTimeInSeconds = tentativePreferredClosureStartTimeInSeconds;
					symbolsAndDirectionsInClosure.addAll(occupancies.get(i).getTrainSymbolsAndDirectionsInOccupancy());
					combineWithNextOccupancy = true;
				}
				else 
				{
					// End closure
					combineWithNextOccupancy = false;
					finaledPreferredClosureStartTimeInSeconds = tentativePreferredClosureStartTimeInSeconds;
					finaledClosureEndTimeInSeconds = tentativeClosureEndTimeInSeconds;
					symbolsAndDirectionsInClosure.addAll(occupancies.get(i).getTrainSymbolsAndDirectionsInOccupancy());
					
					BridgeAnalysisClosure closure = new BridgeAnalysisClosure(symbolsAndDirectionsInClosure, finaledPreferredClosureStartTimeInSeconds, latestClosureStartTimeInSeconds, finaledClosureEndTimeInSeconds, bridgeMoveDownDurationInSeconds, signalSetUpDurationInSeconds, bridgeMoveUpDurationInSeconds, upTimeBetweenCurrentAndNextClosureInSeconds);
					closures.add(closure);
					
					symbolsAndDirectionsInClosure.clear();
				}
			}
			else
			{
				// Final occupancy
				if (!combineWithNextOccupancy)
					finaledPreferredClosureStartTimeInSeconds = tentativePreferredClosureStartTimeInSeconds;
								
				combineWithNextOccupancy = false;
				finaledClosureEndTimeInSeconds = tentativeClosureEndTimeInSeconds;
				symbolsAndDirectionsInClosure.addAll(occupancies.get(i).getTrainSymbolsAndDirectionsInOccupancy());
				
				BridgeAnalysisClosure closure = new BridgeAnalysisClosure(symbolsAndDirectionsInClosure, finaledPreferredClosureStartTimeInSeconds, latestClosureStartTimeInSeconds, finaledClosureEndTimeInSeconds, bridgeMoveDownDurationInSeconds, signalSetUpDurationInSeconds, bridgeMoveUpDurationInSeconds, null);
				closures.add(closure);				
			}
		}
		resultsMessage += "Created "+closures.size()+" bridge closures\n";
		*/
		resultsMessage += "Finished creating bridge compliance results at "+ConvertDateTime.getTimeStamp()+"\n";
	}


	public static ArrayList<BridgeComplianceClosure> getClosures()
	{
		return closures;
	}

	public static void clearClosures()
	{
		closures.clear();
	}
	
	public static void clearMarineAccessPeriods()
	{
		marineAccessPeriods.clear();
	}
	
	public String getResultsMessage()
	{
		return resultsMessage;
	}
}