package com.bl.bias.app;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import com.bl.bias.analyze.BridgeClosureAnalysis;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.tools.ConvertDateTime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BIASUscgBridgeComplianceAnalysisController 
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

	@FXML private Label selectProjectFileLabel;
	@FXML private Label fileNameLabel;
	@FXML private Label selectLineLabel;

	@FXML private TextArea textArea;

	@FXML private ProgressBar progressBar;

	@FXML private void initialize() 
	{
		prefs = Preferences.userRoot().node("BIAS");	
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
			if ((prefs.get("bc_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("bc_lastDirectorySavedTo", null));
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
						prefs.put("bc_lastDirectorySavedTo", file.getParent());
				} 
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}

				message = "\nStarting analysis at "+ConvertDateTime.getTimeStamp();
				displayMessage(message);

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
				selectLineLabel.setDisable(true);
				fileNameLabel.setText("");
			}	
		}
		else
		{
			DirectoryChooser directoryChooser = new DirectoryChooser();

			// See if last location is stored
			if ((prefs.get("bc_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("bc_lastDirectorySavedTo", null));
				if ((path.toFile().exists()) && (path !=null))
					directoryChooser.setInitialDirectory(path.toFile());
			}

			directoryChooser.setTitle("Select Folder");

			Stage stageForFolderChooser = (Stage) executeButton.getScene().getWindow();

			File directory = directoryChooser.showDialog(stageForFolderChooser);
			if (directory != null)
			{
				message = "\nStarting analysis at "+ConvertDateTime.getTimeStamp();
				displayMessage(message);

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put("bc_lastDirectorySavedTo", directory.toString());

				saveFileFolderForSerialFileName = directory.toString();

				selectFileButton.setDisable(true);
				selectProjectFileLabel.setDisable(true);
				executeButton.setDisable(true);
				selectLineLabel.setDisable(true);

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
				selectLineLabel.setDisable(true);
				fileNameLabel.setText("");
			}	
		}
	}

	@FXML private void handleResetButton(ActionEvent event) 
	{
		BridgeClosureAnalysis.clearCrossings();
		BridgeClosureAnalysis.clearSortedCrossings();
		BridgeClosureAnalysis.clearOccupancies();
		BridgeClosureAnalysis.clearClosures();

		resetMessage();

		progressBar.setVisible(false);
		setProgressIndicator(0.00);

		executeButton.setVisible(true);
		resetButton.setVisible(false);
		selectFileButton.setDisable(false);
		selectLineLabel.setDisable(true);
		fileNameLabel.setText("");
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
		if ((prefs.get("bc_lastDirectoryForBridgeAnalysis", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
		{
			Path path = Paths.get(prefs.get("bc_lastDirectoryForBridgeAnalysis", null));

			if ((path.toFile().exists()) && (path !=null))
				fileChooser.setInitialDirectory(path.toFile());
		}

		// Show the chooser and get the file
		Stage stageForFileChooser = (Stage) selectFileButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		// Valid .OPTION file found
		if (file != null)
		{
			// Reset checkbox status
			selectLineLabel.setDisable(true);
			executeButton.setDisable(true);

			// Write message
			clearMessage();
			message = "BIAS USCG Bridge Compliance Analysis Module - "+BIASLaunch.getSoftwareVersion()+"\n";

			// Store path for subsequent runs and set labels
			fileAsString = file.getName().toString();
			fullyQualifiedPath = file.toString();
			fileNameLabel.setText(fullyQualifiedPath);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("bc_lastDirectoryForBridgeAnalysis", file.getParent());

			displayMessage(message);
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

	private void runTask() throws InterruptedException
	{
		System.out.println("Running USCG Bridge Compliance");
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