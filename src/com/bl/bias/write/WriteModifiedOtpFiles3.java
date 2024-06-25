package com.bl.bias.write;

import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.app.BIASGeneralConfigController;
import com.bl.bias.app.BIASModifiedOtpPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.ModifiedOtpTrainObjectA;
import com.bl.bias.tools.ConvertDateTime;

public class WriteModifiedOtpFiles3 extends WriteModifiedOtpFiles2
{
	private static String resultsMessage3 = "";
	
	static Boolean error = false;
	
	public WriteModifiedOtpFiles3(String textArea, String fileAsString)
	{
		super(textArea, fileAsString);
		
		resultsMessage3 = WriteModifiedOtpFiles2.getResultsMessage2();
				
    	//  Write spreadsheet
		try 
	    {
			resultsMessage3 +="\nFinished writing output spreadsheet file at "+ConvertDateTime.getTimeStamp();
			
			String logToWrite = textArea + resultsMessage3;
			
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
	        resultsMessage3 = "";
	    	ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
	    }		
	}
	
	public static String getResultsMessage3()
	{
		return resultsMessage3;
	}
	
	public static Boolean getErrorFound()
	{
		return error;
	}
}