package com.bl.bias.analyze;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.bl.bias.exception.ErrorShutdown;

import javafx.beans.property.SimpleBooleanProperty;

public class TTestValidateTTest 
{
	static String message;
	
	private static Boolean generateVelocity = false;
	private static Boolean generateTrueDelayMinutesPer100TrainMiles = false;
	private static Boolean generateElapsedRunTimePerTrainAsString = false;
	private static Boolean generateElapsedRunTimePerTrainInSeconds = false;
	private static Boolean generateElapsedRunTimePerTrainAsSerial = false;
	private static Boolean generateOtp = false;
	private static Boolean generateType = false;
	private static Boolean generateGroup = false;
	private static Boolean generateCombined = false;
	
	public static Boolean validate(String fullyQualifiedPathA, String fullyQualifiedPathB, SimpleBooleanProperty velocitySelected, SimpleBooleanProperty trueDelayMinutesPer100TrainMilesSelected, SimpleBooleanProperty elapsedRunTimePerTrainSelected, SimpleBooleanProperty otpSelected)
	{
		//  Ensure files have valid information for tests
		LocalTime startValidateFilesTime = LocalTime.now();
		message = "\nStarted validating files at "+startValidateFilesTime+"\n";
		
		Boolean error = false;
	
		Boolean typeRawExistsA = false;
		Boolean groupRawExistsA = false;
		Boolean combinedRawExistsA = false;
		Boolean velocityExistsA = false;
		Boolean trueDelayMinutesPer100TrainMilesExistsA = false;
		Boolean elapsedRunTimePerTrainAsStringExistsA = false;
		Boolean elapsedRunTimePerTrainInSecondsExistsA = false;
		Boolean elapsedRunTimePerTrainAsSerialExistsA = false;
		Boolean otpExistsA = false;
		Boolean typeRawExistsB = false;
		Boolean groupRawExistsB = false;
		Boolean combinedRawExistsB = false;
		Boolean velocityExistsB = false;
		Boolean trueDelayMinutesPer100TrainMilesExistsB = false;
		Boolean elapsedRunTimePerTrainAsStringExistsB = false;
		Boolean elapsedRunTimePerTrainInSecondsExistsB = false;
		Boolean elapsedRunTimePerTrainAsSerialExistsB = false;
		Boolean otpExistsB = false;
		
		// Open fileA to determine if needed data is available
		Workbook wbA = null;
		try 
		{
			wbA = WorkbookFactory.create(new File(fullyQualifiedPathA));
		} 
		catch (EncryptedDocumentException e) 
		{
			ErrorShutdown.displayError(e, "TTestValidateTTest");
		}
		catch (IOException e) 
		{
			ErrorShutdown.displayError(e, "TTestValidateTTest");
		}
	
		for (int i=0; i < wbA.getNumberOfSheets(); i++)
		{
			for (int j = 0; j < wbA.getSheetAt(i).getRow(0).getPhysicalNumberOfCells(); j++)
			{
				if (wbA.getSheetAt(i).getSheetName().equals("Trains by Type Raw Data"))
					typeRawExistsA = true;
				else if (wbA.getSheetAt(i).getSheetName().equals("Trains by Group Raw Data"))
					groupRawExistsA = true;
				else if (wbA.getSheetAt(i).getSheetName().equals("All Trains Raw Data"))
					combinedRawExistsA = true;
				else
					continue;
	
				if (wbA.getSheetAt(i).getRow(0).getCell(j).getStringCellValue().equals("Avg Speed"))
					velocityExistsA = true;
				else if (wbA.getSheetAt(i).getRow(0).getCell(j).getStringCellValue().contains("True Delay Minutes per 100TM"))
					trueDelayMinutesPer100TrainMilesExistsA = true;
				else if (wbA.getSheetAt(i).getRow(0).getCell(j).getStringCellValue().contains("Elapsed Time Per Train as String"))
					elapsedRunTimePerTrainAsStringExistsA = true;
				else if (wbA.getSheetAt(i).getRow(0).getCell(j).getStringCellValue().contains("Elapsed Time Per Train in Seconds"))
					elapsedRunTimePerTrainInSecondsExistsA = true;
				else if (wbA.getSheetAt(i).getRow(0).getCell(j).getStringCellValue().contains("Elapsed Time Per Train as Serial"))
					elapsedRunTimePerTrainAsSerialExistsA = true;
				else if (wbA.getSheetAt(i).getRow(0).getCell(j).getStringCellValue().contains("OTP"))
					otpExistsA = true;
			}
		}
		
		try 
		{
			wbA.close();
		} 
		catch (IOException e) 
		{
			ErrorShutdown.displayError(e, "TTestValidateTTest");
		}
	
		// Open fileB to determine if needed data is available
		Workbook wbB = null;
		try 
		{
			wbB = WorkbookFactory.create(new File(fullyQualifiedPathB));
		} 
		catch (EncryptedDocumentException e) 
		{
			ErrorShutdown.displayError(e, "TTestValidateTTest");
		}
		catch (IOException e) 
		{
			ErrorShutdown.displayError(e, "TTestValidateTTest");
		}
	
		for (int i=0; i < wbB.getNumberOfSheets(); i++)
		{
			for (int j = 0; j < wbB.getSheetAt(i).getRow(0).getPhysicalNumberOfCells(); j++)
			{
				if (wbB.getSheetAt(i).getSheetName().equals("Trains by Type Raw Data"))
					typeRawExistsB = true;
				else if (wbB.getSheetAt(i).getSheetName().equals("Trains by Group Raw Data"))
					groupRawExistsB = true;
				else if (wbB.getSheetAt(i).getSheetName().equals("All Trains Raw Data"))
					combinedRawExistsB = true;
				else
					continue;
	
				if (wbB.getSheetAt(i).getRow(0).getCell(j).getStringCellValue().equals("Avg Speed"))
					velocityExistsB = true;
				else if (wbB.getSheetAt(i).getRow(0).getCell(j).getStringCellValue().contains("True Delay Minutes per 100TM"))
					trueDelayMinutesPer100TrainMilesExistsB = true;
				else if (wbB.getSheetAt(i).getRow(0).getCell(j).getStringCellValue().contains("Elapsed Time Per Train as String"))
					elapsedRunTimePerTrainAsStringExistsB = true;
				else if (wbB.getSheetAt(i).getRow(0).getCell(j).getStringCellValue().contains("Elapsed Time Per Train in Seconds"))
					elapsedRunTimePerTrainInSecondsExistsB = true;
				else if (wbB.getSheetAt(i).getRow(0).getCell(j).getStringCellValue().contains("Elapsed Time Per Train as Serial"))
					elapsedRunTimePerTrainAsSerialExistsB = true;
				else if (wbB.getSheetAt(i).getRow(0).getCell(j).getStringCellValue().contains("OTP"))
					otpExistsB = true;
			}
		}
		
		try 
		{
			wbB.close();
		} 
		catch (IOException e) 
		{
			ErrorShutdown.displayError(e, "TTestValidateTTest");
		}
	
		// See if files are the same
		if (fullyQualifiedPathA.contentEquals(fullyQualifiedPathB))
		{
			message += "Both files are the same -- select new inputs";
			error = true;
		}
	
		// See if a valid combination for comparison exists considering requested metrics
		if (!error)
		{
			if (((typeRawExistsA && typeRawExistsB) || (groupRawExistsA && groupRawExistsB) || (combinedRawExistsA && combinedRawExistsB)) &&
					((velocityExistsA && velocityExistsB) || (trueDelayMinutesPer100TrainMilesExistsA && trueDelayMinutesPer100TrainMilesExistsB) || 
							(elapsedRunTimePerTrainAsStringExistsA && elapsedRunTimePerTrainAsStringExistsB) || (elapsedRunTimePerTrainInSecondsExistsA && elapsedRunTimePerTrainInSecondsExistsB) || 
							(elapsedRunTimePerTrainAsSerialExistsA && elapsedRunTimePerTrainAsSerialExistsB) || (otpExistsA && otpExistsB)))
			{
				message += "Statistics for ";
				if (velocityExistsA && velocityExistsB && velocitySelected.getValue())
				{
					message += "velocity, ";
					generateVelocity = true;
				}
				if (trueDelayMinutesPer100TrainMilesExistsA && trueDelayMinutesPer100TrainMilesExistsB && trueDelayMinutesPer100TrainMilesSelected.getValue())
				{
					message += "true delay minutes per 100 train miles, ";
					generateTrueDelayMinutesPer100TrainMiles = true;
				}
				if (elapsedRunTimePerTrainAsStringExistsA && elapsedRunTimePerTrainAsStringExistsB && elapsedRunTimePerTrainSelected.getValue())
				{
					message += "elapsed run time per train (string), ";
					generateElapsedRunTimePerTrainAsString = true;
				}
				if (elapsedRunTimePerTrainInSecondsExistsA && elapsedRunTimePerTrainInSecondsExistsB && elapsedRunTimePerTrainSelected.getValue())
				{
					message += "elapsed run time per train (seconds), ";
					generateElapsedRunTimePerTrainInSeconds = true;
				}
				if (elapsedRunTimePerTrainAsSerialExistsA && elapsedRunTimePerTrainAsSerialExistsB && elapsedRunTimePerTrainSelected.getValue())
				{
					message += "elapsed run time per train (serial), ";
					generateElapsedRunTimePerTrainAsSerial = true;
				}
				if (otpExistsA && otpExistsB && otpSelected.getValue())
				{
					message += "otp, ";
					generateOtp = true;
				}
				message += "\nwill be generated ";
				if (typeRawExistsA && typeRawExistsB)
				{
					message += "by type, ";
					generateType = true;
				}
				else 
					generateType = false;
				if (groupRawExistsA && groupRawExistsB)
				{
					message += "by group, ";
					generateGroup = true;
				}
				else 
					generateGroup = false;
				if (combinedRawExistsA && combinedRawExistsB)
				{
					message += "for all trains combined, ";
					generateCombined = true;
				}
				else 
					generateCombined = false;
				message += "if available";
			}
			else
			{
				message += "No valid combination of parameters and groupings exists --  select new inputs";
				error = true;
			}
		}
		
		LocalTime endValidateFilesTime = LocalTime.now();
		message += "\nFinished validating files at "+endValidateFilesTime+"\n";
		
		return error;
	}
	
