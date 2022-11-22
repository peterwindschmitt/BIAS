package com.bl.bias.write;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.app.BIASRTCResultsAnalysisOptionsWindowController;
import com.bl.bias.objects.RTCResultsAnalysisGroupDataRow;
import com.bl.bias.read.ReadRTCResultsAnalysisGroupFiles;
import com.bl.bias.tools.ConvertDateTime;

public class WriteExtractedFiles1
{
	private LocalTime startWriteFileTime = LocalTime.now();
	protected String resultsMessage = "\nStarted writing output file at "+startWriteFileTime;
	
	Integer trainCount;
	Double averageSpeed;
	Double trainMiles;
	String elapsedStringTime;
	Integer elapsedSecondsTime;
	Double elapsedSerialTime;
	String elapsedPerTrainStringTime;
	Integer elapsedPerTrainSecondsTime;
	Double elapsedPerTrainSerialTime;
	String idealStringTime;
	Integer idealSecondsTime;
	Double idealSerialTime;
	Double trueSerialDelay;
	Integer secondsTrueDelay;
	Double serialTrueDelay;
	String trueDelayString;
	Double trueDelayMinutes100TM;
	Double trueDelayPerTrain;
	String otp;
	
	List<RTCResultsAnalysisGroupDataRow> sortedGroupFiles;
 		
	XSSFWorkbook workbook = new XSSFWorkbook();
    
