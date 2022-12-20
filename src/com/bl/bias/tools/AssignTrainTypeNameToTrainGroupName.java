
package com.bl.bias.tools;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown; 

public class AssignTrainTypeNameToTrainGroupName 
{
	private static HashMap<String, String> trainGroupAbbreviationToTrainGroupName = new HashMap<String, String>();
	private static HashMap<String, String> trainTypeNameToTrainGroupAbbreviation = new HashMap<String, String>();
	private static HashMap<String, String> trainTypeNameToTrainGroupName = new HashMap<String, String>();

	public AssignTrainTypeNameToTrainGroupName(File optionFile) 
	{
		trainGroupAbbreviationToTrainGroupName.clear();
		trainTypeNameToTrainGroupAbbreviation.clear();
		trainTypeNameToTrainGroupName.clear();

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
					String keyToWrite = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getTrainGroupAbbreviation()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainGroupAbbreviation()[1])).trim();
					String valueToWrite = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getTrainGroupName()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainGroupName()[1])).trim();
					
					trainGroupAbbreviationToTrainGroupName.put(keyToWrite, valueToWrite);
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

		// Train Type Name to Train Group Abbreviation
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
					String trainGroupAbbreviation = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeGroup()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeGroup()[1])).trim();
					String trainTypeName = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeName()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeName()[1])).trim();
					
					if (trainTypeName != "")
					{
						trainTypeNameToTrainGroupAbbreviation.put(trainTypeName, trainGroupAbbreviation);
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
		
		// Join above to create a Train Type Name to Train Group Name
		for (int i = 0; i < trainTypeNameToTrainGroupAbbreviation.size(); i++)
		{
			String keyToWrite = trainTypeNameToTrainGroupAbbreviation.keySet().toArray()[i].toString();
			String valueToWrite = trainGroupAbbreviationToTrainGroupName.get(trainTypeNameToTrainGroupAbbreviation.get(trainTypeNameToTrainGroupAbbreviation.keySet().toArray()[i].toString()));
			trainTypeNameToTrainGroupName.put(keyToWrite, valueToWrite);
		}
	}

	public static HashMap<String, String> returnTrainTypeNameToTrainGroupName()
	{
		return trainTypeNameToTrainGroupName;
	}
}