package com.bl.bias.write;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.AxisCrossBetween;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.AxisTickMark;
import org.apache.poi.xddf.usermodel.chart.BarDirection;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.app.BIASRTCResultsAnalysisConfigPageController;
import com.bl.bias.app.BIASRTCResultsAnalysisOptionsWindowController;
import com.bl.bias.objects.RTCResultsAnalysisGroupDataRow;
import com.bl.bias.read.ReadRTCResultsAnalysisGroupFiles;
import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.tools.ConvertNumberDatatypes;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class WriteExtractedFiles3 extends WriteExtractedFiles2
{
	XSSFSheet averageByGroupSheet;
	
	Integer firstDataRowToUseForThisGraph = 1;
	Integer lastDataRowToUseForThisGraph = 1;
	Integer firstRowForThisGraph = 1;
	Integer lastRowForThisGraph = 1;
	Integer firstColumnForThisGraph = 6;
	Integer lastColumnForThisGraph = 6;
		
	public WriteExtractedFiles3(File directory, String textAreaContents, Boolean writeEntireNetworkOnly, Boolean writeAllLines, Boolean writeTrainCount, Boolean writeVelocity, 
			Boolean writeTrainMiles, Boolean writeElapsedTime, Boolean writeElapsedRunTimePerTrain, Boolean writeIdealRunTime, Boolean writeTrueDelay, Boolean writeTrueDelayMinutes100TM, Boolean writeTrueDelayPerTrain, 
			Boolean writeOTP, Boolean writeRawData, Boolean writeSummaryData, Boolean writeGraphs, Boolean timeAsString, Boolean timeInSeconds, Boolean timeAsSerial)
	{
		super(directory, textAreaContents, writeEntireNetworkOnly, writeAllLines, writeTrainCount, writeVelocity, writeTrainMiles, writeElapsedTime, writeElapsedRunTimePerTrain, writeIdealRunTime, 
				writeTrueDelay, writeTrueDelayMinutes100TM, writeTrueDelayPerTrain, writeOTP, writeRawData, writeSummaryData, writeGraphs, timeAsString, timeInSeconds, timeAsSerial);
		
		ArrayList<String> lineNames;
		ArrayList<String> groupNames;
		HashMap<String, HashSet<String>> lineTypeRelationships = new HashMap<String, HashSet<String>>();
		
		Integer velocityDataCol = null;
		Integer dm100tmDataCol = null;
		Integer elapsedTimePerTrainCol = null;
		Integer otpDataCol = null;
		
		// Set styles
		CellStyle style1 = workbook.createCellStyle();
        
        // Write group files
        if (BIASRTCResultsAnalysisOptionsWindowController.getTrainGroup() && (writeSummaryData) && ((writeVelocity) || (writeElapsedRunTimePerTrain) ||(writeTrueDelayMinutes100TM) || (writeOTP)))
        {
        	averageByGroupSheet = workbook.createSheet("Trains by Group Summary Data");
        	
        	// Header
	        style1.setWrapText(true); //Set wordwrap
	        
	        Row row = averageByGroupSheet.createRow(0);
	        row.createCell(0).setCellValue("Line/Subdivision");
	        resultsMessage += "\nWriting summary (mean) data for Line/Subdivision";
			
	        int colNum = 1;
	        row.createCell(colNum).setCellValue("Train Group");
	        resultsMessage +=", Train Group";
	        if (writeVelocity)
	        {
	        	colNum++;
	        	row.createCell(colNum).setCellValue("Avg Speed");
	        	resultsMessage +=", Average Speed";
	        	velocityDataCol = colNum;
	        }
	        if (writeTrueDelayMinutes100TM)
	        {
	        	colNum++;
	        	Cell cell = row.createCell(colNum);
	        	cell.setCellStyle(style1);
	        	cell.setCellValue("True Delay Minutes per 100TM");
	        	resultsMessage +=", True Delay Minutes per 100TM";
	        	dm100tmDataCol = colNum;
	        }
	        if (writeElapsedRunTimePerTrain)
	        {
	        	colNum++;
	        	Cell cell = row.createCell(colNum);
	        	cell.setCellStyle(style1);
	        	cell.setCellValue("Elapsed Time per Train (Minutes)");
	        	resultsMessage +=", Elapsed Time per Train (Minutes)";
	        	elapsedTimePerTrainCol = colNum;
	        }
	        if (writeOTP)
	        {
	        	colNum++;
	        	Cell cell = row.createCell(colNum);
	        	cell.setCellStyle(style1);
	        	cell.setCellValue("OTP");
	        	resultsMessage +=", OTP";
	        	otpDataCol = colNum;
	        }
	        resultsMessage +=" to output spreadsheet";
	        if (BIASRTCResultsAnalysisConfigPageController.getGenerateGraphs())
	        	resultsMessage +=" (with graphs)";
	        
	        //  Create ArrayLists of group/line names
	        groupNames = new ArrayList<String>();
	        lineNames = new ArrayList<String>();
	        ArrayList<RTCResultsAnalysisGroupDataRow> groupFiles = ReadRTCResultsAnalysisGroupFiles.returnGroupFiles();
	        Iterator<RTCResultsAnalysisGroupDataRow> itr1 = groupFiles.iterator();
			while (itr1.hasNext())
			{
				RTCResultsAnalysisGroupDataRow fileToWorkWith = itr1.next();
				if (!groupNames.contains(fileToWorkWith.returnTrainGroup()))
					groupNames.add(fileToWorkWith.returnTrainGroup());
				if (!lineNames.contains(fileToWorkWith.returnLineName()))
					lineNames.add(fileToWorkWith.returnLineName());
			}
			
			// Sort the ArrayLists alphabetically
			Collections.sort(groupNames);
			Collections.sort(lineNames);
			
			// Store applicable data
			RTCResultsAnalysisGroupDataRow observationToWorkWith;
			String lineToWorkWith;
			String groupToWorkWith;
			int dataRow = 1;
			Boolean recordFound;
						
			// Records by group by line
			Iterator<String> itrLine = lineNames.iterator();
			while (itrLine.hasNext())
			{
				lineToWorkWith = itrLine.next();
				
				Iterator<String> itrGroup = groupNames.iterator();
				while (itrGroup.hasNext())
				{
					groupToWorkWith = itrGroup.next();
					
					Iterator<RTCResultsAnalysisGroupDataRow> itrRecord = ReadRTCResultsAnalysisGroupFiles.returnGroupFiles().iterator();
					ArrayList<Double> velocityResults = new ArrayList<Double>();
					ArrayList<Double> dm100tmResults = new ArrayList<Double>();
					ArrayList<Double> elapsedTimePerTrainResults = new ArrayList<Double>();
					ArrayList<Double> otpResults = new ArrayList<Double>();
					
					recordFound = false;
					
					while(itrRecord.hasNext())
					{
						observationToWorkWith = itrRecord.next();
						//  All lines
						if (lineTypeRelationships.get(observationToWorkWith.returnLineName()) == null)
						{
							lineTypeRelationships.put(observationToWorkWith.returnLineName(), new HashSet<String>());
						}
						if (!lineTypeRelationships.get(observationToWorkWith.returnLineName()).contains(observationToWorkWith.returnTrainGroup()))
						{
							lineTypeRelationships.get(observationToWorkWith.returnLineName()).add(observationToWorkWith.returnTrainGroup());
						}
						
						// Valid record found
						if ((observationToWorkWith.returnTrainGroup().equals(groupToWorkWith)) && (observationToWorkWith.returnLineName().equals(lineToWorkWith)))
						{
							recordFound = true;
							
							// Speed
							velocityResults.add(observationToWorkWith.returnAvgSpeed());
							
							// dm100tm
							double elapsedTimeInSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(observationToWorkWith.returnElapsedTimeAsString());
							double idealTimeInSeconds = ConvertDateTime.convertDDHHMMSSStringToSeconds(observationToWorkWith.returnIdealRunTimeAsString());
							double trainMiles = observationToWorkWith.returnTrainMiles();
							double dm100tm = ((elapsedTimeInSeconds - idealTimeInSeconds) / 60) / (trainMiles / 100);
							dm100tmResults.add(dm100tm);
							
							// elapsed run time per train
							double trainCount = observationToWorkWith.returnTrainCount();
							double elapsedTimePerTrainInSeconds = (elapsedTimeInSeconds/trainCount);
							elapsedTimePerTrainResults.add(elapsedTimePerTrainInSeconds);
							
							// otp
							if (observationToWorkWith.returnOTP().contains("-"))
								continue;
							else
								otpResults.add(Double.valueOf(observationToWorkWith.returnOTP()));
						}
					}
					
					if (recordFound == true)
					{
						DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
						double velocityMean;
						double dm100tmMean;
						double elapsedTimePerTrainMean;
						double otpMean;
						
						// Write to spreadsheet
						row = averageByGroupSheet.createRow(dataRow);
						row.createCell(0).setCellValue(lineToWorkWith);
				        row.createCell(1).setCellValue(groupToWorkWith);
				        colNum = 1;
				        
				        // Compute velocity stats
				        for (double v : velocityResults) 
				        {
				            descriptiveStatistics.addValue(v);
				        }
				        velocityMean = descriptiveStatistics.getMean();
				       
				        // Write average velocity
						if (writeVelocity)
						{
							colNum++;
							Cell cell = row.createCell(colNum);
							cell.setCellValue(ConvertNumberDatatypes.round(velocityMean, 1));
						}
						descriptiveStatistics.clear();
						
						// Compute dm100tm stats
						for (double v : dm100tmResults) 
					    {
					           descriptiveStatistics.addValue(v);
					    }
					    dm100tmMean = descriptiveStatistics.getMean();
					   
					    // Write dm100tm
						if (writeTrueDelayMinutes100TM)
						{
							colNum++;
							Cell cell = row.createCell(colNum);
							cell.setCellValue(ConvertNumberDatatypes.round(dm100tmMean, 1));
						}
						descriptiveStatistics.clear();
						
						// Compute elapsed time per train stats
						for (double v : elapsedTimePerTrainResults) 
					    {
					           descriptiveStatistics.addValue(v);
					    }
					    elapsedTimePerTrainMean = descriptiveStatistics.getMean();
					   
					    // Write elapsed time per train stats
						if (writeElapsedRunTimePerTrain)
						{
							colNum++;
							Cell cell = row.createCell(colNum);
							cell.setCellValue(ConvertNumberDatatypes.round(elapsedTimePerTrainMean/60, 1));
						}
						descriptiveStatistics.clear();
						
						// Compute OTP stats
						for (double v : otpResults) 
					    {
							descriptiveStatistics.addValue(v);
					    }
					    otpMean = descriptiveStatistics.getMean();
					    
					    // Write OTP
						if (writeOTP)
						{
							colNum++;
							Cell cell = row.createCell(colNum);
							if (otpResults.size() == 0)
							{
								cell.setCellValue(0);
							}
							else
							{
								cell.setCellValue(ConvertNumberDatatypes.round(otpMean, 1));
							}
						}
						descriptiveStatistics.clear();
												
						dataRow++;
					}				
				}
				
				if ((!itrLine.hasNext()) && (writeGraphs))
				{
					if (writeVelocity)
					{
						createGroupGraph(true, false, false, false, velocityDataCol, "Velocity for ", "MPH", "Train Group", lineTypeRelationships);
					}
					if (writeTrueDelayMinutes100TM)
					{
						createGroupGraph(false, true, false, false, dm100tmDataCol, "Delay for ", "DM/100TM", "Train Group", lineTypeRelationships);
					}
					if (writeElapsedRunTimePerTrain)
					{
						createGroupGraph(false, false, true, false, elapsedTimePerTrainCol, "Elapsed Run Time Per Train for ", "Minutes", "Train Group", lineTypeRelationships);
					}
					if (writeOTP)
					{	
						createGroupGraph(false, false, false, true, otpDataCol, "OTP for ", "% OTP", "Train Group", lineTypeRelationships);
					}
				}
			}
			
			// Resize all columns to fit the content size
	        for(int i = 0; i <= colNum + 1; i++) 
	        {
	        	if (i < 2)
		    	{
		    		averageByGroupSheet.autoSizeColumn(i);
		    		int currentWidth = averageByGroupSheet.getColumnWidth(i);
		    		currentWidth += 360;
		    		averageByGroupSheet.setColumnWidth(i, currentWidth);
		    	}
		    	else
		    	{
		    		averageByGroupSheet.setColumnWidth(i, 4100);
		    	}
	        }
        }
	}
	
	void createGroupGraph(Boolean velocityChart, Boolean dm100tmChart, Boolean elapsedTimePerTrainChart, Boolean otpChart, Integer dataCol, String chartHeader, String verticalAxis, String horizontalAxis, HashMap<String, HashSet<String>>lineTypeRelationships)
	{
		// Sort keys alphabetically
		TreeMap<String, HashSet<String>> sortedLineTypeRelationships = new TreeMap<>(); 
		sortedLineTypeRelationships.putAll(lineTypeRelationships);
			        
		Iterator<?> it = sortedLineTypeRelationships.entrySet().iterator();
	    while (it.hasNext()) 
	    {
	        Map.Entry pair = (Map.Entry)it.next();
	        HashSet<String> lines = new HashSet<String>();
	        lines = (HashSet<String>) pair.getValue();
	        lastDataRowToUseForThisGraph = firstDataRowToUseForThisGraph + lines.size() - 1;
	        lastRowForThisGraph = firstRowForThisGraph + 15;  //  Numerical value represents the height of each graph
	        lastColumnForThisGraph = firstColumnForThisGraph + 2 + (lines.size());  // First numerical value is min width of graph. Second value is for width added by train type
	        
	        // Create graph
			XSSFDrawing drawing = averageByGroupSheet.createDrawingPatriarch();
			XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, firstColumnForThisGraph, firstRowForThisGraph, lastColumnForThisGraph, lastRowForThisGraph);
			XSSFChart chart = drawing.createChart(anchor);
			
			chart.setTitleText(chartHeader+pair.getKey().toString().replace("line", "Line").replace("subdivision", "Subdivision"));
			chart.setTitleOverlay(false);

			// Use a category axis for the bottom axis
			XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
			bottomAxis.setTitle(horizontalAxis);
			bottomAxis.setMajorTickMark(AxisTickMark.NONE);

			XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
			leftAxis.setTitle(verticalAxis);
			leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);

			if (otpChart)
				leftAxis.setMaximum(100);

			XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange(averageByGroupSheet, new CellRangeAddress(firstDataRowToUseForThisGraph, lastDataRowToUseForThisGraph, 1, 1));       
			
			XDDFNumericalDataSource<Double> values = null;
			values = XDDFDataSourcesFactory.fromNumericCellRange(averageByGroupSheet, new CellRangeAddress(firstDataRowToUseForThisGraph, lastDataRowToUseForThisGraph, dataCol, dataCol));

			XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
			XDDFChartData.Series series = data.addSeries(categories, values);

			// Add data labels
			chart.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(0).addNewDLbls();
			chart.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(0).getDLbls().addNewShowLegendKey().setVal(false);
			chart.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(0).getDLbls().addNewShowCatName().setVal(false);
			chart.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(0).getDLbls().addNewShowSerName().setVal(false);

			// Show chart
			series.setTitle("", null); // https://stackoverflow.com/questions/21855842
			chart.plot(data);

			// Transform to column chart
			XDDFBarChartData bar = (XDDFBarChartData) data;
			bar.setBarDirection(BarDirection.COL);
	        
			firstDataRowToUseForThisGraph = lastDataRowToUseForThisGraph + 1;
	        firstColumnForThisGraph = lastColumnForThisGraph + 2;
	    }
	    
	    firstDataRowToUseForThisGraph = 1;
        firstRowForThisGraph = lastRowForThisGraph + 2;
        firstColumnForThisGraph = 6;
	}
	
	public String getResultsMessageWrite4()
	{
		return resultsMessage;
	}
}