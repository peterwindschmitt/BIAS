package com.bl.bias.read;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.objects.RadixxCarrierOutput;
import com.bl.bias.objects.RadixxFlightLegOutput;
import com.bl.bias.objects.RadixxHeaderOutput;
import com.bl.bias.objects.RadixxTrailerOutput;
import com.bl.bias.tools.ConvertDateTime;

public class ReadExcelFileForConversionToRadixxResSSIM 
{
	private String resultsMessage;
	private String ssimText = "";

	private Boolean validFile = true;

	private Integer objectCount = 0;

	public ReadExcelFileForConversionToRadixxResSSIM(String file) throws Exception 
	{
		resultsMessage = "\nStarted parsing Excel file at "+ConvertDateTime.getTimeStamp()+"\n";

		// Open file
		FileInputStream fis = new FileInputStream(new File(file));

		// Get workbook  
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		// Get first sheet  
		XSSFSheet sheet = wb.getSheetAt(0);

		// Header
		RadixxHeaderOutput rho = new RadixxHeaderOutput(file, wb, sheet);
		objectCount++;

		// Carrier
		RadixxCarrierOutput rco = new RadixxCarrierOutput(file, wb, sheet);
		objectCount = objectCount + 8;

		// Flight Leg and Segment Record
		RadixxFlightLegOutput rfo = new RadixxFlightLegOutput(file, wb, sheet);
		objectCount = RadixxFlightLegOutput.getObjectCount();

		// Trailer Record
		RadixxTrailerOutput rto = new RadixxTrailerOutput(file, wb, sheet);

		if ((rho.getValidHeader()) && (rco.getValidCarrier()) && (rfo.getValidFlightLegRecord()))
		{
			ssimText = rho.getHeader() + "\n"+rco.getCarrier() + "\n"+ rfo.getFlightLegs() + "\n" + rto.getTrailer();
			resultsMessage += "Read "+objectCount+" objects from spreadsheet \n";
			resultsMessage += "Finished parsing Excel file at "+ConvertDateTime.getTimeStamp()+"\n\n";
		}
		else
		{
			validFile = false;
			resultsMessage += "Error in reading objects from spreadsheet";
		}
	}

	public String getSsimText()
	{
		return ssimText;
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}

	public Boolean getValidFile()
	{
		return validFile;
	}
}