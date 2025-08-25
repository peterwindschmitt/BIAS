package com.bl.bias.analyze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.bl.bias.objects.GtmAnalysisLink;
import com.bl.bias.objects.GtmAnalysisTraversalFromRouteFile;
import com.bl.bias.objects.GtmAnalysisTrainsTraversingLink;
import com.bl.bias.tools.ConvertDateTime;

public class GtmAnalysis 
{
	private static String resultsMessage;

	private static ArrayList<GtmAnalysisTrainsTraversingLink> traversalsByLinkArrayList = new ArrayList<GtmAnalysisTrainsTraversingLink>();

	private static HashSet<String> typesFoundHashSet = new HashSet<String>();

	private static HashMap<String, Double> tonsByTypeHashMap = new HashMap<String, Double>();
	private static HashMap<String, Double> trainMilesByTypeHashMap = new HashMap<String, Double>();

	public GtmAnalysis(HashSet<String> returnNodesFromLineFile, ArrayList<GtmAnalysisLink> returnLinksFromLinkFile, ArrayList<GtmAnalysisTraversalFromRouteFile> returnTraversalsFromRouteFile) 	
	{
		traversalsByLinkArrayList.clear();
		typesFoundHashSet.clear();

		resultsMessage = "\nStarted creating GTM Analysis results at "+ConvertDateTime.getTimeStamp()+"\n";

		// Create a set of links that have both of their nodes in the selected line - most links will appear twice given directionality
		Iterator<GtmAnalysisLink> itr1 = returnLinksFromLinkFile.iterator();
		while (itr1.hasNext())
		{
			GtmAnalysisLink linkToWorkWith = itr1.next();
			if ((returnNodesFromLineFile.contains(linkToWorkWith.getNodeAId())) && (returnNodesFromLineFile.contains(linkToWorkWith.getNodeBId())))
			{
				GtmAnalysisTrainsTraversingLink linkToAdd = new GtmAnalysisTrainsTraversingLink(linkToWorkWith.getNodeAId(), linkToWorkWith.getNodeBId(), linkToWorkWith.getLinkDistance(), linkToWorkWith.getLinkDirection());
				traversalsByLinkArrayList.add(linkToAdd);
			}
		}

		// Add weights, symbol and type from route file to each link
		String currentSymbol = "";
		String currentType = "";
		String lastSymbol = "";
		Integer traversals = 0;
		Integer currentRtcIncrement = 1;
		Integer lastRtcIncrement = 0;
		String linkDirection = "";
		String trainDirection = "";
		HashSet<String> usedSymbols = new HashSet<String>();

		// For each row of the ROUTE file containing a run-time train
		for (int i = 0; i < returnTraversalsFromRouteFile.size() - 1; i++)
		{
			currentSymbol = returnTraversalsFromRouteFile.get(i).getTrainSymbol();
			currentType = returnTraversalsFromRouteFile.get(i).getTrainType();
			typesFoundHashSet.add(currentType);

			if (!lastSymbol.equals(currentSymbol)) // A new train restarts the increment counters
			{
				currentRtcIncrement = 1;
				lastRtcIncrement = 0;
			}

			// For each link with both nodes appearing in the LINE file
			for (int j = 0; j < traversalsByLinkArrayList.size(); j++)
			{
				linkDirection = traversalsByLinkArrayList.get(j).getLinkDirection();
				trainDirection = returnTraversalsFromRouteFile.get(i).getTrainDirection().substring(0,1);			
				
				if (((traversalsByLinkArrayList.get(j).getNodeA().contains(returnTraversalsFromRouteFile.get(i).getNode())))
					&& ((traversalsByLinkArrayList.get(j).getNodeB().contains(returnTraversalsFromRouteFile.get(i + 1).getNode()))))
				{
					if ((linkDirection.equals(trainDirection)) ||
							((linkDirection.equals("S")) && (trainDirection.equals("E"))) ||
							((linkDirection.equals("E")) && (trainDirection.equals("S"))) ||
							((linkDirection.equals("N")) && (trainDirection.equals("W"))) ||
							((linkDirection.equals("W")) && (trainDirection.equals("N"))))
					{
						usedSymbols.add(currentSymbol);
						lastSymbol = currentSymbol;
						lastRtcIncrement = currentRtcIncrement;
						currentRtcIncrement = returnTraversalsFromRouteFile.get(i).getRtcIncrement();

						traversalsByLinkArrayList.get(j).addTraversal(currentSymbol, currentType, returnTraversalsFromRouteFile.get(i).getWeight(), linkDirection);
						traversals++;
					}					
				}
			}
		}

		Iterator<String> itrTypes = typesFoundHashSet.iterator();
		while (itrTypes.hasNext())
		{
			Double gtmAssignedToThisLineThisType = 0.0;
			Double trainMilesAssignedToThisLineThisType = 0.0;
			String typeToWorkWith = itrTypes.next();
			
			for (int j = 0; j < traversalsByLinkArrayList.size(); j++)
			{
				for (int k = 0; k < traversalsByLinkArrayList.get(j).getTraversals().size(); k++)
				{
					if (traversalsByLinkArrayList.get(j).getTraversals().get(k).getTrainType().equals(typeToWorkWith))
					{
						Integer weightOverLink = traversalsByLinkArrayList.get(j).getTraversals().get(k).getWeight();
						Double distanceOfLink = traversalsByLinkArrayList.get(j).getDistance();
						trainMilesAssignedToThisLineThisType += distanceOfLink;
						Double gtmToAddToLink = weightOverLink * distanceOfLink; 
						gtmAssignedToThisLineThisType += gtmToAddToLink;
					}
				}
			}
			tonsByTypeHashMap.put(typeToWorkWith, gtmAssignedToThisLineThisType);
			trainMilesByTypeHashMap.put(typeToWorkWith, trainMilesAssignedToThisLineThisType);
		}
		
		resultsMessage += "Found "+traversals+" traversals by "+usedSymbols.size()+" trains over "+traversalsByLinkArrayList.size()+" eligible links in the selected line\n";

		resultsMessage += "Finished GTM Analysis results at "+ConvertDateTime.getTimeStamp()+"\n";
	}

	public static HashMap<String, Double> getTonsByTypeHashMap()
	{
		return tonsByTypeHashMap;
	}
	
	public static HashMap<String, Double> getTrainMilesByTypeHashMap()
	{
		return trainMilesByTypeHashMap;
	}
	
	public String getResultsMessage()
	{
		return resultsMessage;
	}
}