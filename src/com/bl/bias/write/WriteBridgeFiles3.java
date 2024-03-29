package com.bl.bias.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.analyze.BridgeClosureAnalysis;
import com.bl.bias.app.BIASBridgeClosureAnalysisConfigPageController;
import com.bl.bias.app.BIASBridgeClosureAnalysisController;
import com.bl.bias.objects.BridgeAnalysisClosure;
import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.tools.DoesEventOccurDuringActiveMarineAccessPeriod;

public class WriteBridgeFiles3 extends WriteBridgeFiles2
{
	private Integer actualAllPeriodsClosureSumInSeconds = 0;
	private Integer actualWorstAllPeriodClosureInSeconds = 0;
	private Integer actualMarinePeriodsClosureSumInSeconds = 0;
	private Integer actualMarineWorstPeriodClosureInSeconds = 0;

	private Integer modifiedAllPeriodsClosureSumInSeconds = 0;
	private Integer modifiedAllPeriodMarinerAccessSumInSeconds = 0;

	private Integer reportedAllPeriodsClosureSumInSeconds = 0;
	private Integer reportedWorstAllPeriodClosureInSeconds = 0;
	private Integer reportedMarinePeriodsClosureSumInSeconds = 0;
	private Integer reportedMarineWorstPeriodClosureInSeconds = 0;

	private Integer actualAllPeriodMarinerAccessSumInSeconds = 0;
	private Integer actualWorstAllPeriodMarinerAccessInSeconds = 3600;
	private Integer actualMarinePeriodMarinerAccessSumInSeconds = 0;
	private Integer actualMarineWorstPeriodMarinerAccessInSeconds = 3600;

	private Boolean excludeBridgeRaiseTime = false;

	private Boolean debug = false;

	private String resultsMessage = getResultsMessageWrite2();

	private ArrayList<BridgeAnalysisClosure> closures = new ArrayList<BridgeAnalysisClosure>();

	private ArrayList<Integer> actualAllPeriodClosureSumsInSeconds = new ArrayList<Integer>();
	private ArrayList<Integer> reportedAllPeriodClosureSumsInSeconds = new ArrayList<Integer>();
	private ArrayList<Integer> actualAllPeriodMarinerAccessSumsInSeconds = new ArrayList<Integer>();

	private ArrayList<Integer> modifiedAllPeriodClosureSumsInSeconds = new ArrayList<Integer>();
	private ArrayList<Integer> modifiedAllPeriodMarinerAccessSumsInSeconds = new ArrayList<Integer>();

	private HashMap<Integer, Integer> actualMarinePeriodClosureSumsInSeconds = new HashMap<>();
	private HashMap<Integer, Integer> reportedMarinePeriodClosureSumsInSeconds = new HashMap<>();
	private HashMap<Integer, Double> modifiedAllPeriodMarinerAccessPercentages = new HashMap<>();
	private HashMap<Integer, Double> modifiedMarinePeriodMarinerAccessPercentages = new HashMap<>();
	private HashMap<Integer, Integer> actualMarinePeriodMarinerAvailabilitySumsInSeconds = new HashMap<>();

	public WriteBridgeFiles3(String textAreaContents, String locationOfInputFiles)
	{
		super(textAreaContents, locationOfInputFiles);
		
		// Bridge raise excluded?
		if (!BIASBridgeClosureAnalysisConfigPageController.getIncludeBridgeRaiseTimeInClosureTime())
			excludeBridgeRaiseTime = true;
		else
			excludeBridgeRaiseTime = false;

		// Get closures
		closures.addAll(BridgeClosureAnalysis.getClosures());

		// Set styles
		CellStyle style0 = workbook.createCellStyle();
		CellStyle style1 = workbook.createCellStyle();
		CellStyle style2 = workbook.createCellStyle();
		CellStyle style3 = workbook.createCellStyle();
		CellStyle style4 = workbook.createCellStyle();
		CellStyle style5 = workbook.createCellStyle();
		CellStyle style6 = workbook.createCellStyle();
		CellStyle style7 = workbook.createCellStyle();
		CellStyle style8 = workbook.createCellStyle();
		CellStyle style9 = workbook.createCellStyle();
		CellStyle style10 = workbook.createCellStyle();
		CellStyle style11 = workbook.createCellStyle();
		CellStyle style12 = workbook.createCellStyle();
		CellStyle style13 = workbook.createCellStyle();
		CellStyle style14 = workbook.createCellStyle();
		CellStyle style15 = workbook.createCellStyle();
		CellStyle style16 = workbook.createCellStyle();

		// Write Bridge Closures
		XSSFSheet oneHourStatisticsSheet = workbook.createSheet("1-Hour Periods");
		oneHourStatisticsSheet.setDisplayGridlines(false);

		if (BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive())
			resultsMessage += "\nWriting 1-Hour periods (with high-usage marine periods active)";
		else
			resultsMessage += "\nWriting 1-Hour periods";

		// Fonts
		// Font 0 - 14pt White text
		XSSFFont font0 = workbook.createFont();
		font0.setFontHeightInPoints((short) 14);
		font0.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		font0.setFontName("Calibri");

		// Font 1 - 11pt text
		XSSFFont font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 11);
		font1.setFontName("Calibri");

