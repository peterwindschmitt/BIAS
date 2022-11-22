package com.bl.bias.write;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.app.BIASGeneralConfigController;
import com.bl.bias.app.BIASMaintenanceWindowAnalysisPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.tools.ConvertDateTime;

public class WriteMaintenanceWindowFiles5 extends WriteMaintenanceWindowFiles4
{
	String resultsMessage = getResultsMessageWrite4();
	static Boolean error = false;
	
	public WriteMaintenanceWindowFiles5(String textAreaContents, String locationOfInputFiles)
	{
		super(textAreaContents, locationOfInputFiles);
		
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
	    		FileOutputStream outputStream = new FileOutputStream(BIASMaintenanceWindowAnalysisPageController.getSaveFileFolderForSerialFileName()+"\\MaintenanceWindowResults_"+System.nanoTime()+".xlsx");
	    		workbook.write(outputStream);
	 	        outputStream.close();
	 	        workbook.close();  
	    	}
	    	else
	    	{
	    		FileOutputStream outputStream = new FileOutputStream(BIASMaintenanceWindowAnalysisPageController.getSaveFileLocationForUserSpecifiedFileName());
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
	
	public String getWriteResultsMessage5()
	{
		return resultsMessage;
	}
	
	public static Boolean getErrorFound()
	{
		return error;
	}
}