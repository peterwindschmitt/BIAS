package com.bl.bias.write;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.app.BIASRTCResultsAnalysisOptionsWindowController;
import com.bl.bias.objects.RTCResultsAnalysisTypeDataRow;
import com.bl.bias.read.ReadRTCResultsAnalysisTypeFiles;
import com.bl.bias.tools.ConvertDateTime;

public class WriteExtractedFiles2 extends WriteExtractedFiles1
{
	Integer trainCount;
	Double averageSpeed;
	Double trainMiles;
	String elapsedStringTime;
	Integer elapsedSecondsTime;
	Double elapsedSerialTime;
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
	
	List<RTCResultsAnalysisTypeDataRow> sortedTypeFiles;
 			
	public WriteExtractedFiles2(String textAreaContents, Boolean writeEntireNetworkOnly, Boolean writeAllLines, Boolean writeTrainCount, Boolean writeVelocity, 
			Boolean writeTrainMiles, Boolean writeElapsedTime, Boolean writeElapsedTimePerTrain, Boolean writeIdealRunTime, Boolean writeTrueDelay, Boolean writeTrueDelayMinutes100TM, Boolean writeTrueDelayPerTrain, 
			Boolean writeOTP, Boolean writeRawData, Boolean writeSummaryData, Boolean writeGraphs, Boolean timeAsString, Boolean timeInSeconds, Boolean timeAsSerial)
	{
		super(textAreaContents, writeEntireNetworkOnly, writeAllLines, writeTrainCount, writeVelocity, writeTrainMiles, writeElapsedTime, writeElapsedTimePerTrain, writeIdealRunTime, writeTrueDelay, 
				writeTrueDelayMinutes100TM, writeTrueDelayPerTrain, writeOTP, writeRawData, writeSummaryData, writeGraphs, timeAsString, timeInSeconds, timeAsSerial);
		
		// Set styles
		CellStyle style1 = workbook.createCellStyle();
        CellStyle style2 = workbook.createCellStyle();
        
		// Write type files
        if ((BIASRTCResultsAnalysisOptionsWindowController.getTrainType()) && (writeRawData))
        {
        	XSSFSheet rawByTypeSheet = workbook.createSheet("Trains by Type Raw Data");
            
        	// Header
	        style1.setWrapText(true); //Set wordwrap
	        style2.setDataFormat(workbook.createDataFormat().getFormat("0.00")); //Show two places after decimal point and one or more places before decimal always
	        
	        Row row = rawByTypeSheet.createRow(0);
	        row.createCell(0).setCellValue("File");
	        row.createCell(1).setCellValue("Line/Subdivision");
	        resultsMessage += "\nWriting raw data for Line/Subdivision";
			
	        int colNum = 2;
	        row.createCell(colNum).setCellValue("Train Type");
	        resultsMessage +=", Train Type";
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
	        		cell.setCellValue("Elapsed Time as String by Train Type");
	        	}
	        	if (timeInSeconds)
	        	{
	        		colNum++;
	        	   	Cell cell = row.createCell(colNum);
	        		cell.setCellStyle(style1);
	        	   	cell.setCellValue("Elapsed Time in Seconds by Train Type");
	        	}
	        	if (timeAsSerial)
	        	{
		        	colNum++;
		        	Cell cell = row.createCell(colNum);
	        		cell = row.createCell(colNum);
		        	cell.setCellStyle(style1);
		        	cell.setCellValue("Elapsed Time as Serial Date/Time by Train Type");
	        	}
	        	resultsMessage +=", Elapsed Time by Train Type";
	        }
	        if (writeElapsedTimePerTrain)
	        {
	        	if (timeAsString)
	        	{
	        		colNum++;
	        		Cell cell = row.createCell(colNum);
	        		cell.setCellStyle(style1);
	        		cell.setCellValue("Elapsed Time Per Train as String by Train Type");
	        	}
	        	if (timeInSeconds)
	        	{
	        		colNum++;
	        	   	Cell cell = row.createCell(colNum);
	        		cell.setCellStyle(style1);
	        	   	cell.setCellValue("Elapsed Time Per Train in Seconds by Train Type");
	        	}
	        	if (timeAsSerial)
	        	{
		        	colNum++;
		        	Cell cell = row.createCell(colNum);
	        		cell = row.createCell(colNum);
		        	cell.setCellStyle(style1);
		        	cell.setCellValue("Elapsed Time Per Train as Serial Date/Time by Train Type");
	        	}
	        	resultsMessage +=", Elapsed Time Per Train by Train Type";
	        }
	        if (writeIdealRunTime)
	        {
	        	if (timeAsString)
	        	{
		        	colNum++;
		        	Cell cell = row.createCell(colNum);
		        	cell.setCellStyle(style1);
		        	cell.setCellValue("Ideal Run Time as String by Train Type");
	        	}
	        	if (timeInSeconds)
	        	{
	        		colNum++;
		        	Cell cell = row.createCell(colNum);
		        	cell.setCellStyle(style1);
		        	cell.setCellValue("Ideal Run Time in Seconds by Train Type");
	        	}
	        	if (timeAsSerial)
	        	{
		        	colNum++;
		        	Cell cell = row.createCell(colNum);
		        	cell.setCellStyle(style1);
		        	cell.setCellValue("Ideal Run Time as Serial Date/Time by Train Type");
	        	}
	        	resultsMessage +=", Ideal Run Time by Train Type";
	        }
	        if (writeTrueDelay)
	        {
	        	if (timeAsString)
	        	{
	        		colNum++;
		        	Cell cell = row.createCell(colNum);
		        	cell.setCellStyle(style1);
		        	cell.setCellValue("True Delay as String by Train Type");
	        	}
	        	if (timeInSeconds)
	        	{
	        		colNum++;
	        		Cell cell = row.createCell(colNum);
	        		cell.setCellStyle(style1);
		        	cell.setCellValue("True Delay in Seconds by Train Type");
	        	}
	        	if (timeAsSerial)
	        	{
	        		colNum++;
	        		Cell cell = row.createCell(colNum);
	        		cell.setCellStyle(style1);
		        	cell.setCellValue("True Delay as Serial Date/Time by Train Type");
	        	}
	        	resultsMessage +=", True Delay by Train Type";
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
	        	row.createCell(colNum).setCellValue("OTP");
	        	resultsMessage +=", OTP";
	        }
	        resultsMessage +=" to output spreadsheet";
	       
