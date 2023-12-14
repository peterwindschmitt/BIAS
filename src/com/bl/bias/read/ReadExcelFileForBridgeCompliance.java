package com.bl.bias.read;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.BridgeComplianceClosure;
import com.bl.bias.tools.ConvertDateTime;

public class ReadExcelFileForBridgeCompliance 
{
	private String resultsMessage;

	private Boolean validFile = true;

	private Integer closuresReadCount = 0;

	HashSet<String> days = new HashSet<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

	private ArrayList<BridgeComplianceClosure> closures = new ArrayList<BridgeComplianceClosure>();

	public ReadExcelFileForBridgeCompliance(String file, Integer firstRowOfClosures, String dayColumn, String lowerColumn, String raiseColumn, Integer lastRowOfClosures) throws Exception 
	{
		closures.clear();

		resultsMessage = "\nStarted parsing Excel file at "+ConvertDateTime.getTimeStamp()+"\n";

		// Open file
		FileInputStream fis = new FileInputStream(new File(file));

		// Get workbook  
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		// Get first sheet  
		XSSFSheet sheet = wb.getSheetAt(0);

		try
		{
			// Load row-by-row checking for errors
			for (int i = firstRowOfClosures - 1; i <= lastRowOfClosures - 1; i++)
			{
				Integer rowNumber = sheet.getRow(i).getRowNum();
				String day = sheet.getRow(i).getCell(CellReference.convertColStringToIndex(dayColumn)).getStringCellValue();
				Double lowerTime = sheet.getRow(i).getCell(CellReference.convertColStringToIndex(lowerColumn)).getNumericCellValue();
				Double raiseTime = sheet.getRow(i).getCell(CellReference.convertColStringToIndex(raiseColumn)).getNumericCellValue();

				if ((lowerTime < 0) || (lowerTime > 1))
				{
					resultsMessage += "Lower time in row "+(rowNumber+1)+" is invalid\n";
					validFile = false;
				}
				
				if ((raiseTime < 0) || (raiseTime > 1))
				{
					resultsMessage += "Raise time in row "+(rowNumber+1)+" is invalid\n";
					validFile = false;
				}
				
				if (!days.contains(day))
				{
					resultsMessage += "Day of week in row "+(rowNumber+1)+" is invalid\n";
					validFile = false;
				}
				
				BridgeComplianceClosure closure = new BridgeComplianceClosure(rowNumber, null, null, lowerTime, raiseTime, null, day, null, null);
				closures.add(closure);
				closuresReadCount++;
			}
		}
		catch (Exception e)
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
			resultsMessage += "Error found reading closures from spreadsheet.\n";
			validFile = false;
		}
		finally
		{
			wb.close();
		}
		
		resultsMessage += "Read "+closuresReadCount+" closures from spreadsheet \n";
		resultsMessage += "Finished parsing Excel file at "+ConvertDateTime.getTimeStamp()+"\n";
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}

	public Boolean getValidFile()
	{
		return validFile;
	}

	public ArrayList<BridgeComplianceClosure> getClosures()
	{
		return closures;
	}
}