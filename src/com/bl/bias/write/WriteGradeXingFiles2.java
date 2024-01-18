package com.bl.bias.write;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.analyze.GradeXingAnalysis;
import com.bl.bias.app.BIASGradeXingSpeedsConfigController;
import com.bl.bias.objects.GradeXingAggregatedLink;
import com.bl.bias.tools.ConvertDateTime;

public class WriteGradeXingFiles2 extends WriteGradeXingFiles1
{
	private static ArrayList<GradeXingAggregatedLink> gradeXingLinks = new ArrayList<GradeXingAggregatedLink>();
	private static HashMap<String, String> nodeNames = new HashMap<String, String>();

	String resultsMessage = getResultsMessageWrite1();

	public WriteGradeXingFiles2(String textAreaContents, String fileAsString)
	{
		super(textAreaContents, fileAsString);

		if (BIASGradeXingSpeedsConfigController.getGenerateInconsisteneNodeNameSheet())
		{	
			XSSFSheet inconsistentNodeNamesSheet = workbook.createSheet("Inconsistent Node Names");

			nodeNames = GradeXingAnalysis.getNodeNames();
			gradeXingLinks = GradeXingAnalysis.getGradeXingLinks();

			// Set styles
			CellStyle style1 = workbook.createCellStyle();
			CellStyle style2 = workbook.createCellStyle();
			CellStyle style3 = workbook.createCellStyle();

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

			// Style 3 - Left, non-wrapped, 11pt, black text
			style1.setAlignment(HorizontalAlignment.LEFT);  
			style1.setWrapText(false);
			style1.setFont(font1);

			Row row;
			Cell cell;
			Integer rowCounter = 0;

			// Header
			row = inconsistentNodeNamesSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue("Node A"); // Node A
			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue("Node A Name"); // Node B
			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue("Node B");
			cell = row.createCell(3);
			cell.setCellStyle(style1);
			cell.setCellValue("Node B Name");
			rowCounter++;

			// For each link
			for (int i = 0; i < gradeXingLinks.size(); i++)
			{
				if (!nodeNames.get(gradeXingLinks.get(i).getNodeA()).equals(nodeNames.get(gradeXingLinks.get(i).getNodeB())))
				{
					row = inconsistentNodeNamesSheet.createRow(rowCounter);
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
			}

			if (rowCounter == 1)
			{
				row = inconsistentNodeNamesSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style3);
				cell.setCellValue("No inconsistent node pairs found!");
			}

			// Footer rows
			// Timestamp and footnote
			row = inconsistentNodeNamesSheet.createRow(rowCounter + 1);
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("Created on "+ConvertDateTime.getDateStamp()+ " at "+ConvertDateTime.getTimeStamp());

			// Resize all columns to fit the content size
			for (int i = 0; i <= 30; i++) 
			{
				if ((i == 0) || (i == 2))
				{
					inconsistentNodeNamesSheet.setColumnWidth(i, 3900);
				}
				else
					inconsistentNodeNamesSheet.setColumnWidth(i, 6000);
			}
		}
	}

	public String getResultsMessageWrite2()
	{
		return resultsMessage;
	}
}