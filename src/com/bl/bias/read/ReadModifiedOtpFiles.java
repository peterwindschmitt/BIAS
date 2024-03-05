package com.bl.bias.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bl.bias.app.BIASModifiedOtpConfigPageController;
import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.objects.ModifiedOtpTrainObject;
import com.bl.bias.tools.ConvertDateTime;

public class ReadModifiedOtpFiles 
{
	private static HashSet<String> runTimeTrainsInRouteFile = new HashSet<String>();
	private static HashSet<String> trainSymbolsFromConfigFile = new HashSet<String>();
	private static ArrayList<String> trainSymbolsToAnalyze = new ArrayList<String>();
	private static ArrayList<ModifiedOtpTrainObject> modifiedOtpEntries = new ArrayList<ModifiedOtpTrainObject>();

	private static String resultsMessage;

	public ReadModifiedOtpFiles(String file) throws IOException
	{
		runTimeTrainsInRouteFile.clear();
		trainSymbolsFromConfigFile.clear();

		resultsMessage = "\nStarted parsing Modified OTP Analysis files at "+ConvertDateTime.getTimeStamp()+"\n";

		// Read in .ROUTE file with BufferedReader then pass to Scanner
		File routeFile = new File(file.replace("OPTION","ROUTE"));
		BufferedReader bufferedReaderRouteFile = new BufferedReader(new FileReader(routeFile));
		String line = null;
		
		// Load in trains from config file
		for (int i = 0; i < BIASModifiedOtpConfigPageController.getSchedulePointEntries().split(",").length; i+=3)
		{
			trainSymbolsFromConfigFile.add(BIASModifiedOtpConfigPageController.getSchedulePointEntries().split(",")[i]);
		}

		// Get hashset of run-time train symbols
		while ((line = bufferedReaderRouteFile.readLine()) != null) 
		{
			if (line.contains("Run-time train: "))
			{
				String trainSymbolPattern = Pattern.quote("Run-time train:") + "(.*?)" + Pattern.quote("Type:");
				Pattern pattern = Pattern.compile(trainSymbolPattern);
				Matcher matcher = pattern.matcher(line);

				while (matcher.find()) 
				{
					String trainSymbol = matcher.group(1).trim();

					Iterator<String> it = trainSymbolsFromConfigFile.iterator(); 
					while (it.hasNext()) 
					{ 
						String symbolFromConfigFile = it.next();
						if (trainSymbol.startsWith(symbolFromConfigFile))
							runTimeTrainsInRouteFile.add(trainSymbol);
					}
				}
			}
		}
		bufferedReaderRouteFile.close();

		// Create ArrayList from Hashset and step through the arraylist of symbols to find scheduling nodes
		String trainSymbolPattern = Pattern.quote("Run-time train:") + "(.*?)" + Pattern.quote("Type:");
		Pattern pattern = Pattern.compile(trainSymbolPattern);

		trainSymbolsToAnalyze.addAll(runTimeTrainsInRouteFile);

		for (int i = 0; i < trainSymbolsToAnalyze.size(); i++)
		{
			// Read in .ROUTE file with Scanner
			routeFile = new File(file.replace("OPTION","ROUTE"));
			Scanner scannerRouteFile = new Scanner(new FileReader(routeFile));

			Boolean inRunTimeTrainRoute = false;

			ModifiedOtpTrainObject newEntry = new ModifiedOtpTrainObject(); 
			String trainSymbol = null;

			while (scannerRouteFile.hasNextLine()) 
			{
				String lineFromFile = scannerRouteFile.nextLine();
				Matcher matcher = pattern.matcher(lineFromFile);
				if ((matcher.find()) && (inRunTimeTrainRoute == false))
				{
					// Found a train runtime train symbol
					// See if it's a valid symbol
					trainSymbol = matcher.group(1).trim();
					if (trainSymbolsToAnalyze.get(i).equals(trainSymbol))
					{
						// Valid symbol found
						// Create a new ModifiedOtpTrainObject
						newEntry.setSymbol(trainSymbol);
						inRunTimeTrainRoute = true;
						scannerRouteFile.nextLine();
						scannerRouteFile.nextLine();
						scannerRouteFile.nextLine();
						scannerRouteFile.nextLine();
						scannerRouteFile.nextLine();	
					}
				}
				else if ((inRunTimeTrainRoute) && (lineFromFile.contains("----------")))
				{
					// End this runtime train
					inRunTimeTrainRoute = false;
					modifiedOtpEntries.add(newEntry);
					
				}
				else if (inRunTimeTrainRoute)
				{
					// Capture this entry if has a scheduled and observed time
					if ((lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getScheduledDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getScheduledDepartureTime()[1])).trim() != "")		
							|| (lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getScheduledArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getScheduledArrivalTime()[1])).trim() != ""))				

					{
						String node = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getNode()[0]), Integer.valueOf(BIASParseConfigPageController.r_getNode()[1])).trim();

						for (int j = 0; j < BIASModifiedOtpConfigPageController.getSchedulePointEntries().split(",").length; j+=3)
						{
							String eligibleNodeFromConfig = BIASModifiedOtpConfigPageController.getSchedulePointEntries().split(",")[j + 1].trim();
							String trainFromConfig = BIASModifiedOtpConfigPageController.getSchedulePointEntries().split(",")[j].trim();
							// Only collect trains that have an eligible node -- the comparison between the times is handled in the Analyze logic
							if ((trainSymbol.contains(trainFromConfig)) && (node.equals(eligibleNodeFromConfig)))
							{
								String scheduledDepartureTime = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getScheduledDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getScheduledDepartureTime()[1])).trim();
								String scheduledArrivalTime = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getScheduledArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getScheduledArrivalTime()[1])).trim();
								String simulatedDepartureTime = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getHeadEndDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getHeadEndDepartureTime()[1])).trim();
								String simulatedArrivalTime = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getHeadEndArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getHeadEndArrivalTime()[1])).trim();
								newEntry.addRouteEntry(node, scheduledDepartureTime, scheduledArrivalTime, simulatedDepartureTime, simulatedArrivalTime);
							}
						}
					}
				}
			}
		}

		resultsMessage +="Extracted data for "+runTimeTrainsInRouteFile.size()+" run-time trains from the .ROUTE file\n\n";
	}
	
	public static ArrayList<ModifiedOtpTrainObject> getTrainsForModifiedOtp()
	{
		return modifiedOtpEntries;
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}