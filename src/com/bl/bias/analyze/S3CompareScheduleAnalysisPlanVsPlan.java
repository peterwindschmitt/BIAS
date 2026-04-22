package com.bl.bias.analyze;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.bl.bias.objects.ServiceObject;
import com.bl.bias.tools.ConvertDateTime;

public class S3CompareScheduleAnalysisPlanVsPlan 
{
	private String resultsMessage = "";
	private Integer differences = 0;

	private Map<LocalDate, ArrayList<ServiceObject>> trainsInScheduleDateAButNotScheduleDateB = new HashMap<>();
	private Map<LocalDate, ArrayList<ServiceObject>> trainsInScheduleDateBButNotScheduleDateA = new HashMap<>();
	private ArrayList<String> trainsWithDifferentParameters = new ArrayList<>();

	private Boolean debug = false;

	public S3CompareScheduleAnalysisPlanVsPlan(LocalDate scheduleDateA, LocalDate scheduleDateB, ArrayList<ArrayList<ServiceObject>> analyzedDatesData) 
	{
		resultsMessage = "\nStarted analyzing loaded schedules at "+ConvertDateTime.getTimeStamp()+"\n";

		// Check for trains that exist on Day A but not on Day B
		for (int a = 0; a < analyzedDatesData.get(0).size(); a++)
		{
			innerloop:  for (int b = 0; b < analyzedDatesData.get(1).size(); b++)
			{
				if ((analyzedDatesData.get(0).get(a).getServiceName().equals(analyzedDatesData.get(1).get(b).getServiceName())))
				{
					break innerloop;
				}
				else if (b == analyzedDatesData.get(1).size()-1)
				{
					differences++;

					// Check if key exists
					if (trainsInScheduleDateAButNotScheduleDateB.containsKey(scheduleDateA)) 
					{
						trainsInScheduleDateAButNotScheduleDateB.get(scheduleDateA).add(analyzedDatesData.get(0).get(a));
					} 
					else 
					{
						ArrayList<ServiceObject> list = new ArrayList<>();
						list.add(analyzedDatesData.get(0).get(a));
						trainsInScheduleDateAButNotScheduleDateB.put(scheduleDateA, list);
					}
					break innerloop;
				}
				else
					continue;
			}
		}

		// Check for trains that exist on Day B but not on Day A
		for (int c = 0; c < analyzedDatesData.get(1).size(); c++)
		{
			innerloop:  for (int d = 0; d < analyzedDatesData.get(0).size(); d++)
			{
				if ((analyzedDatesData.get(1).get(c).getServiceName().equals(analyzedDatesData.get(0).get(d).getServiceName())))
				{
					break innerloop;
				}
				else if (d == analyzedDatesData.get(0).size()-1)
				{
					differences++;

					// Check if key exists
					if (trainsInScheduleDateBButNotScheduleDateA.containsKey(scheduleDateB)) 
					{
						trainsInScheduleDateBButNotScheduleDateA.get(scheduleDateB).add(analyzedDatesData.get(1).get(c));
					} 
					else 
					{
						ArrayList<ServiceObject> list = new ArrayList<>();
						list.add(analyzedDatesData.get(1).get(c));
						trainsInScheduleDateBButNotScheduleDateA.put(scheduleDateB, list);
					}
					break innerloop;
				}
				else
					continue;
			}
		}

		// Check for trains that exist on both selected days but at least one attribute doesn't match
		// Load all services for selected days' schedules into a hashset
		HashSet<ServiceObject> analyzedDaysServiceObjectHashSetA = new HashSet<ServiceObject>();
		for (int n = 0; n < analyzedDatesData.get(0).size(); n++)
		{
			analyzedDaysServiceObjectHashSetA.add(analyzedDatesData.get(0).get(n));
		}

		HashSet<ServiceObject> analyzedDaysServiceObjectHashSetB = new HashSet<ServiceObject>();
		for (int n = 0; n < analyzedDatesData.get(1).size(); n++)
		{
			analyzedDaysServiceObjectHashSetB.add(analyzedDatesData.get(1).get(n));
		}

		// Remove analyzedService from hashset if there is a match on a core day
		if ((analyzedDaysServiceObjectHashSetA != null) && (analyzedDaysServiceObjectHashSetB != null))
		{
			Iterator<ServiceObject> analyzedDaysServiceObjectsIteratorA = analyzedDaysServiceObjectHashSetA.iterator(); 
			while (analyzedDaysServiceObjectsIteratorA.hasNext()) 
			{
				ServiceObject analyzedServiceA = analyzedDaysServiceObjectsIteratorA.next();

				Iterator<ServiceObject> analyzedDaysServiceObjectsIteratorB = analyzedDaysServiceObjectHashSetB.iterator(); 
				
				innerloop:
				while (analyzedDaysServiceObjectsIteratorB.hasNext()) 
				{
					ServiceObject analyzedServiceB = analyzedDaysServiceObjectsIteratorB.next();

					// Remove if all parameters match
					if ((analyzedServiceA.getServiceName().equals(analyzedServiceB.getServiceName()))
							&& (analyzedServiceA.getServiceType().equals(analyzedServiceB.getServiceType()))
							&& (analyzedServiceA.getDepartureLocation().equals(analyzedServiceB.getDepartureLocation()))
							&& (analyzedServiceA.getDepartureTimestamp().equals(analyzedServiceB.getDepartureTimestamp()))
							&& (analyzedServiceA.getArrivalLocation().equals(analyzedServiceB.getArrivalLocation()))
							&& (analyzedServiceA.getArrivalTimestamp().equals(analyzedServiceB.getArrivalTimestamp())))
					{
						analyzedDaysServiceObjectsIteratorA.remove();
						analyzedDaysServiceObjectsIteratorB.remove();
						break innerloop;
					}
					// Add to Retime list if service names match but did not get kicked out in above 'if'
					else if (analyzedServiceA.getServiceName().equals(analyzedServiceB.getServiceName()))
					{
						// Check if key exists
						trainsWithDifferentParameters.add(analyzedServiceA.getServiceName());
						differences++;
					}
				}
			}
		}
		
		// Results
		if (debug)
		{
			for (Entry<LocalDate, ArrayList<ServiceObject>> entry : trainsInScheduleDateAButNotScheduleDateB.entrySet()) 
			{
				for (int x = 0; x < entry.getValue().size(); x++)
					System.out.println("On "+entry.getKey() + " train " + entry.getValue().get(x).getServiceName()+" is planned to operate under Schedule A but does not show in Schedule B.");
			}

			for (Entry<LocalDate, ArrayList<ServiceObject>> entry : trainsInScheduleDateBButNotScheduleDateA.entrySet()) 
			{
				for (int x = 0; x < entry.getValue().size(); x++)
					System.out.println("On "+entry.getKey() + " train " + entry.getValue().get(x).getServiceName()+" is planned to operate under Schedule B but does not show in Schedule A.");
			}
			
			for (String entry : trainsWithDifferentParameters) 
			{
				System.out.println("Train " + entry + " has different parameters on both dates ("+scheduleDateA+" and "+scheduleDateB+").");
			}
		}

		resultsMessage += "Found "+differences+" inconsistencies on Planned schedule dates\n";
		resultsMessage += "Finished analyzing loaded schedules at "+ConvertDateTime.getTimeStamp()+"\n";
	}

	public Map<LocalDate, ArrayList<ServiceObject>> getTrainsInScheduleDateAButNotScheduleDateB()
	{
		return trainsInScheduleDateAButNotScheduleDateB;
	}

	public Map<LocalDate, ArrayList<ServiceObject>> getTrainsInScheduleDateBButNotScheduleDateA()
	{
		return trainsInScheduleDateBButNotScheduleDateA;
	}
	
	public ArrayList<String> getTrainsWithDifferentParameters()
	{
		return trainsWithDifferentParameters;
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}