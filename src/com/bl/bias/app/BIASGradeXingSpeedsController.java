package com.bl.bias.app;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.prefs.Preferences;

import com.bl.bias.analyze.GradeXingSpeedsAnalysis;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.read.ReadGradeXingAnalysisFiles;
import com.bl.bias.tools.AssignTrainTypeNameToTrainGroupName;
import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.write.WriteGradeXingFiles4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BIASGradeXingSpeedsController 
{
	private static String fileAsString;
	private static String fullyQualifiedPath;

	private static File directory;
	private static File lastDirectory;
	private static File saveFileLocation;
	private static String saveFileLocationForUserSpecifiedFileName;
	private static String saveFileFolderForSerialFileName;

	private static String message = "";
	
	private static String lineToAnalyze = "";

	private static Preferences prefs;

	private static Boolean continueAnalysis = true;

	BIASPreprocessTrainsForGradeXingSpeedAnalysis getPrelimDataTPC;
	BIASPreprocessLinesForMaintenanceWindowAndGradeXingSpeedAnalysis getPrelimDataLines;
	AssignTrainTypeNameToTrainGroupName getPrelimDataGroupNames;

	@FXML private Button selectFileButton;
	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private Label selectProjectFileLabel;
	@FXML private Label fileNameLabel;
	@FXML private Label trainsInTpcFileLabel;
	@FXML private Label lineLabel;

	@FXML private TextArea processTextArea;
	@FXML private TextArea trainsInTpcFileTextArea;

	@FXML private ProgressBar progressBar;

	@FXML private ComboBox<String> lineComboBox;

	@FXML private void initialize() 
	{
		prefs = Preferences.userRoot().node("BIAS");
	}

	@FXML private void handleSelectFileButton(ActionEvent event) 
	{
		chooseFile();
	}

	@FXML private void handleLineComboBox(ActionEvent event) 
	{
		lineToAnalyze =lineComboBox.getValue();
		if (lineToAnalyze == null)
		{
			executeButton.setDisable(true);
		}
		else
		{
			executeButton.setDisable(false);
		}
	}

	@FXML private void handleExecuteButton(ActionEvent event) 
	{
		// Get location to save file to if not using system time as file name
		if (!BIASGeneralConfigController.getUseSerialTimeAsFileName())
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Location to Save Results");

			// Check if previous location is available
			if ((prefs.get("gx_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("gx_lastDirectorySavedTo", null));
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
						prefs.put("gx_lastDirectorySavedTo", file.getParent());
				} 
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}
				
				// Ready to execute
				message = "\nFound "+getPrelimDataTPC.returnAvailableTrains().size()+" trains in TPC file with a reporting increment of "+getPrelimDataTPC.returnTPCIncrementsIncludingUnits().toArray()[0].toString();
				message += "\nSet to perform grade crossing speed analysis on "+lineToAnalyze+" line ";
				message += "\n  from case "+fileAsString.replace(".TPC", "");
				message += "\nStarting analysis of grade crossing speeds at "+ConvertDateTime.getTimeStamp()+" on "+ConvertDateTime.getDateStamp();
				displayMessage(message);

				progressBar.setVisible(true);
				setProgressIndicator(0.00);
				executeButton.setDisable(true);
				selectFileButton.setDisable(true);
				selectProjectFileLabel.setDisable(true);
				trainsInTpcFileLabel.setDisable(true);
				trainsInTpcFileTextArea.setDisable(true);
				lineLabel.setDisable(true);
				lineComboBox.setDisable(true);

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
				selectProjectFileLabel.setDisable(false);
				selectFileButton.setDisable(false);
				trainsInTpcFileLabel.setDisable(true);
				trainsInTpcFileTextArea.setDisable(true);
				trainsInTpcFileTextArea.setText("Select TPC file...");
				lineLabel.setDisable(true);
				lineComboBox.setDisable(true);
				lineComboBox.getItems().clear();
				fileNameLabel.setText("");
			}	
		}
		else
		{
			DirectoryChooser directoryChooser = new DirectoryChooser();

			// See if last location is stored
			if ((prefs.get("gx_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("gx_lastDirectorySavedTo", null));
				if ((path.toFile().exists()) && (path !=null))
					directoryChooser.setInitialDirectory(path.toFile());
			}

			directoryChooser.setTitle("Select Folder");

			Stage stageForFolderChooser = (Stage) executeButton.getScene().getWindow();

			File directory = directoryChooser.showDialog(stageForFolderChooser);
			if (directory != null)
			{
				// Ready to execute
				message = "\nFound "+getPrelimDataTPC.returnAvailableTrains().size()+" trains in TPC file with a reporting increment of "+getPrelimDataTPC.returnTPCIncrementsIncludingUnits().toArray()[0].toString();
				message += "\nSet to perform grade crossing speed analysis on "+lineToAnalyze+" line ";
				message += "\n  from case "+fileAsString.replace(".TPC", "");
				message += "\nStarting analysis of grade crossing speeds at "+ConvertDateTime.getTimeStamp()+" on "+ConvertDateTime.getDateStamp();
				displayMessage(message);
				
				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put("gx_lastDirectorySavedTo", directory.toString());

				saveFileFolderForSerialFileName = directory.toString();

				progressBar.setVisible(true);
				setProgressIndicator(0.00);
				executeButton.setDisable(true);
				selectFileButton.setDisable(true);
				selectProjectFileLabel.setDisable(true);
				trainsInTpcFileLabel.setDisable(true);
				trainsInTpcFileTextArea.setDisable(true);
				lineLabel.setDisable(true);
				lineComboBox.setDisable(true);

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
				selectProjectFileLabel.setDisable(false);
				selectFileButton.setDisable(false);
				trainsInTpcFileLabel.setDisable(true);
				trainsInTpcFileTextArea.setDisable(true);
				trainsInTpcFileTextArea.setText("Select TPC file...");
				fileNameLabel.setText("");
				lineLabel.setDisable(true);
				lineComboBox.setDisable(true);
				lineComboBox.getItems().clear();
			}	
		}
	}

	@FXML private void handleResetButton(ActionEvent event) 
	{
		resetMessage();

		progressBar.setVisible(false);
		setProgressIndicator(0.00);

		executeButton.setVisible(true);
		resetButton.setVisible(false);
		selectProjectFileLabel.setDisable(false);
		selectFileButton.setDisable(false);
		trainsInTpcFileTextArea.setText("Select TPC file...");
		fileNameLabel.setText("");
		lineLabel.setDisable(true);
		lineComboBox.setDisable(true);
		lineComboBox.getItems().clear();
	}

	private void chooseFile()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		FileChooser.ExtensionFilter fileExtensions = 
				new FileChooser.ExtensionFilter(
						"RTC TPC File", "*.TPC");

		fileChooser.getExtensionFilters().add(fileExtensions);		

		// See if last directory is stored
		if ((prefs.get("gx_lastDirectoryForGradeXingSpeedAnalysis", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
		{
			Path path = Paths.get(prefs.get("gx_lastDirectoryForGradeXingSpeedAnalysis", null));

			if ((path.toFile().exists()) && (path !=null))
				fileChooser.setInitialDirectory(path.toFile());
		}

		// Show the chooser and get the file
		Stage stageForFileChooser = (Stage) selectFileButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		// Valid .TPC file found
		if (file != null)
		{
			Boolean linkFileFound = false;
			Boolean nodeFileFound = false;
			Boolean optionFileFound = false;
			Boolean trainFileFound = false;
			Boolean lineFileFound = false;

			// Reset comobox entries and checkbox status
			executeButton.setDisable(true);

			// Write message in main process text area
			clearMessage();
			message = "BIAS Grade Crossing Speed Analysis Module - "+BIASLaunch.getSoftwareVersion()+"\n";

			// Reset train text area & label
			trainsInTpcFileTextArea.clear();
			trainsInTpcFileTextArea.setDisable(true);
			trainsInTpcFileLabel.setDisable(true);

			// Reset line labels and combobox
			lineLabel.setDisable(true);
			lineComboBox.setDisable(true);
			lineComboBox.getItems().clear();

			// Store path for subsequent runs and set labels
			fileAsString = file.getName().toString();
			fullyQualifiedPath = file.toString();
			fileNameLabel.setText(fullyQualifiedPath);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("gx_lastDirectoryForGradeXingSpeedAnalysis", file.getParent());

			// Check that .LINK, .OPTION, .TRAIN, .NODE and .LINE files exist
			File linkFile = new File(file.getParent(), fileAsString.replace(".TPC", ".LINK"));
			if (linkFile.exists())
				linkFileFound = true;
			else
				message += "\n.LINK file is missing!";

			File optionFile = new File(file.getParent(), fileAsString.replace(".TPC", ".OPTION"));
			if (optionFile.exists())
				optionFileFound = true;
			else
				message += "\n.OPTION file is missing!";

			File trainFile = new File(file.getParent(), fileAsString.replace(".TPC", ".TRAIN"));
			if (trainFile.exists())
				trainFileFound = true;
			else
				message += "\n.TRAIN file is missing!";

			File nodeFile = new File(file.getParent(), fileAsString.replace(".TPC", ".NODE"));
			if (nodeFile.exists())
				nodeFileFound = true;
			else
				message += "\n.NODE file is missing!";

			File lineFile = new File(file.getParent(), fileAsString.replace(".TPC", ".LINE"));
			if (lineFile.exists())
				lineFileFound = true;
			else
				message += "\n.LINE file is missing!";

			if (linkFileFound && optionFileFound && trainFileFound && nodeFileFound && lineFileFound)
			{
				// Generate group names and abbreviations
				getPrelimDataGroupNames = new AssignTrainTypeNameToTrainGroupName(optionFile);

				// Check .TPC file to generate entries for textfield 
				getPrelimDataTPC = new BIASPreprocessTrainsForGradeXingSpeedAnalysis(file);

				// Check .LINE file to generate entries for lineComboBox 
				getPrelimDataLines = new BIASPreprocessLinesForMaintenanceWindowAndGradeXingSpeedAnalysis(lineFile);

				if (getPrelimDataTPC.returnTPCIncrementsIncludingUnits().size() > 1)
				{
					message += "\nUnable to perform analysis due to multiple TPC increments used in .TPC file";
				}
				else if (getPrelimDataTPC.returnTPCIncrementsIncludingUnits().size() == 0)
				{
					message += "\nUnable to perform analysis due to no TPC increment specified in .TPC file";
				}
				else
				{
					// Check that .TPC increment is equal to or smaller than the maximum user-configurable TPC increment
					if (Collections.max(getPrelimDataTPC.returnTPCIncrementsDigitsOnly()) <= BIASGradeXingSpeedsConfigController.getMaxTpcIncrement())
					{
						// Check .OPTION file to make sure that correct parameters are selected
						BIASValidateOptionsSchemeB.bIASCheckOptionFiles(optionFile);

						if (BIASValidateOptionsSchemeB.getOptionsFilesFormattedCorrectly())
						{
							if (getPrelimDataTPC.returnAvailableTrains().size() > 0)
							{
								trainsInTpcFileLabel.setDisable(false);

								trainsInTpcFileTextArea.setDisable(false);
								trainsInTpcFileTextArea.setEditable(false);
								trainsInTpcFileTextArea.clear();

								for (int i = 0; i < getPrelimDataTPC.returnAvailableTrains().size(); i++)
									trainsInTpcFileTextArea.appendText(getPrelimDataTPC.returnAvailableTrains().get(i)+"\n");

								// Select LINE
								lineLabel.setDisable(false);
								lineComboBox.getItems().addAll(getPrelimDataLines.returnAllAvailableLines());
								lineComboBox.getItems().add(0, "Entire Network");
								lineComboBox.setDisable(false);
							}
							else
							{
								message += "\nUnable to perform analysis due to no trains in TPC file";
							}
						}
						else
						{
							message += "\nUnable to perform analysis due to invalid output format and/or speed/distance units in .OPTION file\n";
						}			
					}
					else
					{
						message += "\nUnable to perform analysis as TPC increment in TPC file \nis greater than user-specified maximum TPC increment";
					}
				}
			}
			else
				message += "\n\nUnable to perform analysis due to missing file(s)";	
		}
		else
		{
			//  Did not commit read input file
			resetMessage();

			executeButton.setDisable(true);
			executeButton.setVisible(true);
			resetButton.setVisible(false);
			selectProjectFileLabel.setDisable(false);
			selectFileButton.setDisable(false);
			trainsInTpcFileLabel.setDisable(true);
			trainsInTpcFileTextArea.setDisable(true);
			trainsInTpcFileTextArea.setText("Select TPC file...");
			fileNameLabel.setText("");
			lineLabel.setDisable(true);
			lineComboBox.setDisable(true);
			lineComboBox.getItems().clear();
		}

		displayMessage(message);
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

	private void runTask()
	{
		// Read Objects that are required for the grade xing speed analysis
		ReadGradeXingAnalysisFiles readData = new ReadGradeXingAnalysisFiles(fullyQualifiedPath, lineToAnalyze);
		message = readData.getResultsMessage();
		displayMessage("\n\n"+message+"\n");

		setProgressIndicator(0.33);

		if (ReadGradeXingAnalysisFiles.getValidTrainCount() > 0)
		{
			//  Perform Analysis
			GradeXingSpeedsAnalysis analyzeData = new GradeXingSpeedsAnalysis();
			message = analyzeData.getResultsMessage();
			displayMessage(analyzeData.getResultsMessage());

			setProgressIndicator(0.75);

			// Write Results
			writeFiles();
			if (!WriteGradeXingFiles4.getErrorFound())
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
			displayMessage("*** PROCESSING NOT COMPLETE!!! ***");
		}

		// Prepare for next run
		executeButton.setVisible(false);
		resetButton.setVisible(true);
		resetButton.setDisable(false);
	}

	private void writeFiles() 
	{
		WriteGradeXingFiles4 filesToWrite = new WriteGradeXingFiles4(processTextArea.getText(), saveFileLocationForUserSpecifiedFileName);
		displayMessage(filesToWrite.getResultsMessageWrite4());
	}

	private void resetMessage()
	{
		message="";
		processTextArea.setText("Select processing options...");
	}

	private void clearMessage()
	{
		message="";
		processTextArea.setText("");
	}

	private void displayMessage(String message)
	{
		processTextArea.appendText(message);
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