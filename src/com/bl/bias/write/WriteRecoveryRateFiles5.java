package com.bl.bias.write;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.app.BIASRecoveryRateAnalysisConfigController;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.tools.ConvertDateTime;

public class WriteRecoveryRateFiles5 extends WriteRecoveryRateFiles4 // Trains which have had their OTP excluded
{
	protected String resultsMessage = getResultsMessageWrite4();

	public WriteRecoveryRateFiles5(String textArea, String fullyQualifiedPath)
	{
		super(textArea);

		if ((BIASRecoveryRateAnalysisConfigController.getExcludeTrainsBelowThresholdSetA()) 
				|| (BIASRecoveryRateAnalysisConfigController.getExcludeTrainsBelowThresholdSetB()) 
				|| (BIASRecoveryRateAnalysisConfigController.getExcludeTrainsBelowThresholdSetC()) 
				|| (BIASRecoveryRateAnalysisConfigController.getExcludeTrainsBelowThresholdSetD())) 
		{
			rowCounter = 0;

			// Set styles
			CellStyle style0 = workbook.createCellStyle();
			CellStyle style1 = workbook.createCellStyle();
			CellStyle style2 = workbook.createCellStyle();
			CellStyle style3 = workbook.createCellStyle();

			// Write Excluded Train Set
			XSSFSheet seedTrainsExcludedFromOtpSheet = workbook.createSheet("Trains Excluded from OTP");
			seedTrainsExcludedFromOtpSheet.setDisplayGridlines(false);

			// Fonts
			// Font 0 - 14pt White text
			XSSFFont font0 = workbook.createFont();
			font0.setFontHeightInPoints((short) 14);
			font0.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
			font0.setFontName("Calibri");

			// Font 1 - 11pt text, black font
			XSSFFont font1 = workbook.createFont();
			font1.setFontHeightInPoints((short) 11);
			font1.setFontName("Calibri");

			// Font 2 - 8pt text, black font
			XSSFFont font2 = workbook.createFont();
			font2.setFontHeightInPoints((short) 8);
			font2.setFontName("Calibri");

			// Cell styles
			// Style 0 - Centered, non- wrapped 14pt, white text against black background
			style0.setAlignment(HorizontalAlignment.CENTER);  
			style0.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
			style0.setFillPattern(FillPatternType.FINE_DOTS);
			style0.setWrapText(false);
			style0.setFont(font0);

			// Style 1 - Left aligned, non-wrapped, 8pt, black text
			style1.setAlignment(HorizontalAlignment.LEFT);  
			style1.setWrapText(false);
			style1.setFont(font2);

			// Style 2 - Left aligned, non-wrapped, 11pt, black text
			style2.setAlignment(HorizontalAlignment.LEFT);  
			style2.setWrapText(false);
			style2.setFont(font1);

			// Style 3 - Left aligned, non-wrapped, 11pt, black text, thin bottom border
			style3.setAlignment(HorizontalAlignment.LEFT);  
			style3.setWrapText(false);
			style3.setFont(font1);
			style3.setBorderBottom(BorderStyle.THIN);

			// Header rows
			// Case name
			seedTrainsExcludedFromOtpSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

			Row row;
			Cell cell;

			row = seedTrainsExcludedFromOtpSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style0);
			cell.setCellValue("Trains with OTP Excluded in .TRAIN File");

			// Data headers
			rowCounter++;
			rowCounter++;
			row = seedTrainsExcludedFromOtpSheet.createRow(rowCounter);

			cell = row.createCell(0);
			cell.setCellStyle(style3);
			cell.setCellValue("Seed Train");

			// Trains
			// Convert HashSet to ArrayList
			ArrayList<String> seedTrainsBelowTargetRecoveryRateArrayList = new ArrayList<String>();
			seedTrainsBelowTargetRecoveryRateArrayList.addAll(seedTrainsBelowTargetRecoveryRateHashSet);
			Collections.sort(seedTrainsBelowTargetRecoveryRateArrayList);
			
			// Create a backup .TRAIN file and modify existing .TRAIN file
			if (seedTrainsBelowTargetRecoveryRateArrayList.size() > 0) 
			{
				Scanner scanner = null;
				try 
				{
					String backUpTrainFile = "";
					String newTrainFile = "";
					String trainFileLocation = fullyQualifiedPath.replace(".OPTION", ".TRAIN");
					File trainFile = new File(trainFileLocation);
					scanner = new Scanner(trainFile);
					Boolean excludedTrainSymbolFound = false;
					
					// For each line
					while (scanner.hasNextLine())  
					{
						String lineFromFile = scanner.nextLine();
						backUpTrainFile += lineFromFile+"\n";
						
						if (lineFromFile.contains("Train symbol: "))
						{
							newTrainFile += lineFromFile+"\n";
							excludedTrainSymbolFound = false;
							String symbol = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[1])).trim();

							if (seedTrainsBelowTargetRecoveryRateArrayList.contains(symbol))
							{
								excludedTrainSymbolFound = true;
							}
						}
						else if ((lineFromFile.contains("Exclude from OTP statistics: ")) && (excludedTrainSymbolFound))
						{
							// Modify here
							newTrainFile += lineFromFile.replace("Exclude from OTP statistics: NO ", "Exclude from OTP statistics: YES");
						}
						else
							newTrainFile += lineFromFile+"\n";
					}
					
					// Write the back-up file
					try 
					{
						FileWriter fileWriter;
						fileWriter = new FileWriter(trainFileLocation.replace(".TRAIN", ".TRAIN_backupFromBias"));					
						fileWriter.write(backUpTrainFile);
						fileWriter.close();
						
						resultsMessage += "\nWriting back-up .TRAIN file";
					} 
					catch (IOException e) 
					{
						error = true;
						resultsMessage += "\nError writing back-up .TRAIN file";
						ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
					}	
					
					// Write the new file
					try 
					{
						FileWriter fileWriter;
						fileWriter = new FileWriter(trainFileLocation);					
						fileWriter.write(newTrainFile);
						fileWriter.close();
						
						resultsMessage += "\nWriting new .TRAIN file";
					} 
					catch (IOException e) 
					{
						error = true;
						resultsMessage += "\nError writing new .TRAIN file";
						ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
					}	
				}

				catch (Exception e) 
				{
					error = true;
					resultsMessage += "\nInput/output error in Recovery Rate module!";
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}
				finally 
				{
					scanner.close();
				}
			}

			// Add to results sheet
			for (int i = 0; i < seedTrainsBelowTargetRecoveryRateArrayList.size(); i++)
			{
				rowCounter++;
				row = seedTrainsExcludedFromOtpSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue(seedTrainsBelowTargetRecoveryRateArrayList.get(i));
			}

			if (seedTrainsBelowTargetRecoveryRateArrayList.size() == 0) 
			{
				// Train Symbol
				rowCounter++;
				row = seedTrainsExcludedFromOtpSheet.createRow(rowCounter);
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("No trains to exclude from OTP calculation found!");
			}
			else
			{
				resultsMessage += "\nWriting trains to exclude from OTP calculation due to below target recovery rate";
			}

			// Timestamp
			LocalDate creationDate = ConvertDateTime.getDateStamp();
			LocalTime creationTime = ConvertDateTime.getTimeStamp();

			rowCounter++;
			rowCounter++;
			row = seedTrainsExcludedFromOtpSheet.createRow(rowCounter);
			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue("Created on "+creationDate+" at "+creationTime);
		}
	}

	public String getResultsMessageWrite5()
	{
		return resultsMessage;
	}
}