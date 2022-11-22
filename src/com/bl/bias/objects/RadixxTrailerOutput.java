package com.bl.bias.objects;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;

public class RadixxTrailerOutput 
{
	private String defaultTrailerPart1;
	private String modifiedTrailerPart1;
	private String defaultTrailerPart2;
	private String trailer;

	public RadixxTrailerOutput(String file, XSSFWorkbook wb, XSSFSheet sheet) throws Exception
	{
		try
		{
			// Create trailer
			defaultTrailerPart1 =  
					"5 **********                                                                                                                                                                               ******E******";

			defaultTrailerPart2 = 
					"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n"+
							"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n"+
							"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n"+
							"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n"+
							"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

			assembleTrailer(defaultTrailerPart1, RadixxCarrierOutput.getAirlineDesignatorFromCarrier(), RadixxCarrierOutput.getReleaseDateFromCarrier(), RadixxFlightLegOutput.getRecordSerialNumberForTrailerRecord());
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}	
	}

	private void assembleTrailer(String defaultTrailerPart1, String airlineDesignator, String releaseDate, String recordSerialNumber)
	{
		StringBuffer newString = new StringBuffer(defaultTrailerPart1);

		// Airline designator
		// Remove contents from default
		newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getTrl_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_airlineDesignator()[1]), "");

		// Insert the airline designator
		newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getTrl_airlineDesignator()[0]), airlineDesignator);
		modifiedTrailerPart1 = newString.toString();
		trailer = modifiedTrailerPart1.concat("\n").concat(defaultTrailerPart2);

		// Release date
		// Remove contents from default
		newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getTrl_releaseDate()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_releaseDate()[1]), "");

		// Insert the release date
		newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getTrl_releaseDate()[0]), releaseDate);
		modifiedTrailerPart1 = newString.toString();
		trailer = modifiedTrailerPart1.concat("\n").concat(defaultTrailerPart2);

		// Serial number check reference
		// Remove contents from default
		newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getTrl_serialNumberCheckReference()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_serialNumberCheckReference()[1]), "");

		// Insert the serial number check reference
		newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getTrl_serialNumberCheckReference()[0]), recordSerialNumber);

		// Record serial number 
		// Remove contents from default
		newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[1]), "");

		// Insert the record serial number 
		newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[0]), String.format("%06d", Integer.valueOf(recordSerialNumber) + 1));

		modifiedTrailerPart1 = newString.toString();
		trailer = modifiedTrailerPart1.concat("\n").concat(defaultTrailerPart2);
	}

	public String getTrailer() 
	{
		return trailer;
	}

	public void printTrailer()
	{
		System.out.println(trailer);
	}
}