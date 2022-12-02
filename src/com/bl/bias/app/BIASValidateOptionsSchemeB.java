package com.bl.bias.app;

import java.io.File;
import java.util.Scanner;

import com.bl.bias.exception.ErrorShutdown;

public class BIASValidateOptionsSchemeB 
{
	private static Boolean optionFilesFormattedCorrectly;

	public static void bIASCheckOptionFiles(File fileToWorkWith) 
	{
		optionFilesFormattedCorrectly = true;

		Scanner scanner = null;

		try 
		{
			// For each line
			scanner = new Scanner(fileToWorkWith);

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				if (lineFromFile.contains("TPC report format:"))
				{
					// From .OPTION file:   "TPC report format:" line 1921
					String[] newLineName = lineFromFile.split(" format: ");
					if (!newLineName[1].trim().equals("A"))
					{
						optionFilesFormattedCorrectly = false;
						break;
					}
				}
				else if (lineFromFile.contains("Units:"))
				{
					// From .OPTION file:   "Units: ENGLISH"  Line 15
					String[] newLineName = lineFromFile.split(" Units: ");
					if (!newLineName[1].trim().equals("ENGLISH"))
					{
						optionFilesFormattedCorrectly = false;
						break;
					}
				}
				else if (lineFromFile.contains("Train route report in CSV format:"))  
				{
					// From .OPTION file:   "CSV format in TPC report:"  Line 1922
					String[] newLineName = lineFromFile.split(" format: ");
					if (!newLineName[1].trim().equals("NO"))
					{
						optionFilesFormattedCorrectly = false;
						break;
					}
				}
			}
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, "BIASValidateOptionsAndINIFileSchemeB");
		}
		finally
		{
			scanner.close();
		}
	}
	
	public static Boolean getOptionsFilesFormattedCorrectly()
	{
		return optionFilesFormattedCorrectly;
	}
}