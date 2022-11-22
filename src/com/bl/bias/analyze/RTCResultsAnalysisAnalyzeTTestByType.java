package com.bl.bias.analyze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.inference.TTest;

import com.bl.bias.app.BIASTtestPageController;
import com.bl.bias.objects.ResultFromTTest;
import com.bl.bias.objects.SampleForTTest;
import com.bl.bias.tools.ConvertDateTime;

public class RTCResultsAnalysisAnalyzeTTestByType 
{
	private String message = "";
	
	private final Boolean showMathDebugInConsole = false;
	
	private ArrayList<SampleForTTest> byTypeDataA;
	private ArrayList<SampleForTTest> byTypeDataB;
	
	private Boolean generateVelocity;
	private Boolean generateTrueDelayMinutesPer100TrainMiles;
	private Boolean generateElapsedRunTimePerTrain;
	private Boolean generateOtp;
	
	private HashSet<String> lines;
	private HashSet<String> categoriesForTypes;
	
	private static HashSet<ResultFromTTest> resultsByType = new HashSet<ResultFromTTest>();
	
	private int filesASize;
	
	private final double levelOfSignificance = BIASTtestPageController.getLOS();
	
	public RTCResultsAnalysisAnalyzeTTestByType(int filesASize, int filesBSize, HashSet<String> lines, HashSet<String> categoriesForTypes, ArrayList<SampleForTTest> byTypeDataA, ArrayList<SampleForTTest> byTypeDataB, Boolean generateVelocity, Boolean generateTrueDelayMinutesPer100TrainMiles, Boolean generateElapsedRunTimePerTrain, Boolean generateOtp)
	{
		this.filesASize = filesASize;
		
		this.lines = lines;
		this.categoriesForTypes = categoriesForTypes;
		
		this.byTypeDataA = byTypeDataA;
		this.byTypeDataB = byTypeDataB;
		
		this.generateVelocity = generateVelocity;
		this.generateTrueDelayMinutesPer100TrainMiles = generateTrueDelayMinutesPer100TrainMiles;
		this.generateElapsedRunTimePerTrain = generateElapsedRunTimePerTrain;
		this.generateOtp = generateOtp;
	}
	
