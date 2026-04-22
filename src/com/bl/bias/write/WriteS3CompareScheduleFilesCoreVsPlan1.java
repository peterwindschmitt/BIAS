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

public class WriteS3CompareScheduleFilesCoreVsPlan1 
{
	private static LocalTime startWriteFileTime = ConvertDateTime.getTimeStamp();
	protected static String resultsMessage1 = "\nStarted writing output file at "+startWriteFileTime;
	private static Boolean error = false;

	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet coreVsOperatedSheet;

	Integer rowCounter = 0;
	Integer totalDiscrepancies = 0;

	public WriteS3CompareScheduleFilesCoreVsPlan1 (Boolean api1, Boolean api2, String textArea, LocalDate startDate, LocalDate endDate, Map<LocalDate, ArrayList<ServiceObject>> trainsInAnalyzedDayButNotCoreDay, Map<LocalDate, ArrayList<ServiceObject>> trainsInCoreDayButNotAnalyzedDay, Map<LocalDate, ArrayList<ServiceObject>> trainsWithDifferentParameters, Boolean showDetailsForRetimedTrains, ArrayList<ArrayList<ServiceObject>> coreDates, ArrayList<ArrayList<ServiceObject>> analyzedDates)
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
		coreVsOperatedSheet = workbook.createSheet("Core vs Planned");
		coreVsOperatedSheet.setDisplayGridlines(false);

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
		coreVsOperatedSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

		Row row;
		Cell cell;

