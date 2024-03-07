package com.bl.bias.tools;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;

public class ConvertDateTime 
{
	public static int convertDDHHMMSSStringToSeconds(String stringTime)
	{
		// Convert text to seconds
		Integer stringLength = stringTime.length();
		Integer secondsTotal = 0;
		Integer minutesTotal = 0;
		Integer hoursTotal = 0;
		Integer daysTotal = 0;
		boolean proceed = true;

		// seconds
		if (stringLength == 0)
		{
			secondsTotal = 0;
			proceed = false;
		}
		else if (stringLength > 1)
		{
			CharSequence seconds = stringTime.subSequence(stringLength-2, stringLength);
			secondsTotal = Integer.valueOf((String) seconds);
		}
		else
		{
			CharSequence seconds= stringTime.subSequence(stringLength-1, stringLength);
			secondsTotal = Integer.valueOf((String) seconds);
			proceed = false;
		}

		// minutes
		if ((stringLength > 5) && proceed)
		{
			CharSequence minutes = stringTime.subSequence(stringLength-5, stringLength-3);
			minutesTotal = Integer.valueOf((String) minutes);
		}
		else if (stringLength == 5)
		{
			CharSequence minutes = stringTime.subSequence(stringLength-5, stringLength-3);
			minutesTotal = Integer.valueOf((String) minutes);
			proceed = false;
		}
		else if (stringLength == 4)
		{
			CharSequence minutes= stringTime.subSequence(stringLength-4, stringLength-3);
			minutesTotal = Integer.valueOf((String) minutes);
			proceed = false;
		}
		else
		{
			proceed = false;
		}

		// hours            	
		if ((stringLength > 8) && proceed)
		{
			CharSequence hours = stringTime.subSequence(stringLength-8, stringLength-6);
			hoursTotal = Integer.valueOf((String) hours);
		}
		else if (stringLength == 8)
		{
			CharSequence hours = stringTime.subSequence(stringLength-8, stringLength-6);
			hoursTotal = Integer.valueOf((String) hours);
			proceed = false;
		}
		else if (stringLength == 7)
		{
			CharSequence hours = stringTime.subSequence(stringLength-7, stringLength-6);
			hoursTotal = Integer.valueOf((String) hours);
			proceed = false;
		}
		else
		{
			proceed = false;
		}

		// days            	
		if (proceed)
		{
			CharSequence days = stringTime.subSequence(0, stringLength-9);
			daysTotal = Integer.valueOf((String) days);
		}

		int totalElapsedSeconds = secondsTotal + (minutesTotal * 60) + (hoursTotal * 60 * 60) + (daysTotal * 60 * 60 * 24);

		return totalElapsedSeconds;
	}
	
	public static double convertDDHHMMStringToSerial(String stringTime)
	{
		// Convert text to seconds
		Integer stringLength = stringTime.length();
		Integer minutesTotal = 0;
		Integer hoursTotal = 0;
		Integer daysTotal = 0;
		boolean proceed = true;

		// minutes
		if ((stringLength > 2) && proceed)
		{
			CharSequence minutes = stringTime.subSequence(stringLength-2, stringLength);
			minutesTotal = Integer.valueOf((String) minutes);
		}
		else if (stringLength == 2)
		{
			CharSequence minutes = stringTime.subSequence(stringLength-2, stringLength);
			minutesTotal = Integer.valueOf((String) minutes);
			proceed = false;
		}
		else if (stringLength == 1)
		{
			CharSequence minutes= stringTime.subSequence(stringLength-1, stringLength);
			minutesTotal = Integer.valueOf((String) minutes);
			proceed = false;
		}
		else
		{
			proceed = false;
		}

		// hours            	
		if ((stringLength > 5) && proceed)
		{
			CharSequence hours = stringTime.subSequence(stringLength-5, stringLength-3);
			hoursTotal = Integer.valueOf((String) hours);
		}
		else if (stringLength == 5)
		{
			CharSequence hours = stringTime.subSequence(stringLength-5, stringLength-3);
			hoursTotal = Integer.valueOf((String) hours);
			proceed = false;
		}
		else if (stringLength == 1)
		{
			CharSequence hours = stringTime.subSequence(stringLength-4, stringLength-2);
			hoursTotal = Integer.valueOf((String) hours);
			proceed = false;
		}
		else
		{
			proceed = false;
		}

		// days            	
		if (proceed)
		{
			CharSequence days = stringTime.subSequence(0, stringLength-6);
			daysTotal = Integer.valueOf((String) days);
		}

		int totalElapsedSeconds = (minutesTotal * 60) + (hoursTotal * 60 * 60) + (daysTotal * 60 * 60 * 24);
		Double totalElapsedSerialTime = (double) totalElapsedSeconds / (double) 86400;

		return totalElapsedSerialTime;
	}