	public WriteExtractedFiles1(File directory, String textAreaContents, Boolean writeEntireNetworkOnly, Boolean writeAllLines, Boolean writeTrainCount, Boolean writeVelocity, 
			Boolean writeTrainMiles, Boolean writeElapsedTime, Boolean writeElapsedTimePerTrain, Boolean writeIdealRunTime, Boolean writeTrueDelay, Boolean writeTrueDelayMinutes100TM, Boolean writeTrueDelayPerTrain, 
			Boolean writeOTP, Boolean writeRawData, Boolean writeSummaryData, Boolean writeGraphs, Boolean timeAsString, Boolean timeInSeconds, Boolean timeAsSerial)
	{
		// Set styles
		CellStyle style1 = workbook.createCellStyle();
        CellStyle style2 = workbook.createCellStyle();
        
		// Write group files
        if ((BIASRTCResultsAnalysisOptionsWindowController.getTrainGroup()) && (writeRawData))
        {
        	XSSFSheet rawByGroupSheet = workbook.createSheet("Trains by Group Raw Data");
            
        	// Header
	        style1.setWrapText(true); //Set wordwrap
	        style2.setDataFormat(workbook.createDataFormat().getFormat("0.00")); //Show two places after decimal point and one or more places before decimal always
	        
	        Row row = rawByGroupSheet.createRow(0);
	        row.createCell(0).setCellValue("File");
	    
	        row.createCell(1).setCellValue("Line/Subdivision");
	        resultsMessage += "\nWriting raw data for Line/Subdivision";
			
	        int colNum = 2;
	        row.createCell(colNum).setCellValue("Train Group");
	        resultsMessage +=", Train Group";
	        if (writeTrainCount)
	        {
	        	colNum++;
	        	row.createCell(colNum).setCellValue("Train Count");
	        	resultsMessage +=", Train Count";
	        }
	        if (writeVelocity)
	        {
	        	colNum++;
	        	row.createCell(colNum).setCellValue("Avg Speed");
	        	resultsMessage +=", Average Speed";
	        }
	        if (writeTrainMiles)
	        {
	        	colNum++;
	        	row.createCell(colNum).setCellValue("Train Miles");
	        	resultsMessage +=", Train Miles";
	        }
	        if (writeElapsedTime)
	        {
	        	if (timeAsString)
	        	{
	        		colNum++;
	        		Cell cell = row.createCell(colNum);
	        		cell.setCellStyle(style1);
	        		cell.setCellValue("Elapsed Time as String by Train Group");
	        	}
	        	if (timeInSeconds)
	        	{
	        		colNum++;
	        	   	Cell cell = row.createCell(colNum);
	        		cell.setCellStyle(style1);
	        	   	cell.setCellValue("Elapsed Time in Seconds by Train Group");
	        	}
	        	if (timeAsSerial)
	        	{
		        	colNum++;
		        	Cell cell = row.createCell(colNum);
	        		cell = row.createCell(colNum);
		        	cell.setCellStyle(style1);
		        	cell.setCellValue("Elapsed Time as Serial Date/Time by Train Group");
	        	}
	        	resultsMessage +=", Elapsed Time by Train Group";
	        }
	        if (writeElapsedTimePerTrain)
	        {
	        	if (timeAsString)
	        	{
	        		colNum++;
	        		Cell cell = row.createCell(colNum);
	        		cell.setCellStyle(style1);
	        		cell.setCellValue("Elapsed Time Per Train as String by Train Group");
	        	}
	        	if (timeInSeconds)
	        	{
	        		colNum++;
	        	   	Cell cell = row.createCell(colNum);
	        		cell.setCellStyle(style1);
	        	   	cell.setCellValue("Elapsed Time Per Train in Seconds by Train Group");
	        	}
	        	if (timeAsSerial)
	        	{
		        	colNum++;
		        	Cell cell = row.createCell(colNum);
	        		cell = row.createCell(colNum);
		        	cell.setCellStyle(style1);
		        	cell.setCellValue("Elapsed Time Per Train as Serial Date/Time by Train Group");
	        	}
	        	resultsMessage +=", Elapsed Time Per Train by Train Group";
	        }
	        if (writeIdealRunTime)
	        {
	        	if (timeAsString)
	        	{
		        	colNum++;
		        	Cell cell = row.createCell(colNum);
		        	cell.setCellStyle(style1);
		        	cell.setCellValue("Ideal Run Time as String by Train Group");
	        	}
	        	if (timeInSeconds)
	        	{
	        		colNum++;
		        	Cell cell = row.createCell(colNum);
		        	cell.setCellStyle(style1);
		        	cell.setCellValue("Ideal Run Time in Seconds by Train Group");
	        	}
	        	if (timeAsSerial)
	        	{
		        	colNum++;
		        	Cell cell = row.createCell(colNum);
		        	cell.setCellStyle(style1);
		        	cell.setCellValue("Ideal Run Time as Serial Date/Time by Train Group");
	        	}
	        	resultsMessage +=", Ideal Run Time by Train Group";
	        }
	        if (writeTrueDelay)
	        {
	        	if (timeAsString)
	        	{
	        		colNum++;
		        	Cell cell = row.createCell(colNum);
		        	cell.setCellStyle(style1);
		        	cell.setCellValue("True Delay as String by Train Group");
	        	}
	        	if (timeInSeconds)
	        	{
	        		colNum++;
	        		Cell cell = row.createCell(colNum);
	        		cell.setCellStyle(style1);
		        	cell.setCellValue("True Delay in Seconds by Train Group");
	        	}
	        	if (timeAsSerial)
	        	{
	        		colNum++;
	        		Cell cell = row.createCell(colNum);
	        		cell.setCellStyle(style1);
		        	cell.setCellValue("True Delay as Serial Date/Time by Train Group");
	        	}
	        	resultsMessage +=", True Delay by Train Group";
	        }
	        if (writeTrueDelayMinutes100TM)
	        {
	        	colNum++;
	        	Cell cell = row.createCell(colNum);
	        	cell.setCellStyle(style1);
	        	cell.setCellValue("True Delay Minutes per 100TM");
	        	resultsMessage +=", True Delay Minutes per 100TM";
	        }
	        if (writeTrueDelayPerTrain)
	        {
	        	colNum++;
	        	Cell cell = row.createCell(colNum);
	        	cell.setCellStyle(style1);
	        	cell.setCellValue("True Delay Minutes per Train");
	        	resultsMessage +=", True Delay Minutes per Train";
	        }
	        if (writeOTP)
	        {
	        	colNum++;
	        	resultsMessage +=", OTP";
	        	row.createCell(colNum).setCellValue("OTP");
	        }
	        resultsMessage +=" to output spreadsheet";
	        
	        // Sort group files here
	        sortedGroupFiles = new ArrayList<>(ReadRTCResultsAnalysisGroupFiles.returnGroupFiles());
	     	Collections.sort(sortedGroupFiles, new sortByGroup());
	     	
	     	for (int i = 0; i < sortedGroupFiles.size(); i++)
	     	{
		 		row = rawByGroupSheet.createRow(i+1);
		        
		        row.createCell(0).setCellValue(sortedGroupFiles.get(i).returnFileName().replace(".SUMMARY", ""));
		        row.createCell(1).setCellValue(sortedGroupFiles.get(i).returnLineName());
		        colNum = 2;
		        row.createCell(colNum).setCellValue(sortedGroupFiles.get(i).returnTrainGroup());
		        
		        // Compute train count
		        trainCount = sortedGroupFiles.get(i).returnTrainCount();
		        
		        // Write train count
		        if (writeTrainCount)
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(trainCount);
		        }
		        
		        // Compute velocity
		        averageSpeed = sortedGroupFiles.get(i).returnAvgSpeed();
		        
		        // Write velocity
		        if (writeVelocity) 
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(averageSpeed);
		        }
		        
		        // Compute train miles
		        trainMiles = sortedGroupFiles.get(i).returnTrainMiles();
		        
		        // Write train miles
		        if (writeTrainMiles) 
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(trainMiles);
		        }
		        
