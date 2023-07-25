package com.bl.bias.analyze;

import java.util.ArrayList;
import java.util.ListIterator;

import com.bl.bias.objects.RadixxFlightLegInputIATA;
import com.bl.bias.objects.RadixxScheduleInputIATA;
import com.bl.bias.read.ReadRadixxResSSIMFileForComparison;

public class SSIMComparisonAnalysis 
{
	private String fileAName;
	private String fileBName;
	private String fileALocation;
	private String fileBLocation;
	private String message = "";
	private String textForComparisonFile = "";

	private static RadixxScheduleInputIATA fileASchedule;
	private static RadixxScheduleInputIATA fileBSchedule;

	private Boolean checkType1;
	private Boolean checkType2;
	private Boolean checkType3;
	private Boolean checkType4;
	private Boolean checkType5;
	
	private Boolean checkType3AllAttributes;
	private Boolean checkType3LimitedAttributes;
	
	private Integer comparisonCount = 0;

	public SSIMComparisonAnalysis(String fileAName, String fileBName, String fileALocation, String fileBLocation, ReadRadixxResSSIMFileForComparison readDataA, ReadRadixxResSSIMFileForComparison readDataB, Boolean checkType1, Boolean checkType2, Boolean checkType3, Boolean checkType4, Boolean checkType5, Boolean check3FullAttributes, Boolean check3LimitedAttributes)
	{
		this.fileAName = fileAName;
		this.fileBName = fileBName;

		this.fileALocation = fileALocation;
		this.fileBLocation = fileBLocation;

		this.fileASchedule = readDataA.getScheduleIATA();
		this.fileBSchedule = readDataB.getScheduleIATA();

		this.checkType1 = checkType1;
		this.checkType2 = checkType2;
		this.checkType3 = checkType3;
		this.checkType4 = checkType4;
		this.checkType5 = checkType5;
		
		this.checkType3AllAttributes = checkType3AllAttributes;
		this.checkType3LimitedAttributes = checkType3LimitedAttributes;
	}

