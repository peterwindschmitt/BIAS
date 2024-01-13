package com.bl.bias.analyze;

import java.util.ArrayList;
import java.util.HashSet;

import com.bl.bias.app.BIASRecoveryRateAnalysisConfigController;
import com.bl.bias.objects.RecoveryRateAssessment;
import com.bl.bias.objects.TrainAssessment;
import com.bl.bias.read.ReadRecoveryRateAnalysisFiles;
import com.bl.bias.tools.ConvertDateTime;

public class RecoveryRateAnalysis 
{
	private static String resultsMessage;
	
	private static ArrayList<String> nodePairsToAnalyze = new ArrayList<String>();
	private static ArrayList<String> groupsToAnalyze = new ArrayList<String>();
	private static ArrayList<TrainAssessment> trainsReadIn = new ArrayList<TrainAssessment>();
	
	private Integer recoveryRatesCalculated = 0;
	
	private static Boolean debug = false;

	public RecoveryRateAnalysis() 
	{
		resultsMessage = "Started analyzing recovery rates at "+ConvertDateTime.getTimeStamp()+"\n";

		// Add node pairs from Recovery Rate Anaysis config
		nodePairsToAnalyze.clear();
		for (int i = 0; i < BIASRecoveryRateAnalysisConfigController.getSetARecoveryRateAnalysisNodePairs().split(",").length; i++)
		{
			nodePairsToAnalyze.add(BIASRecoveryRateAnalysisConfigController.getSetARecoveryRateAnalysisNodePairs().split(",")[i]);
		}
		
		// Add groups from Recovery Rate Analysis config
		groupsToAnalyze.clear();
		for (int i = 0; i < BIASRecoveryRateAnalysisConfigController.getRecoveryRateAnalysisTrainGroups().split(",").length; i++)
		{
			groupsToAnalyze.add(BIASRecoveryRateAnalysisConfigController.getRecoveryRateAnalysisTrainGroups().split(",")[i]);
		}
		
		// Get trains from read class
		trainsReadIn.clear();
		trainsReadIn.addAll(ReadRecoveryRateAnalysisFiles.getTrainsReadIn());
		
		// For each train
		for (int i = 0; i < trainsReadIn.size(); i++)
		{
			// If it is assigned to a group whose data should be captured
			if (groupsToAnalyze.contains(trainsReadIn.get(i).getTrainGroupAbbreviation()))
			{
				HashSet<String> allScheduledNodesInTrainsRoute = new HashSet<String>();
				for (int j = 0; j < trainsReadIn.get(i).getRouteEntries().size(); j++)
				{
					allScheduledNodesInTrainsRoute.add(trainsReadIn.get(i).getRouteEntries().get(j).getNode().trim());
				}
				
				for (int j = 0; j < nodePairsToAnalyze.size(); j++)
				{
					String aNode = nodePairsToAnalyze.get(j).split(":")[0];
					String bNode = nodePairsToAnalyze.get(j).split(":")[1];
					
					if ((allScheduledNodesInTrainsRoute.contains(aNode))  // Node A of node pair
							&& (allScheduledNodesInTrainsRoute.contains(bNode))) // Node B of node pair
					{
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
						
						for (int k = 0; k < trainsReadIn.get(i).getRouteEntries().size(); k++)
						{
							if (trainsReadIn.get(i).getRouteEntries().get(k).getNode().equals(aNode))
							{
								aNodeArrivalTimeBySchedule = trainsReadIn.get(i).getRouteEntries().get(k).getScheduledArrivalTimeAsString();
								aNodeArrivalTimeBySimulation = trainsReadIn.get(i).getRouteEntries().get(k).getSimulatedArrivalTimeAsString();
								aNodeDepartureTimeBySchedule = trainsReadIn.get(i).getRouteEntries().get(k).getScheduledDepartureTimeAsString();
								aNodeDepartureTimeBySimulation = trainsReadIn.get(i).getRouteEntries().get(k).getSimulatedDepartureTimeAsString();
								aNodeCumulativeDistance =  trainsReadIn.get(i).getRouteEntries().get(k).getCumulativeDistance();
								aNodeRtcIncrement =  trainsReadIn.get(i).getRouteEntries().get(k).getRtcIncrement();
							}
							else if (trainsReadIn.get(i).getRouteEntries().get(k).getNode().equals(bNode))
							{
								bNodeArrivalTimeBySchedule = trainsReadIn.get(i).getRouteEntries().get(k).getScheduledArrivalTimeAsString();
								bNodeArrivalTimeBySimulation = trainsReadIn.get(i).getRouteEntries().get(k).getSimulatedArrivalTimeAsString();
								bNodeDepartureTimeBySchedule = trainsReadIn.get(i).getRouteEntries().get(k).getScheduledDepartureTimeAsString();
								bNodeDepartureTimeBySimulation = trainsReadIn.get(i).getRouteEntries().get(k).getSimulatedDepartureTimeAsString();
								bNodeCumulativeDistance =  trainsReadIn.get(i).getRouteEntries().get(k).getCumulativeDistance();
								bNodeRtcIncrement =  trainsReadIn.get(i).getRouteEntries().get(k).getRtcIncrement();
								break;
							}
							else
							{	
								if ((!trainsReadIn.get(i).getRouteEntries().get(k).getMinimumDwellTimeAsString().equals("0")) 
									&& (trainsReadIn.get(i).getRouteEntries().get(k).getRtcIncrement() > aNodeRtcIncrement)
									&& (aNodeRtcIncrement != 0))
									{
										dwellEventOffsetCumulativeTimeInSeconds += BIASRecoveryRateAnalysisConfigController.getSetAScheduleImprecisionOffsetInSeconds();
										dwellEventCumulativeTimeInSeconds += ConvertDateTime.convertDDHHMMSSStringToSeconds(trainsReadIn.get(i).getRouteEntries().get(k).getMinimumDwellTimeAsString()); 
										dwellEventBetweenAAndBNodesBoolean = true;
									}
								
								if ((!trainsReadIn.get(i).getRouteEntries().get(k).getWaitOnScheduleAsString().equals("0")) 
										&& (trainsReadIn.get(i).getRouteEntries().get(k).getRtcIncrement() > aNodeRtcIncrement)
										&& (aNodeRtcIncrement != 0))
									{
										waitOnScheduleCumulativeTimeInSeconds += ConvertDateTime.convertDDHHMMSSStringToSeconds(trainsReadIn.get(i).getRouteEntries().get(k).getWaitOnScheduleAsString()); 
										waitOnScheduleBetweenAAndBNodesBoolean = true;
									}
							}
						}
						
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
							System.out.println("Train "+trainsReadIn.get(i).getTrainSymbol()+" of group "+trainsReadIn.get(i).getTrainGroupAbbreviation()+" with A node of "+aNode+" and B node of "+bNode+" has created a recovery assessment object.");
						RecoveryRateAssessment assessment = new RecoveryRateAssessment(aNode, bNode, runTimeByScheduleAsSeconds, runTimeBySimulationAsSeconds, dwellEventBetweenAAndBNodesBoolean, dwellEventCumulativeTimeInSeconds, dwellEventOffsetCumulativeTimeInSeconds, waitOnScheduleBetweenAAndBNodesBoolean, waitOnScheduleCumulativeTimeInSeconds, distanceCovered);
						trainsReadIn.get(i).setRecoveryRateAssesment(assessment);
						trainsReadIn.get(i).setTrainHasRecoveryRatesCalculated();
						recoveryRatesCalculated++;
					}
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
	
	public static ArrayList<TrainAssessment> getAnalyzedTrains()
	{
		return trainsReadIn;
	}
}