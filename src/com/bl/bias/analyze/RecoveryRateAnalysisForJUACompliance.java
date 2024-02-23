package com.bl.bias.analyze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.bl.bias.app.BIASRecoveryRateAnalysisConfigController;
import com.bl.bias.objects.RecoveryRateAssessment;
import com.bl.bias.objects.TrainAssessment;
import com.bl.bias.tools.ConvertDateTime;

public class RecoveryRateAnalysisForJUACompliance 
{
	private static String resultsMessage;

	private static ArrayList<String> groupsToAnalyzeSetA = new ArrayList<String>();
	private static ArrayList<String> groupsToAnalyzeSetB = new ArrayList<String>();
	private static ArrayList<String> groupsToAnalyzeSetC = new ArrayList<String>();
	private static ArrayList<String> groupsToAnalyzeSetD = new ArrayList<String>();
	private static ArrayList<String> nodePairsToAnalyzeSetA = new ArrayList<String>();
	private static ArrayList<String> nodePairsToAnalyzeSetB = new ArrayList<String>();
	private static ArrayList<String> nodePairsToAnalyzeSetC = new ArrayList<String>();
	private static ArrayList<String> nodePairsToAnalyzeSetD = new ArrayList<String>();
	private static ArrayList<TrainAssessment> trainsReadInSetA = new ArrayList<TrainAssessment>();
	private static ArrayList<TrainAssessment> trainsReadInSetB = new ArrayList<TrainAssessment>();
	private static ArrayList<TrainAssessment> trainsReadInSetC = new ArrayList<TrainAssessment>();
	private static ArrayList<TrainAssessment> trainsReadInSetD = new ArrayList<TrainAssessment>();

	private Integer recoveryRatesCalculated = 0;

	private static Boolean debug = false;

