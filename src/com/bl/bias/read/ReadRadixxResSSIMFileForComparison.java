package com.bl.bias.read;

import java.io.File;
import java.util.Scanner;

import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.RadixxCarrierInputIATA;
import com.bl.bias.objects.RadixxCarrierInputS3;
import com.bl.bias.objects.RadixxFlightLegInputIATA;
import com.bl.bias.objects.RadixxFlightLegInputS3;
import com.bl.bias.objects.RadixxHeaderInputIATA;
import com.bl.bias.objects.RadixxHeaderInputS3;
import com.bl.bias.objects.RadixxScheduleInputIATA;
import com.bl.bias.objects.RadixxScheduleInputS3;
import com.bl.bias.objects.RadixxSegmentDataRecordInputIATA;
import com.bl.bias.objects.RadixxTrailerInputIATA;
import com.bl.bias.objects.RadixxTrailerInputS3;
import com.bl.bias.tools.ConvertDateTime;

public class ReadRadixxResSSIMFileForComparison 
{
	private String resultsMessage;

	private Integer lastRecordSerialNumber = 0;

	private RadixxScheduleInputIATA scheduleIATA = new RadixxScheduleInputIATA();
	private RadixxScheduleInputS3 scheduleS3 = new RadixxScheduleInputS3();

	private RadixxFlightLegInputIATA lastFlightLegIATA;
	private RadixxFlightLegInputS3 lastFlightLegS3;

	private Boolean error = false;