	        // Sort type files here
	        sortedTypeFiles = new ArrayList<>(ReadRTCResultsAnalysisTypeFiles.returnTypeFiles());
	     	Collections.sort(sortedTypeFiles, new sortByType());	
	        
	     	for (int i = 0; i < sortedTypeFiles.size(); i++)
	     	{
		 		row = rawByTypeSheet.createRow(i+1);
				
		 		row.createCell(0).setCellValue(sortedTypeFiles.get(i).returnFileName().replace(".SUMMARY", ""));
	            row.createCell(1).setCellValue(sortedTypeFiles.get(i).returnLineName());
	            colNum = 2;
	            row.createCell(colNum).setCellValue(sortedTypeFiles.get(i).returnTrainType());
	            
	            // Compute train count
	            trainCount = sortedTypeFiles.get(i).returnTrainCount();
	            
	            // Write train count
	            if (writeTrainCount)
	            {
	            	colNum++;
	            	row.createCell(colNum).setCellValue(trainCount);
	            }
	            
	            // Compute train velocity
	            averageSpeed = sortedTypeFiles.get(i).returnAvgSpeed();
	            
	            // Write velocity
	            if (writeVelocity)
	            {
	            	colNum++;
	            	row.createCell(colNum).setCellValue(averageSpeed);
	            }
	            
	            // Compute train miles
	            trainMiles = sortedTypeFiles.get(i).returnTrainMiles();
	        	
	            // Write train miles
	            if (writeTrainMiles)
	            {
	            	colNum++;
	            	row.createCell(colNum).setCellValue(trainMiles);
	            }
	            
	            // Compute elapsed time as string
	            elapsedStringTime = sortedTypeFiles.get(i).returnElapsedTimeAsString();
	            
	            // Write elapsed time as string
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
		        
		        if ((writeElapsedTime) && (timeAsSerial))
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(elapsedSerialTime);	
		        }
	     	
		        // Compute elapsed time per train as string
	            elapsedPerTrainStringTime = ConvertDateTime.convertSecondsToDDHHMMSSString((int) ((double) elapsedSecondsTime / trainCount));
	            
