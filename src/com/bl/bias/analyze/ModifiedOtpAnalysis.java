package com.bl.bias.analyze;

import java.util.ArrayList;
import java.util.HashMap;

import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.app.BIASModifiedOtpConfigPageController;
import com.bl.bias.objects.ModifiedOtpTrainObject;
import com.bl.bias.objects.ReportingPointForTrainObject;
import com.bl.bias.read.ReadModifiedOtpFiles;

public class ModifiedOtpAnalysis 
{
	private static String resultsMessage;

	private static ArrayList<String> trainSymbolsFromConfigFile = new ArrayList<String>();
	private static ArrayList<odTuple> odPairsFromConfig = new ArrayList<odTuple>();
	private static String exceptTrainThresholdFromConfigFileOptionAAsString; 
	private static Double exceptTrainThresholdFromConfigFileOptionAAsSerial; 
	private static String exceptTrainThresholdFromConfigFileOptionBAsString; 
	private static Double exceptTrainThresholdFromConfigFileOptionBAsSerial; 

	private static HashMap<String, int[]> typeHashmapOfMakes = new HashMap<String, int[]>(); // 0 is num / 1 is denom
	private Integer exceptedTrainCount;
	private Integer makeTrainCount;
	private Integer missTrainCount;

	final Boolean debug = false;

