package com.bl.bias.analyze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.bl.bias.app.BIASJuaComplianceConfigController;
import com.bl.bias.objects.ComplianceLink;
import com.bl.bias.objects.CompliancePermit;
import com.bl.bias.objects.CompliancePriority;
import com.bl.bias.objects.ComplianceTrain;
import com.bl.bias.objects.TrainAssessment;
import com.bl.bias.read.ReadJuaComplianceFiles;
import com.bl.bias.tools.ConvertDateTime;

public class AnalyzeJuaComplianceFiles 
{
	private static String resultsMessage;

	// Train statistics
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

	// Permit statistics
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

	// Link statistics
	private static Double milesOfTrackThisCase = 0.0;
	private static Double milesOfTrackLastAcceptedCase = 0.0;
	private static Double passengerSpeedMilesThisCase = 0.0;
	private static Double passengerSpeedMilesLastAcceptedCase = 0.0;
	private static Double throughSpeedMilesThisCase = 0.0;
	private static Double throughSpeedMilesLastAcceptedCase = 0.0;
	private static Double localSpeedMilesThisCase = 0.0;
	private static Double localSpeedMilesLastAcceptedCase = 0.0;
	private static Integer linkCountThisCase = 0;
	private static Integer linkCountLastAcceptedCase = 0;

	// Statistical Period
	private static Integer statisticalStartDay; 
	private static Integer statisticalEndDay; 

	// Priority/Rank
	private static Integer tier1LowDelayCost = 0;
	private static Integer tier1HighDelayCost = 0;
	private static Integer tier2LowDelayCost = 0;
	private static Integer tier2HighDelayCost = 0;
	private static Integer tier3LowDelayCost = 0;
	private static Integer tier3HighDelayCost = 0;
	private static Integer tier4LowDelayCost = 0;
	private static Integer tier4HighDelayCost = 0;

	private static Integer tier1LowRank = 0;
	private static Integer tier1HighRank = 0;
	private static Integer tier2LowRank = 0;
	private static Integer tier2HighRank = 0;
	private static Integer tier3LowRank = 0;
	private static Integer tier3HighRank = 0;
	private static Integer tier4LowRank = 0;
	private static Integer tier4HighRank = 0;

	// Collections - trains
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

	// Collections - types
	private static ArrayList<String> brightlineTrainTypesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> fecTrainTypesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> triRailTrainTypesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> allTrainTypesFromConfigFile = new ArrayList<String>();
	private static HashMap<String, Integer> seedTrainsByTypeThisCase = new HashMap<>();
	private static HashMap<String, Integer> seedTrainsByTypeLastAcceptedCase = new HashMap<>();
	private static HashMap<String, Integer> operatedTrainsByTypeThisCase = new HashMap<>();
	private static HashMap<String, Integer> operatedTrainsByTypeLastAcceptedCase = new HashMap<>();
	private static HashMap<String, Double> sumOfSeedDurationsByTypeThisCase = new HashMap<>();
	private static HashMap<String, Double> sumOfSeedDurationsByTypeLastAcceptedCase = new HashMap<>();
	private static HashMap<String, Integer> trainsOperatedThisCase = new HashMap<String, Integer>();
	private static HashMap<String, Integer> trainsOperatedLastAcceptedCase = new HashMap<String, Integer>();

	// Collections - nodes
	private static ArrayList<String> brightlineNodesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> fecNodesFromConfigFile = new ArrayList<String>();
	private static ArrayList<String> triRailNodesFromConfigFile = new ArrayList<String>();

	// Collections - permits
	private static ArrayList<CompliancePermit> permitsToAnalyzeForThisCase = new ArrayList<CompliancePermit>();
	private static ArrayList<CompliancePermit> permitsToAnalyzeForLastAcceptedCase = new ArrayList<CompliancePermit>();

	// Collections - locations
	private static HashMap<String, Double> sumOfSeedTrainDwellByLocationThisCase = new HashMap<>();
	private static HashMap<String, Double> sumOfSeedTrainDwellByLocationLastAcceptedCase = new HashMap<>();

	// Collections - links
	private static ArrayList<ComplianceLink> linksToAnalyzeForThisCase = new ArrayList<ComplianceLink>();
	private static ArrayList<ComplianceLink> linksToAnalyzeForLastAcceptedCase = new ArrayList<ComplianceLink>();

	// Collections - priorities
	private static ArrayList<CompliancePriority> trainPrioritiesFromThisCasesOptionFile = new ArrayList<CompliancePriority>();
	private static ArrayList<CompliancePriority> trainPrioritiesFromLastAcceptedCasesOptionFile = new ArrayList<CompliancePriority>();
	private static String[] tier1TrainPriorityTypes;
	private static String[] tier2TrainPriorityTypes;
	private static String[] tier3TrainPriorityTypes;
	private static String[] tier4TrainPriorityTypes;
	
	// Collections - recovery rates
	private static ArrayList<TrainAssessment> setAResults = new ArrayList<TrainAssessment>();
	private static ArrayList<TrainAssessment> setBResults = new ArrayList<TrainAssessment>();
	private static ArrayList<TrainAssessment> setCResults = new ArrayList<TrainAssessment>();
	private static ArrayList<TrainAssessment> setDResults = new ArrayList<TrainAssessment>();
	
	Boolean debug = false;
	private static Boolean prioritiesInTrainFileMatchOptionFile = true;
	private static Boolean prioritiesInOptionFilesMatch = true;
	private static Boolean orderingOfRanksComportsWithJua = true;
	private static Boolean orderingOfPrioritiesComportsWithJua = true;

	public AnalyzeJuaComplianceFiles() 
	{
		resultsMessage = "Started analyzing JUA Compliance at "+ConvertDateTime.getTimeStamp()+"\n";

		// Below are needed for both checking priorities this case and last accepted case
		trainPrioritiesFromThisCasesOptionFile.clear();
		trainPrioritiesFromThisCasesOptionFile.addAll(ReadJuaComplianceFiles.retrieveTrainPrioritiesThisCase());

		if ((BIASJuaComplianceConfigController.getCheckLastAcceptedOptionsFile()) && (ReadJuaComplianceFiles.retrieveTrainPrioritiesLastAcceptedCase() != null))
		{
			trainPrioritiesFromLastAcceptedCasesOptionFile.clear();
			trainPrioritiesFromLastAcceptedCasesOptionFile.addAll(ReadJuaComplianceFiles.retrieveTrainPrioritiesLastAcceptedCase());
		}

		// Trains
		if (BIASJuaComplianceConfigController.getCheckEnabledCountOfTrains())
		{
			// Clear collections of/used by trains
			trainsToAnalyzeForComplianceThisCase.clear();
			trainsToAnalyzeForComplianceLastAcceptedCase.clear();
			seedTrainsFoundEligibleThisCase.clear();
			seedTrainsFoundNotEligibleThisCase.clear();
			seedTrainSymbolsFoundEligibleThisCase.clear();
			seedTrainSymbolsFoundNotEligibleThisCase.clear();
			seedTrainsByTypeThisCase.clear();
			seedTrainsByTypeLastAcceptedCase.clear();
			sumOfSeedTrainDwellByLocationThisCase.clear();
			sumOfSeedTrainDwellByLocationLastAcceptedCase.clear();

			brightlineTrainTypesFromConfigFile.clear();
			fecTrainTypesFromConfigFile.clear();
			triRailTrainTypesFromConfigFile.clear();

			brightlineNodesFromConfigFile.clear();
			fecNodesFromConfigFile.clear();
			triRailNodesFromConfigFile.clear();

			operatedTrainsByTypeThisCase.clear();
			operatedTrainsByTypeLastAcceptedCase.clear();

			trainsOperatedThisCase.clear();
			trainsOperatedLastAcceptedCase.clear();
			
			setAResults.clear();
			setBResults.clear();
			setCResults.clear();
			setDResults.clear();

			allTrainTypesFromConfigFile.clear();

			// This case
			// Populate all arrays with necessary objects
			trainsToAnalyzeForComplianceThisCase.addAll(ReadJuaComplianceFiles.getTrainsToAnalyzeThisCase());
			seedTrainsFoundNotEligibleThisCase.addAll(trainsToAnalyzeForComplianceThisCase);

			// For each train
			for (int i = 0; i < trainsToAnalyzeForComplianceThisCase.size(); i++)
			{
				seedTrainSymbolsFoundNotEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
			}

			if (BIASJuaComplianceConfigController.getBrightlineTrainTypes() != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getBrightlineTrainTypes().length; i++)
				{
					brightlineTrainTypesFromConfigFile.add(BIASJuaComplianceConfigController.getBrightlineTrainTypes()[i].trim());
				}
			}

			if (BIASJuaComplianceConfigController.getFecTrainTypes() != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getFecTrainTypes().length; i++)

				{
					fecTrainTypesFromConfigFile.add(BIASJuaComplianceConfigController.getFecTrainTypes()[i].trim());
				}
			}

