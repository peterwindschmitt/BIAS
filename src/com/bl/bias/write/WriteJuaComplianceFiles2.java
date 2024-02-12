package com.bl.bias.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

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

public class WriteJuaComplianceFiles2 extends WriteJuaComplianceFiles1
{
	protected Boolean error = false;

	Integer rowCounter = 0;

	private static String lastAcceptedTrainFileName1 = BIASJuaComplianceConfigController.getLastAcceptedTrainFileAsString().replace(".TRAIN","");
	private static String lastAcceptedTrainFileName2 = lastAcceptedTrainFileName1.substring(lastAcceptedTrainFileName1.lastIndexOf("\\") + 1);

	private static HashMap<String, Integer> countByTrainTypeThisCase = new HashMap<String, Integer>();
	private static HashMap<String, Integer> countByTrainTypeLastAcceptedCase = new HashMap<String, Integer>();

	public WriteJuaComplianceFiles2(String textArea, String fullyQualifiedPath) 
	{
		super(textArea, fullyQualifiedPath);

		countByTrainTypeThisCase.clear();
		countByTrainTypeLastAcceptedCase.clear();

		if (BIASJuaComplianceConfigController.getCheckLastAcceptedTrainsFile())
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

			XSSFSheet juaComplianceTrainComparison;
			juaComplianceTrainComparison = workbook.createSheet("Train Comparison");
			resultsMessage += "\nWriting train comparisons";
			juaComplianceTrainComparison.setDisplayGridlines(false);

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

			// Header rows
			// Case name
			juaComplianceTrainComparison.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

			Row row;
			Cell cell;

			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style0);
			cell.setCellValue("JUA Compliance:  Train Comparison Between "+thisCase + " .TRAIN File and "+lastAcceptedTrainFileName2+" .TRAIN File");

			// Header rows
			rowCounter++;
			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style11);
			cell.setCellValue(thisCase + "\n[This Case's .TRAIN File]");

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("Case ID");

			cell = row.createCell(2);
			cell.setCellStyle(style11);
			cell.setCellValue(lastAcceptedTrainFileName2 + "\n[Last Accepted .TRAIN File]");
			rowCounter++;
			rowCounter++;

			// Operated train counts
			row = juaComplianceTrainComparison.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			cell = row.createCell(1);
			cell.setCellStyle(style7);
			cell.setCellValue("Trains Operated*");
			rowCounter++;

			cell = row.createCell(2);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(AnalyzeJuaComplianceFiles.getTotalCountOfBrightlineOperatedTrainsThisCase());

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("Brightline Trains Operated");

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(AnalyzeJuaComplianceFiles.getTotalCountOfBrightlineOperatedTrainsLastAcceptedCase());
			rowCounter++;

			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(AnalyzeJuaComplianceFiles.getTotalCountOfFecOperatedTrainsThisCase());

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("FEC Trains Operated");

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(AnalyzeJuaComplianceFiles.getTotalCountOfFecOperatedTrainsLastAcceptedCase());
			rowCounter++;

			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(AnalyzeJuaComplianceFiles.getTotalCountOfTriRailOperatedTrainsThisCase());

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("TriRail Trains Operated");

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(AnalyzeJuaComplianceFiles.getTotalCountOfTriRailOperatedTrainsLastAcceptedCase());
			rowCounter++;
			rowCounter++;

			// Check sum of operated trains end-to-end scheduled duration by train type
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			cell = row.createCell(1);
			cell.setCellStyle(style7);
			cell.setCellValue("Sum of Starts by Type*");

			cell = row.createCell(2);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);


			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue("0");

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("XXXX*");

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue("0");

			// Check sum of operated trains end-to-end scheduled duration by train type
			rowCounter++;
			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			cell = row.createCell(1);
			cell.setCellStyle(style7);
			cell.setCellValue("Sum of End-to-End Durations by Type (DD:HH:MM:SS)^");

			cell = row.createCell(2);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue("0:00:00");

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("XXXX*");

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue("0:00:00");
			

			// Check duration of work events by location for seed trains over statistical period 
			rowCounter++;
			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			cell = row.createCell(1);
			cell.setCellStyle(style7);
			cell.setCellValue("Sum of Work Events by Location (DD:HH:MM:SS)$");

			cell = row.createCell(2);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue("0:00:00");

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("XXXX*");

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue("0:00:00");
			
			// Footer notes
			rowCounter++;
			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("*Only eligible operated trains, as selected in the JUA Compliance Configuration Train Count parameters, are considered");

			// Timestamp 
			LocalDate creationDate = ConvertDateTime.getDateStamp();
			LocalTime creationTime = ConvertDateTime.getTimeStamp();

			rowCounter++;
			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("Created on "+creationDate+" at "+creationTime);

			// Resize all columns to fit the content size
			for (int i = 0; i <= 2; i++) 
			{
				if (i == 0) 
				{
					juaComplianceTrainComparison.setColumnWidth(i, 15000);
				}
				else if (i == 1)
				{
					juaComplianceTrainComparison.setColumnWidth(i, 7000);
				}
				else if (i == 2)
				{
					juaComplianceTrainComparison.setColumnWidth(i, 15000);
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

	public String getResultsMessageWrite2()
	{
		return resultsMessage;
	}
}