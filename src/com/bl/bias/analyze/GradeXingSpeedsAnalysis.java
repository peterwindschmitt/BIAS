package com.bl.bias.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import com.bl.bias.objects.GradeXingAggregatedLink;
import com.bl.bias.objects.GradeXingTpcEntry;
import com.bl.bias.objects.GradeXingTraversal;
import com.bl.bias.read.ReadGradeXingAnalysisFiles;
import com.bl.bias.tools.ConvertDateTime;

public class GradeXingSpeedsAnalysis 
{
	private static String resultsMessage;
	private static String line;

	private static ArrayList<GradeXingTpcEntry> tpcEntries;
	private static ArrayList<GradeXingAggregatedLink> gradeXingLinks;

	private static HashMap<String, String> nodeNames;
	private static HashMap<String, Double> nodeFieldMPs;

	private static HashSet<String> nodesInLine;

	private static ArrayList<GradeXingTraversal> traversals;
	private static ArrayList<GradeXingTraversal> sortedTraversals;

	public GradeXingSpeedsAnalysis() 
	{
		resultsMessage = "Started analyzing Grade Crossing Speeds at "+ConvertDateTime.getTimeStamp()+"\n";

		tpcEntries = new ArrayList<GradeXingTpcEntry>();
		gradeXingLinks = new ArrayList<GradeXingAggregatedLink>();
		nodeNames = new HashMap<>();
		nodeFieldMPs = new HashMap<>();
		nodesInLine = new HashSet<>();
		traversals = new ArrayList<GradeXingTraversal>();
		sortedTraversals = new ArrayList<GradeXingTraversal>();

		tpcEntries.clear();
		gradeXingLinks.clear();
		nodeNames.clear();
		nodeFieldMPs.clear();
		nodesInLine.clear();
		traversals.clear();
		sortedTraversals.clear();

		tpcEntries = ReadGradeXingAnalysisFiles.getTpcEntries();
		gradeXingLinks = ReadGradeXingAnalysisFiles.getGradeXingAggregatedLinks();
		nodeNames = ReadGradeXingAnalysisFiles.getNodeNames();
		nodeFieldMPs = ReadGradeXingAnalysisFiles.getNodeFieldMPs();
		nodesInLine = ReadGradeXingAnalysisFiles.getNodesInLine();

		line = ReadGradeXingAnalysisFiles.getLineName();

		// 1.  Create a traversal object for each road crossing consisting of two node field MPs and the crossing name.  Do not consider direction.  
		// For each entry in gradeXingLinks
		for (int i = 0; i < gradeXingLinks.size(); i++)
		{
			Boolean duplicate = false;
			innerloop:
				for (int j = 0; j < i; j++)
				{
					if ((((nodeFieldMPs.get(gradeXingLinks.get(i).getNodeA()).equals(nodeFieldMPs.get(gradeXingLinks.get(j).getNodeB()))))
							&& (nodeFieldMPs.get(gradeXingLinks.get(i).getNodeB()).equals(nodeFieldMPs.get(gradeXingLinks.get(j).getNodeA())))) ||
							(((nodeFieldMPs.get(gradeXingLinks.get(i).getNodeA()).equals(nodeFieldMPs.get(gradeXingLinks.get(j).getNodeA()))))
									&& (nodeFieldMPs.get(gradeXingLinks.get(i).getNodeB()).equals(nodeFieldMPs.get(gradeXingLinks.get(j).getNodeB())))))
					{
						duplicate = true;
						break innerloop;
					}					
				}
			if (duplicate == false)
			{
				if (line.equals("Entire Network"))
					traversals.add(new GradeXingTraversal(nodeFieldMPs.get(gradeXingLinks.get(i).getNodeA()), nodeFieldMPs.get(gradeXingLinks.get(i).getNodeB()), nodeNames.get(gradeXingLinks.get(i).getNodeA())));
				else if ((nodesInLine.contains(gradeXingLinks.get(i).getNodeA())) || (nodesInLine.contains(gradeXingLinks.get(i).getNodeB())))
				{
					traversals.add(new GradeXingTraversal(nodeFieldMPs.get(gradeXingLinks.get(i).getNodeA()), nodeFieldMPs.get(gradeXingLinks.get(i).getNodeB()), nodeNames.get(gradeXingLinks.get(i).getNodeA())));
				}
			}
		}

		// 2.  Record the lowest and highest speeds design speed and observed speed for either node in the link regardless of direction for all trains
		for (int i = 0; i < traversals.size(); i++)
		{
			for (int j = 0; j < tpcEntries.size(); j++)
			{
				if ((Double.valueOf(traversals.get(i).getNodeAFieldMP()).equals(tpcEntries.get(j).getFieldMarker())) || (Double.valueOf(traversals.get(i).getNodeBFieldMP()).equals(tpcEntries.get(j).getFieldMarker())))

				{
					traversals.get(i).setHighestObservedSpeed(tpcEntries.get(j).getCurrentSpeed());
					traversals.get(i).setLowestObservedSpeed(tpcEntries.get(j).getCurrentSpeed());
					traversals.get(i).setHighestDesignSpeed(tpcEntries.get(j).getDesignSpeed());
					traversals.get(i).setLowestDesignSpeed(tpcEntries.get(j).getDesignSpeed());
				}
			}
		}

		// 3.  Sort traversals on field milepost
		sortedTraversals = new ArrayList <GradeXingTraversal>();
		sortedTraversals.addAll(traversals);
		Collections.sort(sortedTraversals, new MilepostSorter());

		resultsMessage += "Analyzed "+sortedTraversals.size()+" crossing entries\n";
		resultsMessage += "Finished analyzing Grade Crossing Speeds results at "+ConvertDateTime.getTimeStamp()+"\n\n";
	}

	public static ArrayList<GradeXingTraversal> getSortedTraversals()
	{
		return sortedTraversals;
	}

	public static ArrayList<GradeXingAggregatedLink> getGradeXingLinks()
	{
		return gradeXingLinks;
	}

	public static HashMap<String, String> getNodeNames()
	{
		return nodeNames;
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}

	class MilepostSorter implements Comparator<GradeXingTraversal> 
	{ 
		@Override
		public int compare(GradeXingTraversal o1, GradeXingTraversal o2) 
		{	    
			Double x1 = o1.getNodeAFieldMP();
			Double x2 = o2.getNodeAFieldMP();

			int sComp = x1.compareTo(x2);
			if (sComp != 0) 
			{
				return sComp;
			} 

			x1 = o1.getNodeAFieldMP();
			x2 = o2.getNodeAFieldMP();

			return x1.compareTo(x2);
		} 
	}
}