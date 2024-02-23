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

import com.bl.bias.analyze.AnalyzeJuaComplianceFiles;
import com.bl.bias.app.BIASJuaComplianceConfigController;
import com.bl.bias.app.BIASRecoveryRateAnalysisConfigController;
import com.bl.bias.tools.ConvertDateTime;

public class WriteJuaComplianceFiles4 extends WriteJuaComplianceFiles3
{
	protected Boolean error = false;

	Integer rowCounter = 0;

	private static Boolean recoveryRatesCompliant;

	public WriteJuaComplianceFiles4(String textArea, String fullyQualifiedPath) 
	{
		super(textArea, fullyQualifiedPath);

		recoveryRatesCompliant = true;

		if ((BIASJuaComplianceConfigController.getCheckTrainPriority()) || (BIASJuaComplianceConfigController.getCheckLastAcceptedOptionsFile()))
		{
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

			XSSFSheet juaCompliancePriorities;
			juaCompliancePriorities = workbook.createSheet("Recovery Rate");
			juaCompliancePriorities.setDisplayGridlines(false);
			resultsMessage += "\nWriting recovery rate compliance assessment";

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
			font3.setBold(true);

			// Font 4 - 11pt text, green font
			XSSFFont font4 = workbook.createFont();
			font4.setFontHeightInPoints((short) 11);
			font4.setColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
			font4.setFontName("Calibri");
			font4.setBold(true);

			// Font 5 - 11pt text, black font
			XSSFFont font5 = workbook.createFont();
			font5.setFontHeightInPoints((short) 11);
			font5.setFontName("Calibri");
			font5.setBold(true);

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

			// Style 7 - Centered, wrapped, 11pt, black text, thin bottom border
			style7.setAlignment(HorizontalAlignment.CENTER);  
			style7.setWrapText(true);
			style7.setFont(font1);
			style7.setBorderBottom(BorderStyle.THIN);

			// Style 8 - Centered, wrapped, 11pt, red text
			style8.setAlignment(HorizontalAlignment.LEFT);  
			style8.setWrapText(true);
			style8.setFont(font3);

			// Style 9 - Centered, wrapped, 11pt, green text
			style9.setAlignment(HorizontalAlignment.LEFT);  
			style9.setWrapText(true);
			style9.setFont(font4);

			// Style 10 - Left aligned, wrapped, 8pt, black text
			style10.setAlignment(HorizontalAlignment.LEFT);  
			style10.setWrapText(true);
			style10.setFont(font2);

			// Style 11 - Centered, wrapped, 11pt, black text, bold
			style11.setAlignment(HorizontalAlignment.CENTER);  
			style11.setWrapText(true);
			style11.setFont(font5);

			// Style 12 - Left aligned, wrapped, 11pt, black text
			style12.setAlignment(HorizontalAlignment.LEFT);  
			style12.setWrapText(true);
			style12.setFont(font1);

			Row row;
			Cell cell;

			// Recovery Rate Compliance
			if (BIASJuaComplianceConfigController.getCheckRecoveryRates())
			{
				// Header rows
				// Case name
				juaCompliancePriorities.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

				row = juaCompliancePriorities.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style0);
				cell.setCellValue("JUA Compliance:  Recovery Rate Compliance for "+thisCase);

				//Header row
				rowCounter++;
				row = juaCompliancePriorities.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style7);
				cell.setCellValue("Set");

				cell = row.createCell(1);
				cell.setCellStyle(style7);
				cell.setCellValue("Non-Conforming Train Symbol");

				cell = row.createCell(2);
				cell.setCellStyle(style7);
				cell.setCellValue("Measuring Point A");

				cell = row.createCell(3);
				cell.setCellStyle(style7);
				cell.setCellValue("Measuring Point B");

				cell = row.createCell(4);
				cell.setCellStyle(style7);
				cell.setCellValue("Actual Rate (%)");

				cell = row.createCell(5);
				cell.setCellStyle(style7);
				cell.setCellValue("Minimum Permitted Rate (%)");

				// Set A
				for (int i = 0; i < AnalyzeJuaComplianceFiles.getRecoveryRateSetAResults().size(); i++)
				{
					for (int j = 0; j < AnalyzeJuaComplianceFiles.getRecoveryRateSetAResults().get(i).getRecoveryRateAssessments().size(); j++)
					{
						// Recovery Rate
						// Scheduled Time
						Integer adjustedScheduledTimeInSeconds = AnalyzeJuaComplianceFiles.getRecoveryRateSetAResults().get(i).getRecoveryRateAssessments().get(j).getDifferenceInScheduledTimeInSeconds() - AnalyzeJuaComplianceFiles.getRecoveryRateSetAResults().get(i).getRecoveryRateAssessments().get(j).getDwellEventCumulativeTimeInSeconds();
						Integer adjustedMinimumRunTimeInSeconds = AnalyzeJuaComplianceFiles.getRecoveryRateSetAResults().get(i).getRecoveryRateAssessments().get(j).getDifferenceInSimulatedTimeInSeconds() -AnalyzeJuaComplianceFiles.getRecoveryRateSetAResults().get(i).getRecoveryRateAssessments().get(j).getDwellEventCumulativeTimeInSeconds() - AnalyzeJuaComplianceFiles.getRecoveryRateSetAResults().get(i).getRecoveryRateAssessments().get(j).getWaitOnScheduleCumulativeTimeInSeconds();
						Integer adjustedRecoveryTimeAvailableInSeconds = adjustedScheduledTimeInSeconds - adjustedMinimumRunTimeInSeconds - AnalyzeJuaComplianceFiles.getRecoveryRateSetAResults().get(i).getRecoveryRateAssessments().get(j).getDwellEventOffsetCumulativeTimeInSeconds();
						Double recoveryRate1 = 0.0;
						recoveryRate1 = (double) adjustedRecoveryTimeAvailableInSeconds / (double) adjustedScheduledTimeInSeconds;
						Double recoveryRate2 = Math.round(recoveryRate1 * 1000) / 10.0;
						String recoveryRate3 = String.valueOf(recoveryRate2).concat("%");

						if (recoveryRate2 < Double.valueOf(BIASRecoveryRateAnalysisConfigController.getSetALowRecoveryRate()))
						{
							rowCounter++;
							row = juaCompliancePriorities.createRow(rowCounter);
							cell = row.createCell(0);
							cell.setCellStyle(style1);
							cell.setCellValue("A");

							cell = row.createCell(1);
							cell.setCellStyle(style1);
							cell.setCellValue(AnalyzeJuaComplianceFiles.getRecoveryRateSetAResults().get(i).getTrainSymbol());

							cell = row.createCell(2);
							cell.setCellStyle(style1);
							cell.setCellValue(AnalyzeJuaComplianceFiles.getRecoveryRateSetAResults().get(i).getRecoveryRateAssessments().get(j).getANode());

							cell = row.createCell(3);
							cell.setCellStyle(style1);
							cell.setCellValue(AnalyzeJuaComplianceFiles.getRecoveryRateSetAResults().get(i).getRecoveryRateAssessments().get(j).getBNode());							

							cell = row.createCell(4);
							cell.setCellStyle(style1);
							cell.setCellValue(recoveryRate3);

							cell = row.createCell(5);
							cell.setCellStyle(style1);
							cell.setCellValue(BIASRecoveryRateAnalysisConfigController.getSetALowRecoveryRate().toString().concat("%"));

							recoveryRatesCompliant = false;
						}
					}
				}

				// Set B
				for (int i = 0; i < AnalyzeJuaComplianceFiles.getRecoveryRateSetBResults().size(); i++)
				{
					for (int j = 0; j < AnalyzeJuaComplianceFiles.getRecoveryRateSetBResults().get(i).getRecoveryRateAssessments().size(); j++)
					{
						// Recovery Rate
						// Scheduled Time
						Integer adjustedScheduledTimeInSeconds = AnalyzeJuaComplianceFiles.getRecoveryRateSetBResults().get(i).getRecoveryRateAssessments().get(j).getDifferenceInScheduledTimeInSeconds() - AnalyzeJuaComplianceFiles.getRecoveryRateSetBResults().get(i).getRecoveryRateAssessments().get(j).getDwellEventCumulativeTimeInSeconds();
						Integer adjustedMinimumRunTimeInSeconds = AnalyzeJuaComplianceFiles.getRecoveryRateSetBResults().get(i).getRecoveryRateAssessments().get(j).getDifferenceInSimulatedTimeInSeconds() -AnalyzeJuaComplianceFiles.getRecoveryRateSetBResults().get(i).getRecoveryRateAssessments().get(j).getDwellEventCumulativeTimeInSeconds() - AnalyzeJuaComplianceFiles.getRecoveryRateSetBResults().get(i).getRecoveryRateAssessments().get(j).getWaitOnScheduleCumulativeTimeInSeconds();
						Integer adjustedRecoveryTimeAvailableInSeconds = adjustedScheduledTimeInSeconds - adjustedMinimumRunTimeInSeconds - AnalyzeJuaComplianceFiles.getRecoveryRateSetBResults().get(i).getRecoveryRateAssessments().get(j).getDwellEventOffsetCumulativeTimeInSeconds();
						Double recoveryRate1 = 0.0;
						recoveryRate1 = (double) adjustedRecoveryTimeAvailableInSeconds / (double) adjustedScheduledTimeInSeconds;
						Double recoveryRate2 = Math.round(recoveryRate1 * 1000) / 10.0;
						String recoveryRate3 = String.valueOf(recoveryRate2).concat("%");

						if (recoveryRate2 < Double.valueOf(BIASRecoveryRateAnalysisConfigController.getSetBLowRecoveryRate()))
						{
							rowCounter++;
							row = juaCompliancePriorities.createRow(rowCounter);
							cell = row.createCell(0);
							cell.setCellStyle(style1);
							cell.setCellValue("B");

							cell = row.createCell(1);
							cell.setCellStyle(style1);
							cell.setCellValue(AnalyzeJuaComplianceFiles.getRecoveryRateSetBResults().get(i).getTrainSymbol());

							cell = row.createCell(2);
							cell.setCellStyle(style1);
							cell.setCellValue(AnalyzeJuaComplianceFiles.getRecoveryRateSetBResults().get(i).getRecoveryRateAssessments().get(j).getANode());

							cell = row.createCell(3);
							cell.setCellStyle(style1);
							cell.setCellValue(AnalyzeJuaComplianceFiles.getRecoveryRateSetBResults().get(i).getRecoveryRateAssessments().get(j).getBNode());							

							cell = row.createCell(4);
							cell.setCellStyle(style1);
							cell.setCellValue(recoveryRate3);

							cell = row.createCell(5);
							cell.setCellStyle(style1);
							cell.setCellValue(BIASRecoveryRateAnalysisConfigController.getSetBLowRecoveryRate().toString().concat("%"));

							recoveryRatesCompliant = false;
						}
					}
				}

				// Set C
				for (int i = 0; i < AnalyzeJuaComplianceFiles.getRecoveryRateSetCResults().size(); i++)
				{
					for (int j = 0; j < AnalyzeJuaComplianceFiles.getRecoveryRateSetCResults().get(i).getRecoveryRateAssessments().size(); j++)
					{
						// Recovery Rate
						// Scheduled Time
						Integer adjustedScheduledTimeInSeconds = AnalyzeJuaComplianceFiles.getRecoveryRateSetCResults().get(i).getRecoveryRateAssessments().get(j).getDifferenceInScheduledTimeInSeconds() - AnalyzeJuaComplianceFiles.getRecoveryRateSetCResults().get(i).getRecoveryRateAssessments().get(j).getDwellEventCumulativeTimeInSeconds();
						Integer adjustedMinimumRunTimeInSeconds = AnalyzeJuaComplianceFiles.getRecoveryRateSetCResults().get(i).getRecoveryRateAssessments().get(j).getDifferenceInSimulatedTimeInSeconds() - AnalyzeJuaComplianceFiles.getRecoveryRateSetCResults().get(i).getRecoveryRateAssessments().get(j).getDwellEventCumulativeTimeInSeconds() - AnalyzeJuaComplianceFiles.getRecoveryRateSetCResults().get(i).getRecoveryRateAssessments().get(j).getWaitOnScheduleCumulativeTimeInSeconds();
						Integer adjustedRecoveryTimeAvailableInSeconds = adjustedScheduledTimeInSeconds - adjustedMinimumRunTimeInSeconds - AnalyzeJuaComplianceFiles.getRecoveryRateSetCResults().get(i).getRecoveryRateAssessments().get(j).getDwellEventOffsetCumulativeTimeInSeconds();
						Double recoveryRate1 = 0.0;
						recoveryRate1 = (double) adjustedRecoveryTimeAvailableInSeconds / (double) adjustedScheduledTimeInSeconds;
						Double recoveryRate2 = Math.round(recoveryRate1 * 1000) / 10.0;
						String 	recoveryRate3 = String.valueOf(recoveryRate2).concat("%");

						if (recoveryRate2 < Double.valueOf(BIASRecoveryRateAnalysisConfigController.getSetCLowRecoveryRate()))
						{
							rowCounter++;
							row = juaCompliancePriorities.createRow(rowCounter);
							cell = row.createCell(0);
							cell.setCellStyle(style1);
							cell.setCellValue("C");

							cell = row.createCell(1);
							cell.setCellStyle(style1);
							cell.setCellValue(AnalyzeJuaComplianceFiles.getRecoveryRateSetCResults().get(i).getTrainSymbol());

							cell = row.createCell(2);
							cell.setCellStyle(style1);
							cell.setCellValue(AnalyzeJuaComplianceFiles.getRecoveryRateSetCResults().get(i).getRecoveryRateAssessments().get(j).getANode());

							cell = row.createCell(3);
							cell.setCellStyle(style1);
							cell.setCellValue(AnalyzeJuaComplianceFiles.getRecoveryRateSetCResults().get(i).getRecoveryRateAssessments().get(j).getBNode());							

							cell = row.createCell(4);
							cell.setCellStyle(style1);
							cell.setCellValue(recoveryRate3);

							cell = row.createCell(5);
							cell.setCellStyle(style1);
							cell.setCellValue(BIASRecoveryRateAnalysisConfigController.getSetCLowRecoveryRate().toString().concat("%"));

							recoveryRatesCompliant = false;
						}
					}
				}

				// Set D
				for (int i = 0; i < AnalyzeJuaComplianceFiles.getRecoveryRateSetDResults().size(); i++)
				{
					for (int j = 0; j < AnalyzeJuaComplianceFiles.getRecoveryRateSetDResults().get(i).getRecoveryRateAssessments().size(); j++)
					{
						// Recovery Rate
						// Scheduled Time
						Integer adjustedScheduledTimeInSeconds = AnalyzeJuaComplianceFiles.getRecoveryRateSetDResults().get(i).getRecoveryRateAssessments().get(j).getDifferenceInScheduledTimeInSeconds() - AnalyzeJuaComplianceFiles.getRecoveryRateSetDResults().get(i).getRecoveryRateAssessments().get(j).getDwellEventCumulativeTimeInSeconds();
						Integer adjustedMinimumRunTimeInSeconds = AnalyzeJuaComplianceFiles.getRecoveryRateSetDResults().get(i).getRecoveryRateAssessments().get(j).getDifferenceInSimulatedTimeInSeconds() -AnalyzeJuaComplianceFiles.getRecoveryRateSetDResults().get(i).getRecoveryRateAssessments().get(j).getDwellEventCumulativeTimeInSeconds() - AnalyzeJuaComplianceFiles.getRecoveryRateSetDResults().get(i).getRecoveryRateAssessments().get(j).getWaitOnScheduleCumulativeTimeInSeconds();
						Integer adjustedRecoveryTimeAvailableInSeconds = adjustedScheduledTimeInSeconds - adjustedMinimumRunTimeInSeconds - AnalyzeJuaComplianceFiles.getRecoveryRateSetDResults().get(i).getRecoveryRateAssessments().get(j).getDwellEventOffsetCumulativeTimeInSeconds();
						Double recoveryRate1 = 0.0;
						recoveryRate1 = (double) adjustedRecoveryTimeAvailableInSeconds / (double) adjustedScheduledTimeInSeconds;
						Double recoveryRate2 = Math.round(recoveryRate1 * 1000) / 10.0;
						String recoveryRate3 = String.valueOf(recoveryRate2).concat("%");

						if (recoveryRate2 < Double.valueOf(BIASRecoveryRateAnalysisConfigController.getSetDLowRecoveryRate()))
						{
							rowCounter++;
							row = juaCompliancePriorities.createRow(rowCounter);
							cell = row.createCell(0);
							cell.setCellStyle(style1);
							cell.setCellValue("D");

							cell = row.createCell(1);
							cell.setCellStyle(style1);
							cell.setCellValue(AnalyzeJuaComplianceFiles.getRecoveryRateSetDResults().get(i).getTrainSymbol());

							cell = row.createCell(2);
							cell.setCellStyle(style1);
							cell.setCellValue(AnalyzeJuaComplianceFiles.getRecoveryRateSetDResults().get(i).getRecoveryRateAssessments().get(j).getANode());

							cell = row.createCell(3);
							cell.setCellStyle(style1);
							cell.setCellValue(AnalyzeJuaComplianceFiles.getRecoveryRateSetDResults().get(i).getRecoveryRateAssessments().get(j).getBNode());							

							cell = row.createCell(4);
							cell.setCellStyle(style1);
							cell.setCellValue(recoveryRate3);

							cell = row.createCell(5);
							cell.setCellStyle(style1);
							cell.setCellValue(BIASRecoveryRateAnalysisConfigController.getSetDLowRecoveryRate().toString().concat("%"));

							recoveryRatesCompliant = false;
						}
					}
				}

				if (recoveryRatesCompliant)
				{
					rowCounter++;
					row = juaCompliancePriorities.createRow(rowCounter);
					cell = row.createCell(0);
					cell.setCellStyle(style1);
					cell.setCellValue("No non-compliant trains found!");
				}

				// Compliance determination
				rowCounter++;
				rowCounter++;
				juaCompliancePriorities.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 3));
				row = juaCompliancePriorities.createRow(rowCounter);
				cell = row.createCell(0);
				if (recoveryRatesCompliant)
				{
					cell.setCellStyle(style9);
					cell.setCellValue("The recovery rates are COMPLIANT");
				}
				else
				{
					cell.setCellStyle(style8);
					cell.setCellValue("The recovery rates are NOT COMPLIANT");
				}
				rowCounter++;
				rowCounter++;
			}

			// Timestamp and footnote
			LocalDate creationDate = ConvertDateTime.getDateStamp();
			LocalTime creationTime = ConvertDateTime.getTimeStamp();

			row = juaCompliancePriorities.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("Created on "+creationDate+" at "+creationTime);

			// Resize all columns to fit the content size
			for (int i = 0; i <= 5; i++) 
			{
				if (i == 0) 
				{
					juaCompliancePriorities.setColumnWidth(i, 2500);
				}
				else if (i == 1)
				{
					juaCompliancePriorities.setColumnWidth(i, 10000);
				}
				else if (i == 2)
				{
					juaCompliancePriorities.setColumnWidth(i, 8750);
				}
				else if (i == 3)
				{
					juaCompliancePriorities.setColumnWidth(i, 8750);
				}
				else if (i == 4)
				{
					juaCompliancePriorities.setColumnWidth(i, 3500);
				}
				else if (i == 5)
				{
					juaCompliancePriorities.setColumnWidth(i, 3500);
				}
			}
		}
	}

	public static String removeLastChar(String s) 
	{
		return (s == null || s.length() == 0)
				? null 
						: (s.substring(0, s.length() - 1));
	}

	public static String convertArrayOfStringsToString(String[] string)
	{
		String stringToReturn = "";
		for (int i = 0; i < string.length; i++) 
		{
			stringToReturn += string[i];
			stringToReturn += ", ";
		}

		return removeLastChar(removeLastChar(stringToReturn));
	}

	public static String convertArrayListOfStringsToString(ArrayList<String> arrayList)
	{
		String stringToReturn = "";
		for (int i = 0; i < arrayList.size(); i++) 
		{
			stringToReturn += arrayList.get(i);
			stringToReturn += ", ";
		}

		return removeLastChar(removeLastChar(stringToReturn));
	}

	public String getResultsMessageWrite4()
	{
		return resultsMessage;
	}
}