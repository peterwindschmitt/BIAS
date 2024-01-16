package com.bl.bias.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.TrainAssessment;
import com.bl.bias.objects.RouteEntry;
import com.bl.bias.tools.ConvertDateTime;

public class ReadRecoveryRateAnalysisFiles
{
	private static ArrayList<TrainAssessment> trainsReadIn = new ArrayList<TrainAssessment>();
	private static ArrayList<String> nodePairsToAnalyze = new ArrayList<String>();

	private static String resultsMessage;

	public ReadRecoveryRateAnalysisFiles(String file) throws IOException
	{
		trainsReadIn.clear();
		nodePairsToAnalyze.clear();
		resultsMessage = "\nStarted parsing Recovery Rate Analysis files at "+ConvertDateTime.getTimeStamp()+"\n";

		// Read in .ROUTE file with BufferedReader then pass to Scanner
		File routeFile = new File(file.replace("OPTION","ROUTE"));
		BufferedReader bufferedReaderRouteFile = new BufferedReader(new FileReader(routeFile));
		String line = null;
		StringBuilder contentForScanner = new StringBuilder();

		// Get just seed trains
		while ((line = bufferedReaderRouteFile.readLine()) != null) 
		{
			if (line.contains("Run-time"))
			{
				bufferedReaderRouteFile.close();
				break;
			}
			else
			{
				contentForScanner.append(line.subSequence(0, Math.min(line.length(), 430)));  // No pertinent information to the right of column 430
				contentForScanner.append(System.lineSeparator());
			}
		}
		
		// Parse entries in .ROUTE file	
		Scanner scanner = null;
		try 
		{
			String trainSymbol = null;
			String trainGroup = null;
			ArrayList<RouteEntry> routeEntries = null;
			scanner = new Scanner(contentForScanner.toString());

			boolean openingSequence0 = false;
			boolean openingSequence1 = false;

			String targetSequence0 = "Seed train:";
			String targetSequence1 = "------------";

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				if (!lineFromFile.isEmpty())
				{
					if((lineFromFile.contains(targetSequence0)) && (!openingSequence0))
					{ 
						String trainSymbolPattern = Pattern.quote("Seed train:") + "(.*?)" + Pattern.quote("Type:");
						Pattern pattern = Pattern.compile(trainSymbolPattern);
						Matcher matcher = pattern.matcher(lineFromFile);

						while (matcher.find()) 
						{
							trainSymbol = matcher.group(1).trim();
						}

						String trainGroupPattern = Pattern.quote("Group:") + "(.*?)" + Pattern.quote("Entry delay");
						pattern = Pattern.compile(trainGroupPattern);
						matcher = pattern.matcher(lineFromFile);

						while (matcher.find()) 
						{
							trainGroup = matcher.group(1).trim(); 
						}

						routeEntries = new ArrayList<RouteEntry>();

						openingSequence0 = true;
						scanner.nextLine();
					}
					else if ((lineFromFile.contains(targetSequence1)) && (openingSequence0) && (!openingSequence1))
					{
						openingSequence1 = true;
					}
					else if ((openingSequence0) && (openingSequence1) && (lineFromFile.contains(targetSequence1))) // Record for this train is complete
					{
						openingSequence0 = false;
						openingSequence1 = false;

						TrainAssessment trainToAsess = new TrainAssessment(trainSymbol, trainGroup, routeEntries);
						trainsReadIn.add(trainToAsess);
					}
					else if ((openingSequence0) && (openingSequence1))
					{
						// Only capture lines that contain scheduled arrival/departure or dwell times
						String scheduledDepartureTime = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getScheduledDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getScheduledDepartureTime()[1])).trim();
						String scheduledArrivalTime = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getScheduledArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getScheduledArrivalTime()[1])).trim();
						String minimumDwellTime = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getMinimumDwell()[0]), Integer.valueOf(BIASParseConfigPageController.r_getMinimumDwell()[1])).trim();

						if ((!scheduledDepartureTime.equals("")) || (!scheduledArrivalTime.equals("")) || (!minimumDwellTime.equals("0")))
						{
							// Get remaining relevant info in this row
							Integer rtcIncrement = Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getRtcIncrement()[0]), Integer.valueOf(BIASParseConfigPageController.r_getRtcIncrement()[1])).trim());
							String node = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getNode()[0]), Integer.valueOf(BIASParseConfigPageController.r_getNode()[1])).trim();
							String simulatedArrivalTime = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getHeadEndArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getHeadEndArrivalTime()[1])).trim();
							String simulatedDepartureTime = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getHeadEndDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getHeadEndDepartureTime()[1])).trim();
							String cumulativeElapsedTime = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getCumulativeElapsedTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getCumulativeElapsedTime()[1])).trim();
							String waitOnSchedule = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getWaitOnSchedule()[0]), Integer.valueOf(BIASParseConfigPageController.r_getWaitOnSchedule()[1])).trim();
							Double cumulativeDistance = Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getDistance()[0]), Integer.valueOf(BIASParseConfigPageController.r_getDistance()[1])).trim());
							RouteEntry routeEntry = new RouteEntry(rtcIncrement, node, scheduledDepartureTime, scheduledArrivalTime, simulatedDepartureTime, 
									simulatedArrivalTime, cumulativeElapsedTime, minimumDwellTime, waitOnSchedule, cumulativeDistance);
							routeEntries.add(routeEntry);
						}
					}
				}
			}

			resultsMessage +="Extracted data for "+trainsReadIn.size()+" seed trains from the .ROUTE file\n";
			scanner.close();
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		finally
		{
			scanner.close();
		}

		resultsMessage += "Finished parsing Recovery Rate Analysis files at "+ConvertDateTime.getTimeStamp()+"\n\n";
	}			

	public static ArrayList<TrainAssessment> getTrainsReadIn()
	{
		return trainsReadIn;
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}