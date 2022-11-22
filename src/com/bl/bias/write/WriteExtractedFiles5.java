package com.bl.bias.write;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalTime;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.app.BIASRTCResultsAnalysisPageController;
import com.bl.bias.app.BIASGeneralConfigController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.read.ReadRTCResultsAnalysisGroupFiles;
import com.bl.bias.read.ReadRTCResultsAnalysisTypeFiles;

public class WriteExtractedFiles5 extends WriteExtractedFiles4
{
	String resultsMessage = getResultsMessageWrite5();
	static Boolean error = false;
	
	public WriteExtractedFiles5(File directory, String textAreaContents, Boolean entireNetworkOnly, Boolean allLines, Boolean trainCount,
			Boolean velocity, Boolean trainMiles, Boolean elapsedTime, Boolean elapsedTimePerTrain, Boolean idealRunTime, Boolean trueDelay, Boolean trueDelayMinutes100TM, Boolean trueDelayPerTrain, 
			Boolean otp, Boolean writeRawData, Boolean writeSummaryData, Boolean writeGraphs, Boolean timeAsString, Boolean timeInSeconds, Boolean timeAsSerial)
	{
		super(directory, textAreaContents, entireNetworkOnly, allLines, trainCount, velocity, trainMiles, elapsedTime, elapsedTimePerTrain, idealRunTime, trueDelay, 
				trueDelayMinutes100TM, trueDelayPerTrain, otp, writeRawData, writeSummaryData, writeGraphs, timeAsString, timeInSeconds, timeAsSerial);
		
		try 
	    {
			LocalTime endWriteFileTime = LocalTime.now();
			resultsMessage +="\nFinished writing output file at "+endWriteFileTime;
			
			if (ReadRTCResultsAnalysisTypeFiles.returnRTCVersion() != null)
				resultsMessage +="\n\nResults extracted from files created with RTC Version "+ReadRTCResultsAnalysisTypeFiles.returnRTCVersion()+"\n";
			else 
				resultsMessage +="\n\nResults extracted from files created with RTC Version "+ReadRTCResultsAnalysisGroupFiles.returnRTCVersion()+"\n";
			
			String logToWrite = textAreaContents+resultsMessage;
			
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
	    		FileOutputStream outputStream = new FileOutputStream(directory+"\\ExtractedResults_"+System.nanoTime()+".xlsx");
	    		workbook.write(outputStream);
	 	        outputStream.close();
	 	        workbook.close();
	    	}
	    	else
	    	{
	    		FileOutputStream outputStream = new FileOutputStream(BIASRTCResultsAnalysisPageController.getSaveFileLocation());
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
	
	public String getResultsWriteMessage7()
	{
		return resultsMessage;
	}
	
	public static Boolean getErrorFound()
	{
		return error;
	}
}