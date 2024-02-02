package com.bl.bias.analyze;

import java.util.ArrayList;
import java.util.Collections;

import com.bl.bias.app.BIASJuaComplianceConfigController;
import com.bl.bias.objects.CompliancePermit;
import com.bl.bias.objects.ComplianceTrain;
import com.bl.bias.read.ReadJuaComplianceFiles;
import com.bl.bias.tools.ConvertDateTime;

public class AnalyzeJuaComplianceFiles 
{
	private static String resultsMessage;

	private static Integer totalCountOfBrightlineOperatedTrains;
	private static Integer totalCountOfFecOperatedTrains;
	private static Integer totalCountOfTriRailOperatedTrains;

	private static Double dailyAverageCountOfBrightlineOperatedTrains;
	private static Double dailyAverageCountOfFecOperatedTrains;
	private static Double dailyAverageCountOfTriRailOperatedTrains;

	private static Double milesOfAffectedTrackThisCase = 0.0;
	private static Double milesOfAffectedTrackLastAcceptedCase = 0.0;
	private static Double milesOfAffectedTrackMultiplyPassengerSpeedsThisCase = 0.0;
	private static Double milesOfAffectedTrackMultiplyFreightSpeedsThisCase = 0.0;
	private static Double milesOfAffectedTrackMultiplyPassengerSpeedsLastAcceptedCase = 0.0;
	private static Double milesOfAffectedTrackMultiplyFreightSpeedsLastAcceptedCase = 0.0;

	private static Integer statisticalStartDay; 
	private static Integer statisticalEndDay; 

	private static ArrayList<ComplianceTrain> trainsToAnalyzeForCompliance = new ArrayList<ComplianceTrain>();
	private static ArrayList<ComplianceTrain> seedTrainsFoundEligible = new ArrayList<ComplianceTrain>();
	private static ArrayList<ComplianceTrain> seedTrainsFoundNotEligible = new ArrayList<ComplianceTrain>();
	private static ArrayList<String> seedTrainSymbolsFoundEligible = new ArrayList<String>();
	private static ArrayList<String> seedTrainSymbolsFoundNotEligible = new ArrayList<String>();

	private static ArrayList<String> brightlineTrainTypesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> fecTrainTypesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> triRailTrainTypesFromConfigFile = new ArrayList<String>();

	private static ArrayList<String> brightlineNodesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> fecNodesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> triRailNodesFromConfigFile = new ArrayList<String>();

	private static ArrayList<CompliancePermit> permitsToAnalyzeForThisCase = new ArrayList<CompliancePermit>();
	private static ArrayList<CompliancePermit> permitsToAnalyzeForLastAcceptedCase = new ArrayList<CompliancePermit>();

