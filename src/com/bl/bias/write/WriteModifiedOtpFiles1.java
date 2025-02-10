package com.bl.bias.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.analyze.ModifiedOtpAnalysis;
import com.bl.bias.app.BIASModifiedOtpConfigPageController;
import com.bl.bias.objects.ModifiedOtpTrainObject;
import com.bl.bias.read.ReadModifiedOtpFiles;
import com.bl.bias.tools.ConvertDateTime;

public class WriteModifiedOtpFiles1 
{
	private static LocalTime startWriteFileTime = ConvertDateTime.getTimeStamp();
	protected static String resultsMessage1 = "\nStarted writing output file at "+startWriteFileTime;
	private static Boolean error = false;

	ArrayList<ModifiedOtpTrainObject> trainPeformanceFiles = new ArrayList<ModifiedOtpTrainObject>();
	HashMap<String, int[]> trainMakeData = new HashMap<String, int[]>();

	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet modifiedOtpSheet;

	Integer rowCounter = 0;

	public WriteModifiedOtpFiles1 (String textArea, String fileAsString)
	{
		trainPeformanceFiles.addAll(ReadModifiedOtpFiles.getPerformanceFileEntries()); 
		trainMakeData.putAll(ModifiedOtpAnalysis.returnMakeData()); 

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

		// Write Set A
		modifiedOtpSheet = workbook.createSheet("Modfied OTP");
		modifiedOtpSheet.setDisplayGridlines(false);

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

		// Font 2 - 9pt text, black font
		XSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 9);
		font2.setFontName("Calibri");

		// Font 3 - 11pt text, red font
		XSSFFont font3 = workbook.createFont();
		font3.setFontHeightInPoints((short) 11);
		font3.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
		font3.setFontName("Calibri");

		// Font 4 - 11pt text, green font
		XSSFFont font4 = workbook.createFont();
		font4.setFontHeightInPoints((short) 11);
		font4.setColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
		font4.setFontName("Calibri");

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

		// Style 2 - Left aligned, non-wrapped, 9pt, black text
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

		// Style 9 - Centered, wrapped, 11pt, green text
		style9.setAlignment(HorizontalAlignment.CENTER);  
		style9.setWrapText(true);
		style9.setFont(font4);

