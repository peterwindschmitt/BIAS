
package com.bl.bias.app;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BIASPreprocessClosuresForUscgBridgeAnalysis 
{
	int firstRowOfData;
	int lastRowOfData;
	int dayColumnIndex;
	int lowerColumnIndex;
	int raiseColumnIndex;
	int tenderColumnIndex;
	int dateColumnIndex;
	int closingNumberColumnIndex;
	int trainTypeColumnIndex;
	int notesColumnIndex;

	String dayColumn;
	String lowerColumn;
	String raiseColumn;
	String tenderColumn;
	String dateColumn;
	String closingNumberColumn;
	String trainTypeColumn;
	String notesColumn;

	Boolean firstDataRowFound = false;
	Boolean lastDataRowFound = false;
	Boolean dayColumnFound = false;
	Boolean lowerColumnFound = false;
	Boolean raiseColumnFound = false;
	Boolean tenderColumnFound = false;
	Boolean dateColumnFound = false;
	Boolean closingNumberColumnFound = false;
	Boolean trainTypeColumnFound = false;
	Boolean notesColumnFound = false;
	Boolean badRowFound = false;

	List<Object> bridgeFileDataLocations = new ArrayList<Object>();

	public BIASPreprocessClosuresForUscgBridgeAnalysis(String bridgeFile) throws Exception
	{

		int timeFoundInThisRowCounter;
		int examinationRow = 0;
		int headerRow = 0;

		ArrayList<Integer> columnsContainingNumericInFirstRow = new ArrayList<>();
		ArrayList<Integer> columnsContainingNumericInLastRow = new ArrayList<>();
		ArrayList<Integer> columnsContainingStringInFirstRow = new ArrayList<>();
		HashSet<String> days = new HashSet<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

		// Check for columns and rows only.  All other checks will occur in read class.
		// Open file
		FileInputStream fis = new FileInputStream(new File(bridgeFile));

		// Get workbook  
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		// Get first sheet  
		XSSFSheet sheet = wb.getSheetAt(0);

		// Find first row of data
		examinationRow = sheet.getFirstRowNum();
		do
		{
			timeFoundInThisRowCounter = 0;
			columnsContainingNumericInFirstRow.clear();
			for (int i = 0; i < sheet.getRow(examinationRow).getLastCellNum(); i++)
			{
				Cell cellData = sheet.getRow(examinationRow).getCell(i);
				if ((cellData != null) && (cellData.toString() != ""))
				{	
					if (cellData.getCellType() == CellType.NUMERIC)
					{
						timeFoundInThisRowCounter++;
						columnsContainingNumericInFirstRow.add(i);
						if (timeFoundInThisRowCounter > 3)
						{
							firstDataRowFound = true;
							firstRowOfData = examinationRow;
						}
					}
				}
			}
			examinationRow++;
		}
		while
			(examinationRow <= sheet.getLastRowNum() && (firstDataRowFound == false));

		// Find last row of data
		if ((examinationRow - 1) != sheet.getLastRowNum() && (firstDataRowFound))
		{
			if (columnsContainingNumericInFirstRow.size() > 0)
			{
				lastDataRowFound = false;
				do
				{
					timeFoundInThisRowCounter = 0;
					columnsContainingNumericInLastRow.clear();

					for (int i = 0; i < sheet.getRow(examinationRow).getLastCellNum(); i++)
					{
						Cell cellData = sheet.getRow(examinationRow).getCell(i);
						if ((cellData != null) && (cellData.toString() != "") && (columnsContainingNumericInFirstRow.contains(i)))
						{	
							if (cellData.getCellType() == CellType.NUMERIC)
							{
								timeFoundInThisRowCounter++;
								columnsContainingNumericInLastRow.add(i);
								if (timeFoundInThisRowCounter == columnsContainingNumericInFirstRow.size())
								{
									lastRowOfData = examinationRow;
									lastDataRowFound = true;
									break;
								}
							}
						}
					}


					if (timeFoundInThisRowCounter <= 3)
					{
						badRowFound = true;
					}	

					examinationRow++;
				}
				while
					((examinationRow <= sheet.getLastRowNum()) && (badRowFound == false));
			}
		}


		// Find header row of data
		for (int i = firstRowOfData; i >= 0; i--)
		{
			for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++)
			{
				Cell cellData = sheet.getRow(i).getCell(j);
				if ((cellData != null) && (cellData.toString() != ""))
				{	
					if (cellData.getCellType() == CellType.STRING)
					{
						if (cellData.getStringCellValue().trim().toLowerCase().contains("day of week"))
						{
							headerRow = i;
							break;
						}
					}
				}
			}
		}
		
		// Find STRING occurences in first row
		columnsContainingStringInFirstRow.clear();
		for (int i = 0; i < sheet.getRow(firstRowOfData).getLastCellNum(); i++)
		{
			Cell cellData = sheet.getRow(firstRowOfData).getCell(i);
			if (cellData.getCellType() == CellType.STRING)
				columnsContainingStringInFirstRow.add(i);
		}

		if (firstDataRowFound && lastDataRowFound)
		{
			// Find column of days
			for (int i = 0; i < columnsContainingStringInFirstRow.size(); i++)
			{
				Cell cellDataHeader = sheet.getRow(headerRow).getCell(columnsContainingStringInFirstRow.get(i));
				Cell cellData = sheet.getRow(firstRowOfData).getCell(columnsContainingStringInFirstRow.get(i));
				if ((cellData != null) && (cellData.toString() != ""))
				{	
					if (cellDataHeader.getStringCellValue().toLowerCase().contains("day")) 
					{
						dayColumnIndex = columnsContainingStringInFirstRow.get(i);
						dayColumnFound = true;
						dayColumn = CellReference.convertNumToColString(dayColumnIndex);
						columnsContainingStringInFirstRow.remove(i);
						break;
					}
					else if (days.contains(cellData.getStringCellValue())) 
					{
						dayColumnIndex = columnsContainingStringInFirstRow.get(i);
						dayColumnFound = true;
						dayColumn = CellReference.convertNumToColString(dayColumnIndex);
						columnsContainingStringInFirstRow.remove(i);
						break;
					}					
				}
			}

			// Find column of lower times
			for (int i = 0; i < columnsContainingNumericInFirstRow.size(); i++)
			{
				Boolean thisColumnValidTime = true;
				Cell cellDataHeader = sheet.getRow(headerRow).getCell(columnsContainingNumericInFirstRow.get(i));
				for (int j = firstRowOfData; j <= lastRowOfData; j++)
				{
					Cell cellData = sheet.getRow(j).getCell(columnsContainingNumericInFirstRow.get(i));
					if ((cellDataHeader.getStringCellValue().toLowerCase().contains("closing")) && (cellData.getNumericCellValue() < 1))
					{
						break;
					}
					else if ((cellData != null) && (cellData.toString() != ""))
					{	
						if (cellData.getNumericCellValue() >= 1)
						{
							thisColumnValidTime = false;
							break;
						}					
					}
				}

				if (thisColumnValidTime)
				{
					lowerColumnIndex = columnsContainingNumericInFirstRow.get(i);
					lowerColumn = CellReference.convertNumToColString(columnsContainingNumericInFirstRow.get(i));
					lowerColumnFound = true;
					columnsContainingNumericInFirstRow.remove(i);
					break;
				}		
			}

			// Find column of raise times
			for (int i = 0; i < columnsContainingNumericInFirstRow.size(); i++)
			{
				Boolean thisColumnValidTime = true;
				Cell cellDataHeader = sheet.getRow(headerRow).getCell(columnsContainingNumericInFirstRow.get(i));
				for (int j = firstRowOfData; j <= lastRowOfData; j++)
				{
					Cell cellData = sheet.getRow(j).getCell(columnsContainingNumericInFirstRow.get(i));
					if (cellDataHeader.getStringCellValue().toLowerCase().contains("opening")) 
					{
						break;
					}
					else if ((cellData != null) && (cellData.toString() != ""))
					{	
						if (cellData.getNumericCellValue() >= 1)
						{
							thisColumnValidTime = false;
							break;
						}					
					}
					else
						thisColumnValidTime = false;
				}

				if (thisColumnValidTime)
				{
					raiseColumnIndex = columnsContainingNumericInFirstRow.get(i);
					raiseColumn = CellReference.convertNumToColString(columnsContainingNumericInFirstRow.get(i));
					raiseColumnFound = true;
					columnsContainingNumericInFirstRow.remove(i);
					break;
				}		
			}

			// Find column of tenders
			for (int i = 0; i < sheet.getRow(headerRow).getLastCellNum(); i++)
			{
				Cell cellDataHeader = sheet.getRow(headerRow).getCell(i);
				if (cellDataHeader.getStringCellValue().toLowerCase().contains("tender")) 
				{
					tenderColumnIndex = i;
					tenderColumnFound = true;
					tenderColumn = CellReference.convertNumToColString(tenderColumnIndex);				
					break;
				}	
			}

			// Find column of dates
			for (int i = 0; i < columnsContainingNumericInFirstRow.size(); i++)
			{
				Cell cellDataHeader = sheet.getRow(headerRow).getCell(columnsContainingNumericInFirstRow.get(i));
				if (cellDataHeader.getStringCellValue().toLowerCase().contains("date")) 
				{
					dateColumnIndex = columnsContainingNumericInFirstRow.get(i);
					dateColumnFound = true;
					dateColumn = CellReference.convertNumToColString(dateColumnIndex);
					columnsContainingNumericInFirstRow.remove(i);
					break;
				}	
			}
		}

		// Find column of closing numbers
		for (int i = 0; i < columnsContainingNumericInFirstRow.size(); i++)
		{
			Boolean thisColumnValidClosing = true;
			Cell cellDataHeader = sheet.getRow(headerRow).getCell(columnsContainingNumericInFirstRow.get(i));
			for (int j = firstRowOfData; j <= lastRowOfData; j++)
			{
				Cell cellData = sheet.getRow(j).getCell(columnsContainingNumericInFirstRow.get(i));
				if ((cellDataHeader.getStringCellValue().toLowerCase().contains("closing")) && (cellData.getNumericCellValue() >= 1))
				{
					break;
				}
				else if ((cellData != null) && (cellData.toString() != ""))
				{	
					if (cellData.getNumericCellValue() >= 1)
					{
						thisColumnValidClosing = false;
						break;
					}					
				}
			}

			if (thisColumnValidClosing)
			{
				closingNumberColumnIndex = columnsContainingNumericInFirstRow.get(i);
				closingNumberColumn = CellReference.convertNumToColString(columnsContainingNumericInFirstRow.get(i));
				closingNumberColumnFound = true;
				columnsContainingNumericInFirstRow.remove(i);
				break;
			}		
		}

		// Find column of train types
		for (int i = 0; i < sheet.getRow(headerRow).getLastCellNum(); i++)
		{
			Cell cellDataHeader = sheet.getRow(headerRow).getCell(i);
			if ((cellDataHeader.getStringCellValue().toLowerCase().contains("train")) && (cellDataHeader.getStringCellValue().toLowerCase().contains("type")))
			{
				trainTypeColumnIndex = i;
				trainTypeColumnFound = true;
				trainTypeColumn = CellReference.convertNumToColString(trainTypeColumnIndex);
				break;
			}	
		}

		// Find column of notes
		for (int i = 0; i < sheet.getRow(headerRow).getLastCellNum(); i++)
		{
			Cell cellDataHeader = sheet.getRow(headerRow).getCell(i);
			if (cellDataHeader.getStringCellValue().toLowerCase().contains("comments"))
			{
				notesColumnIndex = i;
				notesColumnFound = true;
				notesColumn = CellReference.convertNumToColString(notesColumnIndex);
				break;
			}	
		}

		wb.close();
	}

	public List<Object> returnBridgeFileDataLocations()
	{
		if (firstDataRowFound)
			bridgeFileDataLocations.add(firstRowOfData);
		else 
			bridgeFileDataLocations.add(null);

		if (lastDataRowFound)
			bridgeFileDataLocations.add(lastRowOfData);
		else 
			bridgeFileDataLocations.add(null);

		if (dayColumnFound)
			bridgeFileDataLocations.add(dayColumn);
		else 
			bridgeFileDataLocations.add(null);

		if (lowerColumnFound)
			bridgeFileDataLocations.add(lowerColumn);
		else 
			bridgeFileDataLocations.add(null);

		if (raiseColumnFound)
			bridgeFileDataLocations.add(raiseColumn);
		else 
			bridgeFileDataLocations.add(null);

		if (tenderColumnFound)
			bridgeFileDataLocations.add(tenderColumn);
		else 
			bridgeFileDataLocations.add(null);

		if (dateColumnFound)
			bridgeFileDataLocations.add(dateColumn);
		else 
			bridgeFileDataLocations.add(null);

		if (closingNumberColumnFound)
			bridgeFileDataLocations.add(closingNumberColumn);
		else 
			bridgeFileDataLocations.add(null);

		if (trainTypeColumnFound)
			bridgeFileDataLocations.add(trainTypeColumn);
		else 
			bridgeFileDataLocations.add(null);

		if (notesColumnFound)
			bridgeFileDataLocations.add(notesColumn);
		else 
			bridgeFileDataLocations.add(null);

		return bridgeFileDataLocations;
	}
}