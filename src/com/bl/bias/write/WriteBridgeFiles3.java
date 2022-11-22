package com.bl.bias.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

public class WriteBridgeFiles3 extends WriteBridgeFiles2
{
	String dayOfWeek;
	String hourlyBucket;

	Integer actualAllPeriodsSumInSeconds = 0;
	Integer actualWorstPeriodInSeconds = 0;
	Integer actualMarinePeriodsSumInSeconds = 0;
	Integer actualMarineWorstPeriodInSeconds = 0;

	Integer reportedAllPeriodsSumInSeconds = 0;
	Integer reportedWorstAllPeriodInSeconds = 0;
	Integer reportedMarinePeriodsSumInSeconds = 0;
	Integer reportedMarineWorstPeriodInSeconds = 0;

	String resultsMessage = getResultsMessageWrite2();

	ArrayList<BridgeAnalysisClosure> closures = new ArrayList<BridgeAnalysisClosure>();
	ArrayList<Integer> actualPeriodSumsInSeconds = new ArrayList<Integer>();
	ArrayList<Integer> reportedPeriodSumsInSeconds = new ArrayList<Integer>();
	HashMap<Integer, Integer> actualMarinePeriodSumsInSeconds = new HashMap<>();
	HashMap<Integer, Integer> reportedMarinePeriodSumsInSeconds = new HashMap<>();;

