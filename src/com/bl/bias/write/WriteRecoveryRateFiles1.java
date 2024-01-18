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

import com.bl.bias.analyze.RecoveryRateAnalysis;
import com.bl.bias.app.BIASRecoveryRateAnalysisConfigController;
import com.bl.bias.objects.TrainAssessment;
import com.bl.bias.tools.ConvertDateTime;

public class WriteRecoveryRateFiles1 // Set A
{
	private LocalTime startWriteFileTime = ConvertDateTime.getTimeStamp();
	protected String resultsMessage = "\nStarted writing output file at "+startWriteFileTime;

	ArrayList<TrainAssessment> assessmentsSetA = new ArrayList<TrainAssessment>();

	XSSFWorkbook workbook = new XSSFWorkbook();

	Integer rowCounter = 0;

	Boolean validTrainFound = false;

	public WriteRecoveryRateFiles1(String textArea)
	{
		if (BIASRecoveryRateAnalysisConfigController.getAnalyzeSetA())
		{
			// Get train assessments
			assessmentsSetA.addAll(RecoveryRateAnalysis.getAnalyzedTrainsSetA());

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

			// Write Set A
			XSSFSheet recoveryRatesSheetA;
			if (BIASRecoveryRateAnalysisConfigController.getSetALabel().equals(""))
			{
				recoveryRatesSheetA = workbook.createSheet("Recovery Rates Set A");
				resultsMessage += "\nWriting recovery rate results for trains in Set A";
			}
			else
			{
				recoveryRatesSheetA = workbook.createSheet("Recovery Rates "+ BIASRecoveryRateAnalysisConfigController.getSetALabel());
				resultsMessage += "\nWriting recovery rate results for trains in Set A ("+BIASRecoveryRateAnalysisConfigController.getSetALabel()+")";
			}
			recoveryRatesSheetA.setDisplayGridlines(false);

			// Fonts
			// Font 0 - 14pt White text
			XSSFFont font0 = workbook.createFont();
			font0.setFontHeightInPoints((short) 14);
			font0.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
			font0.setFontName("Calibri");

			// Font 1 - 11pt text, black font
			XSSFFont font1 = workbook.createFont();
			font1.setFontHeightInPoints((short) 11);
			font1.setFontName("Calibri");

			// Font 2 - 8pt text, black font
			XSSFFont font2 = workbook.createFont();
			font2.setFontHeightInPoints((short) 8);
			font2.setFontName("Calibri");

			// Font 3 - 11pt text, red font
			XSSFFont font3 = workbook.createFont();
			font3.setFontHeightInPoints((short) 11);
			font3.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
			font3.setFontName("Calibri");

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

			// Style 5 - Left aligned, non-wrapped, 11pt, black text
			style5.setAlignment(HorizontalAlignment.LEFT);  
			style5.setWrapText(false);
			style5.setFont(font1);

			// Style 6 - Left aligned, non-wrapped, 11pt, black text, thin bottom border
			style6.setAlignment(HorizontalAlignment.LEFT);  
			style6.setWrapText(false);
			style6.setFont(font1);
			style6.setBorderBottom(BorderStyle.THIN);

			// Style 7 - Centered, wrapped, 11pt, black text
			style7.setAlignment(HorizontalAlignment.CENTER);  
			style7.setWrapText(true);
			style7.setFont(font1);
			style7.setBorderBottom(BorderStyle.THIN);

			// Style 8 - Centered, wrapped, 11pt, red text
			style8.setAlignment(HorizontalAlignment.CENTER);  
			style8.setWrapText(true);
			style8.setFont(font3);

			// Header rows
			// Case name
			recoveryRatesSheetA.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

			Row row;
			Cell cell;

			row = recoveryRatesSheetA.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style0);

			if (BIASRecoveryRateAnalysisConfigController.getSetALabel().equals(""))
			{
				cell.setCellValue("Recovery Rate Assessments by Train for Node Set A");
			}
			else
			{
				cell.setCellValue("Recovery Rate Assessments by Train for Node Set A ("+BIASRecoveryRateAnalysisConfigController.getSetALabel()+")");
			}

			// Data headers
			rowCounter++;
			rowCounter++;
			row = recoveryRatesSheetA.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style6);
			cell.setCellValue("Train Symbol (Group)");

			cell = row.createCell(1);
			cell.setCellStyle(style7);
			cell.setCellValue("Node A");

			cell = row.createCell(2);
			cell.setCellStyle(style7);
			cell.setCellValue("Node B");

			cell = row.createCell(3);
			cell.setCellStyle(style7);
			cell.setCellValue("Distance Covered (miles)");

			cell = row.createCell(4);
			cell.setCellStyle(style7);
			cell.setCellValue("Time Alloted By Schedule Excluding Dwell/Work (HH:MM:SS)");

			cell = row.createCell(5);
			cell.setCellStyle(style7);
			cell.setCellValue("Minimum Run Time Excluding Dwell/Work (HH:MM:SS)");

			if (BIASRecoveryRateAnalysisConfigController.getSetAScheduleImprecisionOffsetInSeconds() > 0)
			{
				cell = row.createCell(6);
				cell.setCellStyle(style7);
				cell.setCellValue("Recovery Time Available (HH:MM:SS)+");
			}
			else
			{
				cell = row.createCell(6);
				cell.setCellStyle(style7);
				cell.setCellValue("Recovery Time Available (HH:MM:SS)");
			}

			cell = row.createCell(7);
			cell.setCellStyle(style7);
			cell.setCellValue("Recovery Rate (%)");

