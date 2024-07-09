package com.bl.bias.write;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.app.BIASGeneralConfigController;
import com.bl.bias.app.BIASModifiedOtpPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.tools.ConvertDateTime;

public class WriteModifiedOtpFiles2 extends WriteModifiedOtpFiles1
{
	private static String resultsMessage2 = "";
	
	static Boolean error = false;
	
	public WriteModifiedOtpFiles2(String textArea, String fileAsString)
	{
		super(textArea, fileAsString);
		
		resultsMessage2 = WriteModifiedOtpFiles1.getResultsMessage1();
				
    	//  Write spreadsheet
		try 
	    {
			resultsMessage2 +="\nFinished writing output spreadsheet file at "+ConvertDateTime.getTimeStamp();
			
			String logToWrite = textArea + resultsMessage2;
			
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
	    		FileOutputStream outputStream = new FileOutputStream(BIASModifiedOtpPageController.getSaveFileFolderForSerialFileName()+"\\ModifiedOTPAnalysis_"+System.nanoTime()+".xlsx");
	    		workbook.write(outputStream);
	 	        outputStream.close();
	 	        workbook.close();  
	    	}
	    	else
	    	{
	    		FileOutputStream outputStream = new FileOutputStream(BIASModifiedOtpPageController.getSaveFileLocationForUserSpecifiedFileName());
	    		workbook.write(outputStream);
	 	        outputStream.close();
	 	        workbook.close();
	    	}	
	    } 
	    catch (Exception e) 
	    {
	        error = true;
	        resultsMessage2 = "";
	    	ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
	    }		
	}
	
	public static String getResultsMessage2()
	{
		return resultsMessage2;
	}
	
	public static Boolean getErrorFound()
	{
		return error;
	}
}