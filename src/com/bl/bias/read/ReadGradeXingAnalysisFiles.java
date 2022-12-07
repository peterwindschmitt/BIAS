package com.bl.bias.read;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.GradeXingLink;
import com.bl.bias.objects.GradeXingTpcEntry;
import com.bl.bias.tools.ConvertDateTime;

public class ReadGradeXingAnalysisFiles 
{
	private static ArrayList<GradeXingTpcEntry> tpcEntries = new ArrayList<GradeXingTpcEntry>();
	private static ArrayList<GradeXingLink> gradeXingLinks = new ArrayList<GradeXingLink>();

	private static HashMap<String, String> nodeNames = new HashMap<>();
	private static HashMap<String, Double> nodeFieldMPs = new HashMap<>();

	private static HashSet<String> nodesInTpcFile = new HashSet<String>();

	private static String resultsMessage;

	public ReadGradeXingAnalysisFiles(String file)
	{
		tpcEntries.clear();
		gradeXingLinks.clear();
		nodeNames.clear();
		nodeFieldMPs.clear();
		nodesInTpcFile.clear();

		Scanner scanner = null;
		
		resultsMessage = "Started parsing Grade Crossing Speeds input files at "+ConvertDateTime.getTimeStamp()+"\n";

		// Parse .TPC file	
		try 
		{
			File tpcFile = new File(file);
			scanner = new Scanner(tpcFile);

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();

				if (lineFromFile.length() > 0)
				{
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

			resultsMessage +="Extracted "+tpcEntries.size()+" eligible objects from .TPC file\n";
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

					// If the link has class grade xing and both nodes are in the nodesInTpcFile, add it to gradeXingLinks 
					if ((linkClass.equals("Road Crossing")) && (nodesInTpcFile.contains(originNodeId)) && (nodesInTpcFile.contains(destinationNodeId)))
					{
						gradeXingLinks.add(new GradeXingLink(originNodeId, destinationNodeId));
					}
				}
			}

			resultsMessage +="Extracted "+gradeXingLinks.size()+" eligible objects from .LINK file\n";
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

	public static ArrayList<GradeXingTpcEntry> getTpcEntries()
	{
		return tpcEntries;
	}
	public static ArrayList<GradeXingLink> getGradeXingLinks()
	{
		return gradeXingLinks;
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
	
	public String getResultsMessage()
	{
		return resultsMessage;
	}
}