package com.bl.bias.read;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.bl.bias.app.BIASJuaComplianceConfigController;
import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.ComplianceLink;
import com.bl.bias.objects.CompliancePermit;
import com.bl.bias.objects.CompliancePriority;
import com.bl.bias.objects.ComplianceTrain;
import com.bl.bias.objects.ComplianceTrainRouteEntry;
import com.bl.bias.objects.TrainAssessment;
import com.bl.bias.tools.ConvertDateTime;

public class ReadJuaComplianceFiles
{
	private static ArrayList<ComplianceTrain> complianceTrainsThisCase = new ArrayList<ComplianceTrain>();
	private static ArrayList<ComplianceTrain> comparisonTrainsLastAcceptedCase = new ArrayList<ComplianceTrain>();
	private static ArrayList<CompliancePermit> comparisonPermitsThisCase = new ArrayList<CompliancePermit>();
	private static ArrayList<CompliancePermit> compliancePermitsLastAcceptedCase = new ArrayList<CompliancePermit>();
	private static ArrayList<ComplianceLink> comparisonLinksThisCase = new ArrayList<ComplianceLink>();
	private static ArrayList<ComplianceLink> comparisonLinksLastAcceptedCase = new ArrayList<ComplianceLink>();
	private static ArrayList<CompliancePriority> trainPrioritiesThisCase = new ArrayList<CompliancePriority>();
	private static ArrayList<CompliancePriority> trainPrioritiesLastAcceptedCase = new ArrayList<CompliancePriority>();
	
	private static ArrayList<TrainAssessment> trainsReadIn = new ArrayList<TrainAssessment>();
	private static HashMap<String, String> trainToGroupAssignment = new HashMap<String, String>();
	private static HashMap<String, String> trainToTypeAssignment = new HashMap<String, String>();

	private static String resultsMessage;

	static Boolean formattedCorrectly = true;

	static String simulationStartDayOfWeek = null;
	static String simulationDuration = null;
	static String warmUpExclusion = null;
	static String coolDownExclusion = null;
	static String simulationBeginTime = null;

