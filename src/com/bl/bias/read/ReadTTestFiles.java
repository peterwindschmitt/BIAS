package com.bl.bias.read;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.SampleForTTest;

public class ReadTTestFiles 
{
	String message;
	
	private ArrayList<SampleForTTest> byTypeDataA;
	private ArrayList<SampleForTTest> byGroupDataA;
	private ArrayList<SampleForTTest> byTypeDataB;
	private ArrayList<SampleForTTest> byGroupDataB;
	
	private HashSet<String> lines = new HashSet<String>();
	private HashSet<String> categoriesForTypes = new HashSet<String>();
	private HashSet<String> categoriesForGroups = new HashSet<String>();
	private HashSet<String> filesA = new HashSet<String>();
	private HashSet<String> filesB = new HashSet<String>();
	
	private Boolean error = false;
		
	public Boolean read(String fullyQualifiedPathA, String fullyQualifiedPathB, Boolean generateVelocity, Boolean generateTrueDelayMinutesPer100TrainMiles, Boolean generateElapsedRunTimePerTrain, Boolean generateOtp, Boolean generateType, Boolean generateGroup)
	{
		LocalTime startReadFilesTime = LocalTime.now();
		message = "\nStarted reading files at "+startReadFilesTime;

		// Load data from fileA
		Workbook wbA = null;
		
		try 
		{
			wbA = WorkbookFactory.create(new File(fullyQualifiedPathA));
		} 
		catch (EncryptedDocumentException e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		catch (IOException e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		
		for (int i=0; i < wbA.getNumberOfSheets(); i++)  // by sheet
		{
			if (wbA.getSheetAt(i).getSheetName().equals("Trains by Type Raw Data"))
			{
				byTypeDataA = new ArrayList<SampleForTTest>();
				
				for (int j = 1; j <= wbA.getSheetAt(i).getLastRowNum(); j++)  // by row
				{
					String file = null;
					String line = null;
					String category = null;
					double velocity = 0;
					double delayMinutesPer100TM = 0;
					String elapsedRunTimePerTrainAsString = null;
					double elapsedRunTimePerTrainInSeconds = 0;
					double elapsedRunTimePerTrainAsSerialTime = 0;
					String otp = null;
					
					for (int k = 0; k < wbA.getSheetAt(i).getRow(0).getPhysicalNumberOfCells(); k++)  // by col
					{
						if (wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("File"))
						{
							file = wbA.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
							filesA.add(file);
						}
						else if (wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Line/Subdivision"))
						{
							line = wbA.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
							lines.add(line);
						}
						else if (wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Train Type"))
						{
							category = wbA.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
							categoriesForTypes.add(category);
						}
						else if ((wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Avg Speed")) && (generateVelocity))
						{
							velocity = wbA.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("True Delay Minutes per 100TM")) && (generateTrueDelayMinutesPer100TrainMiles))
						{
							delayMinutesPer100TM = wbA.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Elapsed Time Per Train as String by Train Type")) && (generateElapsedRunTimePerTrain))
						{
							elapsedRunTimePerTrainAsString = wbA.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
						}
						else if ((wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Elapsed Time Per Train in Seconds by Train Type")) && (generateElapsedRunTimePerTrain))
						{
							elapsedRunTimePerTrainInSeconds = wbA.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Elapsed Time Per Train as Serial Date/Time by Train Type")) && (generateElapsedRunTimePerTrain))
						{
							elapsedRunTimePerTrainAsSerialTime = wbA.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("OTP")) && (generateOtp))
						{	
							if (wbA.getSheetAt(i).getRow(j).getCell(k).getCellType() == CellType.NUMERIC)
							{
								otp = String.valueOf(wbA.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue());
							}
							else
							{
								otp = "0";
							}
						}
					}
					byTypeDataA.add(new SampleForTTest(line, category, velocity, delayMinutesPer100TM, elapsedRunTimePerTrainAsString, elapsedRunTimePerTrainInSeconds, elapsedRunTimePerTrainAsSerialTime, otp));
				}
				message += "\nRead " + byTypeDataA.size() + " type records from Case A";
			}
			else if (wbA.getSheetAt(i).getSheetName().equals("Trains by Group Raw Data"))
			{
				byGroupDataA = new ArrayList<SampleForTTest>();
				
				for (int j = 1; j <= wbA.getSheetAt(i).getLastRowNum(); j++)  // by row
				{
					String file = null;
					String line = null;
					String category = null;
					double velocity = 0;
					double delayMinutesPer100TM = 0;
					String elapsedRunTimePerTrainAsString = null;
					double elapsedRunTimePerTrainInSeconds = 0;
					double elapsedRunTimePerTrainAsSerialTime = 0;
					String otp = null;

					for (int k = 0; k < wbA.getSheetAt(i).getRow(0).getPhysicalNumberOfCells(); k++)  // by col
					{
						if (wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("File"))
						{
							file = wbA.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
							filesA.add(file);
						}
						else if (wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Line/Subdivision"))
						{
							line = wbA.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
							lines.add(line);
						}
						else if (wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Train Group"))
						{
							category = wbA.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
							categoriesForGroups.add(category);
						}
						else if ((wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Avg Speed")) && (generateVelocity))
						{
							velocity = wbA.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("True Delay Minutes per 100TM")) && (generateTrueDelayMinutesPer100TrainMiles))
						{
							delayMinutesPer100TM = wbA.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Elapsed Time Per Train as String by Train Group")) && (generateElapsedRunTimePerTrain))
						{
							elapsedRunTimePerTrainAsString = wbA.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
						}
						else if ((wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Elapsed Time Per Train in Seconds by Train Group")) && (generateElapsedRunTimePerTrain))
						{
							elapsedRunTimePerTrainInSeconds = wbA.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Elapsed Time Per Train as Serial Date/Time by Train Group")) && (generateElapsedRunTimePerTrain))
						{
							elapsedRunTimePerTrainAsSerialTime = wbA.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbA.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("OTP")) && (generateOtp))
						{	
							if (wbA.getSheetAt(i).getRow(j).getCell(k).getCellType() == CellType.NUMERIC)
							{
								otp = String.valueOf(wbA.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue());
							}
							else
							{
								otp = "0";
							}
						}
					}
					byGroupDataA.add(new SampleForTTest(line, category, velocity, delayMinutesPer100TM, elapsedRunTimePerTrainAsString, elapsedRunTimePerTrainInSeconds, elapsedRunTimePerTrainAsSerialTime, otp));
				}
				message += "\nRead " + byGroupDataA.size() + " group records from Case A";
			}
		}

		try 
		{
			wbA.close();
		} 
		catch (IOException e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		
		// Load data from fileB
		Workbook wbB = null;
		
		try 
		{
			wbB = WorkbookFactory.create(new File(fullyQualifiedPathB));
		} 
		catch (EncryptedDocumentException e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		catch (IOException e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}

		for (int i=0; i < wbB.getNumberOfSheets(); i++)  // by sheet
		{
			if (wbB.getSheetAt(i).getSheetName().equals("Trains by Type Raw Data"))
			{
				byTypeDataB = new ArrayList<SampleForTTest>();
				
				for (int j = 1; j <= wbB.getSheetAt(i).getLastRowNum(); j++)  // by row
				{
					String file = null;
					String line = null;
					String category = null;
					double velocity = 0;
					double delayMinutesPer100TM = 0;
					String elapsedRunTimePerTrainAsString = null;
					double elapsedRunTimePerTrainInSeconds = 0;
					double elapsedRunTimePerTrainAsSerialTime = 0;
					String otp = null;

					for (int k = 0; k < wbB.getSheetAt(i).getRow(0).getPhysicalNumberOfCells(); k++)  // by col
					{
						if (wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("File"))
						{
							file = wbB.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
							filesB.add(file);
						}
						else if (wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Line/Subdivision"))
						{
							line = wbB.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
							lines.add(line);
						}
						else if (wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Train Type"))
						{
							category = wbB.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
							categoriesForTypes.add(category);
						}
						else if ((wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Avg Speed")) && (generateVelocity))
						{
							velocity = wbB.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("True Delay Minutes per 100TM")) && (generateTrueDelayMinutesPer100TrainMiles))
						{
							delayMinutesPer100TM = wbB.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Elapsed Time Per Train as String by Train Type")) && (generateElapsedRunTimePerTrain))
						{
							elapsedRunTimePerTrainAsString = wbB.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
						}
						else if ((wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Elapsed Time Per Train in Seconds by Train Type")) && (generateElapsedRunTimePerTrain))
						{
							elapsedRunTimePerTrainInSeconds = wbB.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Elapsed Time Per Train as Serial Date/Time by Train Type")) && (generateElapsedRunTimePerTrain))
						{
							elapsedRunTimePerTrainAsSerialTime = wbB.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("OTP")) && (generateOtp))
						{	
							if (wbB.getSheetAt(i).getRow(j).getCell(k).getCellType() == CellType.NUMERIC)
							{
								otp = String.valueOf(wbB.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue());
							}
							else
							{
								otp = "0";
							}
						}
					}
					byTypeDataB.add(new SampleForTTest(line, category, velocity, delayMinutesPer100TM, elapsedRunTimePerTrainAsString, elapsedRunTimePerTrainInSeconds, elapsedRunTimePerTrainAsSerialTime, otp));
				}
				message += "\nRead " + byTypeDataB.size() + " type records from Case B";
			}
			else if (wbB.getSheetAt(i).getSheetName().equals("Trains by Group Raw Data"))
			{
				byGroupDataB = new ArrayList<SampleForTTest>();
				
				for (int j = 1; j <= wbB.getSheetAt(i).getLastRowNum(); j++)  // by row
				{
					String file = null;
					String line = null;
					String category = null;
					double velocity = 0;
					double delayMinutesPer100TM = 0;
					String elapsedRunTimePerTrainAsString = null;
					double elapsedRunTimePerTrainInSeconds = 0;
					double elapsedRunTimePerTrainAsSerialTime = 0;
					String otp = null;

					for (int k = 0; k < wbB.getSheetAt(i).getRow(0).getPhysicalNumberOfCells(); k++)  // by col
					{
						if (wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("File"))
						{
							file = wbB.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
							filesB.add(file);
						}
						else if (wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Line/Subdivision"))
						{
							line = wbB.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
							lines.add(line);
						}
						else if (wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Train Group"))
						{
							category = wbB.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
							categoriesForGroups.add(category);
						}
						else if ((wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Avg Speed")) && (generateVelocity))
						{
							velocity = wbB.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("True Delay Minutes per 100TM")) && (generateTrueDelayMinutesPer100TrainMiles))
						{
							delayMinutesPer100TM = wbB.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Elapsed Time Per Train as String by Train Group")) && (generateElapsedRunTimePerTrain))
						{
							elapsedRunTimePerTrainAsString = wbB.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
						}
						else if ((wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Elapsed Time Per Train in Seconds by Train Group")) && (generateElapsedRunTimePerTrain))
						{
							elapsedRunTimePerTrainInSeconds = wbB.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("Elapsed Time Per Train as Serial Date/Time by Train Group")) && (generateElapsedRunTimePerTrain))
						{
							elapsedRunTimePerTrainAsSerialTime = wbB.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue();
						}
						else if ((wbB.getSheetAt(i).getRow(0).getCell(k).getStringCellValue().equals("OTP")) && (generateOtp))
						{	
							if (wbB.getSheetAt(i).getRow(j).getCell(k).getCellType() == CellType.NUMERIC)
							{
								otp = String.valueOf(wbB.getSheetAt(i).getRow(j).getCell(k).getNumericCellValue());
							}
							else
							{
								otp = "0";
							}
						}
					}
					byGroupDataB.add(new SampleForTTest(line, category, velocity, delayMinutesPer100TM, elapsedRunTimePerTrainAsString, elapsedRunTimePerTrainInSeconds, elapsedRunTimePerTrainAsSerialTime, otp));
				}
				message += "\nRead " + byGroupDataB.size() + " group records from Case B";
			}		
		}

		try 
		{
			wbB.close();
		} 
		catch (IOException e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}
		
		// Type checks
		if (byTypeDataA != null && byTypeDataB != null)
		{
			if ((byTypeDataA.size() != byTypeDataB.size()))  
			{
				error = true;
				message += "\nType result sets are of different size.  They must be the same size.";
			}
		
			// Check that all types in A exist in B
			Boolean matchFound = false;
			for (int i = 0; i < byTypeDataA.size(); i++)
			{
				for (int j = 0; j < byTypeDataB.size(); j++)
				{ 
					if (byTypeDataA.get(i).getCategory().equals(byTypeDataB.get(j).getCategory()))
					{
						// Match found
						matchFound = true;
						break;
					}
				}
				
				if (matchFound) 
				{
					matchFound = false;
					continue;
				}
				else
				{
					// No match found
					error = true;
					message += "\nType "+byTypeDataA.get(i).getCategory()+" does not exist in both cases.  It must be present in both to generate t-tests.";
					break;
				}
			}
			
			// Check that all types in B exist in A
			matchFound = false;
			for (int i = 0; i < byTypeDataB.size(); i++)
			{
				for (int j = 0; j < byTypeDataA.size(); j++)
				{ 
					if (byTypeDataB.get(i).getCategory().equals(byTypeDataA.get(j).getCategory()))
					{
						// Match found
						matchFound = true;
						break;
					}
				}
				
				if (matchFound) 
				{
					matchFound = false;
					continue;
				}
				else
				{
					// No match found
					error = true;
					message += "\nType "+byTypeDataB.get(i).getCategory()+" does not exist in both cases.  It must be present in both to generate t-tests.";
					break;
				}
			}
		}
		
		// Group checks
		if (byGroupDataA != null && byGroupDataB != null)
		{
			if ((byGroupDataA.size() != byGroupDataB.size()))  
			{
				error = true;
				message += "\nGroup result sets are of different size.  They must be the same size.";
			}
			
			// Check that all groups in A exist in B
			Boolean matchFound = false;
			for (int i = 0; i < byGroupDataA.size(); i++)
			{
				for (int j = 0; j < byGroupDataB.size(); j++)
				{ 
					if (byGroupDataA.get(i).getCategory().equals(byGroupDataB.get(j).getCategory()))
					{
						// Match found
						matchFound = true;
						break;
					}
				}
				
				if (matchFound) 
				{
					matchFound = false;
					continue;
				}
				else
				{
					// No match found
					error = true;
					message += "\nGroup "+byGroupDataA.get(i).getCategory()+" does not exist in both cases.  It must be present in both to generate t-tests.";
					break;
				}
			}
			
			// Check that all groups in B exist in A
			matchFound = false;
			for (int i = 0; i < byGroupDataB.size(); i++)
			{
				for (int j = 0; j < byGroupDataA.size(); j++)
				{ 
					if (byGroupDataB.get(i).getCategory().equals(byGroupDataA.get(j).getCategory()))
					{
						// Match found
						matchFound = true;
						break;
					}
				}
				
				if (matchFound) 
				{
					matchFound = false;
					continue;
				}
				else
				{
					// No match found
					error = true;
					message += "\nGroup "+byGroupDataB.get(i).getCategory()+" does not exist in both cases.  It must be present in both to generate t-tests.";
					break;
				}
			}
		}
		
		// Input Files
		if (filesA.size() != filesB.size())
		{
			error = true;
			message += "\nThe number of input files to be considered are of different size.  They must be the same size.";
		}
		
		LocalTime endReadFilesTime = LocalTime.now();
		message += "\nFinished reading files at "+endReadFilesTime+"\n";
		
		return error;	
	}
	
	public ArrayList<SampleForTTest> getTypeDataA()
	{
		return byTypeDataA;
	}
	
	public ArrayList<SampleForTTest> getGroupDataA()
	{
		return byGroupDataA;
	}
	
	public ArrayList<SampleForTTest> getTypeDataB()
	{
		return byTypeDataB;
	}
	
	public ArrayList<SampleForTTest> getGroupDataB()
	{
		return byGroupDataB;
	}
	
	public HashSet<String> getLines()
	{
		return lines;
	}
	
	public HashSet<String> getCategoriesForTypes()
	{
		return categoriesForTypes;
	}
	
	public HashSet<String> getCategoriesForGroups()
	{
		return categoriesForGroups;
	}
	
	public int getFilesASize()
	{
		return filesA.size();
	}
	
	public int getFilesBSize()
	{
		return filesB.size();
	}
	
	public String getResultsMessage()
	{
		return message;
	}
}