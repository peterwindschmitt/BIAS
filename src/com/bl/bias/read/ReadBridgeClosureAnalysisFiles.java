package com.bl.bias.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.BridgeAnalysisLink;
import com.bl.bias.objects.BridgeAnalysisNode;
import com.bl.bias.objects.BridgeAnalysisRouteTraversal;
import com.bl.bias.objects.BridgeAnalysisSignal;
import com.bl.bias.tools.ConvertDateTime;

public class ReadBridgeClosureAnalysisFiles
{
	private static HashSet<String> nodesFromLineFile = new HashSet<String>();
	private static HashSet<BridgeAnalysisNode> nodesFromNodeFile = new HashSet<BridgeAnalysisNode>();
	private static HashSet<BridgeAnalysisLink> linksFromLinkFile = new HashSet<BridgeAnalysisLink>();
	private static HashSet<BridgeAnalysisSignal> signalsFromSignalFile = new HashSet<BridgeAnalysisSignal>();
	private static ArrayList<BridgeAnalysisRouteTraversal> traversalsFromRouteFile = new ArrayList<BridgeAnalysisRouteTraversal>();

	private static String resultsMessage;
	
	public ReadBridgeClosureAnalysisFiles(String file, String line)
	{
		nodesFromLineFile.clear();
		nodesFromNodeFile.clear();
		linksFromLinkFile.clear();
		signalsFromSignalFile.clear();
		traversalsFromRouteFile.clear();

		resultsMessage = "\nStarted parsing Bridge Closure Analysis files at "+ConvertDateTime.getTimeStamp()+"\n";

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
					resultsMessage +="Extracted "+nodesFromLineFile.size()+" objects from .LINE file\n";
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

		// Parse nodes in .NODE file	
		try 
		{
			File nodeFile = new File(file.replace("OPTION","NODE"));
			scanner = new Scanner(nodeFile);

			boolean openingSequence = false;
			Integer nodesIteratedThrough = 0;

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
					nodesIteratedThrough++;
					String nodeId = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.n_getNode()[0]), Integer.valueOf(BIASParseConfigPageController.n_getNode()[1])).trim();
					
