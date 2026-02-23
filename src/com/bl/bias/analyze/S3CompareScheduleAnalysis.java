package com.bl.bias.analyze;

import java.time.LocalDate;
import java.util.ArrayList;

import com.bl.bias.app.BIASS3CompareScheduleConfigPageController;
import com.bl.bias.objects.ServiceObject;
import com.bl.bias.tools.ConvertDateTime;

public class S3CompareScheduleAnalysis 
{
	private String resultsMessage = "";
	private Integer comparisonCount = 0;

	Boolean error = false;

	public S3CompareScheduleAnalysis(LocalDate startDate, LocalDate endDate, ArrayList<ArrayList<ServiceObject>> coreDatesData, ArrayList<ArrayList<ServiceObject>> analyzedDatesData) 
	{
		resultsMessage = "\nStarted analyzing loaded schedules at "+ConvertDateTime.getTimeStamp()+"\n";
		
		System.out.println("Starting Analysis Class");
		for (int i = 0; i < analyzedDatesData.size(); i++)
		{
			for (int j = 0; j < analyzedDatesData.get(i).size(); j++)
			{
				System.out.println(i+":"+j+":"+analyzedDatesData.get(i).get(j).getDate()+":"+analyzedDatesData.get(i).get(j).getServiceName());
			}
		}

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

		resultsMessage += "Compared "+comparisonCount+" data elements from S3 files\n";
		resultsMessage += "Finished analyzing loaded schedules at "+ConvertDateTime.getTimeStamp()+"\n";
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}