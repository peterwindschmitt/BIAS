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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.analyze.BridgeComplianceAnalysis;
import com.bl.bias.app.BIASUscgBridgeComplianceAnalysisConfigPageController;
import com.bl.bias.objects.BridgeComplianceClosure;
import com.bl.bias.tools.ConvertDateTime;

public class WriteBridgeComplianceFiles1
{
	private LocalTime startWriteFileTime = ConvertDateTime.getTimeStamp();
	private final String legalDisclaimer = "*** CONFIDENTIAL AND PREPARED AT THE DIRECTION OF COUNSEL ***";
	protected String resultsMessage = "\nStarted writing output file at "+startWriteFileTime;

	XSSFWorkbook workbook = new XSSFWorkbook();

	private static IndexedColors[] colors = new IndexedColors[2];

	public WriteBridgeComplianceFiles1(ArrayList<BridgeComplianceClosure> closures, String bridgeAndSpan, String textArea, String outputSpreadsheetPath) 
	{
		int rowCounter = 0;

		// Colors used to indicate in-circuit delay periods or violations
		colors[0] = IndexedColors.YELLOW;  
		colors[1] = IndexedColors.RED;

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

		// Write Trains Crossing Bridge
		XSSFSheet closuresSheet = workbook.createSheet("Closures");
		closuresSheet.setDisplayGridlines(false);
		resultsMessage += "\nWriting closures";

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
		// Style 0 - Centered, non- wrapped 16pt, white text against blue background, with border
		style0.setAlignment(HorizontalAlignment.CENTER);  
		style0.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
		style0.setFillPattern(FillPatternType.FINE_DOTS);
		style0.setWrapText(false);
		style0.setFont(font0);
		style0.setBorderBottom(BorderStyle.THIN);
		style0.setBorderLeft(BorderStyle.THIN);
		style0.setBorderRight(BorderStyle.THIN);
		style0.setBorderTop(BorderStyle.THIN);

		// Style 1 - Centered, wrapped, 11pt, black text, with border
		style1.setAlignment(HorizontalAlignment.CENTER);  
		style1.setWrapText(true);
		style1.setFont(font1);
		style1.setBorderBottom(BorderStyle.THIN);
		style1.setBorderLeft(BorderStyle.THIN);
		style1.setBorderRight(BorderStyle.THIN);
		style1.setBorderTop(BorderStyle.THIN);;

		// Style 2 - Left aligned, wrapped, 11pt, black text, with border
		style2.setAlignment(HorizontalAlignment.LEFT);  
		style2.setWrapText(true);
		style2.setFont(font1);
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);

		// Style 3 - Right aligned, non-wrapped, 11pt, black text, with border
		style3.setAlignment(HorizontalAlignment.RIGHT);  
		style3.setWrapText(false);
		style3.setFont(font1);
		style3.setBorderBottom(BorderStyle.THIN);
		style3.setBorderLeft(BorderStyle.THIN);
		style3.setBorderRight(BorderStyle.THIN);
		style3.setBorderTop(BorderStyle.THIN);

		// Style 4 - Yellow cell, with border
		style4.setFillForegroundColor(colors[0].getIndex());
		style4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style4.setBorderBottom(BorderStyle.THIN);
		style4.setBorderLeft(BorderStyle.THIN);
		style4.setBorderRight(BorderStyle.THIN);
		style4.setBorderTop(BorderStyle.THIN);

		// Style 5 - Centered, wrapped, 11pt, black text red background, with border
		style5.setAlignment(HorizontalAlignment.CENTER);  
		style5.setWrapText(true);
		style5.setFont(font1);
		style5.setFillForegroundColor(colors[1].getIndex());
		style5.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style5.setBorderBottom(BorderStyle.THIN);
		style5.setBorderLeft(BorderStyle.THIN);
		style5.setBorderRight(BorderStyle.THIN);
		style5.setBorderTop(BorderStyle.THIN);

