package com.bl.bias.write;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
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

import com.bl.bias.analyze.GtmAnalysis;
import com.bl.bias.app.BIASGtmConfigPageController;
import com.bl.bias.app.BIASGtmController;
import com.bl.bias.tools.ConvertDateTime;

public class WriteGTMFiles1 
{
	private static LocalTime startWriteFileTime = ConvertDateTime.getTimeStamp();
	protected static String resultsMessage1 = "\nStarted writing output file at "+startWriteFileTime;
	private static Boolean error = false;

	HashMap<String, Double> tonsByTypeHashMap = new HashMap<String, Double>();
	HashMap<String, Double> milesByTypeHashMap = new HashMap<String, Double>();

	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet gtmSheet;

	Integer rowCounter = 0;
	Double totalTrainMiles = 0.0;
	Double totalGtm = 0.0;

	DecimalFormat gtmdf = new DecimalFormat("#.#");
	DecimalFormat tmdf = new DecimalFormat("#.##");
	DecimalFormat percentdf = new DecimalFormat("#.#");

	public WriteGTMFiles1 (String textArea, String fileAsString)
	{
		tonsByTypeHashMap.putAll(GtmAnalysis.getTonsByTypeHashMap()); 
		milesByTypeHashMap.putAll(GtmAnalysis.getTrainMilesByTypeHashMap());

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
		gtmSheet = workbook.createSheet("Gross Ton Miles");
		gtmSheet.setDisplayGridlines(false);

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
		if (BIASGtmController.getUseCustomAssignments() 
				&& ((BIASGtmConfigPageController.getValidCustomAssignment1Exists().getValue().equals(true))
						|| (BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))))
			gtmSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		else
			gtmSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

		Row row;
		Cell cell;

