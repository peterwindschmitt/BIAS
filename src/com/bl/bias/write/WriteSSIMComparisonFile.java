package com.bl.bias.write;

import java.io.FileWriter;
import java.io.IOException;

import com.bl.bias.app.BIASGeneralConfigController;
import com.bl.bias.app.BIASRadixxResSsimComparisonPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.tools.ConvertDateTime;

public class WriteSSIMComparisonFile
{
	private String resultsMessage = "Started writing output file at "+ConvertDateTime.getTimeStamp();
	
	private static Boolean error = false;

	public WriteSSIMComparisonFile(String ssimText)
	{
		try 
		{
			//  Determine whether file name should be system serial time or if it should be user-specified
			FileWriter fileWriter;
			if (BIASGeneralConfigController.getUseSerialTimeAsFileName())
			{
				fileWriter = new FileWriter(BIASRadixxResSsimComparisonPageController.getSaveDirectoryLocation()+"\\SSIMComparisonFile_"+System.nanoTime()+".txt");
			}
			else
			{
				fileWriter = new FileWriter(BIASRadixxResSsimComparisonPageController.getSaveFileLocation());
			}
			
			ssimText += "\n\nGenerated at "+ ConvertDateTime.getTimeStamp()+" on "+ConvertDateTime.getDateStamp();
			
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