	public Boolean analyzeByType()
	{
		Boolean error = false;
		
		// Add the data from the array
		Iterator<String> linesIterator = lines.iterator();
	    outerloop:
	    while(linesIterator.hasNext())  // by line
	    {
	        String line = linesIterator.next();
	        Iterator<String> categoriesForTypeIterator = categoriesForTypes.iterator();
	        while(categoriesForTypeIterator.hasNext())  // by category
		    {
		        String type = categoriesForTypeIterator.next();
		        
		        ArrayList<Double> velocityA = new ArrayList<Double>();
		        ArrayList<Double> velocityB = new ArrayList<Double>();
		        ArrayList<Double> delayMinutesPer100trainMilesA = new ArrayList<Double>();
		        ArrayList<Double> delayMinutesPer100trainMilesB = new ArrayList<Double>();
		        ArrayList<Double> elapsedRunTimePerTrainFromSerialA = new ArrayList<Double>();
		        ArrayList<Double> elapsedRunTimePerTrainFromSerialB = new ArrayList<Double>();
		        ArrayList<Double> elapsedRunTimePerTrainInSecondsA = new ArrayList<Double>();
		        ArrayList<Double> elapsedRunTimePerTrainInSecondsB = new ArrayList<Double>();
		        ArrayList<Double> elapsedRunTimePerTrainInSecondsFromStringA = new ArrayList<Double>();
		        ArrayList<Double> elapsedRunTimePerTrainInSecondsFromStringB = new ArrayList<Double>();
		        ArrayList<Double> otpA = new ArrayList<Double>();
		        ArrayList<Double> otpB = new ArrayList<Double>();
		        
				SummaryStatistics velocitySummaryStatisticA = new SummaryStatistics();
				SummaryStatistics velocitySummaryStatisticB = new SummaryStatistics();
				SummaryStatistics delayMinutesPer100trainMilesSummaryStatisticA = new SummaryStatistics();
				SummaryStatistics delayMinutesPer100trainMilesSummaryStatisticB = new SummaryStatistics();
				SummaryStatistics elapsedRunTimePerTrainFromSerialSummaryStatisticA = new SummaryStatistics();
				SummaryStatistics elapsedRunTimePerTrainFromSerialSummaryStatisticB = new SummaryStatistics();
				SummaryStatistics elapsedRunTimePerTrainInSecondsSummaryStatisticA = new SummaryStatistics();
				SummaryStatistics elapsedRunTimePerTrainInSecondsSummaryStatisticB = new SummaryStatistics();
				SummaryStatistics elapsedRunTimePerTrainInSecondsFromStringSummaryStatisticA = new SummaryStatistics();
				SummaryStatistics elapsedRunTimePerTrainInSecondsFromStringSummaryStatisticB = new SummaryStatistics();
				SummaryStatistics otpSummaryStatisticA = new SummaryStatistics();
				SummaryStatistics otpSummaryStatisticB = new SummaryStatistics();
				
				TTest testStatistic = new TTest();		
				
				for(int k = 0; k < byTypeDataA.size(); k++) 
				{
		    		if ((line.equals(byTypeDataA.get(k).getLine())) && (type.equals(byTypeDataA.get(k).getCategory())))
		    		{
		    			if (generateVelocity)
		    			{
		    				velocityA.add(byTypeDataA.get(k).getVelocity());
		    				velocitySummaryStatisticA.addValue(byTypeDataA.get(k).getVelocity());
		    			}
		    			if (generateTrueDelayMinutesPer100TrainMiles)
		    			{	
		    				delayMinutesPer100trainMilesA.add(byTypeDataA.get(k).getDelayMinutesPer100TM());
		    				delayMinutesPer100trainMilesSummaryStatisticA.addValue(byTypeDataA.get(k).getDelayMinutesPer100TM());
		    			}
		    			if (generateElapsedRunTimePerTrain)
		    			{
		    				elapsedRunTimePerTrainFromSerialA.add(byTypeDataA.get(k).getElapsedRunTimePerTrainAsSerialTime());																		// Serial
		    				elapsedRunTimePerTrainFromSerialSummaryStatisticA.addValue(byTypeDataA.get(k).getElapsedRunTimePerTrainAsSerialTime());													// Serial
		    				elapsedRunTimePerTrainInSecondsA.add(byTypeDataA.get(k).getElapsedRunTimePerTrainInSeconds());																			// Seconds
		    				elapsedRunTimePerTrainInSecondsSummaryStatisticA.addValue(byTypeDataA.get(k).getElapsedRunTimePerTrainInSeconds());														// Seconds
		    				if (byTypeDataA.get(k).getElapsedRunTimePerTrainAsString() != null)
		    				{
		    					elapsedRunTimePerTrainInSecondsFromStringA.add((double) ConvertDateTime.convertDDHHMMSSStringToSeconds(byTypeDataA.get(k).getElapsedRunTimePerTrainAsString()));			// String
		    					elapsedRunTimePerTrainInSecondsFromStringSummaryStatisticA.addValue(ConvertDateTime.convertDDHHMMSSStringToSeconds(byTypeDataA.get(k).getElapsedRunTimePerTrainAsString()));// String
		    				}
		    			}
		    			if (generateOtp)
		    			{
		    				double otpToAdd;
		    				if (byTypeDataA.get(k).getOtp().contains("-"))
		    				{
		    					otpToAdd = 0.0;
		    				}
		    				else
		    				{
		    					otpToAdd = Double.parseDouble(byTypeDataA.get(k).getOtp());
		    				}
		    				otpA.add(otpToAdd);
		    				otpSummaryStatisticA.addValue(otpToAdd);
		    			}
		    		}
				}
				for(int k = 0; k < byTypeDataB.size(); k++) 
				{
		    		if ((line.equals(byTypeDataB.get(k).getLine())) && (type.equals(byTypeDataB.get(k).getCategory())))
		    		{
		    			if (generateVelocity)
		    			{
		    				velocityB.add(byTypeDataB.get(k).getVelocity());
		    				velocitySummaryStatisticB.addValue(byTypeDataB.get(k).getVelocity());
		    			}
		    			if (generateTrueDelayMinutesPer100TrainMiles)
		    			{
		    				delayMinutesPer100trainMilesB.add(byTypeDataB.get(k).getDelayMinutesPer100TM());
		    				delayMinutesPer100trainMilesSummaryStatisticB.addValue(byTypeDataB.get(k).getDelayMinutesPer100TM());
		    			}
		    			if (generateElapsedRunTimePerTrain)
		    			{
		    				elapsedRunTimePerTrainFromSerialB.add(byTypeDataB.get(k).getElapsedRunTimePerTrainAsSerialTime());																		// Serial
		    				elapsedRunTimePerTrainFromSerialSummaryStatisticB.addValue(byTypeDataB.get(k).getElapsedRunTimePerTrainAsSerialTime());													// Serial
		    				elapsedRunTimePerTrainInSecondsB.add(byTypeDataB.get(k).getElapsedRunTimePerTrainInSeconds());																			// Seconds
		    				elapsedRunTimePerTrainInSecondsSummaryStatisticB.addValue(byTypeDataB.get(k).getElapsedRunTimePerTrainInSeconds());		 												// Seconds
		    				if (byTypeDataB.get(k).getElapsedRunTimePerTrainAsString() != null)
		    				{
		    					elapsedRunTimePerTrainInSecondsFromStringB.add((double) ConvertDateTime.convertDDHHMMSSStringToSeconds(byTypeDataB.get(k).getElapsedRunTimePerTrainAsString()));			// String
		    					elapsedRunTimePerTrainInSecondsFromStringSummaryStatisticB.addValue(ConvertDateTime.convertDDHHMMSSStringToSeconds(byTypeDataB.get(k).getElapsedRunTimePerTrainAsString()));// String
		    				}
		    			}
		    			if (generateOtp)
		    			{
			    			double otpToAdd;
			    			if (byTypeDataB.get(k).getOtp().contains("-"))
			    			{
			    				otpToAdd = 0.0;
			    			}
			    			else
			    			{
			    				otpToAdd = Double.parseDouble(byTypeDataB.get(k).getOtp());
			    			}
			    			otpB.add(otpToAdd);
			    			otpSummaryStatisticB.addValue(otpToAdd);
		    			}
		    		}
				}
				
				// Instantiate ResultFromTTest Object
				ResultFromTTest resultForTTest = new ResultFromTTest(line, type);
				
				// Check to ensure that critical t-value remains constant
				TDistribution criticalT = new TDistribution(filesASize-1);
				double criticalTvalue = criticalT.inverseCumulativeProbability(1 - (.5 * levelOfSignificance));
			    
			    resultForTTest.setTCritical(criticalTvalue);
			    
			    if (BIASTtestPageController.getCriticalT() != criticalTvalue)
				{
			    	BIASTtestPageController.setCriticalT(criticalTvalue);
			    }
			    else if (criticalTvalue != BIASTtestPageController.getCriticalT())
			    {
			    	error = true;
			    	message += "Mismatch in critical T-values found.  Verify consistent sample sizes and then rerun.\n";
			    	break outerloop;
			    }
			    
			    if (showMathDebugInConsole)
				{
					System.out.println("-START-BY-TYPE-RECORD------------------------------");
					System.out.println("line = "+line);
				    System.out.println("type = "+type);    
				    System.out.println("critical t-stat for "+filesASize+" samples ("+(filesASize-1)+" dof) = "+criticalTvalue);
				}
				
				if (generateVelocity)
				{
					Double aMeanVelocity = velocitySummaryStatisticA.getMean();
					Double bMeanVelocity = velocitySummaryStatisticB.getMean();
					
					if ((!aMeanVelocity.isNaN()) && (!bMeanVelocity.isNaN()))
					{
						double velocityMeanDiff = Math.abs((aMeanVelocity - bMeanVelocity));	
						double velocityTstatShortWay = Math.abs(testStatistic.pairedT(velocityA.stream().mapToDouble(Double::doubleValue).toArray(), velocityB.stream().mapToDouble(Double::doubleValue).toArray()));
						boolean pairedVelocitySignificantWithAlpha = testStatistic.pairedTTest(velocityA.stream().mapToDouble(Double::doubleValue).toArray(), velocityB.stream().mapToDouble(Double::doubleValue).toArray(), levelOfSignificance);
						
						resultForTTest.setVelocityA(aMeanVelocity);
						resultForTTest.setVelocityB(bMeanVelocity);
						resultForTTest.setAbsMeanVelocityDiff(velocityMeanDiff);
						resultForTTest.setVelocityTValue(velocityTstatShortWay);
						resultForTTest.setVelocitySignificant(pairedVelocitySignificantWithAlpha);
						
						if (showMathDebugInConsole)
						{
							System.out.println("\nA velocity mean = "+aMeanVelocity);
							System.out.println("B velocity mean = "+bMeanVelocity);
							System.out.println("velocity mean diff = "+velocityMeanDiff);
							System.out.println("velocity t-stat short way = "+velocityTstatShortWay);
							System.out.println("velocity paired reject null hypothesis (i.e., there is statistical significance between the two variables): " + pairedVelocitySignificantWithAlpha);
						}
					}
				}
				
				if (generateTrueDelayMinutesPer100TrainMiles)
				{
					Double aMeanDelayMinutesPer100trainMiles = delayMinutesPer100trainMilesSummaryStatisticA.getMean();
					Double bMeanDelayMinutesPer100trainMiles = delayMinutesPer100trainMilesSummaryStatisticB.getMean();
					
					if ((!aMeanDelayMinutesPer100trainMiles.isNaN()) && (!bMeanDelayMinutesPer100trainMiles.isNaN()))
					{	
						double delayMinutesPer100trainMilesMeanDiff = Math.abs((aMeanDelayMinutesPer100trainMiles - bMeanDelayMinutesPer100trainMiles));	
						double delayMinutesPer100trainMilesTstatShortWay = Math.abs(testStatistic.pairedT(delayMinutesPer100trainMilesA.stream().mapToDouble(Double::doubleValue).toArray(), delayMinutesPer100trainMilesB.stream().mapToDouble(Double::doubleValue).toArray()));
						boolean pairedDelayMinutesPer100trainMilesSignificantWithAlpha = testStatistic.pairedTTest(delayMinutesPer100trainMilesA.stream().mapToDouble(Double::doubleValue).toArray(), delayMinutesPer100trainMilesB.stream().mapToDouble(Double::doubleValue).toArray(), levelOfSignificance);

						resultForTTest.setDelayMinutesPer100TrainMilesA(aMeanDelayMinutesPer100trainMiles);
						resultForTTest.setDelayMinutesPer100TrainMilesB(bMeanDelayMinutesPer100trainMiles);
						resultForTTest.setAbsDelayMinutesPer100TrainMilesMeanDiff(delayMinutesPer100trainMilesMeanDiff);
						resultForTTest.setDelayMinutesPer100TrainMilesTValue(delayMinutesPer100trainMilesTstatShortWay);
						resultForTTest.setDelayMinutesPer100TrainMilesSignificant(pairedDelayMinutesPer100trainMilesSignificantWithAlpha);
						
						if (showMathDebugInConsole)
						{
							System.out.println("\nA dm/100tm mean = "+aMeanDelayMinutesPer100trainMiles);
						    System.out.println("B dm/100tm mean = "+bMeanDelayMinutesPer100trainMiles);
						    System.out.println("dm/100tm mean diff = "+delayMinutesPer100trainMilesMeanDiff);
						    System.out.println("dm/100tm t-stat short way = "+delayMinutesPer100trainMilesTstatShortWay);
						    System.out.println("dm/100tm paired reject null hypothesis (i.e., there is statistical significance between the two variables): " + pairedDelayMinutesPer100trainMilesSignificantWithAlpha);
						}
					}
				}
				
				if (generateElapsedRunTimePerTrain)
				{
					// Serial
					Double aMeanElapsedRunTimePerTrainAsSerial = elapsedRunTimePerTrainFromSerialSummaryStatisticA.getMean();
					Double bMeanElapsedRunTimePerTrainAsSerial = elapsedRunTimePerTrainFromSerialSummaryStatisticB.getMean();
					
					if ((!aMeanElapsedRunTimePerTrainAsSerial.isNaN()) && (!bMeanElapsedRunTimePerTrainAsSerial.isNaN()) && (aMeanElapsedRunTimePerTrainAsSerial > 0) && (bMeanElapsedRunTimePerTrainAsSerial > 0))
					{	
						double elapsedRunTimePerTrainAsSerialMeanDiff = Math.abs((aMeanElapsedRunTimePerTrainAsSerial - bMeanElapsedRunTimePerTrainAsSerial));	
						double elapsedRunTimePerTrainAsSerialTstatShortWay = Math.abs(testStatistic.pairedT(elapsedRunTimePerTrainFromSerialA.stream().mapToDouble(Double::doubleValue).toArray(), elapsedRunTimePerTrainFromSerialB.stream().mapToDouble(Double::doubleValue).toArray()));
						boolean pairedElapsedRunTimePerTrainsAsSerialSignificantWithAlpha = testStatistic.pairedTTest(elapsedRunTimePerTrainFromSerialA.stream().mapToDouble(Double::doubleValue).toArray(), elapsedRunTimePerTrainFromSerialB.stream().mapToDouble(Double::doubleValue).toArray(), levelOfSignificance);
						
						resultForTTest.setElapsedRunTimePerTrainAsSerialA(aMeanElapsedRunTimePerTrainAsSerial);
						resultForTTest.setElapsedRunTimePerTrainAsSerialB(bMeanElapsedRunTimePerTrainAsSerial);
						resultForTTest.setElapsedRunTimePerTrainAsSerialDiff(elapsedRunTimePerTrainAsSerialMeanDiff);
						resultForTTest.setElapsedRunTimeAsSerialTValue(elapsedRunTimePerTrainAsSerialTstatShortWay);
						resultForTTest.setElapsedRunTimeAsSerialSignificant(pairedElapsedRunTimePerTrainsAsSerialSignificantWithAlpha);
						
						if (showMathDebugInConsole)
						{
							System.out.println("\nA elapsed run time per train as serial mean = "+aMeanElapsedRunTimePerTrainAsSerial);
						    System.out.println("B elapsed run time per train as serial mean = "+bMeanElapsedRunTimePerTrainAsSerial);
						    System.out.println("elapsed run time per train as serial mean diff = "+elapsedRunTimePerTrainAsSerialMeanDiff);
						    System.out.println("elapsed run time per train as serial t-stat short way = "+elapsedRunTimePerTrainAsSerialTstatShortWay);
						    System.out.println("elapsed run time per train as serial paired reject null hypothesis (i.e., there is statistical significance between the two variables): " + pairedElapsedRunTimePerTrainsAsSerialSignificantWithAlpha);
						}
					}
					
					// Seconds
					Double aMeanElapsedRunTimePerTrainInSeconds = elapsedRunTimePerTrainInSecondsSummaryStatisticA.getMean();
					Double bMeanElapsedRunTimePerTrainInSeconds = elapsedRunTimePerTrainInSecondsSummaryStatisticB.getMean();
					
					if ((!aMeanElapsedRunTimePerTrainInSeconds.isNaN()) && (!bMeanElapsedRunTimePerTrainInSeconds.isNaN()) && (aMeanElapsedRunTimePerTrainInSeconds > 0) && (bMeanElapsedRunTimePerTrainInSeconds > 0))
					{	
						double elapsedRunTimePerTrainInSecondsMeanDiff = Math.abs((aMeanElapsedRunTimePerTrainInSeconds - bMeanElapsedRunTimePerTrainInSeconds));	
						double elapsedRunTimePerTrainInSecondsTstatShortWay = Math.abs(testStatistic.pairedT(elapsedRunTimePerTrainInSecondsA.stream().mapToDouble(Double::doubleValue).toArray(), elapsedRunTimePerTrainInSecondsB.stream().mapToDouble(Double::doubleValue).toArray()));
						boolean pairedElapsedRunTimePerTrainInSecondsSignificantWithAlpha = testStatistic.pairedTTest(elapsedRunTimePerTrainInSecondsA.stream().mapToDouble(Double::doubleValue).toArray(), elapsedRunTimePerTrainInSecondsB.stream().mapToDouble(Double::doubleValue).toArray(), levelOfSignificance);
						
						resultForTTest.setElapsedRunTimePerTrainInSecondsA(aMeanElapsedRunTimePerTrainInSeconds);
						resultForTTest.setElapsedRunTimePerTrainInSecondsB(bMeanElapsedRunTimePerTrainInSeconds);
						resultForTTest.setElapsedRunTimePerTrainInSecondsDiff(elapsedRunTimePerTrainInSecondsMeanDiff);
						resultForTTest.setElapsedRunTimeInSecondsTValue(elapsedRunTimePerTrainInSecondsTstatShortWay);
						resultForTTest.setElapsedRunTimeInSecondsSignificant(pairedElapsedRunTimePerTrainInSecondsSignificantWithAlpha);
						
						if (showMathDebugInConsole)
						{
							System.out.println("\nA elapsed run time per train in seconds mean = "+ aMeanElapsedRunTimePerTrainInSeconds);
						    System.out.println("B elapsed run time per train in seconds mean = "+bMeanElapsedRunTimePerTrainInSeconds);
						    System.out.println("elapsed run time per train in seconds mean diff = "+elapsedRunTimePerTrainInSecondsMeanDiff);
						    System.out.println("elapsed run time per train in seconds t-stat short way = "+elapsedRunTimePerTrainInSecondsTstatShortWay);
						    System.out.println("elapsed run time per train in seconds paired reject null hypothesis (i.e., there is statistical significance between the two variables): " + pairedElapsedRunTimePerTrainInSecondsSignificantWithAlpha);
						}
					}
					
					// String
					Double aMeanElapsedRunTimePerTrainInSecondsFromString = elapsedRunTimePerTrainInSecondsFromStringSummaryStatisticA.getMean();
					Double bMeanElapsedRunTimePerTrainInSecondsFromString = elapsedRunTimePerTrainInSecondsFromStringSummaryStatisticB.getMean();
					
					if ((!aMeanElapsedRunTimePerTrainInSecondsFromString.isNaN()) && (!bMeanElapsedRunTimePerTrainInSecondsFromString.isNaN()) && (aMeanElapsedRunTimePerTrainInSecondsFromString > 0) && (bMeanElapsedRunTimePerTrainInSecondsFromString > 0))
					{	
						double elapsedRunTimePerTrainInSecondsFromStringMeanDiff = Math.abs((aMeanElapsedRunTimePerTrainInSecondsFromString - bMeanElapsedRunTimePerTrainInSecondsFromString));	
						double elapsedRunTimePerTrainInSecondsFromStringTstatShortWay = Math.abs(testStatistic.pairedT(elapsedRunTimePerTrainInSecondsFromStringA.stream().mapToDouble(Double::doubleValue).toArray(), elapsedRunTimePerTrainInSecondsFromStringB.stream().mapToDouble(Double::doubleValue).toArray()));
						boolean pairedElapsedRunTimePerTrainInSecondsFromStringSignificantWithAlpha = testStatistic.pairedTTest(elapsedRunTimePerTrainInSecondsFromStringA.stream().mapToDouble(Double::doubleValue).toArray(), elapsedRunTimePerTrainInSecondsFromStringB.stream().mapToDouble(Double::doubleValue).toArray(), levelOfSignificance);
						
						resultForTTest.setElapsedRunTimePerTrainAsStringA(ConvertDateTime.convertSecondsToDDHHMMSSString(aMeanElapsedRunTimePerTrainInSecondsFromString.intValue()));
						resultForTTest.setElapsedRunTimePerTrainAsStringB(ConvertDateTime.convertSecondsToDDHHMMSSString(bMeanElapsedRunTimePerTrainInSecondsFromString.intValue()));
						resultForTTest.setElapsedRunTimePerTrainAsStringDiff(ConvertDateTime.convertSecondsToDDHHMMSSString((int) elapsedRunTimePerTrainInSecondsFromStringMeanDiff));
						resultForTTest.setElapsedRunTimeAsStringTValue(elapsedRunTimePerTrainInSecondsFromStringTstatShortWay);
						resultForTTest.setElapsedRunTimeAsStringSignificant(pairedElapsedRunTimePerTrainInSecondsFromStringSignificantWithAlpha);
						
						if (showMathDebugInConsole)
						{
							System.out.println("\nA elapsed run time per train as string mean = "+ConvertDateTime.convertSecondsToDDHHMMSSString(aMeanElapsedRunTimePerTrainInSecondsFromString.intValue()));
						    System.out.println("B elapsed run time per train as string mean = "+ConvertDateTime.convertSecondsToDDHHMMSSString(bMeanElapsedRunTimePerTrainInSecondsFromString.intValue()));
						    System.out.println("elapsed run time per train as string mean diff = "+ConvertDateTime.convertSecondsToDDHHMMSSString((int) elapsedRunTimePerTrainInSecondsFromStringMeanDiff));
						    System.out.println("elapsed run time per train as string t-stat short way = "+elapsedRunTimePerTrainInSecondsFromStringTstatShortWay);
						    System.out.println("elapsed run time per train as string paired reject null hypothesis (i.e., there is statistical significance between the two variables): " + pairedElapsedRunTimePerTrainInSecondsFromStringSignificantWithAlpha);
						}
					}
				}
							
				if (generateOtp)
				{
					Double aMeanOtp = otpSummaryStatisticA.getMean();
					Double bMeanOtp = otpSummaryStatisticB.getMean();
					
					if ((!aMeanOtp.isNaN()) && (!bMeanOtp.isNaN()))
					{
						double otpMeanDiff = Math.abs((aMeanOtp - bMeanOtp));
						double otpTstatShortWay = Math.abs(testStatistic.pairedT(otpA.stream().mapToDouble(Double::doubleValue).toArray(), otpB.stream().mapToDouble(Double::doubleValue).toArray()));
						boolean otpSignificantWithAlpha = testStatistic.pairedTTest(otpA.stream().mapToDouble(Double::doubleValue).toArray(), otpB.stream().mapToDouble(Double::doubleValue).toArray(), levelOfSignificance);
						
						resultForTTest.setOtpA(aMeanOtp);
						resultForTTest.setOtpB(bMeanOtp);
						resultForTTest.setOtpDiff(otpMeanDiff);
						resultForTTest.setOtpTValue(otpTstatShortWay);
						resultForTTest.setOtpSignificant(otpSignificantWithAlpha);
						
						if (showMathDebugInConsole)
						{
							System.out.println("\nA otp mean = "+aMeanOtp);
						    System.out.println("B otp mean = "+bMeanOtp);
						    System.out.println("otp mean diff = "+otpMeanDiff);
						    System.out.println("otp t-stat short way = "+otpTstatShortWay);
						    System.out.println("otp paired reject null hypothesis (i.e., there is statistical significance between the two variables): " + otpSignificantWithAlpha);
						}
					}	
				}
				
				resultsByType.add(resultForTTest);
					
				if (showMathDebugInConsole)
				{
				    System.out.println("-END-BY-TYPE-RECORD--------------------------------");
				}
		    }
	    }
	    message += "Attempted "+resultsByType.size()+" t-tests comparing train type\n";

	   return error;
	}
	
