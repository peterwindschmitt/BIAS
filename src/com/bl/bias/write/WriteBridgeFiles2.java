package com.bl.bias.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

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

public class WriteBridgeFiles2 extends WriteBridgeFiles1
{
	Integer cycleNumber;
	Integer simDay;

	String dayOfWeek;
	String trainSymbol;
	String trainDirection;
	String entryNode;
	String exitNode;

	Double actualDuration;
	Double reportedDuration;
	Double bridgeOpenPeriod;

	Double sumOfActualClosureDurations = 0.0;
	Double sumOfReportedClosureDurations = 0.0;
	Double sumOfOpenDurations = 0.0;

	String resultsMessage = getResultsMessageWrite1();

	ArrayList<BridgeAnalysisClosure> closures = new ArrayList<BridgeAnalysisClosure>();

	public WriteBridgeFiles2(String textAreaContents, String locationOfInputFiles)
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

		// Write Bridge Closures
		XSSFSheet bridgeClosuresSheet = workbook.createSheet("Bridge Closures");
		bridgeClosuresSheet.setDisplayGridlines(false);
		
		if (BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodActive())
			resultsMessage += "\nWriting bridge closures (with recurring marine access period active)";
		else
			resultsMessage += "\nWriting bridge closures";

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
		font3.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
		font3.setFontName("Calibri");

		// Font 4 - 11pt text
		XSSFFont font4 = workbook.createFont();
		font4.setFontHeightInPoints((short) 11);
		font4.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
		font4.setFontName("Calibri");

		// Font 5 - 11pt text
		XSSFFont font5 = workbook.createFont();
		font5.setFontHeightInPoints((short) 11);
		font5.setColor(HSSFColor.HSSFColorPredefined.ORANGE.getIndex());
		font5.setFontName("Calibri");

		// Font 6 - 8pt text
		XSSFFont font6 = workbook.createFont();
		font6.setFontHeightInPoints((short) 8);
		font6.setColor(HSSFColor.HSSFColorPredefined.ORANGE.getIndex());
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

		// Style 4 - Centered, wrapped, 11pt, black text, DD:HH:MM:SS
		style4.setDataFormat(workbook.createDataFormat().getFormat("dd:HH:mm:ss"));
		style4.setAlignment(HorizontalAlignment.CENTER);  
		style4.setWrapText(true);
		style4.setFont(font1);

		// Style 5 - Right aligned, non-wrapped, 11pt, black text
		style5.setAlignment(HorizontalAlignment.LEFT);  
		style5.setWrapText(false);
		style5.setFont(font1);

		// Style 6 - Centered, wrapped, 11pt, black text, HH:MM:SS
		style6.setDataFormat(workbook.createDataFormat().getFormat("HH:mm:ss"));
		style6.setAlignment(HorizontalAlignment.CENTER);  
		style6.setWrapText(true);
		style6.setFont(font1);

		// Style 7 - Left aligned, non-wrapped, 8pt, red text
		style7.setAlignment(HorizontalAlignment.LEFT);  
		style7.setWrapText(false);
		style7.setFont(font3);

		// Style 8 - Centered, wrapped, 11pt, red text
		style8.setAlignment(HorizontalAlignment.CENTER);  
		style8.setWrapText(true);
		style8.setFont(font4);

		// Style 9 - Centered, wrapped, 11pt, orange text
		style9.setAlignment(HorizontalAlignment.CENTER);  
		style9.setWrapText(true);
		style9.setFont(font5);

		// Style 10 - Left aligned, non-wrapped, 8pt, orange text
		style10.setAlignment(HorizontalAlignment.LEFT);  
		style10.setWrapText(false);
		style10.setFont(font6);
		
		// Style 11 - Centered, non-wrapped, 11pt, black text, 1/10% precision
		style11.setAlignment(HorizontalAlignment.CENTER);  
		style11.setWrapText(false);
		style11.setDataFormat(workbook.createDataFormat().getFormat("00.0%"));
		style11.setFont(font1);

		// Header rows
		// Case name
		bridgeClosuresSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

		Row row;
		Cell cell;

		row = bridgeClosuresSheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(style0);
		cell.setCellValue(BIASBridgeClosureAnalysisController.getAnalyzedLine()+" Bridge Closures");

		// Data headers
		row = bridgeClosuresSheet.createRow(1);

		cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("Closure #");

		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell.setCellValue("Preferred Bridge Down Start Time (day:hh:mm:ss)");

		cell = row.createCell(2);
		cell.setCellStyle(style1);
		cell.setCellValue("Latest Bridge Down Start Time (day:hh:mm:ss)");