	public static double convertSecondsToSerial(int totalSeconds)
	{
		// Convert seconds to serial date/time (DD:HH:MM:SS)
		Double serialTime = (double) totalSeconds/ (double) 86400;

		return serialTime;
	}

	public static int convertSerialToSeconds(double serialTime)
	{
		// Convert serial date/time to seconds
		int seconds = (int) (serialTime * 86400);

		return seconds;
	}

	public static double convertDDHHMMSSStringToSerial(String stringTime)
	{

		double serialTime = convertSecondsToSerial(convertDDHHMMSSStringToSeconds(stringTime));

		return serialTime;
	}

	public static String convertSecondsToDayHHMMSSString(int totalSeconds)
	{
		// Assumes day 1 is a Sunday
		int days = Math.abs(totalSeconds / 86400);
		int secondsLeft = Math.abs(totalSeconds - days * 86400);
		int hours = Math.abs(secondsLeft / 3600);
		secondsLeft = secondsLeft - hours * 3600;
		int minutes = Math.abs(secondsLeft / 60);
		secondsLeft = secondsLeft - minutes * 60;
		int seconds = Math.abs(secondsLeft);

		String formattedTime;
		if (totalSeconds >= 0)
			formattedTime = "";
		else
			formattedTime = "-";

		while (days > 7)
			days = days - 7;

		if (days == 1)
			formattedTime +="Sun:";
		else if (days == 2)
			formattedTime +="Mon:";
		else if (days == 3)
			formattedTime +="Tue:";
		else if (days == 4)
			formattedTime +="Wed:";
		else if (days == 5)
			formattedTime +="Thu:";
		else if (days == 6)
			formattedTime +="Fri:";
		else if (days == 7)
			formattedTime +="Sat:";

		else
			formattedTime +=days+":";

		if ((hours < 10) && (days > 0))
			formattedTime += "0"+hours+":";
		else if (hours > 0)
			formattedTime += hours + ":";

		if ((minutes < 10) && ((hours > 0) || (days>0)))
			formattedTime += "0"+minutes+":";
		else if (minutes > 0)
			formattedTime += minutes + ":";

		if ((seconds < 10) && ((minutes > 0) || (hours > 0) || (days > 0)))
			formattedTime += "0"+seconds;
		else if ((seconds > 0) && ((minutes > 0) || (hours > 0) || (days > 0)))
			formattedTime += seconds ;
		else if ((seconds > 0) && (seconds < 10))
			formattedTime += "0:0"+seconds;
		else if (seconds > 0)
			formattedTime += "0:"+seconds;
		else 
			formattedTime = "0";

		return formattedTime;
	}

	public static String convertSecondsToDay_HHMMString(int totalSeconds)
	{
		// Assumes day 1 is a Sunday
		int days = Math.abs(totalSeconds / 86400);
		int secondsLeft = Math.abs(totalSeconds - days * 86400);
		int hours = Math.abs(secondsLeft / 3600);
		secondsLeft = secondsLeft - hours * 3600;
		int minutes = Math.abs(secondsLeft / 60);

		String formattedTime;
		if (totalSeconds >= 0)
			formattedTime = "";
		else
			formattedTime = "-";

		while (days > 7)
			days = days - 7;

		if (days == 1)
			formattedTime +="Sun ";
		else if (days == 2)
			formattedTime +="Mon ";
		else if (days == 3)
			formattedTime +="Tue ";
		else if (days == 4)
			formattedTime +="Wed ";
		else if (days == 5)
			formattedTime +="Thu ";
		else if (days == 6)
			formattedTime +="Fri ";
		else if (days == 7)
			formattedTime +="Sat ";

		if ((hours < 10) && (days > 0))
			formattedTime += "0"+hours+":";
		else if (hours > 0)
			formattedTime += hours + ":";

		if ((minutes < 10) && ((hours > 0) || (days>0)))
			formattedTime += "0"+minutes;
		else if (minutes > 0)
			formattedTime += minutes;

		return formattedTime;
	}

