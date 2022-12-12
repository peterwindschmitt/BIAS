
package com.bl.bias.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import com.bl.bias.exception.ErrorShutdown; 

public class BIASPreprocessTrainsForGradeXingSpeedAalysis 
{
	private static HashSet<String> tpcIncrementsIncludingUnits = new HashSet<String>();
	private static HashSet<Integer> tpcIncrementsDigitsOnly = new HashSet<Integer>();
	private static HashSet<String> availableTrains = new HashSet<String>();
	private static List<String> sortedTrains;

	public BIASPreprocessTrainsForGradeXingSpeedAalysis(File tpcFile) 
	{
		tpcIncrementsIncludingUnits.clear();
		tpcIncrementsDigitsOnly.clear();
		availableTrains.clear();
		
		Scanner scanner = null;

		try 
		{
			// For each line in file
			scanner = new Scanner(tpcFile);

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				if (lineFromFile.contains("TPC increment is"))
				{		
					String tpcIncrement;
					
					if (lineFromFile.contains("feet"))
					{
						Integer tpcIncrementStartIndex = lineFromFile.lastIndexOf("TPC increment is") + 17;
						Integer tpcIncrementEndIndex = lineFromFile.lastIndexOf("feet");
						tpcIncrement = lineFromFile.substring(tpcIncrementStartIndex, tpcIncrementEndIndex).trim();
						tpcIncrementsDigitsOnly.add(Integer.valueOf(tpcIncrement));
						tpcIncrementsIncludingUnits.add(tpcIncrement.concat(" feet"));
					}
					else
					{
						Integer tpcIncrementStartIndex = lineFromFile.lastIndexOf("TPC increment is") + 17;
						Integer tpcIncrementEndIndex = lineFromFile.lastIndexOf("Max coupler") - 1;
						tpcIncrement = lineFromFile.substring(tpcIncrementStartIndex, tpcIncrementEndIndex).replace("M","").trim();
						tpcIncrementsDigitsOnly.add(Integer.valueOf(tpcIncrement));
						tpcIncrementsIncludingUnits.add(tpcIncrement.concat(" meters"));
					}	
				}
				
				if (lineFromFile.contains("Seed train"))
				{
					Integer trainNameStartIndex = lineFromFile.lastIndexOf("Seed train") + 10;
					Integer trainNameEndIndex = lineFromFile.lastIndexOf("TPC results") - 1;
					String trainName = lineFromFile.substring(trainNameStartIndex, trainNameEndIndex).trim();
					availableTrains.add(trainName);
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
		sortedTrains = new ArrayList<>(availableTrains);
		Collections.sort(sortedTrains, new sortItems());
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

	public HashSet<String> returnTPCIncrementsIncludingUnits()
	{
		return tpcIncrementsIncludingUnits;
	}
	
	public HashSet<Integer> returnTPCIncrementsDigitsOnly()
	{
		return tpcIncrementsDigitsOnly;
	}
	
	public List<String> returnAvailableTrains()
	{
		return sortedTrains;
	}
}