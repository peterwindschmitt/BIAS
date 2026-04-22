package com.bl.bias.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

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

import com.bl.bias.app.BIASS3CompareScheduleConfigPageController;
import com.bl.bias.objects.ServiceObject;
import com.bl.bias.tools.ConvertDateTime;

public class WriteS3CompareScheduleFilesPlanVsPlan1 
{
	private static LocalTime startWriteFileTime = ConvertDateTime.getTimeStamp();
	protected static String resultsMessage1 = "\nStarted writing output file at "+startWriteFileTime;
	private static Boolean error = false;

	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet plannedVsPlanned;

	Integer rowCounter = 0;
	Integer totalDiscrepancies = 0;

	public WriteS3CompareScheduleFilesPlanVsPlan1 (Boolean api1, Boolean api2, String textArea, LocalDate scheduleDateA, LocalDate scheduleDateB, Map<LocalDate, ArrayList<ServiceObject>> trainsInScheduleDateAButNotScheduleDateB, Map<LocalDate, ArrayList<ServiceObject>> trainsInScheduleDateBButNotScheduleDateA, ArrayList<String> trainsWithDifferentParameters, Boolean showDetailsForRetimedTrains, ArrayList<ArrayList<ServiceObject>> analyzedDaysData)
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

		// Write Set A
		plannedVsPlanned = workbook.createSheet("Planned vs Planned");
		plannedVsPlanned.setDisplayGridlines(false);

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

		// Style 7 - Centered, non-wrapped, 11pt, black text
		style7.setAlignment(HorizontalAlignment.CENTER);  
		style7.setWrapText(false);
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

		// Style 10 - Left aligned, non-wrapped, 11pt, green text
		style10.setAlignment(HorizontalAlignment.LEFT);  
		style10.setWrapText(false);
		style10.setFont(font4);

		// Style 11 - Centered, non-wrapped, 11pt, green text
		style11.setAlignment(HorizontalAlignment.CENTER);  
		style11.setWrapText(false);
		style11.setFont(font4);

		// Header rows
		// Case name
		plannedVsPlanned.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

		Row row;
		Cell cell;

