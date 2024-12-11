package com.bl.bias.write;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.app.BIASGeneralConfigController;
import com.bl.bias.app.BIASUscgBridgeComplianceAnalysisConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.BridgeComplianceClosure;
import com.bl.bias.tools.ConvertDateTime;

public class WriteBridgeComplianceFiles3 extends WriteBridgeComplianceFiles2
{
	private static String resultsMessage3 = "";
	private static String notepadComplianceStatistics3 = "";
	
	static Boolean error = false;
	
	public WriteBridgeComplianceFiles3(ArrayList<BridgeComplianceClosure> closures, String bridgeAndSpan, String textArea, String outputSpreadsheetPath, Boolean includeHighUsePeriods, Boolean includeViolationsOnClosuresSheet,
			Boolean includeConfidentialityDisclosure) 
	{
		super(closures, bridgeAndSpan, textArea, outputSpreadsheetPath, includeHighUsePeriods, includeViolationsOnClosuresSheet, includeConfidentialityDisclosure);
		
		resultsMessage3 = WriteBridgeComplianceFiles1.getResultsMessageWrite1() + WriteBridgeComplianceFiles2.getResultsMessageWrite2();
		notepadComplianceStatistics3 = WriteBridgeComplianceFiles1.getNotepadComplianceStatistics1() + WriteBridgeComplianceFiles2.getNotepadComplianceStatistics2();
		
		// Write notepad 
    	if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeSummaryResultsOnNotepad())
		{
	    	try 
			{
	    		//  Determine whether file name should be system serial time or if it should be user-specified
				FileWriter fileWriter;
								
				if (BIASGeneralConfigController.getUseSerialTimeAsFileName())
				{
					fileWriter = new FileWriter(outputSpreadsheetPath+"\\BridgeComplianceAnalysis_"+System.nanoTime()+".txt");
				}
				else
				{
					fileWriter = new FileWriter(outputSpreadsheetPath);
				}
				
				fileWriter.write(notepadComplianceStatistics3);
				fileWriter.close();
				
				resultsMessage3 +="\nFinished writing output text file at "+ConvertDateTime.getTimeStamp();
			} 
			catch (IOException e) 
			{
				error = true;
				resultsMessage3 = "";
				ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
			}
		}
		
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
	    		FileOutputStream outputStream = new FileOutputStream(outputSpreadsheetPath+"\\BridgeComplianceAnalysis_"+System.nanoTime()+".xlsx");
	    		workbook.write(outputStream);
	 	        outputStream.close();
	 	        workbook.close();  
	    	}
	    	else
	    	{
	    		FileOutputStream outputStream = new FileOutputStream(outputSpreadsheetPath);
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
	
	public static String getResultsMessageWrite3()
	{
		return resultsMessage3;
	}
	
	public static Boolean getErrorFound()
	{
		return error;
	}
}