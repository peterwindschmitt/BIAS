package com.bl.bias.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.bl.bias.app.BIASJuaComplianceConfigController;
import com.bl.bias.objects.CompliancePermit;
import com.bl.bias.objects.ComplianceTrain;
import com.bl.bias.read.ReadJuaComplianceFiles;
import com.bl.bias.tools.ConvertDateTime;

public class AnalyzeJuaComplianceFiles 
{
	private static String resultsMessage;

	// Trains
	private static Integer totalCountOfBrightlineOperatedTrainsThisCase;
	private static Integer totalCountOfFecOperatedTrainsThisCase;
	private static Integer totalCountOfTriRailOperatedTrainsThisCase;
	private static Integer totalCountOfBrightlineOperatedTrainsLastAcceptedCase;
	private static Integer totalCountOfFecOperatedTrainsLastAcceptedCase;
	private static Integer totalCountOfTriRailOperatedTrainsLastAcceptedCase;

	private static Double dailyAverageCountOfBrightlineOperatedTrainsThisCase;
	private static Double dailyAverageCountOfFecOperatedTrainsThisCase;
	private static Double dailyAverageCountOfTriRailOperatedTrainsThisCase;
	private static Double dailyAverageCountOfBrightlineOperatedTrainsLastAcceptedCase;
	private static Double dailyAverageCountOfFecOperatedTrainsLastAcceptedCase;
	private static Double dailyAverageCountOfTriRailOperatedTrainsLastAcceptedCase;

	// Permits
	private static Double milesOfAffectedTrackThisCase = 0.0;
	private static Double milesOfAffectedTrackLastAcceptedCase = 0.0;
	private static Double averageSlowOrderPassengerSpeedThisCase = 0.0;
	private static Double averageSlowOrderFreightSpeedThisCase = 0.0;
	private static Double averageSlowOrderPassengerSpeedLastAcceptedCase = 0.0;
	private static Double averageSlowOrderFreightSpeedLastAcceptedCase = 0.0;
	private static Double hoursMilesThisCase = 0.0;
	private static Double hoursMilesLastAcceptedCase = 0.0;
	private static Integer permitsConsideredThisCase = 0;
	private static Integer permitsConsideredLastAcceptedCase = 0;

	// Statistical Period
	private static Integer statisticalStartDay; 
	private static Integer statisticalEndDay; 

	private static ArrayList<ComplianceTrain> trainsToAnalyzeForComplianceThisCase = new ArrayList<ComplianceTrain>();
	private static ArrayList<ComplianceTrain> trainsToAnalyzeForComplianceLastAcceptedCase = new ArrayList<ComplianceTrain>();
	private static ArrayList<ComplianceTrain> seedTrainsFoundEligibleThisCase = new ArrayList<ComplianceTrain>();
	private static ArrayList<ComplianceTrain> seedTrainsFoundNotEligibleThisCase = new ArrayList<ComplianceTrain>();
	private static ArrayList<ComplianceTrain> seedTrainsFoundEligibleLastAcceptedCase = new ArrayList<ComplianceTrain>();
	private static ArrayList<ComplianceTrain> seedTrainsFoundNotEligibleLastAcceptedCase = new ArrayList<ComplianceTrain>();
	private static ArrayList<String> seedTrainSymbolsFoundEligibleThisCase = new ArrayList<String>();
	private static ArrayList<String> seedTrainSymbolsFoundNotEligibleThisCase = new ArrayList<String>();
	private static ArrayList<String> seedTrainSymbolsFoundEligibleLastAcceptedCase = new ArrayList<String>();
	private static ArrayList<String> seedTrainSymbolsFoundNotEligibleLastAcceptedCase = new ArrayList<String>();

	private static ArrayList<String> brightlineTrainTypesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> fecTrainTypesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> triRailTrainTypesFromConfigFile = new ArrayList<String>();

	private static ArrayList<String> brightlineNodesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> fecNodesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> triRailNodesFromConfigFile = new ArrayList<String>();

	private static ArrayList<CompliancePermit> permitsToAnalyzeForThisCase = new ArrayList<CompliancePermit>();
	private static ArrayList<CompliancePermit> permitsToAnalyzeForLastAcceptedCase = new ArrayList<CompliancePermit>();

	Boolean debug = false;

