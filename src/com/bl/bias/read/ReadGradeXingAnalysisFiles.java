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
	private static ArrayList<GradeXingTpcEntry> tpcEntries;
	private static ArrayList<GradeXingAggregatedLink> gradeXingAggregatedLinks;
	private static ArrayList<GradeXingRawLink> gradeXingRawLinks;

	private static HashMap<String, String> nodeNames;
	private static HashMap<String, Double> nodeFieldMPs;
	private static HashMap<String, String> trainSymbolsToTrainTypes;
	private static HashMap<String, String> trainSymbolsToTrainGroupNames;

	private static HashSet<String> nodesInTpcFile;
	private static HashSet<String> nodesInLine;
	private static HashSet<String> trainSymbolsInTpcFile;
	private static HashSet<String> trainSymbolsNotAssignedAGroup;

	private static String resultsMessage;
	private static String lineName;

	private static Integer validTrainCount;

	public ReadGradeXingAnalysisFiles(String file, String line)
	{
		lineName = line;

		tpcEntries = new ArrayList<GradeXingTpcEntry>();
		gradeXingAggregatedLinks = new ArrayList<GradeXingAggregatedLink>();
		nodeNames = new HashMap<>();
		nodeFieldMPs = new HashMap<>();
		trainSymbolsToTrainTypes = new HashMap<>();
		trainSymbolsToTrainGroupNames = new HashMap<>();
		gradeXingRawLinks = new ArrayList<GradeXingRawLink>();
		nodesInTpcFile = new HashSet<String>();
		nodesInLine = new HashSet<String>();
		trainSymbolsInTpcFile = new HashSet<String>();
		trainSymbolsNotAssignedAGroup = new HashSet<String>();
		
		trainSymbolsToTrainTypes.clear();
		tpcEntries.clear();
		gradeXingAggregatedLinks.clear();
		gradeXingRawLinks.clear();
		nodeNames.clear();
		nodeFieldMPs.clear();
		nodesInTpcFile.clear();
		nodesInLine.clear();
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
			
			String trainSymbol = null;

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				
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
						Integer trainNameStartIndex = lineFromFile.lastIndexOf("Seed train") + 10;
						Integer trainNameEndIndex = lineFromFile.lastIndexOf("TPC results") - 1;
						
						// If valid train, then continue parsing route for the train.  Otherwise, go to next valid train or EOF, whichever occurs first
						String tpcNode = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.p_getNode()[0]), Integer.valueOf(BIASParseConfigPageController.p_getNode()[1])).trim();
						if ((!tpcNode.trim().equals("")) && (!tpcNode.trim().contains("---")) && (!tpcNode.trim().contains("Route node")) && (!tpcNode.trim().contains("Case:")))
						{
							// Valid node found
							Double tpcFieldMarker = Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.p_getFieldMarker()[0]), Integer.valueOf(BIASParseConfigPageController.p_getFieldMarker()[1])).trim());
							Double tpcDesignSpeed =  Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.p_getDesignSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.p_getDesignSpeed()[1])).trim());
							Double tpcCurrentSpeed =  Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.p_getCurrentSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.p_getCurrentSpeed()[1])).trim());
							tpcEntries.add(new GradeXingTpcEntry(trainSymbol, tpcNode, tpcFieldMarker, tpcDesignSpeed, tpcCurrentSpeed));
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

			// Parse nodes for selected line in .LINE file
			if (!line.equals("Entire Network"))
			{
				try 
				{
					File lineFile = new File(file.replace("TPC","LINE"));
					scanner = new Scanner(lineFile);

					boolean openingSequence = false;

					String targetSequence0 = "Line #";
					String targetSequence1 = "-------";

					while (scanner.hasNextLine()) 
					{
						String lineFromFile = scanner.nextLine();
						if((lineFromFile.contains(targetSequence0))
								&& (lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getLineName()[0]), Integer.valueOf(BIASParseConfigPageController.w_getLineName()[1])).trim().equals(line)))
						{ 
							openingSequence = true;
						}
						else if ((lineFromFile.contains(targetSequence1)) && (openingSequence))
						{
							resultsMessage +="Extracted "+nodesInLine.size()+" eligible objects from .LINE file\n";
							break;
						}
						else if (openingSequence)
						{ 
							nodesInLine.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol1()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol1()[1])).trim());							
							if (lineFromFile.length()>14)
								nodesInLine.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol2()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol2()[1])).trim());							
							if (lineFromFile.length()>28)
								nodesInLine.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol3()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol3()[1])).trim());							
							if (lineFromFile.length()>42)
								nodesInLine.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol4()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol4()[1])).trim());							
							if (lineFromFile.length()>56)
								nodesInLine.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol5()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol5()[1])).trim());							
							if (lineFromFile.length()>70)
								nodesInLine.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol6()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol6()[1])).trim());							
							if (lineFromFile.length()>84)
								nodesInLine.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol7()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol7()[1])).trim());							
							if (lineFromFile.length()>98)
								nodesInLine.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol8()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol8()[1])).trim());							
							if (lineFromFile.length()>112)
								nodesInLine.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol9()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol9()[1])).trim());							
							if (lineFromFile.length()>126)
								nodesInLine.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol10()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol10()[1])).trim());							
							if (lineFromFile.length()>140)
								nodesInLine.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol11()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol11()[1])).trim());
							if (lineFromFile.length()>154)
								nodesInLine.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol12()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol12()[1])).trim());
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
			}
			else
			{
				resultsMessage +=".LINE file not considered\n";
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

	public static HashSet<String> getNodesInLine()
	{
		return nodesInLine;
	}

	public static String getLineName()
	{
		return lineName;
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