	public static Integer convertDaytoSeconds(String dayIn)
	{
		// Assumes day 1 is a Sunday
		Integer day = null;
		Integer secondsSinceMidnight = null;

		if (dayIn.toLowerCase().equals("sunday"))
			day = 1;
		else if (dayIn.toLowerCase().equals("monday"))
			day = 2;
		else if (dayIn.toLowerCase().equals("tuesday"))
			day = 3;
		else if (dayIn.toLowerCase().equals("wednesday"))
			day = 4;
		else if (dayIn.toLowerCase().equals("thursday"))
			day = 5;
		else if (dayIn.toLowerCase().equals("friday"))
			day = 6;
		else if (dayIn.toLowerCase().equals("saturday"))
			day = 7;

		secondsSinceMidnight = day * 24 * 60 * 60; 

		return secondsSinceMidnight;
	}

	public static String convertSecondsToDay(Integer secondsIn)
	{
		// Assumes day 1 is a Sunday
		String day = null;

		while (secondsIn >= 604800)
		{
			secondsIn = secondsIn - 604800; // Remove 1 week
		}

		if (secondsIn < (1 * 24 * 60 * 60))
			day = "Sunday";
		else if (secondsIn < (2 * 24 * 60 * 60) )
			day = "Monday";
		else if (secondsIn < (3 * 24 * 60 * 60) )
			day = "Tuesday";
		else if (secondsIn < (4 * 24 * 60 * 60) )
			day = "Wednesday";
		else if (secondsIn < (5 * 24 * 60 * 60) )
			day = "Thursday";
		else if (secondsIn < (6 * 24 * 60 * 60) )
			day = "Friday";
		else if (secondsIn < (7 * 24 * 60 * 60) )
			day = "Saturday";

		return day;
	}

	public static String convertSecondsToDDHHMMSSString(int totalSeconds)
	{
		int days = Math.abs(totalSeconds / 86400);
		int secondsLeft = Math.abs(totalSeconds - days * 86400);
		int hours = Math.abs(secondsLeft / 3600);
		secondsLeft = secondsLeft - hours * 3600;
		int minutes = Math.abs(secondsLeft / 60);
		secondsLeft = secondsLeft - minutes * 60;
		int seconds = Math.abs(secondsLeft);

		String formattedTime;
		if (totalSeconds >= 0)
			formattedTime = "";
		else
			formattedTime = "-";

		if (days > 0)
			formattedTime +=days +":";

		if ((hours < 10) && (days > 0))
			formattedTime += "0"+hours+":";
		else if (hours > 0)
			formattedTime += hours + ":";

		if ((minutes < 10) && ((hours > 0) || (days>0)))
			formattedTime += "0"+minutes+":";
		else if (minutes > 0)
			formattedTime += minutes + ":";

		if ((seconds < 10) && ((minutes > 0) || (hours > 0) || (days > 0)))
			formattedTime += "0"+seconds;
		else if ((seconds > 0) && ((minutes > 0) || (hours > 0) || (days > 0)))
			formattedTime += seconds ;
		else if ((seconds > 0) && (seconds < 10))
			formattedTime += "0:0"+seconds;
		else if (seconds > 0)
			formattedTime += "0:"+seconds;
		else 
			formattedTime = "0";

		return formattedTime;
	}
	
