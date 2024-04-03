package com.bl.bias.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.bl.bias.exception.ErrorShutdown;

public class BIASPreprocessTrainCategoriesForRTCResultsAnalysis 
{
	private static HashSet<String> availableTypes = new HashSet<String>();
	private static List<String> sortedTypes;
	private static String targetSequence;
	
	public BIASPreprocessTrainCategoriesForRTCResultsAnalysis(ArrayList<File> files) 
	{
		availableTypes.clear();
		
		Scanner scanner = null;

		try 
		{
			// For each file
			Iterator<File> itr1 = files.iterator();
			while (itr1.hasNext()) 
			{
				// For each file
				File fileToWorkWith = itr1.next();
				targetSequence = "Train type";
				boolean openingSequence = false;
				
				// For each line
				scanner = new Scanner(fileToWorkWith);
				while (scanner.hasNextLine())  
				{
					String lineFromFile = scanner.nextLine();
					if (lineFromFile.length() > 25) 
					{
						if ((lineFromFile.substring(5, 25).contains(targetSequence)) && (openingSequence == false))
						{
							openingSequence = true;
							targetSequence = "-------";
							scanner.nextLine();
						}
						else if ((lineFromFile.substring(5, 25).contains(targetSequence)) && (openingSequence == true))
						{		
							targetSequence = "Train type";
							openingSequence = false;
						}
						else if (openingSequence)
						{ 
							availableTypes.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[0]), Integer.valueOf(BIASParseConfigPageController.x_getTrainCat()[1])).trim()); 
						}
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
		
		// Sort files here
		sortedTypes = new ArrayList<>(availableTypes);
	    Collections.sort(sortedTypes, new sortItems());
	}
	
	class sortItems implements Comparator<String> 
	{ 
	    @Override
		public int compare(String o1, String o2) 
		{	    
	    	String x1 = o1;
	    	String x2 = o2;
	    	
	    	int sComp = x1.compareTo(x2);
	    	if (sComp != 0) 
	    	{
	    		return sComp;
	    	} 
	    	
	    	x1 = o1;
	    	x2 = o2;
	    	
	    	return x1.compareTo(x2);
		} 
	}
	
	public List<String> returnAvailableTypes()
	{
		return sortedTypes;
	}
}