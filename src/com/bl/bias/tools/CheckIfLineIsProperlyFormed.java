package com.bl.bias.tools;

import java.util.HashSet;
import java.util.Iterator;

import com.bl.bias.objects.BridgeAnalysisLink;

import com.google.common.collect.Iterables;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.Traverser;

public class CheckIfLineIsProperlyFormed 
{
	HashSet <BridgeAnalysisLink> lineLinks = new HashSet <BridgeAnalysisLink>();
	
	Boolean lineIsProperlyFormed = true;

	public CheckIfLineIsProperlyFormed(HashSet<String> bridgeClassLinkNodesFromCondition1, HashSet<String> absoluteSignalsFromCondition2, HashSet<BridgeAnalysisLink> allLinksInLine) 
	{
		// Load graph for line
		MutableGraph<String> lineNetwork = GraphBuilder.directed().build();

		Iterator<BridgeAnalysisLink> itrLink = allLinksInLine.iterator();
		while (itrLink.hasNext())
		{
			BridgeAnalysisLink linkToAdd = itrLink.next();
			lineNetwork.putEdge(linkToAdd.getNodeAId(), linkToAdd.getNodeBId());
		}

		// Iterate through absolute signal nodes
		Iterator<String> itrAbsoluteSignals = absoluteSignalsFromCondition2.iterator();
		while (itrAbsoluteSignals.hasNext())
		{
			String absoluteSignalNode = itrAbsoluteSignals.next();
			Boolean absoluteSignalValid = false;
			
			Iterator<String> itrBridgeClassLinkNodes = bridgeClassLinkNodesFromCondition1.iterator();
			while (itrBridgeClassLinkNodes.hasNext())
			{
				String bridgeNode = itrBridgeClassLinkNodes.next();
				
				if (lineNetwork.nodes().contains(absoluteSignalNode))
				{					
					Iterable<String> traversedNodesFromAbsoluteSignal = Traverser.forGraph(lineNetwork).depthFirstPostOrder(absoluteSignalNode);
					if (Iterables.contains(traversedNodesFromAbsoluteSignal, bridgeNode))	
					{
						absoluteSignalValid = true;
						break;
					}							
				}
				else
				{
					absoluteSignalValid = false;
					break;
				}	
			}
			
			if (!absoluteSignalValid)
			{
				lineIsProperlyFormed = false;
				break;
			}
		}
	}

	public boolean getLineIsProperlyFormed() 
	{
		return lineIsProperlyFormed;
	}
}