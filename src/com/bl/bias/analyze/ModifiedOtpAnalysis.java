package com.bl.bias.analyze;

import java.util.ArrayList;

import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.app.BIASModifiedOtpConfigPageController;
import com.bl.bias.objects.ModifiedOtpTrainObjectA;
import com.bl.bias.read.ReadModifiedOtpFiles;

public class ModifiedOtpAnalysis 
{
	private static String resultsMessage;

	private static ArrayList<ModifiedOtpTrainObjectA> trainsToTestForModifiedOtp = new ArrayList<ModifiedOtpTrainObjectA>();
	private static ArrayList<String> trainSymbolsFromConfigFile = new ArrayList<String>();
	private static String gracePeriodFromConfigFileAsString; 
	private static Double gracePeriodFromConfigFileAsSerial; 
	private Integer exceptionsToOtp;

	public ModifiedOtpAnalysis() 
	{
		resultsMessage = "Started analyzing trains at "+ConvertDateTime.getTimeStamp()+"\n";
		
		exceptionsToOtp = 0;
		
		// Get permissible minutes of deviation permitted
		gracePeriodFromConfigFileAsString = BIASModifiedOtpConfigPageController.getPermissibleMinutesOfDelayAsString();
		gracePeriodFromConfigFileAsSerial = ConvertDateTime.convertDDHHMMStringToSerial(gracePeriodFromConfigFileAsString);

		// Assign all trains from Read class
		trainsToTestForModifiedOtp.clear();
		//trainsToTestForModifiedOtp.addAll(ReadModifiedOtpFiles.getEnabledTrainsFromTrainFile());
		trainSymbolsFromConfigFile.clear();

		// Load in trains from config file
		for (int i = 0; i < BIASModifiedOtpConfigPageController.getSchedulePointEntries().split(",").length; i++)
		{
			trainSymbolsFromConfigFile.add(BIASModifiedOtpConfigPageController.getSchedulePointEntries().split(",")[i]);
		}

		// For each train to test
		for (int i = 0; i < trainsToTestForModifiedOtp.size(); i++)
		{
			// Match to train specified in config file
			for (int j = 0; j < trainSymbolsFromConfigFile.size(); j+=3)
			{
				if (trainsToTestForModifiedOtp.get(i).getSymbol().contains(trainSymbolsFromConfigFile.get(j)))
				{
					// Match on node
					for (int k = 0; k < trainsToTestForModifiedOtp.get(i).getRouteEntries().size(); k++)
					{
						if (trainSymbolsFromConfigFile.get(j + 1).equals(trainsToTestForModifiedOtp.get(i).getRouteEntries().get(k).getNode()))
						{
							// Convert values to serial
							// Now check times
							Double scheduledArrivalTimeAsDouble =  ConvertDateTime.convertDDHHMMSSStringToSerial(trainsToTestForModifiedOtp.get(i).getRouteEntries().get(k).getScheduledArrivalTimeAsString());
							Double scheduledDepartureTimeAsDouble =  ConvertDateTime.convertDDHHMMSSStringToSerial(trainsToTestForModifiedOtp.get(i).getRouteEntries().get(k).getScheduledDepartureTimeAsString());
							Double simulatedArrivalTimeAsDouble = ConvertDateTime.convertDDHHMMSSStringToSerial(trainsToTestForModifiedOtp.get(i).getRouteEntries().get(k).getSimulatedArrivalTimeAsString());
							Double simulatedDepartureTimeAsDouble = ConvertDateTime.convertDDHHMMSSStringToSerial(trainsToTestForModifiedOtp.get(i).getRouteEntries().get(k).getSimulatedDepartureTimeAsString());
							
							// Determine if it was over the threshold
							if ((Math.max(simulatedDepartureTimeAsDouble, simulatedArrivalTimeAsDouble)) > (Math.max((scheduledDepartureTimeAsDouble + gracePeriodFromConfigFileAsSerial), (scheduledArrivalTimeAsDouble + gracePeriodFromConfigFileAsSerial))))
							{
								trainsToTestForModifiedOtp.get(i).getRouteEntries().get(k).setNotCompliant();
								exceptionsToOtp++;
							}
						}
					}
				}
			}
		}

		resultsMessage += "Found "+exceptionsToOtp+" exceptions to OTP\n";
		resultsMessage += "Finished analyzing trains at "+ConvertDateTime.getTimeStamp()+("\n");
	}
	
	public ArrayList<ModifiedOtpTrainObjectA> getTrainsAnalyzedForModifiedOtp()
	{
		return trainsToTestForModifiedOtp;
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}