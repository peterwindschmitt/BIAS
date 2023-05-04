package com.bl.bias.objects;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bl.bias.app.BIASLaunch;
import com.bl.bias.app.BIASParseConfigPageController;
import com.bl.bias.exception.ErrorShutdown;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RadixxHeaderOutput 
{
	private String defaultHeaderPart1;
	private String defaultHeaderPart2;
	private String modifiedHeaderPart1;
	private String header;

	protected FileInputStream fis;	   
	
	protected String pattern_3digits = "\\A\\d{3}\\z";  // Required regex format for three digits (as in data set serial number)

	private Boolean validInput = true; 

	public RadixxHeaderOutput(String file, XSSFWorkbook wb, XSSFSheet sheet) throws Exception
	{
		try
		{
			// Get dataSetSerialNumber from cell B6	
			String dataSetSerialNumber = sheet.getRow(5).getCell(1).toString().trim();

			// Create header
			defaultHeaderPart1 = 
					"1AIRLINE STANDARD SCHEDULE DATA SET                                                                                                                                                            ***000001";

			defaultHeaderPart2 = 
					"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n"+
							"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n"+
							"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\n"+
							"00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

			if (replaceData(defaultHeaderPart1, dataSetSerialNumber))
			{
				assembleHeader();
			}
			else
			{
				Platform.runLater(new Runnable()
				{
					@Override
					public void run() 
					{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText(null);
						alert.setContentText("Header record cannot be properly created.");		
						alert.showAndWait();
					}
				});
			}
		}
		catch (Exception e) 
		{
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
		}	
	}

	private Boolean replaceData(String defaultHeaderPart1, String dataSetSerialNumber)
	{
		Pattern r = Pattern.compile(pattern_3digits);
		Matcher m = r.matcher(dataSetSerialNumber);
		if (m.find())
		{
			StringBuffer newString = new StringBuffer(defaultHeaderPart1);

			// Remove contents from default
			newString.replace(Integer.valueOf(BIASParseConfigPageController.z_getHdr_dataSetSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getHdr_dataSetSerialNumber()[1]), "");

			// Insert the dataSetSerialNumber
			newString.insert(Integer.valueOf(BIASParseConfigPageController.z_getHdr_dataSetSerialNumber()[0]), dataSetSerialNumber);
			modifiedHeaderPart1 = newString.toString();
		}
		else
			validInput = false;

		return validInput;
	}

	private void assembleHeader()
	{
		header = modifiedHeaderPart1.concat("\n").concat(defaultHeaderPart2);
	}

	public String getHeader() 
	{
		return header;
	}

	public Boolean getValidHeader() 
	{
		return validInput;
	}

	public void printHeader()
	{
		System.out.println(header);
	}
}