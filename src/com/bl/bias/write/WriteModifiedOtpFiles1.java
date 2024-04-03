package com.bl.bias.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

import com.bl.bias.app.BIASModifiedOtpConfigPageController;
import com.bl.bias.objects.ModifiedOtpTrainObject;
import com.bl.bias.tools.ConvertDateTime;

public class WriteModifiedOtpFiles1 
{
	private static LocalTime startWriteFileTime = ConvertDateTime.getTimeStamp();
	protected static String resultsMessage1 = "\nStarted writing output file at "+startWriteFileTime;
	protected static Boolean error = false;

	private static Boolean showColumnsForGraphs = BIASModifiedOtpConfigPageController.getGenerateSerialTimes();

	ArrayList<ModifiedOtpTrainObject> trains = new ArrayList<ModifiedOtpTrainObject>();

	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet modifiedOtpSheet;

	Integer rowCounter = 0;

	public WriteModifiedOtpFiles1 (ArrayList<ModifiedOtpTrainObject> trains, String textArea, String fileAsString)
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

		// Write Set A
		modifiedOtpSheet = workbook.createSheet("Modfied OTP Analysis");
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

		// Font 2 - 8pt text, black font
		XSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 8);
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

		// Style 9 - Centered, wrapped, 11pt, green text
		style9.setAlignment(HorizontalAlignment.CENTER);  
		style9.setWrapText(true);
		style9.setFont(font4);

		// Header rows
		// Case name
		if (showColumnsForGraphs)
			modifiedOtpSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		else
			modifiedOtpSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

		Row row;
		Cell cell;

		row = modifiedOtpSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style0);
		cell.setCellValue("Modified OTP Analysis for "+fileAsString.replace(".OPTION", ""));

		// Data headers
		rowCounter++;
		rowCounter++;
		row = modifiedOtpSheet.createRow(rowCounter);

		cell = row.createCell(0);
		cell.setCellStyle(style6);
		cell.setCellValue("Train Symbol");

		cell = row.createCell(1);
		cell.setCellStyle(style7);
		cell.setCellValue("Schedule Node");

		cell = row.createCell(2);
		cell.setCellStyle(style7);
		cell.setCellValue("Scheduled Time (HH:MM:SS)");

		cell = row.createCell(3);
		cell.setCellStyle(style7);
		cell.setCellValue("Observed Time (HH:MM:SS)");

		cell = row.createCell(4);
		cell.setCellStyle(style7);
		cell.setCellValue("Violation*");

		if (showColumnsForGraphs)
		{
			cell = row.createCell(5);
			cell.setCellStyle(style7);
			cell.setCellValue("Scheduled Time as Serial");

			cell = row.createCell(6);
			cell.setCellStyle(style7);
			cell.setCellValue("Observed Time as Serial");

			cell = row.createCell(7);
			cell.setCellStyle(style7);
			cell.setCellValue("Difference as Serial");
		}

		// Trains
		rowCounter++;
		Collections.sort(trains, new sortBySymbol());
		for (int i = 0; i < trains.size(); i++)
		{
			row = modifiedOtpSheet.createRow(rowCounter);

			// Train Symbol
			cell = row.createCell(0);
			cell.setCellStyle(style5);
			cell.setCellValue(trains.get(i).getSymbol());

			for (int k = 0; k < trains.get(i).getRouteEntries().size(); k++)
			{
				// Node 
				cell = row.createCell(1);
				cell.setCellStyle(style1);
				cell.setCellValue(trains.get(i).getRouteEntries().get(k).getNode());

				// Times
				Double scheduledArrivalTimeAsDouble =  ConvertDateTime.convertSecondsToSerial(ConvertDateTime.removeDDfromDDHHMMSSStringAndConvertToSeconds(trains.get(i).getRouteEntries().get(k).getScheduledArrivalTimeAsString()));
				Double scheduledDepartureTimeAsDouble =  ConvertDateTime.convertSecondsToSerial(ConvertDateTime.removeDDfromDDHHMMSSStringAndConvertToSeconds(trains.get(i).getRouteEntries().get(k).getScheduledDepartureTimeAsString()));
				Double simulatedArrivalTimeAsDouble = ConvertDateTime.convertSecondsToSerial(ConvertDateTime.removeDDfromDDHHMMSSStringAndConvertToSeconds(trains.get(i).getRouteEntries().get(k).getSimulatedArrivalTimeAsString()));
				Double simulatedDepartureTimeAsDouble = ConvertDateTime.convertSecondsToSerial(ConvertDateTime.removeDDfromDDHHMMSSStringAndConvertToSeconds(trains.get(i).getRouteEntries().get(k).getSimulatedDepartureTimeAsString()));

				// Scheduled time 
				String scheduledTime = ConvertDateTime.convertSerialToHHMMSSString(Math.max(scheduledDepartureTimeAsDouble, scheduledArrivalTimeAsDouble));
				if (scheduledTime.equals("00:00:00")) 
				{
					scheduledTime = "MIDNIGHT";
				}
				cell = row.createCell(2);
				cell.setCellStyle(style1);
				cell.setCellValue(scheduledTime);

				// Observed time
				String observedTime = ConvertDateTime.convertSerialToHHMMSSString(Math.max(simulatedDepartureTimeAsDouble, simulatedArrivalTimeAsDouble));
				if (observedTime.equals("00:00:00")) 
				{
					observedTime = "MIDNIGHT";
				}
				cell = row.createCell(3);
				cell.setCellStyle(style1);
				cell.setCellValue(observedTime);

				// Violation
				// Determine if it was over the threshold
				cell = row.createCell(4);
				if (trains.get(i).getRouteEntries().get(k).getIsCompliant())
				{
					cell.setCellStyle(style9);
					cell.setCellValue("N");
				}
				else
				{
					cell.setCellStyle(style8);
					cell.setCellValue("Y");
				}
				
				if (showColumnsForGraphs)
				{
					// Scheduled time as serial
					cell = row.createCell(5);
					cell.setCellStyle(style1);
					cell.setCellValue(Math.max(scheduledDepartureTimeAsDouble, scheduledArrivalTimeAsDouble));
					
					// Observed time as serial
					cell = row.createCell(6);
					cell.setCellStyle(style1);
					cell.setCellValue(Math.max(simulatedDepartureTimeAsDouble, simulatedArrivalTimeAsDouble));
					
					// Difference as serial
					cell = row.createCell(7);
					cell.setCellStyle(style1);
					cell.setCellValue(Math.max(simulatedDepartureTimeAsDouble, simulatedArrivalTimeAsDouble) - Math.max(scheduledDepartureTimeAsDouble, scheduledArrivalTimeAsDouble));
				}

				rowCounter++;
			}
		}
		rowCounter++;
		row = modifiedOtpSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("* A violation is defined as a train more than "+BIASModifiedOtpConfigPageController.getPermissibleMinutesOfDelayAsString()+" minutes late relative to its scheduled time." );

		// Timestamp and footnote
		LocalDate creationDate = ConvertDateTime.getDateStamp();
		LocalTime creationTime = ConvertDateTime.getTimeStamp();

		rowCounter++;
		row = modifiedOtpSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("Created on "+creationDate+" at "+creationTime);

		// Resize all columns to fit the content size
		for (int i = 0; i <= 7; i++) 
		{
			if (i == 0) 
			{
				modifiedOtpSheet.setColumnWidth(i, 6000);
			}
			else if ((i == 1) || (i == 2) || (i == 3) || (i == 4) || (i == 5) || (i == 6) || (i == 7))
			{
				modifiedOtpSheet.setColumnWidth(i, 5000);
			}
			else
				modifiedOtpSheet.setColumnWidth(5, 10000);
		}
	}

	class sortBySymbol implements Comparator<ModifiedOtpTrainObject> 
	{ 
		@Override
		public int compare(ModifiedOtpTrainObject o1, ModifiedOtpTrainObject o2) 
		{	    
			String x1 = o1.getSymbol();
			String x2 = o2.getSymbol();

			int sComp = x1.compareTo(x2);
			if (sComp != 0) 
			{
				return sComp;
			} 

			x1 = o1.getSymbol();
			x2 = o2.getSymbol();

			return x1.compareTo(x2);
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