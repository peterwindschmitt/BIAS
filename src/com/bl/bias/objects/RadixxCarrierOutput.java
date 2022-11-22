package com.bl.bias.objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.app.BIASLaunch;
import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RadixxCarrierOutput
{
	private String defaultCarrierPart1;
	private String defaultCarrierPart2;
	private String modifiedCarrierPart1;
	private String carrier;

	private static String airlineDesignatorForTrailerRecord;
	private static String releaseDateForTrailerRecord;
	private static String periodOfValidityStartForFlightRecord;
	private static String periodOfValidityEndForFlightRecord;

	protected String pattern_lOrU = "^[LU]$";  					 	 		// Regex format for an L or U (as in time mode)
	protected String pattern_1to3alphanumeric = "^[A-Z0-9]{1,3}$";   		// Regex format for 1-3 letters or digits (alphanumeric) (as in airline designator)
	protected String pattern_0to29chars	= "^[\\s\\S]{0,29}$";     			// Regex format for 0-29 characters (white space or not white space) (as in title of data)
	protected String pattern_0to35chars	= "^[\\s\\S]{0,35}$";     			// Regex format for 0-35 characters (white space of not white space) (as in creator reference)
	protected String pattern_ddmmmyy = "^[0-9]{2}[A-Z]{3}[0-9]{2}$";        // Regex format for ddmmmyy

	private Boolean validInput = true; 							

	public RadixxCarrierOutput(String file, XSSFWorkbook wb, XSSFSheet sheet) throws Exception
	{
		try
		{
			// Get data from cells
			String timeMode = sheet.getRow(7).getCell(5).toString().trim().toUpperCase();
			String airlineDesignator = sheet.getRow(8).getCell(5).toString().trim().toUpperCase();
			String creatorReference = sheet.getRow(8).getCell(1).toString().trim().toUpperCase();
			String periodOfValidtyStart = sheet.getRow(10).getCell(1).toString().trim().toUpperCase();
			String periodOfValidtyEnd = sheet.getRow(11).getCell(1).toString().trim().toUpperCase();
			String creationDate = sheet.getRow(9).getCell(5).toString().trim().toUpperCase();
			String titleOfData = sheet.getRow(7).getCell(1).toString().trim().toUpperCase();
			String releaseDate = sheet.getRow(9).getCell(1).toString().trim().toUpperCase();

			defaultCarrierPart1 = 
					"2***          *********************************************************P***********************************                                                                                   0000000002";

			defaultCarrierPart2 = 
					"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n"+
							"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n"+
							"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n"+
							"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

			if (replaceData(defaultCarrierPart1, timeMode, airlineDesignator, periodOfValidtyStart, periodOfValidtyEnd, creationDate, titleOfData, releaseDate, creatorReference))
			{
				assembleCarrier();
			}
			else
			{
				Platform.runLater(new Runnable()
				{
					@Override
					public void run() 
					{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText(null);
						alert.setContentText("Carrier record cannot be properly created.");		
						alert.showAndWait();
					}
				});
			}
		}

		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}	
	}

	private Boolean replaceData(String defaultCarrierPart1, String timeMode, String airlineDesignator, String periodOfValidityStart, String periodOfValidityEnd, String creationDate, String titleOfData, 
			String releaseDate, String creatorReference)
	{
		//  Create StringBuffer object for all text changes
		StringBuffer newString = new StringBuffer(defaultCarrierPart1);

		// Time mode
		Pattern r = Pattern.compile(pattern_lOrU);
		Matcher m = r.matcher(timeMode);
		if (m.find())
		{
			// Remove contents from default
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getCar_timeMode()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_timeMode()[1]), "");

			// Insert the time mode
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getCar_timeMode()[0]), timeMode);
		}
		else
			validInput = false;

		// Airline designator - left-justified
		r = Pattern.compile(pattern_1to3alphanumeric);
		m = r.matcher(airlineDesignator);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getCar_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_airlineDesignator()[1]), "");

			// Pad the airline designator
			for (int i = 0; i < (3 - airlineDesignator.length()); i++)
				airlineDesignator = airlineDesignator.concat(" ");

			// Store at class level for use in trailer record
			airlineDesignatorForTrailerRecord = airlineDesignator;

			// Insert the airline designator
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getCar_airlineDesignator()[0]), airlineDesignator);
		}
		else
			validInput = false;

		// Period of schedule validity
		r = Pattern.compile(pattern_ddmmmyy);

		// FROM date
		m = r.matcher(periodOfValidityStart);
		if (m.find())
		{
			// Replace default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getCar_periodOfValidity()[0]), (Integer.valueOf(BIASParseConfigPageController.z_getCar_periodOfValidity()[0]) + 6), periodOfValidityStart);

			// Store at class level for use in flight record
			periodOfValidityStartForFlightRecord = periodOfValidityStart;

		}
		else
			validInput = false;

		// TO date
		m = r.matcher(periodOfValidityEnd);
		if (m.find())
		{
			// Replace default contents after previous modification
			newString.replace((Integer.valueOf(BIASParseConfigPageController.z_getCar_periodOfValidity()[0]) + 7), Integer.valueOf(BIASParseConfigPageController.z_getCar_periodOfValidity()[1]), periodOfValidityEnd);
	
			// Store at class level for use in flight record
			periodOfValidityEndForFlightRecord = periodOfValidityEnd;
		}
		else
			validInput = false;

		// Creation date
		m = r.matcher(creationDate);
		if (m.find())
		{
			// Replace default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getCar_creationDate()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_creationDate()[1]), creationDate);
		}
		else
			validInput = false;

		// Title of data - left-justified
		r = Pattern.compile(pattern_0to29chars);
		m = r.matcher(titleOfData);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getCar_titleOfData()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_titleOfData()[1]), "");

			// Insert the title of data
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getCar_titleOfData()[0]), String.format("%-29s", titleOfData));
		}
		else
			validInput = false;

		// Release date
		r = Pattern.compile(pattern_ddmmmyy);
		m = r.matcher(releaseDate);
		if (m.find())
		{
			// Replace default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getCar_releaseDate()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_releaseDate()[1]), releaseDate);

			// Store at class level for use in trailer record
			releaseDateForTrailerRecord = releaseDate;
		}
		else if (releaseDate.equals(""))
		{
			// Replace default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getCar_releaseDate()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_releaseDate()[1]), String.format("%-7s", ""));

			// Store at class level for use in trailer record
			releaseDateForTrailerRecord = String.format("%-7s", "");
		}	
		else
			validInput = false;

		// Creator reference - left-justified
		r = Pattern.compile(pattern_0to35chars);
		m = r.matcher(creatorReference);

		if (m.find())
		{
			// Remove default contents after previous modification
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getCar_creatorReference()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_creatorReference()[1]), "");

			// Insert the title of data
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getCar_creatorReference()[0]), String.format("%-35s", creatorReference));
		}
		else
			validInput = false;

		// Create String from StringBuffer for the new text
		if (validInput)
			modifiedCarrierPart1 = newString.toString();

		return validInput;
	}

	private void assembleCarrier()
	{
		carrier = modifiedCarrierPart1.concat("\n").concat(defaultCarrierPart2);
	}

	public static String getAirlineDesignatorFromCarrier()
	{
		return airlineDesignatorForTrailerRecord;
	}

	public static String getReleaseDateFromCarrier()
	{
		return releaseDateForTrailerRecord;
	}

	public static String getPeriodOfValidityStart() 
	{
		return periodOfValidityStartForFlightRecord;
	}
	
	public static String getPeriodOfValidityEnd() 
	{
		return periodOfValidityEndForFlightRecord;
	}

	public String getCarrier() 
	{
		return carrier;
	}

	public Boolean getValidCarrier() 
	{
		return validInput;
	}
	
	public void printCarrier()
	{
		System.out.println(carrier);
	}
}