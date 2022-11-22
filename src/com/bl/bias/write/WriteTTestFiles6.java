package com.bl.bias.write;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;

import com.bl.bias.analyze.TTestValidateTTest;
import com.bl.bias.app.BIASTtestConfigPageController;
import com.bl.bias.app.BIASTtestPageController;
import com.bl.bias.objects.ResultFromTTest;

public class WriteTTestFiles6
{
	private static String resultsMessage;

	private HashSet<ResultFromTTest> byTypeTTestResults = new HashSet<ResultFromTTest>();
	private HashSet<ResultFromTTest> byGroupTTestResults = new HashSet<ResultFromTTest>();

	private ArrayList<ResultFromTTest> sortedGroupFiles;
	private ArrayList<ResultFromTTest> sortedTypeFiles;

	private Integer rowNumber = 0;
	private Integer lastClusterRowStart = 0;
	private static Integer resultsSetSize = 0;

	private LocalDateTime now = LocalDateTime.now();

	private static XSSFWorkbook workbook = new XSSFWorkbook();
	private static XSSFSheet otpTTestSheet; 

	private static IndexedColors[] tTestColors = new IndexedColors[8];
	
	public WriteTTestFiles6(String textAreaContents, HashSet<ResultFromTTest> byTypeTTestResults, HashSet<ResultFromTTest> byGroupTTestResults)
	{
		//  These colors must be in sync with non-IndexedColors in BIASTtestConfigPageController class
		tTestColors[0] = IndexedColors.RED;  
		tTestColors[1] = IndexedColors.CORAL;
		tTestColors[2] = IndexedColors.LIGHT_YELLOW;
		tTestColors[3] = IndexedColors.LIGHT_GREEN;
		tTestColors[4] = IndexedColors.GREEN;
		tTestColors[5] = IndexedColors.BLUE;
		tTestColors[6] = IndexedColors.LIGHT_BLUE;
		tTestColors[7] = IndexedColors.GREY_25_PERCENT;
		
		resultsMessage = WriteTTestFiles5.getResultsMessageWrite5();
		workbook = WriteTTestFiles5.getWorkbook5();
		
		if (TTestValidateTTest.getGenerateOtp())
		{
			this.byTypeTTestResults = byTypeTTestResults;
			this.byGroupTTestResults = byGroupTTestResults;

			writeFiles6();
		}
	}

	public void writeFiles6()
	{
		// Set fonts
		XSSFFont font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 14);
		font1.setFontName("Calibri");
		font1.setBold(true);

		XSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 8);
		font2.setFontName("Calibri");

		XSSFFont font3 = workbook.createFont();
		font3.setFontHeightInPoints((short) 14);
		font3.setFontName("Calibri");
		font3.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());

		// Set styles
		CellStyle style1 =  workbook.createCellStyle(); // text wrap
		CellStyle style2 =  workbook.createCellStyle(); // align left
		CellStyle style3 =  workbook.createCellStyle(); // align center 
		CellStyle style4 =  workbook.createCellStyle(); // left justify and two places after decimal
		CellStyle style5 =  workbook.createCellStyle(); // center justify and two places after decimal
		CellStyle style6 =  workbook.createCellStyle(); // left justify and large font and bold
		CellStyle style7 =  workbook.createCellStyle(); // left justify and small font and bold
		CellStyle style8 =  workbook.createCellStyle(); // center justify and small font
		CellStyle style9 =  workbook.createCellStyle(); // center justify and large font and inverted font
		CellStyle style10 = workbook.createCellStyle(); // center justify and light gray background
		CellStyle style11 = workbook.createCellStyle(); // center justify and light gray background and two places after decimal

		// Styles 12 - 16 are for color coding of t-test results
		CellStyle style12 = workbook.createCellStyle(); // center justify and index 0 background and two places after decimal
		CellStyle style13 = workbook.createCellStyle(); // center justify and index 1 background and two places after decimal
		CellStyle style14 = workbook.createCellStyle(); // center justify and index 2 background and two places after decimal
		CellStyle style15 = workbook.createCellStyle(); // center justify and index 3 background and two places after decimal
		CellStyle style16 = workbook.createCellStyle(); // center justify and index 4 background and two places after decimal

		style1.setWrapText(true); 

		style2.setAlignment(HorizontalAlignment.LEFT);

		style3.setAlignment(HorizontalAlignment.CENTER);

		style4.setDataFormat(workbook.createDataFormat().getFormat("0.00")); 
		style4.setAlignment(HorizontalAlignment.LEFT);

		style5.setDataFormat(workbook.createDataFormat().getFormat("0.00"));          
		style5.setAlignment(HorizontalAlignment.CENTER);

		style6.setAlignment(HorizontalAlignment.LEFT);
		style6.setFont(font1);

		style7.setAlignment(HorizontalAlignment.LEFT);
		style7.setFont(font2);

		style8.setAlignment(HorizontalAlignment.CENTER);
		style8.setFont(font2);

		style9.setAlignment(HorizontalAlignment.CENTER);
		style9.setFont(font3);
		style9.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		style9.setFillPattern(FillPatternType.FINE_DOTS);

		style10.setAlignment(HorizontalAlignment.CENTER);
		style10.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style10.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		style11.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
		style11.setAlignment(HorizontalAlignment.CENTER);
		style11.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style11.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		style12.setDataFormat(workbook.createDataFormat().getFormat("0.00"));          
		style12.setAlignment(HorizontalAlignment.CENTER);
		style12.setFillForegroundColor(tTestColors[BIASTtestPageController.getTTestColumn1ColorIndex()].getIndex());
		style12.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		style13.setDataFormat(workbook.createDataFormat().getFormat("0.00"));          
		style13.setAlignment(HorizontalAlignment.CENTER);
		style13.setFillForegroundColor(tTestColors[BIASTtestPageController.getTTestColumn2ColorIndex()].getIndex());
		style13.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		style14.setDataFormat(workbook.createDataFormat().getFormat("0.00"));          
		style14.setAlignment(HorizontalAlignment.CENTER);
		style14.setFillForegroundColor(tTestColors[BIASTtestPageController.getTTestColumn3ColorIndex()].getIndex());
		style14.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		style15.setDataFormat(workbook.createDataFormat().getFormat("0.00"));          
		style15.setAlignment(HorizontalAlignment.CENTER);
		style15.setFillForegroundColor(tTestColors[BIASTtestPageController.getTTestColumn4ColorIndex()].getIndex());
		style15.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		style16.setDataFormat(workbook.createDataFormat().getFormat("0.00"));          
		style16.setAlignment(HorizontalAlignment.CENTER);
		style16.setFillForegroundColor(tTestColors[BIASTtestPageController.getTTestColumn5ColorIndex()].getIndex());
		style16.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// Write OTP files
		if  ((byGroupTTestResults.size() > 0) || (byTypeTTestResults.size() > 0))
		{
			otpTTestSheet = workbook.createSheet("OTP");
			otpTTestSheet.setDisplayGridlines(false);
			Row row;
			Cell cell; 

			// Write header here
			// First row of sheet header
			row = otpTTestSheet.createRow(rowNumber);

			cell = row.createCell(0);
			cell.setCellStyle(style6);
			cell.setCellValue("T-test Results");

			cell = row.createCell(4);
			cell.setCellStyle(style10);
			cell.setCellValue("CASE A");

			if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
			{
				cell = row.createCell(7);
			}
			else
			{
				cell = row.createCell(6);
			}
			cell.setCellStyle(style8);
			cell.setCellValue("alpha = "+BIASTtestPageController.getLOS());   

			if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
			{
				cell = row.createCell(10);
			}
			else
			{
				cell = row.createCell(8);
			}
			cell.setCellStyle(style10);
			cell.setCellValue("CASE B");

			// Second row of sheet header
			rowNumber++;
			row = otpTTestSheet.createRow(rowNumber);

			cell = row.createCell(0);
			cell.setCellStyle(style7);
			cell.setCellValue(now.toLocalDate().toString()+" at "+now.toLocalTime().toString());

			cell = row.createCell(4);
			cell.setCellStyle(style10);
			cell.setCellValue(BIASTtestPageController.getFileAAsString().replace(".xlsx", ""));

			if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
			{
				cell = row.createCell(7);
			}
			else
			{
				cell = row.createCell(6);
			}
			cell.setCellStyle(style8);
			double tValue = BIASTtestPageController.getCriticalT();
			double roundedT = Math. round(tValue * 100.0) / 100.0;
			cell.setCellValue("critical t-value = "+roundedT);   

			if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
			{
				cell = row.createCell(10);
			}
			else
			{
				cell = row.createCell(8);
			}
			cell.setCellStyle(style10);
			cell.setCellValue(BIASTtestPageController.getFileBAsString().replace(".xlsx", ""));    

			// By group
			if (byGroupTTestResults.size() > 0)
			{
				// First category header row
				rowNumber++;
				row = otpTTestSheet.createRow(rowNumber);
				if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
				{
					otpTTestSheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 0, 10));
				}
				else
				{
					otpTTestSheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 0, 8));
				}

				cell = row.createCell(0);
				cell.setCellStyle(style9);
				cell.setCellValue("OTP - Group Tests");

				// Second category header row
				rowNumber++;
				row = otpTTestSheet.createRow(rowNumber);

				cell = row.createCell(4);
				cell.setCellStyle(style10);
				cell.setCellValue("CASE A");

				if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
				{
					cell = row.createCell(10);
				}
				else
				{
					cell = row.createCell(8);
				}
				cell.setCellStyle(style10);
				cell.setCellValue("CASE B");

				// Group files
				rowNumber++;
				row = otpTTestSheet.createRow(rowNumber);

				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("Line/Subdivision");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

				cell = row.createCell(1);
				cell.setCellStyle(style2);
				cell.setCellValue("Train Group");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

				cell = row.createCell(2);
				cell.setCellStyle(style3);
				cell.setCellValue("OTP Mean Diff");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

				cell = row.createCell(3);
				cell.setCellStyle(style3);
				cell.setCellValue("T-stat");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

				cell = row.createCell(4);
				cell.setCellStyle(style10);
				cell.setCellValue("OTP");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

				cell = row.createCell(5);
				cell.setCellStyle(style3);
				cell.setCellValue("Significant");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

				if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
				{
					cell = row.createCell(6);
					cell.setCellStyle(style3);
					cell.setCellValue("Slight");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
	
					cell = row.createCell(7);
					cell.setCellStyle(style3);
					cell.setCellValue("Equal");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
	
					cell = row.createCell(8);
					cell.setCellStyle(style3);
					cell.setCellValue("Slight");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
	
					cell = row.createCell(9);
					cell.setCellStyle(style3);
					cell.setCellValue("Significant");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
	
					cell = row.createCell(10);
					cell.setCellStyle(style10);
					cell.setCellValue("OTP");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
				}
				else
				{
					cell = row.createCell(6);
					cell.setCellStyle(style3);
					cell.setCellValue("Not Significant");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
	
					cell = row.createCell(7);
					cell.setCellStyle(style3);
					cell.setCellValue("Significant");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
	
					cell = row.createCell(8);
					cell.setCellStyle(style10);
					cell.setCellValue("OTP");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
				}
				
				// Sort group files here
				sortedGroupFiles = new ArrayList<>(byGroupTTestResults);
				Collections.sort(sortedGroupFiles, new sortByGroupOrType());

				for (int i = 0; i < sortedGroupFiles.size(); i++)
				{
					if ((sortedGroupFiles.get(i).getOtpA() != null ) && (sortedGroupFiles.get(i).getOtpB() != null ) && (sortedGroupFiles.get(i).getOtpA() > 0 ) && (sortedGroupFiles.get(i).getOtpB() > 0))
					{
						resultsSetSize++;
						rowNumber++;
						row = otpTTestSheet.createRow(rowNumber);

						// Pull below from ResultFromTTest object
						cell = row.createCell(0);
						cell.setCellStyle(style2);
						cell.setCellValue(sortedGroupFiles.get(i).getLine());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

						cell = row.createCell(1);
						cell.setCellStyle(style2);
						cell.setCellValue(sortedGroupFiles.get(i).getTypeOrGroup());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

						cell = row.createCell(2);
						cell.setCellStyle(style5);

						if (sortedGroupFiles.get(i).getOtpB() > sortedGroupFiles.get(i).getOtpA())
						{
							cell.setCellValue(sortedGroupFiles.get(i).getOtpDiff());
						}
						else
						{
							cell.setCellValue(-1 * sortedGroupFiles.get(i).getOtpDiff());
						}
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

						cell = row.createCell(3);
						cell.setCellStyle(style5);
						if (Double.isInfinite(sortedGroupFiles.get(i).getOtpTValue()))
						{
							cell.setCellValue("infinity");
						}
						else if (Double.isNaN(sortedGroupFiles.get(i).getOtpTValue()))
						{
							cell.setCellValue(0.00);
						}
						else if (sortedGroupFiles.get(i).getOtpB() > sortedGroupFiles.get(i).getOtpA())
						{
							cell.setCellValue(sortedGroupFiles.get(i).getOtpTValue());
						}
						else
						{
							cell.setCellValue(-1 * sortedGroupFiles.get(i).getOtpTValue());
						}
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

						cell = row.createCell(4);
						cell.setCellStyle(style11);
						cell.setCellValue(sortedGroupFiles.get(i).getOtpA());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

						if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
						{
							cell = row.createCell(10);
						}
						else
						{
							cell = row.createCell(8);
						}
						cell.setCellStyle(style11);
						cell.setCellValue(sortedGroupFiles.get(i).getOtpB());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

						// Put borders around all of the significant, slight and equal cells
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 5, 5), otpTTestSheet);
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 6, 6), otpTTestSheet);
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 7, 7), otpTTestSheet);
						if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
						{
							setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 8, 8), otpTTestSheet);
							setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 9, 9), otpTTestSheet);
						}
						
						if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
						{
							// Color appropriate significance column (5 through 9)
							if (sortedGroupFiles.get(i).getOtpDiff() == 0)
							{
								// Is equal
								cell = row.createCell(7);
								cell.setCellStyle(style14);
								cell.setCellValue("");
								setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
							}
							else if (sortedGroupFiles.get(i).getOtpSignificant())
							{
								if (sortedGroupFiles.get(i).getOtpA() > sortedGroupFiles.get(i).getOtpB())
								{
									// Is significant and A > B
									cell = row.createCell(5);
									cell.setCellStyle(style12);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
								}
								else
								{
									// Is significant and B > A
									cell = row.createCell(9);
									cell.setCellStyle(style16);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
								}
							}
							else
							{
								if (sortedGroupFiles.get(i).getOtpA() > sortedGroupFiles.get(i).getOtpB())
								{
									// Is slight and A > B
									cell = row.createCell(6);
									cell.setCellStyle(style13);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
								}
								else
								{
									// Is slight and B > A
									cell = row.createCell(8);
									cell.setCellStyle(style15);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
								}
							}
						}
						else
						{
							// Color appropriate significance column (5 through 7)
							if (sortedGroupFiles.get(i).getOtpSignificant())
							{
								if (sortedGroupFiles.get(i).getOtpA() > sortedGroupFiles.get(i).getOtpB())
								{
									// Is significant and A > B
									cell = row.createCell(5);
									cell.setCellStyle(style12);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
								}
								else
								{
									// Is significant and B > A
									cell = row.createCell(7);
									cell.setCellStyle(style16);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
								}
							}
							else
							{
								// Is not significant
								cell = row.createCell(6);
								cell.setCellStyle(style14);
								cell.setCellValue("");
								setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
							}
						}
					}
				} 	

				// Set thick borders
				setRegionBorderThick(new CellRangeAddress(lastClusterRowStart, rowNumber, 4, 4), otpTTestSheet);
				if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
				{
					setRegionBorderThick(new CellRangeAddress(lastClusterRowStart, rowNumber, 10, 10), otpTTestSheet);
				}
				else
				{
					setRegionBorderThick(new CellRangeAddress(lastClusterRowStart, rowNumber, 8, 8), otpTTestSheet);
				}
				
				rowNumber++;
				lastClusterRowStart = rowNumber + 2;
			}

			// By type
			if (byTypeTTestResults.size() > 0)
			{
				// First category header row
				rowNumber++;
				row = otpTTestSheet.createRow(rowNumber);
				if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
				{
					otpTTestSheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 0, 10));
				}
				else
				{
					otpTTestSheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 0, 8));
				}

				cell = row.createCell(0);
				cell.setCellStyle(style9);
				cell.setCellValue("OTP - Type Tests");

				// Second category header row
				rowNumber++;
				row = otpTTestSheet.createRow(rowNumber);

				cell = row.createCell(4);
				cell.setCellStyle(style10);
				cell.setCellValue("CASE A");

				if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
				{
					cell = row.createCell(10);
				}
				else
				{
					cell = row.createCell(8);
				}
				cell.setCellStyle(style10);
				cell.setCellValue("CASE B");

				// Type files
				rowNumber++;
				row = otpTTestSheet.createRow(rowNumber);

				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("Line/Subdivision");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

				cell = row.createCell(1);
				cell.setCellStyle(style2);
				cell.setCellValue("Train Type");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

				cell = row.createCell(2);
				cell.setCellStyle(style3);
				cell.setCellValue("OTP Mean Diff");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

				cell = row.createCell(3);
				cell.setCellStyle(style3);
				cell.setCellValue("T-stat");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

				cell = row.createCell(4);
				cell.setCellStyle(style10);
				cell.setCellValue("OTP");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

				cell = row.createCell(5);
				cell.setCellStyle(style3);
				cell.setCellValue("Significant");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

				if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
				{
					cell = row.createCell(6);
					cell.setCellStyle(style3);
					cell.setCellValue("Slight");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
	
					cell = row.createCell(7);
					cell.setCellStyle(style3);
					cell.setCellValue("Equal");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
	
					cell = row.createCell(8);
					cell.setCellStyle(style3);
					cell.setCellValue("Slight");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
	
					cell = row.createCell(9);
					cell.setCellStyle(style3);
					cell.setCellValue("Significant");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
	
					cell = row.createCell(10);
					cell.setCellStyle(style10);
					cell.setCellValue("OTP");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
				}
				else
				{
					cell = row.createCell(6);
					cell.setCellStyle(style3);
					cell.setCellValue("Not Significant");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
	
					cell = row.createCell(7);
					cell.setCellStyle(style3);
					cell.setCellValue("Significant");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
	
					cell = row.createCell(8);
					cell.setCellStyle(style10);
					cell.setCellValue("OTP");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
				}
				
				// Sort type files here
				sortedTypeFiles = new ArrayList<>(byTypeTTestResults);
				Collections.sort(sortedTypeFiles, new sortByGroupOrType());

				for (int i = 0; i < sortedTypeFiles.size(); i++)
				{
					if ((sortedTypeFiles.get(i).getOtpA() != null ) && (sortedTypeFiles.get(i).getOtpB() != null ) && (sortedTypeFiles.get(i).getOtpA() > 0 ) && (sortedTypeFiles.get(i).getOtpB() > 0))
					{
						resultsSetSize++;
						rowNumber++;
						row = otpTTestSheet.createRow(rowNumber);

						// Pull below from ResultFromTTest object
						cell = row.createCell(0);
						cell.setCellStyle(style2);
						cell.setCellValue(sortedTypeFiles.get(i).getLine());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

						cell = row.createCell(1);
						cell.setCellStyle(style2);
						cell.setCellValue(sortedTypeFiles.get(i).getTypeOrGroup());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

						cell = row.createCell(2);
						cell.setCellStyle(style5);
						if (sortedTypeFiles.get(i).getOtpB() > sortedTypeFiles.get(i).getOtpA())
						{
							cell.setCellValue(sortedTypeFiles.get(i).getOtpDiff());
						}
						else
						{
							cell.setCellValue(-1 * sortedTypeFiles.get(i).getOtpDiff());
						}
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

						cell = row.createCell(3);
						cell.setCellStyle(style5);
						if (Double.isInfinite(sortedTypeFiles.get(i).getOtpTValue()))
						{
							cell.setCellValue("infinity");
						}
						else if (Double.isNaN(sortedTypeFiles.get(i).getOtpTValue()))
						{
							cell.setCellValue(0.00);
						}
						else if (sortedTypeFiles.get(i).getOtpB() > sortedTypeFiles.get(i).getOtpA())
						{
							cell.setCellValue(sortedTypeFiles.get(i).getOtpTValue());
						}
						else
						{
							cell.setCellValue(-1 * sortedTypeFiles.get(i).getOtpTValue());
						}
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

						cell = row.createCell(4);
						cell.setCellStyle(style11);
						cell.setCellValue(sortedTypeFiles.get(i).getOtpA());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

						if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
						{
							cell = row.createCell(10);
						}
						else
						{
							cell = row.createCell(8);
						}
						cell.setCellStyle(style11);
						cell.setCellValue(sortedTypeFiles.get(i).getOtpB());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);

						// Put borders around all of the significant, slight and equal cells
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 5, 5), otpTTestSheet);
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 6, 6), otpTTestSheet);
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 7, 7), otpTTestSheet);
						if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
						{
							setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 8, 8), otpTTestSheet);
							setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 9, 9), otpTTestSheet);
						}
						
						if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
						{
							// Color appropriate significance column (5 through 9)
							if (sortedTypeFiles.get(i).getOtpDiff() == 0)
							{
								// Is equal
								cell = row.createCell(7);
								cell.setCellStyle(style14);
								cell.setCellValue("");
								setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
							}
							else if (sortedTypeFiles.get(i).getOtpSignificant())
							{
								if (sortedTypeFiles.get(i).getOtpA() > sortedTypeFiles.get(i).getOtpB())
								{
									// Is significant and A > B
									cell = row.createCell(5);
									cell.setCellStyle(style12);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
								}
								else
								{
									// Is significant and B > A
									cell = row.createCell(9);
									cell.setCellStyle(style16);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
								}
							}
							else
							{
								if (sortedTypeFiles.get(i).getOtpA() > sortedTypeFiles.get(i).getOtpB())
								{
									// Is slight and A > B
									cell = row.createCell(6);
									cell.setCellStyle(style13);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
								}
								else
								{
									// Is slight and B > A
									cell = row.createCell(8);
									cell.setCellStyle(style15);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
								}
							}
						}
						else
						{
							// Color appropriate significance column (5 through 7)
							if (sortedTypeFiles.get(i).getOtpSignificant())
							{
								if (sortedTypeFiles.get(i).getOtpA() > sortedTypeFiles.get(i).getOtpB())
								{
									// Is significant and A > B
									cell = row.createCell(5);
									cell.setCellStyle(style12);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
								}
								else
								{
									// Is significant and B > A
									cell = row.createCell(7);
									cell.setCellStyle(style16);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
								}
							}
							else
							{
								// Is equal
								cell = row.createCell(6);
								cell.setCellStyle(style14);
								cell.setCellValue("");
								setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), otpTTestSheet);
							}
						}
					}
				} 	

				// Set thick borders
				setRegionBorderThick(new CellRangeAddress(lastClusterRowStart, rowNumber, 4, 4), otpTTestSheet);
				if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
				{
					setRegionBorderThick(new CellRangeAddress(lastClusterRowStart, rowNumber, 10, 10), otpTTestSheet);
				}
				else
				{
					setRegionBorderThick(new CellRangeAddress(lastClusterRowStart, rowNumber, 8, 8), otpTTestSheet);
				}
				
				rowNumber++;
				lastClusterRowStart = rowNumber + 2;
			}
		}	

		// Resize all columns to fit the content size
		for(int i = 0; i < 11; i++) 
		{
			if (i <= 4)
			{
				otpTTestSheet.autoSizeColumn(i);
				int currentWidth = otpTTestSheet.getColumnWidth(i);
				currentWidth += 360;
				otpTTestSheet.setColumnWidth(i, currentWidth);
			}
			else if ((i == 10) && (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns()))
			{
				otpTTestSheet.autoSizeColumn(i);
				int currentWidth = Math.max(otpTTestSheet.getColumnWidth(4), otpTTestSheet.getColumnWidth(10));
				currentWidth += 360;
				otpTTestSheet.setColumnWidth(4, currentWidth);
				otpTTestSheet.setColumnWidth(10, currentWidth);
			}
			else if ((i == 8) && (!BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns()))
			{
				otpTTestSheet.autoSizeColumn(i);
				int currentWidth = Math.max(otpTTestSheet.getColumnWidth(4), otpTTestSheet.getColumnWidth(8));
				currentWidth += 360;
				otpTTestSheet.setColumnWidth(4, currentWidth);
				otpTTestSheet.setColumnWidth(8, currentWidth);
			}
			else
			{
				otpTTestSheet.setColumnWidth(i, 4100);
			}
		}

		//  If no results found, remove this sheet
		if (resultsSetSize > 0)
		{
			resultsMessage += "\nWrote "+resultsSetSize+ " OTP t-test results";
		}
		else
		{
			workbook.removeSheetAt(workbook.getNumberOfSheets() - 1);
		}
	}

	class sortByGroupOrType implements Comparator<ResultFromTTest> 
	{ 
		@Override
		public int compare(ResultFromTTest o1, ResultFromTTest o2) 
		{	    
			String x1 = o1.getLine();
			String x2 = o2.getLine();

			int sComp = x1.compareTo(x2);
			if (sComp != 0) 
			{
				return sComp;
			} 

			x1 = o1.getTypeOrGroup();
			x2 = o2.getTypeOrGroup();

			return x1.compareTo(x2);
		} 
	}

	private void setRegionBorderThin(CellRangeAddress region, Sheet sheet) 
	{
		RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
		RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
		RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
	}

	private void setRegionBorderThick(CellRangeAddress region, Sheet sheet) 
	{
		RegionUtil.setBorderBottom(BorderStyle.THICK, region, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THICK, region, sheet);
		RegionUtil.setBorderRight(BorderStyle.THICK, region, sheet);
		RegionUtil.setBorderTop(BorderStyle.THICK, region, sheet);
	}

	public static XSSFWorkbook getWorkbook6()
	{
		return workbook;
	}

	public static String getResultsMessageWrite6()
	{
		return resultsMessage;
	}

	public static int getResultsSetSize6()
	{
		return resultsSetSize;
	}
}