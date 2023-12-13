package com.bl.bias.read;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.tools.ConvertDateTime;

public class ReadExcelFileForBridgeCompliance 
{
	private String resultsMessage;
	
	private Boolean validFile = true;

	private Integer objectCount = 0;

	public ReadExcelFileForBridgeCompliance(String file, Integer firstRowOfClosures, String lowerColumn, String raiseColumn, Integer lastRowOfClosures) throws Exception 
	{
		resultsMessage = "\nStarted parsing Excel file at "+ConvertDateTime.getTimeStamp()+"\n";

		// Open file
		FileInputStream fis = new FileInputStream(new File(file));

		// Get workbook  
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		// Get first sheet  
		XSSFSheet sheet = wb.getSheetAt(0);

		// Load row-by-row checking for errors
		for (int i = firstRowOfClosures - 1; i <= lastRowOfClosures - 1; i++)
		{
			Cell cellData = sheet.getRow(i).getCell(CellReference.convertColStringToIndex(lowerColumn) - 1);
			objectCount++;
			System.out.println("Cycle: "+cellData);
		}
		
		resultsMessage += "Read "+objectCount+" objects from spreadsheet \n";
		resultsMessage += "Finished parsing Excel file at "+ConvertDateTime.getTimeStamp()+"\n\n";
		
		//else
		//{
		//	validFile = false;
		//	resultsMessage += "Error in reading objects from spreadsheet";
		//}
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}

	public Boolean getValidFile()
	{
		return validFile;
	}
}