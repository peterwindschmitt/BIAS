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

	public ReadExcelFileForBridgeCompliance(String file, Integer firstRowOfClosures, String dayColumn, String lowerColumn, String raiseColumn, String tenderColumn, String dateColumn, 
			String closureNumberColumn, String trainTypeColumn, String notesColumn, Integer lastRowOfClosures) throws Exception 
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
			int lastClosureNumber = 0;
			int lastDate = 0;
			double lastClosureEndTime = 0.0;
			
			// Load row-by-row checking for errors
			for (int i = firstRowOfClosures - 1; i <= lastRowOfClosures - 1; i++)
			{
				Integer rowNumber = sheet.getRow(i).getRowNum();
				String day = sheet.getRow(i).getCell(CellReference.convertColStringToIndex(dayColumn)).getStringCellValue();
				Double lowerTime = sheet.getRow(i).getCell(CellReference.convertColStringToIndex(lowerColumn)).getNumericCellValue();
				Double raiseTime = sheet.getRow(i).getCell(CellReference.convertColStringToIndex(raiseColumn)).getNumericCellValue();
				Integer closureNumber = (int) sheet.getRow(i).getCell(CellReference.convertColStringToIndex(closureNumberColumn)).getNumericCellValue();
				Integer date = (int) sheet.getRow(i).getCell(CellReference.convertColStringToIndex(dateColumn)).getNumericCellValue();
				String tender = sheet.getRow(i).getCell(CellReference.convertColStringToIndex(tenderColumn)).getStringCellValue();
				String trainType = sheet.getRow(i).getCell(CellReference.convertColStringToIndex(trainTypeColumn)).getStringCellValue();
				String closureNotes = sheet.getRow(i).getCell(CellReference.convertColStringToIndex(notesColumn)).getStringCellValue();
				
				if ((lowerTime < 0) || (lowerTime > 1))
				{
					resultsMessage += "Lower time in row "+(rowNumber+1)+" is invalid\n";
					validFile = false;
					break;
				}
				
				if ((raiseTime < 0) || (raiseTime > 1))
				{
					resultsMessage += "Raise time in row "+(rowNumber+1)+" is invalid\n";
					validFile = false;
					break;
				}
				
				if (!days.contains(day))
				{
					resultsMessage += "Day of week in row "+(rowNumber+1)+" is invalid\n";
					validFile = false;
					break;
				}
				
				if (((lastClosureNumber + 1) != closureNumber) && (i != (firstRowOfClosures - 1)))
				{
					resultsMessage += "Closure in row "+(rowNumber+1)+" is out of sequence\n";
					validFile = false;
					break;
				}
				else
					lastClosureNumber = closureNumber;
				
				if (((lowerTime < lastClosureEndTime) && (i != (firstRowOfClosures - 1))) && (lastDate == date))
				{
					resultsMessage += "Time in row "+(rowNumber+1)+" is out of sequence\n";
					validFile = false;
					break;
				}
				else
					lastClosureEndTime = raiseTime;
				
				if ((date < lastDate) && (i != (firstRowOfClosures - 1)))
				{
					resultsMessage += "Date in row "+(rowNumber+1)+" is out of sequence\n";
					validFile = false;
					break;
				}
				else
					lastDate = date;
				
				Boolean modifyDurationOfFirstClosure = false;
				Boolean modifyDurationOfLastClosure = false;
				
				// Determine if first closure duration needs to be modified
				if ((raiseTime < lowerTime) && (i == (firstRowOfClosures - 1)))
				{
					modifyDurationOfFirstClosure = true;
				}
				
				// Determine if last closure duration needs to be modified
				if ((raiseTime < lowerTime) && (i == (lastRowOfClosures - 1)))
				{
					modifyDurationOfLastClosure = true;
				}
				
				if (modifyDurationOfFirstClosure && modifyDurationOfLastClosure)
				{
					resultsMessage += "Time in row "+(rowNumber+1)+" is invalid\n";
					validFile = false;
					break;
				}
				
				BridgeComplianceClosure closure = new BridgeComplianceClosure(modifyDurationOfFirstClosure, modifyDurationOfLastClosure, rowNumber, closureNumber, date, lowerTime, raiseTime, tender, day, trainType, closureNotes);
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

	public String getDateSpan()
	{
		String earliestDate = ConvertDateTime.convertSerialToDate(closures.get(0).getClosureDate()).toString();
		String latestDate = ConvertDateTime.convertSerialToDate(closures.get(closures.size() - 1).getClosureDate()).toString();
		String dateSpan = (" [" + earliestDate + " to " + latestDate + "]");
		return dateSpan;
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