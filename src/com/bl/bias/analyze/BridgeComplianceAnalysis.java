package com.bl.bias.analyze;

import java.util.ArrayList;

import com.bl.bias.app.BIASUscgBridgeComplianceAnalysisConfigPageController;
import com.bl.bias.objects.BridgeComplianceClosure;
import com.bl.bias.objects.MarineAccessPeriod;
import com.bl.bias.tools.ConvertDateTime;

import javafx.collections.ObservableList;

public class BridgeComplianceAnalysis 
{
	private static String resultsMessage;

	private ArrayList<BridgeComplianceClosure> closures;
	private ObservableList<MarineAccessPeriod> marineAccessPeriods;

	private static Integer marineAccessPeriodViolationCount = 0;  //  Can be assigned multiple times per closure
	private static Integer closureDurationViolationCount = 0;  // Can only be assigned once per closure
	private static Integer inCircuitCount = 0;

	private static Boolean debug = true;
	private static Boolean lastOpeningDelayed;
		
	public BridgeComplianceAnalysis(ArrayList<BridgeComplianceClosure> closures, ObservableList<MarineAccessPeriod> marineAccessPeriods) 
	{
		resultsMessage = "\nStarted creating bridge compliance results at "+ConvertDateTime.getTimeStamp();

		Double lastPeriodsMinimumDuration = 0.0;
		Double lastPeriodsRaiseTime = 0.0;

		// For each closure
		for (int i = 0; i < closures.size(); i++)
		{
			Integer rowNumber = closures.get(i).getSpreadsheetRowNumber();
			String startDay = closures.get(i).getClosureStartDay();
			String endDay = closures.get(i).getClosureEndDay();
			Double bridgeLowerTimeAsSerial = closures.get(i).getClosureStartTime();
			Double bridgeRaiseTimeAsSerial = closures.get(i).getClosureEndTime();
			Double closureDurationAsSerial = closures.get(i).getClosureDuration();
			
			// Type 0 Violation from VBA  
			// Bridge remains closed over maximum permitted duration (applies to all hours of the day -- even if during a marine access period.)
			// This type of violation can also be subject to marine access period violation(s)
			if ((bridgeRaiseTimeAsSerial - bridgeLowerTimeAsSerial) > (Double.valueOf(BIASUscgBridgeComplianceAnalysisConfigPageController.getMaxClosureMinutes()) / 1440))
			{
	            closureDurationViolationCount++;
	            closures.get(i).setClosureDurationViolation();
	            
	            if (debug)
				{
					System.out.print("A Type 0 Violation occurs on row "+(rowNumber + 1)+" due to a closure starting on "+startDay+" at "+ConvertDateTime.convertSerialToHHMMString(bridgeLowerTimeAsSerial)+
							" and ending on "+endDay+" at "+ConvertDateTime.convertSerialToHHMMString(bridgeRaiseTimeAsSerial)+ " (duration "+ConvertDateTime.convertSerialToHHMMString(closureDurationAsSerial));
					System.out.println(").  The maximum permitted closure duration is "+BIASUscgBridgeComplianceAnalysisConfigPageController.getMaxClosureMinutes() +" minutes."); 
				}
	        }
	        
			// For each marine access period
			for (int j = 0; j < marineAccessPeriods.size(); j++)
			{
				// Check to see if this opening negates the last delayed opening
				if ((bridgeLowerTimeAsSerial - lastPeriodsRaiseTime) > lastPeriodsMinimumDuration)
					lastOpeningDelayed = false;

				// Exclude this marine access period because both the beginning and end of it were before this closure
				if  ((((marineAccessPeriods.get(j).getMo().getValue()) && (startDay.equals("Monday"))			// Check that this marine period is valid on the same day as closure
						|| (marineAccessPeriods.get(j).getTu().getValue()) && (startDay.equals("Tuesday"))	
						|| (marineAccessPeriods.get(j).getWe().getValue()) && (startDay.equals("Wednesday"))
						|| (marineAccessPeriods.get(j).getTh().getValue()) && (startDay.equals("Thursday"))
						|| (marineAccessPeriods.get(j).getFr().getValue()) && (startDay.equals("Friday"))
						|| (marineAccessPeriods.get(j).getSa().getValue()) && (startDay.equals("Saturday"))
						|| (marineAccessPeriods.get(j).getSu().getValue()) && (startDay.equals("Sunday")))			
						&& ((bridgeLowerTimeAsSerial < marineAccessPeriods.get(j).getMarinePeriodStartDouble()) // and the bridge lowers before this period starts
						&& (bridgeRaiseTimeAsSerial < marineAccessPeriods.get(j).getMarinePeriodStartDouble())  // and the bridge raises before this period starts
						&& (!lastOpeningDelayed))))																// and the last opening was NOT delayed
				{
					continue;
				}
				
				// Exclude this marine access period because both the beginning and end of it were after this closure
				else if ((((marineAccessPeriods.get(j).getMo().getValue()) && (startDay.equals("Monday"))	  // Check that this marine period is valid on the same day as closure
						|| (marineAccessPeriods.get(j).getTu().getValue()) && (startDay.equals("Tuesday"))	
						|| (marineAccessPeriods.get(j).getWe().getValue()) && (startDay.equals("Wednesday"))
						|| (marineAccessPeriods.get(j).getTh().getValue()) && (startDay.equals("Thursday"))
						|| (marineAccessPeriods.get(j).getFr().getValue()) && (startDay.equals("Friday"))
						|| (marineAccessPeriods.get(j).getSa().getValue()) && (startDay.equals("Saturday"))
						|| (marineAccessPeriods.get(j).getSu().getValue()) && (startDay.equals("Sunday")))			
						&& ((bridgeLowerTimeAsSerial > marineAccessPeriods.get(j).getMarinePeriodEndDouble()) // and the bridge lowers after this period starts
						&& (bridgeRaiseTimeAsSerial > marineAccessPeriods.get(j).getMarinePeriodEndDouble())  // and the bridge raises after this period starts
						&& (!lastOpeningDelayed)))) 														  // and the last opening was NOT delayed
				{
					continue;
				}

				// Type 1 Violation from VBA  
				// Less than minimum opening duration after last closure delayed
				else if (((marineAccessPeriods.get(j).getMo().getValue()) && (startDay.equals("Monday"))	// Check that this marine period is valid on the same day as closure
						|| (marineAccessPeriods.get(j).getTu().getValue()) && (startDay.equals("Tuesday"))	
						|| (marineAccessPeriods.get(j).getWe().getValue()) && (startDay.equals("Wednesday"))
						|| (marineAccessPeriods.get(j).getTh().getValue()) && (startDay.equals("Thursday"))
						|| (marineAccessPeriods.get(j).getFr().getValue()) && (startDay.equals("Friday"))
						|| (marineAccessPeriods.get(j).getSa().getValue()) && (startDay.equals("Saturday"))
						|| (marineAccessPeriods.get(j).getSu().getValue()) && (startDay.equals("Sunday")))			
						&& (lastOpeningDelayed)																// and the last opening was delayed
						&& (bridgeLowerTimeAsSerial < (lastPeriodsRaiseTime + lastPeriodsMinimumDuration)))	// and the bridge lowers before the minimum required open duration of the previously delayed period  
				{
					if (debug)
					{
						System.out.print("A Type 1 Violation occurs on row "+(rowNumber + 1)+" due to a closure starting on "+startDay+" at "+ConvertDateTime.convertSerialToHHMMString(bridgeLowerTimeAsSerial)+
								" and ending on "+endDay+" at "+ConvertDateTime.convertSerialToHHMMString(bridgeRaiseTimeAsSerial)+ " (duration "+ConvertDateTime.convertSerialToHHMMString(closureDurationAsSerial));
						System.out.print(").  This did not permit a minimum opening time of "+ConvertDateTime.convertSerialToHHMMString(lastPeriodsMinimumDuration)); 
						System.out.println(" after the previous closure ended at "+ConvertDateTime.convertSerialToHHMMString(lastPeriodsRaiseTime)+".");
					}
					
					closures.get(i).setMarineAccessPeriodViolation();
					marineAccessPeriodViolationCount++;
					lastPeriodsMinimumDuration = 0.0;
					lastOpeningDelayed = false;
				}                 

				// Type 2 Violation from VBA
				// Bridge remains lowered over entirety of marine access period
				else if (((marineAccessPeriods.get(j).getMo().getValue()) && (startDay.equals("Monday"))				// Check that this marine period is valid on the same day as closure
						|| (marineAccessPeriods.get(j).getTu().getValue()) && (startDay.equals("Tuesday"))	
						|| (marineAccessPeriods.get(j).getWe().getValue()) && (startDay.equals("Wednesday"))
						|| (marineAccessPeriods.get(j).getTh().getValue()) && (startDay.equals("Thursday"))
						|| (marineAccessPeriods.get(j).getFr().getValue()) && (startDay.equals("Friday"))
						|| (marineAccessPeriods.get(j).getSa().getValue()) && (startDay.equals("Saturday"))
						|| (marineAccessPeriods.get(j).getSu().getValue()) && (startDay.equals("Sunday")))																							
						&& (bridgeLowerTimeAsSerial < (marineAccessPeriods.get(j).getMarinePeriodStartDouble())			// and the bridge lowers before the start of access period
								&& (bridgeRaiseTimeAsSerial > marineAccessPeriods.get(j).getMarinePeriodEndDouble()))) 	// and the bridge raises after the end of the access period 
				{
					closures.get(i).setMarineAccessPeriodViolation();
					marineAccessPeriodViolationCount++;
					lastPeriodsMinimumDuration = 0.0;

					if (debug)
					{
						System.out.print("A Type 2 Violation occurs on row "+(rowNumber + 1)+" due to a closure starting on "+startDay+" at "+ConvertDateTime.convertSerialToHHMMString(bridgeLowerTimeAsSerial)+
								" and ending on "+endDay+" at "+ConvertDateTime.convertSerialToHHMMString(bridgeRaiseTimeAsSerial)+ " (duration "+ConvertDateTime.convertSerialToHHMMString(closureDurationAsSerial));
						System.out.print(") which violates the marine access period from "+ConvertDateTime.convertSerialToHHMMString(marineAccessPeriods.get(j).getMarinePeriodStartDouble())+ 
								" to "+ConvertDateTime.convertSerialToHHMMString(marineAccessPeriods.get(j).getMarinePeriodEndDouble()));
						System.out.println(".  Total violations for this closure is "+closures.get(i).getMarineAccessPeriodViolation()+".");
					}
				}

				// Type 3 Violation from VBA:
				// Check that bridge does not lower during the marine access period 
				else if (((marineAccessPeriods.get(j).getMo().getValue()) && (startDay.equals("Monday"))				// Check that this marine period is valid on the same day as closure
						|| (marineAccessPeriods.get(j).getTu().getValue()) && (startDay.equals("Tuesday"))	
						|| (marineAccessPeriods.get(j).getWe().getValue()) && (startDay.equals("Wednesday"))
						|| (marineAccessPeriods.get(j).getTh().getValue()) && (startDay.equals("Thursday"))
						|| (marineAccessPeriods.get(j).getFr().getValue()) && (startDay.equals("Friday"))
						|| (marineAccessPeriods.get(j).getSa().getValue()) && (startDay.equals("Saturday"))
						|| (marineAccessPeriods.get(j).getSu().getValue()) && (startDay.equals("Sunday")))																							
						&& (bridgeLowerTimeAsSerial > (marineAccessPeriods.get(j).getMarinePeriodStartDouble())			// and the bridge lowers after start of access period
								&& (bridgeLowerTimeAsSerial < marineAccessPeriods.get(j).getMarinePeriodEndDouble()))) 	// and the bridge lowers before the end of the access period 
				{
					closures.get(i).setMarineAccessPeriodViolation();
					marineAccessPeriodViolationCount++;
					lastPeriodsMinimumDuration = 0.0;

					if (debug)
					{
						System.out.print("A Type 3 Violation occurs on row "+(rowNumber + 1)+" due to a closure starting on "+startDay+" at "+ConvertDateTime.convertSerialToHHMMString(bridgeLowerTimeAsSerial)+
								" and ending on "+endDay+" at "+ConvertDateTime.convertSerialToHHMMString(bridgeRaiseTimeAsSerial)+ " (duration "+ConvertDateTime.convertSerialToHHMMString(closureDurationAsSerial));
						System.out.print(") which violates the marine access period from "+ConvertDateTime.convertSerialToHHMMString(marineAccessPeriods.get(j).getMarinePeriodStartDouble())+ 
								" to "+ConvertDateTime.convertSerialToHHMMString(marineAccessPeriods.get(j).getMarinePeriodEndDouble()));
						System.out.println(".  Total violations for this closure is "+closures.get(i).getMarineAccessPeriodViolation()+".");
					}
				}

				// Type 4 Violation from VBA:
				// Check for the bridge opening after a permissible in-circuit delay (i.e., after the first x minutes of a marine access period) 
				else if (((marineAccessPeriods.get(j).getMo().getValue()) && (startDay.equals("Monday"))				  // Check that this marine period is valid on the same day as closure
						|| (marineAccessPeriods.get(j).getTu().getValue()) && (startDay.equals("Tuesday"))	
						|| (marineAccessPeriods.get(j).getWe().getValue()) && (startDay.equals("Wednesday"))
						|| (marineAccessPeriods.get(j).getTh().getValue()) && (startDay.equals("Thursday"))
						|| (marineAccessPeriods.get(j).getFr().getValue()) && (startDay.equals("Friday"))
						|| (marineAccessPeriods.get(j).getSa().getValue()) && (startDay.equals("Saturday"))
						|| (marineAccessPeriods.get(j).getSu().getValue()) && (startDay.equals("Sunday")))																							
						&& (bridgeRaiseTimeAsSerial > (marineAccessPeriods.get(j).getMarinePeriodStartDouble() + ((double) Integer.valueOf(BIASUscgBridgeComplianceAnalysisConfigPageController.getInCircuitPermissibleDelay()) / 1440))// and the bridge opens 5 (or the permitted in-circuit delay minutes) or more minutes after start of access period
								&& (bridgeLowerTimeAsSerial < marineAccessPeriods.get(j).getMarinePeriodStartDouble())))  // and the bridge lowers before the start of the access period 
				{
					closures.get(i).setMarineAccessPeriodViolation();
					marineAccessPeriodViolationCount++;
					lastPeriodsMinimumDuration = 0.0;

					if (debug)
					{
						System.out.print("A Type 4 Violation occurs on row "+(rowNumber + 1)+" due to a closure starting on "+startDay+" at "+ConvertDateTime.convertSerialToHHMMString(bridgeLowerTimeAsSerial)+
								" and ending on "+endDay+" at "+ConvertDateTime.convertSerialToHHMMString(bridgeRaiseTimeAsSerial)+ " (duration "+ConvertDateTime.convertSerialToHHMMString(closureDurationAsSerial));
						System.out.print(") which violates the marine access period from "+ConvertDateTime.convertSerialToHHMMString(marineAccessPeriods.get(j).getMarinePeriodStartDouble())+ 
								" to "+ConvertDateTime.convertSerialToHHMMString(marineAccessPeriods.get(j).getMarinePeriodEndDouble()));
						System.out.println(".  Total violations for this closure is "+closures.get(i).getMarineAccessPeriodViolation()+".");
					}
				}

				// Type 1 In-Circuit Delay from VBA:
				// Bridge opens within the in-circuit delay period
				else if (((marineAccessPeriods.get(j).getMo().getValue()) && (startDay.equals("Monday"))				  // Check that this marine period is valid on the same day as closure
						|| (marineAccessPeriods.get(j).getTu().getValue()) && (startDay.equals("Tuesday"))	
						|| (marineAccessPeriods.get(j).getWe().getValue()) && (startDay.equals("Wednesday"))
						|| (marineAccessPeriods.get(j).getTh().getValue()) && (startDay.equals("Thursday"))
						|| (marineAccessPeriods.get(j).getFr().getValue()) && (startDay.equals("Friday"))
						|| (marineAccessPeriods.get(j).getSa().getValue()) && (startDay.equals("Saturday"))
						|| (marineAccessPeriods.get(j).getSu().getValue()) && (startDay.equals("Sunday")))																							
						&& (bridgeRaiseTimeAsSerial < (marineAccessPeriods.get(j).getMarinePeriodStartDouble() + ((double) Integer.valueOf(BIASUscgBridgeComplianceAnalysisConfigPageController.getInCircuitPermissibleDelay()) / 1440))// and the bridge opens 5 (or the permitted in-circuit delay minutes) or less minutes after start of access period
								&& (bridgeRaiseTimeAsSerial > marineAccessPeriods.get(j).getMarinePeriodStartDouble())	  // and the bridge raises after the start of the access period
								&& (bridgeLowerTimeAsSerial < marineAccessPeriods.get(j).getMarinePeriodStartDouble())))  // and the bridge lowers before the start of the access period 
				{
					closures.get(i).setMarineAccessPeriodViolation();
					inCircuitCount++;
					lastOpeningDelayed = true;
					lastPeriodsRaiseTime = bridgeRaiseTimeAsSerial;
					lastPeriodsMinimumDuration = marineAccessPeriods.get(j).getMarinePeriodEndDouble() - marineAccessPeriods.get(j).getMarinePeriodStartDouble();

					if (debug)
					{
						System.out.print("A Type 1 In-Circuit Delay occurs on row "+(rowNumber + 1)+" due to a closure starting on "+startDay+" at "+ConvertDateTime.convertSerialToHHMMString(bridgeLowerTimeAsSerial)+
								" and ending on "+endDay+" at "+ConvertDateTime.convertSerialToHHMMString(bridgeRaiseTimeAsSerial)+ " (duration "+ConvertDateTime.convertSerialToHHMMString(closureDurationAsSerial));
						System.out.print(") which delays the marine access period from "+ConvertDateTime.convertSerialToHHMMString(marineAccessPeriods.get(j).getMarinePeriodStartDouble())+ 
								" to "+ConvertDateTime.convertSerialToHHMMString(marineAccessPeriods.get(j).getMarinePeriodEndDouble()));
						System.out.println(".  The next opening must be at least "+ConvertDateTime.convertSerialToHHMMString(lastPeriodsMinimumDuration) +" long.");
					}
					
					break;
				}
			}		
		}
		
		// Results
		if (debug)
		{
			System.out.println("Total closure duration violations: "+closureDurationViolationCount);
			System.out.println("Total marine access period violations: "+marineAccessPeriodViolationCount);
			System.out.println("Total in-circuit delays: "+inCircuitCount);
		}
		resultsMessage += "\nFound "+closureDurationViolationCount+" closure duration violations";
		resultsMessage += "\nFound "+marineAccessPeriodViolationCount+" marine access period violations";
		resultsMessage += "\nFound "+inCircuitCount+" delayed (in-circuit) marine access periods";
		resultsMessage += "\nFinished analysis at "+ConvertDateTime.getTimeStamp()+"\n";
	}

	public ArrayList<BridgeComplianceClosure> getClosures()
	{
		return closures;
	}

	public void clearClosures()
	{
		closures.clear();
	}

	public void clearMarineAccessPeriods()
	{
		marineAccessPeriods.clear();
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}