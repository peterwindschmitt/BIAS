
package com.bl.bias.app;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import com.bl.bias.exception.ErrorShutdown; 

public class BIASPreprocessTrainGroupsAndTypesForGradeXingSpeedAnalysis 
{
	private static HashMap<String, String> trainGroupNamesAndAbbreviations = new HashMap<String, String>();
	private static HashMap<String, String> trainTypeNameToGroup = new HashMap<String, String>();

	public BIASPreprocessTrainGroupsAndTypesForGradeXingSpeedAnalysis(File optionFile) 
	{
		trainGroupNamesAndAbbreviations.clear();
		trainTypeNameToGroup.clear();

		Scanner scanner = null;

		// Train Group Name and Abbreviation
		try 
		{
			scanner = new Scanner(optionFile);
			boolean openingSequence = false;

			int rowNumber = 0;
			int startRowNumber = 0;

			String targetSequence0 = "[Train groups]";
			String targetSequence1 = "-------";

			// For each line in file
			while (scanner.hasNextLine()) 
			{
				rowNumber++;
				String lineFromFile = scanner.nextLine();
				if ((lineFromFile.contains(targetSequence0)) && (openingSequence == false))
				{		
					// Skip 6 lines then start gathering group name and abbreviation
					startRowNumber = rowNumber + 6;		
					openingSequence = true;
				}
				else if ((lineFromFile.contains(targetSequence1)) && (rowNumber > startRowNumber) && (openingSequence == true))
				{
					break;
				}
				else if ((rowNumber >= startRowNumber) && (openingSequence == true))
				{
					trainGroupNamesAndAbbreviations.put(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getTrainGroupName()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainGroupName()[1])).trim(),
							lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getTrainGroupAbbreviation()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainGroupAbbreviation()[1])).trim());
				}
			}		
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		finally
		{
			scanner.close();
		}

		// Train Type Name to Group
		try 
		{
			scanner = new Scanner(optionFile);
			boolean openingSequence = false;

			int rowNumber = 0;
			int startRowNumber = 0;

			String targetSequence0 = "[Train types]";
			String targetSequence1 = "-------";

			// For each line in file
			while (scanner.hasNextLine()) 
			{
				rowNumber++;
				String lineFromFile = scanner.nextLine();
				if ((lineFromFile.contains(targetSequence0)) && (openingSequence == false))
				{		
					// Skip 6 lines then start gathering group name and abbreviation
					startRowNumber = rowNumber + 6;		
					openingSequence = true;
				}
				else if ((lineFromFile.contains(targetSequence1)) && (rowNumber > startRowNumber) && (openingSequence == true))
				{
					break;
				}
				else if ((rowNumber >= startRowNumber) && (openingSequence == true))
				{
					String trainGroupName = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeGroup()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeGroup()[1])).trim();
					String trainTypeName = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeName()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeName()[1])).trim();
					
					if (trainTypeName != "")
					{
						trainTypeNameToGroup.put(trainTypeName, trainGroupName);
					}
				}
			}		
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		finally
		{
			scanner.close();
		}
	}

	public HashMap<String, String> returnTrainGroupNamesAndAbbreviations()
	{
		return trainGroupNamesAndAbbreviations;
	}
}