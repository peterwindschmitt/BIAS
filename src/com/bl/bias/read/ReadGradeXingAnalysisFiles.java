package com.bl.bias.read;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import com.bl.bias.app.BIASGradeXingSpeedsConfigController;
import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.GradeXingAggregatedLink;
import com.bl.bias.objects.GradeXingRawLink;
import com.bl.bias.objects.GradeXingTpcEntry;
import com.bl.bias.tools.AssignTrainTypeNameToTrainGroupName;
import com.bl.bias.tools.ConvertDateTime;

public class ReadGradeXingAnalysisFiles 
{
	private static ArrayList<GradeXingTpcEntry> tpcEntries = new ArrayList<GradeXingTpcEntry>();
	private static ArrayList<GradeXingAggregatedLink> gradeXingAggregatedLinks = new ArrayList<GradeXingAggregatedLink>();
	private static ArrayList<GradeXingRawLink> gradeXingRawLinks = new ArrayList<GradeXingRawLink>();

	private static HashMap<String, String> nodeNames = new HashMap<>();
	private static HashMap<String, Double> nodeFieldMPs = new HashMap<>();
	private static HashMap<String, String> trainSymbolsToTrainTypes = new HashMap<>();
	private static HashMap<String, String> trainSymbolsToTrainGroupNames = new HashMap<>();

	private static HashSet<String> nodesInTpcFile = new HashSet<String>();
	private static HashSet<String> trainSymbolsInTpcFile = new HashSet<String>();
	private static HashSet<String> trainSymbolsNotAssignedAGroup = new HashSet<String>();

	private static String resultsMessage;
	
	private static Integer validTrainCount;

