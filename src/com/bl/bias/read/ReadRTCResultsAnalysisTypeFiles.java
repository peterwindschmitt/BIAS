package com.bl.bias.read;

import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.bl.bias.app.BIASCustomAssignmentsWindowController;
import com.bl.bias.app.BIASRTCResultsAnalysisOptionsWindowController;
import com.bl.bias.app.BIASTtestConfigPageController;
import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.RTCResultsAnalysisTypeDataRow;
import com.bl.bias.tools.ConvertDateTime;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ReadRTCResultsAnalysisTypeFiles
{
	private static ArrayList<RTCResultsAnalysisTypeDataRow> parsedTypeFiles = new ArrayList<RTCResultsAnalysisTypeDataRow>();

	private String parsedFileName;
	private String resultsMessage;
	private static String rtcVersion;

	public ReadRTCResultsAnalysisTypeFiles(Boolean parseEntireNetwork, Boolean parseLines, Boolean parseSubdivisions, List<String> listOfLines, List<String> listOfSubdivisions, ArrayList<File> files)
	{
		LocalTime startParseFilesTime = LocalTime.now();
		resultsMessage = "\nStarted parsing "+files.size()+" file(s) by train type at "+startParseFilesTime+"\n";

		Scanner scanner = null;
		
		// Format results
		DecimalFormat formatter1 = new DecimalFormat("#0.###");
		DecimalFormat formatter2 = new DecimalFormat("#0.#");
		
		try 
		{
			// For each file
			Iterator<File> itr1 = files.iterator();
			while (itr1.hasNext())
			{
				File fileToWorkWith = itr1.next();
				setFileName(fileToWorkWith.getName());
				
				Integer customCategory1TrainCount = 0;
				Double customCategory1Velocity = 0.0;
				Double customCategory1TrainMiles = 0.0;
				Integer customCategory1ElapsedTime = 0;
				Integer customCategory1IdealRunTime = 0;
				Double customCategory1OTP = 0.0;
				Boolean customCategory1OTPInvalid = false;
				
				Integer customCategory2TrainCount = 0;
				Double customCategory2Velocity = 0.0;
				Double customCategory2TrainMiles = 0.0;
				Integer customCategory2ElapsedTime = 0;
				Integer customCategory2IdealRunTime = 0;
				Double customCategory2OTP = 0.0;
				Boolean customCategory2OTPInvalid = false;
				
				Integer customCategory1TrainCountEntireNetwork = 0;
				Double customCategory1VelocityEntireNetwork = 0.0;
				Double customCategory1TrainMilesEntireNetwork = 0.0;
				Integer customCategory1ElapsedTimeEntireNetwork = 0;
				Integer customCategory1IdealRunTimeEntireNetwork = 0;
				Double customCategory1OTPEntireNetwork = 0.0;
				Boolean customCategory1OTPEntireNetworkInvalid = false;
				
				Integer customCategory2TrainCountEntireNetwork = 0;
				Double customCategory2VelocityEntireNetwork = 0.0;
				Double customCategory2TrainMilesEntireNetwork = 0.0;
				Integer customCategory2ElapsedTimeEntireNetwork = 0;
				Integer customCategory2IdealRunTimeEntireNetwork = 0;
				Double customCategory2OTPEntireNetwork = 0.0;
				Boolean customCategory2OTPEntireNetworkInvalid = false;
							
				// For each line
				scanner = new Scanner(fileToWorkWith);
				
				while (scanner.hasNextLine()) 
				{
					String targetSequence0 = "RTC Version";
					String targetSequence1 = "statistics";
					String targetSequence2 = null;
					String lineFromFile = scanner.nextLine();

					if (lineFromFile.length() > 75)
					{
						if(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getRtcVersionExtract()[0]), Integer.valueOf(BIASParseConfigPageController.x_getRtcVersionExtract()[1])).trim().contains(targetSequence0))
						{
							String parts[] = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getRtcVersionExtract()[0]), Integer.valueOf(BIASParseConfigPageController.x_getRtcVersionExtract()[1])).split("RTC Version");
							if (rtcVersion == null)
							{
								rtcVersion = parts[1].substring(0,5).trim();
							}
							else if (!parts[1].substring(0,5).trim().equals(rtcVersion))
							{
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Fatal Error Encountered");
								alert.setHeaderText("SUMMARY files from multiple RTC versions detected!");
								alert.showAndWait();
								System.exit(0);
							}
						}

						if(lineFromFile.toLowerCase().substring(1, 55).contains(targetSequence1)) 
						{ 
							String newLineName1 = lineFromFile.substring(1, 45).replace("Statistics reflect data from entire network.", "Entire Network");
							
							//  Check if entire network to be captured
							if ((newLineName1.equals("Entire Network") && (parseEntireNetwork)))
							{
								//  Find train types
								targetSequence2 = "Train type";
								boolean openingSequence = false;
								lineFromFile = scanner.nextLine();

								while (scanner.hasNextLine()) 
								{
									lineFromFile = scanner.nextLine();
									if (lineFromFile.length() > 25) 
									{
										if ((lineFromFile.substring(5, 25).contains(targetSequence2)) && (openingSequence == false))
										{
											openingSequence = true;
											targetSequence2 = "-------";
											scanner.nextLine();
										}
										else if ((lineFromFile.substring(5, 25).contains(targetSequence2)) && (openingSequence == true))
										{		
											resultsMessage +="Extracted type data from file: "+fileToWorkWith.getName().replace(".SUMMARY", "")+", line/subdivision:  "+newLineName1.trim()+"\n";
											
											// Final Custom Category 1 tally for this file
											if ((BIASCustomAssignmentsWindowController.returnCustomCategoryTypes1().size() > 0) && (BIASRTCResultsAnalysisOptionsWindowController.getCustomAssignments()))
											{
												String otpResult;
												
												// Handle OTP == '-----'
												if (customCategory1OTPEntireNetworkInvalid)
													otpResult = "-----";
												else
													otpResult = String.valueOf(Double.parseDouble(formatter2.format(customCategory1OTPEntireNetwork/customCategory1TrainCountEntireNetwork)));										
												
												RTCResultsAnalysisTypeDataRow dataToInsert = new RTCResultsAnalysisTypeDataRow(returnFileName(),																											// File name
													newLineName1.trim(), 																																				// Line name
													BIASCustomAssignmentsWindowController.returnCustomCategory1(), 																							// Train type
													customCategory1TrainCountEntireNetwork,																																// Train count, sum
													Double.parseDouble(formatter1.format(customCategory1VelocityEntireNetwork/customCategory1TrainCountEntireNetwork)),													// Velocity, avg
													Double.parseDouble(formatter2.format(customCategory1TrainMilesEntireNetwork)),																						// Train miles, sum
													String.valueOf(ConvertDateTime.convertSecondsToDDHHMMSSString(customCategory1ElapsedTimeEntireNetwork)),																	// Elapsed time, sum
													String.valueOf(ConvertDateTime.convertSecondsToDDHHMMSSString(customCategory1IdealRunTimeEntireNetwork)),																	// Ideal run time, sum
													otpResult);																																							// OTP, avg
												parsedTypeFiles.add(dataToInsert);
												
												resultsMessage +="Extracted user-defined category ("+BIASCustomAssignmentsWindowController.returnCustomCategory1()+") data from file: "+fileToWorkWith.getName().replace(".SUMMARY", "")+", line/subdivision:  "+newLineName1.trim()+"\n";
											}
											
											// Final Custom Category 2 tally for this file
											if ((BIASCustomAssignmentsWindowController.returnCustomCategoryTypes2().size() > 0) && (BIASRTCResultsAnalysisOptionsWindowController.getCustomAssignments()))
											{
												String otpResult;
												
												// Handle OTP == '-----'
												if (customCategory2OTPEntireNetworkInvalid)
													otpResult = "-----";
												else
													otpResult = String.valueOf(Double.parseDouble(formatter2.format(customCategory2OTPEntireNetwork/customCategory2TrainCountEntireNetwork)));										
												
												RTCResultsAnalysisTypeDataRow dataToInsert = new RTCResultsAnalysisTypeDataRow(returnFileName(),																											// File name
													newLineName1.trim(), 																																				// Line name
													BIASCustomAssignmentsWindowController.returnCustomCategory2(), 																							// Train type
													customCategory2TrainCountEntireNetwork,																																// Train count, sum
													Double.parseDouble(formatter1.format(customCategory2VelocityEntireNetwork/customCategory2TrainCountEntireNetwork)),													// Velocity, avg
													Double.parseDouble(formatter2.format(customCategory2TrainMilesEntireNetwork)),																						// Train miles, sum
													String.valueOf(ConvertDateTime.convertSecondsToDDHHMMSSString(customCategory2ElapsedTimeEntireNetwork)),																	// Elapsed time, sum	
													String.valueOf(ConvertDateTime.convertSecondsToDDHHMMSSString(customCategory2IdealRunTimeEntireNetwork)),																	// Ideal run time, sum
													otpResult);																																							// OTP, avg
												parsedTypeFiles.add(dataToInsert);

												resultsMessage +="Extracted user-defined category ("+BIASCustomAssignmentsWindowController.returnCustomCategory2()+") data from file: "+fileToWorkWith.getName().replace(".SUMMARY", "")+", line/subdivision:  "+newLineName1.trim()+"\n";
											}
											
											break;
										}
										else if (openingSequence)
										{ 
											RTCResultsAnalysisTypeDataRow dataToInsert = new RTCResultsAnalysisTypeDataRow(returnFileName(),																																								// File name
													newLineName1.trim(), 																																																// Line name
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[0]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[1])).trim(), 							// Train type
													Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCount()[0]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCount()[1])).trim()),		// Train count
													Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.x_getSpeed()[1])).trim()),			// Velocity
													Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainMiles()[0]), Integer.valueOf(BIASParseConfigPageController.x_getTrainMiles()[1])).trim()),		// Train miles
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getElapsedRunTime()[0]), Integer.valueOf(BIASParseConfigPageController.x_getElapsedRunTime()[1])).trim(),				// Elapsed time
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getIdealRunTime()[0]), Integer.valueOf(BIASParseConfigPageController.x_getIdealRunTime()[1])).trim(),					// Ideal run time
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getOTP()[0]), Integer.valueOf(BIASParseConfigPageController.x_getOTP()[1])));											// OTP
											
											// Do not execute below if this is part of a custom category and custom categories are enabled
											if ((BIASTtestConfigPageController.getSuppressTypeResultsWhenAssignedToCustomAssignment() == true) && 
													((BIASCustomAssignmentsWindowController.returnCustomCategoryTypes1().contains(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[0]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[1])).trim())) || 
													(BIASCustomAssignmentsWindowController.returnCustomCategoryTypes2().contains(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[0]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[1])).trim()))) && 
													(BIASRTCResultsAnalysisOptionsWindowController.getCustomAssignments() == true))
											{	
												resultsMessage +="Will suppress writing "+lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[0]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[1])).trim()+ " type since it has a user-defined assignment applicable to "+fileToWorkWith.getName().replace(".SUMMARY", "")+", line/subdivision:  "+newLineName1.trim()+"\n";
											}
											else
												parsedTypeFiles.add(dataToInsert);
											
											// Check for Custom Assignment matches for Custom Category 1
											if (BIASCustomAssignmentsWindowController.returnCustomCategoryTypes1().contains(dataToInsert.returnTrainType()) && (BIASRTCResultsAnalysisOptionsWindowController.getCustomAssignments()))
											{
												customCategory1TrainCountEntireNetwork += dataToInsert.returnTrainCount();
												customCategory1VelocityEntireNetwork += (dataToInsert.returnAvgSpeed() * dataToInsert.returnTrainCount());
												customCategory1TrainMilesEntireNetwork += (dataToInsert.returnTrainMiles());
												customCategory1ElapsedTimeEntireNetwork += (ConvertDateTime.convertDDHHMMSSStringToSeconds(dataToInsert.returnElapsedTimeAsString()));
												customCategory1IdealRunTimeEntireNetwork += (ConvertDateTime.convertDDHHMMSSStringToSeconds(dataToInsert.returnIdealRunTimeAsString()));
												
												if (dataToInsert.returnOTP().contains("-"))
												{
													customCategory1OTPEntireNetworkInvalid = true;
												}
												else
												{
													customCategory1OTPEntireNetwork += (Double.valueOf(dataToInsert.returnOTP()) * dataToInsert.returnTrainCount());
												}
											}
											
											// Check for Custom Assignment matches for Custom Category 2
											if (BIASCustomAssignmentsWindowController.returnCustomCategoryTypes2().contains(dataToInsert.returnTrainType()) && (BIASRTCResultsAnalysisOptionsWindowController.getCustomAssignments()))
											{
												customCategory2TrainCountEntireNetwork += dataToInsert.returnTrainCount();
												customCategory2VelocityEntireNetwork += (dataToInsert.returnAvgSpeed() * dataToInsert.returnTrainCount());
												customCategory2TrainMilesEntireNetwork += (dataToInsert.returnTrainMiles());
												customCategory2ElapsedTimeEntireNetwork += (ConvertDateTime.convertDDHHMMSSStringToSeconds(dataToInsert.returnElapsedTimeAsString()));
												customCategory2IdealRunTimeEntireNetwork += (ConvertDateTime.convertDDHHMMSSStringToSeconds(dataToInsert.returnIdealRunTimeAsString()));
												
												if (dataToInsert.returnOTP().contains("-"))
												{
													customCategory2OTPEntireNetworkInvalid = true;
												}
												else
												{
													customCategory2OTPEntireNetwork += (Double.valueOf(dataToInsert.returnOTP()) * dataToInsert.returnTrainCount());
												}
											}
										}
									}
								}
							}
							else if (((listOfLines.contains(lineFromFile.substring(1, 65).split(" line statistics only")[0].toString())) && parseLines) || ((listOfSubdivisions.contains(lineFromFile.substring(1, 65).split(" subdivision statistics only")[0].toString())) && parseSubdivisions))
							{
								// All other lines/subdivisions
								newLineName1 = lineFromFile.substring(1, 65);
								String[] newLineName2 = newLineName1.split(" statistics only");
								
								//  Find train types
								targetSequence2 = "Train type";
								boolean openingSequence = false;							

								while (scanner.hasNextLine()) 
								{
									lineFromFile = scanner.nextLine();
									if (lineFromFile.length() >25)
									{
										if ((lineFromFile.substring(5, 25).contains(targetSequence2)) && (openingSequence == false))
										{
											openingSequence = true;
											targetSequence2 = "-------";
											scanner.nextLine();
										}
										else if ((lineFromFile.substring(5, 25).contains(targetSequence2)) && (openingSequence == true))
										{
											resultsMessage +="Extracted type data from file: "+fileToWorkWith.getName().replace(".SUMMARY", "")+", line/subdivision:  "+newLineName2[0].trim()+"\n";
											
											// Final Custom Category 1 tally for this file
											if ((BIASCustomAssignmentsWindowController.returnCustomCategoryTypes1().size() > 0) && (BIASRTCResultsAnalysisOptionsWindowController.getCustomAssignments()))
											{
												String otpResult;
												
												// Handle OTP == '-----'
												if (customCategory1OTPInvalid)
													otpResult = "-----";
												else
													otpResult = String.valueOf(Double.parseDouble(formatter2.format(customCategory1OTP/customCategory1TrainCount)));										
												
												RTCResultsAnalysisTypeDataRow dataToInsert = new RTCResultsAnalysisTypeDataRow(returnFileName(),																											// File name
													newLineName2[0].trim(), 																																			// Line name
													BIASCustomAssignmentsWindowController.returnCustomCategory1(), 																							// Train type
													customCategory1TrainCount,																																			// Train count, sum
													Double.parseDouble(formatter1.format(customCategory1Velocity/customCategory1TrainCount)),																			// Velocity, avg
													Double.parseDouble(formatter2.format(customCategory1TrainMiles)),																									// Train miles, sum
													String.valueOf(ConvertDateTime.convertSecondsToDDHHMMSSString(customCategory1ElapsedTime)),																					// Elapsed time, sum
													String.valueOf(ConvertDateTime.convertSecondsToDDHHMMSSString(customCategory1IdealRunTime)),																				// Ideal run time, sum
													otpResult);																																							// OTP, avg
												parsedTypeFiles.add(dataToInsert);
												
												resultsMessage +="Extracted user-defined category ("+BIASCustomAssignmentsWindowController.returnCustomCategory1()+") data from file: "+fileToWorkWith.getName().replace(".SUMMARY", "")+", line/subdivision:  "+newLineName2[0].trim()+"\n";
											}
											
											// Final Custom Category 2 tally for this file
											if ((BIASCustomAssignmentsWindowController.returnCustomCategoryTypes2().size() > 0) && (BIASRTCResultsAnalysisOptionsWindowController.getCustomAssignments()))
											{
												String otpResult;
												
												// Handle OTP == '-----'
												if (customCategory2OTPInvalid)
													otpResult = "-----";
												else
													otpResult = String.valueOf(Double.parseDouble(formatter2.format(customCategory2OTP/customCategory2TrainCount)));										
												
												RTCResultsAnalysisTypeDataRow dataToInsert = new RTCResultsAnalysisTypeDataRow(returnFileName(),																											// File name
													newLineName2[0].trim(), 																																			// Line name
													BIASCustomAssignmentsWindowController.returnCustomCategory2(), 																							// Train type
													customCategory2TrainCount,																																			// Train count, sum
													Double.parseDouble(formatter1.format(customCategory2Velocity/customCategory2TrainCount)),																			// Velocity, avg
													Double.parseDouble(formatter2.format(customCategory2TrainMiles)),																									// Train miles, sum
													String.valueOf(ConvertDateTime.convertSecondsToDDHHMMSSString(customCategory2ElapsedTime)),																					// Elapsed time, sum	
													String.valueOf(ConvertDateTime.convertSecondsToDDHHMMSSString(customCategory2IdealRunTime)),																				// Ideal run time, sum
													otpResult);																																							// OTP, avg
												parsedTypeFiles.add(dataToInsert);
												
												resultsMessage +="Extracted user-defined category ("+BIASCustomAssignmentsWindowController.returnCustomCategory2()+") data from file: "+fileToWorkWith.getName().replace(".SUMMARY", "")+", line/subdivision:  "+newLineName2[0].trim()+"\n";
											}
											
											break;
										}
										else if (openingSequence)
										{ 
											RTCResultsAnalysisTypeDataRow dataToInsert = new RTCResultsAnalysisTypeDataRow(returnFileName(),																																								// File name
													newLineName2[0].trim(), 																																															// Line name
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[0]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[1])).trim(), 							// Train type
													Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCount()[0]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCount()[1])).trim()),		// Train count
													Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.x_getSpeed()[1])).trim()),			// Velocity
													Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainMiles()[0]), Integer.valueOf(BIASParseConfigPageController.x_getTrainMiles()[1])).trim()),		// Train miles
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getElapsedRunTime()[0]), Integer.valueOf(BIASParseConfigPageController.x_getElapsedRunTime()[1])).trim(),				// Elapsed time
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getIdealRunTime()[0]), Integer.valueOf(BIASParseConfigPageController.x_getIdealRunTime()[1])).trim(),					// Ideal run time
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getOTP()[0]), Integer.valueOf(BIASParseConfigPageController.x_getOTP()[1])));											// OTP
											
											// Do not execute below if this is part of a custom category and custom categories are enabled
											if ((BIASTtestConfigPageController.getSuppressTypeResultsWhenAssignedToCustomAssignment() == true) && 
													((BIASCustomAssignmentsWindowController.returnCustomCategoryTypes1().contains(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[0]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[1])).trim())) || 
													(BIASCustomAssignmentsWindowController.returnCustomCategoryTypes2().contains(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[0]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[1])).trim()))) && 
													(BIASRTCResultsAnalysisOptionsWindowController.getCustomAssignments() == true))
											{	
												resultsMessage +="Will Suppress writing "+lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[0]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[1])).trim()+ " type since it has a user-defined assignment applicable to "+fileToWorkWith.getName().replace(".SUMMARY", "")+", line/subdivision:  "+newLineName2[0].trim()+"\n";
											}
											else
												parsedTypeFiles.add(dataToInsert);
											
											// Check for Custom Assignment matches for Custom Category 1
											if (BIASCustomAssignmentsWindowController.returnCustomCategoryTypes1().contains(dataToInsert.returnTrainType()) && (BIASRTCResultsAnalysisOptionsWindowController.getCustomAssignments()))
											{
												customCategory1TrainCount += dataToInsert.returnTrainCount();
												customCategory1Velocity += (dataToInsert.returnAvgSpeed() * dataToInsert.returnTrainCount());
												customCategory1TrainMiles += (dataToInsert.returnTrainMiles());
												customCategory1ElapsedTime += (ConvertDateTime.convertDDHHMMSSStringToSeconds(dataToInsert.returnElapsedTimeAsString()));
												customCategory1IdealRunTime += (ConvertDateTime.convertDDHHMMSSStringToSeconds(dataToInsert.returnIdealRunTimeAsString()));
												
												if (dataToInsert.returnOTP().contains("-"))
												{
													customCategory1OTPInvalid = true;
												}
												else
												{
													customCategory1OTP += (Double.valueOf(dataToInsert.returnOTP()) * dataToInsert.returnTrainCount());
												}
											}
											
											// Check for Custom Assignment matches for Custom Category 2
											if (BIASCustomAssignmentsWindowController.returnCustomCategoryTypes2().contains(dataToInsert.returnTrainType()) && (BIASRTCResultsAnalysisOptionsWindowController.getCustomAssignments()))
											{
												customCategory2TrainCount += dataToInsert.returnTrainCount();
												customCategory2Velocity += (dataToInsert.returnAvgSpeed() * dataToInsert.returnTrainCount());
												customCategory2TrainMiles += (dataToInsert.returnTrainMiles());
												customCategory2ElapsedTime += (ConvertDateTime.convertDDHHMMSSStringToSeconds(dataToInsert.returnElapsedTimeAsString()));
												customCategory2IdealRunTime += (ConvertDateTime.convertDDHHMMSSStringToSeconds(dataToInsert.returnIdealRunTimeAsString()));
												
												if (dataToInsert.returnOTP().contains("-"))
												{
													customCategory2OTPInvalid = true;
												}
												else
												{
													customCategory2OTP += (Double.valueOf(dataToInsert.returnOTP()) * dataToInsert.returnTrainCount());
												}
											}
										}
									}					
								}
							}
						}
					}
				}
				scanner.close();
			}
			LocalTime finishParseFilesTime = LocalTime.now();
			resultsMessage += "Finished parsing "+files.size()+" train type file(s) at "+finishParseFilesTime+"\n";
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		finally
		{
			scanner.close();
		}	
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}

	public static ArrayList<RTCResultsAnalysisTypeDataRow> returnTypeFiles()
	{
		return parsedTypeFiles;
	}
	
	public static void clearTypeFiles()
	{
		parsedTypeFiles.clear();
	}

	private void setFileName(String fileName)
	{
		parsedFileName = fileName;
	}

	private String returnFileName()
	{
		return parsedFileName;
	}

	public static String returnRTCVersion()
	{
		return rtcVersion;
	}
}