package com.bl.bias.read;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import com.bl.bias.app.BIASModifiedOtpConfigPageController;
import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.ModifiedOtpTrainObjectB;
import com.bl.bias.tools.ConvertDateTime;

public class ReadModifiedOtpFiles 
{
	private static HashSet<String> trainOriginDataFromConfigFile = new HashSet<String>();
	private static HashMap<String, String> enabledTrainsFromTrainFile = new HashMap<String, String>();
	private static HashMap<String, String> otpThresholdsFromOptionFile = new HashMap<String, String>();
	private static ArrayList<ModifiedOtpTrainObjectB> performanceFileEntries = new ArrayList<ModifiedOtpTrainObjectB>();

	private static String resultsMessage;

	public ReadModifiedOtpFiles(String file) throws IOException
	{
		trainOriginDataFromConfigFile.clear();
		enabledTrainsFromTrainFile.clear();
		otpThresholdsFromOptionFile.clear();
		performanceFileEntries.clear();

		resultsMessage = "\nStarted parsing Modified OTP Analysis files at "+ConvertDateTime.getTimeStamp()+"\n";

		// Load in train data from config file
		for (int i = 0; i < BIASModifiedOtpConfigPageController.getSchedulePointEntries().split(",").length; i+=3)
		{
			trainOriginDataFromConfigFile.add(BIASModifiedOtpConfigPageController.getSchedulePointEntries().split(",")[i]);
		}
		resultsMessage +="Extracted origin data for "+trainOriginDataFromConfigFile.size()+" trains from this module's config file\n";

		// Read in enabled train symbols from .TRAIN file
		Scanner scanner = null;

		try 
		{
			File trainFile = new File(file.replace("OPTION","TRAIN"));
			scanner = new Scanner(trainFile);

			Boolean openingSequence = false;

			String targetSequence0 = "=======";
			String targetSequence1 = "Train symbol:";
			String targetSequence2 = "Train type:";
			String targetSequence3 = "Enabled:";
			String targetSequence4 = "-------";

			String trainSymbol = "";
			String trainType = "";
			Boolean trainEnabled = false;

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				if (lineFromFile.contains(targetSequence0))
				{ 
					openingSequence = true;
					trainSymbol = "";
					trainType = "";
					trainEnabled = false;
				}
				else if ((openingSequence) && (lineFromFile.contains(targetSequence1)))
				{
					trainSymbol = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[1])).trim();
				}
				else if ((openingSequence) && (lineFromFile.contains(targetSequence2)))
				{
					trainType = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainType()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainType()[1])).trim();
				}
				else if ((openingSequence) && (lineFromFile.contains(targetSequence3)))
				{
					if (lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainEnabled()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainEnabled()[1])).trim().toLowerCase().equals("yes"))
					{
						trainEnabled = true;
					}
				}
				else if ((openingSequence) && (lineFromFile.contains(targetSequence4)))
				{
					if (trainEnabled)
						enabledTrainsFromTrainFile.put(trainSymbol, trainType);
					openingSequence = false;
				}
			}
			resultsMessage +="Extracted "+enabledTrainsFromTrainFile.size()+" enabled trains from the .TRAIN file\n";
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		finally
		{
			scanner.close();
		}

		// Read in OTP thresholds from .OPTION file
		try 
		{
			File optionFile = new File(file);
			scanner = new Scanner(optionFile);

			Boolean targetSequence0Found = false;

			String targetSequence0 = "[Train types]";
			String targetSequence1 = "-------";

			String trainType = "";
			String otpThreshold = "";

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				if (lineFromFile.contains(targetSequence0))
				{ 
					trainType = "";
					otpThreshold = "";
					targetSequence0Found = true;
					for (int i = 0; i < 5; i++)
						scanner.nextLine();
				}
				else if ((targetSequence0Found) && (lineFromFile.contains(targetSequence1)))
				{
					targetSequence0Found = false;
					break;
				}
				else if (targetSequence0Found)
				{
					trainType = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeName()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeName()[1])).trim();
					if (!trainType.isBlank())
					{
						otpThreshold = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getOtpThreshold()[0]), Integer.valueOf(BIASParseConfigPageController.o_getOtpThreshold()[1])).trim();
						otpThresholdsFromOptionFile.put(trainType, otpThreshold);
					}
				}							
			}
			resultsMessage +="Extracted "+otpThresholdsFromOptionFile.size()+" OTP thresholds from the .OPTION file\n";
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		finally
		{
			scanner.close();
		}

		// Read in .PERFORMANCE file to get train symbols, nodes, scheduled and actual times
		try 
		{
			File performanceFile = new File(file.replace("OPTION","PERFORMANCE"));
			scanner = new Scanner(performanceFile);
			
			Boolean targetSequence0Found = false;

			String targetSequence0 = "Train:";
			String targetSequence1 = "-------";

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				
				if (lineFromFile.contains(targetSequence0)) 
				{ 
					targetSequence0Found = true;
					for (int i = 0; i < 5; i++)
						scanner.nextLine();
				}
				else if ((targetSequence0Found) && (lineFromFile.contains(targetSequence1)))
				{
					targetSequence0Found = false;
				}
				else if (targetSequence0Found)
				{
					String scheduledArrivalTimeAsString = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.f_getScheduledArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.f_getScheduledArrivalTime()[1])).trim();
					String scheduledDepartureTimeAsString = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.f_getScheduledDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.f_getScheduledDepartureTime()[1])).trim();
					
					if ((!scheduledArrivalTimeAsString.isBlank()) || (!scheduledDepartureTimeAsString.isBlank()))
					{
						String trainSymbol = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.f_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.f_getTrainSymbol()[1])).trim();
						String scheduleNode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.f_getScheduleNode()[0]), Integer.valueOf(BIASParseConfigPageController.f_getScheduleNode()[1])).trim();
						String actualArrivalTimeAsString = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.f_getActualArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.f_getActualArrivalTime()[1])).trim();
						String actualDepartureTimeAsString = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.f_getActualDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.f_getActualDepartureTime()[1])).trim();

						Double scheduledArrivalTimeAsDouble = ConvertDateTime.convertDDHHMMSSStringToSerial(scheduledArrivalTimeAsString);
						Double scheduledDepartureTimeAsDouble = ConvertDateTime.convertDDHHMMSSStringToSerial(scheduledDepartureTimeAsString);
						Double actualArrivalTimeAsDouble = ConvertDateTime.convertDDHHMMSSStringToSerial(actualArrivalTimeAsString);
						Double actualDepartureTimeAsDouble = ConvertDateTime.convertDDHHMMSSStringToSerial(actualDepartureTimeAsString);
						
						ModifiedOtpTrainObjectB performanceEntry = new ModifiedOtpTrainObjectB(trainSymbol, scheduleNode, scheduledArrivalTimeAsDouble, scheduledDepartureTimeAsDouble, actualArrivalTimeAsDouble, actualDepartureTimeAsDouble);
						performanceFileEntries.add(performanceEntry);
					}
				}
			}
			resultsMessage +="Extracted "+performanceFileEntries.size()+" route entries from the .PERFORMANCE file\n";
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		finally
		{
			scanner.close();
		}
		
		resultsMessage += "Finished parsing Modified OTP Analysis files at "+ConvertDateTime.getTimeStamp()+"\n\n";
	}

	public static HashSet<String> getTrainOriginDataFromConfigFile()
	{
		return trainOriginDataFromConfigFile;
	}

	public static HashMap<String, String> getEnabledTrainsFromTrainFile()
	{
		return enabledTrainsFromTrainFile;
	}

	public static HashMap<String, String> getOtpThresholdsFromOptionFile()
	{
		return otpThresholdsFromOptionFile;
	}
	
	public static ArrayList<ModifiedOtpTrainObjectB> getPerformanceFileEntries()
	{
		return performanceFileEntries;
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}