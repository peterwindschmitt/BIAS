package com.bl.bias.write;

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

import com.bl.bias.app.BIASMaintenanceWindowAnalysisConfigPageController;
import com.bl.bias.app.BIASMaintenanceWindowAnalysisPageController;
import com.bl.bias.objects.MaintenanceWindowAnalysisSegment;
import com.bl.bias.objects.MaintenanceWindowAnalysisWindow;
import com.bl.bias.tools.ConvertDateTime;

public class WriteMaintenanceWindowFiles3 extends WriteMaintenanceWindowFiles2
{
	Double windowStartTime;
	Double windowEndTime;
	Double windowDuration;

	Double sumOfWindowDurations;

	ArrayList<MaintenanceWindowAnalysisSegment> segments = new ArrayList<MaintenanceWindowAnalysisSegment>();

	String results = getResultsMessageWrite2();

	public WriteMaintenanceWindowFiles3(String textAreaContents, String locationOfInputFiles)
	{
		super(textAreaContents, locationOfInputFiles);

		// Get segments
		segments.addAll(BIASMaintenanceWindowAnalysisPageController.getSegments());

		// Set styles
		CellStyle style0 = workbook.createCellStyle();
		CellStyle style1 = workbook.createCellStyle();
		CellStyle style2 = workbook.createCellStyle();
		CellStyle style3 = workbook.createCellStyle();
		CellStyle style4 = workbook.createCellStyle();
		CellStyle style5 = workbook.createCellStyle();
		CellStyle style6 = workbook.createCellStyle();

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

		// Cell styles
		// Style 0 - Centered, wrapped 14pt, white text against black background
		style0.setAlignment(HorizontalAlignment.CENTER);  
		style0.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		style0.setFillPattern(FillPatternType.FINE_DOTS);
		style0.setWrapText(true);
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

		// Style 4 - Centered, wrapped, 11pt, black text, HH:MM:SS
		style4.setDataFormat(workbook.createDataFormat().getFormat("HH:mm:ss"));
		style4.setAlignment(HorizontalAlignment.CENTER);  
		style4.setWrapText(true);
		style4.setFont(font1);

		// Style 5 - Right aligned, non-wrapped, 11pt, black text
		style5.setAlignment(HorizontalAlignment.LEFT);  
		style5.setWrapText(false);
		style5.setFont(font1);

		// Style 6 - Centered, wrapped, 11pt, black text, DD:HH:MM:SS
		style6.setDataFormat(workbook.createDataFormat().getFormat("dd:HH:mm:ss"));
		style6.setAlignment(HorizontalAlignment.CENTER);  
		style6.setWrapText(true);
		style6.setFont(font1);

		if (BIASMaintenanceWindowAnalysisConfigPageController.getObservedWindows())
		{
			// Write Occupancies in Line Segment
			resultsMessage += "\nWriting observed windows";

			for (int i = 0; i < segments.size(); i++)
			{
				String lineName = segments.get(i).getLineName().replace("[", "").replace("]", "").replace("MOW_", "");
				XSSFSheet observedWindowsOnMOWLinesSheet = workbook.createSheet(lineName+" Obsvd Windows");
				sumOfWindowDurations = 0.0;

				// Header rows
				// Case name
				observedWindowsOnMOWLinesSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

				Row row;
				Cell cell;

				row = observedWindowsOnMOWLinesSheet.createRow(0);
				cell = row.createCell(0);
				cell.setCellStyle(style0);
				cell.setCellValue(lineName + " Line - Observed Windows");

				// Data headers
				row = observedWindowsOnMOWLinesSheet.createRow(1);

				cell = row.createCell(0);
				cell.setCellStyle(style1);
				cell.setCellValue("Window #");

				cell = row.createCell(1);
				cell.setCellStyle(style1);
				cell.setCellValue("Window Start (day:hh:mm:ss)");

				cell = row.createCell(2);
				cell.setCellStyle(style1);
				cell.setCellValue("Window End (day:hh:mm:ss)");

				cell = row.createCell(3);
				cell.setCellStyle(style1);
				cell.setCellValue("Duration (hh:mm:ss)");

				int rowCounter = 2;
				int validWindowInLineCount = 0;
				double sumOfObservedWindowDurations = 0.0;

				// For each subline
				ArrayList<MaintenanceWindowAnalysisWindow> observedWindowsInLine = new ArrayList<MaintenanceWindowAnalysisWindow>(segments.get(i).getSubLinesObservedWindows());
				ArrayList<String> subLinesKeySet = new ArrayList<String>(segments.get(i).getSubLinesOccupancies().keySet());
				for (int j = 0; j < subLinesKeySet.size(); j++)
				{
					row = observedWindowsOnMOWLinesSheet.createRow(rowCounter);
					cell = row.createCell(0);
					cell.setCellStyle(style1);
					cell.setCellValue("Track "+subLinesKeySet.get(j));
					observedWindowsOnMOWLinesSheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 3));
					rowCounter++;

					Boolean subLineHasEntry = false;

					// For each window in the line
					for (int k = 0; k < observedWindowsInLine.size(); k++)
					{
						if (observedWindowsInLine.get(k).getSubLineId().equals(subLinesKeySet.get(j)))
						{
							// Observed window id
							row = observedWindowsOnMOWLinesSheet.createRow(rowCounter);

							// Window #
							cell = row.createCell(0);
							cell.setCellStyle(style1);
							cell.setCellValue(validWindowInLineCount + 1);

							// Window start time
							cell = row.createCell(1);
							cell.setCellStyle(style1);
							cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(observedWindowsInLine.get(k).getSubLineWindowStartTimeInSeconds()));

							// Window finish time
							cell = row.createCell(2);
							cell.setCellStyle(style1);
							cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(observedWindowsInLine.get(k).getSubLineWindowFinishTimeInSeconds()));

							// Compute window duration
							double windowDurationAsSerial = ConvertDateTime.convertSecondsToSerial(observedWindowsInLine.get(k).getSubLineWindowFinishTimeInSeconds() - observedWindowsInLine.get(k).getSubLineWindowStartTimeInSeconds());

							// Show window duration
							cell = row.createCell(3);
							if (windowDurationAsSerial < 1)
								cell.setCellStyle(style4);
							else
								cell.setCellStyle(style6);
							cell.setCellValue(windowDurationAsSerial);

							sumOfWindowDurations += windowDurationAsSerial;
							rowCounter++;
							validWindowInLineCount++;
							subLineHasEntry = true;
						}
					}
					if (!subLineHasEntry)
					{
						row = observedWindowsOnMOWLinesSheet.createRow(rowCounter);
						cell = row.createCell(0);
						cell.setCellStyle(style1);
						cell.setCellValue("* None *");
						observedWindowsOnMOWLinesSheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 3));
						rowCounter++;
					}
				}

				// Footer rows
				// Add sum and mean of all cycles
				row = observedWindowsOnMOWLinesSheet.createRow(rowCounter);
				cell = row.createCell(2);
				cell.setCellStyle(style3);
				cell.setCellValue("Sum of observed window durations(dd:hh:mm:ss):");

				cell = row.createCell(3);
				cell.setCellStyle(style6);
				cell.setCellValue(sumOfWindowDurations);

				rowCounter++;
				row = observedWindowsOnMOWLinesSheet.createRow(rowCounter);
				cell = row.createCell(2);
				cell.setCellStyle(style3);
				cell.setCellValue("Avg observed window duration(hh:mm:ss):");

				cell = row.createCell(3);
				cell.setCellStyle(style4);
				if (validWindowInLineCount > 0)
					cell.setCellValue(sumOfWindowDurations/validWindowInLineCount);
				else
					cell.setCellValue("N/A");
				rowCounter++;

				// Timestamp and footnote
				row = observedWindowsOnMOWLinesSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("*** First and last occupancies may show event times before/after the analysis period and may not be feasible without additional information.  Any time outside of the analysis period is not included in the duration sums or averages.");
				rowCounter++;
				row = observedWindowsOnMOWLinesSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("*** Minimum acceptable duration is "+BIASMaintenanceWindowAnalysisConfigPageController.getMinimumWindowDurationAsString()+". Line must be unoccupied for "+BIASMaintenanceWindowAnalysisConfigPageController.getBlockUnoccupiedInAdvanceOfWindowMinutes()+"m before window.  Line must be unoccupied "+BIASMaintenanceWindowAnalysisConfigPageController.getWindowMustEndMinutesInAdvanceOfTrain()+"m after window. Ceiling/floor applied is "+BIASMaintenanceWindowAnalysisConfigPageController.getIncrementMinutes()+"m/"+BIASMaintenanceWindowAnalysisConfigPageController.getDecrementMinutes()+"m.");
				rowCounter++;
				row = observedWindowsOnMOWLinesSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("Created on "+ConvertDateTime.getDateStamp()+" at "+ConvertDateTime.getTimeStamp());

				// Resize all columns to fit the content size
				for (int k = 0; k <= 3; k++) 
				{
					if (k == 0) 
					{
						observedWindowsOnMOWLinesSheet.setColumnWidth(k, 2600);
					}
					else if ((k == 1) || (k == 2) || (k == 3))
					{
						observedWindowsOnMOWLinesSheet.setColumnWidth(k, 4800);
					}
				}
			}
		}
	}

	public String getResultsMessageWrite3()
	{
		return resultsMessage;
	}
}