	public WriteBridgeFiles3(String textAreaContents, String locationOfInputFiles)
	{
		super(textAreaContents, locationOfInputFiles);

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

		// Write Bridge Closures
		XSSFSheet hourlyStatisticsSheet = workbook.createSheet("Hourly Periods");
		if (BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive())
			resultsMessage += "\nWriting hourly periods (with high-usage marine periods active)";
		else
			resultsMessage += "\nWriting hourly periods";

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

		// Header rows
		// Case name
		hourlyStatisticsSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

		Row row;
		Cell cell;

		row = hourlyStatisticsSheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(style0);
		cell.setCellValue(BIASBridgeClosureAnalysisController.getAnalyzedLine() + " [reported duration increment = "+BIASBridgeClosureAnalysisConfigPageController.getIncrementHourlyBucket().toLowerCase()+"]");

		// Data headers
		row = hourlyStatisticsSheet.createRow(1);

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

		// Closures
		// Determine count of hours that the analysis period entails
		String ceilingIncrementAsString = BIASBridgeClosureAnalysisConfigPageController.getIncrementHourlyBucket();

		int oneHourPeriods = ((BridgeClosureAnalysis.getEndOfAnalysisPeriodInSeconds() - BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds()) / 3600);

		Integer timeOccupiedInThisPeriod = 0;
		Integer timeOccupiedInNextPeriod = 0;
		Boolean nextPeriodFull = false;

		for (int i = 0; i < oneHourPeriods; i++)
		{
			row = hourlyStatisticsSheet.createRow(i + 2);

			// Period
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(i + 1);

			// Time period start
			Integer startOfHourlyBucket = (3600 * i) + BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds();
			cell = row.createCell(1);
			if ((BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive()) && (doesEventOccurDuringActiveMarineAccessPeriod(startOfHourlyBucket)))
				cell.setCellStyle(style9);
			else
				cell.setCellStyle(style1);

			cell.setCellValue(ConvertDateTime.convertSecondsToDay_HHMMString(startOfHourlyBucket));

			// Time period end
			Integer endOfHourlyBucket = (3600 * (i + 1)) + BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds();
			cell = row.createCell(2);
			// Below makes style of text for end-of-period the same as the start-of-period 
			if ((BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive()) && (doesEventOccurDuringActiveMarineAccessPeriod(startOfHourlyBucket)))
				cell.setCellStyle(style9);
			else
				cell.setCellStyle(style1);
			cell.setCellValue(ConvertDateTime.convertSecondsToDay_HHMMString(endOfHourlyBucket));

			if (nextPeriodFull) // Closure spanning through at least the end of this period
			{
				timeOccupiedInThisPeriod = 3600;
				timeOccupiedInNextPeriod = timeOccupiedInNextPeriod - 3600;

				if (timeOccupiedInNextPeriod >= 3600)
					nextPeriodFull = true;
				else
					nextPeriodFull = false;
			}

			// Compute periods where bridge is not available to marine traffic
			if (!nextPeriodFull)
			{
				timeOccupiedInThisPeriod = timeOccupiedInNextPeriod;
				timeOccupiedInNextPeriod = 0;

				for (int j = 0; j < closures.size(); j++)
				{

					if (closures.get(j).getClosureStartTimeInSeconds() > endOfHourlyBucket) // Closure that is entirely contained in a future hourly period
					{
						continue;
					}
					else if (closures.get(j).getClosureEndTimeInSeconds() < startOfHourlyBucket) // Closure that is entirely contained in a past hourly period
					{
						continue;
					}
					else if ((closures.get(j).getClosureStartTimeInSeconds() >= startOfHourlyBucket) 
							&& (closures.get(j).getClosureEndTimeInSeconds() <= endOfHourlyBucket)) // Closure that is entirely contained in this hourly period
					{
						timeOccupiedInThisPeriod += closures.get(j).getClosureEndTimeInSeconds() - closures.get(j).getClosureStartTimeInSeconds();
					}	
					else if (closures.get(j).getClosureEndTimeInSeconds() > endOfHourlyBucket) // Place part of closure in this period and part of closure in next period(s)
					{
						timeOccupiedInThisPeriod += (endOfHourlyBucket - closures.get(j).getClosureStartTimeInSeconds());
						timeOccupiedInNextPeriod = closures.get(j).getClosureEndTimeInSeconds() - endOfHourlyBucket;
						if (timeOccupiedInNextPeriod >= 3600)
							nextPeriodFull = true;
						else
							nextPeriodFull = false;
					}
					else if ((j == 0) && (closures.get(j).getClosureEndTimeInSeconds() <= endOfHourlyBucket)) // First closure only and ends in single period
					{
						timeOccupiedInThisPeriod += closures.get(j).getClosureEndTimeInSeconds() - startOfHourlyBucket;
					}
					else if ((j == 0) && (closures.get(j).getClosureEndTimeInSeconds() > endOfHourlyBucket)) // First closure only and ends after first period
					{
						timeOccupiedInThisPeriod += closures.get(j).getClosureEndTimeInSeconds() - startOfHourlyBucket;
						timeOccupiedInNextPeriod = closures.get(j).getClosureEndTimeInSeconds() - endOfHourlyBucket;
						if (timeOccupiedInNextPeriod >= 3600)
							nextPeriodFull = true;
						else
							nextPeriodFull = false;
					}
				}
			}
			if (timeOccupiedInThisPeriod >= 3600)
				timeOccupiedInThisPeriod = 3600;

			// Actual duration computations - all periods
			actualPeriodSumsInSeconds.add(i, timeOccupiedInThisPeriod);
			actualAllPeriodsSumInSeconds += timeOccupiedInThisPeriod;

			// Actual duration computations - marine high-usage periods
			if ((BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive()) && (doesEventOccurDuringActiveMarineAccessPeriod(startOfHourlyBucket)))
			{
				actualMarinePeriodSumsInSeconds.put(i, timeOccupiedInThisPeriod);
				actualMarinePeriodsSumInSeconds += timeOccupiedInThisPeriod;
			}

			// Reported duration computations - all periods
			if (ceilingIncrementAsString.equals("None"))
			{
				reportedPeriodSumsInSeconds.add(i, timeOccupiedInThisPeriod);
				reportedAllPeriodsSumInSeconds += timeOccupiedInThisPeriod;
			}
			else
			{
				int ceilingTimeInSeconds = (int) (Math.ceil((double) timeOccupiedInThisPeriod / (Integer.valueOf(ceilingIncrementAsString.replace("min", "").trim()) * 60)) * (Integer.valueOf(ceilingIncrementAsString.replace("min", "").trim()) * 60)); 
				reportedPeriodSumsInSeconds.add(i, ceilingTimeInSeconds);
				reportedAllPeriodsSumInSeconds += ceilingTimeInSeconds;
			}

			// Reported duration computations - marine high-usage periods
			if ((BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive()) && (doesEventOccurDuringActiveMarineAccessPeriod(startOfHourlyBucket)))
			{
				if (ceilingIncrementAsString.equals("None"))
				{
					reportedMarinePeriodSumsInSeconds.put(i, timeOccupiedInThisPeriod);
					reportedMarinePeriodsSumInSeconds += timeOccupiedInThisPeriod;
				}
				else
				{
					int ceilingTimeInSeconds = (int) (Math.ceil((double) timeOccupiedInThisPeriod / (Integer.valueOf(ceilingIncrementAsString.replace("min", "").trim()) * 60)) * (Integer.valueOf(ceilingIncrementAsString.replace("min", "").trim()) * 60)); 
					reportedMarinePeriodSumsInSeconds.put(i, ceilingTimeInSeconds);
					reportedMarinePeriodsSumInSeconds += ceilingTimeInSeconds;
				}
			}

			// Write sums - all periods
			cell = row.createCell(3);
			cell.setCellStyle(style4);
			if (actualPeriodSumsInSeconds.get(i) != 0)
			{
				cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualPeriodSumsInSeconds.get(i)));
			}

