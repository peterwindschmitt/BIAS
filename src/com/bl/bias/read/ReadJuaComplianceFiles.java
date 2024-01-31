package com.bl.bias.read;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.ComplianceTrain;
import com.bl.bias.tools.ConvertDateTime;

public class ReadJuaComplianceFiles
{
	private static ArrayList<ComplianceTrain> complianceTrains = new ArrayList<ComplianceTrain>();

	private static String resultsMessage;

	static Boolean formattedCorrectly = true;

	static String simulationStartDayOfWeek = null;
	static String simulationDuration = null;
	static String warmUpExclusion = null;
	static String coolDownExclusion = null;
	static String simulationBeginTime = null;

	public ReadJuaComplianceFiles(String file) throws IOException
	{
		resultsMessage = "\nStarted parsing JUA Compliance files at "+ConvertDateTime.getTimeStamp()+"\n";

		complianceTrains.clear();
		
		// Read in .OPTION file with Scanner
		Scanner scannerOption = null;
		try 
		{
			File optionFile = new File(file);
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
			}
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		finally
		{
			scannerOption.close();
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
			// Read in .TRAIN file with Scanner
			Scanner scannerTrain = null; 
			File trainFile = new File(file.replace("OPTION","TRAIN"));
			scannerTrain = new Scanner(trainFile);

			try 
			{
				String trainSymbol = null;
				String trainType = null;
				String linkedAtOriginTo = null;
				String enabled = null;
				ArrayList<String> routeEntries = null;
				ArrayList<Integer> daysOfOperationAsInteger = null;
				
				String targetSequence0 = "Train symbol: ";
				String targetSequence1 = "Train type: ";
				String targetSequence2 = "Week  1 Frequency: ";
				String targetSequence3 = "Week  2 Frequency: ";
				String targetSequence4 = "Week  3 Frequency: ";
				String targetSequence5 = "Enabled: ";
				String targetSequence6 = "Linked at origin to the: ";
				String targetSequence7 = "Route Node";
				String targetSequence8 = "=========================================";

				Boolean inRouteNodeSection = false;
				Boolean firstTrainFound = false;

				while (scannerTrain.hasNextLine()) 
				{
					String lineFromTrainFile = scannerTrain.nextLine();
					if ((lineFromTrainFile.contains(targetSequence8)) && (!firstTrainFound)) // Found first train
					{
						firstTrainFound = true;
					}
					else if (lineFromTrainFile.contains(targetSequence0)) // Train symbol
					{ 
						daysOfOperationAsInteger = new ArrayList<Integer>();
						routeEntries = new ArrayList<String>();
						trainSymbol = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[1])).trim();
					}
					else if(lineFromTrainFile.contains(targetSequence1)) // Train type
					{ 
						trainType = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainType()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainType()[1])).trim();
					}
					else if(lineFromTrainFile.contains(targetSequence2)) // Days 1 - 7
					{ 
						ArrayList<String> week1Days = new ArrayList<String>();
						String week1DaysAsString = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[0]), Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[1])).trim();
						String[] splitted = week1DaysAsString.split(" ");
						for (int i = 0; i < splitted.length; i++)
							week1Days.add(splitted[i].trim().toUpperCase());
						daysOfOperationAsInteger.addAll(convertDOWtoInteger(week1Days, 1));
					}
					else if(lineFromTrainFile.contains(targetSequence3)) // Days 8 - 14
					{ 
						ArrayList<String> week2Days = new ArrayList<String>();
						String week2DaysAsString = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[0]), Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[1])).trim();
						String[] splitted = week2DaysAsString.split(" ");
						for (int i = 0; i < splitted.length; i++)
							week2Days.add(splitted[i].trim().toUpperCase());
						daysOfOperationAsInteger.addAll(convertDOWtoInteger(week2Days, 2));
					}
					else if(lineFromTrainFile.contains(targetSequence4)) // Days 15 - 21
					{ 
						ArrayList<String> week3Days = new ArrayList<String>();
						String week3DaysAsString = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[0]), Integer.valueOf(BIASParseConfigPageController.t_getWeekDays()[1])).trim();
						String[] splitted = week3DaysAsString.split(" ");
						for (int i = 0; i < splitted.length; i++)
							week3Days.add(splitted[i].trim().toUpperCase());
						daysOfOperationAsInteger.addAll(convertDOWtoInteger(week3Days, 3));

					}
					else if (lineFromTrainFile.contains(targetSequence5)) // Train enabled
					{ 
						enabled = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainEnabled()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainEnabled()[1])).trim();
					}
					else if (lineFromTrainFile.contains(targetSequence6)) // Linked at origin to
					{ 
						linkedAtOriginTo = lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getLinkedAtOrigin()[0]), Integer.valueOf(BIASParseConfigPageController.t_getLinkedAtOrigin()[1])).trim();
					}
					else if (((inRouteNodeSection) && (lineFromTrainFile.contains(targetSequence8))) 
						|| (!scannerTrain.hasNextLine())) // Create the train
					{
						inRouteNodeSection = false;
						
						ComplianceTrain train = new ComplianceTrain(trainSymbol, trainType, linkedAtOriginTo, enabled, routeEntries, daysOfOperationAsInteger);
						complianceTrains.add(train);	
					}
					else if (inRouteNodeSection) // Route node
					{
						if (!lineFromTrainFile.equals(""))
						{
							routeEntries.add(lineFromTrainFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getRouteNode()[0]), Integer.valueOf(BIASParseConfigPageController.t_getRouteNode()[1])).trim());
						}
					}
					else if ((!inRouteNodeSection) && (lineFromTrainFile.contains(targetSequence7))) // Enter the route node section
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
			}
			
			resultsMessage += "Extracted data for "+complianceTrains.size()+" seed trains from the .TRAIN file\n";
			resultsMessage += "Finished parsing JUA Compliance files at "+ConvertDateTime.getTimeStamp()+"\n\n";
		}
		else
		{
			resultsMessage += "Simulation duration and/or exclusion periods are not set correctly\n\n";
			scannerOption.close();
		}
	}

	public static ArrayList<ComplianceTrain> getTrainsToAnalyzeForCompliance()
	{
		return complianceTrains;
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

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}