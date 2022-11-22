package com.bl.bias.read;

import java.io.File;
import java.util.Scanner;

import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.RadixxCarrierInput;
import com.bl.bias.objects.RadixxFlightLegInput;
import com.bl.bias.objects.RadixxHeaderInput;
import com.bl.bias.objects.RadixxScheduleInput;
import com.bl.bias.objects.RadixxSegmentDataRecordInput;
import com.bl.bias.objects.RadixxTrailerInput;
import com.bl.bias.tools.ConvertDateTime;

public class ReadRadixxResSSIMFileForExcel 
{
	private String resultsMessage;

	private Integer objectCount = 0;
	private Integer lastRecordSerialNumber = 0;
	
	private static RadixxScheduleInput schedule = new RadixxScheduleInput();
	
	private static RadixxFlightLegInput lastFlightLeg;

	public ReadRadixxResSSIMFileForExcel(String file) 
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

				Integer recordType = Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getAll_recordType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getAll_recordType()[1])).trim());
				Integer recordSerialNumber = Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[1])).trim());

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
					String z_hdr_titleOfContents = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getHdr_titleOfContents()[0]), Integer.valueOf(BIASParseConfigPageController.z_getHdr_titleOfContents()[1])).trim();
					String z_hdr_dataSetSerialNumber = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getHdr_dataSetSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getHdr_dataSetSerialNumber()[1])).trim();
										
					RadixxHeaderInput header = new RadixxHeaderInput(z_hdr_titleOfContents, z_hdr_dataSetSerialNumber, recordSerialNumber.toString());
					schedule.setHeader(header);
					
					objectCount = objectCount + 2;
				}
				else if (recordType == 2) // Carrier
				{
					String z_car_timeMode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getCar_timeMode()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_timeMode()[1])).trim();
					String z_car_airlineDesignator = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getCar_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_airlineDesignator()[1])).trim();
					String z_car_creatorReference = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getCar_creatorReference()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_creatorReference()[1])).trim();
					String z_car_periodOfValidity= lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getCar_periodOfValidity()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_periodOfValidity()[1])).trim();
					String z_car_creationDate = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getCar_creationDate()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_creationDate()[1])).trim();
					String z_car_titleOfData = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getCar_titleOfData()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_titleOfData()[1])).trim();
					String z_car_releaseDate = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getCar_releaseDate()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_releaseDate()[1])).trim();
					
					RadixxCarrierInput carrier = new RadixxCarrierInput(z_car_timeMode, z_car_airlineDesignator, z_car_creatorReference, z_car_periodOfValidity, z_car_creationDate, z_car_titleOfData, z_car_releaseDate, recordSerialNumber.toString());
					schedule.setCarrier(carrier);
					
					objectCount = objectCount + 7;
				}
				else if (recordType == 3) // Flight
				{
					String z_flr_airlineDesignator = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_airlineDesignator()[1])).trim();		
					String z_flr_flightNumber = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_flightNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_flightNumber()[1])).trim();
					String z_flr_itineraryVariationIdentifier = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_itineraryVariationIdentifier()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_itineraryVariationIdentifier()[1])).trim();
					String z_flr_legSequenceNumber = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_legSequenceNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_legSequenceNumber()[1])).trim();
					String z_flr_serviceType = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_serviceType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_serviceType()[1])).trim();
					String z_flr_periodOfOperation= lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_periodOfOperation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_periodOfOperation()[1])).trim();
					String z_flr_dayOfOperation = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_dayOfOperation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_dayOfOperation()[1])).trim();
					String z_flr_departureStation = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureStation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureStation()[1])).trim();	
					String z_flr_passengerSTD = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTD()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTD()[1])).trim();
					String z_flr_aircraftSTD = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTD()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTD()[1])).trim();
					String z_flr_timeVariationDeparture = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationDeparture()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationDeparture()[1])).trim();
					String z_flr_departureTerminal = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureTerminal()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureTerminal()[1])).trim();
					String z_flr_arrivalStation = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalStation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalStation()[1])).trim();	
					String z_flr_passengerSTA = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTA()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTA()[1])).trim();
					String z_flr_aircraftSTA = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTA()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTA()[1])).trim();
					String z_flr_timeVariationArrival = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationArrival()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationArrival()[1])).trim();
					String z_flr_arrivalTerminal = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalTerminal()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalTerminal()[1])).trim();
					String z_flr_aircraftType = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftType()[1])).trim();
					String z_flr_onwardAirlineDesignator  = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardAirlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardAirlineDesignator()[1])).trim();
					String z_flr_onwardFlightNumber = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightNumber()[1])).trim();
					String z_flr_onwardFlightTransitLayover = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightTransitLayover()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightTransitLayover()[1])).trim(); 
					String z_flr_aircraftConfiguration = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftConfiguration()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftConfiguration()[1])).trim();
					String z_flr_dateVariation = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[1]));
					
					RadixxFlightLegInput flightLeg = new RadixxFlightLegInput(z_flr_airlineDesignator, z_flr_flightNumber, z_flr_itineraryVariationIdentifier, z_flr_legSequenceNumber, z_flr_serviceType, z_flr_periodOfOperation, z_flr_dayOfOperation,
							z_flr_departureStation, z_flr_passengerSTD, z_flr_aircraftSTD, z_flr_timeVariationDeparture, z_flr_departureTerminal, z_flr_arrivalStation, z_flr_passengerSTA, z_flr_aircraftSTA, z_flr_timeVariationArrival,
							z_flr_arrivalTerminal, z_flr_aircraftType, z_flr_onwardAirlineDesignator, z_flr_onwardFlightNumber, z_flr_onwardFlightTransitLayover, z_flr_aircraftConfiguration, z_flr_dateVariation, recordSerialNumber.toString());
					schedule.setFlightLeg(flightLeg);
					lastFlightLeg = flightLeg;
					
					objectCount = objectCount + 23;
				}
				else if (recordType == 4) // Segment
				{
					// Type 4 - Segment Data Record
					String z_seg_airlineDesignator = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getSeg_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_airlineDesignator()[1])).trim();		
					String z_seg_flightNumber = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getSeg_flightNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_flightNumber()[1])).trim();
					String z_seg_itineraryVariationNumber = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getSeg_itineraryVariationNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_itineraryVariationNumber()[1])).trim();
					String z_seg_legSequenceNumber = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getSeg_legSequenceNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_legSequenceNumber()[1])).trim();
					String z_seg_serviceType = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getSeg_serviceType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_serviceType()[1])).trim();
					String z_seg_boardPointIndicator = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getSeg_boardPointIndicator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_boardPointIndicator()[1])).trim();
					String z_seg_offPointIndicator = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getSeg_offPointIndicator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_offPointIndicator()[1])).trim();
					String z_seg_dataElementIdentifier = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getSeg_dataElementIdentifier()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_dataElementIdentifier()[1])).trim();
					String z_seg_segmentBoardPoint = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentBoardPoint()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentBoardPoint()[1])).trim();
					String z_seg_segmentOffPoint = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentOffPoint()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentOffPoint()[1])).trim();
					String z_seg_data = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getSeg_data()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_data()[1])).trim();
					
					RadixxSegmentDataRecordInput segment = new RadixxSegmentDataRecordInput(z_seg_airlineDesignator, z_seg_flightNumber, z_seg_itineraryVariationNumber, z_seg_legSequenceNumber, z_seg_serviceType, z_seg_boardPointIndicator, 
							z_seg_offPointIndicator, z_seg_dataElementIdentifier, z_seg_segmentBoardPoint, z_seg_segmentOffPoint, z_seg_data, recordSerialNumber.toString());
					lastFlightLeg.setSegmentDataRecord(segment);	
					
					objectCount = objectCount + 11;
				}
				else if (recordType == 5) // Trailer
				{
					String z_trl_airlineDesignator = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getTrl_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_airlineDesignator()[1])).trim();
					String z_trl_releaseDate = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getTrl_releaseDate()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_releaseDate()[1])).trim();
					String z_trl_serialNumberCheckReference = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getTrl_serialNumberCheckReference()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_serialNumberCheckReference()[1])).trim();
					String z_trl_continuationEndCode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getTrl_continuationEndCode()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_continuationEndCode()[1])).trim();
					
					RadixxTrailerInput trailer = new RadixxTrailerInput(z_trl_airlineDesignator,  z_trl_releaseDate, z_trl_serialNumberCheckReference, z_trl_continuationEndCode, recordSerialNumber.toString());
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
	
	public static RadixxScheduleInput getSchedule()
	{
		return schedule;
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}