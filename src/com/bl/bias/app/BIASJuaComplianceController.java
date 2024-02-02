package com.bl.bias.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import com.bl.bias.read.ReadJuaComplianceFiles;
import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.write.WriteJuaComplianceFiles3;
import com.bl.bias.analyze.AnalyzeJuaComplianceFiles;
import com.bl.bias.exception.ErrorShutdown;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BIASJuaComplianceController 
{
	@FXML private Button fileLocationButton;
	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private Label selectFileLocationLabel;
	@FXML private Label fileNameLabel;

	@FXML private TextArea textArea;

	@FXML private ProgressBar progressBar;

	private static Preferences prefs;

	private static String saveFileLocationForUserSpecifiedFileName;
	private static String saveFileFolderForSerialFileName;
	private static String fileAsString;
	private static String fullyQualifiedPath;
	private static String message = "";

	private static Boolean continueAnalysis = true;

	@FXML private void initialize() throws IOException
	{
		prefs = Preferences.userRoot().node("BIAS");	
	}

	@FXML private void handleExecuteButton(ActionEvent event) throws IOException 
	{
		// Get location to save file to if not using system time as file name
		if (!BIASGeneralConfigController.getUseSerialTimeAsFileName())
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Location to Save Results");

			// Check if previous location is available
			if ((prefs.get("ju_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("ju_lastDirectorySavedTo", null));
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
						prefs.put("ju_lastDirectorySavedTo", file.getParent());
				} 
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}

				message = "\nStarting JUA Compliance Analysis at "+ConvertDateTime.getTimeStamp();
				displayMessage(message);

				selectFileLocationLabel.setDisable(true);
				fileLocationButton.setDisable(true);
				fileNameLabel.setDisable(true);
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
				fileLocationButton.setDisable(false);
				selectFileLocationLabel.setDisable(false);
				fileNameLabel.setText("");
				fileNameLabel.setDisable(false);
			}	
		}
		else
		{
			DirectoryChooser directoryChooser = new DirectoryChooser();

			// See if last location is stored
			if ((prefs.get("ju_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("ju_lastDirectorySavedTo", null));
				if ((path.toFile().exists()) && (path !=null))
					directoryChooser.setInitialDirectory(path.toFile());
			}

			directoryChooser.setTitle("Select Folder");

			Stage stageForFolderChooser = (Stage) executeButton.getScene().getWindow();

			File directory = directoryChooser.showDialog(stageForFolderChooser);
			if (directory != null)
			{
				message = "\nStarting JUA Compliance Analysis at "+ConvertDateTime.getTimeStamp();
				displayMessage(message);

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put("ju_lastDirectorySavedTo", directory.toString());

				saveFileFolderForSerialFileName = directory.toString();

				selectFileLocationLabel.setDisable(true);

				fileLocationButton.setDisable(true);
				fileNameLabel.setDisable(true);
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
				selectFileLocationLabel.setDisable(false);
				fileLocationButton.setDisable(false);
				fileNameLabel.setText("");
				fileNameLabel.setDisable(false);
			}	
		}
	}

	@FXML private void handleResetButton(ActionEvent event) throws IOException 
	{
		continueAnalysis = true;

		resetMessage();

		progressBar.setVisible(false);
		setProgressIndicator(0.00);

		executeButton.setVisible(true);
		resetButton.setVisible(false);
		fileLocationButton.setDisable(false);
		selectFileLocationLabel.setDisable(false);
		fileNameLabel.setText("");
		fileNameLabel.setDisable(false);
	}

	@FXML private void handleFileLocationButton(ActionEvent event) throws IOException 
	{
		chooseFile();
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
		// Check that at least one check is selected in the Compliance Controller
		if ((BIASJuaComplianceConfigController.getCheckPermitsEnabled() 
			&& BIASJuaComplianceConfigController.getCheckPermitsSumOfLinearMiles()) 			// Make sure a subtest is selected if Permits check is enabled
				|| BIASJuaComplianceConfigController.getCheckEnabledCountOfTrains())// and/or trains  
		{
			// Read all objects that are required for the recovery rate analysis
			message = "\n\nFor case "+fileAsString.replace(".OPTION", "")+":";
			displayMessage(message);
			ReadJuaComplianceFiles readData = new ReadJuaComplianceFiles(fullyQualifiedPath, BIASJuaComplianceConfigController.getCheckEnabledCountOfTrains(), BIASJuaComplianceConfigController.getCheckPermitsEnabled());
			message = readData.getResultsMessage();
			displayMessage(message);

			if (readData.getFormattedCorrectly())
			{
				setProgressIndicator(0.33);

				if (((BIASJuaComplianceConfigController.getCheckEnabledCountOfTrains()) && (ReadJuaComplianceFiles.getTrainsToAnalyzeThisCase().size() == 0)) 	// Request to analyze trains but no trains found
					|| ((BIASJuaComplianceConfigController.getCheckPermitsEnabled()) && (ReadJuaComplianceFiles.getPermitsToAnalyzeThisCase().size() == 0))) 	// Request to analyze permits but no permits found																	
					continueAnalysis = false;

				if (continueAnalysis)
				{
					//  Perform Analysis
					AnalyzeJuaComplianceFiles analyzeData = new AnalyzeJuaComplianceFiles();
					message = analyzeData.getResultsMessage();
					displayMessage(message);

					setProgressIndicator(0.66);

					WriteJuaComplianceFiles3 writeFiles = new WriteJuaComplianceFiles3(textArea.getText().toString(), fullyQualifiedPath);
					message = writeFiles.getResultsWriteMessage3();
					displayMessage(message);
					if (!WriteJuaComplianceFiles3.getErrorFound())
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
					message += "\nUnable to perform analysis due to no trains in read in";
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
		else
		{
			// No compliance checks selected
			displayMessage("\nMust select at least one JUA Compliance check \n  in the JUA Compliance Configuration to run analysis");
			displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");

			//  Now reset for next case
			executeButton.setVisible(false);
			resetButton.setVisible(true);
			resetButton.setDisable(false);
		}
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
		if ((prefs.get("ju_lastDirectoryForJuaCompliance", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
		{
			Path path = Paths.get(prefs.get("ju_lastDirectoryForJuaCompliance", null));

			if ((path.toFile().exists()) && (path !=null))
				fileChooser.setInitialDirectory(path.toFile());
		}

		// Show the chooser and get the file
		Stage stageForFileChooser = (Stage) fileLocationButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		// Valid .OPTION file found
		if (file != null)
		{
			Boolean trainFileFound = false;

			executeButton.setDisable(true);

			// Write message
			clearMessage();
			message = "BIAS JUA Compliance Module - "+BIASLaunch.getSoftwareVersion()+"\n";

			// Store path for subsequent runs and set labels
			fileAsString = file.getName().toString();
			fullyQualifiedPath = file.toString();
			fileNameLabel.setText(fullyQualifiedPath);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("ju_lastDirectoryForJuaCompliance", file.getParent());

			// Check that .TRAIN file exists
			File trainFile = new File(file.getParent(), fileAsString.replace(".OPTION", ".TRAIN"));
			if (trainFile.exists())
				trainFileFound = true;
			else
				message += "\n.TRAIN file is missing!";

			if (trainFileFound)
			{
				executeButton.setDisable(false);
			}
			else
				message += "\n\nUnable to perform analysis due to missing file(s)";

			displayMessage(message);
		}
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

	public static String getCaseNameAsString()
	{
		return fileAsString.replace(".OPTION", "");
	}
}