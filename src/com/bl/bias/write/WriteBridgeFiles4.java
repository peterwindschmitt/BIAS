package com.bl.bias.write;

import java.io.FileOutputStream;
import java.time.LocalTime;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.app.BIASBridgeClosureAnalysisController;
import com.bl.bias.app.BIASGeneralConfigController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.tools.ConvertDateTime;

public class WriteBridgeFiles4 extends WriteBridgeFiles3
{
	String resultsMessage = getResultsMessageWrite3();
	static Boolean error = false;
	
	public WriteBridgeFiles4(String textAreaContents, String locationOfInputFiles)
	{
		super(textAreaContents, locationOfInputFiles);
		
		try 
	    {
			LocalTime endWriteFileTime = ConvertDateTime.getTimeStamp();
			resultsMessage +="\nFinished writing output file at "+endWriteFileTime;
			
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
	    		FileOutputStream outputStream = new FileOutputStream(BIASBridgeClosureAnalysisController.getSaveFileFolderForSerialFileName()+"\\BridgeClosureResults_"+System.nanoTime()+".xlsx");
	    		workbook.write(outputStream);
	 	        outputStream.close();
	 	        workbook.close();  
	    	}
	    	else
	    	{
	    		FileOutputStream outputStream = new FileOutputStream(BIASBridgeClosureAnalysisController.getSaveFileLocationForUserSpecifiedFileName());
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
	
	public String getResultsWriteMessage4()
	{
		return resultsMessage;
	}
	
	public static Boolean getErrorFound()
	{
		return error;
	}
}