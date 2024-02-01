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

import com.bl.bias.analyze.AnalyzeJuaComplianceFiles;
import com.bl.bias.app.BIASJuaComplianceConfigController;
import com.bl.bias.app.BIASJuaComplianceController;
import com.bl.bias.objects.ComplianceTrain;
import com.bl.bias.read.ReadJuaComplianceFiles;
import com.bl.bias.tools.ConvertDateTime;

public class WriteJuaComplianceFiles1
{
	private LocalTime startWriteFileTime = ConvertDateTime.getTimeStamp();
	protected String resultsMessage = "\nStarted writing output file at "+startWriteFileTime;
	protected Boolean error = false;

	private static String[] brightlineTrainTypes;
	private static String[] fecTrainTypes;
	private static String[] triRailTrainTypes;
	private static String[] brightlineNodes;
	private static String[] fecNodes;
	private static String[] triRailNodes;
	private static Integer totalCountOfBrightlineOperatedTrains;
	private static Integer totalCountOfFecOperatedTrains;
	private static Integer totalCountOfTriRailOperatedTrains;
	private static Integer maxDailyBrightlineOperatedTrains;
	private static Integer maxDailyFecOperatedTrains;
	private static Integer maxDailyTriRailOperatedTrains;

	private static Double averageDailyCountOfBrightlineOperatedTrains;
	private static Double averageDailyCountOfFecOperatedTrains;
	private static Double averageDailyCountOfTriRailOperatedTrains;
	private static ArrayList<String> seedTrainSymbolsFoundEligible;
	private static ArrayList<String> seedTrainSymbolsFoundNotEligible;
	private static Integer seedTrainSymbolCountFoundEligible;
	private static Integer seedTrainSymbolCountFoundNotEligible;
	private static ArrayList<ComplianceTrain> allSeedTrains;
	private static Integer statisticalPeriodDuration;

	XSSFWorkbook workbook = new XSSFWorkbook();

	Integer rowCounter = 0;

	Boolean trainHasRecoveryRates = false;

	public WriteJuaComplianceFiles1(String textArea, String fullyQualifiedPath)
	{
		brightlineTrainTypes = BIASJuaComplianceConfigController.getBrightlineTrainTypes();
		fecTrainTypes = BIASJuaComplianceConfigController.getFecTrainTypes();
		triRailTrainTypes = BIASJuaComplianceConfigController.getTriRailTrainTypes();
		brightlineNodes = BIASJuaComplianceConfigController.getBrightlineNodes();
		fecNodes = BIASJuaComplianceConfigController.getFecNodes();
		triRailNodes = BIASJuaComplianceConfigController.getTriRailNodes();
		totalCountOfBrightlineOperatedTrains = AnalyzeJuaComplianceFiles.getTotalCountOfBrightlinelOperatedTrains();
		totalCountOfFecOperatedTrains = AnalyzeJuaComplianceFiles.getTotalCountOfFecOperatedTrains();
		totalCountOfTriRailOperatedTrains = AnalyzeJuaComplianceFiles.getTotalCountOfTriRailOperatedTrains();
		maxDailyBrightlineOperatedTrains = BIASJuaComplianceConfigController.getDailyBrightlinePermitted();
		maxDailyFecOperatedTrains = BIASJuaComplianceConfigController.getDailyFecPermitted();
		maxDailyTriRailOperatedTrains = BIASJuaComplianceConfigController.getDailyTriRailPermitted();
		
		averageDailyCountOfBrightlineOperatedTrains = AnalyzeJuaComplianceFiles.getDailyAverageCountOfBrightlineOperatedTrains();
		averageDailyCountOfFecOperatedTrains = AnalyzeJuaComplianceFiles.getDailyAverageCountOfFecOperatedTrains();
		averageDailyCountOfTriRailOperatedTrains = AnalyzeJuaComplianceFiles.getDailyAverageCountOfTriRailOperatedTrains();
		seedTrainSymbolsFoundEligible = new ArrayList<String>();
		seedTrainSymbolsFoundNotEligible = new ArrayList<String>();
		seedTrainSymbolCountFoundEligible = AnalyzeJuaComplianceFiles.getSeedTrainSymbolCountFoundEligible();
		seedTrainSymbolCountFoundNotEligible = AnalyzeJuaComplianceFiles.getSeedTrainSymbolCountFoundNotEligible();
		allSeedTrains = new ArrayList<ComplianceTrain>();
		statisticalPeriodDuration = ReadJuaComplianceFiles.getStatisticalDurationInDays();
		
		seedTrainSymbolsFoundEligible.addAll(AnalyzeJuaComplianceFiles.getSeedTrainSymbolsFoundEligible());
		seedTrainSymbolsFoundNotEligible.addAll(AnalyzeJuaComplianceFiles.getSeedTrainSymbolsFoundNotEligible());
		allSeedTrains.addAll(ReadJuaComplianceFiles.getTrainsToAnalyzeForCompliance());

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

		// Write Set A
		XSSFSheet juaComplianceTrainCount;
		juaComplianceTrainCount = workbook.createSheet("Train Count");
		resultsMessage += "\nWriting train counts";
		juaComplianceTrainCount.setDisplayGridlines(false);

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

		// Header rows
		// Case name
		juaComplianceTrainCount.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		
		Row row;
		Cell cell;

		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style0);
		cell.setCellValue("JUA Compliance:  Train Count Assessment for "+BIASJuaComplianceController.getCaseNameAsString() + " Case");

