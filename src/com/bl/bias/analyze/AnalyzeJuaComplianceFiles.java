package com.bl.bias.analyze;

import java.util.ArrayList;
import java.util.Collections;

import com.bl.bias.app.BIASJuaComplianceConfigController;
import com.bl.bias.objects.ComplianceTrain;
import com.bl.bias.read.ReadJuaComplianceFiles;
import com.bl.bias.tools.ConvertDateTime;

public class AnalyzeJuaComplianceFiles 
{
	private static String resultsMessage;

	private static ArrayList<ComplianceTrain> trainsToAnalyzeForCompliance = new ArrayList<ComplianceTrain>();

	private static ArrayList<String> brightlineTrainTypesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> fecTrainTypesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> triRailTrainTypesFromConfigFile = new ArrayList<String>();

	private static ArrayList<String> brightlineNodesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> fecNodesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> triRailNodesFromConfigFile = new ArrayList<String>();

	public AnalyzeJuaComplianceFiles() 
	{
		resultsMessage = "Started analyzing JUA Compliance at "+ConvertDateTime.getTimeStamp()+"\n";

		// For each train
		trainsToAnalyzeForCompliance.clear();

		brightlineTrainTypesFromConfigFile.clear();
		fecTrainTypesFromConfigFile.clear();
		triRailTrainTypesFromConfigFile.clear();

		brightlineNodesFromConfigFile.clear();
		fecNodesFromConfigFile.clear();
		triRailNodesFromConfigFile.clear();

		// Populate all arrays with necessary objects
		trainsToAnalyzeForCompliance.addAll(ReadJuaComplianceFiles.getTrainsToAnalyzeForCompliance());

		for (int i = 0; i < BIASJuaComplianceConfigController.getBrightlineTrainTypes().length; i++)
		{
			brightlineTrainTypesFromConfigFile.add(BIASJuaComplianceConfigController.getBrightlineTrainTypes()[i].trim());
		}

		for (int i = 0; i < BIASJuaComplianceConfigController.getFecTrainTypes().length; i++)
		{
			fecTrainTypesFromConfigFile.add(BIASJuaComplianceConfigController.getFecTrainTypes()[i].trim());
		}

		for (int i = 0; i < BIASJuaComplianceConfigController.getTriRailTrainTypes().length; i++)
		{
			triRailTrainTypesFromConfigFile.add(BIASJuaComplianceConfigController.getTriRailTrainTypes()[i].trim());
		}

		for (int i = 0; i < BIASJuaComplianceConfigController.getBrightlineNodes().length; i++)
		{
			brightlineNodesFromConfigFile.add(BIASJuaComplianceConfigController.getBrightlineNodes()[i].trim());
		}

		for (int i = 0; i < BIASJuaComplianceConfigController.getFecNodes().length; i++)
		{
			fecNodesFromConfigFile.add(BIASJuaComplianceConfigController.getFecNodes()[i].trim());
		}

		for (int i = 0; i < BIASJuaComplianceConfigController.getTriRailNodes().length; i++)
		{
			triRailNodesFromConfigFile.add(BIASJuaComplianceConfigController.getTriRailNodes()[i].trim());
		}

		// Determine statistical simulation days (as integers)
		Integer statisticalStartDay = ReadJuaComplianceFiles.getStatisticalStartDayOfWeekAsInteger();		 
		Integer statisticalEndDay = statisticalStartDay + ReadJuaComplianceFiles.getStatisticalDurationInDays() - 1; // inclusive

		Integer countOfBrightlinelOperatedTrains = 0;
		Integer countOfFecOperatedTrains = 0;
		Integer countOfTriRailOperatedTrains = 0;

		// For each train
		for (int i = 0; i < trainsToAnalyzeForCompliance.size(); i++)
		{
			// Check Brightline type
			if (brightlineTrainTypesFromConfigFile.contains(trainsToAnalyzeForCompliance.get(i).getType().toUpperCase()))
			{
				// Check if enabled
				if (trainsToAnalyzeForCompliance.get(i).getEnabled().toUpperCase().equals("YES"))
				{
					// Check if through a valid node
					if (!Collections.disjoint(brightlineNodesFromConfigFile, trainsToAnalyzeForCompliance.get(i).getRouteEntries()))
					{
						// Check how many times train operates during statistical period
						if (trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().size() == 0)
						{
							String trainToBaseScheduleOn = trainsToAnalyzeForCompliance.get(i).getSymbol();
							ComplianceTrain train = trainsToAnalyzeForCompliance.get(i);
							do
							{
								for (int j = 0; j < trainsToAnalyzeForCompliance.size(); j++)
								{
									if (trainToBaseScheduleOn.equals(trainsToAnalyzeForCompliance.get(j).getSymbol()))
									{
										train = trainsToAnalyzeForCompliance.get(j);
										break; 
									}
								}
								trainToBaseScheduleOn = train.getLinkedAtOriginTo();
							}
							while (train.getDaysOfOperationAsInteger().size() == 0);

							for (int j = 0; j < train.getDaysOfOperationAsInteger().size(); j++)
							{
								if ((train.getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
										&& (train.getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
								{
									countOfBrightlinelOperatedTrains++;
								}
							}
						}
						else
						{
							for (int j = 0; j < trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().size(); j++)
							{
								if ((trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
										&& (trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
								{
									countOfBrightlinelOperatedTrains++;
								}
							}
						}
					}
				}
			}
			// Check FEC type
			else if (fecTrainTypesFromConfigFile.contains(trainsToAnalyzeForCompliance.get(i).getType().toUpperCase()))
			{
				// Check if enabled
				if (trainsToAnalyzeForCompliance.get(i).getEnabled().toUpperCase().equals("YES"))
				{
					// Check if through a valid node
					if (!Collections.disjoint(fecNodesFromConfigFile, trainsToAnalyzeForCompliance.get(i).getRouteEntries()))
					{
						// Check how many times train operates during statistical period
						if (trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().size() == 0)
						{
							String trainToBaseScheduleOn = trainsToAnalyzeForCompliance.get(i).getSymbol();
							ComplianceTrain train = trainsToAnalyzeForCompliance.get(i);
							do
							{
								for (int j = 0; j < trainsToAnalyzeForCompliance.size(); j++)
								{
									if (trainToBaseScheduleOn.equals(trainsToAnalyzeForCompliance.get(j).getSymbol()))
									{
										train = trainsToAnalyzeForCompliance.get(j);
										break; 
									}
								}
								trainToBaseScheduleOn = train.getLinkedAtOriginTo();
							}
							while (train.getDaysOfOperationAsInteger().size() == 0);

							for (int j = 0; j < train.getDaysOfOperationAsInteger().size(); j++)
							{
								if ((train.getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
										&& (train.getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
								{
									countOfFecOperatedTrains++;
								}
							}
						}
						else
						{
							for (int j = 0; j < trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().size(); j++)
							{
								if ((trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
										&& (trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
								{
									countOfFecOperatedTrains++;
								}
							}
						}
					}
				}
			}
			// Check TriRail type
			else if (triRailTrainTypesFromConfigFile.contains(trainsToAnalyzeForCompliance.get(i).getType().toUpperCase()))
			{
				// Check if enabled
				if (trainsToAnalyzeForCompliance.get(i).getEnabled().toUpperCase().equals("YES"))
				{
					// Check if through a valid node
					if (!Collections.disjoint(triRailNodesFromConfigFile, trainsToAnalyzeForCompliance.get(i).getRouteEntries()))
					{
						// Check how many times train operates during statistical period
						if (trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().size() == 0)
						{
							String trainToBaseScheduleOn = trainsToAnalyzeForCompliance.get(i).getSymbol();
							ComplianceTrain train = trainsToAnalyzeForCompliance.get(i);
							do
							{
								for (int j = 0; j < trainsToAnalyzeForCompliance.size(); j++)
								{
									if (trainToBaseScheduleOn.equals(trainsToAnalyzeForCompliance.get(j).getSymbol()))
									{
										train = trainsToAnalyzeForCompliance.get(j);
										break; 
									}
								}
								trainToBaseScheduleOn = train.getLinkedAtOriginTo();
							}
							while (train.getDaysOfOperationAsInteger().size() == 0);

							for (int j = 0; j < train.getDaysOfOperationAsInteger().size(); j++)
							{
								if ((train.getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
										&& (train.getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
								{
									countOfTriRailOperatedTrains++;
								}
							}
						}
						else
						{
							for (int j = 0; j < trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().size(); j++)
							{
								if ((trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
										&& (trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
								{
									countOfTriRailOperatedTrains++;
								}
							}
						}
					}
				}
			}
		}

		resultsMessage += "Found "+countOfBrightlinelOperatedTrains+" eligible Brightline trains operated during the statistical period of the selected case\n";
		resultsMessage += "Found "+countOfFecOperatedTrains+" eligible FEC trains operated during the statistical period of the selected case\n";
		resultsMessage += "Found "+countOfTriRailOperatedTrains+" eligible TriRail trains operated during the statistical period of the selected case\n";
		resultsMessage += "Finished analyzing JUA Compliance at "+ConvertDateTime.getTimeStamp()+("\n");
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}