package com.bl.bias.analyze;

import java.util.ArrayList;

import com.bl.bias.app.BIASRecoveryRateAnalysisConfigController;
import com.bl.bias.objects.TrainAssesment;
import com.bl.bias.read.ReadRecoveryRateAnalysisFiles;
import com.bl.bias.tools.ConvertDateTime;

public class RecoveryRateAnalysis 
{
	private static String resultsMessage;
	
	private static ArrayList<String> nodePairsToAnalyze = new ArrayList<String>();
	private static ArrayList<String> groupsToAnalyze = new ArrayList<String>();
	private static ArrayList<TrainAssesment> trainsReadIn = new ArrayList<TrainAssesment>();

	public RecoveryRateAnalysis() 
	{
		resultsMessage = "Started analyzing Recovery Rates at "+ConvertDateTime.getTimeStamp()+"\n";

		// Add node pairs from Recovery Rate Anaysis config
		nodePairsToAnalyze.clear();
		for (int i = 0; i < BIASRecoveryRateAnalysisConfigController.getRecoveryRateAnalysisNodePairs().split(",").length; i++)
		{
			nodePairsToAnalyze.add(BIASRecoveryRateAnalysisConfigController.getRecoveryRateAnalysisNodePairs().split(",")[i]);
		}
		
		// Add groups from Recovery Rate Analysis config
		for (int i = 0; i < BIASRecoveryRateAnalysisConfigController.getRecoveryRateAnalysisTrainGroups().split(",").length; i++)
		{
			groupsToAnalyze.add(BIASRecoveryRateAnalysisConfigController.getRecoveryRateAnalysisTrainGroups().split(",")[i]);
		}
		
		// Get trains from read class
		trainsReadIn.addAll(ReadRecoveryRateAnalysisFiles.getTrainsReadIn());
		
		// For each train
		for (int i = 0; i < trainsReadIn.size(); i++)
		{
			if (groupsToAnalyze.contains(trainsReadIn.get(i).getTrainGroupAbbreviation()))
					System.out.println("Accepting train "+trainsReadIn.get(i).getTrainSymbol());
			else
				System.out.println("Not accepting train "+trainsReadIn.get(i).getTrainSymbol());
		}	

		resultsMessage += "Analyzed xxx trains\n";
		resultsMessage += "Finished analyzing Recovery Rates at "+ConvertDateTime.getTimeStamp()+"\n\n";
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}