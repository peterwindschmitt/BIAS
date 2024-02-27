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
import com.bl.bias.tools.ConvertNumberDatatypes;

public class WriteJuaComplianceFiles6 extends WriteJuaComplianceFiles5
{
	protected Boolean error = false;

	Integer rowCounter = 0;

	private static String lastAcceptedLinkFileName1 = BIASJuaComplianceConfigController.getLastAcceptedLinkFileAsString().replace(".LINK","");
	private static String lastAcceptedLinkFileName2 = lastAcceptedLinkFileName1.substring(lastAcceptedLinkFileName1.lastIndexOf("\\") + 1);

	private static Boolean linkFilesOfBothCasesEqual;

	public WriteJuaComplianceFiles6(String textArea, String fullyQualifiedPath) 
	{
		super(textArea, fullyQualifiedPath);
		
		linkFilesOfBothCasesEqual = true;
		
		if (BIASJuaComplianceConfigController.getAnalyzeLinks()) 
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

			XSSFSheet juaComplianceTrackComparison;
			juaComplianceTrackComparison = workbook.createSheet("Track Comparison");
			juaComplianceTrackComparison.setDisplayGridlines(false);
			resultsMessage += "\nWriting track assessment";

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
			juaComplianceTrackComparison.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

			Row row;
			Cell cell;

			row = juaComplianceTrackComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style0);
			cell.setCellValue("JUA Compliance:  Track Comparison Between "+thisCase + " .LINK File and "+lastAcceptedLinkFileName2+" .LINK File");

			// Header
			rowCounter++;
			rowCounter++;
			row = juaComplianceTrackComparison.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			cell = row.createCell(1);
			cell.setCellStyle(style7);
			cell.setCellValue("Comparison of Cases");

			cell = row.createCell(2);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			rowCounter++;
			rowCounter++;
			row = juaComplianceTrackComparison.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style11);
			cell.setCellValue(thisCase + "\n[This Case's .LINK File]");

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("Case ID");

			cell = row.createCell(2);
			cell.setCellStyle(style11);
			cell.setCellValue(lastAcceptedLinkFileName2 + "\n[Last Accepted .LINK File]");

			// Link count
			rowCounter++;
			rowCounter++;
			row = juaComplianceTrackComparison.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(AnalyzeJuaComplianceFiles.getLinkCountThisCase());

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("Link Count");

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(AnalyzeJuaComplianceFiles.getLinkCountLastAcceptedCase());

			// Link count
			rowCounter++;
			rowCounter++;
			row = juaComplianceTrackComparison.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(ConvertNumberDatatypes.round(AnalyzeJuaComplianceFiles.getMilesOfTrackThisCase(), 1));

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("Miles of Track");

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(ConvertNumberDatatypes.round(AnalyzeJuaComplianceFiles.getMilesOfTrackLastAcceptedCase(), 1));

			if (linkFilesOfBothCasesEqual)
			{
				if (!AnalyzeJuaComplianceFiles.getMilesOfTrackThisCase().equals(AnalyzeJuaComplianceFiles.getMilesOfTrackLastAcceptedCase()))
					linkFilesOfBothCasesEqual = false;
			}

			// Sum of track miles * speed for passenger trains
			rowCounter++;
			rowCounter++;
			row = juaComplianceTrackComparison.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(ConvertNumberDatatypes.round(AnalyzeJuaComplianceFiles.getPassengerSpeedMilesThisCase(), 0));

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("Track Miles * Passenger Speed");

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(ConvertNumberDatatypes.round(AnalyzeJuaComplianceFiles.getPassengerSpeedMilesLastAcceptedCase(), 0));

			if (linkFilesOfBothCasesEqual)
			{
				if (!AnalyzeJuaComplianceFiles.getPassengerSpeedMilesThisCase().equals(AnalyzeJuaComplianceFiles.getPassengerSpeedMilesLastAcceptedCase()))
					linkFilesOfBothCasesEqual = false;
			}

			// Sum of track miles * speed for through trains
			rowCounter++;
			row = juaComplianceTrackComparison.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(ConvertNumberDatatypes.round(AnalyzeJuaComplianceFiles.getThroughSpeedMilesThisCase(), 0));

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("Track Miles * Through Speed");

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(ConvertNumberDatatypes.round(AnalyzeJuaComplianceFiles.getThroughSpeedMilesLastAcceptedCase(), 0));

			if (linkFilesOfBothCasesEqual)
			{
				if (!AnalyzeJuaComplianceFiles.getThroughSpeedMilesThisCase().equals(AnalyzeJuaComplianceFiles.getThroughSpeedMilesLastAcceptedCase()))
					linkFilesOfBothCasesEqual = false;
			}

			// Sum of track miles * speed for local trains
			rowCounter++;
			row = juaComplianceTrackComparison.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(ConvertNumberDatatypes.round(AnalyzeJuaComplianceFiles.getLocalSpeedMilesThisCase(), 0));

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("Track Miles * Local Speed");

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(ConvertNumberDatatypes.round(AnalyzeJuaComplianceFiles.getLocalSpeedMilesLastAcceptedCase(), 0));

			if (linkFilesOfBothCasesEqual)
			{
				if (!AnalyzeJuaComplianceFiles.getLocalSpeedMilesThisCase().equals(AnalyzeJuaComplianceFiles.getLocalSpeedMilesLastAcceptedCase()))
					linkFilesOfBothCasesEqual = false;
			}

			// Equality determination
			rowCounter++;
			rowCounter++;
			row = juaComplianceTrackComparison.createRow(rowCounter);
			cell = row.createCell(0);
			if (linkFilesOfBothCasesEqual)
			{
				cell.setCellStyle(style9);
				cell.setCellValue("The track infrastructure of both files is EQUAL");
			}
			else
			{
				cell.setCellStyle(style8);
				cell.setCellValue("The track infrastructure of both files is NOT EQUAL");
			}

			// Timestamp and footnote
			LocalDate creationDate = ConvertDateTime.getDateStamp();
			LocalTime creationTime = ConvertDateTime.getTimeStamp();

			rowCounter++;
			rowCounter++;
			row = juaComplianceTrackComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("Created on "+creationDate+" at "+creationTime);

			// Resize all columns to fit the content size
			for (int i = 0; i <= 2; i++) 
			{
				if (i == 0) 
				{
					juaComplianceTrackComparison.setColumnWidth(i, 15000);
				}
				else if (i == 1)
				{
					juaComplianceTrackComparison.setColumnWidth(i, 8000);
				}
				else if (i == 2)
				{
					juaComplianceTrackComparison.setColumnWidth(i, 15000);
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

	public String getResultsMessageWrite6()
	{
		return resultsMessage;
	}
}