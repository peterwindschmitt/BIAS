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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.analyze.BridgeClosureAnalysis;
import com.bl.bias.app.BIASBridgeClosureAnalysisConfigPageController;
import com.bl.bias.app.BIASBridgeClosureAnalysisController;
import com.bl.bias.objects.BridgeAnalysisCrossing;
import com.bl.bias.tools.ConvertDateTime;

public class WriteBridgeFiles1
{
	private LocalTime startWriteFileTime = ConvertDateTime.getTimeStamp();
	protected String resultsMessage = "\nStarted writing output file at "+startWriteFileTime;

	Integer cycleNumber;
	Integer simDay;

	String dayOfWeek;
	String trainSymbol;
	String trainDirection;
	String entryNode;
	String exitNode;

	Double actualDuration;
	Double reportedDuration;

	Double sumOfActualCrossingDurations = 0.0;
	Double sumOfReportedCrossingDurations = 0.0;

	ArrayList<BridgeAnalysisCrossing> crossings = new ArrayList<BridgeAnalysisCrossing>();

	XSSFWorkbook workbook = new XSSFWorkbook();

	public WriteBridgeFiles1(String textAreaContents, String locationOfInputFiles)
	{
		// Get crossings
		crossings.addAll(BridgeClosureAnalysis.getSortedCrossings());

		// Set styles
		CellStyle style0 = workbook.createCellStyle();
		CellStyle style1 = workbook.createCellStyle();
		CellStyle style2 = workbook.createCellStyle();
		CellStyle style3 = workbook.createCellStyle();
		CellStyle style4 = workbook.createCellStyle();

		// Write Trains Crossing Bridge
		XSSFSheet trainsCrossingBridgeSheet = workbook.createSheet("Train Crossings");
		trainsCrossingBridgeSheet.setDisplayGridlines(false);
		resultsMessage += "\nWriting train crossings";

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

		// Header rows
		// Case name
		trainsCrossingBridgeSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

		Row row;
		Cell cell;

		row = trainsCrossingBridgeSheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(style0);
		cell.setCellValue(BIASBridgeClosureAnalysisController.getAnalyzedLine()+" Train Crossings");

		// Data headers
		row = trainsCrossingBridgeSheet.createRow(1);

		cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("Crossing #");

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
		cell.setCellValue("Head-end on Bridge Circuit (day:hh:mm:ss)");

		cell = row.createCell(6);
		cell.setCellStyle(style1);
		cell.setCellValue("Tail-end off Bridge Circuit (day:hh:mm:ss)");

		cell = row.createCell(7);
		cell.setCellStyle(style1);
		cell.setCellValue("Actual Duration (hh:mm:ss)");

		cell = row.createCell(8);
		cell.setCellStyle(style1);
		cell.setCellValue("Reported Duration (w/ceiling function, hh:mm:ss)");

		// Crossings
		for (int i = 0; i < crossings.size(); i++)
		{
			row = trainsCrossingBridgeSheet.createRow(i+2);

			// Crossing cycle
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(i + 1);

			// Train Symbol
			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue(crossings.get(i).getTrainSymbol());

			// Direction
			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(crossings.get(i).getTrainDirection());

			// Entry node
			cell = row.createCell(3);
			cell.setCellStyle(style1);
			cell.setCellValue(crossings.get(i).getEntryNode());

			// Exit node
			cell = row.createCell(4);
			cell.setCellStyle(style1);
			cell.setCellValue(crossings.get(i).getExitNode());

			// Head-end on bridge
			cell = row.createCell(5);
			cell.setCellStyle(style1);
			cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(crossings.get(i).getEntryNodeOSSeconds()));

			// Head-end off bridge
			cell = row.createCell(6);
			cell.setCellStyle(style1);
			cell.setCellValue(ConvertDateTime.convertSecondsToDayHHMMSSString(crossings.get(i).getExitNodeOSSeconds()));

			// Actual duration in seconds
			cell = row.createCell(7);
			cell.setCellStyle(style4);

			double serialSeconds = 0.0;
			if ((i == 0) && (crossings.get(i).getEntryNodeOSSeconds() < BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds()) 
					&& (crossings.get(i).getExitNodeOSSeconds() < BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds()))  // First crossing and Entry OS and Exit OS are before beginning of Analysis Period
			{
				serialSeconds = 0;
			}
			else if ((i == 0) && (crossings.get(i).getEntryNodeOSSeconds() < BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds()))  // First crossing and Entry OS BUT NOT Exit OS is before beginning of Analysis Period 
			{
				serialSeconds = ConvertDateTime.convertSecondsToSerial(crossings.get(i).getExitNodeOSSeconds() - BridgeClosureAnalysis.getBeginningOfAnalysisPeriodInSeconds());
			}
			else if ((i == crossings.size() - 1) && (crossings.get(i).getExitNodeOSSeconds() > BridgeClosureAnalysis.getEndOfAnalysisPeriodInSeconds()))  // Last crossing
			{
				serialSeconds = ConvertDateTime.convertSecondsToSerial(BridgeClosureAnalysis.getEndOfAnalysisPeriodInSeconds() - crossings.get(i).getEntryNodeOSSeconds());
			}
			else // All other traversals
			{
				serialSeconds = ConvertDateTime.convertSecondsToSerial(crossings.get(i).getExitNodeOSSeconds() - crossings.get(i).getEntryNodeOSSeconds());
			}
			sumOfActualCrossingDurations += serialSeconds;
			cell.setCellValue(serialSeconds);

			// Reported duration in seconds
			cell = row.createCell(8);
			cell.setCellStyle(style4);
			String ceilingIncrementAsString = BIASBridgeClosureAnalysisConfigPageController.getIncrementCrossing();
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

			sumOfReportedCrossingDurations += ceilingTimeAsSerial;
		}