			// Trains
			for (int i = 0; i < assessmentsSetA.size(); i++)
			{
				if (assessmentsSetA.get(i).getTrainHasRecoveryRatesCalculated())
				{
					// Valid train found
					validTrainFound = true;

					for (int j = 0; j < assessmentsSetA.get(i).getRecoveryRateAssessments().size(); j++)
					{
						rowCounter++;
						row = recoveryRatesSheetA.createRow(rowCounter);

						// Train Symbol
						if (j < 1)
						{
							cell = row.createCell(0);
							cell.setCellStyle(style5);
							cell.setCellValue(assessmentsSetA.get(i).getTrainSymbol()+" ("+assessmentsSetA.get(i).getTrainGroupAbbreviation()+")");
						}

						// Node A
						cell = row.createCell(1);
						cell.setCellStyle(style1);
						cell.setCellValue(assessmentsSetA.get(i).getRecoveryRateAssessments().get(j).getANode());

						// Node B
						cell = row.createCell(2);
						cell.setCellStyle(style1);
						cell.setCellValue(assessmentsSetA.get(i).getRecoveryRateAssessments().get(j).getBNode());

						// Distance
						cell = row.createCell(3);
						cell.setCellStyle(style1);
						cell.setCellValue(assessmentsSetA.get(i).getRecoveryRateAssessments().get(j).getDistanceCovered());

						// Scheduled Time
						Integer adjustedScheduledTimeInSeconds = assessmentsSetA.get(i).getRecoveryRateAssessments().get(j).getDifferenceInScheduledTimeInSeconds() - assessmentsSetA.get(i).getRecoveryRateAssessments().get(j).getDwellEventCumulativeTimeInSeconds();
						cell = row.createCell(4);
						cell.setCellStyle(style1);
						cell.setCellValue(ConvertDateTime.convertSecondsToDDHHMMSSString(adjustedScheduledTimeInSeconds));

						// Minimum Time
						Integer adjustedMinimumRunTimeInSeconds = assessmentsSetA.get(i).getRecoveryRateAssessments().get(j).getDifferenceInSimulatedTimeInSeconds() - assessmentsSetA.get(i).getRecoveryRateAssessments().get(j).getDwellEventCumulativeTimeInSeconds() - assessmentsSetA.get(i).getRecoveryRateAssessments().get(j).getWaitOnScheduleCumulativeTimeInSeconds();
						cell = row.createCell(5);
						cell.setCellStyle(style1);
						cell.setCellValue(ConvertDateTime.convertSecondsToDDHHMMSSString(adjustedMinimumRunTimeInSeconds));

						// Recovery Time Available
						Integer adjustedRecoveryTimeAvailableInSeconds = adjustedScheduledTimeInSeconds - adjustedMinimumRunTimeInSeconds - assessmentsSetA.get(i).getRecoveryRateAssessments().get(j).getDwellEventOffsetCumulativeTimeInSeconds();
						cell = row.createCell(6);
						cell.setCellStyle(style1);
						cell.setCellValue(ConvertDateTime.convertSecondsToDDHHMMSSString(adjustedRecoveryTimeAvailableInSeconds));

						// Recovery Rate
						Double recoveryRate1 = 0.0;
						recoveryRate1 = (double) adjustedRecoveryTimeAvailableInSeconds / (double) adjustedScheduledTimeInSeconds;
						Double recoveryRate2 = Math.round(recoveryRate1 * 1000) / 10.0;
						String recoveryRate3 = ""; 
						if (assessmentsSetA.get(i).getRecoveryRateAssessments().get(j).getDwellEventBetweenNodes())
						{
							recoveryRate3 = String.valueOf(recoveryRate2).concat("% *");
						}
						else
						{
							recoveryRate3 = String.valueOf(recoveryRate2).concat("%");
						}

						cell = row.createCell(7);

						if (recoveryRate2 < Double.valueOf(BIASRecoveryRateAnalysisConfigController.getSetALowRecoveryRate().replace("%", "")))
							cell.setCellStyle(style8);
						else
							cell.setCellStyle(style1);

						cell.setCellValue(recoveryRate3);
					}
				}
			}

			if (!validTrainFound) 
			{
				// Train Symbol
				rowCounter++;
				row = recoveryRatesSheetA.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style5);
				cell.setCellValue("No trains with recovery rate assessments found!");
			}

			// Timestamp and footnote
			LocalDate creationDate = ConvertDateTime.getDateStamp();
			LocalTime creationTime = ConvertDateTime.getTimeStamp();

			rowCounter++;
			rowCounter++;
			row = recoveryRatesSheetA.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("* Denotes at least one work/dwell event occuring within the node pair.  Recovery rates DO NOT INCLUDE work/dwell events.");

			if (BIASRecoveryRateAnalysisConfigController.getSetAScheduleImprecisionOffsetInSeconds() > 0)
			{
				rowCounter++;
				row = recoveryRatesSheetA.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("+ Recovery time available is reduced by "+BIASRecoveryRateAnalysisConfigController.getSetAScheduleImprecisionOffsetInSeconds()+" seconds per work event/stop due to schedule imprecision.");
			}

			rowCounter++;
			row = recoveryRatesSheetA.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("Created on "+creationDate+" at "+creationTime);

			// Resize all columns to fit the content size
			for (int i = 0; i <= 7; i++) 
			{
				if (i == 0) 
				{
					recoveryRatesSheetA.setColumnWidth(i, 7000);
				}
				else if (i == 1)
				{
					recoveryRatesSheetA.setColumnWidth(i, 5000);
				}
				else if ((i == 2) || (i == 3) || (i == 4) || (i == 5) || (i == 6))
				{
					recoveryRatesSheetA.setColumnWidth(i, 5100);
				}
				else if (i == 7)
				{
					recoveryRatesSheetA.setColumnWidth(i, 3000);
				}
			}
		}
	}

	public String getResultsMessageWrite1()
	{
		return resultsMessage;
	}
}