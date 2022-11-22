package com.bl.bias.analyze;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.tools.ConvertDateTime;

public class RTCResultsAnalysisCleanFiles
{
	private String resultsMessage = "\nStarted cleaning files at "+ConvertDateTime.getTimeStamp()+" on "+ConvertDateTime.getDateStamp()+"\n";
	
	private HashSet<File> filesToAmend = new HashSet<File>();
	private static ArrayList<File> filesToKeepForParsing = new ArrayList<File>();
			
	public RTCResultsAnalysisCleanFiles (Boolean move, Boolean prepend, Boolean purge, ArrayList<File> files)
	{
		filesToKeepForParsing.clear();
		
		// Find eligible files
		Iterator<File> itr1 = files.iterator();
		while (itr1.hasNext())
		{
			Boolean amend = false;
			File fileToWorkWith = itr1.next();
			Scanner scanner = null;
			try 
			{
				scanner = new Scanner(fileToWorkWith);
			} 
			catch (FileNotFoundException e) 
			{
				 ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
			}
			
			while (scanner.hasNextLine()) 
			{
			   final String lineFromFile = scanner.nextLine();
			   String targetPhrase = "RESULTS INVALID";
			   int lengthOfLine = lineFromFile.length();
			   if (Integer.valueOf(BIASParseConfigPageController.x_getInvalidResults()[1]) < lengthOfLine)
			   {
				   lengthOfLine = Integer.valueOf(BIASParseConfigPageController.x_getInvalidResults()[1]);
			   }
			   
			   if(lengthOfLine > Integer.valueOf(BIASParseConfigPageController.x_getInvalidResults()[0]))
			   {
				   if(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getInvalidResults()[0]), Integer.valueOf(lengthOfLine)).trim().contains(targetPhrase))
				   { 
					   resultsMessage +="File "+fileToWorkWith.getName().replace(".SUMMARY", "")+" failed to dispatch\n";
					   filesToAmend.add(fileToWorkWith);
				       amend = true;
				       break;
				   }
			   }
			}
			
			if (amend == false)
				filesToKeepForParsing.add(fileToWorkWith);
			
			scanner.close();
		}
		
		// Actions
		int successCount = 0;
		Boolean folderCreated = false;
		
		Iterator<File> itr2 = filesToAmend.iterator();
		while (itr2.hasNext())
		{
			File fileToWorkWith = itr2.next();
			
			if (prepend)
			{
				// Prepend
				String path = fileToWorkWith.getParent();
				File newFile = new File(path+"\\FAILED_"+fileToWorkWith.getName());  // Construct the file object. Does NOT create a file on disk!
				
				if(fileToWorkWith.renameTo(newFile))
				{
		            successCount++;
		        }				
			}
			else if (purge)
			{
				// Purge
				if(fileToWorkWith.delete())
				{
		            successCount++;
		        }	
			}
			else if (move)
			{
				// Move
				// Create directory if necessary
				String path = fileToWorkWith.getParent()+"\\Failed Dispatches";
				if (!new File(path).isDirectory())
				{
					folderCreated = true;
					boolean file = new File(path).mkdirs();
				}
								
				// Move the files 
				File newFile = new File(path+"\\"+fileToWorkWith.getName());  // Construct the file object. Does NOT create a file on disk!
				
				if(fileToWorkWith.renameTo(newFile))
				{
		            successCount++;
		        }
				else
				{
					// File was NOT able to be moved
					resultsMessage += "Unable to move "+fileToWorkWith.getName()+" (file with this name may already exist in destination folder)\n";
				}
			}
		}
		
		if (prepend)
			resultsMessage += "Prepended "+String.valueOf(successCount)+" file(s)\n";
		else if (purge)
			resultsMessage += "Purged "+String.valueOf(successCount)+" file(s)\n";
		else if ((move) && (successCount > 0) && (folderCreated))
			resultsMessage += "Created FAILED DISPATCHES folder and moved "+String.valueOf(successCount)+" file(s)\n";
		else 
			resultsMessage += "Moved "+String.valueOf(successCount)+" file(s)\n";
		
		resultsMessage +="Finished cleaning files at "+ConvertDateTime.getTimeStamp()+" on "+ConvertDateTime.getDateStamp()+"\n";
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
	
	public static ArrayList<File> getfilesToKeepForParsing()
	{
		return filesToKeepForParsing;
	}
}