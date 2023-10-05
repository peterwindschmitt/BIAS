package com.bl.bias.read;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.RTCResultsAnalysisGroupDataRow;
import com.bl.bias.tools.ConvertDateTime;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ReadRTCResultsAnalysisGroupFiles
{
	private static ArrayList<RTCResultsAnalysisGroupDataRow> parsedGroupFiles = new ArrayList<RTCResultsAnalysisGroupDataRow>();

	private String parsedFileName;
	private String resultsMessage;
	private static String rtcVersion;

	public ReadRTCResultsAnalysisGroupFiles(Boolean parseEntireNetwork, Boolean parseLines, Boolean parseSubdivisions, List<String> listOfLines, List<String> listOfSubdivisions, ArrayList<File> files)
	{
		resultsMessage = "\nStarted parsing "+files.size()+" file(s) by train group at "+ConvertDateTime.getTimeStamp()+"\n";

		Scanner scanner = null;

		try 
		{
			// For each file
			Iterator<File> itr1 = files.iterator();
			while (itr1.hasNext())
			{
				File fileToWorkWith = itr1.next();
				setFileName(fileToWorkWith.getName());

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
						if(lineFromFile.substring(10, 75).trim().contains(targetSequence0))
						{
							String parts[] = lineFromFile.substring(10, 75).split("RTC Version");
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
								//  Find train groups
								targetSequence2 = "Group        Count";
								boolean openingSequence = false;
								lineFromFile = scanner.nextLine();

								while (scanner.hasNextLine()) 
								{
									lineFromFile = scanner.nextLine();
									if (lineFromFile.length() > 15)
									{
										if ((lineFromFile.substring(4, 22).contains(targetSequence2)) && (openingSequence == false))
										{
											openingSequence = true;
											targetSequence2 = "-------";
											scanner.nextLine();
										}
										else if ((lineFromFile.substring(4, 22).contains(targetSequence2)) && (openingSequence == true))
										{
											resultsMessage +="Extracted group data from file: "+fileToWorkWith.getName().replace(".SUMMARY", "")+", line/subdivision: "+newLineName1.trim()+"\n";
											break;
										}
										else if (openingSequence)
										{ 
											RTCResultsAnalysisGroupDataRow dataToInsert = new RTCResultsAnalysisGroupDataRow(returnFileName(),																										// File name
													newLineName1.trim(),																																											// Line name
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[2]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[3])).trim(), 							// Train Group
													Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCount()[2]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCount()[3])).trim()),		// Train count
													Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getSpeed()[2]), Integer.valueOf(BIASParseConfigPageController.x_getSpeed()[3])).trim()),					// Velocity
													Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainMiles()[2]), Integer.valueOf(BIASParseConfigPageController.x_getTrainMiles()[3])).trim()),		// Train miles
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getElapsedRunTime()[2]), Integer.valueOf(BIASParseConfigPageController.x_getElapsedRunTime()[3])).trim(),				// Elapsed time
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getIdealRunTime()[2]), Integer.valueOf(BIASParseConfigPageController.x_getIdealRunTime()[3])).trim(),					// Ideal run time
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getOTP()[2]), Integer.valueOf(BIASParseConfigPageController.x_getOTP()[3])));											// OTP
											parsedGroupFiles.add(dataToInsert);
										}
									}
								}
							}				
							else if (((listOfLines.contains(lineFromFile.substring(1, 65).split(" line statistics only")[0].toString())) && parseLines) || ((listOfSubdivisions.contains(lineFromFile.substring(1, 65).split(" subdivision statistics only")[0].toString())) && parseSubdivisions))
							{ 
								// All other lines/subdivisions
								newLineName1 = lineFromFile.substring(1, 65);
								String[] newLineName2 = newLineName1.split(" statistics only");
								
								//  Find train groups
								targetSequence2 = "Group        Count";
								boolean openingSequence = false;

								while (scanner.hasNextLine()) 
								{
									lineFromFile = scanner.nextLine();
									if (lineFromFile.length() > 15)
									{
										if ((lineFromFile.substring(4, 22).contains(targetSequence2)) && (openingSequence == false))
										{
											targetSequence2 = "-------";
											scanner.nextLine();
											openingSequence = true;
										}
										else if ((lineFromFile.substring(4, 22).contains(targetSequence2)) && (openingSequence == true))
										{
											resultsMessage +="Extracted group data from file: "+fileToWorkWith.getName().replace(".SUMMARY", "")+", line/subdivision: "+newLineName2[0].trim()+"\n";
											break;
										}
										else if (openingSequence)
										{ 
											RTCResultsAnalysisGroupDataRow dataToInsert = new RTCResultsAnalysisGroupDataRow(returnFileName(),																																					// File name
													newLineName2[0].trim(),																																											// Line name
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[2]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[3])).trim(), 							// Train Group
													Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCount()[2]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCount()[3])).trim()),		// Train count
													Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getSpeed()[2]), Integer.valueOf(BIASParseConfigPageController.x_getSpeed()[3])).trim()),					// Velocity
													Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainMiles()[2]), Integer.valueOf(BIASParseConfigPageController.x_getTrainMiles()[3])).trim()),		// Train miles
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getElapsedRunTime()[2]), Integer.valueOf(BIASParseConfigPageController.x_getElapsedRunTime()[3])).trim(),				// Elapsed time
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getIdealRunTime()[2]), Integer.valueOf(BIASParseConfigPageController.x_getIdealRunTime()[3])).trim(),					// Ideal run time
													lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getOTP()[2]), Integer.valueOf(BIASParseConfigPageController.x_getOTP()[3])));											// OTP
											parsedGroupFiles.add(dataToInsert);
										}
									}
								}
							}
						}
					}
				}
				scanner.close();
			}

			resultsMessage += "Finished parsing "+files.size()+" train group file(s) at "+ConvertDateTime.getTimeStamp()+"\n";
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

	public static ArrayList<RTCResultsAnalysisGroupDataRow> returnGroupFiles()
	{
		return parsedGroupFiles;
	}
	
	public static void clearGroupFiles()
	{
		parsedGroupFiles.clear();
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