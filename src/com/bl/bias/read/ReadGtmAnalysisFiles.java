package com.bl.bias.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.GtmAnalysisLink;
import com.bl.bias.objects.GtmAnalysisTraversalFromRouteFile;
import com.bl.bias.tools.ConvertDateTime;

public class ReadGtmAnalysisFiles
{
	private static HashSet<String> nodesFromLineFile = new HashSet<String>();
	private static ArrayList<GtmAnalysisLink> linksFromLinkFile = new ArrayList<GtmAnalysisLink>();
	private static ArrayList<GtmAnalysisTraversalFromRouteFile> traversalsFromRouteFile = new ArrayList<GtmAnalysisTraversalFromRouteFile>();

	private static String resultsMessage;

	public ReadGtmAnalysisFiles(String file, String line)
	{
		nodesFromLineFile.clear();
		linksFromLinkFile.clear();
		traversalsFromRouteFile.clear();

		resultsMessage = "\nStarted parsing GTM Analysis files at "+ConvertDateTime.getTimeStamp()+"\n";

		Scanner scanner = null;

		// Parse nodes in .LINE file	
		try 
		{
			File lineFile = new File(file.replace("OPTION","LINE"));
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
					resultsMessage +="Loaded "+nodesFromLineFile.size()+" nodes from .LINE file\n";
					break;
				}
				else if (openingSequence)
				{ 
					nodesFromLineFile.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol1()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol1()[1])).trim());							
					if (lineFromFile.length()>14)
						nodesFromLineFile.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol2()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol2()[1])).trim());							
					if (lineFromFile.length()>28)
						nodesFromLineFile.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol3()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol3()[1])).trim());							
					if (lineFromFile.length()>42)
						nodesFromLineFile.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol4()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol4()[1])).trim());							
					if (lineFromFile.length()>56)
						nodesFromLineFile.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol5()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol5()[1])).trim());							
					if (lineFromFile.length()>70)
						nodesFromLineFile.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol6()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol6()[1])).trim());							
					if (lineFromFile.length()>84)
						nodesFromLineFile.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol7()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol7()[1])).trim());							
					if (lineFromFile.length()>98)
						nodesFromLineFile.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol8()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol8()[1])).trim());							
					if (lineFromFile.length()>112)
						nodesFromLineFile.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol9()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol9()[1])).trim());							
					if (lineFromFile.length()>126)
						nodesFromLineFile.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol10()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol10()[1])).trim());							
					if (lineFromFile.length()>140)
						nodesFromLineFile.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol11()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol11()[1])).trim());
					if (lineFromFile.length()>154)
						nodesFromLineFile.add(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.w_getCol12()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol12()[1])).trim());
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

		// Parse links in .LINK file	
		try 
		{
			File linkFile = new File(file.replace("OPTION","LINK"));
			scanner = new Scanner(linkFile);

			boolean openingSequence = false;
			Integer linksIteratedThrough = 0;

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
					linksIteratedThrough++;
					String destinationNodeId = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkDestinationNode()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkDestinationNode()[1])).trim();
					String originNodeId = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkOriginNode()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkOriginNode()[1])).trim();
					Double linkDistance = Double.valueOf(lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkDistance()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkDistance()[1])).trim());
					String linkDirection = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkDirection()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkDirection()[1])).trim();
					
					linksFromLinkFile.add(new GtmAnalysisLink(originNodeId, destinationNodeId, linkDistance, linkDirection));
				}
			}

			resultsMessage +="Loaded "+linksFromLinkFile.size()+" links from .LINK file\n";
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

		// Parse entries in .ROUTE file	
		try 
		{
			// Read in .ROUTE file with BufferedReader then pass to Scanner
			File routeFile = new File(file.replace("OPTION","ROUTE"));
			BufferedReader bufferedReaderRouteFile = new BufferedReader(new FileReader(routeFile));
			String lineFromRouteFile = null;
			StringBuilder contentForScanner = new StringBuilder();
			Boolean firstRunTimeTrainFound = false;

			// Get just run-time trains
			while ((lineFromRouteFile = bufferedReaderRouteFile.readLine()) != null) 
			{
				if (lineFromRouteFile.contains("Run-time train:"))
				{
					firstRunTimeTrainFound = true;
					contentForScanner.append(lineFromRouteFile);
					contentForScanner.append(System.lineSeparator());
				}
				else if (firstRunTimeTrainFound)
				{
					contentForScanner.append(lineFromRouteFile);
					contentForScanner.append(System.lineSeparator());
				}
			}
			bufferedReaderRouteFile.close();

			scanner = new Scanner(contentForScanner.toString());

			boolean openingSequence0 = false;
			boolean openingSequence1 = false;
			boolean firstNodeFound = false;

			Integer routeTraversalsIteratedThrough = 0;

			String targetSequence0 = "Run-time train:";
			String targetSequence1 = "------------";
			String firstNode = "";

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				if (!lineFromFile.isEmpty())
				{
					if((lineFromFile.contains(targetSequence0)) && (!openingSequence0))
					{ 
						openingSequence0 = true;
						scanner.nextLine();
					}
					else if ((lineFromFile.contains(targetSequence1)) && (openingSequence0) && (!openingSequence1))
					{
						openingSequence1 = true;
					}
					else if ((openingSequence0) && (openingSequence1) && (lineFromFile.contains(targetSequence1)))
					{
						openingSequence0 = false;
						openingSequence1 = false;
						firstNodeFound = false;
					}
					else if ((openingSequence0) && (openingSequence1))
					{
						routeTraversalsIteratedThrough++;

						// Get train
						String trainSymbol = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.r_getTrainSymbol()[1])).trim();

						// Get type
						String trainType = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getTrainType()[0]), Integer.valueOf(BIASParseConfigPageController.r_getTrainType()[1])).trim();

						// Get node
						String node = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getNode()[0]), Integer.valueOf(BIASParseConfigPageController.r_getNode()[1])).trim();

						// Get weight
						String weight = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getTonnage()[0]), Integer.valueOf(BIASParseConfigPageController.r_getTonnage()[1])).trim();

						// Get RTC increment
						String rtcIncrement = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getRtcIncrement()[0]), Integer.valueOf(BIASParseConfigPageController.r_getRtcIncrement()[1])).trim();

						// Get TPC increment
						String tpcIncrement = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getTpcIncrement()[0]), Integer.valueOf(BIASParseConfigPageController.r_getTpcIncrement()[1])).trim();

						// Get cumulative distance
						String cumulativeDistance = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getDistance()[0]), Integer.valueOf(BIASParseConfigPageController.r_getDistance()[1])).trim();

						// Get train direction
						String trainDirection = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getDirection()[0]), Integer.valueOf(BIASParseConfigPageController.r_getDirection()[1])).trim();
						
						//if ((Integer.valueOf(tpcIncrement) >= 0) && (Double.valueOf(cumulativeDistance) > 0.000))
						if (Double.valueOf(cumulativeDistance) > 0.000)
						{
							if (firstNodeFound)
							{
								traversalsFromRouteFile.add(new GtmAnalysisTraversalFromRouteFile(trainSymbol, trainType, firstNode, Integer.valueOf(weight), Integer.valueOf(rtcIncrement), Integer.valueOf(tpcIncrement) - 1, trainDirection));
								traversalsFromRouteFile.add(new GtmAnalysisTraversalFromRouteFile(trainSymbol, trainType, node, Integer.valueOf(weight), Integer.valueOf(rtcIncrement), Integer.valueOf(tpcIncrement), trainDirection));
								firstNodeFound = false;
							}
							else
							{
								traversalsFromRouteFile.add(new GtmAnalysisTraversalFromRouteFile(trainSymbol, trainType, node, Integer.valueOf(weight), Integer.valueOf(rtcIncrement), Integer.valueOf(tpcIncrement), trainDirection));
							}
						}
						else if ((Integer.valueOf(tpcIncrement) == 0) && (Double.valueOf(cumulativeDistance) == 0.000))
						{
							firstNode = node;
							firstNodeFound = true;
						}
					}
				}
			}

			resultsMessage +="Loaded "+traversalsFromRouteFile.size()+" traversals from .ROUTE file\n";
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

		resultsMessage += "Finished parsing GTM Analysis files at "+ConvertDateTime.getTimeStamp()+"\n";
	}			

	public String getResultsMessage()
	{
		return resultsMessage;
	}

	public HashSet<String> returnNodesFromLineFile()
	{
		return nodesFromLineFile;
	}

	public ArrayList<GtmAnalysisLink> returnLinksFromLinkFile()
	{
		return linksFromLinkFile;
	}

	public ArrayList<GtmAnalysisTraversalFromRouteFile> returnTraversalsFromRouteFile()
	{
		return traversalsFromRouteFile;
	}
}