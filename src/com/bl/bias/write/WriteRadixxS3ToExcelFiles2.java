package com.bl.bias.write;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.app.BIASGeneralConfigController;
import com.bl.bias.app.BIASRadixxResSsimConversionPageS3ExcelController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.RadixxScheduleInputS3;
import com.bl.bias.tools.ConvertDateTime;

public class WriteRadixxS3ToExcelFiles2 extends WriteRadixxS3ToExcelFiles1
{
	String resultsMessage = getResultsMessageWrite1();
	static Boolean error = false;
	
	public WriteRadixxS3ToExcelFiles2(String textAreaContents, String locationOfInputFiles, String fileAsString, RadixxScheduleInputS3 schedule)
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
	    		FileOutputStream outputStream = new FileOutputStream(BIASRadixxResSsimConversionPageS3ExcelController.getSaveFileFolderForSerialFileName()+"\\RadixxResSSIMConversion_"+System.nanoTime()+".xlsx");
	    		workbook.write(outputStream);
	 	        outputStream.close();
	 	        workbook.close();  
	    	}
	    	else
	    	{
	    		FileOutputStream outputStream = new FileOutputStream(BIASRadixxResSsimConversionPageS3ExcelController.getSaveFileLocationForUserSpecifiedFileNameToSpreadsheet());
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