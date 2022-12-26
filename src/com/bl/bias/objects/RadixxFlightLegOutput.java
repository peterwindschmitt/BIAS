package com.bl.bias.objects;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.app.BIASRadixxResSsimConversionConfigPageController;
import com.bl.bias.exception.ErrorShutdown;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RadixxFlightLegOutput
{
	private String defaultFlightLeg;
	private String defaultEndOfType3and4;
	private String flightLeg = "";
	private String flightLegs = "";

	private String defaultSegmentData;

	private Boolean validInput = true;

	private Integer excelRowCounter = 16;
	private Integer recordSerialNumber = 3;
	private Integer highestRecordSerialNumber = 3;

	private static Integer objectsReadCount = 0;

	private static String recordSerialNumberForTrailerRecord;

	private static HashSet<String> usedFlightItinLeg = new HashSet<String>();
	
	private static List<RadixxOverlappingFlightOriginDestinationByDate> flightAndODrecords = new ArrayList<RadixxOverlappingFlightOriginDestinationByDate>();

	final String pattern_1alpha = "^[A-Z]$";  								// Regex format for 1 alpha (as in service type)
	final String pattern_3alpha = "^[A-Z]{3}$";   		    				// Regex format for 3 letters (as in departure station)
	final String pattern_1to2Digits= "^[0-9]{1,2}$";   						// Regex format for 1-2 digits (as in itinerary variation number)
	final String pattern_1to3Digits= "^[0-9]{1,3}$";   						// Regex format for 1-3 digits (data element identifier)
	final String pattern_1to4Digits= "^[0-9]{1,4}$";   						// Regex format for 1-4 digits (as in flight number)
	final String pattern_hhmm= "^[0-2][0-9][0-5][0-9]$";   					// Regex format for hhmm (as in passenger STD)
	final String pattern_offsetHhmm= "^[-]{0,1}[0-2][0-9][0-5][0-9]$";   	// Regex format for -hhmm (as in time offset)
	final String pattern_1to2chars	= "^[A-Z0-9]{1,2}$";     				// Regex format for 1-2 alphanumeric (as in departure terminal)
	final String pattern_1to3alphanumeric = "^[A-Z0-9]{1,3}$";   			// Regex format for 1-3 letters or digits (alphanumeric) (as in airline designator)
	final String pattern_3chars = "^[A-Z0-9]{3}$";   		    			// Regex format for 3 alphanumeric (as in aircraft type)
	final String pattern_1char = "^[A-Z0-9]$";  							// Regex format for 1 character (as in flight transit layover)
	final String pattern_1to20chars = "^[A-Z0-9]{1,20}$";  				    // Regex format for 1 - 20 any character (as in aircraft config)
	final String pattern_1to155chars = "^[A-Z0-9\\s]{1,155}$";     			// Regex format for 1-155 any character (except new line) (as in segment data)
	final String pattern_dateVariation = "^[012A]$";						// Regex format for 1 zero, one, two or A (as in date variation)
	final String pattern_ddmmmyy = "^[0-9]{2}[A-Z]{3}[0-9]{2}$";        	// Regex format for ddmmmyy

	final DateTimeFormatter formatter_ddMMMyy = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("ddMMMyy").toFormatter();

	public RadixxFlightLegOutput(String file, XSSFWorkbook wb, XSSFSheet sheet) throws Exception
	{
		usedFlightItinLeg.clear();
		flightAndODrecords.clear();

		LocalDate periodOfValidityStartDate = LocalDate.parse(RadixxCarrierOutput.getPeriodOfValidityStart(), formatter_ddMMMyy);
		LocalDate periodOfValidityEndDate = LocalDate.parse(RadixxCarrierOutput.getPeriodOfValidityEnd(), formatter_ddMMMyy);
		long numOfDaysInCarrierPeriodOfValidity = ChronoUnit.DAYS.between(periodOfValidityStartDate, periodOfValidityEndDate) + 1;

		try
		{
			// Flight Leg Record
			String airlineDesignator;
			String flightNumber;
			String itineraryVariationIdentifier;
			String legSequenceNumber;
			String serviceType;
			String periodOfOperationStart;
			String periodOfOperationEnd;
			String daysOfOperation;
			String departureStation;
			String passengerSTD;
			String aircraftSTD;
			String timeVariationDeparture;
			String departureTerminal;
			String arrivalStation;
			String aircraftSTA;
			String passengerSTA;
			String timeVariationArrival;
			String arrivalTerminal;
			String aircraftType;
			String onwardAirlineDesignator;
			String onwardFlightNumber;
			String onwardFlightTransitLayover;
			String aircraftConfiguration;
			String dateVariationDeparture;
			String dateVariationArrival;

			// Segment Data Record
			String boardPointIndicator;
			String offPointIndicator;
			String dataElementIdentifier;
			String segmentBoardPoint;
			String segmentOffPoint;
			String data;

			defaultFlightLeg = 
					"3 ********************************* ***************************************                                                              *******   *                        ****************************";
			defaultSegmentData = 
					"4 ************              ****************************************************************************************************************************************************************************";
			defaultEndOfType3and4 = 
					"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n"+
							"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n"+
							"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n"+
							"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

			//  Iterate through rows of spreadsheet
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();

				if (row.getRowNum() > 15)
				{
					if (row.getCell(0).toString().contains("*****"))
					{
						break;
					}

					// Flight Leg Record
					airlineDesignator = row.getCell(0).toString().trim().toUpperCase();
					flightNumber = row.getCell(1).toString().trim();
					itineraryVariationIdentifier = row.getCell(2).toString().trim();
					legSequenceNumber = row.getCell(3).toString().trim();
					serviceType = row.getCell(4).toString().trim().toUpperCase();
					periodOfOperationStart = row.getCell(5).toString().trim().toUpperCase();
					periodOfOperationEnd = row.getCell(6).toString().trim().toUpperCase();
					daysOfOperation = row.getCell(7).toString().trim().toUpperCase();
					departureStation = row.getCell(8).toString().trim().toUpperCase();
					passengerSTD = row.getCell(9).toString().trim();
					aircraftSTD = row.getCell(10).toString().trim();
					timeVariationDeparture = row.getCell(11).toString().trim();
					departureTerminal = row.getCell(12).toString().trim().toUpperCase();
					arrivalStation = row.getCell(13).toString().trim().toUpperCase();
					aircraftSTA = row.getCell(14).toString().trim();
					passengerSTA = row.getCell(15).toString().trim();
					timeVariationArrival = row.getCell(16).toString().trim();
					arrivalTerminal = row.getCell(17).toString().trim().toUpperCase();
					aircraftType = row.getCell(18).toString().trim().toUpperCase();
					onwardAirlineDesignator = row.getCell(19).toString().trim().toUpperCase();
					onwardFlightNumber = row.getCell(20).toString().trim();
					onwardFlightTransitLayover = row.getCell(21).toString().trim().toUpperCase();
					aircraftConfiguration = row.getCell(22).toString().trim().toUpperCase();
					dateVariationDeparture = row.getCell(23).toString().trim().toUpperCase();
					dateVariationArrival = row.getCell(24).toString().trim().toUpperCase();

					// Segment Data Record
					boardPointIndicator = row.getCell(25).toString().trim().toUpperCase();
					offPointIndicator = row.getCell(26).toString().trim().toUpperCase();
					dataElementIdentifier = row.getCell(27).toString().trim().toUpperCase();
					segmentBoardPoint = row.getCell(28).toString().trim().toUpperCase();
					segmentOffPoint = row.getCell(29).toString().trim().toUpperCase();
					data = row.getCell(30).toString().trim().toUpperCase();

					if (replaceData(defaultFlightLeg, airlineDesignator, flightNumber, itineraryVariationIdentifier, legSequenceNumber, serviceType, periodOfOperationStart, 
							periodOfOperationEnd, daysOfOperation, departureStation, passengerSTD, aircraftSTD, timeVariationDeparture, departureTerminal, arrivalStation, aircraftSTA, passengerSTA, 
							timeVariationArrival, arrivalTerminal, aircraftType, onwardAirlineDesignator, onwardFlightNumber, onwardFlightTransitLayover, aircraftConfiguration, dateVariationDeparture, dateVariationArrival,
							boardPointIndicator, offPointIndicator, dataElementIdentifier, segmentBoardPoint, segmentOffPoint, data))
					{
						// Check #1:  Make sure that flightNumber, itineraryVariationIdentifier and legSequenceNumber is unique
						// This ensures that, without reference to date, there are no duplicate entries for the a given flight number with a given 
						// itinerary identifier and a given leg sequence number. 
						String proposedFlightItinLeg = flightNumber.concat(itineraryVariationIdentifier).concat(legSequenceNumber);
						if (usedFlightItinLeg.contains(proposedFlightItinLeg))
						{
							validInput = false;

							Platform.runLater(new Runnable()
							{
								@Override
								public void run() 
								{
									Alert alert = new Alert(AlertType.ERROR);
									alert.setTitle("Error");
									alert.setHeaderText(null);
									alert.setContentText("Duplicate flight number, itinerary variation identifier and leg sequence number encountered at spreadsheet row "+(excelRowCounter+1)+".");	
									alert.showAndWait();
								}
							});
							break;
						}
						else
						{
							usedFlightItinLeg.add(proposedFlightItinLeg);
						}

						// Check #2:  Check that Passenger and Train STAs are the same.  Same for Passenger and Train STDs.
						if (BIASRadixxResSsimConversionConfigPageController.getCheckStasEqual())
						{
							if (!passengerSTA.equals(aircraftSTA))
							{
								validInput = false;

								Platform.runLater(new Runnable()
								{
									@Override
									public void run() 
									{
										Alert alert = new Alert(AlertType.ERROR);
										alert.setTitle("Error");
										alert.setHeaderText(null);
										alert.setContentText("Passenger STA is not equal to Train STA at spreadsheet row "+(excelRowCounter+1)+".");	
										alert.showAndWait();
									}
								});
								break;
							}

							if (!passengerSTD.equals(aircraftSTD))
							{
								validInput = false;

								Platform.runLater(new Runnable()
								{
									@Override
									public void run() 
									{
										Alert alert = new Alert(AlertType.ERROR);
										alert.setTitle("Error");
										alert.setHeaderText(null);
										alert.setContentText("Passenger STD is not equal to Train STD at spreadsheet row "+(excelRowCounter+1)+".");	
										alert.showAndWait();
									}
								});
								break;
							}
						}

						// Check #3:  Check that Passenger and Train STDs are after their corresponding STAs
						if (BIASRadixxResSsimConversionConfigPageController.getCheckStasAtSameTimeOrLaterThanStds())
						{
							SimpleDateFormat sdformat = new SimpleDateFormat("HHmm");
							
							// Passenger
							Date passengerDepartureAsDate = sdformat.parse(passengerSTD);
							Calendar passengerDepartureAsCalendar = Calendar.getInstance();
							passengerDepartureAsCalendar.setTime(passengerDepartureAsDate);
							// Adjust for day and time
							// Adjust day
							if (dateVariationDeparture != "")
								passengerDepartureAsCalendar.add(Calendar.DATE, Integer.valueOf(dateVariationDeparture));
							// Adjust time offset hour
							passengerDepartureAsCalendar.add(Calendar.HOUR, Integer.valueOf(timeVariationDeparture.substring(0, timeVariationDeparture.length() - 2)));
							// Adjust time offset minute
							if (timeVariationDeparture.contains("-"))
								passengerDepartureAsCalendar.add(Calendar.MINUTE, -1 * Integer.valueOf(timeVariationDeparture.substring(timeVariationDeparture.length() - 2)));
							else
								passengerDepartureAsCalendar.add(Calendar.MINUTE, Integer.valueOf(timeVariationDeparture.substring(timeVariationDeparture.length() - 2)));
													
							Date passengerArrivalAsDate = sdformat.parse(passengerSTA);
							Calendar passengerArrivalAsCalendar = Calendar.getInstance();
							passengerArrivalAsCalendar.setTime(passengerArrivalAsDate);
							// Adjust for day and time
							// Adjust day
							if (dateVariationArrival != "")
								passengerArrivalAsCalendar.add(Calendar.DATE, Integer.valueOf(dateVariationArrival));
							// Adjust time offset hour
							passengerArrivalAsCalendar.add(Calendar.HOUR, Integer.valueOf(timeVariationArrival.substring(0, timeVariationArrival.length() - 2)));
							// Adjust time offset minute
							if (timeVariationArrival.contains("-"))
								passengerArrivalAsCalendar.add(Calendar.MINUTE, -1 * Integer.valueOf(timeVariationArrival.substring(timeVariationArrival.length() - 2)));
							else
								passengerArrivalAsCalendar.add(Calendar.MINUTE, Integer.valueOf(timeVariationArrival.substring(timeVariationArrival.length() - 2)));
							
							if (passengerDepartureAsCalendar.after(passengerArrivalAsCalendar))
							{
								validInput = false;

								Platform.runLater(new Runnable()
								{
									@Override
									public void run() 
									{
										Alert alert = new Alert(AlertType.ERROR);
										alert.setTitle("Error");
										alert.setHeaderText(null);
										alert.setContentText("Passenger STD is after Passenger STA at spreadsheet row "+(excelRowCounter+1)+".");	
										alert.showAndWait();
									}
								});
								break;
							}
														
							// Aircraft
							Date aircraftDepartureAsDate = sdformat.parse(aircraftSTD);
							Calendar aircraftDepartureAsCalendar = Calendar.getInstance();
							aircraftDepartureAsCalendar.setTime(aircraftDepartureAsDate);
							// Adjust for day and time
							// Adjust day
							if (dateVariationDeparture != "")
								aircraftDepartureAsCalendar.add(Calendar.DATE, Integer.valueOf(dateVariationDeparture));
							// Adjust time offset hour
							aircraftDepartureAsCalendar.add(Calendar.HOUR, Integer.valueOf(timeVariationDeparture.substring(0, timeVariationDeparture.length() - 2)));
							// Adjust time offset minute
							if (timeVariationDeparture.contains("-"))
								aircraftDepartureAsCalendar.add(Calendar.MINUTE, -1 * Integer.valueOf(timeVariationDeparture.substring(timeVariationDeparture.length() - 2)));
							else
								aircraftDepartureAsCalendar.add(Calendar.MINUTE, Integer.valueOf(timeVariationDeparture.substring(timeVariationDeparture.length() - 2)));
													
							Date aircraftArrivalAsDate = sdformat.parse(aircraftSTA);
							Calendar aircraftArrivalAsCalendar = Calendar.getInstance();
							aircraftArrivalAsCalendar.setTime(aircraftArrivalAsDate);
							// Adjust for day and time
							// Adjust day
							if (dateVariationArrival != "")
								aircraftArrivalAsCalendar.add(Calendar.DATE, Integer.valueOf(dateVariationArrival));
							// Adjust time offset hour
							aircraftArrivalAsCalendar.add(Calendar.HOUR, Integer.valueOf(timeVariationArrival.substring(0, timeVariationArrival.length() - 2)));
							// Adjust time offset minute
							if (timeVariationArrival.contains("-"))
								aircraftArrivalAsCalendar.add(Calendar.MINUTE, -1 * Integer.valueOf(timeVariationArrival.substring(timeVariationArrival.length() - 2)));
							else
								aircraftArrivalAsCalendar.add(Calendar.MINUTE, Integer.valueOf(timeVariationArrival.substring(timeVariationArrival.length() - 2)));
							
							if (aircraftDepartureAsCalendar.after(aircraftArrivalAsCalendar))
							{
								validInput = false;

								Platform.runLater(new Runnable()
								{
									@Override
									public void run() 
									{
										Alert alert = new Alert(AlertType.ERROR);
										alert.setTitle("Error");
										alert.setHeaderText(null);
										alert.setContentText("Aircraft STD is after Aircraft STA at spreadsheet row "+(excelRowCounter+1)+".");	
										alert.showAndWait();
									}
								});
								break;
							}
						}

						// Check #4:  Check that periodOfOperationEnd is at least equal to periodOfOperationStart
						SimpleDateFormat sdformat = new SimpleDateFormat("ddMMMyy");
						Date d1 = sdformat.parse(periodOfOperationStart);
						Date d2 = sdformat.parse(periodOfOperationEnd);

						if(d1.compareTo(d2) > 0)
						{
							validInput = false;

							Platform.runLater(new Runnable()
							{
								@Override
								public void run() 
								{
									Alert alert = new Alert(AlertType.ERROR);
									alert.setTitle("Error");
									alert.setHeaderText(null);
									alert.setContentText("Period of operation start date is after end date in spreadsheet row "+(excelRowCounter+1)+".");	
									alert.showAndWait();
								}
							});
							break;
						} 
						
						// Check #5:  Check that origin and destination locations are valid
						HashSet<String> validLocationCodes = new HashSet<String>(); 
						for (int i = 0; i < BIASRadixxResSsimConversionConfigPageController.getPermittedLocationCodes().split(",").length; i++)
						{
							validLocationCodes.add(BIASRadixxResSsimConversionConfigPageController.getPermittedLocationCodes().split(",")[i]);
						}
						
						if ((!validLocationCodes.contains(departureStation)) || (!validLocationCodes.contains(arrivalStation)))   
						{
							validInput = false;

							Platform.runLater(new Runnable()
							{
								@Override
								public void run() 
								{
									Alert alert = new Alert(AlertType.ERROR);
									alert.setTitle("Error");
									alert.setHeaderText(null);
									alert.setContentText("Invalid location found in spreadsheet row "+(excelRowCounter+1)+".");	
									alert.showAndWait();
								}
							});
							break;
						}

						if (validInput)
						{
							// Create a RadixxOverlappingFlightOriginDestinationByDate object -- one for each date that the flight operates
							LocalDate periodOfOperationStartDate = LocalDate.parse(periodOfOperationStart, formatter_ddMMMyy);
							LocalDate periodOfOperationEndDate = LocalDate.parse(periodOfOperationEnd, formatter_ddMMMyy);
							long numOfDaysInFlightRecordPeriodOfValidity = ChronoUnit.DAYS.between(periodOfOperationStartDate, periodOfOperationEndDate) + 1;

							for (int i = 0; i < numOfDaysInFlightRecordPeriodOfValidity; i++)
							{
								RadixxOverlappingFlightOriginDestinationByDate record = new RadixxOverlappingFlightOriginDestinationByDate(periodOfOperationStartDate.plusDays(i), flightNumber, departureStation, arrivalStation, daysOfOperation);
								flightAndODrecords.add(record);
							}

							// For spreadsheet
							excelRowCounter++;
							recordSerialNumber++;
						}
					}
					else
					{
						validInput = false;

						Platform.runLater(new Runnable()
						{
							@Override
							public void run() 
							{
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Error");
								alert.setHeaderText(null);
								alert.setContentText("Fight record/segment data cannot be properly created due to data in spreadsheet row "+(excelRowCounter+1)+".");	
								alert.showAndWait();
							}
						});
						break;

					}
				}
			}     

			flightLegs = flightLegs.concat(defaultEndOfType3and4);
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}	

		// Check #6:  Make sure that a given O-D pair exists no more than once for each flightNumber for each date for each day of operation
		// during the periodOfOperationStart and periodOfOperationEnd
		// This check is done without reference to itineraryVariationIdentifier or legSequenceNumber
		if (validInput)
		{
			// 1.  Loop through all dates between start and end of period (inclusive)
			for (int i = 0; i < numOfDaysInCarrierPeriodOfValidity; i++)
			{
				// 2.  Outer loop through all flightAndODrecords (j)
				for (int j = 0; j < flightAndODrecords.size(); j++)
				{
					if (periodOfValidityStartDate.plusDays(i).equals(flightAndODrecords.get(j).getDateOfOperationForThisFlightAndOrigindestination()))
					{
						// 3.  Inner loop through all flightAndODrecords (k)
						for (int k = 0; k < flightAndODrecords.size(); k++)
						{

							if (k <= j)
								continue;
							else if ((j != k) &&  (flightAndODrecords.get(j).getDateOfOperationForThisFlightAndOrigindestination().equals(flightAndODrecords.get(k).getDateOfOperationForThisFlightAndOrigindestination()))
									&& (flightAndODrecords.get(j).getFlightNumber().equals(flightAndODrecords.get(k).getFlightNumber()))
									&& (flightAndODrecords.get(j).getOrigin().equals(flightAndODrecords.get(k).getOrigin()))
									&& (flightAndODrecords.get(j).getDestination().equals(flightAndODrecords.get(k).getDestination())))

							{
								ArrayList<Integer> daysOfOperationJ = new ArrayList<Integer>();
								daysOfOperationJ = stringToSetOfIntegers(flightAndODrecords.get(j).getDaysOfOperation());

								ArrayList<Integer> daysOfOperationK = new ArrayList<Integer>();
								daysOfOperationK = stringToSetOfIntegers(flightAndODrecords.get(k).getDaysOfOperation());

								daysOfOperationJ.retainAll(daysOfOperationK);

								if (daysOfOperationJ.size() > 0)
								{
									Collections.sort(daysOfOperationJ);

									validInput = false;

									String errorMessage = " OVERLAPPING TRAIN RECORDS FOUND\n"
											+ " On "+periodOfValidityStartDate.plusDays(i)
											+ " train "+flightAndODrecords.get(j).getFlightNumber()
											+ " from "+flightAndODrecords.get(j).getOrigin()
											+ " to "+flightAndODrecords.get(j).getDestination()
											+ " on day(s): "+daysOfOperationJ;

									Platform.runLater(new Runnable()
									{
										@Override
										public void run() 
										{
											Alert alert = new Alert(AlertType.ERROR);
											alert.setTitle("Error");
											alert.setHeaderText(null);
											alert.setContentText(errorMessage);	
											alert.showAndWait();
										}
									});
								}
							}
						}
					}
				}
			}
		}
	}

	private Boolean replaceData(String defaultFlightLeg, String airlineDesignator, String flightNumber, String itineraryVariationIdentifier, String legSequenceNumber, String serviceType, String periodOfOperationStart, 
			String periodOfOperationEnd, String dayOfOperation, String departureStation, String passengerSTD, String aircraftSTD, String timeVariationDeparture, String departureTerminal, String arrivalStation, String aircraftSTA, String passengerSTA, 
			String timeVariationArrival, String arrivalTerminal, String aircraftType, String onwardAirlineDesignator, String onwardFlightNumber, String onwardFlightTransitLayover, String aircraftConfiguration, String dateVariationDeparture, String dateVariationArrival,
			String boardPointIndicator, String offPointIndicator, String dataElementIdentifier, String segmentBoardPoint, String segmentOffPoint, String data)
	{
		//  Create a leg
		//  Create StringBuffer object for all text changes
		StringBuffer newString = new StringBuffer(defaultFlightLeg);

		// Airline designator - left-justified
		Pattern r = Pattern.compile(pattern_1to3alphanumeric);
		Matcher m = r.matcher(airlineDesignator);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_airlineDesignator()[1]), "");

			// Pad the airline designator
			int spacesToConcatenate = 3 - airlineDesignator.length();
			for (int i = 0; i <spacesToConcatenate; i++)
				airlineDesignator = airlineDesignator.concat(" ");

			// Insert the airline designator
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_airlineDesignator()[0]), airlineDesignator);
		}
		else
			validInput = false;

		// Flight number - right-justified
		r = Pattern.compile(pattern_1to4Digits);
		m = r.matcher(flightNumber);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_flightNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_flightNumber()[1]), "");

			// Insert the flight number
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_flightNumber()[0]), String.format("%4s", flightNumber));
		}
		else
			validInput = false;

		// Itinerary variation number - right justified
		r = Pattern.compile(pattern_1to2Digits);
		m = r.matcher(itineraryVariationIdentifier);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_itineraryVariationIdentifier()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_itineraryVariationIdentifier()[1]), "");

			// Insert the itinerary variation number
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_itineraryVariationIdentifier()[0]), String.format("%2s", itineraryVariationIdentifier));
		}
		else
			validInput = false;

		// Leg sequence number - right justified
		r = Pattern.compile(pattern_1to2Digits);
		m = r.matcher(legSequenceNumber);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_legSequenceNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_legSequenceNumber()[1]), "");

			// Insert the leg sequence number
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_legSequenceNumber()[0]), String.format("%2s", legSequenceNumber));
		}
		else
			validInput = false;

		// Service type
		r = Pattern.compile(pattern_1alpha);
		m = r.matcher(serviceType);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_serviceType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_serviceType()[1]), "");

			// Insert the service type
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_serviceType()[0]), String.format(serviceType));
		}
		else
			validInput = false;

		// Period of operation start
		// FROM date
		r = Pattern.compile(pattern_ddmmmyy);
		m = r.matcher(periodOfOperationStart);

		if (m.find())
		{
			// Replace default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_periodOfOperation()[0]), (Integer.valueOf(BIASParseConfigPageController.z_getFlr_periodOfOperation()[0]) + 7), periodOfOperationStart);
		}
		else
			validInput = false;

		// TO date
		m = r.matcher(periodOfOperationEnd);

		if (m.find())
		{
			// Replace default contents after previous modification
			newString.replace((Integer.valueOf(BIASParseConfigPageController.z_getFlr_periodOfOperation()[0]) + 7), Integer.valueOf(BIASParseConfigPageController.z_getFlr_periodOfOperation()[1]), periodOfOperationEnd);
		}
		else
			validInput = false;

		// Days of operation
		// First create a hashset of days of week that a flight operates
		HashSet<Integer> daysOfOperation = new HashSet<Integer>();
		for (int i = 1; i <= dayOfOperation.toString().trim().length(); i++)
		{
			if (dayOfOperation.toString().substring(i - 1, i).equals(" "))
			{
				continue;
			}
			else if ((Integer.valueOf(dayOfOperation.toString().substring(i - 1, i)) >= 1) && (Integer.valueOf(dayOfOperation.toString().substring(i - 1, i)) <= 7)) 
			{
				daysOfOperation.add(Integer.valueOf(dayOfOperation.toString().substring(i - 1, i)));
			}
			else
			{
				validInput = false;
			}
		}

		// Iterate daysOfOperation hashset and fill in appropriate digits in newString 
		for (int i = 0; i < 7; i++)
		{
			if (daysOfOperation.contains(i + 1))
			{
				newString.replace((Integer.valueOf(BIASParseConfigPageController.z_getFlr_dayOfOperation()[0]) + i), (Integer.valueOf(BIASParseConfigPageController.z_getFlr_dayOfOperation()[0]) + i + 1), String.valueOf(i + 1));
			}
			else
			{
				newString.replace((Integer.valueOf(BIASParseConfigPageController.z_getFlr_dayOfOperation()[0]) + i), (Integer.valueOf(BIASParseConfigPageController.z_getFlr_dayOfOperation()[0]) + i + 1), " ");
			}
		}

		// Departure station
		r = Pattern.compile(pattern_3alpha);
		m = r.matcher(departureStation);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureStation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureStation()[1]), "");

			// Insert the departure station
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureStation()[0]), String.format(departureStation));
		}
		else
			validInput = false;

		// Passenger STD
		r = Pattern.compile(pattern_hhmm);
		m = r.matcher(passengerSTD);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTD()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTD()[1]), "");

			// Insert the passenger STD
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTD()[0]), String.format(passengerSTD));
		}
		else
			validInput = false;

		// Aircraft STD
		m = r.matcher(aircraftSTD);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTD()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTD()[1]), "");

			// Insert the aircraft STD
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTD()[0]), String.format(aircraftSTD));
		}
		else
			validInput = false;

		// Time variation departure
		r = Pattern.compile(pattern_offsetHhmm);
		m = r.matcher(timeVariationDeparture);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationDeparture()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationDeparture()[1]), "");

			// Insert the departure time variation
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationDeparture()[0]), String.format(timeVariationDeparture));
		}
		else
			validInput = false;

		// Departure terminal
		r = Pattern.compile(pattern_1to2chars);
		m = r.matcher(departureTerminal);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureTerminal()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureTerminal()[1]), "");

			// Pad the departure terminal
			int spacesToConcatenate = 2 - departureTerminal.length();
			for (int i = 0; i < spacesToConcatenate; i++)
				departureTerminal = departureTerminal.concat(" ");

			// Insert the departure terminal
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureTerminal()[0]), departureTerminal);
		}
		else
			validInput = false;

		// Arrival station
		r = Pattern.compile(pattern_3alpha);
		m = r.matcher(arrivalStation);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalStation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalStation()[1]), "");

			// Insert the arrival station
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalStation()[0]), String.format(arrivalStation));
		}
		else
			validInput = false;

		// Aircraft STA
		r = Pattern.compile(pattern_hhmm);
		m = r.matcher(aircraftSTA);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTA()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTA()[1]), "");

			// Insert the aircraft STA
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTA()[0]), String.format(aircraftSTA));
		}
		else
			validInput = false;

		// Passenger STA
		m = r.matcher(passengerSTA);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTA()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTA()[1]), "");

			// Insert the passenger STA
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTA()[0]), String.format(passengerSTA));
		}
		else
			validInput = false;

		// Time variation arrival
		r = Pattern.compile(pattern_offsetHhmm);
		m = r.matcher(timeVariationArrival);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationArrival()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationArrival()[1]), "");

			// Insert the arrival time variation
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationArrival()[0]), String.format(timeVariationArrival));
		}
		else
			validInput = false;

		// Arrival terminal
		r = Pattern.compile(pattern_1to2chars);
		m = r.matcher(arrivalTerminal);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalTerminal()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalTerminal()[1]), "");

			// Pad the arrival terminal
			int spacesToConcatenate = 2 - arrivalTerminal.length();
			for (int i = 0; i < spacesToConcatenate; i++)
				arrivalTerminal = arrivalTerminal.concat(" ");

			// Insert the departure terminal
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalTerminal()[0]), arrivalTerminal);
		}
		else
			validInput = false;

		// Aircraft type
		r = Pattern.compile(pattern_3chars);
		m = r.matcher(aircraftType);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftType()[1]), "");

			// Insert the aircraft type
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftType()[0]), String.format(aircraftType));
		}
		else
			validInput = false;

		// Onward airline designator - left-justified
		r = Pattern.compile(pattern_1to3alphanumeric);
		m = r.matcher(onwardAirlineDesignator);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardAirlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardAirlineDesignator()[1]), "");

			// Pad the onward airline designator
			int spacesToConcatenate = 3 - onwardAirlineDesignator.length();
			for (int i = 0; i < spacesToConcatenate; i++)
				onwardAirlineDesignator = onwardAirlineDesignator.concat(" ");

			// Insert the onward airline designator
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardAirlineDesignator()[0]), onwardAirlineDesignator);
		}
		else if (onwardAirlineDesignator.equals(""))
		{
			// Replace default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardAirlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardAirlineDesignator()[1]), String.format("%-3s", ""));
		}
		else
			validInput = false;

		// Onward flight number - right-justified
		r = Pattern.compile(pattern_1to4Digits);
		m = r.matcher(onwardFlightNumber);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightNumber()[1]), "");

			// Insert the onward flight number
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightNumber()[0]), String.format("%4s", onwardFlightNumber));
		}
		else if (onwardFlightNumber.equals(""))
		{
			// Replace default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightNumber()[1]), String.format("%-4s", ""));
		}
		else
			validInput = false;

		// Onward flight transit layover
		r = Pattern.compile(pattern_1char);
		m = r.matcher(onwardFlightTransitLayover);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightTransitLayover()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightTransitLayover()[1]), "");

			// Insert the onward flight transit layover
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightTransitLayover()[0]), String.format(onwardFlightTransitLayover));
		}
		else if (onwardFlightTransitLayover.equals(""))
		{
			// Replace default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightTransitLayover()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightTransitLayover()[1]), " ");
		}
		else
			validInput = false;

		// Aircraft configuration - left-justified
		r = Pattern.compile(pattern_1to20chars);
		m = r.matcher(aircraftConfiguration);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftConfiguration()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftConfiguration()[1]), "");

			// Pad the aircraft configuration
			int spacesToConcatenate = 20 - aircraftConfiguration.length();
			for (int i = 0; i < spacesToConcatenate; i++)
				aircraftConfiguration = aircraftConfiguration.concat(" ");

			// Insert the airline aircraft configuration
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftConfiguration()[0]), aircraftConfiguration);
		}
		else
			validInput = false;

		// Date variation departure
		r = Pattern.compile(pattern_dateVariation);
		m = r.matcher(dateVariationDeparture);
		if (m.find())
		{
			// Replace default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[0]) + 1, "");	

			// Insert the onward flight number
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[0]), dateVariationDeparture);
		}
		else if (dateVariationDeparture.equals(""))
		{
			// Replace default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[0]) + 1, " ");	

		}
		else
			validInput = false;

		// Date variation arrival
		m = r.matcher(dateVariationArrival);
		if (m.find())
		{
			// Replace default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[1]) - 1, Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[1]), "");	

			// Insert the onward flight number
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[1]) - 1, dateVariationArrival);
		}
		else if (dateVariationArrival.equals(""))
		{
			// Replace default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[1]) - 1, Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[1]), " ");	
		}
		else
			validInput = false;

		// Record serial number
		// Remove default contents after previous modification
		newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[1]), "");

		// Insert the record serial number
		newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[0]), String.format("%06d", recordSerialNumber));

		// Store for Trailer class
		if (recordSerialNumber > highestRecordSerialNumber)
			recordSerialNumberForTrailerRecord = String.format("%06d", recordSerialNumber);

		if ((validInput) && (boardPointIndicator.equals("")) && (offPointIndicator.equals("")) && (dataElementIdentifier.equals("")) && (segmentBoardPoint.equals("")) && (segmentOffPoint.equals("")) && (data.equals("")))
		{
			objectsReadCount = objectsReadCount + 28;

			//  Create String from StringBuffer for the new text
			flightLeg = newString.toString()+"\n";
			flightLegs = flightLegs.concat(flightLeg);

			return validInput;
		}
		else if ((validInput) && (!boardPointIndicator.equals("")) && (!offPointIndicator.equals("")) && (!dataElementIdentifier.equals("")) && (!segmentBoardPoint.equals("")) && (!segmentOffPoint.equals("")) && (!data.equals("")))
		{
			objectsReadCount = objectsReadCount + 34;

			// Check for pattern match on Board Point Indicator
			r = Pattern.compile(pattern_1alpha);
			m = r.matcher(boardPointIndicator);

			if (!m.find())
			{
				validInput = false;
			}

			// Check for pattern match on Off Point Indicator
			m = r.matcher(offPointIndicator);

			if (!m.find())
			{
				validInput = false;
			}

			// Check for pattern match on Data Element Identifier
			r = Pattern.compile(pattern_1to3Digits);
			m = r.matcher(dataElementIdentifier);

			if (!m.find())
			{
				validInput = false;
			}

			// Check for pattern match on Segment Board Point
			r = Pattern.compile(pattern_3alpha);
			m = r.matcher(segmentBoardPoint);

			if (!m.find())
			{
				validInput = false;
			}

			// Check for pattern match on Segment Off Point
			r = Pattern.compile(pattern_3alpha);
			m = r.matcher(segmentOffPoint);

			if (!m.find())
			{
				validInput = false;
			}

			// Check for pattern match on segment Data
			r = Pattern.compile(pattern_1to155chars);
			m = r.matcher(data);

			if (!m.find())
			{
				validInput = false;
			}

			if (validInput)
			{
				// Flight Leg record is complete
				flightLeg = newString.toString()+"\n";
				flightLegs = flightLegs.concat(flightLeg);
				recordSerialNumber++;

				// Now write segment data
				newString = new StringBuffer(defaultSegmentData);

				// Airline designator - left-justified
				// Remove default contents after previous modification
				newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getSeg_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_airlineDesignator()[1]), "");

				// Pad the airline designator
				int spacesToConcatenate = 3 - airlineDesignator.length();
				for (int i = 0; i <spacesToConcatenate; i++)
					airlineDesignator = airlineDesignator.concat(" ");

				// Insert the airline designator
				newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getSeg_airlineDesignator()[0]), airlineDesignator);

				// Flight number - right-justified
				// Remove default contents after previous modification
				newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getSeg_flightNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_flightNumber()[1]), "");

				// Insert the flight number
				newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getSeg_flightNumber()[0]), String.format("%4s", flightNumber));

				// Itinerary variation number - right justified
				// Remove default contents after previous modification
				newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getSeg_itineraryVariationNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_itineraryVariationNumber()[1]), "");

				// Insert the itinerary variation number
				newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getSeg_itineraryVariationNumber()[0]), String.format("%2s", itineraryVariationIdentifier));

				// Leg sequence number - right justified
				// Remove default contents after previous modification
				newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getSeg_legSequenceNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_legSequenceNumber()[1]), "");

				// Insert the leg sequence number
				newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getSeg_legSequenceNumber()[0]), String.format("%2s", legSequenceNumber));

				// Service type
				// Remove default contents after previous modification
				newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getSeg_serviceType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_serviceType()[1]), "");

				// Insert the service type
				newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getSeg_serviceType()[0]), String.format(serviceType));

				// Board point indicator
				// Remove default contents after previous modification
				newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getSeg_boardPointIndicator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_boardPointIndicator()[1]), "");

				// Insert the board point indicator
				newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getSeg_boardPointIndicator()[0]), String.format(boardPointIndicator));

				// Off point indicator
				// Remove default contents after previous modification
				newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getSeg_offPointIndicator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_offPointIndicator()[1]), "");

				// Insert the off point indicator
				newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getSeg_offPointIndicator()[0]), String.format(offPointIndicator));

				// Data element identifier
				// Remove default contents after previous modification
				newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getSeg_dataElementIdentifier()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_dataElementIdentifier()[1]), "");

				// Insert the data element identifier
				newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getSeg_dataElementIdentifier()[0]), String.format(dataElementIdentifier));

				// Segment board point
				// Remove default contents after previous modification
				newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentBoardPoint()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentBoardPoint()[1]), "");

				// Insert the segment board point
				newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentBoardPoint()[0]), String.format(segmentBoardPoint));

				// Segment off point
				// Remove default contents after previous modification
				newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentOffPoint()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentOffPoint()[1]), "");

				// Insert the segment off point
				newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentOffPoint()[0]), String.format(segmentOffPoint));

				// Segment data
				// Remove default contents after previous modification
				newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getSeg_data()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_data()[1]), "");

				// Pad the airline designator
				spacesToConcatenate = 155 - data.length();
				for (int i = 0; i <spacesToConcatenate; i++)
					data = data.concat(" ");

				// Insert the airline designator
				newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getSeg_data()[0]), data);

				// Record serial number
				// Remove default contents after previous modification
				newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[1]), "");

				// Insert the record serial number
				newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[0]), String.format("%06d", recordSerialNumber));

				// Store for Trailer class
				if (recordSerialNumber > highestRecordSerialNumber)
					recordSerialNumberForTrailerRecord = String.format("%06d", recordSerialNumber);

				// Combine flight leg and segment
				flightLeg = newString.toString()+"\n";
				flightLegs = flightLegs.concat(flightLeg);
			}

			return validInput;
		} 
		else
		{
			// End analysis
			validInput = false;
			return validInput;
		}
	}

	public static String getRecordSerialNumberForTrailerRecord()
	{
		return recordSerialNumberForTrailerRecord;
	}

	public static Integer getObjectCount()
	{
		return objectsReadCount;
	}

	public String getFlightLegs() 
	{
		return flightLegs;
	}

	public Boolean getValidFlightLegRecord() 
	{
		return validInput;
	}

	public void printFlightLegs()
	{
		System.out.println(flightLegs);
	}

	public static ArrayList<Integer> stringToSetOfIntegers(String s) 
	{
		ArrayList<Integer> daysOfWeek = new ArrayList<Integer>();
		for (int i = 0; i < s.length(); i++) 
		{
			if (!s.substring(i, i + 1).isBlank())
				daysOfWeek.add(Integer.valueOf(s.substring(i, i + 1)));
		}

		return daysOfWeek;
	}
}