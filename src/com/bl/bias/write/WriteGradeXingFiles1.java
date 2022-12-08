package com.bl.bias.write;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.analyze.GradeXingSpeedsAnalysis;
import com.bl.bias.objects.GradeXingTraversal;
import com.bl.bias.read.ReadGradeXingAnalysisFiles;
import com.bl.bias.tools.ConvertDateTime;

public class WriteGradeXingFiles1
{
	protected String resultsMessage = "Started writing output file at "+ConvertDateTime.getTimeStamp();

	XSSFWorkbook workbook = new XSSFWorkbook();

	private static ArrayList<GradeXingTraversal> traversals = new ArrayList<GradeXingTraversal>();

	public WriteGradeXingFiles1(String textAreaContents, String fileAsString)
	{
		XSSFSheet gradeXingTraversalsSheet = workbook.createSheet("Grade Crossing Speeds");
		
		traversals = GradeXingSpeedsAnalysis.getSortedTraversals();
		
		// Set styles
		CellStyle style1 = workbook.createCellStyle();
		CellStyle style2 = workbook.createCellStyle();
		
		// Fonts
		// Font 1 - 11pt text
		XSSFFont font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 11);
		font1.setFontName("Calibri");

		// Font 2 - 8pt text
		XSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 8);
		font2.setFontName("Calibri");

		// Cell styles
		// Style 1 - Centered, wrapped, 11pt, black text
		style1.setAlignment(HorizontalAlignment.CENTER);  
		style1.setWrapText(true);
		style1.setFont(font1);

		// Style 2 - Left aligned, non-wrapped, 8pt, black text
		style2.setAlignment(HorizontalAlignment.LEFT);  
		style2.setWrapText(false);
		style2.setFont(font2);

		Row row;
		Cell cell;
		Integer rowCounter = 0;
		
		// Header
		row = gradeXingTraversalsSheet.createRow(rowCounter);
		cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("Field MP of Node A"); // Node A
		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell.setCellValue("Field MP of Node B"); // Node B
		cell = row.createCell(2);
		cell.setCellStyle(style1);
		cell.setCellValue("Crossing Name");
		cell = row.createCell(3);
		cell.setCellStyle(style1);
		cell.setCellValue("Max Design Speed");
		cell = row.createCell(4);
		cell.setCellStyle(style1);
		cell.setCellValue("Min Design Speed");
		cell = row.createCell(5);
		cell.setCellStyle(style1);
		cell.setCellValue("Max Anticipated Speed");
		cell = row.createCell(6);
		cell.setCellStyle(style1);
		cell.setCellValue("Min Anticipated Speed");
		rowCounter++;
		
		// For each traversal
		for (int i = 0; i < traversals.size(); i++)
		{
			row = gradeXingTraversalsSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(traversals.get(i).getNodeAFieldMP());
			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue(traversals.get(i).getNodeBFieldMP());
			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(traversals.get(i).getNodeAName());
			cell = row.createCell(3);
			cell.setCellStyle(style1);
			cell.setCellValue(traversals.get(i).getHighestDesignSpeed());
			cell = row.createCell(4);
			cell.setCellStyle(style1);
			cell.setCellValue(traversals.get(i).getLowestDesignSpeed());
			cell = row.createCell(5);
			cell.setCellStyle(style1);
			cell.setCellValue(traversals.get(i).getHighestObservedSpeed());
			cell = row.createCell(6);
			cell.setCellStyle(style1);
			cell.setCellValue(traversals.get(i).getLowestObservedSpeed());
			
			rowCounter++;
		}
		
		// Footer rows
		// Timestamp and footnote
		row = gradeXingTraversalsSheet.createRow(rowCounter + 1);
		cell = row.createCell(0);
		cell.setCellStyle(style2);
		cell.setCellValue("Created on "+ConvertDateTime.getDateStamp()+ " at "+ConvertDateTime.getTimeStamp());

		// Resize all columns to fit the content size
		for (int i = 0; i <= 30; i++) 
		{
			if ((i == 0) || (i == 1))
			{
				gradeXingTraversalsSheet.setColumnWidth(i, 3900);
			}
			else if (i == 2)
			{
				gradeXingTraversalsSheet.setColumnWidth(i, 6600);
			}
			else
				gradeXingTraversalsSheet.setColumnWidth(i, 3300);
		}
	}

	public String getResultsMessageWrite1()
	{
		return resultsMessage;
	}
}