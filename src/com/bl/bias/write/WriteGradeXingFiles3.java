package com.bl.bias.write;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.analyze.GradeXingSpeedsAnalysis;
import com.bl.bias.app.BIASGradeXingSpeedsConfigController;
import com.bl.bias.objects.GradeXingLink;
import com.bl.bias.tools.ConvertDateTime;

public class WriteGradeXingFiles3 extends WriteGradeXingFiles2
{
	private static ArrayList<GradeXingLink> gradeXingLinks = new ArrayList<GradeXingLink>();
	
	String resultsMessage = getResultsMessageWrite2();

	public WriteGradeXingFiles3(String textAreaContents, String fileAsString)
	{
		super(textAreaContents, fileAsString);

		if (BIASGradeXingSpeedsConfigController.getGenerateInconsisteneMaxSpeedSheet())
		{	
			XSSFSheet inconsistentMaxSpeedSheet = workbook.createSheet("Inconsistent Max Link Speeds");

			gradeXingLinks = GradeXingSpeedsAnalysis.getGradeXingLinks();

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
			row = inconsistentMaxSpeedSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue("Node A"); // Node A
			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("Node B"); // Node B
			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue("A->B Max Passenger Speed");
			cell = row.createCell(3);
			cell.setCellStyle(style1);
			cell.setCellValue("B->A Max Passenger Speed");
			cell = row.createCell(4);
			cell.setCellStyle(style1);
			cell.setCellValue("A->B Max Through Speed");
			cell = row.createCell(5);
			cell.setCellStyle(style1);
			cell.setCellValue("B->A Max Through Speed");
			cell = row.createCell(6);
			cell.setCellStyle(style1);
			cell.setCellValue("A->B Max Local Speed");
			cell = row.createCell(7);
			cell.setCellStyle(style1);
			cell.setCellValue("B->A Max Local Speed");
			rowCounter++;

			/* For each link
			for (int i = 0; i < gradeXingLinks.size(); i++)
			{
				if (!nodeNames.get(gradeXingLinks.get(i).getNodeA()).equals(nodeNames.get(gradeXingLinks.get(i).getNodeB())))
				{
					row = inconsistentMaxSpeedSheet.createRow(rowCounter);
					cell = row.createCell(0);
					cell.setCellStyle(style1);
					cell.setCellValue(gradeXingLinks.get(i).getNodeA());
					cell = row.createCell(1);
					cell.setCellStyle(style1);
					cell.setCellValue(nodeNames.get(gradeXingLinks.get(i).getNodeA()));
					cell = row.createCell(2);
					cell.setCellStyle(style1);
					cell.setCellValue(gradeXingLinks.get(i).getNodeB());
					cell = row.createCell(3);
					cell.setCellStyle(style1);
					cell.setCellValue(nodeNames.get(gradeXingLinks.get(i).getNodeB()));
					rowCounter++;
				}
			}*/

			if (rowCounter == 1)
			{
				row = inconsistentMaxSpeedSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style1);
				cell.setCellValue("No inconsistent max speeds found by link direction!");
			}

			// Footer rows
			// Timestamp and footnote
			row = inconsistentMaxSpeedSheet.createRow(rowCounter + 1);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("Created on "+ConvertDateTime.getDateStamp()+ " at "+ConvertDateTime.getTimeStamp());

			// Resize all columns to fit the content size
			for (int i = 0; i <= 30; i++) 
			{
				if ((i == 0) || (i == 1))
				{
					inconsistentMaxSpeedSheet.setColumnWidth(i, 6000);
				}
				else if ((i == 6) || (i == 7))
				{
					inconsistentMaxSpeedSheet.setColumnWidth(i, 2800);
				}
				else
					inconsistentMaxSpeedSheet.setColumnWidth(i, 3000);
			}
		}
	}

	public String getResultsMessageWrite3()
	{
		return resultsMessage;
	}
}