	public ReadGradeXingAnalysisFiles(String file)
	{
		trainSymbolsToTrainTypes.clear();
		tpcEntries.clear();
		gradeXingAggregatedLinks.clear();
		gradeXingRawLinks.clear();
		nodeNames.clear();
		nodeFieldMPs.clear();
		nodesInTpcFile.clear();
		trainSymbolsToTrainGroupNames.clear();
		trainSymbolsInTpcFile.clear();
		trainSymbolsNotAssignedAGroup.clear();
				
		validTrainCount = 0;

		Scanner scanner = null;

		resultsMessage = "Started parsing Grade Crossing Speeds input files at "+ConvertDateTime.getTimeStamp()+"\n";

		// Parse .TRAIN file
		try 
		{
			String trainName = null;
			String trainType = null;

			File trainFile = new File(file.replace("TPC","TRAIN"));
			scanner = new Scanner(trainFile);

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();

				if (lineFromFile.length() > 0)
				{
					if (lineFromFile.contains("Train symbol: "))
					{
						trainName = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[1])).trim();
					}
					else if (lineFromFile.contains("Train type: "))
					{
						trainType = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainType()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainType()[1])).trim();
						trainSymbolsToTrainTypes.put(trainName, trainType);
					}
					else if (lineFromFile.contains("====="))
					{
						trainName = null;
						trainType = null;
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

		for (int i = 0; i < trainSymbolsToTrainTypes.size(); i++)
		{
			String trainSymbol;
			String trainTypeName;
			String trainGroupName;

			trainSymbol = trainSymbolsToTrainTypes.keySet().toArray()[i].toString();
			trainTypeName = trainSymbolsToTrainTypes.get(trainSymbol);
			trainGroupName = AssignTrainTypeNameToTrainGroupName.returnTrainTypeNameToTrainGroupName().get(trainTypeName);

			trainSymbolsToTrainGroupNames.put(trainSymbol, trainGroupName);
		}

		// Parse .TPC file
		boolean parseThisTrain = false;
		try 
		{
			File tpcFile = new File(file);
			scanner = new Scanner(tpcFile);

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				String trainSymbol = null;

				if (lineFromFile.length() > 0)
				{
					// Check if a valid train to consider
					if (lineFromFile.contains("Seed train"))
					{
						parseThisTrain = false;
						Integer trainNameStartIndex = lineFromFile.lastIndexOf("Seed train") + 10;
						Integer trainNameEndIndex = lineFromFile.lastIndexOf("TPC results") - 1;
						trainSymbol = lineFromFile.substring(trainNameStartIndex, trainNameEndIndex).trim();

						if (trainSymbolsToTrainGroupNames.get(trainSymbol) != null)
						{
							if ((trainSymbolsToTrainGroupNames.get(trainSymbol).equals("Passenger")) && (BIASGradeXingSpeedsConfigController.getEvaluatePassengerSpeeds()))
							{
								if (trainSymbolsInTpcFile.contains(trainSymbol)) 
								{
									parseThisTrain = false;
									resultsMessage +="Found duplicate Passenger train ("+trainSymbol+") in .TPC file.  This occurence will be omitted from results.\n";
								}
								else
								{
									trainSymbolsInTpcFile.add(trainSymbol);
									validTrainCount++;
									parseThisTrain = true;
								}
							}
							else if ((trainSymbolsToTrainGroupNames.get(trainSymbol).equals("Through")) && (BIASGradeXingSpeedsConfigController.getEvaluateThroughSpeeds()))
							{
								if (trainSymbolsInTpcFile.contains(trainSymbol)) 
								{
									parseThisTrain = false;
									resultsMessage +="Found duplicate Through train ("+trainSymbol+") in .TPC file.  This occurence will be omitted from results.\n";
								}
								else
								{
									trainSymbolsInTpcFile.add(trainSymbol);
									validTrainCount++;
									parseThisTrain = true;
								}
							}	
							else if ((trainSymbolsToTrainGroupNames.get(trainSymbol).equals("Local")) && (BIASGradeXingSpeedsConfigController.getEvaluateLocalSpeeds()))
							{

								if (trainSymbolsInTpcFile.contains(trainSymbol)) 
								{
									parseThisTrain = false;
									resultsMessage +="Found duplicate Local train ("+trainSymbol+") in .TPC file.  This occurence will be omitted from results.\n";
								}
								else
								{
									trainSymbolsInTpcFile.add(trainSymbol);
									validTrainCount++;
									parseThisTrain = true;
								}
							}
							else
							{
								if (!trainSymbolsNotAssignedAGroup.contains(trainSymbol))
								{
									resultsMessage +="Train "+trainSymbol+" in .TPC file is not in a Train Group subject to evaluation. This train will be omitted from results.\n";
									trainSymbolsNotAssignedAGroup.add(trainSymbol);
								}
							}
						}
						else
						{
							resultsMessage +="Train "+trainSymbol+" in .TPC file does not exist in .TRAIN file.  This train will be omitted from results.\n";
						}
					}

					if (parseThisTrain)
					{
						// If valid train, then continue parsing route for the train.  Otherwise, go to next valid train or EOF, whichever occurs first
						String tpcNode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.p_getNode()[0]), Integer.valueOf(BIASParseConfigPageController.p_getNode()[1])).trim();
						if ((!tpcNode.trim().equals("")) && (!tpcNode.trim().contains("---")) && (!tpcNode.trim().contains("Route node")) && (!tpcNode.trim().contains("Case:")))
						{
							// Valid node found
							Double tpcFieldMarker = Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.p_getFieldMarker()[0]), Integer.valueOf(BIASParseConfigPageController.p_getFieldMarker()[1])).trim());
							Double tpcDesignSpeed =  Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.p_getDesignSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.p_getDesignSpeed()[1])).trim());
							Double tpcCurrentSpeed =  Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.p_getCurrentSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.p_getCurrentSpeed()[1])).trim());

							tpcEntries.add(new GradeXingTpcEntry(tpcNode, tpcFieldMarker, tpcDesignSpeed, tpcCurrentSpeed));
							nodesInTpcFile.add(tpcNode);  // Use this hashset to determine which nodes and links to examine
						}
					}
				}
			}
			
			if (validTrainCount > 0)
				resultsMessage +="Extracted "+tpcEntries.size()+" eligible objects from "+validTrainCount+" trains in the .TPC file\n";
			scanner.close();
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		finally
		{
			scanner.close();
		}

		if (validTrainCount == 0)
		{
			resultsMessage +="No valid trains found to analyze!!!\n";
		}
		else
		{
			// Parse .NODE file	
			try 
			{
				File nodeFile = new File(file.replace("TPC","NODE"));
				scanner = new Scanner(nodeFile);
	
				boolean openingSequence = false;
	
				String targetSequence0 = "xxxxxxxxxxxx";
	
				while (scanner.hasNextLine()) 
				{
					String lineFromFile = scanner.nextLine();
					if(lineFromFile.contains(targetSequence0))
					{ 
						openingSequence = true;
						scanner.nextLine();
					}
					else if (openingSequence)
					{ 
						String nodeId = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.n_getNode()[0]), Integer.valueOf(BIASParseConfigPageController.n_getNode()[1])).trim();
						String nodeName = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.n_getNodeName()[0]), Integer.valueOf(BIASParseConfigPageController.n_getNodeName()[1])).trim();
						Double nodeFieldMP = Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.n_getFieldMarker()[0]), Integer.valueOf(BIASParseConfigPageController.n_getFieldMarker()[1])).trim());
	
						// Iterate through nodesFromLine
						for (int j = 0; j < nodesInTpcFile.size(); j++)
						{
							if (nodesInTpcFile.contains(nodeId))
							{
								nodeNames.put(nodeId, nodeName);
								nodeFieldMPs.put(nodeId, nodeFieldMP);
								break;
							}	
						}
					}
				}
	
				resultsMessage +="Extracted "+nodeNames.size()+" eligible objects from .NODE file\n";
				scanner.close();
			}
			catch (Exception e) 
			{
				ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
			}
			finally
			{
				scanner.close();
			}
	
			// Parse links in .LINK file	
			try 
			{
				File linkFile = new File(file.replace("TPC","LINK"));
				scanner = new Scanner(linkFile);
	
				boolean openingSequence = false;
	
				String targetSequence0 = "xxxxxxxxxxxx";
	
				while (scanner.hasNextLine()) 
				{
					String lineFromFile = scanner.nextLine();
					if(lineFromFile.contains(targetSequence0))
					{ 
						openingSequence = true;
						scanner.nextLine();
					}
					else if (openingSequence)
					{ 
						String originNodeId = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkOriginNode()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkOriginNode()[1])).trim();
						String destinationNodeId = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkDestinationNode()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkDestinationNode()[1])).trim();
						String linkClass = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkClass()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkClass()[1])).trim();
						Integer designPassengerSpeed = Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxPassengerSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxPassengerSpeed()[1])).trim());
						Integer designThroughSpeed = Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxThroughSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxThroughSpeed()[1])).trim());
						Integer designLocalSpeed = Integer.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxLocalSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxLocalSpeed()[1])).trim());
						
						// If the link has class grade xing and both nodes are in the nodesInTpcFile, add it to gradeXingLinks 
						if ((linkClass.equals("Road Crossing")) && (nodesInTpcFile.contains(originNodeId)) && (nodesInTpcFile.contains(destinationNodeId)))
						{
							gradeXingAggregatedLinks.add(new GradeXingAggregatedLink(originNodeId, destinationNodeId));
							gradeXingRawLinks.add(new GradeXingRawLink(originNodeId, destinationNodeId, designPassengerSpeed, designThroughSpeed, designLocalSpeed));
						}
					}
				}
	
				resultsMessage +="Extracted "+gradeXingAggregatedLinks.size()+" eligible objects from .LINK file\n";
			}
			catch (Exception e) 
			{
				ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
			}
			finally
			{
				scanner.close();
			}
	
			resultsMessage += "Finished parsing Grade Crossing Speeds input files at "+ConvertDateTime.getTimeStamp()+"\n";
		}
	}

	public static ArrayList<GradeXingTpcEntry> getTpcEntries()
	{
		return tpcEntries;
	}
	
	public static ArrayList<GradeXingAggregatedLink> getGradeXingAggregatedLinks()
	{
		return gradeXingAggregatedLinks;
	}

	public static ArrayList<GradeXingRawLink> getGradeXingRawLinks()
	{
		return gradeXingRawLinks;
	}
	
	public static HashMap<String, String> getNodeNames()
	{
		return nodeNames;
	}

	public static HashMap<String, Double> getNodeFieldMPs()
	{
		return nodeFieldMPs;
	}

	public static HashSet<String> getNodesInTpcFile()
	{
		return nodesInTpcFile;
	}

	public static String getResultsMessage()
	{
		return resultsMessage;
	}
	
	public static Integer getValidTrainCount()
	{
		return validTrainCount;
	}
}