		// Style 6 - Centered, non- wrapped 14pt, white text against black background, with border
		style6.setAlignment(HorizontalAlignment.CENTER);  
		style6.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		style6.setFillPattern(FillPatternType.FINE_DOTS);
		style6.setWrapText(false);
		style6.setFont(font3);
		style6.setBorderBottom(BorderStyle.THIN);
		style6.setBorderLeft(BorderStyle.THIN);
		style6.setBorderRight(BorderStyle.THIN);
		style6.setBorderTop(BorderStyle.THIN);

		// Style 7 - Left aligned, not wrapped, 8pt, black text, no border
		style7.setAlignment(HorizontalAlignment.LEFT);  
		style7.setFont(font2);

		// Style 8 - Right aligned, non-wrapped, 11pt, black text, no border
		style8.setAlignment(HorizontalAlignment.RIGHT);  
		style8.setWrapText(false);
		style8.setFont(font1);

		// Style 9 - Left aligned, not wrapped, 11pt, black text, no border
		style9.setAlignment(HorizontalAlignment.LEFT);  
		style9.setFont(font1);

		// Style 10 - Left aligned, not wrapped, 11pt, black text, no border, x.x%
		style10.setAlignment(HorizontalAlignment.LEFT);  
		style10.setFont(font1);
		style10.setDataFormat(workbook.createDataFormat().getFormat("0.0"));