	            // Write elapsed time per train as string
	            if ((writeElapsedTimePerTrain) && (timeAsString))
	            {
	            	colNum++;
	            	row.createCell(colNum).setCellValue(elapsedPerTrainStringTime);
	            }
	            
	            // Compute elapsed time per train in seconds
	            elapsedPerTrainSecondsTime = ((int) ((double) elapsedSecondsTime / trainCount));	
	            
	            // Write elapsed time per train in seconds
	            if ((writeElapsedTimePerTrain) && (timeInSeconds))
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(elapsedPerTrainSecondsTime);
		        }
		        
		        // Compute serial elapsed time per train
		        // Convert seconds to serial date/time (DD:HH:MM:SS)
		        elapsedPerTrainSerialTime = ConvertDateTime.convertSecondsToSerial(elapsedPerTrainSecondsTime);
		        
		        if ((writeElapsedTimePerTrain) && (timeAsSerial))
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(elapsedPerTrainSerialTime);	
		        }
	     	
		        // Compute ideal time as string
	            idealStringTime = sortedTypeFiles.get(i).returnIdealRunTimeAsString();
		        
	            // Write ideal time as string
	            if ((writeIdealRunTime) && (timeAsString))
	            {
	            	colNum++;
	            	row.createCell(colNum).setCellValue(idealStringTime);
	            }
	            	
	            // Compute ideal time in seconds
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
		        
		        // Compute serial true delay
		        // Convert seconds to serial date/time (DD:HH:MM:SS)
		        serialTrueDelay = ConvertDateTime.convertSecondsToSerial(secondsTrueDelay);
		        
		        // Write serial true delay
		        if ((writeTrueDelay) && (timeAsSerial))
		        {
		        	colNum++;
		        	row.createCell(colNum).setCellValue(serialTrueDelay);
		        }
		        
		        // Compute delay mins/ 100 TM
		        trueDelayMinutes100TM = ((elapsedSerialTime - idealSerialTime) * 1440) / (trainMiles / 100);
	            
		        // Write delay mins per 100 TM
		        if (writeTrueDelayMinutes100TM)
	            {
	            	colNum++;
	            	Cell cell = row.createCell(colNum);
	            	cell.setCellStyle(style2);
	            	cell.setCellValue(trueDelayMinutes100TM);
	            }
		        
		        // Compute delay mins per train
		        trueDelayPerTrain = ((elapsedSerialTime - idealSerialTime) * 1440) / (trainCount);
	            
		        // Write delay mins per train
		        if (writeTrueDelayPerTrain)
	            {
	            	colNum++;
	            	Cell cell = row.createCell(colNum);
	            	cell.setCellStyle(style2);
	            	cell.setCellValue(trueDelayPerTrain);
	            }
		        
		        // Compute OTP
		        otp = sortedTypeFiles.get(i).returnOTP();
		        
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
		    rawByTypeSheet.setAutoFilter(new CellRangeAddress(0, sortedTypeFiles.size(), 0, colNum));
		    
		    // Resize all columns to fit the content size
	        for(int i = 0; i <= colNum + 1; i++) 
	        {
	        	if (i < 3)
		    	{
		    		rawByTypeSheet.autoSizeColumn(i);
		    		int currentWidth = rawByTypeSheet.getColumnWidth(i);
		    		currentWidth += 360;
		    		rawByTypeSheet.setColumnWidth(i, currentWidth);
		    	}
		    	else
		    	{
		    		rawByTypeSheet.setColumnWidth(i, 4100);
		    	}
	        }    
        }
	}
	
	class sortByType implements Comparator<RTCResultsAnalysisTypeDataRow> 
	{ 
	    @Override
		public int compare(RTCResultsAnalysisTypeDataRow o1, RTCResultsAnalysisTypeDataRow o2) 
		{	    
	    	String x1 = o1.returnLineName();
	    	String x2 = o2.returnLineName();
	    	
	    	int sComp = x1.compareTo(x2);
	    	if (sComp != 0) 
	    	{
	    		return sComp;
	    	} 
	    	
	    	x1 = o1.returnTrainType();
	    	x2 = o2.returnTrainType();
	    	
	    	return x1.compareTo(x2);
		} 
	} 
	
	public String getResultsMessageWrite2()
	{
		return resultsMessage;
	}
	
	public List<RTCResultsAnalysisTypeDataRow> returnSortedTypeFiles()
	{
		return sortedTypeFiles;
	}
}