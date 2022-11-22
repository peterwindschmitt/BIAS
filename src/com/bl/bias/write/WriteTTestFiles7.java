package com.bl.bias.write;

import java.io.FileOutputStream;
import java.time.LocalTime;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.app.BIASGeneralConfigController;
import com.bl.bias.app.BIASTtestPageController;
import com.bl.bias.exception.ErrorShutdown;

public class WriteTTestFiles7 
{
	private String resultsMessage = WriteTTestFiles6.getResultsMessageWrite6();
	private Boolean error;
	
	private static XSSFWorkbook workbook;
	private static XSSFSheet logSheet; 
	
	public WriteTTestFiles7(String textAreaContents)
	{
		error = false;
		LocalTime endWriteFileTime = LocalTime.now();
		resultsMessage +="\nFinished writing output file at "+endWriteFileTime;
		
		workbook = WriteTTestFiles6.getWorkbook6();
		logSheet = workbook.createSheet("Log");
		logSheet.setDisplayGridlines(false);
		
		String logToWrite = textAreaContents+resultsMessage;
		
	    // Write log to log sheet
		String[] logResults = logToWrite.split("\n");
	    for (int i = 0; i < logResults.length; i++)
	    {
	    	Row row = logSheet.createRow(i);
	    	row.createCell(0).setCellValue(logResults[i]);
	    }
	   
		try 
	    {
	    	//  Determine whether file name should be system serial time or if it should be user-specified
	    	if (BIASGeneralConfigController.getUseSerialTimeAsFileName())
	    	{
	    		FileOutputStream outputStream = new FileOutputStream(BIASTtestPageController.getSaveDirectoryLocation()+"\\TTestResults_"+System.nanoTime()+".xlsx");
	    		workbook.write(outputStream);
	 	        outputStream.close();
	 	        workbook.close();
	    	}
	    	else
	    	{
	    		FileOutputStream outputStream = new FileOutputStream(BIASTtestPageController.getSaveFileLocation());
	    		workbook.write(outputStream);
	 	        outputStream.close();
	 	        workbook.close();
	    	}
	    } 
	    catch (Exception e) 
	    {
	        error = true;
	    	ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
	    }		
	}
	
	public String getResultsWriteMessage7()
	{
		return resultsMessage;
	}
	
	public Boolean getErrorFound()
	{
		return error;
	}
}