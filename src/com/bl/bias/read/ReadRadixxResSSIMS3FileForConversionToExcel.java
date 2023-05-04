package com.bl.bias.read;

import java.io.File;
import java.util.Scanner;

import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.RadixxCarrierInputS3;
import com.bl.bias.objects.RadixxFlightLegInputS3;
import com.bl.bias.objects.RadixxHeaderInputS3;
import com.bl.bias.objects.RadixxScheduleInputS3;
import com.bl.bias.objects.RadixxTrailerInputS3;
import com.bl.bias.tools.ConvertDateTime;

public class ReadRadixxResSSIMS3FileForConversionToExcel 
{
	private String resultsMessage;

	private Integer objectCount = 0;
	private Integer lastRecordSerialNumber = 0;
	
	private static RadixxScheduleInputS3 schedule = new RadixxScheduleInputS3();
	
	private static RadixxFlightLegInputS3 lastTrainLeg;

	public ReadRadixxResSSIMS3FileForConversionToExcel(String file) 
	{
		resultsMessage = "\nStarted parsing Radixx Res SSIM file at "+ConvertDateTime.getTimeStamp()+"\n";

		Scanner scanner = null;

		try 
		{
			File sSIMFile = new File(file);
			scanner = new Scanner(sSIMFile);

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();

				if (lineFromFile.startsWith(" "))  // Nothing on this line
				{
					continue;
				}

				Integer recordType = Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getAll_recordType()[0]), Integer.valueOf(BIASParseConfigPageController.y_getAll_recordType()[1])).trim());
				Integer recordSerialNumber = Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getAll_recordSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.y_getAll_recordSerialNumber()[1])).trim());
				
				objectCount = objectCount + 2;
				
				if (recordType == 0) // Nothing on this line
				{
					continue;
				}
				else if (lastRecordSerialNumber + 1 != recordSerialNumber) // Throw exception
				{
					throw new Exception ("Malformed Radixx Res SSIM file");
				}
				else if (recordType == 1) // Header
				{
					String y_hdr_titleOfContents = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getHdr_titleOfContents()[0]), Integer.valueOf(BIASParseConfigPageController.y_getHdr_titleOfContents()[1])).trim();
										
					RadixxHeaderInputS3 header = new RadixxHeaderInputS3(y_hdr_titleOfContents, recordSerialNumber.toString());
					schedule.setHeader(header);
					
					objectCount = objectCount + 1;
				}
				else if (recordType == 2) // Company (Carrier)
				{
					String y_com_timeMode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getCom_timeMode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_timeMode()[1])).trim();
					String y_com_companyCode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getCom_companyCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_companyCode()[1])).trim();
					String y_com_description = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getCom_description()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_description()[1])).trim();
					String y_com_periodOfValidity= lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getCom_periodOfValidity()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_periodOfValidity()[1])).trim();
					String y_com_creationDate = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getCom_creationDate()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_creationDate()[1])).trim();
					
					RadixxCarrierInputS3 carrier = new RadixxCarrierInputS3(y_com_timeMode, y_com_companyCode, y_com_description, y_com_periodOfValidity, y_com_creationDate);
					schedule.setCarrier(carrier);
					
					objectCount = objectCount + 5;	
				}
				else if (recordType == 3) // Timetable
				{
					String y_ttb_companyCode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_companyCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_companyCode()[1])).trim();		
					String y_ttb_trainNumber = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainNumber()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainNumber()[1])).trim();
					String y_ttb_itineraryVariationIdentifier = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_itineraryVariationIdentifier()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_itineraryVariationIdentifier()[1])).trim();
					String y_ttb_legSequenceNumber = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_legSequenceNumber()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_legSequenceNumber()[1])).trim();
					String y_ttb_commercialCategory = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_commercialCategory()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_commercialCategory()[1])).trim();
					String y_ttb_periodOfOperation= lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_periodOfOperation()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_periodOfOperation()[1])).trim();
					String y_ttb_dayOfOperation = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_dayOfOperation()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_dayOfOperation()[1])).trim();
					String y_ttb_departureStation = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_departureStation()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_departureStation()[1])).trim();	
					String y_ttb_passengerSTD = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_passengerSTD()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_passengerSTD()[1])).trim();
					String y_ttb_trainSTD = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainSTD()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainSTD()[1])).trim();
					String y_ttb_timeVariationDeparture = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_timeVariationDeparture()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_timeVariationDeparture()[1])).trim();
					String y_ttb_departureTerminal = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_departureTerminal()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_departureTerminal()[1])).trim();
					String y_ttb_arrivalStation = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_arrivalStation()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_arrivalStation()[1])).trim();	
					String y_ttb_passengerSTA = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_passengerSTA()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_passengerSTA()[1])).trim();
					String y_ttb_trainSTA = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainSTA()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainSTA()[1])).trim();
					String y_ttb_timeVariationArrival = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_timeVariationArrival()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_timeVariationArrival()[1])).trim();
					String y_ttb_arrivalTerminal = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_arrivalTerminal()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_arrivalTerminal()[1])).trim();
					String y_ttb_serviceType = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTtb_serviceType()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_serviceType()[1])).trim();
					
					RadixxFlightLegInputS3 trainLeg = new RadixxFlightLegInputS3(y_ttb_companyCode, y_ttb_trainNumber, y_ttb_itineraryVariationIdentifier, y_ttb_legSequenceNumber, y_ttb_commercialCategory, y_ttb_periodOfOperation, y_ttb_dayOfOperation,
							y_ttb_departureStation, y_ttb_passengerSTD, y_ttb_trainSTD, y_ttb_timeVariationDeparture, y_ttb_departureTerminal, 
							y_ttb_arrivalStation, y_ttb_passengerSTA, y_ttb_trainSTA, y_ttb_timeVariationArrival, y_ttb_arrivalTerminal, y_ttb_serviceType);
					schedule.setTrainLeg(trainLeg);
					lastTrainLeg = trainLeg;
					
					objectCount = objectCount + 18;
				}
				else if (recordType == 4) // Segment
				{
					throw new Exception ("Segment/Comment data is not supported for S3 SSIM files");
				}
				else if (recordType == 5) // Trailer
				{
					String y_trl_companyCode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTrl_companyCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_companyCode()[1])).trim();
					String y_trl_startDate = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTrl_startDate()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_startDate()[1])).trim();
					String y_trl_serialNumberCheckReference = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTrl_serialNumberCheckReference()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_serialNumberCheckReference()[1])).trim();
					String y_trl_continuationEndCode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTrl_continuationEndCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_continuationEndCode()[1])).trim();
					
					RadixxTrailerInputS3 trailer = new RadixxTrailerInputS3(y_trl_companyCode,  y_trl_startDate, y_trl_serialNumberCheckReference, y_trl_continuationEndCode, recordSerialNumber.toString());
					schedule.setTrailer(trailer);
							
					objectCount = objectCount + 4;
				}
				else // Throw exception
				{
					throw new Exception ("Malformed Radixx Res SSIM file");
				}

				lastRecordSerialNumber = recordSerialNumber;
			}

			resultsMessage +="Extracted "+objectCount+" eligible objects from SSIM file\n";
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
		
		resultsMessage += "Finished parsing Radixx Res SSIM file at "+ConvertDateTime.getTimeStamp()+"\n";
	}
	
	public static RadixxScheduleInputS3 getSchedule()
	{
		return schedule;
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}