		// Data headers
		rowCounter++;
		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style6);
		cell.setCellValue("INPUT PARAMETERS");

		cell = row.createCell(1);
		cell.setCellStyle(style6);
		cell.setCellValue("");

		cell = row.createCell(2);
		cell.setCellStyle(style6);
		cell.setCellValue("");

		cell = row.createCell(3);
		cell.setCellStyle(style6);
		cell.setCellValue("");

		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Eligible Brightline train types: ");

		cell = row.createCell(1);
		cell.setCellStyle(style5);
		cell.setCellValue(convertArrayOfStringsToString(brightlineTrainTypes));

		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Eligible Brightline nodes (of which at least 1 must be scheduled over): ");

		cell = row.createCell(1);
		cell.setCellStyle(style5);
		cell.setCellValue(convertArrayOfStringsToString(brightlineNodes));

		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Eligible FEC train types: ");

		cell = row.createCell(1);
		cell.setCellStyle(style5);
		cell.setCellValue(convertArrayOfStringsToString(fecTrainTypes));

		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Eligible FEC nodes (of which at least 1 must be scheduled over): ");

		cell = row.createCell(1);
		cell.setCellStyle(style5);
		cell.setCellValue(convertArrayOfStringsToString(fecNodes));

		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Eligible TriRail train types: ");

		cell = row.createCell(1);
		cell.setCellStyle(style5);
		cell.setCellValue(convertArrayOfStringsToString(triRailTrainTypes));

		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Eligible TriRail nodes (of which at least 1 must be scheduled over): ");

		cell = row.createCell(1);
		cell.setCellStyle(style5);
		cell.setCellValue(convertArrayOfStringsToString(triRailNodes));

		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Count of seed trains found in .TRAIN file: ");

		cell = row.createCell(1);
		cell.setCellStyle(style5);
		cell.setCellValue(allSeedTrains.size());

		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Days of statistical reporting: ");

		cell = row.createCell(1);
		cell.setCellStyle(style5);
		cell.setCellValue(statisticalPeriodDuration);

		rowCounter++;
		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style6);
		cell.setCellValue("COMPLIANCE COMPUTATIONS");

		cell = row.createCell(1);
		cell.setCellStyle(style6);
		cell.setCellValue("");

		cell = row.createCell(2);
		cell.setCellStyle(style6);
		cell.setCellValue("");

		cell = row.createCell(3);
		cell.setCellStyle(style6);
		cell.setCellValue("");

		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Count of eligible operated Brightline trains in .TRAIN file: ");

		cell = row.createCell(1);
		cell.setCellStyle(style5);
		cell.setCellValue(totalCountOfBrightlineOperatedTrains + " total");

		cell = row.createCell(2);
		cell.setCellStyle(style5);
		cell.setCellValue((Math.round(averageDailyCountOfBrightlineOperatedTrains * 10) / 10.0) + " average daily");

		cell = row.createCell(3);
		cell.setCellStyle(style5);
		cell.setCellValue("Average daily permitted is "+maxDailyBrightlineOperatedTrains);

		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Count of eligible operated FEC trains in .TRAIN file: ");

		cell = row.createCell(1);
		cell.setCellStyle(style5);
		cell.setCellValue(totalCountOfFecOperatedTrains + " total");

		cell = row.createCell(2);
		cell.setCellStyle(style5);
		cell.setCellValue((Math.round(averageDailyCountOfFecOperatedTrains * 10) / 10.0) + " average daily");

		cell = row.createCell(3);
		cell.setCellStyle(style5);
		cell.setCellValue("Average daily permitted is "+maxDailyFecOperatedTrains);

		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Count of eligible operated TriRail trains in .TRAIN file: ");

		cell = row.createCell(1);
		cell.setCellStyle(style5);
		cell.setCellValue(totalCountOfTriRailOperatedTrains + " total");

		cell = row.createCell(2);
		cell.setCellStyle(style5);
		cell.setCellValue((Math.round(averageDailyCountOfTriRailOperatedTrains * 10) / 10.0) + " average daily");

		cell = row.createCell(3);
		cell.setCellStyle(style5);
		cell.setCellValue("Average daily permitted is "+maxDailyTriRailOperatedTrains);

		// Show the eligible trains
		rowCounter++;
		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style6);
		cell.setCellValue("INCLUDED TRAINS ("+seedTrainSymbolCountFoundEligible+")");

		cell = row.createCell(1);
		cell.setCellStyle(style6);
		cell.setCellValue("");

		cell = row.createCell(2);
		cell.setCellStyle(style6);
		cell.setCellValue("");

		cell = row.createCell(3);
		cell.setCellStyle(style6);
		cell.setCellValue("");

		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style10);
		cell.setCellValue(convertArrayListOfStringsToString(seedTrainSymbolsFoundEligible));
		juaComplianceTrainCount.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 3));
		row.setHeight((short) Math.max(400, (seedTrainSymbolCountFoundEligible * 12)));
		
		// Show the ineligible trains
		rowCounter++;
		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style6);
		cell.setCellValue("EXCLUDED TRAINS ("+seedTrainSymbolCountFoundNotEligible+")");

		cell = row.createCell(1);
		cell.setCellStyle(style6);
		cell.setCellValue("");

		cell = row.createCell(2);
		cell.setCellStyle(style6);
		cell.setCellValue("");

		cell = row.createCell(3);
		cell.setCellStyle(style6);
		cell.setCellValue("");

		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style10);
		cell.setCellValue(convertArrayListOfStringsToString(seedTrainSymbolsFoundNotEligible));
		juaComplianceTrainCount.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 3));
		row.setHeight((short) Math.max(400, (seedTrainSymbolCountFoundNotEligible * 12)));
				
		rowCounter++;
		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);

		if (((Math.round(averageDailyCountOfBrightlineOperatedTrains * 10) / 10.0) <= maxDailyBrightlineOperatedTrains) 
				&& ((Math.round(averageDailyCountOfFecOperatedTrains * 10) / 10.0) <= maxDailyFecOperatedTrains) 
				&& ((Math.round(averageDailyCountOfTriRailOperatedTrains * 10) / 10.0) <= maxDailyTriRailOperatedTrains))
		{
			cell.setCellStyle(style9);
			cell.setCellValue("Train count is COMPLIANT");
		}
		else
		{
			cell.setCellStyle(style8);
			cell.setCellValue("Train count is NOT COMPLIANT");
		}

		// Timestamp and footnote
		LocalDate creationDate = ConvertDateTime.getDateStamp();
		LocalTime creationTime = ConvertDateTime.getTimeStamp();

		rowCounter++;
		rowCounter++;
		row = juaComplianceTrainCount.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("Created on "+creationDate+" at "+creationTime);

		// Resize all columns to fit the content size
		for (int i = 0; i <= 3; i++) 
		{
			if (i == 0) 
			{
				juaComplianceTrainCount.setColumnWidth(i, 16000);
			}
			else if (i == 1)
			{
				juaComplianceTrainCount.setColumnWidth(i, 4000);
			}
			else if (i == 2)
			{
				juaComplianceTrainCount.setColumnWidth(i, 6000);
			}
			else if (i == 3)
			{
				juaComplianceTrainCount.setColumnWidth(i, 23000);
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

	public String getResultsMessageWrite1()
	{
		return resultsMessage;
	}
}