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

import com.bl.bias.analyze.MaintenanceWindowAnalysis;
import com.bl.bias.app.BIASMaintenanceWindowAnalysisConfigPageController;
import com.bl.bias.app.BIASMaintenanceWindowAnalysisPageController;
import com.bl.bias.objects.MaintenanceWindowAnalysisSegment;
import com.bl.bias.objects.MaintenanceWindowAnalysisSubLineOccupancy;
import com.bl.bias.tools.ConvertDateTime;

public class WriteMaintenanceWindowFiles2 extends WriteMaintenanceWindowFiles1
{
	Integer simDay;

	String dayOfWeek;
	String nodeA;
	String nodeB;
	String trainsAndDirectionsInOccupancy;

	Double headEndOS;
	Double tailEndOS;
	Double actualDuration;

	ArrayList<MaintenanceWindowAnalysisSegment> segments = new ArrayList<MaintenanceWindowAnalysisSegment>();

	String results = getResultsMessageWrite1();

	public WriteMaintenanceWindowFiles2(String textAreaContents, String locationOfInputFiles)
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

		if (BIASMaintenanceWindowAnalysisConfigPageController.getGenerateOccupancies())
		{
			// Write Occupancies in Line Segment
			resultsMessage += "\nWriting occupancy periods";

			for (int i = 0; i < segments.size(); i++)
			{
				String lineName = segments.get(i).getLineName().replace("[", "").replace("]", "").replace("MOW_", "");
				XSSFSheet observedOccupanciesOnMOWLinesSheet = workbook.createSheet(lineName+" Occupancies");

				// Header rows
				// Case name
				observedOccupanciesOnMOWLinesSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

				Row row;
				Cell cell;

				row = observedOccupanciesOnMOWLinesSheet.createRow(0);
				cell = row.createCell(0);
				cell.setCellStyle(style0);
				cell.setCellValue(lineName + " Line - Occupancies");

				// Data headers
				row = observedOccupanciesOnMOWLinesSheet.createRow(1);

				cell = row.createCell(0);
				cell.setCellStyle(style1);
				cell.setCellValue("Occupancy #");

				cell = row.createCell(1);
				cell.setCellStyle(style1);
				cell.setCellValue("Head-end on MOW Line (day:hh:mm:ss)");

				cell = row.createCell(2);
				cell.setCellStyle(style1);
				cell.setCellValue("Tail-end off MOW Line (day:hh:mm:ss)");

				cell = row.createCell(3);
				cell.setCellStyle(style1);
				cell.setCellValue("Duration (hh:mm:ss)");

				cell = row.createCell(4);
				cell.setCellStyle(style5);
				cell.setCellValue("Train Symbols and Directions in Occupancy");	

				int rowCounter = 2;
				int validOccupancyCount = 0;
				double sumOfValidOccupancyDurations = 0.0;

				// For each subline
				ArrayList<String> subLinesKeySet = new ArrayList<String>(segments.get(i).getSubLinesOccupancies().keySet());
				for (int j = 0; j < subLinesKeySet.size(); j++)
				{
					row = observedOccupanciesOnMOWLinesSheet.createRow(rowCounter);
					cell = row.createCell(0);
					cell.setCellStyle(style1);
					cell.setCellValue("Track "+subLinesKeySet.get(j));
					observedOccupanciesOnMOWLinesSheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 4));
					rowCounter++;

					// For each occupancy in the subline
					ArrayList<MaintenanceWindowAnalysisSubLineOccupancy> subLineValueSet = new ArrayList<MaintenanceWindowAnalysisSubLineOccupancy>(segments.get(i).getSubLinesOccupancies().get(subLinesKeySet.get(j)));
					if (subLineValueSet.size() == 0)
					{
						row = observedOccupanciesOnMOWLinesSheet.createRow(rowCounter);
						cell = row.createCell(3);
						cell.setCellStyle(style1);
						cell.setCellValue("* None *");
						rowCounter++;
					}
					else
					{
						for (int k = 0; k < subLineValueSet.size(); k++)
						{
							// Occupancies
							row = observedOccupanciesOnMOWLinesSheet.createRow(rowCounter);

							// Occupancy #
							cell = row.createCell(0);
							cell.setCellStyle(style1);
							cell.setCellValue(validOccupancyCount + 1);

							// Head-end on MOW segment
							cell = row.createCell(1);
							cell.setCellStyle(style1);
							cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(subLineValueSet.get(k).getOccupancyStartTimeInSeconds()));

							// Tail-end off MOW segment
							cell = row.createCell(2);
							cell.setCellStyle(style1);
							cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(subLineValueSet.get(k).getOccupancyEndTimeInSeconds()));

							double serialSeconds;
							double displaySeconds = ConvertDateTime.convertSecondsToSerial(subLineValueSet.get(k).getOccupancyEndTimeInSeconds() - subLineValueSet.get(k).getOccupancyStartTimeInSeconds());

							if (subLineValueSet.get(k).getOccupancyEndTimeInSeconds() < MaintenanceWindowAnalysis.getBeginningOfAnalysisPeriodInSeconds())  // Occupancy entirely before analysis period
							{
								serialSeconds = 0.0;
							}
							else if (subLineValueSet.get(k).getOccupancyStartTimeInSeconds() > MaintenanceWindowAnalysis.getEndOfAnalysisPeriodInSeconds())  // Occupancy entirely after analysis period
							{
								serialSeconds = 0.0;
							}
							else if (subLineValueSet.get(k).getOccupancyStartTimeInSeconds() < MaintenanceWindowAnalysis.getBeginningOfAnalysisPeriodInSeconds())  // Occupancy partially before statistical period
							{
								serialSeconds = ConvertDateTime.convertSecondsToSerial(subLineValueSet.get(k).getOccupancyEndTimeInSeconds() - MaintenanceWindowAnalysis.getBeginningOfAnalysisPeriodInSeconds());
								validOccupancyCount++;
							}
							else if (subLineValueSet.get(k).getOccupancyEndTimeInSeconds() > MaintenanceWindowAnalysis.getEndOfAnalysisPeriodInSeconds())  // Occupancy partially after statistical period
							{
								serialSeconds = ConvertDateTime.convertSecondsToSerial(MaintenanceWindowAnalysis.getEndOfAnalysisPeriodInSeconds() - subLineValueSet.get(k).getOccupancyStartTimeInSeconds());
								validOccupancyCount++;
							}
							else // All other occupancies
							{
								serialSeconds = ConvertDateTime.convertSecondsToSerial(subLineValueSet.get(k).getOccupancyEndTimeInSeconds() - subLineValueSet.get(k).getOccupancyStartTimeInSeconds());
								validOccupancyCount++;
							}
							sumOfValidOccupancyDurations += serialSeconds;
							cell = row.createCell(3);
							cell.setCellStyle(style4);
							cell.setCellValue(displaySeconds);

							// Train symbols and directions in occupancy
							cell = row.createCell(4);
							cell.setCellStyle(style5);
							cell.setCellValue(subLineValueSet.get(k).getTrainSymbolsAndDirectionsInOccupancy().toString());

							rowCounter++;
						}
					}
				}

				// Footer rows
				// Add sum and mean of all cycles
				row = observedOccupanciesOnMOWLinesSheet.createRow(rowCounter);
				cell = row.createCell(2);
				cell.setCellStyle(style3);
				cell.setCellValue("Sum of occupancy durations(dd:hh:mm:ss):");

				cell = row.createCell(3);
				cell.setCellStyle(style6);
				cell.setCellValue(sumOfValidOccupancyDurations);

				rowCounter++;
				row = observedOccupanciesOnMOWLinesSheet.createRow(rowCounter);
				cell = row.createCell(2);
				cell.setCellStyle(style3);
				cell.setCellValue("Avg occupancy duration(hh:mm:ss):");

				cell = row.createCell(3);
				cell.setCellStyle(style4);
				cell.setCellValue(sumOfValidOccupancyDurations/validOccupancyCount);

				rowCounter++;
				row = observedOccupanciesOnMOWLinesSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("*** First and last occupancies may show event times before/after the analysis period.  However, time outside of the analysis period is not included in the duration sums or averages.");

				rowCounter++;
				row = observedOccupanciesOnMOWLinesSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("Created on "+ConvertDateTime.getDateStamp()+" at "+ConvertDateTime.getTimeStamp());

				// Resize all columns to fit the content size
				for (int k = 0; k <= 3; k++) 
				{
					if (k == 0) 
					{
						observedOccupanciesOnMOWLinesSheet.setColumnWidth(k, 2600);
					}
					else if ((k == 1) || (k == 2) || (k == 3))
					{
						observedOccupanciesOnMOWLinesSheet.setColumnWidth(k, 3800);
					}
				}

				observedOccupanciesOnMOWLinesSheet.autoSizeColumn(4);
			}
		}
	}

	public String getResultsMessageWrite2()
	{
		return resultsMessage;
	}
}