package com.bl.bias.write;

import java.io.FileOutputStream;
import java.time.LocalTime;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.app.BIASGeneralConfigController;
import com.bl.bias.app.BIASRecoveryRateAnalysisController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.tools.ConvertDateTime;

public class WriteRecoveryRateFiles6 extends WriteRecoveryRateFiles5
{
	String resultsMessage = getResultsMessageWrite5();
	static Boolean error = false;
	
	public WriteRecoveryRateFiles6(String textArea, String fullyQualifiedPath)
	{
		super(textArea, fullyQualifiedPath);
		
		try 
	    {
			LocalTime endWriteFileTime = ConvertDateTime.getTimeStamp();
			resultsMessage +="\nFinished writing output file at "+endWriteFileTime;
			String logToWrite = textArea + resultsMessage;
		    
			// Write log to log sheet
			XSSFSheet logSheet = workbook.createSheet("Log");
			logSheet.setDisplayGridlines(false);
				    	
			String[] logResults = logToWrite.split("\n");
		    for (int i = 0; i < logResults.length; i++)
		    {
	        	Row row = logSheet.createRow(i);
		        row.createCell(0).setCellValue(logResults[i]);
		    }
		    		    
			//  Determine whether file name should be system serial time or if it should be user-specified
	    	if (BIASGeneralConfigController.getUseSerialTimeAsFileName())
	    	{
	    		FileOutputStream outputStream = new FileOutputStream(BIASRecoveryRateAnalysisController.getSaveFileFolderForSerialFileName()+"\\RecoveryRateResults_"+System.nanoTime()+".xlsx");
	    		workbook.write(outputStream);
	 	        outputStream.close();
	 	        workbook.close();  
	    	}
	    	else
	    	{
	    		FileOutputStream outputStream = new FileOutputStream(BIASRecoveryRateAnalysisController.getSaveFileLocationForUserSpecifiedFileName());
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
	
	public String getResultsWriteMessage6()
	{
		return resultsMessage;
	}
	
	public static Boolean getErrorFound()
	{
		return error;
	}
}