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
import com.bl.bias.tools.ConvertDateTime;

public class WriteJuaComplianceFiles3 extends WriteJuaComplianceFiles2
{
	protected Boolean error = false;

	Integer rowCounter = 0;
	
	private static String lastAcceptedOptionFileName1 = BIASJuaComplianceConfigController.getLastAcceptedOptionFileAsString().replace(".OPTION","");
	private static String lastAcceptedOptionFileName2 = lastAcceptedOptionFileName1.substring(lastAcceptedOptionFileName1.lastIndexOf("\\") + 1);

	private static Boolean prioritiesInOptionFilesOfBothCasesEqual;
	private static Boolean prioritiesAndRanksCompliantWithJua;

	public WriteJuaComplianceFiles3(String textArea, String fullyQualifiedPath) 
	{
		super(textArea, fullyQualifiedPath);

		prioritiesInOptionFilesOfBothCasesEqual = AnalyzeJuaComplianceFiles.getErrorsWithTrainPriorityLastAcceptedCase();
		prioritiesAndRanksCompliantWithJua = true;
		
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
			juaCompliancePriorities = workbook.createSheet("Priority Compliance");
			juaCompliancePriorities.setDisplayGridlines(false);
			resultsMessage += "\nWriting priority/rank compliance assessment";

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

			// Compliance selected
			if (BIASJuaComplianceConfigController.getCheckTrainPriority())
			{
				// Header rows
				// Case name
				juaCompliancePriorities.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
				
				row = juaCompliancePriorities.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style0);
				cell.setCellValue("JUA Compliance:  Train Priority and Rank Compliance for "+thisCase);

				// Tier 1
				rowCounter++;
				rowCounter++;
				row = juaCompliancePriorities.createRow(rowCounter);

				juaCompliancePriorities.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
				cell = row.createCell(0);
				cell.setCellStyle(style7);
				cell.setCellValue("Tier 1 (Highest Priority)");

				cell = row.createCell(1);
				cell.setCellStyle(style7);
				cell.setCellValue("");

				rowCounter++;
				row = juaCompliancePriorities.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style5);
				cell.setCellValue("Applies to Train Types:");

				String tier1TrainTypes = "";
				if (BIASJuaComplianceConfigController.getTier2TrainTypesAsArray() != null)
				{
					for (int i = 0; i < BIASJuaComplianceConfigController.getTier1TrainTypesAsArray().length; i++)
					{
						tier1TrainTypes += BIASJuaComplianceConfigController.getTier1TrainTypesAsArray()[i];
						if (i < BIASJuaComplianceConfigController.getTier1TrainTypesAsArray().length - 1)
							tier1TrainTypes += ", ";
					}
					cell = row.createCell(1);
					cell.setCellStyle(style12);
					cell.setCellValue(tier1TrainTypes);

					rowCounter++;
					row = juaCompliancePriorities.createRow(rowCounter);
					cell = row.createCell(0);
					cell.setCellStyle(style5);
					cell.setCellValue("Delay Cost (higher means less likely to be delayed): ");

					String tier1DelayCosts = AnalyzeJuaComplianceFiles.getTier1LowDelayCost() +" - "+AnalyzeJuaComplianceFiles.getTier1HighDelayCost();
					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue(tier1DelayCosts);

					rowCounter++;
					row = juaCompliancePriorities.createRow(rowCounter);
					cell = row.createCell(0);
					cell.setCellStyle(style5);
					cell.setCellValue("Dispatch Rank (lower means better preferenced): ");

					String tier1Ranks = "";
					if (AnalyzeJuaComplianceFiles.getTier1LowRank().equals(AnalyzeJuaComplianceFiles.getTier1HighRank()))
					{
						tier1Ranks += AnalyzeJuaComplianceFiles.getTier1LowRank();
					}
					else
						tier1Ranks += AnalyzeJuaComplianceFiles.getTier1HighRank() + " to "+AnalyzeJuaComplianceFiles.getTier1LowRank();
					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue(tier1Ranks);
				}
				else
				{
					tier1TrainTypes = "None found in this tier";

					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue(tier1TrainTypes);
				}

				// Tier 2
				rowCounter++;
				rowCounter++;
				row = juaCompliancePriorities.createRow(rowCounter);

