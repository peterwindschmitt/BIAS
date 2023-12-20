package com.bl.bias.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.app.BIASUscgBridgeComplianceAnalysisConfigPageController;
import com.bl.bias.objects.BridgeComplianceClosure;
import com.bl.bias.tools.ConvertDateTime;

public class WriteBridgeComplianceFiles2 extends WriteBridgeComplianceFiles1
{
	String resultsMessage = getResultsMessageWrite1();

	private final String legalDisclaimer = "*** CONFIDENTIAL AND PREPARED AT THE DIRECTION OF COUNSEL ***";
	private final Integer marineAccessPeriodsPerWeek = BIASUscgBridgeComplianceAnalysisConfigPageController.getMarinePeriodsPerWeekAsInteger();
	
	public WriteBridgeComplianceFiles2(ArrayList<BridgeComplianceClosure> closures, String bridgeAndSpan, String textArea, String outputSpreadsheetPath) 
	{
		super(closures, bridgeAndSpan, textArea, outputSpreadsheetPath);

		if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeSummaryResultsOnSpreadsheet())
		{

			int rowCounter = 0;

			// Set styles
			CellStyle style0 = workbook.createCellStyle();
			CellStyle style1 = workbook.createCellStyle();
			CellStyle style2 = workbook.createCellStyle();
			CellStyle style3 = workbook.createCellStyle();
			CellStyle style6 = workbook.createCellStyle();
			CellStyle style7 = workbook.createCellStyle();
			CellStyle style8 = workbook.createCellStyle();
			CellStyle style9 = workbook.createCellStyle();
			CellStyle style10 = workbook.createCellStyle();

			// Write violations
			XSSFSheet complianceSheet = workbook.createSheet("Compliance Summary");
			complianceSheet.setDisplayGridlines(false);
			resultsMessage += "\nWriting compliance summary";

			// Fonts
			// Font 0 - 16pt White text
			XSSFFont font0 = workbook.createFont();
			font0.setFontHeightInPoints((short) 16);
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

			// Font 3 - 14pt White text
			XSSFFont font3 = workbook.createFont();
			font3.setFontHeightInPoints((short) 14);
			font3.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
			font3.setFontName("Calibri");

			// Cell styles
			// Style 0 - Centered, non- wrapped 16pt, white text against blue background
			style0.setAlignment(HorizontalAlignment.CENTER);  
			style0.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
			style0.setFillPattern(FillPatternType.FINE_DOTS);
			style0.setWrapText(false);
			style0.setFont(font0);

			// Style 1 - Centered, wrapped, 11pt, black text, with border
			style1.setAlignment(HorizontalAlignment.CENTER);  
			style1.setWrapText(true);
			style1.setFont(font1);
			style1.setBorderBottom(BorderStyle.THIN);
			style1.setBorderLeft(BorderStyle.THIN);
			style1.setBorderRight(BorderStyle.THIN);
			style1.setBorderTop(BorderStyle.THIN);

			// Style 2 - Left aligned, wrapped, 11pt, black text
			style2.setAlignment(HorizontalAlignment.LEFT);  
			style2.setWrapText(true);
			style2.setFont(font1);

			// Style 3 - Right aligned, non-wrapped, 11pt, black text
			style3.setAlignment(HorizontalAlignment.RIGHT);  
			style3.setWrapText(false);
			style3.setFont(font1);

			// Style 6 - Centered, non- wrapped 14pt, white text against black background
			style6.setAlignment(HorizontalAlignment.CENTER);  
			style6.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
			style6.setFillPattern(FillPatternType.FINE_DOTS);
			style6.setWrapText(false);
			style6.setFont(font3);

			// Style 7 - Left aligned, not wrapped, 8pt, black text, no border
			style7.setAlignment(HorizontalAlignment.LEFT);  
			style7.setFont(font2);

			// Style 8 - Right aligned, non-wrapped, 11pt, black text, no border
			style8.setAlignment(HorizontalAlignment.RIGHT);  
			style8.setWrapText(false);
			style8.setFont(font1);

			// Style 9 - Left aligned, not wrapped, 11pt, black text, with border
			style9.setAlignment(HorizontalAlignment.LEFT);  
			style9.setFont(font1);
			style9.setBorderBottom(BorderStyle.THIN);
			style9.setBorderLeft(BorderStyle.THIN);
			style9.setBorderRight(BorderStyle.THIN);
			style9.setBorderTop(BorderStyle.THIN);

			// Style 10 - Center aligned, not wrapped, 11pt, black text, with border, x.x%
			style10.setAlignment(HorizontalAlignment.CENTER);  
			style10.setFont(font1);
			style10.setDataFormat(workbook.createDataFormat().getFormat("0.0"));
			style10.setBorderBottom(BorderStyle.THIN);
			style10.setBorderLeft(BorderStyle.THIN);
			style10.setBorderRight(BorderStyle.THIN);
			style10.setBorderTop(BorderStyle.THIN);
			// Header rows
			Row row;
			Cell cell;

			complianceSheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 5));
			row = complianceSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style0);
			cell.setCellValue(bridgeAndSpan);
			rowCounter++;


			// Legal disclaimer
			if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeConfidentialityDisclaimer())
			{
				complianceSheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 5));
				row = complianceSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style6);
				cell.setCellValue(legalDisclaimer);
				rowCounter++;
			}

			// All hours header
			rowCounter++;
			rowCounter++;
			row = complianceSheet.createRow(rowCounter);
			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("Weekly Periods \n["+marineAccessPeriodsPerWeek+"]");


			// High-use period header
			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue("Weekly Closures \n["+closures.size()+"]");
			rowCounter++;			

			// In-circuit delay closures
			int inCircuitDelayCount = 0;
			for (int i = 0; i < closures.size(); i++)
			{
				if (closures.get(i).getInCircuitException())
					inCircuitDelayCount++;
			}
			double inCircuitPeriodPercentageAsDouble = 100 * ((double) inCircuitDelayCount / (double) (marineAccessPeriodsPerWeek));
			double inCircuitClosurePercentageAsDouble = 100 * ((double) inCircuitDelayCount / (double) closures.size());
			
			row = complianceSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style9);
			cell.setCellValue("In-Circuit delay count: ");	
			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue(inCircuitDelayCount);	
			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(inCircuitDelayCount);	
			rowCounter++;
			
			row = complianceSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style9);
			cell.setCellValue("In-Circuit delay %: ");	
			cell = row.createCell(1);
			cell.setCellStyle(style10);
			cell.setCellValue(inCircuitPeriodPercentageAsDouble);	
			cell = row.createCell(2);
			cell.setCellStyle(style10);
			cell.setCellValue(inCircuitClosurePercentageAsDouble);	
			rowCounter++;
			rowCounter++;

			// Period violation
			int periodViolationCount = 0;
			int closureViolationCount = 0;
			for (int i = 0; i < closures.size(); i++)
			{
				if (closures.get(i).getMarineAccessPeriodViolation() > 0)
				{
					periodViolationCount+= closures.get(i).getMarineAccessPeriodViolation();
					closureViolationCount++;
				}
			}
			double violationPeriodPercentageAsDouble = 100 * ((double) periodViolationCount / (double) (marineAccessPeriodsPerWeek));
			double violationClosurePercentageAsDouble = 100 * ((double) closureViolationCount / (double) closures.size());
			
			row = complianceSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style9);
			cell.setCellValue("Violation count: ");	
			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue(periodViolationCount);
			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(closureViolationCount);
			rowCounter++;
			
			row = complianceSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style9);
			cell.setCellValue("Violation %: ");	
			cell = row.createCell(1);
			cell.setCellStyle(style10);
			cell.setCellValue(violationPeriodPercentageAsDouble);	
			cell = row.createCell(2);
			cell.setCellStyle(style10);
			cell.setCellValue(violationClosurePercentageAsDouble);	
			rowCounter++;
			rowCounter++;

			// Duration violation
			int durationViolationCount = 0;
			for (int i = 0; i < closures.size(); i++)
			{
				if (closures.get(i).getClosureDurationViolation())
				{
					durationViolationCount++;
				}
			}
			double durationViolationPercentageAsDouble = 100 * ((double) durationViolationCount / (double) closures.size());
			
			row = complianceSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style9);
			cell.setCellValue("Duration violation count: ");	
			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("N/A");
			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(durationViolationCount);
			rowCounter++;
			
			row = complianceSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style9);
			cell.setCellValue("Duration violation %: ");
			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("N/A");
			cell = row.createCell(2);
			cell.setCellStyle(style10);
			cell.setCellValue(durationViolationPercentageAsDouble);
			rowCounter++;
			rowCounter++;

			// Compliance Rate
			double complianceRate = 100.0 * (1.0 - (((double) periodViolationCount + (double) closureViolationCount + (double) durationViolationCount) / ((double) marineAccessPeriodsPerWeek + (double) closures.size() + 168.0))); 
			row = complianceSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style9);
			cell.setCellValue("Compliance %: ");
			cell = row.createCell(1);
			cell.setCellStyle(style10);
			cell.setCellValue(complianceRate);
			rowCounter++;
			row = complianceSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style7);
			cell.setCellValue("Compliance % is computed as:  1 - (Weekly Period Violation Count + Weekly Closure Violation Count + Duration Violation Count) / (Weekly Periods + Weekly Closures + 168 (potential duration violation opportunities))");	
			rowCounter++;
						
			// Timestamp and footnote
			LocalDate creationDate = ConvertDateTime.getDateStamp();
			LocalTime creationTime = ConvertDateTime.getTimeStamp();
			rowCounter++;
			row = complianceSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style7);
			cell.setCellValue("Created on "+creationDate+" at "+creationTime);

			// Resize rows
			for (int i = 0; i <= 5 ; i++) 
			{
				if (i == 0)
				{
					complianceSheet.setColumnWidth(i, 10000);
				}
				else
					complianceSheet.setColumnWidth(i, 5000);
			}			
		}
	}

	public String getResultsMessageWrite2()
	{
		return resultsMessage;
	}
}