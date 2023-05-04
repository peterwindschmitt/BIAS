package com.bl.bias.write;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.objects.RadixxScheduleInputS3;
import com.bl.bias.tools.ConvertDateTime;

public class WriteRadixxS3ToExcelFiles1
{
	protected String resultsMessage = "Started writing output file at "+ConvertDateTime.getTimeStamp();

	XSSFWorkbook workbook = new XSSFWorkbook();

	public WriteRadixxS3ToExcelFiles1(String textAreaContents, String locationOfInputFiles, String fileAsString, RadixxScheduleInputS3 schedule)
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
		CellStyle style13 = workbook.createCellStyle();

		// Write converted data sheet
		XSSFSheet convertedRadixxDataSheet = workbook.createSheet("Radixx Res SSIM");
		
		// Set data format to text for all entries where input is possible
		DataFormat fmt = workbook.createDataFormat();

		// Fonts
		// Font 0 - 14pt White text
		XSSFFont font0 = workbook.createFont();
		font0.setFontHeightInPoints((short) 11);
		font0.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		font0.setFontName("Calibri");

		// Font 1 - 11pt text
		XSSFFont font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 11);
		font1.setFontName("Calibri");

		// Font 2 - 8pt text
		XSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 8);
		font2.setFontName("Calibri");

		// Font 3 - 11pt italicized text
		XSSFFont font3 = workbook.createFont();
		font3.setFontHeightInPoints((short) 11);
		font3.setItalic(true);
		font3.setFontName("Calibri");

		// Cell styles
		// Style 0 - Left aligned, non-wrapped 11pt, white text against black background
		style0.setAlignment(HorizontalAlignment.LEFT);  
		style0.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		style0.setFillPattern(FillPatternType.FINE_DOTS);
		style0.setWrapText(false);
		style0.setFont(font0);

		// Style 1 - Left aligned, non-wrapped, 8pt, black text
		style1.setAlignment(HorizontalAlignment.LEFT);  
		style1.setWrapText(false);
		style1.setFont(font2);

		// Style 2 - Left aligned, non-wrapped, 11pt, black text
		style2.setAlignment(HorizontalAlignment.LEFT);  
		style2.setVerticalAlignment(VerticalAlignment.TOP);
		style2.setWrapText(false);
		style2.setFont(font1);

		// Style 3 - Right aligned, non-wrapped, 11pt, black text
		style3.setAlignment(HorizontalAlignment.RIGHT);  
		style3.setWrapText(false);
		style3.setFont(font1);

		// Style 4 - Left, non-wrapped 11pt, black text against yellow background
		style4.setAlignment(HorizontalAlignment.LEFT);  
		style4.setVerticalAlignment(VerticalAlignment.TOP);
		style4.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		style4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style4.setWrapText(false);
		style4.setFont(font1);

		// Style 5 - Left aligned, wrapped 11pt, white text against black background
		style5.setAlignment(HorizontalAlignment.LEFT);  
		style5.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		style5.setFillPattern(FillPatternType.FINE_DOTS);
		style5.setWrapText(true);
		style5.setFont(font0);

		// Style 6 - Center, wrapped 11pt, black text
		style6.setAlignment(HorizontalAlignment.CENTER);  
		style6.setWrapText(true);
		style6.setFont(font1);

		// Style 7 - Center, non-wrapped 11pt, white text against yellow background
		style7.setDataFormat(fmt.getFormat("@"));
		style7.setAlignment(HorizontalAlignment.CENTER);  
		style7.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		style7.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style7.setWrapText(false);
		style7.setFont(font1);

		// Style 8 - Center, non-wrapped 11pt, white text against green background
		style8.setDataFormat(fmt.getFormat("@"));
		style8.setAlignment(HorizontalAlignment.CENTER);  
		style8.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		style8.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style8.setWrapText(false);
		style8.setFont(font1);

		// Style 9 - Left aligned, wrapped, 11pt, black text
		style9.setAlignment(HorizontalAlignment.LEFT);  
		style9.setWrapText(true);
		style9.setFont(font1);

		// Style 10 - Left aligned, non-wrapped, 11pt, black text, italicized
		style10.setAlignment(HorizontalAlignment.LEFT);  
		style10.setVerticalAlignment(VerticalAlignment.TOP);
		style10.setWrapText(false);
		style10.setFont(font3);

		// Style 11 - Left, non-wrapped 11pt, black text against gray background
		style11.setDataFormat(fmt.getFormat("@"));
		style11.setAlignment(HorizontalAlignment.LEFT);  
		style11.setVerticalAlignment(VerticalAlignment.TOP);
		style11.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style11.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style11.setWrapText(false);
		style11.setFont(font1);

		// Style 12 - Left aligned, non-wrapped, 11pt, black text
		style12.setAlignment(HorizontalAlignment.LEFT);  
		style12.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		style12.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style12.setWrapText(false);
		style12.setFont(font1);
		
		// Style 13 - Red solid fill to signify end of Type 3/4 records
		style13.setFillForegroundColor(IndexedColors.RED.getIndex());
		style13.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		Row row;
		Cell cell;
		Integer rowCounter = 0;
		String lastFlightNumber = null; 

		row = convertedRadixxDataSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style0);
		cell.setCellValue("SSIM File");
		rowCounter++;
		row = convertedRadixxDataSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("File Name:");
		cell = row.createCell(1);
		cell.setCellStyle(style2);
		cell.setCellValue(fileAsString);
		cell = row.createCell(2);
		cell.setCellStyle(style10);
		cell.setCellValue("Elements with an * have additional notes at the bottom of this sheet!");
		rowCounter++;
		row = convertedRadixxDataSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("Source Format:");
		cell = row.createCell(1);
		cell.setCellStyle(style2);
		cell.setCellValue("S3");
		cell = row.createCell(2);
		rowCounter++;
		rowCounter++;

		// Header record - type 1
		// Not used

		// Carrier record - type 2
		resultsMessage += "\nConverting company record to spreadsheet";

		row = convertedRadixxDataSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style0);
		cell.setCellValue("Company Record");
		rowCounter++;

		row = convertedRadixxDataSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("Description: ");
		cell = row.createCell(1);
		cell.setCellStyle(style11);
		cell.setCellValue(schedule.getCarrier().getDescription());
		cell = row.createCell(2);
		cell.setCellStyle(style2);
		cell.setCellValue("0 - 29 chars");
		cell = row.createCell(4);
		cell.setCellStyle(style2);
		cell.setCellValue("Time Mode: ");
		cell = row.createCell(5);
		cell.setCellStyle(style2);
		cell.setCellValue(schedule.getCarrier().getTimeMode());
		cell = row.createCell(6);
		cell.setCellStyle(style2);
		cell.setCellValue("'L' or 'U'");
		rowCounter++;

		row = convertedRadixxDataSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("Start Period of Validity: ");
		cell = row.createCell(1);
		cell.setCellStyle(style11);
		String carrierStartPeriodOfValidity = schedule.getCarrier().getPeriodOfValidity().substring(0,7);
		cell.setCellValue(carrierStartPeriodOfValidity);
		cell = row.createCell(2);
		cell.setCellStyle(style2);
		cell.setCellValue("DDMMMYY");
		cell = row.createCell(4);
		cell.setCellStyle(style2);
		cell.setCellValue("Company Code: "); // Airline Designator in IATA doc
		cell = row.createCell(5);
		cell.setCellStyle(style11);
		cell.setCellValue(schedule.getCarrier().getCompanyCode());
		cell = row.createCell(6);
		cell.setCellStyle(style2);
		cell.setCellValue("1 - 3 chars");
		rowCounter++;
		
		row = convertedRadixxDataSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("End Period of Validity: ");
		cell = row.createCell(1);
		cell.setCellStyle(style11);
		String carrierEndPeriodOfValidity = schedule.getCarrier().getPeriodOfValidity().substring(7,14);
		cell.setCellValue(carrierEndPeriodOfValidity);
		cell = row.createCell(2);
		cell.setCellStyle(style2);
		cell.setCellValue("DDMMMYY");
		cell = row.createCell(4);
		cell.setCellStyle(style2);
		cell.setCellValue("Creation Date: ");
		cell = row.createCell(5);
		cell.setCellStyle(style11);
		cell.setCellValue(schedule.getCarrier().getCreationDate());
		cell = row.createCell(6);
		cell.setCellStyle(style2);
		cell.setCellValue("DDMMMYY");
		rowCounter++;
		rowCounter++;

		// Flight Leg Records and Segment Data Records are combined in the spreadsheet generated
		resultsMessage += "\nConverting timetable records to spreadsheet";

		row = convertedRadixxDataSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style5);
		cell.setCellValue("Timetable Records*");
		rowCounter++;

		// Flight Records
		// Title
		row = convertedRadixxDataSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style6);
		//cell.setCellValue("Railroad ID"); // Airline Designator in IATA doc
		cell.setCellValue("Company Code"); // Airline Designator in IATA doc
		cell = row.createCell(1);
		cell.setCellStyle(style6);
		cell.setCellValue("Train Number"); // Flight Number in IATA doc
		cell = row.createCell(2);
		cell.setCellStyle(style6);
		cell.setCellValue("Itinerary Variation Identifier");
		cell = row.createCell(3);
		cell.setCellStyle(style6);
		cell.setCellValue("Leg Sequence Number");
		cell = row.createCell(4);
		cell.setCellStyle(style6);
		cell.setCellValue("Commercial Category");
		cell = row.createCell(5);
		cell.setCellStyle(style6);
		cell.setCellValue("Period of Operation Start");
		cell = row.createCell(6);
		cell.setCellStyle(style6);
		cell.setCellValue("Period of Operation End");
		cell = row.createCell(7);
		cell.setCellStyle(style6);
		cell.setCellValue("Day of Operation");
		cell = row.createCell(8);
		cell.setCellStyle(style6);
		cell.setCellValue("Departure Station");
		cell = row.createCell(9);
		cell.setCellStyle(style6);
		cell.setCellValue("Passenger STD");
		cell = row.createCell(10);
		cell.setCellStyle(style6);
		cell.setCellValue("Train STD"); // Aircraft STD in IATA doc
		cell = row.createCell(11);
		cell.setCellStyle(style6);
		cell.setCellValue("Time Variation Departure");
		cell = row.createCell(12);
		cell.setCellStyle(style6);
		cell.setCellValue("Departure Terminal");
		cell = row.createCell(13);
		cell.setCellStyle(style6);
		cell.setCellValue("Arrival Station");
		cell = row.createCell(14);
		cell.setCellStyle(style6);
		cell.setCellValue("Train STA"); // Train STA in IATA doc
		cell = row.createCell(15);
		cell.setCellStyle(style6);
		cell.setCellValue("Passenger STA");
		cell = row.createCell(16);
		cell.setCellStyle(style6);
		cell.setCellValue("Time Variation Arrival");
		cell = row.createCell(17);
		cell.setCellStyle(style6);
		cell.setCellValue("Arrival Terminal");
		cell = row.createCell(18);
		cell.setCellStyle(style6);
		cell.setCellValue("Service Type"); // Aircraft Type in IATA doc
		rowCounter++;

		// Format
		row = convertedRadixxDataSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style6);
		cell.setCellValue("1 - 3 chars");
		cell = row.createCell(1);
		cell.setCellStyle(style6);
		cell.setCellValue("1 - 4 chars");
		cell = row.createCell(2);
		cell.setCellStyle(style6);
		cell.setCellValue("2 digits");
		cell = row.createCell(3);
		cell.setCellStyle(style6);
		cell.setCellValue("2 digits");
		cell = row.createCell(4);
		cell.setCellStyle(style6);
		cell.setCellValue("1 char");
		cell = row.createCell(5);
		cell.setCellStyle(style6);
		cell.setCellValue("DDMMMYY");
		cell = row.createCell(6);
		cell.setCellStyle(style6);
		cell.setCellValue("DDMMMYY");
		cell = row.createCell(7);
		cell.setCellStyle(style6);
		cell.setCellValue("max 7 digits");
		cell = row.createCell(8);
		cell.setCellStyle(style6);
		cell.setCellValue("3 chars");
		cell = row.createCell(9);
		cell.setCellStyle(style6);
		cell.setCellValue("4 digits");
		cell = row.createCell(10);
		cell.setCellStyle(style6);
		cell.setCellValue("4 digits");
		cell = row.createCell(11);
		cell.setCellStyle(style6);
		cell.setCellValue("4 digits*");
		cell = row.createCell(12);
		cell.setCellStyle(style6);
		cell.setCellValue("1 - 2 chars");
		cell = row.createCell(13);
		cell.setCellStyle(style6);
		cell.setCellValue("3 chars");
		cell = row.createCell(14);
		cell.setCellStyle(style6);
		cell.setCellValue("4 digits");
		cell = row.createCell(15);
		cell.setCellStyle(style6);
		cell.setCellValue("4 digits");
		cell = row.createCell(16);
		cell.setCellStyle(style6);
		cell.setCellValue("4 digits*");
		cell = row.createCell(17);
		cell.setCellStyle(style6);
		cell.setCellValue("1 - 2 chars");
		cell = row.createCell(18);
		cell.setCellStyle(style6);
		cell.setCellValue("3 chars");
		rowCounter++;

		// For each flight leg
		for (int i = 0; i < schedule.getFlightLegs().size(); i++)
		{
			row = convertedRadixxDataSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getCompanyCode());
			cell = row.createCell(1);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getTrainNumber());
			cell = row.createCell(2);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getItineraryVariationIdentifier());
			cell = row.createCell(3);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getLegSequenceNumber());
			cell = row.createCell(4);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getCommercialCategory());
			cell = row.createCell(5);
			cell.setCellStyle(style7);
			String flightRecordStartPeriodOfValidity = schedule.getFlightLegs().get(i).getPeriodOfOperation().substring(0,7);
			cell.setCellValue(flightRecordStartPeriodOfValidity);
			cell = row.createCell(6);
			cell.setCellStyle(style7);
			String flightRecordEndPeriodOfValidity = schedule.getFlightLegs().get(i).getPeriodOfOperation().substring(7,14);
			cell.setCellValue(flightRecordEndPeriodOfValidity);
			cell = row.createCell(7);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getDayOfOperation());
			cell = row.createCell(8);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getDepartureStation());
			cell = row.createCell(9);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getPassengerSTD());
			cell = row.createCell(10);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getTrainSTD());
			cell = row.createCell(11);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getTimeVariationDeparture());
			cell = row.createCell(12);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getDepartureTerminal());
			cell = row.createCell(13);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getArrivalStation());
			cell = row.createCell(14);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getTrainSTA());
			cell = row.createCell(15);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getPassengerSTA());
			cell = row.createCell(16);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getTimeVariationArrival());
			cell = row.createCell(17);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getArrivalTerminal());
			cell = row.createCell(18);
			cell.setCellStyle(style7);
			cell.setCellValue(schedule.getFlightLegs().get(i).getServiceType());
			
			// Add a border to top of row if a new flight
			CellRangeAddress region = new CellRangeAddress(rowCounter, rowCounter, 0, 18);
			//if (!schedule.getFlightLegs().get(i).getFlightNumber().equals(lastFlightNumber))
			if (!schedule.getFlightLegs().get(i).getTrainNumber().equals(lastFlightNumber))
			{
				// Add top border
				RegionUtil.setBorderTop(BorderStyle.MEDIUM, region, convertedRadixxDataSheet);
			}
			//lastFlightNumber = schedule.getFlightLegs().get(i).getFlightNumber();
			lastFlightNumber = schedule.getFlightLegs().get(i).getTrainNumber();
			
			// Ignore "number as text" errors
			convertedRadixxDataSheet.addIgnoredErrors(region, IgnoredErrorType.NUMBER_STORED_AS_TEXT);

			rowCounter++;
		}

		CellRangeAddress region;

		// Add bottom border
		region = new CellRangeAddress(rowCounter - 1, rowCounter - 1, 0, 18);
		RegionUtil.setBorderBottom(BorderStyle.MEDIUM, region, convertedRadixxDataSheet);
		
		// Ignore "number stored as text" errors
		region = new CellRangeAddress(0, rowCounter, 0, 18);
		convertedRadixxDataSheet.addIgnoredErrors(region, IgnoredErrorType.NUMBER_STORED_AS_TEXT);

		// Trailer record is not decoded as all data in it is based on previous record types for single-carrier SSIM files. 
		row = convertedRadixxDataSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("***** DO NOT REMOVE *****");

		// Footer rows
		// Timestamp and footnote
		row = convertedRadixxDataSheet.createRow(rowCounter + 1);
		cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("1.  Company records are shown in gray.  Timetable record data is shown in yellow.");

		row = convertedRadixxDataSheet.createRow(rowCounter + 2);
		cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("2.  Time Variation Departure and Arrival may be preceeded by '-'.");

		row = convertedRadixxDataSheet.createRow(rowCounter + 3);
		cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("3.  Only data in gray and yellow will be converted to SSIM format.");

		row = convertedRadixxDataSheet.createRow(rowCounter + 4);
		cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("4.  Insert new rows in proper Train Number, Itinerary Variation Identifier and Leg Sequence Number order.");

		row = convertedRadixxDataSheet.createRow(rowCounter + 5);
		cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("Created on "+ConvertDateTime.getDateStamp()+ " at "+ConvertDateTime.getTimeStamp());

		// Resize all columns to fit the content size
		for (int i = 0; i <= 18; i++) 
		{
			if (i == 0)
			{
				convertedRadixxDataSheet.setColumnWidth(i, 5600);
			}
			else if (i == 4)
			{
				convertedRadixxDataSheet.setColumnWidth(i, 5300);
			}
			else if (i == 5)
			{
				convertedRadixxDataSheet.setColumnWidth(i, 3200);
			}
			else if (i == 1)
			{
				convertedRadixxDataSheet.autoSizeColumn(i);
			}
			else
				convertedRadixxDataSheet.setColumnWidth(i, 3100);
		}
	}

	public String getResultsMessageWrite1()
	{
		return resultsMessage;
	}
}