					// Iterate through nodesFromLineFile
					Iterator<String> itr1 = nodesFromLineFile.iterator();
					while (itr1.hasNext())
					{
						String nodeToWorkWith = itr1.next();
						if (nodeToWorkWith.equals(nodeId))
						{
							String nodeName = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.n_getNodeName()[0]), Integer.valueOf(BIASParseConfigPageController.n_getNodeName()[1])).trim();
							String isASignal = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.n_getIsASignal()[0]), Integer.valueOf(BIASParseConfigPageController.n_getIsASignal()[1])).trim();
							nodesFromNodeFile.add(new BridgeAnalysisNode(nodeId, nodeName, isASignal));	
						}
					}	
				}
			}

			resultsMessage +="Extracted "+nodesFromNodeFile.size()+" eligible objects of "+nodesIteratedThrough+" total objects from .NODE file\n";
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
					String originNodeId = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkOriginNode()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkOriginNode()[1])).trim();
					
					// Iterate through nodesFromLineFile
					Iterator<String> itr1 = nodesFromLineFile.iterator();
					while (itr1.hasNext())
					{
						String originNodeToWorkWith = itr1.next();
						if (originNodeToWorkWith.equals(originNodeId))
						{
							Iterator<String> itr2 = nodesFromLineFile.iterator();
							while (itr2.hasNext())
							{
								String destinationNodeToWorkWith = itr2.next();
								String destinationNodeId = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkDestinationNode()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkDestinationNode()[1])).trim();
								
								if (destinationNodeToWorkWith.equals(destinationNodeId))
								{
									String linkClass = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkClass()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkClass()[1])).trim();
									String linkDirection = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.l_getLinkDirection()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkDirection()[1])).trim();
									linksFromLinkFile.add(new BridgeAnalysisLink(originNodeId, destinationNodeId, linkClass, linkDirection));
								}
							}
						}
					}	
				}
			}

			resultsMessage +="Extracted "+linksFromLinkFile.size()+" eligible objects of "+linksIteratedThrough+" total objects from .LINK file\n";
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

		// Parse signals in .SIGNAL file	
		try 
		{
			File linkFile = new File(file.replace("OPTION","SIGNAL"));
			scanner = new Scanner(linkFile);

			boolean openingSequence0 = false;
			boolean openingSequence1 = false;

			Integer signalsIteratedThrough = 0;

			String targetSequence0 = "------------";

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				if((lineFromFile.contains(targetSequence0)) && (!openingSequence0))
				{ 
					openingSequence0 = true;
					scanner.nextLine();
				}
				else if ((lineFromFile.contains(targetSequence0)) && (openingSequence0))
				{
					openingSequence1 = true;
				}
				else if ((openingSequence0) && (openingSequence1))
				{
					// Get begin node
					String signalBeginNodeId = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.s_getSignalBeginNode()[0]), Integer.valueOf(BIASParseConfigPageController.s_getSignalBeginNode()[1])).trim();

					// Iterate through nodesFromLineFile
					// If beginNode is in nodesFromLine, then add the signal
					Iterator<String> itr1 = nodesFromLineFile.iterator();
					while (itr1.hasNext())
					{
						String beginNodeToWorkWith = itr1.next();
											
						if (beginNodeToWorkWith.equals(signalBeginNodeId))
						{
							// Get type from next line
							lineFromFile = scanner.nextLine();
							String signalType = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.s_getSignalType()[0]), Integer.valueOf(BIASParseConfigPageController.s_getSignalType()[1])).trim();

							// Get direction from next line
							lineFromFile = scanner.nextLine();
							String signalDirection = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.s_getSignalDirection()[0]), Integer.valueOf(BIASParseConfigPageController.s_getSignalDirection()[1])).trim();
							
							signalsFromSignalFile.add(new BridgeAnalysisSignal(signalBeginNodeId, signalType, signalDirection));
						}
					}		

					// Move to next signal
					openingSequence1 = false;
					signalsIteratedThrough++;
				}
			}

			resultsMessage +="Extracted "+signalsFromSignalFile.size()+" eligible objects of "+signalsIteratedThrough+" total objects from .SIGNAL file\n";
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
			
			Integer routeTraversalsIteratedThrough = 0;

			String targetSequence0 = "Run-time train:";
			String targetSequence1 = "------------";
			
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
					}
					else if ((openingSequence0) && (openingSequence1))
					{
						routeTraversalsIteratedThrough++;
						
						// Get node
						String node = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getNode()[0]), Integer.valueOf(BIASParseConfigPageController.r_getNode()[1])).trim();
						
						Iterator<String> itr1 = nodesFromLineFile.iterator();
						while (itr1.hasNext())
						{
							String nodeToWorkWith = itr1.next();
							if (node.equals(nodeToWorkWith))
							{
								// Get train
								String trainSymbol = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.r_getTrainSymbol()[1])).trim();
								
								// Get RTC route index
								String routeIncrement = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getRtcIncrement()[0]), Integer.valueOf(BIASParseConfigPageController.r_getRtcIncrement()[1])).trim();
								
								// Get head-end speed
								String headEndSpeed = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getHeadEndSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.r_getHeadEndSpeed()[1])).trim();
								
								// Get head-end arrival time
								String headEndArrivalTime = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getHeadEndArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getHeadEndArrivalTime()[1])).trim();
								
								// Get tail-end departure time
								String tailEndDepartureTime = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getTailEndDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getTailEndDepartureTime()[1])).trim();
								
								// Get cumulative elapsed time
								String cumulativeElapsedTime = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getCumulativeElapsedTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getCumulativeElapsedTime()[1])).trim();
								
								// Get aspect
								String aspect = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getAspect()[0]), Integer.valueOf(BIASParseConfigPageController.r_getAspect()[1])).trim();
								
								// Get direction
								String direction = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.r_getDirection()[0]), Integer.valueOf(BIASParseConfigPageController.r_getDirection()[1])).trim();
								
								traversalsFromRouteFile.add(new BridgeAnalysisRouteTraversal(trainSymbol, routeIncrement, node, headEndSpeed, headEndArrivalTime, tailEndDepartureTime, cumulativeElapsedTime, aspect, direction));
							}
						}	
					}
				}
			}

			resultsMessage +="Extracted "+traversalsFromRouteFile.size()+" eligible objects of "+routeTraversalsIteratedThrough+" total objects from .ROUTE file\n";
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

		resultsMessage += "Finished parsing Bridge Closure Analysis files at "+ConvertDateTime.getTimeStamp()+"\n";
	}			

	public String getResultsMessage()
	{
		return resultsMessage;
	}

	public HashSet<String> returnNodesFromLineFile()
	{
		return nodesFromLineFile;
	}

	public HashSet<BridgeAnalysisNode> returnNodesFromNodeFile()
	{
		return nodesFromNodeFile;
	}

	public HashSet<BridgeAnalysisLink> returnLinksFromLinkFile()
	{
		return linksFromLinkFile;
	}

	public HashSet<BridgeAnalysisSignal> returnSignalsFromSignalFile()
	{
		return signalsFromSignalFile;
	}
	
	public ArrayList<BridgeAnalysisRouteTraversal> returnTraversalsFromRouteFile()
	{
		return traversalsFromRouteFile;
	}
}