		// Footer rows
		// Add sum and mean of all cycles
		row = trainsCrossingBridgeSheet.createRow(crossings.size() + 3);
		cell = row.createCell(6);
		cell.setCellStyle(style3);
		cell.setCellValue("Sum of crossing durations(hh:mm:ss):");

		cell = row.createCell(7);
		cell.setCellStyle(style4);
		cell.setCellValue(sumOfActualCrossingDurations);

		cell = row.createCell(8);
		cell.setCellStyle(style4);
		cell.setCellValue(sumOfReportedCrossingDurations);

		row = trainsCrossingBridgeSheet.createRow(crossings.size() + 4);
		cell = row.createCell(6);
		cell.setCellStyle(style3);
		cell.setCellValue("Avg crossing duration(hh:mm:ss):");

		cell = row.createCell(7);
		cell.setCellStyle(style4);
		cell.setCellValue(sumOfActualCrossingDurations/crossings.size());

		cell = row.createCell(8);
		cell.setCellStyle(style4);
		cell.setCellValue(sumOfReportedCrossingDurations/crossings.size());

		// Timestamp and footnote
		LocalDate creationDate = ConvertDateTime.getDateStamp();
		LocalTime creationTime = ConvertDateTime.getTimeStamp();

		row = trainsCrossingBridgeSheet.createRow(crossings.size() + 5);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("First and last crossings may show event times before/after the analysis period.  However, time outside of the analysis period is not included in the durations.");

		row = trainsCrossingBridgeSheet.createRow(crossings.size() + 6);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("Above data does not include signal setup or the time for bridge to lower and raise.  See next sheet for closures (including signal setup, bridge lower and raise times) created by above crossings.");

		row = trainsCrossingBridgeSheet.createRow(crossings.size() + 7);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("Created on "+creationDate+" at "+creationTime);

		// Resize all columns to fit the content size
		for (int i = 0; i <= 8; i++) 
		{
			if (i == 0) 
			{
				trainsCrossingBridgeSheet.setColumnWidth(i, 2500);
			}
			else if (i == 1)
			{
				trainsCrossingBridgeSheet.setColumnWidth(i, 5800);
			}
			else if (i == 2)
			{
				trainsCrossingBridgeSheet.setColumnWidth(i, 2300);
			}
			else if ((i == 5) || (i == 6) || (i == 7) || (i == 8))
			{
				trainsCrossingBridgeSheet.setColumnWidth(i, 4800);
			}
			else if ((i == 3) || (i == 4))
			{
				trainsCrossingBridgeSheet.setColumnWidth(i, 3500);
			}
		}
	}

	public String getResultsMessageWrite1()
	{
		return resultsMessage;
	}
}