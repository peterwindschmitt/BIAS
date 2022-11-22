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

		// Write Bridge Closures
		XSSFSheet bridgeClosuresSheet = workbook.createSheet("Bridge Closures");
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

		// Header rows
		// Case name
		bridgeClosuresSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

		Row row;
		Cell cell;

		row = bridgeClosuresSheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(style0);
		cell.setCellValue(BIASBridgeClosureAnalysisController.getAnalyzedLine() + " [reported duration increment = "+BIASBridgeClosureAnalysisConfigPageController.getIncrementClosure().toLowerCase()+"]");

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
		cell.setCellValue("HE of First Train On Circuit (day:hh:mm:ss)");

		cell = row.createCell(4);
		cell.setCellStyle(style1);
		cell.setCellValue("TE of Last Train Off Circuit (day:hh:mm:ss)");

		cell = row.createCell(5);
		cell.setCellStyle(style1);
		cell.setCellValue("Bridge Up End Time (day:hh:mm:ss)");

		cell = row.createCell(6);
		cell.setCellStyle(style1);
		cell.setCellValue("Actual Duration (hh:mm:ss)");

		cell = row.createCell(7);
		cell.setCellStyle(style1);
		cell.setCellValue("Reported Duration (w/ceiling function, hh:mm:ss)");

		cell = row.createCell(8);
		cell.setCellStyle(style1);
		cell.setCellValue("Bridge Open Period (hh:mm:ss)");

		cell = row.createCell(9);
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
			if ((BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodActive()) && (doesEventOccurDuringActiveMarineAccessPeriod(closures.get(i).getPreferredBridgeDownStartTimeInSeconds())))
				cell.setCellStyle(style8);
			else
				cell.setCellStyle(style1);
			
			cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(closures.get(i).getPreferredBridgeDownStartTimeInSeconds()));
			
			// Latest bridge down start time
			cell = row.createCell(2);
			if ((BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodActive()) && (doesEventOccurDuringActiveMarineAccessPeriod(closures.get(i).getLatestBridgeDownStartTimeInSeconds())))
				cell.setCellStyle(style8);
			else
				cell.setCellStyle(style1);

			cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(closures.get(i).getLatestBridgeDownStartTimeInSeconds()));

			// Head-end arrival time for first train
			cell = row.createCell(3);
			if ((BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodActive()) && (doesEventOccurDuringActiveMarineAccessPeriod(closures.get(i).getSignalSetUpCompleteTimeInSeconds())))
				cell.setCellStyle(style8);
			else
				cell.setCellStyle(style1);
			
			cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(closures.get(i).getSignalSetUpCompleteTimeInSeconds()));

			// Tail-end of last train off circuit
			cell = row.createCell(4);
			if ((BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodActive()) && (doesEventOccurDuringActiveMarineAccessPeriod(closures.get(i).getBridgeUpStartTimeInSeconds())))
				cell.setCellStyle(style8);
			else
				cell.setCellStyle(style1);
			
			cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(closures.get(i).getBridgeUpStartTimeInSeconds()));

			// Bridge up end time
			cell = row.createCell(5);
			if ((BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodActive()) && (doesEventOccurDuringActiveMarineAccessPeriod(closures.get(i).getBridgeUpCompleteTimeInSeconds())))
				cell.setCellStyle(style8);
			else
				cell.setCellStyle(style1);
			
			cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(closures.get(i).getBridgeUpCompleteTimeInSeconds()));

			// Actual duration
			cell = row.createCell(6);
			cell.setCellStyle(style6);

			double serialSeconds = 0.0;
			if ((i == 0) && (closures.get(i).getPreferredBridgeDownStartTimeInSeconds() < BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds()))  // First closure
			{
				serialSeconds = ConvertDateTime.convertSecondsToSerial(closures.get(i).getBridgeUpCompleteTimeInSeconds() - BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds());
			}
			else if ((i == closures.size() - 1) && (closures.get(i).getBridgeUpCompleteTimeInSeconds() > BridgeClosureAnalysis.getEndOfAnalysisPeriodInSeconds()))  // Last closure
			{
				serialSeconds = ConvertDateTime.convertSecondsToSerial(BridgeClosureAnalysis.getEndOfAnalysisPeriodInSeconds() - closures.get(i).getPreferredBridgeDownStartTimeInSeconds());
			}
			else // All other closures
			{
				serialSeconds = ConvertDateTime.convertSecondsToSerial(closures.get(i).getBridgeUpCompleteTimeInSeconds() - closures.get(i).getPreferredBridgeDownStartTimeInSeconds());
			}
			sumOfActualClosureDurations += serialSeconds;
			cell.setCellValue(serialSeconds);

			// Reported duration
			cell = row.createCell(7);
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
			if (i == 0) // First closure
			{
				// Determine if need to account for open period prior to first closure
				if (closures.get(i).getPreferredBridgeDownStartTimeInSeconds() > BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds())
				{
					sumOfOpenDurations += ConvertDateTime.convertSecondsToSerial(closures.get(i).getPreferredBridgeDownStartTimeInSeconds() - BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds());
				}

				// Determine open period following first closure
				nextClosureStartTime = ConvertDateTime.convertSecondsToSerial(closures.get(i + 1).getPreferredBridgeDownStartTimeInSeconds());
				bridgeOpenPeriod = nextClosureStartTime - ConvertDateTime.convertSecondsToSerial(closures.get(i).getBridgeUpCompleteTimeInSeconds());
				sumOfOpenDurations += bridgeOpenPeriod;
			}
			else if (i == closures.size() - 1) // Last closure
			{
				if (closures.get(i).getBridgeUpCompleteTimeInSeconds() < BridgeClosureAnalysis.getEndOfAnalysisPeriodInSeconds())
				{
					sumOfOpenDurations += ConvertDateTime.convertSecondsToSerial(BridgeClosureAnalysis.getEndOfAnalysisPeriodInSeconds() - closures.get(i).getBridgeUpCompleteTimeInSeconds());
					bridgeOpenPeriod = ConvertDateTime.convertSecondsToSerial(BridgeClosureAnalysis.getEndOfAnalysisPeriodInSeconds() - closures.get(i).getBridgeUpCompleteTimeInSeconds());
				}
				else
					bridgeOpenPeriod = 0.0;
			}
			else // All other closures
			{
				nextClosureStartTime = ConvertDateTime.convertSecondsToSerial(closures.get(i + 1).getPreferredBridgeDownStartTimeInSeconds());
				bridgeOpenPeriod = nextClosureStartTime - ConvertDateTime.convertSecondsToSerial(closures.get(i).getBridgeUpCompleteTimeInSeconds());
				sumOfOpenDurations += bridgeOpenPeriod;
			}
			cell = row.createCell(8);
			cell.setCellStyle(style6);
			cell.setCellValue(bridgeOpenPeriod);  

			// Trains crossing in closure
			cell = row.createCell(9);
			cell.setCellStyle(style5);
			cell.setCellValue(closures.get(i).getTrainSymbolsAndDirectionsInClosure().toString());
		}

		// Footer rows
		// Add sum and mean of all cycles
		row = bridgeClosuresSheet.createRow(closures.size() + 2);
		cell = row.createCell(5);
		cell.setCellStyle(style3);
		cell.setCellValue("Sum of closures(dd:hh:mm:ss):");

		cell = row.createCell(6);
		cell.setCellStyle(style4);
		cell.setCellValue(sumOfActualClosureDurations);

		cell = row.createCell(7);
		cell.setCellStyle(style4);
		cell.setCellValue(sumOfReportedClosureDurations);

		row = bridgeClosuresSheet.createRow(closures.size() + 3);
		cell = row.createCell(5);
		cell.setCellStyle(style3);
		cell.setCellValue("Avg closure duration(hh:mm:ss):");

		cell = row.createCell(6);
		cell.setCellStyle(style6);
		cell.setCellValue(sumOfActualClosureDurations/closures.size());

		cell = row.createCell(7);
		cell.setCellStyle(style6);
		cell.setCellValue(sumOfReportedClosureDurations/closures.size());

		row = bridgeClosuresSheet.createRow(closures.size() + 4);
		cell = row.createCell(5);
		cell.setCellStyle(style3);
		cell.setCellValue("Sum of bridge open periods (dd:hh:mm:ss):");

		cell = row.createCell(8);
		cell.setCellStyle(style4);
		cell.setCellValue(sumOfOpenDurations);

		row = bridgeClosuresSheet.createRow(closures.size() + 5);
		cell = row.createCell(5);
		cell.setCellStyle(style3);
		cell.setCellValue("Avg duration of bridge open period (hh:mm:ss):");

		cell = row.createCell(8);
		cell.setCellStyle(style6);
		cell.setCellValue(sumOfOpenDurations/(closures.size() - 1));

		row = bridgeClosuresSheet.createRow(closures.size() + 6);
		cell = row.createCell(5);
		cell.setCellStyle(style3);
		cell.setCellValue("Analysis period (sum of closed and open periods) (dd:hh:mm:ss):");

		cell = row.createCell(6);
		cell.setCellStyle(style4);
		cell.setCellValue(sumOfOpenDurations + sumOfActualClosureDurations);

		// Timestamp and footnote
		LocalDate creationDate = ConvertDateTime.getDateStamp();
		LocalTime creationTime = ConvertDateTime.getTimeStamp();
		
		row = bridgeClosuresSheet.createRow(closures.size() + 7);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("*** First and last closures may show event times before/after the analysis period.  However, time outside of the analysis period is not included in the durations.  Durations and Bridge Closed/Open Periods are based on Preferred Bridge Down Start Time (rather than Latest).");

		if (BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodActive())
		{
			row = bridgeClosuresSheet.createRow(closures.size() + 8);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("For bridge open periods, the time prior to the first closure and following the last closure (if applicable) are computed based on the beginning/end of the analysis period.");

			row = bridgeClosuresSheet.createRow(closures.size() + 9);
			cell = row.createCell(0);
			cell.setCellStyle(style7);
			cell.setCellValue("Closures which potentially bust the recurring marine access period (from minute "+BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute()+" to minute "+BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute()
					+ " starting at "+BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartHour()+" and ending at "+BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour()+") are shown in red. ***");

			row = bridgeClosuresSheet.createRow(closures.size() + 10);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("Created on "+creationDate+" at "+creationTime);
		}
		else
		{
			row = bridgeClosuresSheet.createRow(closures.size() + 8);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("For bridge open periods, the time prior to the first closure and following the last closure (if applicable) are computed based on the beginning/end of the analysis period. ***");

			row = bridgeClosuresSheet.createRow(closures.size() + 9);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("Created on "+creationDate+" at "+creationTime);
		}

		// Resize all columns to fit the content size
		for (int i = 0; i <= 9; i++) 
		{
			if (i == 0) 
			{
				bridgeClosuresSheet.setColumnWidth(i, 2600); //2400
			}
			else if (i < 6)
			{
				bridgeClosuresSheet.setColumnWidth(i, 5000); //4800
			}
			else  
			{
				bridgeClosuresSheet.setColumnWidth(i, 3500);
			}
		}
		bridgeClosuresSheet.autoSizeColumn(9);
	}

	private Boolean doesEventOccurDuringActiveMarineAccessPeriod(int eventTimeInSeconds)
	{
		Boolean eventDuringActiveMarineAccessPeriod = false;
		/*System.out.print("For closure#"+i+", recurring marine period start hour = "+ Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartHour().replace(":00", "")) 
		+ " recurring marine period end hour = "+ Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour().replace(":00", "")) 
		+ " bridge down start hour = "+ ConvertDateTime.convertSecondsToHH(closures.get(i).getBridgeDownStartTimeInSeconds()));
		System.out.print("\n recurring marine period start min = "+ Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartMinute().replace(":", "")) 
		+ " recurring marine period end min = "+ Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndMinute().replace(":", "")) 
		+ " bridge down start min = "+ ConvertDateTime.convertSecondsToMM(closures.get(i).getBridgeDownStartTimeInSeconds()));*/
		
		
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
				//System.out.println(": Case 1");
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
				//System.out.println(": Case 2");
			}
			//else
			//	System.out.println(": No conflict");
		}
		// Marine access period extends over midnight
		else if ((Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodEndHour().replace(":00", "")) < Integer.valueOf(BIASBridgeClosureAnalysisConfigPageController.getRecurringMarineAccessPeriodStartHour().replace(":00", ""))) 
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
			//	System.out.println(": Case 3");
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
			//	System.out.println(": Case 4");
			}
			//else
			//	System.out.println(": No conflict");
		}
		//else
			//System.out.println(": No conflict");
					
		return eventDuringActiveMarineAccessPeriod;
	}
	
	public String getResultsMessageWrite2()
	{
		return resultsMessage;
	}
}