	public static String convertSerialToDDHHMMSSString(double serialTime)
	{
		int totalSeconds = (int) (serialTime  * 86400);
		int days = Math.abs(totalSeconds / 86400);
		int secondsLeft = Math.abs(totalSeconds - days * 86400);
		int hours = Math.abs(secondsLeft / 3600);
		secondsLeft = secondsLeft - hours * 3600;
		int minutes = Math.abs(secondsLeft / 60);
		secondsLeft = secondsLeft - minutes * 60;
		int seconds = Math.abs(secondsLeft);

		String formattedTime;
		if (totalSeconds >= 0)
			formattedTime = "";
		else
			formattedTime = "-";

		if (days > 0)
			formattedTime +=days +":";

		if ((hours < 10) && (days > 0))
			formattedTime += "0"+hours+":";
		else if (hours > 0)
			formattedTime += hours + ":";

		if ((minutes < 10) && ((hours > 0) || (days>0)))
			formattedTime += "0"+minutes+":";
		else if (minutes > 0)
			formattedTime += minutes + ":";

		if ((seconds < 10) && ((minutes > 0) || (hours > 0) || (days > 0)))
			formattedTime += "0"+seconds;
		else if ((seconds > 0) && ((minutes > 0) || (hours > 0) || (days > 0)))
			formattedTime += seconds ;
		else if ((seconds > 0) && (seconds < 10))
			formattedTime += "0:0"+seconds;
		else if (seconds > 0)
			formattedTime += "0:"+seconds;
		else 
			formattedTime = "0";

		return formattedTime;
	}

	public static String convertSerialToHHMMString(double serialTime)
	{
		int secondsLeft = (int) (Math.round(Math.abs(serialTime * 86400) / 20) * 20);
		int hours = Math.abs(secondsLeft / 3600);
		secondsLeft = secondsLeft - hours * 3600;
		int minutes = Math.abs(secondsLeft / 60);
		
		String formattedTime;
		if (serialTime >= 0)
			formattedTime = "";
		else
			formattedTime = "-";

		if (hours < 10)
			formattedTime += "0"+hours+":";
		else if (hours > 0)
			formattedTime += hours + ":";

		if ((minutes < 10) && (hours >= 0))
			formattedTime += "0"+minutes;
		else if (minutes > 0)
			formattedTime += minutes;

		return formattedTime;
	}
	
	public static String convertSerialToHHMMSSString(double serialTime)
	{
		int secondsLeft = (int) (serialTime * 86400);
		int hours = Math.abs(secondsLeft / 3600);
		secondsLeft = secondsLeft - hours * 3600;
		int minutes = Math.abs(secondsLeft / 60);
		secondsLeft = secondsLeft - minutes * 60;
		
		String formattedTime;
		if (serialTime >= 0)
			formattedTime = "";
		else
			formattedTime = "-";

		if (hours < 10)
			formattedTime += "0"+hours+":";
		else if (hours > 0)
			formattedTime += hours + ":";

		if ((minutes < 10) && ((hours > 0)))
			formattedTime += "0"+minutes+":";
		else if (minutes > 0)
			formattedTime += minutes + ":";

		if ((secondsLeft < 10) && ((minutes > 0) || (hours > 0)))
			formattedTime += "0"+secondsLeft;
		else if ((secondsLeft > 0) && ((minutes > 0) || (hours > 0)))
			formattedTime += secondsLeft;
		else if ((secondsLeft > 0) && (secondsLeft < 10))
			formattedTime += "0:0"+secondsLeft;
		else if (secondsLeft > 0)
			formattedTime += "0:"+secondsLeft;
		else 
			formattedTime = "0";

		return formattedTime;
	}
	
