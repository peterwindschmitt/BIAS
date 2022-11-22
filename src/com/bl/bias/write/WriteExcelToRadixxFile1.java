package com.bl.bias.write;

import java.io.FileWriter;
import java.io.IOException;

import com.bl.bias.app.BIASGeneralConfigController;
import com.bl.bias.app.BIASRadixxResSsimConversionController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.tools.ConvertDateTime;

public class WriteExcelToRadixxFile1
{
	private String resultsMessage = "Started writing output file at "+ConvertDateTime.getTimeStamp();
	
	private static Boolean error = false;

	public WriteExcelToRadixxFile1(String ssimText)
	{
		try 
		{
			//  Determine whether file name should be system serial time or if it should be user-specified
			FileWriter fileWriter;
			if (BIASGeneralConfigController.getUseSerialTimeAsFileName())
			{
				fileWriter = new FileWriter(BIASRadixxResSsimConversionController.getSaveFileFolderForSerialFileName()+"\\SSIMConversionFromExcel_"+System.nanoTime()+".txt");
			}
			else
			{
				fileWriter = new FileWriter(BIASRadixxResSsimConversionController.getSaveFileLocationForUserSpecifiedFileNameToRadixx());
			}
			
			fileWriter.write(ssimText);
			fileWriter.close();
			
			resultsMessage +="\nFinished writing output file at "+ConvertDateTime.getTimeStamp();
		} 
		catch (IOException e) 
		{
			error = true;
			resultsMessage = "";
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}	
	} 
	
	public static Boolean getErrorFound()
	{
		return error;
	}

	public String getResultsMessageWrite1()
	{
		return resultsMessage;
	}
}