		row = gtmSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style0);
		cell.setCellValue("Gross Ton Miles Analysis for "+fileAsString);

		// Data headers
		rowCounter++;
		rowCounter++;
		row = gtmSheet.createRow(rowCounter);

		cell = row.createCell(0);
		cell.setCellStyle(style6);
		cell.setCellValue("Line");

		cell = row.createCell(1);
		cell.setCellStyle(style6);
		cell.setCellValue("Type");

		cell = row.createCell(2);
		cell.setCellStyle(style7);
		cell.setCellValue("Train Miles");

		if ((BIASGtmController.getUseCustomAssignments())	 
				&& ((BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))
						|| (BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))))
		{
			cell = row.createCell(3);
			cell.setCellStyle(style7);
			cell.setCellValue("%");
		}


		if ((BIASGtmController.getUseCustomAssignments())	 
				&& ((BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))
						|| (BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))))
		{
			cell = row.createCell(4);
			cell.setCellStyle(style7);
			cell.setCellValue("Gross Ton Miles (*1,000)");
		}
		else
		{
			cell = row.createCell(3);
			cell.setCellStyle(style7);
			cell.setCellValue("Gross Ton Miles (*1,000)");
		}


		if ((BIASGtmController.getUseCustomAssignments())	 
				&& ((BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))
						|| (BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))))
		{
			cell = row.createCell(5);
			cell.setCellStyle(style7);
			cell.setCellValue("%");
		}			
		if ((BIASGtmController.getUseCustomAssignments())	 
				&& ((BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))
						|| (BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))))
		{
			cell = row.createCell(6);
			cell.setCellStyle(style7);
			cell.setCellValue("Operator");
		}		

		// By train type
		rowCounter++;
		for (int i = 0; i < tonsByTypeHashMap.size(); i++)
		{
			row = gtmSheet.createRow(rowCounter);

			// Line
			cell = row.createCell(0);
			cell.setCellStyle(style5);
			cell.setCellValue(BIASGtmController.getAnalyzedLine());

			// Type
			cell = row.createCell(1);
			cell.setCellStyle(style5);
			cell.setCellValue(tonsByTypeHashMap.keySet().toArray()[i].toString());

			// Train Miles
			Double trainMiles = milesByTypeHashMap.get(tonsByTypeHashMap.keySet().toArray()[i]);
			totalTrainMiles += trainMiles;
			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(tmdf.format(trainMiles));

			// Ton Miles
			if ((BIASGtmController.getUseCustomAssignments())	 
					&& ((BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))
							|| (BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))))
			{
				Double tonMiles = Math.round(tonsByTypeHashMap.get(tonsByTypeHashMap.keySet().toArray()[i])) / 1000.0;
				totalGtm += tonMiles;
				cell = row.createCell(4);
				cell.setCellStyle(style1);
				cell.setCellValue(gtmdf.format(tonMiles));
			}
			else
			{
				Double tonMiles = Math.round(tonsByTypeHashMap.get(tonsByTypeHashMap.keySet().toArray()[i])) / 1000.0;
				totalGtm += tonMiles;
				cell = row.createCell(3);
				cell.setCellStyle(style1);
				cell.setCellValue(gtmdf.format(tonMiles));
			}

			// Show user-defined groups
			if (BIASGtmController.getUseCustomAssignments() 
					&& ((BIASGtmConfigPageController.getValidCustomAssignment1Exists().getValue().equals(true))
							|| (BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))))
			{
				String typesToDisplay = "-";

				// If Assignment 1 and 2 exist
				if ((GtmAnalysis.getTypesinUserAssignments1AsHashSet().contains(tonsByTypeHashMap.keySet().toArray()[i].toString())) 
						&& (GtmAnalysis.getTypesinUserAssignments2AsHashSet().contains(tonsByTypeHashMap.keySet().toArray()[i].toString())))
				{
					typesToDisplay = BIASGtmConfigPageController.getUserCategory1Name().getValue();
					typesToDisplay += ", ";
					typesToDisplay = BIASGtmConfigPageController.getUserCategory2Name().getValue();
				}

				// If only Assignment 1
				if ((GtmAnalysis.getTypesinUserAssignments1AsHashSet().contains(tonsByTypeHashMap.keySet().toArray()[i].toString()))
						&& (!GtmAnalysis.getTypesinUserAssignments2AsHashSet().contains(tonsByTypeHashMap.keySet().toArray()[i].toString())))
				{
					typesToDisplay = BIASGtmConfigPageController.getUserCategory1Name().getValue();
				}				

				// If only Assignment 2
				if ((GtmAnalysis.getTypesinUserAssignments2AsHashSet().contains(tonsByTypeHashMap.keySet().toArray()[i].toString()))
						&& (!GtmAnalysis.getTypesinUserAssignments1AsHashSet().contains(tonsByTypeHashMap.keySet().toArray()[i].toString())))
				{
					typesToDisplay = BIASGtmConfigPageController.getUserCategory2Name().getValue();
				}

				cell = row.createCell(6);
				cell.setCellStyle(style1);
				cell.setCellValue(typesToDisplay);
			}
			rowCounter++;
		}

		// Totals by Type
		rowCounter++;
		row = gtmSheet.createRow(rowCounter);
		cell = row.createCell(1);
		cell.setCellStyle(style10);
		cell.setCellValue("Total");
		cell = row.createCell(2);
		cell.setCellStyle(style11);
		cell.setCellValue(tmdf.format(totalTrainMiles));

		if ((BIASGtmController.getUseCustomAssignments())	 
				&& ((BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))
						|| (BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))))
		{
			cell = row.createCell(4);
			cell.setCellStyle(style11);
			cell.setCellValue(gtmdf.format(totalGtm));
		}
		else
		{
			cell = row.createCell(3);
			cell.setCellStyle(style11);
			cell.setCellValue(gtmdf.format(totalGtm));
		}


		// User defined category totals
		if (BIASGtmController.getUseCustomAssignments()	&& (BIASGtmConfigPageController.getValidCustomAssignment1Exists().getValue().equals(true)))
		{
			rowCounter++;
			row = gtmSheet.createRow(rowCounter);
			cell = row.createCell(1);
			cell.setCellStyle(style10);
			cell.setCellValue(BIASGtmConfigPageController.getUserCategory1Name().getValue());
			
			cell = row.createCell(2);
			cell.setCellStyle(style11);
			cell.setCellValue(tmdf.format(GtmAnalysis.getTrainMilesUserAssignment1()));
			
			cell = row.createCell(3);
			cell.setCellStyle(style11);
			double trainMilesPercentageOperator1 = GtmAnalysis.getTrainMilesUserAssignment1()/totalTrainMiles;
			cell.setCellValue(percentdf.format(trainMilesPercentageOperator1 * 100).concat("%"));
			
			cell = row.createCell(4);
			cell.setCellStyle(style11);
			cell.setCellValue(gtmdf.format(GtmAnalysis.getTonMilesUserAssignment1()/1000));
			
			cell = row.createCell(5);
			cell.setCellStyle(style11);
			double tonMilesPercentageOperator1 = (GtmAnalysis.getTonMilesUserAssignment1()/1000)/totalGtm;
			cell.setCellValue(percentdf.format(tonMilesPercentageOperator1 * 100).concat("%"));
		}

		if (BIASGtmController.getUseCustomAssignments()	&& (BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true)))
		{
			rowCounter++;
			row = gtmSheet.createRow(rowCounter);
			cell = row.createCell(1);
			cell.setCellStyle(style10);
			cell.setCellValue(BIASGtmConfigPageController.getUserCategory2Name().getValue());
			
			cell = row.createCell(2);
			cell.setCellStyle(style11);
			cell.setCellValue(tmdf.format(GtmAnalysis.getTrainMilesUserAssignment2()));
			
			cell = row.createCell(3);
			cell.setCellStyle(style11);
			double trainMilesPercentageOperator2 = GtmAnalysis.getTrainMilesUserAssignment2()/totalTrainMiles;
			cell.setCellValue(percentdf.format(trainMilesPercentageOperator2 * 100).concat("%"));
			
			cell = row.createCell(4);
			cell.setCellStyle(style11);
			cell.setCellValue(gtmdf.format(GtmAnalysis.getTonMilesUserAssignment2()/1000));
			
			cell = row.createCell(5);
			cell.setCellStyle(style11);
			double tonMilesPercentageOperator2 = (GtmAnalysis.getTonMilesUserAssignment2()/1000)/totalGtm;
			cell.setCellValue(percentdf.format(tonMilesPercentageOperator2 * 100).concat("%"));
		}

		if ((BIASGtmController.getUseCustomAssignments())	 
				&& ((BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))
						|| (BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))))
		{
			rowCounter++;
			row = gtmSheet.createRow(rowCounter);
			cell = row.createCell(1);
			cell.setCellStyle(style10);
			cell.setCellValue("No Operator Defined");
			
			cell = row.createCell(2);
			cell.setCellStyle(style11);
			cell.setCellValue(tmdf.format(totalTrainMiles - GtmAnalysis.getTrainMilesUserAssignment1()- GtmAnalysis.getTrainMilesUserAssignment2()));
			
			cell = row.createCell(3);
			cell.setCellStyle(style11);
			double noOperatorTrainMiles = (totalTrainMiles - GtmAnalysis.getTrainMilesUserAssignment1()- GtmAnalysis.getTrainMilesUserAssignment2())/totalTrainMiles;
			cell.setCellValue(percentdf.format(noOperatorTrainMiles*100).concat("%"));
			
			cell = row.createCell(4);
			cell.setCellStyle(style11);
			cell.setCellValue(gtmdf.format(totalGtm - (GtmAnalysis.getTonMilesUserAssignment1()/1000) - (GtmAnalysis.getTonMilesUserAssignment2()/1000)));
			
			cell = row.createCell(5);
			cell.setCellStyle(style11);
			double noOperatorTonMiles = ((totalGtm - (GtmAnalysis.getTonMilesUserAssignment1()/1000) - (GtmAnalysis.getTonMilesUserAssignment2()/1000))/totalGtm)/1000;
			cell.setCellValue(percentdf.format(noOperatorTonMiles * 100).concat("%"));
		}

		// Timestamp and footnote
		LocalDate creationDate = ConvertDateTime.getDateStamp();
		LocalTime creationTime = ConvertDateTime.getTimeStamp();

		rowCounter++;
		rowCounter++;
		row = gtmSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("Created on "+creationDate+" at "+creationTime);

		// Resize all columns to fit the content size
		if ((BIASGtmController.getUseCustomAssignments())	 
				&& ((BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))
						|| (BIASGtmConfigPageController.getValidCustomAssignment2Exists().getValue().equals(true))))
		{
			for (int i = 0; i < 7; i++) 
			{
				if (i == 0)   // Line
				{
					gtmSheet.setColumnWidth(i, 8000);
				}
				else if (i == 1)   // Type
				{
					gtmSheet.setColumnWidth(i, 8000);
				}
				else if ((i == 2) || (i == 4))  // Miles and Tons
				{
					gtmSheet.setColumnWidth(i, 6000);
				}
				else if ((i == 3) || (i == 5))  // %
				{
					gtmSheet.setColumnWidth(i, 1300);
				}
				else if (i == 6) 
				{
					gtmSheet.setColumnWidth(i, 6000);
				}	
			}
		}
		else
		{
			for (int i = 0; i < 4; i++) 
			{
				if (i == 0)   // Line
				{
					gtmSheet.setColumnWidth(i, 8000);
				}
				else if (i == 1)   // Type
				{
					gtmSheet.setColumnWidth(i, 8000);
				}
				else if ((i == 2) || (i == 3))  // Miles and Tons
				{
					gtmSheet.setColumnWidth(i, 6000);
				}
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