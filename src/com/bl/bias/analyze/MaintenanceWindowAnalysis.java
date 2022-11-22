package com.bl.bias.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.bl.bias.app.BIASMaintenanceWindowAnalysisPageController;
import com.bl.bias.app.BIASMaintenanceWindowAnalysisConfigPageController;
import com.bl.bias.objects.MaintenanceWindowAnalysisLink;
import com.bl.bias.objects.MaintenanceWindowAnalysisNode;
import com.bl.bias.objects.MaintenanceWindowAnalysisSegment;
import com.bl.bias.objects.MaintenanceWindowAnalysisWindow;
import com.bl.bias.objects.MaintenanceWindowAnalysisSubLineOccupancy;
import com.bl.bias.objects.MaintenanceWindowAnalysisTrainTraversal;
import com.bl.bias.tools.ConvertDateTime;

public class MaintenanceWindowAnalysis 
{
	private static String resultsMessage;

	private static Integer endOfExclusionPeriodInSeconds;
	private static Integer endOfAnalysisPeriodInSeconds;

	public MaintenanceWindowAnalysis(ArrayList<MaintenanceWindowAnalysisSegment> segments) 
	{
		resultsMessage = "\nStarted creating maintenance window results at "+ConvertDateTime.getTimeStamp()+"\n";

		// Compute the end of the exclusion period (sim start day and time + warm-up period + exclusion period)
		endOfExclusionPeriodInSeconds = ConvertDateTime.convertDaytoSeconds(BIASMaintenanceWindowAnalysisPageController.getSimulationBeginDay())
				+ ConvertDateTime.convertDDHHMMSSStringToSeconds(BIASMaintenanceWindowAnalysisPageController.getSimulationBeginTime()+":00")
				+ ConvertDateTime.convertDDHHMMSSStringToSeconds(BIASMaintenanceWindowAnalysisPageController.getWarmUpDuration()+":00")
				+ ConvertDateTime.convertDDHHMMSSStringToSeconds(BIASMaintenanceWindowAnalysisConfigPageController.getResultsExclusionPeriodInHours()+":00:00");

		// Compute the end of the statistical period (sim start day and time + duration of simulation - cool-down period)
		endOfAnalysisPeriodInSeconds = ConvertDateTime.convertDaytoSeconds(BIASMaintenanceWindowAnalysisPageController.getSimulationBeginDay())
				+ ConvertDateTime.convertDDHHMMSSStringToSeconds(BIASMaintenanceWindowAnalysisPageController.getSimulationBeginTime()+":00")
				+ ConvertDateTime.convertDDHHMMSSStringToSeconds(BIASMaintenanceWindowAnalysisPageController.getSimulationDuration()+":00")
				- ConvertDateTime.convertDDHHMMSSStringToSeconds(BIASMaintenanceWindowAnalysisPageController.getCoolDownDuration()+":00");

		resultsMessage += "THE ANALYZED PERIOD IS FROM "+ConvertDateTime.convertSecondsToDayHHMMSSString(endOfExclusionPeriodInSeconds).toUpperCase()+" TO "+ConvertDateTime.convertSecondsToDayHHMMSSString(endOfAnalysisPeriodInSeconds).toUpperCase()+"\n";

		for (int h = 0; h < segments.size(); h++)
		{
			ArrayList<MaintenanceWindowAnalysisTrainTraversal> trainTraversals = new ArrayList<MaintenanceWindowAnalysisTrainTraversal>();
			ArrayList<MaintenanceWindowAnalysisTrainTraversal> sortedTraversals = new ArrayList<MaintenanceWindowAnalysisTrainTraversal>();
			ArrayList<MaintenanceWindowAnalysisSubLineOccupancy> subLineOccupancies = new ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>(); 

			// 1.  Create a TrainTraversal object for each applicable train in the MaintenanceWindowAnalysisRouteTraversal objects.  There may be more than one object created for
			// each train if it crosses a line more than once (e.g., changes direction enroute).  Store the first head-end on-station time for the line as well 
			// as the last tail-end on-station time.  
			String entryNode = null;
			String exitNode = null;
			Integer entryNodeArrivalTimeInSeconds = null;
			Integer exitNodeDepartureTimeInSeconds = null;

			Integer lowestPreviousJ = 0;
			Integer traversalCount = 0;

			// For each entry in route file
			for (int i = 0; i < segments.get(h).getRouteTraversalsInLine().size(); i++)
			{
				String currentTrainSymbol = segments.get(h).getRouteTraversalsInLine().get(i).getTrainSymbol();
				String currentTrainDirection = segments.get(h).getRouteTraversalsInLine().get(i).getDirection();
				String currentNode = segments.get(h).getRouteTraversalsInLine().get(i).getNode();
				String currentNodeArrivalTimeAsString = segments.get(h).getRouteTraversalsInLine().get(i).getHeadEndArrivalTime();
				String currentNodeDepartureTimeAsString =segments.get(h).getRouteTraversalsInLine().get(i).getTailEndDepartureTime();
				Integer currentNodeArrivalTimeInSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(currentNodeArrivalTimeAsString);
				Integer currentNodeDepartureTimeInSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(currentNodeDepartureTimeAsString);
				Integer currentNodeRtcIncrement = Integer.valueOf(segments.get(h).getRouteTraversalsInLine().get(i).getRtcIncrement());

				entryNode = currentNode;
				entryNodeArrivalTimeInSeconds = currentNodeArrivalTimeInSeconds;
				exitNode = currentNode;
				exitNodeDepartureTimeInSeconds = currentNodeDepartureTimeInSeconds;

				for (int j = lowestPreviousJ; j < segments.get(h).getRouteTraversalsInLine().size(); j++)
				{
					String potentialTrainSymbol = segments.get(h).getRouteTraversalsInLine().get(j).getTrainSymbol();
					String potentialTrainDirection = segments.get(h).getRouteTraversalsInLine().get(j).getDirection();
					String potentialNode = segments.get(h).getRouteTraversalsInLine().get(j).getNode();
					String potentialNodeDepartureTimeAsString = segments.get(h).getRouteTraversalsInLine().get(j).getTailEndDepartureTime();
					Integer potentialNodeDepartureTimeInSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(potentialNodeDepartureTimeAsString);
					Integer potentialNodeRtcIncrement = Integer.valueOf(segments.get(h).getRouteTraversalsInLine().get(j).getRtcIncrement());

					if ((currentTrainSymbol.equals(potentialTrainSymbol)) && (currentTrainDirection.equals(potentialTrainDirection)) && (potentialNodeRtcIncrement > currentNodeRtcIncrement))
					{
						//  Update current traversal object
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

				// Create traversal object
				if ((exitNodeDepartureTimeInSeconds + (BIASMaintenanceWindowAnalysisConfigPageController.getBlockUnoccupiedInAdvanceOfWindowMinutes() * 60) >= endOfExclusionPeriodInSeconds)
						&& ((entryNodeArrivalTimeInSeconds - BIASMaintenanceWindowAnalysisConfigPageController.getWindowMustEndMinutesInAdvanceOfTrain()) < endOfAnalysisPeriodInSeconds))
				{
					MaintenanceWindowAnalysisTrainTraversal traversal = new MaintenanceWindowAnalysisTrainTraversal(currentTrainSymbol, currentTrainDirection, entryNode, entryNodeArrivalTimeInSeconds, exitNode, exitNodeDepartureTimeInSeconds);
					trainTraversals.add(traversal);
					traversalCount++;
				}
			}

			// Sort the ArrayList of closures by entry time OS
			sortedTraversals = new ArrayList <MaintenanceWindowAnalysisTrainTraversal>();
			sortedTraversals.addAll(trainTraversals);
			Collections.sort(sortedTraversals, new TimeSorter());

			// Send the sortedTraversals to the segment object
			segments.get(h).setTrainTraversalsInLine(sortedTraversals);

			resultsMessage += "Found "+traversalCount+" train traversals over "+segments.get(h).getLineName()+" line\n";
		}

		// 2.  Create a SubLineOccupany object that includes the time period that one or more trains continuously occupies a subline
		// For each segment	
		for (int i = 0; i < segments.size(); i++)
		{
			// Create a HashMap to store the subLine occupancies
			HashMap<String, ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>> subLinesOccupancies = new HashMap<String, ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>>();

			// Create a HashMap for determining the node/link track assignment
			HashMap<String, String> nodes = new HashMap<String, String>();
			Iterator<MaintenanceWindowAnalysisNode> itrGetNodesInSegment = segments.get(i).getNodesInLine().iterator();
			while (itrGetNodesInSegment.hasNext())
			{
				MaintenanceWindowAnalysisNode node = itrGetNodesInSegment.next();
				nodes.put(node.getNodeId(), node.getTrackNumber());
			}

			// For each subLine	
			Iterator<HashSet<MaintenanceWindowAnalysisLink>> itrSubLine = segments.get(i).getSubLines().iterator();
			while (itrSubLine.hasNext())
			{
				HashSet<MaintenanceWindowAnalysisLink> subLine = itrSubLine.next();
				Iterator<MaintenanceWindowAnalysisLink> itrSubLineLinks = subLine.iterator();
				String subLinesTrackNumber = nodes.get(itrSubLineLinks.next().getNodeAId());

				ArrayList<MaintenanceWindowAnalysisSubLineOccupancy> subLineOccupancies = new ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>();
				ArrayList<MaintenanceWindowAnalysisTrainTraversal> subLineTraversals = new ArrayList<MaintenanceWindowAnalysisTrainTraversal>();

				// Create arrayList of trains (subLine traversals) which traversed the subline
				if (segments.get(i).getTrainTraversalsInLine() != null)
				{
					for (int k = 0; k < segments.get(i).getTrainTraversalsInLine().size(); k++)
					{	
						// For the entry node of the traversal, determine the track number
						String trainTraversalTrackNumber = nodes.get(segments.get(i).getTrainTraversalsInLine().get(k).getEntryNode());
						if (trainTraversalTrackNumber.equals(subLinesTrackNumber))
						{
							subLineTraversals.add(segments.get(i).getTrainTraversalsInLine().get(k));	
						}
					}
				}

				MaintenanceWindowAnalysisTrainTraversal currentTraversal = null;
				MaintenanceWindowAnalysisTrainTraversal nextTraversal = null;

				Integer currentTraversalEntryTimeInSeconds = null;
				Integer currentTraversalExitTimeInSeconds = null;
				Integer nextTraversalEntryTimeInSeconds = null;
				Integer occupancyStartTime = null;
				Integer occupancyEndTime = null;

				Boolean overlappingOccupancy = false;

				ArrayList<String> trainsInThisSubLineOccupancy = new ArrayList<String>();

				for (int k = 0; k < subLineTraversals.size(); k++)
				{
					currentTraversal = subLineTraversals.get(k);
					currentTraversalEntryTimeInSeconds = currentTraversal.getEntryNodeOSSeconds();
					currentTraversalExitTimeInSeconds = currentTraversal.getExitNodeOSSeconds();

					if (k < subLineTraversals.size() - 1)
					{
						nextTraversal = subLineTraversals.get(k + 1);
						nextTraversalEntryTimeInSeconds = nextTraversal.getEntryNodeOSSeconds();
					}
					else
						nextTraversalEntryTimeInSeconds = Integer.MAX_VALUE;

					if ((nextTraversalEntryTimeInSeconds <= currentTraversalExitTimeInSeconds) && (i < (subLineTraversals.size() - 1)))
					{
						if (overlappingOccupancy)
						{
							// Already at least two trains involved in this occupancy
							trainsInThisSubLineOccupancy.add(currentTraversal.getTrainSymbol()+"-"+currentTraversal.getTrainDirection());
							if (occupancyStartTime == null)
								occupancyStartTime =currentTraversalEntryTimeInSeconds;
							else if (occupancyStartTime > currentTraversalEntryTimeInSeconds)
								occupancyStartTime = currentTraversalEntryTimeInSeconds;

							if (occupancyEndTime == null)
								occupancyEndTime = currentTraversalExitTimeInSeconds;
							else if (occupancyEndTime < currentTraversalExitTimeInSeconds)
								occupancyEndTime = currentTraversalExitTimeInSeconds;
						}
						else
						{
							// Second train involved in this occupancy
							overlappingOccupancy = true;
							trainsInThisSubLineOccupancy.add(currentTraversal.getTrainSymbol()+"-"+currentTraversal.getTrainDirection());
							if (occupancyStartTime == null)
								occupancyStartTime = currentTraversalEntryTimeInSeconds;
							else if (occupancyStartTime > currentTraversalEntryTimeInSeconds)
								occupancyStartTime = currentTraversalEntryTimeInSeconds;

							if (occupancyEndTime == null)
								occupancyEndTime = currentTraversalExitTimeInSeconds;
							else if (occupancyEndTime < currentTraversalExitTimeInSeconds)
								occupancyEndTime = currentTraversalExitTimeInSeconds;
						}
					}
					else
					{
						// No more trains in this occupancy
						// Add current train to the Occupancy object
						trainsInThisSubLineOccupancy.add(currentTraversal.getTrainSymbol()+"-"+currentTraversal.getTrainDirection());
						if (occupancyStartTime == null)
							occupancyStartTime = currentTraversalEntryTimeInSeconds;
						else if (occupancyStartTime > currentTraversalEntryTimeInSeconds)
							occupancyStartTime = currentTraversalEntryTimeInSeconds;

						if (occupancyEndTime == null)
							occupancyEndTime = currentTraversalExitTimeInSeconds;
						else if (occupancyEndTime < currentTraversalExitTimeInSeconds)
							occupancyEndTime = currentTraversalExitTimeInSeconds;

						// Create new occupancy object
						MaintenanceWindowAnalysisSubLineOccupancy occupancy = new MaintenanceWindowAnalysisSubLineOccupancy(trainsInThisSubLineOccupancy, occupancyStartTime, occupancyEndTime);
						subLineOccupancies.add(occupancy);

						// Remove all symbols, directions and times for next object
						overlappingOccupancy = false;
						trainsInThisSubLineOccupancy.clear();
						occupancyStartTime = null;
						occupancyEndTime = null;
					}
				}

				subLinesOccupancies.put(subLinesTrackNumber, subLineOccupancies);
			}

			// Load occupancies back to segment
			segments.get(i).setSubLinesOccupancies(subLinesOccupancies);

			resultsMessage += "Found occupanices on "+subLinesOccupancies.size()+" tracks over "+segments.get(i).getLineName()+" line\n";
		}

		//  3.  Create Observed Windows by subtracting blockMustRemainOccupiedBeforeWindow time, blockMustEndInAdvanceOfTrain time, increment and decrement
		//  times from the inter-occupancy times.  Then compare this amount to the minimumWindowDuration parameter to see if it is a valid window.  This 
		//  will be done on a subLine (track) basis for each segment.  Unlike the reported values in the Bridge Analysis (which use ceiling functions and do not impact
		//  the values written to the spreadsheet), the floor/ceiling values in MOW analysis are used in determining whether a window meeting the minimum duration
		//  threshold and they can NEVER be zero. 

		final Integer blockUnoccupiedInAdvanceOfWindowSeconds = BIASMaintenanceWindowAnalysisConfigPageController.getBlockUnoccupiedInAdvanceOfWindowMinutes() * 60;
		final Integer windowMustEndSecondsInAdvanceOfTrain = BIASMaintenanceWindowAnalysisConfigPageController.getWindowMustEndMinutesInAdvanceOfTrain() * 60;
		final Integer incrementSeconds = BIASMaintenanceWindowAnalysisConfigPageController.getIncrementMinutes() * 60;
		final Integer decrementSeconds = BIASMaintenanceWindowAnalysisConfigPageController.getDecrementMinutes() * 60;
		final Integer minimumAcceptableDurationInSeconds = BIASMaintenanceWindowAnalysisConfigPageController.getMinimumWindowDurationMinutes() * 60;

		//  For each segment
		for (int i = 0; i < segments.size(); i++)
		{
			// Create a HashMap to store the subLine observed windows
			ArrayList<MaintenanceWindowAnalysisWindow> subLinesObservedWindows = new ArrayList<MaintenanceWindowAnalysisWindow>();

			HashMap<String, ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>> subLinesOccupancies = new HashMap<>();
			subLinesOccupancies.putAll(segments.get(i).getSubLinesOccupancies());
			Set<String> keySet = subLinesOccupancies.keySet();
			ArrayList<String> arrayListOfKeys = new ArrayList<String>(keySet);

			int validWindows = 0;

			// For each subLine
			for (int j = 0; j < keySet.size(); j++)
			{
				MaintenanceWindowAnalysisSubLineOccupancy currentSubLineObservedOccupancy = null;
				MaintenanceWindowAnalysisSubLineOccupancy nextSubLineObservedOccupancy = null;

				Integer currentSubLineObservedOccupancyFinishTimeInSeconds = null;
				Integer nextSubLineObservedOccupancyStartTimeInSeconds = null;
				Integer subLineWindowStartTimeInSeconds = null;
				Integer subLineWindowFinishTimeInSeconds = null;
				Integer subLineWindowDurationInSeconds = null;

				// Create ArrayList of subLineOccupancies from HashMap
				ArrayList<MaintenanceWindowAnalysisSubLineOccupancy> occupanciesInSubLine = subLinesOccupancies.get(arrayListOfKeys.get(j));

				// If no occupancies in this subLine then create a window from the analysis period start to analysis period end 
				if (occupanciesInSubLine.size() == 0)
				{
					MaintenanceWindowAnalysisWindow window = new MaintenanceWindowAnalysisWindow(endOfExclusionPeriodInSeconds, endOfAnalysisPeriodInSeconds, endOfExclusionPeriodInSeconds, endOfAnalysisPeriodInSeconds, arrayListOfKeys.get(j));
					subLinesObservedWindows.add(window);	
					validWindows++;	
				}
				else // else create windows between occupancies
				{
					// For each occupancy
					for (int k = 0; k < occupanciesInSubLine.size(); k++)
					{
						if (k == 0)  // First occupancy only 
						{
							// Try to create window from start of analysis period to the beginning of the first occupancy
							currentSubLineObservedOccupancyFinishTimeInSeconds = getBeginningOfAnalysisPeriodInSeconds();
							nextSubLineObservedOccupancy = occupanciesInSubLine.get(0);
							nextSubLineObservedOccupancyStartTimeInSeconds = nextSubLineObservedOccupancy.getOccupancyStartTimeInSeconds();

							// Account for blockUnoccupiedInAdvanceOfWindowSeconds and windowMustEndSecondsInAdvanceOfTrain
							subLineWindowStartTimeInSeconds = currentSubLineObservedOccupancyFinishTimeInSeconds;
							subLineWindowFinishTimeInSeconds = nextSubLineObservedOccupancyStartTimeInSeconds - windowMustEndSecondsInAdvanceOfTrain;

							// Account for ceiling (effectively delaying window start time) and floor (effectively making window end time earlier) functions 
							// See implementation as described in above section header comments 
							double subLineWindowStartTimeWithCeilingInSeconds = ConvertDateTime.ceilingToNearestIncrementOfSeconds(subLineWindowStartTimeInSeconds, incrementSeconds);
							double subLineWindowFinishTimeWithFloorInSeconds = ConvertDateTime.floorToNearestIncrementOfSeconds(subLineWindowFinishTimeInSeconds, decrementSeconds);

							// Window may never exceed end of analysis period
							subLineWindowFinishTimeWithFloorInSeconds = Math.min(subLineWindowFinishTimeWithFloorInSeconds, endOfAnalysisPeriodInSeconds);

							// Determine duration of window (considering floor and ceiling)
							subLineWindowDurationInSeconds = (int) subLineWindowFinishTimeWithFloorInSeconds - (int) subLineWindowStartTimeWithCeilingInSeconds;


							if (subLineWindowDurationInSeconds >=  minimumAcceptableDurationInSeconds)
							{
								MaintenanceWindowAnalysisWindow window = new MaintenanceWindowAnalysisWindow(currentSubLineObservedOccupancyFinishTimeInSeconds, nextSubLineObservedOccupancyStartTimeInSeconds, (int) subLineWindowStartTimeWithCeilingInSeconds, (int) subLineWindowFinishTimeWithFloorInSeconds, arrayListOfKeys.get(j));
								subLinesObservedWindows.add(window);	
								validWindows++;	
							}
						}

						// Start with time between occupancies
						currentSubLineObservedOccupancy = occupanciesInSubLine.get(k);
						currentSubLineObservedOccupancyFinishTimeInSeconds = currentSubLineObservedOccupancy.getOccupancyEndTimeInSeconds();
						if (k < occupanciesInSubLine.size() - 1)
						{
							nextSubLineObservedOccupancy = occupanciesInSubLine.get(k + 1);
							nextSubLineObservedOccupancyStartTimeInSeconds = nextSubLineObservedOccupancy.getOccupancyStartTimeInSeconds();
						}
						else
							nextSubLineObservedOccupancyStartTimeInSeconds = Integer.MAX_VALUE;		

						// Account for blockUnoccupiedInAdvanceOfWindowSeconds and windowMustEndSecondsInAdvanceOfTrain
						subLineWindowStartTimeInSeconds = currentSubLineObservedOccupancyFinishTimeInSeconds + blockUnoccupiedInAdvanceOfWindowSeconds;
						subLineWindowFinishTimeInSeconds = nextSubLineObservedOccupancyStartTimeInSeconds - windowMustEndSecondsInAdvanceOfTrain;

						// Account for ceiling (effectively delaying window start time) and floor (effectively making window end time earlier) functions 
						// See implementation as described in above section header comments 
						double subLineWindowStartTimeWithCeilingInSeconds = ConvertDateTime.ceilingToNearestIncrementOfSeconds(subLineWindowStartTimeInSeconds, incrementSeconds);
						double subLineWindowFinishTimeWithFloorInSeconds = ConvertDateTime.floorToNearestIncrementOfSeconds(subLineWindowFinishTimeInSeconds, decrementSeconds);

						// Window may never exceed end of analysis period
						subLineWindowFinishTimeWithFloorInSeconds = Math.min(subLineWindowFinishTimeWithFloorInSeconds, endOfAnalysisPeriodInSeconds);

						// Determine duration of window (considering floor and ceiling)
						subLineWindowDurationInSeconds = (int) subLineWindowFinishTimeWithFloorInSeconds - (int) subLineWindowStartTimeWithCeilingInSeconds;


						if (subLineWindowDurationInSeconds >=  minimumAcceptableDurationInSeconds)
						{
							MaintenanceWindowAnalysisWindow window = new MaintenanceWindowAnalysisWindow(currentSubLineObservedOccupancyFinishTimeInSeconds, nextSubLineObservedOccupancyStartTimeInSeconds, (int) subLineWindowStartTimeWithCeilingInSeconds, (int) subLineWindowFinishTimeWithFloorInSeconds, arrayListOfKeys.get(j));
							subLinesObservedWindows.add(window);	
							validWindows++;	
						}
					}
				}

				// Load windows back to segment
				segments.get(i).setSubLinesObservedWindows(subLinesObservedWindows);
			}
			resultsMessage += "Found "+validWindows+" observed maintenance windows on "+segments.get(i).getLineName()+"\n";
		}	

		//  4.  Create Proposed Maximized Windows by subtracting blockMustRemainOccupiedBeforeWindow time, blockMustEndInAdvanceOfTrain time, increment and decrement
		//  times from the inter-occupancy times.  Then compare this amount to the minimumWindowDuration parameter to see if it is a valid window.  

		//  For each segment
		for (int i = 0; i < segments.size(); i++)
		{
			// Create a HashMap to store the subLine observed windows
			HashMap<String, ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>> subLinesOccupancies = new HashMap<>();
			subLinesOccupancies.putAll(segments.get(i).getSubLinesOccupancies());
			Set<String> keySet = subLinesOccupancies.keySet();
			ArrayList<String> arrayListOfKeys = new ArrayList<String>(keySet);

			//  Create a final arrayList of all occupancies on each subLine within the segment for use as reference when trying to move occupancies to other tracks
			final ArrayList<ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>> subLineOccupanciesAsArrayReference = new ArrayList<>();

			// For each subLine (reference)
			for (int j = 0; j < keySet.size(); j++)
			{
				subLineOccupanciesAsArrayReference.add(new ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>());
				subLineOccupanciesAsArrayReference.get(j).addAll(subLinesOccupancies.get(arrayListOfKeys.get(j)));
			}

			// Subline to compare against
			for (int referenceArrayACounter = 0; referenceArrayACounter < keySet.size(); referenceArrayACounter++)
			{
				for (int referenceArrayBCounter = 0; referenceArrayBCounter < keySet.size(); referenceArrayBCounter++)
				{
					if (!arrayListOfKeys.get(referenceArrayACounter).equals(arrayListOfKeys.get(referenceArrayBCounter)))
					{
						//  Create proposed arrayLists of occupancies for proposed maximized windows
						//  A is always the line being potentially merged to
						//  B is always the line being potentially merged from
						ArrayList<MaintenanceWindowAnalysisSubLineOccupancy> proposedOccupancyArrayA = new ArrayList<>();
						ArrayList<MaintenanceWindowAnalysisSubLineOccupancy> proposedOccupancyArrayB = new ArrayList<>();

						//  Load proposedOccupancyArrayB
						for (int referenceArrayFCounter = 0; referenceArrayFCounter < subLineOccupanciesAsArrayReference.get(referenceArrayBCounter).size(); referenceArrayFCounter++)
						{
							proposedOccupancyArrayB.add(subLineOccupanciesAsArrayReference.get(referenceArrayBCounter).get(referenceArrayFCounter));
						}

						// Check each occupancy in referenceArrayA
						for (int referenceArrayCCounter = 0; referenceArrayCCounter < subLineOccupanciesAsArrayReference.get(referenceArrayACounter).size(); referenceArrayCCounter++)
						{
							Integer thisOccupancyEndA = subLineOccupanciesAsArrayReference.get(referenceArrayACounter).get(referenceArrayCCounter).getOccupancyEndTimeInSeconds();
							Integer nextOccupancyStartA = getEndOfAnalysisPeriodInSeconds();

							if (referenceArrayCCounter < subLineOccupanciesAsArrayReference.get(referenceArrayACounter).size() - 1)
							{
								nextOccupancyStartA = subLineOccupanciesAsArrayReference.get(referenceArrayACounter).get(referenceArrayCCounter + 1).getOccupancyStartTimeInSeconds();
							}

							// Add all occupancies in referenceArrayA to proposedOccupancyArrayA
							proposedOccupancyArrayA.add(subLineOccupanciesAsArrayReference.get(referenceArrayACounter).get(referenceArrayCCounter));

							// Check each occupancy in referenceArrayBCounter
							for (int referenceArrayDCounter = 0; referenceArrayDCounter < subLineOccupanciesAsArrayReference.get(referenceArrayBCounter).size(); referenceArrayDCounter++)
							{
								Integer proposedOccupancyStartB = subLineOccupanciesAsArrayReference.get(referenceArrayBCounter).get(referenceArrayDCounter).getOccupancyStartTimeInSeconds();
								Integer proposedOccupancyEndB = subLineOccupanciesAsArrayReference.get(referenceArrayBCounter).get(referenceArrayDCounter).getOccupancyEndTimeInSeconds();

								if ((proposedOccupancyStartB >= thisOccupancyEndA) && (proposedOccupancyEndB <= nextOccupancyStartA))
								{
									// Add mergable occupancies in referenceArrayB to proposedOccupancyArrayA
									proposedOccupancyArrayA.add(subLineOccupanciesAsArrayReference.get(referenceArrayBCounter).get(referenceArrayDCounter));

									// Then remove from proposedOccupancyArrayB
									proposedOccupancyArrayB.remove(subLineOccupanciesAsArrayReference.get(referenceArrayBCounter).get(referenceArrayDCounter));
								}
							}
						}

						// Prepare proposed occupancy objects for potential work windows 
						// Send over a hashmap for each proposal
						HashMap<String, ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>> proposal = new HashMap<String, ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>>();
						proposal.put(arrayListOfKeys.get(referenceArrayACounter), proposedOccupancyArrayA);
						proposal.put(arrayListOfKeys.get(referenceArrayBCounter), proposedOccupancyArrayB);
						segments.get(i).addToProposedOccupancies(proposal);	
					}
				}
			}

			// Create windows between occupancies for each subline in each proposal
			for (int proposalCounter = 0; proposalCounter < segments.get(i).getSubLinesProposedOccupancies().size(); proposalCounter++)
			{	
				Boolean successfulProposedWindow = false;
				ArrayList<MaintenanceWindowAnalysisWindow> allWindowsOnAllSublinesForOneProposal = new ArrayList<MaintenanceWindowAnalysisWindow>();

				// For each subline
				for (int j = 0; j < keySet.size(); j++)
				{
					ArrayList<MaintenanceWindowAnalysisSubLineOccupancy> occupanciesInSubLine = segments.get(i).getSubLinesProposedOccupancies().get(proposalCounter).get(arrayListOfKeys.get(j));

					MaintenanceWindowAnalysisSubLineOccupancy currentSubLineProposedOccupancy = null;
					MaintenanceWindowAnalysisSubLineOccupancy nextSubLineProposedOccupancy = null;

					Integer currentSubLineProposedOccupancyFinishTimeInSeconds = null;
					Integer nextSubLineProposedOccupancyStartTimeInSeconds = null;
					Integer subLineWindowStartTimeInSeconds = null;
					Integer subLineWindowFinishTimeInSeconds = null;
					Integer subLineWindowDurationInSeconds = null;

					// For each occupancy
					// If no occupancies in this subLine then create a window from the analysis period start to analysis period end 
					if (occupanciesInSubLine.size() == 0)
					{
						MaintenanceWindowAnalysisWindow window = new MaintenanceWindowAnalysisWindow(endOfExclusionPeriodInSeconds, endOfAnalysisPeriodInSeconds, endOfExclusionPeriodInSeconds, endOfAnalysisPeriodInSeconds, arrayListOfKeys.get(j));
						allWindowsOnAllSublinesForOneProposal.add(window);
					}
					else
					{
						for (int k = 0; k < occupanciesInSubLine.size(); k++)
						{
							if (k == 0)  // First occupancy only 
							{
								// Try to create window from start of analysis period to the beginning of the first occupancy
								currentSubLineProposedOccupancyFinishTimeInSeconds = getBeginningOfAnalysisPeriodInSeconds();
								nextSubLineProposedOccupancy = occupanciesInSubLine.get(0);
								nextSubLineProposedOccupancyStartTimeInSeconds = nextSubLineProposedOccupancy.getOccupancyStartTimeInSeconds();

								// Account for blockUnoccupiedInAdvanceOfWindowSeconds and windowMustEndSecondsInAdvanceOfTrain
								subLineWindowStartTimeInSeconds = currentSubLineProposedOccupancyFinishTimeInSeconds;
								subLineWindowFinishTimeInSeconds = nextSubLineProposedOccupancyStartTimeInSeconds - windowMustEndSecondsInAdvanceOfTrain;

								// Account for ceiling (effectively delaying window start time) and floor (effectively making window end time earlier) functions 
								// See implementation as described in above section header comments 
								double subLineWindowStartTimeWithCeilingInSeconds = ConvertDateTime.ceilingToNearestIncrementOfSeconds(subLineWindowStartTimeInSeconds, incrementSeconds);
								double subLineWindowFinishTimeWithFloorInSeconds = ConvertDateTime.floorToNearestIncrementOfSeconds(subLineWindowFinishTimeInSeconds, decrementSeconds);

								// Window may never exceed end of analysis period
								subLineWindowFinishTimeWithFloorInSeconds = Math.min(subLineWindowFinishTimeWithFloorInSeconds, endOfAnalysisPeriodInSeconds);

								// Determine duration of window (considering floor and ceiling)
								subLineWindowDurationInSeconds = (int) subLineWindowFinishTimeWithFloorInSeconds - (int) subLineWindowStartTimeWithCeilingInSeconds;


								if (subLineWindowDurationInSeconds >=  minimumAcceptableDurationInSeconds)
								{
									MaintenanceWindowAnalysisWindow window = new MaintenanceWindowAnalysisWindow(currentSubLineProposedOccupancyFinishTimeInSeconds, nextSubLineProposedOccupancyStartTimeInSeconds, (int) subLineWindowStartTimeWithCeilingInSeconds, (int) subLineWindowFinishTimeWithFloorInSeconds, arrayListOfKeys.get(j));
									allWindowsOnAllSublinesForOneProposal.add(window);	
								}
							}

							// Start with time between occupancies
							currentSubLineProposedOccupancy = occupanciesInSubLine.get(k);
							currentSubLineProposedOccupancyFinishTimeInSeconds = currentSubLineProposedOccupancy.getOccupancyEndTimeInSeconds();
							if (k < occupanciesInSubLine.size() - 1)
							{
								nextSubLineProposedOccupancy = occupanciesInSubLine.get(k + 1);
								nextSubLineProposedOccupancyStartTimeInSeconds = nextSubLineProposedOccupancy.getOccupancyStartTimeInSeconds();
							}
							else
								nextSubLineProposedOccupancyStartTimeInSeconds = Integer.MAX_VALUE;		

							// Account for blockUnoccupiedInAdvanceOfWindowSeconds and windowMustEndSecondsInAdvanceOfTrain
							subLineWindowStartTimeInSeconds = currentSubLineProposedOccupancyFinishTimeInSeconds + blockUnoccupiedInAdvanceOfWindowSeconds;
							subLineWindowFinishTimeInSeconds = nextSubLineProposedOccupancyStartTimeInSeconds - windowMustEndSecondsInAdvanceOfTrain;

							// Account for ceiling (effectively delaying window start time) and floor (effectively making window end time earlier) functions 
							// See implementation as described in above section header comments 
							double subLineWindowStartTimeWithCeilingInSeconds = ConvertDateTime.ceilingToNearestIncrementOfSeconds(subLineWindowStartTimeInSeconds, incrementSeconds);
							double subLineWindowFinishTimeWithFloorInSeconds = ConvertDateTime.floorToNearestIncrementOfSeconds(subLineWindowFinishTimeInSeconds, decrementSeconds);

							// Window may never exceed end of analysis period
							subLineWindowFinishTimeWithFloorInSeconds = Math.min(subLineWindowFinishTimeWithFloorInSeconds, endOfAnalysisPeriodInSeconds);

							// Determine duration of window (considering floor and ceiling)
							subLineWindowDurationInSeconds = (int) subLineWindowFinishTimeWithFloorInSeconds - (int) subLineWindowStartTimeWithCeilingInSeconds;

							if (subLineWindowDurationInSeconds >=  minimumAcceptableDurationInSeconds)
							{
								successfulProposedWindow = true;
								MaintenanceWindowAnalysisWindow window = new MaintenanceWindowAnalysisWindow(currentSubLineProposedOccupancyFinishTimeInSeconds, nextSubLineProposedOccupancyStartTimeInSeconds, (int) subLineWindowStartTimeWithCeilingInSeconds, (int) subLineWindowFinishTimeWithFloorInSeconds, arrayListOfKeys.get(j));
								allWindowsOnAllSublinesForOneProposal.add(window);	
							}
						}
					}
				}

				//  Send back to segment (sending one proposal's windows for all tracks) 
				if (successfulProposedWindow)
					segments.get(i).addToProposedWindows(allWindowsOnAllSublinesForOneProposal);
			}
			resultsMessage += "Found "+segments.get(i).getSubLinesProposedWindows().size()+" alternate proposals for maintenance windows on "+segments.get(i).getLineName()+"\n";
		}

		resultsMessage += "Finished creating maintenance window results at "+ConvertDateTime.getTimeStamp()+"\n";
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}

	public static Integer getBeginningOfAnalysisPeriodInSeconds()
	{
		return endOfExclusionPeriodInSeconds;
	}

	public static Integer getEndOfAnalysisPeriodInSeconds()
	{
		return endOfAnalysisPeriodInSeconds;
	}

	class TimeSorter implements Comparator<MaintenanceWindowAnalysisTrainTraversal> 
	{ 
		@Override
		public int compare(MaintenanceWindowAnalysisTrainTraversal o1, MaintenanceWindowAnalysisTrainTraversal o2) 
		{	    
			Integer x1 = o1.getEntryNodeOSSeconds();
			Integer x2 = o2.getEntryNodeOSSeconds();

			int sComp = x1.compareTo(x2);
			if (sComp != 0) 
			{
				return sComp;
			} 

			x1 = o1.getEntryNodeOSSeconds();
			x2 = o2.getEntryNodeOSSeconds();

			return x1.compareTo(x2);
		}
	}
}