	public ReadJuaComplianceFiles(String fileOfCaseBeingChecked, Boolean checkTrainCount, Boolean checkPermits, Boolean checkLinks, Boolean checkRecoveryRates) throws IOException
	{
		resultsMessage = "\nStarted parsing JUA Compliance files at "+ConvertDateTime.getTimeStamp()+"\n";

		complianceTrainsThisCase.clear();
		comparisonTrainsLastAcceptedCase.clear();
		comparisonPermitsThisCase.clear();
		compliancePermitsLastAcceptedCase.clear();
		comparisonLinksThisCase.clear();
		comparisonLinksLastAcceptedCase.clear();
		trainPrioritiesThisCase.clear();
		trainPrioritiesLastAcceptedCase.clear();
		trainsReadIn.clear();
		trainToGroupAssignment.clear();
		trainToTypeAssignment.clear();

		// Read in this cases .OPTION file with Scanner
		Scanner scannerOption = null;
		try 
		{
			String targetSequence0 = "-----------------------------------------";
			Boolean firstTargetFound = false;
			Boolean secondTargetFound = false;
			Boolean thirdTargetFound = false;
			Boolean fourthTargetFound = false;

			File optionFile = new File(fileOfCaseBeingChecked);
			scannerOption = new Scanner(optionFile);

			while (scannerOption.hasNextLine()) 
			{
				String lineFromOptionFile = scannerOption.nextLine();
				if (lineFromOptionFile.contains("Simulation begin day: "))
				{
					simulationStartDayOfWeek = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginDay()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginDay()[1])).trim();
				}
				else if (lineFromOptionFile.contains("Simulation duration (DD:HH:MM): "))
				{
					simulationDuration = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getSimulationDuration()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationDuration()[1]));
				}
				else if (lineFromOptionFile.contains("Warm-up statistical exclusion (DD:HH:MM): "))
				{
					warmUpExclusion = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getWarmUpExclusion()[0]), Integer.valueOf(BIASParseConfigPageController.o_getWarmUpExclusion()[1]));
				}
				else if (lineFromOptionFile.contains("Cool-down statistical exclusion (DD:HH:MM): "))
				{
					coolDownExclusion = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getCoolDownExclusion()[0]), Integer.valueOf(BIASParseConfigPageController.o_getCoolDownExclusion()[1]));
				}
				else if (lineFromOptionFile.contains("Simulation begin time (HH:MM): "))
				{
					simulationBeginTime = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginTime()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginTime()[1]));
				}
				else if (lineFromOptionFile.contains("[Train types]"))
				{
					if ((!firstTargetFound) && (!secondTargetFound) && (!thirdTargetFound))
					{
						firstTargetFound = true;
					}
				}
				else if ((firstTargetFound) && (!secondTargetFound) && (!thirdTargetFound) && (!fourthTargetFound) && (lineFromOptionFile.contains(targetSequence0)))
				{
					secondTargetFound = true;
				}
				else if ((firstTargetFound) && (secondTargetFound) && (!thirdTargetFound) && (!fourthTargetFound) && (lineFromOptionFile.contains(targetSequence0)))
				{
					thirdTargetFound = true;
				}	
				else if ((firstTargetFound) && (secondTargetFound) && (thirdTargetFound) && (!fourthTargetFound))
				{
					if (lineFromOptionFile.contains(targetSequence0))
					{
						fourthTargetFound = true;
					}
					else
					{
						String trainType = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeName()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeName()[1])).trim();
						String minimumDelayCost = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getMinimumDelayCost()[0]), Integer.valueOf(BIASParseConfigPageController.o_getMinimumDelayCost()[1])).trim();
						String initialDelayCost = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getInitialDelayCost()[0]), Integer.valueOf(BIASParseConfigPageController.o_getInitialDelayCost()[1])).trim();
						String maximumDelayCost = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getMaximumDelayCost()[0]), Integer.valueOf(BIASParseConfigPageController.o_getMaximumDelayCost()[1])).trim();
						String conflictRank = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getConflictRank()[0]), Integer.valueOf(BIASParseConfigPageController.o_getConflictRank()[1])).trim();

						if ((trainType == null) || (trainType.equals("")))
						{
							continue;
						}
						else
						{
							CompliancePriority trainPriority = new CompliancePriority(trainType, Integer.valueOf(minimumDelayCost), Integer.valueOf(initialDelayCost), Integer.valueOf(maximumDelayCost), Integer.valueOf(conflictRank));
							trainPrioritiesThisCase.add(trainPriority);
						}
					}
				}
			}
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		finally
		{
			resultsMessage += "Extracted hierarchy of "+trainPrioritiesThisCase.size()+" train type priorities from this case's .OPTION file\n";
			scannerOption.close();
		}

		// Read in last accepted cases .OPTION file with Scanner
		if ((BIASJuaComplianceConfigController.getCheckLastAcceptedOptionsFile()) && (BIASJuaComplianceConfigController.getLastAcceptedOptionFileExists()))
		{
			scannerOption = null;
			try 
			{
				String targetSequence0 = "-----------------------------------------";
				Boolean firstTargetFound = false;
				Boolean secondTargetFound = false;
				Boolean thirdTargetFound = false;
				Boolean fourthTargetFound = false;

				File optionFile = new File(BIASJuaComplianceConfigController.getLastAcceptedOptionFileAsString());
				scannerOption = new Scanner(optionFile);

				while (scannerOption.hasNextLine()) 
				{
					String lineFromOptionFile = scannerOption.nextLine();
					if (lineFromOptionFile.contains("Simulation begin day: "))
					{
						simulationStartDayOfWeek = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginDay()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginDay()[1])).trim();
					}
					else if (lineFromOptionFile.contains("Simulation duration (DD:HH:MM): "))
					{
						simulationDuration = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getSimulationDuration()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationDuration()[1]));
					}
					else if (lineFromOptionFile.contains("Warm-up statistical exclusion (DD:HH:MM): "))
					{
						warmUpExclusion = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getWarmUpExclusion()[0]), Integer.valueOf(BIASParseConfigPageController.o_getWarmUpExclusion()[1]));
					}
					else if (lineFromOptionFile.contains("Cool-down statistical exclusion (DD:HH:MM): "))
					{
						coolDownExclusion = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getCoolDownExclusion()[0]), Integer.valueOf(BIASParseConfigPageController.o_getCoolDownExclusion()[1]));
					}
					else if (lineFromOptionFile.contains("Simulation begin time (HH:MM): "))
					{
						simulationBeginTime = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginTime()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginTime()[1]));
					}
					else if (lineFromOptionFile.contains("[Train types]"))
					{
						if ((!firstTargetFound) && (!secondTargetFound) && (!thirdTargetFound))
						{
							firstTargetFound = true;
						}
					}
					else if ((firstTargetFound) && (!secondTargetFound) && (!thirdTargetFound) && (!fourthTargetFound) && (lineFromOptionFile.contains(targetSequence0)))
					{
						secondTargetFound = true;
					}
					else if ((firstTargetFound) && (secondTargetFound) && (!thirdTargetFound) && (!fourthTargetFound) && (lineFromOptionFile.contains(targetSequence0)))
					{
						thirdTargetFound = true;
					}	
					else if ((firstTargetFound) && (secondTargetFound) && (thirdTargetFound) && (!fourthTargetFound))
					{
						if (lineFromOptionFile.contains(targetSequence0))
						{
							fourthTargetFound = true;
						}
						else
						{
							String trainType = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeName()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeName()[1])).trim();
							String minimumDelayCost = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getMinimumDelayCost()[0]), Integer.valueOf(BIASParseConfigPageController.o_getMinimumDelayCost()[1])).trim();
							String initialDelayCost = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getInitialDelayCost()[0]), Integer.valueOf(BIASParseConfigPageController.o_getInitialDelayCost()[1])).trim();
							String maximumDelayCost = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getMaximumDelayCost()[0]), Integer.valueOf(BIASParseConfigPageController.o_getMaximumDelayCost()[1])).trim();
							String conflictRank = lineFromOptionFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getConflictRank()[0]), Integer.valueOf(BIASParseConfigPageController.o_getConflictRank()[1])).trim();

							if ((trainType == null) || (trainType.equals("")))
							{
								continue;
							}
							else
							{
								CompliancePriority trainPriority = new CompliancePriority(trainType, Integer.valueOf(minimumDelayCost), Integer.valueOf(initialDelayCost), Integer.valueOf(maximumDelayCost), Integer.valueOf(conflictRank));
								trainPrioritiesLastAcceptedCase.add(trainPriority);
							}
						}
					}
				}
			}
			catch (Exception e) 
			{
				ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
			}
			finally
			{
				resultsMessage += "Extracted hierarchy of "+trainPrioritiesThisCase.size()+" train type priorities from the last accepted .OPTION file\n";
				scannerOption.close();
			}
		}

		// Check .OPTION parameters to determine if compliance analysis can continue
		if (!simulationDuration.substring(3, 8).equals("00:00"))
			formattedCorrectly = false;
		if (!simulationBeginTime.substring(6, 8).equals("00"))
			formattedCorrectly = false;
		if (!warmUpExclusion.substring(3, 8).equals("00:00"))
			formattedCorrectly = false;
		if (!coolDownExclusion.substring(3, 8).equals("00:00"))
			formattedCorrectly = false;

		if (formattedCorrectly)
		{
			// Train compliance checks
			Integer objectCount = 0;
			if (checkTrainCount)
			{
				// Read in .TRAIN files with Scanner
				// This case
				Scanner scannerTrain = null; 
				File trainFileOfCaseBeingChecked = new File(fileOfCaseBeingChecked.replace("OPTION","TRAIN"));
				scannerTrain = new Scanner(trainFileOfCaseBeingChecked);

				try 
				{
					objectCount = 0;
					String trainSymbol = null;
					String trainType = null;
					String linkedAtOriginTo = null;
					String enabled = null;
					Integer minimumPriority = null;
					Integer initialPriority = null;
					Integer maximumPriority = null;
					ArrayList<ComplianceTrainRouteEntry> routeEntries = null;
					ArrayList<Integer> daysOfOperationAsInteger = null;

					String targetSequence0 = "Train symbol: ";
					String targetSequence1 = "Train type: ";
					String targetSequence2 = "Initial priority (0-9999): ";
					String targetSequence3 = "Week  1 Frequency: ";
					String targetSequence4 = "Week  2 Frequency: ";
					String targetSequence5 = "Week  3 Frequency: ";
					String targetSequence6 = "Enabled: ";
					String targetSequence7 = "Linked at origin to the: ";
					String targetSequence8 = "Route Node";
					String targetSequence9 = "=========================================";

					Boolean inRouteNodeSection = false;
					Boolean firstTrainFound = false;

					while (scannerTrain.hasNextLine()) 
					{
						String lineFromTrainFile = scannerTrain.nextLine();
						if ((lineFromTrainFile.contains(targetSequence9)) && (!firstTrainFound)) // Found first train
						{
							firstTrainFound = true;
						}
						else if (lineFromTrainFile.contains(targetSequence0)) // Train symbol
						{ 
							daysOfOperationAsInteger = new ArrayList<Integer>();
							routeEntries = new ArrayList<ComplianceTrainRouteEntry>();
							trainSymbol = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[1])).trim();
							objectCount++;
						}
						else if(lineFromTrainFile.contains(targetSequence1)) // Train type
						{ 
							trainType = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainType()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainType()[1])).trim();
							objectCount++;
						}
						else if(lineFromTrainFile.contains(targetSequence2)) // Train priority
						{ 
							minimumPriority = Integer.valueOf(lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getMinimumDelayCost()[0]), Integer.valueOf(BIASParseConfigPageController.t_getMinimumDelayCost()[1])).trim());
							initialPriority = Integer.valueOf(lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getInitialDelayCost()[0]), Integer.valueOf(BIASParseConfigPageController.t_getInitialDelayCost()[1])).trim());
							maximumPriority = Integer.valueOf(lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getMaximumDelayCost()[0]), Integer.valueOf(BIASParseConfigPageController.t_getMaximumDelayCost()[1])).trim());
							objectCount+=3;
						}
						else if(lineFromTrainFile.contains(targetSequence3)) // Days 1 - 7
						{ 
							ArrayList<String> week1Days = new ArrayList<String>();
							String week1DaysAsString = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[0]), Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[1])).trim();
							String[] splitted = week1DaysAsString.split(" ");
							for (int i = 0; i < splitted.length; i++)
								week1Days.add(splitted[i].trim().toUpperCase());
							daysOfOperationAsInteger.addAll(convertDOWtoInteger(week1Days, 1));
							objectCount+=7;
						}
						else if(lineFromTrainFile.contains(targetSequence4)) // Days 8 - 14
						{ 
							ArrayList<String> week2Days = new ArrayList<String>();
							String week2DaysAsString = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[0]), Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[1])).trim();
							String[] splitted = week2DaysAsString.split(" ");
							for (int i = 0; i < splitted.length; i++)
								week2Days.add(splitted[i].trim().toUpperCase());
							daysOfOperationAsInteger.addAll(convertDOWtoInteger(week2Days, 2));
							objectCount+=7;
						}
						else if(lineFromTrainFile.contains(targetSequence5)) // Days 15 - 21
						{ 
							ArrayList<String> week3Days = new ArrayList<String>();
							String week3DaysAsString = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[0]), Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[1])).trim();
							String[] splitted = week3DaysAsString.split(" ");
							for (int i = 0; i < splitted.length; i++)
								week3Days.add(splitted[i].trim().toUpperCase());
							daysOfOperationAsInteger.addAll(convertDOWtoInteger(week3Days, 3));
							objectCount+=7;
						}
						else if (lineFromTrainFile.contains(targetSequence6)) // Train enabled
						{ 
							enabled = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainEnabled()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainEnabled()[1])).trim();
							objectCount++;
						}
						else if (lineFromTrainFile.contains(targetSequence7)) // Linked at origin to
						{ 
							linkedAtOriginTo = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getLinkedAtOrigin()[0]), Integer.valueOf(BIASParseConfigPageController.t_getLinkedAtOrigin()[1])).trim();
							objectCount++;
						}
						else if (((inRouteNodeSection) && (lineFromTrainFile.contains(targetSequence9))) 
								|| (!scannerTrain.hasNextLine())) // Create the train
						{
							inRouteNodeSection = false;

							ComplianceTrain train = new ComplianceTrain(trainSymbol, trainType, linkedAtOriginTo, enabled, minimumPriority, initialPriority, maximumPriority, routeEntries, daysOfOperationAsInteger);
							complianceTrainsThisCase.add(train);	
						}
						else if (inRouteNodeSection) // Route node
						{
							if (!lineFromTrainFile.equals(""))
							{
								routeEntries.add(new ComplianceTrainRouteEntry(lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getRouteNode()[0]), Integer.valueOf(BIASParseConfigPageController.t_getRouteNode()[1])).trim(),
										lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.t_getArrivalTime()[1])).trim(),
										lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.t_getDepartureTime()[1])).trim(),
										lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getMinimumDwellTime()[0]), Integer.valueOf(BIASParseConfigPageController.t_getMinimumDwellTime()[1])).trim()));
								objectCount+=4;
							}
						}
						else if ((!inRouteNodeSection) && (lineFromTrainFile.contains(targetSequence8))) // Enter the route node section
						{
							inRouteNodeSection = true;
							scannerTrain.nextLine();
							scannerTrain.nextLine();
						}
					}
				}
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}
				finally
				{
					scannerTrain.close();
					resultsMessage += "Extracted data from "+objectCount+" objects from this case's .TRAIN file\n";
				}

				// Last accepted train file 
				if ((BIASJuaComplianceConfigController.getLastAcceptedTrainFileExists()) && (BIASJuaComplianceConfigController.getLastAcceptedTrainFileAsString() != null) && (BIASJuaComplianceConfigController.getCheckLastAcceptedTrainsFile()))
				{
					File trainFileOfLastAcceptedCase = new File(BIASJuaComplianceConfigController.getLastAcceptedTrainFileAsString());
					scannerTrain = new Scanner(trainFileOfLastAcceptedCase);

					try 
					{
						objectCount = 0;
						String trainSymbol = null;
						String trainType = null;
						String linkedAtOriginTo = null;
						String enabled = null;
						Integer minimumPriority = null;
						Integer initialPriority = null;
						Integer maximumPriority = null;
						ArrayList<ComplianceTrainRouteEntry> routeEntries = null;
						ArrayList<Integer> daysOfOperationAsInteger = null;

						String targetSequence0 = "Train symbol: ";
						String targetSequence1 = "Train type: ";
						String targetSequence2 = "Initial priority (0-9999): ";
						String targetSequence3 = "Week  1 Frequency: ";
						String targetSequence4 = "Week  2 Frequency: ";
						String targetSequence5 = "Week  3 Frequency: ";
						String targetSequence6 = "Enabled: ";
						String targetSequence7 = "Linked at origin to the: ";
						String targetSequence8 = "Route Node";
						String targetSequence9 = "=========================================";

						Boolean inRouteNodeSection = false;
						Boolean firstTrainFound = false;

						while (scannerTrain.hasNextLine()) 
						{
							String lineFromTrainFile = scannerTrain.nextLine();
							if ((lineFromTrainFile.contains(targetSequence9)) && (!firstTrainFound)) // Found first train
							{
								firstTrainFound = true;
							}
							else if (lineFromTrainFile.contains(targetSequence0)) // Train symbol
							{ 
								daysOfOperationAsInteger = new ArrayList<Integer>();
								routeEntries = new ArrayList<ComplianceTrainRouteEntry>();
								trainSymbol = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[1])).trim();
								objectCount++;
							}
							else if(lineFromTrainFile.contains(targetSequence1)) // Train type
							{ 
								trainType = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainType()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainType()[1])).trim();
								objectCount++;
							}
							else if(lineFromTrainFile.contains(targetSequence2)) // Train priority
							{ 
								minimumPriority = Integer.valueOf(lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getMinimumDelayCost()[0]), Integer.valueOf(BIASParseConfigPageController.t_getMinimumDelayCost()[1])).trim());
								initialPriority = Integer.valueOf(lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getInitialDelayCost()[0]), Integer.valueOf(BIASParseConfigPageController.t_getInitialDelayCost()[1])).trim());
								maximumPriority = Integer.valueOf(lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getMaximumDelayCost()[0]), Integer.valueOf(BIASParseConfigPageController.t_getMaximumDelayCost()[1])).trim());
								objectCount+=3;
							}
							else if(lineFromTrainFile.contains(targetSequence3)) // Days 1 - 7
							{ 
								ArrayList<String> week1Days = new ArrayList<String>();
								String week1DaysAsString = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[0]), Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[1])).trim();
								String[] splitted = week1DaysAsString.split(" ");
								for (int i = 0; i < splitted.length; i++)
									week1Days.add(splitted[i].trim().toUpperCase());
								daysOfOperationAsInteger.addAll(convertDOWtoInteger(week1Days, 1));
								objectCount+=7;
							}
							else if(lineFromTrainFile.contains(targetSequence4)) // Days 8 - 14
							{ 
								ArrayList<String> week2Days = new ArrayList<String>();
								String week2DaysAsString = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[0]), Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[1])).trim();
								String[] splitted = week2DaysAsString.split(" ");
								for (int i = 0; i < splitted.length; i++)
									week2Days.add(splitted[i].trim().toUpperCase());
								daysOfOperationAsInteger.addAll(convertDOWtoInteger(week2Days, 2));
								objectCount+=7;
							}
							else if(lineFromTrainFile.contains(targetSequence5)) // Days 15 - 21
							{ 
								ArrayList<String> week3Days = new ArrayList<String>();
								String week3DaysAsString = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[0]), Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[1])).trim();
								String[] splitted = week3DaysAsString.split(" ");
								for (int i = 0; i < splitted.length; i++)
									week3Days.add(splitted[i].trim().toUpperCase());
								daysOfOperationAsInteger.addAll(convertDOWtoInteger(week3Days, 3));
								objectCount+=7;
							}
							else if (lineFromTrainFile.contains(targetSequence6)) // Train enabled
							{ 
								enabled = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainEnabled()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainEnabled()[1])).trim();
								objectCount++;
							}
							else if (lineFromTrainFile.contains(targetSequence7)) // Linked at origin to
							{ 
								linkedAtOriginTo = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getLinkedAtOrigin()[0]), Integer.valueOf(BIASParseConfigPageController.t_getLinkedAtOrigin()[1])).trim();
								objectCount++;
							}
							else if (((inRouteNodeSection) && (lineFromTrainFile.contains(targetSequence9))) 
									|| (!scannerTrain.hasNextLine())) // Create the train
							{
								inRouteNodeSection = false;

								ComplianceTrain train = new ComplianceTrain(trainSymbol, trainType, linkedAtOriginTo, enabled, minimumPriority, initialPriority, maximumPriority, routeEntries, daysOfOperationAsInteger);
								comparisonTrainsLastAcceptedCase.add(train);	
							}
							else if (inRouteNodeSection) // Route node
							{
								if (!lineFromTrainFile.equals(""))
								{
									routeEntries.add(new ComplianceTrainRouteEntry(lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getRouteNode()[0]), Integer.valueOf(BIASParseConfigPageController.t_getRouteNode()[1])).trim(),
											lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.t_getArrivalTime()[1])).trim(),
											lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.t_getDepartureTime()[1])).trim(),
											lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getMinimumDwellTime()[0]), Integer.valueOf(BIASParseConfigPageController.t_getMinimumDwellTime()[1])).trim()));
									objectCount+=4;
								}
							}
							else if ((!inRouteNodeSection) && (lineFromTrainFile.contains(targetSequence8))) // Enter the route node section
							{
								inRouteNodeSection = true;
								scannerTrain.nextLine();
								scannerTrain.nextLine();
							}
						}
					}
					catch (Exception e) 
					{
						ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
					}
					finally
					{
						scannerTrain.close();
						resultsMessage += "Extracted data from "+objectCount+" objects from last accepted .TRAIN file\n";
					}
				}

			}

			// Permit Comparison Checks
			if ((checkPermits) && (BIASJuaComplianceConfigController.getLastAcceptedTrainFileExists()))
			{
				// Read in permits of case being checked
				comparisonPermitsThisCase = new ArrayList<CompliancePermit>();
				comparisonPermitsThisCase.addAll(retrievePermits(new File(fileOfCaseBeingChecked.replace("OPTION","PERMIT"))));

				// Read in permits of last accepted .permit file
				compliancePermitsLastAcceptedCase = new ArrayList<CompliancePermit>();
				compliancePermitsLastAcceptedCase.addAll(retrievePermits(new File(BIASJuaComplianceConfigController.getLastAcceptedPermitFileAsString())));

				resultsMessage += "Extracted data from " + ((comparisonPermitsThisCase.size() + compliancePermitsLastAcceptedCase.size()) * 9)+" objects from both .PERMIT files\n";
			}

			// Link Comparison Checks
			if ((checkLinks) && (BIASJuaComplianceConfigController.getLastAcceptedLinkFileExists()))
			{
				// Read in permits of case being checked
				comparisonLinksThisCase = new ArrayList<ComplianceLink>();
				comparisonLinksThisCase.addAll(retrieveLinks(new File(fileOfCaseBeingChecked.replace("OPTION","LINK"))));

				// Read in permits of last accepted .permit file
				comparisonLinksLastAcceptedCase = new ArrayList<ComplianceLink>();
				comparisonLinksLastAcceptedCase.addAll(retrieveLinks(new File(BIASJuaComplianceConfigController.getLastAcceptedLinkFileAsString())));

				resultsMessage += "Extracted data from " + ((comparisonLinksThisCase.size() + comparisonLinksLastAcceptedCase.size()) * 4)+" objects from both .LINK files\n";
			}
			
			// Check recovery rates
			if ((checkRecoveryRates) && (checkFileExists(fileOfCaseBeingChecked.replace(".OPTION", ".ROUTE"))))
			{
				ReadRecoveryRateAnalysisFiles readRecoveryRate = new ReadRecoveryRateAnalysisFiles(fileOfCaseBeingChecked.replace(".OPTION", ".ROUTE"));
				trainsReadIn = readRecoveryRate.getTrainsReadIn();
				trainToGroupAssignment = readRecoveryRate.getTrainToGroupAssignment();
				trainToTypeAssignment = readRecoveryRate.getTrainToTypeAssignment();
				
				resultsMessage += "Extracted data for "+trainsReadIn.size()+" trains from this case's .ROUTE file\n";
			}
			
			resultsMessage += "Finished parsing JUA Compliance files at "+ConvertDateTime.getTimeStamp()+"\n\n";
		}
		else
		{
			resultsMessage += "Simulation duration and/or exclusion periods are not set correctly\n";
			scannerOption.close();
		}
	}

	public static ArrayList<TrainAssessment> getTrainsReadInForRecoveryAnalysisThisCase()
	{
		return trainsReadIn;
	}
	
	public static HashMap<String, String> getTrainToGroupAssignmentsForRecoveryAnalysisThisCase()
	{
		return trainToGroupAssignment;
	}
	
	public static HashMap<String, String> getTrainToTypeAssignmentsForRecoveryAnalysisThisCase()
	{
		return trainToTypeAssignment;
	}
	
	public static ArrayList<ComplianceTrain> getTrainsToAnalyzeThisCase()
	{
		return complianceTrainsThisCase;
	}

	public static ArrayList<ComplianceTrain> getTrainsToAnalyzeLastAcceptedCase()
	{
		return comparisonTrainsLastAcceptedCase;
	}

	public static ArrayList<CompliancePermit> getPermitsToAnalyzeThisCase()
	{
		return comparisonPermitsThisCase;
	}

	public static ArrayList<CompliancePermit> getPermitsToAnalyzeLastAcceptedCase()
	{
		return compliancePermitsLastAcceptedCase;
	}

	public static ArrayList<ComplianceLink> getLinksToAnalyzeThisCase() 
	{
		return comparisonLinksThisCase;
	}

	public static ArrayList<ComplianceLink> getLinksToAnalyzeLastAcceptedCase() 
	{
		return comparisonLinksLastAcceptedCase;
	}

	public Boolean getFormattedCorrectly()
	{
		return formattedCorrectly;
	}

	public static Integer getStatisticalStartDayOfWeekAsInteger()
	{
		ArrayList<String> simulationStartDayOfWeekAsArrayList = new ArrayList<String>();
		simulationStartDayOfWeekAsArrayList.add(simulationStartDayOfWeek);
		Integer simulationStartDayOfWeekAsInteger = convertDOWtoInteger(simulationStartDayOfWeekAsArrayList, 1).get(0);
		String warmUpExclusionAsString = warmUpExclusion.substring(0, 2).trim();
		Integer statisticalStartDayOfWeekAsInteger = simulationStartDayOfWeekAsInteger + Integer.valueOf(warmUpExclusionAsString);
		return statisticalStartDayOfWeekAsInteger;
	}

	public static Integer getStatisticalDurationInDays()
	{
		Integer adjustedDuration = Integer.valueOf(simulationDuration.substring(0, 2).trim()); 
		adjustedDuration -= Integer.valueOf(warmUpExclusion.substring(0, 2).trim());
		adjustedDuration -= Integer.valueOf(coolDownExclusion.substring(0, 2).trim());

		return adjustedDuration;
	}

	public static ArrayList<CompliancePriority> retrieveTrainPrioritiesThisCase()
	{
		return trainPrioritiesThisCase;
	}
	
	public static ArrayList<CompliancePriority> retrieveTrainPrioritiesLastAcceptedCase()
	{
		return trainPrioritiesLastAcceptedCase;
	}

	private static ArrayList<Integer> convertDOWtoInteger(ArrayList<String> days, Integer week) // 1 = SUNDAY
	{
		ArrayList<Integer> daysAsInteger = new ArrayList<Integer>();
		for (int i = 0; i < days.size(); i++)
		{
			if (days.get(i).contains("SUN"))
			{
				daysAsInteger.add(1 + ((week - 1) * 7));
			}
			else if (days.get(i).contains("MON"))
			{
				daysAsInteger.add(2 + ((week - 1) * 7));
			}
			else if (days.get(i).contains("TUE"))
			{
				daysAsInteger.add(3 + ((week - 1) * 7));
			}
			else if (days.get(i).contains("WED"))
			{
				daysAsInteger.add(4 + ((week - 1) * 7));
			}
			else if (days.get(i).contains("THU"))
			{
				daysAsInteger.add(5 + ((week - 1) * 7));
			}
			else if (days.get(i).contains("FRI"))
			{
				daysAsInteger.add(6 + ((week - 1) * 7));
			}
			else if (days.get(i).contains("SAT"))
			{
				daysAsInteger.add(7 + ((week - 1) * 7));
			}
		}
		return daysAsInteger;
	}

	private ArrayList<CompliancePermit> retrievePermits(File permitFile) throws FileNotFoundException
	{
		ArrayList<CompliancePermit> permits = new ArrayList<CompliancePermit>();
		Scanner scannerPermit = new Scanner(permitFile);
		try 
		{
			String targetSequence0 = "xxxxxxxxxxxxxxxxxxxx";

			Boolean firstPermitFound = false;

			while (scannerPermit.hasNextLine()) 
			{
				String lineFromPermitFile = scannerPermit.nextLine();
				if ((lineFromPermitFile.contains(targetSequence0)) && (!firstPermitFound)) // Found first permit
				{
					scannerPermit.nextLine();
					firstPermitFound = true;
				}
				else if (firstPermitFound)
				{
					String subdivision = lineFromPermitFile.substring(Integer.valueOf(BIASParseConfigPageController.b_getSubdivision()[0]), Integer.valueOf(BIASParseConfigPageController.b_getSubdivision()[1])).trim();
					String beginMp = lineFromPermitFile.substring(Integer.valueOf(BIASParseConfigPageController.b_getStartMp()[0]), Integer.valueOf(BIASParseConfigPageController.b_getStartMp()[1])).trim();
					String endMp = lineFromPermitFile.substring(Integer.valueOf(BIASParseConfigPageController.b_getEndMp()[0]), Integer.valueOf(BIASParseConfigPageController.b_getEndMp()[1])).trim();
					String pasSpeed = lineFromPermitFile.substring(Integer.valueOf(BIASParseConfigPageController.b_getPasSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.b_getPasSpeed()[1])).trim();
					String frtSpeed = lineFromPermitFile.substring(Integer.valueOf(BIASParseConfigPageController.b_getFrtSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.b_getFrtSpeed()[1])).trim();
					String startTime = lineFromPermitFile.substring(Integer.valueOf(BIASParseConfigPageController.b_getStartTime()[0]), Integer.valueOf(BIASParseConfigPageController.b_getStartTime()[1])).trim();
					String endTime = lineFromPermitFile.substring(Integer.valueOf(BIASParseConfigPageController.b_getEndTime()[0]), Integer.valueOf(BIASParseConfigPageController.b_getEndTime()[1])).trim();
					String enabled = lineFromPermitFile.substring(Integer.valueOf(BIASParseConfigPageController.b_getEnabled()[0]), Integer.valueOf(BIASParseConfigPageController.b_getEnabled()[1])).trim();

					CompliancePermit permit = new CompliancePermit(subdivision, Double.valueOf(beginMp), Double.valueOf(endMp), Integer.valueOf(pasSpeed), Integer.valueOf(frtSpeed), startTime, endTime, enabled);
					permits.add(permit);
				}
			}
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		finally
		{
			scannerPermit.close();
		}

		return permits;
	}

	private ArrayList<ComplianceLink> retrieveLinks(File linkFile) throws FileNotFoundException
	{
		ArrayList<ComplianceLink> links = new ArrayList<ComplianceLink>();
		Scanner scannerLink = new Scanner(linkFile);
		try 
		{
			String targetSequence0 = "xxxxxxxxxxxxxxxxxxxx";

			Boolean firstLinkFound = false;

			while (scannerLink.hasNextLine()) 
			{
				String lineFromLinkFile = scannerLink.nextLine();
				if ((lineFromLinkFile.contains(targetSequence0)) && (!firstLinkFound)) // Found first link
				{
					scannerLink.nextLine();
					firstLinkFound = true;
				}
				else if (firstLinkFound)
				{
					String distance = lineFromLinkFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkDistance()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkDistance()[1])).trim();
					String passengerSpeed = lineFromLinkFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxPassengerSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxPassengerSpeed()[1])).trim();
					String throughSpeed = lineFromLinkFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxThroughSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxThroughSpeed()[1])).trim();
					String localSpeed = lineFromLinkFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxLocalSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxLocalSpeed()[1])).trim();

					ComplianceLink link = new ComplianceLink(Double.valueOf(distance), Integer.valueOf(passengerSpeed), Integer.valueOf(throughSpeed), Integer.valueOf(localSpeed));
					links.add(link);
				}
			}
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		finally
		{
			scannerLink.close();
		}

		return links;
	}
	
	public static Boolean checkFileExists(String file) 
	{
		File f = new File(file);
		if(f.exists() && !f.isDirectory()) 
			return true;
		else
			return false;
	}
	
	public String getResultsMessage()
	{
		return resultsMessage;
	}
}