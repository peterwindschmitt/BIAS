
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
	private static HashSet<String> tpcIncrements = new HashSet<String>();
	private static HashSet<String> availableTrains = new HashSet<String>();
	private static List<String> sortedTrains;

	public BIASPreprocessTrainsForGradeXingSpeedAalysis(File tpcFile) 
	{
		tpcIncrements.clear();
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
					String tpcIncrement0 = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.p_getTpcIncrement()[0]), Integer.valueOf(BIASParseConfigPageController.p_getTpcIncrement()[1])).trim();
					if (tpcIncrement0.contains("feet"))
					{
						String tpcIncrement1 = tpcIncrement0.replace("TPC increment is", "").trim();
						String toRemove = tpcIncrement1.substring(tpcIncrement1.lastIndexOf("t") + 1);
						tpcIncrement = tpcIncrement1.replace(toRemove, "").trim();
					}
					else
					{
						String tpcIncrement1 = tpcIncrement0.replace("TPC increment is", "").trim();
						String toRemove = tpcIncrement1.substring(tpcIncrement1.indexOf("M"));
						tpcIncrement = tpcIncrement1.replace(toRemove, "").trim().concat(" meters");
					}	
					
					tpcIncrements.add(tpcIncrement);
				}
				
				if (lineFromFile.contains("Seed train"))
				{
					String trainName0 = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.p_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.p_getTrainSymbol()[1])).trim();
					String trainName1 = trainName0.replace("Seed train", "");
					String toRemove = trainName0.substring(trainName0.indexOf("TPC"));
					String trainName2 = trainName1.replace(toRemove, "").trim();
					availableTrains.add(trainName2);
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

	public HashSet<String> returnTPCIncrements()
	{
		return tpcIncrements;
	}
	
	public List<String> returnAvailableTrains()
	{
		return sortedTrains;
	}
}