	public ModifiedOtpAnalysis() 
	{
		resultsMessage = "Started analyzing trains at "+ConvertDateTime.getTimeStamp()+"\n";

		trainSymbolsFromConfigFile.clear();
		odPairsFromConfig.clear();
		typeHashmapOfMakes.clear();

		exceptedTrainCount = 0;
		makeTrainCount = 0;
		missTrainCount = 0;

		// Load in actual points (O/D pair) from config file
		for (int i = 0; i < BIASModifiedOtpConfigPageController.getActualPointEntries().split(",").length; i+=2)
		{
			odTuple<String, String> odTuple = new odTuple<>(BIASModifiedOtpConfigPageController.getActualPointEntries().split(",")[i], BIASModifiedOtpConfigPageController.getActualPointEntries().split(",")[i+1]);
			odPairsFromConfig.add(odTuple);
		}

		HashMap<String, String> enabledTrainsFromTrainFile = new HashMap<String, String>();
		HashMap<String, String> otpThresholdsFromOptionFile = new HashMap<String, String>();
		ArrayList<ModifiedOtpTrainObject> performanceFileEntries = new ArrayList<ModifiedOtpTrainObject>();			

		enabledTrainsFromTrainFile = ReadModifiedOtpFiles.getEnabledTrainsFromTrainFile(); // Use to get type
		otpThresholdsFromOptionFile = ReadModifiedOtpFiles.getOtpThresholdsFromOptionFile(); // Gets thresholds
		performanceFileEntries.addAll(ReadModifiedOtpFiles.getPerformanceFileEntries());

		exceptTrainThresholdFromConfigFileOptionAAsString = BIASModifiedOtpConfigPageController.getPermissibleMinutesOfDelayOptionAAsString();
		exceptTrainThresholdFromConfigFileOptionAAsSerial = ConvertDateTime.convertDDHHMMStringToSerial(exceptTrainThresholdFromConfigFileOptionAAsString);
		exceptTrainThresholdFromConfigFileOptionBAsString = BIASModifiedOtpConfigPageController.getPermissibleMinutesOfDelayOptionBAsString();
		exceptTrainThresholdFromConfigFileOptionBAsSerial = ConvertDateTime.convertDDHHMMStringToSerial(exceptTrainThresholdFromConfigFileOptionBAsString);

		for (int i = 0; i < performanceFileEntries.size(); i++) // For each symbol
		{
			String runtimeSymbol = performanceFileEntries.get(i).getTrainSymbol();
			String scheduledSymbol = performanceFileEntries.get(i).getTrainSymbol().substring(0, performanceFileEntries.get(i).getTrainSymbol().lastIndexOf("-"));
			String trainType = enabledTrainsFromTrainFile.get(scheduledSymbol);
			String otpThresholdAsString = otpThresholdsFromOptionFile.get(trainType);
			Double otpThresholdAsDouble = ConvertDateTime.convertDDHHMMSSStringToSerial(otpThresholdAsString);
			
			Boolean exceptTrain = false;
			
			int[] numDenom = new int[2];
			typeHashmapOfMakes.putIfAbsent(trainType, numDenom);

			performanceFileEntries.get(i).setTrainType(trainType);
			performanceFileEntries.get(i).setOtpThresholdAsDouble(otpThresholdAsDouble);

			for (int j = 0; j < odPairsFromConfig.size(); j++)  // For each OD pair from config
			{
				String originNodeInConfigFile = odPairsFromConfig.get(j).origin;
				String destinationNodeInConfigFile = odPairsFromConfig.get(j).destination;

				for (int k = 0; k < performanceFileEntries.get(i).getSchedulePoints().size(); k++)  // For each performanceFileEntry
				{
					String originNodeFromPerformanceFile = performanceFileEntries.get(i).getSchedulePoints().get(k).getScheduledNode();

					Double scheduledOriginArrivalTimeAsDouble = performanceFileEntries.get(i).getSchedulePoints().get(k).getScheduledArrivalTime();
					Double actualOriginArrivalTimeAsDouble = performanceFileEntries.get(i).getSchedulePoints().get(k).getActualArrivalTime();
					Double scheduledOriginDepartureTimeAsDouble = performanceFileEntries.get(i).getSchedulePoints().get(k).getScheduledDepartureTime();
					Double actualOriginDepartureTimeAsDouble = performanceFileEntries.get(i).getSchedulePoints().get(k).getActualDepartureTime();

					if (originNodeFromPerformanceFile.contains(originNodeInConfigFile))
					{
						for (int l = k; l < performanceFileEntries.get(i).getSchedulePoints().size(); l++)  // For each performanceFileEntry
						{
							String destinationNodeInPerformanceFile = performanceFileEntries.get(i).getSchedulePoints().get(l).getScheduledNode();

							if (destinationNodeInPerformanceFile.contains(destinationNodeInConfigFile))
							{
								Double scheduledDestinationArrivalTimeAsDouble = performanceFileEntries.get(i).getSchedulePoints().get(l).getScheduledArrivalTime();
								Double actualDestinationArrivalTimeAsDouble = performanceFileEntries.get(i).getSchedulePoints().get(l).getActualArrivalTime();
								Double scheduledDestinationDepartureTimeAsDouble = performanceFileEntries.get(i).getSchedulePoints().get(l).getScheduledDepartureTime();
								Double actualDestinationDepartureTimeAsDouble = performanceFileEntries.get(i).getSchedulePoints().get(l).getActualDepartureTime();

								String make = "?";

								Double scheduleOriginTimeToUse = Math.max(scheduledOriginArrivalTimeAsDouble, scheduledOriginDepartureTimeAsDouble);
								Double scheduleDestinationTimeToUse = Math.min(scheduledDestinationArrivalTimeAsDouble, scheduledDestinationDepartureTimeAsDouble);
								Double scheduleTransitTime = scheduleDestinationTimeToUse - scheduleOriginTimeToUse;

								Double actualOriginTimeToUse = Math.max(actualOriginArrivalTimeAsDouble, actualOriginDepartureTimeAsDouble);
								Double actualDestinationTimeToUse = Math.min(actualDestinationArrivalTimeAsDouble, actualDestinationDepartureTimeAsDouble);
								Double actualTransitTime = actualDestinationTimeToUse - actualOriginTimeToUse;

								Double lateAtOrigin = Math.max(0, actualOriginTimeToUse - scheduleOriginTimeToUse);

								Integer num = 0;
								Integer denom = 0;

								// If late at origin based on train's schedule
								if ((lateAtOrigin > exceptTrainThresholdFromConfigFileOptionBAsSerial) && 
										((BIASModifiedOtpConfigPageController.getB_exceptTrainsBasedOnRunTimeStatus() ||
												BIASModifiedOtpConfigPageController.getC_exceptTrainsBasedOnExternalAndRunTimeStatus())))
								{
									System.out.println("excepting at 125");
									make = "EXCEPT";	
									num = 0;
									denom = 0;
									exceptedTrainCount++;
									exceptTrain = true;
								}
								
								// If late at origin based on external time provided in config file
								if (((BIASModifiedOtpConfigPageController.getA_exceptTrainsBasedOnExternalSchedule() ||
										BIASModifiedOtpConfigPageController.getC_exceptTrainsBasedOnExternalAndRunTimeStatus())))
								{
									String externalSchedulesFromConfig = BIASModifiedOtpConfigPageController.getSchedulePointEntries();
									for (int m = 0; m < externalSchedulesFromConfig.split(",").length; m = m + 3)
									{
										if ((runtimeSymbol.contains(externalSchedulesFromConfig.split(",")[m])) &&  // Symbol
											(originNodeFromPerformanceFile.contains(externalSchedulesFromConfig.split(",")[m + 1])))  // Node
										{
											if ((actualOriginTimeToUse - actualOriginTimeToUse.intValue()) > (ConvertDateTime.convertHHMMStringToSerial(externalSchedulesFromConfig.split(",")[m + 2])) + exceptTrainThresholdFromConfigFileOptionAAsSerial)
											{
												System.out.println("excepting at 145");
												make = "EXCEPT";	
												num = 0;
												denom = 0;
												exceptedTrainCount++;
												exceptTrain = true;
												break;
											}
										}
									}
								}

								// If not an excepted train, then process one of the methodologies
								if (!exceptTrain)
								{
									//  Methodology 1 
									if (BIASModifiedOtpConfigPageController.getUseMethodology1())
									{
										// Use OTP Thresholds Methodology 1
										if (BIASModifiedOtpConfigPageController.getUseOtpThresholds())
										{
											if (actualDestinationTimeToUse <= (scheduleDestinationTimeToUse + otpThresholdAsDouble))
											{
												make = "Y";	
												num = 1;
												denom = 1;
												makeTrainCount++;
											}
											else if (actualDestinationTimeToUse <= (actualOriginTimeToUse + scheduleTransitTime + otpThresholdAsDouble))
											{
												make = "Y";
												num = 1;
												denom = 1;
												makeTrainCount++;
											}
											else
											{
												make = "N";
												num = 0;
												denom = 1;
												missTrainCount++;
											}
										}
										else
										{
											// Do not use OTP Thresholds Methodology 1
											if (actualDestinationTimeToUse <= scheduleDestinationTimeToUse)
											{
												make = "Y";	
												num = 1;
												denom = 1;
												makeTrainCount++;
											}
											else if (actualDestinationTimeToUse <= (actualOriginTimeToUse + scheduleTransitTime))
											{
												make = "Y";
												num = 1;
												denom = 1;
												makeTrainCount++;
											}
											else
											{
												make = "N";
												num = 0;
												denom = 1;
												missTrainCount++;
											}
										}
									}
									// Use Metholodology 2
									else if (BIASModifiedOtpConfigPageController.getUseMethodology2())
									{
										// Use OTP Thresholds Methodology 2
										if (BIASModifiedOtpConfigPageController.getUseOtpThresholds())
										{
											if (actualDestinationTimeToUse <= (scheduleDestinationTimeToUse + otpThresholdAsDouble))
											{
												make = "Y";	
												num = 1;
												denom = 1;
												makeTrainCount++;
											}
											else
											{
												make = "N";
												num = 0;
												denom = 1;
												missTrainCount++;
											}
										}
										else
										{
											// Do not use OTP Thresholds Methodology 2
											if (actualDestinationTimeToUse <= scheduleDestinationTimeToUse)
											{
												make = "Y";	
												num = 1;
												denom = 1;
												makeTrainCount++;
											}
											else
											{
												make = "N";
												num = 0;
												denom = 1;
												missTrainCount++;
											}
										}
									}
									// Use Methodology 3
									else if (BIASModifiedOtpConfigPageController.getUseMethodology3())
									{
										if (BIASModifiedOtpConfigPageController.getUseOtpThresholds())
										{
											if (actualDestinationTimeToUse <= (actualOriginTimeToUse + scheduleTransitTime + otpThresholdAsDouble))
											{
												make = "Y";
												num = 1;
												denom = 1;
												makeTrainCount++;
											}
											else
											{
												make = "N";
												num = 0;
												denom = 1;
												missTrainCount++;
											}
										}
										else
										{
											// Do not use OTP Thresholds Methodology 3
											if (actualDestinationTimeToUse <= (actualOriginTimeToUse + scheduleTransitTime))
											{
												make = "Y";
												num = 1;
												denom = 1;
												makeTrainCount++;
											}
											else
											{
												make = "N";
												num = 0;
												denom = 1;
												missTrainCount++;
											}
										}
									}
								}

								ReportingPointForTrainObject reportingPoint = new ReportingPointForTrainObject(originNodeFromPerformanceFile, scheduleOriginTimeToUse, actualOriginTimeToUse, lateAtOrigin, destinationNodeInPerformanceFile, scheduleDestinationTimeToUse, actualDestinationTimeToUse, scheduleTransitTime, actualTransitTime, make, num, denom);
								performanceFileEntries.get(i).addReportingPoint(reportingPoint);

								int[] currentNumeratorDenominator = typeHashmapOfMakes.get(trainType);
								int[] updatedNumeratorDenominator = new int[2];
								updatedNumeratorDenominator[0] = (currentNumeratorDenominator[0] + num);
								updatedNumeratorDenominator[1] = (currentNumeratorDenominator[1] + denom);
								currentNumeratorDenominator[0] = updatedNumeratorDenominator[0];
								currentNumeratorDenominator[1] = updatedNumeratorDenominator[1];

								if (debug)
								{
									System.out.println("\nFor train "+runtimeSymbol);				
									System.out.println(" type is "+trainType);
									if (BIASModifiedOtpConfigPageController.getUseOtpThresholds())
									{
										System.out.println(" threshold is "+ConvertDateTime.convertSerialToDDHHMMSSString(otpThresholdAsDouble));
									}
									else
									{
										System.out.println(" no OTP threshold");
									}

									System.out.println(" at origin node: "+originNodeFromPerformanceFile);
									System.out.print(" sched origin arvl: "+ConvertDateTime.convertSerialToDDHHMMSSString(scheduledOriginArrivalTimeAsDouble));
									System.out.println(" actual origin arvl: "+ConvertDateTime.convertSerialToDDHHMMSSString(actualOriginArrivalTimeAsDouble));
									System.out.print(" sched origin dept: "+ConvertDateTime.convertSerialToDDHHMMSSString(scheduledOriginDepartureTimeAsDouble));
									System.out.println(" actual origin dept: "+ConvertDateTime.convertSerialToDDHHMMSSString(actualOriginDepartureTimeAsDouble));
									System.out.println(" late at origin: "+ConvertDateTime.convertSerialToHHMMSSString(lateAtOrigin));

									System.out.println(" at destination node: "+destinationNodeInPerformanceFile);
									System.out.print(" sched destination arvl: "+ConvertDateTime.convertSerialToDDHHMMSSString(scheduledDestinationArrivalTimeAsDouble));
									System.out.println(" actual destination arvl: "+ConvertDateTime.convertSerialToDDHHMMSSString(actualDestinationArrivalTimeAsDouble));
									System.out.print(" sched destination dept: "+ConvertDateTime.convertSerialToDDHHMMSSString(scheduledDestinationDepartureTimeAsDouble));
									System.out.println(" actual destination dept: "+ConvertDateTime.convertSerialToDDHHMMSSString(actualDestinationDepartureTimeAsDouble));

									System.out.println(" scheduled transit time: "+ConvertDateTime.convertSerialToDDHHMMSSString(scheduleTransitTime));
									System.out.println(" actual transit time: "+ConvertDateTime.convertSerialToDDHHMMSSString(actualTransitTime));
									System.out.println(" make: "+make);
								}
							}
						}
					}
				}
			}
		}	

		resultsMessage += "Total trains measured are " + (makeTrainCount + missTrainCount + exceptedTrainCount)+"\n";
		resultsMessage += "Calculated "+makeTrainCount+" makes, "+missTrainCount+" misses, and "+exceptedTrainCount+" excepted trains\n";

		resultsMessage += "Finished analyzing trains at "+ConvertDateTime.getTimeStamp()+("\n");
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}

	public static HashMap<String, int[]> returnMakeData()
	{
		return typeHashmapOfMakes;
	}

	class odTuple<origin, destination> {

		private final String origin;
		private final String destination;

		public odTuple(String origin, String destination) {
			this.origin = origin;
			this.destination = destination;
		}

		public String getOrigin() {
			return origin;
		}

		public String getDestination() {
			return destination;
		}
	}
}