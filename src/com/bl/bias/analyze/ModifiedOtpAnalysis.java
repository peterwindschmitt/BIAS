package com.bl.bias.analyze;

import java.util.ArrayList;

import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.app.BIASModifiedOtpConfigPageController;
import com.bl.bias.objects.ModifiedOtpTrainObject;
import com.bl.bias.read.ReadModifiedOtpFiles;

public class ModifiedOtpAnalysis 
{
	private static String resultsMessage;

	private static ArrayList<ModifiedOtpTrainObject> trainsToTestForModifiedOtp = new ArrayList<ModifiedOtpTrainObject>();
	private static ArrayList<String> trainSymbolsFromConfigFile = new ArrayList<String>();
	private static String gracePeriodFromConfigFileAsString; 
	private static Double gracePeriodFromConfigFileAsSerial; 
	private Integer modificationsToOtp;

	public ModifiedOtpAnalysis() 
	{
		resultsMessage = "Started analyzing trains at "+ConvertDateTime.getTimeStamp()+"\n";
		
		modificationsToOtp = 0;
		
		// Get permissible minutes of deviation permitted
		gracePeriodFromConfigFileAsString = BIASModifiedOtpConfigPageController.getPermissibleMinutesOfDelayAsString();
		gracePeriodFromConfigFileAsSerial = ConvertDateTime.convertDDHHMMStringToSerial(gracePeriodFromConfigFileAsString);

		// Assign all trains from Read class
		trainsToTestForModifiedOtp.clear();
		trainsToTestForModifiedOtp.addAll(ReadModifiedOtpFiles.getTrainsForModifiedOtp());
		trainSymbolsFromConfigFile.clear();

		// Load in trains from config file
		for (int i = 0; i < BIASModifiedOtpConfigPageController.getSchedulePointEntries().split(",").length; i++)
		{
			trainSymbolsFromConfigFile.add(BIASModifiedOtpConfigPageController.getSchedulePointEntries().split(",")[i]);
		}

		// For each train
		for (int i = 0; i < trainsToTestForModifiedOtp.size(); i++)
		{
			// Match to train from config
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
							Double scheduledArrivalTimeAsDouble = ConvertDateTime.convertDDHHMMSSStringToSerial(trainsToTestForModifiedOtp.get(i).getRouteEntries().get(k).getScheduledArrivalTimeAsString());
							Double scheduledDepartureTimeAsDouble =  ConvertDateTime.convertDDHHMMSSStringToSerial(trainsToTestForModifiedOtp.get(i).getRouteEntries().get(k).getScheduledDepartureTimeAsString());
							Double simulatedArrivalTimeAsDouble = ConvertDateTime.convertDDHHMMSSStringToSerial(trainsToTestForModifiedOtp.get(i).getRouteEntries().get(k).getSimulatedArrivalTimeAsString());
							Double simulatedDepartureTimeAsDouble = ConvertDateTime.convertDDHHMMSSStringToSerial(trainsToTestForModifiedOtp.get(i).getRouteEntries().get(k).getSimulatedDepartureTimeAsString());
							System.out.print("Checking train "+trainsToTestForModifiedOtp.get(i).getSymbol());
							System.out.print(" at node "+trainSymbolsFromConfigFile.get(j+1));
							System.out.print(" which OSd at "+ConvertDateTime.convertSerialToDDHHMMSSString(simulatedArrivalTimeAsDouble)+"/"+ConvertDateTime.convertSerialToDDHHMMSSString(simulatedDepartureTimeAsDouble));
							System.out.println(" and was scheduled for "+ConvertDateTime.convertSerialToDDHHMMSSString(scheduledArrivalTimeAsDouble)+"/"+ConvertDateTime.convertSerialToDDHHMMSSString(scheduledDepartureTimeAsDouble));
						}
					}
				}
			}
		}

		resultsMessage += "Found "+modificationsToOtp+" modifications to OTP\n";
		resultsMessage += "Finished analyzing trains at "+ConvertDateTime.getTimeStamp()+("\n");
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}