	public String getResultsMessage()
	{
		return message;
	}
	
	public static Boolean getGenerateVelocity()
	{
		return generateVelocity;
	}
	
	public static Boolean getGenerateTrueDelayMinutesPer100TrainMiles()
	{
		return generateTrueDelayMinutesPer100TrainMiles;
	}
	
	public static Boolean getGenerateElapsedRunTimePerTrainAsString()
	{
		return generateElapsedRunTimePerTrainAsString;
	}
	
	public static Boolean getGenerateElapsedRunTimePerTrainInSeconds()
	{
		return generateElapsedRunTimePerTrainInSeconds;
	}
	
	public static Boolean getGenerateElapsedRunTimePerTrainAsSerial()
	{
		return generateElapsedRunTimePerTrainAsSerial;
	}
	
	public static Boolean getGenerateOtp()
	{
		return generateOtp;
	}
	
	public static Boolean getGenerateType()
	{
		return generateType;
	}
	
	public static Boolean getGenerateGroup()
	{
		return generateGroup;
	}
	
	public static Boolean getGenerateCombined()
	{
		return generateCombined;
	}
	
	public static void resetGenerateValidation()
	{
		generateVelocity = false;
		generateTrueDelayMinutesPer100TrainMiles = false;
		generateElapsedRunTimePerTrainAsString = false;
		generateElapsedRunTimePerTrainInSeconds = false;
		generateElapsedRunTimePerTrainAsSerial = false;
		generateOtp = false;
		generateType = false;
		generateGroup = false;
		generateCombined = false;
	}
}