	public Boolean analyze()
	{
		Boolean error = false;

		// No record types selected
		if ((!checkType1) && (!checkType2) && (!checkType3) && (!checkType4) && (!checkType5))
		{
			message += "No record types have been selected for comparison.\n";
			error = true;
		}	
		else
		{
			textForComparisonFile = "Radixx Res SSIM Conversion Comparison Report";
			textForComparisonFile += "\nFile A: "+fileAName.replace(".txt", "");
			textForComparisonFile += " at "+fileALocation;
			textForComparisonFile += "\nFile B: "+fileBName.replace(".txt", "");
			textForComparisonFile += " at "+fileBLocation+"\n";

			// Type 1
			if (checkType1)
			{
				textForComparisonFile += "\nType 1 record comparison:";

				// Title of Contents
				if (fileASchedule.getHeader().getTitleOfContents().equals(fileBSchedule.getHeader().getTitleOfContents()))
					textForComparisonFile += "\n\t Title of Contents \t\tOk";
				else
				{
					textForComparisonFile += "\n\t Title of Contents \t\t*** A = "+fileASchedule.getHeader().getTitleOfContents();
					textForComparisonFile += ", B = "+fileBSchedule.getHeader().getTitleOfContents()+" ***";
				}

				// Data Set Serial Number
				if (fileASchedule.getHeader().getDataSetSerialNumber().equals(fileBSchedule.getHeader().getDataSetSerialNumber()))
					textForComparisonFile += "\n\t Data Set Serial Number \tOk";
				else
				{
					textForComparisonFile += "\n\t Data Set Serial Number \t*** A = "+fileASchedule.getHeader().getDataSetSerialNumber();
					textForComparisonFile += ", B = "+fileBSchedule.getHeader().getDataSetSerialNumber()+" ***";
				}
				
				comparisonCount += 2;
			}
			else
				textForComparisonFile += "\nType 1 records not compared";

			// Type 2
			if (checkType2)
			{
				textForComparisonFile += "\nType 2 record comparison:";

				// Time Mode
				if (fileASchedule.getCarrier().getTimeMode().equals(fileBSchedule.getCarrier().getTimeMode()))
					textForComparisonFile += "\n\t Time Mode \t\t\tOk";
				else
				{
					textForComparisonFile += "\n\t Time Mode \t\t\t*** A = "+fileASchedule.getCarrier().getTimeMode();
					textForComparisonFile += ", B = "+fileBSchedule.getCarrier().getTimeMode()+" ***";
				}

				// Airline Designator
				if (fileASchedule.getCarrier().getAirlineDesignator().equals(fileBSchedule.getCarrier().getAirlineDesignator()))
					textForComparisonFile += "\n\t Airline (Railroad) Designator \tOk";
				else
				{
					textForComparisonFile += "\n\t Airline (Railroad) Designator \t*** A = "+fileASchedule.getCarrier().getAirlineDesignator();
					textForComparisonFile += ", B = "+fileBSchedule.getCarrier().getAirlineDesignator()+" ***";
				}

				// Creator Reference
				if (fileASchedule.getCarrier().getCreatorReference().equals(fileBSchedule.getCarrier().getCreatorReference()))
					textForComparisonFile += "\n\t Creator Reference \t\tOk";
				else
				{
					textForComparisonFile += "\n\t Creation Reference \t\t*** A = "+fileASchedule.getCarrier().getCreatorReference();
					textForComparisonFile += ", B = "+fileBSchedule.getCarrier().getCreatorReference()+" ***";
				}

				// Period of Validity
				if (fileASchedule.getCarrier().getPeriodOfValidity().equals(fileBSchedule.getCarrier().getPeriodOfValidity()))
					textForComparisonFile += "\n\t Period of Validity \t\tOk";
				else
				{
					textForComparisonFile += "\n\t Period of Validity \t\t*** A = "+fileASchedule.getCarrier().getPeriodOfValidity();
					textForComparisonFile += ", B = "+fileBSchedule.getCarrier().getPeriodOfValidity()+" ***";
				}

				// Creation Date
				if (fileASchedule.getCarrier().getCreationDate().equals(fileBSchedule.getCarrier().getCreationDate()))
					textForComparisonFile += "\n\t Creation Date \t\t\tOk";
				else
				{
					textForComparisonFile += "\n\t Creation Date \t\t\t*** A = "+fileASchedule.getCarrier().getCreationDate();
					textForComparisonFile += ", B = "+fileBSchedule.getCarrier().getCreationDate()+" ***";
				}

				// Title of Data
				if (fileASchedule.getCarrier().getTitleOfData().equals(fileBSchedule.getCarrier().getTitleOfData()))
					textForComparisonFile += "\n\t Title of Data \t\t\tOk";
				else
				{
					textForComparisonFile += "\n\t Title of Data \t\t\t*** A = "+fileASchedule.getCarrier().getTitleOfData();
					textForComparisonFile += ", B = "+fileBSchedule.getCarrier().getTitleOfData()+" ***";
				}

				// Release Date
				if (fileASchedule.getCarrier().getReleaseDate().equals(fileBSchedule.getCarrier().getReleaseDate()))
					textForComparisonFile += "\n\t Release Date \t\t\tOk";
				else
				{
					textForComparisonFile += "\n\t Release Date \t\t\t*** A = "+fileASchedule.getCarrier().getReleaseDate();
					textForComparisonFile += ", B = "+fileBSchedule.getCarrier().getReleaseDate()+" ***";
				}

				// Record Serial Number
				if (fileASchedule.getCarrier().getRecordSerialNumber().equals(fileBSchedule.getCarrier().getRecordSerialNumber()))
					textForComparisonFile += "\n\t Record Serial Number \t\tOk";
				else
				{
					textForComparisonFile += "\n\t Record Serial Number \t\t*** A = "+fileASchedule.getCarrier().getRecordSerialNumber();
					textForComparisonFile += ", B = "+fileBSchedule.getCarrier().getRecordSerialNumber()+" ***";
				}
				
				comparisonCount += 8;
			}
			else
				textForComparisonFile += "\nType 2 records not compared";

			// Type 3 and 4
			if ((checkType3) && (!checkType4))
			{
				textForComparisonFile += "\nType 3 (but not Type 4) record comparison:";

				ArrayList<RadixxFlightLegInputIATA> fileALegs = fileASchedule.getFlightLegs();
				ArrayList<RadixxFlightLegInputIATA> fileBLegs = fileBSchedule.getFlightLegs();

				ListIterator<RadixxFlightLegInputIATA> fileALegsIterator = fileALegs.listIterator();
				ListIterator<RadixxFlightLegInputIATA> fileBLegsIterator = fileBLegs.listIterator();

				while(fileALegsIterator.hasNext())  // For each Flight Leg in A
				{
					RadixxFlightLegInputIATA thisFileALeg= fileALegsIterator.next();

					fileBLegsIterator = fileBLegs.listIterator();
					
					comparisonCount += 23;

					inner:
						while(fileBLegsIterator.hasNext())  // For each Flight Leg in B
						{
							RadixxFlightLegInputIATA thisFileBLeg= fileBLegsIterator.next();

							if ((thisFileALeg.getAirlineDesignator().equals(thisFileBLeg.getAirlineDesignator())) &&
									(thisFileALeg.getFlightNumber().equals(thisFileBLeg.getFlightNumber())) &&
									(thisFileALeg.getItineraryVariationIdentifier().equals(thisFileBLeg.getItineraryVariationIdentifier())) &&
									(thisFileALeg.getLegSequenceNumber().equals(thisFileBLeg.getLegSequenceNumber())) &&
									(thisFileALeg.getServiceType().equals(thisFileBLeg.getServiceType())) &&
									(thisFileALeg.getPeriodOfOperation().equals(thisFileBLeg.getPeriodOfOperation())) &&
									(thisFileALeg.getDayOfOperation().equals(thisFileBLeg.getDayOfOperation())) &&

									(thisFileALeg.getDepartureStation().equals(thisFileBLeg.getDepartureStation())) &&
									(thisFileALeg.getPassengerSTD().equals(thisFileBLeg.getPassengerSTD())) &&
									(thisFileALeg.getAircraftSTD().equals(thisFileBLeg.getAircraftSTD())) &&
									(thisFileALeg.getTimeVariationDeparture().equals(thisFileBLeg.getTimeVariationDeparture())) &&
									(thisFileALeg.getDepartureTerminal().equals(thisFileBLeg.getDepartureTerminal())) &&

									(thisFileALeg.getArrivalStation().equals(thisFileBLeg.getArrivalStation())) &&
									(thisFileALeg.getPassengerSTA().equals(thisFileBLeg.getPassengerSTA())) &&
									(thisFileALeg.getAircraftSTA().equals(thisFileBLeg.getAircraftSTA())) &&
									(thisFileALeg.getTimeVariationArrival().equals(thisFileBLeg.getTimeVariationArrival())) &&
									(thisFileALeg.getArrivalTerminal().equals(thisFileBLeg.getArrivalTerminal())) &&

									(thisFileALeg.getAircraftType().equals(thisFileBLeg.getAircraftType())) &&
									(thisFileALeg.getOnwardAirlineDesignator().equals(thisFileBLeg.getOnwardAirlineDesignator())) &&
									(thisFileALeg.getOnwardFlightNumber().equals(thisFileBLeg.getOnwardFlightNumber())) &&
									(thisFileALeg.getOnwardFlightTransitLayover().equals(thisFileBLeg.getOnwardFlightTransitLayover())) &&
									(thisFileALeg.getAircraftConfiguration().equals(thisFileBLeg.getAircraftConfiguration())) &&
									(thisFileALeg.getDateVariation().equals(thisFileBLeg.getDateVariation())))
							{
								// Remove from A and B
								fileALegsIterator.remove();
								fileBLegsIterator.remove();
								break inner;
							}
						}	
				}

				int foundDiscrepancies = 0;
				// For each remaining Flight Leg in A
				fileALegsIterator = fileALegs.listIterator();
				while(fileALegsIterator.hasNext())  
				{
					foundDiscrepancies++;
					RadixxFlightLegInputIATA remainingFileALeg= fileALegsIterator.next();
					textForComparisonFile += "\n\t\t\t\t\t*** Schedule elements for record serial number "+remainingFileALeg.getRecordSerialNumber()+" occur in A but not B ***";
				}

				// For each remaining Flight Leg in B
				fileBLegsIterator = fileBLegs.listIterator();
				while(fileBLegsIterator.hasNext())  
				{
					foundDiscrepancies++;
					RadixxFlightLegInputIATA remainingFileBLeg= fileBLegsIterator.next();
					textForComparisonFile += "\n\t\t\t\t\t*** Schedule elements for record serial number "+remainingFileBLeg.getRecordSerialNumber()+" occur in B but not A ***";
				}

				if (foundDiscrepancies == 0) 
				{
					textForComparisonFile += "\n\t Schedules \t\t\tOk";
				}
			}
			else if ((checkType3) && (checkType4))
			{
				textForComparisonFile += "\nType 3 and Type 4 record comparison:";

				ArrayList<RadixxFlightLegInputIATA> fileALegs = fileASchedule.getFlightLegs();
				ArrayList<RadixxFlightLegInputIATA> fileBLegs = fileBSchedule.getFlightLegs();

				ListIterator<RadixxFlightLegInputIATA> fileALegsIterator = fileALegs.listIterator();
				ListIterator<RadixxFlightLegInputIATA> fileBLegsIterator = fileBLegs.listIterator();

				while(fileALegsIterator.hasNext())  // For each Flight Leg in A
				{
					RadixxFlightLegInputIATA thisFileALeg= fileALegsIterator.next();

					fileBLegsIterator = fileBLegs.listIterator();
					
					comparisonCount += 35;

					inner:
						while(fileBLegsIterator.hasNext())  // For each Flight Leg in B
						{
							RadixxFlightLegInputIATA thisFileBLeg= fileBLegsIterator.next();
							
							// Type 3
							if ((thisFileALeg.getAirlineDesignator().equals(thisFileBLeg.getAirlineDesignator())) &&
									(thisFileALeg.getFlightNumber().equals(thisFileBLeg.getFlightNumber())) &&
									(thisFileALeg.getItineraryVariationIdentifier().equals(thisFileBLeg.getItineraryVariationIdentifier())) &&
									(thisFileALeg.getLegSequenceNumber().equals(thisFileBLeg.getLegSequenceNumber())) &&
									(thisFileALeg.getServiceType().equals(thisFileBLeg.getServiceType())) &&
									(thisFileALeg.getPeriodOfOperation().equals(thisFileBLeg.getPeriodOfOperation())) &&
									(thisFileALeg.getDayOfOperation().equals(thisFileBLeg.getDayOfOperation())) &&

									(thisFileALeg.getDepartureStation().equals(thisFileBLeg.getDepartureStation())) &&
									(thisFileALeg.getPassengerSTD().equals(thisFileBLeg.getPassengerSTD())) &&
									(thisFileALeg.getAircraftSTD().equals(thisFileBLeg.getAircraftSTD())) &&
									(thisFileALeg.getTimeVariationDeparture().equals(thisFileBLeg.getTimeVariationDeparture())) &&
									(thisFileALeg.getDepartureTerminal().equals(thisFileBLeg.getDepartureTerminal())) &&

									(thisFileALeg.getArrivalStation().equals(thisFileBLeg.getArrivalStation())) &&
									(thisFileALeg.getPassengerSTA().equals(thisFileBLeg.getPassengerSTA())) &&
									(thisFileALeg.getAircraftSTA().equals(thisFileBLeg.getAircraftSTA())) &&
									(thisFileALeg.getTimeVariationArrival().equals(thisFileBLeg.getTimeVariationArrival())) &&
									(thisFileALeg.getArrivalTerminal().equals(thisFileBLeg.getArrivalTerminal())) &&

									(thisFileALeg.getAircraftType().equals(thisFileBLeg.getAircraftType())) &&
									(thisFileALeg.getOnwardAirlineDesignator().equals(thisFileBLeg.getOnwardAirlineDesignator())) &&
									(thisFileALeg.getOnwardFlightNumber().equals(thisFileBLeg.getOnwardFlightNumber())) &&
									(thisFileALeg.getOnwardFlightTransitLayover().equals(thisFileBLeg.getOnwardFlightTransitLayover())) &&
									(thisFileALeg.getAircraftConfiguration().equals(thisFileBLeg.getAircraftConfiguration())) &&
									(thisFileALeg.getDateVariation().equals(thisFileBLeg.getDateVariation())) &&

									// Type 4 (this code would need to be updated if more than one type 4 record could be assigned to a type 3 record)
									(thisFileALeg.getSegmentDataRecords().get(0).getAirlineDesignator().equals(thisFileBLeg.getSegmentDataRecords().get(0).getAirlineDesignator())) &&
									(thisFileALeg.getSegmentDataRecords().get(0).getFlightNumber().equals(thisFileBLeg.getSegmentDataRecords().get(0).getFlightNumber())) &&
									(thisFileALeg.getSegmentDataRecords().get(0).getItineraryVariationNumber().equals(thisFileBLeg.getSegmentDataRecords().get(0).getItineraryVariationNumber())) &&
									(thisFileALeg.getSegmentDataRecords().get(0).getLegSequenceNumber().equals(thisFileBLeg.getSegmentDataRecords().get(0).getLegSequenceNumber())) &&
									(thisFileALeg.getSegmentDataRecords().get(0).getServiceType().equals(thisFileBLeg.getSegmentDataRecords().get(0).getServiceType())) &&
									(thisFileALeg.getSegmentDataRecords().get(0).getBoardPointIndicator().equals(thisFileBLeg.getSegmentDataRecords().get(0).getBoardPointIndicator())) &&
									(thisFileALeg.getSegmentDataRecords().get(0).getOffPointIndicator().equals(thisFileBLeg.getSegmentDataRecords().get(0).getOffPointIndicator())) &&
									(thisFileALeg.getSegmentDataRecords().get(0).getDataElementIdentifier().equals(thisFileBLeg.getSegmentDataRecords().get(0).getDataElementIdentifier())) &&
									(thisFileALeg.getSegmentDataRecords().get(0).getSegmentBoardPoint().equals(thisFileBLeg.getSegmentDataRecords().get(0).getSegmentBoardPoint())) &&
									(thisFileALeg.getSegmentDataRecords().get(0).getSegmentOffPoint().equals(thisFileBLeg.getSegmentDataRecords().get(0).getSegmentOffPoint())) &&
									(thisFileALeg.getSegmentDataRecords().get(0).getData().equals(thisFileBLeg.getSegmentDataRecords().get(0).getData())) &&
									(thisFileALeg.getSegmentDataRecords().get(0).getRecordSerialNumber().equals(thisFileBLeg.getSegmentDataRecords().get(0).getRecordSerialNumber()))
									) 
							{
								// Remove from A and B
								fileALegsIterator.remove();
								fileBLegsIterator.remove();
								break inner;
							}
						}	
				}

				int foundDiscrepancies = 0;
				// For each remaining Flight Leg in A
				fileALegsIterator = fileALegs.listIterator();
				while(fileALegsIterator.hasNext())  
				{
					foundDiscrepancies++;
					RadixxFlightLegInputIATA remainingFileALeg= fileALegsIterator.next();
					textForComparisonFile += "\n\t\t\t\t\t*** Schedule elements for record serial number "+remainingFileALeg.getRecordSerialNumber()+" (or the subsequent type 4 row, if applicable) occur in A but not B ***";
				}

				// For each remaining Flight Leg in B
				fileBLegsIterator = fileBLegs.listIterator();
				while(fileBLegsIterator.hasNext())  
				{
					foundDiscrepancies++;
					RadixxFlightLegInputIATA remainingFileBLeg= fileBLegsIterator.next();
					textForComparisonFile += "\n\t\t\t\t\t*** Schedule elements for record serial number "+remainingFileBLeg.getRecordSerialNumber()+" (or the subsequent type 4 row, if applicable) occur in B but not A ***";		
				}

				if (foundDiscrepancies == 0) 
				{
					textForComparisonFile += "\n\t Schedules \t\t\tOk";
				}
			}
			else
				textForComparisonFile += "\nType 3 and Type 4 records not compared";

			// Type 5
			if (checkType5)
			{
				textForComparisonFile += "\nType 5 record comparison:";

				// Airline Designator
				if (fileASchedule.getTrailer().getAirlineDesignator().equals(fileBSchedule.getTrailer().getAirlineDesignator()))
					textForComparisonFile += "\n\t Airline (Railroad) Designator \tOk";
				else
				{
					textForComparisonFile += "\n\t Airline (Railroad) Designator \t*** A = "+fileASchedule.getTrailer().getAirlineDesignator();
					textForComparisonFile += ", B = "+fileBSchedule.getTrailer().getAirlineDesignator()+" ***";
				}

				// Release Date
				if (fileASchedule.getTrailer().getReleaseDate().equals(fileBSchedule.getTrailer().getReleaseDate()))
					textForComparisonFile += "\n\t Release Date \t\t\tOk";
				else
				{
					textForComparisonFile += "\n\t Release Date \t\t\t*** A = "+fileASchedule.getTrailer().getReleaseDate();
					textForComparisonFile += ", B = "+fileBSchedule.getTrailer().getReleaseDate()+" ***";
				}

				// Continuation End Code
				if (fileASchedule.getTrailer().getContinuationEndCode().equals(fileBSchedule.getTrailer().getContinuationEndCode()))
					textForComparisonFile += "\n\t Continuation End Code \t\tOk";
				else
				{
					textForComparisonFile += "\n\t Continuation End Code \t\t*** A = "+fileASchedule.getTrailer().getContinuationEndCode();
					textForComparisonFile += ", B = "+fileBSchedule.getTrailer().getContinuationEndCode()+" ***";
				}

				// Record Serial Number
				if (fileASchedule.getTrailer().getRecordSerialNumber().equals(fileBSchedule.getTrailer().getRecordSerialNumber()))
					textForComparisonFile += "\n\t Record Serial Number \t\tOk";
				else
				{
					textForComparisonFile += "\n\t Record Serial Number \t\t*** A = "+fileASchedule.getTrailer().getRecordSerialNumber();
					textForComparisonFile += ", B = "+fileBSchedule.getTrailer().getRecordSerialNumber()+" ***";
				}

				// Serial Number Check Reference
				if (fileASchedule.getTrailer().getSerialNumberCheckReference().equals(fileBSchedule.getTrailer().getSerialNumberCheckReference()))
					textForComparisonFile += "\n\t Serial Number Check Reference \tOk";
				else
				{
					textForComparisonFile += "\n\t Serial Number Check Reference \t*** A = "+fileASchedule.getTrailer().getSerialNumberCheckReference();
					textForComparisonFile += ", B = "+fileBSchedule.getTrailer().getSerialNumberCheckReference()+" ***";
				}	
				
				comparisonCount += 5;
			}
			else
				textForComparisonFile += "\nType 5 records not compared";
		}

		return error;
	}

	public String getResultsMessage()
	{
		message = "Compared "+comparisonCount+" data elements in Radixx SSIM files";
	
		return message;
	}

	public String getTextForComparisonFile()
	{
		return textForComparisonFile;
	}
}