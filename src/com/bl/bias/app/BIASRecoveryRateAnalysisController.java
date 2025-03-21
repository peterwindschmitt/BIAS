package com.bl.bias.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import com.bl.bias.analyze.RecoveryRateAnalysis;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.read.ReadRecoveryRateAnalysisFiles;
import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.write.WriteRecoveryRateFiles6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BIASRecoveryRateAnalysisController 
{
	private static String fileAsString;
	private static String fullyQualifiedPath;
	private static String message = "";
	private static String saveFileLocationForUserSpecifiedFileName;
	private static String saveFileFolderForSerialFileName;

	private static Preferences prefs;

	private static Boolean continueAnalysis = true;

	@FXML private Button selectFileButton;
	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private Label step1Label;
	@FXML private Label selectProjectFileLabel;
	@FXML private Label fileNameLabel;
	@FXML private Label modifyTrainFileWarningLabel;

	@FXML private TextArea textArea;

	@FXML private ProgressBar progressBar;

	@FXML private void initialize()
	{
		prefs = Preferences.userRoot().node("BIAS");	
		
		modifyTrainFileWarningLabel.setVisible(false);
	}

	@FXML private void handleSelectFileButton(ActionEvent event) 
	{
		chooseFile();
	}

	@FXML private void handleExecuteButton(ActionEvent event) 
	{
		// Get location to save file to if not using system time as file name
		if (!BIASGeneralConfigController.getUseSerialTimeAsFileName())
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Location to Save Results");

			// Check if previous location is available
			if ((prefs.get("rr_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("rr_lastDirectorySavedTo", null));
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
					saveFileLocationForUserSpecifiedFileName = file.toString();
					if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
						prefs.put("rr_lastDirectorySavedTo", file.getParent());
				} 
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}

				message = "\nStarting Recovery Rate Analysis at "+ConvertDateTime.getTimeStamp();
				displayMessage(message);

				step1Label.setDisable(true);
				selectFileButton.setDisable(true);
				selectProjectFileLabel.setDisable(true);
				executeButton.setDisable(true);

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
				modifyTrainFileWarningLabel.setVisible(false);
			}	
		}
		else
		{
			DirectoryChooser directoryChooser = new DirectoryChooser();

			// See if last location is stored
			if ((prefs.get("rr_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("rr_lastDirectorySavedTo", null));
				if ((path.toFile().exists()) && (path !=null))
					directoryChooser.setInitialDirectory(path.toFile());
			}

			directoryChooser.setTitle("Select Folder");

			Stage stageForFolderChooser = (Stage) executeButton.getScene().getWindow();

			File directory = directoryChooser.showDialog(stageForFolderChooser);
			if (directory != null)
			{
				message = "\nStarting Recovery Rate Analysis at "+ConvertDateTime.getTimeStamp();
				displayMessage(message);

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put("rr_lastDirectorySavedTo", directory.toString());

				saveFileFolderForSerialFileName = directory.toString();

				step1Label.setDisable(true);
				selectFileButton.setDisable(true);
				selectProjectFileLabel.setDisable(true);
				executeButton.setDisable(true);

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
				modifyTrainFileWarningLabel.setVisible(false);
			}	
		}
	}

	@FXML private void handleResetButton(ActionEvent event) 
	{
		resetMessage();

		progressBar.setVisible(false);
		setProgressIndicator(0.00);

		step1Label.setDisable(false);
		selectProjectFileLabel.setDisable(false);
		executeButton.setVisible(true);
		resetButton.setVisible(false);
		selectFileButton.setDisable(false);
		fileNameLabel.setText("");
		modifyTrainFileWarningLabel.setVisible(false);
	}

	private void chooseFile()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		FileChooser.ExtensionFilter fileExtensions = 
				new FileChooser.ExtensionFilter(
						"RTC Option Files", "*.OPTION");

		fileChooser.getExtensionFilters().add(fileExtensions);		

		// See if last directory is stored
		if ((prefs.get("rr_lastDirectoryForRecoveryAnalysis", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
		{
			Path path = Paths.get(prefs.get("rr_lastDirectoryForRecoveryAnalysis", null));

			if ((path.toFile().exists()) && (path !=null))
				fileChooser.setInitialDirectory(path.toFile());
		}

		// Show the chooser and get the file
		Stage stageForFileChooser = (Stage) selectFileButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		// Valid .OPTION file found
		if (file != null)
		{
			Boolean routeFileFound = false;
			Boolean trainFileFound = false;

			executeButton.setDisable(true);

			// Write message
			clearMessage();
			message = "BIAS Recovery Rate Analysis Module - "+BIASLaunch.getSoftwareVersion()+"\n";

			// Store path for subsequent runs and set labels
			fileAsString = file.getName().toString();
			fullyQualifiedPath = file.toString();
			fileNameLabel.setText(fullyQualifiedPath);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("rr_lastDirectoryForRecoveryAnalysis", file.getParent());

			// Check that .ROUTE file exists
			File routeFile = new File(file.getParent(), fileAsString.replace(".OPTION", ".ROUTE"));
			if (routeFile.exists())
				routeFileFound = true;
			else
				message += "\n.ROUTE file is missing!";

			// Check that .TRAIN file exists
			File trainFile = new File(file.getParent(), fileAsString.replace(".OPTION", ".TRAIN"));
			if (trainFile.exists())
				trainFileFound = true;
			else
				message += "\n.TRAIN file is missing!";

			if ((routeFileFound) && (trainFileFound))
			{
				executeButton.setDisable(false);
			}
			else
				message += "\n\nUnable to perform analysis due to missing file(s)";

			displayMessage(message);
			
			if ((BIASRecoveryRateAnalysisConfigController.getExcludeTrainsBelowThresholdSetA()) 
				|| (BIASRecoveryRateAnalysisConfigController.getExcludeTrainsBelowThresholdSetB()) 
				|| (BIASRecoveryRateAnalysisConfigController.getExcludeTrainsBelowThresholdSetC())
				|| (BIASRecoveryRateAnalysisConfigController.getExcludeTrainsBelowThresholdSetD()))
			{
				// Add label that .TRAIN file may be modified
				modifyTrainFileWarningLabel.setVisible(true);
			}
		}
	}                     

	private void startTask()
	{
		progressBar.setVisible(true);

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

	private void runTask() throws InterruptedException, IOException
	{
		// Check date/time format, verbose .ROUTE file, output format and ENGLISH input units
		File optionFile = new File(fullyQualifiedPath);
		File optionFileFolder = new File(optionFile.getParent());
		BIASValidateOptionsAndINIFileSchemeA.bIASCheckOptionFiles(optionFileFolder);
		if (BIASValidateOptionsAndINIFileSchemeA.getOptionsFilesFormattedCorrectly())
		{
			message = "\nValidated date/time format, verbose .ROUTE file, output format and speed/distance \nunits from .OPTION file\n";
		}
		else
		{
			message = "\nInvalid date/time format, verbose .ROUTE file, output format, speed/distance \nunits, invalid .OPTION file and/or invalid count of .OPTION files\n";
			continueAnalysis = false;
		}
		displayMessage(message);

		if (continueAnalysis)
		{
			// Ensure that there are valid groups and node pairs
			if (((BIASRecoveryRateAnalysisConfigController.getSetARecoveryRateAnalysisTrainGroups() != null) 
					&& (!BIASRecoveryRateAnalysisConfigController.getSetARecoveryRateAnalysisTrainGroups().equals("")) 
					&& (BIASRecoveryRateAnalysisConfigController.getAnalyzeSetA() == true)
					&& (BIASRecoveryRateAnalysisConfigController.getSetARecoveryRateAnalysisNodePairs() != null)) ||
					((BIASRecoveryRateAnalysisConfigController.getSetBRecoveryRateAnalysisTrainGroups() != null)
							&& (!BIASRecoveryRateAnalysisConfigController.getSetBRecoveryRateAnalysisTrainGroups().equals("")) 
							&& (BIASRecoveryRateAnalysisConfigController.getAnalyzeSetB() == true)
							&& (BIASRecoveryRateAnalysisConfigController.getSetBRecoveryRateAnalysisNodePairs() != null)) |
					((BIASRecoveryRateAnalysisConfigController.getSetCRecoveryRateAnalysisTrainGroups() != null)
							&& (!BIASRecoveryRateAnalysisConfigController.getSetCRecoveryRateAnalysisTrainGroups().equals("")) 
							&& (BIASRecoveryRateAnalysisConfigController.getAnalyzeSetC() == true)
							&& (BIASRecoveryRateAnalysisConfigController.getSetCRecoveryRateAnalysisNodePairs() != null)) ||
					((BIASRecoveryRateAnalysisConfigController.getSetDRecoveryRateAnalysisTrainGroups() != null)
							&& (!BIASRecoveryRateAnalysisConfigController.getSetDRecoveryRateAnalysisTrainGroups().equals("")) 
							&& (BIASRecoveryRateAnalysisConfigController.getAnalyzeSetD() == true)
							&& (BIASRecoveryRateAnalysisConfigController.getSetDRecoveryRateAnalysisNodePairs() != null)))

			{
				// Read all objects that are required for the recovery rate analysis
				ReadRecoveryRateAnalysisFiles readData = new ReadRecoveryRateAnalysisFiles(fullyQualifiedPath);
				message = readData.getResultsMessage();
				displayMessage(message);

				setProgressIndicator(0.40);

				if (continueAnalysis)
				{
					// Analyze trains' recovery rates
					RecoveryRateAnalysis analyze = new RecoveryRateAnalysis();
					message = analyze.getResultsMessage();
					displayMessage(message);

					setProgressIndicator(0.80);

					// Write results to spreadsheet
					WriteRecoveryRateFiles6 writeFiles = new WriteRecoveryRateFiles6(textArea.getText().toString(), fullyQualifiedPath);
					message = writeFiles.getResultsWriteMessage6();
					displayMessage(message);

					if (!WriteRecoveryRateFiles6.getErrorFound())
					{
						setProgressIndicator(1.0);
						displayMessage("\n*** PROCESSING COMPLETE ***");
					}
					else
					{
						displayMessage("\nError in writing files");
						displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
					}
				}
				else
				{
					displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
				}
			}
			else
			{
				displayMessage("\nMust select analyzing at least one set, at least one defined group\n and at least one defined node pair to run analysis");
				displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
			}
		}
		else
		{
			displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
		}

		//  Now reset for next case
		executeButton.setVisible(false);
		resetButton.setVisible(true);
		resetButton.setDisable(false);
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

	public static String getSaveFileLocationForUserSpecifiedFileName()
	{
		if (!saveFileLocationForUserSpecifiedFileName.toLowerCase().endsWith(".xlsx"))
		{
			saveFileLocationForUserSpecifiedFileName += ".xlsx";
		}

		return saveFileLocationForUserSpecifiedFileName;
	}

	public static String getSaveFileFolderForSerialFileName()
	{
		return saveFileFolderForSerialFileName;
	}
}