		        // Compute string of elapsed time
		        elapsedStringTime = sortedGroupFiles.get(i).returnElapsedTimeAsString();
		        
		        // Write string elapsed time
		        if ((writeElapsedTime) && (timeAsString))
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(elapsedStringTime);
		        }
		        	
		        // Compute elapsed time in seconds
		        elapsedSecondsTime = ConvertDateTime.convertDDHHMMSSStringToSeconds(elapsedStringTime);
		        
		        // Write elapsed time in seconds
		        if ((writeElapsedTime) && (timeInSeconds))
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(elapsedSecondsTime);
		        }
		        
		        // Compute serial elapsed time
		        // Convert seconds to serial date/time (DD:HH:MM:SS)
		        elapsedSerialTime = ConvertDateTime.convertSecondsToSerial(elapsedSecondsTime);
		        
		        // Write serial elapsed time
		        if ((writeElapsedTime) && (timeAsSerial))
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(elapsedSerialTime);
		        }
		        
		        // Compute string of elapsed time per train
		        elapsedPerTrainStringTime = ConvertDateTime.convertSecondsToDDHHMMSSString((int) ((double) elapsedSecondsTime / trainCount));
		        
		        // Write string elapsed time
		        if ((writeElapsedTimePerTrain) && (timeAsString))
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(elapsedPerTrainStringTime);
		        }
		        	
		        // Compute elapsed time per train in seconds
		        elapsedPerTrainSecondsTime = (int) ((double) elapsedSecondsTime / trainCount);
		        
		        // Write elapsed time per train in seconds
		        if ((writeElapsedTimePerTrain) && (timeInSeconds))
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(elapsedPerTrainSecondsTime);
		        }
		        
		        // Compute serial elapsed time per train
		        // Convert seconds to serial date/time (DD:HH:MM:SS)
		        elapsedPerTrainSerialTime = ConvertDateTime.convertSecondsToSerial(elapsedPerTrainSecondsTime);
		        
		        // Write serial elapsed time
		        if ((writeElapsedTimePerTrain) && (timeAsSerial))
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(elapsedPerTrainSerialTime);
		        }
		        
		     	// Compute string of ideal run time
		        idealStringTime = sortedGroupFiles.get(i).returnIdealRunTimeAsString(); 
		        
		        // Write string of ideal run time
		        if ((writeIdealRunTime) && (timeAsString))
		     	{
		        	colNum++;
		        	row.createCell(colNum).setCellValue(sortedGroupFiles.get(i).returnIdealRunTimeAsString());
		     	}
		        
		        // Compute seconds of ideal time
		        idealSecondsTime = ConvertDateTime.convertDDHHMMSSStringToSeconds(idealStringTime);
		        
		        // Write ideal time in seconds
		        if ((writeIdealRunTime) && (timeInSeconds))
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(idealSecondsTime);
		        }
		        
		        // Compute serial ideal time
		        // Convert seconds to serial date/time (DD:HH:MM:SS)
		        idealSerialTime = ConvertDateTime.convertSecondsToSerial(idealSecondsTime);
		        
		        // Write serial ideal time
		        if ((writeIdealRunTime) && (timeAsSerial))
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(idealSerialTime);
		        }
		        
		        // Compute true delay as string
		        trueDelayString = ConvertDateTime.convertSecondsToDDHHMMSSString(elapsedSecondsTime - idealSecondsTime);
	            
		        // Write true delay as string
		        if ((writeTrueDelay) && (timeAsString))
	            {
	            	colNum++;
	            	row.createCell(colNum).setCellValue(trueDelayString);
	            }
		        
		        // Compute seconds of true delay
		        secondsTrueDelay = elapsedSecondsTime - idealSecondsTime;
		        
		        // Write seconds of true delay
		        if ((writeTrueDelay) && (timeInSeconds))
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(secondsTrueDelay);
		        }
		        
		        // Compute serial of true delay
		        serialTrueDelay = elapsedSerialTime - idealSerialTime;
		        
		        // Write serial of true delay
		        if ((writeTrueDelay) && (timeAsSerial))
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(serialTrueDelay);
		        }
		        
		        //Compute delay minutes per 100 TM
		        trueDelayMinutes100TM = ((elapsedSerialTime - idealSerialTime) * 1440) / (trainMiles / 100);
	            
		        // Write delay mins per 100 TM
		        if (writeTrueDelayMinutes100TM)
	            {
	            	colNum++;
	            	Cell cell = row.createCell(colNum);
	            	cell.setCellStyle(style2);
	            	cell.setCellValue(trueDelayMinutes100TM);
	            }
		        
		        //Compute true delay per train
		        trueDelayPerTrain = ((elapsedSerialTime - idealSerialTime) * 1440) / (trainCount);
	            
		        // Write true delay per train
		        if (writeTrueDelayPerTrain)
	            {
	            	colNum++;
	            	Cell cell = row.createCell(colNum);
	            	cell.setCellStyle(style2);
	            	cell.setCellValue(trueDelayPerTrain);
	            }
		        
		        // Compute OTP
		        otp = sortedGroupFiles.get(i).returnOTP();
		       
		        // Write OTP
		        if (writeOTP)
		        {
		        	colNum++;
		        	if (otp.contains("-"))
		        		row.createCell(colNum).setCellValue("-----");
		        	else
		        		row.createCell(colNum).setCellValue(Double.valueOf(otp));
		        }
	     	}
				
	        // Turn on filters
		    rawByGroupSheet.setAutoFilter(new CellRangeAddress(0, sortedGroupFiles.size(), 0, colNum));
	        
		    // Resize all columns to fit the content size
		    for(int i = 0; i <= colNum + 1; i++) 
	        {
		    	if (i < 3)
		    	{
		    		rawByGroupSheet.autoSizeColumn(i);
		    		int currentWidth = rawByGroupSheet.getColumnWidth(i);
		    		currentWidth += 360;
		    		rawByGroupSheet.setColumnWidth(i, currentWidth);
		    	}
		    	else
		    	{
		    		rawByGroupSheet.setColumnWidth(i, 4100);
		    	}
	        }
		}
	}
	
	class sortByGroup implements Comparator<RTCResultsAnalysisGroupDataRow> 
	{ 
	    @Override
		public int compare(RTCResultsAnalysisGroupDataRow o1, RTCResultsAnalysisGroupDataRow o2) 
		{	    
	    	String x1 = o1.returnLineName();
	    	String x2 = o2.returnLineName();
	    	
	    	int sComp = x1.compareTo(x2);
	    	if (sComp != 0) 
	    	{
	    		return sComp;
	    	} 
	    	
	    	x1 = o1.returnTrainGroup();
	    	x2 = o2.returnTrainGroup();
	    	
	    	return x1.compareTo(x2);
		} 
	}
	
	public String getResultsMessageWrite1()
	{
		return resultsMessage;
	}
	
	public List<RTCResultsAnalysisGroupDataRow> returnSortedGroupFiles()
	{
		return sortedGroupFiles;
	}
}