	public AnalyzeJuaComplianceFiles() 
	{
		resultsMessage = "Started analyzing JUA Compliance at "+ConvertDateTime.getTimeStamp()+"\n";

		// Trains
		if (BIASJuaComplianceConfigController.getCheckEnabledCountOfTrains())
		{
			// For each train
			trainsToAnalyzeForCompliance.clear();
			seedTrainsFoundEligible.clear();
			seedTrainsFoundNotEligible.clear();
			seedTrainSymbolsFoundEligible.clear();
			seedTrainSymbolsFoundNotEligible.clear();

			brightlineTrainTypesFromConfigFile.clear();
			fecTrainTypesFromConfigFile.clear();
			triRailTrainTypesFromConfigFile.clear();

			brightlineNodesFromConfigFile.clear();
			fecNodesFromConfigFile.clear();
			triRailNodesFromConfigFile.clear();

			// Populate all arrays with necessary objects
			trainsToAnalyzeForCompliance.addAll(ReadJuaComplianceFiles.getTrainsToAnalyzeThisCase());
			seedTrainsFoundNotEligible.addAll(trainsToAnalyzeForCompliance);
			
			for (int i = 0; i < trainsToAnalyzeForCompliance.size(); i++)
			{
				seedTrainSymbolsFoundNotEligible.add(trainsToAnalyzeForCompliance.get(i).getSymbol());
			}

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
			totalCountOfBrightlineOperatedTrains = 0;
			totalCountOfFecOperatedTrains = 0;
			totalCountOfTriRailOperatedTrains = 0;
			statisticalStartDay = ReadJuaComplianceFiles.getStatisticalStartDayOfWeekAsInteger();		 
			statisticalEndDay = statisticalStartDay + ReadJuaComplianceFiles.getStatisticalDurationInDays() - 1; // inclusive

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
								seedTrainsFoundEligible.add(trainsToAnalyzeForCompliance.get(i));
								seedTrainSymbolsFoundEligible.add(trainsToAnalyzeForCompliance.get(i).getSymbol());
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
										totalCountOfBrightlineOperatedTrains++;
										// Train is eligible so remove from the ineligible list
										seedTrainsFoundNotEligible.remove(trainsToAnalyzeForCompliance.get(i));
										seedTrainSymbolsFoundNotEligible.remove(trainsToAnalyzeForCompliance.get(i).getSymbol());
									}
								}
							}
							else
							{
								seedTrainsFoundEligible.add(trainsToAnalyzeForCompliance.get(i));
								seedTrainSymbolsFoundEligible.add(trainsToAnalyzeForCompliance.get(i).getSymbol());
								for (int j = 0; j < trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().size(); j++)
								{
									if ((trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
											&& (trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
									{
										totalCountOfBrightlineOperatedTrains++;
										// Train is eligible so remove from the ineligible list
										seedTrainsFoundNotEligible.remove(trainsToAnalyzeForCompliance.get(i));
										seedTrainSymbolsFoundNotEligible.remove(trainsToAnalyzeForCompliance.get(i).getSymbol());
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
								seedTrainsFoundEligible.add(trainsToAnalyzeForCompliance.get(i));
								seedTrainSymbolsFoundEligible.add(trainsToAnalyzeForCompliance.get(i).getSymbol());
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
										totalCountOfFecOperatedTrains++;
										// Train is eligible so remove from the ineligible list
										seedTrainsFoundNotEligible.remove(trainsToAnalyzeForCompliance.get(i));
										seedTrainSymbolsFoundNotEligible.remove(trainsToAnalyzeForCompliance.get(i).getSymbol());
									}
								}
							}
							else
							{
								seedTrainsFoundEligible.add(trainsToAnalyzeForCompliance.get(i));
								seedTrainSymbolsFoundEligible.add(trainsToAnalyzeForCompliance.get(i).getSymbol());
								for (int j = 0; j < trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().size(); j++)
								{
									if ((trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
											&& (trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
									{
										totalCountOfFecOperatedTrains++;
										// Train is eligible so remove from the ineligible list
										seedTrainsFoundNotEligible.remove(trainsToAnalyzeForCompliance.get(i));
										seedTrainSymbolsFoundNotEligible.remove(trainsToAnalyzeForCompliance.get(i).getSymbol());
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
								seedTrainsFoundEligible.add(trainsToAnalyzeForCompliance.get(i));
								seedTrainSymbolsFoundEligible.add(trainsToAnalyzeForCompliance.get(i).getSymbol());
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
										totalCountOfTriRailOperatedTrains++;
										// Train is eligible so remove from the ineligible list
										seedTrainsFoundNotEligible.remove(trainsToAnalyzeForCompliance.get(i));
										seedTrainSymbolsFoundNotEligible.remove(trainsToAnalyzeForCompliance.get(i).getSymbol());
									}
								}
							}
							else
							{
								seedTrainsFoundEligible.add(trainsToAnalyzeForCompliance.get(i));
								seedTrainSymbolsFoundEligible.add(trainsToAnalyzeForCompliance.get(i).getSymbol());
								for (int j = 0; j < trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().size(); j++)
								{
									if ((trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
											&& (trainsToAnalyzeForCompliance.get(i).getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
									{
										totalCountOfTriRailOperatedTrains++;
										// Train is eligible so remove from the ineligible list
										seedTrainsFoundNotEligible.remove(trainsToAnalyzeForCompliance.get(i));
										seedTrainSymbolsFoundNotEligible.remove(trainsToAnalyzeForCompliance.get(i).getSymbol());
									}
								}
							}
						}
					}
				}
			}

			resultsMessage += "Found "+seedTrainsFoundEligible.size()+" eligible seed trains operated in the selected case:\n";
			resultsMessage += "  yielding "+totalCountOfBrightlineOperatedTrains+" dispatched Brightline trains operated during the statistical period of the selected case\n";
			resultsMessage += "  yielding "+totalCountOfFecOperatedTrains+" dispatched FEC trains operated during the statistical period of the selected case\n";
			resultsMessage += "  yielding "+totalCountOfTriRailOperatedTrains+" dispatched TriRail trains operated during the statistical period of the selected case\n";
			resultsMessage += "Found "+seedTrainsFoundNotEligible.size()+" other seed trains in the selected case\n";
		}

		// Permits
		if (BIASJuaComplianceConfigController.getCheckPermitsEnabled())
		{
			permitsToAnalyzeForThisCase.clear();
			permitsToAnalyzeForLastAcceptedCase.clear();
			permitsToAnalyzeForThisCase.addAll(ReadJuaComplianceFiles.getPermitsToAnalyzeThisCase());
			permitsToAnalyzeForLastAcceptedCase.addAll(ReadJuaComplianceFiles.getPermitsToAnalyzeLastAcceptedCase());
		
			resultsMessage += "Found " + permitsToAnalyzeForThisCase.size() + " permits in this case's .PERMIT file and " + permitsToAnalyzeForLastAcceptedCase.size() + " permits in the last accepted .PERMIT file:\n";

			// Compute miles of affected track, miles * speed and miles * duration
			if (BIASJuaComplianceConfigController.getCheckPermitsSumOfLinearMiles())
			{
				milesOfAffectedTrackThisCase = 0.0;
				milesOfAffectedTrackLastAcceptedCase = 0.0;
				milesOfAffectedTrackMultiplyPassengerSpeedsThisCase = 0.0;
				milesOfAffectedTrackMultiplyFreightSpeedsThisCase = 0.0;
				milesOfAffectedTrackMultiplyPassengerSpeedsLastAcceptedCase = 0.0;
				milesOfAffectedTrackMultiplyFreightSpeedsLastAcceptedCase = 0.0;

				// This case
				for (int i = 0; i < permitsToAnalyzeForThisCase.size(); i++)
				{
					milesOfAffectedTrackThisCase += Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp());
					milesOfAffectedTrackMultiplyPassengerSpeedsThisCase += (milesOfAffectedTrackThisCase * permitsToAnalyzeForThisCase.get(i).getPasSpeed());
					milesOfAffectedTrackMultiplyFreightSpeedsThisCase += (milesOfAffectedTrackThisCase * permitsToAnalyzeForThisCase.get(i).getFrtSpeed());
				}

				// Last accepted case
				for (int i = 0; i < permitsToAnalyzeForLastAcceptedCase.size(); i++)
				{
					milesOfAffectedTrackLastAcceptedCase += Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp());
					milesOfAffectedTrackMultiplyPassengerSpeedsLastAcceptedCase += (milesOfAffectedTrackLastAcceptedCase * permitsToAnalyzeForLastAcceptedCase.get(i).getPasSpeed());
					milesOfAffectedTrackMultiplyFreightSpeedsLastAcceptedCase += (milesOfAffectedTrackLastAcceptedCase * permitsToAnalyzeForLastAcceptedCase.get(i).getFrtSpeed());
				}
			}
			resultsMessage += "  comparing miles of affected track\n";
		}

		resultsMessage += "Finished analyzing JUA Compliance at "+ConvertDateTime.getTimeStamp()+("\n");
	}

	public static Integer getTotalCountOfBrightlinelOperatedTrains()
	{
		return totalCountOfBrightlineOperatedTrains;
	}

	public static Integer getTotalCountOfFecOperatedTrains()
	{
		return totalCountOfFecOperatedTrains;
	}

	public static Integer getTotalCountOfTriRailOperatedTrains()
	{
		return totalCountOfTriRailOperatedTrains;
	}

	public static Double getDailyAverageCountOfBrightlineOperatedTrains()
	{
		dailyAverageCountOfBrightlineOperatedTrains = (double) totalCountOfBrightlineOperatedTrains / (double) (statisticalEndDay - statisticalStartDay + 1);

		return dailyAverageCountOfBrightlineOperatedTrains;
	}

	public static Double getDailyAverageCountOfFecOperatedTrains()
	{
		dailyAverageCountOfFecOperatedTrains = (double) totalCountOfFecOperatedTrains / (double) (statisticalEndDay - statisticalStartDay + 1);

		return dailyAverageCountOfFecOperatedTrains;
	}

	public static Double getDailyAverageCountOfTriRailOperatedTrains()
	{
		dailyAverageCountOfTriRailOperatedTrains = (double) totalCountOfTriRailOperatedTrains / (double) (statisticalEndDay - statisticalStartDay + 1);

		return dailyAverageCountOfTriRailOperatedTrains;
	}

	public static ArrayList<ComplianceTrain> getSeedTrainsFoundEligible()
	{
		return seedTrainsFoundEligible;
	}

	public static ArrayList<ComplianceTrain> getSeedTrainsFoundNotEligible()
	{
		return seedTrainsFoundNotEligible;
	}

	public static ArrayList<String> getSeedTrainSymbolsFoundEligible()
	{
		return seedTrainSymbolsFoundEligible;
	}

	public static ArrayList<String> getSeedTrainSymbolsFoundNotEligible()
	{
		return seedTrainSymbolsFoundNotEligible;
	}

	public static Integer getSeedTrainSymbolCountFoundEligible()
	{
		return seedTrainSymbolsFoundEligible.size();
	}

	public static Integer getSeedTrainSymbolCountFoundNotEligible()
	{
		return seedTrainSymbolsFoundNotEligible.size();
	}

	public static Double getMilesOfImpactedTrackThisCase()
	{
		return  milesOfAffectedTrackThisCase;
	}

	public static Double getMilesOfImpactedTrackLastAcceptedCase()
	{
		return  milesOfAffectedTrackLastAcceptedCase;
	}
	
	public static Double getMilesOfAffectedTrackMultiplyPassengerSpeedsThisCase()
	{
		return  milesOfAffectedTrackMultiplyPassengerSpeedsThisCase;
	}

	public static Double getMilesOfAffectedTrackMultiplyFreightSpeedsThisCase()
	{
		return  milesOfAffectedTrackMultiplyFreightSpeedsThisCase;
	}
	
	public static Double getMilesOfAffectedTrackMultiplyPassengerSpeedsLastAcceptedCase()
	{
		return  milesOfAffectedTrackMultiplyPassengerSpeedsLastAcceptedCase;
	}

	public static Double getMilesOfAffectedTrackMultiplyFreightSpeedsLastAcceptedCase()
	{
		return  milesOfAffectedTrackMultiplyFreightSpeedsLastAcceptedCase;
	}

	public static String getResultsMessage()
	{
		return resultsMessage;
	}
}