	private static double computeSumOfSquares(double[] parameterA, double[] parameterB)
	{
		double sumOfSquares = 0;
		for (int i = 0; i < parameterA.length; i++)
		{	
			sumOfSquares += Math.pow((parameterA[i] - parameterB[i]), 2);
		}
		
		return sumOfSquares;
	}
	
	public String getResultsMessage()
	{
		return message;
	}
	
	public static HashSet<ResultFromTTest> getTypeTTestResults()
	{
		return resultsByType;
	}
	
	public static void resetResults()
	{
		resultsByType.clear();
	}
}

//NOTE:  Below was to prove out velocity only!
//double velocitySquareOfSummedDiffs = Math.pow(StatUtils.sumDifference(velocityA.stream().mapToDouble(Double::doubleValue).toArray(), velocityB.stream().mapToDouble(Double::doubleValue).toArray()), 2);
//double velocitySumOfSquares = computeSumOfSquares(velocityA.stream().mapToDouble(Double::doubleValue).toArray(), velocityB.stream().mapToDouble(Double::doubleValue).toArray());
//double velocityVarianceOfDifferences = ((velocitySumOfSquares - (velocitySquareOfSummedDiffs / velocityA.size())))/(velocityA.size()-1);
//double velocityStandardDeviationOfDifferences = Math.sqrt(Math.abs(velocityVarianceOfDifferences));
//double velocityStandardError = (velocityStandardDeviationOfDifferences / Math.sqrt(velocityA.size()));
//double velocityTstatLongWay = Math.abs((aMeanVelocity - bMeanVelocity)/velocityStandardError);

//System.out.println("square of summed differences = "+velocitySquareOfSummedDiffs);
//System.out.println("sum of squares = "+velocitySumOfSquares);
//System.out.println("variance of differences = "+velocityVarianceOfDifferences);
//System.out.println("standard deviation of differences = "+velocityStandardDeviationOfDifferences);
//System.out.println("standard error = "+velocityStandardError);
//System.out.println("t-stat long way = "+velocityTstatLongWay);
