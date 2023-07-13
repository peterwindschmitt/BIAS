package com.bl.bias.tools;

import com.bl.bias.app.BIASBridgeClosureAnalysisConfigPageController;

public class DoesEventOccurDuringActiveMarineAccessPeriod 
{
	static Boolean debug = false;
	
	public static Boolean doesEventOccurDuringActiveMarineAccessPeriod(int eventTimeInSeconds)
	{
		Boolean eventDuringActiveMarineAccessPeriod = false;
		if (debug)
		{
			System.out.print("For event at "+ConvertDateTime.convertSecondsToDayHHMMSSString(eventTimeInSeconds)+", recurring marine period start hour = "+ Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartHour().replace(":00", "")) 
			+ " recurring marine period end hour = "+ Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour().replace(":00", "")));
			
			System.out.print("\n recurring marine period start min = "+ Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", "")) 
			+ " recurring marine period end min = "+ Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", "")) 
			+ " event start min = "+ ConvertDateTime.convertSecondsToMM(eventTimeInSeconds));
		}
		
		// Marine access period all on same day
		if (((Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour().replace(":00", "")) > Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartHour().replace(":00", "")))
				|| (Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour().replace(":00", "")) == 0))
				&& ((ConvertDateTime.convertSecondsToHH(eventTimeInSeconds) >= Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartHour().replace(":00", "")))
				&& (ConvertDateTime.convertSecondsToHH(eventTimeInSeconds) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour().replace(":00", "")))))
		// HOUR
		// if marine access period end hour is later than the marine access period start hour of the same day
		// and bridge down hour occurs when or after the marine access period start hour
		// and bridge down hour occurs before the marine access period end hour
		{
			// MINUTE
			// if marine access period end minute is later than the marine access period start minute of the same hour
			// and bridge down minute occurs when or after the marine access period start minute
			// and bridge down minute occurs before the marine access period end minute
			if ((Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", "")) > Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", ""))) 
					&& (ConvertDateTime.convertSecondsToMM(eventTimeInSeconds) >= Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", "")))
					&& (ConvertDateTime.convertSecondsToMM(eventTimeInSeconds) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", ""))))
			{
				eventDuringActiveMarineAccessPeriod = true;
				if (debug)
					System.out.println(": Case 1");
			}
			else if ((Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", "")) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", ""))) 
					&& ((ConvertDateTime.convertSecondsToMM(eventTimeInSeconds) >= Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", "")))
					|| (ConvertDateTime.convertSecondsToMM(eventTimeInSeconds) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", "")))))
			// MINUTE
			// if marine access period end minute is in the next hour following later the marine access period start minute of the same day
			// and bridge down minute occurs when or after the marine access period start minute
			// or bridge down minute occurs before the marine access period end minute
			{
				eventDuringActiveMarineAccessPeriod = true;
				if (debug)
					System.out.println(": Case 2");
			}
			else
				if (debug)
					System.out.println(": No conflict 1");
		}
		// Marine access period extends over midnight
		else if ((Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour().replace(":00", "")) <= Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartHour().replace(":00", ""))) 
				&& ((ConvertDateTime.convertSecondsToHH(eventTimeInSeconds) >= Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartHour().replace(":00", "")))
				|| (ConvertDateTime.convertSecondsToHH(eventTimeInSeconds) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour().replace(":00", "")))))
		// HOUR
		// if marine access period end hour is the day after the marine access period start hour
		// and bridge down hour occurs when or after the marine access period start hour
		// or bridge down hour occurs before the marine access period end hour
		{
			// MINUTE
			// if marine access period end minute is later than the marine access period start minute of the same hour
			// and bridge down minute occurs when or after the marine access period start minute
			// and bridge down minute occurs before the marine access period end minute
			if ((Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", "")) > Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", ""))) 
					&& (ConvertDateTime.convertSecondsToMM(eventTimeInSeconds) >= Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", "")))
					&& (ConvertDateTime.convertSecondsToMM(eventTimeInSeconds) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", ""))))
			{
				eventDuringActiveMarineAccessPeriod = true;
				if (debug)
					System.out.println(": Case 3");
			}
			else if ((Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", "")) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", ""))) 
					&& ((ConvertDateTime.convertSecondsToMM(eventTimeInSeconds) >= Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", "")))
					|| (ConvertDateTime.convertSecondsToMM(eventTimeInSeconds) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", "")))))
			// MINUTE
			// if marine access period end minute is in the next hour following later the marine access period start minute of the same day
			// and bridge down minute occurs when or after the marine access period start minute
			// or bridge down minute occurs before the marine access period end minute
			{
				eventDuringActiveMarineAccessPeriod = true;
				if (debug)
					System.out.println(": Case 4");
			}
			else
			{
				if (debug)
					System.out.println(": No conflict 2");
			}
		}
		else
		{
			if (debug)
				System.out.println(": No conflict 3");
		}
					
		return eventDuringActiveMarineAccessPeriod;
	}
}