	public AnalyzeJuaComplianceFiles() 
	{
		resultsMessage = "Started analyzing JUA Compliance at "+ConvertDateTime.getTimeStamp()+"\n";

		// Trains - This Case
		if (BIASJuaComplianceConfigController.getCheckEnabledCountOfTrains())
		{
			// For each train
			trainsToAnalyzeForComplianceThisCase.clear();
			seedTrainsFoundEligibleThisCase.clear();
			seedTrainsFoundNotEligibleThisCase.clear();
			seedTrainSymbolsFoundEligibleThisCase.clear();
			seedTrainSymbolsFoundNotEligibleThisCase.clear();

			brightlineTrainTypesFromConfigFile.clear();
			fecTrainTypesFromConfigFile.clear();
			triRailTrainTypesFromConfigFile.clear();

			brightlineNodesFromConfigFile.clear();
			fecNodesFromConfigFile.clear();
			triRailNodesFromConfigFile.clear();

			// Populate all arrays with necessary objects
			trainsToAnalyzeForComplianceThisCase.addAll(ReadJuaComplianceFiles.getTrainsToAnalyzeThisCase());
			seedTrainsFoundNotEligibleThisCase.addAll(trainsToAnalyzeForComplianceThisCase);

			for (int i = 0; i < trainsToAnalyzeForComplianceThisCase.size(); i++)
			{
				seedTrainSymbolsFoundNotEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
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
			totalCountOfBrightlineOperatedTrainsThisCase = 0;
			totalCountOfFecOperatedTrainsThisCase = 0;
			totalCountOfTriRailOperatedTrainsThisCase = 0;
			statisticalStartDay = ReadJuaComplianceFiles.getStatisticalStartDayOfWeekAsInteger();		 
			statisticalEndDay = statisticalStartDay + ReadJuaComplianceFiles.getStatisticalDurationInDays(); 
			// For each train
			for (int i = 0; i < trainsToAnalyzeForComplianceThisCase.size(); i++)
			{
				// Check Brightline type
				if (brightlineTrainTypesFromConfigFile.contains(trainsToAnalyzeForComplianceThisCase.get(i).getType().toUpperCase()))
				{
					// Check if enabled
					if (trainsToAnalyzeForComplianceThisCase.get(i).getEnabled().toUpperCase().equals("YES"))
					{
						// Check if through a valid node
						for (int k = 0; k < trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().size(); k++)
						{
							if (brightlineNodesFromConfigFile.contains(trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().get(k).getNode()))
							{
								// Check how many times train operates during statistical period
								if (trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().size() == 0)
								{
									String trainToBaseScheduleOn = trainsToAnalyzeForComplianceThisCase.get(i).getSymbol();
									ComplianceTrain train = trainsToAnalyzeForComplianceThisCase.get(i);
									seedTrainsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i));
									seedTrainSymbolsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
									do
									{
										for (int j = 0; j < trainsToAnalyzeForComplianceThisCase.size(); j++)
										{
											if (trainToBaseScheduleOn.equals(trainsToAnalyzeForComplianceThisCase.get(j).getSymbol()))
											{
												train = trainsToAnalyzeForComplianceThisCase.get(j);
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
											totalCountOfBrightlineOperatedTrainsThisCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i));
											seedTrainSymbolsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
											// Increment the by type counter
										}
									}
								}
								else
								{
									seedTrainsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i));
									seedTrainSymbolsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
									for (int j = 0; j < trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().size(); j++)
									{
										if ((trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
												&& (trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
										{
											totalCountOfBrightlineOperatedTrainsThisCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i));
											seedTrainSymbolsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
											// Increment the by type counter
										}
									}
								}
							}
						}
					}
				}				
				// Check FEC type
				else if (fecTrainTypesFromConfigFile.contains(trainsToAnalyzeForComplianceThisCase.get(i).getType().toUpperCase()))
				{
					// Check if enabled
					if (trainsToAnalyzeForComplianceThisCase.get(i).getEnabled().toUpperCase().equals("YES"))
					{
						// Check if through a valid node
						for (int k = 0; k < trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().size(); k++)
						{
							if (fecNodesFromConfigFile.contains(trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().get(k).getNode()))
							{
								// Check how many times train operates during statistical period
								if (trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().size() == 0)
								{
									String trainToBaseScheduleOn = trainsToAnalyzeForComplianceThisCase.get(i).getSymbol();
									ComplianceTrain train = trainsToAnalyzeForComplianceThisCase.get(i);
									seedTrainsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i));
									seedTrainSymbolsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
									do
									{
										for (int j = 0; j < trainsToAnalyzeForComplianceThisCase.size(); j++)
										{
											if (trainToBaseScheduleOn.equals(trainsToAnalyzeForComplianceThisCase.get(j).getSymbol()))
											{
												train = trainsToAnalyzeForComplianceThisCase.get(j);
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
											totalCountOfFecOperatedTrainsThisCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i));
											seedTrainSymbolsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
											// Increment the by type counter
										}
									}
								}
								else
								{
									seedTrainsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i));
									seedTrainSymbolsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
									for (int j = 0; j < trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().size(); j++)
									{
										if ((trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
												&& (trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
										{
											totalCountOfFecOperatedTrainsThisCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i));
											seedTrainSymbolsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
											// Increment the by type counter
										}
									}
								}
							}
						}
					}
				}
				// Check TriRail type
				else if (triRailTrainTypesFromConfigFile.contains(trainsToAnalyzeForComplianceThisCase.get(i).getType().toUpperCase()))
				{
					// Check if enabled
					if (trainsToAnalyzeForComplianceThisCase.get(i).getEnabled().toUpperCase().equals("YES"))
					{
						// Check if through a valid node
						for (int k = 0; k < trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().size(); k++)
						{
							if (triRailNodesFromConfigFile.contains(trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().get(k).getNode()))
							{
								// Check how many times train operates during statistical period
								if (trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().size() == 0)
								{
									String trainToBaseScheduleOn = trainsToAnalyzeForComplianceThisCase.get(i).getSymbol();
									ComplianceTrain train = trainsToAnalyzeForComplianceThisCase.get(i);
									seedTrainsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i));
									seedTrainSymbolsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
									do
									{
										for (int j = 0; j < trainsToAnalyzeForComplianceThisCase.size(); j++)
										{
											if (trainToBaseScheduleOn.equals(trainsToAnalyzeForComplianceThisCase.get(j).getSymbol()))
											{
												train = trainsToAnalyzeForComplianceThisCase.get(j);
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
											totalCountOfTriRailOperatedTrainsThisCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i));
											seedTrainSymbolsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
											// Increment the by type counter
										}
									}
								}
								else
								{
									seedTrainsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i));
									seedTrainSymbolsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
									for (int j = 0; j < trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().size(); j++)
									{
										if ((trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
												&& (trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
										{
											totalCountOfTriRailOperatedTrainsThisCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i));
											seedTrainSymbolsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
											// Increment the by type counter
										}
									}
								}
							}
						}
					}
				}
			}

			resultsMessage += "Found "+seedTrainsFoundEligibleThisCase.size()+" eligible seed trains operated in the selected case:\n";
			resultsMessage += "  yielding "+totalCountOfBrightlineOperatedTrainsThisCase+" dispatched Brightline trains operated during the statistical period of the selected case\n";
			resultsMessage += "  yielding "+totalCountOfFecOperatedTrainsThisCase+" dispatched FEC trains operated during the statistical period of the selected case\n";
			resultsMessage += "  yielding "+totalCountOfTriRailOperatedTrainsThisCase+" dispatched TriRail trains operated during the statistical period of the selected case\n";
			resultsMessage += "Found "+seedTrainsFoundNotEligibleThisCase.size()+" other seed trains in the selected case\n\n";
		}

		// Trains - Last Accepted Case
		if (BIASJuaComplianceConfigController.getCheckLastAcceptedTrainsFile())
		{
			// For each train
			trainsToAnalyzeForComplianceLastAcceptedCase.clear();
			seedTrainsFoundEligibleLastAcceptedCase.clear();
			seedTrainsFoundNotEligibleLastAcceptedCase.clear();
			seedTrainSymbolsFoundEligibleLastAcceptedCase.clear();
			seedTrainSymbolsFoundNotEligibleLastAcceptedCase.clear();

			brightlineTrainTypesFromConfigFile.clear();
			fecTrainTypesFromConfigFile.clear();
			triRailTrainTypesFromConfigFile.clear();

			brightlineNodesFromConfigFile.clear();
			fecNodesFromConfigFile.clear();
			triRailNodesFromConfigFile.clear();

			// Populate all arrays with necessary objects
			trainsToAnalyzeForComplianceLastAcceptedCase.addAll(ReadJuaComplianceFiles.getTrainsToAnalyzeLastAcceptedCase());
			seedTrainsFoundNotEligibleLastAcceptedCase.addAll(trainsToAnalyzeForComplianceLastAcceptedCase);

			for (int i = 0; i < trainsToAnalyzeForComplianceLastAcceptedCase.size(); i++)
			{
				seedTrainSymbolsFoundNotEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
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
			totalCountOfBrightlineOperatedTrainsLastAcceptedCase = 0;
			totalCountOfFecOperatedTrainsLastAcceptedCase = 0;
			totalCountOfTriRailOperatedTrainsLastAcceptedCase = 0;
			statisticalStartDay = ReadJuaComplianceFiles.getStatisticalStartDayOfWeekAsInteger();		 
			statisticalEndDay = statisticalStartDay + ReadJuaComplianceFiles.getStatisticalDurationInDays(); 

			// For each train
			for (int i = 0; i < trainsToAnalyzeForComplianceLastAcceptedCase.size(); i++)
			{
				// Check Brightline type
				if (brightlineTrainTypesFromConfigFile.contains(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType().toUpperCase()))
				{
					// Check if enabled
					if (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getEnabled().toUpperCase().equals("YES"))
					{
						// Check if through a valid node
						for (int k = 0; k < trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().size(); k++)
						{
							if (brightlineNodesFromConfigFile.contains(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().get(k).getNode()))
							{
								// Check how many times train operates during statistical period
								if (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().size() == 0)
								{
									String trainToBaseScheduleOn = trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol();
									ComplianceTrain train = trainsToAnalyzeForComplianceLastAcceptedCase.get(i);
									seedTrainsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
									seedTrainSymbolsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
									do
									{
										for (int j = 0; j < trainsToAnalyzeForComplianceLastAcceptedCase.size(); j++)
										{
											if (trainToBaseScheduleOn.equals(trainsToAnalyzeForComplianceLastAcceptedCase.get(j).getSymbol()))
											{
												train = trainsToAnalyzeForComplianceLastAcceptedCase.get(j);
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
											totalCountOfBrightlineOperatedTrainsLastAcceptedCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
											seedTrainSymbolsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
											// Increment the by type counter
										}
									}
								}
								else
								{
									seedTrainsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
									seedTrainSymbolsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
									for (int j = 0; j < trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().size(); j++)
									{
										if ((trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
												&& (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
										{
											totalCountOfBrightlineOperatedTrainsLastAcceptedCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
											seedTrainSymbolsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
											// Increment the by type counter
										}
									}
								}
							}
						}
					}
				}
				// Check FEC type
				else if (fecTrainTypesFromConfigFile.contains(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType().toUpperCase()))
				{
					// Check if enabled
					if (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getEnabled().toUpperCase().equals("YES"))
					{
						// Check if through a valid node
						for (int k = 0; k < trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().size(); k++)
						{
							if (fecNodesFromConfigFile.contains(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().get(k).getNode()))
							{
								// Check how many times train operates during statistical period
								if (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().size() == 0)
								{
									String trainToBaseScheduleOn = trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol();
									ComplianceTrain train = trainsToAnalyzeForComplianceLastAcceptedCase.get(i);
									seedTrainsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
									seedTrainSymbolsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
									do
									{
										for (int j = 0; j < trainsToAnalyzeForComplianceLastAcceptedCase.size(); j++)
										{
											if (trainToBaseScheduleOn.equals(trainsToAnalyzeForComplianceLastAcceptedCase.get(j).getSymbol()))
											{
												train = trainsToAnalyzeForComplianceLastAcceptedCase.get(j);
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
											totalCountOfFecOperatedTrainsLastAcceptedCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
											seedTrainSymbolsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
											// Increment the by type counter
										}
									}
								}
								else
								{
									seedTrainsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
									seedTrainSymbolsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
									for (int j = 0; j < trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().size(); j++)
									{
										if ((trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
												&& (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
										{
											totalCountOfFecOperatedTrainsLastAcceptedCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
											seedTrainSymbolsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
											// Increment the by type counter
										}
									}
								}
							}
						}
					}
				}
				// Check TriRail type
				else if (triRailTrainTypesFromConfigFile.contains(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType().toUpperCase()))
				{
					// Check if enabled
					if (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getEnabled().toUpperCase().equals("YES"))
					{
						// Check if through a valid node
						for (int k = 0; k < trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().size(); k++)
						{
							if (triRailNodesFromConfigFile.contains(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().get(k).getNode()))
							{
								// Check how many times train operates during statistical period
								if (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().size() == 0)
								{
									String trainToBaseScheduleOn = trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol();
									ComplianceTrain train = trainsToAnalyzeForComplianceLastAcceptedCase.get(i);
									seedTrainsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
									seedTrainSymbolsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
									do
									{
										for (int j = 0; j < trainsToAnalyzeForComplianceLastAcceptedCase.size(); j++)
										{
											if (trainToBaseScheduleOn.equals(trainsToAnalyzeForComplianceLastAcceptedCase.get(j).getSymbol()))
											{
												train = trainsToAnalyzeForComplianceLastAcceptedCase.get(j);
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
											totalCountOfTriRailOperatedTrainsLastAcceptedCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
											seedTrainSymbolsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
											// Increment the by type counter
										}
									}
								}
								else
								{
									seedTrainsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
									seedTrainSymbolsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
									for (int j = 0; j < trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().size(); j++)
									{
										if ((trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
												&& (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().get(j) <= statisticalEndDay)) 
										{
											totalCountOfTriRailOperatedTrainsLastAcceptedCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
											seedTrainSymbolsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
											// Increment the by type counter
										}
									}
								}
							}
						}
					}
				}
			}

			resultsMessage += "Found "+seedTrainsFoundEligibleLastAcceptedCase.size()+" eligible seed trains operated in the last approved .TRAIN file:\n";
			resultsMessage += "  yielding "+totalCountOfBrightlineOperatedTrainsLastAcceptedCase+" dispatched Brightline trains operated during the statistical period in the last approved .TRAIN file\n";
			resultsMessage += "  yielding "+totalCountOfFecOperatedTrainsLastAcceptedCase+" dispatched FEC trains operated during the statistical period in the last approved .TRAIN file\n";
			resultsMessage += "  yielding "+totalCountOfTriRailOperatedTrainsLastAcceptedCase+" dispatched TriRail trains operated during the statistical period in the last approved .TRAIN file\n";
			resultsMessage += "Found "+seedTrainsFoundNotEligibleLastAcceptedCase.size()+" other seed trains in the last approved .TRAIN file\n\n";

			// Compare both files

			// Check seed train count
			// Check seed train end-to-end scheduled duration
			// Check seed train work event count
			// Check seed train DOW
			// Check seed type
		}

		// Permits - Common
		if (BIASJuaComplianceConfigController.getCheckPermitsEnabled())
		{
			permitsToAnalyzeForThisCase.clear();
			permitsToAnalyzeForLastAcceptedCase.clear();
			permitsToAnalyzeForThisCase.addAll(ReadJuaComplianceFiles.getPermitsToAnalyzeThisCase());
			permitsToAnalyzeForLastAcceptedCase.addAll(ReadJuaComplianceFiles.getPermitsToAnalyzeLastAcceptedCase());

			// Get bridge MPs to exclude (if any)
			ArrayList<Double> bridgeMpsToExclude = new ArrayList<Double>();
			for (int i = 0; i < BIASJuaComplianceConfigController.getBridgeMps().length; i++)
			{
				bridgeMpsToExclude.add(Double.valueOf(BIASJuaComplianceConfigController.getBridgeMps()[i].trim()));
			}

			resultsMessage += "Found " + permitsToAnalyzeForThisCase.size() + " permits in this case's .PERMIT file and " + permitsToAnalyzeForLastAcceptedCase.size() + " permits in the last accepted .PERMIT file:\n";

			// Compute miles of affected track, average slow order speed and miles * duration
			if (BIASJuaComplianceConfigController.getCheckPermitsSumOfTrackMiles())
			{
				milesOfAffectedTrackThisCase = 0.0;
				milesOfAffectedTrackLastAcceptedCase = 0.0;
				averageSlowOrderPassengerSpeedThisCase = 0.0;
				averageSlowOrderFreightSpeedThisCase = 0.0;
				averageSlowOrderPassengerSpeedLastAcceptedCase = 0.0;
				averageSlowOrderFreightSpeedLastAcceptedCase = 0.0;
				hoursMilesThisCase = 0.0;
				hoursMilesLastAcceptedCase = 0.0;
				Double sumOfSlowOrderPassengerSpeedThisCase = 0.0;
				Double sumOfSlowOrderFreightSpeedThisCase = 0.0;
				Double sumOfSlowOrderPassengerSpeedLastAcceptedCase = 0.0;
				Double sumOfSlowOrderFreightSpeedLastAcceptedCase = 0.0;
				permitsConsideredThisCase = 0;
				permitsConsideredLastAcceptedCase = 0;

				if (debug)
				{
					System.out.println("The statistical start day is "+statisticalStartDay);
					System.out.println("The statistical end day is "+statisticalEndDay);
				}

				if (BIASJuaComplianceConfigController.getCheckEnabledPermitsOnly())
					resultsMessage += "Checking enabled permits only, ";
				else
					resultsMessage += "Checking all permits (enabled and disabled), ";

				if (BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly())
					resultsMessage += "within the statistical period only, \n";
				else
					resultsMessage += "within and outside the statistical period, \n";

				if (BIASJuaComplianceConfigController.getExcludePermitsNearBridge())
					resultsMessage += " and excluding permits encompassing bridges near ";
				else
					resultsMessage += " and including permits encompassing a bridge\n";

				if (BIASJuaComplianceConfigController.getExcludePermitsNearBridge())
				{
					for (int i = 0; i < BIASJuaComplianceConfigController.getBridgeMps().length; i++)
					{
						if (i == BIASJuaComplianceConfigController.getBridgeMps().length - 1)
							resultsMessage += BIASJuaComplianceConfigController.getBridgeMps()[i];
						else
							resultsMessage += BIASJuaComplianceConfigController.getBridgeMps()[i]+", ";
					}
					resultsMessage += "\n";
				}

				// Permits - This case
				for (int i = 0; i < permitsToAnalyzeForThisCase.size(); i++)
				{
					Boolean permitContainsABridgeMp = false;
					for (int j = 0; j < bridgeMpsToExclude.size(); j++)
					{
						if ((permitsToAnalyzeForThisCase.get(i).getBeginMp() <= bridgeMpsToExclude.get(j)) && (permitsToAnalyzeForThisCase.get(i).getEndMp() >= bridgeMpsToExclude.get(j)))
						{
							permitContainsABridgeMp = true;
							break;
						}
					}

					if ((BIASJuaComplianceConfigController.getCheckEnabledPermitsOnly()) && (permitsToAnalyzeForThisCase.get(i).getEnabled().equals("YES"))) // If check enabled
					{
						if (debug)
						{
							System.out.println(" Enabled permit start time in This Case is "+permitsToAnalyzeForThisCase.get(i).getStartTime() +" and end time in This Case is "+permitsToAnalyzeForThisCase.get(i).getEndTime());
							System.out.println(" Enabled permit MPs are between "+permitsToAnalyzeForThisCase.get(i).getBeginMp() +" and "+permitsToAnalyzeForThisCase.get(i).getEndMp());
							if ((permitContainsABridgeMp) && (BIASJuaComplianceConfigController.getExcludePermitsNearBridge()))
								System.out.println(" Enabled permit is excluded as it contains a bridge");
						}

						if (((permitContainsABridgeMp) && (BIASJuaComplianceConfigController.getExcludePermitsNearBridge())))
						{
							continue;
						}
						else
						{

							if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit wholly within statistical period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime()) >= statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime()) < statisticalEndDay)) 
							{
								if (debug)
									System.out.println("  Case 1");
								milesOfAffectedTrackThisCase += Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getFrtSpeed();
								Double timeStart = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime());
								Double timeEnd = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime());

								hoursMilesThisCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp()));
								permitsConsideredThisCase++;
							}	
							else if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit starts and ends before statistical period starts
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime()) < statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime()) < statisticalStartDay)) 
							{
								if (debug)
									System.out.println("  Case 2");
								continue;
							}
							else if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit starts and ends after statistical period ends
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime()) > statisticalEndDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime()) > statisticalEndDay)) 
							{
								if (debug)
									System.out.println("  Case 3");
								continue;
							}
							else if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit starts before statistical period and ends during statistical period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime()) < statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime()) < statisticalEndDay)) 
							{
								if (debug)
									System.out.println("  Case 4");
								milesOfAffectedTrackThisCase += Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getFrtSpeed();
								Double timeStart = (double) statisticalStartDay;
								Double timeEnd = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime());

								hoursMilesThisCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp()));
								permitsConsideredThisCase++;							
							}
							else if((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly())  // Permit starts during statistical period and ends after statistical period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime()) >= statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime()) < statisticalEndDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime()) > statisticalEndDay))
							{
								if (debug)
									System.out.println("  Case 5");
								milesOfAffectedTrackThisCase += Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getFrtSpeed();
								Double timeStart = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime());
								Double timeEnd = (double) statisticalEndDay - (1/86400);

								hoursMilesThisCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp()));
								permitsConsideredThisCase++;	

							}
							else if((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly())  // Permit starts before statistical period and ends after statistical period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime()) < statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime()) > statisticalEndDay))
							{
								if (debug)
									System.out.println("  Case 6");
								milesOfAffectedTrackThisCase += Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getFrtSpeed();
								Double timeStart = (double) statisticalStartDay;
								Double timeEnd = (double) statisticalEndDay;

								hoursMilesThisCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp()));
								permitsConsideredThisCase++;	

							}
							else if(!BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Statistical period not considered
							{
								if (debug)
									System.out.println("  Case 7");
								milesOfAffectedTrackThisCase += Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getFrtSpeed();
								Double timeStart = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime());
								Double timeEnd = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime());

								hoursMilesThisCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp()));
								permitsConsideredThisCase++;
							}
						}
					}
					else if (!BIASJuaComplianceConfigController.getCheckEnabledPermitsOnly()) // Not enabled
					{
						if (debug)
						{
							System.out.println(" All permit start time in This Case is "+permitsToAnalyzeForThisCase.get(i).getStartTime() +" and end time in This Case is "+permitsToAnalyzeForThisCase.get(i).getEndTime());
							System.out.println(" All permit MPs are between "+permitsToAnalyzeForThisCase.get(i).getBeginMp() +" and "+permitsToAnalyzeForThisCase.get(i).getEndMp());
							if ((permitContainsABridgeMp) && (BIASJuaComplianceConfigController.getExcludePermitsNearBridge()))
								System.out.println(" Permit is excluded as it contains a bridge");
						}

						if (((permitContainsABridgeMp) && (BIASJuaComplianceConfigController.getExcludePermitsNearBridge())))
						{
							continue;
						}
						else
						{
							if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit wholly within statistical Period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime()) >= statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime()) < statisticalEndDay)) 
							{
								if (debug)
									System.out.println("  Case 8");
								milesOfAffectedTrackThisCase += Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getFrtSpeed();
								Double timeStart = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime());
								Double timeEnd = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime());

								hoursMilesThisCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp()));
								permitsConsideredThisCase++;
							}	
							else if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit starts and ends before statistical period starts
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime()) < statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime()) < statisticalStartDay)) 
							{
								if (debug)
									System.out.println("  Case 9");
								continue;
							}
							else if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit starts and ends after statistical period ends
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime()) > statisticalEndDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime()) > statisticalEndDay)) 
							{
								if (debug)
									System.out.println("  Case 10");
								continue;
							}
							else if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit starts before statistical period and ends during statistical period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime()) < statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime()) < statisticalEndDay)) 
							{
								if (debug)
									System.out.println("  Case 11");
								milesOfAffectedTrackThisCase += Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getFrtSpeed();
								Double timeStart = (double) statisticalStartDay;
								Double timeEnd = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime());

								hoursMilesThisCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp()));
								permitsConsideredThisCase++;							
							}
							else if((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly())  // Permit starts during statistical period and ends after statistical period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime()) >= statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime()) < statisticalEndDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime()) > statisticalEndDay))
							{
								if (debug)
									System.out.println("  Case 12");
								milesOfAffectedTrackThisCase += Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getFrtSpeed();
								Double timeStart = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime());
								Double timeEnd = (double) statisticalEndDay - (1/86400);

								hoursMilesThisCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp()));
								permitsConsideredThisCase++;	

							}
							else if((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly())  // Permit starts before statistical period and ends after statistical period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime()) < statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime()) > statisticalEndDay))
							{
								if (debug)
									System.out.println("  Case 13");
								milesOfAffectedTrackThisCase += Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getFrtSpeed();
								Double timeStart = (double) statisticalStartDay;
								Double timeEnd = (double) statisticalEndDay;

								hoursMilesThisCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp()));
								permitsConsideredThisCase++;	

							}
							else if(!BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Statistical period not considered
							{
								if (debug)
									System.out.println("  Case 14");
								milesOfAffectedTrackThisCase += Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedThisCase += permitsToAnalyzeForThisCase.get(i).getFrtSpeed();
								Double timeStart = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getStartTime());
								Double timeEnd = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForThisCase.get(i).getEndTime());

								hoursMilesThisCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForThisCase.get(i).getEndMp() - permitsToAnalyzeForThisCase.get(i).getBeginMp()));
								permitsConsideredThisCase++;
							}
						}
					}
				}

				// Permits - Last accepted case
				for (int i = 0; i < permitsToAnalyzeForLastAcceptedCase.size(); i++)
				{
					Boolean permitContainsABridgeMp = false;
					for (int j = 0; j < bridgeMpsToExclude.size(); j++)
					{
						if ((permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp() <= bridgeMpsToExclude.get(j)) && (permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() >= bridgeMpsToExclude.get(j)))
						{
							permitContainsABridgeMp = true;
							break;
						}
					}

					if ((BIASJuaComplianceConfigController.getCheckEnabledPermitsOnly()) && (permitsToAnalyzeForLastAcceptedCase.get(i).getEnabled().equals("YES"))) // If check enabled
					{
						if (debug)
						{
							System.out.println(" Enabled permit start time in Last Accepted Case is "+permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime() +" and end time in Last Accepted Case is "+permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime());
							System.out.println(" Enabled permit MPs are between "+permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp() +" and "+permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp());
							if ((permitContainsABridgeMp) && (BIASJuaComplianceConfigController.getExcludePermitsNearBridge()))
								System.out.println(" Permit is excluded as it contains a bridge");
						}

						if (((permitContainsABridgeMp) && (BIASJuaComplianceConfigController.getExcludePermitsNearBridge())))
						{
							continue;
						}
						else
						{
							if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit wholly within statistical Period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime()) >= statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime()) < statisticalEndDay)) 
							{
								if (debug)
									System.out.println("  Case 21");
								milesOfAffectedTrackLastAcceptedCase += Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getFrtSpeed();
								Double timeStart = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime());
								Double timeEnd = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime());

								hoursMilesLastAcceptedCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp()));
								permitsConsideredLastAcceptedCase++;
							}	
							else if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit starts and ends before statistical period starts
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime()) < statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime()) < statisticalStartDay)) 
							{
								if (debug)
									System.out.println("  Case 22");
								continue;
							}
							else if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit starts and ends after statistical period ends
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime()) > statisticalEndDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime()) > statisticalEndDay)) 
							{
								if (debug)
									System.out.println("  Case 23");
								continue;
							}
							else if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit starts before statistical period and ends during statistical period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime()) < statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime()) < statisticalEndDay)) 
							{
								if (debug)
									System.out.println("  Case 24");
								milesOfAffectedTrackLastAcceptedCase += Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getFrtSpeed();
								Double timeStart = (double) statisticalStartDay;
								Double timeEnd = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime());

								hoursMilesLastAcceptedCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp()));
								permitsConsideredLastAcceptedCase++;							
							}
							else if((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly())  // Permit starts during statistical period and ends after statistical period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime()) >= statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime()) < statisticalEndDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime()) > statisticalEndDay))
							{
								if (debug)
									System.out.println("  Case 25");
								milesOfAffectedTrackLastAcceptedCase += Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getFrtSpeed();
								Double timeStart = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime());
								Double timeEnd = (double) statisticalEndDay - (1/86400);

								hoursMilesLastAcceptedCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp()));
								permitsConsideredLastAcceptedCase++;	

							}
							else if((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly())  // Permit starts before statistical period and ends after statistical period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime()) < statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime()) > statisticalEndDay))
							{
								if (debug)
									System.out.println("  Case 26");
								milesOfAffectedTrackLastAcceptedCase += Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getFrtSpeed();
								Double timeStart = (double) statisticalStartDay;
								Double timeEnd = (double) statisticalEndDay;

								hoursMilesLastAcceptedCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp()));
								permitsConsideredLastAcceptedCase++;	

							}
							else if(!BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Statistical period not considered
							{
								if (debug)
									System.out.println("  Case 27");
								milesOfAffectedTrackLastAcceptedCase += Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getFrtSpeed();
								Double timeStart = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime());
								Double timeEnd = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime());

								hoursMilesLastAcceptedCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp()));
								permitsConsideredLastAcceptedCase++;
							}
						}
					}
					else if (!BIASJuaComplianceConfigController.getCheckEnabledPermitsOnly()) // Not enabled
					{
						if (debug)
						{
							System.out.println(" All permits start time in This Case is "+permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime() +" and end time in This Case is "+permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime());
							System.out.println(" All permits MPs are between "+permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp() +" and "+permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp());
							if ((permitContainsABridgeMp) && (BIASJuaComplianceConfigController.getExcludePermitsNearBridge()))
								System.out.println(" Permit is excluded as it contains a bridge");
						}

						if (((permitContainsABridgeMp) && (BIASJuaComplianceConfigController.getExcludePermitsNearBridge())))
						{
							continue;
						}
						else
						{
							if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit wholly within statistical Period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime()) >= statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime()) < statisticalEndDay)) 
							{
								if (debug)
									System.out.println("  Case 28");
								milesOfAffectedTrackLastAcceptedCase += Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getFrtSpeed();
								Double timeStart = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime());
								Double timeEnd = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime());

								hoursMilesLastAcceptedCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp()));
								permitsConsideredLastAcceptedCase++;
							}	
							else if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit starts and ends before statistical period starts
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime()) < statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime()) < statisticalStartDay)) 
							{
								if (debug)
									System.out.println("  Case 29");
								continue;
							}
							else if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit starts and ends after statistical period ends
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime()) > statisticalEndDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime()) > statisticalEndDay)) 
							{
								if (debug)
									System.out.println("  Case 30");
								continue;
							}
							else if ((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Permit starts before statistical period and ends during statistical period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime()) < statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime()) < statisticalEndDay)) 
							{
								if (debug)
									System.out.println("  Case 31");
								milesOfAffectedTrackLastAcceptedCase += Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getFrtSpeed();
								Double timeStart = (double) statisticalStartDay;
								Double timeEnd = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime());

								hoursMilesLastAcceptedCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp()));
								permitsConsideredLastAcceptedCase++;							
							}
							else if((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly())  // Permit starts during statistical period and ends after statistical period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime()) >= statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime()) < statisticalEndDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime()) > statisticalEndDay))
							{
								if (debug)
									System.out.println("  Case 32");
								milesOfAffectedTrackLastAcceptedCase += Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getFrtSpeed();
								Double timeStart = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime());
								Double timeEnd = (double) statisticalEndDay - (1/86400);

								hoursMilesLastAcceptedCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp()));
								permitsConsideredLastAcceptedCase++;	

							}
							else if((BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly())  // Permit starts before statistical period and ends after statistical period
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime()) < statisticalStartDay) 
									&& (ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime()) > statisticalEndDay))
							{
								if (debug)
									System.out.println("  Case 33");
								milesOfAffectedTrackLastAcceptedCase += Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getFrtSpeed();
								Double timeStart = (double) statisticalStartDay;
								Double timeEnd = (double) statisticalEndDay;

								hoursMilesLastAcceptedCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp()));
								permitsConsideredLastAcceptedCase++;	

							}
							else if(!BIASJuaComplianceConfigController.getCheckStatisticalPeriodOnly()) // Statistical period not considered
							{
								if (debug)
									System.out.println("  Case 34");
								milesOfAffectedTrackLastAcceptedCase += Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp());
								sumOfSlowOrderPassengerSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getPasSpeed();
								sumOfSlowOrderFreightSpeedLastAcceptedCase += permitsToAnalyzeForLastAcceptedCase.get(i).getFrtSpeed();
								Double timeStart = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getStartTime());
								Double timeEnd = ConvertDateTime.convertDDHHMMSSStringToSerial(permitsToAnalyzeForLastAcceptedCase.get(i).getEndTime());

								hoursMilesLastAcceptedCase += ((timeEnd - timeStart) * Math.abs(permitsToAnalyzeForLastAcceptedCase.get(i).getEndMp() - permitsToAnalyzeForLastAcceptedCase.get(i).getBeginMp()));
								permitsConsideredLastAcceptedCase++;
							}
						}
					}
				}

				averageSlowOrderPassengerSpeedThisCase = sumOfSlowOrderPassengerSpeedThisCase/permitsToAnalyzeForThisCase.size();
				averageSlowOrderFreightSpeedThisCase = sumOfSlowOrderFreightSpeedThisCase/permitsToAnalyzeForThisCase.size();
				averageSlowOrderPassengerSpeedLastAcceptedCase = sumOfSlowOrderPassengerSpeedLastAcceptedCase/permitsToAnalyzeForLastAcceptedCase.size();
				averageSlowOrderFreightSpeedLastAcceptedCase = sumOfSlowOrderFreightSpeedLastAcceptedCase/permitsToAnalyzeForLastAcceptedCase.size();
			}
		}

		resultsMessage += "Finished analyzing JUA Compliance at "+ConvertDateTime.getTimeStamp()+("\n");
	}

	public static Integer getTotalCountOfBrightlineOperatedTrainsThisCase()
	{
		return totalCountOfBrightlineOperatedTrainsThisCase;
	}

	public static Integer getTotalCountOfBrightlineOperatedTrainsLastAcceptedCase()
	{
		return totalCountOfBrightlineOperatedTrainsLastAcceptedCase;
	}

	public static Integer getTotalCountOfFecOperatedTrainsThisCase()
	{
		return totalCountOfFecOperatedTrainsThisCase;
	}

	public static Integer getTotalCountOfFecOperatedTrainsLastAcceptedCase()
	{
		return totalCountOfFecOperatedTrainsLastAcceptedCase;
	}

	public static Integer getTotalCountOfTriRailOperatedTrainsThisCase()
	{
		return totalCountOfTriRailOperatedTrainsThisCase;
	}

	public static Integer getTotalCountOfTriRailOperatedTrainsLastAcceptedCase()
	{
		return totalCountOfTriRailOperatedTrainsLastAcceptedCase;
	}

	public static Double getDailyAverageCountOfBrightlineOperatedTrainsThisCase()
	{
		dailyAverageCountOfBrightlineOperatedTrainsThisCase = (double) totalCountOfBrightlineOperatedTrainsThisCase / (double) (statisticalEndDay - statisticalStartDay + 1);

		return dailyAverageCountOfBrightlineOperatedTrainsThisCase;
	}

	public static Double getDailyAverageCountOfBrightlineOperatedTrainsLastAcceptedCase()
	{
		dailyAverageCountOfBrightlineOperatedTrainsLastAcceptedCase = (double) totalCountOfBrightlineOperatedTrainsLastAcceptedCase / (double) (statisticalEndDay - statisticalStartDay + 1);

		return dailyAverageCountOfBrightlineOperatedTrainsLastAcceptedCase;
	}

	public static Double getDailyAverageCountOfFecOperatedTrainsThisCase()
	{
		dailyAverageCountOfFecOperatedTrainsThisCase = (double) totalCountOfFecOperatedTrainsThisCase / (double) (statisticalEndDay - statisticalStartDay + 1);

		return dailyAverageCountOfFecOperatedTrainsThisCase;
	}

	public static Double getDailyAverageCountOfFecOperatedTrainsLastAcceptedCase()
	{
		dailyAverageCountOfFecOperatedTrainsLastAcceptedCase = (double) totalCountOfFecOperatedTrainsLastAcceptedCase / (double) (statisticalEndDay - statisticalStartDay + 1);

		return dailyAverageCountOfFecOperatedTrainsLastAcceptedCase;
	}

	public static Double getDailyAverageCountOfTriRailOperatedTrainsLastAcceptedCase()
	{
		dailyAverageCountOfTriRailOperatedTrainsLastAcceptedCase = (double) totalCountOfTriRailOperatedTrainsLastAcceptedCase / (double) (statisticalEndDay - statisticalStartDay + 1);

		return dailyAverageCountOfTriRailOperatedTrainsLastAcceptedCase;
	}

	public static Double getDailyAverageCountOfTriRailOperatedTrainsThisCase()
	{
		dailyAverageCountOfTriRailOperatedTrainsThisCase = (double) totalCountOfTriRailOperatedTrainsThisCase / (double) (statisticalEndDay - statisticalStartDay + 1);

		return dailyAverageCountOfTriRailOperatedTrainsThisCase;
	}

	public static ArrayList<ComplianceTrain> getSeedTrainsFoundEligibleThisCase()
	{
		return seedTrainsFoundEligibleThisCase;
	}

	public static ArrayList<ComplianceTrain> getSeedTrainsFoundNotEligibleThisCase()
	{
		return seedTrainsFoundNotEligibleThisCase;
	}

	public static ArrayList<ComplianceTrain> getSeedTrainsFoundEligibleLastAcceptedCase()
	{
		return seedTrainsFoundEligibleLastAcceptedCase;
	}

	public static ArrayList<ComplianceTrain> getSeedTrainsFoundNotEligibleLastAcceptedCase()
	{
		return seedTrainsFoundNotEligibleLastAcceptedCase;
	}

	public static ArrayList<String> getSeedTrainSymbolsFoundEligibleThisCase()
	{
		return seedTrainSymbolsFoundEligibleThisCase;
	}

	public static ArrayList<String> getSeedTrainSymbolsFoundNotEligibleThisCase()
	{
		return seedTrainSymbolsFoundNotEligibleThisCase;
	}

	public static ArrayList<String> getSeedTrainSymbolsFoundEligibleLastAcceptedCase()
	{
		return seedTrainSymbolsFoundEligibleLastAcceptedCase;
	}

	public static ArrayList<String> getSeedTrainSymbolsFoundNotEligibleLastAcceptedCase()
	{
		return seedTrainSymbolsFoundNotEligibleLastAcceptedCase;
	}

	public static Integer getSeedTrainSymbolCountFoundEligibleThisCase()
	{
		return seedTrainSymbolsFoundEligibleThisCase.size();
	}

	public static Integer getSeedTrainSymbolCountFoundNotEligibleThisCase()
	{
		return seedTrainSymbolsFoundNotEligibleThisCase.size();
	}

	public static Integer getSeedTrainSymbolCountFoundEligibleLastAcceptedCase()
	{
		return seedTrainSymbolsFoundEligibleLastAcceptedCase.size();
	}

	public static Integer getSeedTrainSymbolCountFoundNotEligibleLastAcceptedCase()
	{
		return seedTrainSymbolsFoundNotEligibleLastAcceptedCase.size();
	}

	public static Double getMilesOfImpactedTrackThisCase()
	{
		return  milesOfAffectedTrackThisCase;
	}

	public static Double getMilesOfImpactedTrackLastAcceptedCase()
	{
		return  milesOfAffectedTrackLastAcceptedCase;
	}

	public static Double getAverageSlowOrderPassengerSpeedThisCase()
	{
		return  averageSlowOrderPassengerSpeedThisCase;
	}

	public static Double getAverageSlowOrderFreightSpeedThisCase()
	{
		return  averageSlowOrderFreightSpeedThisCase;
	}

	public static Double getAverageSlowOrderPassengerSpeedLastAcceptedCase()
	{
		return  averageSlowOrderPassengerSpeedLastAcceptedCase;
	}

	public static Double getAverageSlowOrderFreightSpeedLastAcceptedCase()
	{
		return  averageSlowOrderFreightSpeedLastAcceptedCase;
	}

	public static Double getHoursMilesThisCase()
	{
		return  hoursMilesThisCase;
	}

	public static Double getHoursMilesLastAcceptedCase()
	{
		return  hoursMilesLastAcceptedCase;
	}

	public static Integer getPermitsConisderedThisCase()
	{
		return  permitsConsideredThisCase;
	}

	public static Integer getPermitsConisderedLastAcceptedCase()
	{
		return  permitsConsideredLastAcceptedCase;
	}

	public static String getResultsMessage()
	{
		return resultsMessage;
	}
}