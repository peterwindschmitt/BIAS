package com.bl.bias.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.read.ReadExcelFileForConversionToRadixxResSSIM;
import com.bl.bias.read.ReadRadixxResSSIMIATAFileForConversionToExcel;
import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.write.WriteExcelToRadixxFile1;
import com.bl.bias.write.WriteRadixxIATAToExcelFiles2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BIASRadixxResSsimConversionPageIATAExcelController 
{
	private static String saveFileLocationForUserSpecifiedFileNameToSpreadsheet;
	private static String saveFileLocationForUserSpecifiedFileNameToRadixx;
	private static String saveFileFolderForSerialFileName;
	private static String fileAsString;
	private static String fullyQualifiedPath;
	private static String message = "";

	private static Preferences prefs;

	private static Boolean continueAnalysis = true;
	private static Boolean ssimToExcel = true;
	private static Boolean excelToSsim = false;

	@FXML private Button selectFileButton;
	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private TextArea textArea;

	@FXML private Label step1Label;
	@FXML private Label	fileNameLabel;
	@FXML private Label	orLabel;
	@FXML private Label	convertLabel;

	@FXML private RadioButton convertSsimToExcelRadioButton;
	@FXML private RadioButton convertExcelToSsimRadioButton;

	@FXML private ProgressIndicator progressBar;

	@FXML private void initialize() 
	{
		prefs = Preferences.userRoot().node("BIAS");
	}

	private void chooseFile()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");

		if (ssimToExcel)
		{
			FileChooser.ExtensionFilter fileExtensions = 
					new FileChooser.ExtensionFilter(
							"SSIM Files", "*.txt");
			fileChooser.getExtensionFilters().add(fileExtensions);		
		}
		else if (excelToSsim)
		{
			FileChooser.ExtensionFilter fileExtensions = 
					new FileChooser.ExtensionFilter(
							"Excel", "*.xlsx");
			fileChooser.getExtensionFilters().add(fileExtensions);		
		}

		// See if last directory is stored
		if ((prefs.get("sc_lastDirectoryForRadixxResSsimConversion", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
		{
			Path path = Paths.get(prefs.get("sc_lastDirectoryForRadixxResSsimConversion", null));

			if ((path.toFile().exists()) && (path !=null))
				fileChooser.setInitialDirectory(path.toFile());
		}

		// Show the chooser and get the file
		Stage stageForFileChooser = (Stage) selectFileButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		// Valid input file found
		if (file != null) 
		{
			executeButton.setDisable(false);

			// Write message
			clearMessage();
			message = "BIAS Radixx Res SSIM (IATA)/Excel Conversion Module - "+BIASLaunch.getSoftwareVersion()+"\n";

			// Store path for subsequent runs and set labels
			fileAsString = file.getName().toString();
			fullyQualifiedPath = file.toString();
			fileNameLabel.setText(fullyQualifiedPath);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("sc_lastDirectoryForRadixxResSsimConversion", file.getParent());
			displayMessage(message);
		}                   
	}

	private void startTask()
	{
		Runnable task = new Runnable()
		{
			@Override
			public void run() 
			{
				try
				{
					runTask();
				}
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}
			}
		};

		Thread backgroundThread = new Thread(task);
		backgroundThread.setDaemon(true);
		backgroundThread.start();
	}

	private void runTask() throws Exception
	{
		// SSIM to Excel
		if (ssimToExcel)
		{
			// Read all objects that are required for the conversion from Radixx to Excel
			ReadRadixxResSSIMIATAFileForConversionToExcel readData = new ReadRadixxResSSIMIATAFileForConversionToExcel(fullyQualifiedPath);
			message = readData.getResultsMessage();
			displayMessage(message+"\n");

			// Write Results
			if (continueAnalysis)
			{
				writeFiles();
			}

			if (!WriteRadixxIATAToExcelFiles2.getErrorFound())
			{
				setProgressIndicator(1.0);
				displayMessage("\n*** PROCESSING COMPLETE ***");
			}
			else
			{
				displayMessage("\nError in writing files");
				displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
			}
			displayMessage("\n");
		}
		
		// Excel to SSIM
		else if (excelToSsim)
		{
			ReadExcelFileForConversionToRadixxResSSIM readData = new ReadExcelFileForConversionToRadixxResSSIM(fullyQualifiedPath);
			if (readData.getValidFile())
			{
				WriteExcelToRadixxFile1 writeData = new WriteExcelToRadixxFile1(readData.getSsimText());			
				message = readData.getResultsMessage().concat(writeData.getResultsMessageWrite1());
				displayMessage(message+"\n");
				
				if (!WriteExcelToRadixxFile1.getErrorFound())
				{
					setProgressIndicator(1.0);
					displayMessage("*** PROCESSING COMPLETE ***");
				}
				else
				{
					displayMessage("\nError in writing files");
					displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
				}
			}
			else
			{
				message = readData.getResultsMessage();
				displayMessage(message+"\n");
				displayMessage("*** PROCESSING NOT COMPLETE!!! ***");
			}
			displayMessage("\n");
		}

		// Prepare for next run
		executeButton.setVisible(false);
		resetButton.setVisible(true);
		resetButton.setDisable(false);
	}

	@FXML private void handleConvertSsimToExcelRadioButton(ActionEvent event) throws IOException
	{
		ssimToExcel = true;
		excelToSsim = false;
		
		resetMessage();

		executeButton.setDisable(true);
		executeButton.setVisible(true);
		resetButton.setVisible(false);
		selectFileButton.setDisable(false);

		fileNameLabel.setText("");
	}

	@FXML private void handleConvertExceltoSsimRadioButton(ActionEvent event) throws IOException
	{
		ssimToExcel = false;
		excelToSsim = true;
		
		resetMessage();

		executeButton.setDisable(true);
		executeButton.setVisible(true);
		resetButton.setVisible(false);
		selectFileButton.setDisable(false);

		fileNameLabel.setText("");
	}

	@FXML private void handleSelectFileButton(ActionEvent event) throws IOException
	{
		chooseFile();
	}

	@FXML private void handleExecuteButton(ActionEvent event) throws IOException
	{
		// Get location to save file to if not using system time as file name
		if (!BIASGeneralConfigController.getUseSerialTimeAsFileName())
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Location to Save Results");

			// Check if previous location is available
			if ((prefs.get("sc_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("sc_lastDirectorySavedTo", null));
				if ((path.toFile().exists()) && (path !=null))
				{
					fileChooser.setInitialDirectory(path.toFile());
				}
			}

			Stage stageForFolderChooser = (Stage) executeButton.getScene().getWindow();

			File file = null;

			file = fileChooser.showSaveDialog(stageForFolderChooser);

			if (file != null) 
			{
				try 
				{
					if (ssimToExcel)
						saveFileLocationForUserSpecifiedFileNameToSpreadsheet = file.toString();
					else
						saveFileLocationForUserSpecifiedFileNameToRadixx = file.toString();
					
					if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
						prefs.put("sc_lastDirectorySavedTo", file.getParent());
				} 
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}

				message = "\nStarting analysis of "+fileAsString.replace(".txt", "")+" SSIM file at "+ConvertDateTime.getTimeStamp()+" on "+ConvertDateTime.getDateStamp();
				displayMessage(message);

				progressBar.setVisible(true);
				setProgressIndicator(0.00);
				convertExcelToSsimRadioButton.setDisable(true);
				convertSsimToExcelRadioButton.setDisable(true);
				convertLabel.setDisable(true);
				orLabel.setDisable(true);
				executeButton.setDisable(true);
				selectFileButton.setDisable(true);

				continueAnalysis = true;

				startTask();
			}
			else
			{
				//  Did not commit file to save so reset
				resetMessage();

				executeButton.setDisable(true);
				executeButton.setVisible(true);
				resetButton.setVisible(false);
				selectFileButton.setDisable(false);

				fileNameLabel.setText("");
			}	
		}
		else
		{
			DirectoryChooser directoryChooser = new DirectoryChooser();

			// See if last location is stored
			if ((prefs.get("sc_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("sc_lastDirectorySavedTo", null));
				if ((path.toFile().exists()) && (path !=null))
					directoryChooser.setInitialDirectory(path.toFile());
			}

			directoryChooser.setTitle("Select Folder");

			Stage stageForFolderChooser = (Stage) executeButton.getScene().getWindow();

			File directory = directoryChooser.showDialog(stageForFolderChooser);
			if (directory != null)
			{
				message = "\nStarting analysis of "+fileAsString.replace(".txt", "")+" SSIM file at "+ConvertDateTime.getTimeStamp()+" on "+ConvertDateTime.getDateStamp();
				displayMessage(message);

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put("sc_lastDirectorySavedTo", directory.toString());

				saveFileFolderForSerialFileName = directory.toString();

				progressBar.setVisible(true);
				setProgressIndicator(0.00);
				convertExcelToSsimRadioButton.setDisable(true);
				convertSsimToExcelRadioButton.setDisable(true);
				convertLabel.setDisable(true);
				orLabel.setDisable(true);
				executeButton.setDisable(true);
				selectFileButton.setDisable(true);

				continueAnalysis = true;

				startTask();
			}
			else
			{
				//  Did not commit file to save so reset
				resetMessage();

				executeButton.setDisable(true);
				executeButton.setVisible(true);
				resetButton.setVisible(false);
				selectFileButton.setDisable(false);

				fileNameLabel.setText("");
			}	
		}
	}

	@FXML private void handleResetButton(ActionEvent event) throws IOException
	{
		resetMessage();

		progressBar.setVisible(false);
		setProgressIndicator(0.00);

		convertLabel.setDisable(false);
		
		orLabel.setDisable(false);
		convertExcelToSsimRadioButton.setDisable(false);
		
		convertSsimToExcelRadioButton.setDisable(false);
		convertSsimToExcelRadioButton.setSelected(true);
		ssimToExcel = true;
		excelToSsim = false;
		executeButton.setVisible(true);
		resetButton.setVisible(false);
		selectFileButton.setDisable(false);
		fileNameLabel.setText("");
	}

	private void writeFiles() 
	{
		WriteRadixxIATAToExcelFiles2 fileToConvert = new WriteRadixxIATAToExcelFiles2(textArea.getText(), saveFileLocationForUserSpecifiedFileNameToSpreadsheet, fileAsString, ReadRadixxResSSIMIATAFileForConversionToExcel.getSchedule());
		displayMessage(fileToConvert.getResultsMessageWrite2());
	}

	private void resetMessage()
	{
		message="";
		textArea.setText("Select processing options...");
	}

	private void clearMessage()
	{
		message="";
		textArea.setText("");
	}

	private void displayMessage(String message)
	{
		textArea.appendText(message);
	}

	private void setProgressIndicator(double value)
	{
		progressBar.setProgress(value);
	}

	public static String getSaveFileFolderForSerialFileName() 
	{
		return saveFileFolderForSerialFileName;
	}

	public static String getSaveFileLocationForUserSpecifiedFileNameToSpreadsheet()
	{
		if (!saveFileLocationForUserSpecifiedFileNameToSpreadsheet.toLowerCase().endsWith(".xlsx"))
		{
			saveFileLocationForUserSpecifiedFileNameToSpreadsheet += ".xlsx";
		}

		return saveFileLocationForUserSpecifiedFileNameToSpreadsheet;
	}
	
	public static String getSaveFileLocationForUserSpecifiedFileNameToRadixx()
	{
		if (!saveFileLocationForUserSpecifiedFileNameToRadixx.toLowerCase().endsWith(".txt"))
		{
			saveFileLocationForUserSpecifiedFileNameToRadixx += ".txt";
		}

		return saveFileLocationForUserSpecifiedFileNameToRadixx;
	}
}