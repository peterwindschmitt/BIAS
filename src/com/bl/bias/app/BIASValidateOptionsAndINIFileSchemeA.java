package com.bl.bias.app;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Scanner;
import java.util.prefs.Preferences;

import com.bl.bias.exception.ErrorShutdown;

public class BIASValidateOptionsAndINIFileSchemeA 
{
	private static Boolean optionFilesFormattedCorrectly;
	private static Boolean INIFileFormattedCorrectly;
	private static Boolean trainsEquippedCorrectly;

	private static Preferences prefs;

	public static void bIASCheckOptionFiles(File directory) 
	{
		optionFilesFormattedCorrectly = true;

		// Determine number of .OPTION files 
		File[] files = directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File directory, String name) {
				return name.toLowerCase().endsWith(".option");
			}
		});

		if (files.length == 1)
		{
			Scanner scanner = null;

			try 
			{
				File fileToWorkWith = files[0];

				// For each line
				scanner = new Scanner(fileToWorkWith);

				while (scanner.hasNextLine()) 
				{
					String lineFromFile = scanner.nextLine();
					if (lineFromFile.contains("Summary report time format:"))
					{
						// From .OPTION file:   "Summary report time format: DD:HH:MM:SS"
						String[] newLineName = lineFromFile.split(" format: ");
						if (!newLineName[1].trim().equals("DD:HH:MM:SS"))
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
						// From .OPTION file:   "Train route report in CSV format: NO"  Line 2191
						String[] newLineName = lineFromFile.split(" format: ");
						if (!newLineName[1].trim().equals("NO"))
						{
							optionFilesFormattedCorrectly = false;
							break;
						}
					}
					else if (lineFromFile.contains("All nodes (vs event only) in route report:"))
					{
						// From .OPTION file:   "All nodes (vs event only) in route report: YES"  Line 2190
						String[] newLineName = lineFromFile.split(" report: ");
						if (!newLineName[1].trim().equals("YES"))
						{
							optionFilesFormattedCorrectly = false;
							break;
						}
					}
					else if (lineFromFile.contains("Show seed trains in route report:")) 
					{
						// From .OPTION file:   "Show seed trains in route report: NO"  Line 2188
						String[] newLineName = lineFromFile.split(" report: ");
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
				ErrorShutdown.displayError(e, "BIASValidateOptionsAndINIFile");
			}
			finally
			{
				scanner.close();
			}
		}
		else
		{
			optionFilesFormattedCorrectly = false;
		}
	}

	public static void bIASCheckINIFile(File directory) 
	{
		INIFileFormattedCorrectly = true;

		prefs = Preferences.userRoot().node("BIAS");

		// Is .INI file in project folder or in C:\RTC
		if (prefs.getBoolean("gc_useRtcFolderForIniFile", true))
		{
			// Overwrite directory with C:\RTC if using generic RTC file location
			directory = new File("C:\\RTC");
		}

		// Determine number of .INI files 
		File[] files = directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File directory, String name) {
				return name.toLowerCase().endsWith(".ini");
			}
		});

		if (files.length == 1)
		{
			Scanner scanner = null;

			try 
			{
				File fileToWorkWith = files[0];

				// For each line
				scanner = new Scanner(fileToWorkWith);

				while (scanner.hasNextLine()) 
				{
					String lineFromFile = scanner.nextLine();
					if (lineFromFile.contains("allow_alpha_day_in_times ="))
					{
						// From .INI:   "allow_alpha_day_in_times = NO"
						String[] newLineName = lineFromFile.split("times =");
						if (!newLineName[1].trim().equals("NO"))
						{
							INIFileFormattedCorrectly = false;
						}
						break;
					}
				}

			}
			catch (Exception e) 
			{
				ErrorShutdown.displayError(e, "BIASValidateOptionsAndINIFileSchemeA");
			}
			finally
			{
				scanner.close();
			}
		}
		else
		{
			INIFileFormattedCorrectly = false;
		}
	}

	public static void bIASCheckTrainFile(File directory) 
	{
		trainsEquippedCorrectly = true;

		// Determine number of .OPTION files 
		File[] files = directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File directory, String name) {
				return name.toLowerCase().endsWith(".train");
			}
		});

		if (files.length == 1)
		{
			Scanner scanner = null;

			try 
			{
				File fileToWorkWith = files[0];

				// For each line
				scanner = new Scanner(fileToWorkWith);

				while (scanner.hasNextLine()) 
				{
					String lineFromFile = scanner.nextLine();
					if ((lineFromFile.contains("PTC equipped:")) && (BIASRTCResultsAnalysisConfigPageController.getCheckForPtcEquipped()))
					{
						// From .TRAIN file:   "PTC equipped: YES"
						if (lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getPtcEquipped()[0]), Integer.valueOf(BIASParseConfigPageController.t_getPtcEquipped()[1])).trim().equals("NO")) 				
						{
							trainsEquippedCorrectly = false;
							break;
						}
					}
					else if ((lineFromFile.contains("ATC:")) && (BIASRTCResultsAnalysisConfigPageController.getCheckForAtcEquipped()))
					{
						// From .TRAIN file:   "ATC: YES"
						if (lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getAtcEquipped()[0]), Integer.valueOf(BIASParseConfigPageController.t_getAtcEquipped()[1])).trim().equals("NO")) 				
						{
							trainsEquippedCorrectly = false;
							break;
						}
					}
				}
			}
			catch (Exception e) 
			{
				ErrorShutdown.displayError(e, "BIASValidateOptionsAndINIFile");
			}
			finally
			{
				scanner.close();
			}
		}
		else
		{
			trainsEquippedCorrectly = false;
		}
	}

	public static Boolean getOptionsFilesFormattedCorrectly()
	{
		return optionFilesFormattedCorrectly;
	}

	public static Boolean getINIFileFormattedCorrectly()
	{
		return INIFileFormattedCorrectly;
	}

	public static Boolean getTrainsEquippedCorrectly()
	{
		return trainsEquippedCorrectly;
	}
}