		// Font 2 - 8pt text
		XSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 8);
		font2.setFontName("Calibri");

		// Font 3 - 8pt text
		XSSFFont font3 = workbook.createFont();
		font3.setFontHeightInPoints((short) 8);
		font3.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
		font3.setFontName("Calibri");

		// Font 4 - 11pt text
		XSSFFont font4 = workbook.createFont();
		font4.setFontHeightInPoints((short) 11);
		font4.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
		font4.setFontName("Calibri");

		// Font 5 - 8pt text
		XSSFFont font5 = workbook.createFont();
		font5.setFontHeightInPoints((short) 8);
		font5.setColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
		font5.setFontName("Calibri");

		// Font 6 - 11pt text
		XSSFFont font6 = workbook.createFont();
		font6.setFontHeightInPoints((short) 11);
		font6.setColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
		font6.setFontName("Calibri");

		// Cell styles
		// Style 0 - Centered, non- wrapped 14pt, white text against black background
		style0.setAlignment(HorizontalAlignment.CENTER);  
		style0.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		style0.setFillPattern(FillPatternType.FINE_DOTS);
		style0.setWrapText(false);
		style0.setFont(font0);

		// Style 1 - Centered, wrapped, 11pt, black text
		style1.setAlignment(HorizontalAlignment.CENTER);  
		style1.setWrapText(true);
		style1.setFont(font1);

		// Style 2 - Left aligned, non-wrapped, 8pt, black text
		style2.setAlignment(HorizontalAlignment.LEFT);  
		style2.setWrapText(false);
		style2.setFont(font2);

		// Style 3 - Right aligned, non-wrapped, 11pt, black text
		style3.setAlignment(HorizontalAlignment.RIGHT);  
		style3.setWrapText(false);
		style3.setFont(font1);

		// Style 4 - Centered, wrapped, 11pt, black text, H:MM:SS
		style4.setDataFormat(workbook.createDataFormat().getFormat("h:mm:ss"));
		style4.setAlignment(HorizontalAlignment.CENTER);  
		style4.setWrapText(true);
		style4.setFont(font1);

		// Style 5 - Right aligned, non-wrapped, 11pt, black text
		style5.setAlignment(HorizontalAlignment.LEFT);  
		style5.setWrapText(false);
		style5.setFont(font1);

		// Style 6 - Left, wrapped, 11pt, black text
		style6.setAlignment(HorizontalAlignment.LEFT);  
		style6.setFont(font1);

		// Style 7 - Left, wrapped, 11pt, black text, DD:HH:MM:SS
		style7.setDataFormat(workbook.createDataFormat().getFormat("dd:HH:mm:ss"));
		style7.setAlignment(HorizontalAlignment.CENTER);  
		style7.setWrapText(true);
		style7.setFont(font1);

		// Style 8 - Left aligned, non-wrapped, 8pt, blue text
		style8.setAlignment(HorizontalAlignment.LEFT);  
		style8.setWrapText(false);
		style8.setFont(font3);

		// Style 9 - Centered, non-wrapped, 11pt, blue text
		style9.setAlignment(HorizontalAlignment.CENTER);  
		style9.setWrapText(false);
		style9.setFont(font4);

		// Style 10 - Left, wrapped, 11pt, blue text
		style10.setAlignment(HorizontalAlignment.LEFT);  
		style10.setFont(font4);

		// Style 11 - Left, wrapped, 11pt, blue text, DD:HH:MM:SS
		style11.setDataFormat(workbook.createDataFormat().getFormat("dd:HH:mm:ss"));
		style11.setAlignment(HorizontalAlignment.CENTER); 
		style11.setWrapText(true);
		style11.setFont(font4);

		// Style 12 - Centered, wrapped, 11pt, blue text, H:MM:SS
		style12.setDataFormat(workbook.createDataFormat().getFormat("h:mm:ss"));
		style12.setAlignment(HorizontalAlignment.CENTER);  
		style12.setWrapText(true);
		style12.setFont(font4);

		// Style 13 - Centered, non-wrapped, 11pt, black text, 1/10% precision
		style13.setAlignment(HorizontalAlignment.CENTER);  
		style13.setWrapText(false);
		style13.setDataFormat(workbook.createDataFormat().getFormat("#0.0%"));
		style13.setFont(font1);

		// Style 14 - Centered, non-wrapped, 11pt, blue text, 1/10% precision
		style14.setAlignment(HorizontalAlignment.CENTER);  
		style14.setWrapText(false);
		style14.setDataFormat(workbook.createDataFormat().getFormat("#0.0%"));
		style14.setFont(font4);

		// Style 15 - Left aligned, non-wrapped, 8pt, green text
		style15.setAlignment(HorizontalAlignment.LEFT);  
		style15.setWrapText(false);
		style15.setFont(font5);

		// Style 16 - Centered, wrapped, 11pt, green text
		style16.setAlignment(HorizontalAlignment.CENTER);  
		style16.setWrapText(true);
		style16.setFont(font6);

		// Header rows
		// Case name
		if (excludeBridgeRaiseTime)
			oneHourStatisticsSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
		else
			oneHourStatisticsSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));

		Row row;
		Cell cell;

		row = oneHourStatisticsSheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(style0);
		cell.setCellValue(BIASBridgeClosureAnalysisController.getAnalyzedLine()+" 1-Hour Periods");

		// Data headers
		row = oneHourStatisticsSheet.createRow(1);

		cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("Period #");

		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell.setCellValue("Time Period Start");

		cell = row.createCell(2);
		cell.setCellStyle(style1);
		cell.setCellValue("Time Period End");

		cell = row.createCell(3);
		cell.setCellStyle(style1);
		cell.setCellValue("Actual Closure Time During Period (h:mm:ss)");

		cell = row.createCell(4);
		cell.setCellStyle(style1);
		cell.setCellValue("Reported Closure Time During Period (h:mm:ss)");

		cell = row.createCell(5);
		cell.setCellStyle(style1);
		cell.setCellValue("Actual Mariner Access Time During Period (h:mm:ss)");

		cell = row.createCell(6);
		cell.setCellStyle(style1);
		cell.setCellValue("Mariner Access % (based on actual duration)");

		if (excludeBridgeRaiseTime)
		{
			cell = row.createCell(7);
			cell.setCellStyle(style16);
			cell.setCellValue("Modified Mariner Access %");
		}

		// Closures
		// Determine count of hours that the analysis period entails
		String ceilingIncrementAsString = BIASBridgeClosureAnalysisConfigPageController.getIncrementHourlyBucket();

		int oneHourPeriods = ((BridgeClosureAnalysis.getEndOfAnalysisPeriodInSeconds() - BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds()) / 3600);

		if (debug)
		{
			System.out.println("*** Debug for 1-hour periods follows ***\n");
		}
		
		for (int i = 0; i < oneHourPeriods; i++)
		{
			row = oneHourStatisticsSheet.createRow(i + 2);

			Integer timeOccupiedInThisPeriod = 0;
			Integer timeOccupiedInNextPeriod = 0;
			Integer modifiedTimeOccupiedInThisPeriod = 0;
			Integer modifiedTimeInThisPeriod = 3600;

			// Period
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(i + 1);

			// Time period start
			Integer startOfOneHourPeriod = (3600 * i) + BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds();
			cell = row.createCell(1);
			if ((BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive()) && (DoesEventOccurDuringActiveMarineAccessPeriod.doesEventOccurDuringActiveMarineAccessPeriod(startOfOneHourPeriod)))
				cell.setCellStyle(style9);
			else
				cell.setCellStyle(style1);

			cell.setCellValue(ConvertDateTime.convertSecondsToDay_HHMMString(startOfOneHourPeriod));

			// Time period end
			Integer endOfOneHourPeriod = (3600 * (i + 1)) + BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds();
			cell = row.createCell(2);
			// Below makes style of text for end-of-period the same as the start-of-period 
			if ((BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive()) && (DoesEventOccurDuringActiveMarineAccessPeriod.doesEventOccurDuringActiveMarineAccessPeriod(startOfOneHourPeriod)))
				cell.setCellStyle(style9);
			else
				cell.setCellStyle(style1);
			cell.setCellValue(ConvertDateTime.convertSecondsToDay_HHMMString(endOfOneHourPeriod));
			
			// Compute periods where bridge is not available to marine traffic
			timeOccupiedInThisPeriod = timeOccupiedInNextPeriod;
			timeOccupiedInNextPeriod = 0;

			for (int j = 0; j < closures.size(); j++)
			{
				// 1.  Closure that is entirely contained in a future period
				if (closures.get(j).getClosureStartTimeInSeconds() > endOfOneHourPeriod) 
				{
					continue;
				}
				// 2.  Closure that is entirely contained in a past period
				else if (closures.get(j).getClosureEndTimeInSeconds() < startOfOneHourPeriod) 
				{
					continue;
				}
				// 3.  Closure that is entirely contained in this period
				else if ((closures.get(j).getClosureStartTimeInSeconds() >= startOfOneHourPeriod) && (closures.get(j).getClosureEndTimeInSeconds() <= endOfOneHourPeriod)) 
				{
					// Figure actual times here
					timeOccupiedInThisPeriod += (closures.get(j).getClosureEndTimeInSeconds() - closures.get(j).getClosureStartTimeInSeconds());

					// Figure modified times here
					// If excluding raise time
					if (!BIASBridgeClosureAnalysisConfigPageController.getIncludeBridgeRaiseTimeInClosureTime())
					{	
						//Numerator
						modifiedTimeOccupiedInThisPeriod += (closures.get(j).getBridgeUpStartTimeInSeconds() - closures.get(j).getPlannedBridgeDownStartTimeInSeconds()); 

						//Denominator
						modifiedTimeInThisPeriod -= (closures.get(j).getBridgeUpCompleteTimeInSeconds() - closures.get(j).getBridgeUpStartTimeInSeconds());

						if (debug)
						{
							System.out.println("period: "+(i+1)+" start of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(startOfOneHourPeriod)+" end of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(endOfOneHourPeriod));
							System.out.println("closure: "+(j+1));
							System.out.println("timeOccupiedInThisPeriod: "+timeOccupiedInThisPeriod+" modifiedTimeOccupiedInThisPeriod: "+modifiedTimeOccupiedInThisPeriod);
							System.out.println("timeInThisPeriod: 3600 modifiedTimeInThisPeriod: "+modifiedTimeInThisPeriod);
							System.out.println("type 3");
						}
					}
				}
				// 4.  First closure AND first period AND closure ends in first period
				else if ((j == 0) && (i == 0) && (closures.get(j).getClosureEndTimeInSeconds() <= endOfOneHourPeriod)) 
				{
					// Figure actual times here
					timeOccupiedInThisPeriod = (closures.get(j).getClosureEndTimeInSeconds() - Math.max(startOfOneHourPeriod, closures.get(j).getPlannedBridgeDownStartTimeInSeconds()));

					// Figure modified times here
					// If excluding raise time
					if (!BIASBridgeClosureAnalysisConfigPageController.getIncludeBridgeRaiseTimeInClosureTime())
					{	
						//Numerator
						modifiedTimeOccupiedInThisPeriod = (closures.get(j).getBridgeUpStartTimeInSeconds() - Math.max(startOfOneHourPeriod, closures.get(j).getPlannedBridgeDownStartTimeInSeconds()));
						if (modifiedTimeOccupiedInThisPeriod < 0)
							modifiedTimeOccupiedInThisPeriod = 0;

						//Denominator
						if (closures.get(j).getBridgeUpStartTimeInSeconds() < startOfOneHourPeriod)
						{
							modifiedTimeInThisPeriod -= (closures.get(j).getBridgeUpCompleteTimeInSeconds() - startOfOneHourPeriod);
						}
						else
							modifiedTimeInThisPeriod -= (closures.get(j).getBridgeUpCompleteTimeInSeconds() - closures.get(j).getBridgeUpStartTimeInSeconds());

						if (debug)
						{
							System.out.println("period: "+(i+1)+" start of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(startOfOneHourPeriod)+" end of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(endOfOneHourPeriod));
							System.out.println("closure: "+(j+1));
							System.out.println("timeOccupiedInThisPeriod: "+timeOccupiedInThisPeriod+" modifiedTimeOccupiedInThisPeriod: "+modifiedTimeOccupiedInThisPeriod);
							System.out.println("timeInThisPeriod: 3600 modifiedTimeInThisPeriod: "+modifiedTimeInThisPeriod);
							System.out.println("type 4");
						}
					}
				}
				// 5.  First closure AND first period AND closure ends after first period
				else if ((j == 0) && (i == 0) && (closures.get(j).getClosureEndTimeInSeconds() > endOfOneHourPeriod)) 
				{
					// Figure actual times here
					timeOccupiedInThisPeriod = (endOfOneHourPeriod - Math.max(startOfOneHourPeriod, closures.get(j).getPlannedBridgeDownStartTimeInSeconds()));
					timeOccupiedInNextPeriod = (closures.get(j).getClosureEndTimeInSeconds() - endOfOneHourPeriod);

					// Figure modified times here
					// If excluding raise time
					if (!BIASBridgeClosureAnalysisConfigPageController.getIncludeBridgeRaiseTimeInClosureTime())
					{	
						if ((closures.get(j).getBridgeUpStartTimeInSeconds() <= endOfOneHourPeriod)  && (closures.get(j).getBridgeUpCompleteTimeInSeconds() > endOfOneHourPeriod)) // Must account for part of modified time occupied in this period
						{
							//Numerator
							modifiedTimeOccupiedInThisPeriod += (closures.get(j).getBridgeUpStartTimeInSeconds() - Math.max(startOfOneHourPeriod, closures.get(j).getPlannedBridgeDownStartTimeInSeconds())); 

							// Denominator
							modifiedTimeInThisPeriod -= (endOfOneHourPeriod - (closures.get(j).getBridgeUpStartTimeInSeconds())); 
						}
						else
						{
							// Numerator
							modifiedTimeOccupiedInThisPeriod += (endOfOneHourPeriod - Math.max(startOfOneHourPeriod, closures.get(j).getPlannedBridgeDownStartTimeInSeconds())); 

							//Denominator
							modifiedTimeInThisPeriod -= 0;
						}
						if (debug)
						{
							System.out.println("period: "+(i+1)+" start of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(startOfOneHourPeriod)+" end of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(endOfOneHourPeriod));
							System.out.println("closure: "+(j+1));
							System.out.println("type 5");
						}
					}
				}
				// 6.  Closure ends in next period BUT bridge starts up in this period.  Must account for part of modified time occupied in this period
				else if ((closures.get(j).getBridgeUpStartTimeInSeconds() <= endOfOneHourPeriod) && (closures.get(j).getClosureEndTimeInSeconds() > endOfOneHourPeriod))
				{
					// Figure actual times here
					timeOccupiedInThisPeriod += (endOfOneHourPeriod - Math.max(startOfOneHourPeriod, closures.get(j).getPlannedBridgeDownStartTimeInSeconds()));

					//Numerator
					modifiedTimeOccupiedInThisPeriod += (closures.get(j).getBridgeUpStartTimeInSeconds() - Math.max(startOfOneHourPeriod, closures.get(j).getPlannedBridgeDownStartTimeInSeconds())); 

					//Denominator
					modifiedTimeInThisPeriod -= (endOfOneHourPeriod - (closures.get(j).getBridgeUpStartTimeInSeconds())); 

					if (debug)
					{
						System.out.println("period: "+(i+1)+" start of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(startOfOneHourPeriod)+" end of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(endOfOneHourPeriod));
						System.out.println("closure: "+(j+1));
						System.out.println("timeOccupiedInThisPeriod: "+timeOccupiedInThisPeriod+" modifiedTimeOccupiedInThisPeriod: "+modifiedTimeOccupiedInThisPeriod);
						System.out.println("timeInThisPeriod: 3600 modifiedTimeInThisPeriod: "+modifiedTimeInThisPeriod);
						System.out.println("type 6");
					}
				}	
				// 7.  Place part of closure in this period and part of closure in next period(s)
				else if (closures.get(j).getClosureEndTimeInSeconds() > endOfOneHourPeriod) 
				{
					// Figure actual times here
					timeOccupiedInThisPeriod += (endOfOneHourPeriod - Math.max(closures.get(j).getClosureStartTimeInSeconds(), startOfOneHourPeriod));
					timeOccupiedInNextPeriod = (closures.get(j).getClosureEndTimeInSeconds() - endOfOneHourPeriod);

					if (!BIASBridgeClosureAnalysisConfigPageController.getIncludeBridgeRaiseTimeInClosureTime())
					{	
						if (closures.get(j).getBridgeUpStartTimeInSeconds() <= endOfOneHourPeriod) // Must account for part of modified time occupied in this period
						{
							//Numerator
							modifiedTimeOccupiedInThisPeriod += (closures.get(j).getBridgeUpStartTimeInSeconds() - closures.get(j).getPlannedBridgeDownStartTimeInSeconds()); 

							//Denominator
							modifiedTimeInThisPeriod -= (endOfOneHourPeriod - closures.get(j).getBridgeUpStartTimeInSeconds());

							if (debug)
							{
								System.out.println("period: "+(i+1)+" start of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(startOfOneHourPeriod)+" end of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(endOfOneHourPeriod));
								System.out.println("closure: "+(j+1));
								System.out.println("timeOccupiedInThisPeriod: "+timeOccupiedInThisPeriod+" modifiedTimeOccupiedInThisPeriod: "+modifiedTimeOccupiedInThisPeriod);
								System.out.println("timeInThisPeriod: 3600 modifiedTimeInThisPeriod: "+modifiedTimeInThisPeriod);
								System.out.println("type 7.1");
							}
						}
						else // Modified time occupied extends into next period
						{
							//Numerator
							modifiedTimeOccupiedInThisPeriod += (endOfOneHourPeriod - Math.max(closures.get(j).getPlannedBridgeDownStartTimeInSeconds(), startOfOneHourPeriod)); 

							//Denominator
							modifiedTimeInThisPeriod -= 0;

							if (debug)
							{
								System.out.println("period: "+(i+1)+" start of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(startOfOneHourPeriod)+" end of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(endOfOneHourPeriod));
								System.out.println("closure: "+(j+1));
								System.out.println("timeOccupiedInThisPeriod: "+timeOccupiedInThisPeriod+" modifiedTimeOccupiedInThisPeriod: "+modifiedTimeOccupiedInThisPeriod);
								System.out.println("timeInThisPeriod: 3600 modifiedTimeInThisPeriod: "+modifiedTimeInThisPeriod);
								System.out.println("type 7.2"); 
							}
						}
					}
				}
				// 8.  Started in an earlier period AND ends in this period AND not first closure	
				else 
				{
					// Figure actual times here
					timeOccupiedInThisPeriod += (closures.get(j).getBridgeUpCompleteTimeInSeconds() - startOfOneHourPeriod);

					//Numerator
					modifiedTimeOccupiedInThisPeriod += (Math.max(closures.get(j).getBridgeUpStartTimeInSeconds(), startOfOneHourPeriod) - startOfOneHourPeriod); 

					//Denominator
					modifiedTimeInThisPeriod -= (closures.get(j).getBridgeUpCompleteTimeInSeconds() - Math.max(closures.get(j).getBridgeUpStartTimeInSeconds(), startOfOneHourPeriod)); 

					if (debug)
					{
						System.out.println("period: "+(i+1)+" start of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(startOfOneHourPeriod)+" end of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(endOfOneHourPeriod));
						System.out.println("closure: "+(j+1));
						System.out.println("timeOccupiedInThisPeriod: "+timeOccupiedInThisPeriod+" modifiedTimeOccupiedInThisPeriod: "+modifiedTimeOccupiedInThisPeriod);
						System.out.println("timeInThisPeriod: 3600 modifiedTimeInThisPeriod: "+modifiedTimeInThisPeriod);
						System.out.println("type 8");
					}
				}
			}
			
			if (timeOccupiedInThisPeriod == 0)
			{
				if (debug)
				{
					System.out.println("period: "+(i+1)+" start of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(startOfOneHourPeriod)+" end of 1-hour period: "+ConvertDateTime.convertSecondsToDayHHMMSSString(endOfOneHourPeriod));
					System.out.println("no closures in this period");
				}		
			}
			
			if (timeOccupiedInThisPeriod >= 3600)
				timeOccupiedInThisPeriod = 3600;

			// Actual duration computations - all periods
			actualAllPeriodClosureSumsInSeconds.add(i, timeOccupiedInThisPeriod);
			actualAllPeriodsClosureSumInSeconds += timeOccupiedInThisPeriod;
			actualAllPeriodMarinerAccessSumsInSeconds.add(i, (3600 - timeOccupiedInThisPeriod));
			actualAllPeriodMarinerAccessSumInSeconds += (3600 - timeOccupiedInThisPeriod);

			// Modified duration computations - all periods
			modifiedAllPeriodClosureSumsInSeconds.add(i, modifiedTimeOccupiedInThisPeriod);
			modifiedAllPeriodsClosureSumInSeconds += modifiedTimeOccupiedInThisPeriod;
			modifiedAllPeriodMarinerAccessSumsInSeconds.add(i, (modifiedTimeInThisPeriod - modifiedTimeOccupiedInThisPeriod));
			modifiedAllPeriodMarinerAccessSumInSeconds += (modifiedTimeInThisPeriod - modifiedTimeOccupiedInThisPeriod);
			modifiedAllPeriodMarinerAccessPercentages.put(i, (double) modifiedTimeOccupiedInThisPeriod/modifiedTimeInThisPeriod);

			// Duration computations - marine high-usage periods
			if ((BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive()) && (DoesEventOccurDuringActiveMarineAccessPeriod.doesEventOccurDuringActiveMarineAccessPeriod(startOfOneHourPeriod)))
			{
				// ACTUAL
				actualMarinePeriodClosureSumsInSeconds.put(i, timeOccupiedInThisPeriod);
				actualMarinePeriodsClosureSumInSeconds += timeOccupiedInThisPeriod;
				actualMarinePeriodMarinerAvailabilitySumsInSeconds.put(i, (3600 - timeOccupiedInThisPeriod));
				actualMarinePeriodMarinerAccessSumInSeconds += (3600 - timeOccupiedInThisPeriod);

				// MODIFIED
				modifiedMarinePeriodMarinerAccessPercentages.put(i, (double) modifiedTimeOccupiedInThisPeriod/modifiedTimeInThisPeriod);
			}

			// Reported duration computations - all periods
			if (ceilingIncrementAsString.equals("None"))
			{
				reportedAllPeriodClosureSumsInSeconds.add(i, timeOccupiedInThisPeriod);
				reportedAllPeriodsClosureSumInSeconds += timeOccupiedInThisPeriod;
			}
			else
			{
				int ceilingTimeInSeconds = (int) (Math.ceil((double) timeOccupiedInThisPeriod / (Integer.valueOf(ceilingIncrementAsString.replace("min", "").trim()) * 60)) * (Integer.valueOf(ceilingIncrementAsString.replace("min", "").trim()) * 60)); 
				reportedAllPeriodClosureSumsInSeconds.add(i, ceilingTimeInSeconds);
				reportedAllPeriodsClosureSumInSeconds += ceilingTimeInSeconds;
			}

			// Reported duration computations - marine high-usage periods
			if ((BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive()) && (DoesEventOccurDuringActiveMarineAccessPeriod.doesEventOccurDuringActiveMarineAccessPeriod(startOfOneHourPeriod)))
			{
				if (ceilingIncrementAsString.equals("None"))
				{
					reportedMarinePeriodClosureSumsInSeconds.put(i, timeOccupiedInThisPeriod);
					reportedMarinePeriodsClosureSumInSeconds += timeOccupiedInThisPeriod;
				}
				else
				{
					int ceilingTimeInSeconds = (int) (Math.ceil((double) timeOccupiedInThisPeriod / (Integer.valueOf(ceilingIncrementAsString.replace("min", "").trim()) * 60)) * (Integer.valueOf(ceilingIncrementAsString.replace("min", "").trim()) * 60)); 
					reportedMarinePeriodClosureSumsInSeconds.put(i, ceilingTimeInSeconds);
					reportedMarinePeriodsClosureSumInSeconds += ceilingTimeInSeconds;
				}
			}

			// Write sums - every period individually 
			cell = row.createCell(3);
			cell.setCellStyle(style4);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualAllPeriodClosureSumsInSeconds.get(i)));
			
			cell = row.createCell(4);
			cell.setCellStyle(style4);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(reportedAllPeriodClosureSumsInSeconds.get(i)));
			
			cell = row.createCell(5);
			cell.setCellStyle(style4);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualAllPeriodMarinerAccessSumsInSeconds.get(i)));
			
			cell = row.createCell(6);
			cell.setCellStyle(style13);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualAllPeriodMarinerAccessSumsInSeconds.get(i))/ConvertDateTime.convertSecondsToSerial(3600));
			
			if (excludeBridgeRaiseTime)
			{
				cell = row.createCell(7);
				cell.setCellStyle(style13);
				cell.setCellValue(ConvertDateTime.convertSecondsToSerial(modifiedAllPeriodMarinerAccessSumsInSeconds.get(i))/ConvertDateTime.convertSecondsToSerial(modifiedAllPeriodMarinerAccessSumsInSeconds.get(i) + modifiedAllPeriodClosureSumsInSeconds.get(i)));

				if (debug)
				{
					System.out.println("period: "+(i+1)+" numerator: "+modifiedAllPeriodMarinerAccessSumsInSeconds.get(i)+" denominator: "+(modifiedAllPeriodMarinerAccessSumsInSeconds.get(i)+modifiedAllPeriodClosureSumsInSeconds.get(i)));
					System.out.println((double) modifiedAllPeriodMarinerAccessSumsInSeconds.get(i)/((double) modifiedAllPeriodMarinerAccessSumsInSeconds.get(i) + (double) modifiedAllPeriodClosureSumsInSeconds.get(i))*100+"%\n");
				}					
			}

			// Worst period computations -- all periods
			// Actual
			if (actualAllPeriodClosureSumsInSeconds.get(i) > actualWorstAllPeriodClosureInSeconds)
			{
				actualWorstAllPeriodClosureInSeconds = actualAllPeriodClosureSumsInSeconds.get(i);
			}

			if (actualAllPeriodMarinerAccessSumsInSeconds.get(i) < actualWorstAllPeriodMarinerAccessInSeconds)
			{
				actualWorstAllPeriodMarinerAccessInSeconds = actualAllPeriodMarinerAccessSumsInSeconds.get(i);
			}

			// Reported
			if (reportedAllPeriodClosureSumsInSeconds.get(i) > reportedWorstAllPeriodClosureInSeconds)
			{
				reportedWorstAllPeriodClosureInSeconds = reportedAllPeriodClosureSumsInSeconds.get(i);
			}

			// Worst period computations -- marine periods
			if ((BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive()) && (DoesEventOccurDuringActiveMarineAccessPeriod.doesEventOccurDuringActiveMarineAccessPeriod(startOfOneHourPeriod)))
			{
				// Actual
				if (timeOccupiedInThisPeriod > actualMarineWorstPeriodClosureInSeconds)
				{
					actualMarineWorstPeriodClosureInSeconds = timeOccupiedInThisPeriod;
				}

				if (actualMarinePeriodMarinerAvailabilitySumsInSeconds.get(i) < actualMarineWorstPeriodMarinerAccessInSeconds)
				{
					actualMarineWorstPeriodMarinerAccessInSeconds = actualMarinePeriodMarinerAvailabilitySumsInSeconds.get(i);
				}				

				// Reported
				if (timeOccupiedInThisPeriod > reportedMarineWorstPeriodClosureInSeconds)
				{
					reportedMarineWorstPeriodClosureInSeconds = timeOccupiedInThisPeriod;
				}
			}
		}

		// Footer rows
		// Sum, mean and max (worst) of all cycles
		// Sum
		row = oneHourStatisticsSheet.createRow(oneHourPeriods + 3);
		cell = row.createCell(3);
		cell.setCellStyle(style7);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualAllPeriodsClosureSumInSeconds));

		cell = row.createCell(4);
		cell.setCellStyle(style7);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial(reportedAllPeriodsClosureSumInSeconds));

		cell = row.createCell(5);
		cell.setCellStyle(style7);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualAllPeriodMarinerAccessSumInSeconds));

		cell = row.createCell(6);
		cell.setCellStyle(style1);
		cell.setCellValue("N/A");

		if (excludeBridgeRaiseTime) 
		{
			cell = row.createCell(7);
			cell.setCellStyle(style1);
			cell.setCellValue("N/A");
		}

		if (excludeBridgeRaiseTime)
			cell = row.createCell(8);
		else
			cell = row.createCell(7);
		cell.setCellStyle(style6);
		cell.setCellValue("Sum of all periods (dd:hh:mm:ss)");

		// Worst
		row = oneHourStatisticsSheet.createRow(oneHourPeriods + 4);
		cell = row.createCell(3);
		cell.setCellStyle(style4);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualWorstAllPeriodClosureInSeconds));

		cell = row.createCell(4);
		cell.setCellStyle(style4);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial(reportedWorstAllPeriodClosureInSeconds));

		cell = row.createCell(5);
		cell.setCellStyle(style4);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualWorstAllPeriodMarinerAccessInSeconds));

		cell = row.createCell(6);
		cell.setCellStyle(style13);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualWorstAllPeriodMarinerAccessInSeconds)/ConvertDateTime.convertSecondsToSerial(3600));

		if (excludeBridgeRaiseTime) 
		{
			cell = row.createCell(7);
			cell.setCellStyle(style13);
			cell.setCellValue(1 - Collections.max(modifiedAllPeriodMarinerAccessPercentages.values()));
		}

		if (excludeBridgeRaiseTime)
			cell = row.createCell(8);
		else
			cell = row.createCell(7);
		cell.setCellStyle(style6);
		cell.setCellValue("Worst 1-hour period (h:mm:ss or %)");

		// Average
		row = oneHourStatisticsSheet.createRow(oneHourPeriods + 5);
		cell = row.createCell(3);
		cell.setCellStyle(style4);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial((int) ((double) actualAllPeriodsClosureSumInSeconds/actualAllPeriodClosureSumsInSeconds.size())));

		cell = row.createCell(4);
		cell.setCellStyle(style4);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial((int) ((double) reportedAllPeriodsClosureSumInSeconds/reportedAllPeriodClosureSumsInSeconds.size())));

		cell = row.createCell(5);
		cell.setCellStyle(style4);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial((int) ((double) actualAllPeriodMarinerAccessSumInSeconds/actualAllPeriodClosureSumsInSeconds.size())));

		cell = row.createCell(6);
		cell.setCellStyle(style13);
		cell.setCellValue((double) (actualAllPeriodMarinerAccessSumInSeconds/actualAllPeriodClosureSumsInSeconds.size())/3600);

		if (excludeBridgeRaiseTime) 
		{
			cell = row.createCell(7);
			cell.setCellStyle(style13);
			cell.setCellValue(1 - modifiedAllPeriodMarinerAccessPercentages.values().stream().mapToDouble(Double::doubleValue).average().orElse(0));
		}

		if (excludeBridgeRaiseTime)
			cell = row.createCell(8);
		else
			cell = row.createCell(7);
		cell.setCellStyle(style6);
		cell.setCellValue("Avg 1-hour period (h:mm:ss or %)");

		// Sum, mean and max of marine high-usage period cycles
		if (BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive())
		{
			//Sum
			row = oneHourStatisticsSheet.createRow(oneHourPeriods + 6);
			cell = row.createCell(3);
			cell.setCellStyle(style11);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualMarinePeriodsClosureSumInSeconds));

			cell = row.createCell(4);
			cell.setCellStyle(style11);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(reportedMarinePeriodsClosureSumInSeconds));

			cell = row.createCell(5);
			cell.setCellStyle(style11);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualMarinePeriodMarinerAccessSumInSeconds));

			cell = row.createCell(6);
			cell.setCellStyle(style11);
			cell.setCellValue("N/A");

			if (excludeBridgeRaiseTime) 
			{
				cell = row.createCell(7);
				cell.setCellStyle(style11);
				cell.setCellValue("N/A");
			}

			if (excludeBridgeRaiseTime)
				cell = row.createCell(8);
			else
				cell = row.createCell(7);
			cell.setCellStyle(style10);
			cell.setCellValue("Sum of marine high-usage periods (dd:hh:mm:ss)");

			// Worst
			row = oneHourStatisticsSheet.createRow(oneHourPeriods + 7);
			cell = row.createCell(3);
			cell.setCellStyle(style12);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualMarineWorstPeriodClosureInSeconds));

			cell = row.createCell(4);
			cell.setCellStyle(style12);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(reportedMarineWorstPeriodClosureInSeconds));

			cell = row.createCell(5);
			cell.setCellStyle(style12);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualMarineWorstPeriodMarinerAccessInSeconds));

			cell = row.createCell(6);
			cell.setCellStyle(style14);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualMarineWorstPeriodMarinerAccessInSeconds)/ConvertDateTime.convertSecondsToSerial(3600));

			if (excludeBridgeRaiseTime)
			{
				cell = row.createCell(7);
				cell.setCellStyle(style14);
				cell.setCellValue(1 - Collections.max(modifiedMarinePeriodMarinerAccessPercentages.values()));
			}

			if (excludeBridgeRaiseTime)
				cell = row.createCell(8);
			else
				cell = row.createCell(7);
			cell.setCellStyle(style10);
			cell.setCellValue("Worst 1-hour period (h:mm:ss or %)");

			//Average
			row = oneHourStatisticsSheet.createRow(oneHourPeriods + 8);
			cell = row.createCell(3);
			cell.setCellStyle(style12);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial((int) ((double) actualMarinePeriodsClosureSumInSeconds/actualMarinePeriodClosureSumsInSeconds.size())));

			cell = row.createCell(4);
			cell.setCellStyle(style12);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial((int) ((double) reportedMarinePeriodsClosureSumInSeconds/reportedMarinePeriodClosureSumsInSeconds.size())));

			cell = row.createCell(5);
			cell.setCellStyle(style12);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial((int) ((double) actualMarinePeriodMarinerAccessSumInSeconds/actualMarinePeriodMarinerAvailabilitySumsInSeconds.size())));

			cell = row.createCell(6);
			cell.setCellStyle(style14);
			cell.setCellValue((double) (actualMarinePeriodMarinerAccessSumInSeconds/actualMarinePeriodMarinerAvailabilitySumsInSeconds.size())/3600);

			if (excludeBridgeRaiseTime)
			{
				cell = row.createCell(7);
				cell.setCellStyle(style14);
				cell.setCellValue(1 - modifiedMarinePeriodMarinerAccessPercentages.values().stream().mapToDouble(Double::doubleValue).average().orElse(0));
			}

			if (excludeBridgeRaiseTime)
				cell = row.createCell(8);
			else
				cell = row.createCell(7);
			cell.setCellStyle(style10);
			cell.setCellValue("Avg 1-hour period (h:mm:ss or %)");
		}

		// Timestamp and footnote
		LocalDate creationDate = ConvertDateTime.getDateStamp();
		LocalTime creationTime = ConvertDateTime.getTimeStamp();

		if (BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive())
		{
			row = oneHourStatisticsSheet.createRow(oneHourPeriods + 9);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("Only complete 60-minute periods are reported.  ");

			row = oneHourStatisticsSheet.createRow(oneHourPeriods + 10);
			cell = row.createCell(0);
			cell.setCellStyle(style8);
			cell.setCellValue("Marine high-usage periods (1-hour periods which contain a recurring marine aceess period) are shown in blue.");

			if (excludeBridgeRaiseTime)
			{
				row = oneHourStatisticsSheet.createRow(oneHourPeriods + 11);
				cell = row.createCell(0);
				cell.setCellStyle(style15);
				if (!BIASBridgeClosureAnalysisConfigPageController.getIncludeBridgeRaiseTimeInClosureTime()) // Exclude raising of bridge
					cell.setCellValue("Modified Mariner Access % excludes bridge raise time from being assigned to railroad and mariners.  This results in some time periods being less than 60 minutes.");

				row = oneHourStatisticsSheet.createRow(oneHourPeriods + 12);
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("Created on "+creationDate+" at "+creationTime);
			}
			else
			{
				row = oneHourStatisticsSheet.createRow(oneHourPeriods + 11);
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("Created on "+creationDate+" at "+creationTime);
			}
		}
		else
		{
			row = oneHourStatisticsSheet.createRow(oneHourPeriods + 6);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("Only complete 60-minute periods are reported");

			if (excludeBridgeRaiseTime)
			{
				row = oneHourStatisticsSheet.createRow(oneHourPeriods + 7);
				cell = row.createCell(0);
				cell.setCellStyle(style15);
				if (!BIASBridgeClosureAnalysisConfigPageController.getIncludeBridgeRaiseTimeInClosureTime()) // Exclude raising of bridge
					cell.setCellValue("Modified Mariner Access % excludes bridge raise time from being assigned to railroad and mariners.  This results in some time periods being less than 60 minutes.");

				row = oneHourStatisticsSheet.createRow(oneHourPeriods + 8);
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("Created on "+creationDate+" at "+creationTime);
			}
			else
			{
				row = oneHourStatisticsSheet.createRow(oneHourPeriods + 7);
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("Created on "+creationDate+" at "+creationTime);
			}
		}

		// Resize all columns to fit the content size
		for (int i = 0; i <= 7; i++) 
		{
			if (i == 0) 
			{
				oneHourStatisticsSheet.setColumnWidth(i, 2500);
			}
			else if ((i == 1) || (i == 2))
			{
				oneHourStatisticsSheet.setColumnWidth(i, 3800);
			}
			else  
			{
				oneHourStatisticsSheet.setColumnWidth(i, 3500);
			}
		}
	}

	public String getResultsMessageWrite3()
	{
		return resultsMessage;
	}
}