		row = plannedVsPlanned.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style0);
		cell.setCellValue("Planned Trains ["+scheduleDateA+" vs "+scheduleDateB+"]");

		rowCounter++;
		row = plannedVsPlanned.createRow(rowCounter);

		cell = row.createCell(0);
		cell.setCellStyle(style5);
		if (api1)
			cell.setCellValue("Source: "+BIASS3CompareScheduleConfigPageController.getProfileName1AsObservable().getValue());
		else
			cell.setCellValue("Source: "+BIASS3CompareScheduleConfigPageController.getProfileName2AsObservable().getValue());

		// Schedule day A
		rowCounter++;
		rowCounter++;
		row = plannedVsPlanned.createRow(rowCounter);
		plannedVsPlanned.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 1));

		String analyzedDayOfWeek = scheduleDateA.getDayOfWeek().toString();

		cell = row.createCell(0);
		cell.setCellStyle(style7);
		cell.setCellValue(scheduleDateA.toString()+" ["+analyzedDayOfWeek+"]");

		cell = row.createCell(1);
		cell.setCellStyle(style7);
		cell.setCellValue("");

		Boolean reporting = false;
		if (trainsInScheduleDateAButNotScheduleDateB.get(scheduleDateA) != null)
		{
			for (int i = 0; i < trainsInScheduleDateAButNotScheduleDateB.get(scheduleDateA).size(); i++) 
			{
				totalDiscrepancies++; 
				rowCounter++;
				row = plannedVsPlanned.createRow(rowCounter);

				cell = row.createCell(0);
				cell.setCellStyle(style5);
				cell.setCellValue(trainsInScheduleDateAButNotScheduleDateB.get(scheduleDateA).get(i).getServiceName());

				cell = row.createCell(1);
				cell.setCellStyle(style5);
				cell.setCellValue("Planned to operate on "+scheduleDateA+" but not on "+scheduleDateB);

				reporting = true;
			}
			rowCounter++;
		}

		if (!reporting)
		{
			rowCounter++;
			row = plannedVsPlanned.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style5);
			cell.setCellValue("No Discrepancies");
			rowCounter++;
		}

		// Schedule day B
		rowCounter++;
		row = plannedVsPlanned.createRow(rowCounter);
		plannedVsPlanned.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 1));

		analyzedDayOfWeek = scheduleDateB.getDayOfWeek().toString();

		cell = row.createCell(0);
		cell.setCellStyle(style7);
		cell.setCellValue(scheduleDateB.toString()+" ["+analyzedDayOfWeek+"]");

		cell = row.createCell(1);
		cell.setCellStyle(style7);
		cell.setCellValue("");

		reporting = false;
		if (trainsInScheduleDateBButNotScheduleDateA.get(scheduleDateB) != null)
		{
			for (int i = 0; i < trainsInScheduleDateBButNotScheduleDateA.get(scheduleDateB).size(); i++) 
			{
				totalDiscrepancies++; 
				rowCounter++;
				row = plannedVsPlanned.createRow(rowCounter);

				cell = row.createCell(0);
				cell.setCellStyle(style5);
				cell.setCellValue(trainsInScheduleDateBButNotScheduleDateA.get(scheduleDateB).get(i).getServiceName());

				cell = row.createCell(1);
				cell.setCellStyle(style5);
				cell.setCellValue("Planned to operate on "+scheduleDateB+" but not on "+scheduleDateA);

				reporting = true;
			}
			rowCounter++;
		}

		if (!reporting)
		{
			rowCounter++;
			row = plannedVsPlanned.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style5);
			cell.setCellValue("No Discrepancies");
			rowCounter++;
		}

		// In both schedules but different parameters
		rowCounter++;
		row = plannedVsPlanned.createRow(rowCounter);
		plannedVsPlanned.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 1));
		cell = row.createCell(0);
		cell.setCellStyle(style7);
		cell.setCellValue("Services with Different Parameters on "+scheduleDateA.toString()+" and "+scheduleDateB.toString());

		cell = row.createCell(1);
		cell.setCellStyle(style7);
		cell.setCellValue("");

		reporting = false;

		if (trainsWithDifferentParameters != null)
		{
			for (int i = 0; i < trainsWithDifferentParameters.size(); i++) 
			{
				totalDiscrepancies++;
				rowCounter++;
				row = plannedVsPlanned.createRow(rowCounter);

				cell = row.createCell(0);
				cell.setCellStyle(style5);
				cell.setCellValue(trainsWithDifferentParameters.get(i));

				cell = row.createCell(1);
				cell.setCellStyle(style5);
				cell.setCellValue("In both schedules but not all parameters are the same");

				if (showDetailsForRetimedTrains)
				{
					// Build schedule A train string
					String scheduleATrainDetails = "";
					for (int k = 0; k < analyzedDaysData.get(0).size(); k++)
					{
						if (analyzedDaysData.get(0).get(k).getServiceName().equals(trainsWithDifferentParameters.get(i)))
						{
							scheduleATrainDetails = "     "+ scheduleDateA+" Schedule Train Type: "+analyzedDaysData.get(0).get(k).getServiceType();
							scheduleATrainDetails += ", Origin: "+analyzedDaysData.get(0).get(k).getDepartureLocation();
							scheduleATrainDetails += " at "+analyzedDaysData.get(0).get(k).getDepartureTimestamp().substring(0, analyzedDaysData.get(0).get(k).getDepartureTimestamp().length() - 5);
							scheduleATrainDetails += ", Destination: "+analyzedDaysData.get(0).get(k).getArrivalLocation();
							scheduleATrainDetails += " at "+analyzedDaysData.get(0).get(k).getArrivalTimestamp().substring(0, analyzedDaysData.get(0).get(k).getArrivalTimestamp().length() - 5);
							break;
						}
					}

					// Build schedule B train string
					String scheduleBTrainDetails = "";
					for (int k = 0; k < analyzedDaysData.get(1).size(); k++)
					{
						if (analyzedDaysData.get(1).get(k).getServiceName().equals(trainsWithDifferentParameters.get(i)))
						{
							scheduleBTrainDetails = "     " + scheduleDateB+" Schedule Train Type: "+analyzedDaysData.get(1).get(k).getServiceType();
							scheduleBTrainDetails += ", Origin: "+analyzedDaysData.get(1).get(k).getDepartureLocation();
							scheduleBTrainDetails += " at "+analyzedDaysData.get(1).get(k).getDepartureTimestamp().substring(0, analyzedDaysData.get(1).get(k).getDepartureTimestamp().length() - 5);
							scheduleBTrainDetails += ", Destination: "+analyzedDaysData.get(1).get(k).getArrivalLocation();
							scheduleBTrainDetails += " at "+analyzedDaysData.get(1).get(k).getArrivalTimestamp().substring(0, analyzedDaysData.get(1).get(k).getArrivalTimestamp().length() - 5);
							break;
						}
					}
					
					rowCounter++;
					row = plannedVsPlanned.createRow(rowCounter);
					cell = row.createCell(1);
					cell.setCellStyle(style2);
					cell.setCellValue(scheduleATrainDetails);
					
					rowCounter++;
					row = plannedVsPlanned.createRow(rowCounter);
					cell = row.createCell(1);
					cell.setCellStyle(style2);
					cell.setCellValue(scheduleBTrainDetails);
				}
				reporting = true;
			}
			rowCounter++;
		}

		if (!reporting)
		{
			row = plannedVsPlanned.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style5);
			cell.setCellValue("No Discrepancies");
			rowCounter++;
		}

		rowCounter++;
		row = plannedVsPlanned.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Total discrepancies: "+totalDiscrepancies);
		rowCounter++;

		// Timestamp and footnote
		rowCounter++;
		LocalDate creationDate = ConvertDateTime.getDateStamp();
		LocalTime creationTime = ConvertDateTime.getTimeStamp();

		row = plannedVsPlanned.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("Created on "+creationDate+" at "+creationTime);

		// Resize all columns to fit the content size
		for (int i = 0; i < 2; i++) 
		{
			if (i == 0)
			{
				plannedVsPlanned.setColumnWidth(i, 9000);
			}
			else if (i == 1)
			{
				plannedVsPlanned.setColumnWidth(i, 20100);
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