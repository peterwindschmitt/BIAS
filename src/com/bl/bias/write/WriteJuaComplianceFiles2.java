package com.bl.bias.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

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
	
	private Boolean casesAreSame = true;

	Integer rowCounter = 0;

	private static String lastAcceptedTrainFileName1 = BIASJuaComplianceConfigController.getLastAcceptedTrainFileAsString().replace(".TRAIN","");
	private static String lastAcceptedTrainFileName2 = lastAcceptedTrainFileName1.substring(lastAcceptedTrainFileName1.lastIndexOf("\\") + 1);

	private static HashMap<String, Integer> setOfOperatedByTrainTypeThisCase = new HashMap<String, Integer>();
	private static HashMap<String, Integer> setOfOperatedByTrainTypeLastAcceptedCase = new HashMap<String, Integer>();

	private static HashMap<String, Integer> setOfSeedTrainsByTypeThisCase = new HashMap<String, Integer>();
	private static HashMap<String, Integer> setOfSeedTrainsByTrainTypeLastAcceptedCase = new HashMap<String, Integer>();

	private static HashMap<String, Double> setOfSumOfSeedTrainsByTrainTypeThisCase = new HashMap<String, Double>();
	private static HashMap<String, Double> setOfSumOfSeedTrainsByTrainTypeLastAcceptedCase = new HashMap<String, Double>();

	private static HashMap<String, Double> setOfSumOfSeedTrainDwellByLocationThisCase = new HashMap<String, Double>();
	private static HashMap<String, Double> setOfSumOfSeedTrainDwellByLocationLastAcceptedCase = new HashMap<String, Double>();

	private static Integer thisCaseSubTotal = 0;
	private static Integer lastAcceptedCaseSubTotal = 0;

	public WriteJuaComplianceFiles2(String textArea, String fullyQualifiedPath) 
	{
		super(textArea, fullyQualifiedPath);

		setOfSeedTrainsByTypeThisCase.clear();
		setOfSeedTrainsByTrainTypeLastAcceptedCase.clear();

		setOfOperatedByTrainTypeThisCase.clear();
		setOfOperatedByTrainTypeLastAcceptedCase.clear();

		setOfSumOfSeedTrainsByTrainTypeThisCase.clear();
		setOfSumOfSeedTrainsByTrainTypeLastAcceptedCase.clear();

		setOfSumOfSeedTrainDwellByLocationThisCase.clear();
		setOfSumOfSeedTrainDwellByLocationLastAcceptedCase.clear();

		setOfSeedTrainsByTypeThisCase = AnalyzeJuaComplianceFiles.getSortedSeedTrainsByTypeThisCase(); // This map is reverse sorted by entry
		setOfSeedTrainsByTrainTypeLastAcceptedCase = AnalyzeJuaComplianceFiles.getSeedTrainsByTypeLastAcceptedCase();

		setOfOperatedByTrainTypeThisCase = AnalyzeJuaComplianceFiles.getSortedTrainsOperatedByTypeThisCase();  // This map is reverse sorted by entry
		setOfOperatedByTrainTypeLastAcceptedCase = AnalyzeJuaComplianceFiles.getTrainsOperatedByTypeLastAcceptedCase();

		setOfSumOfSeedTrainsByTrainTypeThisCase = AnalyzeJuaComplianceFiles.getSortedSumOfSeedDurationsByTypeThisCase();  // This map is reverse sorted by entry
		setOfSumOfSeedTrainsByTrainTypeLastAcceptedCase = AnalyzeJuaComplianceFiles.getSumOfSeedDurationsByTypeLastAcceptedCase();

		setOfSumOfSeedTrainDwellByLocationThisCase = AnalyzeJuaComplianceFiles.getSumOfSeedTrainDwellByLocationThisCase();
		setOfSumOfSeedTrainDwellByLocationLastAcceptedCase = AnalyzeJuaComplianceFiles.getSumOfSeedTrainDwellByLocationLastAcceptedCase();

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

			// Count of operated trains by operator
			row = juaComplianceTrainComparison.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			cell = row.createCell(1);
			cell.setCellStyle(style7);
			cell.setCellValue("Count of Trains Operated by Operator*");
			rowCounter++;

			cell = row.createCell(2);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(AnalyzeJuaComplianceFiles.getTotalCountOfBrightlineOperatedTrainsThisCase());
			thisCaseSubTotal = AnalyzeJuaComplianceFiles.getTotalCountOfBrightlineOperatedTrainsThisCase();


			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("Brightline Trains Operated");

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(AnalyzeJuaComplianceFiles.getTotalCountOfBrightlineOperatedTrainsLastAcceptedCase());
			lastAcceptedCaseSubTotal += AnalyzeJuaComplianceFiles.getTotalCountOfBrightlineOperatedTrainsLastAcceptedCase();
			rowCounter++;
			
			if (AnalyzeJuaComplianceFiles.getTotalCountOfBrightlineOperatedTrainsThisCase() != AnalyzeJuaComplianceFiles.getTotalCountOfBrightlineOperatedTrainsLastAcceptedCase())
				casesAreSame = false;
			
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(AnalyzeJuaComplianceFiles.getTotalCountOfFecOperatedTrainsThisCase());
			thisCaseSubTotal += AnalyzeJuaComplianceFiles.getTotalCountOfFecOperatedTrainsThisCase();
			
			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("FEC Trains Operated");

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(AnalyzeJuaComplianceFiles.getTotalCountOfFecOperatedTrainsLastAcceptedCase());
			lastAcceptedCaseSubTotal += AnalyzeJuaComplianceFiles.getTotalCountOfFecOperatedTrainsLastAcceptedCase();
			rowCounter++;
			
			if (AnalyzeJuaComplianceFiles.getTotalCountOfFecOperatedTrainsThisCase() != AnalyzeJuaComplianceFiles.getTotalCountOfFecOperatedTrainsLastAcceptedCase())
				casesAreSame = false;
			
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(AnalyzeJuaComplianceFiles.getTotalCountOfTriRailOperatedTrainsThisCase());
			thisCaseSubTotal += AnalyzeJuaComplianceFiles.getTotalCountOfTriRailOperatedTrainsThisCase();

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("TriRail Trains Operated");

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(AnalyzeJuaComplianceFiles.getTotalCountOfTriRailOperatedTrainsLastAcceptedCase());
			lastAcceptedCaseSubTotal += AnalyzeJuaComplianceFiles.getTotalCountOfTriRailOperatedTrainsLastAcceptedCase();
			rowCounter++;
			
			if (AnalyzeJuaComplianceFiles.getTotalCountOfTriRailOperatedTrainsThisCase() != AnalyzeJuaComplianceFiles.getTotalCountOfTriRailOperatedTrainsLastAcceptedCase())
				casesAreSame = false;
			
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style11);
			cell.setCellValue(thisCaseSubTotal);

			cell = row.createCell(1);
			cell.setCellStyle(style11);
			cell.setCellValue("Total Operated");

			cell = row.createCell(2);
			cell.setCellStyle(style11);
			cell.setCellValue(lastAcceptedCaseSubTotal);
			rowCounter++;
			rowCounter++;

			// Count of operated trains by type
			thisCaseSubTotal = 0;
			lastAcceptedCaseSubTotal = 0;

			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			cell = row.createCell(1);
			cell.setCellStyle(style7);
			cell.setCellValue("Count of Operated Trains by Type*");

			cell = row.createCell(2);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			for (int i = 0; i < setOfOperatedByTrainTypeThisCase.keySet().size(); i++)
			{
				rowCounter++;
				row = juaComplianceTrainComparison.createRow(rowCounter);

				cell = row.createCell(0);
				cell.setCellStyle(style1);
				if (setOfOperatedByTrainTypeThisCase.get(setOfOperatedByTrainTypeThisCase.keySet().toArray()[i]) != null)
				{
					cell.setCellValue(setOfOperatedByTrainTypeThisCase.get(setOfOperatedByTrainTypeThisCase.keySet().toArray()[i]));
					thisCaseSubTotal += setOfOperatedByTrainTypeThisCase.get(setOfOperatedByTrainTypeThisCase.keySet().toArray()[i]);
				}
				else
					cell.setCellValue("0");

				cell = row.createCell(1);
				cell.setCellStyle(style1);
				cell.setCellValue(setOfOperatedByTrainTypeThisCase.keySet().toArray()[i].toString());

				cell = row.createCell(2);
				cell.setCellStyle(style1);
				cell.setCellValue("0");
				if (setOfOperatedByTrainTypeLastAcceptedCase.get(setOfOperatedByTrainTypeThisCase.keySet().toArray()[i]) != null)
				{
					cell.setCellValue(setOfOperatedByTrainTypeLastAcceptedCase.get(setOfOperatedByTrainTypeThisCase.keySet().toArray()[i]));
					lastAcceptedCaseSubTotal += setOfOperatedByTrainTypeLastAcceptedCase.get(setOfOperatedByTrainTypeThisCase.keySet().toArray()[i]);
				}
				else
					cell.setCellValue("0");
				
				if (!setOfOperatedByTrainTypeThisCase.get(setOfOperatedByTrainTypeThisCase.keySet().toArray()[i]).equals(setOfOperatedByTrainTypeLastAcceptedCase.get(setOfOperatedByTrainTypeThisCase.keySet().toArray()[i])))
					casesAreSame = false;
			}
			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style11);
			cell.setCellValue(thisCaseSubTotal);

			cell = row.createCell(1);
			cell.setCellStyle(style11);
			cell.setCellValue("Total Operated");

			cell = row.createCell(2);
			cell.setCellStyle(style11);
			cell.setCellValue(lastAcceptedCaseSubTotal);
			rowCounter++;
			rowCounter++;

			// Count of seed trains by type
			thisCaseSubTotal = 0;
			lastAcceptedCaseSubTotal = 0;

			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			cell = row.createCell(1);
			cell.setCellStyle(style7);
			cell.setCellValue("Count of Seed Trains by Type*");

			cell = row.createCell(2);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			for (int i = 0; i < setOfSeedTrainsByTypeThisCase.keySet().size(); i++)
			{
				rowCounter++;
				row = juaComplianceTrainComparison.createRow(rowCounter);

				cell = row.createCell(0);
				cell.setCellStyle(style1);
				if (setOfSeedTrainsByTypeThisCase.get(setOfSeedTrainsByTypeThisCase.keySet().toArray()[i]) != null)
				{
					cell.setCellValue(setOfSeedTrainsByTypeThisCase.get(setOfSeedTrainsByTypeThisCase.keySet().toArray()[i]));
					thisCaseSubTotal += setOfSeedTrainsByTypeThisCase.get(setOfSeedTrainsByTypeThisCase.keySet().toArray()[i]);
				}
				else
					cell.setCellValue("0");

				cell = row.createCell(1);
				cell.setCellStyle(style1);
				cell.setCellValue(setOfSeedTrainsByTypeThisCase.keySet().toArray()[i].toString());

				cell = row.createCell(2);
				cell.setCellStyle(style1);
				cell.setCellValue("0");
				if (setOfSeedTrainsByTrainTypeLastAcceptedCase.get(setOfSeedTrainsByTypeThisCase.keySet().toArray()[i]) != null)
				{
					cell.setCellValue(setOfSeedTrainsByTrainTypeLastAcceptedCase.get(setOfSeedTrainsByTypeThisCase.keySet().toArray()[i]));
					lastAcceptedCaseSubTotal += setOfSeedTrainsByTrainTypeLastAcceptedCase.get(setOfSeedTrainsByTypeThisCase.keySet().toArray()[i]);
				}
				else
					cell.setCellValue("0");
				
				if (!setOfSeedTrainsByTypeThisCase.get(setOfSeedTrainsByTypeThisCase.keySet().toArray()[i]).equals(setOfSeedTrainsByTrainTypeLastAcceptedCase.get(setOfSeedTrainsByTypeThisCase.keySet().toArray()[i])))
					casesAreSame = false;
			}
			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style11);
			cell.setCellValue(thisCaseSubTotal);

			cell = row.createCell(1);
			cell.setCellStyle(style11);
			cell.setCellValue("Total Seed");

			cell = row.createCell(2);
			cell.setCellStyle(style11);
			cell.setCellValue(lastAcceptedCaseSubTotal);
			rowCounter++;
			rowCounter++;

			// Sum of seed trains end-to-end scheduled duration by train type
			double thisCaseSubTotal = 0.0;
			double lastAcceptedCaseSubTotal = 0.0;

			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			cell = row.createCell(1);
			cell.setCellStyle(style7);
			cell.setCellValue("Sum of Seed Trains' End-to-End Duration by Type (DD:HH:MM:SS)^");

			cell = row.createCell(2);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			for (int i = 0; i < setOfSumOfSeedTrainsByTrainTypeThisCase.keySet().size(); i++)
			{
				rowCounter++;
				row = juaComplianceTrainComparison.createRow(rowCounter);

				cell = row.createCell(0);
				cell.setCellStyle(style1);
				if (setOfSumOfSeedTrainsByTrainTypeThisCase.get(setOfSumOfSeedTrainsByTrainTypeThisCase.keySet().toArray()[i]) != null)
				{
					cell.setCellValue(ConvertDateTime.convertSerialToDDHHMMSSString(setOfSumOfSeedTrainsByTrainTypeThisCase.get(setOfSumOfSeedTrainsByTrainTypeThisCase.keySet().toArray()[i])));
					thisCaseSubTotal += setOfSumOfSeedTrainsByTrainTypeThisCase.get(setOfSumOfSeedTrainsByTrainTypeThisCase.keySet().toArray()[i]);
				}
				else
					cell.setCellValue("N/A");

				cell = row.createCell(1);
				cell.setCellStyle(style1);
				cell.setCellValue(setOfSumOfSeedTrainsByTrainTypeThisCase.keySet().toArray()[i].toString());

				cell = row.createCell(2);
				cell.setCellStyle(style1);
				if (setOfSumOfSeedTrainsByTrainTypeLastAcceptedCase.get(setOfSumOfSeedTrainsByTrainTypeThisCase.keySet().toArray()[i]) != null)
				{
					cell.setCellValue(ConvertDateTime.convertSerialToDDHHMMSSString(setOfSumOfSeedTrainsByTrainTypeLastAcceptedCase.get(setOfSumOfSeedTrainsByTrainTypeThisCase.keySet().toArray()[i])));
					lastAcceptedCaseSubTotal += setOfSumOfSeedTrainsByTrainTypeLastAcceptedCase.get(setOfSumOfSeedTrainsByTrainTypeThisCase.keySet().toArray()[i]);
				}
				else
					cell.setCellValue("N/A");
				
				if (!setOfSumOfSeedTrainsByTrainTypeThisCase.get(setOfSumOfSeedTrainsByTrainTypeThisCase.keySet().toArray()[i]).equals(setOfSumOfSeedTrainsByTrainTypeLastAcceptedCase.get(setOfSumOfSeedTrainsByTrainTypeThisCase.keySet().toArray()[i])))
					casesAreSame = false;
			}
			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style11);
			cell.setCellValue(ConvertDateTime.convertSerialToDDHHMMSSString(thisCaseSubTotal));

			cell = row.createCell(1);
			cell.setCellStyle(style11);
			cell.setCellValue("Total Seed");

			cell = row.createCell(2);
			cell.setCellStyle(style11);
			cell.setCellValue(ConvertDateTime.convertSerialToDDHHMMSSString(lastAcceptedCaseSubTotal));
			rowCounter++;
			rowCounter++;

			// Check for DIFFERENCES in duration of scheduled dwell by location for seed trains over the statistical period
			thisCaseSubTotal = 0.0;
			lastAcceptedCaseSubTotal = 0.0;

			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			cell = row.createCell(1);
			cell.setCellStyle(style7);
			cell.setCellValue("Difference in Sum of Scheduled Dwell by Location (DD:HH:MM:SS)$");

			cell = row.createCell(2);
			cell.setCellStyle(style6);
			cell.setCellValue("");

			// Create a set of all dwell locations
			HashSet<String> allDwellLocationsAsHashSet = new HashSet<String>();
			allDwellLocationsAsHashSet.addAll(setOfSumOfSeedTrainDwellByLocationThisCase.keySet());
			allDwellLocationsAsHashSet.addAll(setOfSumOfSeedTrainDwellByLocationLastAcceptedCase.keySet());
			ArrayList<String> allDwellLocationsAsArrayList = new ArrayList<String>();
			allDwellLocationsAsArrayList.addAll(allDwellLocationsAsHashSet);
			Collections.sort(allDwellLocationsAsArrayList);

			for (int i = 0; i <  allDwellLocationsAsArrayList.size(); i++)
			{
				Double thisCaseEntry = 0.0;
				Double lastCaseEntry = 0.0;
				if (setOfSumOfSeedTrainDwellByLocationThisCase.get(allDwellLocationsAsArrayList.get(i)) != null) 
				{
					thisCaseEntry = setOfSumOfSeedTrainDwellByLocationThisCase.get(allDwellLocationsAsArrayList.get(i));
				}

				if (setOfSumOfSeedTrainDwellByLocationLastAcceptedCase.get(allDwellLocationsAsArrayList.get(i)) != null) 
				{
					lastCaseEntry = setOfSumOfSeedTrainDwellByLocationLastAcceptedCase.get(allDwellLocationsAsArrayList.get(i));
				}

				if (thisCaseEntry.equals(lastCaseEntry))
				{
					continue;
				}
				else
				{
					rowCounter++;
					row = juaComplianceTrainComparison.createRow(rowCounter);

					cell = row.createCell(0);
					cell.setCellStyle(style1); 
					cell.setCellValue(ConvertDateTime.convertSerialToDDHHMMSSString(thisCaseEntry));
					thisCaseSubTotal += thisCaseEntry;

					cell = row.createCell(1);
					cell.setCellStyle(style1);
					cell.setCellValue(allDwellLocationsAsArrayList.get(i));

					cell = row.createCell(2);
					cell.setCellStyle(style1);
					cell.setCellValue(ConvertDateTime.convertSerialToDDHHMMSSString(lastCaseEntry));
					lastAcceptedCaseSubTotal += lastCaseEntry;
					
					casesAreSame = false;
				}
			}
			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style11);
			cell.setCellValue(ConvertDateTime.convertSerialToDDHHMMSSString(thisCaseSubTotal));

			cell = row.createCell(1);
			cell.setCellStyle(style11);
			cell.setCellValue("Total Seed");

			cell = row.createCell(2);
			cell.setCellStyle(style11);
			cell.setCellValue(ConvertDateTime.convertSerialToDDHHMMSSString(lastAcceptedCaseSubTotal));
			
			// Case equality determination
			rowCounter++;
			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);

			if (casesAreSame)
			{
				cell.setCellStyle(style9);
				cell.setCellValue("Train impact is EQUIVALENT");
			}
			else
			{
				cell.setCellStyle(style8);
				cell.setCellValue("Train impact is NOT EQUIVALENT");
			}

			// Footer notes
			rowCounter++;
			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("*Only eligible operated trains, as selected in the JUA Compliance Configuration Train Count parameters, are considered");

			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("^Reflects the sum, by type, of each seed train's scheduled duration, for one trip, from initial origin to final destination");

			rowCounter++;
			row = juaComplianceTrainComparison.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("$Reflects the sum, by location, of each seed train's scheduled dwell, for one trip, from initial origin to final destination");

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