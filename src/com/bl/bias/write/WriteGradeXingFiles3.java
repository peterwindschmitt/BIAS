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
import com.bl.bias.objects.GradeXingRawLink;
import com.bl.bias.read.ReadGradeXingAnalysisFiles;
import com.bl.bias.tools.ConvertDateTime;

public class WriteGradeXingFiles3 extends WriteGradeXingFiles2
{
	private static ArrayList<GradeXingRawLink> gradeXingRawLinks = new ArrayList<GradeXingRawLink>();

	String resultsMessage = getResultsMessageWrite2();

	public WriteGradeXingFiles3(String textAreaContents, String fileAsString)
	{
		super(textAreaContents, fileAsString);

		if (BIASGradeXingSpeedsConfigController.getGenerateInconsisteneMaxSpeedSheet())
		{	
			XSSFSheet inconsistentMaxSpeedSheet = workbook.createSheet("Inconsistent Link Design Speeds");

			gradeXingRawLinks = ReadGradeXingAnalysisFiles.getGradeXingRawLinks();

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
			cell.setCellValue("A->B Passenger Max Design Speed");
			cell = row.createCell(3);
			cell.setCellStyle(style1);
			cell.setCellValue("B->A Passenger Max Design Speed");
			cell = row.createCell(4);
			cell.setCellStyle(style1);
			cell.setCellValue("A->B Through Max Design Speed");
			cell = row.createCell(5);
			cell.setCellStyle(style1);
			cell.setCellValue("B->A Through Max Design Speed");
			cell = row.createCell(6);
			cell.setCellStyle(style1);
			cell.setCellValue("A->B Local Max Design Speed");
			cell = row.createCell(7);
			cell.setCellStyle(style1);
			cell.setCellValue("B->A Local Max Design Speed");


			rowCounter++;

			// For each link
			for (int i = 0; i < gradeXingRawLinks.size(); i++)
			{
				for (int j = 0; j < gradeXingRawLinks.size(); j++)
				{
					if ((gradeXingRawLinks.get(i).getNodeA().equals(gradeXingRawLinks.get(j).getNodeB())) && 
							(gradeXingRawLinks.get(i).getNodeB().equals(gradeXingRawLinks.get(j).getNodeA())))
					{
						if ((!gradeXingRawLinks.get(i).getPasssengerSpeed().equals(gradeXingRawLinks.get(j).getPasssengerSpeed())) || 
								(!gradeXingRawLinks.get(i).getThroughSpeed().equals(gradeXingRawLinks.get(j).getThroughSpeed())) ||
								(!gradeXingRawLinks.get(i).getLocalSpeed().equals(gradeXingRawLinks.get(j).getLocalSpeed())))
						{
							row = inconsistentMaxSpeedSheet.createRow(rowCounter);
							cell = row.createCell(0);
							cell.setCellStyle(style1);
							cell.setCellValue(gradeXingRawLinks.get(i).getNodeA());
							cell = row.createCell(1);
							cell.setCellStyle(style1);
							cell.setCellValue(gradeXingRawLinks.get(i).getNodeB());
							cell = row.createCell(2);
							cell.setCellStyle(style1);
							cell.setCellValue(gradeXingRawLinks.get(i).getPasssengerSpeed());
							cell = row.createCell(4);
							cell.setCellStyle(style1);
							cell.setCellValue(gradeXingRawLinks.get(i).getThroughSpeed());
							cell = row.createCell(6);
							cell.setCellStyle(style1);
							cell.setCellValue(gradeXingRawLinks.get(i).getLocalSpeed());
							cell = row.createCell(3);
							cell.setCellStyle(style1);
							cell.setCellValue(gradeXingRawLinks.get(j).getPasssengerSpeed());
							cell = row.createCell(5);
							cell.setCellStyle(style1);
							cell.setCellValue(gradeXingRawLinks.get(j).getThroughSpeed());
							cell = row.createCell(7);
							cell.setCellStyle(style1);
							cell.setCellValue(gradeXingRawLinks.get(j).getLocalSpeed());

							rowCounter++;
							
							break;
						}
					}
				}
			}

			if (rowCounter == 1)
			{
				row = inconsistentMaxSpeedSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style1);
				cell.setCellValue("No inconsistent max design speeds found by link direction!");
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
				if (i < 2) 
				{
					inconsistentMaxSpeedSheet.setColumnWidth(i, 6000);
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