		row = coreVsOperatedSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style0);
		cell.setCellValue("Core vs Planned Trains ["+startDate+" to "+endDate+"]");

		rowCounter++;
		row = coreVsOperatedSheet.createRow(rowCounter);

		cell = row.createCell(0);
		cell.setCellStyle(style5);
		if (api1)
			cell.setCellValue("Source: "+BIASS3CompareScheduleConfigPageController.getProfileName1AsObservable().getValue());
		else
			cell.setCellValue("Source: "+BIASS3CompareScheduleConfigPageController.getProfileName2AsObservable().getValue());

		// Data headers section 1
		rowCounter++;
		rowCounter++;
		row = coreVsOperatedSheet.createRow(rowCounter);

		cell = row.createCell(0);
		cell.setCellStyle(style6);
		cell.setCellValue("Core Day of Week");

		cell = row.createCell(1);
		cell.setCellStyle(style6);
		cell.setCellValue("Associated Core Date*");

		rowCounter++;
		row = coreVsOperatedSheet.createRow(rowCounter);

		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Monday");
		cell = row.createCell(1);
		cell.setCellStyle(style5);
		if (BIASS3CompareScheduleConfigPageController.getMondayCoreDate() != null)
			cell.setCellValue(BIASS3CompareScheduleConfigPageController.getMondayCoreDate().toString());
		else
			cell.setCellValue("N/A");

		rowCounter++;
		row = coreVsOperatedSheet.createRow(rowCounter);

		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Tuesday");
		cell = row.createCell(1);
		cell.setCellStyle(style5);
		if (BIASS3CompareScheduleConfigPageController.getTuesdayCoreDate() != null)
			cell.setCellValue(BIASS3CompareScheduleConfigPageController.getTuesdayCoreDate().toString());
		else
			cell.setCellValue("N/A");

		rowCounter++;
		row = coreVsOperatedSheet.createRow(rowCounter);

		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Wednesday");
		cell = row.createCell(1);
		cell.setCellStyle(style5);
		if (BIASS3CompareScheduleConfigPageController.getWednesdayCoreDate() != null)
			cell.setCellValue(BIASS3CompareScheduleConfigPageController.getWednesdayCoreDate().toString());
		else
			cell.setCellValue("N/A");

		rowCounter++;
		row = coreVsOperatedSheet.createRow(rowCounter);

		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Thursday");
		cell = row.createCell(1);
		cell.setCellStyle(style5);
		if (BIASS3CompareScheduleConfigPageController.getThursdayCoreDate() != null)
			cell.setCellValue(BIASS3CompareScheduleConfigPageController.getThursdayCoreDate().toString());
		else
			cell.setCellValue("N/A");

		rowCounter++;
		row = coreVsOperatedSheet.createRow(rowCounter);

		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Friday");
		cell = row.createCell(1);
		cell.setCellStyle(style5);
		if (BIASS3CompareScheduleConfigPageController.getFridayCoreDate() != null)
			cell.setCellValue(BIASS3CompareScheduleConfigPageController.getFridayCoreDate().toString());
		else
			cell.setCellValue("N/A");

		rowCounter++;
		row = coreVsOperatedSheet.createRow(rowCounter);

		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Saturday");
		cell = row.createCell(1);
		cell.setCellStyle(style5);
		if (BIASS3CompareScheduleConfigPageController.getSaturdayCoreDate() != null)
			cell.setCellValue(BIASS3CompareScheduleConfigPageController.getSaturdayCoreDate().toString());
		else
			cell.setCellValue("N/A");

		rowCounter++;
		row = coreVsOperatedSheet.createRow(rowCounter);

		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Sunday");
		cell = row.createCell(1);
		cell.setCellStyle(style5);
		if (BIASS3CompareScheduleConfigPageController.getSundayCoreDate() != null)
			cell.setCellValue(BIASS3CompareScheduleConfigPageController.getSundayCoreDate().toString());
		else
			cell.setCellValue("N/A");
		rowCounter++;

		// For each analyzed day
		for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1))
		{
			String analyzedDayOfWeek = date.getDayOfWeek().toString();

			rowCounter++;
			row = coreVsOperatedSheet.createRow(rowCounter);
			coreVsOperatedSheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, 0, 1));

			cell = row.createCell(0);
			cell.setCellStyle(style7);
			cell.setCellValue(date.toString()+" ["+analyzedDayOfWeek+"]");

			cell = row.createCell(1);
			cell.setCellStyle(style7);
			cell.setCellValue("");

			Boolean reporting = false;
			if (trainsInAnalyzedDayButNotCoreDay.get(date) != null)
			{
				for (int i = 0; i < trainsInAnalyzedDayButNotCoreDay.get(date).size(); i++) 
				{
					totalDiscrepancies++; 
					rowCounter++;
					row = coreVsOperatedSheet.createRow(rowCounter);

					cell = row.createCell(0);
					cell.setCellStyle(style5);
					cell.setCellValue(trainsInAnalyzedDayButNotCoreDay.get(date).get(i).getServiceName());

					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue("Planned to operate but not in Core [ADD]");

					reporting = true;
				}
				rowCounter++;
			}

			if (trainsInCoreDayButNotAnalyzedDay.get(date) != null)
			{
				for (int i = 0; i < trainsInCoreDayButNotAnalyzedDay.get(date).size(); i++) 
				{
					totalDiscrepancies++;
					rowCounter++;
					row = coreVsOperatedSheet.createRow(rowCounter);

					cell = row.createCell(0);
					cell.setCellStyle(style5);
					cell.setCellValue(trainsInCoreDayButNotAnalyzedDay.get(date).get(i).getServiceName());

					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue("In Core but not planned to operate [CANCEL]");

					reporting = true;
				}
				rowCounter++;
			}

			if (trainsWithDifferentParameters.get(date) != null)
			{
				for (int i = 0; i < trainsWithDifferentParameters.get(date).size(); i++) 
				{
					totalDiscrepancies++;
					rowCounter++;
					row = coreVsOperatedSheet.createRow(rowCounter);

					cell = row.createCell(0);
					cell.setCellStyle(style5);
					cell.setCellValue(trainsWithDifferentParameters.get(date).get(i).getServiceName());

					cell = row.createCell(1);
					cell.setCellStyle(style5);
					cell.setCellValue("In Core and planned to operate but not all parameters are the same [RETIMEs and others]");

					if (showDetailsForRetimedTrains)
					{
						// Build core train string
						String coreTrainDetails = "";
						for (int j = 0; j < coreDates.get(date.getDayOfWeek().getValue() - 1).size(); j++)
						{
							if (coreDates.get(date.getDayOfWeek().getValue() - 1).get(j).getServiceName().equals(trainsWithDifferentParameters.get(date).get(i).getServiceName()))
							{
								coreTrainDetails = "     Core Train Type: "+coreDates.get(date.getDayOfWeek().getValue() - 1).get(j).getServiceType();
								coreTrainDetails += ", Origin: "+coreDates.get(date.getDayOfWeek().getValue() - 1).get(j).getDepartureLocation();
								coreTrainDetails += " at "+coreDates.get(date.getDayOfWeek().getValue() - 1).get(j).getDepartureTimestamp().substring(0, coreDates.get(date.getDayOfWeek().getValue() - 1).get(j).getDepartureTimestamp().length() - 5);
								coreTrainDetails += ", Destination: "+coreDates.get(date.getDayOfWeek().getValue() - 1).get(j).getArrivalLocation();
								coreTrainDetails += " at "+coreDates.get(date.getDayOfWeek().getValue() - 1).get(j).getArrivalTimestamp().substring(0, coreDates.get(date.getDayOfWeek().getValue() - 1).get(j).getArrivalTimestamp().length() - 5);
								break;
							}
						}

						// Build planned train string
						String analyzedTrainDetails = "";
						outerloop:
						for (int j = 0; j < analyzedDates.size(); j++)
						{
							for (int k = 0; k < analyzedDates.get(j).size(); k++)
							{
								if (trainsWithDifferentParameters.get(date).get(i).getServiceName().equals(analyzedDates.get(j).get(k).getServiceName()))
								{
									analyzedTrainDetails = "     Planned Train Type: "+trainsWithDifferentParameters.get(date).get(i).getServiceType();
									analyzedTrainDetails += ", Origin: "+trainsWithDifferentParameters.get(date).get(i).getDepartureLocation();
									analyzedTrainDetails += " at "+trainsWithDifferentParameters.get(date).get(i).getDepartureTimestamp().substring(0, trainsWithDifferentParameters.get(date).get(i).getDepartureTimestamp().length() - 5);
									analyzedTrainDetails += ", Destination: "+trainsWithDifferentParameters.get(date).get(i).getArrivalLocation();
									analyzedTrainDetails += " at "+trainsWithDifferentParameters.get(date).get(i).getArrivalTimestamp().substring(0, trainsWithDifferentParameters.get(date).get(i).getArrivalTimestamp().length() - 5);
									break outerloop;
								}
							}
						}
						
						rowCounter++;
						row = coreVsOperatedSheet.createRow(rowCounter);
						cell = row.createCell(1);
						cell.setCellStyle(style2);
						cell.setCellValue(coreTrainDetails);

						rowCounter++;
						row = coreVsOperatedSheet.createRow(rowCounter);
						cell = row.createCell(1);
						cell.setCellStyle(style2);
						cell.setCellValue(analyzedTrainDetails);
					}
					reporting = true;
				}
				rowCounter++;
			}

			if (!reporting)
			{
				rowCounter++;
				row = coreVsOperatedSheet.createRow(rowCounter);

				cell = row.createCell(0);
				cell.setCellStyle(style5);
				cell.setCellValue("No Discrepancies");
				rowCounter++;
			}
		}

		rowCounter++;
		row = coreVsOperatedSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Total discrepancies: "+totalDiscrepancies);
		rowCounter++;

		// Timestamp and footnote
		rowCounter++;
		row = coreVsOperatedSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("*Core Date does not have to match Core Day of Week (i.e., a Core Date which is a Wednesday can be assigned to a Core Day of Monday)");

		LocalDate creationDate = ConvertDateTime.getDateStamp();
		LocalTime creationTime = ConvertDateTime.getTimeStamp();

		rowCounter++;
		row = coreVsOperatedSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("Created on "+creationDate+" at "+creationTime);

		// Resize all columns to fit the content size
		for (int i = 0; i < 2; i++) 
		{
			if (i == 0)   // Core Day of Week
			{
				coreVsOperatedSheet.setColumnWidth(i, 9000);
			}
			else if (i == 1)   // Associated Core Date, Reason
			{
				coreVsOperatedSheet.setColumnWidth(i, 20100);
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