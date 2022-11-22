
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

public class BIASPreprocessLinesAndSubsForRTCResultsAnalysis 
{
	private static HashSet<String> availableSubdivisions = new HashSet<String>();
	private static HashSet<String> availableLines = new HashSet<String>();
	private static List<String> sortedLines;
	private static List<String> sortedSubdivisions;
	
	public BIASPreprocessLinesAndSubsForRTCResultsAnalysis(ArrayList<File> arrayList) 
	{
		availableLines.clear();
		availableSubdivisions.clear();
		
		Scanner scanner = null;

		try 
		{
			// For each file
			Iterator<File> itr1 = arrayList.iterator();
			while (itr1.hasNext())
			{
				File fileToWorkWith = itr1.next();
				// For each line
				scanner = new Scanner(fileToWorkWith);
			
				while (scanner.hasNextLine()) 
				{
					String lineFromFile = scanner.nextLine();
					if (lineFromFile.contains("statistics only"))
					{
						String[] newLineName = lineFromFile.split(" statistics only");
						if (newLineName[0].contains("line"))
						{
							availableLines.add(newLineName[0].replace("line","").trim());
						}
						else if (newLineName[0].contains("subdivision"))
						{
							availableSubdivisions.add(newLineName[0].replace("subdivision","").trim());
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
		sortedSubdivisions = new ArrayList<>(availableSubdivisions);
	    Collections.sort(sortedSubdivisions, new sortItems());
        
	    sortedLines = new ArrayList<>(availableLines);
     	Collections.sort(sortedLines, new sortItems());
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
	
	public int returnTotalFiles() 
	{
		return BIASRTCResultsAnalysisPageController.getEligibleFileCount();
	}
	
	public List<String> returnAvailableSubdivisions()
	{
		return sortedSubdivisions;
	}
	
	public List<String> returnAvailableLines()
	{
		return sortedLines;
	}
}