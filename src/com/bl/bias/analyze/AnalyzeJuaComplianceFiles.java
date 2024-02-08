package com.bl.bias.analyze;

import java.util.ArrayList;
import java.util.Collections;

import com.bl.bias.app.BIASJuaComplianceConfigController;
import com.bl.bias.app.BIASRecoveryRateAnalysisConfigController;
import com.bl.bias.objects.CompliancePermit;
import com.bl.bias.objects.ComplianceTrain;
import com.bl.bias.read.ReadJuaComplianceFiles;
import com.bl.bias.tools.ConvertDateTime;

public class AnalyzeJuaComplianceFiles 
{
	private static String resultsMessage;

	// Trains
	private static Integer totalCountOfBrightlineOperatedTrains;
	private static Integer totalCountOfFecOperatedTrains;
	private static Integer totalCountOfTriRailOperatedTrains;

	private static Double dailyAverageCountOfBrightlineOperatedTrains;
	private static Double dailyAverageCountOfFecOperatedTrains;
	private static Double dailyAverageCountOfTriRailOperatedTrains;

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

	Boolean debug = false;

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
			statisticalEndDay = statisticalStartDay + ReadJuaComplianceFiles.getStatisticalDurationInDays(); 

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

				// This case
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

				// Last accepted case
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