	public Boolean read(String file, String designation, Boolean typeIATA, Boolean typeS3) 
	{
		resultsMessage = "\nStarted parsing Radixx Res SSIM file " + designation + " at "+ConvertDateTime.getTimeStamp()+"\n";

		Scanner scanner = null;

		try 
		{
			File sSIMFile = new File(file);
			scanner = new Scanner(sSIMFile);

			if (typeIATA)
			{
				while (scanner.hasNextLine()) 
				{
					String lineFromFile = scanner.nextLine();

					if (lineFromFile.startsWith(" "))  // Nothing on this line
					{
						continue;
					}

					Integer recordType = Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getAll_recordType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getAll_recordType()[1])).trim());
					Integer recordSerialNumber = Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[1])).trim());

					if (recordType == 0) // Nothing on this line
					{
						continue;
					}
					else if (lastRecordSerialNumber + 1 != recordSerialNumber) // Throw exception
					{
						error = true;
						throw new Exception ("Malformed Radixx Res SSIM file");
					}
					else if (recordType == 1) // Header
					{
						String z_hdr_titleOfContents = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getHdr_titleOfContents()[0]), Integer.valueOf(BIASParseConfigPageController.z_getHdr_titleOfContents()[1])).trim();
						String z_hdr_dataSetSerialNumber = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getHdr_dataSetSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getHdr_dataSetSerialNumber()[1])).trim();

						RadixxHeaderInputIATA header = new RadixxHeaderInputIATA(z_hdr_titleOfContents, z_hdr_dataSetSerialNumber, recordSerialNumber.toString());
						scheduleIATA.setHeader(header);
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

						RadixxCarrierInputIATA carrier = new RadixxCarrierInputIATA(z_car_timeMode, z_car_airlineDesignator, z_car_creatorReference, z_car_periodOfValidity, z_car_creationDate, z_car_titleOfData, z_car_releaseDate, recordSerialNumber.toString());
						scheduleIATA.setCarrier(carrier);
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

						RadixxFlightLegInputIATA flightLeg = new RadixxFlightLegInputIATA(z_flr_airlineDesignator, z_flr_flightNumber, z_flr_itineraryVariationIdentifier, z_flr_legSequenceNumber, z_flr_serviceType, z_flr_periodOfOperation, z_flr_dayOfOperation,
								z_flr_departureStation, z_flr_passengerSTD, z_flr_aircraftSTD, z_flr_timeVariationDeparture, z_flr_departureTerminal, z_flr_arrivalStation, z_flr_passengerSTA, z_flr_aircraftSTA, z_flr_timeVariationArrival,
								z_flr_arrivalTerminal, z_flr_aircraftType, z_flr_onwardAirlineDesignator, z_flr_onwardFlightNumber, z_flr_onwardFlightTransitLayover, z_flr_aircraftConfiguration, z_flr_dateVariation, recordSerialNumber.toString());
						scheduleIATA.setFlightLeg(flightLeg);
						lastFlightLegIATA = flightLeg;
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

						RadixxSegmentDataRecordInputIATA segment = new RadixxSegmentDataRecordInputIATA(z_seg_airlineDesignator, z_seg_flightNumber, z_seg_itineraryVariationNumber, z_seg_legSequenceNumber, z_seg_serviceType, z_seg_boardPointIndicator, 
								z_seg_offPointIndicator, z_seg_dataElementIdentifier, z_seg_segmentBoardPoint, z_seg_segmentOffPoint, z_seg_data, recordSerialNumber.toString());
						lastFlightLegIATA.setSegmentDataRecord(segment);	
					}
					else if (recordType == 5) // Trailer
					{
						String z_trl_airlineDesignator = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getTrl_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_airlineDesignator()[1])).trim();
						String z_trl_releaseDate = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getTrl_releaseDate()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_releaseDate()[1])).trim();
						String z_trl_serialNumberCheckReference = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getTrl_serialNumberCheckReference()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_serialNumberCheckReference()[1])).trim();
						String z_trl_continuationEndCode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.z_getTrl_continuationEndCode()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_continuationEndCode()[1])).trim();

						RadixxTrailerInputIATA trailer = new RadixxTrailerInputIATA(z_trl_airlineDesignator,  z_trl_releaseDate, z_trl_serialNumberCheckReference, z_trl_continuationEndCode, recordSerialNumber.toString());
						scheduleIATA.setTrailer(trailer);
					}
					else // Throw exception
					{
						error = true;
						throw new Exception ("Malformed Radixx Res SSIM file");
					}

					lastRecordSerialNumber = recordSerialNumber;
				}
			}
			else if (typeS3) 
			{
				while (scanner.hasNextLine()) 
				{
					String lineFromFile = scanner.nextLine();

					if (lineFromFile.startsWith(" "))  // Nothing on this line
					{
						continue;
					}

					Integer recordType = Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getAll_recordType()[0]), Integer.valueOf(BIASParseConfigPageController.y_getAll_recordType()[1])).trim());
					Integer recordSerialNumber = Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getAll_recordSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.y_getAll_recordSerialNumber()[1])).trim());

					if (recordType == 0) // Nothing on this line
					{
						continue;
					}
					else if (lastRecordSerialNumber + 1 != recordSerialNumber) // Throw exception
					{
						error = true;
						throw new Exception ("Malformed Radixx Res SSIM file");
					}
					else if (recordType == 1) // Tape
					{
						String y_hdr_titleOfContents = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getHdr_titleOfContents()[0]), Integer.valueOf(BIASParseConfigPageController.y_getHdr_titleOfContents()[1])).trim();

						RadixxHeaderInputS3 header = new RadixxHeaderInputS3(y_hdr_titleOfContents, recordSerialNumber.toString());
						scheduleS3.setHeader(header);
					}
					else if (recordType == 2) // Company
					{
						String y_com_timeMode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getCom_timeMode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_timeMode()[1])).trim();
						String y_com_companyCode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getCom_companyCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_companyCode()[1])).trim();
						String y_com_description = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getCom_description()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_description()[1])).trim();
						String y_com_periodOfValidity= lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getCom_periodOfValidity()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_periodOfValidity()[1])).trim();
						String y_com_creationDate = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getCom_creationDate()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_creationDate()[1])).trim();

						RadixxCarrierInputS3 carrier = new RadixxCarrierInputS3(y_com_timeMode, y_com_companyCode, y_com_description, y_com_periodOfValidity, y_com_creationDate);
						scheduleS3.setCarrier(carrier);
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
						scheduleS3.setTrainLeg(trainLeg);
						lastFlightLegS3 = trainLeg;
					}
					else if (recordType == 5) // Final
					{
						String y_trl_companyCode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTrl_companyCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_companyCode()[1])).trim();
						String y_trl_startDate = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTrl_startDate()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_startDate()[1])).trim();
						String y_trl_serialNumberCheckReference = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTrl_serialNumberCheckReference()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_serialNumberCheckReference()[1])).trim();
						String y_trl_continuationEndCode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.y_getTrl_continuationEndCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_continuationEndCode()[1])).trim();

						RadixxTrailerInputS3 trailer = new RadixxTrailerInputS3(y_trl_companyCode,  y_trl_startDate, y_trl_serialNumberCheckReference, y_trl_continuationEndCode, recordSerialNumber.toString());
						scheduleS3.setTrailer(trailer);
					}
					else // Throw exception
					{
						error = true;
						throw new Exception ("Malformed Radixx Res SSIM file");
					}

					lastRecordSerialNumber = recordSerialNumber;
				}
			}

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

		resultsMessage += "Finished parsing Radixx Res SSIM file " + designation + " at "+ConvertDateTime.getTimeStamp()+"\n";

		return error;
	}

	public RadixxScheduleInputIATA getScheduleIATA()
	{
		return scheduleIATA;
	}

	public RadixxScheduleInputS3 getScheduleS3()
	{
		return scheduleS3;
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}