				juaCompliancePriorities.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 1));
				cell = row.createCell(0);
				cell.setCellStyle(style7);
				cell.setCellValue("Tier 2");

				cell = row.createCell(1);
				cell.setCellStyle(style7);
				cell.setCellValue("");

				rowCounter++;
				row = juaCompliancePriorities.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style5);
				cell.setCellValue("Applies to Train Types:");

				String tier2TrainTypes = "";
				if (BIASJuaComplianceConfigController.getTier2TrainTypesAsArray() != null)
				{
					for (int i = 0; i < BIASJuaComplianceConfigController.getTier2TrainTypesAsArray().length; i++)
					{
						tier2TrainTypes += BIASJuaComplianceConfigController.getTier2TrainTypesAsArray()[i];
						if (i < BIASJuaComplianceConfigController.getTier2TrainTypesAsArray().length - 1)
							tier2TrainTypes += ", ";
					}
					cell = row.createCell(1);
					cell.setCellStyle(style12);
					cell.setCellValue(tier2TrainTypes);

					rowCounter++;
					row = juaCompliancePriorities.createRow(rowCounter);
					cell = row.createCell(0);
					cell.setCellStyle(style5);
					cell.setCellValue("Delay Cost (higher means less likely to be delayed): ");

					String tier2DelayCosts = AnalyzeJuaComplianceFiles.getTier2LowDelayCost() +" - "+AnalyzeJuaComplianceFiles.getTier2HighDelayCost();
					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue(tier2DelayCosts);

					rowCounter++;
					row = juaCompliancePriorities.createRow(rowCounter);
					cell = row.createCell(0);
					cell.setCellStyle(style5);
					cell.setCellValue("Dispatch Rank (lower means better preferenced): ");

					String tier2Ranks = "";
					if (AnalyzeJuaComplianceFiles.getTier2LowRank().equals(AnalyzeJuaComplianceFiles.getTier2HighRank()))
					{
						tier2Ranks += AnalyzeJuaComplianceFiles.getTier2LowRank();
					}
					else
						tier2Ranks += AnalyzeJuaComplianceFiles.getTier2HighRank() + " to "+AnalyzeJuaComplianceFiles.getTier2LowRank();
					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue(tier2Ranks);
				}
				else
				{
					tier2TrainTypes = "None found in this tier";

					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue(tier2TrainTypes);
				}

				// Tier 3
				rowCounter++;
				rowCounter++;
				row = juaCompliancePriorities.createRow(rowCounter);

				juaCompliancePriorities.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 1));
				cell = row.createCell(0);
				cell.setCellStyle(style7);
				cell.setCellValue("Tier 3");

				cell = row.createCell(1);
				cell.setCellStyle(style7);
				cell.setCellValue("");

				rowCounter++;
				row = juaCompliancePriorities.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style5);
				cell.setCellValue("Applies to Train Types:");

				String tier3TrainTypes = "";
				if (BIASJuaComplianceConfigController.getTier3TrainTypesAsArray() != null)
				{
					for (int i = 0; i < BIASJuaComplianceConfigController.getTier3TrainTypesAsArray().length; i++)
					{
						tier3TrainTypes += BIASJuaComplianceConfigController.getTier3TrainTypesAsArray()[i];
						if (i < BIASJuaComplianceConfigController.getTier3TrainTypesAsArray().length - 1)
							tier3TrainTypes += ", ";
					}

					cell = row.createCell(1);
					cell.setCellStyle(style12);
					cell.setCellValue(tier3TrainTypes);

					rowCounter++;
					row = juaCompliancePriorities.createRow(rowCounter);
					cell = row.createCell(0);
					cell.setCellStyle(style5);
					cell.setCellValue("Delay Cost (higher means less likely to be delayed): ");

					String tier3DelayCosts = AnalyzeJuaComplianceFiles.getTier3LowDelayCost() +" - "+AnalyzeJuaComplianceFiles.getTier3HighDelayCost();
					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue(tier3DelayCosts);

					rowCounter++;
					row = juaCompliancePriorities.createRow(rowCounter);
					cell = row.createCell(0);
					cell.setCellStyle(style5);
					cell.setCellValue("Dispatch Rank (lower means better preferenced): ");

					String tier3Ranks = "";
					if (AnalyzeJuaComplianceFiles.getTier3LowRank().equals(AnalyzeJuaComplianceFiles.getTier3HighRank()))
					{
						tier3Ranks += AnalyzeJuaComplianceFiles.getTier3LowRank();
					}
					else
						tier3Ranks += AnalyzeJuaComplianceFiles.getTier3HighRank() + " to "+AnalyzeJuaComplianceFiles.getTier3LowRank();
					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue(tier3Ranks);
				}
				else
				{
					tier3TrainTypes = "None found in this tier";

					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue(tier3TrainTypes);
				}

				// Tier 4
				rowCounter++;
				rowCounter++;
				row = juaCompliancePriorities.createRow(rowCounter);

				juaCompliancePriorities.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 1));
				cell = row.createCell(0);
				cell.setCellStyle(style7);
				cell.setCellValue("Tier 4 (Lowest Priority)");

				cell = row.createCell(1);
				cell.setCellStyle(style7);
				cell.setCellValue("");

				rowCounter++;
				row = juaCompliancePriorities.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style5);
				cell.setCellValue("Applies to Train Types:");

				String tier4TrainTypes = "";
				if (BIASJuaComplianceConfigController.getTier4TrainTypesAsArray() != null)
				{
					for (int i = 0; i < BIASJuaComplianceConfigController.getTier4TrainTypesAsArray().length; i++)
					{
						tier4TrainTypes += BIASJuaComplianceConfigController.getTier4TrainTypesAsArray()[i];
						if (i < BIASJuaComplianceConfigController.getTier4TrainTypesAsArray().length - 1)
							tier4TrainTypes += ", ";
					}
					cell = row.createCell(1);
					cell.setCellStyle(style12);
					cell.setCellValue(tier4TrainTypes);

					rowCounter++;
					row = juaCompliancePriorities.createRow(rowCounter);
					cell = row.createCell(0);
					cell.setCellStyle(style5);
					cell.setCellValue("Delay Cost (higher means less likely to be delayed): ");

					String tier4DelayCosts = AnalyzeJuaComplianceFiles.getTier4LowDelayCost() +" - "+AnalyzeJuaComplianceFiles.getTier4HighDelayCost();
					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue(tier4DelayCosts);

					rowCounter++;
					row = juaCompliancePriorities.createRow(rowCounter);
					cell = row.createCell(0);
					cell.setCellStyle(style5);
					cell.setCellValue("Dispatch Rank (lower means better preferenced): ");

					String tier4Ranks = "";
					if (AnalyzeJuaComplianceFiles.getTier4LowRank().equals(AnalyzeJuaComplianceFiles.getTier4HighRank()))
					{
						tier4Ranks += AnalyzeJuaComplianceFiles.getTier4LowRank();
					}
					else
						tier4Ranks += AnalyzeJuaComplianceFiles.getTier4HighRank() + " to "+AnalyzeJuaComplianceFiles.getTier4LowRank();
					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue(tier4Ranks);
				}
				else
				{
					tier2TrainTypes = "None found in this tier";

					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue(tier4TrainTypes);
				}

				// Compliance determination
				rowCounter++;
				rowCounter++;
				juaCompliancePriorities.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 1));
				row = juaCompliancePriorities.createRow(rowCounter);
				cell = row.createCell(0);
				if (prioritiesAndRanksCompliantWithJua)
				{
					cell.setCellStyle(style9);
					cell.setCellValue("The train priorities and dispatch ranks are COMPLIANT");
				}
				else
				{
					cell.setCellStyle(style8);
					cell.setCellValue("The train priorities and dispatch ranks are NOT COMPLIANT");
				}
				rowCounter++;
				rowCounter++;
			}

			// Optionally, check both OPTION files priorities are the same
			if (BIASJuaComplianceConfigController.getCheckLastAcceptedOptionsFile())
			{
				// Header rows
				// Case name
				juaCompliancePriorities.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 1));
				
				row = juaCompliancePriorities.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style0);
				cell.setCellValue("JUA Compliance:  Comparison of "+thisCase+" and "+lastAcceptedOptionFileName2);
				
				// Equality determination
				rowCounter++;
				rowCounter++;
				juaCompliancePriorities.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 1));
				row = juaCompliancePriorities.createRow(rowCounter);
				cell = row.createCell(0);
				if (prioritiesInOptionFilesOfBothCasesEqual)
				{
					cell.setCellStyle(style9);
					cell.setCellValue("The train priorities and dispatch ranks of both cases are EQUAL");
				}
				else
				{
					cell.setCellStyle(style8);
					cell.setCellValue("The train priorities and dispatch ranks of both cases  are NOT EQUAL");
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
			for (int i = 0; i <= 1; i++) 
			{
				if (i == 0) 
				{
					juaCompliancePriorities.setColumnWidth(i, 12000);
				}
				else if (i == 1)
				{
					juaCompliancePriorities.setColumnWidth(i, 25000);
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

	public String getResultsMessageWrite3()
	{
		return resultsMessage;
	}
}