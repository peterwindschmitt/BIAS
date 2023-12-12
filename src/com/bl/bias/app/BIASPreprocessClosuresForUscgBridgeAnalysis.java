
package com.bl.bias.app;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BIASPreprocessClosuresForUscgBridgeAnalysis 
{
	int firstRowOfData;
	int lastRowOfData;
	int lowerColumnIndex;
	int raiseColumnIndex;
	String lowerColumn;
	String raiseColumn;
	Boolean firstDataRowFound = false;
	Boolean lastDataRowFound = false;
	Boolean lowerColumnFound = false;
	Boolean raiseColumnFound = false;
	Boolean badRowFound = false;

	List<Object> bridgeFileDataLocations = new ArrayList<Object>();

	public BIASPreprocessClosuresForUscgBridgeAnalysis(String bridgeFile) throws Exception
	{
		
		int timeFoundInThisRowCounter;
		int examinationRow = 0;

		ArrayList<Integer> columnsContainingNumericInFirstRow = new ArrayList<>();
		ArrayList<Integer> columnsContainingNumericInLastRow = new ArrayList<>();

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
					switch (cellData.getCellType())
					{
					case NUMERIC:
						timeFoundInThisRowCounter++;
						columnsContainingNumericInFirstRow.add(i);
						if (timeFoundInThisRowCounter > 3)
						{
							firstDataRowFound = true;
							firstRowOfData = examinationRow;
							break;
						}

					default:
						continue;
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
							switch (cellData.getCellType())
							{
							case NUMERIC:
								timeFoundInThisRowCounter++;
								columnsContainingNumericInLastRow.add(i);
								if (timeFoundInThisRowCounter == columnsContainingNumericInFirstRow.size())
								{
									lastRowOfData = examinationRow;
									lastDataRowFound = true;
									break;
								}

							default:
								continue;
							}
						}
					}
					if (timeFoundInThisRowCounter <= 3)
					{
						badRowFound = true;
						break;
					}	
					
					examinationRow++;
				}
				while
					((examinationRow <= sheet.getLastRowNum()) && (badRowFound == false));
			}
		}
		
		if (firstDataRowFound && lastDataRowFound)
		{
			// Find column of lower times
			for (int i = 0; i < columnsContainingNumericInFirstRow.size(); i++)
			{
				Boolean thisColumnValidTime = true;
				for (int j = firstRowOfData; j <= lastRowOfData; j++)
				{
					Cell cellData = sheet.getRow(j).getCell(columnsContainingNumericInFirstRow.get(i));
					if ((cellData != null) && (cellData.toString() != ""))
					{	
						if ((cellData.getNumericCellValue() == 0) || (cellData.getNumericCellValue() >= 1))
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
					break;
				}		
			}
			
			// Find column of raise times
			for (int i = 0; i < columnsContainingNumericInFirstRow.size(); i++)
			{
				if (columnsContainingNumericInFirstRow.get(i) == lowerColumnIndex)
				{
					continue;
				}

				Boolean thisColumnValidTime = true;

				for (int j = firstRowOfData; j <= lastRowOfData; j++)
				{
					Cell cellData = sheet.getRow(j).getCell(columnsContainingNumericInFirstRow.get(i));
					if ((cellData != null) && (cellData.toString() != ""))
					{	
						if ((cellData.getNumericCellValue() == 0) || (cellData.getNumericCellValue() >= 1))
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
					break;
				}		
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
		
		if (lowerColumnFound)
			bridgeFileDataLocations.add(lowerColumn);
		else 
			bridgeFileDataLocations.add(null);
		
		if (raiseColumnFound)
			bridgeFileDataLocations.add(raiseColumn);
		else 
			bridgeFileDataLocations.add(null);

		return bridgeFileDataLocations;
	}
}