		// Header rows
		// Case name
		modifiedOtpSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 15));

		Row row;
		Cell cell;

		row = modifiedOtpSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style0);
		cell.setCellValue("Modified OTP Analysis (Sched vs Actual) for "+fileAsString.replace(".OPTION", ""));

		// Data headers
		rowCounter++;
		rowCounter++;
		row = modifiedOtpSheet.createRow(rowCounter);

		cell = row.createCell(0);
		cell.setCellStyle(style6);
		cell.setCellValue("File");

		cell = row.createCell(1);
		cell.setCellStyle(style6);
		cell.setCellValue("Train Symbol");

		cell = row.createCell(2);
		cell.setCellStyle(style7);
		cell.setCellValue("Train Type");

		cell = row.createCell(3);
		cell.setCellStyle(style7);
		cell.setCellValue("OTP Threshold (HH:MM:SS)");

		cell = row.createCell(4);
		cell.setCellStyle(style7);
		cell.setCellValue("Origin Reporting Node");

		cell = row.createCell(5);
		cell.setCellStyle(style7);
		cell.setCellValue("Scheduled at Origin (DD:HH:MM:SS)");

		cell = row.createCell(6);
		cell.setCellStyle(style7);
		cell.setCellValue("OS at Origin (DD:HH:MM:SS)");

		cell = row.createCell(7);
		cell.setCellStyle(style7);
		cell.setCellValue("Late at Origin (HH:MM:SS)");

		cell = row.createCell(8);
		cell.setCellStyle(style7);
		cell.setCellValue("Destination Reporting Node");

		cell = row.createCell(9);
		cell.setCellStyle(style7);
		cell.setCellValue("Scheduled at Destination (DD:HH:MM:SS)");

		cell = row.createCell(10);
		cell.setCellStyle(style7);
		cell.setCellValue("OS at Destination (DD:HH:MM:SS)");

		cell = row.createCell(11);
		cell.setCellStyle(style7);
		cell.setCellValue("Scheduled Transit Time (HH:MM:SS)");

		cell = row.createCell(12);
		cell.setCellStyle(style7);
		cell.setCellValue("Actual Transit Time (HH:MM:SS)");

		cell = row.createCell(13);
		cell.setCellStyle(style7);
		cell.setCellValue("Make?");

		cell = row.createCell(14);
		cell.setCellStyle(style7);
		cell.setCellValue("Num");

		cell = row.createCell(15);
		cell.setCellStyle(style7);
		cell.setCellValue("Denom");

		// By train symbol (ordering in .PERFORMANCE file)
		rowCounter++;
		for (int i = 0; i < trainPeformanceFiles.size(); i++)
		{
			if ((trainPeformanceFiles.get(i).getReportingPoints().size() == 0) && (BIASModifiedOtpConfigPageController.getSuppressTrainsAndResultsWithNoEligibleReportings()))
			{
				continue;
			}
			else
			{
				row = modifiedOtpSheet.createRow(rowCounter);

				// Run
				cell = row.createCell(0);
				cell.setCellStyle(style5);
				cell.setCellValue(trainPeformanceFiles.get(i).getPerformanceFileName().replace(fileAsString, "").replace(".PERFORMANCE", ""));

				// Train Symbol
				cell = row.createCell(1);
				cell.setCellStyle(style5);
				cell.setCellValue(trainPeformanceFiles.get(i).getTrainSymbol());

				// Train Type
				cell = row.createCell(2);
				cell.setCellStyle(style1);
				cell.setCellValue(trainPeformanceFiles.get(i).getTrainType());

				// Train's OTP Threshold
				String trainOtpThresholdAsString;
				if (BIASModifiedOtpConfigPageController.getUseOtpThresholds())
					trainOtpThresholdAsString = ConvertDateTime.convertSerialToHHMMSSString(trainPeformanceFiles.get(i).getOtpThresholdAsDouble());
				else
					trainOtpThresholdAsString = "N/A";
				cell = row.createCell(3);
				cell.setCellStyle(style1);
				cell.setCellValue(trainOtpThresholdAsString);

				// Need to accommodate multiple O/D pairs here as well as nulls
				if (trainPeformanceFiles.get(i).getReportingPoints().size() > 0)
				{
					// Origin Node
					String originNode = trainPeformanceFiles.get(i).getReportingPoints().get(0).getOriginNode();
					cell = row.createCell(4);
					cell.setCellStyle(style1);
					cell.setCellValue(originNode);

					// Scheduled Origin Time
					String scheduledOriginTime = ConvertDateTime.convertSerialToDDHHMMSSString(trainPeformanceFiles.get(i).getReportingPoints().get(0).getScheduleOriginTimeToUseAsDouble());
					cell = row.createCell(5);
					cell.setCellStyle(style1);
					cell.setCellValue(scheduledOriginTime);

					// Origin OS Time
					String originOsTime = ConvertDateTime.convertSerialToDDHHMMSSString(trainPeformanceFiles.get(i).getReportingPoints().get(0).getActualOriginTimeToUseAsDouble());
					cell = row.createCell(6);
					cell.setCellStyle(style1);
					cell.setCellValue(originOsTime);

					// Late at Origin
					if (trainPeformanceFiles.get(i).getReportingPoints().get(0).getLateAtOriginAsDouble() > 0)
					{
						String lateAtOrigin = ConvertDateTime.convertSerialToHHMMSSString(trainPeformanceFiles.get(i).getReportingPoints().get(0).getLateAtOriginAsDouble());
						cell = row.createCell(7);
						cell.setCellStyle(style1);
						cell.setCellValue(lateAtOrigin);
					}

					// Destination Node
					String destinationNode = trainPeformanceFiles.get(i).getReportingPoints().get(0).getDestinationNode();
					cell = row.createCell(8);
					cell.setCellStyle(style1);
					cell.setCellValue(destinationNode);

					// Scheduled Destination Time
					String scheduledDestinationTime = ConvertDateTime.convertSerialToDDHHMMSSString(trainPeformanceFiles.get(i).getReportingPoints().get(0).getScheduleDestinationTimeToUseAsDouble());
					cell = row.createCell(9);
					cell.setCellStyle(style1);
					cell.setCellValue(scheduledDestinationTime);

					// Destination OS Time
					String destinationOsTime = ConvertDateTime.convertSerialToDDHHMMSSString(trainPeformanceFiles.get(i).getReportingPoints().get(0).getActualDestinationTimeToUseAsDouble());
					cell = row.createCell(10);
					cell.setCellStyle(style1);
					cell.setCellValue(destinationOsTime);

					// Scheduled Transit Time
					String scheduledTransitTime = ConvertDateTime.convertSerialToDDHHMMSSString(trainPeformanceFiles.get(i).getReportingPoints().get(0).getScheduleTransitTimeAsDouble());
					cell = row.createCell(11);
					cell.setCellStyle(style1);
					cell.setCellValue(scheduledTransitTime);

					// Actual Transit Time
					String actualTransitTime = ConvertDateTime.convertSerialToDDHHMMSSString(trainPeformanceFiles.get(i).getReportingPoints().get(0).getActualTransitTimeAsDouble());
					cell = row.createCell(12);
					cell.setCellStyle(style1);
					cell.setCellValue(actualTransitTime);

					// Make
					String make = trainPeformanceFiles.get(i).getReportingPoints().get(0).getMake();
					cell = row.createCell(13);
					cell.setCellStyle(style1);
					cell.setCellValue(make);

					// Num
					Integer num = Integer.valueOf(trainPeformanceFiles.get(i).getReportingPoints().get(0).getNum());
					cell = row.createCell(14);
					cell.setCellStyle(style1);
					cell.setCellValue(num);

					// Denom
					Integer denom = Integer.valueOf(trainPeformanceFiles.get(i).getReportingPoints().get(0).getDenom());
					cell = row.createCell(15);
					cell.setCellStyle(style1);
					cell.setCellValue(denom);
				}
				rowCounter++;
			}
		}

		// Type OTP%
		rowCounter++;
		row = modifiedOtpSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style7);
		cell.setCellValue("Type (# in calc)");

		cell = row.createCell(1);
		cell.setCellStyle(style7);
		cell.setCellValue("% OTP");

		ArrayList<String> sortedKeys = new ArrayList<String>(trainMakeData.keySet());
		Collections.sort(sortedKeys);

		for (String type : sortedKeys)
		{
			if ((trainMakeData.get(type)[1] == 0) && (BIASModifiedOtpConfigPageController.getSuppressTrainsAndResultsWithNoEligibleReportings()))
			{
				continue;	
			}
			else if (trainMakeData.get(type)[1] == 0)
			{
				rowCounter++;
				String typeAndCount = type +" [0]";
				row = modifiedOtpSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style5);
				cell.setCellValue(typeAndCount);

				cell = row.createCell(1);
				cell.setCellStyle(style1);
				cell.setCellValue("N/A");	
			}
			else
			{
				double numerator = trainMakeData.get(type)[0];
				double denominator = trainMakeData.get(type)[1];
				double otp = Math.round((numerator/denominator) * 1000) / 10.0;
				int denominatorAsInt = (int) denominator;
				String typeAndCount = type +" ["+denominatorAsInt+"]";

				rowCounter++;
				row = modifiedOtpSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style5);
				cell.setCellValue(typeAndCount);

				cell = row.createCell(1);
				cell.setCellStyle(style1);
				cell.setCellValue(otp+"%");				
			}
		}

		// User-defined categories (if applicable)
		if (((BIASModifiedOtpConfigPageController.getAnalyzeUserCategorySet1()) && (BIASModifiedOtpConfigPageController.getValidCustomAssignment1Exists().getValue()))
				|| ((BIASModifiedOtpConfigPageController.getAnalyzeUserCategorySet2()) && (BIASModifiedOtpConfigPageController.getValidCustomAssignment2Exists().getValue())))
		{
			rowCounter++;
			rowCounter++;
			row = modifiedOtpSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style7);
			cell.setCellValue("User-Defined Category (# in calc)");

			cell = row.createCell(1);
			cell.setCellStyle(style7);
			cell.setCellValue("% OTP");

			// User-defined category 1
			if ((BIASModifiedOtpConfigPageController.getAnalyzeUserCategorySet1()) && (BIASModifiedOtpConfigPageController.getValidCustomAssignment1Exists().getValue()))
			{
				double numerator = 0.0;
				double denominator = 0.0;
				// for each type in user types
				String[] userCategory1Types = BIASModifiedOtpConfigPageController.getUserCategory1Types().getValue().split(",");
				for (String userCategoryType: userCategory1Types)
				{
					for (String type : sortedKeys)
					{
						if (userCategoryType.equals(type))
						{
							numerator += trainMakeData.get(type)[0];
							denominator += trainMakeData.get(type)[1];	
						}
					}
				}

				// Complete calc
				double otp = Math.round((numerator/denominator) * 1000) / 10.0;
				int denominatorAsInt = (int) denominator;
				String typeAndCount = BIASModifiedOtpConfigPageController.getUserCategory1Name().getValue() +" ["+denominatorAsInt+"]";

				rowCounter++;
				row = modifiedOtpSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style5);
				cell.setCellValue(typeAndCount);

				cell = row.createCell(1);
				cell.setCellStyle(style1);
				cell.setCellValue(otp+"%");
			}

			// User-defined category 2
			if ((BIASModifiedOtpConfigPageController.getAnalyzeUserCategorySet2()) && (BIASModifiedOtpConfigPageController.getValidCustomAssignment2Exists().getValue()))
			{
				double numerator = 0.0;
				double denominator = 0.0;
				// for each type in user types
				String[] userCategory2Types = BIASModifiedOtpConfigPageController.getUserCategory2Types().getValue().split(",");
				for (String userCategoryType: userCategory2Types)
				{
					for (String type : sortedKeys)
					{
						if (userCategoryType.equals(type))
						{
							numerator += trainMakeData.get(type)[0];
							denominator += trainMakeData.get(type)[1];	
						}
					}
				}

				// Complete calc
				double otp = Math.round((numerator/denominator) * 1000) / 10.0;
				int denominatorAsInt = (int) denominator;
				String typeAndCount = BIASModifiedOtpConfigPageController.getUserCategory2Name().getValue() +" ["+denominatorAsInt+"]";

				rowCounter++;
				row = modifiedOtpSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style5);
				cell.setCellValue(typeAndCount);

				cell = row.createCell(1);
				cell.setCellStyle(style1);
				cell.setCellValue(otp+"%");
			}
		}

		//  Footnotes
		rowCounter++;
		rowCounter++;
		row = modifiedOtpSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("A train, for a given set of origin/destination measuring points, counts as an OTP 'make' if:");

		if ((BIASModifiedOtpConfigPageController.getUseMethodology1()) && (BIASModifiedOtpConfigPageController.getUseOtpThresholds()))
		{
			rowCounter++;
			row = modifiedOtpSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("1. If (actual destination OS time) <= (scheduled destination time + OTP threshold); or");
			rowCounter++;
			row = modifiedOtpSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("2. When actual origin OS time is later than scheduled origin OS time, then a make is recorded if (actual destination time) <= (actual origin time + scheduled traversal time + OTP threshold)");
		}
		else if ((BIASModifiedOtpConfigPageController.getUseMethodology1()) && (!BIASModifiedOtpConfigPageController.getUseOtpThresholds()))
		{
			rowCounter++;
			row = modifiedOtpSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("1. If (actual destination OS time) <= (scheduled destination time); or");
			rowCounter++;
			row = modifiedOtpSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("2. When actual origin OS time is later than scheduled origin OS time, then a make is recorded if (actual destination time) <= (actual origin time + scheduled traversal time)");
		}
		else if ((BIASModifiedOtpConfigPageController.getUseMethodology2()) && (BIASModifiedOtpConfigPageController.getUseOtpThresholds()))
		{
			rowCounter++;
			row = modifiedOtpSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("1. If (actual destination OS time) <= (scheduled destination time + OTP threshold)");
		}
		else if ((BIASModifiedOtpConfigPageController.getUseMethodology2()) && (!BIASModifiedOtpConfigPageController.getUseOtpThresholds()))
		{
			rowCounter++;
			row = modifiedOtpSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("1. If (actual destination OS time) <= (scheduled destination time)");
		}	
		else if ((BIASModifiedOtpConfigPageController.getUseMethodology3()) && (BIASModifiedOtpConfigPageController.getUseOtpThresholds()))
		{
			rowCounter++;
			row = modifiedOtpSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("1. If (actual destination OS time) <= (actual origin time + scheduled traversal time + OTP threshold)");
		}
		else if ((BIASModifiedOtpConfigPageController.getUseMethodology3()) && (!BIASModifiedOtpConfigPageController.getUseOtpThresholds()))
		{
			rowCounter++;
			row = modifiedOtpSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("1. If (actual destination OS time) <= (actual origin time + scheduled traversal time)");
		}	

		// Are trains excepted
		if ((BIASModifiedOtpConfigPageController.getD_doNotExceptTrains()) && (BIASModifiedOtpConfigPageController.getUseMethodology3()))
		{
			rowCounter++;
			row = modifiedOtpSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("No trains are 'excepted'");
		}	
		else if (BIASModifiedOtpConfigPageController.getD_doNotExceptTrains())
		{
			rowCounter++;
			row = modifiedOtpSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("No trains are 'excepted' due to lateness at origin");
		}	

		// Timestamp and footnote
		LocalDate creationDate = ConvertDateTime.getDateStamp();
		LocalTime creationTime = ConvertDateTime.getTimeStamp();

		rowCounter++;
		rowCounter++;
		row = modifiedOtpSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("Created on "+creationDate+" at "+creationTime);

		// Resize all columns to fit the content size
		for (int i = 0; i <= 13; i++) 
		{
			if ((i == 1) || (i == 2))   // File, Symbol and type
			{
				modifiedOtpSheet.setColumnWidth(i, 5000);
			}
			else if ((i == 3) || (i == 5) || (i == 6) || (i == 8) || (i == 9) || (i == 10) || (i == 11) || (i == 12))
			{
				modifiedOtpSheet.setColumnWidth(i, 3800);
			}
			else if ((i == 4) || (i == 7))  // Nodes
			{
				modifiedOtpSheet.setColumnWidth(i, 3300);
			}
			else if (i == 13)  // Adjusted transit time
			{
				modifiedOtpSheet.setColumnWidth(i, 3300);
			}
			else if (i == 0) // Performance file name
			{
				modifiedOtpSheet.setColumnWidth(i, 8000);
			}
			else
			{
				modifiedOtpSheet.setColumnWidth(i, 2000);
			}
		}
	}

	public static String getResultsMessage1()
	{
		return resultsMessage1;
	}

	public static Boolean getErrorFound()
	{
		return error;
	}
}