package com.bl.bias.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import com.bl.bias.objects.MaintenanceWindowAnalysisLink;

import com.google.common.collect.Sets;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.Traverser;

public class PartitionLineIntoSubLines 
{
	HashSet<MaintenanceWindowAnalysisLink> reducedLineLinks = new HashSet <MaintenanceWindowAnalysisLink>();
	HashSet<MaintenanceWindowAnalysisLink> lineLinks = new HashSet<MaintenanceWindowAnalysisLink>();
	ArrayList<HashSet<MaintenanceWindowAnalysisLink>> subLines = new ArrayList<HashSet<MaintenanceWindowAnalysisLink>>();
	HashSet<MaintenanceWindowAnalysisLink> subLine;

	public PartitionLineIntoSubLines(ArrayList<MaintenanceWindowAnalysisLink> reducedLineLinks) 
	{
		// Load graph for line
		MutableGraph<String> lineNetwork = GraphBuilder.directed().build();
		for (int i = 0; i < reducedLineLinks.size(); i++)
		{
			lineNetwork.putEdge(reducedLineLinks.get(i).getNodeAId(), reducedLineLinks.get(i).getNodeBId());
		}

		// Create a hashset of remaining links to be assigned
		lineLinks.addAll(reducedLineLinks);

		// Place each link into a subLine
		while (lineLinks.size() > 0)
		{
			// Iterate through remaining links
			Iterator<MaintenanceWindowAnalysisLink> itrRemainingLinks = lineLinks.iterator();
			while (itrRemainingLinks.hasNext())
			{
				Boolean linkPlaced = false;
				MaintenanceWindowAnalysisLink linkToConsider = itrRemainingLinks.next();

				Iterator<HashSet<MaintenanceWindowAnalysisLink>> itrSubLine = subLines.iterator();
				while (itrSubLine.hasNext())
				{
					subLine = itrSubLine.next();
					
					HashSet<String> nodesInLine = new HashSet<String>();
					subLine.forEach(n -> nodesInLine.add(n.getNodeAId()));
										
					//  For each subLine being considered, see if this link is contiguous with the other links in the subLine
					Iterable<String> traversedNodesFromLinkToConsider = Traverser.forGraph(lineNetwork).depthFirstPostOrder(linkToConsider.getNodeAId());
					Boolean linkInLine = !Collections.disjoint(Sets.newHashSet(traversedNodesFromLinkToConsider), nodesInLine);
					if (linkInLine)
					{
						subLine.add(linkToConsider);
						itrRemainingLinks.remove();
						linkPlaced = true;
						break;
					}
				}

				if (!linkPlaced)
				{
					// Create a new subLine with the linkToConsider
					subLine = new HashSet<MaintenanceWindowAnalysisLink>();
					subLine.add(linkToConsider);
					itrRemainingLinks.remove();
					subLines.add(subLine);
				}
			}
		}
	}

	public ArrayList<HashSet<MaintenanceWindowAnalysisLink>> getSubLines() 
	{
		return subLines;
	}
}