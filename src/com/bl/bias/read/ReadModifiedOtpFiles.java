package com.bl.bias.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import com.bl.bias.app.BIASModifiedOtpConfigPageController;
import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.ModifiedOtpTrainObject;
import com.bl.bias.objects.SchedulePointForTrainObject;
import com.bl.bias.tools.ConvertDateTime;

public class ReadModifiedOtpFiles 
{
	private static HashSet<String> trainOriginDataFromConfigFile = new HashSet<String>();
	private static HashMap<String, String> eligibleTrainsFromTrainFile = new HashMap<String, String>();  // Symbol, type -- train must be enabled and not excluded from OTP stats
	private static HashMap<String, String> otpThresholdsFromOptionFile = new HashMap<String, String>();  // Type, threshold
	private static ArrayList<File> performanceFiles = new ArrayList<File>();  // Performance files
	private static ArrayList<ModifiedOtpTrainObject> performanceFileEntries = new ArrayList<ModifiedOtpTrainObject>();

	private static String resultsMessage;

	public ReadModifiedOtpFiles(String file) throws IOException
	{
		trainOriginDataFromConfigFile.clear();
		eligibleTrainsFromTrainFile.clear();
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
			String targetSequence4 = "Exclude from OTP statistics:";
			String targetSequence5 = "-------";

			String trainSymbol = "";
			String trainType = "";
			Boolean trainEnabled = false;
			Boolean trainNotExcluded = false;

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				if (lineFromFile.contains(targetSequence0))
				{ 
					openingSequence = true;
					trainSymbol = "";
					trainType = "";
					trainEnabled = false;
					trainNotExcluded = false;
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
					if (lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getOtpExcluded()[0]), Integer.valueOf(BIASParseConfigPageController.t_getOtpExcluded()[1])).trim().toLowerCase().equals("no"))
					{
						trainNotExcluded = true;
					}
				}
				else if ((openingSequence) && (lineFromFile.contains(targetSequence5)))
				{
					if ((trainEnabled) && (trainNotExcluded))
					{
						eligibleTrainsFromTrainFile.put(trainSymbol, trainType);
					}
					openingSequence = false;
				}
			}
			resultsMessage +="Extracted "+eligibleTrainsFromTrainFile.size()+" eligible trains from the .TRAIN file\n";
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
				if (lineFromFile.startsWith(targetSequence0))
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
			// Read file with BufferedReader
			for (File performanceFile: performanceFiles)
			{
				// Read as BufferedReader then load into Scanner
				BufferedReader bufferedReaderPerformanceFile = new BufferedReader(new FileReader(performanceFile));
				StringBuilder contentForScanner = new StringBuilder();
				String line = null;
				
				while ((line = bufferedReaderPerformanceFile.readLine()) != null) 
				{
					if (line.length() != 0) //&& ((!line.substring(Integer.valueOf(BIASParseConfigPageController.f_getScheduledArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.f_getScheduledArrivalTime()[1])).isBlank()) || (!line.substring(Integer.valueOf(BIASParseConfigPageController.f_getScheduledDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.f_getScheduledDepartureTime()[1])).isBlank())))
					{
						contentForScanner.append(line.substring(0, 100));
						contentForScanner.append(System.lineSeparator());
					}
				}
				bufferedReaderPerformanceFile.close();
				
				// Then load into Scanner
				scanner = new Scanner(contentForScanner.toString());
				
				String trainSymbol = null;
				Boolean targetSequence0Found = false;
				ModifiedOtpTrainObject performanceEntry = null;

				String targetSequence0 = "Train:";
				String targetSequence1 = "-----";
				
				while (scanner.hasNextLine()) 
				{
					String lineFromFile = scanner.nextLine();
					if (lineFromFile.startsWith(targetSequence0)) 
					{ 
						trainSymbol = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.f_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.f_getTrainSymbol()[1])).trim();
						if (eligibleTrainsFromTrainFile.containsKey(trainSymbol.split("-")[0]))
						{
							targetSequence0Found = true;

							performanceEntry = new ModifiedOtpTrainObject(performanceFile.getName(), trainSymbol);

							for (int i = 0; i < 4; i++)
								scanner.nextLine();
						}
					}
					else if ((targetSequence0Found) && (lineFromFile.startsWith(targetSequence1)))
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

							SchedulePointForTrainObject point = new SchedulePointForTrainObject(scheduleNode, scheduledArrivalTimeAsDouble, scheduledDepartureTimeAsDouble, actualArrivalTimeAsDouble, actualDepartureTimeAsDouble);
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
		return eligibleTrainsFromTrainFile;
	}

	public static HashMap<String, String> getOtpThresholdsFromOptionFile()
	{
		return otpThresholdsFromOptionFile;
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}

	public static ArrayList<ModifiedOtpTrainObject> getPerformanceFileEntries() 
	{
		return performanceFileEntries;
	}
}