			if (BIASJuaComplianceConfigController.getTriRailTrainTypes() != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getTriRailTrainTypes().length; i++)
				{
					triRailTrainTypesFromConfigFile.add(BIASJuaComplianceConfigController.getTriRailTrainTypes()[i].trim());
				}
			}

			if (BIASJuaComplianceConfigController.getBrightlineNodes() != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getBrightlineNodes().length; i++)
				{
					brightlineNodesFromConfigFile.add(BIASJuaComplianceConfigController.getBrightlineNodes()[i].trim());
				}
			}

			if (BIASJuaComplianceConfigController.getFecNodes() != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getFecNodes().length; i++)
				{
					fecNodesFromConfigFile.add(BIASJuaComplianceConfigController.getFecNodes()[i].trim());
				}
			}

			if (BIASJuaComplianceConfigController.getTriRailNodes() != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getTriRailNodes().length; i++)
				{
					triRailNodesFromConfigFile.add(BIASJuaComplianceConfigController.getTriRailNodes()[i].trim());
				}
			}

			// Create an array list of all train types 
			if (BIASJuaComplianceConfigController.getBrightlineTrainTypes() != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getBrightlineTrainTypes().length; i++)
				{
					allTrainTypesFromConfigFile.add(BIASJuaComplianceConfigController.getBrightlineTrainTypes()[i].trim());
				}
			}

			if (BIASJuaComplianceConfigController.getFecTrainTypes() != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getFecTrainTypes().length; i++)
				{
					allTrainTypesFromConfigFile.add(BIASJuaComplianceConfigController.getFecTrainTypes()[i].trim());
				}
			}

			if (BIASJuaComplianceConfigController.getTriRailTrainTypes() != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getTriRailTrainTypes().length; i++)
				{
					allTrainTypesFromConfigFile.add(BIASJuaComplianceConfigController.getTriRailTrainTypes()[i].trim());
				}
			}

			// Add all types to a HashMap for this case 
			for (int i = 0; i < allTrainTypesFromConfigFile.size(); i++)
			{
				operatedTrainsByTypeThisCase.put(allTrainTypesFromConfigFile.get(i), 0);
				seedTrainsByTypeThisCase.put(allTrainTypesFromConfigFile.get(i), 0);
			}

			// Add all train symbols to a HashMap for this case 
			for (int i = 0; i < trainsToAnalyzeForComplianceThisCase.size(); i++)
			{
				trainsOperatedThisCase.put(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol(), 0);
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
				if ((brightlineTrainTypesFromConfigFile != null) & (brightlineTrainTypesFromConfigFile.contains(trainsToAnalyzeForComplianceThisCase.get(i).getType().toUpperCase())))
				{
					// Check if enabled
					if (trainsToAnalyzeForComplianceThisCase.get(i).getEnabled().toUpperCase().equals("YES"))
					{
						// Check if through a valid node
						for (int k = 0; k < trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().size(); k++)
						{
							if (brightlineNodesFromConfigFile.contains(trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().get(k).getNode()))
							{
								// Add symbol count as entry in hashmap with train type as key 
								int oldValueForSeed = seedTrainsByTypeThisCase.get(trainsToAnalyzeForComplianceThisCase.get(i).getType());
								int newValueForSeed = oldValueForSeed + 1;
								seedTrainsByTypeThisCase.put(trainsToAnalyzeForComplianceThisCase.get(i).getType(), newValueForSeed);
								// Check how many times train operates during statistical period
								if (trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().size() == 0) // Linked train
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
												&& (train.getDaysOfOperationAsInteger().get(j) < statisticalEndDay)) 
										{
											totalCountOfBrightlineOperatedTrainsThisCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i));
											seedTrainSymbolsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
											// Update hashmap for operated train count
											for (k = 0; k < allTrainTypesFromConfigFile.size(); k++)
											{
												if (train.getType().equals(allTrainTypesFromConfigFile.get(k)))
												{
													// Increment hashmap entry by 1
													int oldValue = operatedTrainsByTypeThisCase.get(allTrainTypesFromConfigFile.get(k));
													int newValue = oldValue + 1;
													operatedTrainsByTypeThisCase.put(allTrainTypesFromConfigFile.get(k), newValue);
												}
											}
										}
									}
								}
								else // Not a linked train
								{
									seedTrainsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i));
									seedTrainSymbolsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
									for (int j = 0; j < trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().size(); j++)
									{
										if ((trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
												&& (trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().get(j) < statisticalEndDay)) 
										{
											totalCountOfBrightlineOperatedTrainsThisCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i));
											seedTrainSymbolsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
											// Update hashmap for operated train count
											for (k = 0; k < allTrainTypesFromConfigFile.size(); k++)
											{
												if (trainsToAnalyzeForComplianceThisCase.get(i).getType().equals(allTrainTypesFromConfigFile.get(k)))
												{
													// Increment hashmap entry by 1
													int oldValue = operatedTrainsByTypeThisCase.get(allTrainTypesFromConfigFile.get(k));
													int newValue = oldValue + 1;
													operatedTrainsByTypeThisCase.put(allTrainTypesFromConfigFile.get(k), newValue);
												}
											}
										}
									}
								}
							}
						}
					}				
				}
				// Check FEC type
				else if ((fecTrainTypesFromConfigFile != null) & (fecTrainTypesFromConfigFile.contains(trainsToAnalyzeForComplianceThisCase.get(i).getType().toUpperCase())))
				{
					// Check if enabled
					if (trainsToAnalyzeForComplianceThisCase.get(i).getEnabled().toUpperCase().equals("YES"))
					{
						// Check if through a valid node
						for (int k = 0; k < trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().size(); k++)
						{
							if (fecNodesFromConfigFile.contains(trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().get(k).getNode()))
							{
								// Add symbol count as entry in hashmap with train type as key 
								int oldValueForSeed = seedTrainsByTypeThisCase.get(trainsToAnalyzeForComplianceThisCase.get(i).getType());
								int newValueForSeed = oldValueForSeed + 1;
								seedTrainsByTypeThisCase.put(trainsToAnalyzeForComplianceThisCase.get(i).getType(), newValueForSeed);
								// Check how many times train operates during statistical period
								if (trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().size() == 0)  // Linked train
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
												&& (train.getDaysOfOperationAsInteger().get(j) < statisticalEndDay)) 
										{
											totalCountOfFecOperatedTrainsThisCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i));
											seedTrainSymbolsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
											// Update hashmap for operated train count
											for (k = 0; k < allTrainTypesFromConfigFile.size(); k++)
											{
												if (train.getType().equals(allTrainTypesFromConfigFile.get(k)))
												{
													// Increment hashmap entry by 1
													int oldValue = operatedTrainsByTypeThisCase.get(allTrainTypesFromConfigFile.get(k));
													int newValue = oldValue + 1;
													operatedTrainsByTypeThisCase.put(allTrainTypesFromConfigFile.get(k), newValue);
												}
											}
										}
									}
								}
								else // Not a linked train
								{
									seedTrainsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i));
									seedTrainSymbolsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
									for (int j = 0; j < trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().size(); j++)
									{
										if ((trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
												&& (trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().get(j) < statisticalEndDay)) 
										{
											totalCountOfFecOperatedTrainsThisCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i));
											seedTrainSymbolsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
											// Update hashmap for operated train count
											for (k = 0; k < allTrainTypesFromConfigFile.size(); k++)
											{
												if (trainsToAnalyzeForComplianceThisCase.get(i).getType().equals(allTrainTypesFromConfigFile.get(k)))
												{
													// Increment hashmap entry by 1
													int oldValue = operatedTrainsByTypeThisCase.get(allTrainTypesFromConfigFile.get(k));
													int newValue = oldValue + 1;
													operatedTrainsByTypeThisCase.put(allTrainTypesFromConfigFile.get(k), newValue);
												}
											}
										}
									}
								}
							}
						}
					}
				}
				// Check TriRail type
				else if ((triRailTrainTypesFromConfigFile != null) & (triRailTrainTypesFromConfigFile.contains(trainsToAnalyzeForComplianceThisCase.get(i).getType().toUpperCase())))
				{
					// Check if enabled
					if (trainsToAnalyzeForComplianceThisCase.get(i).getEnabled().toUpperCase().equals("YES"))
					{
						// Check if through a valid node
						for (int k = 0; k < trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().size(); k++)
						{
							if (triRailNodesFromConfigFile.contains(trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().get(k).getNode()))
							{
								// Add symbol count as entry in hashmap with train type as key 
								int oldValueForSeed = seedTrainsByTypeThisCase.get(trainsToAnalyzeForComplianceThisCase.get(i).getType());
								int newValueForSeed = oldValueForSeed + 1;
								seedTrainsByTypeThisCase.put(trainsToAnalyzeForComplianceThisCase.get(i).getType(), newValueForSeed);
								// Check how many times train operates during statistical period
								if (trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().size() == 0)  // Linked train
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
												&& (train.getDaysOfOperationAsInteger().get(j) < statisticalEndDay)) 
										{
											totalCountOfTriRailOperatedTrainsThisCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i));
											seedTrainSymbolsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
											// Update hashmap for operated train count
											for (k = 0; k < allTrainTypesFromConfigFile.size(); k++)
											{
												if (train.getType().equals(allTrainTypesFromConfigFile.get(k)))
												{
													// Increment hashmap entry by 1 for by type
													int oldValue = operatedTrainsByTypeThisCase.get(allTrainTypesFromConfigFile.get(k));
													int newValue = oldValue + 1;
													operatedTrainsByTypeThisCase.put(allTrainTypesFromConfigFile.get(k), newValue);

													// Increment hashmap entry by 1 for by symbol
													oldValue = trainsOperatedThisCase.get(train.getSymbol());
													newValue = oldValue + 1;
													trainsOperatedThisCase.put(train.getSymbol(), newValue);
												}
											}
										}
									}
								}
								else // Not a linked train
								{
									seedTrainsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i));
									seedTrainSymbolsFoundEligibleThisCase.add(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
									for (int j = 0; j < trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().size(); j++)
									{
										if ((trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
												&& (trainsToAnalyzeForComplianceThisCase.get(i).getDaysOfOperationAsInteger().get(j) < statisticalEndDay)) 
										{
											totalCountOfTriRailOperatedTrainsThisCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i));
											seedTrainSymbolsFoundNotEligibleThisCase.remove(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
											// Update hashmap for operated train count
											for (k = 0; k < allTrainTypesFromConfigFile.size(); k++)
											{
												if (trainsToAnalyzeForComplianceThisCase.get(i).getType().equals(allTrainTypesFromConfigFile.get(k)))
												{
													// Increment hashmap entry by 1 by type 
													int oldValue = operatedTrainsByTypeThisCase.get(allTrainTypesFromConfigFile.get(k));
													int newValue = oldValue + 1;
													operatedTrainsByTypeThisCase.put(allTrainTypesFromConfigFile.get(k), newValue);

													// Increment hashmap entry by 1 for by symbol
													oldValue = trainsOperatedThisCase.get(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol());
													newValue = oldValue + 1;
													trainsOperatedThisCase.put(trainsToAnalyzeForComplianceThisCase.get(i).getSymbol(), newValue);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

			resultsMessage += "Found "+seedTrainsFoundEligibleThisCase.size()+" eligible seed trains in the selected case:\n";
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

			if (brightlineTrainTypesFromConfigFile != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getBrightlineTrainTypes().length; i++)
				{
					brightlineTrainTypesFromConfigFile.add(BIASJuaComplianceConfigController.getBrightlineTrainTypes()[i].trim());
				}
			}

			if (fecTrainTypesFromConfigFile != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getFecTrainTypes().length; i++)
				{
					fecTrainTypesFromConfigFile.add(BIASJuaComplianceConfigController.getFecTrainTypes()[i].trim());
				}
			}

			if (triRailTrainTypesFromConfigFile != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getTriRailTrainTypes().length; i++)
				{
					triRailTrainTypesFromConfigFile.add(BIASJuaComplianceConfigController.getTriRailTrainTypes()[i].trim());
				}
			}

			if (BIASJuaComplianceConfigController.getBrightlineNodes() != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getBrightlineNodes().length; i++)
				{
					brightlineNodesFromConfigFile.add(BIASJuaComplianceConfigController.getBrightlineNodes()[i].trim());
				}
			}

			if (BIASJuaComplianceConfigController.getFecNodes() != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getFecNodes().length; i++)
				{
					fecNodesFromConfigFile.add(BIASJuaComplianceConfigController.getFecNodes()[i].trim());
				}
			}

			if (BIASJuaComplianceConfigController.getTriRailNodes() != null)
			{
				for (int i = 0; i < BIASJuaComplianceConfigController.getTriRailNodes().length; i++)
				{
					triRailNodesFromConfigFile.add(BIASJuaComplianceConfigController.getTriRailNodes()[i].trim());
				}
			}

			// Add all train symbols to a HashMap for last accepted case 
			for (int i = 0; i < trainsToAnalyzeForComplianceLastAcceptedCase.size(); i++)
			{
				trainsOperatedLastAcceptedCase.put(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol(), 0);
			}

			// Determine statistical simulation days (as integers)
			totalCountOfBrightlineOperatedTrainsLastAcceptedCase = 0;
			totalCountOfFecOperatedTrainsLastAcceptedCase = 0;
			totalCountOfTriRailOperatedTrainsLastAcceptedCase = 0;
			statisticalStartDay = ReadJuaComplianceFiles.getStatisticalStartDayOfWeekAsInteger();		 
			statisticalEndDay = statisticalStartDay + ReadJuaComplianceFiles.getStatisticalDurationInDays(); 

			// Add all types to a HashMap for last accepted case for counting operated trains by type
			for (int i = 0; i < allTrainTypesFromConfigFile.size(); i++)
			{
				operatedTrainsByTypeLastAcceptedCase.put(allTrainTypesFromConfigFile.get(i), 0);
				seedTrainsByTypeLastAcceptedCase.put(allTrainTypesFromConfigFile.get(i), 0);
			}

			// For each train
			for (int i = 0; i < trainsToAnalyzeForComplianceLastAcceptedCase.size(); i++)
			{
				// Check Brightline type
				if ((brightlineTrainTypesFromConfigFile != null) & (brightlineTrainTypesFromConfigFile.contains(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType().toUpperCase())))
				{
					// Check if enabled
					if (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getEnabled().toUpperCase().equals("YES"))
					{
						// Check if through a valid node
						for (int k = 0; k < trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().size(); k++)
						{
							if (brightlineNodesFromConfigFile.contains(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().get(k).getNode()))
							{
								// Add symbol count as entry in hashmap with train type as key 
								int oldValueForSeed = seedTrainsByTypeLastAcceptedCase.get(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType());
								int newValueForSeed = oldValueForSeed + 1;

								seedTrainsByTypeLastAcceptedCase.put(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType(), newValueForSeed);
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

									for (int j = 0; j < train.getDaysOfOperationAsInteger().size(); j++) // Linked train
									{
										if ((train.getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
												&& (train.getDaysOfOperationAsInteger().get(j) < statisticalEndDay)) 
										{
											totalCountOfBrightlineOperatedTrainsLastAcceptedCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
											seedTrainSymbolsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
											// Update hashmap for operated train count
											for (k = 0; k < allTrainTypesFromConfigFile.size(); k++)
											{
												if (train.getType().equals(allTrainTypesFromConfigFile.get(k)))
												{
													// Increment hashmap entry by 1
													int oldValue = operatedTrainsByTypeLastAcceptedCase.get(allTrainTypesFromConfigFile.get(k));
													int newValue = oldValue + 1;
													operatedTrainsByTypeLastAcceptedCase.put(allTrainTypesFromConfigFile.get(k), newValue);
												}
											}
										}
									}
								}
								else // Not linked
								{
									seedTrainsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
									seedTrainSymbolsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
									for (int j = 0; j < trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().size(); j++)
									{
										if ((trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
												&& (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().get(j) < statisticalEndDay)) 
										{
											totalCountOfBrightlineOperatedTrainsLastAcceptedCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
											seedTrainSymbolsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
											// Update hashmap for operated train count
											for (k = 0; k < allTrainTypesFromConfigFile.size(); k++)
											{
												if (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType().equals(allTrainTypesFromConfigFile.get(k)))
												{
													// Increment hashmap entry by 1
													int oldValue = operatedTrainsByTypeLastAcceptedCase.get(allTrainTypesFromConfigFile.get(k));
													int newValue = oldValue + 1;
													operatedTrainsByTypeLastAcceptedCase.put(allTrainTypesFromConfigFile.get(k), newValue);
												}
											}
										}
									}
								}
							}
						}
					}
				}
				// Check FEC type
				else if ((fecTrainTypesFromConfigFile != null) & (fecTrainTypesFromConfigFile.contains(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType().toUpperCase())))
				{
					// Check if enabled
					if (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getEnabled().toUpperCase().equals("YES"))
					{
						// Check if through a valid node
						for (int k = 0; k < trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().size(); k++)
						{
							if (fecNodesFromConfigFile.contains(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().get(k).getNode()))
							{
								// Add symbol count as entry in hashmap with train type as key 
								int oldValueForSeed = seedTrainsByTypeLastAcceptedCase.get(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType());
								int newValueForSeed = oldValueForSeed + 1;
								seedTrainsByTypeLastAcceptedCase.put(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType(), newValueForSeed);
								// Check how many times train operates during statistical period
								if (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().size() == 0) // Linked train
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
												&& (train.getDaysOfOperationAsInteger().get(j) < statisticalEndDay)) 
										{
											totalCountOfFecOperatedTrainsLastAcceptedCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
											seedTrainSymbolsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
											// Update hashmap for operated train count
											for (k = 0; k < allTrainTypesFromConfigFile.size(); k++)
											{
												if (train.getType().equals(allTrainTypesFromConfigFile.get(k)))
												{
													// Increment hashmap entry by 1
													int oldValue = operatedTrainsByTypeLastAcceptedCase.get(allTrainTypesFromConfigFile.get(k));
													int newValue = oldValue + 1;
													operatedTrainsByTypeLastAcceptedCase.put(allTrainTypesFromConfigFile.get(k), newValue);
												}
											}
										}
									}
								}
								else // Not linked
								{
									seedTrainsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
									seedTrainSymbolsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
									for (int j = 0; j < trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().size(); j++)
									{
										if ((trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
												&& (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().get(j) < statisticalEndDay)) 
										{
											totalCountOfFecOperatedTrainsLastAcceptedCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
											seedTrainSymbolsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
											// Update hashmap for operated train count
											for (k = 0; k < allTrainTypesFromConfigFile.size(); k++)
											{
												if (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType().equals(allTrainTypesFromConfigFile.get(k)))
												{
													// Increment hashmap entry by 1
													int oldValue = operatedTrainsByTypeLastAcceptedCase.get(allTrainTypesFromConfigFile.get(k));
													int newValue = oldValue + 1;
													operatedTrainsByTypeLastAcceptedCase.put(allTrainTypesFromConfigFile.get(k), newValue);
												}
											}
										}
									}
								}
							}
						}
					}
				}
				// Check TriRail type
				else if ((triRailTrainTypesFromConfigFile != null) & (triRailTrainTypesFromConfigFile.contains(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType().toUpperCase())))
				{
					// Check if enabled
					if (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getEnabled().toUpperCase().equals("YES"))
					{
						// Check if through a valid node
						for (int k = 0; k < trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().size(); k++)
						{
							if (triRailNodesFromConfigFile.contains(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().get(k).getNode()))
							{
								// Add symbol count as entry in hashmap with train type as key 
								int oldValueForSeed = seedTrainsByTypeLastAcceptedCase.get(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType());
								int newValueForSeed = oldValueForSeed + 1;
								seedTrainsByTypeLastAcceptedCase.put(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType(), newValueForSeed);
								// Check how many times train operates during statistical period
								if (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().size() == 0)  // Linked train
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
												&& (train.getDaysOfOperationAsInteger().get(j) < statisticalEndDay)) 
										{
											totalCountOfTriRailOperatedTrainsLastAcceptedCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
											seedTrainSymbolsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
											// Update hashmap for operated train count
											for (k = 0; k < allTrainTypesFromConfigFile.size(); k++)
											{
												if (train.getType().equals(allTrainTypesFromConfigFile.get(k)))
												{
													// Increment hashmap entry by 1 for by type
													int oldValue = operatedTrainsByTypeLastAcceptedCase.get(allTrainTypesFromConfigFile.get(k));
													int newValue = oldValue + 1;
													operatedTrainsByTypeLastAcceptedCase.put(allTrainTypesFromConfigFile.get(k), newValue);

													// Increment hashmap entry by 1 for by symbol
													oldValue = trainsOperatedLastAcceptedCase.get(train.getSymbol());
													newValue = oldValue + 1;
													trainsOperatedLastAcceptedCase.put(train.getSymbol(), newValue);
												}
											}
										}
									}
								}
								else // Not a linked train
								{
									seedTrainsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
									seedTrainSymbolsFoundEligibleLastAcceptedCase.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
									for (int j = 0; j < trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().size(); j++)
									{
										if ((trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().get(j) >= statisticalStartDay) 
												&& (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getDaysOfOperationAsInteger().get(j) < statisticalEndDay)) 
										{
											totalCountOfTriRailOperatedTrainsLastAcceptedCase++;
											// Train is eligible so remove from the ineligible list
											seedTrainsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i));
											seedTrainSymbolsFoundNotEligibleLastAcceptedCase.remove(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
											// Update hashmap for operated train count
											for (k = 0; k < allTrainTypesFromConfigFile.size(); k++)
											{
												if (trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getType().equals(allTrainTypesFromConfigFile.get(k)))
												{
													// Increment hashmap entry by 1 for by type
													int oldValue = operatedTrainsByTypeLastAcceptedCase.get(allTrainTypesFromConfigFile.get(k));
													int newValue = oldValue + 1;
													operatedTrainsByTypeLastAcceptedCase.put(allTrainTypesFromConfigFile.get(k), newValue);

													// Increment hashmap entry by 1 for by symbol
													oldValue = trainsOperatedLastAcceptedCase.get(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol());
													newValue = oldValue + 1;
													trainsOperatedLastAcceptedCase.put(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getSymbol(), newValue);
												}
											}
										}
									}
								}
							}
						}
					}
				}	
			}
			// Check sum of end-to-end scheduled duration 
			// Set all train types to cumulative zero duration
			for (int i = 0; i < allTrainTypesFromConfigFile.size(); i++)
			{
				sumOfSeedDurationsByTypeThisCase.put(allTrainTypesFromConfigFile.get(i), 0.0);
				sumOfSeedDurationsByTypeLastAcceptedCase.put(allTrainTypesFromConfigFile.get(i), 0.0);
			}			

			// This case
			// For each type
			for (int i = 0; i < allTrainTypesFromConfigFile.size(); i++)
			{
				double durationForThisTypeInCurrentCase = 0.0;
				// Check each train
				for (int j = 0; j < trainsToAnalyzeForComplianceThisCase.size(); j++)
				{
					if (trainsToAnalyzeForComplianceThisCase.get(j).getType().equals(allTrainTypesFromConfigFile.get(i)))
					{
						String startTimeAsString = null;
						String endTimeAsString = null;

						// Start time
						for (int k = 0; k < trainsToAnalyzeForComplianceThisCase.get(j).getRouteEntries().size(); k++)
						{
							startTimeAsString = trainsToAnalyzeForComplianceThisCase.get(j).getRouteEntries().get(k).getDepartureTime();
							if (startTimeAsString.equals("FLOAT"))
								continue;
							else
								break;
						}

						// End time
						for (int k = trainsToAnalyzeForComplianceThisCase.get(j).getRouteEntries().size() - 1; k >= 0; k--)
						{
							endTimeAsString = trainsToAnalyzeForComplianceThisCase.get(j).getRouteEntries().get(k).getArrivalTime();
							if (endTimeAsString.equals("FLOAT"))
								continue;
							else
								break;
						}

						if ((startTimeAsString.equals("FLOAT")) || (endTimeAsString.equals("FLOAT")))
						{
							continue;
						}
						else
						{
							double durationThisTrain = ConvertDateTime.convertDDHHMMSSStringToSerial(endTimeAsString) - ConvertDateTime.convertDDHHMMSSStringToSerial(startTimeAsString);
							durationForThisTypeInCurrentCase += durationThisTrain;
						}
					}	
				}
				sumOfSeedDurationsByTypeThisCase.put(allTrainTypesFromConfigFile.get(i), durationForThisTypeInCurrentCase);
			}

			// Last accepted case
			// For each type
			for (int i = 0; i < allTrainTypesFromConfigFile.size(); i++)
			{
				double durationForThisTypeInLastAcceptedCase = 0.0;
				// Check each train
				for (int j = 0; j < trainsToAnalyzeForComplianceLastAcceptedCase.size(); j++)
				{
					if (trainsToAnalyzeForComplianceLastAcceptedCase.get(j).getType().equals(allTrainTypesFromConfigFile.get(i)))
					{
						String startTimeAsString = null;
						String endTimeAsString = null;

						// Start time
						for (int k = 0; k < trainsToAnalyzeForComplianceLastAcceptedCase.get(j).getRouteEntries().size(); k++)
						{
							startTimeAsString = trainsToAnalyzeForComplianceLastAcceptedCase.get(j).getRouteEntries().get(k).getDepartureTime();
							if (startTimeAsString.equals("FLOAT"))
								continue;
							else
								break;
						}

						// End time
						for (int k = trainsToAnalyzeForComplianceLastAcceptedCase.get(j).getRouteEntries().size() - 1; k >= 0; k--)
						{
							endTimeAsString = trainsToAnalyzeForComplianceLastAcceptedCase.get(j).getRouteEntries().get(k).getArrivalTime();
							if (endTimeAsString.equals("FLOAT"))
								continue;
							else
								break;
						}

						if ((startTimeAsString.equals("FLOAT")) || (endTimeAsString.equals("FLOAT")))
						{
							continue;
						}
						else
						{
							double durationThisTrain = ConvertDateTime.convertDDHHMMSSStringToSerial(endTimeAsString) - ConvertDateTime.convertDDHHMMSSStringToSerial(startTimeAsString);
							durationForThisTypeInLastAcceptedCase += durationThisTrain;
						}
					}	
				}
				sumOfSeedDurationsByTypeLastAcceptedCase.put(allTrainTypesFromConfigFile.get(i), durationForThisTypeInLastAcceptedCase);
			}

			// Check sum of scheduled dwell by location 
			// Set all locations to cumulative zero duration this case
			HashSet<String> thisCaseScheduledDwellLocationsHashSet = new HashSet<String>();
			ArrayList<String> thisCaseScheduledDwellLocationsArrayList = new ArrayList<String>();
			for (int i = 0; i < trainsToAnalyzeForComplianceThisCase.size(); i++)
			{
				for (int j = 0; j < trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().size(); j++)
					if (!trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().get(j).getMinimumDwellTime().equals("0"))
						thisCaseScheduledDwellLocationsHashSet.add(trainsToAnalyzeForComplianceThisCase.get(i).getRouteEntries().get(j).getNode());
			}
			thisCaseScheduledDwellLocationsArrayList.addAll(thisCaseScheduledDwellLocationsHashSet);
			for (int i = 0; i < thisCaseScheduledDwellLocationsArrayList.size(); i++)
			{
				sumOfSeedTrainDwellByLocationThisCase.put(thisCaseScheduledDwellLocationsArrayList.get(i), 0.0);
			}
			for (int i = 0; i < thisCaseScheduledDwellLocationsArrayList.size(); i++)
			{
				for (int j = 0; j < trainsToAnalyzeForComplianceThisCase.size(); j++)
				{
					for (int k = 0; k < trainsToAnalyzeForComplianceThisCase.get(j).getRouteEntries().size(); k++)
					{
						if (trainsToAnalyzeForComplianceThisCase.get(j).getRouteEntries().get(k).getNode().equals(thisCaseScheduledDwellLocationsArrayList.get(i)))
						{
							if (sumOfSeedTrainDwellByLocationThisCase.get(thisCaseScheduledDwellLocationsArrayList.get(i)) != null)
							{
								double oldDwellValue = sumOfSeedTrainDwellByLocationThisCase.get(thisCaseScheduledDwellLocationsArrayList.get(i));
								double newDwellValue = oldDwellValue + ConvertDateTime.convertDDHHMMSSStringToSerial(trainsToAnalyzeForComplianceThisCase.get(j).getRouteEntries().get(k).getMinimumDwellTime());
								sumOfSeedTrainDwellByLocationThisCase.put(thisCaseScheduledDwellLocationsArrayList.get(i), newDwellValue);
							}
						}
					}
				}
			}

			// Set all locations to cumulative zero duration last accepted case
			HashSet<String> lastCaseScheduledDwellLocationsHashSet = new HashSet<String>();
			ArrayList<String> lastAcceptedCaseScheduledDwellLocationsArrayList = new ArrayList<String>();
			for (int i = 0; i < trainsToAnalyzeForComplianceLastAcceptedCase.size(); i++)
			{
				for (int j = 0; j < trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().size(); j++)
					if (!trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().get(j).getMinimumDwellTime().equals("0"))
						lastCaseScheduledDwellLocationsHashSet.add(trainsToAnalyzeForComplianceLastAcceptedCase.get(i).getRouteEntries().get(j).getNode());
			}
			lastAcceptedCaseScheduledDwellLocationsArrayList.addAll(thisCaseScheduledDwellLocationsHashSet);
			for (int i = 0; i < lastAcceptedCaseScheduledDwellLocationsArrayList.size(); i++)
			{
				sumOfSeedTrainDwellByLocationLastAcceptedCase.put(lastAcceptedCaseScheduledDwellLocationsArrayList.get(i), 0.0);
			}
			for (int i = 0; i < lastAcceptedCaseScheduledDwellLocationsArrayList.size(); i++)
			{
				for (int j = 0; j < trainsToAnalyzeForComplianceLastAcceptedCase.size(); j++)
				{
					for (int k = 0; k < trainsToAnalyzeForComplianceLastAcceptedCase.get(j).getRouteEntries().size(); k++)
					{
						if (trainsToAnalyzeForComplianceLastAcceptedCase.get(j).getRouteEntries().get(k).getNode().equals(lastAcceptedCaseScheduledDwellLocationsArrayList.get(i)))
						{
							if (sumOfSeedTrainDwellByLocationLastAcceptedCase.get(lastAcceptedCaseScheduledDwellLocationsArrayList.get(i)) != null)
							{
								double oldDwellValue = sumOfSeedTrainDwellByLocationLastAcceptedCase.get(lastAcceptedCaseScheduledDwellLocationsArrayList.get(i));
								double newDwellValue = oldDwellValue + ConvertDateTime.convertDDHHMMSSStringToSerial(trainsToAnalyzeForComplianceLastAcceptedCase.get(j).getRouteEntries().get(k).getMinimumDwellTime());
								sumOfSeedTrainDwellByLocationLastAcceptedCase.put(lastAcceptedCaseScheduledDwellLocationsArrayList.get(i), newDwellValue);
							}
						}
					}
				}
			}

			resultsMessage += "Found "+seedTrainsFoundEligibleLastAcceptedCase.size()+" eligible seed trains in the last approved .TRAIN file:\n";
			resultsMessage += "  yielding "+totalCountOfBrightlineOperatedTrainsLastAcceptedCase+" dispatched Brightline trains operated during the statistical period in the last approved .TRAIN file\n";
			resultsMessage += "  yielding "+totalCountOfFecOperatedTrainsLastAcceptedCase+" dispatched FEC trains operated during the statistical period in the last approved .TRAIN file\n";
			resultsMessage += "  yielding "+totalCountOfTriRailOperatedTrainsLastAcceptedCase+" dispatched TriRail trains operated during the statistical period in the last approved .TRAIN file\n";
			resultsMessage += "Found "+seedTrainsFoundNotEligibleLastAcceptedCase.size()+" other seed trains in the last approved .TRAIN file\n\n";
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
					resultsMessage += "\n\n";
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

				averageSlowOrderPassengerSpeedThisCase = sumOfSlowOrderPassengerSpeedThisCase/permitsConsideredThisCase;
				averageSlowOrderFreightSpeedThisCase = sumOfSlowOrderFreightSpeedThisCase/permitsConsideredThisCase;
				averageSlowOrderPassengerSpeedLastAcceptedCase = sumOfSlowOrderPassengerSpeedLastAcceptedCase/permitsConsideredLastAcceptedCase;
				averageSlowOrderFreightSpeedLastAcceptedCase = sumOfSlowOrderFreightSpeedLastAcceptedCase/permitsConsideredLastAcceptedCase;
			}
		}

		// Links - Common
		if (BIASJuaComplianceConfigController.getAnalyzeLinks())
		{
			linksToAnalyzeForThisCase.clear();
			linksToAnalyzeForLastAcceptedCase.clear();
			linksToAnalyzeForThisCase.addAll(ReadJuaComplianceFiles.getLinksToAnalyzeThisCase());
			linksToAnalyzeForLastAcceptedCase.addAll(ReadJuaComplianceFiles.getLinksToAnalyzeLastAcceptedCase());

			resultsMessage += "Found " + linksToAnalyzeForThisCase.size() + " links in this case's .LINK file and " + linksToAnalyzeForLastAcceptedCase.size() + " links in the last accepted .LINK file\n";
			resultsMessage += "Calculating sum of miles multiplied by speed for track classes\n\n";

			// Compute miles of links, and miles * speed for three train groups
			milesOfTrackThisCase = 0.0;
			milesOfTrackLastAcceptedCase = 0.0;
			passengerSpeedMilesThisCase = 0.0;
			passengerSpeedMilesLastAcceptedCase = 0.0;
			throughSpeedMilesThisCase = 0.0;
			throughSpeedMilesLastAcceptedCase = 0.0;
			localSpeedMilesThisCase = 0.0;
			localSpeedMilesLastAcceptedCase = 0.0;
			linkCountThisCase = linksToAnalyzeForThisCase.size();
			linkCountLastAcceptedCase = linksToAnalyzeForLastAcceptedCase.size();

			for (int i = 0; i < linkCountThisCase; i++)
			{
				milesOfTrackThisCase += linksToAnalyzeForThisCase.get(i).getLinkDistance();
				passengerSpeedMilesThisCase += (linksToAnalyzeForThisCase.get(i).getLinkDistance() * linksToAnalyzeForThisCase.get(i).getPassengerSpeed());
				throughSpeedMilesThisCase += (linksToAnalyzeForThisCase.get(i).getLinkDistance() * linksToAnalyzeForThisCase.get(i).getThroughSpeed());
				localSpeedMilesThisCase += (linksToAnalyzeForThisCase.get(i).getLinkDistance() * linksToAnalyzeForThisCase.get(i).getLocalSpeed());
			}

			for (int i = 0; i < linkCountLastAcceptedCase; i++)
			{
				milesOfTrackLastAcceptedCase += linksToAnalyzeForLastAcceptedCase.get(i).getLinkDistance();
				passengerSpeedMilesLastAcceptedCase += (linksToAnalyzeForLastAcceptedCase.get(i).getLinkDistance() * linksToAnalyzeForLastAcceptedCase.get(i).getPassengerSpeed());
				throughSpeedMilesLastAcceptedCase += (linksToAnalyzeForLastAcceptedCase.get(i).getLinkDistance() * linksToAnalyzeForLastAcceptedCase.get(i).getThroughSpeed());
				localSpeedMilesLastAcceptedCase += (linksToAnalyzeForLastAcceptedCase.get(i).getLinkDistance() * linksToAnalyzeForLastAcceptedCase.get(i).getLocalSpeed());
			}
		}

		// Train priority
		if (BIASJuaComplianceConfigController.getCheckTrainPriority())
		{
			tier1TrainPriorityTypes = BIASJuaComplianceConfigController.getTier1TrainTypesAsArray();
			tier2TrainPriorityTypes = BIASJuaComplianceConfigController.getTier2TrainTypesAsArray();
			tier3TrainPriorityTypes = BIASJuaComplianceConfigController.getTier3TrainTypesAsArray();
			tier4TrainPriorityTypes = BIASJuaComplianceConfigController.getTier4TrainTypesAsArray();

			resultsMessage += "Found " + (trainPrioritiesFromThisCasesOptionFile.size() * 5) + " train priority objects in this case's .OPTION file\n";

			// Check hierarchy within the .OPTION file
			resultsMessage += "Checking that priorities/ranks in .OPTION file comport with JUA\n";

			// Find high and low priorities for Tier 1
			int tier1HighMinimumPriority = 0;
			int tier1LowMinimumPriority = 9999;
			int tier1HighInitialPriority = 0;
			int tier1LowInitialPriority = 9999;
			int tier1HighMaximumPriority = 0;
			int tier1LowMaximumPriority = 9999;

			if (tier1TrainPriorityTypes != null)
			{
				// Max
				for (int i = 0; i < tier1TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier1TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost() > tier1HighMaximumPriority)
								tier1HighMaximumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost() < tier1LowMaximumPriority)
								tier1LowMaximumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost();
						}
					}
				}
				// Initial
				for (int i = 0; i < tier1TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier1TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost() > tier1HighInitialPriority)
								tier1HighInitialPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost() < tier1LowInitialPriority)
								tier1LowInitialPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost();
						}
					}
				}
				// Minimum
				for (int i = 0; i < tier1TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier1TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost() > tier1HighMinimumPriority)
								tier1HighMinimumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost() < tier1LowMinimumPriority)
								tier1LowMinimumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost();
						}
					}
				}
				tier1LowDelayCost = tier1LowMinimumPriority;
				tier1HighDelayCost = tier1HighMaximumPriority;
			}

			// Find high and low priorities for Tier 2
			int tier2HighMinimumPriority = 0;
			int tier2LowMinimumPriority = 9999;
			int tier2HighInitialPriority = 0;
			int tier2LowInitialPriority = 9999;
			int tier2HighMaximumPriority = 0;
			int tier2LowMaximumPriority = 9999;

			if (tier2TrainPriorityTypes != null)
			{
				// Max
				for (int i = 0; i < tier2TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier2TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost() > tier2HighMaximumPriority)
								tier2HighMaximumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost() < tier2LowMaximumPriority)
								tier2LowMaximumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost();
						}
					}
				}
				// Initial
				for (int i = 0; i < tier2TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier2TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost() > tier2HighInitialPriority)
								tier2HighInitialPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost() < tier2LowInitialPriority)
								tier2LowInitialPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost();
						}
					}
				}
				// Minimum
				for (int i = 0; i < tier2TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier2TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost() > tier2HighMinimumPriority)
								tier2HighMinimumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost() < tier2LowMinimumPriority)
								tier2LowMinimumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost();
						}
					}
				}
				tier2LowDelayCost = tier2LowMinimumPriority;
				tier2HighDelayCost = tier2HighMaximumPriority;
			}

			// Find high and low priorities for Tier 3
			int tier3HighMinimumPriority = 0;
			int tier3LowMinimumPriority = 9999;
			int tier3HighInitialPriority = 0;
			int tier3LowInitialPriority = 9999;
			int tier3HighMaximumPriority = 0;
			int tier3LowMaximumPriority = 9999;

			if (tier3TrainPriorityTypes != null)
			{
				// Max
				for (int i = 0; i < tier3TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier3TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost() > tier3HighMaximumPriority)
								tier3HighMaximumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost() < tier3LowMaximumPriority)
								tier3LowMaximumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost();
						}
					}
				}
				// Initial
				for (int i = 0; i < tier3TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier3TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost() > tier3HighInitialPriority)
								tier3HighInitialPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost() < tier3LowInitialPriority)
								tier3LowInitialPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost();
						}
					}
				}
				// Minimum
				for (int i = 0; i < tier3TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier3TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost() > tier3HighMinimumPriority)
								tier3HighMinimumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost() < tier3LowMinimumPriority)
								tier3LowMinimumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost();
						}
					}
				}
				tier3LowDelayCost = tier3LowMinimumPriority;
				tier3HighDelayCost = tier3HighMaximumPriority;
			}

			// Find high and low priorities for Tier 4
			int tier4HighMinimumPriority = 0;
			int tier4LowMinimumPriority = 9999;
			int tier4HighInitialPriority = 0;
			int tier4LowInitialPriority = 9999;
			int tier4HighMaximumPriority = 0;
			int tier4LowMaximumPriority = 9999;

			if (tier4TrainPriorityTypes != null)
			{
				// Max
				for (int i = 0; i < tier4TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier4TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost() > tier4HighMaximumPriority)
								tier4HighMaximumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost() < tier4LowMaximumPriority)
								tier4LowMaximumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost();
						}
					}
				}
				// Initial
				for (int i = 0; i < tier4TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier4TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost() > tier4HighInitialPriority)
								tier4HighInitialPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost() < tier4LowInitialPriority)
								tier4LowInitialPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost();
						}
					}
				}
				// Minimum
				for (int i = 0; i < tier4TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier4TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost() > tier4HighMinimumPriority)
								tier4HighMinimumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost() < tier4LowMinimumPriority)
								tier4LowMinimumPriority = trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost();
						}
					}
				}
				tier4LowDelayCost = tier4LowMinimumPriority;
				tier4HighDelayCost = tier4HighMaximumPriority;
			}

			ArrayList<Integer> stackOfPriorities = new ArrayList<Integer>();

			if (tier1TrainPriorityTypes != null)
			{
				stackOfPriorities.add(tier1HighMaximumPriority);
				stackOfPriorities.add(tier1LowMinimumPriority);
			}
			if (tier2TrainPriorityTypes != null)
			{
				stackOfPriorities.add(tier2HighMaximumPriority);
				stackOfPriorities.add(tier2LowMinimumPriority);
			}
			if (tier3TrainPriorityTypes != null)
			{
				stackOfPriorities.add(tier3HighMaximumPriority);
				stackOfPriorities.add(tier3LowMinimumPriority);
			}
			if (tier4TrainPriorityTypes != null)
			{
				stackOfPriorities.add(tier4HighMaximumPriority);
				stackOfPriorities.add(tier4LowMinimumPriority);
			}

			// Check if priorities are in order
			for (int i = 0; i < (stackOfPriorities.size()/2) - 1; i++)
			{
				if (stackOfPriorities.get((i*2)+1) >= stackOfPriorities.get(i*2)+2)
				{
					orderingOfPrioritiesComportsWithJua = false;
					resultsMessage += "Dispatch priorities are not compliant with JUA\n";
				}
			}

			// Check dispatch rank
			int tier1HighDispatchRank = 7;
			int tier1LowDispatchRank = 1;
			int tier2HighDispatchRank = 7;
			int tier2LowDispatchRank = 1;
			int tier3HighDispatchRank = 7;
			int tier3LowDispatchRank = 1;
			int tier4HighDispatchRank = 7;
			int tier4LowDispatchRank = 1;

			// Tier 1
			if (tier1TrainPriorityTypes != null)
			{
				for (int i = 0; i < tier1TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier1TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank() < tier1HighDispatchRank)
								tier1HighDispatchRank = trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank() > tier1LowDispatchRank)
								tier1LowDispatchRank = trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank();
						}
					}
				}
			}

			// Tier 2
			if (tier2TrainPriorityTypes != null)
			{
				for (int i = 0; i < tier2TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier2TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank() < tier2HighDispatchRank)
								tier2HighDispatchRank = trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank() > tier2LowDispatchRank)
								tier2LowDispatchRank = trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank();
						}
					}
				}
			}

			// Tier 3
			if (tier3TrainPriorityTypes != null)
			{
				for (int i = 0; i < tier3TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier3TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank() < tier3HighDispatchRank)
								tier3HighDispatchRank = trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank() > tier3LowDispatchRank)
								tier3LowDispatchRank = trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank();
						}
					}
				}
			}

			// Tier 4
			if (tier4TrainPriorityTypes != null)
			{

				for (int i = 0; i < tier4TrainPriorityTypes.length; i++)
				{
					for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
					{
						if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().trim().equals(tier4TrainPriorityTypes[i].trim()))
						{
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank() < tier4HighDispatchRank)
								tier4HighDispatchRank = trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank();
							if (trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank() > tier4LowDispatchRank)
								tier4LowDispatchRank = trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank();
						}
					}
				}
			}

			tier1LowRank = tier1LowDispatchRank;
			tier1HighRank = tier1HighDispatchRank;
			tier2LowRank = tier2LowDispatchRank;
			tier2HighRank = tier2HighDispatchRank;
			tier3LowRank = tier3LowDispatchRank;
			tier3HighRank = tier3HighDispatchRank;
			tier4LowRank = tier4LowDispatchRank;
			tier4HighRank = tier4HighDispatchRank;

			ArrayList<Integer> stackOfDispatchRanks = new ArrayList<Integer>();

			if (tier1TrainPriorityTypes != null)
			{
				stackOfDispatchRanks.add(tier1HighDispatchRank);
				stackOfDispatchRanks.add(tier1LowDispatchRank);
			}
			if (tier2TrainPriorityTypes != null)
			{
				stackOfDispatchRanks.add(tier2HighDispatchRank);
				stackOfDispatchRanks.add(tier2LowDispatchRank);
			}
			if (tier3TrainPriorityTypes != null)
			{
				stackOfDispatchRanks.add(tier3HighDispatchRank);
				stackOfDispatchRanks.add(tier3LowDispatchRank);
			}
			if (tier4TrainPriorityTypes != null)
			{
				stackOfDispatchRanks.add(tier4HighDispatchRank);
				stackOfDispatchRanks.add(tier4LowDispatchRank);
			}

			// Check if ranks are in order
			for (int i = 0; i < (stackOfDispatchRanks.size()/2) - 1; i++)
			{
				if (stackOfDispatchRanks.get((i*2)+1) >= stackOfDispatchRanks.get(i*2)+2)
				{
					orderingOfRanksComportsWithJua = false;
					resultsMessage += "Dispatch ranks are not compliant with JUA\n";
				}
			}

			// Check that priorities match between .OPTION and .TRAIN files
			resultsMessage += "Checking that priorities in .TRAIN file comport with .OPTION file\n";
			for (int i = 0; i < trainsToAnalyzeForComplianceThisCase.size(); i++)
			{
				for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++)
				{
					if (trainPrioritiesFromThisCasesOptionFile.get(j).getType().equals(trainsToAnalyzeForComplianceThisCase.get(i).getType()))
					{
						// Check that .OPTION and .TRAIN match for each train type
						if ((!trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost().equals(trainsToAnalyzeForComplianceThisCase.get(i).getMinimumPriority()))
								|| (!trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost().equals(trainsToAnalyzeForComplianceThisCase.get(i).getInitialPriority()))
								|| (!trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost().equals(trainsToAnalyzeForComplianceThisCase.get(i).getMaximumPriority())))
						{
							prioritiesInTrainFileMatchOptionFile = false;
							resultsMessage += "Priorities on train "+trainsToAnalyzeForComplianceThisCase.get(i).getSymbol()+" are out of sync\n";
							break;
						}
						else if ((trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost().equals(trainsToAnalyzeForComplianceThisCase.get(i).getMinimumPriority()))
								&& (trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost().equals(trainsToAnalyzeForComplianceThisCase.get(i).getInitialPriority()))
								&& (trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost().equals(trainsToAnalyzeForComplianceThisCase.get(i).getMaximumPriority())))
						{
							break;
						}
					}
					else if (j == trainPrioritiesFromThisCasesOptionFile.size() - 1)
					{
						// Train type not found
						prioritiesInTrainFileMatchOptionFile = false;
					}
				}
			}
		}

		// Check that priorities match between both .OPTION files, if selected
		if ((BIASJuaComplianceConfigController.getCheckLastAcceptedOptionsFile()) && (ReadJuaComplianceFiles.retrieveTrainPrioritiesLastAcceptedCase() != null))
		{
			resultsMessage += "Checking that priorities in .OPTION files of this case and last accepted case are the same\n";

			prioritiesInOptionFilesMatch = true;
			
			// Create a merged set of train types from both cases
			HashSet<String> hashSetOfMergedTrainTypes = new HashSet<String>();
			for (int i = 0; i < trainPrioritiesFromThisCasesOptionFile.size(); i++)
			{
				hashSetOfMergedTrainTypes.add(trainPrioritiesFromThisCasesOptionFile.get(i).getType());
			}

			for (int i = 0; i < trainPrioritiesFromLastAcceptedCasesOptionFile.size(); i++)
			{
				hashSetOfMergedTrainTypes.add(trainPrioritiesFromLastAcceptedCasesOptionFile.get(i).getType());
			}

			ArrayList<String> arrayListOfMergedTrainTypes = new ArrayList<String>();
			arrayListOfMergedTrainTypes.addAll(hashSetOfMergedTrainTypes);

			trainType:
			for (int i = 0; i < arrayListOfMergedTrainTypes.size(); i++)
			{
				for (int j = 0; j < trainPrioritiesFromThisCasesOptionFile.size(); j++) // This case
				{
					for (int k = 0; k < trainPrioritiesFromLastAcceptedCasesOptionFile.size(); k++) // Last Accepted case
					{
						if ((arrayListOfMergedTrainTypes.get(i).equals(trainPrioritiesFromThisCasesOptionFile.get(j).getType()))
							&& (arrayListOfMergedTrainTypes.get(i).equals(trainPrioritiesFromLastAcceptedCasesOptionFile.get(k).getType())))
						{
							if ((trainPrioritiesFromThisCasesOptionFile.get(j).getMinimumDelayCost().equals(trainPrioritiesFromLastAcceptedCasesOptionFile.get(k).getMinimumDelayCost()))
									&& (trainPrioritiesFromThisCasesOptionFile.get(j).getInitialDelayCost().equals(trainPrioritiesFromLastAcceptedCasesOptionFile.get(k).getInitialDelayCost()))
									&& (trainPrioritiesFromThisCasesOptionFile.get(j).getMaximumDelayCost().equals(trainPrioritiesFromLastAcceptedCasesOptionFile.get(k).getMaximumDelayCost()))
									&& (trainPrioritiesFromThisCasesOptionFile.get(j).getConflictRank().equals(trainPrioritiesFromLastAcceptedCasesOptionFile.get(k).getConflictRank())))
							{
								continue trainType;
							}
							else
							{
								prioritiesInOptionFilesMatch = false;
								break;
							}
						}
					}
				}
				prioritiesInOptionFilesMatch = false;
				break;
			}
		}
		
		// Check recovery rates
		if (BIASJuaComplianceConfigController.getCheckRecoveryRates())
		{
			RecoveryRateAnalysisForJUACompliance analyzeTrainsForRecoveryRates = new RecoveryRateAnalysisForJUACompliance(ReadJuaComplianceFiles.getTrainsReadInForRecoveryAnalysisThisCase(), ReadJuaComplianceFiles.getTrainToGroupAssignmentsForRecoveryAnalysisThisCase(), ReadJuaComplianceFiles.getTrainToTypeAssignmentsForRecoveryAnalysisThisCase());
			setAResults = analyzeTrainsForRecoveryRates. getAnalyzedTrainsSetA();
			setBResults = analyzeTrainsForRecoveryRates. getAnalyzedTrainsSetB();
			setCResults = analyzeTrainsForRecoveryRates. getAnalyzedTrainsSetC();
			setDResults = analyzeTrainsForRecoveryRates. getAnalyzedTrainsSetD();
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
		dailyAverageCountOfBrightlineOperatedTrainsThisCase = (double) totalCountOfBrightlineOperatedTrainsThisCase / (double) (statisticalEndDay - statisticalStartDay);

		return dailyAverageCountOfBrightlineOperatedTrainsThisCase;
	}

	public static Double getDailyAverageCountOfBrightlineOperatedTrainsLastAcceptedCase()
	{
		dailyAverageCountOfBrightlineOperatedTrainsLastAcceptedCase = (double) totalCountOfBrightlineOperatedTrainsLastAcceptedCase / (double) (statisticalEndDay - statisticalStartDay);

		return dailyAverageCountOfBrightlineOperatedTrainsLastAcceptedCase;
	}

	public static Double getDailyAverageCountOfFecOperatedTrainsThisCase()
	{
		dailyAverageCountOfFecOperatedTrainsThisCase = (double) totalCountOfFecOperatedTrainsThisCase / (double) (statisticalEndDay - statisticalStartDay);

		return dailyAverageCountOfFecOperatedTrainsThisCase;
	}

	public static Double getDailyAverageCountOfFecOperatedTrainsLastAcceptedCase()
	{
		dailyAverageCountOfFecOperatedTrainsLastAcceptedCase = (double) totalCountOfFecOperatedTrainsLastAcceptedCase / (double) (statisticalEndDay - statisticalStartDay);

		return dailyAverageCountOfFecOperatedTrainsLastAcceptedCase;
	}

	public static Double getDailyAverageCountOfTriRailOperatedTrainsLastAcceptedCase()
	{
		dailyAverageCountOfTriRailOperatedTrainsLastAcceptedCase = (double) totalCountOfTriRailOperatedTrainsLastAcceptedCase / (double) (statisticalEndDay - statisticalStartDay);

		return dailyAverageCountOfTriRailOperatedTrainsLastAcceptedCase;
	}

	public static Double getDailyAverageCountOfTriRailOperatedTrainsThisCase()
	{
		dailyAverageCountOfTriRailOperatedTrainsThisCase = (double) totalCountOfTriRailOperatedTrainsThisCase / (double) (statisticalEndDay - statisticalStartDay);

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

	public static HashMap<String, Double> getSumOfSeedDurationsByTypeThisCase()
	{
		return sumOfSeedDurationsByTypeThisCase;
	}

	public static HashMap<String, Double> getSumOfSeedDurationsByTypeLastAcceptedCase()
	{
		return sumOfSeedDurationsByTypeLastAcceptedCase;
	}

	public static HashMap<String, Double> getSumOfSeedTrainDwellByLocationThisCase()
	{
		return sumOfSeedTrainDwellByLocationThisCase;
	}

	public static HashMap<String, Double> getSumOfSeedTrainDwellByLocationLastAcceptedCase()
	{
		return sumOfSeedTrainDwellByLocationLastAcceptedCase;
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

	public static Integer getLinkCountThisCase()
	{
		return linkCountThisCase;
	}

	public static Integer getLinkCountLastAcceptedCase()
	{
		return linkCountLastAcceptedCase;
	}

	public static Double getMilesOfTrackThisCase()
	{
		return milesOfTrackThisCase;
	}

	public static Double getMilesOfTrackLastAcceptedCase()
	{
		return milesOfTrackLastAcceptedCase;
	}

	public static Double getPassengerSpeedMilesThisCase()
	{
		return passengerSpeedMilesThisCase;
	}

	public static Double getPassengerSpeedMilesLastAcceptedCase()
	{
		return passengerSpeedMilesLastAcceptedCase;
	}

	public static Double getThroughSpeedMilesThisCase()
	{
		return throughSpeedMilesThisCase;
	}

	public static Double getThroughSpeedMilesLastAcceptedCase()
	{
		return throughSpeedMilesLastAcceptedCase;
	}

	public static Double getLocalSpeedMilesThisCase()
	{
		return localSpeedMilesThisCase;
	}

	public static Double getLocalSpeedMilesLastAcceptedCase()
	{
		return localSpeedMilesLastAcceptedCase;
	}

	public static Integer getTier1LowDelayCost()
	{
		return tier1LowDelayCost;
	}

	public static Integer getTier1HighDelayCost()
	{
		return tier1HighDelayCost;
	}

	public static Integer getTier2LowDelayCost()
	{
		return tier2LowDelayCost;
	}

	public static Integer getTier2HighDelayCost()
	{
		return tier2HighDelayCost;
	}

	public static Integer getTier3LowDelayCost()
	{
		return tier3LowDelayCost;
	}

	public static Integer getTier3HighDelayCost()
	{
		return tier3HighDelayCost;
	}

	public static Integer getTier4LowDelayCost()
	{
		return tier4LowDelayCost;
	}

	public static Integer getTier4HighDelayCost()
	{
		return tier4HighDelayCost;
	}

	public static Integer getTier1LowRank()
	{
		return tier1LowRank;
	}

	public static Integer getTier1HighRank()
	{
		return tier1HighRank;
	}

	public static Integer getTier2LowRank()
	{
		return tier2LowRank;
	}

	public static Integer getTier2HighRank()
	{
		return tier2HighRank;
	}

	public static Integer getTier3LowRank()
	{
		return tier3LowRank;
	}

	public static Integer getTier3HighRank()
	{
		return tier3HighRank;
	}

	public static Integer getTier4LowRank()
	{
		return tier4LowRank;
	}

	public static Integer getTier4HighRank()
	{
		return tier4HighRank;
	}

	public static HashMap<String, Integer> getSeedTrainsByTypeThisCase()
	{
		return  seedTrainsByTypeThisCase;
	}

	public static HashMap<String, Integer> getSeedTrainsByTypeLastAcceptedCase()
	{
		return  seedTrainsByTypeLastAcceptedCase;
	}

	public static HashMap<String, Integer> getTrainsOperatedByTypeThisCase()
	{
		return  operatedTrainsByTypeThisCase;
	}

	public static HashMap<String, Integer> getTrainsOperatedByTypeLastAcceptedCase()
	{
		return  operatedTrainsByTypeLastAcceptedCase;
	}

	public static ArrayList<String> getAllTrainTypesFromAnalysisFile()
	{
		return allTrainTypesFromConfigFile;
	}
	
	public static ArrayList<TrainAssessment> getRecoveryRateSetAResults()
	{
		return setAResults;
	}
	
	public static ArrayList<TrainAssessment> getRecoveryRateSetBResults()
	{
		return setBResults;
	}
	
	public static ArrayList<TrainAssessment> getRecoveryRateSetCResults()
	{
		return setCResults;
	}
	
	public static ArrayList<TrainAssessment> getRecoveryRateSetDResults()
	{
		return setDResults;
	}

	public static Boolean getErrorsWithTrainPriorityThisCase()
	{
		if ((prioritiesInTrainFileMatchOptionFile == false) || (orderingOfRanksComportsWithJua == false) || (orderingOfPrioritiesComportsWithJua == false))
			return true;
		else
			return false;
	}
	
	public static Boolean getErrorsWithTrainPriorityLastAcceptedCase()
	{
		return prioritiesInOptionFilesMatch;
	}
	
	public static String getResultsMessage()
	{
		return resultsMessage;
	}
}