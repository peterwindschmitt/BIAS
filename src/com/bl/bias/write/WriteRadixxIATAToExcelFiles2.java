package com.bl.bias.write;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.app.BIASGeneralConfigController;
import com.bl.bias.app.BIASRadixxResSsimConversionPageIATAExcelController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.RadixxScheduleInputIATA;
import com.bl.bias.tools.ConvertDateTime;

public class WriteRadixxIATAToExcelFiles2 extends WriteRadixxIATAToExcelFiles1
{
	String resultsMessage = getResultsMessageWrite1();
	static Boolean error = false;
	
	public WriteRadixxIATAToExcelFiles2(String textAreaContents, String locationOfInputFiles, String fileAsString, RadixxScheduleInputIATA schedule)
	{
		super(textAreaContents, locationOfInputFiles, fileAsString, schedule);
		
		try 
	    {
			resultsMessage +="\nFinished writing output file at "+ConvertDateTime.getTimeStamp();
			
			String logToWrite = textAreaContents + resultsMessage;
			
		    // Write log to log sheet
			XSSFSheet logSheet = workbook.createSheet("Log");
	    	
			String[] logResults = logToWrite.split("\n");
		    for (int i = 0; i < logResults.length; i++)
		    {
	        	Row row = logSheet.createRow(i);
		        row.createCell(0).setCellValue(logResults[i]);
		    }
		    		    
			//  Determine whether file name should be system serial time or if it should be user-specified
	    	if (BIASGeneralConfigController.getUseSerialTimeAsFileName())
	    	{
	    		FileOutputStream outputStream = new FileOutputStream(BIASRadixxResSsimConversionPageIATAExcelController.getSaveFileFolderForSerialFileName()+"\\RadixxResSSIMConversion_"+System.nanoTime()+".xlsx");
	    		workbook.write(outputStream);
	 	        outputStream.close();
	 	        workbook.close();  
	    	}
	    	else
	    	{
	    		FileOutputStream outputStream = new FileOutputStream(BIASRadixxResSsimConversionPageIATAExcelController.getSaveFileLocationForUserSpecifiedFileNameToSpreadsheet());
	    		workbook.write(outputStream);
	 	        outputStream.close();
	 	        workbook.close();
	    	}
	    } 
	    catch (Exception e) 
	    {
	        error = true;
	        resultsMessage = "";
	    	ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
	    }		
	}
	
	public String getResultsMessageWrite2()
	{
		return resultsMessage;
	}
	
	public static Boolean getErrorFound()
	{
		return error;
	}
}