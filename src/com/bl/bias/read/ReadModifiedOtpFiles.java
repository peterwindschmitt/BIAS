package com.bl.bias.read;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import com.bl.bias.app.BIASModifiedOtpConfigPageController;
import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.ModifiedOtpTrainObjectB;
import com.bl.bias.objects.SchedulePointForTrainObjectB;
import com.bl.bias.tools.ConvertDateTime;

public class ReadModifiedOtpFiles 
{
	private static HashSet<String> trainOriginDataFromConfigFile = new HashSet<String>();
	private static HashMap<String, String> enabledTrainsFromTrainFile = new HashMap<String, String>();  // Symbol, type
	private static HashMap<String, String> otpThresholdsFromOptionFile = new HashMap<String, String>();  // Type, threshold
	private static ArrayList<File> performanceFiles = new ArrayList<File>();  // Performance files
	private static ArrayList<ModifiedOtpTrainObjectB> performanceFileEntries = new ArrayList<ModifiedOtpTrainObjectB>();

	private static String resultsMessage;

	public ReadModifiedOtpFiles(String file) throws IOException
	{
		trainOriginDataFromConfigFile.clear();
		enabledTrainsFromTrainFile.clear();
		otpThresholdsFromOptionFile.clear();
		performanceFiles.clear();
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

		// Create ArrayList of .PERFORMANCE file(s) 
		File[] filesAsList;

		try
		{
			// Determine number of files to review
			File fileAsFile = new File(file);		
			File directoryPathForFile = fileAsFile.getParentFile();

			// Determine number of files to review
			filesAsList = directoryPathForFile.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File directory, String name) {
					return name.toLowerCase().endsWith(".performance");
				}
			});

			performanceFiles = new ArrayList<File>();
			for (int i = 0; i < filesAsList.length; i++) 
			{
				performanceFiles.add(filesAsList[i]);
			}
		}
		catch (Exception e)
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		finally
		{
			scanner.close();
		}

		// Get train symbols, nodes, scheduled and actual times from each .PERFORMANCE file
		try 
		{
			// For each .PERFORMANCE file
			for (File performanceFile: performanceFiles)
			{
				scanner = new Scanner(performanceFile);

				String trainSymbol = null;
				Boolean targetSequence0Found = false;
				ModifiedOtpTrainObjectB performanceEntry = null;

				String targetSequence0 = "Train:";
				String targetSequence1 = "-------";
				String targetSequence2 = "*****";  // Used to indicate that parsing should stop at this line in the file

				while (scanner.hasNextLine()) 
				{
					String lineFromFile = scanner.nextLine();

					if (lineFromFile.contains(targetSequence2)) 
					{ 
						break;
					}
					else if (lineFromFile.contains(targetSequence0)) 
					{ 
						targetSequence0Found = true;

						trainSymbol = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.f_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.f_getTrainSymbol()[1])).trim();
						performanceEntry = new ModifiedOtpTrainObjectB(performanceFile.getName(), trainSymbol);

						for (int i = 0; i < 5; i++)
							scanner.nextLine();
					}
					else if ((targetSequence0Found) && (lineFromFile.contains(targetSequence1)))
					{
						targetSequence0Found = false;
						performanceFileEntries.add(performanceEntry);
					}
					else if (targetSequence0Found)
					{
						String scheduledArrivalTimeAsString = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.f_getScheduledArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.f_getScheduledArrivalTime()[1])).trim();
						String scheduledDepartureTimeAsString = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.f_getScheduledDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.f_getScheduledDepartureTime()[1])).trim();

						if (scheduledDepartureTimeAsString.isBlank())
							scheduledDepartureTimeAsString = scheduledArrivalTimeAsString;

						if (scheduledArrivalTimeAsString.isBlank())
							scheduledArrivalTimeAsString = scheduledDepartureTimeAsString;

						if ((!scheduledArrivalTimeAsString.isBlank()) || (!scheduledDepartureTimeAsString.isBlank()))
						{
							String scheduleNode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.f_getScheduleNode()[0]), Integer.valueOf(BIASParseConfigPageController.f_getScheduleNode()[1])).trim();

							String actualArrivalTimeAsString = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.f_getActualArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.f_getActualArrivalTime()[1])).trim();
							String actualDepartureTimeAsString = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.f_getActualDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.f_getActualDepartureTime()[1])).trim();

							Double scheduledArrivalTimeAsDouble = ConvertDateTime.convertDDHHMMSSStringToSerial(scheduledArrivalTimeAsString);
							Double scheduledDepartureTimeAsDouble = ConvertDateTime.convertDDHHMMSSStringToSerial(scheduledDepartureTimeAsString);
							Double actualArrivalTimeAsDouble = ConvertDateTime.convertDDHHMMSSStringToSerial(actualArrivalTimeAsString);
							Double actualDepartureTimeAsDouble = ConvertDateTime.convertDDHHMMSSStringToSerial(actualDepartureTimeAsString);

							SchedulePointForTrainObjectB point = new SchedulePointForTrainObjectB(scheduleNode, scheduledArrivalTimeAsDouble, scheduledDepartureTimeAsDouble, actualArrivalTimeAsDouble, actualDepartureTimeAsDouble);
							performanceEntry.addSchedulePoint(point);
						}
					}
				}
			}
			resultsMessage +="Extracted "+performanceFileEntries.size()+" trains from the .PERFORMANCE file(s)\n";
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

	public String getResultsMessage()
	{
		return resultsMessage;
	}

	public static ArrayList<ModifiedOtpTrainObjectB> getPerformanceFileEntries() 
	{
		return performanceFileEntries;
	}
}