		// Header rows
		if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeMarineHighUsagePeriods())
		{
			if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeViolationsOnClosuresSheet())
			{
				closuresSheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 12));
			}
			else
			{
				closuresSheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 10));
			}	
		}
		else
		{
			if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeViolationsOnClosuresSheet())
			{
				closuresSheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 11));
			}
			else
			{
				closuresSheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 9));
			}
		}

		Row row;
		Cell cell;

		row = closuresSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style0);
		cell.setCellValue(bridgeAndSpan);
		rowCounter++;

		// Legal disclaimer
		if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeConfidentialityDisclaimer())
		{
			if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeMarineHighUsagePeriods())
			{
				if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeViolationsOnClosuresSheet())
				{
					closuresSheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 12));
				}
				else
				{
					closuresSheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 10));
				}
			}
			else
			{
				if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeViolationsOnClosuresSheet())
				{
					closuresSheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 11));
				}
				else
				{
					closuresSheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 9));
				}
			}

			row = closuresSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style6);
			cell.setCellValue(legalDisclaimer);
			rowCounter++;
		}

		// Data headers
		row = closuresSheet.createRow(rowCounter);

		cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("Closure #");

		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell.setCellValue("Start Date");

		cell = row.createCell(2);
		cell.setCellStyle(style1);
		cell.setCellValue("Start Day");

		cell = row.createCell(3);
		cell.setCellStyle(style1);
		cell.setCellValue("End Day");

		cell = row.createCell(4);
		cell.setCellStyle(style1);
		cell.setCellValue("Lowering Time");

		cell = row.createCell(5);
		cell.setCellStyle(style1);
		cell.setCellValue("Raising Time");

		cell = row.createCell(6);
		cell.setCellStyle(style2);
		cell.setCellValue("Type of Train");

		cell = row.createCell(7);
		cell.setCellStyle(style2);
		cell.setCellValue("Notes");

		cell = row.createCell(8);
		cell.setCellStyle(style2);
		cell.setCellValue("Tender");

		cell = row.createCell(9);
		cell.setCellStyle(style1);
		cell.setCellValue("Duration (hh:mm)");

		if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeMarineHighUsagePeriods())
		{
			cell = row.createCell(10);
			cell.setCellStyle(style1);
			cell.setCellValue("Duration during high-use period (hh:mm)");

			if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeViolationsOnClosuresSheet())
			{
				cell = row.createCell(11);
				cell.setCellStyle(style1);
				cell.setCellValue("Period Violation(red) / Delay(yellow)");

				cell = row.createCell(12);
				cell.setCellStyle(style1);
				cell.setCellValue("Duration Violation(red)");
			}
		}
		else
		{
			if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeViolationsOnClosuresSheet())
			{
				cell = row.createCell(10);
				cell.setCellStyle(style1);
				cell.setCellValue("Period Violation(red) / Delay(yellow)");

				cell = row.createCell(11);
				cell.setCellStyle(style1);
				cell.setCellValue("Duration Violation(red)");
			}
		}
		rowCounter++;

		// Closures
		for (int i = 0; i < closures.size(); i++)
		{
			row = closuresSheet.createRow(rowCounter);

			// Closure number
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(closures.get(i).getClosureNumber());

			// Start date
			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue(ConvertDateTime.convertSerialToDate(closures.get(i).getClosureDate()));

			// Start day
			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(closures.get(i).getClosureStartDay());

			// End day
			cell = row.createCell(3);
			cell.setCellStyle(style1);
			cell.setCellValue(closures.get(i).getClosureEndDay());

			// Lowering time
			cell = row.createCell(4);
			cell.setCellStyle(style1);
			cell.setCellValue(ConvertDateTime.convertSerialToHHMMString(closures.get(i).getClosureStartTime()));

			// Raising time
			cell = row.createCell(5);
			cell.setCellStyle(style1);
			cell.setCellValue(ConvertDateTime.convertSerialToHHMMString(closures.get(i).getClosureEndTime()));

			// Type of train
			cell = row.createCell(6);
			cell.setCellStyle(style2);
			cell.setCellValue(closures.get(i).getClosureTrainType());

			// Notes
			cell = row.createCell(7);
			cell.setCellStyle(style2);
			cell.setCellValue(closures.get(i).getClosureNotes());

			// Tender
			cell = row.createCell(8);
			cell.setCellStyle(style2);
			cell.setCellValue(closures.get(i).getTender());

			// Duration
			cell = row.createCell(9);
			cell.setCellStyle(style1);
			cell.setCellValue(ConvertDateTime.convertSerialToHHMMString(closures.get(i).getClosureDuration()));

			// Duration during marine high-use period
			if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeMarineHighUsagePeriods())
			{
				if (closures.get(i).getClosureDurationOccuringDuringMarineHighUsagePeriod() != null)
				{
					cell = row.createCell(10);
					cell.setCellStyle(style1);
					cell.setCellValue(ConvertDateTime.convertSerialToHHMMString(closures.get(i).getClosureDurationOccuringDuringMarineHighUsagePeriod()));
				}
				else
				{
					cell = row.createCell(10);
					cell.setCellStyle(style1);
				}

				if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeViolationsOnClosuresSheet())
				{			
					// Period "in-circuit" delay/violation
					cell = row.createCell(11);
					if (closures.get(i).getMarineAccessPeriodViolation() > 0)
					{
						// Show violation count and make cell background red
						cell.setCellStyle(style5);
						cell.setCellValue(closures.get(i).getMarineAccessPeriodViolation());
					} 
					else if (closures.get(i).getInCircuitException())
					{
						// Make cell background yellow
						cell.setCellStyle(style4);
					}
					else
					{
						cell.setCellStyle(style1);
					}

					// Duration violation
					cell = row.createCell(12);
					if (closures.get(i).getClosureDurationViolation())
					{
						// Make cell background red
						cell.setCellStyle(style5);
					}
					else
					{
						cell.setCellStyle(style1);
					}
				}
			}
			else
			{
				if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeViolationsOnClosuresSheet())
				{			
					// Period "in-circuit" delay/violation
					cell = row.createCell(10);
					if (closures.get(i).getMarineAccessPeriodViolation() > 0)
					{
						// Show violation count and make cell background red
						cell.setCellStyle(style5);
						cell.setCellValue(closures.get(i).getMarineAccessPeriodViolation());
					} 
					else if (closures.get(i).getInCircuitException())
					{
						// Make cell background yellow
						cell.setCellStyle(style4);
					}
					else
					{
						cell.setCellStyle(style1);
					}

					// Duration violation
					cell = row.createCell(11);
					if (closures.get(i).getClosureDurationViolation())
					{
						// Make cell background red
						cell.setCellStyle(style5);
					}
					else
					{
						cell.setCellStyle(style1);
					}
				}
			}
			rowCounter++;
		}

		// Footer rows
		// Show sum of closure durations (high use period and overall) 
		if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeMarineHighUsagePeriods())
		{
			rowCounter++;
			row = closuresSheet.createRow(rowCounter);
			cell = row.createCell(7);
			cell.setCellStyle(style8);
			cell.setCellValue("Sum of crossing durations from "+BIASUscgBridgeComplianceAnalysisConfigPageController.getMarineAccessPeriodStartHour()+" to "+BIASUscgBridgeComplianceAnalysisConfigPageController.getMarineAccessPeriodEndHour()+" (hhh:mm):");

			cell = row.createCell(8);
			cell.setCellStyle(style9);
			cell.setCellValue(ConvertDateTime.convertSerialToHHMMString(BridgeComplianceAnalysis.getTotalDurationOfClosureDuringHighUsePeriodsAsSerial()));
		}

		rowCounter++;
		row = closuresSheet.createRow(rowCounter);
		cell = row.createCell(7);
		cell.setCellStyle(style8);
		cell.setCellValue("Sum of crossing durations during all hours (hhh:mm):");

		cell = row.createCell(8);
		cell.setCellStyle(style9);
		cell.setCellValue(ConvertDateTime.convertSerialToHHMMString(BridgeComplianceAnalysis.getTotalDurationClosureAsSerial()));

		// Show average of closure durations (high use period and overall) 
		rowCounter++;
		if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeMarineHighUsagePeriods())
		{
			rowCounter++;
			row = closuresSheet.createRow(rowCounter);
			cell = row.createCell(7);
			cell.setCellStyle(style8);
			cell.setCellValue("Average crossing duration from "+BIASUscgBridgeComplianceAnalysisConfigPageController.getMarineAccessPeriodStartHour()+" to "+BIASUscgBridgeComplianceAnalysisConfigPageController.getMarineAccessPeriodEndHour()+" (hh:mm):");

			cell = row.createCell(8);
			cell.setCellStyle(style9);
			cell.setCellValue(ConvertDateTime.convertSerialToHHMMString(BridgeComplianceAnalysis.getTotalDurationOfClosureDuringHighUsePeriodsAsSerial() / BridgeComplianceAnalysis.getCountOfClosuresDuringHighUsePeriod()));
		}

		rowCounter++;
		row = closuresSheet.createRow(rowCounter);
		cell = row.createCell(7);
		cell.setCellStyle(style8);
		cell.setCellValue("Average crossing duration during all hours (hh:mm):");

		cell = row.createCell(8);
		cell.setCellStyle(style9);
		cell.setCellValue(ConvertDateTime.convertSerialToHHMMString(BridgeComplianceAnalysis.getTotalDurationClosureAsSerial() / closures.size()));

		// Show % mariner availability (high use period and overall) 
		rowCounter++;
		if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeMarineHighUsagePeriods())
		{
			rowCounter++;
			row = closuresSheet.createRow(rowCounter);
			cell = row.createCell(7);
			cell.setCellStyle(style8);
			cell.setCellValue("Mariner availability from "+BIASUscgBridgeComplianceAnalysisConfigPageController.getMarineAccessPeriodStartHour()+" to "+BIASUscgBridgeComplianceAnalysisConfigPageController.getMarineAccessPeriodEndHour()+" (%):");

			Double marineAccessPeriodSpanAsSerial = (BIASUscgBridgeComplianceAnalysisConfigPageController.getMarineAccessPeriodSpanAsDouble() / 24);
			Double durationOfClosuresDuringHighUsePeriods = BridgeComplianceAnalysis.getTotalDurationOfClosureDuringHighUsePeriodsAsSerial();
			Double marinerAvailabilityDuringPeak = (((marineAccessPeriodSpanAsSerial * 7) - durationOfClosuresDuringHighUsePeriods) / (marineAccessPeriodSpanAsSerial * 7)) * 100;  
			cell = row.createCell(8);
			cell.setCellStyle(style10);
			cell.setCellValue(marinerAvailabilityDuringPeak);
		}
		rowCounter++;
		row = closuresSheet.createRow(rowCounter);
		cell = row.createCell(7);
		cell.setCellStyle(style8);
		cell.setCellValue("Mariner availability during all hours (%):");

		Double durationOfClosuresAllHours = BridgeComplianceAnalysis.getTotalDurationClosureAsSerial();
		Double marinerAvailabilityDuringAllHours = ((7 - durationOfClosuresAllHours) / 7) * 100;  
		cell = row.createCell(8);
		cell.setCellStyle(style10);
		cell.setCellValue(marinerAvailabilityDuringAllHours);

		// Timestamp and footnote
		LocalDate creationDate = ConvertDateTime.getDateStamp();
		LocalTime creationTime = ConvertDateTime.getTimeStamp();

		rowCounter++;
		rowCounter++;
		row = closuresSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style7);
		cell.setCellValue("First and last closures may extend over midnight and will show as such in columns B, C, D, E and F.  "
				+ "However, time before/after the analysis period is not included in the durations and summary statistics.  "
				+ "A 7-day (168 hour week) is assumed in summary calculations.");

		rowCounter++;
		row = closuresSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style7);
		cell.setCellValue("Created on "+creationDate+" at "+creationTime);

		// Resize all columns to fit the content size
		if (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeMarineHighUsagePeriods())
		{
			for (int i = 0; i <= 12 ; i++) 
			{
				if (i == 0)
				{
					closuresSheet.setColumnWidth(i, 2500);
				}
				else if (i == 1)
				{
					closuresSheet.setColumnWidth(i, 2900);
				}
				else if ((i == 2) || (i ==3))
				{
					closuresSheet.setColumnWidth(i, 3000);
				}
				else if (i == 6)
				{
					closuresSheet.setColumnWidth(i, 12000);
				}
				else if (i == 7)
				{
					closuresSheet.setColumnWidth(i, 19000);
				}
				else if (i == 8)
				{
					closuresSheet.setColumnWidth(i, 5000);
				}
				else if ((i == 9) || (i == 10))
				{
					closuresSheet.setColumnWidth(i, 3800);
				}
				else if (((i == 11) || (i == 12)) && (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeViolationsOnClosuresSheet()))
				{
					closuresSheet.setColumnWidth(i, 4500);
				}
			}
		}
		else
		{
			for (int i = 0; i <= 11 ; i++) 
			{
				if (i == 0)
				{
					closuresSheet.setColumnWidth(i, 2500);
				}
				else if (i == 1)
				{
					closuresSheet.setColumnWidth(i, 2900);
				}
				else if ((i == 2) || (i ==3))
				{
					closuresSheet.setColumnWidth(i, 3000);
				}
				else if (i == 6)
				{
					closuresSheet.setColumnWidth(i, 12000);
				}
				else if (i == 7)
				{
					closuresSheet.setColumnWidth(i, 19000);
				}
				else if (i == 8)
				{
					closuresSheet.setColumnWidth(i, 5000);
				}
				else if (i == 9)
				{
					closuresSheet.setColumnWidth(i, 3800);
				}
				else if (((i == 10) || (i == 11)) && (BIASUscgBridgeComplianceAnalysisConfigPageController.getIncludeViolationsOnClosuresSheet()))
				{
					closuresSheet.setColumnWidth(i, 4500);
				}
			}
		}
	}

	public String getResultsMessageWrite1()
	{
		return resultsMessage;
	}
}