
package com.bl.bias.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import com.bl.bias.exception.ErrorShutdown;

public class BIASPreprocessLinesForBridgeClosureAnalysis 
{
	private static HashSet<String> availableLines = new HashSet<String>();
	private static List<String> sortedLines;

	public BIASPreprocessLinesForBridgeClosureAnalysis(File lineFile) 
	{
		availableLines.clear();
		Scanner scanner = null;

		try 
		{
			// For each line
			scanner = new Scanner(lineFile);

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				if (lineFromFile.contains("Line #"))
				{
					String lineName = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getLineName()[0]), Integer.valueOf(BIASParseConfigPageController.w_getLineName()[1])).trim();
					availableLines.add(lineName);
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

	public List<String> returnAvailableLines()
	{
		return sortedLines;
	}
}