	public static int removeDDfromDDHHMMSSStringAndConvertToSeconds(String stringTime)
	{
		// Convert text to seconds
		Integer stringLength = stringTime.length();
		Integer secondsTotal = 0;
		Integer minutesTotal = 0;
		Integer hoursTotal = 0;
		boolean proceed = true;

		// seconds
		if (stringLength == 0)
		{
			secondsTotal = 0;
			proceed = false;
		}
		else if (stringLength > 1)
		{
			CharSequence seconds = stringTime.subSequence(stringLength-2, stringLength);
			secondsTotal = Integer.valueOf((String) seconds);
		}
		else
		{
			CharSequence seconds= stringTime.subSequence(stringLength-1, stringLength);
			secondsTotal = Integer.valueOf((String) seconds);
			proceed = false;
		}

		// minutes
		if ((stringLength > 5) && proceed)
		{
			CharSequence minutes = stringTime.subSequence(stringLength-5, stringLength-3);
			minutesTotal = Integer.valueOf((String) minutes);
		}
		else if (stringLength == 5)
		{
			CharSequence minutes = stringTime.subSequence(stringLength-5, stringLength-3);
			minutesTotal = Integer.valueOf((String) minutes);
			proceed = false;
		}
		else if (stringLength == 4)
		{
			CharSequence minutes= stringTime.subSequence(stringLength-4, stringLength-3);
			minutesTotal = Integer.valueOf((String) minutes);
			proceed = false;
		}
		else
		{
			proceed = false;
		}

		// hours            	
		if ((stringLength > 8) && proceed)
		{
			CharSequence hours = stringTime.subSequence(stringLength-8, stringLength-6);
			hoursTotal = Integer.valueOf((String) hours);
		}
		else if (stringLength == 8)
		{
			CharSequence hours = stringTime.subSequence(stringLength-8, stringLength-6);
			hoursTotal = Integer.valueOf((String) hours);
			proceed = false;
		}
		else if (stringLength == 7)
		{
			CharSequence hours = stringTime.subSequence(stringLength-7, stringLength-6);
			hoursTotal = Integer.valueOf((String) hours);
			proceed = false;
		}
		else
		{
			proceed = false;
		}

		int totalElapsedSeconds = secondsTotal + (minutesTotal * 60) + (hoursTotal * 60 * 60);

		return totalElapsedSeconds;
	}

	public static Integer convertSecondsToHH(int totalSeconds)
	{
		int days = Math.abs(totalSeconds / 86400);
		int secondsLeft = Math.abs(totalSeconds - days * 86400);
		int hours = Math.abs(secondsLeft / 3600);

		return hours;
	}

	public static Integer convertSecondsToMM(int totalSeconds)
	{
		int days = Math.abs(totalSeconds / 86400);
		int secondsLeft = Math.abs(totalSeconds - days * 86400);
		int hours = Math.abs(secondsLeft / 3600);
		secondsLeft = secondsLeft - hours * 3600;
		int minutes = Math.abs(secondsLeft / 60);

		return minutes;
	}

	public static Integer convertSecondsToSS(int totalSeconds)
	{
		int days = Math.abs(totalSeconds / 86400);
		int secondsLeft = Math.abs(totalSeconds - days * 86400);
		int hours = Math.abs(secondsLeft / 3600);
		secondsLeft = secondsLeft - hours * 3600;
		int minutes = Math.abs(secondsLeft / 60);
		secondsLeft = secondsLeft - minutes * 60;
		int seconds = Math.abs(secondsLeft);

		return seconds;
	}
	
	public static String convertSerialToDate(double serialTime)
	{
		Date date= DateUtil.getJavaDate(serialTime);
		String dateAsString = new SimpleDateFormat("MM/dd/yyyy").format(date);
		return dateAsString;
	}

	public static LocalTime getTimeStamp()
	{
		LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);

		return time;
	}

	public static LocalDate getDateStamp()
	{
		LocalDate date = LocalDate.now();

		return date;
	}

	public static double ceilingToNearestIncrementOfSeconds(int numberOfSeconds, int multipleToBeIncrementedToInSeconds)
	{
		double incrementedValue =  Math.ceil((double) numberOfSeconds / (double) multipleToBeIncrementedToInSeconds) * multipleToBeIncrementedToInSeconds;

		return incrementedValue;
	}

	public static double floorToNearestIncrementOfSeconds(int numberOfSeconds, int multipleToBeDecrementedToInSeconds)
	{
		double decrementedValue =  Math.floor((double) numberOfSeconds / (double) multipleToBeDecrementedToInSeconds) * multipleToBeDecrementedToInSeconds;

		return decrementedValue;
	}
}