package com.bl.bias.tools;

import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class ConvertAnyCellFormatToString 
{	
	public static String getCellValueAsString(Cell cell) 
	{
        System.out.println("Trying to convert in ConvertAnyCellFormatToStrint line 12");
		String strCellValue = null;
        if (cell != null) 
        {
            switch (cell.getCellType()) 
            {
	            case STRING:
	                strCellValue = cell.toString();
	                break;
	            case NUMERIC:
	                if (DateUtil.isCellDateFormatted(cell)) 
	                {
	                    SimpleDateFormat dateFormat = new SimpleDateFormat(
	                            "dd/MM/yyyy");
	                    strCellValue = dateFormat.format(cell.getDateCellValue());
	                } 
	                else 
	                {
	                    Double value = cell.getNumericCellValue();
	                    Long longValue = value.longValue();
	                    strCellValue = new String(longValue.toString());
	                }
	                break;
	            case BOOLEAN:
	                strCellValue = new String(new Boolean(cell.getBooleanCellValue()).toString());
	                break;
	            case BLANK:
	                strCellValue = "";
	                break;
	                
	            //  These should never occur
				case ERROR:
					break;
				case FORMULA:
					break;
				case _NONE:
					break;
				default:
					break;
            }
        }
        return strCellValue;
    }
}