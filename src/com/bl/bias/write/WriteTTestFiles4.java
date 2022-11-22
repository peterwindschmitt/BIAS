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

public class WriteTTestFiles4
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

	private static XSSFWorkbook workbook;

	private static XSSFSheet elapsedRunTimePerTrainAsSerialTTestSheet; 

	private static IndexedColors[] tTestColors = new IndexedColors[8];
	
	public WriteTTestFiles4(String textAreaContents, HashSet<ResultFromTTest> byTypeTTestResults, HashSet<ResultFromTTest> byGroupTTestResults)
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
		
		resultsMessage = WriteTTestFiles3.getResultsMessageWrite3();
		workbook = WriteTTestFiles3.getWorkbook3();

		if (TTestValidateTTest.getGenerateElapsedRunTimePerTrainAsSerial())
		{
			this.byTypeTTestResults = byTypeTTestResults;
			this.byGroupTTestResults = byGroupTTestResults;
			
			writeFiles4();
		}
	}

	public void writeFiles4()
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
		CellStyle style5 =  workbook.createCellStyle(); // center justify and two places after decimal
		CellStyle style6 =  workbook.createCellStyle(); // left justify and large font and bold
		CellStyle style7 =  workbook.createCellStyle(); // left justify and small font and bold
		CellStyle style8 =  workbook.createCellStyle(); // center justify and small font
		CellStyle style9 =  workbook.createCellStyle(); // center justify and large font and inverted font
		CellStyle style10 = workbook.createCellStyle(); // center justify and light gray background
		
		// Styles 12 - 16 are for color coding of t-test results
		CellStyle style12 = workbook.createCellStyle(); // center justify and index 0 background and two places after decimal
		CellStyle style13 = workbook.createCellStyle(); // center justify and index 1 background and two places after decimal
		CellStyle style14 = workbook.createCellStyle(); // center justify and index 2 background and two places after decimal
		CellStyle style15 = workbook.createCellStyle(); // center justify and index 3 background and two places after decimal
		CellStyle style16 = workbook.createCellStyle(); // center justify and index 4 background and two places after decimal

		style1.setWrapText(true); 

		style2.setAlignment(HorizontalAlignment.LEFT);

		style3.setAlignment(HorizontalAlignment.CENTER);

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

		// Write elapsed run time per train as serial files
		if  ((byGroupTTestResults.size() > 0) || (byTypeTTestResults.size() > 0))
		{
			elapsedRunTimePerTrainAsSerialTTestSheet = workbook.createSheet("Elapsed Time per Train(serial)");
			elapsedRunTimePerTrainAsSerialTTestSheet.setDisplayGridlines(false);
			Row row;
			Cell cell; 

			// Write header here
			// First row of sheet header
			row = elapsedRunTimePerTrainAsSerialTTestSheet.createRow(rowNumber);

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
			row = elapsedRunTimePerTrainAsSerialTTestSheet.createRow(rowNumber);

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
				row = elapsedRunTimePerTrainAsSerialTTestSheet.createRow(rowNumber);
				if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
				{
					elapsedRunTimePerTrainAsSerialTTestSheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 0, 10));
				}
				else
				{
					elapsedRunTimePerTrainAsSerialTTestSheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 0, 8));
				}
				
				cell = row.createCell(0);
				cell.setCellStyle(style9);
				cell.setCellValue("Elapsed Run Time per Train - Group Tests");

				// Second category header row
				rowNumber++;
				row = elapsedRunTimePerTrainAsSerialTTestSheet.createRow(rowNumber);

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
				row = elapsedRunTimePerTrainAsSerialTTestSheet.createRow(rowNumber);

				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("Line/Subdivision");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

				cell = row.createCell(1);
				cell.setCellStyle(style2);
				cell.setCellValue("Train Group");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

				cell = row.createCell(2);
				cell.setCellStyle(style3);
				cell.setCellValue("Elapsed Time Mean Diff");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

				cell = row.createCell(3);
				cell.setCellStyle(style3);
				cell.setCellValue("T-stat");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

				cell = row.createCell(4);
				cell.setCellStyle(style10);
				cell.setCellValue("Elapsed Time per Train");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

				cell = row.createCell(5);
				cell.setCellStyle(style3);
				cell.setCellValue("Significant");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

				if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
				{
					cell = row.createCell(6);
					cell.setCellStyle(style3);
					cell.setCellValue("Slight");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
	
					cell = row.createCell(7);
					cell.setCellStyle(style3);
					cell.setCellValue("Equal");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
	
					cell = row.createCell(8);
					cell.setCellStyle(style3);
					cell.setCellValue("Slight");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
	
					cell = row.createCell(9);
					cell.setCellStyle(style3);
					cell.setCellValue("Significant");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
	
					cell = row.createCell(10);
					cell.setCellStyle(style10);
					cell.setCellValue("Elapsed Time per Train");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
				}
				else
				{
					cell = row.createCell(6);
					cell.setCellStyle(style3);
					cell.setCellValue("Not Significant");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
	
					cell = row.createCell(7);
					cell.setCellStyle(style3);
					cell.setCellValue("Significant");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
	
					cell = row.createCell(8);
					cell.setCellStyle(style10);
					cell.setCellValue("Elapsed Time per Train");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
				}
				
				// Sort group files here
				sortedGroupFiles = new ArrayList<>(byGroupTTestResults);
				Collections.sort(sortedGroupFiles, new sortByGroupOrType());
				
				for (int i = 0; i < sortedGroupFiles.size(); i++)
				{
					if ((sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialA() != null)  && (sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialB() != null))
					{
						resultsSetSize++;
						rowNumber++;
						row = elapsedRunTimePerTrainAsSerialTTestSheet.createRow(rowNumber);

						// Pull below from ResultFromTTest object
						cell = row.createCell(0);
						cell.setCellStyle(style2);
						cell.setCellValue(sortedGroupFiles.get(i).getLine());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

						cell = row.createCell(1);
						cell.setCellStyle(style2);
						cell.setCellValue(sortedGroupFiles.get(i).getTypeOrGroup());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

						cell = row.createCell(2);
						cell.setCellStyle(style3);
						double diffAsSerial = sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialB() - sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialA();
						cell.setCellValue(diffAsSerial);
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

						cell = row.createCell(3);
						cell.setCellStyle(style5);
						if (Double.isInfinite(sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialTValue()))
						{
							cell.setCellValue("infinity");
						}
						else if (Double.isNaN(sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialTValue()))
						{
							cell.setCellValue(0.00);
						}
						else if (sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialB() > sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialA())
						{
							cell.setCellValue(sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialTValue());
						}
						else if (sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialA() > sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialB())
						{
							cell.setCellValue(-1 * sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialTValue());
						}
						else
							cell.setCellValue(0);
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

						cell = row.createCell(4);
						cell.setCellStyle(style10);
						cell.setCellValue(sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialA());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

						if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
						{
							cell = row.createCell(10);
						}
						else
						{
							cell = row.createCell(8);
						}
						cell.setCellStyle(style10);
						cell.setCellValue(sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialB());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

						// Put borders around all of the significant, slight and equal cells
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 5, 5), elapsedRunTimePerTrainAsSerialTTestSheet);
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 6, 6), elapsedRunTimePerTrainAsSerialTTestSheet);
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 7, 7), elapsedRunTimePerTrainAsSerialTTestSheet);
						if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
						{
							setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 8, 8), elapsedRunTimePerTrainAsSerialTTestSheet);
							setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 9, 9), elapsedRunTimePerTrainAsSerialTTestSheet);
						}
						
						if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
						{
							// Color appropriate significance column (5 through 9)
							if (sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialDiff() == 0)
							{
								// Is equal
								cell = row.createCell(7);
								cell.setCellStyle(style14);
								cell.setCellValue("");
								setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
							}
							else if (sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialSignificant())
							{
								if (sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialA() < sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialB())
								{
									// Is significant and A < B
									cell = row.createCell(5);
									cell.setCellStyle(style12);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
								}
								else
								{
									// Is significant and B < A
									cell = row.createCell(9);
									cell.setCellStyle(style16);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
								}
							}
							else
							{
								if (sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialA() < sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialB())
								{
									// Is slight and A < B
									cell = row.createCell(6);
									cell.setCellStyle(style13);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
								}
								else
								{
									// Is slight and B < A
									cell = row.createCell(8);
									cell.setCellStyle(style15);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
								}
							}
						}
						else
						{
							// Color appropriate significance column (5 through 7)
							if (sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialSignificant())
							{
								if ((sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialA()) < (sortedGroupFiles.get(i).getElapsedRunTimePerTrainAsSerialB()))
								{
									// Is significant and A < B
									cell = row.createCell(5);
									cell.setCellStyle(style12);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
								}
								else
								{
									// Is significant and B < A
									cell = row.createCell(7);
									cell.setCellStyle(style16);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
								}
							}
							else
							{
								// Is not significant
								cell = row.createCell(6);
								cell.setCellStyle(style14);
								cell.setCellValue("");
								setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
							}
						}
					}
				}

				// Set thick borders
				setRegionBorderThick(new CellRangeAddress(lastClusterRowStart, rowNumber, 4, 4), elapsedRunTimePerTrainAsSerialTTestSheet);
				if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
				{
					setRegionBorderThick(new CellRangeAddress(lastClusterRowStart, rowNumber, 10, 10), elapsedRunTimePerTrainAsSerialTTestSheet);
				}
				else
				{
					setRegionBorderThick(new CellRangeAddress(lastClusterRowStart, rowNumber, 8, 8), elapsedRunTimePerTrainAsSerialTTestSheet);
				}
				rowNumber++;
				lastClusterRowStart = rowNumber + 2;
			}
			
			// By type 
			if (byTypeTTestResults.size() > 0)
			{
				// First category header row
				rowNumber++;
				row = elapsedRunTimePerTrainAsSerialTTestSheet.createRow(rowNumber);
				if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
				{
					elapsedRunTimePerTrainAsSerialTTestSheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 0, 10));
				}
				else
				{
					elapsedRunTimePerTrainAsSerialTTestSheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 0, 8));
				}
				
				cell = row.createCell(0);
				cell.setCellStyle(style9);
				cell.setCellValue("Elapsed Run Time per Train - Type Tests");

				// Second category header row
				rowNumber++;
				row = elapsedRunTimePerTrainAsSerialTTestSheet.createRow(rowNumber);

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
				row = elapsedRunTimePerTrainAsSerialTTestSheet.createRow(rowNumber);

				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue("Line/Subdivision");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

				cell = row.createCell(1);
				cell.setCellStyle(style2);
				cell.setCellValue("Train Type");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

				cell = row.createCell(2);
				cell.setCellStyle(style3);
				cell.setCellValue("Elapsed Time Mean Diff");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

				cell = row.createCell(3);
				cell.setCellStyle(style3);
				cell.setCellValue("T-stat");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

				cell = row.createCell(4);
				cell.setCellStyle(style10);
				cell.setCellValue("Elapsed Time per Train");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

				cell = row.createCell(5);
				cell.setCellStyle(style3);
				cell.setCellValue("Significant");
				setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

				if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
				{
					cell = row.createCell(6);
					cell.setCellStyle(style3);
					cell.setCellValue("Slight");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
	
					cell = row.createCell(7);
					cell.setCellStyle(style3);
					cell.setCellValue("Equal");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
	
					cell = row.createCell(8);
					cell.setCellStyle(style3);
					cell.setCellValue("Slight");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
	
					cell = row.createCell(9);
					cell.setCellStyle(style3);
					cell.setCellValue("Significant");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
	
					cell = row.createCell(10);
					cell.setCellStyle(style10);
					cell.setCellValue("Elapsed Time per Train");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
				}
				else
				{
					cell = row.createCell(6);
					cell.setCellStyle(style3);
					cell.setCellValue("Not Significant");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
	
					cell = row.createCell(7);
					cell.setCellStyle(style3);
					cell.setCellValue("Significant");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
	
					cell = row.createCell(8);
					cell.setCellStyle(style10);
					cell.setCellValue("Elapsed Time per Train");
					setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
				}
				
				// Sort type files here
				sortedTypeFiles = new ArrayList<>(byTypeTTestResults);
				Collections.sort(sortedTypeFiles, new sortByGroupOrType());

				for (int i = 0; i < sortedTypeFiles.size(); i++)
				{
					if ((sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialA() != null)  && (sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialB() != null))
					{
						resultsSetSize++;
						rowNumber++;
						row = elapsedRunTimePerTrainAsSerialTTestSheet.createRow(rowNumber);

						// Pull below from ResultFromTTest object
						cell = row.createCell(0);
						cell.setCellStyle(style2);
						cell.setCellValue(sortedTypeFiles.get(i).getLine());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

						cell = row.createCell(1);
						cell.setCellStyle(style2);
						cell.setCellValue(sortedTypeFiles.get(i).getTypeOrGroup());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

						cell = row.createCell(2);
						cell.setCellStyle(style3);
						double diffAsSerial = (sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialB()) - sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialA();
						cell.setCellValue(diffAsSerial);
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

						cell = row.createCell(3);
						cell.setCellStyle(style5);
						if (Double.isInfinite(sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialTValue()))
						{
							cell.setCellValue("infinity");
						}
						else if (Double.isNaN(sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialTValue()))
						{
							cell.setCellValue(0.00);
						}
						else if (sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialB() > sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialA())
						{
							cell.setCellValue(sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialTValue());
						}
						else if (sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialA() > sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialB())
						{
							cell.setCellValue(-1 * sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialTValue());
						}
						else
						{
							cell.setCellValue(0);
						}
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

						cell = row.createCell(4);
						cell.setCellStyle(style10);
						cell.setCellValue(sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialA());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

						if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
						{
							cell = row.createCell(10);
						}
						else
						{
							cell = row.createCell(8);
						}
						cell.setCellStyle(style10);
						cell.setCellValue(sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialB());
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);

						// Put borders around all of the significant, slight and equal cells
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 5, 5), elapsedRunTimePerTrainAsSerialTTestSheet);
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 6, 6), elapsedRunTimePerTrainAsSerialTTestSheet);
						setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 7, 7), elapsedRunTimePerTrainAsSerialTTestSheet);
						if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
						{
							setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 8, 8), elapsedRunTimePerTrainAsSerialTTestSheet);
							setRegionBorderThin(new CellRangeAddress(rowNumber, rowNumber, 9, 9), elapsedRunTimePerTrainAsSerialTTestSheet);
						}
						
						if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
						{
							// Color appropriate significance column (5 through 9)
							if (sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialDiff() == 0)
							{
								// Is equal
								cell = row.createCell(7);
								cell.setCellStyle(style14);
								cell.setCellValue("");
								setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
							}
							else if (sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialSignificant())
							{
								if (sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialA() < sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialB())
								{
									// Is significant and A < B
									cell = row.createCell(5);
									cell.setCellStyle(style12);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
								}
								else
								{
									// Is significant and B < A
									cell = row.createCell(9);
									cell.setCellStyle(style16);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
								}
							}
							else
							{
								if (sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialA() < sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialB())
								{
									// Is slight and A < B
									cell = row.createCell(6);
									cell.setCellStyle(style13);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
								}
								else
								{
									// Is slight and B < A
									cell = row.createCell(8);
									cell.setCellStyle(style15);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
								}
							}
						}
						else
						{
							// Color appropriate significance column (5 through 7)
							if (sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialSignificant())
							{
								if ((sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialA()) < (sortedTypeFiles.get(i).getElapsedRunTimePerTrainAsSerialB()))
								{
									// Is significant and A < B
									cell = row.createCell(5);
									cell.setCellStyle(style12);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
								}
								else
								{
									// Is significant and B < A
									cell = row.createCell(7);
									cell.setCellStyle(style16);
									cell.setCellValue("");
									setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
								}
							}
							else
							{
								// Is not significant
								cell = row.createCell(6);
								cell.setCellStyle(style14);
								cell.setCellValue("");
								setRegionBorderThick(new CellRangeAddress(rowNumber, rowNumber, cell.getColumnIndex(), cell.getColumnIndex()), elapsedRunTimePerTrainAsSerialTTestSheet);
							}
						}
					}
				}

				// Set thick borders
				setRegionBorderThick(new CellRangeAddress(lastClusterRowStart, rowNumber, 4, 4), elapsedRunTimePerTrainAsSerialTTestSheet);
				if (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns())
				{
					setRegionBorderThick(new CellRangeAddress(lastClusterRowStart, rowNumber, 10, 10), elapsedRunTimePerTrainAsSerialTTestSheet);
				}
				else
				{
					setRegionBorderThick(new CellRangeAddress(lastClusterRowStart, rowNumber, 8, 8), elapsedRunTimePerTrainAsSerialTTestSheet);
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
				elapsedRunTimePerTrainAsSerialTTestSheet.autoSizeColumn(i);
				int currentWidth = elapsedRunTimePerTrainAsSerialTTestSheet.getColumnWidth(i);
				currentWidth += 360;
				elapsedRunTimePerTrainAsSerialTTestSheet.setColumnWidth(i, currentWidth);
			}
			else if ((i == 10) && (BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns()))
			{
				elapsedRunTimePerTrainAsSerialTTestSheet.autoSizeColumn(i);
				int currentWidth = Math.max(elapsedRunTimePerTrainAsSerialTTestSheet.getColumnWidth(4), elapsedRunTimePerTrainAsSerialTTestSheet.getColumnWidth(10));
				currentWidth += 360;
				elapsedRunTimePerTrainAsSerialTTestSheet.setColumnWidth(4, currentWidth);
				elapsedRunTimePerTrainAsSerialTTestSheet.setColumnWidth(10, currentWidth);
			}
			else if ((i == 8) && (!BIASTtestConfigPageController.getShowSlightAndEqualTstatColumns()))
			{
				elapsedRunTimePerTrainAsSerialTTestSheet.autoSizeColumn(i);
				int currentWidth = Math.max(elapsedRunTimePerTrainAsSerialTTestSheet.getColumnWidth(4), elapsedRunTimePerTrainAsSerialTTestSheet.getColumnWidth(8));
				currentWidth += 360;
				elapsedRunTimePerTrainAsSerialTTestSheet.setColumnWidth(4, currentWidth);
				elapsedRunTimePerTrainAsSerialTTestSheet.setColumnWidth(8, currentWidth);
			}
			else
			{
				elapsedRunTimePerTrainAsSerialTTestSheet.setColumnWidth(i, 4100);
			}
		}
		resultsMessage += "\nWrote "+resultsSetSize+ " elapsed run time per train (as serial) t-test results";
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

	public static XSSFWorkbook getWorkbook4()
	{
		return workbook;
	}
	
	public static String getResultsMessageWrite4()
	{
		return resultsMessage;
	}
	
	public static int getResultsSetSize4()
	{
		return resultsSetSize;
	}
}