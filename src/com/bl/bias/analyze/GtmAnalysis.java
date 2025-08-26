package com.bl.bias.analyze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.bl.bias.app.BIASGtmConfigPageController;
import com.bl.bias.objects.GtmAnalysisLink;
import com.bl.bias.objects.GtmAnalysisTraversalFromRouteFile;
import com.bl.bias.objects.GtmAnalysisTrainsTraversingLink;
import com.bl.bias.tools.ConvertDateTime;

public class GtmAnalysis 
{
	private static String resultsMessage;

	private static ArrayList<GtmAnalysisTrainsTraversingLink> traversalsByLinkArrayList = new ArrayList<GtmAnalysisTrainsTraversingLink>();

	private static HashSet<String> typesFoundHashSet = new HashSet<String>();
	private static HashSet<String> typesInUserAssignments1AsHashSet = new HashSet<String>();
	private static HashSet<String> typesInUserAssignments2AsHashSet = new HashSet<String>();

	private static HashMap<String, Double> tonsByTypeHashMap = new HashMap<String, Double>();
	private static HashMap<String, Double> trainMilesByTypeHashMap = new HashMap<String, Double>();
	private static double trainMilesUserAssignment1;
	private static double tonMilesUserAssignment1;
	private static double trainMilesUserAssignment2;
	private static double tonMilesUserAssignment2;

	public GtmAnalysis(HashSet<String> returnNodesFromLineFile, ArrayList<GtmAnalysisLink> returnLinksFromLinkFile, ArrayList<GtmAnalysisTraversalFromRouteFile> returnTraversalsFromRouteFile, Boolean useCustomAssignments) 	
	{
		traversalsByLinkArrayList.clear();
		typesFoundHashSet.clear();
		typesInUserAssignments1AsHashSet.clear();
		typesInUserAssignments2AsHashSet.clear();
		
		trainMilesUserAssignment1 = 0.0;
		tonMilesUserAssignment1 = 0.0;
		trainMilesUserAssignment2 = 0.0;
		tonMilesUserAssignment2 = 0.0;

		resultsMessage = "\nStarted creating GTM Analysis results at "+ConvertDateTime.getTimeStamp()+"\n";

		// Determine if user assignments are active
		if ((useCustomAssignments) && (BIASGtmConfigPageController.getValidCustomAssignment1Exists().getValue().equals(true)))
		{
			resultsMessage += "!!!User-defined Operator 1 is in effect for this analysis!!!\n";

			String[] userCat1TypesAsArray = BIASGtmConfigPageController.getUserCategory1Types().getValue().split(",");
			List<String> userCat1TypesAsList = Arrays.asList(userCat1TypesAsArray); 
			typesInUserAssignments1AsHashSet.addAll(userCat1TypesAsList);
		}

		if ((useCustomAssignments) && (BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true)))
		{
			resultsMessage += "!!!User-defined Operator 2 is in effect for this analysis!!!\n";

			String[] userCat2TypesAsArray = BIASGtmConfigPageController.getUserCategory2Types().getValue().split(",");
			List<String> userCat2TypesAsList = Arrays.asList(userCat2TypesAsArray); 
			typesInUserAssignments2AsHashSet.addAll(userCat2TypesAsList);
		}

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
		Integer traversals = 0;
		String linkDirection = "";
		String trainDirection = "";
		HashSet<String> usedSymbols = new HashSet<String>();

		// For each row of the ROUTE file containing a run-time train
		for (int i = 0; i < returnTraversalsFromRouteFile.size() - 1; i++)
		{
			currentSymbol = returnTraversalsFromRouteFile.get(i).getTrainSymbol();
			currentType = returnTraversalsFromRouteFile.get(i).getTrainType();
			typesFoundHashSet.add(currentType);

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
					// Types found in Route File
					if (traversalsByLinkArrayList.get(j).getTraversals().get(k).getTrainType().equals(typeToWorkWith))
					{
						Integer weightOverLink = traversalsByLinkArrayList.get(j).getTraversals().get(k).getWeight();
						Double distanceOfLink = traversalsByLinkArrayList.get(j).getDistance();
						trainMilesAssignedToThisLineThisType += distanceOfLink;
						Double gtmToAddToLink = weightOverLink * distanceOfLink; 
						gtmAssignedToThisLineThisType += gtmToAddToLink;
					}
					
					// User category 1
					if ((typesInUserAssignments1AsHashSet.contains(traversalsByLinkArrayList.get(j).getTraversals().get(k).getTrainType()))		
						&& (traversalsByLinkArrayList.get(j).getTraversals().get(k).getTrainType().equals(typeToWorkWith)))
					{
						Integer weightOverLink = traversalsByLinkArrayList.get(j).getTraversals().get(k).getWeight();
						Double distanceOfLink = traversalsByLinkArrayList.get(j).getDistance();
						trainMilesUserAssignment1 += distanceOfLink;
						Double gtmToAddToLink = weightOverLink * distanceOfLink; 
						tonMilesUserAssignment1 += gtmToAddToLink;}

					// User category 2
					if ((typesInUserAssignments2AsHashSet.contains(traversalsByLinkArrayList.get(j).getTraversals().get(k).getTrainType()))	
						&& (traversalsByLinkArrayList.get(j).getTraversals().get(k).getTrainType().equals(typeToWorkWith)))
					{

						Integer weightOverLink = traversalsByLinkArrayList.get(j).getTraversals().get(k).getWeight();
						Double distanceOfLink = traversalsByLinkArrayList.get(j).getDistance();
						trainMilesUserAssignment2 += distanceOfLink;
						Double gtmToAddToLink = weightOverLink * distanceOfLink; 
						tonMilesUserAssignment2 += gtmToAddToLink;
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

	public static double getTrainMilesUserAssignment1()
	{
		return trainMilesUserAssignment1;
	}

	public static double getTonMilesUserAssignment1()
	{
		return tonMilesUserAssignment1;
	}

	public static double getTrainMilesUserAssignment2()
	{
		return trainMilesUserAssignment2;
	}

	public static double getTonMilesUserAssignment2()
	{
		return tonMilesUserAssignment2;
	}
	
	public static HashSet<String> getTypesinUserAssignments1AsHashSet()
	{
		return typesInUserAssignments1AsHashSet;
	}
	
	public static HashSet<String> getTypesinUserAssignments2AsHashSet()
	{
		return typesInUserAssignments2AsHashSet;
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}