			cell = row.createCell(4);
			cell.setCellStyle(style4);
			if (reportedPeriodSumsInSeconds.get(i) != 0)
			{
				cell.setCellValue(ConvertDateTime.convertSecondsToSerial(reportedPeriodSumsInSeconds.get(i)));
			}

			// Worst period computations -- all periods
			// Actual
			if (actualPeriodSumsInSeconds.get(i) > actualWorstPeriodInSeconds)
			{
				actualWorstPeriodInSeconds = actualPeriodSumsInSeconds.get(i);
			}
			// Reported
			if (reportedPeriodSumsInSeconds.get(i) > reportedWorstAllPeriodInSeconds)
			{
				reportedWorstAllPeriodInSeconds = reportedPeriodSumsInSeconds.get(i);
			}

			// Worst period computations -- marine periods
			if ((BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive()) && (doesEventOccurDuringActiveMarineAccessPeriod(startOfHourlyBucket)))
			{
				// Actual
				if (timeOccupiedInThisPeriod > actualMarineWorstPeriodInSeconds)
				{
					actualMarineWorstPeriodInSeconds = timeOccupiedInThisPeriod;
				}
				// Reported
				if (timeOccupiedInThisPeriod > reportedMarineWorstPeriodInSeconds)
				{
					reportedMarineWorstPeriodInSeconds = timeOccupiedInThisPeriod;
				}
			}
		}

		// Footer rows
		// Sum, mean and max (worst) of all cycles
		row = hourlyStatisticsSheet.createRow(oneHourPeriods + 2);
		cell = row.createCell(5);
		cell.setCellStyle(style6);
		cell.setCellValue("Sum of all periods (bridge not available to marine traffic for (dd:hh:mm:ss))");

		cell = row.createCell(3);
		cell.setCellStyle(style7);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualAllPeriodsSumInSeconds));

		cell = row.createCell(4);
		cell.setCellStyle(style7);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial(reportedAllPeriodsSumInSeconds));

		row = hourlyStatisticsSheet.createRow(oneHourPeriods + 3);
		cell = row.createCell(5);
		cell.setCellStyle(style6);
		cell.setCellValue("Worst hourly period (bridge not available to marine traffic for (h:mm:ss))");

		cell = row.createCell(3);
		cell.setCellStyle(style4);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualWorstPeriodInSeconds));

		cell = row.createCell(4);
		cell.setCellStyle(style4);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial(reportedWorstAllPeriodInSeconds));

		row = hourlyStatisticsSheet.createRow(oneHourPeriods + 4);
		cell = row.createCell(5);
		cell.setCellStyle(style6);
		cell.setCellValue("Avg hourly period (bridge not available to marine traffic for (h:mm:ss))");

		cell = row.createCell(3);
		cell.setCellStyle(style4);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial((int) ((double) actualAllPeriodsSumInSeconds/actualPeriodSumsInSeconds.size())));

		cell = row.createCell(4);
		cell.setCellStyle(style4);
		cell.setCellValue(ConvertDateTime.convertSecondsToSerial((int) ((double) reportedAllPeriodsSumInSeconds/reportedPeriodSumsInSeconds.size())));

		// Sum, mean and max of marine high-usage period cycles
		if (BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive())
		{
			row = hourlyStatisticsSheet.createRow(oneHourPeriods + 5);
			cell = row.createCell(5);
			cell.setCellStyle(style10);
			cell.setCellValue("Sum of marine high-usage periods (bridge not available to marine traffic for (dd:hh:mm:ss))");

			cell = row.createCell(3);
			cell.setCellStyle(style11);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualMarinePeriodsSumInSeconds));

			cell = row.createCell(4);
			cell.setCellStyle(style11);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(reportedMarinePeriodsSumInSeconds));

			row = hourlyStatisticsSheet.createRow(oneHourPeriods + 6);
			cell = row.createCell(5);
			cell.setCellStyle(style10);
			cell.setCellValue("Worst hourly period (bridge not available to marine traffic for (h:mm:ss))");

			cell = row.createCell(3);
			cell.setCellStyle(style12);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(actualMarineWorstPeriodInSeconds));

			cell = row.createCell(4);
			cell.setCellStyle(style12);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial(reportedMarineWorstPeriodInSeconds));

			row = hourlyStatisticsSheet.createRow(oneHourPeriods + 7);
			cell = row.createCell(5);
			cell.setCellStyle(style10);
			cell.setCellValue("Avg hourly period (bridge not available to marine traffic for (h:mm:ss))");

			cell = row.createCell(3);
			cell.setCellStyle(style12);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial((int) ((double) actualMarinePeriodsSumInSeconds/actualMarinePeriodSumsInSeconds.size())));

			cell = row.createCell(4);
			cell.setCellStyle(style12);
			cell.setCellValue(ConvertDateTime.convertSecondsToSerial((int) ((double) reportedMarinePeriodsSumInSeconds/reportedMarinePeriodSumsInSeconds.size())));
		}

		// Timestamp and footnote
		LocalDate creationDate = ConvertDateTime.getDateStamp();
		LocalTime creationTime = ConvertDateTime.getTimeStamp();

		if (BIASBridgeClosureAnalysisConfigPageController.getComputeMarineHighUsagePeriodActive())
		{
			row = hourlyStatisticsSheet.createRow(oneHourPeriods + 8);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("*** Only complete 60-minute periods are reported.  ");

			row = hourlyStatisticsSheet.createRow(oneHourPeriods + 9);
			cell = row.createCell(0);
			cell.setCellStyle(style8);
			cell.setCellValue("Marine high-usage periods (hourly periods which contain a recurring marine aceess period) are shown in blue. ***");

			row = hourlyStatisticsSheet.createRow(oneHourPeriods + 10);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("Created on "+creationDate+" at "+creationTime);
		}
		else
		{
			row = hourlyStatisticsSheet.createRow(oneHourPeriods + 5);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("*** Only complete 60-minute periods are reported ***");

			row = hourlyStatisticsSheet.createRow(oneHourPeriods + 6);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("Created on "+creationDate+" at "+creationTime);
		}

		// Resize all columns to fit the content size
		for (int i = 0; i <= 5; i++) 
		{
			if (i == 0) 
			{
				hourlyStatisticsSheet.setColumnWidth(i, 2500);
			}
			else if ((i == 1) || (i == 2))
			{
				hourlyStatisticsSheet.setColumnWidth(i, 3800);
			}
			else  
			{
				hourlyStatisticsSheet.setColumnWidth(i, 3500);
			}
		}
	}

	private Boolean doesEventOccurDuringActiveMarineAccessPeriod(int eventTimeInSeconds)
	{
		Boolean eventDuringActiveMarineAccessPeriod = false;

		// Marine access period all on same day
		if (((Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour().replace(":00", "")) > Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartHour().replace(":00", "")))
				|| (Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour().replace(":00", "")) == 0))
				&& ((ConvertDateTime.convertSecondsToHH(eventTimeInSeconds) >= Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartHour().replace(":00", "")))
						&& (ConvertDateTime.convertSecondsToHH(eventTimeInSeconds) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour().replace(":00", "")))))
			// HOUR
			// if marine access period end hour is later than the marine access period start hour of the same day
			// and hourly bucket starts at or after the marine access period start hour
			// and hourly bucket ends before the marine access period end hour
		{
			eventDuringActiveMarineAccessPeriod = true;
		}
		// Marine access period extends over midnight
		else if ((Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour().replace(":00", "")) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartHour().replace(":00", ""))) 
				&& ((ConvertDateTime.convertSecondsToHH(eventTimeInSeconds) >= Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartHour().replace(":00", "")))
						|| (ConvertDateTime.convertSecondsToHH(eventTimeInSeconds) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour().replace(":00", "")))))
			// HOUR
			// if marine access period end hour is the day after the marine access period start hour
			// and hourly bucket starts at or after the marine access period start hour
			// or hourly bucket occurs before the marine access period end hour
		{
			eventDuringActiveMarineAccessPeriod = true;
		}

		return eventDuringActiveMarineAccessPeriod;
	}

	public String getResultsMessageWrite3()
	{
		return resultsMessage;
	}
}