		cell = row.createCell(3);
		cell.setCellStyle(style1);
		cell.setCellValue("Planned Bridge Down Start Time (day:hh:mm:ss)");

		cell = row.createCell(4);
		cell.setCellStyle(style1);
		cell.setCellValue("HE of First Train On Circuit (day:hh:mm:ss)");

		cell = row.createCell(5);
		cell.setCellStyle(style1);
		cell.setCellValue("TE of Last Train Off Circuit (day:hh:mm:ss)");

		cell = row.createCell(6);
		cell.setCellStyle(style1);
		cell.setCellValue("Bridge Up End Time (day:hh:mm:ss)");

		cell = row.createCell(7);
		cell.setCellStyle(style1);
		cell.setCellValue("Actual Duration (hh:mm:ss)");

		cell = row.createCell(8);
		cell.setCellStyle(style1);
		cell.setCellValue("Reported Duration (w/ceiling function, hh:mm:ss)");

		cell = row.createCell(9);
		cell.setCellStyle(style1);
		cell.setCellValue("Bridge Open Period (hh:mm:ss)");

		cell = row.createCell(10);
		cell.setCellStyle(style1);
		cell.setCellValue("Trains Crossing During Closure");

		// Closures
		for (int i = 0; i < closures.size(); i++)
		{
			row = bridgeClosuresSheet.createRow(i+2);

			// Closure cycle
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(i + 1);

			// Preferred bridge down start time
			cell = row.createCell(1);
			cell.setCellStyle(style1);

			cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(closures.get(i).getPreferredBridgeDownStartTimeInSeconds()));

			// Latest bridge down start time
			cell = row.createCell(2);
			cell.setCellStyle(style1);

			cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(closures.get(i).getLatestBridgeDownStartTimeInSeconds()));
			
			// Planned bridge down start time
			cell = row.createCell(3);
			if ((BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodActive()) && (DoesEventOccurDuringActiveMarineAccessPeriod.doesEventOccurDuringActiveMarineAccessPeriod(closures.get(i).getPlannedBridgeDownStartTimeInSeconds())))
				cell.setCellStyle(style8); // red -- busts marine access period
			else if ((BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodActive()) && (DoesEventOccurDuringActiveMarineAccessPeriod.doesEventOccurDuringActiveMarineAccessPeriod(closures.get(i).getPreferredBridgeDownStartTimeInSeconds())))
				cell.setCellStyle(style9); // orange -- between preferred and latest bridge down start times
			else
				cell.setCellStyle(style1); // black -- preferred bridge down start time

			cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(closures.get(i).getPlannedBridgeDownStartTimeInSeconds()));

			// Head-end arrival time for first train
			cell = row.createCell(4);
			if ((BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodActive()) && (DoesEventOccurDuringActiveMarineAccessPeriod.doesEventOccurDuringActiveMarineAccessPeriod(closures.get(i).getSignalSetUpCompleteTimeInSeconds())))
				cell.setCellStyle(style8);
			else
				cell.setCellStyle(style1);

			cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(closures.get(i).getSignalSetUpCompleteTimeInSeconds()));

			// Tail-end of last train off circuit
			cell = row.createCell(5);
			if ((BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodActive()) && (DoesEventOccurDuringActiveMarineAccessPeriod.doesEventOccurDuringActiveMarineAccessPeriod(closures.get(i).getBridgeUpStartTimeInSeconds())))
				cell.setCellStyle(style8);
			else
				cell.setCellStyle(style1);

			cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(closures.get(i).getBridgeUpStartTimeInSeconds()));

			// Bridge up end time
			cell = row.createCell(6);
			if ((BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodActive()) && (DoesEventOccurDuringActiveMarineAccessPeriod.doesEventOccurDuringActiveMarineAccessPeriod(closures.get(i).getBridgeUpCompleteTimeInSeconds())))
				cell.setCellStyle(style8);
			else
				cell.setCellStyle(style1);

			cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(closures.get(i).getBridgeUpCompleteTimeInSeconds()));
			
			// Actual duration
			cell = row.createCell(7);
			cell.setCellStyle(style6);

			double serialSeconds = 0.0;
			if ((i == 0) && (closures.get(i).getPlannedBridgeDownStartTimeInSeconds() < BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds()))  // First closure
			{
				serialSeconds = ConvertDateTime.convertSecondsToSerial(closures.get(i).getBridgeUpCompleteTimeInSeconds() - BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds());
			}
			else if ((i == closures.size() - 1) && (closures.get(i).getBridgeUpCompleteTimeInSeconds() > BridgeClosureAnalysis.getEndOfAnalysisPeriodInSeconds()))  // Last closure
			{
				serialSeconds = ConvertDateTime.convertSecondsToSerial(BridgeClosureAnalysis.getEndOfAnalysisPeriodInSeconds() - closures.get(i).getPlannedBridgeDownStartTimeInSeconds());
			}
			else // All other closures
			{
				serialSeconds = ConvertDateTime.convertSecondsToSerial(closures.get(i).getBridgeUpCompleteTimeInSeconds() - closures.get(i).getPlannedBridgeDownStartTimeInSeconds());
			}
			sumOfActualClosureDurations += serialSeconds;
			cell.setCellValue(serialSeconds);

			// Reported duration
			cell = row.createCell(8);
			cell.setCellStyle(style6);
			String ceilingIncrementAsString = BIASBridgeClosureAnalysisConfigPageController.getIncrementClosure();
			double ceilingTimeAsSerial;
			if (ceilingIncrementAsString.equals("None"))
			{
				ceilingTimeAsSerial = serialSeconds;
			}
			else
			{
				ceilingTimeAsSerial = Math.ceil(serialSeconds * 24 * (60 / Integer.valueOf(ceilingIncrementAsString.replace("min", "").trim()))) * (Integer.valueOf(ceilingIncrementAsString.replace("min", "").trim()))/(24 * 60); 
			}
			cell.setCellValue(ceilingTimeAsSerial);

			sumOfReportedClosureDurations += ceilingTimeAsSerial;
			
			// Time until next closure
			double nextClosureStartTime;
			if (i == closures.size() - 1) // Last closure.  Must evaluate this first in case there's only a single train.
			{
				if (closures.get(i).getBridgeUpCompleteTimeInSeconds() < BridgeClosureAnalysis.getEndOfAnalysisPeriodInSeconds())
				{
					sumOfOpenDurations += ConvertDateTime.convertSecondsToSerial(BridgeClosureAnalysis.getEndOfAnalysisPeriodInSeconds() - closures.get(i).getBridgeUpCompleteTimeInSeconds());
					bridgeOpenPeriod = ConvertDateTime.convertSecondsToSerial(BridgeClosureAnalysis.getEndOfAnalysisPeriodInSeconds() - closures.get(i).getBridgeUpCompleteTimeInSeconds());
				}
				else
					bridgeOpenPeriod = 0.0;
			}
			else if (i == 0) // First closure
			{
				// Determine if need to account for open period prior to first closure
				if (closures.get(i).getPlannedBridgeDownStartTimeInSeconds() > BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds())
				{
					sumOfOpenDurations += ConvertDateTime.convertSecondsToSerial(closures.get(i).getPlannedBridgeDownStartTimeInSeconds() - BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds());
				}

				// Determine open period following first closure
				nextClosureStartTime = ConvertDateTime.convertSecondsToSerial(closures.get(i + 1).getPlannedBridgeDownStartTimeInSeconds());
				bridgeOpenPeriod = nextClosureStartTime - ConvertDateTime.convertSecondsToSerial(closures.get(i).getBridgeUpCompleteTimeInSeconds());
				sumOfOpenDurations += bridgeOpenPeriod;
			}
			else // All other closures
			{
				nextClosureStartTime = ConvertDateTime.convertSecondsToSerial(closures.get(i + 1).getPlannedBridgeDownStartTimeInSeconds());
				bridgeOpenPeriod = nextClosureStartTime - ConvertDateTime.convertSecondsToSerial(closures.get(i).getBridgeUpCompleteTimeInSeconds());
				sumOfOpenDurations += bridgeOpenPeriod;
			}
			cell = row.createCell(9);
			cell.setCellStyle(style6);
			cell.setCellValue(bridgeOpenPeriod);  
			
			// Trains crossing in closure
			cell = row.createCell(10);
			cell.setCellStyle(style5);
			cell.setCellValue(closures.get(i).getTrainSymbolsAndDirectionsInClosure().toString().replace("[", "").replace("]", ""));
		}

		// Footer rows
		// Add sum and mean of all cycles
		row = bridgeClosuresSheet.createRow(closures.size() + 3);
		cell = row.createCell(6);
		cell.setCellStyle(style3);
		cell.setCellValue("Sum of closures(dd:hh:mm:ss):");
		
		cell = row.createCell(7);
		cell.setCellStyle(style4);
		cell.setCellValue(sumOfActualClosureDurations);

		cell = row.createCell(8);
		cell.setCellStyle(style4);
		cell.setCellValue(sumOfReportedClosureDurations);

		row = bridgeClosuresSheet.createRow(closures.size() + 4);
		cell = row.createCell(6);
		cell.setCellStyle(style3);
		cell.setCellValue("Avg closure duration(hh:mm:ss):");

		cell = row.createCell(7);
		cell.setCellStyle(style6);
		cell.setCellValue(sumOfActualClosureDurations/closures.size());

		cell = row.createCell(8);
		cell.setCellStyle(style6);
		cell.setCellValue(sumOfReportedClosureDurations/closures.size());

		row = bridgeClosuresSheet.createRow(closures.size() + 5);
		cell = row.createCell(6);
		cell.setCellStyle(style3);
		cell.setCellValue("Sum of bridge open periods (dd:hh:mm:ss):");

		cell = row.createCell(9);
		cell.setCellStyle(style4);
		cell.setCellValue(sumOfOpenDurations);

		row = bridgeClosuresSheet.createRow(closures.size() + 6);
		cell = row.createCell(6);
		cell.setCellStyle(style3);
		cell.setCellValue("Avg duration of bridge open period (hh:mm:ss):");

		cell = row.createCell(9);
		cell.setCellStyle(style6);
		cell.setCellValue(sumOfOpenDurations/closures.size());

		row = bridgeClosuresSheet.createRow(closures.size() + 7);
		cell = row.createCell(6);
		cell.setCellStyle(style3);
		cell.setCellValue("Analysis period (sum of closed and open periods) (dd:hh:mm:ss):");

		cell = row.createCell(7);
		cell.setCellStyle(style4);
		cell.setCellValue(sumOfOpenDurations + sumOfActualClosureDurations);

		row = bridgeClosuresSheet.createRow(closures.size() + 8);
		cell = row.createCell(6);
		cell.setCellStyle(style3);
		cell.setCellValue("Mariner access % (for entire analysis period based on actual durations):");
		
		cell = row.createCell(7);
		cell.setCellStyle(style11);
		cell.setCellValue(sumOfOpenDurations / (sumOfOpenDurations + sumOfActualClosureDurations));

		// Timestamp and footnote
		LocalDate creationDate = ConvertDateTime.getDateStamp();
		LocalTime creationTime = ConvertDateTime.getTimeStamp();

		row = bridgeClosuresSheet.createRow(closures.size() + 9);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("First and last closures may show event times before/after the analysis period.  However, time outside of the analysis period is not included in the durations.  Durations and Bridge Closed/Open Periods are based on Planned Bridge Down Start Time.");

		if (BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodActive())
		{
			row = bridgeClosuresSheet.createRow(closures.size() + 10);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("For bridge open periods, the time prior to the first closure and following the last closure (if applicable) are computed based on the beginning/end of the analysis period.");

			row = bridgeClosuresSheet.createRow(closures.size() + 11);
			cell = row.createCell(0);
			cell.setCellStyle(style10);
			cell.setCellValue("Closures which potentially contain suboptimal signal aspects during the recurring marine access period (from minute "+BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute()+" to minute "+BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute()
			+ " starting at "+BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartHour()+" and ending at "+BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour()+") are shown in orange. Planned bridge down start time is the end of the hourly recurring marine access period.");

			row = bridgeClosuresSheet.createRow(closures.size() + 12);
			cell = row.createCell(0);
			cell.setCellStyle(style7);
			cell.setCellValue("Event times which violate the recurring marine access period (from minute "+BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute()+" to minute "+BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute()
			+ " starting at "+BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartHour()+" and ending at "+BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour()+") are shown in red. Planned bridge down start time is the latest possible down time.");

			row = bridgeClosuresSheet.createRow(closures.size() + 13);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("Created on "+creationDate+" at "+creationTime);
		}
		else
		{
			row = bridgeClosuresSheet.createRow(closures.size() + 10);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("For bridge open periods, the time prior to the first closure and following the last closure (if applicable) are computed based on the beginning/end of the analysis period.");

			row = bridgeClosuresSheet.createRow(closures.size() + 11);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("Created on "+creationDate+" at "+creationTime);
		}

		// Resize all columns to fit the content size
		for (int i = 0; i <= 10; i++) 
		{
			if (i == 0) 
			{
				bridgeClosuresSheet.setColumnWidth(i, 2600); //2400
			}
			else if (i < 7)
			{
				bridgeClosuresSheet.setColumnWidth(i, 5000); //4800
			}
			else  
			{
				bridgeClosuresSheet.setColumnWidth(i, 3500);
			}
		}
		bridgeClosuresSheet.autoSizeColumn(10);
	}

	public String getResultsMessageWrite2()
	{
		return resultsMessage;
	}
}