	public RecoveryRateAnalysisForJUACompliance(ArrayList<TrainAssessment> trainsReadIn, HashMap<String, String> trainToGroupAssignment, HashMap<String, String> trainToTypeAssignment) 
	{
		resultsMessage = "Started analyzing recovery rates at "+ConvertDateTime.getTimeStamp()+"\n";

		// Add groups from Set A Recovery Rate Analysis config
		groupsToAnalyzeSetA.clear();
		for (int i = 0; i < BIASRecoveryRateAnalysisConfigController.getSetARecoveryRateAnalysisTrainGroups().split(",").length; i++)
		{
			groupsToAnalyzeSetA.add(BIASRecoveryRateAnalysisConfigController.getSetARecoveryRateAnalysisTrainGroups().split(",")[i]);
		}

		// Add groups from Set B Recovery Rate Analysis config
		groupsToAnalyzeSetB.clear();
		for (int i = 0; i < BIASRecoveryRateAnalysisConfigController.getSetBRecoveryRateAnalysisTrainGroups().split(",").length; i++)
		{
			groupsToAnalyzeSetB.add(BIASRecoveryRateAnalysisConfigController.getSetBRecoveryRateAnalysisTrainGroups().split(",")[i]);
		}

		// Add groups from Set C Recovery Rate Analysis config
		groupsToAnalyzeSetC.clear();
		for (int i = 0; i < BIASRecoveryRateAnalysisConfigController.getSetCRecoveryRateAnalysisTrainGroups().split(",").length; i++)
		{
			groupsToAnalyzeSetC.add(BIASRecoveryRateAnalysisConfigController.getSetCRecoveryRateAnalysisTrainGroups().split(",")[i]);
		}

		// Add groups from Set D Recovery Rate Analysis config
		groupsToAnalyzeSetD.clear();
		for (int i = 0; i < BIASRecoveryRateAnalysisConfigController.getSetDRecoveryRateAnalysisTrainGroups().split(",").length; i++)
		{
			groupsToAnalyzeSetD.add(BIASRecoveryRateAnalysisConfigController.getSetDRecoveryRateAnalysisTrainGroups().split(",")[i]);
		}

		// Add node pairs from Set A Recovery Rate Analysis config
		nodePairsToAnalyzeSetA.clear();
		if (BIASRecoveryRateAnalysisConfigController.getSetARecoveryRateAnalysisNodePairs() != null)
		{
			for (int i = 0; i < BIASRecoveryRateAnalysisConfigController.getSetARecoveryRateAnalysisNodePairs().split(",").length; i++)
			{
				nodePairsToAnalyzeSetA.add(BIASRecoveryRateAnalysisConfigController.getSetARecoveryRateAnalysisNodePairs().split(",")[i]);
			}
		}

		// Add node pairs from Set B Recovery Rate Analysis config
		nodePairsToAnalyzeSetB.clear();
		if (BIASRecoveryRateAnalysisConfigController.getSetBRecoveryRateAnalysisNodePairs() != null)
		{
			for (int i = 0; i < BIASRecoveryRateAnalysisConfigController.getSetBRecoveryRateAnalysisNodePairs().split(",").length; i++)
			{
				nodePairsToAnalyzeSetB.add(BIASRecoveryRateAnalysisConfigController.getSetBRecoveryRateAnalysisNodePairs().split(",")[i]);
			}
		}

		// Add node pairs from Set C Recovery Rate Analysis config
		nodePairsToAnalyzeSetC.clear();
		if (BIASRecoveryRateAnalysisConfigController.getSetCRecoveryRateAnalysisNodePairs() != null)
		{
			for (int i = 0; i < BIASRecoveryRateAnalysisConfigController.getSetCRecoveryRateAnalysisNodePairs().split(",").length; i++)
			{
				nodePairsToAnalyzeSetC.add(BIASRecoveryRateAnalysisConfigController.getSetCRecoveryRateAnalysisNodePairs().split(",")[i]);
			}
		}

		// Add node pairs from Set D Recovery Rate Analysis config
		nodePairsToAnalyzeSetD.clear();
		if (BIASRecoveryRateAnalysisConfigController.getSetDRecoveryRateAnalysisNodePairs() != null)
		{
			for (int i = 0; i < BIASRecoveryRateAnalysisConfigController.getSetDRecoveryRateAnalysisNodePairs().split(",").length; i++)
			{
				nodePairsToAnalyzeSetD.add(BIASRecoveryRateAnalysisConfigController.getSetDRecoveryRateAnalysisNodePairs().split(",")[i]);
			}
		}

		// Assign all trains from Read class to sets A, B, C and/or D
		trainsReadInSetA.clear();
		trainsReadInSetB.clear();
		trainsReadInSetC.clear();
		trainsReadInSetD.clear();
		String aNodeSetA = "";
		String bNodeSetA = "";
		String aNodeSetB = "";
		String bNodeSetB = "";
		String aNodeSetC = "";
		String bNodeSetC = "";
		String aNodeSetD = "";
		String bNodeSetD = "";

		for (int i = 0; i < trainsReadIn.size(); i++)
		{
			HashSet<String> allScheduledNodesInTrainsRoute = new HashSet<String>();
			for (int j = 0; j < trainsReadIn.get(i).getRouteEntries().size(); j++)
			{
				allScheduledNodesInTrainsRoute.add(trainsReadIn.get(i).getRouteEntries().get(j).getNode().trim());
			}

			for (int k = 0; k < nodePairsToAnalyzeSetA.size(); k++)
			{
				aNodeSetA = nodePairsToAnalyzeSetA.get(k).split(":")[0];
				bNodeSetA = nodePairsToAnalyzeSetA.get(k).split(":")[1];

				if ((allScheduledNodesInTrainsRoute.contains(aNodeSetA))  // Node A of node pair
						&& (allScheduledNodesInTrainsRoute.contains(bNodeSetA)) // Node B of node pair
						&& (groupsToAnalyzeSetA.contains(trainsReadIn.get(i).getTrainGroupAbbreviation()))) // Group
				{
					trainsReadInSetA.add(trainsReadIn.get(i));
					break;
				}
			}

			for (int k = 0; k < nodePairsToAnalyzeSetB.size(); k++)
			{
				aNodeSetB = nodePairsToAnalyzeSetB.get(k).split(":")[0];
				bNodeSetB = nodePairsToAnalyzeSetB.get(k).split(":")[1];

				if ((allScheduledNodesInTrainsRoute.contains(aNodeSetB))  // Node A of node pair
						&& (allScheduledNodesInTrainsRoute.contains(bNodeSetB)) // Node B of node pair
						&& (groupsToAnalyzeSetB.contains(trainsReadIn.get(i).getTrainGroupAbbreviation()))) // Group
				{
					trainsReadInSetB.add(trainsReadIn.get(i));
					break;
				}
			}

			for (int k = 0; k < nodePairsToAnalyzeSetC.size(); k++)
			{
				aNodeSetC = nodePairsToAnalyzeSetC.get(k).split(":")[0];
				bNodeSetC = nodePairsToAnalyzeSetC.get(k).split(":")[1];

				if ((allScheduledNodesInTrainsRoute.contains(aNodeSetC))  // Node A of node pair
						&& (allScheduledNodesInTrainsRoute.contains(bNodeSetC)) // Node B of node pair
						&& (groupsToAnalyzeSetC.contains(trainsReadIn.get(i).getTrainGroupAbbreviation()))) // Group
				{
					trainsReadInSetC.add(trainsReadIn.get(i));
					break;
				}
			}

			for (int k = 0; k < nodePairsToAnalyzeSetD.size(); k++)
			{
				aNodeSetD = nodePairsToAnalyzeSetD.get(k).split(":")[0];
				bNodeSetD = nodePairsToAnalyzeSetD.get(k).split(":")[1];

				if ((allScheduledNodesInTrainsRoute.contains(aNodeSetD))  // Node A of node pair
						&& (allScheduledNodesInTrainsRoute.contains(bNodeSetD)) // Node B of node pair
						&& (groupsToAnalyzeSetD.contains(trainsReadIn.get(i).getTrainGroupAbbreviation()))) // Group
				{
					trainsReadInSetD.add(trainsReadIn.get(i));
					break;
				}
			}
		}

		// Set A
		// For each train
		for (int i = 0; i < trainsReadInSetA.size(); i++)
		{
			for (int j = 0; j < nodePairsToAnalyzeSetA.size(); j++)
			{
				aNodeSetA = nodePairsToAnalyzeSetA.get(j).split(":")[0];
				bNodeSetA = nodePairsToAnalyzeSetA.get(j).split(":")[1];

				String aNodeArrivalTimeBySchedule = "";
				String aNodeArrivalTimeBySimulation = "";
				String aNodeDepartureTimeBySchedule = "";
				String aNodeDepartureTimeBySimulation = "";

				String bNodeArrivalTimeBySchedule = "";
				String bNodeArrivalTimeBySimulation = "";
				String bNodeDepartureTimeBySchedule = "";
				String bNodeDepartureTimeBySimulation = "";

				Double aNodeCumulativeDistance = 0.0;
				Double bNodeCumulativeDistance = 0.0;
				Integer aNodeRtcIncrement = 0;
				Integer bNodeRtcIncrement = 0;
				Integer dwellEventCumulativeTimeInSeconds = 0;
				Integer dwellEventOffsetCumulativeTimeInSeconds = 0;
				Integer waitOnScheduleCumulativeTimeInSeconds = 0;
				Boolean dwellEventBetweenAAndBNodesBoolean = false;
				Boolean waitOnScheduleBetweenAAndBNodesBoolean = false;

				Boolean validANodeFound = false;
				Boolean validBNodeFound = false;

				for (int k = 0; k < trainsReadInSetA.get(i).getRouteEntries().size(); k++)
				{
					if (trainsReadInSetA.get(i).getRouteEntries().get(k).getNode().equals(aNodeSetA)) 
					{
						aNodeArrivalTimeBySchedule = trainsReadInSetA.get(i).getRouteEntries().get(k).getScheduledArrivalTimeAsString();
						aNodeArrivalTimeBySimulation = trainsReadInSetA.get(i).getRouteEntries().get(k).getSimulatedArrivalTimeAsString();
						aNodeDepartureTimeBySchedule = trainsReadInSetA.get(i).getRouteEntries().get(k).getScheduledDepartureTimeAsString();
						aNodeDepartureTimeBySimulation = trainsReadInSetA.get(i).getRouteEntries().get(k).getSimulatedDepartureTimeAsString();
						aNodeCumulativeDistance =  trainsReadInSetA.get(i).getRouteEntries().get(k).getCumulativeDistance();
						aNodeRtcIncrement =  trainsReadInSetA.get(i).getRouteEntries().get(k).getRtcIncrement();
						validANodeFound = true;
					}
					else if ((trainsReadInSetA.get(i).getRouteEntries().get(k).getNode().equals(bNodeSetA)) && (validANodeFound))
					{
						bNodeArrivalTimeBySchedule = trainsReadInSetA.get(i).getRouteEntries().get(k).getScheduledArrivalTimeAsString();
						bNodeArrivalTimeBySimulation = trainsReadInSetA.get(i).getRouteEntries().get(k).getSimulatedArrivalTimeAsString();
						bNodeDepartureTimeBySchedule = trainsReadInSetA.get(i).getRouteEntries().get(k).getScheduledDepartureTimeAsString();
						bNodeDepartureTimeBySimulation = trainsReadInSetA.get(i).getRouteEntries().get(k).getSimulatedDepartureTimeAsString();
						bNodeCumulativeDistance =  trainsReadInSetA.get(i).getRouteEntries().get(k).getCumulativeDistance();
						bNodeRtcIncrement =  trainsReadInSetA.get(i).getRouteEntries().get(k).getRtcIncrement();
						validBNodeFound = true;
						break;
					}
					else
					{	
						if ((!trainsReadInSetA.get(i).getRouteEntries().get(k).getMinimumDwellTimeAsString().equals("0")) 
								&& (trainsReadInSetA.get(i).getRouteEntries().get(k).getRtcIncrement() > aNodeRtcIncrement)
								&& (aNodeRtcIncrement != 0))
						{
							dwellEventOffsetCumulativeTimeInSeconds += BIASRecoveryRateAnalysisConfigController.getSetAScheduleImprecisionOffsetInSeconds();
							dwellEventCumulativeTimeInSeconds += ConvertDateTime.convertDDHHMMSSStringToSeconds(trainsReadInSetA.get(i).getRouteEntries().get(k).getMinimumDwellTimeAsString()); 
							dwellEventBetweenAAndBNodesBoolean = true;
						}

						if ((!trainsReadInSetA.get(i).getRouteEntries().get(k).getWaitOnScheduleAsString().equals("0")) 
								&& (trainsReadInSetA.get(i).getRouteEntries().get(k).getRtcIncrement() > aNodeRtcIncrement)
								&& (aNodeRtcIncrement != 0))
						{
							waitOnScheduleCumulativeTimeInSeconds += ConvertDateTime.convertDDHHMMSSStringToSeconds(trainsReadInSetA.get(i).getRouteEntries().get(k).getWaitOnScheduleAsString()); 
							waitOnScheduleBetweenAAndBNodesBoolean = true;
						}
					}
				}

				if ((validANodeFound) && (validBNodeFound))
				{
					//  Valid computation found
					String scheduleTimeToUseAtNodeA;
					String scheduleTimeToUseAtNodeB;

					if (aNodeDepartureTimeBySchedule == "")
						scheduleTimeToUseAtNodeA = aNodeArrivalTimeBySchedule;
					else
						scheduleTimeToUseAtNodeA = aNodeDepartureTimeBySchedule;

					if (bNodeArrivalTimeBySchedule == "")
						scheduleTimeToUseAtNodeB = bNodeDepartureTimeBySchedule;
					else
						scheduleTimeToUseAtNodeB = bNodeArrivalTimeBySchedule;

					Integer runTimeByScheduleAsSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(scheduleTimeToUseAtNodeB) - ConvertDateTime.convertDDHHMMSSStringToSeconds(scheduleTimeToUseAtNodeA);
					Integer runTimeBySimulationAsSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(bNodeArrivalTimeBySimulation) - ConvertDateTime.convertDDHHMMSSStringToSeconds(aNodeDepartureTimeBySimulation);

					Double distanceCovered = bNodeCumulativeDistance - aNodeCumulativeDistance;

					while (runTimeByScheduleAsSeconds < 0)
						runTimeByScheduleAsSeconds += 86400;

					// Create Recovery Rate Assessment object
					if (debug)
						System.out.println("Set A: Train "+trainsReadInSetA.get(i).getTrainSymbol()+" of group "+trainsReadInSetA.get(i).getTrainGroupAbbreviation()+" with A node of "+aNodeSetA+" and B node of "+bNodeSetA+" has created a recovery assessment object.");
					RecoveryRateAssessment assessmentSetA = new RecoveryRateAssessment("A", aNodeSetA, bNodeSetA, runTimeByScheduleAsSeconds, runTimeBySimulationAsSeconds, dwellEventBetweenAAndBNodesBoolean, dwellEventCumulativeTimeInSeconds, dwellEventOffsetCumulativeTimeInSeconds, waitOnScheduleBetweenAAndBNodesBoolean, waitOnScheduleCumulativeTimeInSeconds, distanceCovered);
					trainsReadInSetA.get(i).setRecoveryRateAssesment(assessmentSetA);
					trainsReadInSetA.get(i).setTrainHasRecoveryRatesCalculated();
					recoveryRatesCalculated++;
				}
			}
		}

		// Set B
		// For each train
		for (int i = 0; i < trainsReadInSetB.size(); i++)
		{
			for (int j = 0; j < nodePairsToAnalyzeSetB.size(); j++)
			{
				aNodeSetB = nodePairsToAnalyzeSetB.get(j).split(":")[0];
				bNodeSetB = nodePairsToAnalyzeSetB.get(j).split(":")[1];

				String aNodeArrivalTimeBySchedule = "";
				String aNodeArrivalTimeBySimulation = "";
				String aNodeDepartureTimeBySchedule = "";
				String aNodeDepartureTimeBySimulation = "";

				String bNodeArrivalTimeBySchedule = "";
				String bNodeArrivalTimeBySimulation = "";
				String bNodeDepartureTimeBySchedule = "";
				String bNodeDepartureTimeBySimulation = "";

				Double aNodeCumulativeDistance = 0.0;
				Double bNodeCumulativeDistance = 0.0;
				Integer aNodeRtcIncrement = 0;
				Integer bNodeRtcIncrement = 0;
				Integer dwellEventCumulativeTimeInSeconds = 0;
				Integer dwellEventOffsetCumulativeTimeInSeconds = 0;
				Integer waitOnScheduleCumulativeTimeInSeconds = 0;
				Boolean dwellEventBetweenAAndBNodesBoolean = false;
				Boolean waitOnScheduleBetweenAAndBNodesBoolean = false;

				Boolean validANodeFound = false;
				Boolean validBNodeFound = false;

				for (int k = 0; k < trainsReadInSetB.get(i).getRouteEntries().size(); k++)
				{
					if (trainsReadInSetB.get(i).getRouteEntries().get(k).getNode().equals(aNodeSetB))
					{
						aNodeArrivalTimeBySchedule = trainsReadInSetB.get(i).getRouteEntries().get(k).getScheduledArrivalTimeAsString();
						aNodeArrivalTimeBySimulation = trainsReadInSetB.get(i).getRouteEntries().get(k).getSimulatedArrivalTimeAsString();
						aNodeDepartureTimeBySchedule = trainsReadInSetB.get(i).getRouteEntries().get(k).getScheduledDepartureTimeAsString();
						aNodeDepartureTimeBySimulation = trainsReadInSetB.get(i).getRouteEntries().get(k).getSimulatedDepartureTimeAsString();
						aNodeCumulativeDistance =  trainsReadInSetB.get(i).getRouteEntries().get(k).getCumulativeDistance();
						aNodeRtcIncrement =  trainsReadInSetB.get(i).getRouteEntries().get(k).getRtcIncrement();
						validANodeFound = true;
					}
					else if ((trainsReadInSetB.get(i).getRouteEntries().get(k).getNode().equals(bNodeSetB)) && (validANodeFound))
					{
						bNodeArrivalTimeBySchedule = trainsReadInSetB.get(i).getRouteEntries().get(k).getScheduledArrivalTimeAsString();
						bNodeArrivalTimeBySimulation = trainsReadInSetB.get(i).getRouteEntries().get(k).getSimulatedArrivalTimeAsString();
						bNodeDepartureTimeBySchedule = trainsReadInSetB.get(i).getRouteEntries().get(k).getScheduledDepartureTimeAsString();
						bNodeDepartureTimeBySimulation = trainsReadInSetB.get(i).getRouteEntries().get(k).getSimulatedDepartureTimeAsString();
						bNodeCumulativeDistance =  trainsReadInSetB.get(i).getRouteEntries().get(k).getCumulativeDistance();
						bNodeRtcIncrement =  trainsReadInSetB.get(i).getRouteEntries().get(k).getRtcIncrement();
						validBNodeFound = true;
						break;
					}
					else
					{	
						if ((!trainsReadInSetB.get(i).getRouteEntries().get(k).getMinimumDwellTimeAsString().equals("0")) 
								&& (trainsReadInSetB.get(i).getRouteEntries().get(k).getRtcIncrement() > aNodeRtcIncrement)
								&& (aNodeRtcIncrement != 0))
						{
							dwellEventOffsetCumulativeTimeInSeconds += BIASRecoveryRateAnalysisConfigController.getSetBScheduleImprecisionOffsetInSeconds();
							dwellEventCumulativeTimeInSeconds += ConvertDateTime.convertDDHHMMSSStringToSeconds(trainsReadInSetB.get(i).getRouteEntries().get(k).getMinimumDwellTimeAsString()); 
							dwellEventBetweenAAndBNodesBoolean = true;
						}

						if ((!trainsReadInSetB.get(i).getRouteEntries().get(k).getWaitOnScheduleAsString().equals("0")) 
								&& (trainsReadInSetB.get(i).getRouteEntries().get(k).getRtcIncrement() > aNodeRtcIncrement)
								&& (aNodeRtcIncrement != 0))
						{
							waitOnScheduleCumulativeTimeInSeconds += ConvertDateTime.convertDDHHMMSSStringToSeconds(trainsReadInSetB.get(i).getRouteEntries().get(k).getWaitOnScheduleAsString()); 
							waitOnScheduleBetweenAAndBNodesBoolean = true;
						}
					}
				}

				if ((validANodeFound) && (validBNodeFound))
				{	
					//  Valid computation found
					String scheduleTimeToUseAtNodeA;
					String scheduleTimeToUseAtNodeB;

					if (aNodeDepartureTimeBySchedule == "")
						scheduleTimeToUseAtNodeA = aNodeArrivalTimeBySchedule;
					else
						scheduleTimeToUseAtNodeA = aNodeDepartureTimeBySchedule;

					if (bNodeArrivalTimeBySchedule == "")
						scheduleTimeToUseAtNodeB = bNodeDepartureTimeBySchedule;
					else
						scheduleTimeToUseAtNodeB = bNodeArrivalTimeBySchedule;


					Integer runTimeByScheduleAsSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(scheduleTimeToUseAtNodeB) - ConvertDateTime.convertDDHHMMSSStringToSeconds(scheduleTimeToUseAtNodeA);
					Integer runTimeBySimulationAsSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(bNodeArrivalTimeBySimulation) - ConvertDateTime.convertDDHHMMSSStringToSeconds(aNodeDepartureTimeBySimulation);

					Double distanceCovered = bNodeCumulativeDistance - aNodeCumulativeDistance;

					while (runTimeByScheduleAsSeconds < 0)
						runTimeByScheduleAsSeconds += 86400;

					// Create Recovery Rate Assessment object
					if (debug)
						System.out.println("Set B: Train "+trainsReadInSetB.get(i).getTrainSymbol()+" of group "+trainsReadInSetB.get(i).getTrainGroupAbbreviation()+" with A node of "+aNodeSetB+" and B node of "+bNodeSetB+" has created a recovery assessment object.");
					RecoveryRateAssessment assessmentSetB = new RecoveryRateAssessment("B", aNodeSetB, bNodeSetB, runTimeByScheduleAsSeconds, runTimeBySimulationAsSeconds, dwellEventBetweenAAndBNodesBoolean, dwellEventCumulativeTimeInSeconds, dwellEventOffsetCumulativeTimeInSeconds, waitOnScheduleBetweenAAndBNodesBoolean, waitOnScheduleCumulativeTimeInSeconds, distanceCovered);
					trainsReadInSetB.get(i).setRecoveryRateAssesment(assessmentSetB);
					trainsReadInSetB.get(i).setTrainHasRecoveryRatesCalculated();
					recoveryRatesCalculated++;
				}
			}
		}

		// Set C
		// For each train
		for (int i = 0; i < trainsReadInSetC.size(); i++)
		{
			for (int j = 0; j < nodePairsToAnalyzeSetC.size(); j++)
			{
				aNodeSetC = nodePairsToAnalyzeSetC.get(j).split(":")[0];
				bNodeSetC = nodePairsToAnalyzeSetC.get(j).split(":")[1];

				String aNodeArrivalTimeBySchedule = "";
				String aNodeArrivalTimeBySimulation = "";
				String aNodeDepartureTimeBySchedule = "";
				String aNodeDepartureTimeBySimulation = "";

				String bNodeArrivalTimeBySchedule = "";
				String bNodeArrivalTimeBySimulation = "";
				String bNodeDepartureTimeBySchedule = "";
				String bNodeDepartureTimeBySimulation = "";

				Double aNodeCumulativeDistance = 0.0;
				Double bNodeCumulativeDistance = 0.0;
				Integer aNodeRtcIncrement = 0;
				Integer bNodeRtcIncrement = 0;
				Integer dwellEventCumulativeTimeInSeconds = 0;
				Integer dwellEventOffsetCumulativeTimeInSeconds = 0;
				Integer waitOnScheduleCumulativeTimeInSeconds = 0;
				Boolean dwellEventBetweenAAndBNodesBoolean = false;
				Boolean waitOnScheduleBetweenAAndBNodesBoolean = false;

				Boolean validANodeFound = false;
				Boolean validBNodeFound = false;

				for (int k = 0; k < trainsReadInSetC.get(i).getRouteEntries().size(); k++)
				{
					if (trainsReadInSetC.get(i).getRouteEntries().get(k).getNode().equals(aNodeSetC))
					{
						aNodeArrivalTimeBySchedule = trainsReadInSetC.get(i).getRouteEntries().get(k).getScheduledArrivalTimeAsString();
						aNodeArrivalTimeBySimulation = trainsReadInSetC.get(i).getRouteEntries().get(k).getSimulatedArrivalTimeAsString();
						aNodeDepartureTimeBySchedule = trainsReadInSetC.get(i).getRouteEntries().get(k).getScheduledDepartureTimeAsString();
						aNodeDepartureTimeBySimulation = trainsReadInSetC.get(i).getRouteEntries().get(k).getSimulatedDepartureTimeAsString();
						aNodeCumulativeDistance =  trainsReadInSetC.get(i).getRouteEntries().get(k).getCumulativeDistance();
						aNodeRtcIncrement =  trainsReadInSetC.get(i).getRouteEntries().get(k).getRtcIncrement();
						validANodeFound = true;
					}
					else if ((trainsReadInSetC.get(i).getRouteEntries().get(k).getNode().equals(bNodeSetC)) && (validANodeFound))
					{
						bNodeArrivalTimeBySchedule = trainsReadInSetC.get(i).getRouteEntries().get(k).getScheduledArrivalTimeAsString();
						bNodeArrivalTimeBySimulation = trainsReadInSetC.get(i).getRouteEntries().get(k).getSimulatedArrivalTimeAsString();
						bNodeDepartureTimeBySchedule = trainsReadInSetC.get(i).getRouteEntries().get(k).getScheduledDepartureTimeAsString();
						bNodeDepartureTimeBySimulation = trainsReadInSetC.get(i).getRouteEntries().get(k).getSimulatedDepartureTimeAsString();
						bNodeCumulativeDistance =  trainsReadInSetC.get(i).getRouteEntries().get(k).getCumulativeDistance();
						bNodeRtcIncrement =  trainsReadInSetC.get(i).getRouteEntries().get(k).getRtcIncrement();
						validBNodeFound = true;
						break;
					}
					else
					{	
						if ((!trainsReadInSetC.get(i).getRouteEntries().get(k).getMinimumDwellTimeAsString().equals("0")) 
								&& (trainsReadInSetC.get(i).getRouteEntries().get(k).getRtcIncrement() > aNodeRtcIncrement)
								&& (aNodeRtcIncrement != 0))
						{
							dwellEventOffsetCumulativeTimeInSeconds += BIASRecoveryRateAnalysisConfigController.getSetCScheduleImprecisionOffsetInSeconds();
							dwellEventCumulativeTimeInSeconds += ConvertDateTime.convertDDHHMMSSStringToSeconds(trainsReadInSetC.get(i).getRouteEntries().get(k).getMinimumDwellTimeAsString()); 
							dwellEventBetweenAAndBNodesBoolean = true;
						}

						if ((!trainsReadInSetC.get(i).getRouteEntries().get(k).getWaitOnScheduleAsString().equals("0")) 
								&& (trainsReadInSetC.get(i).getRouteEntries().get(k).getRtcIncrement() > aNodeRtcIncrement)
								&& (aNodeRtcIncrement != 0))
						{
							waitOnScheduleCumulativeTimeInSeconds += ConvertDateTime.convertDDHHMMSSStringToSeconds(trainsReadInSetC.get(i).getRouteEntries().get(k).getWaitOnScheduleAsString()); 
							waitOnScheduleBetweenAAndBNodesBoolean = true;
						}
					}
				}

				if ((validANodeFound) && (validBNodeFound))
				{	
					//  Valid computation found
					String scheduleTimeToUseAtNodeA;
					String scheduleTimeToUseAtNodeB;

					if (aNodeDepartureTimeBySchedule == "")
						scheduleTimeToUseAtNodeA = aNodeArrivalTimeBySchedule;
					else
						scheduleTimeToUseAtNodeA = aNodeDepartureTimeBySchedule;

					if (bNodeArrivalTimeBySchedule == "")
						scheduleTimeToUseAtNodeB = bNodeDepartureTimeBySchedule;
					else
						scheduleTimeToUseAtNodeB = bNodeArrivalTimeBySchedule;


					Integer runTimeByScheduleAsSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(scheduleTimeToUseAtNodeB) - ConvertDateTime.convertDDHHMMSSStringToSeconds(scheduleTimeToUseAtNodeA);
					Integer runTimeBySimulationAsSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(bNodeArrivalTimeBySimulation) - ConvertDateTime.convertDDHHMMSSStringToSeconds(aNodeDepartureTimeBySimulation);

					Double distanceCovered = bNodeCumulativeDistance - aNodeCumulativeDistance;

					while (runTimeByScheduleAsSeconds < 0)
						runTimeByScheduleAsSeconds += 86400;

					// Create Recovery Rate Assessment object
					if (debug)
						System.out.println("Set C: Train "+trainsReadInSetC.get(i).getTrainSymbol()+" of group "+trainsReadInSetC.get(i).getTrainGroupAbbreviation()+" with A node of "+aNodeSetC+" and B node of "+bNodeSetC+" has created a recovery assessment object.");
					RecoveryRateAssessment assessmentSetC = new RecoveryRateAssessment("C", aNodeSetC, bNodeSetC, runTimeByScheduleAsSeconds, runTimeBySimulationAsSeconds, dwellEventBetweenAAndBNodesBoolean, dwellEventCumulativeTimeInSeconds, dwellEventOffsetCumulativeTimeInSeconds, waitOnScheduleBetweenAAndBNodesBoolean, waitOnScheduleCumulativeTimeInSeconds, distanceCovered);
					trainsReadInSetC.get(i).setRecoveryRateAssesment(assessmentSetC);
					trainsReadInSetC.get(i).setTrainHasRecoveryRatesCalculated();
					recoveryRatesCalculated++;
				}
			}
		}

		// Set D
		// For each train
		for (int i = 0; i < trainsReadInSetD.size(); i++)
		{
			for (int j = 0; j < nodePairsToAnalyzeSetD.size(); j++)
			{
				aNodeSetD = nodePairsToAnalyzeSetD.get(j).split(":")[0];
				bNodeSetD = nodePairsToAnalyzeSetD.get(j).split(":")[1];

				String aNodeArrivalTimeBySchedule = "";
				String aNodeArrivalTimeBySimulation = "";
				String aNodeDepartureTimeBySchedule = "";
				String aNodeDepartureTimeBySimulation = "";

				String bNodeArrivalTimeBySchedule = "";
				String bNodeArrivalTimeBySimulation = "";
				String bNodeDepartureTimeBySchedule = "";
				String bNodeDepartureTimeBySimulation = "";

				Double aNodeCumulativeDistance = 0.0;
				Double bNodeCumulativeDistance = 0.0;
				Integer aNodeRtcIncrement = 0;
				Integer bNodeRtcIncrement = 0;
				Integer dwellEventCumulativeTimeInSeconds = 0;
				Integer dwellEventOffsetCumulativeTimeInSeconds = 0;
				Integer waitOnScheduleCumulativeTimeInSeconds = 0;
				Boolean dwellEventBetweenAAndBNodesBoolean = false;
				Boolean waitOnScheduleBetweenAAndBNodesBoolean = false;

				Boolean validANodeFound = false;
				Boolean validBNodeFound = false;

				for (int k = 0; k < trainsReadInSetD.get(i).getRouteEntries().size(); k++)
				{
					if (trainsReadInSetD.get(i).getRouteEntries().get(k).getNode().equals(aNodeSetD))
					{
						aNodeArrivalTimeBySchedule = trainsReadInSetD.get(i).getRouteEntries().get(k).getScheduledArrivalTimeAsString();
						aNodeArrivalTimeBySimulation = trainsReadInSetD.get(i).getRouteEntries().get(k).getSimulatedArrivalTimeAsString();
						aNodeDepartureTimeBySchedule = trainsReadInSetD.get(i).getRouteEntries().get(k).getScheduledDepartureTimeAsString();
						aNodeDepartureTimeBySimulation = trainsReadInSetD.get(i).getRouteEntries().get(k).getSimulatedDepartureTimeAsString();
						aNodeCumulativeDistance =  trainsReadInSetD.get(i).getRouteEntries().get(k).getCumulativeDistance();
						aNodeRtcIncrement =  trainsReadInSetD.get(i).getRouteEntries().get(k).getRtcIncrement();
						validANodeFound = true;
					}
					else if ((trainsReadInSetD.get(i).getRouteEntries().get(k).getNode().equals(bNodeSetD)) && (validANodeFound))
					{
						bNodeArrivalTimeBySchedule = trainsReadInSetD.get(i).getRouteEntries().get(k).getScheduledArrivalTimeAsString();
						bNodeArrivalTimeBySimulation = trainsReadInSetD.get(i).getRouteEntries().get(k).getSimulatedArrivalTimeAsString();
						bNodeDepartureTimeBySchedule = trainsReadInSetD.get(i).getRouteEntries().get(k).getScheduledDepartureTimeAsString();
						bNodeDepartureTimeBySimulation = trainsReadInSetD.get(i).getRouteEntries().get(k).getSimulatedDepartureTimeAsString();
						bNodeCumulativeDistance =  trainsReadInSetD.get(i).getRouteEntries().get(k).getCumulativeDistance();
						bNodeRtcIncrement =  trainsReadInSetD.get(i).getRouteEntries().get(k).getRtcIncrement();
						validBNodeFound = true;
						break;
					}
					else
					{	
						if ((!trainsReadInSetD.get(i).getRouteEntries().get(k).getMinimumDwellTimeAsString().equals("0")) 
								&& (trainsReadInSetD.get(i).getRouteEntries().get(k).getRtcIncrement() > aNodeRtcIncrement)
								&& (aNodeRtcIncrement != 0))
						{
							dwellEventOffsetCumulativeTimeInSeconds += BIASRecoveryRateAnalysisConfigController.getSetDScheduleImprecisionOffsetInSeconds();
							dwellEventCumulativeTimeInSeconds += ConvertDateTime.convertDDHHMMSSStringToSeconds(trainsReadInSetD.get(i).getRouteEntries().get(k).getMinimumDwellTimeAsString()); 
							dwellEventBetweenAAndBNodesBoolean = true;
						}

						if ((!trainsReadInSetD.get(i).getRouteEntries().get(k).getWaitOnScheduleAsString().equals("0")) 
								&& (trainsReadInSetD.get(i).getRouteEntries().get(k).getRtcIncrement() > aNodeRtcIncrement)
								&& (aNodeRtcIncrement != 0))
						{
							waitOnScheduleCumulativeTimeInSeconds += ConvertDateTime.convertDDHHMMSSStringToSeconds(trainsReadInSetD.get(i).getRouteEntries().get(k).getWaitOnScheduleAsString()); 
							waitOnScheduleBetweenAAndBNodesBoolean = true;
						}
					}
				}

				if ((validANodeFound) && (validBNodeFound))
				{	
					//  Valid computation found
					String scheduleTimeToUseAtNodeA;
					String scheduleTimeToUseAtNodeB;

					if (aNodeDepartureTimeBySchedule == "")
						scheduleTimeToUseAtNodeA = aNodeArrivalTimeBySchedule;
					else
						scheduleTimeToUseAtNodeA = aNodeDepartureTimeBySchedule;

					if (bNodeArrivalTimeBySchedule == "")
						scheduleTimeToUseAtNodeB = bNodeDepartureTimeBySchedule;
					else
						scheduleTimeToUseAtNodeB = bNodeArrivalTimeBySchedule;


					Integer runTimeByScheduleAsSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(scheduleTimeToUseAtNodeB) - ConvertDateTime.convertDDHHMMSSStringToSeconds(scheduleTimeToUseAtNodeA);
					Integer runTimeBySimulationAsSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(bNodeArrivalTimeBySimulation) - ConvertDateTime.convertDDHHMMSSStringToSeconds(aNodeDepartureTimeBySimulation);

					Double distanceCovered = bNodeCumulativeDistance - aNodeCumulativeDistance;

					while (runTimeByScheduleAsSeconds < 0)
						runTimeByScheduleAsSeconds += 86400;

					// Create Recovery Rate Assessment object
					if (debug)
						System.out.println("Set D: Train "+trainsReadInSetD.get(i).getTrainSymbol()+" of group "+trainsReadInSetD.get(i).getTrainGroupAbbreviation()+" with A node of "+aNodeSetD+" and B node of "+bNodeSetD+" has created a recovery assessment object.");
					RecoveryRateAssessment assessmentSetD = new RecoveryRateAssessment("D", aNodeSetD, bNodeSetD, runTimeByScheduleAsSeconds, runTimeBySimulationAsSeconds, dwellEventBetweenAAndBNodesBoolean, dwellEventCumulativeTimeInSeconds, dwellEventOffsetCumulativeTimeInSeconds, waitOnScheduleBetweenAAndBNodesBoolean, waitOnScheduleCumulativeTimeInSeconds, distanceCovered);
					trainsReadInSetD.get(i).setRecoveryRateAssesment(assessmentSetD);
					trainsReadInSetD.get(i).setTrainHasRecoveryRatesCalculated();
					recoveryRatesCalculated++;
				}
			}
		}

		resultsMessage += "Calculated "+recoveryRatesCalculated+" recovery rates\n";
		resultsMessage += "Finished analyzing recovery rates at "+ConvertDateTime.getTimeStamp()+("\n");
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}

	public static ArrayList<TrainAssessment> getAnalyzedTrainsSetA()
	{
		return trainsReadInSetA;
	}

	public static ArrayList<TrainAssessment> getAnalyzedTrainsSetB()
	{
		return trainsReadInSetB;
	}

	public static ArrayList<TrainAssessment> getAnalyzedTrainsSetC()
	{
		return trainsReadInSetC;
	}

	public static ArrayList<TrainAssessment> getAnalyzedTrainsSetD()
	{
		return trainsReadInSetD;
	}
}