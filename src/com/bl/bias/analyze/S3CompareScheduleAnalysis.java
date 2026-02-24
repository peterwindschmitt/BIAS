package com.bl.bias.analyze;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.bl.bias.app.BIASS3CompareScheduleConfigPageController;
import com.bl.bias.objects.ServiceObject;
import com.bl.bias.tools.ConvertDateTime;

public class S3CompareScheduleAnalysis 
{
	private String resultsMessage = "";
	private Integer differences = 0;

	private Map<LocalDate, ArrayList<ServiceObject>> trainsInAnalyzedDayButNotCoreDay = new HashMap<>();
	private Map<LocalDate, ArrayList<ServiceObject>> trainsInCoreDayButNotAnalyzedDay = new HashMap<>();
	private Map<LocalDate, ArrayList<ServiceObject>> trainsWithDifferentParameters = new HashMap<>();

	private Boolean debug = false;

	public S3CompareScheduleAnalysis(LocalDate startDate, LocalDate endDate, ArrayList<ArrayList<ServiceObject>> coreDatesData, ArrayList<ArrayList<ServiceObject>> analyzedDatesData) 
	{
		resultsMessage = "\nStarted analyzing loaded schedules at "+ConvertDateTime.getTimeStamp()+"\n";

		for (int i = 0; i < analyzedDatesData.size(); i++)
		{
			String analyzedDayOfWeek = startDate.plusDays(i).getDayOfWeek().toString();
			Integer analyzedDayOfWeekValue = startDate.plusDays(i).getDayOfWeek().getValue(); // 1 = Monday

			LocalDate coreDayOfWeekAsLocalDateValue = null;
			Integer coreDayOfWeekValue = null; // 1 = Monday
			if (analyzedDayOfWeek.toUpperCase().equals("MONDAY"))
			{
				coreDayOfWeekAsLocalDateValue = BIASS3CompareScheduleConfigPageController.getMondayCoreDate();
				coreDayOfWeekValue = 1;
			}
			else if (analyzedDayOfWeek.toUpperCase().equals("TUESDAY"))
			{
				coreDayOfWeekAsLocalDateValue = BIASS3CompareScheduleConfigPageController.getTuesdayCoreDate();
				coreDayOfWeekValue = 2;
			}
			else if (analyzedDayOfWeek.toUpperCase().equals("WEDNESDAY"))
			{
				coreDayOfWeekAsLocalDateValue = BIASS3CompareScheduleConfigPageController.getWednesdayCoreDate();
				coreDayOfWeekValue = 3;
			}
			else if (analyzedDayOfWeek.toUpperCase().equals("THURSDAY"))
			{
				coreDayOfWeekAsLocalDateValue = BIASS3CompareScheduleConfigPageController.getThursdayCoreDate();
				coreDayOfWeekValue = 4;
			}
			else if (analyzedDayOfWeek.toUpperCase().equals("FRIDAY"))
			{
				coreDayOfWeekAsLocalDateValue = BIASS3CompareScheduleConfigPageController.getFridayCoreDate();
				coreDayOfWeekValue = 5;
			}
			else if (analyzedDayOfWeek.toUpperCase().equals("SATURDAY"))
			{
				coreDayOfWeekAsLocalDateValue = BIASS3CompareScheduleConfigPageController.getSaturdayCoreDate();
				coreDayOfWeekValue = 6;
			}
			else if (analyzedDayOfWeek.toUpperCase().equals("SUNDAY"))
			{
				coreDayOfWeekAsLocalDateValue = BIASS3CompareScheduleConfigPageController.getSundayCoreDate();
				coreDayOfWeekValue = 7;
			}

			if (debug)
			{
				System.out.println("Checking schedules on "+startDate.plusDays(i)+" which is a "+analyzedDayOfWeek+ " (day of week "+analyzedDayOfWeekValue+")");
				System.out.println(" and this will be compared to trains on Core Date "+coreDayOfWeekAsLocalDateValue +" (day of week "+coreDayOfWeekValue+")");
				System.out.print(" Actual scheduled trains are: ");
				for (int j = 0; j < analyzedDatesData.get(i).size(); j++)
				{
					System.out.print(analyzedDatesData.get(i).get(j).getServiceName()+" ");
				}
				System.out.println();
				System.out.print(" Core scheduled trains are:   ");
				for (int j = 0; j < coreDatesData.get(coreDayOfWeekValue - 1).size(); j++)
				{
					System.out.print(coreDatesData.get(coreDayOfWeekValue - 1).get(j).getServiceName()+" ");
				}
				System.out.println();
			}

			// Check for trains that exist on Analyzed Day but not on Core Day
			for (int a = 0; a < analyzedDatesData.get(i).size(); a++)
			{
				innerloop:  for (int b = 0; b < coreDatesData.get(coreDayOfWeekValue - 1).size(); b++)
				{
					if ((analyzedDatesData.get(i).get(a).getServiceName().equals(coreDatesData.get(coreDayOfWeekValue - 1).get(b).getServiceName())))
					{
						break innerloop;
					}
					else if (b == coreDatesData.get(coreDayOfWeekValue - 1).size()-1)
					{
						differences++;

						// Check if key exists
						if (trainsInAnalyzedDayButNotCoreDay.containsKey(startDate.plusDays(i))) 
						{
							trainsInAnalyzedDayButNotCoreDay.get(startDate.plusDays(i)).add(analyzedDatesData.get(i).get(a));
						} 
						else 
						{
							ArrayList<ServiceObject> list = new ArrayList<>();
							list.add(analyzedDatesData.get(i).get(a));
							trainsInAnalyzedDayButNotCoreDay.put(startDate.plusDays(i), list);
						}
						break innerloop;
					}
					else
						continue;
				}
			}

			// Check for trains that exist on Core Day but not on Analyzed Day
			for (int a = 0; a < coreDatesData.get(coreDayOfWeekValue - 1).size(); a++)
			{
				innerloop:  for (int b = 0; b < analyzedDatesData.get(i).size(); b++)
				{
					if ((coreDatesData.get(coreDayOfWeekValue - 1).get(a).getServiceName().equals(analyzedDatesData.get(i).get(b).getServiceName())))
					{
						break innerloop;
					}
					else if (b == analyzedDatesData.get(i).size()-1)
					{
						differences++;
						
						// Check if key exists
						if (trainsInCoreDayButNotAnalyzedDay.containsKey(startDate.plusDays(i))) 
						{
							trainsInCoreDayButNotAnalyzedDay.get(startDate.plusDays(i)).add(coreDatesData.get(coreDayOfWeekValue - 1).get(a));
						} 
						else 
						{
							ArrayList<ServiceObject> list = new ArrayList<>();
							list.add(coreDatesData.get(coreDayOfWeekValue - 1).get(a));
							trainsInCoreDayButNotAnalyzedDay.put(startDate.plusDays(i), list);
						}
						break innerloop;
					}
					else
						continue;
				}
			}

			// Check for trains that exist on Analyzed Day AND Core Day but at least one attribute doesn't match

		}

		// Results
		for (Entry<LocalDate, ArrayList<ServiceObject>> entry : trainsInAnalyzedDayButNotCoreDay.entrySet()) 
		{
			for (int i = 0; i < entry.getValue().size(); i++)
				System.out.println("On "+entry.getKey() + " train " + entry.getValue().get(i).getServiceName()+" is planned to operate but does not show in Core Schedule.");
		}

		for (Entry<LocalDate, ArrayList<ServiceObject>> entry : trainsInCoreDayButNotAnalyzedDay.entrySet()) 
		{
			for (int i = 0; i < entry.getValue().size(); i++)
			System.out.println("On "+entry.getKey() + " train " + entry.getValue().get(i).getServiceName()+" is in Core Schedule but is not planned to operate.");
		}

		resultsMessage += "Found "+differences+" inconsistencies in S3 schedules\n";
		resultsMessage += "Finished analyzing loaded schedules at "+ConvertDateTime.getTimeStamp()+"\n";
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}