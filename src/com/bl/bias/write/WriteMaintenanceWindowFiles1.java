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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.analyze.MaintenanceWindowAnalysis;
import com.bl.bias.app.BIASMaintenanceWindowAnalysisConfigPageController;
import com.bl.bias.app.BIASMaintenanceWindowAnalysisPageController;
import com.bl.bias.objects.MaintenanceWindowAnalysisSegment;
import com.bl.bias.objects.MaintenanceWindowAnalysisTrainTraversal;
import com.bl.bias.tools.ConvertDateTime;

public class WriteMaintenanceWindowFiles1
{
	protected String resultsMessage = "\nStarted writing output file at "+ConvertDateTime.getTimeStamp();

	Integer simDay;

	String dayOfWeek;
	String trainSymbol;
	String trainDirection;
	String entryNode;
	String exitNode;

	Double headEndOS;
	Double tailEndOS;
	Double actualDuration;

	ArrayList<MaintenanceWindowAnalysisSegment> segments = new ArrayList<MaintenanceWindowAnalysisSegment>();

	XSSFWorkbook workbook = new XSSFWorkbook();

	public WriteMaintenanceWindowFiles1(String textAreaContents, String locationOfInputFiles)
	{
		// Get segments
		segments.addAll(BIASMaintenanceWindowAnalysisPageController.getSegments());

		// Set styles
		CellStyle style0 = workbook.createCellStyle();
		CellStyle style1 = workbook.createCellStyle();
		CellStyle style2 = workbook.createCellStyle();
		CellStyle style3 = workbook.createCellStyle();
		CellStyle style4 = workbook.createCellStyle();
		CellStyle style5 = workbook.createCellStyle();

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

		// Style 5 - Centered, wrapped, 11pt, black text, DD:HH:MM:SS
		style5.setDataFormat(workbook.createDataFormat().getFormat("dd:HH:mm:ss"));
		style5.setAlignment(HorizontalAlignment.CENTER);  
		style5.setWrapText(true);
		style5.setFont(font1);

		if (BIASMaintenanceWindowAnalysisConfigPageController.getGenerateTraversals())
		{
			// Write Trains Traversing Line Segment
			resultsMessage += "\nWriting train traversals";

			for (int i = 0; i < segments.size(); i++)
			{
				String lineName = segments.get(i).getLineName().replace("[", "").replace("]", "").replace("MOW_", "");
				XSSFSheet trainsTraversingMOWLinesSheet = workbook.createSheet(lineName+" Traversals");

				ArrayList<MaintenanceWindowAnalysisTrainTraversal> trainTraversals = new ArrayList<MaintenanceWindowAnalysisTrainTraversal>();
				trainTraversals.addAll(segments.get(i).getTrainTraversalsInLine());

				// Header rows
				// Case name
				trainsTraversingMOWLinesSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

				Row row;
				Cell cell;

				row = trainsTraversingMOWLinesSheet.createRow(0);
				cell = row.createCell(0);
				cell.setCellStyle(style0);
				cell.setCellValue(lineName + " Line - Train Traversals");

				// Data headers
				row = trainsTraversingMOWLinesSheet.createRow(1);

				cell = row.createCell(0);
				cell.setCellStyle(style1);
				cell.setCellValue("Traversal #");

				cell = row.createCell(1);
				cell.setCellStyle(style1);
				cell.setCellValue("Train Symbol");

				cell = row.createCell(2);
				cell.setCellStyle(style1);
				cell.setCellValue("Train Direction");

				cell = row.createCell(3);
				cell.setCellStyle(style1);
				cell.setCellValue("Entry Node");

				cell = row.createCell(4);
				cell.setCellStyle(style1);
				cell.setCellValue("Exit Node");

				cell = row.createCell(5);
				cell.setCellStyle(style1);
				cell.setCellValue("Head-end on MOW Line (day:hh:mm:ss)");

				cell = row.createCell(6);
				cell.setCellStyle(style1);
				cell.setCellValue("Tail-end off MOW Line (day:hh:mm:ss)");

				cell = row.createCell(7);
				cell.setCellStyle(style1);
				cell.setCellValue("Duration (hh:mm:ss)");

				// Traversals
				int rowCounter = 2;
				int validTraversalCount = 0;
				double sumOfValidTraversalDurations = 0.0;

				for (int j = 0; j < trainTraversals.size(); j++)
				{
					row = trainsTraversingMOWLinesSheet.createRow(rowCounter);

					// Traversal cycle
					cell = row.createCell(0);
					cell.setCellStyle(style1);
					cell.setCellValue(rowCounter - 1);

					// Train Symbol
					cell = row.createCell(1);
					cell.setCellStyle(style1);
					cell.setCellValue(trainTraversals.get(j).getTrainSymbol());

					// Direction
					cell = row.createCell(2);
					cell.setCellStyle(style1);
					cell.setCellValue(trainTraversals.get(j).getTrainDirection());

					// Entry node
					cell = row.createCell(3);
					cell.setCellStyle(style1);
					cell.setCellValue(trainTraversals.get(j).getEntryNode());

					// Exit node
					cell = row.createCell(4);
					cell.setCellStyle(style1);
					cell.setCellValue(trainTraversals.get(j).getExitNode());

					// Head-end on MOW segment
					cell = row.createCell(5);
					cell.setCellStyle(style1);
					cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(trainTraversals.get(j).getEntryNodeOSSeconds()));

					// Head-end off MOW segment
					cell = row.createCell(6);
					cell.setCellStyle(style1);
					cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(trainTraversals.get(j).getExitNodeOSSeconds()));

					// Duration in seconds
					cell = row.createCell(7);
					cell.setCellStyle(style4);

					double serialSeconds;
					double displaySeconds = ConvertDateTime.convertSecondsToSerial(trainTraversals.get(j).getExitNodeOSSeconds() - trainTraversals.get(j).getEntryNodeOSSeconds());

					if (trainTraversals.get(j).getExitNodeOSSeconds() < MaintenanceWindowAnalysis.getBeginningOfAnalysisPeriodInSeconds())  // Crossing entirely before analysis period
					{
						serialSeconds = 0.0;
					}
					else if (trainTraversals.get(j).getEntryNodeOSSeconds() > MaintenanceWindowAnalysis.getEndOfAnalysisPeriodInSeconds())  // Crossing entirely after analysis period
					{
						serialSeconds = 0.0;
					}
					else if (trainTraversals.get(j).getEntryNodeOSSeconds() < MaintenanceWindowAnalysis.getBeginningOfAnalysisPeriodInSeconds())  // Crossing partially before statistical period
					{
						serialSeconds = ConvertDateTime.convertSecondsToSerial(trainTraversals.get(j).getExitNodeOSSeconds() - MaintenanceWindowAnalysis.getBeginningOfAnalysisPeriodInSeconds());
						validTraversalCount++;
					}
					else if (trainTraversals.get(j).getExitNodeOSSeconds() > MaintenanceWindowAnalysis.getEndOfAnalysisPeriodInSeconds())  // Crossing partially after statistical period
					{
						serialSeconds = ConvertDateTime.convertSecondsToSerial(MaintenanceWindowAnalysis.getEndOfAnalysisPeriodInSeconds() - trainTraversals.get(j).getEntryNodeOSSeconds());
						validTraversalCount++;
					}
					else // All other traversals
					{
						serialSeconds = ConvertDateTime.convertSecondsToSerial(trainTraversals.get(j).getExitNodeOSSeconds() - trainTraversals.get(j).getEntryNodeOSSeconds());
						validTraversalCount++;
					}
					sumOfValidTraversalDurations += serialSeconds;
					cell.setCellValue(displaySeconds);
					rowCounter++;
				}

				// Footer rows
				// Add sum and mean of all cycles
				row = trainsTraversingMOWLinesSheet.createRow(rowCounter);
				cell = row.createCell(6);
				cell.setCellStyle(style3);
				cell.setCellValue("Sum of traversal durations(dd:hh:mm:ss):");

				cell = row.createCell(7);
				cell.setCellStyle(style5);
				cell.setCellValue(sumOfValidTraversalDurations);

				rowCounter++;
				row = trainsTraversingMOWLinesSheet.createRow(rowCounter);
				cell = row.createCell(6);
				cell.setCellStyle(style3);
				cell.setCellValue("Avg traversal duration(hh:mm:ss):");

				cell = row.createCell(7);
				cell.setCellStyle(style4);
				cell.setCellValue(sumOfValidTraversalDurations/validTraversalCount);

				rowCounter++;
				row = trainsTraversingMOWLinesSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("*** First and last traversals may show event times before/after the analysis period.  However, time outside of the analysis period is not included in the duration sums or averages.");

				rowCounter++;
				row = trainsTraversingMOWLinesSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("Created on "+ConvertDateTime.getDateStamp()+" at "+ConvertDateTime.getTimeStamp());

				// Resize all columns to fit the content size
				for (int j = 0; j <= 7; j++) 
				{
					if (j == 0) 
					{
						trainsTraversingMOWLinesSheet.setColumnWidth(j, 2500);
					}
					else if (j == 1)
					{
						trainsTraversingMOWLinesSheet.setColumnWidth(j, 5300);
					}
					else if (j == 2)
					{
						trainsTraversingMOWLinesSheet.setColumnWidth(j, 2300);
					}
					else if ((j == 5) || (j == 6) || (j == 7))
					{
						trainsTraversingMOWLinesSheet.setColumnWidth(j, 4800);
					}
					else if ((j == 3) || (j == 4))
					{
						trainsTraversingMOWLinesSheet.setColumnWidth(j, 3500);
					}
				}
			}
		}
	}

	public String getResultsMessageWrite1()
	{
		return resultsMessage;
	}
}