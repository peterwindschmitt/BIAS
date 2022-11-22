package com.bl.bias.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MaintenanceWindowAnalysisSegment
{
	private String lineName;
	private ArrayList<MaintenanceWindowAnalysisNode> nodesInLine;
	private ArrayList<MaintenanceWindowAnalysisLink> linksInLine;
	private ArrayList<MaintenanceWindowAnalysisLink> reducedLinksInLine;
	private ArrayList<MaintenanceWindowAnalysisRouteTraversal> routeTraversalsInLine;
	private ArrayList<MaintenanceWindowAnalysisTrainTraversal> trainTraversalsInLine;
	private ArrayList<HashSet<MaintenanceWindowAnalysisLink>> subLines;
	private ArrayList<MaintenanceWindowAnalysisWindow> subLinesObservedWindows;
	private HashMap<String, ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>> subLinesOccupancies;
	private ArrayList<HashMap<String, ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>>> subLinesProposedOccupancies;
	private ArrayList<ArrayList<MaintenanceWindowAnalysisWindow>> allWindowsOnAllSublinesForAllProposals;
			
	@SuppressWarnings("unchecked")
	public MaintenanceWindowAnalysisSegment(String lineName, ArrayList<MaintenanceWindowAnalysisNode> nodesInLine, ArrayList<MaintenanceWindowAnalysisLink> linksInLine, ArrayList<MaintenanceWindowAnalysisRouteTraversal> routeTraversalsInLine)
	{
		this.lineName = lineName;
		this.nodesInLine = (ArrayList<MaintenanceWindowAnalysisNode>) nodesInLine.clone();
		this.linksInLine = (ArrayList<MaintenanceWindowAnalysisLink>) linksInLine.clone();
		this.routeTraversalsInLine = (ArrayList<MaintenanceWindowAnalysisRouteTraversal>) routeTraversalsInLine.clone();
		
		subLinesProposedOccupancies = new ArrayList<HashMap<String, ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>>>();
		allWindowsOnAllSublinesForAllProposals = new ArrayList<ArrayList<MaintenanceWindowAnalysisWindow>>();
	}

	public String getLineName() 
	{
		return lineName;
	}

	public ArrayList<MaintenanceWindowAnalysisNode> getNodesInLine() 
	{
		return nodesInLine;
	}

	public ArrayList<MaintenanceWindowAnalysisLink> getLinksInLine()
	{
		return linksInLine;
	}

	public ArrayList<MaintenanceWindowAnalysisRouteTraversal> getRouteTraversalsInLine()
	{
		return routeTraversalsInLine;
	}

	public ArrayList<MaintenanceWindowAnalysisTrainTraversal> getTrainTraversalsInLine()
	{
		return trainTraversalsInLine;
	}
	
	public ArrayList<MaintenanceWindowAnalysisLink> getReducedLinksInLine()
	{
		return reducedLinksInLine;
	}
	
	public ArrayList<HashSet<MaintenanceWindowAnalysisLink>> getSubLines()
	{
		return subLines;
	}
	
	public HashMap<String, ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>> getSubLinesOccupancies()
	{
		return subLinesOccupancies;
	}

	public ArrayList<MaintenanceWindowAnalysisWindow> getSubLinesObservedWindows()
	{
		return subLinesObservedWindows;
	}
	
	public ArrayList<HashMap<String, ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>>> getSubLinesProposedOccupancies()
	{
		return subLinesProposedOccupancies;
	}
		
	public ArrayList<ArrayList<MaintenanceWindowAnalysisWindow>> getSubLinesProposedWindows()
	{
		return allWindowsOnAllSublinesForAllProposals;
	}
	
	public void setReducedLinksInLine(ArrayList<MaintenanceWindowAnalysisLink> reducedLinksInLine)
	{
		this.reducedLinksInLine = reducedLinksInLine;
	}
	
	public void setSubLines(ArrayList<HashSet<MaintenanceWindowAnalysisLink>> subLines)
	{
		this.subLines = subLines;
	}
	
	public void setTrainTraversalsInLine(ArrayList<MaintenanceWindowAnalysisTrainTraversal> trainTraversalsInLine)
	{
		this.trainTraversalsInLine = trainTraversalsInLine;
	}
	
	public void setSubLinesOccupancies(HashMap<String, ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>> subLinesOccupancies)
	{
		this.subLinesOccupancies = subLinesOccupancies;
	}
	
	public void setSubLinesObservedWindows(ArrayList<MaintenanceWindowAnalysisWindow> subLinesObservedWindows)
	{
		this.subLinesObservedWindows = subLinesObservedWindows;
	}
	
	public void addToProposedOccupancies(HashMap<String, ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>> thisProposal)
	{
		subLinesProposedOccupancies.add(thisProposal);
	}
	
	public void addToProposedWindows(ArrayList<MaintenanceWindowAnalysisWindow> thisProposal)
	{
		allWindowsOnAllSublinesForAllProposals.add(thisProposal);
	}
}